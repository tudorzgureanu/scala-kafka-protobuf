package com.tudorzgureanu

import java.util.UUID

import cakesolutions.kafka._
import com.tudorzgureanu.domain._
import com.tudorzgureanu.kafka.producers.v1.{UserProducerImpl => UserProducerImplV1}
import com.tudorzgureanu.kafka.producers.v2.{UserProducerImpl => UserProducerImplV2}
import com.tudorzgureanu.protocol.users.v1.{UsersEnvelope => UsersEnvelopeV1}
import com.tudorzgureanu.protocol.users.v2.{UsersEnvelope => UsersEnvelopeV2}
import org.apache.kafka.common.serialization.{Serializer, StringSerializer}

object KafkaMessageSender {
  val bootstrapServers = "localhost:29092"

  def main(args: Array[String]): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global
    val userProducerV1KafkaConf = createKafkaProducerConf(KafkaSerializer(UsersEnvelopeV1.toByteArray))
    val userProducerV1 = new UserProducerImplV1(KafkaProducer(userProducerV1KafkaConf))
    val userProducerV2KafkaConf = createKafkaProducerConf(KafkaSerializer(UsersEnvelopeV2.toByteArray))
    val userProducerV2 = new UserProducerImplV2(KafkaProducer(userProducerV2KafkaConf))

    val user1 = User(UserId(UUID.randomUUID().toString), "John", "Doe")
    val user2 = User(UserId(UUID.randomUUID().toString), "John", "Roe")
    val userCreated = UserCreated(user1)
    val userUpdated = UserUpdated(user2)
    val userDeactivated = UserDeactivated(UserId(userUpdated.userId.underlying))

//    userProducerV1.writeToKafka(userCreated.userId.underlying, userCreated.toProtoPayloadV1).map(_ => println("sent to kafka"))
//    userProducerV2.writeToKafka(userCreated.userId.underlying, userCreated.toProtoPayloadV2).map(_ => println("sent to kafka"))
    userProducerV2
      .writeToKafka(userDeactivated.userId.underlying, userDeactivated.toProtoPayloadV2)
      .map(_ => println("sent to kafka"))
//    userProducerV1
//      .writeToKafka(userUpdated.userId.underlying, userUpdated.toProtoPayloadV1)
//      .map(_ => println("sent to kafka"))

//    userProducerV2
//      .writeToKafka(userUpdated.userId.underlying, userUpdated.toProtoPayloadV2)
//      .map(_ => println("sent to kafka"))
    Thread.sleep(1000)
  }

  private def createKafkaProducerConf[T](valueSerializer: Serializer[T]): KafkaProducer.Conf[String, T] =
    KafkaProducer.Conf(
      keySerializer = new StringSerializer,
      valueSerializer = valueSerializer,
      bootstrapServers = bootstrapServers,
      enableIdempotence = true
    )
}
