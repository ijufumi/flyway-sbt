ThisBuild / organization := "io.github.ijufumi"
ThisBuild / organizationName := "ijufumi"

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/ijufumi/flyway-sbt"),
    "scm:git@github.com:ijufumi/flyway-sbt.git"
  )
)
ThisBuild / developers := List(
  Developer(
    id = "ijufumi",
    name = "Takafumi Iju",
    email = "ijufumi@gmail.com",
    url = url("https://github.com/ijufumi/flyway-sbt")
  )
)

licenses := List(
  "Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")
)
homepage := Some(url("https://github.com/ijufumi/flyway-sbt"))

// Remove all additional repository other than Maven Central from POM
pomIncludeRepository := { _ => false }
publishTo := {
  // For accounts created after Feb 2021:
  // val nexus = "https://s01.oss.sonatype.org/"
  val nexus = "https://s01.oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
publishMavenStyle := true

credentials += Credentials(Path.userHome / ".sbt" / "sonatype_credentials")
