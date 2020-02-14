package com.imooc.flink.course05

import java.lang.Iterable
import java.util

import org.apache.flink.streaming.api.collector.selector.OutputSelector
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment


object DataStreamTransformationApp {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //filterFunction(env)
    //unionFunction(env)
    splitSelectFunction(env)
    env.execute("DataStreamTransformationApp")
  }


  def filterFunction(env: StreamExecutionEnvironment): Unit ={

    import org.apache.flink.api.scala._
    val data = env.addSource(new CustomNonParallelSourceFunction)

    data.map(x=> {
      println("received: " + x)
      x
    }).filter(_%2 == 0).print().setParallelism(1)
  }

  def unionFunction(env: StreamExecutionEnvironment): Unit ={
    import org.apache.flink.api.scala._
    val data1 = env.addSource(new CustomNonParallelSourceFunction)
    val data2 = env.addSource(new CustomNonParallelSourceFunction)

    data1.union(data2).print().setParallelism(1)
  }

  def splitSelectFunction(env: StreamExecutionEnvironment): Unit ={
    import org.apache.flink.api.scala._
    val data = env.addSource(new CustomNonParallelSourceFunction)

    val splits = data.split(new OutputSelector[Long] {
      override def select(value: Long): Iterable[String] = {

        val list = new util.ArrayList[String]()
        if(value % 2 == 0){
          list.add("even")
        } else {
          list.add("odd")
        }
        list
      }
    })

    splits.select("even").print().setParallelism(1)
  }
}
