# otel4s-sdk Compliance Matrix

Generated from `otel4s-sdk.yaml`.

## Traces

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| **[TracerProvider](specification/trace/api.md#tracerprovider-operations)** / Create TracerProvider | `+` |  | `../otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/TracerProvider.scala#L40-L59` |
| **[TracerProvider](specification/trace/api.md#tracerprovider-operations)** / Get a Tracer | `+` |  | `../otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/TracerProvider.scala#L40-L59` |
| **[TracerProvider](specification/trace/api.md#tracerprovider-operations)** / Get a Tracer with schema_url | `+` |  | `../otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/TracerBuilder.scala#L31-L42` |
| **[TracerProvider](specification/trace/api.md#tracerprovider-operations)** / Get a Tracer with scope attributes | `-` |  |  |
| **[TracerProvider](specification/trace/api.md#tracerprovider-operations)** / Associate Tracer with InstrumentationScope | `+` |  | `../otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/TracerProvider.scala#L40-L59` |
| **[TracerProvider](specification/trace/api.md#tracerprovider-operations)** / Safe for concurrent calls | `-` |  |  |
| **[TracerProvider](specification/trace/api.md#tracerprovider-operations)** / Shutdown (SDK only required) | `-` |  |  |
| **[TracerProvider](specification/trace/api.md#tracerprovider-operations)** / ForceFlush (SDK only required) | `-` |  |  |
| **[Trace / Context interaction](specification/trace/api.md#context-interaction)** / Get active Span | `+` |  | `../otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala#L52-L100` |
| **[Trace / Context interaction](specification/trace/api.md#context-interaction)** / Set active Span | `+` |  | `../otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala#L52-L100` |
| **[Tracer](specification/trace/api.md#tracer-operations)** / Create a new Span | `+` |  | `../otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala#L52-L100` |
| **[Tracer](specification/trace/api.md#tracer-operations)** / Documentation defines adding attributes at span creation as preferred | `-` |  |  |
| **[Tracer](specification/trace/api.md#tracer-operations)** / Get active Span | `+` |  | `../otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala#L52-L100` |
| **[Tracer](specification/trace/api.md#tracer-operations)** / Mark Span active | `+` |  | `../otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala#L52-L100` |
| **[Tracer](specification/trace/api.md#tracer-operations)** / Safe for concurrent calls | `-` |  |  |
| **[SpanContext](specification/trace/api.md#spancontext)** / IsValid | `+` |  | `../otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/SpanContext.scala#L64-L70` |
| **[SpanContext](specification/trace/api.md#spancontext)** / IsRemote | `+` |  | `../otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/SpanContext.scala#L64-L70` |
| **[SpanContext](specification/trace/api.md#spancontext)** / Conforms to the W3C TraceContext spec | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/W3CTraceContextPropagator.scala#L41-L74` |
| **[SpanContext](specification/trace/api.md#spancontext)** / [Support W3C Trace Context Level 2 randomness](specification/trace/sdk.md#traceid-randomness) | `-` |  |  |
| **[Span](specification/trace/api.md#span)** / Create root span | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198` |
| **[Span](specification/trace/api.md#span)** / Create with default parent (active span) | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198` |
| **[Span](specification/trace/api.md#span)** / Create with parent from Context | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198` |
| **[Span](specification/trace/api.md#span)** / No explicit parent Span/SpanContext allowed | `-` |  |  |
| **[Span](specification/trace/api.md#span)** / SpanProcessor.OnStart receives parent Context | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span](specification/trace/api.md#span)** / UpdateName | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span](specification/trace/api.md#span)** / User-defined start timestamp | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span](specification/trace/api.md#span)** / End | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span](specification/trace/api.md#span)** / End with timestamp | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span](specification/trace/api.md#span)** / IsRecording | `-` |  |  |
| **[Span](specification/trace/api.md#span)** / IsRecording becomes false after End | `-` |  |  |
| **[Span](specification/trace/api.md#span)** / Set status with StatusCode (Unset, Ok, Error) | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span](specification/trace/api.md#span)** / Safe for concurrent calls | `-` |  |  |
| **[Span](specification/trace/api.md#span)** / events collection size limit | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span](specification/trace/api.md#span)** / attribute collection size limit | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span](specification/trace/api.md#span)** / links collection size limit | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span](specification/trace/api.md#span)** / [SpanProcessor.OnEnding](specification/trace/sdk.md#onending) | `-` | `true` |  |
| **[Span attributes](specification/trace/api.md#set-attributes)** / SetAttribute | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span attributes](specification/trace/api.md#set-attributes)** / Set order preserved | `-` | `true` |  |
| **[Span attributes](specification/trace/api.md#set-attributes)** / String type | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span attributes](specification/trace/api.md#set-attributes)** / Boolean type | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span attributes](specification/trace/api.md#set-attributes)** / Double floating-point type | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span attributes](specification/trace/api.md#set-attributes)** / Signed int64 type | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span attributes](specification/trace/api.md#set-attributes)** / Array of primitives (homogeneous) | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span attributes](specification/trace/api.md#set-attributes)** / `null` values documented as invalid/undefined | `-` |  |  |
| **[Span attributes](specification/trace/api.md#set-attributes)** / Unicode support for keys and string values | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span linking](specification/trace/api.md#specifying-links)** / Links can be recorded on span creation | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198`<br>`sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span linking](specification/trace/api.md#specifying-links)** / Links can be recorded after span creation | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198`<br>`sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span linking](specification/trace/api.md#specifying-links)** / Links order is preserved | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198`<br>`sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span events](specification/trace/api.md#add-events)** / AddEvent | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span events](specification/trace/api.md#add-events)** / Add order preserved | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span events](specification/trace/api.md#add-events)** / Safe for concurrent calls | `-` |  |  |
| **[Span exceptions](specification/trace/api.md#record-exception)** / RecordException | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Span exceptions](specification/trace/api.md#record-exception)** / RecordException with extra parameters | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Sampling](specification/trace/sdk.md#sampling)** / Allow samplers to modify tracestate | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/samplers/SamplingResult.scala#L82-L94` |
| **[Sampling](specification/trace/sdk.md#sampling)** / ShouldSample gets full parent Context | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198`<br>`sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/samplers/Sampler.scala#L55-L63` |
| **[Sampling](specification/trace/sdk.md#sampling)** / Sampler: JaegerRemoteSampler | `-` |  |  |
| **[Sampling](specification/trace/sdk.md#sampling)** / [New Span ID created also for non-recording Spans](specification/trace/sdk.md#sdk-span-creation) | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198` |
| **[Sampling](specification/trace/sdk.md#sampling)** / [IdGenerators](specification/trace/sdk.md#id-generators) | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198` |
| **[Sampling](specification/trace/sdk.md#sampling)** / [SpanLimits](specification/trace/sdk.md#span-limits) | `+` | `true` | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SpanLimits.scala#L175-L201` |
| **[Sampling](specification/trace/sdk.md#sampling)** / [Built-in `SpanProcessor`s implement `ForceFlush` spec](specification/trace/sdk.md#forceflush-1) | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/processor/SpanProcessor.scala#L58-L71`<br>`sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/processor/BatchSpanProcessor.scala#L96-L98` |
| **[Sampling](specification/trace/sdk.md#sampling)** / [Attribute Limits](specification/common/README.md#attribute-limits) | `+` | `true` | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SpanLimits.scala#L175-L201`<br>`sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Sampling](specification/trace/sdk.md#sampling)** / Fetch InstrumentationScope from ReadableSpan | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183` |
| **[Sampling](specification/trace/sdk.md#sampling)** / [TraceIdRatioBased sampler implements OpenTelemetry tracestate `th` field](specification/trace/sdk.md#traceidratiobased) | `-` | `true` |  |
| **[Sampling](specification/trace/sdk.md#sampling)** / [CompositeSampler and built-in ComposableSamplers](specification/trace/sdk.md#compositesampler) | `-` | `true` |  |
| **[Sampling](specification/trace/sdk.md#sampling)** / [Sampler: AlwaysRecord](specification/trace/sdk.md#alwaysrecord) | `-` |  |  |

## Baggage

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| Basic support | `+` |  | `../otel4s/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L197-L204` |
| Use official header name `baggage` | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/W3CBaggagePropagator.scala#L41-L44` |

## Metrics

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| The API provides a way to set and get a global default `MeterProvider`. | `-` | `true` |  |
| It is possible to create any number of `MeterProvider`s. | `+` | `true` | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L181-L250` |
| `MeterProvider` provides a way to get a `Meter`. | `+` |  | `../otel4s/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/MeterProvider.scala#L37-L56` |
| `get_meter` accepts name, `version` and `schema_url`. | `+` |  | `../otel4s/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/MeterProvider.scala#L37-L56`<br>`../otel4s/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/MeterBuilder.scala#L28-L39` |
| `get_meter` accepts `attributes`. | `-` |  |  |
| When an invalid `name` is specified a working `Meter` implementation is returned as a fallback. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L60-L66` |
| The fallback `Meter` `name` property keeps its original invalid value. | `-` | `true` |  |
| Associate `Meter` with `InstrumentationScope`. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L181-L250` |
| `Counter` instrument is supported. | `+` |  | `../otel4s/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267` |
| `AsynchronousCounter` instrument is supported. | `+` |  | `../otel4s/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267` |
| `Histogram` instrument is supported. | `+` |  | `../otel4s/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267` |
| `AsynchronousGauge` instrument is supported. | `+` |  | `../otel4s/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267` |
| `Gauge` instrument is supported. | `+` |  | `../otel4s/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267` |
| `UpDownCounter` instrument is supported. | `+` |  | `../otel4s/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267` |
| `AsynchronousUpDownCounter` instrument is supported. | `+` |  | `../otel4s/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267` |
| Instruments have `name` | `+` |  | `../otel4s/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267` |
| Instruments have kind. | `+` |  | `../otel4s/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267` |
| Instruments have an optional unit of measure. | `+` |  | `../otel4s/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267` |
| Instruments have an optional description. | `+` |  | `../otel4s/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267` |
| A valid instrument MUST be created and warning SHOULD be emitted when multiple instruments are registered under the same `Meter` using the same `name`. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/ViewRegistry.scala#L57-L62` |
| Duplicate instrument registration name conflicts are resolved by using the first-seen for the stream name. | `-` |  |  |
| It is possible to register two instruments with same `name` under different `Meter`s. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L181-L250` |
| Instrument names conform to the specified syntax. | `-` |  |  |
| Instrument units conform to the specified syntax. | `-` |  |  |
| Instrument descriptions conform to the specified syntax. | `-` |  |  |
| Instrument supports the advisory ExplicitBucketBoundaries parameter. | `+` |  | `../otel4s/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Histogram.scala#L75-L85`<br>`sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkHistogram.scala#L114-L125` |
| Instrument supports the advisory Attributes parameter. | `-` |  |  |
| All methods of `MeterProvider` are safe to be called concurrently. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L181-L250` |
| All methods of `Meter` are safe to be called concurrently. | `+` |  | `../otel4s/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267` |
| All methods of any instrument are safe to be called concurrently. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkCounter.scala#L43-L51` |
| `MeterProvider` allows a `Resource` to be specified. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L95-L109` |
| A specified `Resource` can be associated with all the produced metrics from any `Meter` from the `MeterProvider`. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L221-L229` |
| The supplied `name`, `version` and `schema_url` arguments passed to the `MeterProvider` are used to create an `InstrumentationLibrary` instance stored in the `Meter`. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L207-L214` |
| The supplied `name`, `version` and `schema_url` arguments passed to the `MeterProvider` are used to create an `InstrumentationScope` instance stored in the `Meter`. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L207-L214` |
| Configuration is managed solely by the `MeterProvider`. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L181-L250` |
| The `MeterProvider` provides methods to update the configuration | `-` | `true` |  |
| The updated configuration applies to all already returned `Meter`s. | `-` | `if above` |  |
| There is a way to register `View`s with a `MeterProvider`. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L137-L137` |
| The `View` instrument selection criteria is as specified. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/InstrumentSelector.scala#L37-L43` |
| The `View` instrument selection criteria supports wildcards. | `+` | `true` | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/InstrumentSelector.scala#L37-L43` |
| The `View` instrument selection criteria supports the match-all wildcard. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/InstrumentSelector.scala#L37-L43` |
| The name of the `View` can be specified. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/View.scala#L77-L179` |
| The `View` allows configuring the name, description, attributes keys and aggregation of the resulting metric stream. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/View.scala#L77-L179` |
| The `View` allows configuring excluded attribute keys of resulting metric stream. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/View.scala#L77-L179` |
| The `View` allows configuring the exemplar reservoir of resulting metric stream. | `-` | `true` |  |
| The SDK allows more than one `View` to be specified per instrument. | `+` | `true` | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/ViewRegistry.scala#L57-L62` |
| The `Drop` aggregation is available. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/Aggregation.scala#L51-L109` |
| The `Default` aggregation is available. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/Aggregation.scala#L51-L109` |
| The `Default` aggregation uses the specified aggregation by instrument. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/Aggregation.scala#L51-L109` |
| The `Sum` aggregation is available. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/Aggregation.scala#L51-L109` |
| The `LastValue` aggregation is available. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/Aggregation.scala#L51-L109` |
| The `ExplicitBucketHistogram` aggregation is available. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/Aggregation.scala#L51-L109` |
| The `ExponentialBucketHistogram` aggregation is available. | `-` |  |  |
| The metrics Reader implementation supports registering metric Exporters | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/MetricReader.scala#L36-L72`<br>`sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/PeriodicMetricReader.scala#L59-L122` |
| The metrics Reader implementation supports configuring the default aggregation on the basis of instrument kind. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/PeriodicMetricReader.scala#L59-L122` |
| The metrics Reader implementation supports configuring the default temporality on the basis of instrument kind. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/PeriodicMetricReader.scala#L59-L122` |
| The metrics Exporter has access to the aggregated metrics data (aggregated points, not raw measurements). | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/MetricExporter.scala#L72-L84` |
| The metrics Exporter `export` function can not be called concurrently from the same Exporter instance. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/PeriodicMetricReader.scala#L59-L122` |
| The metrics Exporter `export` function does not block indefinitely. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/PeriodicMetricReader.scala#L59-L122` |
| The metrics Exporter `export` function receives a batch of metrics. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/MetricExporter.scala#L72-L84` |
| The metrics Exporter `export` function returns `Success` or `Failure`. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/PeriodicMetricReader.scala#L59-L122` |
| The metrics Exporter provides a `ForceFlush` function. | `-` |  |  |
| The metrics Exporter `ForceFlush` can inform the caller whether it succeeded, failed or timed out. | `-` |  |  |
| The metrics Exporter provides a `shutdown` function. | `-` |  |  |
| The metrics Exporter `shutdown` function do not block indefinitely. | `-` |  |  |
| The metrics SDK samples `Exemplar`s from measurements. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarReservoir.scala#L45-L56` |
| Exemplar sampling can be disabled. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarReservoir.scala#L45-L56` |
| The metrics SDK supports SDK-wide exemplar filter configuration | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/autoconfigure/ExemplarFilterAutoConfigure.scala#L33-L33` |
| The metrics SDK supports `TraceBased` exemplar filter | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarFilter.scala#L54-L75` |
| The metrics SDK supports `AlwaysOn` exemplar filter | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarFilter.scala#L54-L75` |
| The metrics SDK supports `AlwaysOff` exemplar filter | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarFilter.scala#L54-L75` |
| Exemplars retain any attributes available in the measurement that are not preserved by aggregation or view configuration. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/ExemplarData.scala#L48-L57` |
| Exemplars contain the associated trace id and span id of the active span in the Context when the measurement was taken. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/ExemplarData.scala#L48-L57` |
| Exemplars contain the timestamp when the measurement was taken. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/ExemplarData.scala#L48-L57` |
| The metrics SDK provides an `ExemplarReservoir` interface or extension point. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarReservoir.scala#L45-L56` |
| An `ExemplarReservoir` has an `offer` method with access to the measurement value, attributes, `Context` and timestamp. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarReservoir.scala#L45-L56` |
| The metrics SDK provides a `SimpleFixedSizeExemplarReservoir` that is used by default for all aggregations except `ExplicitBucketHistogram`. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarReservoir.scala#L71-L99` |
| The metrics SDK provides an `AlignedHistogramBucketExemplarReservoir` that is used by default for `ExplicitBucketHistogram` aggregation. | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarReservoir.scala#L71-L99` |
| A metric Producer accepts an optional metric Filter | `-` |  |  |
| The metric Reader implementation supports registering metric Filter and passing them  its registered metric Producers | `-` |  |  |
| The metric SDK's metric Producer implementations uses the metric Filter | `-` |  |  |
| Metric SDK implements [cardinality limit](./specification/metrics/sdk.md#cardinality-limits) | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/SynchronousStorage.scala#L141-L145` |
| Metric SDK supports configuring cardinality limit at MeterReader level | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/CardinalityLimitSelector.scala#L38-L39` |
| Metric SDK supports configuring cardinality limit per metric (using Views) | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/View.scala#L77-L179` |

## Logs

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| LoggerProvider.Get Logger | `+` |  | `../otel4s/core/logs/src/main/scala/org/typelevel/otel4s/logs/LoggerProvider.scala#L44-L63` |
| LoggerProvider.Get Logger accepts attributes | `-` |  |  |
| LoggerProvider.Shutdown | `-` |  |  |
| LoggerProvider.ForceFlush | `-` |  |  |
| Logger.Emit(LogRecord) | `+` |  | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/SdkLogRecordBuilder.scala#L84-L90` |
| LogRecord.Set EventName | `+` |  | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/SdkLogRecordBuilder.scala#L72-L73` |
| Logger.Enabled | `-` | `true` |  |
| Ergonomic API | `-` | `true` |  |
| SimpleLogRecordProcessor | `+` |  | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/processor/SimpleLogRecordProcessor.scala#L46-L54` |
| BatchLogRecordProcessor | `+` |  | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/processor/BatchLogRecordProcessor.scala#L76-L85` |
| Can plug custom LogRecordProcessor | `+` |  | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/SdkLoggerProvider.scala#L151-L153` |
| LogRecordProcessor.Enabled | `-` | `true` |  |
| OTLP/gRPC exporter | `+` |  | `sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/OtlpLogRecordExporter.scala#L42-L46` |
| OTLP/HTTP exporter | `+` |  | `sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/OtlpLogRecordExporter.scala#L42-L46` |
| OTLP File exporter | `-` |  |  |
| Can plug custom LogRecordExporter | `+` |  | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/autoconfigure/LogRecordExportersAutoConfigure.scala#L39-L39` |
| Trace Context Injection | `+` |  | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/SdkLogRecordBuilder.scala#L96-L97` |

## Resource

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| Create from Attributes | `+` |  | `sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/TelemetryResource.scala#L120-L134` |
| Create empty | `+` |  | `sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/TelemetryResource.scala#L141-L145` |
| [Merge (v2)](specification/resource/sdk.md#merge) | `+` |  | `sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/TelemetryResource.scala#L158-L170` |
| Retrieve attributes | `+` |  | `sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/TelemetryResource.scala#L38-L41` |
| [Default value](https://github.com/open-telemetry/semantic-conventions/blob/main/docs/resource/README.md#semantic-attributes-with-dedicated-environment-variable) for service.name | `+` |  | `sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/TelemetryResource.scala#L103-L107` |
| [Resource detector](specification/resource/sdk.md#detecting-resource-information-from-the-environment) interface/mechanism | `+` |  | `sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/autoconfigure/TelemetryResourceAutoConfigure.scala#L142-L146` |
| [Resource detectors populate Schema URL](specification/resource/sdk.md#detecting-resource-information-from-the-environment) | `-` |  |  |

## Context Propagation

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| Create Context Key | `+` |  | `sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/context/Context.scala#L95-L105` |
| Get value from Context | `+` |  | `sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/context/Context.scala#L34-L40` |
| Set value for Context | `+` |  | `sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/context/Context.scala#L44-L45` |
| Attach Context | `+` |  | `../otel4s/core/common/src/main/scala/org/typelevel/otel4s/context/LocalProvider.scala#L140-L142` |
| Detach Context | `+` |  | `../otel4s/core/common/src/main/scala/org/typelevel/otel4s/context/LocalProvider.scala#L140-L142` |
| Get current Context | `+` |  | `../otel4s/core/common/src/main/scala/org/typelevel/otel4s/context/LocalProvider.scala#L138-L139` |
| Composite Propagator | `+` |  | `../otel4s/core/common/src/main/scala/org/typelevel/otel4s/context/propagation/TextMapPropagator.scala#L93-L99` |
| Global Propagator | `-` |  |  |
| TraceContext Propagator | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/W3CTraceContextPropagator.scala#L36-L43` |
| B3 Propagator | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/B3Propagator.scala#L48-L66` |
| Jaeger Propagator | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/JaegerPropagator.scala#L47-L53` |
| OT Propagator | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/OtTracePropagator.scala#L37-L48` |
| OpenCensus Binary Propagator | `-` |  |  |
| [TextMapPropagator](specification/context/api-propagators.md#textmap-propagator) | `+` |  | `../otel4s/core/common/src/main/scala/org/typelevel/otel4s/context/propagation/TextMapPropagator.scala#L35-L38` |
| Fields | `+` |  | `../otel4s/core/common/src/main/scala/org/typelevel/otel4s/context/propagation/TextMapPropagator.scala#L37-L38` |
| Setter argument | `+` | `true` | `../otel4s/core/common/src/main/scala/org/typelevel/otel4s/context/propagation/TextMapPropagator.scala#L73-L73` |
| Getter argument | `+` | `true` | `../otel4s/core/common/src/main/scala/org/typelevel/otel4s/context/propagation/TextMapPropagator.scala#L54-L54` |
| Getter argument returning Keys | `+` | `true` | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/JaegerPropagator.scala#L146-L147` |

## Environment Variables

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| OTEL_SDK_DISABLED | `+` |  | `sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/autoconfigure/CommonConfigKeys.scala#L20-L20`<br>`sdk/all/src/main/scala/org/typelevel/otel4s/sdk/OpenTelemetrySdk.scala#L498-L505` |
| OTEL_RESOURCE_ATTRIBUTES | `+` |  | `sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/autoconfigure/TelemetryResourceAutoConfigure.scala#L110-L115` |
| OTEL_SERVICE_NAME | `+` |  | `sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/autoconfigure/TelemetryResourceAutoConfigure.scala#L117-L124` |
| OTEL_LOG_LEVEL | `-` |  |  |
| OTEL_PROPAGATORS | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/ContextPropagatorsAutoConfigure.scala#L119-L121` |
| OTEL_BSP_* | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/BatchSpanProcessorAutoConfigure.scala#L39-L42` |
| OTEL_BLRP_* | `+` |  | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/autoconfigure/BatchLogRecordProcessorAutoConfigure.scala#L39-L42` |
| OTEL_EXPORTER_OTLP_* | `+` |  | `sdk-exporter/common/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/autoconfigure/OtlpClientAutoConfigure.scala#L47-L53` |
| OTEL_EXPORTER_ZIPKIN_* | `-` |  |  |
| OTEL_TRACES_EXPORTER | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SpanExportersAutoConfigure.scala#L39-L39` |
| OTEL_METRICS_EXPORTER | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/autoconfigure/MetricExportersAutoConfigure.scala#L39-L39` |
| OTEL_LOGS_EXPORTER | `+` |  | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/autoconfigure/LogRecordExportersAutoConfigure.scala#L39-L39` |
| OTEL_SPAN_ATTRIBUTE_COUNT_LIMIT | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SpanLimitsAutoConfigure.scala#L31-L31` |
| OTEL_SPAN_ATTRIBUTE_VALUE_LENGTH_LIMIT | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SpanLimitsAutoConfigure.scala#L36-L36` |
| OTEL_SPAN_EVENT_COUNT_LIMIT | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SpanLimitsAutoConfigure.scala#L32-L32` |
| OTEL_SPAN_LINK_COUNT_LIMIT | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SpanLimitsAutoConfigure.scala#L33-L33` |
| OTEL_EVENT_ATTRIBUTE_COUNT_LIMIT | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SpanLimitsAutoConfigure.scala#L34-L34` |
| OTEL_LINK_ATTRIBUTE_COUNT_LIMIT | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SpanLimitsAutoConfigure.scala#L35-L35` |
| OTEL_LOGRECORD_ATTRIBUTE_COUNT_LIMIT | `-` |  |  |
| OTEL_LOGRECORD_ATTRIBUTE_VALUE_LENGTH_LIMIT | `-` |  |  |
| OTEL_TRACES_SAMPLER | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SamplerAutoConfigure.scala#L33-L34` |
| OTEL_TRACES_SAMPLER_ARG | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SamplerAutoConfigure.scala#L33-L34` |
| OTEL_ATTRIBUTE_VALUE_LENGTH_LIMIT | `+` |  | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/autoconfigure/LogRecordLimitsAutoConfigure.scala#L33-L34` |
| OTEL_ATTRIBUTE_COUNT_LIMIT | `+` |  | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/autoconfigure/LogRecordLimitsAutoConfigure.scala#L33-L34` |
| OTEL_METRIC_EXPORT_INTERVAL | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/autoconfigure/MetricReadersAutoConfigure.scala#L36-L37` |
| OTEL_METRIC_EXPORT_TIMEOUT | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/autoconfigure/MetricReadersAutoConfigure.scala#L36-L37` |
| OTEL_METRICS_EXEMPLAR_FILTER | `+` |  | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/autoconfigure/ExemplarFilterAutoConfigure.scala#L33-L33` |
| OTEL_EXPORTER_OTLP_METRICS_TEMPORALITY_PREFERENCE | `-` |  |  |
| OTEL_EXPORTER_OTLP_METRICS_DEFAULT_HISTOGRAM_AGGREGATION | `-` |  |  |
| OTEL_EXPERIMENTAL_CONFIG_FILE | `-` |  |  |

## Declarative configuration

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| `Parse` a configuration file | `-` |  |  |
| The `Parse` operation accepts the configuration YAML file format | `-` |  |  |
| The `Parse` operation performs environment variable substitution | `-` |  |  |
| The `Parse` operation returns configuration model | `-` |  |  |
| The `Parse` operation resolves extension component configuration to `properties` | `-` |  |  |
| `Create` SDK components | `-` |  |  |
| The `Create` operation accepts configuration model | `-` |  |  |
| The `Create` operation returns `TracerProvider` | `-` |  |  |
| The `Create` operation returns `MeterProvider` | `-` |  |  |
| The `Create` operation returns `LoggerProvider` | `-` |  |  |
| The `Create` operation returns `Propagators` | `-` |  |  |
| The `Create` operation calls `CreatePlugin` of corresponding `ComponentProvider` when encountering extension components | `-` |  |  |
| Register a `ComponentProvider` | `-` |  |  |

## Exporters

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| [Exporter interface](specification/trace/sdk.md#span-exporter) | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/exporter/SpanExporter.scala#L37-L60` |
| [Exporter interface has `ForceFlush`](specification/trace/sdk.md#forceflush-2) | `-` |  |  |
| Standard output (logging) | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/exporter/ConsoleSpanExporter.scala#L32-L35`<br>`sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/ConsoleMetricExporter.scala#L38-L40`<br>`sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/exporter/ConsoleLogRecordExporter.scala#L54-L68` |
| In-memory (mock exporter) | `+` |  | `sdk/trace-testkit/src/main/scala/org/typelevel/otel4s/sdk/testkit/trace/InMemorySpanExporter.scala#L34-L41`<br>`sdk/metrics-testkit/src/main/scala/org/typelevel/otel4s/sdk/testkit/metrics/InMemoryMetricExporter.scala#L40-L47`<br>`sdk/logs-testkit/src/main/scala/org/typelevel/otel4s/sdk/testkit/logs/InMemoryLogRecordExporter.scala#L34-L41` |
| ****[OTLP](specification/protocol/otlp.md)**** / OTLP/gRPC Exporter | `+` |  | `sdk-exporter/trace/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/trace/OtlpSpanExporter.scala#L41-L44` |
| ****[OTLP](specification/protocol/otlp.md)**** / OTLP/HTTP binary Protobuf Exporter | `+` |  | `sdk-exporter/trace/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/trace/OtlpSpanExporter.scala#L41-L44` |
| ****[OTLP](specification/protocol/otlp.md)**** / OTLP/HTTP JSON Protobuf Exporter | `+` |  | `sdk-exporter/trace/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/trace/OtlpSpanExporter.scala#L41-L44` |
| ****[OTLP](specification/protocol/otlp.md)**** / OTLP/HTTP gzip Content-Encoding support | `+` | `true` | `sdk-exporter/common/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/OtlpClient.scala#L189-L194` |
| ****[OTLP](specification/protocol/otlp.md)**** / Concurrent sending | `+` |  | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/exporter/SpanExporter.scala#L53-L56` |
| ****[OTLP](specification/protocol/otlp.md)**** / Honors retryable responses with backoff | `+` | `true` | `sdk-exporter/common/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/OtlpClient.scala#L195-L210`<br>`sdk-exporter/common/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/OtlpClient.scala#L216-L235` |
| ****[OTLP](specification/protocol/otlp.md)**** / Honors non-retryable responses | `-` | `true` |  |
| ****[OTLP](specification/protocol/otlp.md)**** / Honors throttling response | `-` | `true` |  |
| ****[OTLP](specification/protocol/otlp.md)**** / Multi-destination spec compliance | `-` | `true` |  |
| ****[OTLP](specification/protocol/otlp.md)**** / SchemaURL in ResourceSpans and ScopeSpans | `+` |  | `sdk-exporter/trace/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/trace/SpansProtoEncoder.scala#L68-L81` |
| ****[OTLP](specification/protocol/otlp.md)**** / SchemaURL in ResourceMetrics and ScopeMetrics | `+` |  | `sdk-exporter/metrics/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/metrics/MetricsProtoEncoder.scala#L59-L75` |
| ****[OTLP](specification/protocol/otlp.md)**** / SchemaURL in ResourceLogs and ScopeLogs | `+` |  | `sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/LogsProtoEncoder.scala#L109-L121` |
| ****[OTLP](specification/protocol/otlp.md)**** / Honors the [user agent spec](specification/protocol/exporter.md#user-agent) | `+` |  | `sdk-exporter/common/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/OtlpClient.scala#L78-L89` |
| ****[OTLP](specification/protocol/otlp.md)**** / [Partial Success](https://github.com/open-telemetry/opentelemetry-proto/blob/main/docs/specification.md#partial-success) messages are handled and logged for OTLP/gRPC | `+` | `true` | `sdk-exporter/common/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/OtlpClient.scala#L350-L357` |
| ****[OTLP](specification/protocol/otlp.md)**** / [Partial Success](https://github.com/open-telemetry/opentelemetry-proto/blob/main/docs/specification.md#partial-success-1) messages are handled and logged for OTLP/HTTP | `-` | `true` |  |
| ****[OTLP](specification/protocol/otlp.md)**** / Metric Exporter configurable temporality preference | `+` |  | `sdk-exporter/metrics/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/metrics/OtlpMetricExporter.scala#L123-L130` |
| ****[OTLP](specification/protocol/otlp.md)**** / Metric Exporter configurable default aggregation | `+` |  | `sdk-exporter/metrics/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/metrics/OtlpMetricExporter.scala#L139-L141` |
| ****[Zipkin](specification/trace/sdk_exporters/zipkin.md)**** / Zipkin V1 JSON | `-` | `true` |  |
| ****[Zipkin](specification/trace/sdk_exporters/zipkin.md)**** / Zipkin V1 Thrift | `-` | `true` |  |
| ****[Zipkin](specification/trace/sdk_exporters/zipkin.md)**** / Zipkin V2 JSON | `-` | `true` |  |
| ****[Zipkin](specification/trace/sdk_exporters/zipkin.md)**** / Zipkin V2 Protobuf | `-` | `true` |  |
| ****[Zipkin](specification/trace/sdk_exporters/zipkin.md)**** / Service name mapping | `-` |  |  |
| ****[Zipkin](specification/trace/sdk_exporters/zipkin.md)**** / SpanKind mapping | `-` |  |  |
| ****[Zipkin](specification/trace/sdk_exporters/zipkin.md)**** / InstrumentationLibrary mapping | `-` |  |  |
| ****[Zipkin](specification/trace/sdk_exporters/zipkin.md)**** / InstrumentationScope mapping | `-` |  |  |
| ****[Zipkin](specification/trace/sdk_exporters/zipkin.md)**** / Boolean attributes | `-` |  |  |
| ****[Zipkin](specification/trace/sdk_exporters/zipkin.md)**** / Array attributes | `-` |  |  |
| ****[Zipkin](specification/trace/sdk_exporters/zipkin.md)**** / Status mapping | `-` |  |  |
| ****[Zipkin](specification/trace/sdk_exporters/zipkin.md)**** / Error Status mapping | `-` |  |  |
| ****[Zipkin](specification/trace/sdk_exporters/zipkin.md)**** / Event attributes mapping to Annotations | `-` |  |  |
| ****[Zipkin](specification/trace/sdk_exporters/zipkin.md)**** / Integer microseconds in timestamps | `-` |  |  |
| ****Prometheus**** / [Metadata Deduplication](specification/compatibility/prometheus_and_openmetrics.md#metric-metadata-1) | `+` |  | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L305-L334` |
| ****Prometheus**** / [Name Sanitization](specification/compatibility/prometheus_and_openmetrics.md#metric-metadata-1) | `+` |  | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusConverter.scala#L81-L111` |
| ****Prometheus**** / [UNIT Metadata](specification/compatibility/prometheus_and_openmetrics.md#metric-metadata-1) | `+` | `true` | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusConverter.scala#L145-L173` |
| ****Prometheus**** / [Unit Suffixes](specification/compatibility/prometheus_and_openmetrics.md#metric-metadata-1) | `+` | `true` | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L297-L303` |
| ****Prometheus**** / [Unit Full Words](specification/compatibility/prometheus_and_openmetrics.md#metric-metadata-1) | `+` | `true` | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusConverter.scala#L25-L69` |
| ****Prometheus**** / [HELP Metadata](specification/compatibility/prometheus_and_openmetrics.md#metric-metadata-1) | `+` |  | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L220-L223` |
| ****Prometheus**** / [TYPE Metadata](specification/compatibility/prometheus_and_openmetrics.md#metric-metadata-1) | `+` |  | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L349-L359` |
| ****Prometheus**** / [otel_scope_name and otel_scope_version labels on all Metrics](specification/compatibility/prometheus_and_openmetrics.md#instrumentation-scope-1) | `+` |  | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L124-L130` |
| ****Prometheus**** / [otel_scope_[attribute] labels on all Metrics](specification/compatibility/prometheus_and_openmetrics.md#instrumentation-scope-1) | `+` |  | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L124-L130` |
| ****Prometheus**** / [otel_scope labels can be disabled](specification/compatibility/prometheus_and_openmetrics.md#instrumentation-scope-1) | `+` | `true` | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L97-L99` |
| ****Prometheus**** / [Gauges become Prometheus Gauges](specification/compatibility/prometheus_and_openmetrics.md#gauges-1) | `+` |  | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L357-L359` |
| ****Prometheus**** / [Cumulative Monotonic Sums become Prometheus Counters](specification/compatibility/prometheus_and_openmetrics.md#sums) | `+` |  | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L351-L354` |
| ****Prometheus**** / [Prometheus Counters have _total suffix by default](specification/compatibility/prometheus_and_openmetrics.md#sums) | `+` |  | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L118-L123` |
| ****Prometheus**** / [Prometheus Counters _total suffixing can be disabled](specification/compatibility/prometheus_and_openmetrics.md#sums) | `+` | `true` | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L94-L96` |
| ****Prometheus**** / [Cumulative Non-Monotonic Sums become Prometheus Gauges](specification/compatibility/prometheus_and_openmetrics.md#sums) | `+` |  | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L354-L356` |
| ****Prometheus**** / [Delta Non-Monotonic Sums become Cumulative Prometheus Counters](specification/compatibility/prometheus_and_openmetrics.md#sums) | `-` | `true` |  |
| ****Prometheus**** / [Cumulative Histograms become Prometheus Histograms](specification/compatibility/prometheus_and_openmetrics.md#histograms-1) | `+` |  | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L342-L344` |
| ****Prometheus**** / [Delta Histograms become Cumulative Prometheus Histograms](specification/compatibility/prometheus_and_openmetrics.md#histograms-1) | `-` | `true` |  |
| ****Prometheus**** / [Attributes Keys are Sanitized](specification/compatibility/prometheus_and_openmetrics.md#metric-attributes) | `+` |  | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusConverter.scala#L130-L140` |
| ****Prometheus**** / [Colliding sanitized attribute keys are merged](specification/compatibility/prometheus_and_openmetrics.md#metric-attributes) | `+` |  | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L305-L334` |
| ****Prometheus**** / [Exemplars for Histograms and Monotonic sums](specification/compatibility/prometheus_and_openmetrics.md#exemplars-1) | `-` | `true` |  |
| ****Prometheus**** / [`target_info` metric from Resource](specification/compatibility/prometheus_and_openmetrics.md#resource-attributes-1) | `+` | `true` | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L141-L149` |

## OpenCensus Compatibility

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| [Trace Bridge](specification/compatibility/opencensus.md#trace-bridge) | `-` |  |  |
| [Metric Bridge](specification/compatibility/opencensus.md#metrics--stats) | `-` |  |  |

## OpenTracing Compatibility

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| [Create OpenTracing Shim](specification/compatibility/opentracing.md#create-an-opentracing-tracer-shim) | `-` |  |  |
| [Tracer](specification/compatibility/opentracing.md#tracer-shim) | `-` |  |  |
| [Span](specification/compatibility/opentracing.md#span-shim) | `-` |  |  |
| [SpanContext](specification/compatibility/opentracing.md#spancontext-shim) | `-` |  |  |
| [ScopeManager](specification/compatibility/opentracing.md#scopemanager-shim) | `-` |  |  |
| Error mapping for attributes/events | `-` |  |  |
| Migration to OpenTelemetry guide | `-` |  |  |

