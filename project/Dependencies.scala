import sbt.{ModuleID, _}

object Dependencies {

  object Akka {
    val version = "2.5.9"
    lazy val core = "com.typesafe.akka" %% "akka-actor" % version
    lazy val slf4j = "com.typesafe.akka" %% "akka-slf4j" % version

    lazy val testKit = "com.typesafe.akka" %% "akka-testkit" % version % Test
  }

  object Kafka {
    lazy val scalaKafkaClientAkka = "net.cakesolutions" %% "scala-kafka-client-akka" % "2.0.0"
  }

  object Logging {
    lazy val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3"
  }

  object ScalaPB {
    // (optional) If you need scalapb/scalapb.proto or anything from google/protobuf/*.proto
    val runtime: ModuleID = "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
  }

  object Testing {
    lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test
  }

}
