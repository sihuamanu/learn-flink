package com.imooc.flink.course05

import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext


class CustomNonParallelSourceFunction extends SourceFunction[Long]{

  var count = 1L

  var isRunning = true

  override def run(ctx: SourceContext[Long]) = {
    while (isRunning){
      ctx.collect(count)
      count += 1
      Thread.sleep(1000)
    }
  }

  override def cancel() = {
    isRunning = false
  }

}
