package com.ylxx.fx.controller.price.pricecontxp;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.domain.price.CmmFiltrate;
import com.ylxx.fx.core.domain.price.Countexp;
import com.ylxx.fx.service.price.pricecontxp.PriceContxpService;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 交叉盘计算，策略字典
 */
@Controller
//@RequestMapping("fx")
public class PriceContxpController {
	@Resource(name="priceContxpService")
	private PriceContxpService priceContxpService;
	private String codeMessage="";
	//查询交叉盘计算1
	@RequestMapping(value="price/getPriceContxp.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getPriceContxp(){
		List<Countexp> list = priceContxpService.getContexpPrice();
		if(list.size()>0){
			codeMessage=JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="暂无记录";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
	//查询策略字典
	@RequestMapping(value="price/getPriceFile.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getPriceFile(){
		List<CmmFiltrate> list = priceContxpService.getPriceFile();
		if(list.size()>0){
			codeMessage=JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "暂无记录";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
	//交叉盘计算2//p007,p999
	@RequestMapping(value="price/getPriceContxp1.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getPriceContxp1(){
		List<Countexp> list = priceContxpService.getContexpPrice1();
		if(list.size()>0){
			codeMessage=JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="暂无记录";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
}
