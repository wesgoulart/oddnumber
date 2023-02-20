package com.techtest

object MapCounterApproachMain {

  def main(args: Array[String]): Unit = {
    val inputS3Path: String = args(0)
    val outputS3Path: String = args(1)

    ApproachRunner.run(new MapCounterApproach(), inputS3Path, outputS3Path)
  }

}
