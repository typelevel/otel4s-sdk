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
import munit.FunSuite
import org.typelevel.otel4s.Attribute
import org.typelevel.otel4s.Attributes
import org.typelevel.otel4s.sdk.data.LimitedData
import org.typelevel.otel4s.sdk.trace.data.EventData

import scala.concurrent.duration._

class EventSetExpectationSuite extends FunSuite {

  test("exists succeeds when one event matches") {
    val events = List(event("db", 1.second), event("exception", 2.seconds, Attribute("type", "timeout")))

    assertEquals(
      EventSetExpectation.exists(EventExpectation.name("exception")).check(events),
      Right(())
    )
  }

  test("contains uses distinct matching") {
    val events = List(
      event("message", 1.second, Attribute("kind", "a")),
      event("message", 1.second, Attribute("kind", "b"))
    )

    assertEquals(
      EventSetExpectation
        .contains(
          EventExpectation.name("message").attributesSubset(Attribute("kind", "a")),
          EventExpectation.name("message").attributesSubset(Attribute("kind", "b"))
        )
        .check(events),
      Right(())
    )
  }

  test("exactly reports unexpected events") {
    val events = List(event("message", 1.second), event("extra", 2.seconds))

    assertEquals(
      EventSetExpectation.exactly(EventExpectation.name("message")).check(events),
      Left(NonEmptyList.one(EventSetExpectation.Mismatch.UnexpectedEvent(1)))
    )
  }

  test("contains reports the unmatched expectation in original order") {
    val events = List(event("message", 1.second))
    val unmatched =
      EventExpectation.name("message").where("timestamp must be 2 seconds")(_.timestamp == 2.seconds)

    val expectation = EventSetExpectation.contains(
      EventExpectation.name("message"),
      unmatched
    )

    assertMatches(expectation.check(events)) {
      case Left(NonEmptyList(mismatch: EventSetExpectation.Mismatch.MissingExpectedEvent, Nil)) =>
        assertEquals(mismatch.clue, unmatched.clue)
        assertEquals(
          mismatch.mismatches,
          NonEmptyList.one(EventExpectation.Mismatch.PredicateMismatch(Some("timestamp must be 2 seconds")))
        )
        true
    }
  }

  private def event(name: String, timestamp: FiniteDuration, attributes: Attribute[_]*): EventData =
    EventData(name, timestamp, LimitedData.attributes(8, 32).appendAll(Attributes(attributes: _*)))
}
