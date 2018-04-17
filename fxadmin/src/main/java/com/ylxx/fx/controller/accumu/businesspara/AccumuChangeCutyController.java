package com.ylxx.fx.controller.accumu.businesspara;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.AccumuBusinessParaVo;
import com.ylxx.fx.service.accumu.businesspara.AccumuChangeCutyService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;
/**
 * 积存金
 * 修改客户等级
 * @author lz130
 *
 */
@Controller
public class AccumuChangeCutyController {
	@Resource(name="accumuChangeCutyService")
	private AccumuChangeCutyService accumuChangeCutyService;
	
	/**
	 * 查询客户信息
	 * @param accumuBusinessParaVo
	 * @return
	 */
	@RequestMapping(value="/changeSearch.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String changeSearch(@RequestBody AccumuBusinessParaVo accumuBusinessParaVo){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(accumuBusinessParaVo.getUserKey());
		return accumuChangeCutyService.getSearch(accumuBusinessParaVo.getCuno(), accumuBusinessParaVo.getCuac(), accumuBusinessParaVo.getPageNo(), accumuBusinessParaVo.getPageSize());
	}
	
	/**
	 * 修改客户等级
	 * @param accumuBusinessParaVo
	 * @return
	 */
	@RequestMapping(value="/getUpdate.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getUpdate(@RequestBody AccumuBusinessParaVo accumuBusinessParaVo){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(accumuBusinessParaVo.getUserKey());
		return accumuChangeCutyService.doUpdateCuty(accumuBusinessParaVo.getCuty(), accumuBusinessParaVo.getCuno(), accumuBusinessParaVo.getCuac(), accumuBusinessParaVo.getTrac());
	}

}
