package com.tudorzgureanu.domain

import com.tudorzgureanu.protocol

sealed trait UserEvent {
  def userId: UserId
}

case class UserCreated(user: User) extends UserEvent {
  override val userId: UserId = user.id

  def toProtoPayloadV1: protocol.users.v1.UsersEnvelope.Payload.UserCreated =
    protocol.users.v1.UsersEnvelope.Payload
      .UserCreated(protocol.users.v1.UserCreated(user.id.underlying, user.firstName, user.lastName))

  def toProtoPayloadV2: protocol.users.v2.UsersEnvelope.Payload.UserCreated =
    protocol.users.v2.UsersEnvelope.Payload
      .UserCreated(protocol.users.v2.UserCreated(user.id.underlying, user.firstName, user.lastName))
}

object UserCreated {

  def fromProtoV1(userCreatedProto: protocol.users.v1.UserCreated): UserCreated =
    UserCreated(User(UserId(userCreatedProto.id), userCreatedProto.firstName, userCreatedProto.lastName))
}

case class UserUpdated(user: User) extends UserEvent {
  val userId: UserId = user.id

  def toProtoPayloadV1: protocol.users.v1.UsersEnvelope.Payload.UserUpdated =
    protocol.users.v1.UsersEnvelope.Payload
      .UserUpdated(protocol.users.v1.UserUpdated(user.id.underlying, user.firstName, user.lastName))

  def toProtoPayloadV2: protocol.users.v2.UsersEnvelope.Payload =
    protocol.users.v2.UsersEnvelope.Payload
      .UserUpdated(protocol.users.v2.UserUpdated(user.id.underlying, user.firstName, 42))
}

object UserUpdated {

  def fromProtoV1(userUpdatedProto: protocol.users.v1.UserUpdated): UserUpdated =
    UserUpdated(User(UserId(userUpdatedProto.id), userUpdatedProto.firstName, userUpdatedProto.lastName))
}

case class UserActivated(userId: UserId) extends UserEvent {

  def toProtoPayloadV1: protocol.users.v1.UsersEnvelope.Payload.UserActivated =
    protocol.users.v1.UsersEnvelope.Payload.UserActivated(protocol.users.v1.UserActivated(userId.underlying))

  def toProtoPayloadV2: protocol.users.v2.UsersEnvelope.Payload.UserActivated =
    protocol.users.v2.UsersEnvelope.Payload
      .UserActivated(protocol.users.v2.UserActivated(userId.underlying))

}

object UserActivated {

  def fromProtoV1(userDeactivatedProto: protocol.users.v1.UserActivated): UserActivated =
    UserActivated(UserId(userDeactivatedProto.userId))
}

case class UserDeactivated(userId: UserId) extends UserEvent {

  def toProtoPayloadV2: protocol.users.v2.UsersEnvelope.Payload.UserDeactivated =
    protocol.users.v2.UsersEnvelope.Payload.UserDeactivated(protocol.users.v2.UserDeactivated(userId.underlying))
}

object UserDeactivated {

  def fromProtoV2(userDeactivatedProto: protocol.users.v2.UserDeactivated): UserDeactivated =
    UserDeactivated(UserId(userDeactivatedProto.userId))
}
