package com.ylxx.fx.controller.price.pricesource;

import javax.annotation.Resource;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.service.po.CmmPrice;
import com.ylxx.fx.service.price.pricesource.ExnmService;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;

@Controller
//@RequestMapping("fx")
public class ExnmController {
	
	@Resource(name="exnmService")
	private ExnmService exnmService;
	private String codeMessage="";
	
	@RequestMapping(value="price/getPdtinfoMap.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getPdtinfoMap(){
		List<Map<String,String>> list = exnmService.getPdtinfo();
		if(list!=null&&list.size()>0){
			codeMessage=JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="获取市场失败！";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	
	@RequestMapping(value="price/getCMMPRICE.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getCMMPRICEList(@RequestBody String mkinfo){
		List<CmmPrice> list = exnmService.getCMMPRICE(mkinfo);
		if(list!=null&&list.size()>0){
			codeMessage=JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="当前市场暂无数据";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
}
