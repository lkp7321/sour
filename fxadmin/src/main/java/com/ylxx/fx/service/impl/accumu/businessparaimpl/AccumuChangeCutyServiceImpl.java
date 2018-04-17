package com.ylxx.fx.service.impl.accumu.businessparaimpl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.accumu.businesspara.AccumuChangeCutyMapper;
import com.ylxx.fx.service.accumu.businesspara.AccumuChangeCutyService;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;
@Service("accumuChangeCutyService")
public class AccumuChangeCutyServiceImpl implements AccumuChangeCutyService {
	@Resource
	public AccumuChangeCutyMapper accumuChangeCutyMapper;
	@Override
	public String getSearch(String cuno, String cuac,Integer pageNo,Integer pageSize) {
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		String result = "";
		try {
		List<HashMap<String, String>> list = accumuChangeCutyMapper.getSearch(cuno, cuac);
		if(list.size()>0){
			PageInfo<HashMap<String,String>> page=new PageInfo<HashMap<String,String>>(list);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
		}else{
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), "查询无记录");
		}
		
		}catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	@Override
	public String doUpdateCuty(String cuty, String cuno, String cuac, String trac) {
		String result = "";
		try{
			accumuChangeCutyMapper.doUpdateCuty(cuty, cuno, cuac, trac);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), "修改成功");
		}catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), "修改失败");
		}
		return result;
	}

}
