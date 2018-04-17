package com.ylxx.fx.core.mapper.person.price;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.PdtCtrlPriBean;

public interface PdtCtrlPriMapper{
	
	//返回指定的产品干预值 通用规则
	public List<PdtCtrlPriBean> selectAllPdtCtrlpri(@Param("ptid")String ptid) throws Exception;
	
}