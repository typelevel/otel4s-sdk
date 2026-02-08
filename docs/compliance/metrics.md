# Metrics Compliance (SDK)

This file tracks compliance for the OpenTelemetry metrics specification in this repository.

## Scope

- This repository implements the SDK. The Metrics API types live in the external `otel4s-core-*` modules.
- Metrics API-specific specs (e.g. `metrics/api.md`) are tracked as external dependencies here.

## Module mapping

- SDK core: [metrics](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics)
- SDK exporters: [metrics](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/metrics), [prometheus](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus)
- SDK data model: [data](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/data)

## metrics/sdk.md checklist

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| MeterProvider | Builder supports resource, views, readers, producers | Compliant | [SdkMeterProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala) |  |
| MeterProvider | Default config (default resource, no readers/producers, noop lookup) | Compliant | [SdkMeterProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala) |  |
| Meter | Noop meter when no readers configured | Compliant | [SdkMeterProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala) |  |
| MeterProvider | ForceFlush / Shutdown support | Partial | [MeterProviderAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/autoconfigure/MeterProviderAutoConfigure.scala) | Provider lifecycle is Resource-managed; no explicit shutdown/forceFlush API in core interface |
| Views | View + selector registry | In Progress | [view/](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/), [MeterSharedState.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/MeterSharedState.scala) | Validate match rules and warnings vs spec | 
| Aggregation | Aggregation + selectors + temporality selectors | In Progress | [aggregation/](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/aggregation/), [exporter/](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/) | Validate defaults | 
| Attribute limits | Attributes are exempt from limits | Not Applicable |  | Spec states metric attributes are exempt from attribute limits | 
| Cardinality limits | Default 2000 + overflow attribute + warning | Compliant | [CardinalityLimitSelector.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/CardinalityLimitSelector.scala), [SynchronousStorage.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/SynchronousStorage.scala), [AsynchronousStorage.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/AsynchronousStorage.scala) | Uses `otel.metric.overflow` attribute on overflow | 
| Exemplar | Exemplar data + filters + reservoirs | In Progress | [exemplar/](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/), [ExemplarData.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/ExemplarData.scala) | Default filter is `trace_based` via autoconfigure | 
| Exemplar | Custom ExemplarReservoir per-view configurability | Partial | [View.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/View.scala) | View does not expose reservoir configuration | 
| MetricReader | Interface + periodic reader | Partial | [MetricReader.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/MetricReader.scala), [PeriodicMetricReader.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/PeriodicMetricReader.scala) | Lifecycle is Resource-managed; no explicit shutdown API; forceFlush does not call exporter ForceFlush | 
| MetricReader | Prevent registration on multiple MeterProviders | Partial | [PeriodicMetricReader.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/PeriodicMetricReader.scala) | Logs warning but does not error | 
| MetricExporter | Push/Pull interfaces + noop | Partial | [MetricExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/MetricExporter.scala) | Lifecycle is Resource-managed; missing ForceFlush / Shutdown on exporter interface | 
| MetricProducer | Interface + registration | In Progress | [MetricProducer.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/MetricProducer.scala) |  |
| MetricFilter | Filtering support | Blocked |  | No MetricFilter implementation found | 
| Temporality | Delta/Cumulative collection semantics | In Progress | [SynchronousStorage.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/SynchronousStorage.scala), [AsynchronousStorage.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/AsynchronousStorage.scala) | Validate start/end timestamp rules per reader | 
| Numerical limits | Handling for NaN/Inf, etc. | Partial | [SynchronousStorage.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/SynchronousStorage.scala) | Drops NaN for synchronous double values; no explicit Inf handling | 

## metrics/data-model.md checklist

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| Metric data types | MetricData, Points, Temporality | In Progress | [data/](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/) | Validate mapping to spec data model | 
| Point kinds | Sum/Gauge/Histogram | Compliant | [MetricPoints.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/MetricPoints.scala), [PointData.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/PointData.scala) |  |
| Point kinds | ExponentialHistogram | Missing |  | No exponential histogram support in data model | 
| Point kinds | Summary (legacy) | Missing |  | No summary support in data model | 
| Exemplars | Exemplar data and trace context | In Progress | [ExemplarData.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/ExemplarData.scala) | Verify required fields | 
| Data point flags | No-recorded-value flag | Missing |  | No flags representation in PointData | 
| Temporality | Delta/Cumulative semantics | Compliant | [SynchronousStorage.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/SynchronousStorage.scala), [AsynchronousStorage.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/AsynchronousStorage.scala), [RegisteredReader.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/exporter/RegisteredReader.scala), [SdkMeterProvider.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala) | Per-reader last-collect timestamps used for delta; cumulative keeps fixed start time | 
| Time windows | Start/End timestamps per point | In Progress | [TimeWindow.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/TimeWindow.scala), [MeterSharedState.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/MeterSharedState.scala) | End time = collect time; verify mapping to TimeUnixNano | 
| Resets/Gaps/Overlap | Handling | Missing |  | No explicit reset-gap-overlap handling observed in SDK data model or storage | 

## metrics/sdk_exporters

| Spec | Status | Evidence | Notes |
| --- | --- | --- | --- |
| stdout.md | Partial | [ConsoleMetricExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/ConsoleMetricExporter.scala) | Verify output format requirements | 
| in-memory.md | TBD |  | Locate in-memory exporter if present | 
| otlp.md | Partial | [OtlpMetricExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/metrics/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/metrics/OtlpMetricExporter.scala), [OtlpMetricExporterAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/metrics/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/metrics/autoconfigure/OtlpMetricExporterAutoConfigure.scala), [OtlpClientAutoConfigure.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/common/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/autoconfigure/OtlpClientAutoConfigure.scala) | OTLP protocol config supported; missing env config for temporality preference + default histogram aggregation | 
| prometheus.md | Partial | [PrometheusMetricExporter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusMetricExporter.scala), [PrometheusHttpRoutes.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusHttpRoutes.scala), [PrometheusWriter.scala](@OTEL4S_SDK_GITHUB_URL@/sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala) | Pull exporter with text format 0.0.4, cumulative temporality; content negotiation is minimal (text/* only), no translation_strategy config | 
