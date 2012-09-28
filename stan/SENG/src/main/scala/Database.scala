package main.scala.classes
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations
import org.squeryl._
import adapters.H2Adapter
import java.util.{Calendar, Date}
import java.sql.Timestamp



object UserType extends Enumeration {
  type UserType = Value
  val clerk = Value("Clerk")
  val manager = Value("Manager")
  val admin = Value("Admin")
}

/*class Product (val id:Long, val productName:String, val expiryDate:Date, var price:BigDecimal){
  def this() = this(0, "", Calendar.getInstance().getTime, 0.0)
} */


object LocationType extends Enumeration {
  type LocationType = Value
  val location = Value(1,"Location")
  val warehouse = Value(2,"Warehouse")
  val frontStore = Value(3,"FrontStore")
  val backStore = Value(4,"BackStore")
}

import LocationType._
//Location Class
/*class Location (val id:Long, val locationName:String, val locationType:LocationType){
  import Database._
  def this() = this(0,"",LocationType.location)
  val isLocation = location.where(l=>l.locationName === locationName).single
  location.insert(new Location(0,"Mike",LocationType.location))

}    */

import UserType._

class User (val id:Long, var name:String, var userType:UserType){
  def this() = this(0,"",UserType.admin)
}

class Transaction(val id:Long, val product:Set[(String)], val amount:BigDecimal){
  def this() = this(0,Set(""),0.0)
}

class Member (val id:Long, var name:String, var points:Long){
  def this() = this(0,"",0)
}


object Database extends Schema{
  val productTable = table[Product]("product")
  val locationTable = table[Location]("location")
  val userTable = table[User]("user")
  val transactionTable = table[Transaction]("transaction")
  val memberTable = table[Member]("member")
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

      userTable.insert(new User(0,"Stan",UserType.manager))
      userTable.insert(new User(1,"Daniel",UserType.manager))
      userTable.insert(new User(2,"Mike",UserType.clerk))

      val query = userTable.where(a=> a.userType === UserType.manager)
      for (q <- query) {
        println(q.id+" "+q.name+" "+q.userType)
      }

      val location1 = new Location()
      location1.AddLocation("A")
      location1.AddLocation("B")
      val printAllLocations = for (l <- from(locationTable)(a=> select(a))) {println(l.id+" "+l.locationName+" "+l.locationType)}
      printAllLocations
      //val fullTable = (table:Table) => {from (table) (t => select(t))}

      val product1 = new Product()
      product1.AddProduct("Milk",Calendar.getInstance().getTime(),10)
      val printAllProducts = for(p <- {from (productTable) (t => select(t))}) {
        println(p.id+" "+p.productName+" "+p.expiryDate+" "+p.price)
      }
      printAllProducts

      val printAllUsers = for(u <- {from (userTable) (u => select(u))}) {
        println(u.id+" "+u.name+" "+u.userType)
      }
      printAllUsers
    }
  }
}