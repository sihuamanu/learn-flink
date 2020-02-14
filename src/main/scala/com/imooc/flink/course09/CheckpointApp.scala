package com.imooc.flink.course09

import org.apache.flink.contrib.streaming.state.RocksDBStateBackend
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
  * Created by sihua.ancloudera.com on 2020/2/8.
  */
object CheckpointApp {

  def main(args: Array[String]): Unit = {


    val env = StreamExecutionEnvironment.getExecutionEnvironment

    env.setStateBackend(new RocksDBStateBackend("hdfs:///checkpoints-data/"));

  }

}
