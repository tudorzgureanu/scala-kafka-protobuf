package com.tudorzgureanu.services

import com.tudorzgureanu.domain.UserEvent

import scala.concurrent.{ExecutionContext, Future}

trait UserService {
  def persistUserEvent(userEvent: UserEvent)(implicit ec: ExecutionContext): Future[UserEvent]
}

class UserServiceMock extends UserService {
  override def persistUserEvent(userEvent: UserEvent)(implicit ec: ExecutionContext): Future[UserEvent] = {
    Future.successful(userEvent)
  }
}
