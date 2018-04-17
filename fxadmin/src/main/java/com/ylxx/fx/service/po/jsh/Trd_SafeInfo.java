package com.ylxx.fx.service.po.jsh;
/**
 * 外管局柜员
 * @author lz130
 *
 */
public class Trd_SafeInfo {
	/**
	 * 柜员id
	 */
	private String tellerId;
	/**
	 * 机构号
	 */
	private String ogcd;
	/**
	 * 交易柜员
	 */
	private String trtl;
	/**
	 * 柜员密码
	 */
	private String pass;
	/**
	 * 柜员类型
	 */
	private String tltp;
	/**
	 * 请求方机构代号
	 */
	private String bhid;
	/**
	 * 交易渠道
	 */
	private String chnl;
	public String getTellerId() {
		return tellerId;
	}
	public void setTellerId(String tellerId) {
		this.tellerId = tellerId;
	}
	public String getOgcd() {
		return ogcd;
	}
	public void setOgcd(String ogcd) {
		this.ogcd = ogcd;
	}
	public String getTrtl() {
		return trtl;
	}
	public void setTrtl(String trtl) {
		this.trtl = trtl;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getTltp() {
		return tltp;
	}
	public void setTltp(String tltp) {
		this.tltp = tltp;
	}
	public String getBhid() {
		return bhid;
	}
	public void setBhid(String bhid) {
		this.bhid = bhid;
	}
	public String getChnl() {
		return chnl;
	}
	public void setChnl(String chnl) {
		this.chnl = chnl;
	}
	
	
}
