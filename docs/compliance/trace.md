# Trace Compliance (SDK)

This file tracks compliance for the OpenTelemetry trace specification in this repository.

## Scope

- This repository implements the SDK. The Trace API types live in the external `otel4s-core-*` modules.
- Trace API-specific specs (e.g. `trace/api.md`, `trace/exceptions.md`) are tracked as external dependencies here.

## Module mapping

- SDK core: [trace](@OTEL4S_SDK_GITHUB_URL@/sdk/trace)
- SDK exporters: [trace](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/trace)
- Context propagation: [propagation](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/context/propagation)
- Contrib (non-core): [xray](@OTEL4S_SDK_GITHUB_URL@/sdk-contrib/aws/xray), [xray-propagator](@OTEL4S_SDK_GITHUB_URL@/sdk-contrib/aws/xray-propagator)

## trace/sdk.md checklist

| Area | Requirement | Status | Evidence | Notes                                                                                                           |
| --- | --- | --- | --- |-----------------------------------------------------------------------------------------------------------------|
| TracerProvider | [Builder supports id generator, resource, span limits, sampler, propagators, processors](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#tracer-provider) | Compliant | [SdkTracerProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkTracerProvider.scala#L36) |                                                                                                                 |
| TracerProvider | [Default config (random ids, default resource, default span limits, parent-based always-on sampler)](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#tracer-provider) | Compliant | [SdkTracerProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkTracerProvider.scala#L36) |                                                                                                                 |
| TracerProvider | [Shutdown + ForceFlush on provider (invoke processors)](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#tracer-provider) | Partial | [TracerProviderAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/TracerProviderAutoConfigure.scala#L40), [BatchSpanProcessor.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/processor/BatchSpanProcessor.scala#L54) | Processor lifecycles are Resource-managed; no explicit shutdown/forceFlush API on provider                      |
| Span limits | [Span limits type + builder with defaults](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#span-limits) | Compliant | [SpanLimits.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SpanLimits.scala#L27) | Defaults match spec (128 limits; unlimited value length)                                                        |
| Span limits | [Env/config auto-config for span limits](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#span-limits) | Compliant | [SpanLimitsAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/autoconfigure/SpanLimitsAutoConfigure.scala#L42) |                                                                                                                 |
| Id generator | [Default random ids](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#idgenerator-randomness-1) | Compliant | [IdGenerator.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/IdGenerator.scala#L35) |                                                                                                                 |
| Id generator | [Custom IdGenerator support](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#idgenerator-randomness-1) | Compliant | [SdkTracerProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkTracerProvider.scala#L36) |                                                                                                                 |
| Sampling | [Sampler interface + parent-based + trace-id-ratio](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#sampling) | Partial | [samplers/](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/samplers/) | No ProbabilitySampler, AlwaysRecord, CompositeSampler; no warnings when TraceIdRatioBased used as child sampler |
| Span processor | [SpanProcessor interface with onStart/onEnd + forceFlush](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#span-processor) | Compliant | [SpanProcessor.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/processor/SpanProcessor.scala#L38) |                                                                                                                 |
| Span processor | [OnEnding hook](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#span-processor) | Partial | [SpanProcessor.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/processor/SpanProcessor.scala#L38) | `onEnd` exists with `SpanData` snapshot; no distinct pre-end OnEnding hook                                      |
| Span processor | [Shutdown support](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#span-processor) | Partial | [BatchSpanProcessor.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/processor/BatchSpanProcessor.scala#L54) | Batch processor finalizer flushes (`exportAll`); no explicit shutdown/forceFlush API                            |
| Span processor | [Simple + Batch processors](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#span-processor) | Compliant | [processor/](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/processor/) |                                                                                                                 |
| Span exporter | [SpanExporter interface + no-op + composite](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#go-spanexporter-interface) | Partial | [SpanExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/exporter/SpanExporter.scala#L37) | Spec includes shutdown + forceFlush; only `flush` is present                                                    |
| Span exporter | [Shutdown + ForceFlush methods](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#span-exporter) | Partial | [SpanExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/exporter/SpanExporter.scala#L37), [OtlpSpanExporterAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/trace/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/trace/autoconfigure/OtlpSpanExporterAutoConfigure.scala#L41) | Lifecycle handled via Resource in concrete exporters; interface lacks shutdown/forceFlush                       |
| Span exporter | [Stdout exporter](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#span-exporter) | Partial | [ConsoleSpanExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/exporter/ConsoleSpanExporter.scala#L30) | Doc comment does not note format is unspecified                                                                 |
| Span lifecycle | [Span creation/ending via SdkSpanBuilder + SdkSpanBackend](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#sdk-span-creation) | Partial | [SdkSpanBuilder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBuilder.scala#L48), [SdkSpanBackend.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkSpanBackend.scala#L67) | SpanId generated before ShouldSample (spec order is ShouldSample then SpanId)                                   |
| Span limits | [Logging when limits drop data](@OTEL_SPEC_GITHUB_URL@/specification/trace/sdk.md#span-limits) | Not Implemented | [LimitedData.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/data/LimitedData.scala#L36) | No once-per-span log when attributes/events/links are dropped                                                   |

## trace/tracestate-handling.md checklist

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| TraceState propagation | [W3C TraceContext encode/decode](@OTEL_SPEC_GITHUB_URL@/specification/trace/tracestate-handling.md#tracestate-handling) | Partial | [W3CTraceContextPropagator.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/W3CTraceContextPropagator.scala#L36) | No OpenTelemetry `ot` entry handling or validation (key uniqueness/length) |
| TraceState propagation | [OpenTelemetry `ot` sub-key handling (th/rv)](@OTEL_SPEC_GITHUB_URL@/specification/trace/tracestate-handling.md#tracestate-handling) | Not Implemented | [W3CTraceContextPropagator.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/W3CTraceContextPropagator.scala#L36) | No utilities to set/merge `ot` entry |

## trace/tracestate-probability-sampling.md checklist

| Area | Requirement | Status | Evidence | Notes                                                                        |
| --- | --- | --- | --- |------------------------------------------------------------------------------|
| Consistent probability sampling | [TraceState update hooks in sampling results](@OTEL_SPEC_GITHUB_URL@/specification/trace/tracestate-probability-sampling.md#tracestate-probability-sampling) | Partial | [SamplingResult.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/samplers/SamplingResult.scala#L30) | Updater exists but no ProbabilitySampler, CompositeSampler or rv/th handling |

## trace/sdk_exporters

| Status | Evidence | Notes |
| --- | --- | --- |
| stdout.md | Partial | [ConsoleSpanExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/exporter/ConsoleSpanExporter.scala) |
| zipkin.md | Not Applicable |  |

## trace/api.md checklist (external: otel4s-core)

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| TracerProvider | [Global default TracerProvider access](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracerprovider) | Not Implemented | [TracerProvider.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/TracerProvider.scala#L26) | No global/default provider API found |
| TracerProvider | [Get a Tracer accepts name + version + schema_url + attributes](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracerprovider) | Partial | [TracerProvider.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/TracerProvider.scala#L26), [TracerBuilder.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/TracerBuilder.scala#L24) | Attributes parameter not supported |
| TracerProvider | [Invalid name handling (empty string -> fallback + log)](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracerprovider) | Not Implemented | [TracerProvider.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/TracerProvider.scala#L26) | No validation/logging for empty name |
| Tracer | [Enabled API](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracerprovider) | Not Implemented | [Tracer.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala#L44) | No `Enabled`/`isEnabled` API on tracer |
| SpanContext | [TraceId/SpanId hex + binary access](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#spancontext) | Compliant | [SpanContext.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/SpanContext.scala#L33) | `traceId`/`spanId` (bytes) and `traceIdHex`/`spanIdHex` |
| SpanContext | [TraceFlags sampled + random flag exposure](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#spancontext) | Partial | [TraceFlags.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/TraceFlags.scala#L28) | Only sampled flag exposed; no random flag API |
| TraceState | [get/add/update/delete with validation + error handling](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#tracestate) | Partial | [TraceState.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/TraceState.scala#L40) | Invalid inputs are ignored (no error signaling) |
| Span | [IsRecording API](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#spancontext) | Not Implemented | [Span.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/Span.scala#L66) | No `isRecording`/`IsRecording` method |
| Span | [Creation only via Tracer; parent must be Context only](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#determining-the-parent-span-from-a-context) | Partial | [SpanBuilder.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/SpanBuilder.scala#L30) | Allows explicit parent `SpanContext` |
| Span | [Wrap SpanContext into non-recording Span](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#spancontext) | Not Implemented | [Span.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/Span.scala#L66) | No public API to wrap SpanContext |
| Span | [Set attributes / add event / add link / set status / update name / end / record exception](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#spancontext) | Compliant | [Span.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/Span.scala#L66), [SpanMacro.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala-2/org/typelevel/otel4s/trace/SpanMacro.scala#L23) | Matches required operations (except IsRecording) |
| Concurrency | [TracerProvider/Tracer/Span methods safe to call concurrently](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#concurrency) | Partial | [tracing.md](@OTEL4S_GITHUB_URL@/docs/instrumentation/tracing.md#L1) | Documented; not verified by tests |
| No-SDK behavior | [No-op API preserves parent SpanContext for propagation](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#behavior-of-the-api-in-the-absence-of-an-installed-sdk) | Not Implemented | [Tracer.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala#L44), [Span.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/Span.scala#L66) | No public API to wrap SpanContext; `Tracer.noop` always uses `SpanContext.invalid` |

## trace/api.md context interaction checklist (external: otel4s-core)

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| Context interaction | [Extract Span from Context](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#context-interaction) | Partial | [TraceScope.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/TraceScope.scala#L37) | TraceScope exposes `current` SpanContext only; no public API to extract Span |
| Context interaction | [Combine Span with Context to create new Context](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#context-interaction) | Partial | [TraceScope.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/TraceScope.scala#L37) | `childScope`/`withContext` are internal; no explicit API to attach Span to Context |
| Implicit context | [Get current span from implicit context](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#determining-the-parent-span-from-a-context) | Partial | [Tracer.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala#L44) | `currentSpanContext` and `currentSpanOrNoop` exist, but not a direct Context extraction API |
| Implicit context | [Set current span into implicit context](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#spancontext) | Partial | [Tracer.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/Tracer.scala#L44) | `childScope`/`rootScope`/`noopScope` operate on SpanContext; no public Context-combine API |
| Context API | [Generic Context storage only (no trace-specific helpers)](@OTEL_SPEC_GITHUB_URL@/specification/trace/api.md#context-interaction) | Not Implemented | [Context.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/context/Context.scala#L30), [Contextual.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/context/Contextual.scala#L20) | No helpers to extract/attach Span or SpanContext outside trace module |

## trace/exceptions.md checklist

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| Exception event | [Event name is `exception`](@OTEL_SPEC_GITHUB_URL@/specification/trace/exceptions.md#recording-an-exception) | Compliant | [EventData.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/data/EventData.scala#L36) | `ExceptionEventName = "exception"` |
| Exception attrs | [`exception.type`, `exception.message`, `exception.stacktrace`](@OTEL_SPEC_GITHUB_URL@/specification/trace/exceptions.md#recording-an-exception) | Compliant | [EventData.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/data/EventData.scala#L36) | Uses `ExceptionAttributes.*` |
| RecordException API | [Optional extra attributes override defaults](@OTEL_SPEC_GITHUB_URL@/specification/trace/exceptions.md#recording-an-exception) | Compliant | [EventData.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/data/EventData.scala#L36) | `attributes.prependAll(exceptionAttributes)` keeps user attrs last |
| Unhandled exception guidance | [Record exception + set status Error on unhandled](@OTEL_SPEC_GITHUB_URL@/specification/trace/exceptions.md#recording-an-exception) | Partial | [SpanFinalizer.scala](@OTEL4S_GITHUB_URL@/core/trace/src/main/scala/org/typelevel/otel4s/trace/SpanFinalizer.scala#L35) | `reportAbnormal` records exception + sets Error; API does not enforce “only if unhandled” |
