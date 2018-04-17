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

package com.ylxx.fx.controller.bail.eodprocess;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.service.bail.eodprocess.IBailMT4ReportService;

@Controller
//@RequestMapping("fx")
public class BailMT4ReportController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BailMT4ReportController.class);
	
	@Resource(name="bailMT4ReportService")
	private IBailMT4ReportService bailMT4ReportService;
	
	//检查文件是否存在
	@RequestMapping(value="/balanceQuery.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String balanceQuery(@RequestBody String date) throws Exception {
		return bailMT4ReportService.checkFileExist(date);	
	}
	//查看
	/*public String sendToURL() throws Exception {
		String url = "./scanHtm.jsp";
		URLRequest request = new URLRequest(encodeURI(url));
		URLVariables variables = new URLVariables();
		return null;	
	}*/
	
}
