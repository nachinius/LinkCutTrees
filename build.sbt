name := "LinkCutTrees"

version := "0.1"

scalaVersion := "2.12.4"

val scalaTestVersion = "3.0.4"
//libraryDependencies += "org.scalactic" %% "scalactic" % scalaTestVersion
libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion % "test"

val scalaCheckVersion = "1.13.4"
libraryDependencies += "org.scalacheck" %% "scalacheck" % scalaCheckVersion % "test"

libraryDependencies += "com.nachinius" %% "splay-trees" % "0.1"

coverageEnabled := true
