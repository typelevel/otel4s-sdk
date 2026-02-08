# Baggage Compliance (API + SDK)

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
| Baggage container | Immutable container of name/value pairs | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala) | `updated`/`removed` return new instances |
| Names/values | Accept any UTF-8, case-sensitive; do not restrict names | Partial | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala) | No validation; UTF-8 acceptance not explicitly enforced or documented |
| Get value | API to get value by name (null/None if absent) | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala) | `get` returns `Option` |
| Get all values | API to enumerate all entries (order not significant) | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala) | `asMap` |
| Set value | API to set name/value (optional metadata) | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala) | `updated` overloads with metadata |
| Remove value | API to remove name/value | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala) | `removed` |
| No-SDK behavior | API functional without SDK | Partial | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala), [BaggageManager.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/BaggageManager.scala) | `Baggage` is pure; `BaggageManager.noop` exists; propagation requires SDK |
| Context interaction | Extract Baggage from Context | Not Implemented |  | No explicit Context API in core for baggage extract/attach |
| Context interaction | Insert Baggage into Context | Not Implemented |  | No explicit Context API in core for baggage extract/attach |
| Implicit context | Get current Baggage from implicit Context | Partial | [BaggageManager.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/BaggageManager.scala), [SdkBaggageManager.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage/SdkBaggageManager.scala) | Provided via `BaggageManager` + `LocalContext` |
| Implicit context | Set current Baggage into implicit Context | Partial | [BaggageManager.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/BaggageManager.scala), [SdkBaggageManager.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage/SdkBaggageManager.scala) | `scope`/`local` exist; no explicit Context API |
| Clear baggage | Ability to clear all baggage from Context | Partial | [SdkBaggageManager.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/common/shared/src/main/scala/org/typelevel/otel4s/sdk/baggage/SdkBaggageManager.scala) | Use `scope(Baggage.empty)`; no explicit helper |
| Propagation | W3C Baggage TextMapPropagator available | Compliant | [W3CBaggagePropagator.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/trace/src/main/scala/org/typelevel/otel4s/sdk/trace/context/propagation/W3CBaggagePropagator.scala) | Included in default propagators |
| Conflict resolution | New entry replaces existing same name | Compliant | [Baggage.scala](@OTEL4S_GITHUB_URL@/core/common/src/main/scala/org/typelevel/otel4s/baggage/Baggage.scala) | `Map.updated` overwrites |

