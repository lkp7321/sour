package com.ylxx.fx.service.person.price;

import java.util.List;

import com.ylxx.fx.service.po.CmmStoper;

public interface IHandQuickStopService {

	//加载市场
	public String queryMktinfo() throws Exception;
	//加载币别对
	public String queryhqsExnms() throws Exception;
	//加载停牌数据
	public String selecthqsCmmStopers() throws Exception;
	//条件加载停牌数据
	public String selecthqsCmmStopers(String mkid, String excd) throws Exception;
	//启用
	public String openChannel(String userKey,List<CmmStoper> cmms) throws Exception;
	//停牌
	public String closeChannel(String userKey,List<CmmStoper> cmms) throws Exception;
	
}

