import com.typesafe.tools.mima.core._

ThisBuild / tlBaseVersion := "0.15"

ThisBuild / organization := "org.typelevel"
ThisBuild / organizationName := "Typelevel"
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("rossabaker", "Ross A. Baker"),
  tlGitHubDev("iRevive", "Maksym Ochenashko")
)
ThisBuild / startYear := Some(2022)

// publish website from this branch
ThisBuild / tlSitePublishBranch := Some("main")

// VM runs out of memory when linking multiple targets concurrently, hence limit it
Global / concurrentRestrictions += Tags.limit(NativeTags.Link, 1)

lazy val scalaJSLinkerSettings = Def.settings(
  scalaJSLinkerConfig ~= (_.withESFeatures(
    _.withESVersion(org.scalajs.linker.interface.ESVersion.ES2018)
  )),
  Test / scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule)),
)

lazy val scalaNativeSettings = Def.settings(
  Test / nativeBrewFormulas ++= Set("s2n", "utf8proc"),
  Test / envVars ++= Map("S2N_DONT_MLOCK" -> "1"),
)

// the JS and SN artifacts could be quite large and exceed the CI disk space limit
lazy val artifactUploadSettings = Def.settings(
  githubWorkflowArtifactUpload := {
    val platform = crossProjectPlatform.?.value
    if (platform.contains(NativePlatform) || platform.contains(JSPlatform)) false
    else githubWorkflowArtifactUpload.value
  }
)

val Scala212 = "2.12.21"
val Scala213 = "2.13.18"
ThisBuild / crossScalaVersions := Seq(Scala213, "3.3.7")
ThisBuild / scalaVersion := Scala213 // the default Scala

ThisBuild / githubWorkflowBuildPreamble ++= nativeBrewInstallWorkflowSteps.value

ThisBuild / mergifyStewardConfig := None
ThisBuild / mergifyLabelPaths := Map(
  "module:sdk" -> file("sdk"),
  "module:sdk:exporter" -> file("sdk-exporter"),
  "module:sdk:contrib:aws" -> file("sdk-contrib/aws"),
  "documentation" -> file("docs")
)

ThisBuild / mergifyPrRules ++= Seq(
  MergifyPrRule(
    "Label metrics PRs",
    List(MergifyCondition.Custom("files~=/(metrics)/")),
    List(MergifyAction.Label(add = List("metrics")))
  ),
  MergifyPrRule(
    "Label trace PRs",
    List(MergifyCondition.Custom("files~=/(trace)/")),
    List(MergifyAction.Label(add = List("tracing")))
  ),
  MergifyPrRule(
    "Label logs PRs",
    List(MergifyCondition.Custom("files~=/(logs)/")),
    List(MergifyAction.Label(add = List("logs")))
  ),
  MergifyPrRule(
    "Label Scala Steward PRs",
    List(MergifyCondition.Custom("author=typelevel-steward[bot]")),
    List(MergifyAction.Label(add = List("dependencies")))
  )
)

val CatsVersion = "2.11.0"
val CatsEffectVersion = "3.6.3"
val CatsMtlVersion = "1.4.0"
val FS2Version = "3.12.2"
val MUnitVersion = "1.0.0"
val MUnitScalaCheckVersion = "1.0.0-M11"
val MUnitCatsEffectVersion = "2.1.0"
val MUnitDisciplineVersion = "2.0.0-M3"
val MUnitScalaCheckEffectVersion = "2.0.0-M2"
val OpenTelemetryVersion = "1.58.0"
val OpenTelemetryProtoVersion = "1.9.0-alpha"
val ScodecVersion = "1.1.38"
val VaultVersion = "3.6.0"
val Http4sVersion = "0.23.33"
val CirceVersion = "0.14.8"
val ScalaPBCirceVersion = "0.15.1"
val CaseInsensitiveVersion = "1.4.2"
val ScalaJavaTimeVersion = "2.6.0"
val ScribeVersion = "3.17.0"
val Otel4sVersion = "0.14.0"

lazy val scalaReflectDependency = Def.settings(
  libraryDependencies ++= {
    if (tlIsScala3.value) Nil
    else Seq("org.scala-lang" % "scala-reflect" % scalaVersion.value % Provided)
  }
)

