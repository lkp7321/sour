package com.ylxx.qt.service.po;

import java.io.Serializable;

/**
 * 用来构造持仓偏好图表和成交偏好图表 这个类适用于持仓偏好和成交偏好，适用于json
 * 格式为[{"name":XX,"value":XX,"color":XX},{"name":XX,"value":XX,"color":XX}]
 * 
 * @author mengpeitong
 *
 */
public class PositionPreferBean implements Serializable {
	private static final long serialVersionUID = 1L;
	// 品种名称
	private String name;
	// 品种值
	private Double value;
	// 颜色值
	private String color;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
