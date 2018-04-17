package com.ylxx.fx.core.mapper.pere.presysmanager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.QueryRegWater;

public interface QueryRegWaterMapper {
	//根据条件查询外管局流水   
	public List<QueryRegWater> queryRegWater1(@Param("dgfieldbegin")String dgfieldbegin,
			@Param("dgfieldend")String dgfieldend,@Param("comStcd")String comStcd) throws Exception;

}
