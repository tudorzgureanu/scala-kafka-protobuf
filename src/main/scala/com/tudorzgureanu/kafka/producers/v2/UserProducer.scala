package com.tudorzgureanu.kafka.producers.v2

import java.util.UUID

import cakesolutions.kafka.{KafkaProducerLike, KafkaProducerRecord}
import com.tudorzgureanu.protocol.users.v2.UsersEnvelope
import org.apache.kafka.clients.producer.RecordMetadata

import scala.concurrent.Future

trait UserProducer {
  def writeToKafka(key: String, value: UsersEnvelope.Payload): Future[RecordMetadata]
}

class UserProducerImpl(kafkaProducerLike: KafkaProducerLike[String, UsersEnvelope]) extends UserProducer {
  override def writeToKafka(key: String, value: UsersEnvelope.Payload): Future[RecordMetadata] = {
    val envelope = UsersEnvelope(UUID.randomUUID().toString, value)
    val record =
      KafkaProducerRecord[String, UsersEnvelope](topic = "users", key = key, value = envelope)
    kafkaProducerLike.send(record)
  }
}
