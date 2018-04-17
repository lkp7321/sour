package com.ylxx.fx.core.mapper.person.price;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.PdtPointBean;

public interface PdtPointMapper{
	
	//查询产品点差列表
	public List<PdtPointBean> selectAllPdtPoint(@Param("ptid")String ptid) throws Exception;
	
}