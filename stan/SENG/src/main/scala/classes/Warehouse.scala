package main.scala.classes

import classes.PoSWare
//import main.scala.classes.{Product, Location}
import collection.mutable.{Set,Map}

//Warehouse Class
class Warehouse(LocationID:String) extends Location (LocationID:String){

  def RestockWH(Product:Product, addAmount:Int) {
    require(PoSWare.Warehouse.contains(this.ID))
    require(PoSWare.Product.contains(Product.ID))
    require(PoSWare.ActiveProd.contains(this->Product))
    PoSWare.Stock.update(Set(this->Product), PoSWare.Stock.apply(Set(this->Product)) + addAmount)
    PoSWare.Order.update(Set(this->Product), 0)
  }

  def SupplierOrder(Product:Product, amount:Int){
    require(PoSWare.Warehouse.contains(this.ID))
    require(PoSWare.Product.contains(Product.ID))
    require(PoSWare.ActiveProd.contains(this->Product))
    require(amount > 0)
    PoSWare.Order.update(Set(this->Product), amount)
  }

  def SetStockAlertLevel(Product:Product, level:Int) {
    require(PoSWare.Warehouse.contains(this.ID))
    require(PoSWare.Product.contains(Product.ID))
    require(level > 0)
    require(PoSWare.ActiveProd.contains(this->Product))
    PoSWare.ReorderLevel.update(Set(this->Product), level)
  }

  def LowStockAlert(Product:Product, level:Int, stock:Int) {
    require(PoSWare.Warehouse.contains(this.ID))
    require(PoSWare.Product.contains(Product.ID))
    require(PoSWare.ActiveProd.contains(this->Product))
    require(stock == PoSWare.Stock.apply(Set(this->Product)))
    require(stock < level)
    require(level == PoSWare.ReorderLevel.apply(Set(this->Product)))
    PoSWare.Order.update(Set(this->Product), level)
  }

}