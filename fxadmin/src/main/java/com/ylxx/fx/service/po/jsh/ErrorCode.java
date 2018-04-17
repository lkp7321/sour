package com.ylxx.fx.service.po.jsh;
/**
 * 错误码
 * @author lz130
 *
 */
public class ErrorCode {
	/**
	 * 错误码
	 */
	private String ercd;
	/**
	 * 外部错误描述
	 */
	private String ertx;
	/**
	 * 内部错误描述
	 */
	private String erin;
	public String getErcd() {
		return ercd;
	}
	public void setErcd(String ercd) {
		this.ercd = ercd;
	}
	public String getErtx() {
		return ertx;
	}
	public void setErtx(String ertx) {
		this.ertx = ertx;
	}
	public String getErin() {
		return erin;
	}
	public void setErin(String erin) {
		this.erin = erin;
	}
	
}
