package com.ylxx.fx.core.mapper.pere.presysmanager;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.ElecTellerManagerVo;
import com.ylxx.fx.service.po.ElecTellerManager;


public interface ElecTellerManagerMapper {
	
	//初始化电子柜员信息列表
	public List<ElecTellerManager> selctTellerList() throws Exception;
	
	//条件查询电子柜员信息列表
	
	public List<ElecTellerManager> selectTOgcd(@Param("trtltxt")String trtltxt,
			@Param("comdata1")String comdata1,@Param("bhidp")String bhidp) throws Exception;
	
	//删除柜员信息
	public boolean deleteEleT(@Param("tellerid")String tellerid) throws Exception;
	
	//修改
	public boolean updateEleT(@Param("etmVo")ElecTellerManagerVo etmVo) throws Exception;
	
	
	public List<ElecTellerManager> isTrtlBhidExist(@Param("trtl")String trtl,@Param("bhid")String bhid) throws Exception;

	//初始化外管局机构
	public List<ElecTellerManager> selectOgcd() throws Exception;
	
	//用户添加机构框
	public List<ElecTellerManager> selOrganTab(@Param("usnm")String usnm,
			@Param("orgn")String orgn) throws Exception;
	
	public List<HashMap<String,String>> selectChnlP() throws Exception;
	
	//添加外管局机构
	public boolean insertEleT (@Param("etmVo")ElecTellerManagerVo etmVo) throws Exception;
}
