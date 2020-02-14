package com.imooc.flink.cource08

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer


object KafkaConnectorSinkApp {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    //checkpoint 常用设置参数
    env.enableCheckpointing(4000)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
    env.getCheckpointConfig.setCheckpointTimeout(10000)
    env.getCheckpointConfig.setMaxConcurrentCheckpoints(1)


    val data = env.socketTextStream("localhost", 9999)

    val topic = "pktest"

    val myProducer = new FlinkKafkaProducer[String](
      "cdp-client2.vpc.cloudera.com:9092",
      topic,
      new SimpleStringSchema)

    myProducer.setWriteTimestampToKafka(true)

    data.addSink(myProducer)

    env.execute("KafkaConnectorSinkApp")
  }

}