lazy val munitDependencies = Def.settings(
  libraryDependencies ++= Seq(
    "org.scalameta" %%% "munit" % MUnitVersion % Test,
    "org.scalameta" %%% "munit-scalacheck" % MUnitScalaCheckVersion % Test,
    "org.typelevel" %%% "munit-cats-effect" % MUnitCatsEffectVersion % Test
  )
)

lazy val root = tlCrossRootProject
  .aggregate(
    `sdk-common`,
    `sdk-logs`,
    `sdk-logs-testkit`,
    `sdk-metrics`,
    `sdk-metrics-testkit`,
    `sdk-trace`,
    `sdk-trace-testkit`,
    `sdk-testkit`,
    sdk,
    `sdk-exporter-common`,
    `sdk-exporter-proto`,
    `sdk-exporter-logs`,
    `sdk-exporter-metrics`,
    `sdk-exporter-prometheus`,
    `sdk-exporter-trace`,
    `sdk-exporter`,
    `sdk-contrib-aws-resource`,
    `sdk-contrib-aws-xray`,
    `sdk-contrib-aws-xray-propagator`,
    benchmarks,
    examples,
    unidocs
  )
  .settings(name := "otel4s-sdk")

//
// SDK
//

lazy val `sdk-common` = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Full)
  .enablePlugins(BuildInfoPlugin)
  .in(file("sdk/common"))
  .settings(artifactUploadSettings)
  .settings(
    name := "otel4s-sdk-common",
    startYear := Some(2023),
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "otel4s-core-common" % Otel4sVersion,
      "org.typelevel" %%% "otel4s-semconv" % Otel4sVersion,
      "org.typelevel" %%% "cats-effect-kernel" % CatsEffectVersion,
      "org.typelevel" %%% "cats-mtl" % CatsMtlVersion,
      "org.typelevel" %%% "cats-laws" % CatsVersion % Test,
      "org.typelevel" %%% "otel4s-semconv-experimental" % Otel4sVersion % Test,
      "org.typelevel" %%% "discipline-munit" % MUnitDisciplineVersion % Test,
      "org.typelevel" %%% "cats-effect-testkit" % CatsEffectVersion % Test,
    ),
    buildInfoPackage := "org.typelevel.otel4s.sdk",
    buildInfoOptions += sbtbuildinfo.BuildInfoOption.PackagePrivate,
    buildInfoKeys := Seq[BuildInfoKey](
      version
    )
  )
  .settings(munitDependencies)
  .jsSettings(scalaJSLinkerSettings)

lazy val `sdk-logs` = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("sdk/logs"))
  .dependsOn(
    `sdk-common` % "compile->compile;test->test",
  )
  .settings(artifactUploadSettings)
  .settings(
    name := "otel4s-sdk-logs",
    startYear := Some(2025),
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "otel4s-core-logs" % Otel4sVersion,
      "org.typelevel" %%% "cats-effect" % CatsEffectVersion,
      "org.typelevel" %%% "cats-laws" % CatsVersion % Test,
      "org.typelevel" %%% "cats-effect-testkit" % CatsEffectVersion % Test,
      "org.typelevel" %%% "discipline-munit" % MUnitDisciplineVersion % Test,
      "org.typelevel" %%% "scalacheck-effect-munit" % MUnitScalaCheckEffectVersion % Test
    ),
  )
  .settings(munitDependencies)
  .jsSettings(scalaJSLinkerSettings)
  .jsSettings(
    libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % ScalaJavaTimeVersion,
  )

lazy val `sdk-logs-testkit` = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("sdk/logs-testkit"))
  .dependsOn(`sdk-logs`)
  .settings(artifactUploadSettings)
  .settings(
    name := "otel4s-sdk-logs-testkit",
    startYear := Some(2025)
  )

lazy val `sdk-metrics` = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("sdk/metrics"))
  .dependsOn(
    `sdk-common` % "compile->compile;test->test",
  )
  .settings(artifactUploadSettings)
  .settings(
    name := "otel4s-sdk-metrics",
    startYear := Some(2024),
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "otel4s-core-metrics" % Otel4sVersion,
      "org.typelevel" %%% "cats-effect" % CatsEffectVersion,
      "org.scodec" %%% "scodec-bits" % ScodecVersion,
      "org.typelevel" %%% "case-insensitive" % CaseInsensitiveVersion,
      "org.typelevel" %%% "cats-laws" % CatsVersion % Test,
      "org.typelevel" %%% "discipline-munit" % MUnitDisciplineVersion % Test,
      "org.typelevel" %%% "scalacheck-effect-munit" % MUnitScalaCheckEffectVersion % Test
    )
  )
  .settings(munitDependencies)
  .jsSettings(scalaJSLinkerSettings)

