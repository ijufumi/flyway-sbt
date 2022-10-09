val flywayVersion = "9.4.0"
val pluginVersion = "9.4.0"

val scala213Version = "2.13.9"
val scala212Version = "2.12.17"

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name := "flyway-sbt",
    organization := "io.github.ijufumi",
    version := pluginVersion,
    scalaVersion := scala212Version,
    crossScalaVersions := Seq(scala212Version, scala213Version),
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
