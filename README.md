# scala-kafka-protobuf
A PoC using Scala that defines single-message protobuf API per Kafka topic

# Introduction

The goal for this PoC is to evaluate the usage of a single envelope protobuf definition per kafka topic. The main reason behind that is having a clear "topic API" and clear expectations for consumers of these messages. 

# Tech stack

- [Scala 2.12.4](https://github.com/scala/scala)
- [Akka 2.5.9](https://github.com/akka/akka)
- [Scala Kafka Client 1.0.0](https://github.com/cakesolutions/scala-kafka-client)
- [ScalaPB](https://github.com/scalapb/ScalaPB) for generating Scala classes for the protobuf messages. Using proto3 syntax.
- [Apache Kafka 1.0](https://github.com/apache/kafka) through [Confluent Platform 4.0.0](https://docs.confluent.io/current/platform.html)
- [Docker](https://www.docker.com/) for running Kafka and [Zookeeper](https://zookeeper.apache.org/)

# Running 

# Example

# Conclusion

