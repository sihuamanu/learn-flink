package com.imooc.flink.cource08

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.streaming.runtime.streamrecord.StreamElementSerializer.StreamElementSerializerSnapshot


object KafkaConnectorConsumerApp {
  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val properties = new Properties()

    properties.setProperty("bootstrap.servers", "cdfplay.vpc.cloudera.com:9092")
    properties.setProperty("group.id", "test")

    val topic = "iot"
    import org.apache.flink.api.scala._
    val data = env.addSource(new FlinkKafkaConsumer[String](topic, new SimpleStringSchema(), properties))

    data.print()

    env.execute("KafkaConnectorConsumerApp")
  }
}
