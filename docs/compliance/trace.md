# Trace Compliance (SDK)

This file tracks compliance for the OpenTelemetry trace specification in this repository.

## Scope

- This repository implements the SDK. The Trace API types live in the external `otel4s-core-*` modules.
- Trace API-specific specs (e.g. `trace/api.md`, `trace/exceptions.md`) are tracked as external dependencies here.

## Module mapping

- SDK core: `sdk/trace`
- SDK exporters: `sdk-exporter/trace`
- Context propagation: `sdk/trace/context/propagation`
- Contrib (non-core): `sdk-contrib/aws/xray`, `sdk-contrib/aws/xray-propagator`

## trace/sdk.md checklist

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| TracerProvider | Builder supports id generator, resource, span limits, sampler, propagators, processors | Compliant | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkTracerProvider.scala` |  |
| TracerProvider | Default config (random ids, default resource, default span limits, parent-based always-on sampler) | Compliant | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkTracerProvider.scala` |  |
| TracerProvider | Shutdown + ForceFlush on provider (invoke processors) | Partial | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/TracerProviderAutoConfigure.scala`, `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/processor/BatchSpanProcessor.scala` | Processor lifecycles are Resource-managed; no explicit shutdown/forceFlush API on provider |
| Span limits | Span limits type + builder with defaults | Compliant | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SpanLimits.scala` | Defaults match spec (128 limits; unlimited value length) |
| Span limits | Env/config auto-config for span limits | Compliant | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SpanLimitsAutoConfigure.scala` |  |
| Id generator | Default random ids | Compliant | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/IdGenerator.scala` |  |
| Id generator | Custom IdGenerator support | Compliant | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkTracerProvider.scala` |  |
| Sampling | Sampler interface + parent-based + trace-id-ratio | Partial | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/samplers/` | No ProbabilitySampler/AlwaysRecord/CompositeSampler; no warnings when TraceIdRatioBased used as child sampler |
| Span processor | SpanProcessor interface with onStart/onEnd + forceFlush | Compliant | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/processor/SpanProcessor.scala` |  |
| Span processor | OnEnding hook | Partial | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/processor/SpanProcessor.scala` | `onEnd` exists with `SpanData` snapshot; no distinct pre-end OnEnding hook |
| Span processor | Shutdown support | Partial | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/processor/BatchSpanProcessor.scala` | Batch processor finalizer flushes (`exportAll`); no explicit shutdown/forceFlush API |
| Span processor | Simple + Batch processors | Compliant | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/processor/` |  |
| Span exporter | SpanExporter interface + no-op + composite | Partial | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/exporter/SpanExporter.scala` | Spec includes shutdown + forceFlush; only `flush` is present |
| Span exporter | Shutdown + ForceFlush methods | Partial | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/exporter/SpanExporter.scala`, `sdk-exporter/trace/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/trace/autoconfigure/OtlpSpanExporterAutoConfigure.scala` | Lifecycle handled via Resource in concrete exporters; interface lacks shutdown/forceFlush |
| Span exporter | Stdout exporter | Partial | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/exporter/ConsoleSpanExporter.scala` | Doc comment does not note format is unspecified |
| Span lifecycle | Span creation/ending via SdkSpanBuilder + SdkSpanBackend | Partial | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala`, `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala` | SpanId generated before ShouldSample (spec order is ShouldSample then SpanId) |
| Span limits | Logging when limits drop data | Not Implemented | `sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/data/LimitedData.scala` | No once-per-span log when attributes/events/links are dropped |

## trace/tracestate-handling.md checklist

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| TraceState propagation | W3C TraceContext encode/decode | Partial | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/W3CTraceContextPropagator.scala` | No OpenTelemetry `ot` entry handling or validation (key uniqueness/length) |
| TraceState propagation | OpenTelemetry `ot` sub-key handling (th/rv) | Not Implemented | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/W3CTraceContextPropagator.scala` | No utilities to set/merge `ot` entry |

## trace/tracestate-probability-sampling.md checklist

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| Consistent probability sampling | TraceState update hooks in sampling results | Partial | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/samplers/SamplingResult.scala` | Updater exists but no ProbabilitySampler/CompositeSampler or rv/th handling |

## trace/sdk_exporters

| Spec | Status | Evidence | Notes |
| --- | --- | --- | --- |
| stdout.md | Partial | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/exporter/ConsoleSpanExporter.scala` | Verify output format requirements | 
| zipkin.md | Not Applicable |  | No Zipkin exporter in this repository | 

