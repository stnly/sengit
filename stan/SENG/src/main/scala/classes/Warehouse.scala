package main.scala.classes

//import classes.PoSWare
import main.scala.classes.Database._
import org.squeryl.PrimitiveTypeMode._
import main.scala.classes.LocationType._
import org.squeryl.Table

//import main.scala.classes.LocationType

//import main.scala.classes.{Product, Location}
import collection.mutable.{Set,Map}

//Warehouse Class
class Warehouse(locationName:String, locationType:LocationType) extends Location (locationName:String, locationType:LocationType){

  //AddWarehouse Event
  /*def AddWarehouse(LocationID:Long):Warehouse = {
    require(PoSWare.Location.contains(LocationID))
    require(!PoSWare.Store.contains(LocationID))
    require(!PoSWare.Warehouse.contains(LocationID))
    PoSWare.Warehouse += LocationID //add to database
    new Warehouse()
  } */
  def this() = this("", LocationType.location)

  override val add = (name:String) => {
    require(locationTable.exists(l => l.locationName.matches(name)))
    require(!warehouseTable.exists(l => l.locationName.matches(name)))
    require(!frontstoreTable.exists(l => l.locationName.matches(name)))
    warehouseTable.insert(new Warehouse(name,LocationType.warehouse))
  }

  override def printAll() {
    for (l <- from(warehouseTable)(a => select(a))) {
      println(l.id + " " + l.locationName + " " + l.locationType)
    }
  }

  //TODO Convert to Squeryl
  /*def RestockWH(Product:Product, addAmount:Int) {
    require(PoSWare.Warehouse.contains(this.id))
    require(PoSWare.Product.contains(Product.id))
    require(PoSWare.ActiveProd.contains(this->Product))
    PoSWare.Stock.update(Set(this->Product), PoSWare.Stock.apply(Set(this->Product)) + addAmount)
    PoSWare.Order.update(Set(this->Product), 0)
  }

  //TODO Convert to Squeryl
  def SupplierOrder(Product:Product, amount:Int){
    require(PoSWare.Warehouse.contains(this.id))
    require(PoSWare.Product.contains(Product.id))
    require(PoSWare.ActiveProd.contains(this->Product))
    require(amount > 0)
    PoSWare.Order.update(Set(this->Product), amount)
  }

  //TODO Convert to Squeryl
  def SetStockAlertLevel(Product:Product, level:Int) {
    require(PoSWare.Warehouse.contains(this.id))
    require(PoSWare.Product.contains(Product.id))
    require(level > 0)
    require(PoSWare.ActiveProd.contains(this->Product))
    PoSWare.ReorderLevel.update(Set(this->Product), level)
  }

  //TODO Convert to Squeryl
  def LowStockAlert(Product:Product, level:Int, stock:Int) {
    require(PoSWare.Warehouse.contains(this.id))
    require(PoSWare.Product.contains(Product.id))
    require(PoSWare.ActiveProd.contains(this->Product))
    require(stock == PoSWare.Stock.apply(Set(this->Product)))
    require(stock < level)
    require(level == PoSWare.ReorderLevel.apply(Set(this->Product)))
    PoSWare.Order.update(Set(this->Product), level)
  }*/

}