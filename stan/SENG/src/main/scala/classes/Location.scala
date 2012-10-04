package main.scala.classes

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations
import org.squeryl.dsl.OneToMany
import org.squeryl.adapters.H2Adapter
import java.util.{Calendar, Date}
import java.sql.Timestamp
import main.scala.classes.Database._
import LocationType._

class Location (val locationName: String, val locationType: LocationType) extends Basic {
  lazy val products: OneToMany[Product] = locationToProduct.left(this)
  def this(locationName: String) = this(locationName, LocationType.location)


  def getId(): Long ={
    val n = locationTable.where(l=>l.id === this.id).single
    return n.id
  }

  def add() {
    locationTable.insert(this)
  }

  def setLocAsStore() :Long = {
    //warehouseTable.insert()
    return 0   //warehouse id
  }

  def setLocAsBackstore() :Long = {
 //   warehouseTable.insert()
    return 0   //warehouse id
  }

  //def productsToRestock(productName: String) =
  //  from(locationTable, productTable)(l,p) =>
  //    where(l.id === this.id
  //    and p.productName === productName)
  //    select(p))

  def printAll() {
    for (l <- from(locationTable)(a => select(a))) {
      println(l.id + " " + l.locationName + " " + l.locationType)
    }
  }

}

