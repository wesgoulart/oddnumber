ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "oddnumber",
    idePackagePrefix := Some("com.techtest")
  )

libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"
libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.12.410"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"
libraryDependencies += "org.scalatest" %% "scalatest-funsuite" % "3.2.15" % "test"