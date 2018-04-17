package com.ylxx.fx.service.impl.pere.presysmanagerimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.pere.presysmanager.QueryNationCodeMapper;
import com.ylxx.fx.service.pere.presysmanager.IQueryNationCodeService;
import com.ylxx.fx.service.po.Kon_MxSpotTradeBean;
import com.ylxx.fx.service.po.QueryNationCode;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;

@Service("iQueryNationCodeService")
public class QueryNationCodeServiceImpl implements IQueryNationCodeService{
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryNationCodeServiceImpl.class);
	
	@Resource
	private QueryNationCodeMapper queryNationCodeMapper;
	//查询国别代码对照表
	public String selcCount(){
		String result="";
		try {
			List<QueryNationCode> list= queryNationCodeMapper.selctCouList();
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

	//根据输入条件查询国别代码对照表
	public String queryCountByCondition(String fieldName,String fieldValue,Integer pageNo,Integer pageSize){
		String result="";
		pageNo = pageNo==null?1:pageNo;
	 	pageSize = pageSize==null?10:pageSize;
	 	PageHelper.startPage(pageNo, pageSize);
		try {
			List<QueryNationCode> list= queryNationCodeMapper.selctCouListByCondition(fieldName.trim(),fieldValue.trim());
			if (list.size()>0) {
			LOGGER.info("条数为："+list.size());
			PageInfo<QueryNationCode> page = new PageInfo<QueryNationCode>(list);
			result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
			}else{
				result= ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), JSON.toJSONString("查询无结果"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	
	
}
