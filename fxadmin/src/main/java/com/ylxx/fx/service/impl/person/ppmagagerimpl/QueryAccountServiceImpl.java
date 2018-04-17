package com.ylxx.fx.service.impl.person.ppmagagerimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.ppmagager.QueryAccountMapper;
import com.ylxx.fx.service.person.ppmagager.IQueryAccountService;
import com.ylxx.fx.service.po.Ck_ppresultdetail;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;



@Service("queryAccountService")
public class QueryAccountServiceImpl implements IQueryAccountService {

	@Resource
	private QueryAccountMapper queryAccountMapper;

	//获取页面数据列表
	public String selectList(String jgdt,String trdt){
		String result = "";
		List<Ck_ppresultdetail> ppresults;
		try {
			ppresults = queryAccountMapper.selectList(jgdt, trdt);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(),JSON.toJSONString(ppresults));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}

}
