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

package org.typelevel.otel4s.sdk.testkit.metrics

import cats.data.NonEmptyList
import cats.syntax.functor._
import org.typelevel.otel4s.sdk.metrics.data.PointData
import org.typelevel.otel4s.sdk.testkit.CollectionExpectationChecks
import org.typelevel.otel4s.sdk.testkit.ExpectationChecks
import org.typelevel.otel4s.sdk.testkit.LogicalOperator
import org.typelevel.otel4s.sdk.testkit.MaximumMatching

/** A partial expectation over a collection of metric points.
  *
  * `PointSetExpectation` is the collection-level layer that sits between individual point expectations and full metric
  * expectations. It can express existential, universal, cardinality, and exact-set constraints over a metric's points.
  */
sealed trait PointSetExpectation[P] {

  /** An optional human-readable clue shown in mismatch messages. */
  def clue: Option[String]

  /** Combines this expectation with another one using logical conjunction. */
  def and(other: PointSetExpectation[P]): PointSetExpectation[P] =
    PointSetExpectation.and(this, other)

  /** Combines this expectation with another one using logical disjunction. */
  def or(other: PointSetExpectation[P]): PointSetExpectation[P] =
    PointSetExpectation.or(this, other)

  /** Attaches a human-readable clue to this expectation. */
  def clue(text: String): PointSetExpectation[P]

  private[metrics] def check(points: List[P]): Either[NonEmptyList[PointSetExpectation.Mismatch], Unit]
}

object PointSetExpectation {

  /** A structured reason explaining why a [[PointSetExpectation]] did not match a collection of points. */
  sealed trait Mismatch extends Product with Serializable {

    /** A human-readable description of the mismatch. */
    def message: String
  }

  object Mismatch {

    private[testkit] final case class PointCountMismatch(expected: Int, actual: Int) extends Mismatch {
      def message: String =
        s"point count mismatch: expected $expected, got $actual"
    }

    private[testkit] final case class MinimumPointCountMismatch(expectedAtLeast: Int, actual: Int) extends Mismatch {
      def message: String =
        s"point count mismatch: expected at least $expectedAtLeast, got $actual"
    }

    private[testkit] final case class MaximumPointCountMismatch(expectedAtMost: Int, actual: Int) extends Mismatch {
      def message: String =
        s"point count mismatch: expected at most $expectedAtMost, got $actual"
    }

    private[testkit] final case class MatchedPointCountMismatch(expected: Int, actual: Int) extends Mismatch {
      def message: String =
        s"matched point count mismatch: expected $expected, got $actual"
    }

    private[testkit] final case class MissingExpectedPoint(
        clue: Option[String],
        mismatches: NonEmptyList[PointExpectation.Mismatch]
    ) extends Mismatch {
      def message: String = {
        val prefix = clue.fold("")(value => s" [$value]")
        s"missing expected point$prefix: ${mismatches.toList.map(_.message).mkString(", ")}"
      }
    }

    private[testkit] final case class UnexpectedPoint(index: Int) extends Mismatch {
      def message: String =
        s"unexpected point at index $index"
    }

    private[testkit] final case class FailingPoint(index: Int, mismatches: NonEmptyList[PointExpectation.Mismatch])
        extends Mismatch {
      def message: String =
        s"failing point at index $index: ${mismatches.toList.map(_.message).mkString(", ")}"
    }

    private[testkit] final case class PredicateFailed(clue: Option[String]) extends Mismatch {
      def message: String =
        s"point set predicate returned false${clue.fold("")(value => s": $value")}"
    }

    private[testkit] case object NoPointsCollected extends Mismatch {
      def message: String =
        "no points were collected"
    }

    private[testkit] final case class CompositeMismatch(
        /** The logical operator that combined the nested mismatches. */
        operator: LogicalOperator,
        mismatches: NonEmptyList[Mismatch]
    ) extends Mismatch {
      def message: String =
        s"${operator.render} mismatch: ${mismatches.toList.map(_.message).mkString(", ")}"
    }

    private[testkit] final case class CluedMismatch(clue: String, mismatches: NonEmptyList[Mismatch]) extends Mismatch {
      def message: String =
        s"point-set mismatch [$clue]: ${mismatches.toList.map(_.message).mkString(", ")}"
    }
  }

