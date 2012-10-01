package main.scala.classes
import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import main.scala.classes.Database._

class Transaction (val product: String, val amount: BigDecimal) extends Basic {
  def this() = this("", 0.0)
  def newTransaction (product: String,amount: BigDecimal){
    transactionTable.insert(new Transaction(product, amount))
  }
  def voidTransaction(id : Long) {
    //remove from database
    purchaseTable.deleteWhere(p => p.transactionID === id)
    transactionTable.deleteWhere((p => p.id === id))
  }

  def checkOut(id : Long){
    var total = 0
    for (p <- from(purchaseTable)(p => where(p.transactionID === id)select(p))) {
      println(p.productName)

      //from(activeProductTable)(ap => where(p.Product === ap.productName) select(ap))
      //total += ap.price;
    }
  }
}
  //transaction to product
class Purchase(val transactionID: Long, val productName: String, var productId: Long, val active: Boolean){
  def this() = this(0, "", 0, true)


    //AddProdTrolley
  def addProductToTransaction(id: Long, productName: String, productId: Long){
      //product table
      purchaseTable.insert(new Purchase(id, productName, productId, true))
  }

    // RemProdTrolley
  def removeProductToTransaction(id: Long, productName: String, productId: Long){
      purchaseTable.deleteWhere(p=>
        p.transactionID === id and p.productName === productName and p.productId === productId )
  }
}
