package com.ylxx.fx.service.impl.accumu.businessparaimpl;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.accumu.businesspara.AccumuQRegTradeMapper;
import com.ylxx.fx.service.accumu.businesspara.IAccumuQRegTradeService;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;

@Service("accumuQRegTradeService")
public class AccumuQRegTradeServiceImpl implements IAccumuQRegTradeService {

	@Resource
	private AccumuQRegTradeMapper accumuQRegTradeMapper;
	private static final Logger log = LoggerFactory.getLogger(AccumuQRegTradeServiceImpl.class);
	/**
	 * 条件获得对应数据
	 */
	public String selectAccumuTranlist(String comdata1, String comdata3, String strcuac, String trdtbegin,
			String trdtend, String strcuno, String comaogcd, String combogcd, Integer pageNo, Integer pageSize) {
		log.info("comdata1:"+comdata1);
		log.info("comdata3:"+comdata3);
		log.info("strcuac:"+strcuac);
		log.info("trdtbegin:"+trdtbegin);
		log.info("trdtend:"+trdtend);
		log.info("strcuno:"+strcuno);
		log.info("comaogcd:"+comaogcd);
		log.info("combogcd:"+combogcd);
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		List<Map<String, Object>> list = null;
		try {
			 list = accumuQRegTradeMapper.selectAccumuRegTrade(comdata1, comdata3, strcuac, trdtbegin, trdtend, strcuno, comaogcd, combogcd);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		PageInfo<Map<String,Object>> page = new PageInfo<Map<String,Object>>(list);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	
	public List<Map<String, Object>> selectAllAccumuTranlist(
			String comdata1, String comdata3, 
			String strcuac, String trdtbegin,
			String trdtend, String strcuno, 
			String comaogcd, String combogcd){
		List<Map<String, Object>> list = null;
		try {
			 list = accumuQRegTradeMapper.selectAccumuRegTrade(comdata1, comdata3, strcuac, trdtbegin, trdtend, strcuno, comaogcd, combogcd);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		return list;
	}
}
