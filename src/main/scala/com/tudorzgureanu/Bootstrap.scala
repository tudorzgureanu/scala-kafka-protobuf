package com.tudorzgureanu

import akka.actor.ActorSystem
import cakesolutions.kafka.{KafkaConsumer, KafkaDeserializer}
import cakesolutions.kafka.akka.KafkaConsumerActor
import com.tudorzgureanu.kafka.consumers.UserConsumerActor
import com.tudorzgureanu.protocol.users.v1.UsersEnvelope
import com.tudorzgureanu.services.UserServiceMock
import com.typesafe.config.Config
import scala.concurrent.duration._
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.common.requests.IsolationLevel
import org.apache.kafka.common.serialization.StringDeserializer

object Bootstrap {

  def apply( /*config: Config, */ ): Bootstrap =
    new Bootstrap( /*config, */ ActorSystem.create("users-service" /*, config*/ ))
}

class Bootstrap( /*config: Config, */ actorSystem: ActorSystem) {

  val bootstrapServers = "localhost:29092"

  def startServer(): Unit = {
    val userKafkaConsumerConf =
      KafkaConsumer.Conf(
        keyDeserializer = new StringDeserializer,
        valueDeserializer = KafkaDeserializer(UsersEnvelope.parseFrom),
        bootstrapServers = bootstrapServers,
        groupId = "userConsumerV1",
        enableAutoCommit = false,
        maxPollRecords = 50,
        autoOffsetReset = OffsetResetStrategy.EARLIEST,
        isolationLevel = IsolationLevel.READ_UNCOMMITTED
      )
    val userKafkaConsumerActorConf =
      KafkaConsumerActor.Conf(
        scheduleInterval = 1.seconds, // scheduling interval for Kafka polling when consumer is inactive
        unconfirmedTimeout = 3.seconds, // duration for how long to wait for a confirmation before redelivery
        maxRedeliveries = 3 // maximum number of times a unconfirmed message will be redelivered
      )

    actorSystem.actorOf(UserConsumerActor.props(userKafkaConsumerConf, userKafkaConsumerActorConf, new UserServiceMock))
  }
}
