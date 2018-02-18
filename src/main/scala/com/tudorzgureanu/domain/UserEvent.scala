package com.tudorzgureanu.domain

sealed trait UserEvent

case class UserCreated(user: User) extends UserEvent
case class UserUpdated(user: User) extends UserEvent
case class UserDeactivated(userId: String) extends UserEvent
