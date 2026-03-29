/*
 * Copyright 2024 Typelevel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.typelevel.otel4s.sdk.testkit.trace

import cats.data.NonEmptyList
import cats.syntax.functor._
import org.typelevel.otel4s.sdk.testkit.CollectionExpectationChecks
import org.typelevel.otel4s.sdk.testkit.ExpectationChecks
import org.typelevel.otel4s.sdk.testkit.LogicalOperator
import org.typelevel.otel4s.sdk.trace.data.EventData

/** A partial expectation over a collection of span events.
  *
  * `EventSetExpectation` is used for collection-level assertions such as exact membership, counts, and predicates over
  * the entire event list. Expectations can be combined with logical `and` and `or`.
  */
sealed trait EventSetExpectation {

  /** An optional human-readable clue shown in mismatch messages. */
  def clue: Option[String]

  /** Combines this expectation with another one using logical `and`. */
  def and(other: EventSetExpectation): EventSetExpectation =
    EventSetExpectation.and(this, other)

  /** Combines this expectation with another one using logical `or`. */
  def or(other: EventSetExpectation): EventSetExpectation =
    EventSetExpectation.or(this, other)

  /** Attaches a human-readable clue to this expectation. */
  def clue(text: String): EventSetExpectation

  private[trace] def check(events: List[EventData]): Either[NonEmptyList[EventSetExpectation.Mismatch], Unit]
}

object EventSetExpectation {

  /** A structured reason explaining why an [[EventSetExpectation]] did not match the collected events. */
  sealed trait Mismatch extends Product with Serializable {

    /** A human-readable description of the mismatch. */
    def message: String
  }

  object Mismatch {
    private[testkit] final case class EventCountMismatch(expected: Int, actual: Int) extends Mismatch {
      def message: String = s"event count mismatch: expected $expected, got $actual"
    }

    private[testkit] final case class MinimumEventCountMismatch(expectedAtLeast: Int, actual: Int) extends Mismatch {
      def message: String = s"event count mismatch: expected at least $expectedAtLeast, got $actual"
    }

    private[testkit] final case class MaximumEventCountMismatch(expectedAtMost: Int, actual: Int) extends Mismatch {
      def message: String = s"event count mismatch: expected at most $expectedAtMost, got $actual"
    }

    private[testkit] final case class MatchedEventCountMismatch(expected: Int, actual: Int) extends Mismatch {
      def message: String = s"matched event count mismatch: expected $expected, got $actual"
    }

    private[testkit] final case class MissingExpectedEvent(
        clue: Option[String],
        mismatches: NonEmptyList[EventExpectation.Mismatch]
    ) extends Mismatch {
      def message: String =
        s"missing expected event${clue.fold("")(v => s" [$v]")}: ${mismatches.toList.map(_.message).mkString(", ")}"
    }

    private[testkit] final case class UnexpectedEvent(index: Int) extends Mismatch {
      def message: String = s"unexpected event at index $index"
    }

    private[testkit] final case class FailingEvent(index: Int, mismatches: NonEmptyList[EventExpectation.Mismatch])
        extends Mismatch {
      def message: String = s"failing event at index $index: ${mismatches.toList.map(_.message).mkString(", ")}"
    }

    private[testkit] final case class PredicateFailed(clue: Option[String]) extends Mismatch {
      def message: String = s"event set predicate returned false${clue.fold("")(v => s": $v")}"
    }

    private[testkit] case object NoEventsCollected extends Mismatch {
      def message: String = "no events were collected"
    }

    private[testkit] final case class CompositeMismatch(
        /** The logical operator that combined the nested mismatches. */
        operator: LogicalOperator,
        mismatches: NonEmptyList[Mismatch]
    ) extends Mismatch {
      def message: String = s"${operator.render} mismatch: ${mismatches.toList.map(_.message).mkString(", ")}"
    }

    private[testkit] final case class CluedMismatch(clue: String, mismatches: NonEmptyList[Mismatch]) extends Mismatch {
      def message: String = s"event-set mismatch [$clue]: ${mismatches.toList.map(_.message).mkString(", ")}"
    }
  }

  /** Creates an expectation that accepts any event collection. */
  def any: EventSetExpectation = AnyImpl(None)

  /** Requires at least one collected event to match the given expectation. */
  def exists(event: EventExpectation): EventSetExpectation = ExistsImpl(event, None)

  /** Requires every collected event to match the given expectation. */
  def forall(event: EventExpectation): EventSetExpectation = ForAllImpl(event, None)

