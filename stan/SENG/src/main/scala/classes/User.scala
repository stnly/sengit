package main.scala.classes
import UserType._
import main.scala.classes.Database._
import org.squeryl.PrimitiveTypeMode._

//added active field  for remove
class User (val name: String, var userType: UserType, val active: Boolean) extends Basic {
  def this(name: String,userType: UserType) = this(name, userType, true)

  def add (){
    require(!userTable.exists(u => u.name.matches(this.name)))
    userTable.insert(this)
  }
  //delete user
  def deactivate(){
    update(userTable)(u =>
      where(u.name === this.name)
        set (u.active := false))
  }
       /*
  def getTransactionUser() :String = {
    val u = transactionTable.where(t => t.id === this.id).single
    return u.user
  }
  */

  def isManager() :Boolean = {
    val n = userTable.where(u => u.id === this.id).single
    return n.userType == UserType.manager
  }
}