package com.imooc.flink.course07

import org.apache.flink.streaming.api.scala.function.ProcessWindowFunction
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector


class MyProcessWindowFunction extends ProcessWindowFunction [(Int,Int), String, Int, TimeWindow]{
  @scala.throws[Exception]
  override def process(key: Int, context: Context, input: Iterable[(Int,Int)], out: Collector[String]) = {
    var count = 0L
    for (in <- input) {
      count = count + 1
    }
    out.collect(s"Window ${context.window} count: $count")
  }
}
