package com.techtest

import scala.util.Try

object AsInt {

  def unapply(str: String): Option[Int] = Try(str.toInt).toOption

}
