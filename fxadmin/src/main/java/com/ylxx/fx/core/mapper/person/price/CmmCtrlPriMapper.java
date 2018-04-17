package com.ylxx.fx.core.mapper.person.price;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.CmmCtrlpri;

public interface CmmCtrlPriMapper{
	
	//查询货币对
	public List<HashMap<String, String>> selectPrices() throws Exception;
	//查询页面数据
	public List<CmmCtrlpri> selectAllCmmCtrlpri(@Param("exnm")String exnm) throws Exception;
	//查询添加数据是否重复
	public List<CmmCtrlpri> checkRepeat(@Param("cmmCtrlpri")CmmCtrlpri cmmCtrlpri) throws Exception; 
	//添加
	public Boolean insertPrice(@Param("cmmCtrlpri")CmmCtrlpri cmmCtrlpri) throws Exception;
	//修改
	public Boolean updateCtrl(@Param("cmmCtrlpri")CmmCtrlpri cmmCtrlpri) throws Exception;
	//启用/停用
	public Boolean updateStfg(@Param("cmmCtrlpri")CmmCtrlpri cmmCtrlpri) throws Exception;
	
}