  /** Type class used to evaluate a single point expectation against a collected point of the corresponding type. */
  trait SinglePointChecker[E] {
    type P

    /** Returns the clue attached to the point expectation, if any. */
    def clue(expectation: E): Option[String]

    /** Checks a collected point against a point expectation. */
    def check(expectation: E, point: P): Either[NonEmptyList[PointExpectation.Mismatch], Unit]
  }

  object SinglePointChecker {

    /** Refines [[SinglePointChecker]] with an explicit point type member. */
    type Aux[E, P0] = SinglePointChecker[E] { type P = P0 }

    implicit def numeric[A]: Aux[PointExpectation.Numeric[A], PointData.NumberPoint.Aux[A]] =
      new SinglePointChecker[PointExpectation.Numeric[A]] {
        type P = PointData.NumberPoint.Aux[A]
        def clue(expectation: PointExpectation.Numeric[A]): Option[String] = expectation.clue
        def check(
            expectation: PointExpectation.Numeric[A],
            point: PointData.NumberPoint.Aux[A]
        ): Either[NonEmptyList[PointExpectation.Mismatch], Unit] =
          expectation.check(point)
      }

    implicit val histogram: Aux[PointExpectation.Histogram, PointData.Histogram] =
      new SinglePointChecker[PointExpectation.Histogram] {
        type P = PointData.Histogram
        def clue(expectation: PointExpectation.Histogram): Option[String] = expectation.clue
        def check(
            expectation: PointExpectation.Histogram,
            point: PointData.Histogram
        ): Either[NonEmptyList[PointExpectation.Mismatch], Unit] =
          expectation.check(point)
      }
  }

  /** Creates an expectation that matches any point collection. */
  def any[P]: PointSetExpectation[P] =
    AnyPoint(None)

  /** Creates an expectation that requires at least one collected point to match the given point expectation. */
  def exists[E](point: E)(implicit checker: SinglePointChecker[E]): PointSetExpectation[checker.P] =
    Exists(point, checker, None)

  /** Creates an expectation that requires every collected point to match the given point expectation. */
  def forall[E](point: E)(implicit checker: SinglePointChecker[E]): PointSetExpectation[checker.P] =
    ForAll(point, checker, None)

  /** Creates an expectation that requires the point collection to contain distinct matches for all given expectations.
    */
  def contains[E](first: E, rest: E*)(implicit checker: SinglePointChecker[E]): PointSetExpectation[checker.P] =
    Contains(NonEmptyList(first, rest.toList), checker, None)

  /** Creates an expectation that requires the point collection to match the given point expectations exactly. */
  def exactly[E](first: E, rest: E*)(implicit checker: SinglePointChecker[E]): PointSetExpectation[checker.P] =
    Exactly(NonEmptyList(first, rest.toList), checker, None)

  /** Creates an expectation that requires the point collection to have exactly the given size. */
  def count[P](expected: Int): PointSetExpectation[P] =
    Count(expected, None)

  /** Creates an expectation that requires the point collection to have at least the given size. */
  def minCount[P](expectedAtLeast: Int): PointSetExpectation[P] =
    MinCount(expectedAtLeast, None)

  /** Creates an expectation that requires the point collection to have at most the given size. */
  def maxCount[P](expectedAtMost: Int): PointSetExpectation[P] =
    MaxCount(expectedAtMost, None)

  /** Creates an expectation that requires exactly the given number of collected points to match the point expectation.
    */
  def countWhere[E](point: E, expected: Int)(implicit checker: SinglePointChecker[E]): PointSetExpectation[checker.P] =
    CountWhere(point, expected, checker, None)

  /** Creates an expectation that requires no collected point to match the given point expectation. */
  def none[E](point: E)(implicit checker: SinglePointChecker[E]): PointSetExpectation[checker.P] =
    NoneOf(point, checker, None)

  /** Creates an expectation from a custom predicate over the entire point collection. */
  def predicate[P](f: List[P] => Boolean): PointSetExpectation[P] =
    Predicate(f, None)

  /** Creates an expectation from a custom predicate over the entire point collection with an explanatory clue. */
  def predicate[P](clue: String)(f: List[P] => Boolean): PointSetExpectation[P] =
    Predicate(f, Some(clue))

