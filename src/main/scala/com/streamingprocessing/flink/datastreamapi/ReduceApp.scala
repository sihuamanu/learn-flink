package com.streamingprocessing.flink.datastreamapi

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._

object ReduceApp {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val inputStream = env.fromElements(("en", List("tea")), ("fr", List("vin")), ("en", List("cake")),("en", List("beer")))

    val resultStream = inputStream.keyBy(0).reduce((x,y) => (x._1, x._2 ::: y._2))

    resultStream.print().setParallelism(1)

    env.execute("ReduceApp")
  }

}
