organization := "org.flywaydb"
enablePlugins(FlywayPlugin)
name := "flyway-sbt-test4"

libraryDependencies ++= Seq(
  "org.hsqldb" % "hsqldb" % "2.7.0",
)

flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayCallbacks := Seq(Callback)
flywayCleanDisabled := false
