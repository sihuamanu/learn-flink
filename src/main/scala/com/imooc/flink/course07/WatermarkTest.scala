package com.imooc.flink.course07

import java.text.SimpleDateFormat

import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.{TimeWindow, Window}
import org.apache.flink.util.Collector
import org.apache.flink.api.scala._

object WatermarkTest {
  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.getConfig.setAutoWatermarkInterval(2000L)  // 可以设置watermark生成的频率
    val input = env.socketTextStream("localhost", 9999)

    val inputMap = input.map(f => {
      val arr = f.split("\\W+")
      val code = arr(0)
      val time = arr(1).toLong
      (code,time)
    })

    val watermark = inputMap.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[(String, Long)] {

      var currentMaxTimestamp = 0L
      val maxOutOfOrderness = 10000L //最大允许的乱序时间是10s

      var a: Watermark = null

      val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

      override def getCurrentWatermark: Watermark = {
        a = new Watermark(currentMaxTimestamp - maxOutOfOrderness)
        a
      }

      override def extractTimestamp(element: (String, Long), previousElementTimestamp: Long) = {
        val timeStamp = element._2
        currentMaxTimestamp = Math.max(timeStamp, currentMaxTimestamp)
        println("timestamp:" + element._1 +","+ element._2
          + "|" +format.format(element._2) +","
          +  currentMaxTimestamp + "|"
          + format.format(currentMaxTimestamp)
          + ","+ a.toString
        )
        timeStamp
      }
    }).setParallelism(1)   // 这里需要设置并行度为1，否则watermark不会升高

    val window = watermark
      .keyBy(_._1)
      .timeWindow(Time.seconds(3))
      .apply(new WindowFunctionTest)

    window.print()

    env.execute("WatermarkTest")

  }

  class WindowFunctionTest extends WindowFunction[(String,Long), (String, Int,String,String,String,String), String, TimeWindow]{
    override def apply(key: String, window: TimeWindow
                       , input: Iterable[(String, Long)]
                       , out: Collector[(String, Int,String,String,String,String)]
                      ): Unit = {

      val list = input.toList.sortBy(_._2)
      val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

      out.collect(key
        ,input.size
        ,format.format(list.head._2)
        ,format.format(list.last._2)
        ,format.format(window.getStart)
        ,format.format(window.getEnd)
      )
    }
  }

}