  /** Requires the collected events to contain all given expectations. */
  def contains(first: EventExpectation, rest: EventExpectation*): EventSetExpectation =
    ContainsImpl(NonEmptyList(first, rest.toList), None)

  /** Requires the collected events to match the given expectations exactly. */
  def exactly(first: EventExpectation, rest: EventExpectation*): EventSetExpectation =
    ExactlyImpl(NonEmptyList(first, rest.toList), None)

  /** Requires the collected events to have exactly the given size. */
  def count(expected: Int): EventSetExpectation = CountImpl(expected, None)

  /** Requires the collected events to have at least the given size. */
  def countAtLeast(expectedAtLeast: Int): EventSetExpectation = MinCountImpl(expectedAtLeast, None)

  /** Requires the collected events to have at most the given size. */
  def countAtMost(expectedAtMost: Int): EventSetExpectation = MaxCountImpl(expectedAtMost, None)

  /** Requires exactly the given number of collected events to match the expectation. */
  def countWhere(event: EventExpectation, expected: Int): EventSetExpectation = CountWhereImpl(event, expected, None)

  /** Requires no collected event to match the given expectation. */
  def none(event: EventExpectation): EventSetExpectation = NoneOfImpl(event, None)

  /** Adds a custom predicate over the full event collection. */
  def where(f: List[EventData] => Boolean): EventSetExpectation = PredicateImpl(f, None)

  /** Adds a custom predicate over the full event collection with a clue shown in mismatches. */
  def where(clue: String)(f: List[EventData] => Boolean): EventSetExpectation = PredicateImpl(f, Some(clue))

  /** Combines two event-set expectations using logical `and`. */
  def and(left: EventSetExpectation, right: EventSetExpectation): EventSetExpectation = AndImpl(left, right, None)

  /** Combines two event-set expectations using logical `or`. */
  def or(left: EventSetExpectation, right: EventSetExpectation): EventSetExpectation = OrImpl(left, right, None)

  private final case class AnyImpl(clue: Option[String]) extends EventSetExpectation {
    def clue(text: String): EventSetExpectation = copy(clue = Some(text))
    def check(events: List[EventData]): Either[NonEmptyList[Mismatch], Unit] = ExpectationChecks.success
  }

  private final case class ExistsImpl(event: EventExpectation, clue: Option[String]) extends EventSetExpectation {
    def clue(text: String): EventSetExpectation = copy(clue = Some(text))
    def check(events: List[EventData]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        if (events.exists(event.matches)) {
          ExpectationChecks.success
        } else {
          val mismatch = Mismatch.MissingExpectedEvent(event.clue, closestMismatch(events, event))
          ExpectationChecks.mismatch(mismatch)
        }
      }
  }

  private final case class ForAllImpl(event: EventExpectation, clue: Option[String]) extends EventSetExpectation {
    def clue(text: String): EventSetExpectation = copy(clue = Some(text))
    def check(events: List[EventData]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        if (events.isEmpty) {
          ExpectationChecks.mismatch(Mismatch.NoEventsCollected)
        } else {
          CollectionExpectationChecks.firstFailingIndex(events, event)(
            (expectation, actual) => expectation.check(actual),
            (index, mismatches) => Mismatch.FailingEvent(index, mismatches)
          ) match {
            case Some(mismatch) => ExpectationChecks.mismatch(mismatch)
            case None           => ExpectationChecks.success
          }
        }
      }
  }

  private final case class ContainsImpl(expected: NonEmptyList[EventExpectation], clue: Option[String])
      extends EventSetExpectation {
    def clue(text: String): EventSetExpectation = copy(clue = Some(text))
    def check(events: List[EventData]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue)(containsCheck(expected, events).void)
  }

  private final case class ExactlyImpl(expected: NonEmptyList[EventExpectation], clue: Option[String])
      extends EventSetExpectation {
    def clue(text: String): EventSetExpectation = copy(clue = Some(text))
    def check(events: List[EventData]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        containsCheck(expected, events).flatMap { matchedIndices =>
          val unexpected = events.indices.filterNot(matchedIndices.contains).map(Mismatch.UnexpectedEvent(_)).toList
          NonEmptyList.fromList(unexpected).toLeft(())
        }
      }
  }

  private final case class CountImpl(expected: Int, clue: Option[String]) extends EventSetExpectation {
    def clue(text: String): EventSetExpectation = copy(clue = Some(text))
    def check(events: List[EventData]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        if (events.length == expected) ExpectationChecks.success
        else ExpectationChecks.mismatch(Mismatch.EventCountMismatch(expected, events.length))
      }
  }

