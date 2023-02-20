package com.techtest

import com.amazonaws.services.s3.model.ObjectMetadata

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}
import scala.collection.parallel.CollectionConverters.IterableIsParallelizable
import scala.io.Source

object ApproachRunner {

  private val s3PathExtractor = "([^/]*)/(.*)".r

  def run(approach: Approach, inputS3Path: String, outputS3Path: String) = {
    val s3PathExtractor(bucketNameFromInput, inputPath) = inputS3Path
    val objectsSummary = S3.listFiles(bucketNameFromInput, inputPath)
    objectsSummary.par.foreach(summary => approach.registerFile(Source.fromInputStream(S3.readObject(summary).getObjectContent).getLines()))
    writeResultIntoS3(approach, outputS3Path)
  }


  private def writeResultIntoS3(approach: Approach, outputS3Path: String) = {
    val s3PathExtractor(bucketNameFromOutput, outputPath) = outputS3Path
    val baos = new ByteArrayOutputStream()
    baos.write("key\tvalue\n".getBytes)
    approach.getMapOfOddValues.foreach(kv => baos.write((kv._1 + "\t" + kv._2 + "\n").getBytes))
    val meta: ObjectMetadata = new ObjectMetadata()
    meta.setContentType("text/tsv")
    S3.writeObject(bucketNameFromOutput, outputPath + "/" + approach.getName + ".tsv", new ByteArrayInputStream(baos.toByteArray), meta)
  }

}
