package com.imooc.flink.course05

import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction
import org.apache.flink.streaming.api.functions.source.SourceFunction.SourceContext


class CustomParallelSourceFunction extends ParallelSourceFunction[Long]{

  var isRunning = true
  var count = 1L

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
