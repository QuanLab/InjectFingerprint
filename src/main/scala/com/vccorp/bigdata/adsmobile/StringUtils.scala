package com.vccorp.bigdata.adsmobile

import java.net.URLDecoder
//import java.util.regex.Matcher
import java.util.regex.Pattern

/**
  * Created by quanpv on 5/21/16.
  */

object StringUtils{

  def parseLong(value:String):Long={
    try{
      if(value.length>=1){
        return value.toLong
      }
    }catch{
      case e: Exception => -1L
    }
    return 0L
  }

  def parseInt(value:String):Int={
    try{
      if(value.length>=1){
        return value.toInt
      }
    }catch{
      case e: Exception => -1
    }
    return 0
  }

  def decode(value:String):String={
    var fpsreplace = value.replace("%(?![0-9a-fA-F]{2})", "%25");
    val pattern = "%+";
    val replace = "%";
    try {
      val r = Pattern.compile(pattern);
      val m = r.matcher(fpsreplace);
      fpsreplace = m.replaceAll(replace);
      return URLDecoder.decode(fpsreplace, "utf-8");
    }catch{
      case e: Exception => ""
    }
  }

  def getAction(fps_record: Array[String]):Int={

    var size=fps_record.length
    try{
      return this.parseInt(fps_record(16))
    }catch{
      case e: Exception => -1
    }
    return -1
  }

}
