package com.ylxx.fx.service.person.price;

import com.ylxx.fx.service.po.PdtinfoBean;

public interface IQutFreqtService {
	
	//查询报价频率参数
	public String selectQutFreqt(String ptid) throws Exception;
	//修改报价频率参数
	public String updateQutFreqt(String userKey,PdtinfoBean freqt) throws Exception;

}

