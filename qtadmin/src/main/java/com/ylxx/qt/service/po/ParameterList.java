package com.ylxx.qt.service.po;

import java.util.List;

public class ParameterList {
	private List<ParameterdetailsBean> list;
	private String UserId;
	
	public List<ParameterdetailsBean> getList() {
		return list;
	}
	public void setList(List<ParameterdetailsBean> list) {
		this.list = list;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	
}
