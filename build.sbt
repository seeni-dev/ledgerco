import sbt.util

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "ledgerco"
  )
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.13" % "test"

mainClass in (Compile, run) := Some("Geektrust")

Test / parallelExecution := false
