package com.techtest

object MapReduceTailRecursionApproachMain {

  def main(args: Array[String]): Unit = {
    val inputS3Path: String = args(0)
    val outputS3Path: String = args(1)

    ApproachRunner.run(new MapReduceTailRecursionApproach(), inputS3Path, outputS3Path)
  }

}
