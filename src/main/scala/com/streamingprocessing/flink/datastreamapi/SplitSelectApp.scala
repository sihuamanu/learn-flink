package com.streamingprocessing.flink.datastreamapi

import java.util.Properties

import org.apache.flink.streaming.api.scala.{DataStream, SplitStream, StreamExecutionEnvironment}
import org.apache.flink.api.scala._

object SplitSelectApp {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val props = new Properties()

    props.setProperty("bootstrap-server", "cdpdccdpdc.vpc.cloudera.com:9092")
    props.setProperty("group-id", "test")

    val topic = "split-select"

    val inputStream:DataStream[(Int,String)] = env.fromElements((1234,"hello"),(999,"world"))

    val splitted: SplitStream[(Int,String)] = inputStream.split(t => if (t._1 > 1000) Seq("Large") else Seq("Small"))

    val large = splitted.select("Large")
    val small = splitted.select("Small")

    val all = splitted.select("Large","Small")

    large.print("come").setParallelism(1)
    small.print("go").setParallelism(1)

    env.execute("SplitSelectApp")

  }

}
