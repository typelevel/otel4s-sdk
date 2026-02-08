# Logs Compliance (SDK)

This file tracks compliance for the OpenTelemetry logs specification in this repository.

## Scope

- This repository implements the SDK. The Logs API types live in the external `otel4s-core-*` modules.
- Logs API-specific specs (e.g. `logs/api.md`) are tracked as external dependencies here.

## Module mapping

- SDK core: `sdk/logs`
- SDK exporters: `sdk-exporter/logs`

## logs/sdk.md checklist

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| LoggerProvider | Builder supports resource, processors, limits | Compliant | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/SdkLoggerProvider.scala` | Exporters configured via processors/autoconfigure |
| LoggerProvider | Default config and no-op behavior | Compliant | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/SdkLoggerProvider.scala` | No processors => LoggerProvider.noop |
| LogRecordProcessor | Interface + forceFlush | Compliant | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/processor/LogRecordProcessor.scala` |  |
| LogRecordProcessor | Simple + Batch processors | Compliant | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/processor/SimpleLogRecordProcessor.scala`, `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/processor/BatchLogRecordProcessor.scala` |  |
| LogRecordProcessor | Shutdown support | Partial | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/processor/BatchLogRecordProcessor.scala`, `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/processor/SimpleLogRecordProcessor.scala` | Processor lifecycles are Resource-managed; no explicit shutdown API on processor interface |
| LogExporter | Interface + no-op + composite | Partial | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/exporter/LogRecordExporter.scala` | Missing shutdown API; only flush | 
| LogRecord limits | Attribute/value limits + drop behavior | Compliant | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/LogRecordLimits.scala`, `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/SdkLogRecordBuilder.scala` | Uses LimitedData to enforce limits | 
| LogRecord data model | Mapping to spec fields | Partial | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/data/LogRecordData.scala`, `sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/LogsProtoEncoder.scala` | EventName/TraceFlags encoded in OTLP; severity mapping depends on core | 

## logs/data-model.md checklist

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| Log data types | LogRecord, severity, attributes, timestamps | Partial | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/data/LogRecordData.scala`, `sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/LogsProtoEncoder.scala` | Uses FiniteDuration and encodes to nanos; TraceFlags encoded in OTLP | 
| Body types | AnyValue mapping | In Progress | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/data/LogRecordData.scala` | AnyValue comes from core |

## logs/sdk_exporters

| Spec | Status | Evidence | Notes |
| --- | --- | --- | --- |
| stdout.md | Partial | `sdk/logs/src/main/scala/org/typelevel/otel4s/sdk/logs/exporter/ConsoleLogRecordExporter.scala` | Verify output format requirements | 
| otlp.md | Partial | `sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/OtlpLogRecordExporter.scala`, `sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/LogsProtoEncoder.scala`, `sdk-exporter/logs/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/logs/autoconfigure/OtlpLogRecordExporterAutoConfigure.scala` | OTLP protocol config supported; LogRecord flags/name set | 
