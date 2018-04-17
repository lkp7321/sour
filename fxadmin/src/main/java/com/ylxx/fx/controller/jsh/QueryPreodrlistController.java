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

package com.ylxx.fx.controller.jsh;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.service.jsh.QueryPreodrlistService;
import com.ylxx.fx.service.po.jsh.JshPages;
import com.ylxx.fx.service.po.jsh.QueryPreodrlistIn;
import com.ylxx.fx.service.po.jsh.Trd_Preodrlist;
import com.ylxx.fx.utils.PoiExcelExport;

@Controller
public class QueryPreodrlistController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryPreodrlistController.class);
	
	@Resource(name="queryPreodrlistService")
	private QueryPreodrlistService queryPreodrlistService;
	
	/*QueryPreodrlistIn in = new QueryPreodrlistIn();
 	JshPages<QueryPreodrlistIn> jshPages = new JshPages<QueryPreodrlistIn>(); */	
	
	//挂单流水查询
	@RequestMapping(value="/getPreodrlist.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getPreodrlist(@RequestBody JshPages<QueryPreodrlistIn> jshPages) throws Exception {
	   //Deal : customerNo 客户号, contractNo 挂单合同号,startDate 起始日期,endDate 结束日期,queryType 状态
		/*in.setQutp(3);
		in.setProductCode("P004");
		in.setCuno("123");
		in.setRqno("1");
		in.setStdt("20170101");
		in.setEddt("20180122");
		jshPages.setPageNo(1);
		jshPages.setPageSize(5);
		jshPages.setEntity(in);*/
		return queryPreodrlistService.getPreodrlist(jshPages.getEntity(), jshPages.getPageNo(), jshPages.getPageSize());	
	}
	/**
	 * 导出Excel
	 */
	@RequestMapping(value = "/queryPreodrlistExcel.do")
    public void queryPreodrlistExcel(String tableName, String poductCode, int qutp, String cuno, String stdt, String eddt, String rqno, JshPages<QueryPreodrlistIn> jshPages, HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		LOGGER.info("导出Excel");
		List<Trd_Preodrlist> list = queryPreodrlistService.getPreodrList(poductCode, qutp, cuno, stdt, eddt, rqno);
        PoiExcelExport<Trd_Preodrlist> pee = new PoiExcelExport<Trd_Preodrlist>(tableName, jshPages.getTableList(), list, resp);
        pee.exportExcel();
    }
	
}
