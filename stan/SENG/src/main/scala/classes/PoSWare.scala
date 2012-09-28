package classes
import main.scala.classes._
import collection.mutable.{Set,Map}
import collection.mutable

object PoSWare {

  //Initialise Store Set in PoSWare machine
  var Store:Set[(String)] = Set()

  //Initialise location Set in PoSWare machine
  var Location:Set[(String)] = Set()

  //Initialise Warehouse Set in PoSWare machine
  var Warehouse:Set[(String)] = Set()

  //Initialise PRODUCT Set in PoSWare_ctx context
  //var PRODUCT:Set[(String)] = Set()

  //Initialise Product Set in PoSWare machine
  var Product:Set[(String)] = Set()

  //Initialise ActiveProd Set in PoSWare machine
  var ActiveProd:Set[(Location,Product)] = Set()

  //Initialise Stock Set in PoSWare machine
  var Stock:Map[Set[(Location,Product)],Int] = Map()

  var Order:Map[Set[(Location,Product)], Int] = Map()

  var ReorderLevel:Map[Set[(Location,Product)], Int] = Map()


  /**
   * AddLocation Event.
   * @param LocationID
   *                   LocationID:String should not exist in the set of PoSWare.Location
   */
  /*def AddLocation(LocationID:String):Location = {
    require(!PoSWare.Location.contains(LocationID))
    PoSWare.Location += LocationID
    new Location(LocationID)
  }*/
  val AddLocation = (ID:String) => {
    require(!PoSWare.Location.contains(ID))
    PoSWare.Location += ID
    new Location(ID)
  }

  //TODO extension in WarehouseR1
  //AddStore Event
  def AddStore(LocationID:String):(Location,Location) = {
    require(PoSWare.Location.contains(LocationID))
    require(!PoSWare.Store.contains(LocationID))
    require(!PoSWare.Warehouse.contains(LocationID))
    PoSWare.Store += LocationID
    (new Backstore(LocationID), new Store(LocationID))
  }

  //AddWarehouse Event
  def AddWarehouse(LocationID:String):Warehouse = {
    require(PoSWare.Location.contains(LocationID))
    require(!PoSWare.Store.contains(LocationID))
    require(!PoSWare.Warehouse.contains(LocationID))
    PoSWare.Warehouse += LocationID
    new Warehouse(LocationID)
  }

  //StoreArea ∈ Store ↔ STORELOCATION
  var StoreArea:Map[Store,String] = Map()
  //	StoreAreaItem ∈ StoreArea ↔ Product
  var StoreAreaItem:Map[Map[Store,String], Product] = Map()
  //StoreAreaItemQuantity ∈ StoreAreaItem → ℕ
  var StoreAreaItemQuantity:Map[Map[Location,Store],Int] = Map()

  var Users:Set[(String)] = Set()
  var Auth:Map[String,String] = Map()

  //Backstore Class
  class Backstore(LocationID:String) extends Location (LocationID:String){

  }

  //AddProductList Event
  /*def AddProductList(ProductID:String):Product = {
    require(!Product.contains(ProductID))
    Product += ProductID
    new Product(ProductID)
  }*/
  val AddProductList = (ID:String) => {
    require(!Product.contains(ID))
    Product += ID
    new Product(ID)
  }

  /**
   * AddProductLocation Event
   * I'm not sure if it is meant to take in a Product class and Location class in respect to the Event-B.
   * But it is what I did.
   * @param Product
   *                A Product Class
   * @param Location
   *                A Location Class
   * @param stock
   *                An Integer, should be 0.
   */
  def AddProductLocation(Product:Product, Location:Location, stock:Int) {
    require(PoSWare.Product.contains(Product.ID))
    //require(PoSWare.Location.contains(Location.ID))
    require(PoSWare.Warehouse.contains(Location.ID))
    require(!ActiveProd.contains(Location->Product))
    require(stock == 0)
    //@grd5 {location ↦ item} ∈ Location ↔ Product
    //require((Location->Product.ID).eq(PoSWare.Location.contains(Location.ID)->Product.ID))
    require(!PoSWare.Store.contains(Location.ID))
    ActiveProd += (Location->Product)
    Stock += Set(Location->Product)->stock

    PoSWare.Order += (Set(Location->Product))->0
    PoSWare.ReorderLevel += (Set(Location->Product))->0
  }

}