## trace/api.md checklist (external: otel4s-core)

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| TracerProvider | Global default TracerProvider access | Not Implemented | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/TracerProvider.scala` | No global/default provider API found |
| TracerProvider | Get a Tracer accepts name + version + schema_url + attributes | Partial | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/TracerProvider.scala`, `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/TracerBuilder.scala` | Attributes parameter not supported |
| TracerProvider | Invalid name handling (empty string -> fallback + log) | Not Implemented | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/TracerProvider.scala` | No validation/logging for empty name |
| Tracer | Enabled API | Not Implemented | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala` | No `Enabled`/`isEnabled` API on tracer |
| SpanContext | TraceId/SpanId hex + binary access | Compliant | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/SpanContext.scala` | `traceId`/`spanId` (bytes) and `traceIdHex`/`spanIdHex` |
| SpanContext | TraceFlags sampled + random flag exposure | Partial | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/TraceFlags.scala` | Only sampled flag exposed; no random flag API |
| TraceState | get/add/update/delete with validation + error handling | Partial | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/TraceState.scala` | Invalid inputs are ignored (no error signaling) |
| Span | IsRecording API | Not Implemented | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/Span.scala` | No `isRecording`/`IsRecording` method |
| Span | Creation only via Tracer; parent must be Context only | Partial | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/SpanBuilder.scala` | Allows explicit parent `SpanContext` |
| Span | Wrap SpanContext into non-recording Span | Not Implemented | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/Span.scala` | No public API to wrap SpanContext |
| Span | Set attributes / add event / add link / set status / update name / end / record exception | Compliant | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/Span.scala`, `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala-2/org/typelevel/otel4s/trace/SpanMacro.scala` | Matches required operations (except IsRecording) |
| Concurrency | TracerProvider/Tracer/Span methods safe to call concurrently | Partial | `/Users/maksim/projects/oss/public/otel4s/docs/instrumentation/tracing.md` | Documented; not verified by tests |
| No-SDK behavior | No-op API preserves parent SpanContext for propagation | Not Implemented | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala`, `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/Span.scala` | No public API to wrap SpanContext; `Tracer.noop` always uses `SpanContext.invalid` |

## trace/api.md context interaction checklist (external: otel4s-core)

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| Context interaction | Extract Span from Context | Partial | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/TraceScope.scala` | TraceScope exposes `current` SpanContext only; no public API to extract Span |
| Context interaction | Combine Span with Context to create new Context | Partial | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/TraceScope.scala` | `childScope`/`withContext` are internal; no explicit API to attach Span to Context |
| Implicit context | Get current span from implicit context | Partial | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala` | `currentSpanContext` and `currentSpanOrNoop` exist, but not a direct Context extraction API |
| Implicit context | Set current span into implicit context | Partial | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala` | `childScope`/`rootScope`/`noopScope` operate on SpanContext; no public Context-combine API |
| Context API | Generic Context storage only (no trace-specific helpers) | Not Implemented | `/Users/maksim/projects/oss/public/otel4s-sdk/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/context/Context.scala`, `/Users/maksim/projects/oss/public/otel4s/core/common/src/main/scala/org/typelevel/otel4s/context/Contextual.scala` | No helpers to extract/attach Span or SpanContext outside trace module |

## trace/exceptions.md checklist

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| Exception event | Event name is `exception` | Compliant | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/data/EventData.scala` | `ExceptionEventName = "exception"` |
| Exception attrs | `exception.type`, `exception.message`, `exception.stacktrace` | Compliant | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/data/EventData.scala` | Uses `ExceptionAttributes.*` |
| RecordException API | Optional extra attributes override defaults | Compliant | `sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/data/EventData.scala` | `attributes.prependAll(exceptionAttributes)` keeps user attrs last |
| Unhandled exception guidance | Record exception + set status Error on unhandled | Partial | `/Users/maksim/projects/oss/public/otel4s/core/trace/src/main/scala/org/typelevel/otel4s/trace/SpanFinalizer.scala` | `reportAbnormal` records exception + sets Error; API does not enforce “only if unhandled” |
