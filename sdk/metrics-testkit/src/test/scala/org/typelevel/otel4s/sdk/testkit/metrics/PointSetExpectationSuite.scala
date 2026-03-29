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
import cats.effect.IO
import munit.CatsEffectSuite
import munit.Location
import munit.TestOptions
import org.typelevel.otel4s.Attribute
import org.typelevel.otel4s.Attributes
import org.typelevel.otel4s.sdk.metrics.data.MetricData
import org.typelevel.otel4s.sdk.metrics.data.MetricPoints
import org.typelevel.otel4s.sdk.metrics.data.PointData
import org.typelevel.otel4s.sdk.testkit.LogicalOperator

import scala.reflect.ClassTag
import scala.reflect.classTag

class PointSetExpectationSuite extends CatsEffectSuite {

  testkitTest("any matches arbitrary point collections") { testkit =>
    for {
      meter <- testkit.meterProvider.get("test")
      counter <- meter.counter[Long]("service.counter").create
      _ <- counter.add(1L)
      points <- collectLongPoints(testkit, "service.counter")
    } yield assertSuccess(PointSetExpectation.any[PointData.NumberPoint.Aux[Long]].check(points))
  }

  testkitTest("exists matches when at least one point satisfies the expectation") { testkit =>
    for {
      meter <- testkit.meterProvider.get("test")
      counter <- meter.counter[Long]("service.counter").create
      _ <- counter.add(1L, Attributes(Attribute("region", "eu")))
      _ <- counter.add(1L, Attributes(Attribute("region", "us")))
      points <- collectLongPoints(testkit, "service.counter")
    } yield assertSuccess(
      PointSetExpectation
        .exists(PointExpectation.numeric(1L).attributesSubset(Attribute("region", "us")))
        .check(points)
    )
  }

  testkitTest("exists returns MissingExpectedPoint when no point matches") { testkit =>
    for {
      meter <- testkit.meterProvider.get("test")
      counter <- meter.counter[Long]("service.counter").create
      _ <- counter.add(1L, Attributes(Attribute("region", "eu")))
      points <- collectLongPoints(testkit, "service.counter")
    } yield {
      val result = PointSetExpectation
        .exists(PointExpectation.numeric(1L).attributesSubset(Attribute("region", "us")).clue("US point"))
        .check(points)

      val mismatch = assertMismatchType[PointSetExpectation.Mismatch.MissingExpectedPoint](result)
      assertEquals(mismatch.clue, Some("US point"))
      assert(mismatch.message.contains("missing expected point"))
    }
  }

  testkitTest("forall succeeds when every point matches") { testkit =>
    for {
      meter <- testkit.meterProvider.get("test")
      counter <- meter.counter[Long]("service.counter").create
      _ <- counter.add(1L, Attributes(Attribute("kind", "ok"), Attribute("region", "eu")))
      _ <- counter.add(1L, Attributes(Attribute("kind", "ok"), Attribute("region", "us")))
      points <- collectLongPoints(testkit, "service.counter")
    } yield assertSuccess(
      PointSetExpectation
        .forall(PointExpectation.numeric(1L).attributesSubset(Attribute("kind", "ok")))
        .check(points)
    )
  }

  test("forall fails on an empty point set") {
    val result = PointSetExpectation.forall(PointExpectation.numeric(1L)).check(Nil)
    assertMismatchType[PointSetExpectation.Mismatch.NoPointsCollected.type](result)
  }

  testkitTest("contains enforces distinct matching for duplicate expectations") { testkit =>
    for {
      meter <- testkit.meterProvider.get("test")
      counter <- meter.counter[Long]("service.counter").create
      _ <- counter.add(1L, Attributes(Attribute("region", "eu")))
      points <- collectLongPoints(testkit, "service.counter")
    } yield {
      val mismatch = assertMismatchType[PointSetExpectation.Mismatch.MatchedPointCountMismatch](
        PointSetExpectation
          .contains(
            PointExpectation.numeric(1L).attributesSubset(Attribute("region", "eu")),
            PointExpectation.numeric(1L).attributesSubset(Attribute("region", "eu"))
          )
          .check(points)
      )

      assertEquals(mismatch.expected, 2)
      assertEquals(mismatch.actual, 1)
    }
  }

  testkitTest("exactly rejects extra points") { testkit =>
    for {
      meter <- testkit.meterProvider.get("test")
      counter <- meter.counter[Long]("service.counter").create
      _ <- counter.add(1L, Attributes(Attribute("region", "eu")))
      _ <- counter.add(1L, Attributes(Attribute("region", "us")))
      _ <- counter.add(1L, Attributes(Attribute("region", "apac")))
      points <- collectLongPoints(testkit, "service.counter")
    } yield {
      val mismatch = assertMismatchType[PointSetExpectation.Mismatch.UnexpectedPoint](
        PointSetExpectation
          .exactly(
            PointExpectation.numeric(1L).attributesSubset(Attribute("region", "eu")),
            PointExpectation.numeric(1L).attributesSubset(Attribute("region", "us"))
          )
          .check(points)
      )

      assert(mismatch.index >= 0 && mismatch.index < points.length)
    }
  }

