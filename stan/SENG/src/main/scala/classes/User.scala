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
}


     /*

class Product (val productName: String, val expiryDate: Date, var price: BigDecimal) extends Basic{

  def this() = this("", Calendar.getInstance().getTime, 0.0)

  val add = (name: String, expiry: Date, price: BigDecimal) => {
    require(!productTable.exists(p => p.productName.matches(name)))
    productTable.insert(new Product(name, expiry, price))
  }

  def printAll() {
    for(p <- {from (productTable) (t => select(t))}) {
      println(p.id+" "+p.productName+" "+p.expiryDate+" "+p.price)
    }
  }

  //TODO Convert to Squeryl
  val addProductLocation = (locationName: String, productName: String, stock: Int) => {
    require(productTable.exists(p => p.productName.matches(productName)))
    require(locationTable.exists(p => p.locationName.matches(locationName)))

  }

}


  val add = (name: String, expiry: Date, price: BigDecimal) => {
    require(!productTable.exists(p => p.productName.matches(name)))
    productTable.insert(new Product(name, expiry, price))
  }


def printAll() {
for(p <- {from (productTable) (t => select(t))}) {
println(p.id+" "+p.productName+" "+p.expiryDate+" "+p.price)
}
}
     */