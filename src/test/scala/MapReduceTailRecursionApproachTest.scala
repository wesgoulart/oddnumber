package com.techtest

import org.scalatest.funsuite.AnyFunSuite

class MapReduceTailRecursionApproachTest extends AnyFunSuite {

  test("insert key 0, value 0 one time must return key (0, 0)") {
    val service = new MapReduceTailRecursionApproach()
    service.register(0, 0)
    val result = service.getMapOfOddValues

    assert(result.size == 1)
    assert(result.contains(0))
  }

  test("insert key 0, values 0 and 1 must NOT return key 0") {
    val service = new MapReduceTailRecursionApproach()
    service.register(0, 0)
    service.register(0, 0)
    val result = service.getMapOfOddValues

    assert(result.isEmpty)
    assert(!result.contains(0))
  }

  test("insert many keys parallel, odd keys register 4 times, even keys register 3 times, must maintain only even keys") {
    val service = new MapReduceTailRecursionApproach()
    val listInput = (1 to 100000).flatMap(i => {
      if (i % 2 == 0) {
        List((i, i))
      } else {
        List((i, 1), (i, 2), (i, 3), (i, 4))
      }
    }).toList

    listInput.foreach(input => service.register(input._1, input._2))
    val result = service.getMapOfOddValues

    assert(result.size == 50000)
    assert(result.contains(2))
    assert(result.contains(50000))
    assert(!result.contains(1))
    assert(!result.contains(1001))
  }

}