lazy val `sdk-metrics-testkit` =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("sdk/metrics-testkit"))
    .dependsOn(`sdk-metrics`)
    .settings(artifactUploadSettings)
    .settings(
      name := "otel4s-sdk-metrics-testkit",
      startYear := Some(2024)
    )

lazy val `sdk-trace` = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("sdk/trace"))
  .dependsOn(
    `sdk-common` % "compile->compile;test->test",
  )
  .settings(artifactUploadSettings)
  .settings(
    name := "otel4s-sdk-trace",
    startYear := Some(2023),
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "otel4s-core-trace" % Otel4sVersion,
      "org.typelevel" %%% "cats-effect" % CatsEffectVersion,
      "org.typelevel" %%% "cats-laws" % CatsVersion % Test,
      "org.typelevel" %%% "cats-effect-testkit" % CatsEffectVersion % Test,
      "org.typelevel" %%% "discipline-munit" % MUnitDisciplineVersion % Test,
      "org.typelevel" %%% "scalacheck-effect-munit" % MUnitScalaCheckEffectVersion % Test
    ),
  )
  .settings(munitDependencies)
  .jsSettings(scalaJSLinkerSettings)

lazy val `sdk-trace-testkit` =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("sdk/trace-testkit"))
    .settings(artifactUploadSettings)
    .dependsOn(`sdk-trace`)
    .settings(
      name := "otel4s-sdk-trace-testkit",
      startYear := Some(2024)
    )

lazy val `sdk-testkit` = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("sdk/testkit"))
  .dependsOn(`sdk-logs-testkit`, `sdk-metrics-testkit`, `sdk-trace-testkit`)
  .settings(artifactUploadSettings)
  .settings(
    name := "otel4s-sdk-testkit",
    startYear := Some(2024),
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "otel4s-core" % Otel4sVersion,
    )
  )

lazy val sdk = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("sdk/all"))
  .dependsOn(
    `sdk-common`,
    `sdk-logs` % "compile->compile;test->test",
    `sdk-metrics` % "compile->compile;test->test",
    `sdk-metrics-testkit` % Test,
    `sdk-trace` % "compile->compile;test->test",
    `sdk-trace-testkit` % Test
  )
  .settings(artifactUploadSettings)
  .settings(
    name := "otel4s-sdk",
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "otel4s-core" % Otel4sVersion,
    )
  )
  .settings(munitDependencies)
  .jsSettings(scalaJSLinkerSettings)

//
// SDK exporter
//

lazy val `sdk-exporter-proto` =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("sdk-exporter/proto"))
    .settings(artifactUploadSettings)
    .settings(
      name := "otel4s-sdk-exporter-proto",
      Compile / PB.protoSources += baseDirectory.value.getParentFile / "src" / "main" / "protobuf",
      Compile / PB.targets ++= Seq(
        scalapb.gen(grpc = false) -> (Compile / sourceManaged).value / "scalapb"
      ),
      Compile / PB.generate := {
        val files = (Compile / PB.generate).value

        files.filter(_.isFile).foreach { file =>
          val content = IO.read(file)

          // see: https://github.com/scalapb/ScalaPB/issues/1778
          val updated = content
            .replaceAll(
              """(?m)^object (\w+) extends _root_\.scalapb\.GeneratedEnumCompanion\[\w+\]""",
              "private[exporter] object $1 extends _root_.scalapb.GeneratedEnumCompanion[$1]"
            )
            .replaceAll(
              """(?m)^object (\w+) extends _root_\.scalapb\.GeneratedFileObject""",
              "private[exporter] object $1 extends _root_.scalapb.GeneratedFileObject"
            )

          IO.write(file, updated)
        }

        files
      },
      scalacOptions := {
        val opts = scalacOptions.value
        if (tlIsScala3.value) opts.filterNot(_ == "-Wvalue-discard") else opts
      },
      // We use open-telemetry protobuf spec to generate models
      // See https://scalapb.github.io/docs/third-party-protos/#there-is-a-library-on-maven-with-the-protos-and-possibly-generated-java-code
      libraryDependencies ++= Seq(
        "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
        "io.opentelemetry.proto" % "opentelemetry-proto" % OpenTelemetryProtoVersion % "protobuf-src" intransitive ()
      )
    )
    .jvmSettings(
      // scalafix settings to ensure there are no public classes in the module
      // run scalafix against generated sources
      Compile / ScalafixPlugin.autoImport.scalafix / unmanagedSources := (Compile / managedSources).value,
      // run scalafix only on scala 2.13
      scalafixOnCompile := !tlIsScala3.value,
      // read scalafix rules from a shared folder
      ScalafixConfig / sourceDirectory := {
        if (tlIsScala3.value) {
          (ScalafixConfig / sourceDirectory).value
        } else {
          baseDirectory.value.getParentFile / "src" / "scalafix"
        }
      },
      // required by scalafix rules
      libraryDependencies ++= {
        if (tlIsScala3.value) Nil
        else Seq("ch.epfl.scala" %% "scalafix-core" % _root_.scalafix.sbt.BuildInfo.scalafixVersion % ScalafixConfig)
      }
    )

