package com.ylxx.fx.core.mapper.person.price;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.PdtStoperBean;

public interface PdtStoperMapper{
	
	//获取所有的产品价格停牌数据
	public List<PdtStoperBean> selectAllPdtStoper(@Param("ptid")String ptid) throws Exception;
	
}