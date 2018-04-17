package com.ylxx.fx.service.po.jsh;

import java.io.Serializable;
/**
 * 币种
 * @author lz130
 *
 */
public class Cytp implements Serializable{
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 币种代码，2位数字
	 */
	private String cytp;	
	/**
	 * 币种英文名，如USD
	 */
	private String cyen;
	/**
	 * 币种中文名，如美元
	 */
	private String cycn;
	/**
	 * 状态，0：启用，1：停用
	 */
	private String usfg;
	/**
	 * 参数说明
	 */
	private String remk;
	public String getCytp() {
		return cytp;
	}
	public void setCytp(String cytp) {
		this.cytp = cytp;
	}
	public String getCyen() {
		return cyen;
	}
	public void setCyen(String cyen) {
		this.cyen = cyen;
	}
	public String getCycn() {
		return cycn;
	}
	public void setCycn(String cycn) {
		this.cycn = cycn;
	}
	public String getUsfg() {
		return usfg;
	}
	public void setUsfg(String usfg) {
		this.usfg = usfg;
	}
	public String getRemk() {
		return remk;
	}
	public void setRemk(String remk) {
		this.remk = remk;
	}
}
