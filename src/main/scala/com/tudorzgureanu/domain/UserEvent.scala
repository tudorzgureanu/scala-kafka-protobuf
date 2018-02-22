package com.tudorzgureanu.domain

import com.tudorzgureanu.protocol

sealed trait UserEvent

case class UserCreated(user: User) extends UserEvent

object UserCreated {

  def fromProto(userCreatedProto: protocol.users.v1.UserCreated): UserCreated =
    UserCreated(User(UserId(userCreatedProto.id), userCreatedProto.firstName, userCreatedProto.lastName))
}

case class UserUpdated(user: User) extends UserEvent

object UserUpdated {

  def fromProto(userUpdatedProto: protocol.users.v1.UserUpdated): UserUpdated =
    UserUpdated(User(UserId(userUpdatedProto.id), userUpdatedProto.firstName, userUpdatedProto.lastName))
}

case class UserDeactivated(userId: String) extends UserEvent

object UserDeactivated {

  def fromProto(userDeactivatedProto: protocol.users.v1.UserDeactivated): UserDeactivated =
    UserDeactivated(userDeactivatedProto.userId)
}
