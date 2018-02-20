package com.tudorzgureanu.actors

import akka.actor.Actor
import com.tudorzgureanu.actors.RestartableActor.{RestartActor, RestartActorException}

trait RestartableActor extends Actor {
  abstract override def receive = super.receive orElse {
    case RestartActor(ex) =>
      throw RestartActorException(ex)
  }
}

object RestartableActor {

  case class RestartActor(ex: Throwable)

  private case class RestartActorException(cause: Throwable) extends Exception

}
