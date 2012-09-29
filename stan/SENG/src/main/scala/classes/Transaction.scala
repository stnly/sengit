package main.scala.classes
import org.squeryl.PrimitiveTypeMode._
import main.scala.classes.Database._

class Transaction(val product: Set[(String)], val amount: BigDecimal) extends Basic {
  def this() = this(Set(""), 0.0)
  //one to many with products
  //lazy val productTable : OneToMany[Product] = Database.transactionToProduct.left(this)

  def add (product: Set[(String)],amount: BigDecimal)   {
    transactionTable.insert(new Transaction(product, amount))
  }

}