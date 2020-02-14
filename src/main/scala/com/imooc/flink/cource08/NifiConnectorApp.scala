package com.imooc.flink.cource08


import org.apache.flink.api.common.functions.MapFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.nifi._
import org.apache.nifi.remote.client.{SiteToSiteClient, SiteToSiteClientConfig}
import org.apache.flink.api.scala._

object NifiConnectorApp {

  def main(args: Array[String]): Unit = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment

    val clientConfig: SiteToSiteClientConfig = new SiteToSiteClient.Builder()
      .url("http://cdpdccdpdc-w-1.vpc.cloudera.com:8080/nifi")
      .portName("Data for Flink")
      .requestBatchCount(5)
      .buildConfig()


    val nifiSource = new NiFiSource(clientConfig)

    val streamSource = env.addSource(nifiSource).setParallelism(1)

    val dataStream = streamSource.map(new MyMapFunction)

    dataStream.print()

    env.execute("NifiConnectorApp")

  }


  class MyMapFunction extends MapFunction[NiFiDataPacket, String]{
    override def map(value: NiFiDataPacket): String = value.getContent().toString
  }

}


