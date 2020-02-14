package com.imooc.flink.course05

import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext


class CustomRichParallelSourceFunction extends RichParallelSourceFunction[Long]{
  var isRunning = true
  var count = 1L

  override def cancel(): Unit = {
    isRunning = false
  }

  override def run(ctx: SourceContext[Long]): Unit = {
    while (isRunning){
      ctx.collect(count)
      count += 1
      Thread.sleep(1000)
    }
  }
}
