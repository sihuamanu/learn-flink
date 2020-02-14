package com.imooc.flink.course07

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector


object WindowsApp {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment


    import org.apache.flink.api.scala._
    val text = env.socketTextStream("localhost", 9999)


    // tumbling window
    /*text.flatMap(_.split(","))
      .map((_,1))
      .keyBy(0)
      .timeWindow(Time.seconds(5))
      .sum(1)
      .print().setParallelism(1)
*/
    // sliding window
    /*text.flatMap(_.split(","))
      .map((_,1))
      .keyBy(0)
      .timeWindow(Time.seconds(10), Time.seconds(5))
      .sum(1)
      .print()
      .setParallelism(1)*/

    // window function with incremental aggregation
    /*text.flatMap(_.split(",")) // 原来传递进来的数据是字符串，此处我们就使用数值类型，通过数值类型来演示增量聚合的效果
      .map(x => (1, x.toInt)) // map转换为(1,1) (1,2) (1,3) (1,4) (1,5)
      .keyBy(0) // 因为key都是1，所以所有的元素都到一个task执行
      .timeWindow(Time.seconds(5))
      .reduce((v1, v2) => {   // 不是等待窗口所有的数据进行一次性处理，而是数据两两处理
        println(v1 + "..." + v2)
        (v1._1, v1._2 + v2._2)
      })
      .print()*/

    // process window function
    /*text.flatMap(_.split(","))
      .map(x => (1, x.toInt))
      .keyBy(0)
      .timeWindow(Time.seconds(5))
      .process(new MyProcessWindowFunction)*/

    env.execute("WindowsApp")
  }


}