  testkitTest("predicate, and, or, and none expose collection-wide checks") { testkit =>
    for {
      meter <- testkit.meterProvider.get("test")
      counter <- meter.counter[Long]("service.counter").create
      _ <- counter.add(1L, Attributes(Attribute("region", "eu")))
      _ <- counter.add(1L, Attributes(Attribute("region", "us")))
      points <- collectLongPoints(testkit, "service.counter")
    } yield {
      assertSuccess(
        PointSetExpectation
          .predicate[PointData.NumberPoint.Aux[Long]] { (points: List[PointData.NumberPoint.Aux[Long]]) =>
            points.map(_.attributes).size == 2
          }
          .check(points)
      )

      val clued = assertMismatchType[PointSetExpectation.Mismatch.CluedMismatch](
        PointSetExpectation
          .predicate[PointData.NumberPoint.Aux[Long]]("expected a single point")(_.size == 1)
          .check(points)
      )
      assertEquals(clued.clue, "expected a single point")

      val andMismatch = assertMismatchType[PointSetExpectation.Mismatch.CompositeMismatch](
        PointSetExpectation
          .count[PointData.NumberPoint.Aux[Long]](3)
          .and(PointSetExpectation.none(PointExpectation.numeric(1L).attributesSubset(Attribute("region", "eu"))))
          .check(points)
      )
      assertEquals(andMismatch.operator, LogicalOperator.And)

      assertSuccess(
        PointSetExpectation
          .contains(PointExpectation.numeric(1L).attributesSubset(Attribute("region", "eu")))
          .or(PointSetExpectation.count[PointData.NumberPoint.Aux[Long]](3))
          .check(points)
      )
    }
  }

  testkitTest("histogram point sets are supported directly") { testkit =>
    for {
      meter <- testkit.meterProvider.get("test")
      histogram <- meter.histogram[Long]("service.histogram").create
      _ <- histogram.record(10L, Attributes(Attribute("region", "eu")))
      _ <- histogram.record(20L, Attributes(Attribute("region", "us")))
      points <- collectHistogramPoints(testkit, "service.histogram")
    } yield assertSuccess(
      PointSetExpectation
        .contains(
          PointExpectation.histogram.count(1L).sum(10.0).attributesSubset(Attribute("region", "eu")),
          PointExpectation.histogram.count(1L).sum(20.0).attributesSubset(Attribute("region", "us"))
        )
        .check(points)
    )
  }

  private def testkitTest[A](options: TestOptions)(body: MetricsTestkit[IO] => IO[A])(implicit loc: Location): Unit =
    test(options)(MetricsTestkit.inMemory[IO]().use(body))

  private def collectLongPoints(
      testkit: MetricsTestkit[IO],
      name: String
  ): IO[List[PointData.NumberPoint.Aux[Long]]] =
    testkit.collectMetrics.map { metrics =>
      metricByName(metrics, name).data match {
        case sum: MetricPoints.Sum =>
          sum.points.toVector.toList
            .map(PointExpectation.toNumericPoint(org.typelevel.otel4s.metrics.MeasurementValue[Long], _).toOption.get)
        case other =>
          fail(s"expected sum metric, got $other")
      }
    }

  private def collectHistogramPoints(
      testkit: MetricsTestkit[IO],
      name: String
  ): IO[List[PointData.Histogram]] =
    testkit.collectMetrics.map { metrics =>
      metricByName(metrics, name).data match {
        case histogram: MetricPoints.Histogram => histogram.points.toVector.toList
        case other                             => fail(s"expected histogram metric, got $other")
      }
    }

  private def metricByName(metrics: List[MetricData], name: String): MetricData =
    metrics.find(_.name == name).getOrElse(fail(s"metric $name was not collected"))

  private def assertSuccess(result: Either[NonEmptyList[PointSetExpectation.Mismatch], Unit]): Unit =
    result match {
      case Right(_)         => ()
      case Left(mismatches) => fail(mismatches.toList.map(_.message).mkString(", "))
    }

  private def assertMismatchType[A <: PointSetExpectation.Mismatch: ClassTag](
      result: Either[NonEmptyList[PointSetExpectation.Mismatch], Unit]
  ): A =
    result match {
      case Right(_) =>
        fail("expected mismatch, got success")
      case Left(mismatches) =>
        val mismatch = mismatches.head
        if (classTag[A].runtimeClass.isInstance(mismatch)) mismatch.asInstanceOf[A]
        else fail(s"unexpected mismatch: $mismatch")
    }
}
