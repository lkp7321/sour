package com.ylxx.fx.service.pere.presysmanager;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.QueryDiscountRate;

public interface IQueryDiscountRateService {
	//折算汇率查询
	public String selcDisRate() throws Exception;
	//分页,导出
	public PageInfo<QueryDiscountRate> pageSelcDisRate(Integer pageNo,Integer pageSize) throws Exception;
	public List<QueryDiscountRate> selcAllDisRate() throws Exception;
}
