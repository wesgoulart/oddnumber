package com.techtest

import scala.util.matching.Regex

trait Approach {

  private object Consts {
    val SEPARATOR_CSV_OR_TSV: Regex = "[,\\t]".r
  }

  def registerFile(it: Iterator[String]): Unit = {
    while (it.hasNext) {
      val line = it.next()
      val lineArray = Consts.SEPARATOR_CSV_OR_TSV.split(line)
      lineArray match {
        case Array(AsInt(key), AsInt(value)) =>
          register(key, value)
        case Array(AsInt(key)) =>
          register(key, 0)
        case Array(_, AsInt(value)) =>
          register(0, value)
        case _ => //ignore line
      }
    }
  }

  def register(key: Int, value: Int): Unit

  def getName: String
  def getMapOfOddValues: Map[Int, Int]

  protected def isOddNumber(n: Int): Boolean = n % 2 == 1

}
