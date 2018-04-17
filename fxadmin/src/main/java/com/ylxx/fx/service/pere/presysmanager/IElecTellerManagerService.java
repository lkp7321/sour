package com.ylxx.fx.service.pere.presysmanager;

import java.util.List;

import com.github.pagehelper.PageInfo;
 import com.ylxx.fx.core.domain.ElecTellerManagerVo;
import com.ylxx.fx.service.po.ElecTellerManager;

public interface IElecTellerManagerService {
	//初始化电子柜员信息列表
	public String selcTellerList() throws Exception;
	public PageInfo<ElecTellerManager> pageSelcAllTellerList(Integer pageNo, Integer pageSize);
	public List<ElecTellerManager> selcAllTellerList();
	
	//条件查询电子柜员信息列表
	public String selcPreMessage(String trtltxt, String comdata1, String bhidp,Integer pageNo,Integer pageSize) throws Exception;

	public String deleteEleTeller(String userKey,String tellerid) throws Exception;
	
	public String updateEleT(String userKey,ElecTellerManagerVo etmVo) throws Exception;
	
	
	public String isTrtlBhidExist(String userKey,String bhid ,String trtl) throws Exception;
	
	//初始化外管局机构
	public String selectOgcd() throws Exception;
	
	//用户添加机构框
	public String selectTOgcd(String orgn, String usnm,Integer pageNo,Integer pageSize) throws Exception;
	
	public String selectChnlP() throws Exception;
	
	//添加外管局机构
	public String insertEleT(String userKey,ElecTellerManagerVo etmVo) throws Exception;
}
