package models

case class User(userId: Int, firstName: Option[String], lastName: Option[String], age: Option[Int])
