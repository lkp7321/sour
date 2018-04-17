package com.ylxx.fx.service.person.price;

import java.util.List;

import com.ylxx.fx.service.po.PdtStoperBean;

public interface IPdtStoperUpService {

	//查询实盘币种
	public String selectEXNM(String prod) throws Exception;
	//获得停牌器的所有数据
	public String selectAllCmmStopers(String ptid) throws Exception;
	//条件获得停牌器的数据
	public String selectAllCmmStoper(String ptid,String excd) throws Exception;
	//启用状态
	public String openChannel(String prod,String userKey,List<PdtStoperBean> pdts) throws Exception;
	//关闭状态
	public String closeChannel(String prod,String userKey,List<PdtStoperBean> pdts) throws Exception;
	//根据货币对名称查询货币对编号
	public String getExcd(String ptid,String exnm) throws Exception;
}

