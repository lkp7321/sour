package com.ylxx.fx.service.bail.eodprocess;

import com.ylxx.fx.service.po.BailTouCunBean;

public interface IBailTouCunBingZhangService {

	//查询保证金头寸并账
	public String queryBailTouCunBZList(String hddate) throws Exception;
	//检查并账日期前是否有未并账的记录
	public String checkInput(String userKey,BailTouCunBean bailTouCunBean) throws Exception;
	
}

