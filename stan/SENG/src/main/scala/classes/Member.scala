package main.scala.classes

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._
import main.scala.classes.Database._

class Member (var name: String, var points: Long) extends Basic {
  def this(name: String) = this(name, 0)

  //return true when entry is added correctly
  def add() :Boolean = {
    if (memberTable.exists(m => m.name.matches(name)))
      return false
    memberTable.insert(this)
    return true;
  }

  def remove(name: String) :Boolean = {
    if (!memberTable.exists(m => m.name.matches(name)))
      return false
    memberTable.deleteWhere(m=> m.name === name)
    return true;
  }

  def checkPoints():Long = {
            /*
    val point: Long =
      from(memberTable)(m =>
        where(m.name === name) )
              */
    val u = memberTable.where(m => m.id === this.id).single

    return u.points
  }



  def addPoint(points : Long){
    update(memberTable)(m=>
    where (m.name === name)
    set(m.points := m.points plus points))
  }

  def removePoint(points : Long){
    update(memberTable)(m=>
      where (m.name === name)
        set(m.points := m.points minus points))
  }
}

