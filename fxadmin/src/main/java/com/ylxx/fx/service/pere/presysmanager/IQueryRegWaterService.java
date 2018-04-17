package com.ylxx.fx.service.pere.presysmanager;

public interface IQueryRegWaterService {
	//根据条件查询外管局流水
	public String  queryRegWater(String dgfieldbegin,String dgfieldend,String comStcd
			,Integer pageNo,Integer pageSize) throws Exception;
}
