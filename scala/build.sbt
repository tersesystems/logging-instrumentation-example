import Dependencies._

ThisBuild / scalaVersion     := "2.13.6"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .enablePlugins(JavaAgent, JavaAppPackaging)
  .settings(
    name := "scala-logging-instrumentation-example",

    // Basic logback
    libraryDependencies += logbackClassic,
    libraryDependencies += logstashLogbackEncoder,
    libraryDependencies += jansi,

    // sqlite appender
    libraryDependencies += blackliteLogback,

    // terse-logback utilities
    libraryDependencies += terseLogbackClassic,
    libraryDependencies += logbackUniqueId,

    // library dependencies needed for instrumentation
    // this must be run using sbt-native-packager to run the agent in a script
    libraryDependencies += byteBuddy,
    libraryDependencies += logbackByteBuddy,
    libraryDependencies += logbackTracing,

    javaAgents += JavaAgent(logbackByteBuddy),

    libraryDependencies += scalaTest % Test
  )

