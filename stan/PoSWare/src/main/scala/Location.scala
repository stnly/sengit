/**
 * Created with IntelliJ IDEA.
 * User: stan
 * Date: 25/09/12
 * Time: 8:39 PM
 * To change this template use File | Settings | File Templates.
 */
class Location(LocName: String, LocAddress: String, LocOwner: String) {

  require(!LOCATION.contains(LocName))
  //Initialise LOCATION Set in PoSWare_ctx
  var LOCATION:Set[(String)] = Set()
  //Initialise Store Set in PoSWare machine
  var Store:Set[(String)] = Set()
  //Initialise location Set in PoSWare machine
  var Location:Set[(String)] = Set()
  //Initialise Warehouse Set in PoSWare machine
  var Warehouse:Set[(String)] = Set()

  var name: String = LocName
  var address: String = LocAddress
  var Type : Char = 0
  var owner: String = LocOwner

  def becomeStore : Location = new Store()
  def becomeWarehouse : Location = new Warehouse()

  def AddLocation {
    require(!Location.contains(name))
    Location += (name)
  }

  class Store() extends Location (name, address, owner) {
    def AddStore {
      require(Location.contains(name) && !Store.contains(name) && !Warehouse.contains(name))
      Store += (name)
      this.Type = 'S'
      var backStore = new BackStore()
    }
  }

  class Warehouse() extends Location (name, address, owner) {
    def AddWarehouse {
      require(Location.contains(name) && !Warehouse.contains(name) && !Store.contains(name))
      Warehouse += (name)
      this.Type = 'W'
    }
  }

  class BackStore extends Location(name, address, owner) {
    require(Location.contains(name) && Store.contains(name))
    this.Type = 'B'
  }

  class Shelf extends Location(this.name, this.address, this.owner) {
    require(this.Type == 'S' || this.Type == 'W' || this.Type == 'B' )
  }

}

class Shelf (ShelfID: String) {
  require(ShelfID != 'a')

}

class BackStore() {
}