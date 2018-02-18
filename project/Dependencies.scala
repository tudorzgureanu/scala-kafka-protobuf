import sbt._

object Dependencies {

  object Akka {
    lazy val core = "com.typesafe.akka" %% "akka-actor" % "2.5.9"
    lazy val testKit = "com.typesafe.akka" %% "akka-testkit" % "2.5.9" % Test
  }

  object Kafka {
    lazy val scalaKafkaClientAkka = "net.cakesolutions" %% "scala-kafka-client-akka" % "1.0.0"
  }

  object Testing {
    lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test
  }

}
