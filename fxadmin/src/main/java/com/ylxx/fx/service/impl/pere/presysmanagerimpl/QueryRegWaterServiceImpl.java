package com.ylxx.fx.service.impl.pere.presysmanagerimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.pere.presysmanager.QueryRegWaterMapper;
import com.ylxx.fx.service.impl.accumu.businessparaimpl.AccumuTransServiceImpl;
import com.ylxx.fx.service.pere.presysmanager.IQueryRegWaterService;
import com.ylxx.fx.service.po.Kon_MxSpotTradeBean;
import com.ylxx.fx.service.po.QueryRegWater;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;

@Service("iQueryRegWaterService")
public class QueryRegWaterServiceImpl implements IQueryRegWaterService{
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryRegWaterServiceImpl.class);
	@Resource
	private QueryRegWaterMapper queryRegWaterMapper;
	//根据条件查询外管局流水
	
	public String  queryRegWater(String dgfieldbegin,String dgfieldend,String comStcd,Integer pageNo,Integer pageSize) {
		String result = "";
		pageNo = pageNo==null?1:pageNo;
	 	pageSize = pageSize==null?10:pageSize;
	 	PageHelper.startPage(pageNo, pageSize);
		try {
			List<QueryRegWater> list = queryRegWaterMapper.queryRegWater1(dgfieldbegin.trim(),dgfieldend.trim(),comStcd.trim());
		if(list.size()>0){
			LOGGER.info("条数为："+list.size());
			PageInfo<QueryRegWater> page = new PageInfo<QueryRegWater>(list);
			result= ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
		}else {
			result= ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), JSON.toJSONString("查询无结果"));
		}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	};
}
