package classes
import java.util.ArrayList
import java.util.Date

class Trolley (trolOwner:Customer) {
  val owner = trolOwner
  var items = new ArrayList[Product]
  def addProductToTrolley(newProduct:Product) {
    this.items.add(newProduct)
  }
}

class Customer() {
	def addTrolley(newTrolley:Trolley){
		var trolley = newTrolley
	}
	def removeTrolley{
	
	}
	def checkOut(Items:Trolley){
		
	}
	def joinMemberShip {
		
	}
	class Member(memberName:String,Contact:Int,Points:Int) extends Customer() {
		var name: String = memberName
		var contact: Int = Contact
		var points: Int = Points
	}
}

class Purchase(Items:Trolley,TotalCost:Int) {
  val items = Items
  val cost = TotalCost
}

class Reserve (Items:Trolley,PickUpDate:Date) {
	val items = Items
	val pickUpDate = PickUpDate
}