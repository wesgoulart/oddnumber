package com.techtest

import com.amazonaws.services.s3.model.{ObjectListing, ObjectMetadata, S3Object, S3ObjectSummary}
import com.amazonaws.services.s3.{AmazonS3, AmazonS3Client}

import java.io.InputStream
import scala.annotation.tailrec
import scala.jdk.CollectionConverters.ListHasAsScala

object S3 {

  private val s3Client: AmazonS3 = AmazonS3Client.builder().build()

  def listFiles(bucketName: String, path: String): List[S3ObjectSummary] = {
    listAllObjects(s3Client.listObjects(bucketName, path))
  }

  @tailrec
  private def listAllObjects(listing: ObjectListing, list: List[S3ObjectSummary] = Nil): List[S3ObjectSummary] = {
    if (!listing.isTruncated) {
      listing.getObjectSummaries.asScala.toList ::: list
    } else {
      listAllObjects(s3Client.listNextBatchOfObjects(listing), listing.getObjectSummaries.asScala.toList ::: list)
    }
  }

  def readObject(objectSummary: S3ObjectSummary): S3Object = {
    readObject(objectSummary.getBucketName, objectSummary.getKey)
  }

  def readObject(bucketName: String, objectKey: String): S3Object = {
    s3Client.getObject(bucketName, objectKey)
  }

  def writeObject(bucketName: String, objectKey: String, is: InputStream, meta: ObjectMetadata): Any = {
    s3Client.putObject(bucketName, objectKey, is, meta)
  }

}
