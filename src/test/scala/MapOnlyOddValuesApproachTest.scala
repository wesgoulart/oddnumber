package com.techtest

import org.scalatest.funsuite.AnyFunSuite

import scala.collection.parallel.CollectionConverters._

class MapOnlyOddValuesApproachTest extends AnyFunSuite {

  test("insert key 0, value 0 one time must return key (0, 0)") {
    val service = new MapOnlyOddValuesApproach()
    service.register(0, 0)

    val result = service.getMapOfOddValues
    assert(result.size == 1)
    assert(result.contains(0))
  }

  test("insert key 0, value 0 twice must NOT return key (0, 0)") {
    val service = new MapOnlyOddValuesApproach()
    service.register(0, 0)
    service.register(0, 0)

    val result = service.getMapOfOddValues
    assert(result.isEmpty)
    assert(!result.contains(0))
  }

  test("insert many keys parallel, odd keys register 4 times, even keys register 3 times, must maintain only even keys") {
    val service = new MapOnlyOddValuesApproach()
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

    val result = service.getMapOfOddValues
    assert(result.size == 50000)
    assert(result.contains(2))
    assert(result.contains(50000))
    assert(!result.contains(1))
    assert(!result.contains(1001))
  }

}
