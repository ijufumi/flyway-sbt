organization := "org.flywaydb"
enablePlugins(FlywayPlugin)
name := "flyway-sbt-test3"

libraryDependencies ++= Seq(
  "org.hsqldb" % "hsqldb" % "2.7.2",
  "org.flywaydb" % "flyway-database-hsqldb" % "10.1.0",
)

flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayLocations := Seq("filesystem:src/main/resources/db/migration")
flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayLocations := Seq("filesystem:src/main/resources/db/migration")
flywayCleanDisabled := false

Test / flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
Test / flywayUser := "SA"
Test / flywayLocations := Seq("filesystem:src/main/resources/db/migration")
Test / flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
Test / flywayUser := "SA"
Test / flywayLocations := Seq("filesystem:src/main/resources/db/migration")
Test / flywayCleanDisabled := false
