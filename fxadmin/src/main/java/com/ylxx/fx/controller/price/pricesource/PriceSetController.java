package com.ylxx.fx.controller.price.pricesource;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.domain.price.PriceJiaSetVo;
import com.ylxx.fx.core.domain.price.PriceJiaVo;
import com.ylxx.fx.service.po.calendar.CalendarVO;
import com.ylxx.fx.service.price.pricesource.PriceSetService;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.UDPClient;

@Controller
//@RequestMapping("fx")
public class PriceSetController {
	@Resource(name="priceSetService")
	private PriceSetService priceSetService;
	private String codeMessage = "";
	private UDPClient udclient = new UDPClient();
	@RequestMapping(value="price/getPriceJiaList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getallPriceSet(){
		List<PriceJiaVo> list = priceSetService.getPriceJiaList();
		if(list!=null&&list.size()>0){
			codeMessage = JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			codeMessage = "暂无记录";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
	//下拉列表
	@RequestMapping(value="price/getpriceProd.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getallPriceSetProd(){
		List<Map<String, String>> list = priceSetService.getProList();
		if(list!=null&&list.size()>0){
			codeMessage = JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "获取产品列表失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	@RequestMapping(value="price/removePriceSet.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delPriceSet(@RequestBody PriceJiaSetVo priceSetVo){
		String userKey = priceSetVo.getUserKey();
		PriceJiaVo priceJiaBean = priceSetVo.getPriceJiaBean();
		boolean flag = priceSetService.delPriceJia(userKey, priceJiaBean);
		if(flag){
			codeMessage = "删除成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "删除失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	@RequestMapping(value="price/savePriceSet.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String insPriceSet(@RequestBody PriceJiaSetVo priceSetVo){
		String userKey = priceSetVo.getUserKey();
		PriceJiaVo priceJiaBean = priceSetVo.getPriceJiaBean();
		boolean flag = priceSetService.savePriceJia(userKey, priceJiaBean);
		if(flag){
			codeMessage = "添加成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "添加失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	
	@RequestMapping(value="price/modifyPriceSet.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upPriceSet(@RequestBody PriceJiaSetVo priceSetVo){
		String userKey = priceSetVo.getUserKey();
		PriceJiaVo priceJiaBean = priceSetVo.getPriceJiaBean();
		boolean flag = priceSetService.upPriceJia(userKey, priceJiaBean);
		if(flag){
			codeMessage = "修改成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "修改失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	
	@RequestMapping(value="price/showPriceSet.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String showPriceSet(@RequestBody PriceJiaSetVo priceSetVo){
		String userKey = priceSetVo.getUserKey();
		String proid = priceSetVo.getProd();
		String caltime = priceSetVo.getTime();
		List<CalendarVO> list = priceSetService.getPriceJiacalRuleList(userKey, proid, caltime);
		if(list!=null && list.size()>0){
			codeMessage = JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "暂无记录";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}
	}
	//生效
	@RequestMapping(value="socketInfo.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public void sendSocketInfo(){
		udclient.SendSocketPdtInfo();
	}
	
}
