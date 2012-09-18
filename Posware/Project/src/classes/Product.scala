package classes
import java.util.Date

class Product(ProdName:String,ProdDescription:String,Prodprice:Int,ProdExpDate:Date) {
	var name: String = ProdName
	var description: String = ProdDescription
	var price: Int = Prodprice
	var amount: Int = 0
	var expiryDate: Date = ProdExpDate
	def addProductLocation(ProdLocation:Shelf){
		this.amount += 1
		
	}
}