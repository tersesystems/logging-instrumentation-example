import sbt._

object Dependencies {

  val terseLogback = "1.1.1"
  val blacklite = "1.2.2"

  val scalaTest = "org.scalatest" %% "scalatest" % "3.2.8"

  // Basic Logback
  val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3"
  val logstashLogbackEncoder = "net.logstash.logback" % "logstash-logback-encoder" % "6.6"
  val jansi  = "org.fusesource.jansi" % "jansi" % "1.17.1"

  // https://github.com/tersesystems/blacklite#logback
  val blackliteLogback = "com.tersesystems.blacklite" % "blacklite-logback" % blacklite
  // https://github.com/tersesystems/blacklite#codec
  //val blackliteZtdCodec = "com.tersesystems.blacklite" % "blacklite-codec-zstd" % blacklite

  // https://tersesystems.github.io/terse-logback/
  val terseLogbackClassic = "com.tersesystems.logback" % "logback-classic" % terseLogback
  val logbackUniqueId = "com.tersesystems.logback" % "logback-uniqueid-appender" % terseLogback
  val logbackTracing = "com.tersesystems.logback" % "logback-tracing" % terseLogback
  val logbackByteBuddy = "com.tersesystems.logback" % "logback-bytebuddy" % terseLogback

  // https://mvnrepository.com/artifact/net.bytebuddy/byte-buddy
  val byteBuddy = "net.bytebuddy" % "byte-buddy" % "1.11.5"  
}
