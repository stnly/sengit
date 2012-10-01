package main.scala.classes

import main.scala.classes.Database._
import org.squeryl.PrimitiveTypeMode._
import java.util.{Calendar, Date}
import org.squeryl.Table

//one to many with product
class Reserve (val memberName: String,  var pickupDate: Date) extends Basic {
  def this() = this("", Calendar.getInstance().getTime)

  def newReservation() {
    reserveTable.insert(this)
  }
  def addProduct(productId: Long){
    reservedproductTable.insert(new ReservedProduct(this.id, productId))
  }

  def removeProduct(productId: Long){
    reservedproductTable.deleteWhere(r => (r.reserveId === this.id and r.productId === productId))
  }

  def cancel(){
    reservedproductTable.deleteWhere(r => (r.reserveId === this.id))
    reserveTable.deleteWhere(r => (r.memberName === this.memberName))

  }

  def buy(clerkName: String): Long ={
   var t = new Transaction(clerkName, this.memberName)
    for(r <- {from (reservedproductTable) (r => where(this.id === r.reserveId) select(r))} ){
      t.addProductToTransaction(r.productId)
    }
    return t.id
  }

 def printReservationByMember(memberId : Long){
   for(r <- {from (reservedproductTable) (r => where(memberId === r.reserveId) select(r))} ){
     println(r.productId)
   }
 }
}
class ReservedProduct(var reserveId: Long, var productId: Long){
  def this() = this(0,0)
}

