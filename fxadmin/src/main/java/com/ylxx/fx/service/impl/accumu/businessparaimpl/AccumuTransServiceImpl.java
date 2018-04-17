package com.ylxx.fx.service.impl.accumu.businessparaimpl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.accumu.businesspara.AccumuTransMapper;
import com.ylxx.fx.service.accumu.businesspara.IAccumuTransService;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;

@Service("accumuTransService")
public class AccumuTransServiceImpl implements IAccumuTransService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccumuTransServiceImpl.class);
	@Resource
	private AccumuTransMapper accumutransMapper;

	//查询买币种

	public String selbyexnm(String prod){
		String result = "";
		try {
			List<String> list = accumutransMapper.querybuyexnm(prod);
			if(list.size()>0){
				LOGGER.info("条数为："+list.size());
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
			}else{
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), JSON.toJSONString("查询无结果"));
			}
				
		
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}

	//查询交易码
	public String querytrancode() {
		String result = "";
	/*	pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize ==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		List<PdtRParaTBean> pdtRPataTs;*/
		try {
			List<HashMap<String, String>> list = accumutransMapper.querytrancode();
			
			if(list.size()>0){
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
				LOGGER.info("条数为："+list.size());
			}else{
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), JSON.toJSONString("查询无结果"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	
	// 为第一个机构下拉框赋值
	public String queryOneOrgan()  {
		String result = "";
		try {
			List<HashMap<String, String>> list = accumutransMapper.queryOneOrgan();
			if(list.size()>0){
				LOGGER.info("条数为："+list.size());
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
			}else{
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), JSON.toJSONString("查询无结果"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	
	}

	
	// 为第二个机构下拉框赋值
		public String queryTwoOrgan(String comaogcd)  {
			String result = "";
			try {
				List<HashMap<String, String>> list = accumutransMapper.queryTwoOrgan(comaogcd);
				if(list.size()>0){
					LOGGER.info("条数为："+list.size());
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
				}else{
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), JSON.toJSONString("查询无结果"));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
			}
			return result;
		
		}
		// 条件获得对应的数据
		@Override
		public String selectAccumuTranlist(String comdata1, String comdata3, String strcuac, String trdtbegin,
				String trdtend, String byexnm, String comaogcd, String combogcd, Integer pageNo, Integer pageSize) {
			String result = "";
			pageNo = pageNo==null?1:pageNo;
			pageSize = pageSize==null?10:pageSize;
			PageHelper.startPage(pageNo,pageSize);
			try {
				List<HashMap<String, String>> list = accumutransMapper.selectAccumuTranlist(Integer.valueOf(comdata1),comdata3,strcuac,trdtbegin,trdtend, byexnm, comaogcd, combogcd);
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

}
