# Baggage

This file tracks compliance for the OpenTelemetry baggage specification in this repository.

## Scope

- Baggage API types live in the external `otel4s-core-*` modules.
- This repository provides SDK-side context integration and propagators.

## Module mapping

- Core API: [baggage](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage)
- SDK context integration: [baggage](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage)
- SDK context keys: [SdkContextKeys.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkContextKeys.scala#L40)
- SDK propagators: [propagation](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation)

## [baggage/api.md](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md) checklist

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| Baggage container | [Immutable container of name/value pairs](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#baggage-api) | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L200-L204), [BaggageSuite.scala](@OTEL4S_GITHUB_URL@/core/common/src/test/scala/org/typelevel/otel4s/baggage/BaggageSuite.scala#L75-L80) | `updated`/`removed` return new instances |
| Names/values | [Accept any UTF-8, case-sensitive; do not restrict names](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#get-all-values) | Partial | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L200-L201) | No validation; UTF-8 acceptance not explicitly enforced or documented |
| Get value | [API to get value by name (null/None if absent)](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#get-value) | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L197-L198), [BaggageSuite.scala](@OTEL4S_GITHUB_URL@/core/common/src/test/scala/org/typelevel/otel4s/baggage/BaggageSuite.scala#L60-L61) | `get` returns `Option` |
| Get all values | [API to enumerate all entries (order not significant)](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#get-all-values) | Compliant | [BaggageSuite.scala](@OTEL4S_GITHUB_URL@/core/common/src/test/scala/org/typelevel/otel4s/baggage/BaggageSuite.scala#L57-L60), [BaggageSuite.scala](@OTEL4S_GITHUB_URL@/core/common/src/test/scala/org/typelevel/otel4s/baggage/BaggageSuite.scala#L69-L73) | `asMap` |
| Set value | [API to set name/value (optional metadata)](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#set-value) | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L200-L201), [BaggageSuite.scala](@OTEL4S_GITHUB_URL@/core/common/src/test/scala/org/typelevel/otel4s/baggage/BaggageSuite.scala#L55-L57), [BaggageSuite.scala](@OTEL4S_GITHUB_URL@/core/common/src/test/scala/org/typelevel/otel4s/baggage/BaggageSuite.scala#L75-L77) | Overloads support optional metadata and update by key. |
| Remove value | [API to remove name/value](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#remove-value) | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L203-L204), [BaggageSuite.scala](@OTEL4S_GITHUB_URL@/core/common/src/test/scala/org/typelevel/otel4s/baggage/BaggageSuite.scala#L86-L91) | Removal API is exposed and implemented by map key removal. |
| No-SDK behavior | [API functional without SDK](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#baggage-api) | Partial | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L200-L204), [BaggageManager.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/BaggageManager.scala#L72-L80) | `Baggage` is pure; `BaggageManager.noop` exists; propagation requires SDK |
| Context interaction | [Extract Baggage from Context](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#context-interaction) | Not Implemented | [SdkBaggageManager.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage/SdkBaggageManager.scala#L31-L33), [SdkBaggageManager.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage/SdkBaggageManager.scala#L51-L52) | No explicit Context API in core for baggage extract/attach |
| Context interaction | [Insert Baggage into Context](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#context-interaction) | Not Implemented | [SdkBaggageManager.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage/SdkBaggageManager.scala#L34-L38) | No explicit Context API in core for baggage extract/attach |
| Implicit context | [Get current Baggage from implicit Context](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#clear-baggage-in-the-context) | Partial | [SdkBaggageManager.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage/SdkBaggageManager.scala#L31-L33), [SdkBaggageManager.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage/SdkBaggageManager.scala#L40-L42) | Provided via `BaggageManager` + `LocalContext` |
| Implicit context | [Set current Baggage into implicit Context](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#clear-baggage-in-the-context) | Partial | [SdkBaggageManager.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage/SdkBaggageManager.scala#L33-L38) | `scope`/`local` exist; no explicit Context API |
| Clear baggage | [Ability to clear all baggage from Context](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#clear-baggage-in-the-context) | Partial | [SdkBaggageManager.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage/SdkBaggageManager.scala#L32-L35) | Use `scope(Baggage.empty)`; no explicit helper |
| Propagation | [W3C Baggage TextMapPropagator available](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#propagation) | Compliant | [W3CBaggagePropagator.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/W3CBaggagePropagator.scala#L43-L50), [W3CBaggagePropagator.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/W3CBaggagePropagator.scala#L55-L63) | Included in default propagators |
| Conflict resolution | [New entry replaces existing same name](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#conflict-resolution) | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L200-L201), [BaggageSuite.scala](@OTEL4S_GITHUB_URL@/core/common/src/test/scala/org/typelevel/otel4s/baggage/BaggageSuite.scala#L75-L80) | `Map.updated` overwrites |

## Cross-cutting specs

- Context propagation requirements are tracked in [context-propagation.md](context-propagation.md).
