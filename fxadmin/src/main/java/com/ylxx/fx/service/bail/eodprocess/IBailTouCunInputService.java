package com.ylxx.fx.service.bail.eodprocess;

import java.util.List;

import com.ylxx.fx.service.po.BailTouCunBean;

public interface IBailTouCunInputService {

	//查询保证金头寸录入
	public String queryBailTouCunList(String hddate) throws Exception;
	//保证金头寸录入发送报文
	public String toucunluru(String userKey,String bzDate, 
			List<BailTouCunBean> bailTouCunList) throws Exception;

}

