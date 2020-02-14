/*package com.streamingprocessing.flink.datastreamapi

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.api.scala._
/**
  * Created by sihua.ancloudera.com on 2020/2/12.
  */
object ConnectApp {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val props = new Properties()

    props.setProperty("bootstrap-server", "cdpdccdpdc.vpc.cloudera.com:9092")
    props.setProperty("group-id", "test")

    val topic1 = "pre-conn-1"
    val topic2 = "pre-conn-2"

    val source1 = env.addSource(new FlinkKafkaConsumer[String](topic1, new SimpleStringSchema(), props))
    val source2 = env.addSource(new FlinkKafkaConsumer[String](topic2, new SimpleStringSchema(), props))

    val connectSource = source1.connect(source2)

    connectSource.keyBy(0, 0)


    env.execute("ConnectApp")
  }
}*/
