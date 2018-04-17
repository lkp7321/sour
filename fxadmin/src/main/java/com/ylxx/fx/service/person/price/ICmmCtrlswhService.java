package com.ylxx.fx.service.person.price;

import java.util.List;

import com.ylxx.fx.service.po.CmmCtrlswh;

public interface ICmmCtrlswhService {

	//加载页面数据
	public String selectCtrlSwh() throws Exception;
	//查询直盘币别对
	public String curExnm() throws Exception;
	//查询价格类型
	public String selectPrice() throws Exception;
	//保存
	public String addCmmCtrlSwh(String userKey,CmmCtrlswh cmmbean) throws Exception;
	//修改
	public String upCmmCtrlSwh(String userKey, CmmCtrlswh cmmbean) throws Exception;
	//删除
	public String delCmmCtrl(String userKey, List<CmmCtrlswh> cmmbeans) throws Exception;
	//启用/停用
	public String updateCmmCtrl(String userKey, String usfg,List<CmmCtrlswh> cmmbeans) throws Exception;
	
}

