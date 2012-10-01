package main.scala.classes

//import classes.PoSWare.Backstore
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

object LocationType extends Enumeration {
  type LocationType = Value
  val location = Value(1,"Location")
  val warehouse = Value(2,"Warehouse")
  val frontStore = Value(3,"FrontStore")
  val backStore = Value(4,"BackStore")
}

object Database extends Schema {
  val productTable = table[Product]("product")
  val locationTable = table[Location]("location")
  val warehouseTable = table[Warehouse]("warehouse")
  val frontstoreTable = table[Store]("store")
  val backstoreTable = table[Backstore]("backstore")
  val userTable = table[User]("user")
  val memberTable = table[Member]("member")
  val reserveTable = table[Reserve]("reserve")
  val transactionTable = table[Transaction]("transaction")
  val purchaseTable = table[Purchase]("purchase")
  val reservedproductTable = table[ReservedProduct]("reservedproduct")
  val activeproductTable = table[ActiveProducts]("activeproduct")
  val locationToProduct = oneToManyRelation(locationTable, productTable).via((l,p) => l.id === p.locationId )

  override def applyDefaultForeignKeyPolicy(foreignKeyDeclaration: ForeignKeyDeclaration)
  = foreignKeyDeclaration.constrainReference
  locationToProduct.foreignKeyDeclaration.constrainReference(onDelete cascade)
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

      val user1 = new User("test",UserType.manager, false )
      user1.add()
      //userTable.insert(new User("admin",UserType.admin, true))
      //userTable.insert(new User("Stan",UserType.clerk, true))
      //userTable.insert(new User("Daniel",UserType.manager, true))
      //userTable.insert(new User("Mike",UserType.manager, true))

      println("user 1 is a manager " + user1.isManager())

      val query = userTable.where(a=> a.userType === UserType.manager)
      for (q <- query) {
        println(q.id+" "+q.name+" "+q.userType)
      }

      val location2 = new Location("A")
      location2.add()
      //location2.setLocAsWarehouse()
      val product1 = new Product("String", Calendar.getInstance().getTime, 10)
      location2.products.associate(product1)
      product1.add()

      //println(product1.locations.single.locationName)

      val l = from(locationTable)(l=>
        where(l.id in
          from (productTable)(p=> where(l.id === p.id) select(p.id)))
        select(l)
      ).single
      println(l.locationName)

        /*
      val location1 = new Location()
      location1.add("A")
      location1.add("B")
      location1.printAll()
      location1.add("C")

      val warehouse1 = new Warehouse()
      warehouse1.add("A")
      warehouse1.printAll()

      val store1 = new Store()
      store1.add("B")
      store1.printAll()
      product1.add("Bread",Calendar.getInstance().getTime,2)
      product1.printAll()
      val printAllUsers = for(u <- {from (userTable) (u => select(u))}) {
        println(u.id+" "+u.name+" "+u.userType)
      }
      printAllUsers
      location1.printAll()
      */
      /*
      val product1 = new Product("Milk",Calendar.getInstance().getTime,10.00)
      val product2 = new Product("Cheese",Calendar.getInstance().getTime,4.00)
      product2.add()
      product1.add()
      product1.printAll()

      val member1 = new Member("test")
      member1.add()
      member1.addPoint(10)
      println("added 10 points = member1.checkPoints()"  )
      member1.removePoint(5)
      println("removed 5 points = member1.checkPoints()"  )
      val transaction1 = new Transaction("Daniel", "test")
      println("is it a transction : " + transaction1.transactionExists()  )
      transaction1.newTransaction()
      transaction1.addProductToTransaction(product1.id)
      transaction1.addProductToTransaction(product2.id)
      println("the user for transaction1 is "+ transaction1.getTransactionUser())
      println("the price for transaction1 is "+ transaction1.calculateTotal())


      val location1 = new Location("A")
      val location2 = new Location("B")
      val location3 = new Warehouse("C")
      val location4 = new Warehouse("D")
      location1.add()
      location2.add()
      location3.add()
      println("id of location1 is "+ location1.getId())
      location1.setLocAsWarehouse()
         */

     // val purchase1 = new Purchase()
     // purchase1.addProductToTransaction(1, "Milk")


             /*
      val reserve1 = new Reserve()
      reserve1.add("Daniel", Calendar.getInstance().getTime, "Milk", 10)
      reserve1.printReservationByMember("Daniel")
      reserve1.change("Daniel", "Milk", 3)
      reserve1.printReservationByMember("Daniel")
      reserve1.cancel("Daniel")
      reserve1.printReservationByMember("Daniel")
            */
    }
  }
}