package main.scala.classes

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import main.scala.classes.Database._

class Member (var name: String, var points: Long) extends Basic {
  def this() = this("", 0)

  //return true when entry is added correctly
  def add(name: String) :Boolean = {
    if (memberTable.exists(m => m.name.matches(name)))
      return false
    memberTable.insert(new Member(name, 0))
    return true;
  }

  def remove(name: String) :Boolean = {
    if (!memberTable.exists(m => m.name.matches(name)))
      return false
    memberTable.deleteWhere(m=> m.name === name)
    return true;
  }


 /*
  def checkPoints(name: String):Long = {
    val point
    for(m <- from(memberTable)(m =>
      where(m.name === name) select(m)) {
      point = m.point
    }
    return point
  }
       */

  def addPoint(name : String, points : Long){
    update(memberTable)(m=>
    where (m.name === name)
    set(m.points := m.points plus points))
  }

  def removePoint(name : String, points : Long){
    update(memberTable)(m=>
      where (m.name === name)
        set(m.points := m.points minus points))
  }
}

