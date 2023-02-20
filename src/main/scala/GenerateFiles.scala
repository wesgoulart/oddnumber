package com.techtest

import com.amazonaws.services.s3.model.ObjectMetadata

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import scala.annotation.tailrec
import scala.util.Random

object GenerateFiles extends App {

  private val keysAndOddValues = (1 to 5000).map(n => (n, (math.random() * 100).toInt - 20))

  private def writeTemplateToS3(fileNumber: Int, keysAndValues: List[(Int, Int)]): Unit = {
    val header = "key,value\n"
    val fileLines = header + keysAndValues.map(line => {
      val key = line._1
      val value = if (line._2 < 0) {
        0
      } else line._2
      key + "," + value + "\n"
    }).mkString

    val fileName = "template/data-" + fileNumber + ".csv"

    val metadata = new ObjectMetadata()
    metadata.setContentType("text/csv")
    S3.writeObject("wesgoulart-vigil", fileName, new ByteArrayInputStream(fileLines.getBytes(StandardCharsets.UTF_8)), metadata)
  }


  keysAndOddValues.zipWithIndex.groupBy(k => 1 + (k._2 / 1000)).map(gk => {
    val keysAndValues = gk._2.map(_._1).toList

    writeTemplateToS3(gk._1, keysAndValues)

    val csv = (math.random() * 2).toInt == 0
    val header = "asdasdasd" + (if (csv) "," else "\t") + "defedfdefedf\n"
    val fileLines = header + generateLines(keysAndValues).map(line => {
      val key = line._1
      val value = if (line._2 < 0) {
        ""
      } else line._2
      key + (if (csv) "," else "\t") + value + "\n"
    }).mkString
    val fileName = "input/data-" + gk._1 + (if (csv) ".csv" else ".tsv")

    val metadata = new ObjectMetadata()
    metadata.setContentType("text/" + (if (csv) "csv" else "tsv"))
    S3.writeObject("wesgoulart-vigil", fileName, new ByteArrayInputStream(fileLines.getBytes(StandardCharsets.UTF_8)), metadata)
  })

  @tailrec
  private def generateLines(listBase: List[(Int, Int)], acc: List[(Int, Int)] = Nil): List[(Int, Int)] = {
    if (listBase.isEmpty) Random.shuffle(acc)
    else {
      val head = listBase.head
      val oddNumberOfOccurrences: Int = generateEvenNumber(0, 7) + 1
      val evenNumberOfOccurrences: Int = generateEvenNumber(2, 20)

      val listOdd = (1 to oddNumberOfOccurrences).map(_ => head).toList
      val litEven = (1 to evenNumberOfOccurrences).map(_ => (head._1, head._2 + (1 + math.random()).toInt)).toList
      generateLines(listBase.tail,
        listOdd :::
          litEven :::
          acc
      )
    }
  }

  private def generateEvenNumber(min: Int, max: Int): Int = {
    val n = min + (math.random() * (max - min)).toInt
    if (n % 2 == 0) n else n + 1
  }

}
