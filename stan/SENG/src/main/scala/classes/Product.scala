package main.scala.classes

import org.squeryl.PrimitiveTypeMode._
import java.util.{Calendar, Date}
import main.scala.classes.Database.productTable

//Product Class
class Product (val productName:String, val expiryDate:Date, var price:BigDecimal) extends BasicEntry{

  def this() = this("", Calendar.getInstance().getTime, 0.0)

  val AddProduct = (productName:String, exipiryDate:Date, price:BigDecimal) => {
    val check = productTable.exists(p => p.productName.matches(productName))
    require(!check)
    productTable.insert(new Product(productName, expiryDate, price))
  }


}