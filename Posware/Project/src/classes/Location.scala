package classes
import java.util.ArrayList

class Location(LocName: String, LocAddress: String, LocOwner: String) {
	var name: String = LocName
	var address: String = LocAddress
	var Type : Char = 0
	var owner: String = LocOwner
	var lanes = new ArrayList[Lane]
	def becomeStore : Location = new Store ()
	def becomeWarehouse : Location = new Warehouse ()
	class Store() extends Location (this.name,this.address,this.owner) {
		this.Type = 'S'
		var backStore = new BackStore()
	}
	class Warehouse() extends Location (this.name,this.address,this.owner) {
		this.Type = 'W'
	}
}

class Lane () {
  var shelves = new ArrayList[Shelf]
}

class Shelf () {
	var levels = new ArrayList[Level]
}

class Level () {
	var items = new ArrayList[Product]
	def addItem (newProduct:Product) {
	  items.add(newProduct)
	}
	def removeItem (removeProduct:Product) {
	  
	}
	
}

class BackStore() {
  
}