  private final case class MinCountImpl(expectedAtLeast: Int, clue: Option[String]) extends EventSetExpectation {
    def clue(text: String): EventSetExpectation = copy(clue = Some(text))
    def check(events: List[EventData]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        if (events.length >= expectedAtLeast) ExpectationChecks.success
        else ExpectationChecks.mismatch(Mismatch.MinimumEventCountMismatch(expectedAtLeast, events.length))
      }
  }

  private final case class MaxCountImpl(expectedAtMost: Int, clue: Option[String]) extends EventSetExpectation {
    def clue(text: String): EventSetExpectation = copy(clue = Some(text))
    def check(events: List[EventData]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        if (events.length <= expectedAtMost) ExpectationChecks.success
        else ExpectationChecks.mismatch(Mismatch.MaximumEventCountMismatch(expectedAtMost, events.length))
      }
  }

  private final case class CountWhereImpl(event: EventExpectation, expected: Int, clue: Option[String])
      extends EventSetExpectation {
    def clue(text: String): EventSetExpectation = copy(clue = Some(text))
    def check(events: List[EventData]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        val actual = events.count(event.matches)
        if (actual == expected) ExpectationChecks.success
        else ExpectationChecks.mismatch(Mismatch.MatchedEventCountMismatch(expected, actual))
      }
  }

  private final case class NoneOfImpl(event: EventExpectation, clue: Option[String]) extends EventSetExpectation {
    def clue(text: String): EventSetExpectation = copy(clue = Some(text))
    def check(events: List[EventData]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        val firstMatch = CollectionExpectationChecks.firstMatchingIndex(events, event)((expectation, actual) =>
          expectation.matches(actual)
        )

        firstMatch match {
          case Some(index) => ExpectationChecks.mismatch(Mismatch.UnexpectedEvent(index))
          case None        => ExpectationChecks.success
        }
      }
  }

  private final case class PredicateImpl(f: List[EventData] => Boolean, clue: Option[String])
      extends EventSetExpectation {
    def clue(text: String): EventSetExpectation = copy(clue = Some(text))
    def check(events: List[EventData]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue)(Either.cond(f(events), (), NonEmptyList.one(Mismatch.PredicateFailed(clue))))
  }

  private final case class AndImpl(left: EventSetExpectation, right: EventSetExpectation, clue: Option[String])
      extends EventSetExpectation {
    def clue(text: String): EventSetExpectation = copy(clue = Some(text))
    def check(events: List[EventData]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        CollectionExpectationChecks.andCheck(left.check(events), right.check(events)) { mismatches =>
          Mismatch.CompositeMismatch(LogicalOperator.And, mismatches)
        }
      }
  }

  private final case class OrImpl(left: EventSetExpectation, right: EventSetExpectation, clue: Option[String])
      extends EventSetExpectation {
    def clue(text: String): EventSetExpectation = copy(clue = Some(text))
    def check(events: List[EventData]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        CollectionExpectationChecks.orCheck(left.check(events), right.check(events)) { mismatches =>
          Mismatch.CompositeMismatch(LogicalOperator.Or, mismatches)
        }
      }
  }

  private def withClueContext(
      clue: Option[String]
  )(result: Either[NonEmptyList[Mismatch], Unit]): Either[NonEmptyList[Mismatch], Unit] =
    CollectionExpectationChecks.withClueContext(clue, result) { (clue, mismatches) =>
      Mismatch.CluedMismatch(clue, mismatches)
    }

  private def closestMismatch(
      events: List[EventData],
      expectation: EventExpectation
  ): NonEmptyList[EventExpectation.Mismatch] =
    CollectionExpectationChecks.closestMismatch(events, expectation)(
      (expected, actual) => expected.check(actual),
      EventExpectation.Mismatch.PredicateMismatch(Some("no events available"))
    )

  private def containsCheck(
      expected: NonEmptyList[EventExpectation],
      events: List[EventData]
  ): Either[NonEmptyList[Mismatch], Set[Int]] =
    CollectionExpectationChecks.containsCheck[EventData, EventExpectation, Mismatch, EventExpectation.Mismatch](
      expected,
      events
    )(
      (expectation, actual) => expectation.matches(actual),
      (event, mismatches) => Mismatch.MissingExpectedEvent(event.clue, mismatches),
      closestMismatch
    )
}
