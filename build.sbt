val flywayVersion = "5.0.3" // Remember to update this in docs/_config.yml and sbt-test/flyway-sbt/test{1|2}/build.sbt
val pluginVersion = "5.0.0-SNAPSHOT"

lazy val root = (project in file ("."))
    .settings(
      sbtPlugin := true,
      name := "flyway-sbt",
      scalaVersion := "2.12.4",
      organization := "org.flywaydb",
      version := pluginVersion,
      libraryDependencies ++= Seq(
        "org.flywaydb" % "flyway-core" % flywayVersion
      ),
      scalacOptions ++= Seq(
        "-deprecation",
        "-unchecked",
        "-Xfuture"
      ),
      scriptedLaunchOpts := { scriptedLaunchOpts.value ++
        Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
      },
      scriptedBufferLog := false
  )

