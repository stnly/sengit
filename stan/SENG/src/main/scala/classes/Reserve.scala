package main.scala.classes

import main.scala.classes.Database._
import org.squeryl.PrimitiveTypeMode._
import java.util.{Calendar, Date}
import org.squeryl.Table

class Reserve (val memberName: String,  var pickupDate: Date, val productName: String, var qty: Long) extends Basic{
  def this() = this("", Calendar.getInstance().getTime, "", 0)
  def add(memberName: String,pickupDate: Date, productName: String, itemQty: Long){

    //check if member already has reserved product
    //if(reserveTable.exists(r => r.memberName.matches(memberName) and r.productName.matches(productName))){
    //  change(memberName, productName, itemQty)
    //} else {
      reserveTable.insert(new Reserve(memberName, pickupDate, productName, qty))
    //}
  }

  def change(memberName: String, productName:String,  newQty : Long){
    if (newQty == 0){
      removeProduct(memberName, productName)
    } else {
    update(reserveTable)(r =>
      where(r.productName === productName)
      set (r.qty := newQty))
    }
  }

  def removeProduct(memberName: String, productName:String){
    reserveTable.deleteWhere(r => (r.memberName === memberName and r.productName === productName))
  }

  def cancel(memberName: String){
    reserveTable.deleteWhere(r => (r.memberName === memberName))
  }

  def buy(){
    /*
    calcluateTotal
    total = 0;
       for(r <- {from (reserveTable) (r => where(member === r.memberName) select(r))} ){
              from (Product)((p) =>
                where( p.productName === r.productName) select(p))
              total = total + p.price
   }
    */
    val prodInTransaction = Set("")
    val total = 100
    transactionTable.insert(new Transaction(prodInTransaction, total))
  }

 def printReservationByMember(member : String){
   for(r <- {from (reserveTable) (r => where(member === r.memberName) select(r))} ){
     println(r.id+" "+r.memberName+" "+r.pickupDate+" "+r.productName + " " + r.qty)
   }
 }
}
