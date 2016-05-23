package com.vccorp.bigdata.adsmobile

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext


object LogCLickAnalystic {

  case class Click(app_id:String, device_id:String, device_os:String, os_version:String, model_name:String, brand_name:String, ip_address:String)

  def main(arg: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("LogFingerspint").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    import sqlContext.implicits._

    val rdd = sc.textFile("file:////home/quanpv/Database/LogClick2/")
    val data_click = rdd.map(mapClickRule).toDF()
    data_click.show(620)
  }

  def mapClickRule : (String => Click) = {

    click => {

      val mobileClickParser = new MobileClickParser(click)

      val user_agent = mobileClickParser.get_user_agent();

      val uaParser = new UserAgentParser(user_agent)

      Click(mobileClickParser.get_game_id(), uaParser.get_device_id(), uaParser.get_device_os(), uaParser.get_os_version(), uaParser.get_model_name(), uaParser.get_brand_name(), mobileClickParser.get_ip_address())
    }
  }
}