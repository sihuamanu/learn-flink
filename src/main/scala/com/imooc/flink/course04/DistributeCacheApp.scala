package com.imooc.flink.course04

import org.apache.commons.io.FileUtils
import org.apache.flink.api.common.functions.RichMapFunction
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration

/**
  *
  * step1: 注册一个本地或者HDFS文件
  * step2: 在open方法中获取到分布式缓存的内容
  */
object DistributeCacheApp {

  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment

    // step1: 注册一个本地或者HDFS文件
    val filePath = "resource/hello.txt"
    env.registerCachedFile(filePath, "scala-distibuteCache")

    import org.apache.flink.api.scala._
    val data = env.fromElements("hadoop","spark", "flink", "pyspark", "storm")

    data.map(new RichMapFunction[String, String] {

      // step2: 在open方法中获取到分布式缓存的内容
      override def open(parameters: Configuration): Unit ={
        val dcFile = getRuntimeContext.getDistributedCache().getFile("scala-distibuteCache")

        val lines = FileUtils.readLines(dcFile)  // java

        /**
          * 此时会出现一个异常： java集合与scala集合不兼容的问题
          * Error:(37, 20) value foreach is not a member of java.util.List[String]
            for(ele <- lines){ // scala
          *
          * 需要引入一个转换
          */


        import scala.collection.JavaConverters._
        for(ele <- lines.asScala){ // scala
          println(ele)
        }
      }

      override def map(value: String): String = {
        value
      }
    }).print()

  }

}
