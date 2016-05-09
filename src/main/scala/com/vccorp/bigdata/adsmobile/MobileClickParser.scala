package com.vccorp.bigdata.adsmobile

/**
  * Created by quanpv on 5/9/16.
  */
class MobileClickParser(log_mobile_click:String){

  //demo click:
  //2016-04-26 09:05:59	2016-04-26 09:05:59	1961636359245984742		14.169.109.230	e0f9af1e-f29c-57c3-459d-e25ec2fabfb3	-1	-1	-1	-1	-1	-1	Mozilla/5.0 (Linux; U; Android 4.1.2; vi-vn; Thunderbird Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30	http://ngokhong.sohagame.vn/download	480x854	Null	google,1,shg_NKTK_And_download,,1ef7562e647d4d01773fe1f1fa522f6a,vn.shg.ngokhong	adn_mob

  val splits= log_mobile_click.split("\t")

  def get_user_agent(): String ={
  return splits(12)
}

  def get_request_time(): String ={
  return splits(0)
}

  def get_cookie_create(): String ={
  return splits(1)
}

  def get_ip_address(): String ={
  return splits(4)
}

}