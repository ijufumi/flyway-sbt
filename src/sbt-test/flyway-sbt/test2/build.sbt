organization := "org.flywaydb"
enablePlugins(FlywayPlugin)
name := "flyway-sbt-test1"

libraryDependencies ++= Seq(
  "org.hsqldb" % "hsqldb" % "2.6.1"
)

flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayLocations += "db/migration"
flywayUrl  := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser  := "SA"
flywayCleanDisabled := false
