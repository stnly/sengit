package main.scala.classes
import UserType._
import main.scala.classes.Database._
import org.squeryl.PrimitiveTypeMode._

//added active field  for remove
class User (val name: String, var userType: UserType, val active: Boolean) extends Basic {
  def this() = this("", UserType.admin, true)

  def add (name: String, userType: UserType){
    require(!userTable.exists(u => u.name.matches(name)))
    userTable.insert(new User(name, userType, true))
  }
  //delete user
  def deactivate(name: String){
    update(userTable)(u =>
      where(u.name === name)
        set (u.active := false))
  }
  def isManager(name: String) :Boolean = {
    return (userTable.exists(u => u.name.matches(name) && u.userType == UserType.manager))
  }
}