package main.scala.classes

import org.squeryl.PrimitiveTypeMode._
import java.util.{Calendar, Date}
import main.scala.classes.Database._
import org.squeryl.dsl.ManyToOne

//Product Class
class Product (val productName: String, val locationId: Long, val expiryDate: Date, var price: BigDecimal) extends Basic{
  lazy val locations: ManyToOne[Location] = locationToProduct.right(this)
  def this(productName: String, expiryDate: Date, price: BigDecimal) = this(productName,0, expiryDate, price)
  val add = () => {
    productTable.insert(this)
  }

  def printAll() {
    for(p <- {from (productTable) (t => select(t))}) {
      println(p.id+" "+p.productName+" "+p.expiryDate+" "+p.price)
    }
  }

  def getActiveProductId() : Long ={
    val n = activeproductTable.where(p =>p.productName === this.productName).single
    return n.id
  }

  /*
  //TODO Convert to Squeryl
  val addProductLocation = (locationName: String, productName: String, stock: Int) => {
    require(productTable.exists(p => p.productName.matches(productName)))
    require(locationTable.exists(p => p.locationName.matches(locationName)))

  }
  */
}

class ActiveProducts (val productName: String, var price: BigDecimal, var active: Boolean) extends Basic{
  def this(productName: String) = this(productName, 0, true)
  def this(productName: String, price: BigDecimal) = this(productName, price, true)
  def add(){
    activeproductTable.insert(this)
  }
  def getPrice(productName: String) : BigDecimal ={
    val n = activeproductTable.where(ap => ap.productName === productName).single
    return n.price
  }

  def changePrice(newPrice: BigDecimal){
    update(activeproductTable)(ap=>
      where(ap.productName === this.productName)
        set (ap.price := newPrice))
  }

  def GetIdFromProduct():Long={
    val n = activeproductTable.where(ap => ap.productName === this.productName).single
    return n.id
  }

  def exist(): Boolean={
    return activeproductTable.exists(ap => ap.productName.matches(this.productName))
  }
}