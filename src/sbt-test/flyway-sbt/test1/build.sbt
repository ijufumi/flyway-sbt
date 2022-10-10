organization := "org.flywaydb"
enablePlugins(FlywayPlugin)
name := "flyway-sbt-test1"

libraryDependencies ++= Seq(
  "org.hsqldb" % "hsqldb" % "2.5.2",
)

flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayLocations += "db/sbt"
flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayCleanDisabled := false

Test / flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
Test / flywayUser := "SA"
Test / flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
Test / flywayUser := "SA"
Test / flywayCleanDisabled := false
