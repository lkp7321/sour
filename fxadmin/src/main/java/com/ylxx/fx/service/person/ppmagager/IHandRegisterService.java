package com.ylxx.fx.service.person.ppmagager;

import com.ylxx.fx.service.po.Register;

public interface IHandRegisterService {

	//在总敞口中查找币别对
	public String selectCurrencyPair(String prod) throws Exception;
	//获取敞口列表
	public String selectCkno(String prod) throws Exception;
	//获取平盘对手列表
	public String selectPPDS(String prod) throws Exception;
	//登记
	public String registerCK(String userKey,Register ckno) throws Exception;
}