lazy val `sdk-exporter-common` =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("sdk-exporter/common"))
    .dependsOn(
      `sdk-common` % "compile->compile;test->test",
      `sdk-exporter-proto`
    )
    .settings(artifactUploadSettings)
    .settings(
      name := "otel4s-sdk-exporter-common",
      startYear := Some(2023),
      libraryDependencies ++= Seq(
        "co.fs2" %%% "fs2-scodec" % FS2Version,
        "co.fs2" %%% "fs2-io" % FS2Version,
        "org.http4s" %%% "http4s-ember-client" % Http4sVersion,
        "org.http4s" %%% "http4s-circe" % Http4sVersion,
        "io.github.scalapb-json" %%% "scalapb-circe" % ScalaPBCirceVersion,
        "org.typelevel" %%% "cats-laws" % CatsVersion % Test,
        "org.typelevel" %%% "discipline-munit" % MUnitDisciplineVersion % Test,
        "io.circe" %%% "circe-generic" % CirceVersion % Test
      )
    )
    .jsSettings(scalaJSLinkerSettings)
    .nativeEnablePlugins(ScalaNativeBrewedConfigPlugin)
    .nativeSettings(scalaNativeSettings)
    .settings(munitDependencies)

lazy val `sdk-exporter-logs` =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("sdk-exporter/logs"))
    .enablePlugins(DockerComposeEnvPlugin)
    .dependsOn(
      `sdk-exporter-common` % "compile->compile;test->test",
      `sdk-logs` % "compile->compile;test->test"
    )
    .settings(artifactUploadSettings)
    .settings(
      name := "otel4s-sdk-exporter-logs",
      startYear := Some(2025),
      dockerComposeEnvFile := crossProjectBaseDirectory.value / "docker" / "docker-compose.yml",
      Test / scalacOptions ++= {
        // see https://github.com/circe/circe/issues/2162
        if (tlIsScala3.value) Seq("-Xmax-inlines", "64") else Nil
      }
    )
    .jsSettings(scalaJSLinkerSettings)
    .nativeEnablePlugins(ScalaNativeBrewedConfigPlugin)
    .nativeSettings(scalaNativeSettings)
    .settings(munitDependencies)

lazy val `sdk-exporter-metrics` =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("sdk-exporter/metrics"))
    .enablePlugins(DockerComposeEnvPlugin)
    .dependsOn(
      `sdk-exporter-common` % "compile->compile;test->test",
      `sdk-metrics` % "compile->compile;test->test"
    )
    .settings(artifactUploadSettings)
    .settings(
      name := "otel4s-sdk-exporter-metrics",
      startYear := Some(2024),
      dockerComposeEnvFile := crossProjectBaseDirectory.value / "docker" / "docker-compose.yml"
    )
    .jsSettings(scalaJSLinkerSettings)
    .nativeEnablePlugins(ScalaNativeBrewedConfigPlugin)
    .nativeSettings(scalaNativeSettings)
    .settings(munitDependencies)

