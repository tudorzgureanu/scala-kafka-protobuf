package com.tudorzgureanu

import com.typesafe.config.ConfigFactory

object Main {

  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load("application.conf")
    Bootstrap().startServer()
  }
}
