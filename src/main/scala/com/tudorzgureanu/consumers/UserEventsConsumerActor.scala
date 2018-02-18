package com.tudorzgureanu.consumers

import akka.actor.{Actor, ActorLogging, Props}
import cakesolutions.kafka.KafkaConsumer
import cakesolutions.kafka.akka.{ConsumerRecords, KafkaConsumerActor}
import cakesolutions.kafka.akka.KafkaConsumerActor.Confirm
import com.tudorzgureanu.domain._
import com.tudorzgureanu.protocol.users.UsersEnvelope
import com.tudorzgureanu.protocol.users.UsersEnvelope.Payload
import com.tudorzgureanu.services.UserService

import scala.concurrent.Future

object UserEventsConsumerActor {
  def props(): Props = ???
}

class UserEventsConsumerActor(
  kafkaConsumerConf: KafkaConsumer.Conf[String, UsersEnvelope],
  kafkaConsumerActorConf: KafkaConsumerActor.Conf,
  userService: UserService
) extends Actor
    with ActorLogging {

  import context.dispatcher

  private val UserEventsExtractor = ConsumerRecords.extractor[String, UsersEnvelope]

  // provide a consumer factory as dependency for testability
  val kafkaConsumer = context.actorOf(KafkaConsumerActor.props(kafkaConsumerConf, kafkaConsumerActorConf, self))

  override def receive: Receive = {
    case UserEventsExtractor(records) =>
      Future.traverse(records.recordsList)(record => processUserEvent(Option(record.key()), record.value())).map { _ =>
        kafkaConsumer ! Confirm(offsets = records.offsets, commit = true)
      }
  }

  private def processUserEvent(key: Option[String], value: UsersEnvelope): Future[Either[String, UserEvent]] = {
    value.payload match {
      case Payload.UserCreated(userCreated) =>
        log.info(s"[correlationId: ${value.correlationId}] User created $userCreated")
        userService
          .persistUserEvent(UserCreated(User(UserId(userCreated.id), userCreated.firstName, userCreated.lastName)))
          .map(Right(_))
      case Payload.UserUpdated(userUpdated) =>
        log.info(s"[correlationId: ${value.correlationId}] User updated $userUpdated")
        userService
          .persistUserEvent(UserUpdated(User(UserId(userUpdated.id), userUpdated.firstName, userUpdated.lastName)))
          .map(Right(_))
      case Payload.UserDeactivated(userDeactivated) =>
        log.info(s"[correlationId: ${value.correlationId}] User deactivated $userDeactivated")
        userService.persistUserEvent(UserDeactivated(userDeactivated.userId)).map(Right(_))
      case Payload.Empty =>
        log.info(s"[correlationId: ${value.correlationId}] Unexpected payload with key: $key. Payload ignored.")
        Future.successful(Left("Couldn't not process payload"))
    }
  }

}
