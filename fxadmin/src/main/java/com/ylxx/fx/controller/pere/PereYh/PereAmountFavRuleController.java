package com.ylxx.fx.controller.pere.PereYh;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.PereAmountFavRuleVo;
import com.ylxx.fx.service.pere.PereYh.IPereAmountFavRuleService;

@Controller
//@RequestMapping("fx")
public class PereAmountFavRuleController {
	@Resource(name = "pereAmountFavRuleService")
	private IPereAmountFavRuleService pereAmountFavRuleService;
	
	/**
	 * @AUTHOR 桑海峰 添加交易折美元优惠
	 * @PARAM PereAmountFavRuleVo pereAmountFavRuleVo
	 * @RETURN
	 */
	@RequestMapping(value = "/addAmountRuleByAmount.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String addAmountRuleByAmount(@RequestBody PereAmountFavRuleVo pereAmountFavRuleVo){
		return pereAmountFavRuleService.addAmountRuleByAmount(pereAmountFavRuleVo);
	}
	
	/**
	 * @AUTHOR 桑海峰 删除交易折美元优惠
	 * @PARAM PereAmountFavRuleVo pereAmountFavRuleVo
	 * @RETURN
	 */
	@RequestMapping(value = "/deleteFavrRuleByAmount.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteFavrRuleByAmount(@RequestBody PereAmountFavRuleVo pereAmountFavRuleVo){
		return pereAmountFavRuleService.deleteFavrRuleByAmount(pereAmountFavRuleVo);
	}
	
	/**
	 * @AUTHOR 桑海峰 修改交易折美元优惠
	 * @PARAM PereAmountFavRuleVo pereAmountFavRuleVo
	 * @RETURN
	 */
	@RequestMapping(value = "/updateAmountFavRuleByAmount.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String updateAmountFavRuleByAmount(@RequestBody PereAmountFavRuleVo pereAmountFavRuleVo){
		return pereAmountFavRuleService.updateAmountFavRuleByAmount(pereAmountFavRuleVo);
	}
	/**
	 * @AUTHOR 桑海峰 根据机构号获取交易折美元优惠
	 * @PARAM PereAmountFavRuleVo pereAmountFavRuleVo
	 * @RETURN
	 */
	@RequestMapping(value = "/getAmountFavRuleByOgcd.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getAmountFavRuleByOgcd(@RequestBody PereAmountFavRuleVo pereAmountFavRuleVo){
		return pereAmountFavRuleService.getAmountFavRuleByOgcd(pereAmountFavRuleVo.getOgcd(), pereAmountFavRuleVo.getPageNo(), pereAmountFavRuleVo.getPageSize());
	}
	/**
	 * @AUTHOR 桑海峰 根据机构号获取交易折美元优惠最后一条数据
	 * @PARAM PereAmountFavRuleVo pereAmountFavRuleVo
	 * @RETURN
	 */
	@RequestMapping(value = "/getAmountFavRuleLastData.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getAmountFavRuleLastData(@RequestBody PereAmountFavRuleVo pereAmountFavRuleVo){
		return pereAmountFavRuleService.getAmountFavRuleLastData(pereAmountFavRuleVo.getOgcd());
	}
}
