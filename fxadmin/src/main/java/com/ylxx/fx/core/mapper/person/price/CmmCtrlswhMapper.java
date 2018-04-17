package com.ylxx.fx.core.mapper.person.price;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.CmmCtrlswh;

public interface CmmCtrlswhMapper{
	
	//查询页面数据
	public List<CmmCtrlswh> selectCmmPrice() throws Exception;
	//查询直盘币别对
	public List<HashMap<String, String>> selectPriceUSD() throws Exception;
	//查询价格类型
	public List<HashMap<String, String>> selectPrice() throws Exception;
	//查询记录是否存在 
	public List<CmmCtrlswh> checkExsit(@Param("cmmbean")CmmCtrlswh cmmbean) throws Exception;
	//添加点差干预上限
	public Boolean insertPrice(@Param("cmmbean")CmmCtrlswh cmmbean) throws Exception;
	//修改点差干预上限
	public Boolean updatePrice(@Param("cmmbean")CmmCtrlswh cmmbean) throws Exception;
	//删除
	public Boolean deletePrice(@Param("cmmbean")CmmCtrlswh cmmbean) throws Exception;
	//启用/停用
	public Boolean updateCtrlusfg(@Param("cmmbean")CmmCtrlswh cmmbean) throws Exception;
	
}