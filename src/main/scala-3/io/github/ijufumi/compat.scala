package io.github.ijufumi

import java.net.URL
import java.nio.file.Paths
import sbt._
import sbt.Keys.Classpath

package object compat {
  val CollectionConverters = scala.jdk.CollectionConverters

  def classpathUrls(cp: Classpath): Array[URL] =
    cp.map(entry => Paths.get(entry.data.id).toUri.toURL).toArray
}
