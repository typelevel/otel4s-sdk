# Metrics Compliance (SDK)

This file tracks compliance for the OpenTelemetry metrics specification in this repository.

## Scope

- This repository implements the SDK. The Metrics API types live in the external `otel4s-core-*` modules.
- Metrics API-specific specs (e.g. `metrics/api.md`) are tracked as external dependencies here.

## Module mapping

- SDK core: `sdk/metrics`
- SDK exporters: `sdk-exporter/metrics`, `sdk-exporter/prometheus`
- SDK data model: `sdk/metrics/data`

## metrics/sdk.md checklist

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| MeterProvider | Builder supports resource, views, readers, producers | Compliant | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala` |  |
| MeterProvider | Default config (default resource, no readers/producers, noop lookup) | Compliant | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala` |  |
| Meter | Noop meter when no readers configured | Compliant | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala` |  |
| MeterProvider | ForceFlush / Shutdown support | Partial | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/autoconfigure/MeterProviderAutoConfigure.scala` | Provider lifecycle is Resource-managed; no explicit shutdown/forceFlush API in core interface |
| Views | View + selector registry | In Progress | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/`, `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/MeterSharedState.scala` | Validate match rules and warnings vs spec | 
| Aggregation | Aggregation + selectors + temporality selectors | In Progress | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/aggregation/`, `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/` | Validate defaults | 
| Attribute limits | Attributes are exempt from limits | Not Applicable |  | Spec states metric attributes are exempt from attribute limits | 
| Cardinality limits | Default 2000 + overflow attribute + warning | Compliant | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/CardinalityLimitSelector.scala`, `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/SynchronousStorage.scala`, `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/AsynchronousStorage.scala` | Uses `otel.metric.overflow` attribute on overflow | 
| Exemplar | Exemplar data + filters + reservoirs | In Progress | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exemplar/`, `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/ExemplarData.scala` | Default filter is `trace_based` via autoconfigure | 
| Exemplar | Custom ExemplarReservoir per-view configurability | Partial | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/view/View.scala` | View does not expose reservoir configuration | 
| MetricReader | Interface + periodic reader | Partial | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/MetricReader.scala`, `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/PeriodicMetricReader.scala` | Lifecycle is Resource-managed; no explicit shutdown API; forceFlush does not call exporter ForceFlush | 
| MetricReader | Prevent registration on multiple MeterProviders | Partial | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/PeriodicMetricReader.scala` | Logs warning but does not error | 
| MetricExporter | Push/Pull interfaces + noop | Partial | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/MetricExporter.scala` | Lifecycle is Resource-managed; missing ForceFlush / Shutdown on exporter interface | 
| MetricProducer | Interface + registration | In Progress | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/MetricProducer.scala` |  |
| MetricFilter | Filtering support | Blocked |  | No MetricFilter implementation found | 
| Temporality | Delta/Cumulative collection semantics | In Progress | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/SynchronousStorage.scala`, `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/AsynchronousStorage.scala` | Validate start/end timestamp rules per reader | 
| Numerical limits | Handling for NaN/Inf, etc. | Partial | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/SynchronousStorage.scala` | Drops NaN for synchronous double values; no explicit Inf handling | 

## metrics/data-model.md checklist

| Area | Requirement | Status | Evidence | Notes |
| --- | --- | --- | --- | --- |
| Metric data types | MetricData, Points, Temporality | In Progress | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/` | Validate mapping to spec data model | 
| Point kinds | Sum/Gauge/Histogram | Compliant | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/MetricPoints.scala`, `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/PointData.scala` |  |
| Point kinds | ExponentialHistogram | Missing |  | No exponential histogram support in data model | 
| Point kinds | Summary (legacy) | Missing |  | No summary support in data model | 
| Exemplars | Exemplar data and trace context | In Progress | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/ExemplarData.scala` | Verify required fields | 
| Data point flags | No-recorded-value flag | Missing |  | No flags representation in PointData | 
| Temporality | Delta/Cumulative semantics | Compliant | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/SynchronousStorage.scala`, `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/storage/AsynchronousStorage.scala`, `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/exporter/RegisteredReader.scala`, `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/SdkMeterProvider.scala` | Per-reader last-collect timestamps used for delta; cumulative keeps fixed start time | 
| Time windows | Start/End timestamps per point | In Progress | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/data/TimeWindow.scala`, `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/internal/MeterSharedState.scala` | End time = collect time; verify mapping to TimeUnixNano | 
| Resets/Gaps/Overlap | Handling | Missing |  | No explicit reset-gap-overlap handling observed in SDK data model or storage | 

## metrics/sdk_exporters

| Spec | Status | Evidence | Notes |
| --- | --- | --- | --- |
| stdout.md | Partial | `sdk/metrics/src/main/scala/org/typelevel/otel4s/sdk/metrics/exporter/ConsoleMetricExporter.scala` | Verify output format requirements | 
| in-memory.md | TBD |  | Locate in-memory exporter if present | 
| otlp.md | Partial | `sdk-exporter/metrics/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/metrics/OtlpMetricExporter.scala`, `sdk-exporter/metrics/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/metrics/autoconfigure/OtlpMetricExporterAutoConfigure.scala`, `sdk-exporter/common/src/main/scala/org/typelevel/otel4s/sdk/exporter/otlp/autoconfigure/OtlpClientAutoConfigure.scala` | OTLP protocol config supported; missing env config for temporality preference + default histogram aggregation | 
| prometheus.md | Partial | `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusMetricExporter.scala`, `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusHttpRoutes.scala`, `sdk-exporter/prometheus/src/main/scala/org/typelevel/otel4s/sdk/exporter/prometheus/PrometheusWriter.scala` | Pull exporter with text format 0.0.4, cumulative temporality; content negotiation is minimal (text/* only), no translation_strategy config | 
