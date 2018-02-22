package com.tudorzgureanu.kafka.producers

import com.tudorzgureanu.domain.UserEvent
import org.apache.kafka.clients.producer.RecordMetadata

import scala.concurrent.Future

trait UserProducer {
  def writeToKafka(event: UserEvent): Future[RecordMetadata]
}

class UserProducerImpl extends UserProducer {
  override def writeToKafka(event: UserEvent): Future[RecordMetadata] = ???
}
