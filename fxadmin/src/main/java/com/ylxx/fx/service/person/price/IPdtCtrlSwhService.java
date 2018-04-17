package com.ylxx.fx.service.person.price;

import java.util.List;

import com.ylxx.fx.service.po.PdtCtrlSwhBean;

public interface IPdtCtrlSwhService{
	
	//得到当前所有干预器列表及状态*
	public String selectPrice(String ptid) throws Exception;
	//得到所有币种信息*
	public String selectPriceUSD(String ptid) throws Exception;
	//添加或更新产品价格干预器总控
	public String pdtAddCtrlList(String userKey,String prod,PdtCtrlSwhBean pdtCtrlSwhBean)throws Exception;
	//删除点差干预上限
	public String delCtrl(String userKey,String prod,String exnm) throws Exception;
	//启用/停用
	public String updateCtrl(String userKey,String prod,String usfg,List<PdtCtrlSwhBean> pdtCtrls) throws Exception;
	
}