package com.ylxx.fx.core.mapper.pere.presysmanager;

import java.util.List;

import com.ylxx.fx.service.po.QueryDiscountRate;
public interface QueryDiscountRateMapper {
	
	//折算汇率查询
	public List<QueryDiscountRate> selDisRateList() throws Exception;

}
