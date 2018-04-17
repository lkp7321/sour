package com.ylxx.fx.core.mapper.person.ppmagager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.CkHandMxDetail;

public interface MoZhangMapper{
	//获取所有数据
	public List<CkHandMxDetail> selectAllMxDetail(@Param("prod")String prod) throws Exception;
	//条件查询
	public List<CkHandMxDetail> selectMxDetail(@Param("prod")String prod,@Param("dateApdt")
		String dateApdt,@Param("dataEndInput")String dataEndInput) throws Exception;
}