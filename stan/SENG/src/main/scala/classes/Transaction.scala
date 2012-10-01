package main.scala.classes
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import main.scala.classes.Database._

class Transaction (val user: String, val member: String, var active: Boolean) extends Basic {
  def this(user: String, member:String) = this(user,member, true)
  def newTransaction () : Long ={
    transactionTable.insert(this)
    //println("creating new transaction: " + this.id)
    return this.id
  }
  def voidTransaction(id : Long) {
    //remove from database
    purchaseTable.deleteWhere(p => p.transactionId === id)
    transactionTable.deleteWhere((p => p.id === id))
  }
  def checkOut(){
    update(transactionTable)(t=>
    where(t.id === this.id)
    set(t.active := false) )
  }

  def calculateTotal() :BigDecimal ={
    var total: BigDecimal = 0
    for (pur <- from(purchaseTable)(pur => where(pur.transactionId === id)select(pur))) {
      val n = productTable.where(p => p.id === pur.productId).single
      total = total + n.price
    }
    return total
  }

  def isProductInTransaction(productId: Long) :Boolean = {
    if (purchaseTable.exists(p => p.productId == productId))
      return true
    return false
  }

  def transactionExists() :Boolean = {
    if (transactionTable.exists(t => t.id == this.id))
      return true
    return false
  }

  def getProductsInTransaction(transactionId: Long) {
    from(purchaseTable)(p=> where(p.transactionId === transactionId) select(p))
  }


  def getTransactionUser() :String = {
    val u = transactionTable.where(t => t.id === this.id).single
    return u.user
  }

  def addProductToTransaction(productId: Long){
    //product table
    purchaseTable.insert(new Purchase(this.id, productId))
  }

  // RemProdTrolley
  def removeProductFromTransaction(id: Long, productName: String, productId: Long){
    purchaseTable.deleteWhere(p=>
      p.transactionId === id and p.productId === productId )
  }

}
  //transaction to product
class Purchase(val transactionId: Long, var productId: Long){
  def this() = this(0, 0)
}
