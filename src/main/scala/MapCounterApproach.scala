package com.techtest

import scala.collection.mutable

/**
 * This class register a pair (key, value) as map key. This map increments the counter of occurrences of key, so, every registered key is kept inside map.
 */
class MapCounterApproach extends Approach {

  private val map: mutable.Map[(Int, Int), Int] = mutable.Map()

  override def getName: String = "map-counter"

  //This method uses synchornized for thread safety purpose
  //Register each key=value into a mutable map using tuple (key, value) as key, and value is each occurrence of tuple in files
  //The use of mutable map avoids many objects creation
  def register(key: Int, value: Int): Unit = this.synchronized {
    val keyTuple = (key, value)
    //put counter inside map, using keyTuple as key
    map.put(keyTuple, map.getOrElse(keyTuple, 0) + 1)
  }

  def getMapOfOddValues: Map[Int, Int] = {
    //Map containing all (key, value) -> occurrence of all lines from files. Must be filtered by odd occurrences and transformed again into a Map[Int, Int]
    map.filter(kv => isOddNumber(kv._2)).map(kv => kv._1._1 -> kv._1._2).toMap
  }

}
