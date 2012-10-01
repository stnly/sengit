package main.scala.classes
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import main.scala.classes.Database._

class Transaction (val user: String, member: String) extends Basic {
  def this() = this("","")
  def newTransaction (user: String, member: String) : Long ={
    transactionTable.insert(new Transaction(user, member ))
    println("creating new transaction: "+ id)
    return this.id
  }
  def voidTransaction(id : Long) {
    //remove from database
    purchaseTable.deleteWhere(p => p.transactionId === id)
    transactionTable.deleteWhere((p => p.id === id))
  }

  def checkOut(id : Long){
    for (pur <- from(purchaseTable)(pur => where(pur.transactionId === id)select(pur))) {
      println(pur.productName)
      from(productTable)(p => where(p.id === pur.productId) select(p))

      //from(activeProductTable)(ap => where(p.Product === ap.productName) select(ap))
      //total += ap.price;
    }
    //calculate cost
  }

  def isProductInTransaction(productId: Long) :Boolean = {
    if (purchaseTable.exists(p => p.productId == productId))
      return true
    return false
  }

  def transactionExists(transactionId: Long) :Boolean = {
    if (transactionTable.exists(t => t.id == transactionId))
      return true
    return false
  }

  def getProductsInTransaction(transactionId: Long) {
    from(purchaseTable)(p=> where(p.transactionId === transactionId) select(p))
  }


  def getTransactionUser(transactionId: Long) :String = {
    var name = ""
    for (t <- from(transactionTable)(t => where(t.id === transactionId)select(t))) {
      name = t.user
      //from(activeProductTable)(ap => where(p.Product === ap.productName) select(ap))
      //total += ap.price;
    }
    return name
    //val u = transactionTable.where(t => t.id === transactionId).single
    //return u.user
  }
}
  //transaction to product
class Purchase(val transactionId: Long, val productName: String, var productId: Long, val active: Boolean){
  def this() = this(0, "", 0, true)


    //AddProdTrolley
  def addProductToTransaction(id: Long, productName: String, productId: Long){
      //product table
      purchaseTable.insert(new Purchase(id, productName, productId, true))
  }

    // RemProdTrolley
  def removeProductFromTransaction(id: Long, productName: String, productId: Long){
      purchaseTable.deleteWhere(p=>
        p.transactionId === id and p.productName === productName and p.productId === productId )
  }
}