  /** Combines two point-set expectations using logical conjunction. */
  def and[P](left: PointSetExpectation[P], right: PointSetExpectation[P]): PointSetExpectation[P] =
    Composite(left, right, LogicalOperator.And, None)

  /** Combines two point-set expectations using logical disjunction. */
  def or[P](left: PointSetExpectation[P], right: PointSetExpectation[P]): PointSetExpectation[P] =
    Composite(left, right, LogicalOperator.Or, None)

  private final case class AnyPoint[P](clue: Option[String]) extends PointSetExpectation[P] {
    def clue(text: String): PointSetExpectation[P] = copy(clue = Some(text))
    def check(points: List[P]): Either[NonEmptyList[Mismatch], Unit] = ExpectationChecks.success
  }

  private final case class Exists[E, P](
      point: E,
      checker: SinglePointChecker.Aux[E, P],
      clue: Option[String]
  ) extends PointSetExpectation[P] {
    def clue(text: String): PointSetExpectation[P] = copy(clue = Some(text))
    def check(points: List[P]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        if (points.exists(point => checker.check(this.point, point).isRight)) {
          ExpectationChecks.success
        } else {
          val mismatch = Mismatch.MissingExpectedPoint(checker.clue(point), closestMismatch(points, point, checker))
          ExpectationChecks.mismatch(mismatch)
        }
      }
  }

  private final case class ForAll[E, P](
      point: E,
      checker: SinglePointChecker.Aux[E, P],
      clue: Option[String]
  ) extends PointSetExpectation[P] {
    def clue(text: String): PointSetExpectation[P] = copy(clue = Some(text))
    def check(points: List[P]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        if (points.isEmpty) {
          ExpectationChecks.mismatch(Mismatch.NoPointsCollected)
        } else {
          val firstMismatch = CollectionExpectationChecks.firstFailingIndex(points, point)(
            (expectation, actual) => checker.check(expectation, actual),
            (index, mismatches) => Mismatch.FailingPoint(index, mismatches)
          )

          firstMismatch match {
            case Some(mismatch) => ExpectationChecks.mismatch(mismatch)
            case None           => ExpectationChecks.success
          }
        }
      }
  }

  private final case class Contains[E, P](
      expected: NonEmptyList[E],
      checker: SinglePointChecker.Aux[E, P],
      clue: Option[String]
  ) extends PointSetExpectation[P] {
    def clue(text: String): PointSetExpectation[P] = copy(clue = Some(text))
    def check(points: List[P]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue)(containsCheck(expected, checker, points).void)
  }

  private final case class Exactly[E, P](
      expected: NonEmptyList[E],
      checker: SinglePointChecker.Aux[E, P],
      clue: Option[String]
  ) extends PointSetExpectation[P] {
    def clue(text: String): PointSetExpectation[P] = copy(clue = Some(text))
    def check(points: List[P]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        containsCheck(expected, checker, points).flatMap { matchedIndices =>
          val unexpected = points.indices.filterNot(matchedIndices.contains).map(Mismatch.UnexpectedPoint(_)).toList
          NonEmptyList.fromList(unexpected).toLeft(())
        }
      }
  }

  private final case class Count[P](expected: Int, clue: Option[String]) extends PointSetExpectation[P] {
    def clue(text: String): PointSetExpectation[P] = copy(clue = Some(text))
    def check(points: List[P]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        if (points.length == expected) ExpectationChecks.success
        else ExpectationChecks.mismatch(Mismatch.PointCountMismatch(expected, points.length))
      }
  }

  private final case class MinCount[P](expectedAtLeast: Int, clue: Option[String]) extends PointSetExpectation[P] {
    def clue(text: String): PointSetExpectation[P] = copy(clue = Some(text))
    def check(points: List[P]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        if (points.length >= expectedAtLeast) ExpectationChecks.success
        else ExpectationChecks.mismatch(Mismatch.MinimumPointCountMismatch(expectedAtLeast, points.length))
      }
  }

  private final case class MaxCount[P](expectedAtMost: Int, clue: Option[String]) extends PointSetExpectation[P] {
    def clue(text: String): PointSetExpectation[P] = copy(clue = Some(text))
    def check(points: List[P]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        if (points.length <= expectedAtMost) ExpectationChecks.success
        else ExpectationChecks.mismatch(Mismatch.MaximumPointCountMismatch(expectedAtMost, points.length))
      }

  }

