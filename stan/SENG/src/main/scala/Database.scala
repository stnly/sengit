package main.scala
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations
import org.squeryl._
import adapters.H2Adapter
import java.util.Date
import java.sql.Timestamp



object UserType extends Enumeration {
  type UserType = Value
  val clerk = Value("Clerk")
  val manager = Value("Manager")
  val admin = Value("Admin")
}

object LocationType extends Enumeration {
  type LocationType = Value
  val location = Value(1,"Location")
  val warehouse = Value(2,"Warehouse")
  val frontStore = Value(3,"FrontStore")
  val backStore = Value(4,"BackStore")
}

class Product (
  val id:Long,
  val productName:String,
  val expiryDate:Date,
  var price:BigDecimal){
}

import LocationType._
class Location (
  val id:Long,
  val locationName:String,
  val locationType:LocationType){
}
import UserType._
class User (val id:Long,
  var name:String,
  var userType:UserType){
}

class Transaction(
  val id:Long,
  val product:Set[(Product)],
  val amount:BigDecimal){
}

class Member (
  val id:Long,
  var name:String,
  var points:Long){
}


object Database extends Schema{
  val product = table[Product]
  val location = table[Location]
  val user = table[User]
  val transaction = table[Transaction]
  val member = table[Member]


}

object Main {
  def main(args: Array[String]) {
    import Database._
    import org.squeryl.SessionFactory
         Class.forName("org.h2.Driver");
    SessionFactory.concreteFactory = Some(()=>
      Session.create(
        java.sql.DriverManager.getConnection("jdbc:h2:~/example", "sa", ""),
        new H2Adapter)
    )

    inTransaction {

    drop  // Bad idea in production application!!!!
    create
    printDdl

      //import main.scala._

      user.insert(new User(0,"Stan",UserType.manager))
      val a = from(user)(a=>where(a.name === "Stan")select(a))
      println(a)
    }
  }
}