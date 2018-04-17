package com.ylxx.fx.service.impl.pere.presysmanagerimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.pere.presysmanager.QueryDiscountRateMapper;
import com.ylxx.fx.service.pere.presysmanager.IQueryDiscountRateService;
import com.ylxx.fx.service.po.QueryDiscountRate;
import com.ylxx.fx.service.po.QueryNationCode;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;

@Service("iQueryDiscountRateService")
public class QueryDiscountRateServiceImpl implements IQueryDiscountRateService{
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryDiscountRateServiceImpl.class);

	@Resource
	private QueryDiscountRateMapper queryDiscountRateMapper;
	
	//折算汇率查询
	public String selcDisRate(){
		String result="";
		try {
			List<QueryDiscountRate> list= queryDiscountRateMapper.selDisRateList();
			if (list.size()>0) {
				LOGGER.info("条数为："+list.size());
				result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
			}else{
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), JSON.toJSONString("查询无结果"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//分页
	public PageInfo<QueryDiscountRate> pageSelcDisRate(Integer pageNo, Integer pageSize){
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		List<QueryDiscountRate> list = null;
		try {
			list = queryDiscountRateMapper.selDisRateList();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		PageInfo<QueryDiscountRate> page = new PageInfo<QueryDiscountRate>(list);
		return page;
	}
	//导出
	public List<QueryDiscountRate> selcAllDisRate(){
		List<QueryDiscountRate> list = null;
		try {
			list = queryDiscountRateMapper.selDisRateList();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return list;
	}

}
