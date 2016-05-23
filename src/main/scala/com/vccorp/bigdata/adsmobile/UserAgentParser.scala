package com.vccorp.bigdata.adsmobile

import com.scientiamobile.wurfl.Wurfl
import net.sourceforge.wurfl.core.GeneralWURFLEngine
import net.sourceforge.wurfl.core.cache.LRUMapCacheProvider

/**
  * Created by quanpv on 5/9/16.
  */
class UserAgentParser(userAgent: String) {

  val userAgentHelper = new UserAgentHelper((userAgent));

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

  var headers = Map("Accept-Datetime" -> "Thu, 31 May 2007 20:35:00 GMT")
  headers += ("Content-Type" -> "application/x-www-form-urlencoded")

  var device = wurflWrapper.deviceForHeaders(userAgent, headers)

  def getDeviceId(): String = {
    try {
      return device.id
    } catch {
      case e: Exception => {
        return "-1"
      }
    }
  }

  def getOsType(): String = {
    try {
      return device.capabilities("device_os")
    } catch {
      case e: Exception => {
        return "-1"
      }
    }
  }

  def getOsVersion(): String = {
    try {
      var version = userAgentHelper.getOsVersion();
      if(!version.trim().equals("")){
        return version
      } else{
        return device.capabilities("device_os_version")
      }
    } catch {
      case e: Exception => {
        return "-1"
      }
    }
  }

  def getModelName(): String = {
    try {
      return device.capabilities("model_name")
    } catch {
      case e: Exception => {
        return "-1"
      }
    }
  }

  def getBrandName(): String = {
    try {
      return device.capabilities("brand_name")
    } catch {
      case e: Exception => {
        return "-1"
      }
    }
  }

  def isWilessDevice(): Boolean = {
    try {
      return device.capabilities("is_wireless_device").toBoolean
    } catch {
      case e: Exception => {
        return false
      }
    }
  }

  def isActualDeviceRoot(): Boolean = {
    try {
      return device.isActualDeviceRoot
    } catch {
      case e: Exception => {
        return false
      }
    }
  }

  def getResolutionHeight(): Int = {
    try {
      return device.capabilities("resolution_height").toInt
    } catch {
      case e: Exception => {
        return -1
      }
    }
  }

  def getResolutionWidth(): Int = {
    try {
      return device.capabilities("resolution_width").toInt
    } catch {
      case e: Exception => {
        return -1
      }
    }
  }

  def canAssignPhoneNumber(): Boolean = {
    try {
      return device.capabilities("can_assign_phone_number").toBoolean
    } catch {
      case e: Exception => {
        return false
      }
    }
  }

  def getUserAgentParsedToString() :String = {

    return getDeviceId() + "\t" + getOsType() + "\t" + getOsVersion() + "\t" + getModelName() + "\t" + getBrandName()
  }

}