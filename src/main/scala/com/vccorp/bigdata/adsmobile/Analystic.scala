package com.vccorp.bigdata.adsmobile

import org.apache.spark.{SparkConf, SparkContext}


object LogCLickAnalystic {

  def main(arg: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("LogFingerspint").setMaster("local[*]")
    val sc = new SparkContext(conf)

    val rdd = sc.textFile("file:////home/quanpv/Downloads/LogClick2/")

    rdd.map(x => (x, 1))

    val click = "2016-04-26 11:01:09\t2016-01-02 12:48:37\t1717137171897288259\t\t113.187.3.108\te52877fa-60f4-5ae6-7cdb-4ede962e9996\t373040\tm.cafef.vn\t/nha-1-ty-dong-cua-dai-gia-le-thanh-than-tha-lai-it-con-hon-chet-20160425104108899.chn\t6869\t1074694\t-1\tMozilla/5.0 (Mobile; Windows Phone 8.1; Android 4.0; ARM; Trident/7.0; Touch; rv:11.0; IEMobile/11.0; NOKIA; Lumia 625H) like iPhone OS 7_0_3 Mac OS X AppleWebKit/537 (KHTML, like Gecko) Mobile Safari/537\t-1\t396x660\tNull\t-1\tmobilead"

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
    println("ip_address               " + mobileClickParser.get_ip_address())
    println("resolution_height        " + userAgentParser.get_resolution_height())
    println("resolution_width         " + userAgentParser.get_resolution_width())
    println("can_assign_phone_number: " + userAgentParser.can_assign_phone_number())

  }
}