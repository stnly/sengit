package main.scala.classes

class Transaction(val product: Set[(String)], val amount: BigDecimal) extends Basic {
  def this() = this(Set(""), 0.0)
}