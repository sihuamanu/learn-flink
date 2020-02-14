package com.imooc.flink.course04

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.core.fs.FileSystem.WriteMode
import org.apache.flink.api.scala._


object DataSetSinkApp {

  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment

    writeAsTextFunction(env)

    env.execute("DataSetSinkApp")
  }

  def writeAsTextFunction(env: ExecutionEnvironment): Unit ={
    val data = 1.to(10)

    val text = env.fromCollection(data)

    val filePath = "Resources/sink-out"
    text.writeAsText(filePath, WriteMode.OVERWRITE).setParallelism(2)
  }

}
