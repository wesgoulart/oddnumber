package com.techtest

import org.scalatest.funsuite.AnyFunSuite

import scala.collection.parallel.CollectionConverters._

class MapCounterApproachTest extends AnyFunSuite {

  test("insert key 0, value 0 one time must return key (0, 0)") {
    val service = new MapCounterApproach()
    service.register(0, 0)

    val result = service.getMapOfOddValues
    assert(result.size == 1)
    assert(result.contains(0))
    assert(result.get(0).contains(1))
  }

  test("insert key 0, value 0 twice must NOT return key (0, 0)") {
    val service = new MapCounterApproach()
    service.register(0, 0)
    service.register(0, 0)

    val resultMapOdd = service.getMapOfOddValues
    assert(resultMapOdd.isEmpty)
    assert(!resultMapOdd.contains(0))
    assert(!resultMapOdd.get(0).contains(2))
  }

  test("insert many keys parallel, odd keys inserts twice values, even keys inserts one value, must maintain only even keys") {
    val service = new MapCounterApproach()

    val keys = (1 to 100000).toList

    keys.par.foreach(key => {
      if (key % 2 == 0) {
        service.register(key, key)
        service.register(key, key)
        service.register(key, key)
      } else {
        service.register(key, key)
        service.register(key, key)
        service.register(key, key)
        service.register(key, key)
      }
    })

    val resultMapOdd = service.getMapOfOddValues
    assert(resultMapOdd.size == 50000)
    assert(resultMapOdd.contains(2))
    assert(resultMapOdd.get(2).contains(3))
    assert(resultMapOdd.contains(50000))
    assert(resultMapOdd.get(50000).contains(3))
    assert(!resultMapOdd.contains(1))
    assert(!resultMapOdd.get(1).contains(4))
    assert(!resultMapOdd.contains(1001))
    assert(!resultMapOdd.get(1001).contains(4))
  }

}
