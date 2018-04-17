package com.ylxx.fx.controller.accumu.clientPara;

/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.AccumuBusinessParaVo;
import com.ylxx.fx.service.accumu.clientPara.IAccumuCustinfoAccumuService;


@Controller
//@RequestMapping("fx")
public class AccumuCustinfoAccumuController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccumuCustinfoAccumuController.class);
	
	@Resource(name="accumuCustinfoAccumuService")
	private IAccumuCustinfoAccumuService iAccumuCustinfoAccumuService;
	
	//获得全部数据
	@RequestMapping(value="/queryRegmsgInfoList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryRegmsgInfoList(@RequestBody AccumuBusinessParaVo AccumuBusinessParaVo) throws Exception { 
	//	String strcuno, String strcuac, String comaogcd, String combogcd
		//@RequestBody AccumuBusinessParaVo AccumuBusinessParaVo
		/*AccumuBusinessParaVo AccumuBusinessParaVo =new AccumuBusinessParaVo();
		AccumuBusinessParaVo.setPageNo(10);
		AccumuBusinessParaVo.setPageSize(20);
		AccumuBusinessParaVo.setStrcuac("");
		AccumuBusinessParaVo.setStrcuno("");
		AccumuBusinessParaVo.setComaogcd("");
		AccumuBusinessParaVo.setCombogcd("");
		AccumuBusinessParaVo.setProd("P003");*/
		
		return iAccumuCustinfoAccumuService.queryRegmsgInfoList(AccumuBusinessParaVo.getProd(),AccumuBusinessParaVo.getStrcuno(),AccumuBusinessParaVo.getStrcuac(),AccumuBusinessParaVo.getComaogcd(),AccumuBusinessParaVo.getCombogcd(),AccumuBusinessParaVo.getPageNo(),AccumuBusinessParaVo.getPageSize());	
	}
}