package classes

import collection.mutable.{Set,Map}

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
  var ActiveProd:Set[(PoSWare.Location,PoSWare.Product)] = Set()

  //Initialise Stock Set in PoSWare machine
  var Stock:Map[Set[(PoSWare.Location,PoSWare.Product)],Int] = Map()

  var Order:Map[Set[(PoSWare.Location,PoSWare.Product)], Int] = Map()

  var ReorderLevel:Map[Set[(PoSWare.Location,PoSWare.Product)], Int] = Map()

  /**
   * AddLocation Event.
   * @param LocationID
   *                   LocationID:String should not exist in the set of PoSWare.Location
   */
  def AddLocation(LocationID:String):Location = {
    require(!PoSWare.Location.contains(LocationID))
    PoSWare.Location += LocationID
    new PoSWare.Location(LocationID)
  }

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

  //Location Class
  class Location (LocationID:String) {
    val ID : String  = LocationID    //Needed for Product. If can find a way to use LocationID instead then remove this.

  }

  //Warehouse Class
  class Warehouse(LocationID:String) extends Location (LocationID:String){

    def RestockWH(Product:Product, Location:Location, addAmount:Int) {
      require(PoSWare.Warehouse.contains(Location.ID))
      require(PoSWare.Product.contains(Product.ID))
      require(PoSWare.ActiveProd.contains(Location->Product))
      PoSWare.Stock.update(Set(Location->Product),PoSWare.Stock.apply(Set(Location->Product))+addAmount)
      PoSWare.Order.update(Set(Location->Product),0)
    }

    def SupplierOrder(Product:Product, Location:Location, amount:Int){
      require(PoSWare.Warehouse.contains(Location.ID))
      require(PoSWare.Product.contains(Product.ID))
      require(PoSWare.ActiveProd.contains(Location->Product))
      require(amount.equals(Int) && amount > 0)
      PoSWare.Order.update(Set(Location->Product),amount)
    }

    def SetStockAlertLevel(Product:Product, Location:Location, level:Int) {
      require(PoSWare.Warehouse.contains(Location.ID))
      require(PoSWare.Product.contains(Product.ID))
      require(level.equals(Int) && level > 0)
      require(PoSWare.ActiveProd.contains(Location->Product))
      PoSWare.ReorderLevel.update(Set(Location->Product),level)
    }

    def LowStockAlert(Product:Product, Location:Location, level:Int, stock:Int) {
      require(PoSWare.Warehouse.contains(Location.ID))
      require(PoSWare.Product.contains(Product.ID))
      require(PoSWare.ActiveProd.contains(Location->Product))
      require(stock == PoSWare.Stock.apply(Set(Location->Product)))
      require(stock < level)
      require(level == PoSWare.ReorderLevel.apply(Set(Location->Product)))
      PoSWare.Order.update(Set(Location->Product),level)
    }

  }
  //val STORELOCATION:String = "0,1,2"
  var StoreArea:Set[(PoSWare.Store,String)] = Set()
  //var StoreAreaItem:Set[((Store,Backstore),Product)] = Set("a")
  //Store Class
  class Store(LocationID:String) extends Location (LocationID:String){
    def RemProductStoreFront(Product:Product, Store:Store) {
      require(PoSWare.Product.contains(Product.ID))
      require(PoSWare.Store.contains(Store.ID))
      require(!PoSWare.Warehouse.contains(Store.ID))
      //require(mutable.Set(PoSWare.Location->PoSWare.Product).contains(ActiveProd))
      //require((ActiveProd.union(mutable.Set(Store->Product)).contains(Store->Product)))   //@grd4 in RemProductStoreFront
      require(PoSWare.ActiveProd.contains(Store->Product))
      ActiveProd -= Store->Product
      //@act2 StoreAreaItemQuantity ≔ StoreAreaItemQuantity  {(store ↦ Frontstore ↦ item) ↦ 0, (store ↦ Backstore ↦ item) ↦ 0}
      PoSWare.Stock.update(Set(Store->Product),0)
    }
  }

  //Backstore Class
  class Backstore(LocationID:String) extends Location (LocationID:String){

  }

  //AddProductList Event
  def AddProductList(ProductID:String):Product = {
    require(!Product.contains(ProductID))
    Product += ProductID
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
    //require(PoSWare.Location.contains(Location.ID))
    require(PoSWare.Warehouse.contains(Location.ID))
    require(!ActiveProd.contains(Location->Product))
    require(stock == 0)
    //#!#require((Location->Product.ID).eq(PoSWare.Location.contains(Location.ID)->Product.ID))  //@grd5 in AddProductLocation Event. (Verify the math)
    require(!PoSWare.Store.contains(Location.ID))
    ActiveProd += (Location->Product)
    Stock += Set(Location->Product)->stock

    PoSWare.Order += (Set(Location->Product))->0
    PoSWare.ReorderLevel += (Set(Location->Product))->0
  }

  class Product (ProductID:String) {
    val ID = ProductID

  }

}