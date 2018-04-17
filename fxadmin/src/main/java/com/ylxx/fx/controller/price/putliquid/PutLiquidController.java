package com.ylxx.fx.controller.price.putliquid;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.price.PutLiQuidVo;
import com.ylxx.fx.service.po.PdtChkpara;
import com.ylxx.fx.service.po.Pdtinfo;
import com.ylxx.fx.service.po.Put_Config;
import com.ylxx.fx.service.po.Put_Liquid;
import com.ylxx.fx.service.price.putliquid.PutliQuidService;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 报价平台--发布管理
 */
@Controller
//@RequestMapping("fx")
public class PutLiquidController {
	private static final Logger log = LoggerFactory.getLogger(PutLiquidController.class);
	@Resource(name="putliQuidService")
	private PutliQuidService putliQuidService;
	private String codeMessage = "";
	/**
	 * 价格使用管理--所有数据
	 * @param putLiQuidVo
	 * @return
	 */
	@RequestMapping(value="/price/allLiquid.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getallPutLiquid(HttpServletRequest req, @RequestBody PutLiQuidVo putLiQuidVo){
		String stat = putLiQuidVo.getStat();
		Integer pageNo = putLiQuidVo.getPageNo();
		Integer pageSize = putLiQuidVo.getPageSize();
		PageInfo<Put_Liquid> page = putliQuidService.getAllPutliQuid(stat, pageNo, pageSize);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	//申请
	@RequestMapping(value="/price/addLiquid.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addPutLiquid(@RequestBody PutLiQuidVo putLiQuidVo){
		String userKey = putLiQuidVo.getUserKey();
		Put_Liquid put_Liquid =  putLiQuidVo.getPut_Liquid();
		boolean flag = putliQuidService.addPutliquid(userKey, put_Liquid);
		if(flag){
			codeMessage = "提交价格使用申请成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "提交价格使用申请失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	//审批
	@RequestMapping(value="/price/upLiquid.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upPutLiquid(@RequestBody PutLiQuidVo putLiQuidVo){
		String userKey = putLiQuidVo.getUserKey();
		Put_Liquid put_Liquid = putLiQuidVo.getPut_Liquid();
		boolean flag = putliQuidService.updatePutliquid(userKey, put_Liquid);
		if(flag){
			codeMessage = "审批价格使用成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "审批价格使用失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	
	//接口配置
	@RequestMapping(value="/price/upBuildLiquid.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upBuildLiquid(HttpServletRequest req, @RequestBody PutLiQuidVo putLiQuidVo){
		log.info("配置价格使用接口：");
		String userKey = putLiQuidVo.getUserKey();
		String inid = putLiQuidVo.getInid();
		List<Put_Config> put_ConfigList = putLiQuidVo.getPut_ConfigList();
		List<Pdtinfo> pdtinList = putLiQuidVo.getPdtinList();
		Put_Liquid put_Liquid = putLiQuidVo.getPut_Liquid();
		return putliQuidService.upbuildput(userKey, inid, put_ConfigList, pdtinList, put_Liquid);
	}
	
	//产品列表
	@RequestMapping(value="/price/getLiquiPtno.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getLiquiPtno(){
		List<Map<String,String>> list = putliQuidService.getAllptno();
		if(list.size()>0){
			codeMessage = JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "获取产品列表失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	//所有货币对
	@RequestMapping(value="/price/allLiquidJK.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String allLiquidJK(@RequestBody String inid){
		List<PdtChkpara> list = putliQuidService.getAllPtidJK(inid);
		codeMessage = JSON.toJSONString(list);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
	}
	//根据产品获取货币
	@RequestMapping(value="/price/getLiquidptidJK.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getLiquidptidJK(@RequestBody String prod){
		List<PdtChkpara> list = putliQuidService.getPtidJk(prod);
		codeMessage = JSON.toJSONString(list);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
	}
	//获取当前选中数据
	@RequestMapping(value="/price/getOnLiquid.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getOnLiquid(@RequestBody Put_Liquid put_Liquid){
		codeMessage = JSON.toJSONString(putliQuidService.getonlyquid(put_Liquid.getSqid(),put_Liquid.getName(),put_Liquid.getUnit()));
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
	}
}
