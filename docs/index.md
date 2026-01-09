# otel4s-sdk

[SDK backend](sdk/overview.md) is an alternative to the [otel4s-oteljava][oteljava].
The backend is implemented in Scala from scratch. Available for JVM, Scala.js, and Scala Native.
While the implementation is compliant with the OpenTelemetry specification,
it remains **experimental** and some functionality may be lacking and the memory overhead may be noticeable.

## Getting started

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

## Configuration

The `.autoConfigured(...)` relies on the environment variables and system properties to configure the SDK.
For example, use `export OTEL_SERVICE_NAME=auth-service` to configure the name of the service.

See the full set of the [supported options](sdk/configuration.md).

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

### Metrics missing features

- `Exponential Histogram` aggregation is not supported yet

@:callout(warning)

`OtelJava.autoConfigured` creates an **isolated** **non-global** instance.
If you create multiple instances, those instances won't interoperate (i.e. be able to see each others spans).

@:@


[cats-effect]: https://typelevel.org/cats-effect/
[oteljava]: https://typelevel.org/otel4s/oteljava/overview.html
