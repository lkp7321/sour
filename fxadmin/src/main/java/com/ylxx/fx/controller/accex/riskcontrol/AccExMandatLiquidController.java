package com.ylxx.fx.controller.accex.riskcontrol;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ylxx.fx.core.domain.AdditionalMarginVo;
import com.ylxx.fx.service.accex.riskcontrol.IAccExMandatLiquidService;
@Controller
//@RequestMapping("fx")
@Scope("prototype")
public class AccExMandatLiquidController {
	@Resource(name="accExMandatLiquidService")
	private IAccExMandatLiquidService accExMandatLiquidService;	
	@RequestMapping(value="/mandatLiquid.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String mandatLiquid(@RequestBody AdditionalMarginVo additionalMarginVo){
		return accExMandatLiquidService.mandatLiquid(additionalMarginVo.getUserKey(),additionalMarginVo.getProd(),additionalMarginVo.getPageSize(),additionalMarginVo.getPageNo());
	}
	@RequestMapping(value="/mandatLiquidService.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String mandatLiquidService(@RequestBody AdditionalMarginVo additionalMarginVo) {
		return accExMandatLiquidService.mandatLiquidService(additionalMarginVo.getUserKey(),additionalMarginVo.getAdmlist());
	}
	/**
	 * 更新自动强平开关配置状态 
	 * @param prod
	 * @return 00开启01关闭 02失败
	 */
	@RequestMapping(value="/upAutoForceStat.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upAutoForceStat(@RequestBody AdditionalMarginVo additionalMarginVo) {
		return accExMandatLiquidService.upAutoForceStat(additionalMarginVo.getProd(),additionalMarginVo.getStat());
	}
	/**
	 * 获取自动强平开关配置状态   
	 * @param userKey
	 * @param prod
	 * @return
	 */
	@RequestMapping(value="/getAutoForceStat.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAutoForceStat(@RequestBody AdditionalMarginVo additionalMarginVo) {
		return accExMandatLiquidService.getAutoForceSta(additionalMarginVo.getUserKey(),additionalMarginVo.getProd());
	}
}
