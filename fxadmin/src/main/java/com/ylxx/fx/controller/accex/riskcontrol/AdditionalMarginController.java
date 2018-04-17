package com.ylxx.fx.controller.accex.riskcontrol;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.AdditionalMarginVo;
import com.ylxx.fx.service.accex.riskcontrol.IAdditionalMarginService;

@Controller
//@RequestMapping("fx")
public class AdditionalMarginController {
	
	@Resource(name="iAdditionalMarginService")
	private IAdditionalMarginService iAdditionalMarginService;
	
	@RequestMapping(value="/addMargin.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	//@RequestBody AdditionalMarginVo AmVo
	public String addMargin(@RequestBody AdditionalMarginVo AmVo) throws Exception{
		return iAdditionalMarginService.addMargin(AmVo.getUserKey(),AmVo.getProd(),
				AmVo.getPageNo(),AmVo.getPageSize());
	}

	
	
}
