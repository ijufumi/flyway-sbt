package io.github.ijufumi

import java.net.URL
import sbt._
import sbt.Keys.Classpath

package object compat {
  val CollectionConverters = scala.collection.JavaConverters

  def classpathUrls(cp: Classpath): Array[URL] =
    cp.map(_.data.toURI.toURL).toArray
}
