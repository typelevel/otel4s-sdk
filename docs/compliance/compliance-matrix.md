# Compliance Matrix

The compliance matrix below is based on the [OpenTelemetry Specification](https://github.com/open-telemetry/opentelemetry-specification/blob/main/spec-compliance-matrix.md) template.

## Traces

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| **[TracerProvider](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracerprovider-operations)** / Create TracerProvider | `+` |  | [TracerProvider.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/TracerProvider.scala#L40-L59) |
| **[TracerProvider](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracerprovider-operations)** / Get a Tracer | `+` |  | [TracerProvider.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/TracerProvider.scala#L40-L59) |
| **[TracerProvider](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracerprovider-operations)** / Get a Tracer with schema_url | `+` |  | [TracerBuilder.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/TracerBuilder.scala#L31-L42) |
| **[TracerProvider](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracerprovider-operations)** / Get a Tracer with scope attributes | `-` |  |  |
| **[TracerProvider](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracerprovider-operations)** / Associate Tracer with InstrumentationScope | `+` |  | [TracerProvider.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/TracerProvider.scala#L40-L59) |
| **[TracerProvider](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracerprovider-operations)** / Safe for concurrent calls | `+` |  | [SdkTracerProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkTracerProvider.scala#L36-L60), [SpanStorage.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SpanStorage.scala#L19-L50) |
| **[TracerProvider](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracerprovider-operations)** / Shutdown (SDK only required) | `-` |  |  |
| **[TracerProvider](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracerprovider-operations)** / ForceFlush (SDK only required) | `-` |  |  |
| **[Trace / Context interaction](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#context-interaction)** / Get active Span | `+` |  | [Tracer.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala#L52-L100) |
| **[Trace / Context interaction](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#context-interaction)** / Set active Span | `+` |  | [Tracer.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala#L52-L100) |
| **[Tracer](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracer-operations)** / Create a new Span | `+` |  | [Tracer.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala#L52-L100) |
| **[Tracer](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracer-operations)** / Documentation defines adding attributes at span creation as preferred | `-` |  |  |
| **[Tracer](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracer-operations)** / Get active Span | `+` |  | [Tracer.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala#L52-L100) |
| **[Tracer](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracer-operations)** / Mark Span active | `+` |  | [Tracer.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala#L52-L100) |
| **[Tracer](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracer-operations)** / Safe for concurrent calls | `+` |  | [SdkTracer.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkTracer.scala#L37-L89), [SpanStorage.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SpanStorage.scala#L19-L50) |
| **[SpanContext](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#spancontext)** / IsValid | `+` |  | [SpanContext.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/SpanContext.scala#L64-L70) |
| **[SpanContext](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#spancontext)** / IsRemote | `+` |  | [SpanContext.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/SpanContext.scala#L64-L70) |
| **[SpanContext](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#spancontext)** / Conforms to the W3C TraceContext spec | `+` |  | [W3CTraceContextPropagator.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/W3CTraceContextPropagator.scala#L41-L74) |
| **[SpanContext](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#spancontext)** / [Support W3C Trace Context Level 2 randomness](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#traceid-randomness) | `-` |  |  |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / Create root span | `+` |  | [SdkSpanBuilder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198) |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / Create with default parent (active span) | `+` |  | [SdkSpanBuilder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198) |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / Create with parent from Context | `+` |  | [SdkSpanBuilder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198) |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / No explicit parent Span/SpanContext allowed | `-` |  |  |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / SpanProcessor.OnStart receives parent Context | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / UpdateName | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / User-defined start timestamp | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / End | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / End with timestamp | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / IsRecording | `-` |  |  |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / IsRecording becomes false after End | `-` |  |  |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / Set status with StatusCode (Unset, Ok, Error) | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / Safe for concurrent calls | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L67-L204) |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / events collection size limit | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / attribute collection size limit | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / links collection size limit | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#span)** / [SpanProcessor.OnEnding](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#onending) | `-` | `true` |  |
| **[Span attributes](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#set-attributes)** / SetAttribute | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span attributes](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#set-attributes)** / Set order preserved | `-` | `true` |  |
| **[Span attributes](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#set-attributes)** / String type | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span attributes](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#set-attributes)** / Boolean type | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span attributes](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#set-attributes)** / Double floating-point type | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span attributes](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#set-attributes)** / Signed int64 type | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span attributes](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#set-attributes)** / Array of primitives (homogeneous) | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span attributes](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#set-attributes)** / `null` values documented as invalid/undefined | `-` |  |  |
| **[Span attributes](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#set-attributes)** / Unicode support for keys and string values | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span linking](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#specifying-links)** / Links can be recorded on span creation | `+` |  | [SdkSpanBuilder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198), [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span linking](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#specifying-links)** / Links can be recorded after span creation | `+` |  | [SdkSpanBuilder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198), [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span linking](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#specifying-links)** / Links order is preserved | `+` |  | [SdkSpanBuilder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198), [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span events](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#add-events)** / AddEvent | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span events](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#add-events)** / Add order preserved | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span events](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#add-events)** / Safe for concurrent calls | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L94-L197) |
| **[Span exceptions](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#record-exception)** / RecordException | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Span exceptions](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#record-exception)** / RecordException with extra parameters | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Sampling](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#sampling)** / Allow samplers to modify tracestate | `+` |  | [SamplingResult.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/samplers/SamplingResult.scala#L82-L94) |
| **[Sampling](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#sampling)** / ShouldSample gets full parent Context | `+` |  | [SdkSpanBuilder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198), [Sampler.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/samplers/Sampler.scala#L55-L63) |
| **[Sampling](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#sampling)** / Sampler: JaegerRemoteSampler | `-` |  |  |
| **[Sampling](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#sampling)** / [New Span ID created also for non-recording Spans](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#sdk-span-creation) | `+` |  | [SdkSpanBuilder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198) |
| **[Sampling](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#sampling)** / [IdGenerators](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#id-generators) | `+` |  | [SdkSpanBuilder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L148-L198) |
| **[Sampling](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#sampling)** / [SpanLimits](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#span-limits) | `+` | `true` | [SpanLimits.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SpanLimits.scala#L175-L201) |
| **[Sampling](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#sampling)** / [Built-in `SpanProcessor`s implement `ForceFlush` spec](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#forceflush-1) | `+` |  | [SpanProcessor.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/processor/SpanProcessor.scala#L58-L71), [BatchSpanProcessor.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/processor/BatchSpanProcessor.scala#L96-L98) |
| **[Sampling](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#sampling)** / [Attribute Limits](@OTEL_SPEC_GITHUB_URL@/specification/common/README.md#attribute-limits) | `+` | `true` | [SpanLimits.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SpanLimits.scala#L175-L201), [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Sampling](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#sampling)** / Fetch InstrumentationScope from ReadableSpan | `+` |  | [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L82-L183) |
| **[Sampling](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#sampling)** / [TraceIdRatioBased sampler implements OpenTelemetry tracestate `th` field](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#traceidratiobased) | `-` | `true` |  |
| **[Sampling](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#sampling)** / [CompositeSampler and built-in ComposableSamplers](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#compositesampler) | `-` | `true` |  |
| **[Sampling](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#sampling)** / [Sampler: AlwaysRecord](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#alwaysrecord) | `-` |  |  |

## Baggage

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| Basic support | `+` |  | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L197-L204) |
| Use official header name `baggage` | `+` |  | [W3CBaggagePropagator.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/W3CBaggagePropagator.scala#L41-L44) |

## Metrics

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| The API provides a way to set and get a global default `MeterProvider`. | `-` | `true` |  |
| It is possible to create any number of `MeterProvider`s. | `+` | `true` | [SdkMeterProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L181-L250) |
| `MeterProvider` provides a way to get a `Meter`. | `+` |  | [MeterProvider.scala](@OTEL4S_GITHUB_URL@/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/MeterProvider.scala#L37-L56) |
| `get_meter` accepts name, `version` and `schema_url`. | `+` |  | [MeterProvider.scala](@OTEL4S_GITHUB_URL@/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/MeterProvider.scala#L37-L56), [MeterBuilder.scala](@OTEL4S_GITHUB_URL@/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/MeterBuilder.scala#L28-L39) |
| `get_meter` accepts `attributes`. | `-` |  |  |
| When an invalid `name` is specified a working `Meter` implementation is returned as a fallback. | `+` |  | [SdkMeterProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L60-L66) |
| The fallback `Meter` `name` property keeps its original invalid value. | `-` | `true` |  |
| Associate `Meter` with `InstrumentationScope`. | `+` |  | [SdkMeterProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L181-L250) |
| `Counter` instrument is supported. | `+` |  | [Meter.scala](@OTEL4S_GITHUB_URL@/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267) |
| `AsynchronousCounter` instrument is supported. | `+` |  | [Meter.scala](@OTEL4S_GITHUB_URL@/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267) |
| `Histogram` instrument is supported. | `+` |  | [Meter.scala](@OTEL4S_GITHUB_URL@/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267) |
| `AsynchronousGauge` instrument is supported. | `+` |  | [Meter.scala](@OTEL4S_GITHUB_URL@/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267) |
| `Gauge` instrument is supported. | `+` |  | [Meter.scala](@OTEL4S_GITHUB_URL@/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267) |
| `UpDownCounter` instrument is supported. | `+` |  | [Meter.scala](@OTEL4S_GITHUB_URL@/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267) |
| `AsynchronousUpDownCounter` instrument is supported. | `+` |  | [Meter.scala](@OTEL4S_GITHUB_URL@/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267) |
| Instruments have `name` | `+` |  | [Meter.scala](@OTEL4S_GITHUB_URL@/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267) |
| Instruments have kind. | `+` |  | [Meter.scala](@OTEL4S_GITHUB_URL@/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267) |
| Instruments have an optional unit of measure. | `+` |  | [Meter.scala](@OTEL4S_GITHUB_URL@/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267) |
| Instruments have an optional description. | `+` |  | [Meter.scala](@OTEL4S_GITHUB_URL@/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Meter.scala#L70-L267) |
| A valid instrument MUST be created and warning SHOULD be emitted when multiple instruments are registered under the same `Meter` using the same `name`. | `+` |  | [ViewRegistry.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/ViewRegistry.scala#L57-L62) |
| Duplicate instrument registration name conflicts are resolved by using the first-seen for the stream name. | `-` |  |  |
| It is possible to register two instruments with same `name` under different `Meter`s. | `+` |  | [SdkMeterProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L181-L250) |
| Instrument names conform to the specified syntax. | `-` |  |  |
| Instrument units conform to the specified syntax. | `-` |  |  |
| Instrument descriptions conform to the specified syntax. | `-` |  |  |
| Instrument supports the advisory ExplicitBucketBoundaries parameter. | `+` |  | [Histogram.scala](@OTEL4S_GITHUB_URL@/core/metrics/src/main/scala/org/typelevel/otel4s/metrics/Histogram.scala#L75-L85), [SdkHistogram.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkHistogram.scala#L114-L125) |
| Instrument supports the advisory Attributes parameter. | `-` |  |  |
| All methods of `MeterProvider` are safe to be called concurrently. | `+` |  | [SdkMeterProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L206-L257), [ComponentRegistry.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/internal/ComponentRegistry.scala#L73-L99) |
| All methods of `Meter` are safe to be called concurrently. | `+` |  | [SdkMeter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeter.scala#L45-L113), [MeterSharedState.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/MeterSharedState.scala#L44-L55) |
| All methods of any instrument are safe to be called concurrently. | `+` |  | [SdkCounter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkCounter.scala#L56-L75), [SynchronousStorage.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/SynchronousStorage.scala#L47-L74), [SynchronousStorage.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/SynchronousStorage.scala#L123-L151) |
| `MeterProvider` allows a `Resource` to be specified. | `+` |  | [SdkMeterProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L95-L109) |
| A specified `Resource` can be associated with all the produced metrics from any `Meter` from the `MeterProvider`. | `+` |  | [SdkMeterProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L221-L229) |
| The supplied `name`, `version` and `schema_url` arguments passed to the `MeterProvider` are used to create an `InstrumentationLibrary` instance stored in the `Meter`. | `+` |  | [SdkMeterProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L207-L214) |
| The supplied `name`, `version` and `schema_url` arguments passed to the `MeterProvider` are used to create an `InstrumentationScope` instance stored in the `Meter`. | `+` |  | [SdkMeterProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L207-L214) |
| Configuration is managed solely by the `MeterProvider`. | `+` |  | [SdkMeterProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L181-L250) |
| The `MeterProvider` provides methods to update the configuration | `-` | `true` |  |
| The updated configuration applies to all already returned `Meter`s. | `-` | `if above` |  |
| There is a way to register `View`s with a `MeterProvider`. | `+` |  | [SdkMeterProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala#L137-L137) |
| The `View` instrument selection criteria is as specified. | `+` |  | [InstrumentSelector.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/InstrumentSelector.scala#L37-L43) |
| The `View` instrument selection criteria supports wildcards. | `+` | `true` | [InstrumentSelector.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/InstrumentSelector.scala#L37-L43) |
| The `View` instrument selection criteria supports the match-all wildcard. | `+` |  | [InstrumentSelector.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/InstrumentSelector.scala#L37-L43) |
| The name of the `View` can be specified. | `+` |  | [View.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/View.scala#L77-L179) |
| The `View` allows configuring the name, description, attributes keys and aggregation of the resulting metric stream. | `+` |  | [View.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/View.scala#L77-L179) |
| The `View` allows configuring excluded attribute keys of resulting metric stream. | `+` |  | [View.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/View.scala#L77-L179) |
| The `View` allows configuring the exemplar reservoir of resulting metric stream. | `-` | `true` |  |
| The SDK allows more than one `View` to be specified per instrument. | `+` | `true` | [ViewRegistry.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/ViewRegistry.scala#L57-L62) |
| The `Drop` aggregation is available. | `+` |  | [Aggregation.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/Aggregation.scala#L51-L109) |
| The `Default` aggregation is available. | `+` |  | [Aggregation.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/Aggregation.scala#L51-L109) |
| The `Default` aggregation uses the specified aggregation by instrument. | `+` |  | [Aggregation.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/Aggregation.scala#L51-L109) |
| The `Sum` aggregation is available. | `+` |  | [Aggregation.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/Aggregation.scala#L51-L109) |
| The `LastValue` aggregation is available. | `+` |  | [Aggregation.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/Aggregation.scala#L51-L109) |
| The `ExplicitBucketHistogram` aggregation is available. | `+` |  | [Aggregation.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/Aggregation.scala#L51-L109) |
| The `ExponentialBucketHistogram` aggregation is available. | `-` |  |  |
| The metrics Reader implementation supports registering metric Exporters | `+` |  | [MetricReader.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/MetricReader.scala#L36-L72), [PeriodicMetricReader.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/PeriodicMetricReader.scala#L59-L122) |
| The metrics Reader implementation supports configuring the default aggregation on the basis of instrument kind. | `+` |  | [PeriodicMetricReader.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/PeriodicMetricReader.scala#L59-L122) |
| The metrics Reader implementation supports configuring the default temporality on the basis of instrument kind. | `+` |  | [PeriodicMetricReader.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/PeriodicMetricReader.scala#L59-L122) |
| The metrics Exporter has access to the aggregated metrics data (aggregated points, not raw measurements). | `+` |  | [MetricExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/MetricExporter.scala#L72-L84) |
| The metrics Exporter `export` function can not be called concurrently from the same Exporter instance. | `+` |  | [PeriodicMetricReader.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/PeriodicMetricReader.scala#L59-L122) |
| The metrics Exporter `export` function does not block indefinitely. | `+` |  | [PeriodicMetricReader.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/PeriodicMetricReader.scala#L59-L122) |
| The metrics Exporter `export` function receives a batch of metrics. | `+` |  | [MetricExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/MetricExporter.scala#L72-L84) |
| The metrics Exporter `export` function returns `Success` or `Failure`. | `+` |  | [PeriodicMetricReader.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/PeriodicMetricReader.scala#L59-L122) |
| The metrics Exporter provides a `ForceFlush` function. | `-` |  |  |
| The metrics Exporter `ForceFlush` can inform the caller whether it succeeded, failed or timed out. | `-` |  |  |
| The metrics Exporter provides a `shutdown` function. | `-` |  |  |
| The metrics Exporter `shutdown` function do not block indefinitely. | `-` |  |  |
| The metrics SDK samples `Exemplar`s from measurements. | `+` |  | [ExemplarReservoir.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarReservoir.scala#L45-L56) |
| Exemplar sampling can be disabled. | `+` |  | [ExemplarReservoir.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarReservoir.scala#L45-L56) |
| The metrics SDK supports SDK-wide exemplar filter configuration | `+` |  | [ExemplarFilterAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/autoconfigure/ExemplarFilterAutoConfigure.scala#L33-L33) |
| The metrics SDK supports `TraceBased` exemplar filter | `+` |  | [ExemplarFilter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarFilter.scala#L54-L75) |
| The metrics SDK supports `AlwaysOn` exemplar filter | `+` |  | [ExemplarFilter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarFilter.scala#L54-L75) |
| The metrics SDK supports `AlwaysOff` exemplar filter | `+` |  | [ExemplarFilter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarFilter.scala#L54-L75) |
| Exemplars retain any attributes available in the measurement that are not preserved by aggregation or view configuration. | `+` |  | [ExemplarData.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/ExemplarData.scala#L48-L57) |
| Exemplars contain the associated trace id and span id of the active span in the Context when the measurement was taken. | `+` |  | [ExemplarData.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/ExemplarData.scala#L48-L57) |
| Exemplars contain the timestamp when the measurement was taken. | `+` |  | [ExemplarData.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/ExemplarData.scala#L48-L57) |
| The metrics SDK provides an `ExemplarReservoir` interface or extension point. | `+` |  | [ExemplarReservoir.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarReservoir.scala#L45-L56) |
| An `ExemplarReservoir` has an `offer` method with access to the measurement value, attributes, `Context` and timestamp. | `+` |  | [ExemplarReservoir.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarReservoir.scala#L45-L56) |
| The metrics SDK provides a `SimpleFixedSizeExemplarReservoir` that is used by default for all aggregations except `ExplicitBucketHistogram`. | `+` |  | [ExemplarReservoir.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarReservoir.scala#L71-L99) |
| The metrics SDK provides an `AlignedHistogramBucketExemplarReservoir` that is used by default for `ExplicitBucketHistogram` aggregation. | `+` |  | [ExemplarReservoir.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/ExemplarReservoir.scala#L71-L99) |
| A metric Producer accepts an optional metric Filter | `-` |  |  |
| The metric Reader implementation supports registering metric Filter and passing them  its registered metric Producers | `-` |  |  |
| The metric SDK's metric Producer implementations uses the metric Filter | `-` |  |  |
| Metric SDK implements [cardinality limit](@OTEL_SPEC_GITHUB_URL@/specification/metrics/sdk.md#cardinality-limits) | `+` |  | [SynchronousStorage.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/SynchronousStorage.scala#L141-L145) |
| Metric SDK supports configuring cardinality limit at MeterReader level | `+` |  | [CardinalityLimitSelector.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/CardinalityLimitSelector.scala#L38-L39) |
| Metric SDK supports configuring cardinality limit per metric (using Views) | `+` |  | [View.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/View.scala#L77-L179) |

## Logs

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| LoggerProvider.Get Logger | `+` |  | [LoggerProvider.scala](@OTEL4S_GITHUB_URL@/core/logs/src/main/scala/org/typelevel/otel4s/logs/LoggerProvider.scala#L44-L63) |
| LoggerProvider.Get Logger accepts attributes | `-` |  |  |
| LoggerProvider.Shutdown | `-` |  |  |
| LoggerProvider.ForceFlush | `-` |  |  |
| Logger.Emit(LogRecord) | `+` |  | [SdkLogRecordBuilder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/SdkLogRecordBuilder.scala#L84-L90) |
| LogRecord.Set EventName | `+` |  | [SdkLogRecordBuilder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/SdkLogRecordBuilder.scala#L72-L73) |
| Logger.Enabled | `+` | `true` | [SdkLogger.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/SdkLogger.scala#L35-L42), [SdkLoggerProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/SdkLoggerProvider.scala#L164-L168) |
| Ergonomic API | `-` | `true` |  |
| SimpleLogRecordProcessor | `+` |  | [SimpleLogRecordProcessor.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/processor/SimpleLogRecordProcessor.scala#L46-L54) |
| BatchLogRecordProcessor | `+` |  | [BatchLogRecordProcessor.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/processor/BatchLogRecordProcessor.scala#L76-L85) |
| Can plug custom LogRecordProcessor | `+` |  | [SdkLoggerProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/SdkLoggerProvider.scala#L151-L153) |
| LogRecordProcessor.Enabled | `-` | `true` |  |
| OTLP/gRPC exporter | `+` |  | [OtlpLogRecordExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/OtlpLogRecordExporter.scala#L42-L46) |
| OTLP/HTTP exporter | `+` |  | [OtlpLogRecordExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/OtlpLogRecordExporter.scala#L42-L46) |
| OTLP File exporter | `-` |  |  |
| Can plug custom LogRecordExporter | `+` |  | [LogRecordExportersAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/autoconfigure/LogRecordExportersAutoConfigure.scala#L39-L39) |
| Trace Context Injection | `+` |  | [SdkLogRecordBuilder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/SdkLogRecordBuilder.scala#L96-L97) |

## Resource

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| Create from Attributes | `+` |  | [TelemetryResource.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/TelemetryResource.scala#L120-L134) |
| Create empty | `+` |  | [TelemetryResource.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/TelemetryResource.scala#L141-L145) |
| [Merge (v2)](@OTEL_SPEC_GITHUB_URL@/specification/resource/sdk.md#merge) | `+` |  | [TelemetryResource.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/TelemetryResource.scala#L158-L170) |
| Retrieve attributes | `+` |  | [TelemetryResource.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/TelemetryResource.scala#L38-L41) |
| [Default value](https://github.com/open-telemetry/semantic-conventions/blob/main/docs/resource/README.md#semantic-attributes-with-dedicated-environment-variable) for service.name | `+` |  | [TelemetryResource.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/TelemetryResource.scala#L103-L107) |
| [Resource detector](@OTEL_SPEC_GITHUB_URL@/specification/resource/sdk.md#detecting-resource-information-from-the-environment) interface/mechanism | `+` |  | [TelemetryResourceAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/autoconfigure/TelemetryResourceAutoConfigure.scala#L142-L146) |
| [Resource detectors populate Schema URL](@OTEL_SPEC_GITHUB_URL@/specification/resource/sdk.md#detecting-resource-information-from-the-environment) | `-` |  |  |

## Context Propagation

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| Create Context Key | `+` |  | [Context.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/context/Context.scala#L95-L105) |
| Get value from Context | `+` |  | [Context.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/context/Context.scala#L34-L40) |
| Set value for Context | `+` |  | [Context.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/context/Context.scala#L44-L45) |
| Attach Context | `+` |  | [LocalProvider.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/context/LocalProvider.scala#L140-L142) |
| Detach Context | `+` |  | [LocalProvider.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/context/LocalProvider.scala#L140-L142) |
| Get current Context | `+` |  | [LocalProvider.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/context/LocalProvider.scala#L138-L139) |
| Composite Propagator | `+` |  | [TextMapPropagator.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/context/propagation/TextMapPropagator.scala#L93-L99) |
| Global Propagator | `-` |  |  |
| TraceContext Propagator | `+` |  | [W3CTraceContextPropagator.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/W3CTraceContextPropagator.scala#L36-L43) |
| B3 Propagator | `+` |  | [B3Propagator.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/B3Propagator.scala#L48-L66) |
| Jaeger Propagator | `+` |  | [JaegerPropagator.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/JaegerPropagator.scala#L47-L53) |
| OT Propagator | `+` |  | [OtTracePropagator.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/OtTracePropagator.scala#L37-L48) |
| OpenCensus Binary Propagator | `-` |  |  |
| [TextMapPropagator](@OTEL_SPEC_GITHUB_URL@/specification/context/api-propagators.md#textmap-propagator) | `+` |  | [TextMapPropagator.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/context/propagation/TextMapPropagator.scala#L35-L38) |
| Fields | `+` |  | [TextMapPropagator.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/context/propagation/TextMapPropagator.scala#L37-L38) |
| Setter argument | `+` | `true` | [TextMapPropagator.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/context/propagation/TextMapPropagator.scala#L73-L73) |
| Getter argument | `+` | `true` | [TextMapPropagator.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/context/propagation/TextMapPropagator.scala#L54-L54) |
| Getter argument returning Keys | `+` | `true` | [JaegerPropagator.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/JaegerPropagator.scala#L146-L147) |

## Environment Variables

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| OTEL\\_SDK\\_DISABLED | `+` |  | [CommonConfigKeys.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/autoconfigure/CommonConfigKeys.scala#L20-L20), [OpenTelemetrySdk.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/all/src/main/scala/org/typelevel/otel4s/sdk/OpenTelemetrySdk.scala#L498-L505) |
| OTEL\\_RESOURCE\\_ATTRIBUTES | `+` |  | [TelemetryResourceAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/autoconfigure/TelemetryResourceAutoConfigure.scala#L110-L115) |
| OTEL\\_SERVICE\\_NAME | `+` |  | [TelemetryResourceAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/autoconfigure/TelemetryResourceAutoConfigure.scala#L117-L124) |
| OTEL\\_LOG\\_LEVEL | `-` |  |  |
| OTEL\\_PROPAGATORS | `+` |  | [ContextPropagatorsAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/ContextPropagatorsAutoConfigure.scala#L119-L121) |
| OTEL\\_BSP\\_* | `+` |  | [BatchSpanProcessorAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/BatchSpanProcessorAutoConfigure.scala#L39-L42) |
| OTEL\\_BLRP\\_* | `+` |  | [BatchLogRecordProcessorAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/autoconfigure/BatchLogRecordProcessorAutoConfigure.scala#L39-L42) |
| OTEL\\_EXPORTER\\_OTLP\\_* | `+` |  | [OtlpClientAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/common/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/autoconfigure/OtlpClientAutoConfigure.scala#L47-L53) |
| OTEL\\_EXPORTER\\_ZIPKIN\\_* | `-` |  |  |
| OTEL\\_TRACES\\_EXPORTER | `+` |  | [SpanExportersAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SpanExportersAutoConfigure.scala#L39-L39) |
| OTEL\\_METRICS\\_EXPORTER | `+` |  | [MetricExportersAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/autoconfigure/MetricExportersAutoConfigure.scala#L39-L39) |
| OTEL\\_LOGS\\_EXPORTER | `+` |  | [LogRecordExportersAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/autoconfigure/LogRecordExportersAutoConfigure.scala#L39-L39) |
| OTEL\\_SPAN\\_ATTRIBUTE\\_COUNT\\_LIMIT | `+` |  | [SpanLimitsAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SpanLimitsAutoConfigure.scala#L31-L31) |
| OTEL\\_SPAN\\_ATTRIBUTE\\_VALUE\\_LENGTH\\_LIMIT | `+` |  | [SpanLimitsAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SpanLimitsAutoConfigure.scala#L36-L36) |
| OTEL\\_SPAN\\_EVENT\\_COUNT\\_LIMIT | `+` |  | [SpanLimitsAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SpanLimitsAutoConfigure.scala#L32-L32) |
| OTEL\\_SPAN\\_LINK\\_COUNT\\_LIMIT | `+` |  | [SpanLimitsAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SpanLimitsAutoConfigure.scala#L33-L33) |
| OTEL\\_EVENT\\_ATTRIBUTE\\_COUNT\\_LIMIT | `+` |  | [SpanLimitsAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SpanLimitsAutoConfigure.scala#L34-L34) |
| OTEL\\_LINK\\_ATTRIBUTE\\_COUNT\\_LIMIT | `+` |  | [SpanLimitsAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SpanLimitsAutoConfigure.scala#L35-L35) |
| OTEL\\_LOGRECORD\\_ATTRIBUTE\\_COUNT\\_LIMIT | `-` |  |  |
| OTEL\\_LOGRECORD\\_ATTRIBUTE\\_VALUE\\_LENGTH\\_LIMIT | `-` |  |  |
| OTEL\\_TRACES\\_SAMPLER | `+` |  | [SamplerAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SamplerAutoConfigure.scala#L33-L34) |
| OTEL\\_TRACES\\_SAMPLER\\_ARG | `+` |  | [SamplerAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SamplerAutoConfigure.scala#L33-L34) |
| OTEL\\_ATTRIBUTE\\_VALUE\\_LENGTH\\_LIMIT | `+` |  | [LogRecordLimitsAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/autoconfigure/LogRecordLimitsAutoConfigure.scala#L33-L34) |
| OTEL\\_ATTRIBUTE\\_COUNT\\_LIMIT | `+` |  | [LogRecordLimitsAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/autoconfigure/LogRecordLimitsAutoConfigure.scala#L33-L34) |
| OTEL\\_METRIC\\_EXPORT\\_INTERVAL | `+` |  | [MetricReadersAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/autoconfigure/MetricReadersAutoConfigure.scala#L36-L37) |
| OTEL\\_METRIC\\_EXPORT\\_TIMEOUT | `+` |  | [MetricReadersAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/autoconfigure/MetricReadersAutoConfigure.scala#L36-L37) |
| OTEL\\_METRICS\\_EXEMPLAR\\_FILTER | `+` |  | [ExemplarFilterAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/autoconfigure/ExemplarFilterAutoConfigure.scala#L33-L33) |
| OTEL\\_EXPORTER\\_OTLP\\_METRICS\\_TEMPORALITY\\_PREFERENCE | `-` |  |  |
| OTEL\\_EXPORTER\\_OTLP\\_METRICS\\_DEFAULT\\_HISTOGRAM\\_AGGREGATION | `-` |  |  |
| OTEL\\_EXPERIMENTAL\\_CONFIG\\_FILE | `-` |  |  |

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

| Item                                                                                                                                                                                                                                         | Status | Optional | Evidence |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| --- | --- | --- |
| [Exporter interface](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#span-exporter)                                                                                                                                                        | `+` |  | [SpanExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/exporter/SpanExporter.scala#L37-L60) |
| [Exporter interface has `ForceFlush`](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#forceflush-2)                                                                                                                                        | `-` |  |  |
| Standard output (logging)                                                                                                                                                                                                                    | `+` |  | [ConsoleSpanExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/exporter/ConsoleSpanExporter.scala#L32-L35), [ConsoleMetricExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/ConsoleMetricExporter.scala#L38-L40), [ConsoleLogRecordExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/exporter/ConsoleLogRecordExporter.scala#L54-L68) |
| In-memory (mock exporter)                                                                                                                                                                                                                    | `+` |  | [InMemorySpanExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace-testkit/src/main/scala/org/typelevel/otel4s/sdk/testkit/trace/InMemorySpanExporter.scala#L34-L41), [InMemoryMetricExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics-testkit/src/main/scala/org/typelevel/otel4s/sdk/testkit/metrics/InMemoryMetricExporter.scala#L40-L47), [InMemoryLogRecordExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs-testkit/src/main/scala/org/typelevel/otel4s/sdk/testkit/logs/InMemoryLogRecordExporter.scala#L34-L41) |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / OTLP/gRPC Exporter                                                                                                                                                   | `+` |  | [OtlpSpanExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/trace/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/trace/OtlpSpanExporter.scala#L41-L44) |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / OTLP/HTTP binary Protobuf Exporter                                                                                                                                   | `+` |  | [OtlpSpanExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/trace/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/trace/OtlpSpanExporter.scala#L41-L44) |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / OTLP/HTTP JSON Protobuf Exporter                                                                                                                                     | `+` |  | [OtlpSpanExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/trace/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/trace/OtlpSpanExporter.scala#L41-L44) |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / OTLP/HTTP gzip Content-Encoding support                                                                                                                              | `+` | `true` | [OtlpClient.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/common/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/OtlpClient.scala#L189-L194) |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / Concurrent sending                                                                                                                                                   | `+` |  | [SpanExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/exporter/SpanExporter.scala#L53-L56) |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / Honors retryable responses with backoff                                                                                                                              | `+` | `true` | [OtlpClient.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/common/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/OtlpClient.scala#L195-L210), [OtlpClient.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/common/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/OtlpClient.scala#L216-L235) |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / Honors non-retryable responses                                                                                                                                       | `-` | `true` |  |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / Honors throttling response                                                                                                                                           | `-` | `true` |  |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / Multi-destination spec compliance                                                                                                                                    | `-` | `true` |  |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / SchemaURL in ResourceSpans and ScopeSpans                                                                                                                            | `+` |  | [SpansProtoEncoder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/trace/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/trace/SpansProtoEncoder.scala#L68-L81) |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / SchemaURL in ResourceMetrics and ScopeMetrics                                                                                                                        | `+` |  | [MetricsProtoEncoder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/metrics/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/metrics/MetricsProtoEncoder.scala#L59-L75) |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / SchemaURL in ResourceLogs and ScopeLogs                                                                                                                              | `+` |  | [LogsProtoEncoder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/LogsProtoEncoder.scala#L109-L121) |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / Honors the [user agent spec](@OTEL_SPEC_GITHUB_URL@/specification/protocol/exporter.md#user-agent)                                                                   | `+` |  | [OtlpClient.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/common/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/OtlpClient.scala#L78-L89) |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / [Partial Success](https://github.com/open-telemetry/opentelemetry-proto/blob/main/docs/specification.md#partial-success) messages are handled and logged for OTLP/gRPC | `+` | `true` | [OtlpClient.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/common/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/OtlpClient.scala#L350-L357) |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / [Partial Success](https://github.com/open-telemetry/opentelemetry-proto/blob/main/docs/specification.md#partial-success-1) messages are handled and logged for OTLP/HTTP | `-` | `true` |  |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / Metric Exporter configurable temporality preference                                                                                                                  | `+` |  | [OtlpMetricExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/metrics/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/metrics/OtlpMetricExporter.scala#L123-L130) |
| ****[OTLP](@OTEL_SPEC_GITHUB_URL@/specification/protocol/otlp.md)**** / Metric Exporter configurable default aggregation                                                                                                                     | `+` |  | [OtlpMetricExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/metrics/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/metrics/OtlpMetricExporter.scala#L139-L141) |
| ****[Zipkin](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk_exporters/zipkin.md)**** / Zipkin V1 JSON                                                                                                                                        | `-` | `true` |  |
| ****[Zipkin](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk_exporters/zipkin.md)**** / Zipkin V1 Thrift                                                                                                                                      | `-` | `true` |  |
| ****[Zipkin](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk_exporters/zipkin.md)**** / Zipkin V2 JSON                                                                                                                                        | `-` | `true` |  |
| ****[Zipkin](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk_exporters/zipkin.md)**** / Zipkin V2 Protobuf                                                                                                                                    | `-` | `true` |  |
| ****[Zipkin](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk_exporters/zipkin.md)**** / Service name mapping                                                                                                                                  | `-` |  |  |
| ****[Zipkin](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk_exporters/zipkin.md)**** / SpanKind mapping                                                                                                                                      | `-` |  |  |
| ****[Zipkin](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk_exporters/zipkin.md)**** / InstrumentationLibrary mapping                                                                                                                        | `-` |  |  |
| ****[Zipkin](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk_exporters/zipkin.md)**** / InstrumentationScope mapping                                                                                                                          | `-` |  |  |
| ****[Zipkin](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk_exporters/zipkin.md)**** / Boolean attributes                                                                                                                                    | `-` |  |  |
| ****[Zipkin](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk_exporters/zipkin.md)**** / Array attributes                                                                                                                                      | `-` |  |  |
| ****[Zipkin](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk_exporters/zipkin.md)**** / Status mapping                                                                                                                                        | `-` |  |  |
| ****[Zipkin](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk_exporters/zipkin.md)**** / Error Status mapping                                                                                                                                  | `-` |  |  |
| ****[Zipkin](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk_exporters/zipkin.md)**** / Event attributes mapping to Annotations                                                                                                               | `-` |  |  |
| ****[Zipkin](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk_exporters/zipkin.md)**** / Integer microseconds in timestamps                                                                                                                    | `-` |  |  |
| ****Prometheus**** / [Metadata Deduplication](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#metric-metadata-1)                                                                                            | `+` |  | [PrometheusWriter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L305-L334) |
| ****Prometheus**** / [Name Sanitization](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#metric-metadata-1)                                                                                                 | `+` |  | [PrometheusConverter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusConverter.scala#L81-L111) |
| ****Prometheus**** / [UNIT Metadata](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#metric-metadata-1)                                                                                                     | `+` | `true` | [PrometheusConverter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusConverter.scala#L145-L173) |
| ****Prometheus**** / [Unit Suffixes](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#metric-metadata-1)                                                                                                     | `+` | `true` | [PrometheusWriter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L297-L303) |
| ****Prometheus**** / [Unit Full Words](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#metric-metadata-1)                                                                                                   | `+` | `true` | [PrometheusConverter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusConverter.scala#L25-L69) |
| ****Prometheus**** / [HELP Metadata](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#metric-metadata-1)                                                                                                     | `+` |  | [PrometheusWriter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L220-L223) |
| ****Prometheus**** / [TYPE Metadata](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#metric-metadata-1)                                                                                                     | `+` |  | [PrometheusWriter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L349-L359) |
| ****Prometheus**** / [otel_scope_name and otel_scope_version labels on all Metrics](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#instrumentation-scope-1)                                                | `+` |  | [PrometheusWriter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L124-L130) |
| ****Prometheus**** / [otel_scope_'attribute' labels on all Metrics](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#instrumentation-scope-1)                                                                  | `+` |  | [PrometheusWriter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L124-L130) |
| ****Prometheus**** / [otel_scope labels can be disabled](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#instrumentation-scope-1)                                                                           | `+` | `true` | [PrometheusWriter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L97-L99) |
| ****Prometheus**** / [Gauges become Prometheus Gauges](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#gauges-1)                                                                                            | `+` |  | [PrometheusWriter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L357-L359) |
| ****Prometheus**** / [Cumulative Monotonic Sums become Prometheus Counters](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#sums)                                                                           | `+` |  | [PrometheusWriter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L351-L354) |
| ****Prometheus**** / [Prometheus Counters have _total suffix by default](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#sums)                                                                              | `+` |  | [PrometheusWriter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L118-L123) |
| ****Prometheus**** / [Prometheus Counters _total suffixing can be disabled](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#sums)                                                                           | `+` | `true` | [PrometheusWriter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L94-L96) |
| ****Prometheus**** / [Cumulative Non-Monotonic Sums become Prometheus Gauges](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#sums)                                                                         | `+` |  | [PrometheusWriter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L354-L356) |
| ****Prometheus**** / [Delta Non-Monotonic Sums become Cumulative Prometheus Counters](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#sums)                                                                 | `-` | `true` |  |
| ****Prometheus**** / [Cumulative Histograms become Prometheus Histograms](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#histograms-1)                                                                     | `+` |  | [PrometheusWriter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L342-L344) |
| ****Prometheus**** / [Delta Histograms become Cumulative Prometheus Histograms](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#histograms-1)                                                               | `-` | `true` |  |
| ****Prometheus**** / [Attributes Keys are Sanitized](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#metric-attributes)                                                                                     | `+` |  | [PrometheusConverter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusConverter.scala#L130-L140) |
| ****Prometheus**** / [Colliding sanitized attribute keys are merged](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#metric-attributes)                                                                     | `+` |  | [PrometheusWriter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L305-L334) |
| ****Prometheus**** / [Exemplars for Histograms and Monotonic sums](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#exemplars-1)                                                                             | `-` | `true` |  |
| ****Prometheus**** / [`target_info` metric from Resource](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/prometheus_and_openmetrics.md#resource-attributes-1)                                                                            | `+` | `true` | [PrometheusWriter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala#L141-L149) |

## OpenCensus Compatibility

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| [Trace Bridge](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/opencensus.md#trace-bridge) | `-` |  |  |
| [Metric Bridge](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/opencensus.md#metrics--stats) | `-` |  |  |

## OpenTracing Compatibility

| Item | Status | Optional | Evidence |
| --- | --- | --- | --- |
| [Create OpenTracing Shim](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/opentracing.md#create-an-opentracing-tracer-shim) | `-` |  |  |
| [Tracer](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/opentracing.md#tracer-shim) | `-` |  |  |
| [Span](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/opentracing.md#span-shim) | `-` |  |  |
| [SpanContext](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/opentracing.md#spancontext-shim) | `-` |  |  |
| [ScopeManager](@OTEL_SPEC_GITHUB_URL@/specification/compatibility/opentracing.md#scopemanager-shim) | `-` |  |  |
| Error mapping for attributes/events | `-` |  |  |
| Migration to OpenTelemetry guide | `-` |  |  |
