val flywayVersion = "9.22.3"
val pluginVersion = "9.22.3"

val scala212Version = "2.12.18"

ThisBuild / scalaVersion := scala212Version

ThisBuild / versionScheme := Some("early-semver")
ThisBuild / publishMavenStyle := true

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "flyway-sbt",
    organization := "io.github.ijufumi",
    version := pluginVersion,
    libraryDependencies ++= Seq(
      "org.flywaydb" % "flyway-core" % flywayVersion,
    ),
    scalacOptions ++= Seq(
      "-deprecation",
      "-unchecked",
      "-Xfuture",
    ),
    Compile / doc / scalacOptions ++= {
      Seq(
        "-sourcepath",
        (LocalRootProject / baseDirectory).value.getAbsolutePath,
        "-doc-source-url",
        s"""https://github.com/ijufumi/flyway-sbt/tree/${sys.process
          .Process("git rev-parse HEAD")
          .lineStream_!
          .head}€{FILE_PATH}.scala""",
      )
    },
    scriptedLaunchOpts := {
      scriptedLaunchOpts.value ++
        Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false,
  )
