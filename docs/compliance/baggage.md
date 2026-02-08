# Baggage

This file tracks compliance for the OpenTelemetry baggage specification in this repository.

## Scope

- Baggage API types live in the external `otel4s-core-*` modules.
- This repository provides SDK-side context integration and propagators.

## Module mapping

- Core API: [baggage](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage)
- SDK context integration: [baggage](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage)
- SDK context keys: [SdkContextKeys.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/SdkContextKeys.scala)
- SDK propagators: [propagation](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation)

## baggage/api.md checklist

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| Baggage container | [Immutable container of name/value pairs](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#baggage-api) | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L34) | `updated`/`removed` return new instances |
| Names/values | [Accept any UTF-8, case-sensitive; do not restrict names](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#get-all-values) | Partial | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L34) | No validation; UTF-8 acceptance not explicitly enforced or documented |
| Get value | [API to get value by name (null/None if absent)](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#get-value) | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L34) | `get` returns `Option` |
| Get all values | [API to enumerate all entries (order not significant)](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#get-all-values) | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L34) | `asMap` |
| Set value | [API to set name/value (optional metadata)](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#set-value) | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L34) | `updated` overloads with metadata |
| Remove value | [API to remove name/value](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#remove-value) | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L34) | `removed` |
| No-SDK behavior | [API functional without SDK](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#baggage-api) | Partial | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L34), [BaggageManager.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/BaggageManager.scala#L36) | `Baggage` is pure; `BaggageManager.noop` exists; propagation requires SDK |
| Context interaction | [Extract Baggage from Context](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#context-interaction) | Not Implemented | [BaggageManager.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/BaggageManager.scala#L36), [SdkBaggageManager.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage/SdkBaggageManager.scala#L26) | No explicit Context API in core for baggage extract/attach |
| Context interaction | [Insert Baggage into Context](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#context-interaction) | Not Implemented | [BaggageManager.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/BaggageManager.scala#L36), [SdkBaggageManager.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage/SdkBaggageManager.scala#L26) | No explicit Context API in core for baggage extract/attach |
| Implicit context | [Get current Baggage from implicit Context](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#clear-baggage-in-the-context) | Partial | [BaggageManager.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/BaggageManager.scala#L36), [SdkBaggageManager.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage/SdkBaggageManager.scala#L26) | Provided via `BaggageManager` + `LocalContext` |
| Implicit context | [Set current Baggage into implicit Context](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#clear-baggage-in-the-context) | Partial | [BaggageManager.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/BaggageManager.scala#L36), [SdkBaggageManager.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage/SdkBaggageManager.scala#L26) | `scope`/`local` exist; no explicit Context API |
| Clear baggage | [Ability to clear all baggage from Context](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#clear-baggage-in-the-context) | Partial | [SdkBaggageManager.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage/SdkBaggageManager.scala#L26) | Use `scope(Baggage.empty)`; no explicit helper |
| Propagation | [W3C Baggage TextMapPropagator available](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#propagation) | Compliant | [W3CBaggagePropagator.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/W3CBaggagePropagator.scala#L36) | Included in default propagators |
| Conflict resolution | [New entry replaces existing same name](@OTEL_SPEC_GITHUB_URL@/specification/baggage/api.md#conflict-resolution) | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala#L34) | `Map.updated` overwrites |
