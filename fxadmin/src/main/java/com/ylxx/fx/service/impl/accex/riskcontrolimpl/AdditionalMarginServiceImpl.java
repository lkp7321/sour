package com.ylxx.fx.service.impl.accex.riskcontrolimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.ylxx.fx.core.mapper.accex.riskcontrol.AdditionalMarginMapper;
import com.ylxx.fx.service.accex.riskcontrol.IAdditionalMarginService;
import com.ylxx.fx.service.po.AdditionalMargin;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;

@Service("iAdditionalMarginService")
public class AdditionalMarginServiceImpl implements IAdditionalMarginService{
	private static final Logger LOGGER = LoggerFactory.getLogger(AdditionalMarginServiceImpl.class);
	@Resource
	private AdditionalMarginMapper additionalMarginMapper;
	@Override
	public String addMargin(String userKey, String prod, Integer pageNo, Integer pageSize) throws Exception {
		List<AdditionalMargin> ambList = new ArrayList<AdditionalMargin>();
		String result="";
		pageNo = pageNo==null?1:pageNo;
	 	pageSize = pageSize==null?10:pageSize;
	 	PageHelper.startPage(pageNo, pageSize);
	 	try {
	 		BigDecimal[] risks = getRiskProperties(prod);
	 		if (risks==null){
				return null;
			}
	 		List<AdditionalMargin> list= additionalMarginMapper.addMargin(prod.trim());
	 		AdditionalMargin amb = null;
	 		for (int i = 0; i < list.size(); i++) {
	 			BigDecimal risk = list.get(i).getRisk();
	 			if (risk.compareTo(risks[1])==1 && risk.compareTo(risks[0])<=0){
	 				amb = new AdditionalMargin();
	 				amb.setTrac(list.get(i).getTrac());
	 				amb.setCyen(list.get(i).getCyen());
	 				amb.setAmut(list.get(i).getAmut());
	 				amb.setMarg(list.get(i).getMarg());
	 				amb.setProfit(list.get(i).getProfit());
	 				amb.setRisk(list.get(i).getRisk());
	 				amb.setCuno(list.get(i).getCuno());
	 				amb.setCuac(list.get(i).getCuac());
	 				amb.setTlno(list.get(i).getTlno());
	 				ambList.add(amb);
	 				if (ambList.size()>0) {
	 					LOGGER.info("条数为："+ambList.size());
	 					result=ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(ambList));
	 				}else{
	 					result= ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), JSON.toJSONString("查询无结果"));
	 				}
	 			}
			}
	 		
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	public BigDecimal[] getRiskProperties(String prod) throws Exception{
		List<AdditionalMargin> list = additionalMarginMapper.selectPtpara(prod.trim());
		BigDecimal[] prop = new BigDecimal[2];
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getPaid().equals("BZJ60")){
				prop[0] = new BigDecimal(list.get(i).getValu());
			}
			if(list.get(i).getPaid().equals("BZJ20")){
				prop[1] = new BigDecimal(list.get(i).getValu());
			}
		}
		return prop;
	}

}
