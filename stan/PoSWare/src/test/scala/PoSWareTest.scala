import classes.PoSWare

import org.scalatest._

class PoSWareTest extends FunSuite {

  test("Empty Location Set") {
    assert(PoSWare.Location.size == 0)
  }

  test("Adding a Location") {
    PoSWare.AddLocation("A")
    assert(PoSWare.Location.contains("A"))
    assert(PoSWare.Location.size == 1)
  }

  test("Adding multiple Locations") {
    PoSWare.AddLocation("B")
    assert(PoSWare.Location.contains("B"))
    assert(PoSWare.Location.size == 2)
    PoSWare.AddLocation("C")
    assert(PoSWare.Location.contains("C"))
    assert(PoSWare.Location.size == 3)
  }

  //Made to fail
  test("Adding repeated Locations") {
    PoSWare.AddLocation("A")
    assert(PoSWare.Location.size == 3)
    PoSWare.AddLocation("B")
    assert(PoSWare.Location.size == 3)
    PoSWare.AddLocation("C")
    assert(PoSWare.Location.size == 3)
  }

  test("Empty Store Set") {
    assert(PoSWare.Store.size == 0)
  }

  test("Adding a Store"){
    assert(PoSWare.Location.contains("A"))
    PoSWare.AddStore("A")
    assert(PoSWare.Store.contains("A"))
    assert(PoSWare.Store.size == 1)
  }

  test("Adding multiple Stores") {
    assert(PoSWare.Location.contains("B"))
    PoSWare.AddStore("B")
    assert(PoSWare.Store.contains("B"))
    assert(PoSWare.Store.size == 2)
    assert(PoSWare.Location.contains("C"))
    PoSWare.AddStore("C")
    assert(PoSWare.Store.contains("C"))
    assert(PoSWare.Store.size == 3)
  }

  //Made to fail
  test("Adding repeated Stores") {
    assert(PoSWare.Store.contains("A"))
    PoSWare.AddStore("A")
    assert(PoSWare.Store.size == 3)
    PoSWare.AddStore("B")
    assert(PoSWare.Store.size == 3)
    PoSWare.AddStore("C")
    assert(PoSWare.Store.size == 3)
  }



}
