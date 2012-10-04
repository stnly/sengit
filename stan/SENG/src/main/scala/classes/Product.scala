package main.scala.classes

import org.squeryl.PrimitiveTypeMode._
import java.util.{Calendar, Date}
import main.scala.classes.Database._
import org.squeryl.dsl.ManyToOne
/*
 * Product Class
 * Queries:
 *
 *
 *
 * Queries needed;
 *
 *
 * Usage:
 *
 *
 *
 */

class Product (val productName: String, val locationId: Long, val expiryDate: Date, var price: BigDecimal) extends Basic{
  lazy val locations: ManyToOne[Location] = locationToProduct.right(this)
  def this(productName: String, expiryDate: Date, price: BigDecimal) = this(productName,0, expiryDate, price)

}

class ActiveProduct (val productName: String, var price: BigDecimal, var count: Long, var active: Boolean) extends Basic{
  def this(productName: String, price: BigDecimal) = this(productName, price, 0, true)
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



  def productAtLocationExpired (locationId: Long) : List[Product] = {
    val products = from(locationTable, productTable)((l,p)=>
      where(l.id === locationId
        and l.id === p.locationId
        and p.productName === this.productName)
        //check expiry date gt getCalendar.getTime()
        select(p))
    return products.toList
  }

}