package main.scala.classes

import classes.PoSWare
//import main.scala.classes.{Product, Location}
import collection.mutable.{Set,Map}

//Store Class
class Store() extends Location (){


  def RemProductStoreFront(Product:Product, Location:Location) {
    require(PoSWare.Product.contains(Product.id))
    require(PoSWare.Store.contains(Location.id))
    require(!PoSWare.Warehouse.contains(Location.id))
    require(Set(Set(Location->Product)).contains(PoSWare.ActiveProd.union(Set(Location->Product))))  //@grd4 ActiveProd ∪ {store ↦ item} ∈ Location ↔ Product
    require(PoSWare.ActiveProd.contains(Location->Product))
    PoSWare.ActiveProd -= Location->Product
    //TODO @act2 StoreAreaItemQuantity ≔ StoreAreaItemQuantity {(store ↦ Frontstore ↦ item) ↦ 0, (store ↦ Backstore ↦ item) ↦ 0}
    //StoreAreaItemQuantity.update(Map(Location,Location),0)
    PoSWare.Stock.update(Set(Location->Product),0)
  }

  //TODO  AddProductStoreFront Event
  def AddProductStoreFront(Product:Product, Location:Location, Stock:String) {
    require(PoSWare.Product.contains(Product.id))
    require(PoSWare.Store.contains(Location.id))
    require(PoSWare.Warehouse.contains(Location.id))
    require(Set(Set(Location->Product)).contains(PoSWare.ActiveProd.union(Set(Location->Product)))) //@grd4 ActiveProd ∪ {store ↦ item} ∈ Location ↔ Product
  }

  def LowStockAlertStore(Product:Product, Location:Location, level:Int, stock:Int) {
    require(PoSWare.Store.contains(Location.id))
    require(PoSWare.Product.contains(Product.id))
    require(level == PoSWare.ReorderLevel.apply(Set(Location->Product)))
    require(PoSWare.ActiveProd.contains(Location->Product))
    require(stock == PoSWare.Stock.apply(Set(Location->Product)))
    require(stock < level)
    PoSWare.Order.update(Set(Location->Product),level)
  }

  def SetStockAlertLevelStore(Product:Product, Location:Location, level:Int, user:String) {
    require(PoSWare.Store.contains(Location.id))
    require(PoSWare.Product.contains(Product.id))
    require(level > 0)
    require(PoSWare.ActiveProd.contains(Location->Product))
    require(PoSWare.Users.contains(user))
    require(PoSWare.Auth.apply(user) == "Manager")
    PoSWare.ReorderLevel.update(Set(Location->Product),level)
  }
}