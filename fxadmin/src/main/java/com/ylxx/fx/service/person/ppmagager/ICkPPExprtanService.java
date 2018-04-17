package com.ylxx.fx.service.person.ppmagager;

public interface ICkPPExprtanService {

	//获得全部异常交易的数据
	public String queryExprtan(String trac) throws Exception;
	//处理
	public String updateExprtan(String userKey,String ppno) throws Exception;
}

