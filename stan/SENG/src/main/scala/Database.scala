package main.scala.classes

//import classes.PoSWare.Backstore
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations
import org.squeryl._
import adapters.H2Adapter
import java.util.{Calendar, Date}
import java.sql.Timestamp
import swing._


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
  val activeproductTable = table[ActiveProduct]("activeproduct")
  val locationToProduct = oneToManyRelation(locationTable, productTable).via((l,p) => l.id === p.locationId )

  override def applyDefaultForeignKeyPolicy(foreignKeyDeclaration: ForeignKeyDeclaration)
  = foreignKeyDeclaration.constrainReference
  locationToProduct.foreignKeyDeclaration.constrainReference(onDelete cascade)
}

object GUI extends SimpleSwingApplication{
  import event._
  def top = new MainFrame {
    interface.setup
    title = "Add Location"
    val LocationInput = new TextField
    val LocationLabel = new Label{
      text = "Location:"
      border = Swing.EmptyBorder(5,5,5,5)
    }
    val LocationTypeInput = new TextField
    val LocationType = new Label{
      text = "Type:"
      border = Swing.EmptyBorder(5,5,5,5)
    }

    val convertButton = new Button {
      text = "Add Location"
    }


    val printButton = new Button {
      text = "print"
    }
    val output = new Label{
      text = ""
      border = Swing.EmptyBorder(20,20,20,20)
      listenTo(convertButton,LocationInput)
      def add() {
        val location = LocationInput.text
        interface.addWarehouse(location)
        text = location + " added in successfully!"
        println("added successfully")
      }
      reactions += {
        case ButtonClicked(_)| EditDone(_) => add()
      }
    }
    contents = new GridPanel(4,4) {
      contents.append(LocationLabel,LocationInput,LocationType,LocationTypeInput,convertButton,printButton,output)
      border = Swing.EmptyBorder(10,10,10,10)
    }

  }
}

object interface{
  import Database._
  import org.squeryl.SessionFactory
  import main.scala.classes._

  def setup {

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
    }
  }

  /*def addUser (UserName:String)  {
    inTransaction {
      val user1 = new User(UserName,UserType.manager,false)
      user1.add()
    }
  }*/

  def printUser (UserID:Long) {
    inTransaction {
      val query = userTable.where(a=> a.id === UserID)
      for (q <- query) {
        println(q.id+" "+q.name+" "+q.userType)
      }
    }
  }

  def addWarehouse (LocationName:String) {
    inTransaction {
      val location1 = new Location(LocationName)
      location1.add()
      //location1.setLocAsWarehouse()
    }
  }

  def printWarehouse (LocationName:String) {
    inTransaction {
      val query = warehouseTable.where(a=> a.locationName === LocationName)
      for (q <- query) {
        println(q.id+" "+q.locationName)
      }
    }
  }

  def printWarehouse () {
    inTransaction {
      //val warehouse1 = new Warehouse()
      //warehouse1.printAll()
    }
  }
  /*def addStore (LocationName:String) {
    inTransaction {
      val location1 = new Location(LocationName)
      location1.add()
      location1.setLocAsStore()
    }
  }*/

}

