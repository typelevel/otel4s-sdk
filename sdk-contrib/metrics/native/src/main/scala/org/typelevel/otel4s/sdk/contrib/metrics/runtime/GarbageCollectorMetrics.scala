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

import cats.effect.Async
import cats.effect.Resource
import cats.effect.syntax.all._
import cats.syntax.all._
import org.typelevel.otel4s.Attributes
import org.typelevel.otel4s.metrics.BucketBoundaries
import org.typelevel.otel4s.metrics.Histogram
import org.typelevel.otel4s.metrics.Meter
import org.typelevel.otel4s.semconv.attributes.JvmAttributes

import java.lang.management.GarbageCollectorMXBean
import java.lang.management.ManagementFactory
import java.util.concurrent.TimeUnit
import scala.concurrent.duration._
import scala.jdk.CollectionConverters._

/** @see
  *   [[https://opentelemetry.io/docs/specs/semconv/runtime/jvm-metrics/#jvm-garbage-collection]]
  */
private object GarbageCollectorMetrics {
  private val MillisPerSecond = TimeUnit.SECONDS.toMillis(1)

  def register[F[_]: Async: Meter](
      refreshRate: FiniteDuration,
      bucketBoundaries: BucketBoundaries
  ): Resource[F, Unit] =
    for {
      beans <- Async[F].delay(ManagementFactory.getGarbageCollectorMXBeans).toResource

      histogram <- Meter[F]
        .histogram[Double]("scalanative.gc.duration")
        .withDescription("Duration of process garbage collection actions.")
        .withUnit("s")
        .withExplicitBucketBoundaries(bucketBoundaries)
        .create
        .toResource

      _ <- beans.asScala.toList.traverse_(bean => record(bean, histogram, refreshRate))
    } yield ()

  // The bean only exposes accumulated totals, so every collection since the previous
  // refresh is recorded with the average duration of the collections in that period.
  private def record[F[_]: Async](
      bean: GarbageCollectorMXBean,
      histogram: Histogram[F, Double],
      refreshRate: FiniteDuration
  ): Resource[F, Unit] = {
    val attributes = Attributes(JvmAttributes.JvmGcName(bean.getName))
    val totals = Async[F].delay((bean.getCollectionCount, bean.getCollectionTime))

    totals.toResource.flatMap { initial =>
      initial
        .iterateForeverM[F, Unit] { case (previousCount, previousTime) =>
          totals.delayBy(refreshRate).flatTap { case (count, time) =>
            val collections = count - previousCount
            Async[F].whenA(collections > 0) {
              val duration = (time - previousTime).toDouble / collections / MillisPerSecond
              histogram.record(duration, attributes).replicateA_(collections.toInt)
            }
          }
        }
        .background
        .void
    }
  }

}
