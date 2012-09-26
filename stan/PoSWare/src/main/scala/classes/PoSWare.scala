package classes

object PoSWare {

  //Initialise Store Set in PoSWare machine
  var Store:Set[(String)] = Set()

  //Initialise location Set in PoSWare machine
  var Location: Set[(String)] = Set()

  //Initialise Warehouse Set in PoSWare machine
  var Warehouse:Set[(String)] = Set()

  //AddLocation Event
  def AddLocation(LocationID:String) {
    require(!PoSWare.Location.contains(LocationID))
    //PoSWare.Location += LocationID
    new PoSWare.Location(LocationID)
  }

  //AddStore Event
  def AddStore(LocationID:String) {
    require(PoSWare.Location.contains(LocationID) &&
      !PoSWare.Store.contains(LocationID) &&
      !PoSWare.Warehouse.contains(LocationID))
    new Store(LocationID)
    new Backstore()
  }


  //AddWarehouse Event
  def AddWarehouse(LocationID:String) {
    require(PoSWare.Location.contains(LocationID) &&
      !PoSWare.Store.contains(LocationID) &&
      !PoSWare.Warehouse.contains(LocationID))
    new Warehouse(LocationID)
  }

  //Location Class
  class Location (LocationID:String) {
    val ID : String  = LocationID    //Needed for Product. If can find a way to use LocationID instead then remove this.
    PoSWare.Location += LocationID
  }

  //Warehouse Class
  class Warehouse(LocationID:String) {
    PoSWare.Warehouse += LocationID
  }

  //Store Class
  class Store(LocationID:String) {
    PoSWare.Store += LocationID
  }

  //Backstore Class
  class Backstore {

  }
}

class Product (ProdID:String) {
  //Initialise PRODUCT Set in PoSWare_ctx context
  var PRODUCT:Set[(String)] = Set()
  //Initialise Product Set in PoSWare machine
  var Product:Set[(String)] = Set()
  //Initialise ActiveProd Set in PoSWare machine
  var ActiveProd:Set[(PoSWare.Location,Product)] = Set()
  //Initialise Stock Set in PoSWare machine
  var Stock:Set[(Set[(PoSWare.Location,Product)],Int)] = Set()

  var ID: String = ProdID

  def AddProductList(ProdID:String) {
    require(PRODUCT.contains(ProdID) && !Product.contains(ProdID))
    Product += ProdID
  }
  def AddProductLocation(ProdID:String,Location:PoSWare.Location, stock:Int){
    require(Product.contains(ProdID) &&
      PoSWare.Location.contains(Location.ID) &&
      PoSWare.Warehouse.contains(Location.ID) &&
      !ActiveProd.contains(Location->Product.this) &&
      stock == 0 &&
      (Location->ProdID).eq(PoSWare.Location.contains(Location.ID)->Product.this.ID) &&  //@grd4 in event AddProductLocation. (Verify please)
      !PoSWare.Store.contains(Location.ID))
    ActiveProd += (Location->Product.this)
    Stock = Set(ActiveProd->stock)
  }
}