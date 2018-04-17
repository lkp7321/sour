package com.ylxx.fx.controller.price.pricemonitor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.domain.price.PriceParaVo;
import com.ylxx.fx.service.po.PdtChkparaForAcc;
import com.ylxx.fx.service.po.PdtPointForAcc;
import com.ylxx.fx.service.po.PdtPointTForAcc;
import com.ylxx.fx.service.price.pricemonitor.PriceParaConstService;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;

import java.util.*;
/**
 * 外汇点差查询，产品校验参数
 */
@Controller
//@RequestMapping("fx")
public class PriceParaConstController {

	@Resource(name="priceParaConstService")
	private PriceParaConstService priceParaConstService;
	private String codeMessage="";
	/**
	 * 外汇点差查询,下拉框
	 * @param ptid
	 * @return
	 */
	@RequestMapping(value="price/getSelItems.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getselItems(@RequestBody String ptid){
		List<Map<String,String>> list = priceParaConstService.getItems(ptid);
		if(list!=null&&list.size()>0){
			codeMessage = JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "获取交易类型失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	/**
	 * 获取外汇点差数据
	 * @param priceParaVo
	 * @return
	 */
	@RequestMapping(value="price/getAllpdtprice.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllpdtprice(@RequestBody PriceParaVo priceParaVo){
		String userKey = priceParaVo.getUserKey();
		String ptid = priceParaVo.getProd();
		String ptid1 = priceParaVo.getPtid1();
		List<PdtPointForAcc> list = priceParaConstService.getAllPdtPoint(userKey, ptid, ptid1);
		if(list!= null&&list.size()>0){
			codeMessage = JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "当前数据为空";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
	/**
	 * 修改外汇点差
	 * @param priceParaVo
	 * @return
	 */
	@RequestMapping(value="price/upPricepara.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upPricepara(@RequestBody PriceParaVo priceParaVo){
		String userKey = priceParaVo.getUserKey();
		String prod = priceParaVo.getProd();
		PdtPointTForAcc pdtPoint = priceParaVo.getPdtPoint();
		boolean flag = priceParaConstService.upPoint(userKey, prod, pdtPoint);
		if(flag){
			codeMessage = "修改外汇交易点差成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "修改外汇交易点差失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	/**
	 * 外汇点差生效
	 */
	@RequestMapping(value="price/sendSoketB1.do")
	public void sendSoketB1(){
		priceParaConstService.SendSocketB1();
	}
	/**
	 * 产品校验生效
	 */
	@RequestMapping(value="price/sendSoketB.do")
	public void sendSoketB(){
		priceParaConstService.SendSocketB();
	}
	
	/**
	 * 获取产品校验数据
	 * @param priceParaVo
	 * @return
	 */
	@RequestMapping(value="price/getAllPdtChkpara.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllPdtChkpara(@RequestBody PriceParaVo priceParaVo){
		String userKey = priceParaVo.getUserKey();
		String prod = priceParaVo.getProd();
		List<PdtChkparaForAcc> list = priceParaConstService.getAllPdtChkpara(userKey, prod);
		if(list!=null&&list.size()>0){
			codeMessage=JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "当前记录为空";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
	/**
	 * 全部启用
	 * @param priceParaVo
	 * @return
	 */
	@RequestMapping(value="price/startAll.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String startAll(HttpServletRequest req, @RequestBody PriceParaVo priceParaVo){
		String userKey = priceParaVo.getUserKey();
		String pdtinfoid = priceParaVo.getProd();
		boolean flag = priceParaConstService.startAll(userKey, pdtinfoid);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "启用成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "启用失败");
		}
	}
	/**
	 * 全部停用
	 * @param priceParaVo
	 * @return
	 */
	@RequestMapping(value="price/closeAll.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String closeAll(HttpServletRequest req, @RequestBody PriceParaVo priceParaVo){
		String userKey = priceParaVo.getUserKey();
		String pdtinfoid = priceParaVo.getProd();
		boolean flag = priceParaConstService.endAll(userKey, pdtinfoid);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "停用成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "停用失败");
		}
	}
	
}
