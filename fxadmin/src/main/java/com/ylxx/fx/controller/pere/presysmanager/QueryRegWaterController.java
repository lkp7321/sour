package com.ylxx.fx.controller.pere.presysmanager;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.PreSysManagerVo;
import com.ylxx.fx.service.pere.presysmanager.IQueryRegWaterService;


@Controller
//@RequestMapping("fx")
public class QueryRegWaterController {
private static final Logger LOGGER = LoggerFactory.getLogger(QueryRegWaterController.class);
	
	@Resource(name="iQueryRegWaterService")
	private IQueryRegWaterService iQueryRegWaterService;
    private PreSysManagerVo preSysManagerVo;
	
	//
	@RequestMapping(value="/queryRegWater.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	//@RequestBody PreSysManagerVo preSysManagerVo
	public String queryRegWater(@RequestBody PreSysManagerVo preSysManagerVo) throws Exception {
		/*PreSysManagerVo preSysManagerVo= new PreSysManagerVo();
		preSysManagerVo.setComStcd("");
		preSysManagerVo.setDgfieldbegin("");
		preSysManagerVo.setDgfieldend("");
		preSysManagerVo.setPageNo(null);
		preSysManagerVo.setPageSize(null);*/
		return iQueryRegWaterService.queryRegWater(preSysManagerVo.getComStcd(),preSysManagerVo.getDgfieldbegin(),
				preSysManagerVo.getDgfieldend(),preSysManagerVo.getPageNo(),preSysManagerVo.getPageSize());	
	}
	

}
