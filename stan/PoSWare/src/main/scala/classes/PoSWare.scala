package classes

object PoSWare {

  //Initialise Store Set in PoSWare machine
  var Store:Set[(String)] = Set()

  //Initialise location Set in PoSWare machine
  var Location: Set[(String)] = Set()

  //Initialise Warehouse Set in PoSWare machine
  var Warehouse:Set[(String)] = Set()

  /**
   * AddLocation Event.
   * @param LocationID
   *                   LocationID:String should not exist in the set of PoSWare.Location
   */
  def AddLocation(LocationID:String):Location = {
    require(!PoSWare.Location.contains(LocationID))
    //PoSWare.Location += LocationID
    new PoSWare.Location(LocationID)
  }

  //AddStore Event
  def AddStore(LocationID:String):Location = {
    require(PoSWare.Location.contains(LocationID) &&
      !PoSWare.Store.contains(LocationID) &&
      !PoSWare.Warehouse.contains(LocationID))
    new Backstore()
    new PoSWare.Store(LocationID)
  }


  //AddWarehouse Event
  def AddWarehouse(LocationID:String):Location = {
    require(PoSWare.Location.contains(LocationID) &&
      !PoSWare.Store.contains(LocationID) &&
      !PoSWare.Warehouse.contains(LocationID))
    new Warehouse(LocationID)
    //PoSWare.Warehouse += LocationID
    //new Location(LocationID)
  }

  //Location Class
  class Location (LocationID:String) {
    val ID : String  = LocationID    //Needed for Product. If can find a way to use LocationID instead then remove this.
    PoSWare.Location += LocationID
  }

  //Warehouse Class
  class Warehouse(LocationID:String) extends Location (LocationID:String){
    PoSWare.Warehouse += LocationID
  }

  //Store Class
  class Store(LocationID:String) extends Location (LocationID:String){
    PoSWare.Store += LocationID
  }

  //Backstore Class
  class Backstore {

  }

  //Initialise PRODUCT Set in PoSWare_ctx context
  //var PRODUCT:Set[(String)] = Set()

  //Initialise Product Set in PoSWare machine
  var Product:Set[(String)] = Set()

  //Initialise ActiveProd Set in PoSWare machine
  var ActiveProd:Set[(PoSWare.Location,PoSWare.Product)] = Set()

  //Initialise Stock Set in PoSWare machine
  var Stock:Set[(Set[(PoSWare.Location,Product)],Int)] = Set()

  //AddProductList Event
  def AddProductList(ProductID:String):Product = {
    require(!Product.contains(ProductID))
    new Product(ProductID)
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
    require(PoSWare.Location.contains(Location.ID))
    require(PoSWare.Warehouse.contains(Location.ID))
    require(!ActiveProd.contains(Location->Product))
    require(stock == 0)
    //#!#require((Location->Product.ID).eq(PoSWare.Location.contains(Location.ID)->Product.ID))  //@grd4 in AddProductLocation Event. (Verify the math)
    require(!PoSWare.Store.contains(Location.ID))
    ActiveProd += (Location->Product)
    Stock = Set(ActiveProd->stock)
  }

  class Product (ProductID:String) {
    val ID = ProductID
    Product += ProductID
  }

}