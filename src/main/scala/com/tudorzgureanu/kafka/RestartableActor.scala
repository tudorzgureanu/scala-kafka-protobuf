package com.tudorzgureanu.kafka

import akka.actor.Actor
import com.tudorzgureanu.kafka.RestartableActor.{RestartActor, RestartActorException}

trait RestartableActor extends Actor {
  override def receive: Receive = {
    case RestartActor(ex) =>
      throw RestartActorException(ex)
  }
}

object RestartableActor {

  case class RestartActor(ex: Throwable)

  private case class RestartActorException(cause: Throwable) extends Exception

}
