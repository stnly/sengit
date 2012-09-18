import classes.Location

object main {
	def main (args: Array[String]) {
	  var A = new Location ("A place", "Hornsby", "Kevin")
	  var B = new Location ("B place", "Eastwood", "Dave")
	  println(A.name + " is at " + A.address + " own by " + A.owner)
	  println(B.name + " is at " + B.address + " own by " + B.owner)
	  
	  A = A.becomeStore
	  B = B.becomeWarehouse
	  if(A.Type == 'S'){
		println(A.name + " is a store")
	  }
	  if(B.Type == 'W')
	    println(B.name + " is a warehouse")
	}
}