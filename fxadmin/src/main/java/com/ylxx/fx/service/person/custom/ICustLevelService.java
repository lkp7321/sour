package com.ylxx.fx.service.person.custom;

import com.ylxx.fx.service.po.CustLevelBean;

public interface ICustLevelService {
	
	//添加客户级别信息
	public String CustLevelAdd(CustLevelBean clBean,String userKey);	
	//修改客户级别信息
	public String CustLevelUpate(CustLevelBean custLevelBean,String userKey);
	//删除客户级别信息
	public String CustLevelDelete(CustLevelBean clBean,String userKey);
	//获取所有客户级别信息
	public String getCustLevel(String prod) throws Exception;
	
}