package com.imooc.flink.course06

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.table.api.scala.BatchTableEnvironment
import org.apache.flink.types.Row


object TableSQLApp {

  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment

    val tableEnv = BatchTableEnvironment.create(env)

    val filePath = "resource/sales.csv"

    import org.apache.flink.api.scala._

    val csv = env.readCsvFile[SalesLog](filePath,ignoreFirstLine = true)

    val salesTable = tableEnv.fromDataSet(csv)

    tableEnv.registerTable("sales", salesTable)

    val resultTable = tableEnv.sqlQuery("select customerId, sum(amountPaid) money from sales group by customerId")

    tableEnv.toDataSet[Row](resultTable).print()
  }

  case class SalesLog(transactionId:String,
                      customerId:String,
                      itemId:String,
                      amountPaid:Double)

}
