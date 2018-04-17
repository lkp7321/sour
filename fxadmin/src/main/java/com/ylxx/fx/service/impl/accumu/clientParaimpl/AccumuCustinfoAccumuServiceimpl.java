package com.ylxx.fx.service.impl.accumu.clientParaimpl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.accumu.clientPara.AccumuCustinfoAccumuMapper;
import com.ylxx.fx.service.accumu.clientPara.IAccumuCustinfoAccumuService;
import com.ylxx.fx.service.impl.accumu.businessparaimpl.AccumuTransServiceImpl;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;

@Service("accumuCustinfoAccumuService")
public class AccumuCustinfoAccumuServiceimpl implements IAccumuCustinfoAccumuService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccumuTransServiceImpl.class);
	@Resource
	private AccumuCustinfoAccumuMapper accumuCustinfoAccumuMapper;

	//获得全部数据

	public String queryRegmsgInfoList(String prod,String strcuno, String strcuac, String comaogcd, String combogcd,Integer pageNo, Integer pageSize){
		String result = "";
		try {
			pageNo = pageNo==null?1:pageNo;
			pageSize = pageSize==null?10:pageSize;
			PageHelper.startPage(pageNo,pageSize);
			List<HashMap<String, String>> list = accumuCustinfoAccumuMapper.queryRegmsgInfoList(prod,strcuno,strcuac,comaogcd,combogcd);
			if(list.size()>0){
				LOGGER.info("条数为："+list.size());
				PageInfo<HashMap<String,String>> page=new PageInfo<HashMap<String,String>>(list);
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
			}else{
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), JSON.toJSONString("查询无结果"));
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}

		//获得对应数据

		public String selectTrac(String trac){
			String result = "";
			try {
				List<HashMap<String, String>> list = accumuCustinfoAccumuMapper.selectTrac(trac);
				LOGGER.info("条数为："+list.size());
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
			} catch (Exception e) {
				e.printStackTrace();
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
			}
			return result;
		}

}

