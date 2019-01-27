import Dependencies._
lazy val root = (project in file(".")).settings(

    inThisBuild(List(
      organization := "com.neulab",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),

    scalacOptions += "-Ypartial-unification",
    name := "LDA.scala",
    libraryDependencies += scalaTest % Test,
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.5.11",
      "com.typesafe.akka" %% "akka-testkit" % "2.5.11" % Test
    ),
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-stream" % "2.5.11",
      "com.typesafe.akka" %% "akka-remote" % "2.5.11",
      "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.11" % Test
    ),
    // libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.25",
    // libraryDependencies += "org.typelevel" %% "cats-core" % "1.0.1",
    // libraryDependencies += "org.typelevel" %% "cats-free" % "1.0.1"
)
