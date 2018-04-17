package com.ylxx.fx.service.bail.eodprocess;

import com.ylxx.fx.service.po.BailBalanceBean;

public interface IBailBalanceBingZhangService {

	//查询保证金余额并账/保证金利息并账
	public String queryBailBalanceBZList(String hddate,String tradeCode) throws Exception;
	//检查并账日期前是否有未并账的记录
	public String checkInput(String userKey,BailBalanceBean bailBalanceBean) throws Exception;
}

