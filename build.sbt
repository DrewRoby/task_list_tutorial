name := """task_list_tutorial"""
organization := "com.marklewis"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.10"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.1"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.0.0"
libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.3.2"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.11"
libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2"
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.4"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.marklewis.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.marklewis.binders._"
