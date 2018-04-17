package com.ylxx.fx.core.mapper.pere.presysmanager;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.QueryNationCode;

public interface QueryNationCodeMapper {
	//查询国别代码对照表
	public List<QueryNationCode> selctCouList() throws Exception;
	//
	public List<QueryNationCode> selctCouListByCondition(@Param("fieldName")String fieldName,
			@Param("fieldValue")String fieldValue) throws Exception;
	
	

}
