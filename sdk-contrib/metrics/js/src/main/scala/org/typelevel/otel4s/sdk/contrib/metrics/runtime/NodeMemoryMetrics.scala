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
import cats.syntax.flatMap._
import cats.syntax.foldable._
import cats.syntax.functor._
import org.typelevel.otel4s.AttributeKey
import org.typelevel.otel4s.Attributes
import org.typelevel.otel4s.metrics.Meter

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/** @see
  *   [[https://opentelemetry.io/docs/specs/semconv/runtime/v8js-metrics/#metric-v8jsmemoryheaplimit]]
  */
private object NodeMemoryMetrics {

  def register[F[_]: Async: Meter]: Resource[F, Unit] =
    Meter[F].batchCallback.of(
      Meter[F]
        .observableGauge[Double](MetricNames.V8jsMemoryHeapLimit)
        .withDescription("Total heap memory size pre-allocated for a heap space.")
        .withUnit("By")
        .createObserver,
      Meter[F]
        .observableGauge[Double](MetricNames.V8jsMemoryHeapUsed)
        .withDescription("Heap Memory size allocated.")
        .withUnit("By")
        .createObserver,
      Meter[F]
        .observableGauge[Double](MetricNames.V8jsHeapSpaceAvailableSize)
        .withDescription("Heap space available size.")
        .withUnit("By")
        .createObserver,
      Meter[F]
        .observableGauge[Double](MetricNames.V8jsHeapSpacePhysicalSize)
        .withDescription("Committed size of a heap space.")
        .withUnit("By")
        .createObserver
    ) { (heapLimit, heapUsed, heapAvailable, heapPhysical) =>
      for {
        spaces <- readHeapSpaces[F]
        _ <- spaces.traverse_ { space =>
          val attributes = heapSpaceAttributes(space.space_name)
          for {
            _ <- heapLimit.record(space.space_size, attributes)
            _ <- heapUsed.record(space.space_used_size, attributes)
            _ <- heapAvailable.record(space.space_available_size, attributes)
            _ <- heapPhysical.record(space.physical_space_size, attributes)
          } yield ()
        }
      } yield ()
    }

  private def readHeapSpaces[F[_]: Async]: F[Vector[HeapSpaceInfo]] =
    Async[F].delay(V8.getHeapSpaceStatistics().toVector)

  private def heapSpaceAttributes(spaceName: String): Attributes =
    Attributes(Keys.V8jsHeapSpaceName(spaceName))

  private object MetricNames {
    val V8jsMemoryHeapLimit = "v8js.memory.heap.space.size"
    val V8jsMemoryHeapUsed = "v8js.memory.heap.used"
    val V8jsHeapSpaceAvailableSize = "v8js.memory.heap.space.available_size"
    val V8jsHeapSpacePhysicalSize = "v8js.memory.heap.space.physical_size"
  }

  private object Keys {
    val V8jsHeapSpaceName: AttributeKey[String] =
      AttributeKey("v8js.heap.space.name")
  }

  @js.native
  @JSImport("node:v8", JSImport.Namespace)
  private object V8 extends js.Object {
    def getHeapSpaceStatistics(): js.Array[HeapSpaceInfo] = js.native
  }

  @js.native
  private trait HeapSpaceInfo extends js.Object {
    def space_name: String = js.native
    def space_size: Double = js.native
    def space_used_size: Double = js.native
    def space_available_size: Double = js.native
    def physical_space_size: Double = js.native
  }

}
