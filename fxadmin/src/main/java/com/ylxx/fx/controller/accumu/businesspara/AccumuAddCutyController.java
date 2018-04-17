package com.ylxx.fx.controller.accumu.businesspara;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.AccumuBusinessParaVo;
import com.ylxx.fx.service.accumu.businesspara.AccumuAddCutyService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;

/**
 * 添加客户等级
 * @author lz130
 *
 */
@Controller
public class AccumuAddCutyController {
	@Resource(name="accumuAddCutyService")
	private AccumuAddCutyService accumuAddCutyService;
	
	//查询等级
	@RequestMapping(value="/getSearchList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getSearchList(@RequestBody AccumuBusinessParaVo accumuBusinessParaVo){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(accumuBusinessParaVo.getUserKey());
		return accumuAddCutyService.doSearchList(accumuBusinessParaVo.getPtid(), accumuBusinessParaVo.getGstp(), accumuBusinessParaVo.getPageNo(), accumuBusinessParaVo.getPageSize());
	}
	//添加核心等级
	@RequestMapping(value="/addCoreCuty.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addCoreCuty(@RequestBody AccumuBusinessParaVo accumuBusinessParaVo){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(accumuBusinessParaVo.getUserKey());
		String name = "核心等级";
		String gstp = "1";
		return accumuAddCutyService.insertCuty(gstp, accumuBusinessParaVo.getBp(), name, accumuBusinessParaVo.getPtid());
	}
	//添加自定义等级
	@RequestMapping(value="/addDefineCuty.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addDefineCuty(@RequestBody AccumuBusinessParaVo accumuBusinessParaVo){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(accumuBusinessParaVo.getUserKey());
		String name = "自定义等级";
		String gstp = "1";
		return accumuAddCutyService.insertCuty(gstp, accumuBusinessParaVo.getBp(), name, accumuBusinessParaVo.getPtid());
	}
	//反显等级
	@RequestMapping(value="/selMaxCuty.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selMaxCuty(@RequestBody String tynm){
		return accumuAddCutyService.selMaxCuty(tynm);
	}
}
