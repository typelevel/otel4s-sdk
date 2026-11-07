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

package org.typelevel.otel4s.sdk.testkit

import cats.effect.IO
import munit.CatsEffectSuite
import org.typelevel.otel4s.sdk.common.Diagnostic
import org.typelevel.otel4s.sdk.context.Context
import org.typelevel.otel4s.sdk.metrics.data.PointData
import org.typelevel.otel4s.sdk.testkit.logs.LogsTestkit
import org.typelevel.otel4s.sdk.testkit.metrics.MetricsTestkit
import org.typelevel.otel4s.sdk.testkit.trace.TracesTestkit

class ContextManagerSuite extends CatsEffectSuite {

  private implicit val diagnostic: Diagnostic[IO] = Diagnostic.noop

  test("captured context restores a finished parent span and custom values for deferred work") {
    OpenTelemetrySdkTestkit.inMemory[IO]().use { sdk =>
      val metrics: MetricsTestkit[IO] = sdk
      val traces: TracesTestkit[IO] = sdk
      val logs: LogsTestkit[IO] = sdk

      for {
        key <- Context.Key.unique[IO, String]("custom")
        tracer <- sdk.tracerProvider.get("test")
        logger <- sdk.loggerProvider.get("test")
        meter <- sdk.meterProvider.get("test")
        counter <- meter.counter[Long]("counter").create
        captured <- sdk.contextManager.scope(Context.root.updated(key, "value")) {
          tracer.span("parent").surround(sdk.contextManager.captureScope)
        }
        _ <- captured {
          for {
            current <- metrics.contextManager.current
            _ = assertEquals(current.get(key), Some("value"))
            logContext <- logger.currentContext
            _ = assertEquals(logContext, current)
            traceContext <- traces.contextManager.current
            _ = assertEquals(traceContext, current)
            logsContext <- logs.contextManager.current
            _ = assertEquals(logsContext, current)
            _ <- tracer.span("child").surround(logger.logRecordBuilder.emit *> counter.add(1L))
          } yield ()
        }
        current <- sdk.contextManager.current
        _ = assertEquals(current.get(key), None)
        spans <- sdk.finishedSpans
        parent = spans.find(_.name == "parent").get
        child = spans.find(_.name == "child").get
        _ = assertEquals(child.parentSpanContext, Some(parent.spanContext))
        metricsData <- sdk.collectMetrics
        exemplars = metricsData
          .flatMap(_.data.points.toVector)
          .collect { case point: PointData.NumberPoint =>
            point.exemplars
          }
          .flatten
        _ = assertEquals(exemplars.flatMap(_.traceContext.map(_.spanId)), List(child.spanContext.spanId))
        records <- sdk.collectLogs
        _ = assertEquals(records.flatMap(_.traceContext.map(_.spanId)), List(child.spanContext.spanId))
      } yield ()
    }
  }

  test("standalone logs manager scopes the context read by its logger") {
    LogsTestkit.inMemory[IO]().use { logs =>
      for {
        key <- Context.Key.unique[IO, String]("custom")
        logger <- logs.loggerProvider.get("test")
        value <- logs.contextManager.scope(Context.root.updated(key, "value"))(logger.currentContext)
        _ = assertEquals(value.get(key), Some("value"))
        restored <- logger.currentContext
        _ = assertEquals(restored.get(key), None)
      } yield ()
    }
  }

  test("standalone traces manager restores span parenting") {
    TracesTestkit.inMemory[IO]().use { traces =>
      for {
        tracer <- traces.tracerProvider.get("test")
        captured <- tracer.span("parent").surround(traces.contextManager.captureScope)
        _ <- captured(tracer.span("child").surround(IO.unit))
        spans <- traces.finishedSpans
        parent = spans.find(_.name == "parent").get
        child = spans.find(_.name == "child").get
        _ = assertEquals(child.parentSpanContext, Some(parent.spanContext))
      } yield ()
    }
  }

  test("standalone metrics manager restores after failure") {
    MetricsTestkit.inMemory[IO]().use { metrics =>
      for {
        key <- Context.Key.unique[IO, String]("custom")
        result <- metrics.contextManager
          .scope(Context.root.updated(key, "value")) {
            metrics.contextManager.current.flatMap { context =>
              IO(assertEquals(context.get(key), Some("value"))) *> IO.raiseError[Unit](new RuntimeException("boom"))
            }
          }
          .attempt
        _ = assert(result.isLeft)
        restored <- metrics.contextManager.current
        _ = assertEquals(restored.get(key), None)
      } yield ()
    }
  }
}
