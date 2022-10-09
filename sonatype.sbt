import xerial.sbt.Sonatype._

sonatypeProfileName := "io.github.ijufumi"
publishMavenStyle := true
sonatypeProjectHosting := Some(GitHubHosting(user="ijufumi", repository="flyway-sbt", email="ijufumi@gmail.com"))
//developers := List(
//  Developer(id="davidmweber", name="David Weber", email="dave@veryflatcat.com", url=url("https://davidmweber.github.io/flyway-sbt-docs/"))
//)
licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))
publishTo := sonatypePublishTo.value

// sbt publishSigned
// sbt sonatypeRelease
