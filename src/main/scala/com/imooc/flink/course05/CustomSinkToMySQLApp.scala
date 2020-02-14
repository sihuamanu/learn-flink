//package com.imooc.flink.course05
//
//import org.apache.flink.api.common.functions.MapFunction
//import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
//import org.apache.flink.api.scala._
///**
//  * Scala实现CustomSinkToMySQLApp
//  */
//object CustomSinkToMySQLApp {
//
//  def main(args: Array[String]): Unit = {
//
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//
//    val source = env.socketTextStream("localhost", 7777)
//
//    val studentStream = source.map(new MapFunction[String, Student] {
//      override def map(value: String): Student = {
//        val splits = value.split(",")
//
//        val stu = new Student
//
//        stu.setId(splits(0).toInt)
//        stu.setName(splits(1))
//        stu.setAge(splits(2).toInt)
//
//        stu
//
//      }
//    })
//
//    studentStream.addSink(new SinkToMySQL)
//
//    env.execute("JavaCustomSinkToMySQL")
//  }
//
//}
