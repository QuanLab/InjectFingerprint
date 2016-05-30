package com.vccorp.bigdata.adsmobile;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FPS {

//	final static Logger logger = Logger.getLogger(FPS.class);
	private String value;

	private boolean isEmpty;
	private int os_type;// 1 is android 2 is IOS
	private String os_version;
	private String manufacturer;
	private String device_model;
	private String device_brand;
	private String device_product;
	private String device_build_number;
	private String app_id;
	private String carrier;
	private String ip_address;
	private String language;
	private String create_time;
	private String fingerprint;
	private String deviceid;
	private String sdkid;
	private int realdevice;// 1 is real device 0 is virtual divice
	private int action;
	private String extraField;
	int size;

	public FPS(String value) {

		size = 0;
		this.isEmpty = true;

		try {
			this.value = decode(value);
		} catch (Exception e) {
			System.out.println("Cannot decode " + e);
		}

		String[] splits = new String[17];

		if(value!=null){
			splits= this.value.split(";");
			size = splits.length;
		}

		if (size >= 16) {
			try {
				this.os_type = StringUtil.intStr(splits[0]);
				this.os_version = splits[1];
				this.manufacturer = splits[2];
				this.device_model = splits[3];
				this.device_brand = splits[4];
				this.device_product = splits[5];
				this.device_build_number = splits[6];
				this.app_id = splits[7];
				this.carrier = splits[8];
				this.ip_address = splits[9];
				this.language = splits[10];
				this.create_time = splits[11];
				this.fingerprint = splits[12];
				this.deviceid = splits[13];
				this.sdkid = splits[14];
				this.realdevice = StringUtil.intStr(splits[15]);

				try {
					this.action = StringUtil.intStr(splits[16]);
				} catch (Exception e) {
					this.action = -1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.isEmpty = false;
		}

	}

	public String decode(String fpsString){

		String fpsreplace = fpsString.replace("%(?![0-9a-fA-F]{2})", "%25");
		// check more than %
		String pattern = "%+";
		String replace = "%";

		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(fpsreplace);
		fpsreplace = m.replaceAll(replace);

		try{
			return URLDecoder.decode(fpsreplace, "utf-8");
		}catch (Exception e){
//			logger.error("Cannot decode fps string: " + fpsreplace + " cause by" + e);
			return "-1";
		}
	}

	public int getRealdevice() {
		return realdevice;
	}

	public void setRealdevice(int realdevice) {
		this.realdevice = realdevice;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public int getOs_type() {
		return os_type;
	}

	public void setOs_type(int os_type) {
		this.os_type = os_type;
	}

	public String getOs_version() {
		return os_version;
	}

	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getDevice_model() {
		return device_model;
	}

	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}

	public String getDevice_brand() {
		return device_brand;
	}

	public void setDevice_brand(String device_brand) {
		this.device_brand = device_brand;
	}

	public String getDevice_product() {
		return device_product;
	}

	public void setDevice_product(String device_product) {
		this.device_product = device_product;
	}

	public String getDevice_build_number() {
		return device_build_number;
	}

	public void setDevice_build_number(String device_build_number) {
		this.device_build_number = device_build_number;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getSdkid() {
		return sdkid;
	}

	public void setSdkid(String sdkid) {
		this.sdkid = sdkid;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String getExtraField() {
		return extraField;
	}

	public void setExtraField(String extraField) {
		this.extraField = extraField;
	}
}
