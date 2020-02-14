package com.streamingprocessing.flink.datastreamapi

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.api.scala._

object UnionApp {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val props = new Properties()

    props.setProperty("bootstrap.servers", "cdpdccdpdc.vpc.cloudera.com:9092")
    props.setProperty("group.id", "test")

    val topic1 = "pre-union1"
    val topic2 = "pre-union2"
    val topic3 = "pre-union3"


    val source1 = env.addSource(new FlinkKafkaConsumer[String](topic1, new SimpleStringSchema(), props))

    val source2 = env.addSource(new FlinkKafkaConsumer[String](topic2, new SimpleStringSchema(), props))

    val source3 = env.addSource(new FlinkKafkaConsumer[String](topic3, new SimpleStringSchema(), props))

    val unionSource = source1.union(source2,source3)

    unionSource
      .flatMap(_.split(","))
      .map((_,1))
      .keyBy(0)
      .sum(1)
      .print()
      .setParallelism(1)


    env.execute("UnionApp")
  }

}
