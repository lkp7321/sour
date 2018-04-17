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

import com.ylxx.fx.core.domain.KondorVo;
import com.ylxx.fx.service.bail.eodprocess.IBailOutAccountService;

@Controller
//@RequestMapping("fx")
public class BailOutAccountController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BailOutAccountController.class);
	
	@Resource(name="bailOutAccountService")
	private IBailOutAccountService bailOutAccountService;
	
	//查询交易码
	@RequestMapping(value="/seltrancode.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String seltrancode() throws Exception {
		return bailOutAccountService.seltrancode();	
	}
	//查询批量出金数据
	@RequestMapping(value="/seleOutAccountList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String seleOutAccountList(@RequestBody KondorVo kdVo) throws Exception {
		/*KondorVo kdVo = new KondorVo();
		kdVo.setStartDate("20130808");
		kdVo.setEndDate("20130808");
		kdVo.setStatus("1");
		kdVo.setPageNo(null);
		kdVo.setPageSize(null);*/
		return bailOutAccountService.seleOutAccountList(kdVo.getStartDate(), kdVo.getStatus(),
				kdVo.getEndDate(), kdVo.getPageNo(), kdVo.getPageSize());
	}
	//核对提交
	@RequestMapping(value="/hedui.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String hedui(@RequestBody KondorVo kdVo) throws Exception {
		/*KondorVo kdVo = new KondorVo();
		kdVo.setUser("xlj");
		kdVo.setProd("P009");
		List<BailTranlist> list = new ArrayList<BailTranlist>();
		BailTranlist list1 = new BailTranlist();
		list1.setLcno("00022080");
		list1.setTrac("800005");
		list1.setCuac("6226193300092077");
		list1.setStcd("MT4出金成功");
		BailTranlist list2 = new BailTranlist();
		list2.setLcno("00022081");
		list2.setTrac("800005");
		list2.setCuac("6226193300092077");
		list2.setStcd("MT4出金成功");
		list.add(list1);
		list.add(list2);
		kdVo.setList(list);*/
		return bailOutAccountService.hedui(kdVo.getUserKey(),kdVo.getList());
	}
	//核对取消
	@RequestMapping(value="/cancelHedui.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String cancelHedui(@RequestBody KondorVo kdVo) throws Exception {
		/*KondorVo kdVo = new KondorVo();
		kdVo.setUser("xlj");
		kdVo.setProd("P009");
		List<BailTranlist> list = new ArrayList<BailTranlist>();
		BailTranlist list1 = new BailTranlist();
		list1.setLcno("00022080");
		list1.setTrac("800005");
		list1.setCuac("6226193300092077");
		list1.setStcd("MT4出金成功");
		BailTranlist list2 = new BailTranlist();
		list2.setLcno("00022081");
		list2.setTrac("800005");
		list2.setCuac("6226193300092077");
		list2.setStcd("MT4出金成功");
		list.add(list1);
		list.add(list2);
		kdVo.setList(list);*/
		return bailOutAccountService.cancelHedui(kdVo.getUserKey(),kdVo.getList());
	}
	//复核
	@RequestMapping(value="/fuhe.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String fuhe(@RequestBody KondorVo kdVo) throws Exception {
		/*KondorVo kdVo = new KondorVo();
		kdVo.setUser("xlj");
		kdVo.setProd("P009");
		List<BailTranlist> list = new ArrayList<BailTranlist>();
		BailTranlist list1 = new BailTranlist();
		list1.setLcno("00022080");
		list1.setTrac("800005");
		list1.setCuac("6226193300092077");
		list1.setStcd("MT4出金成功");
		BailTranlist list2 = new BailTranlist();
		list2.setLcno("00022081");
		list2.setTrac("800005");
		list2.setCuac("6226193300092077");
		list2.setStcd("MT4出金成功");
		list.add(list1);
		list.add(list2);
		kdVo.setList(list);*/
		return bailOutAccountService.fuhe(kdVo.getUserKey(),kdVo.getList());
	}
}
