organization := "org.flywaydb"
enablePlugins(FlywayPlugin)
name := "flyway-sbt-test1"

libraryDependencies ++= Seq(
  "org.hsqldb" % "hsqldb" % "2.7.2",
  "org.flywaydb" % "flyway-database-hsqldb" % "10.1.0",
)

flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayLocations += "db/migration"
flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayCleanDisabled := false
