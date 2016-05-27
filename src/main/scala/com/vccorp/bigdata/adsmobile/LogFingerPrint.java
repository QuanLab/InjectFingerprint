package com.vccorp.bigdata.adsmobile;

import org.apache.log4j.Logger;


public class LogFingerPrint{

//	final static Logger logger = Logger.getLogger(LogFingerPrint.class);

	private boolean isEmpty;
	private long request_time; // thoi gian nhan request
	private long ip;
	private long guid;
	private int browser_code;
	private String brower_ver;

	private String fps;
	private String user_agent;
	private long create_time; // thoi gian tao cookie guid
	private int src; // web : src =1 ; mobile web : src = 2 ; mobile app : src =3
						// 3
	private String referer; // mobile web : url referer ; mobile app click :
							// app_name
	private int cov;
	private String extraField;// with src =4 ext is bannerID

	public LogFingerPrint() {

		isEmpty = true;
	}

	public LogFingerPrint(String value) {

		String[] splits = value.split("\t");
		isEmpty = true;

		if (splits != null && splits.length >= 11) {
			this.request_time =StringUtil.longStr(splits[0]);
			this.ip = StringUtil.longStr(splits[1]);
			this.guid = StringUtil.longStr(splits[2]);
			this.browser_code = StringUtil.intStr(splits[3]);
			this.brower_ver = splits[4];
			this.fps = splits[5];
			this.user_agent = splits[6];
			this.create_time = StringUtil.longStr(splits[7]);
			this.src = StringUtil.intStr(splits[8]);
			this.referer = splits[9];
			this.cov = StringUtil.intStr(splits[10]);
			try {
				if (splits.length == 12) {
					this.extraField = splits[11];
				} else {
					this.extraField = "";
				}
				this.isEmpty = false;
			} catch (Exception e) {
				this.extraField = "";
			}
		}
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public String getExtraField() {
		return extraField;
	}

	public void setExtraField(String extraField) {
		this.extraField = extraField;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public long getRequest_time() {
		return request_time;
	}

	public void setRequest_time(long request_time) {
		this.request_time = request_time;
	}

	public long getIp() {
		return ip;
	}

	public void setIp(long ip) {
		this.ip = ip;
	}

	public long getGuid() {
		return guid;
	}

	public void setGuid(long guid) {
		this.guid = guid;
	}

	public int getBrowser_code() {
		return browser_code;
	}

	public void setBrowser_code(int browser_code) {
		this.browser_code = browser_code;
	}

	public String getBrower_ver() {
		return brower_ver;
	}

	public void setBrower_ver(String brower_ver) {
		this.brower_ver = brower_ver;
	}

	public String getFps() {
		return fps;
	}

	public void setFps(String fps) {
		this.fps = fps;
	}

	public String getUser_agent() {
		return user_agent;
	}

	public void setUser_agent(String user_agent) {
		this.user_agent = user_agent;
	}

	public long getCreate_time() {
		return create_time;
	}

	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}

	public int getSrc() {
		return src;
	}

	public void setSrc(int src) {
		this.src = src;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public int getCov() {
		return cov;
	}

	public void setCov(int cov) {
		this.cov = cov;
	}
}
