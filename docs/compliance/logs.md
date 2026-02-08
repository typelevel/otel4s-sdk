# Logs

This file tracks compliance for the OpenTelemetry logs specification in this repository.

## Scope

- This repository implements the SDK. The Logs API types live in the external `otel4s-core-*` modules.
- Logs API-specific specs (e.g. `logs/api.md`) are tracked as external dependencies here.

## Module mapping

- SDK core: [logs](@OTEL4S_SDK_GITHUB_URL@/sdk/logs)
- SDK exporters: [logs](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/logs)

## logs/sdk.md checklist

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| LoggerProvider | [Builder supports resource, processors, limits](@OTEL_SPEC_GITHUB_URL@/specification/logs/sdk.md#loggerprovider) | Compliant | [SdkLoggerProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/SdkLoggerProvider.scala#L41) | Exporters configured via processors/autoconfigure |
| LoggerProvider | [Default config and no-op behavior](@OTEL_SPEC_GITHUB_URL@/specification/logs/sdk.md#loggerprovider) | Compliant | [SdkLoggerProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/SdkLoggerProvider.scala#L41) | No processors => LoggerProvider.noop |
| LogRecordProcessor | [Interface + forceFlush](@OTEL_SPEC_GITHUB_URL@/specification/logs/sdk.md#logrecordprocessor) | Compliant | [LogRecordProcessor.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/processor/LogRecordProcessor.scala#L36) |  |
| LogRecordProcessor | [Simple + Batch processors](@OTEL_SPEC_GITHUB_URL@/specification/logs/sdk.md#logrecordprocessor) | Compliant | [SimpleLogRecordProcessor.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/processor/SimpleLogRecordProcessor.scala#L39), [BatchLogRecordProcessor.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/processor/BatchLogRecordProcessor.scala#L49) |  |
| LogRecordProcessor | [Shutdown support](@OTEL_SPEC_GITHUB_URL@/specification/logs/sdk.md#logrecordprocessor) | Partial | [BatchLogRecordProcessor.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/processor/BatchLogRecordProcessor.scala#L49), [SimpleLogRecordProcessor.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/processor/SimpleLogRecordProcessor.scala#L39) | Processor lifecycles are Resource-managed; no explicit shutdown API on processor interface |
| LogExporter | [Interface + no-op + composite](@OTEL_SPEC_GITHUB_URL@/specification/logs/sdk.md#logrecordexporter) | Partial | [LogRecordExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/exporter/LogRecordExporter.scala#L36) | Missing shutdown API; only flush |
| LogRecord limits | [Attribute/value limits + drop behavior](@OTEL_SPEC_GITHUB_URL@/specification/logs/sdk.md#logrecord-limits) | Compliant | [LogRecordLimits.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/LogRecordLimits.scala#L30), [SdkLogRecordBuilder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/SdkLogRecordBuilder.scala#L40) | Uses LimitedData to enforce limits |
| LogRecord data model | [Mapping to spec fields](@OTEL_SPEC_GITHUB_URL@/specification/logs/sdk.md#additional-logrecord-interfaces) | Partial | [LogRecordData.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/data/LogRecordData.scala#L44), [LogsProtoEncoder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/LogsProtoEncoder.scala#L43) | EventName/TraceFlags encoded in OTLP; severity mapping depends on core |

## logs/data-model.md checklist

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| Log data types | [LogRecord, severity, attributes, timestamps](@OTEL_SPEC_GITHUB_URL@/specification/logs/data-model.md#log-and-event-record-definition) | Partial | [LogRecordData.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/data/LogRecordData.scala#L44), [LogsProtoEncoder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/LogsProtoEncoder.scala#L43) | Uses FiniteDuration and encodes to nanos; TraceFlags encoded in OTLP |
| Body types | [AnyValue mapping](@OTEL_SPEC_GITHUB_URL@/specification/logs/data-model.md#field-body) | Compliant | [LogRecordData.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/data/LogRecordData.scala#L44), [LogsProtoEncoder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/LogsProtoEncoder.scala#L43) | SDK uses `AnyValue` for body and OTLP encoding covers scalar/array/map/bytes/empty variants |

## logs/sdk_exporters

| Spec | Status | Evidence | Notes |
| --- | --- | --- | --- |
| stdout.md | Partial | [ConsoleLogRecordExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/exporter/ConsoleLogRecordExporter.scala) | Verify output format requirements |
| otlp.md | Partial | [OtlpLogRecordExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/OtlpLogRecordExporter.scala), [LogsProtoEncoder.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/LogsProtoEncoder.scala), [OtlpLogRecordExporterAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/autoconfigure/OtlpLogRecordExporterAutoConfigure.scala) | OTLP protocol and autoconfigure are implemented; lifecycle remains Resource-managed |
