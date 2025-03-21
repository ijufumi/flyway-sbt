val flywayVersion = "9.22.3"
val pluginVersion = "9.22.3.1"

val scala212Version = "2.12.20"
val scala213Version = "2.13.16"

lazy val supportedScalaVersions = Seq(scala212Version, scala213Version)

// ThisBuild / scalaVersion := scala212Version
ThisBuild / crossScalaVersions := supportedScalaVersions

ThisBuild / versionScheme := Some("early-semver")

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "flyway-sbt",
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