object Main {
  import Database._
  import org.squeryl.SessionFactory
  def main(args: Array[String]) {

         Class.forName("org.h2.Driver");
    SessionFactory.concreteFactory = Some(()=>
      Session.create(
        java.sql.DriverManager.getConnection("jdbc:h2:~/PointOfSales", "sa", ""),
        new H2Adapter)
    )

    inTransaction {

      drop  // Bad idea in production application!!!!
      create
      printDdl

      val user1 = new User("test",UserType.manager, false )
      user1.add()
      userTable.insert(new User("admin",UserType.admin, true))
      userTable.insert(new User("Stan",UserType.clerk, true))
      userTable.insert(new User("Daniel",UserType.manager, true))
      userTable.insert(new User("Mike",UserType.manager, true))

      println("user 1 is a manager " + user1.isManager())

      val query = userTable.where(a=> a.userType === UserType.manager).toList

      println(query(2).name)


      val location2 = new Location("A")
      location2.add()
      val location3 = new Location("B")
      location3.add()
      //location2.setLocAsWarehouse()
      //val product1 = new Product("Milk", Calendar.getInstance().getTime, 10)
      newProduct("Milk", location2.id, Calendar.getInstance().getTime, 10)
      getActiveProductId("Milk")
      val product2 = new Product("Cheese", Calendar.getInstance().getTime, 10)
      val product3 = new Product("Bread", Calendar.getInstance().getTime, 10)
      //location2.products.associate(product1)
      location2.products.associate(product2)
      location3.products.associate(product3)


      //println(product1.locations.single.locationName)

      //relation query example

      println("printing all locations")
      val locations1 = from(locationTable)(a =>select (a)).toList
      for (i <- locations1) {
        println(i.locationName)
      }


      val l = from(locationTable)(l=>
        where(l.id in
          from (productTable)(p=> where(l.id === p.id) select(p.id)))
        select(l)
      )
      //println(l.locationName)

      //join example
      //from(locationTable, productTable)((l,p)=>
      //  where(l.locationType === LocationType.location and l.id === p.locationId)
      //    select(p))

      val pro =from(locationTable, productTable)((l,p)=>
        where(l.id === location2.id and l.locationType === LocationType.location and l.id === p.locationId)
          select(p)).toList

      println(pro(0).productName)
      println(pro(1).productName)
      //println(pro(2).productName)
      println(pro.length)
      println(productLineExists("Milk"))
      println("Milk Quantity at location2: "+productStockAtLocation("Milk", location2.id))
      //for(s <- pro)
      //    println(s.productName)
      /*


       */

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

  // ==================================
  // ============ PRODUCTS ============
  // ==================================

  def newProduct(productName: String, locationId: Long, expiryDate : Date, price: BigDecimal):Long={
    val newProduct = new Product(productName, expiryDate, price)
    val data = locationTable.where(l => l.id === locationId).single
    data.products.associate(newProduct) //productTable.insert(newProduct)
    return newProduct.id
  }

  def printAllProducts() {
    for(p <- {from (productTable) (t => select(t))}) {
      println(p.id+" "+p.productName+" "+p.expiryDate+" "+p.price)
    }
  }

  def productLineExists(productName: String):Boolean = {
    return activeproductTable.exists(ap => ap.productName.matches(productName))
  }

  def productStockAtLocation (productName: String, locationId: Long): Long = {
    val products = from(locationTable, productTable)((l,p)=>
                    where(l.id === locationId
                    and l.id === p.locationId
                    and p.productName === productName)
                    select(p)).toList
    var count = 0
    for (i <- products){
      count += 1
    }
    return count
  }

  // ==================================
  // ======== ACTIVE PRODUCTS =========
  // ==================================

  //TODO Convert to Squeryl

  def newActiveProducts (productName: String, price: BigDecimal) :Long={
    //addProductLocation
    val ap = new ActiveProduct(productName, price)
    return ap.id

  }


  def activeProductExists(productName: String): Boolean = {
    return activeproductTable.exists(ap => ap.productName.matches(productName))
  }

  def getActiveProductId(productName: String) : Long = {
    if (!activeProductExists(productName)) return -1
    val n = activeproductTable.where(p =>p.productName === productName).single
    println("The activeproduct id of product is " + n.id)
    return n.id
  }

  // ==================================
  // ============= USER ===============
  // ==================================

  // ==================================
  // ============ LOCATION ============
  // ==================================
  import LocationType._
  def newLocation(locationName: String, locationType: LocationType):Long={
    val l = new Location(locationName, locationType)
    locationTable.insert(l)
    val w = new Warehouse(locationName)
    warehouseTable.insert(w)
    return w.id
  }

  // ==================================
  // ========= TRANSACTIONS ===========
  // ==================================
}
