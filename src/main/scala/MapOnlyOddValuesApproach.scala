package com.techtest

import scala.collection.mutable

/**
 * This class register a pair (key, value) as map key. If map does not have the key (by trying to remove it), the key is inserted into map.
 * If map does have the key, the key is removed, maintaining only odd key occurrences inside the map.
 */
class MapOnlyOddValuesApproach extends Approach {

  private val map: mutable.Map[(Int, Int), Int] = mutable.Map()

  override def getName: String = "map-only-odd-values"

  //This method uses synchornized for thread safety purpose
  //Register each key=value into a mutable map using tuple (key, value) as key, and value is each occurrence of tuple in files
  //The use of mutable map avoids many objects creation
  def register(key: Int, value: Int): Unit = this.synchronized {
    val keyTuple = (key, value)
    //If map does NOT have the keyTuple, map.remove returns None, so map.put(keyTuple, 1) is called
    //If map does have the keyTuple, map.remove returns Some(1), so map.put(keyTuple, 1) is NOT called
    val removed = map.remove(keyTuple)
    removed.orElse({
      map.put(keyTuple, 1)
    })
  }

  def getMapOfOddValues: Map[Int, Int] = {
    map.map(t => t._1._1 -> t._1._2).toMap
  }

}
