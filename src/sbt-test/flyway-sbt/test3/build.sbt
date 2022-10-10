organization := "org.flywaydb"
enablePlugins(FlywayPlugin)
name := "flyway-sbt-test3"

libraryDependencies ++= Seq(
  "org.hsqldb" % "hsqldb" % "2.5.2",
)

flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayLocations := Seq("filesystem:src/main/resources/db/migration")
flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser  := "SA"
flywayLocations := Seq("filesystem:src/main/resources/db/migration")
flywayCleanDisabled := false
