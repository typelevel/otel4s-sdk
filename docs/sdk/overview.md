# Overview

SDK modules are an alternative to the [otel4s-oteljava][oteljava] implementation.
All modules are implemented in Scala and available for all platforms: JVM, Scala Native, and Scala.js.
The implementation remains **experimental** and some functionality may be lacking.

## Getting Started

@:select(build-tool)

@:choice(sbt)

Add settings to the `build.sbt`:

```scala
libraryDependencies ++= Seq(
  "org.typelevel" %%% "otel4s-sdk" % "@VERSION@", // <1>
  "org.typelevel" %%% "otel4s-sdk-exporter" % "@VERSION@" // <2>
)
```

@:choice(scala-cli)

Add directives to the `*.scala` file:

```scala
//> using dep "org.typelevel::otel4s-sdk::@VERSION@" // <1>
//> using dep "org.typelevel::otel4s-sdk-exporter::@VERSION@" // <2>
```

@:@

1. Add the `otel4s-sdk` library
2. Add the `otel4s-sdk-exporter` library. Without the exporter, the application will crash

_______

Then use `OpenTelemetrySdk.autoConfigured` to autoconfigure the SDK:
```scala mdoc:silent:reset
import cats.effect.{IO, IOApp}
import org.typelevel.otel4s.sdk.OpenTelemetrySdk
import org.typelevel.otel4s.sdk.exporter.otlp.autoconfigure.OtlpExportersAutoConfigure
import org.typelevel.otel4s.metrics.MeterProvider
import org.typelevel.otel4s.trace.TracerProvider

object TelemetryApp extends IOApp.Simple {

  def run: IO[Unit] =
    OpenTelemetrySdk
      .autoConfigured[IO]( // register OTLP exporters configurer
        _.addExportersConfigurer(OtlpExportersAutoConfigure[IO]) 
      )
      .use { autoConfigured =>
        val sdk = autoConfigured.sdk
        program(sdk.meterProvider, sdk.tracerProvider)
      }

  def program(
      meterProvider: MeterProvider[IO], 
      tracerProvider: TracerProvider[IO]
  ): IO[Unit] =
    ???
}
```

## Capturing and restoring context

`OpenTelemetrySdk.contextManager` captures and restores the complete SDK context,
including the active span, baggage, and custom context values. Use it when deferring
work until after the scope that created it has ended:

```scala mdoc:silent
import cats.effect.IO
import org.typelevel.otel4s.sdk.OpenTelemetrySdk

def deferredWork(sdk: OpenTelemetrySdk[IO]): IO[Unit] =
  for {
    tracer <- sdk.tracerProvider.get("example")
    restore <- tracer.span("request").surround(sdk.contextManager.captureScope)
    _ <- restore(tracer.span("deferred").surround(IO.unit))
  } yield ()
```

The `deferred` span retains `request` as its parent even though `request` has ended.
`current` returns the complete current context; `scope(context)` runs an effect with
that context. Both `scope` and the transformation returned by `captureScope` restore
the previous context on success, failure, or cancellation.

The manager is exposed as `ContextManager.Aux[F, Context]` on these entry points:

| Entry point | Context storage |
| --- | --- |
| `OpenTelemetrySdk` | Shared by traces, metrics, logs, and baggage |
| `SdkTraces` | The tracing module's local context |
| `OpenTelemetrySdkTestkit` | Shared by all signals and baggage |
| `TracesTestkit`, `MetricsTestkit`, `LogsTestkit` | Each standalone testkit's local context |

Each manager uses the context storage already allocated for its entry point.
The combined testkit exposes the same manager when used through any of its individual
signal testkit traits. Separate standalone testkits allocate independent storage by
default; their managers do not establish shared storage or trace-context lookup.

Standalone `SdkMetrics` and `SdkLogs` use a constant root context and do not expose a
manager. Use `OpenTelemetrySdk` for shared context across signals. Exposing a mutable
manager on these standalone modules would require changing their context storage and
construction requirements. No additional capability trait is needed: the existing
entry-point traits expose the manager directly.

## Configuration

The `.autoConfigured(...)` relies on the environment variables and system properties to configure the SDK.
For example, use `export OTEL_SERVICE_NAME=auth-service` to configure the name of the service.

See the full set of the [supported options](configuration.md).

## Limitations

### No autoload of third-party components

Since Scala Native and Scala.js lack [SPI](https://docs.oracle.com/javase/tutorial/sound/SPI-intro.html) support, 
third-party components cannot be loaded dynamically as OpenTelemetry Java does.

Hence, the configurers must be registered manually:
```scala mdoc:silent
OpenTelemetrySdk.autoConfigured[IO](
  _.addExportersConfigurer(OtlpExportersAutoConfigure[IO])
)
```

[oteljava]: https://typelevel.org/otel4s/oteljava/overview.html
