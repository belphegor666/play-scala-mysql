name := """play-scala-mysql"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val root = project.in(file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
    jdbc,
    "mysql" % "mysql-connector-java" % "5.1.31",
    "com.typesafe.play" %% "anorm" % "2.5.0",
    "javax.inject" % "javax.inject" % "1"
)

fork in run := true