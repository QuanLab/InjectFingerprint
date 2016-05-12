package com.vccorp.bigdata.adsmobile

//import org.apache.spark.{SparkConf, SparkContext}
//import org.apache.spark.sql.SQLContext


object LogCLickAnalystic {

  def main(arg: Array[String]): Unit = {
//
//    val conf = new SparkConf().setAppName("LogFingerspint").setMaster("local[*]")
//    val sc = new SparkContext(conf)
//    val sqlContext = new SQLContext(sc)
//
//     //this is used to implicitly convert an RDD to a DataFrame.
//    import sqlContext.implicits._
//
//    val rdd = sc.textFile("file:////home/quanpv/Downloads/LogClick2/")
//    rdd.map(x => (x, 1)).toDF().show(5000)

    val click = "2016-04-26 17:18:42\t2016-03-15 15:01:13\t1280288731963305729\t\t113.190.100.33\ta1dd3d6b-e7a4-5e43-488b-ef03416b43c0\t-1\t-1\t-1\t-1\t-1\t-1\tMozilla/5.0 (Linux; U; Android 4.1.2; vi-vn; GT-I8552 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30\thttps://play.google.com/store/apps/details?id=haitacbongdem.vn.msh\t480x800\tNull\tgoogle,1,shg_HTBD_And_store,,0fe265c35423bb3ee2e5950e61ef9195,haitacbongdem.vn.msh\tadn_mob"

    val mobileClickParser = new MobileClickParser(click)

    val user_agent = mobileClickParser.get_user_agent()

    println(user_agent)

    val userAgentParser = new UserAgentParser(user_agent)

    println("device_id                " + userAgentParser.get_device_id())
    println("device_os                " + userAgentParser.get_device_os())
    println("device_os_version        " + userAgentParser.get_os_version())
    println("model_name               " + userAgentParser.get_model_name())
    println("brand_name               " + userAgentParser.get_brand_name())
    println("is_wireless_device       " + userAgentParser.is_wiless_device())
    println("is_actual_device_root    " + userAgentParser.is_actual_device_root())
    //println("ip_address               " + mobileClickParser.get_ip_address())
    println("resolution_height        " + userAgentParser.get_resolution_height())
    println("resolution_width         " + userAgentParser.get_resolution_width())
    println("can_assign_phone_number: " + userAgentParser.can_assign_phone_number())

  }
}