lazy val `sdk-exporter-prometheus` =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("sdk-exporter/prometheus"))
    .dependsOn(
      `sdk-exporter-common` % "compile->compile;test->test",
      `sdk-metrics` % "compile->compile;test->test"
    )
    .settings(artifactUploadSettings)
    .settings(
      name := "otel4s-sdk-exporter-prometheus",
      startYear := Some(2024),
      libraryDependencies ++= Seq(
        "org.http4s" %%% "http4s-ember-server" % Http4sVersion
      ),
      mimaBinaryIssueFilters ++= Seq(
        // see #838
        ProblemFilters.exclude[DirectMissingMethodProblem](
          "org.typelevel.otel4s.sdk.exporter.prometheus.PrometheusMetricExporter#HttpServerBuilderImpl.*"
        ),
        ProblemFilters.exclude[IncompatibleResultTypeProblem](
          "org.typelevel.otel4s.sdk.exporter.prometheus.PrometheusMetricExporter#HttpServerBuilderImpl.*"
        ),
        ProblemFilters.exclude[ReversedMissingMethodProblem](
          "org.typelevel.otel4s.sdk.exporter.prometheus.PrometheusMetricExporter#HttpServerBuilder.withShutdownTimeout"
        )
      )
    )
    .jsSettings(scalaJSLinkerSettings)
    .nativeEnablePlugins(ScalaNativeBrewedConfigPlugin)
    .nativeSettings(scalaNativeSettings)
    .settings(munitDependencies)

lazy val `sdk-exporter-trace` =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("sdk-exporter/trace"))
    .enablePlugins(DockerComposeEnvPlugin)
    .dependsOn(
      `sdk-exporter-common` % "compile->compile;test->test",
      `sdk-trace` % "compile->compile;test->test"
    )
    .settings(artifactUploadSettings)
    .settings(
      name := "otel4s-sdk-exporter-trace",
      startYear := Some(2023),
      dockerComposeEnvFile := crossProjectBaseDirectory.value / "docker" / "docker-compose.yml",
      Test / scalacOptions ++= {
        // see https://github.com/circe/circe/issues/2162
        if (tlIsScala3.value) Seq("-Xmax-inlines", "64") else Nil
      }
    )
    .jsSettings(scalaJSLinkerSettings)
    .nativeEnablePlugins(ScalaNativeBrewedConfigPlugin)
    .nativeSettings(scalaNativeSettings)
    .settings(munitDependencies)

lazy val `sdk-exporter` = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .crossType(CrossType.Pure)
  .in(file("sdk-exporter/all"))
  .dependsOn(
    sdk,
    `sdk-exporter-common`,
    `sdk-exporter-logs`,
    `sdk-exporter-metrics`,
    `sdk-exporter-trace`
  )
  .settings(artifactUploadSettings)
  .settings(
    name := "otel4s-sdk-exporter"
  )

//
// SDK contrib modules
//

lazy val `sdk-contrib-aws-resource` =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("sdk-contrib/aws/resource"))
    .dependsOn(`sdk-common`)
    .settings(
      name := "otel4s-sdk-contrib-aws-resource",
      startYear := Some(2024),
      libraryDependencies ++= Seq(
        "co.fs2" %%% "fs2-io" % FS2Version,
        "io.circe" %%% "circe-parser" % CirceVersion,
        "org.http4s" %%% "http4s-ember-client" % Http4sVersion,
        "org.http4s" %%% "http4s-circe" % Http4sVersion,
        "org.http4s" %%% "http4s-dsl" % Http4sVersion % Test,
        "org.typelevel" %%% "otel4s-semconv-experimental" % Otel4sVersion % Test,
      )
    )
    .settings(artifactUploadSettings)
    .settings(munitDependencies)
    .jsSettings(scalaJSLinkerSettings)
    .nativeEnablePlugins(ScalaNativeBrewedConfigPlugin)
    .nativeSettings(scalaNativeSettings)

lazy val `sdk-contrib-aws-xray-propagator` =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("sdk-contrib/aws/xray-propagator"))
    .dependsOn(
      `sdk-trace` % "compile->compile;test->test",
    )
    .settings(
      name := "otel4s-sdk-contrib-aws-xray-propagator",
      startYear := Some(2024),
      libraryDependencies ++= Seq(
        "org.typelevel" %%% "otel4s-semconv-experimental" % Otel4sVersion % Test,
      )
    )
    .settings(artifactUploadSettings)
    .settings(munitDependencies)
    .jsSettings(scalaJSLinkerSettings)

