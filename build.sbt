val flywayVersion = "9.22.3"
val pluginVersion = "9.22.3.1"

val scala212Version = "2.12.20"
val scala3Version = "3.8.2"

lazy val supportedScalaVersions = Seq(scala212Version, scala3Version)

ThisBuild / crossScalaVersions := supportedScalaVersions

ThisBuild / versionScheme := Some("early-semver")

(pluginCrossBuild / sbtVersion) := {
  scalaBinaryVersion.value match {
    case "2.12" => "1.12.8"
    case _      => "2.0.0-RC9"
  }
}

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
    ),
    scalacOptions ++= {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, _)) => Seq("-Xfuture")
        case _ => Seq(
          "-Wconf:msg=is deprecated for wildcard arguments:s",
          "-Wconf:msg=is no longer supported for vararg splices:s",
        )
      }
    },
    Compile / unmanagedSourceDirectories += {
      val sourceDir = (Compile / sourceDirectory).value
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, n)) if n <= 12 => sourceDir / "scala-2.12"
        case _                       => sourceDir / "scala-3"
      }
    },
    Compile / doc / scalacOptions ++= {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, _)) =>
          Seq(
            "-sourcepath",
            (LocalRootProject / baseDirectory).value.getAbsolutePath,
            "-doc-source-url",
            s"""https://github.com/ijufumi/flyway-sbt/tree/${sys.process
              .Process("git rev-parse HEAD")
              .lineStream_!
              .head}€{FILE_PATH}.scala""",
          )
        case _ => Seq.empty
      }
    },
    scriptedLaunchOpts := {
      scriptedLaunchOpts.value ++
        Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
    },
    scriptedBufferLog := false,
  )