  private final case class CountWhere[E, P](
      point: E,
      expected: Int,
      checker: SinglePointChecker.Aux[E, P],
      clue: Option[String]
  ) extends PointSetExpectation[P] {
    def clue(text: String): PointSetExpectation[P] = copy(clue = Some(text))
    def check(points: List[P]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        val actual = points.count(point => checker.check(this.point, point).isRight)
        if (actual == expected) ExpectationChecks.success
        else ExpectationChecks.mismatch(Mismatch.MatchedPointCountMismatch(expected, actual))
      }
  }

  private final case class NoneOf[E, P](
      point: E,
      checker: SinglePointChecker.Aux[E, P],
      clue: Option[String]
  ) extends PointSetExpectation[P] {
    def clue(text: String): PointSetExpectation[P] = copy(clue = Some(text))
    def check(points: List[P]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        val firstMatch = CollectionExpectationChecks.firstMatchingIndex(points, point)((expectation, actual) =>
          checker.check(expectation, actual).isRight
        )

        firstMatch match {
          case None        => ExpectationChecks.success
          case Some(index) => ExpectationChecks.mismatch(Mismatch.UnexpectedPoint(index))
        }

      }
  }

  private final case class Predicate[P](
      f: List[P] => Boolean,
      clue: Option[String]
  ) extends PointSetExpectation[P] {
    def clue(text: String): PointSetExpectation[P] = copy(clue = Some(text))
    def check(points: List[P]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue)(Either.cond(f(points), (), NonEmptyList.one(Mismatch.PredicateFailed(clue))))
  }

  private final case class Composite[P](
      left: PointSetExpectation[P],
      right: PointSetExpectation[P],
      operator: LogicalOperator,
      clue: Option[String]
  ) extends PointSetExpectation[P] {
    def clue(text: String): PointSetExpectation[P] = copy(clue = Some(text))
    def check(points: List[P]): Either[NonEmptyList[Mismatch], Unit] =
      withClueContext(clue) {
        operator match {
          case LogicalOperator.And =>
            CollectionExpectationChecks.andCheck(left.check(points), right.check(points)) { mismatches =>
              Mismatch.CompositeMismatch(operator, mismatches)
            }
          case LogicalOperator.Or =>
            CollectionExpectationChecks.orCheck(left.check(points), right.check(points)) { mismatches =>
              Mismatch.CompositeMismatch(operator, mismatches)
            }
        }
      }
  }

  private def withClueContext(clue: Option[String])(
      result: Either[NonEmptyList[Mismatch], Unit]
  ): Either[NonEmptyList[Mismatch], Unit] =
    CollectionExpectationChecks.withClueContext(clue, result) { (clue, mismatches) =>
      Mismatch.CluedMismatch(clue, mismatches)
    }

  private def closestMismatch[E, P](
      points: List[P],
      expectation: E,
      checker: SinglePointChecker.Aux[E, P]
  ): NonEmptyList[PointExpectation.Mismatch] =
    CollectionExpectationChecks.closestMismatch(points, expectation)(
      (expected, actual) => checker.check(expected, actual),
      PointExpectation.Mismatch.PredicateMismatch("no points were collected")
    )

  private def containsCheck[E, P](
      expected: NonEmptyList[E],
      checker: SinglePointChecker.Aux[E, P],
      points: List[P]
  ): Either[NonEmptyList[Mismatch], Set[Int]] = {
    val indexedPoints = points.toVector
    val candidates = expected.toList.map { expectation =>
      indexedPoints.indices.filter(index => checker.check(expectation, indexedPoints(index)).isRight).toList
    }
    val matching = MaximumMatching.find(candidates.toVector)

    if (matching.isComplete) Right(matching.matchedCandidateIndices)
    else {
      val missing = expected.toList.zip(candidates).collect { case (expectation, Nil) =>
        Mismatch.MissingExpectedPoint(checker.clue(expectation), closestMismatch(points, expectation, checker))
      }

      Left(
        NonEmptyList
          .fromList(missing)
          .getOrElse(
            NonEmptyList.one(Mismatch.MatchedPointCountMismatch(expected.length, matching.size))
          )
      )
    }
  }
}
