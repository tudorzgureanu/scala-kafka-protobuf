package com.tudorzgureanu.domain

case class User(id: UserId, firstName: String, lastName: String)

case class UserId(underlying: String)
