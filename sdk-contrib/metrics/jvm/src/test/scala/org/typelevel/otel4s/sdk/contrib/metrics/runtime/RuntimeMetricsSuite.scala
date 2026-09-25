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
import cats.effect.Resource
import munit.CatsEffectSuite
import org.typelevel.otel4s.metrics.MeterProvider
import org.typelevel.otel4s.sdk.metrics.data.MetricData
import org.typelevel.otel4s.sdk.metrics.data.PointData
import org.typelevel.otel4s.sdk.testkit.metrics.MetricsTestkit
import org.typelevel.otel4s.semconv.MetricSpec
import org.typelevel.otel4s.semconv.Requirement
import org.typelevel.otel4s.semconv.metrics.JvmMetrics

import java.lang.management.ManagementFactory
import java.net.URLClassLoader
import scala.concurrent.duration._
import scala.jdk.CollectionConverters._

class RuntimeMetricsSuite extends CatsEffectSuite {

  test("specification check") {
    val specs = List(
      // class
      JvmMetrics.ClassCount,
      JvmMetrics.ClassLoaded,
      JvmMetrics.ClassUnloaded,
      // cpu
      JvmMetrics.CpuTime,
      JvmMetrics.CpuCount,
      JvmMetrics.CpuRecentUtilization,
      // gc,
      JvmMetrics.GcDuration,
      // memory
      JvmMetrics.MemoryUsed,
      JvmMetrics.MemoryCommitted,
      JvmMetrics.MemoryLimit,
      JvmMetrics.MemoryUsedAfterLastGc,
      // thread
      JvmMetrics.ThreadCount
    )

    assertEquals(specs.sortBy(_.name), JvmMetrics.specs.sortBy(_.name))

    MetricsTestkit.inMemory[IO]().use { testkit =>
      implicit val meterProvider: MeterProvider[IO] = testkit.meterProvider
      RuntimeMetrics.register[IO].surround {
        for {
          _ <- IO.delay(System.gc())
          _ <- IO.sleep(500.millis)
          metrics <- testkit.collectMetrics
        } yield specs.foreach(spec => specTest(metrics, spec))
      }
    }
  }

  test("record GC durations in seconds") {
    val config = RuntimeMetrics.Config.disabledAll.withGcMetricsEnabled

    // the accumulated collection time reported by the JVM, in milliseconds
    val collectionTime =
      IO.delay(ManagementFactory.getGarbageCollectorMXBeans.asScala.map(_.getCollectionTime).sum)

    MetricsTestkit.inMemory[IO]().use { testkit =>
      implicit val meterProvider: MeterProvider[IO] = testkit.meterProvider
      RuntimeMetrics.register[IO](config).surround {
        for {
          before <- collectionTime
          _ <- IO.delay(System.gc())
          _ <- IO.sleep(500.millis)
          after <- collectionTime
          metrics <- testkit.collectMetrics
        } yield {
          val expected = (after - before).toDouble / 1000
          val recorded = histogramSum(metrics, JvmMetrics.GcDuration.name)

          assume(expected > 0, "System.gc() did not add any collection time")
          assert(recorded >= expected / 2, s"recorded [$recorded s], but the JVM reported [$expected s]")
        }
      }
    }
  }

  test("record currently loaded classes and classes loaded since JVM start") {
    val config = RuntimeMetrics.Config.disabledAll.withClassMetricsEnabled

    MetricsTestkit.inMemory[IO]().use { testkit =>
      implicit val meterProvider: MeterProvider[IO] = testkit.meterProvider
      RuntimeMetrics.register[IO](config).surround {
        for {
          _ <- loadUnloadableClass
          _ <- IO.delay(System.gc())
          metrics <- testkit.collectMetrics
        } yield {
          val count = longValue(metrics, JvmMetrics.ClassCount.name)
          val loaded = longValue(metrics, JvmMetrics.ClassLoaded.name)
          val unloaded = longValue(metrics, JvmMetrics.ClassUnloaded.name)

          assume(unloaded > 0, "no classes were unloaded")
          assert(loaded > count, s"loaded since JVM start [$loaded] must exceed currently loaded [$count]")
        }
      }
    }
  }

  // loads the class with a separate class loader, so the next collection can unload it
  private val loadUnloadableClass: IO[Unit] = {
    val unloadable = classOf[RuntimeMetricsSuite.Unloadable]
    val location = unloadable.getProtectionDomain.getCodeSource.getLocation

    Resource
      .fromAutoCloseable(IO.delay(new URLClassLoader(Array(location), null)))
      .use(loader => IO.delay(loader.loadClass(unloadable.getName)))
      .void
  }

  private def longValue(metrics: List[MetricData], name: String): Long =
    metrics
      .filter(_.name == name)
      .flatMap(_.data.points.toVector)
      .collect { case point: PointData.LongNumber => point.value }
      .sum

  private def histogramSum(metrics: List[MetricData], name: String): Double =
    metrics
      .filter(_.name == name)
      .flatMap(_.data.points.toVector)
      .collect { case point: PointData.Histogram => point.stats.fold(0.0)(_.sum) }
      .sum

  private def specTest(metrics: List[MetricData], spec: MetricSpec): Unit = {
    val metric = metrics.find(_.name == spec.name)
    assert(
      metric.isDefined,
      s"${spec.name} metric is missing. Available [${metrics.map(_.name).mkString(", ")}]",
    )

    val clue = s"[${spec.name}] has a mismatched property"

    metric.foreach { md =>
      assertEquals(md.name, spec.name, clue)
      assertEquals(md.description, Some(spec.description), clue)
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

object RuntimeMetricsSuite {

  final class Unloadable

}
