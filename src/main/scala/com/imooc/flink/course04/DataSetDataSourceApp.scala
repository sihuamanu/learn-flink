package com.imooc.flink.course04

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration


object DataSetDataSourceApp {

  def main(args: Array[String]): Unit = {


    val env = ExecutionEnvironment.getExecutionEnvironment

    //fromCollection(env)   //从集合读数据
    //textFile(env)   // 从text文件读数据
    //csvFile(env)     // 从csv文件读数据
    //recursiveFile(env)  // 从递归文件目录读数据
    readCompressionFile(env)  // 从压缩文件读数据

  }

  def fromCollection(env: ExecutionEnvironment): Unit = {

    import org.apache.flink.api.scala._
    val data = 1 to 10
    env.fromCollection(data).print()
  }

  def textFile(env: ExecutionEnvironment): Unit = {
    val filePath = "Resources/inputs"

    env.readTextFile(filePath).print()
  }

  case class caseClass(name:String, age:Int)  //定义case class

  def csvFile(env: ExecutionEnvironment): Unit ={

    import org.apache.flink.api.scala._
    val csvFilePath = "Resources/people.csv"

    //env.readCsvFile[(String,Int)](csvFilePath,ignoreFirstLine=true,includedFields = Array(0,1)).print() //使用tuple

    //env.readCsvFile[caseClass](csvFilePath,ignoreFirstLine = true,includedFields = Array(0,1)).print() //使用case class

    //env.readCsvFile[Person](csvFilePath, ignoreFirstLine = true, pojoFields = Array("name","age","job")).print() //使用POJO
  }

  def recursiveFile(env: ExecutionEnvironment): Unit ={
    val recursiveFilePath = "Resources/inputs/nested"
    val parameters = new Configuration
    parameters.setBoolean("recursive.file.enumeration", true)   // 设置递归
    env.readTextFile(recursiveFilePath).withParameters(parameters).print()
  }

  def readCompressionFile(env: ExecutionEnvironment): Unit ={
    val compressionFilePath = "Resources/inputs/compression/hello.txt.tar.gz"
    env.readTextFile(compressionFilePath).print() // Flink支持默认的解压，不需要额外配置
  }

}
