organization := "org.flywaydb"
enablePlugins(FlywayPlugin)
name := "flyway-sbt-test1"

libraryDependencies ++= Seq(
  "org.hsqldb" % "hsqldb" % "2.5.0"
)

flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayLocations += "db/sbt"
flywayUrl / Test := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser / Test := "SA"
