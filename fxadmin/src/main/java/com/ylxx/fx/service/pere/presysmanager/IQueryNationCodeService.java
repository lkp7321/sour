package com.ylxx.fx.service.pere.presysmanager;

public interface IQueryNationCodeService {
	//查询国别代码对照表
	public String selcCount() throws Exception;
	//根据输入条件查询国别代码对照表
	public String queryCountByCondition(String fieldName,String fieldValue,Integer pageNo,Integer pageSize) throws Exception;

}
