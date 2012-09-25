import java.util.Date


/**
 * Created with IntelliJ IDEA.
 * User: stan
 * Date: 25/09/12
 * Time: 9:26 PM
 * To change this template use File | Settings | File Templates.
 */
class Product(ProdName:String,ProdDescription:String,Prodprice:Int,ProdExpDate:Date) {

  var Location = new Location("","","")
  //Initialise PRODUCT Set in PoSWare_ctx context
  var PRODUCT:Set[(String)] = Set()
  //Initialise Product Set in PoSWare machine
  var Product:Set[(String)] = Set()
  //Initialise ActiveProd Set in PoSWare machine
  var ActiveProd:Set[(Location,Product)] = Set()
  //Initialise Stock Set in PoSWare machine
  var Stock:Set[(Set[(Location,Product)],Int)] = Set(ActiveProd->0)

  var name: String = ProdName
  var description: String = ProdDescription
  var price: Int = Prodprice
  var amount: Int = 0
  var expiryDate: Date = ProdExpDate

  def AddProductList {
    require(PRODUCT.contains(ProdName) && !Product.contains(ProdName))
    Product += ProdName
  }
  def AddProductLocation(ProdLocation:Shelf){
    require(Product.contains(ProdName) && Location)
    this.amount += 1

  }
}
