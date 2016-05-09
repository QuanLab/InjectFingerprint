package com.vccorp.bigdata.adsmobile

import com.scientiamobile.wurfl.Wurfl
import net.sourceforge.wurfl.core.GeneralWURFLEngine
import net.sourceforge.wurfl.core.cache.LRUMapCacheProvider

/**
  * Created by quanpv on 5/9/16.
  */
class UserAgentParser (userAgent:String){

  val wurflWrapper = new Wurfl(new GeneralWURFLEngine("file:///home/quanpv/Downloads/wurfl.zip"))

  wurflWrapper.setCacheProvider(new LRUMapCacheProvider)

  wurflWrapper.setTargetAccuracy

  wurflWrapper.setFilter(
    "can_assign_phone_number",
    "marketing_name",
    "brand_name",
    "model_name",
    "is_smarttv",
    "is_wireless_device",
    "device_os",
    "device_os_version",
    "is_tablet",
    "ux_full_desktop",
    "pointing_method",
    "preferred_markup",
    "resolution_height",
    "resolution_width",
    "xhtml_support_level")

  var headers = Map("Accept-Datetime"->"Thu, 31 May 2007 20:35:00 GMT")
  headers += ("Content-Type"-> "application/x-www-form-urlencoded")

  var device = wurflWrapper.deviceForHeaders(userAgent, headers)

  def get_device_id():String={
    return device.id
  }

  def get_device_os():String={
    return device.capabilities("device_os")
  }

  def get_os_version():String={
    return device.capabilities("device_os_version")
  }

  def get_model_name():String={
    return device.capabilities("model_name")
  }

  def get_brand_name():String={
    return device.capabilities("brand_name")
  }

  def is_wiless_device():Boolean={
    return device.capabilities("is_wireless_device").toBoolean
  }

  def is_actual_device_root():Boolean={
    return device.isActualDeviceRoot
  }

  def get_resolution_height():Int={
    return device.capabilities("resolution_height").toInt
  }

  def get_resolution_width():Int={
    return device.capabilities("resolution_width").toInt
  }

  def can_assign_phone_number():Boolean={
    return  device.capabilities("can_assign_phone_number").toBoolean
  }

}