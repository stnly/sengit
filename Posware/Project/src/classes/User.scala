package classes

class User(userName:String,userType:Char) {
	var name = userName
	var Type : Char = userType
	class Manager(userName:String,userType:Char) extends User(userName,userType) {
	  
	  
		class Admin(userName:String,userType:Char) extends Manager(userName,userType) {
		  
		}
	}
}
