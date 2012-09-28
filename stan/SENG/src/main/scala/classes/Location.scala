package main.scala.classes

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations
import org.squeryl._
import adapters.H2Adapter
import java.util.{Calendar, Date}
import java.sql.Timestamp
import Database.locationTable
import LocationType._

class Location (val locationName:String, val locationType:LocationType) extends BasicEntry {


  def this() = this("", LocationType.location)

  val AddLocation = (locationName:String) => {
    val check = locationTable.exists(l => l.locationName.matches(locationName))
    require(!check)
    locationTable.insert(new Location(locationName,LocationType.location))
  }

}