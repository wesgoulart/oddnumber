package com.techtest

import scala.annotation.tailrec

class MapReduceTailRecursionApproach extends Approach {

  var registered: Int = 0
  private var list: List[(Int, Int)] = Nil

  override def getName: String = "map-reduce-tail-recursion"

  //This method uses synchornized for thread safety purpose
  //Register each key=value into a List as a tuple (key, value)
  //The use of immutable list here brings a concern about objects creation and memory consumption
  override def register(key: Int, value: Int): Unit = this.synchronized {
    list = (key, value) :: list
  }

  override def getMapOfOddValues: Map[Int, Int] = {
    //Reduces list using tail recursion, counting the number of occurences of each (key, value) tuple
    val reducedList: Map[(Int, Int), Int] = reduce(list)
    //Must be filtered by odd occurrences and transformed again into a Map[Int, Int]
    reducedList.filter(t => isOddNumber(t._2)).map(kv => kv._1._1 -> kv._1._2)
  }

  //The use of immutable map here brings a concern about objects creation and memory consumption
  @tailrec
  private def reduce(list: List[(Int, Int)], map: Map[(Int, Int), Int] = Map()): Map[(Int, Int), Int] = {
    if (list.isEmpty) map
    else {
      val key = list.head
      reduce(list.tail, map + (key -> (map.getOrElse(key, 0) + 1)))
    }
  }

}
