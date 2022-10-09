organization := "org.flywaydb"
enablePlugins(FlywayPlugin)
name := "flyway-sbt-test3"

libraryDependencies ++= Seq(
  "org.hsqldb" % "hsqldb" % "2.5.0"
)

flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayLocations := Seq("filesystem:src/main/resources/db/migration")
flywayUrl / Test := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser / Test := "SA"
flywayLocations / Test := Seq("filesystem:src/main/resources/db/migration")
