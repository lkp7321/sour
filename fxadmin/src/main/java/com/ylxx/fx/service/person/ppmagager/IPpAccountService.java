package com.ylxx.fx.service.person.ppmagager;

import com.ylxx.fx.service.po.Ck_ppresultdetail;

public interface IPpAccountService {

	//查询所有不明交易
	public String selPPAccount(String lcno) throws Exception;
	//查询UBS联系方式
	public String queryPtpara(String paid) throws Exception;
	//成功处理
	public String onSucessManage(String userKey,Ck_ppresultdetail ppresult)throws Exception;
	//失败处理
	public String onFaultManage(String userKey,String ppno, 
			String seqn,String trfl) throws Exception;
}

