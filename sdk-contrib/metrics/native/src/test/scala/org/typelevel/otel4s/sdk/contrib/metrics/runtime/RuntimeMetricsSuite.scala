/*
 * Copyright 2026 Typelevel
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

package org.typelevel.otel4s.sdk.contrib.metrics.runtime

import cats.effect.IO
import munit.CatsEffectSuite
import org.typelevel.otel4s.metrics.MeterProvider
import org.typelevel.otel4s.sdk.metrics.data.MetricData
import org.typelevel.otel4s.sdk.metrics.data.PointData
import org.typelevel.otel4s.sdk.testkit.metrics.MetricsTestkit
import org.typelevel.otel4s.semconv.MetricSpec
import org.typelevel.otel4s.semconv.Requirement
import org.typelevel.otel4s.semconv.metrics.JvmMetrics

import java.lang.management.ManagementFactory
import scala.concurrent.duration._
import scala.jdk.CollectionConverters._

class RuntimeMetricsSuite extends CatsEffectSuite {

  test("specification check") {
    val specs = List(
      // cpu
      JvmMetrics.CpuTime,
      JvmMetrics.CpuCount,
      // gc
      JvmMetrics.GcDuration,
      // memory
      JvmMetrics.MemoryUsed,
      JvmMetrics.MemoryCommitted,
      JvmMetrics.MemoryLimit,
      // thread
      JvmMetrics.ThreadCount
    )

    val config = RuntimeMetrics.Config.enabledAll.withGcMetricsRefreshRate(100.millis)

    MetricsTestkit.inMemory[IO]().use { testkit =>
      implicit val meterProvider: MeterProvider[IO] = testkit.meterProvider
      RuntimeMetrics.register[IO](config).surround {
        for {
          _ <- IO.delay(System.gc())
          _ <- IO.sleep(500.millis)
          metrics <- testkit.collectMetrics
        } yield specs.foreach(spec => specTest(metrics, spec))
      }
    }
  }

  test("record GC collections since registration") {
    val config = RuntimeMetrics.Config.disabledAll.withGcMetricsEnabled.withGcMetricsRefreshRate(100.millis)

    // the accumulated number of collections reported by the process
    val collections =
      IO.delay(ManagementFactory.getGarbageCollectorMXBeans.asScala.map(_.getCollectionCount).sum)

    MetricsTestkit.inMemory[IO]().use { testkit =>
      implicit val meterProvider: MeterProvider[IO] = testkit.meterProvider
      for {
        before <- collections
        metrics <- RuntimeMetrics.register[IO](config).surround {
          IO.delay(System.gc()).replicateA_(3) >> IO.sleep(500.millis) >> testkit.collectMetrics
        }
        after <- collections
      } yield {
        val recorded = histogramCount(metrics, "scalanative.gc.duration")

        assert(recorded >= 3L, s"recorded [$recorded] collections, but System.gc() was called 3 times")
        assert(recorded <= after - before, s"recorded [$recorded] collections, but only [${after - before}] happened")
      }
    }
  }

  private def histogramCount(metrics: List[MetricData], name: String): Long =
    metrics
      .filter(_.name == name)
      .flatMap(_.data.points.toVector)
      .collect { case point: PointData.Histogram => point.stats.fold(0L)(_.count) }
      .sum

  private def specTest(metrics: List[MetricData], spec: MetricSpec): Unit = {
    val specName = spec.name.replace("jvm.", "scalanative.")
    val metric = metrics.find(_.name == specName)
    assert(
      metric.isDefined,
      s"$specName metric is missing. Available [${metrics.map(_.name).mkString(", ")}]",
    )

    val clue = s"[$specName] has a mismatched property"

    // since we are trying to follow the JVM semantics, we need to adjust the description a bit
    val description = spec.description
      .replace("JVM", "process")
      .replace("Java virtual machine", "process")

    metric.foreach { md =>
      assertEquals(md.name, specName, clue)
      assertEquals(md.description, Some(description), clue)
      assertEquals(md.unit, Some(spec.unit), clue)

      val required = spec.attributeSpecs
        .filter(_.requirement.level == Requirement.Level.Required)
        .map(_.key)
        .toSet

      val current = md.data.points.toVector
        .flatMap(_.attributes.map(_.key))
        .filter(key => required.contains(key))
        .toSet

      assertEquals(current, required, clue)
    }
  }

}
