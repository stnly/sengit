package controls
import classes.Location


object Admin {
	def createLocation(LocName: String, LocAddress: String, LocOwner: String) {
	  var location = new Location(LocName, LocAddress, LocOwner)
	}
	
	def setLocation(Type:Int) {
	  if(Type == 0){
	    
	  }
	}
	def assignManager() {
	  
	}
	def removeManager() {
	  
	}
}