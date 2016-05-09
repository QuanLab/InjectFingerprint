package com.vccorp.bigdata.adsmobile

import com.scientiamobile.wurfl.Wurfl
import net.sourceforge.wurfl.core.GeneralWURFLEngine
import net.sourceforge.wurfl.core.cache.LRUMapCacheProvider

/**
  * Created by quanpv on 5/9/16.
  */
class UserAgentParser(userAgent: String) {

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

  def get_device_id(): String = {
    try {
      return device.id
    } catch {
      case e: Exception => {
        return "-1"
      }
    }
  }

  def get_device_os(): String = {
    try {
      return device.capabilities("device_os")
    } catch {
      case e: Exception => {
        return "-1"
      }
    }
  }

  def get_os_version(): String = {
    try {
      return device.capabilities("device_os_version")
    } catch {
      case e: Exception => {
        return "-1"
      }
    }
  }

  def get_model_name(): String = {
    try {
      return device.capabilities("model_name")
    } catch {
      case e: Exception => {
        return "-1"
      }
    }
  }

  def get_brand_name(): String = {
    try {
      return device.capabilities("brand_name")
    } catch {
      case e: Exception => {
        return "-1"
      }
    }
  }

  def is_wiless_device(): Boolean = {
    try {
      return device.capabilities("is_wireless_device").toBoolean
    } catch {
      case e: Exception => {
        return false
      }
    }
  }

  def is_actual_device_root(): Boolean = {
    try {
      return device.isActualDeviceRoot
    } catch {
      case e: Exception => {
        return false
      }
    }
  }

  def get_resolution_height(): Int = {
    try {
      return device.capabilities("resolution_height").toInt
    } catch {
      case e: Exception => {
        return -1
      }
    }
  }

  def get_resolution_width(): Int = {
    try {
      return device.capabilities("resolution_width").toInt
    } catch {
      case e: Exception => {
        return -1
      }
    }
  }

  def can_assign_phone_number(): Boolean = {
    try {
      return device.capabilities("can_assign_phone_number").toBoolean
    } catch {
      case e: Exception => {
        return false
      }
    }
  }

}