package main.scala.classes

import org.squeryl.PrimitiveTypeMode._
import java.util.{Calendar, Date}
import main.scala.classes.Database._

//Product Class
class Product (val productName: String, val expiryDate: Date, var price: BigDecimal) extends Basic{

  def this() = this("", Calendar.getInstance().getTime, 0.0)
  //lazy val transactionsTable : ManyToOne[Transaction] = Database.transactionToProduct.right(this)

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