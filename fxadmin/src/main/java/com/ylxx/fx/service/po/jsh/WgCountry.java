package com.ylxx.fx.service.po.jsh;

import java.io.Serializable;
/**
 * 外管局国别
 * @author lz130
 *
 */
public class WgCountry implements Serializable{
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 国别名称
	 */
	private String name;
	/**
	 * 外管局国别
	 */
	private String cout;
	/**
	 * 别名
	 */
	private String cmbccout;
	/**
	 * 国别备注
	 */
	private String copycout;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCout() {
		return cout;
	}
	public void setCout(String cout) {
		this.cout = cout;
	}
	public String getCmbccout() {
		return cmbccout;
	}
	public void setCmbccout(String cmbccout) {
		this.cmbccout = cmbccout;
	}
	public String getCopycout() {
		return copycout;
	}
	public void setCopycout(String copycout) {
		this.copycout = copycout;
	}
	
}
