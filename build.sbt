name := """helicoidal"""

organization := "im.jeanfrancois"

licenses += ("Apache 2.0", url("https://www.apache.org/licenses/LICENSE-2.0"))

javacOptions ++= Seq("-source", "1.6", "-target", "1.6")

scalaVersion := "2.10.4"

crossScalaVersions := Seq("2.10.4", "2.11.4")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.apache.helix" % "helix-core" % "0.7.1" exclude("javax.jms", "jms") exclude("com.sun.jdmk", "jmxtools") exclude("com.sun.jmx", "jmxri")
)

bintraySettings

com.typesafe.sbt.SbtGit.versionWithGit
