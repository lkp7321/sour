package com.ylxx.fx.service.person.price;

import java.util.List;

import com.ylxx.fx.service.po.CmmCtrlpri;

public interface ICmmCtrlPriService {

	//查询货币对
	public String allExnm() throws Exception;
	//查询页面数据
	public String allCmmCtrlPri(String exnm) throws Exception;
	//添加
	public String addCmmCtrlpri(String userKey,CmmCtrlpri cmmCtrl) throws Exception;
	//修改
	public String updateCmmCtrlpri(String userKey,CmmCtrlpri cmmCtrl) throws Exception;
	//启用/停用
	public String upCmmCtrlPriStfg(String userKey,List<CmmCtrlpri> ctrlpris,String usfg) throws Exception;
	
}

