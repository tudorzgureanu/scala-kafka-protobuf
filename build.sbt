import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.tudorzgureanu",
      scalaVersion := "2.12.4",
      version := "0.1.0-SNAPSHOT"
    )),
    name := "Scala Kafka Protobuf POC",
    resolvers += Resolver.bintrayRepo("cakesolutions", "maven"),
    libraryDependencies ++= Seq(
      Akka.core,
      Akka.slf4j,
      Kafka.scalaKafkaClientAkka,
      Testing.scalaTest
    ),
    managedSourceDirectories in Compile += target.value / "proto-generated",
    PB.targets in Compile := Seq(
      scalapb.gen(flatPackage = true/*, javaConversions = true*/) -> (target.value / "proto-generated")
    )
  )
