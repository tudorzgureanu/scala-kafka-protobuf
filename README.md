# scala-kafka-protobuf
A PoC using Scala that defines single-message protobuf API per Kafka topic

# Introduction

The goal for this PoC is to evaluate the usage of a single envelope protobuf definition per kafka topic. The main reason behind that is having a clear "topic API" and clear expectations for consumers of these messages. 

# Description

The idea is to have an envelope per topic defined in protobuf. We can add any header fields we need (e.g. correlation id) but the payload must use protobuf's `oneof` to list all the message types are sent on that topic.

## Example

In the [`users.proto`](https://github.com/tudorzgureanu/scala-kafka-protobuf/blob/master/src/main/protobuf/users.proto) file we have got a basic protobuf definitions for the `users` topic:

```
// protobuf file headers ..

message UsersEnvelope {
    string correlation_id = 1;
    // some other metadata
    oneof payload {
        UserCreated user_created = 11;
        UserUpdated user_updated = 12;
        UserActivated user_activated = 13;
    }
}

// the rest of the messages ..
```
Each protobuf compiler will generate these classes in its own way along with an enum or some class hierarchy for the `oneof` cases (depending on the language). Usually it will also generate a special Empty case (in Scala, Java and C# at least, the name varies per language) to handle invalid or missing messages.

# Tech stack

- [Scala 2.12.4](https://github.com/scala/scala)
- [Akka 2.5.9](https://github.com/akka/akka)
- [Scala Kafka Client 1.0.0](https://github.com/cakesolutions/scala-kafka-client)
- [ScalaPB](https://github.com/scalapb/ScalaPB) for generating Scala classes for the protobuf messages. Using proto3 syntax.
- [Apache Kafka 1.0](https://github.com/apache/kafka) through [Confluent Platform 4.0.0](https://docs.confluent.io/current/platform.html)
- [Docker](https://www.docker.com/) for running Kafka and [Zookeeper](https://zookeeper.apache.org/)

# Running 



# Conclusion

