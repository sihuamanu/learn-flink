package com.streamingprocessing.flink.datastreamapi

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import sun.nio.cs.StreamEncoder


object SetParallelismApp {

  def main(args: Array[String]): Unit = {


    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val defaultP = env.getParallelism

    println("default parallelism is: " + defaultP)
  }

}
