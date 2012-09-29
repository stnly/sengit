package main.scala.classes

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations
import org.squeryl._
import adapters.H2Adapter
import java.util.{Calendar, Date}
import java.sql.Timestamp
import main.scala.classes.Database._
import LocationType._

class Location (val locationName: String, val locationType: LocationType) extends Basic {


  def this() = this("", LocationType.location)

  val add = (name: String) => {
    require(!locationTable.exists(l => l.locationName.matches(name)))
    locationTable.insert(new Location(name,LocationType.location))
  }

  def printAll() {
    for (l <- from(locationTable)(a => select(a))) {
      println(l.id + " " + l.locationName + " " + l.locationType)
    }
  }

}