lazy val `sdk-contrib-aws-xray` =
  crossProject(JVMPlatform, JSPlatform, NativePlatform)
    .crossType(CrossType.Pure)
    .in(file("sdk-contrib/aws/xray"))
    .dependsOn(`sdk-trace` % "compile->compile;test->test")
    .settings(
      name := "otel4s-sdk-contrib-aws-xray",
      startYear := Some(2024),
    )
    .settings(artifactUploadSettings)
    .settings(munitDependencies)
    .jsSettings(scalaJSLinkerSettings)

//
// Utility
//

lazy val benchmarks = project
  .enablePlugins(NoPublishPlugin)
  .enablePlugins(JmhPlugin)
  .in(file("benchmarks"))
  .dependsOn(sdk.jvm, `sdk-testkit`.jvm)
  .settings(
    name := "otel4s-benchmarks",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "otel4s-oteljava" % Otel4sVersion,
      "io.opentelemetry" % "opentelemetry-sdk-testing" % OpenTelemetryVersion
    )
  )

lazy val examples = project
  .enablePlugins(NoPublishPlugin)
  .in(file("examples"))
  .dependsOn(sdk.jvm, `sdk-exporter`.jvm, `sdk-exporter-prometheus`.jvm)
  .settings(
    name := "otel4s-sdk-examples",
    run / fork := true,
    javaOptions += "-Dotel.java.global-autoconfigure.enabled=true",
    envVars ++= Map(
      "OTEL_PROPAGATORS" -> "b3multi",
      "OTEL_SERVICE_NAME" -> "Trace Example"
    )
  )

lazy val docs = project
  .in(file("site"))
  .enablePlugins(TypelevelSitePlugin)
  .dependsOn(
    sdk.jvm,
    `sdk-exporter`.jvm,
    `sdk-exporter-prometheus`.jvm,
    `sdk-contrib-aws-resource`.jvm,
    `sdk-contrib-aws-xray`.jvm,
    `sdk-contrib-aws-xray-propagator`.jvm,
    `sdk-testkit`.jvm
  )
  .settings(
    libraryDependencies ++= Seq(
      "org.typelevel" %% "otel4s-instrumentation-metrics" % Otel4sVersion,
      "org.http4s" %% "http4s-client" % Http4sVersion,
      "org.http4s" %% "http4s-dsl" % Http4sVersion,
      "com.outr" %% "scribe" % ScribeVersion,
    ),
    run / fork := true,
    laikaConfig := {
      import laika.config.{ChoiceConfig, Selections, SelectionConfig}

      laikaConfig.value.withConfigValue(
        Selections(
          SelectionConfig(
            "build-tool",
            ChoiceConfig("sbt", "sbt"),
            ChoiceConfig("scala-cli", "Scala CLI")
          ).withSeparateEbooks,
          SelectionConfig(
            "sdk-options-source",
            ChoiceConfig("sbt", "sbt"),
            ChoiceConfig("scala-cli", "Scala CLI"),
            ChoiceConfig("shell", "Shell")
          ).withSeparateEbooks,
          SelectionConfig(
            "scala-version",
            ChoiceConfig("scala-2", "Scala 2"),
            ChoiceConfig("scala-3", "Scala 3")
          ).withSeparateEbooks,
          SelectionConfig(
            "sdk-entry-point",
            ChoiceConfig("traces", "SdkTraces"),
            ChoiceConfig("metrics", "SdkMetrics"),
            ChoiceConfig("sdk", "OpenTelemetrySDK")
          ).withSeparateEbooks
        )
      )
    }
  )

lazy val unidocs = project
  .in(file("unidocs"))
  .enablePlugins(TypelevelUnidocPlugin)
  .settings(
    name := "otel4s-docs",
    ScalaUnidoc / unidoc / unidocProjectFilter := inProjects(
      `sdk-common`.jvm,
      `sdk-logs`.jvm,
      `sdk-logs-testkit`.jvm,
      `sdk-metrics`.jvm,
      `sdk-metrics-testkit`.jvm,
      `sdk-trace`.jvm,
      `sdk-trace-testkit`.jvm,
      `sdk-testkit`.jvm,
      sdk.jvm,
      `sdk-exporter-common`.jvm,
      `sdk-exporter-logs`.jvm,
      `sdk-exporter-metrics`.jvm,
      `sdk-exporter-prometheus`.jvm,
      `sdk-exporter-trace`.jvm,
      `sdk-exporter`.jvm,
      `sdk-contrib-aws-resource`.jvm,
      `sdk-contrib-aws-xray`.jvm,
      `sdk-contrib-aws-xray-propagator`.jvm,
    )
  )
