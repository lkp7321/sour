package com.ylxx.fx.service.bail.eodprocess;

import com.ylxx.fx.service.po.BailBalanceBean;

public interface IBailBalanceInputService {

	//查询保证金余额录入/保证金利息录入
	public String queryBailBalanceList(String hddate,String tradeCode) throws Exception;
	//保证金余额录入/保证金利息录入发送报文
	public String sunyiluru(String userKey,BailBalanceBean bailBalanceBean) throws Exception;
	
}

