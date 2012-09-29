package main.scala.classes
import UserType._

class User (var name: String, var userType: UserType) extends Basic {
  def this() = this("", UserType.admin)
}
