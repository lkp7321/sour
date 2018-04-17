package com.ylxx.fx.core.mapper.bail.eodprocess;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.BailTranlist;

public interface BailOutAccountMapper{
	
	//查询交易码
	public List<HashMap<String, String>> querytrancode() throws Exception;
	//选择核对时的所有数据
	public List<BailTranlist> selectOutAccountHDList(@Param("hddate")String hddate,
			@Param("status")String status,@Param("enddate")String enddate) throws Exception;
	//核对提交
	public boolean hedui(@Param("lcno")String lcno) throws Exception;
	//核对取消
	public boolean cancelHedui(@Param("lcno")String lcno) throws Exception;
	//复核
	public boolean fuhe(@Param("lcno")String lcno) throws Exception;
	
}