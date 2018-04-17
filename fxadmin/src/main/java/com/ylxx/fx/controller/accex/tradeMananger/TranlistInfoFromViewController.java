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

package com.ylxx.fx.controller.accex.tradeMananger;

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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.ViewDoneDetailVo;
import com.ylxx.fx.service.accex.tradeMananger.ITranlistInfoFromViewService;
import com.ylxx.fx.service.po.ViewDoneDetailBean;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.Table;

/**
 * 账户交易，
 *交易流水查询,贵金属流水查询
 */
@Controller
//@RequestMapping("fx")
public class TranlistInfoFromViewController {

	private static final Logger log = LoggerFactory.getLogger(TranlistInfoFromViewController.class);
	
	@Resource(name="tranlistInfoFromViewService")
	private ITranlistInfoFromViewService tranlistInfoFromViewService;
	
	/**
	 * 流水表视图 VIEW_DONEDETAIL_P007
	 * @param startDate
	 * @param endDate
	 * @param prod
	 * @param multiple
	 * @return 
	 */
	@RequestMapping(value="/accex/getTranlistInfoFromView.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getTranlistInfoFromView(@RequestBody ViewDoneDetailVo viewDoneDetailVo){
		log.info("查询账户交易，交易流水:");
		// 分页
		Integer pageNo = viewDoneDetailVo.getPageNo() == null ? 1 : viewDoneDetailVo.getPageNo();
		Integer pageSize = viewDoneDetailVo.getPageSize() == null ? 10 : viewDoneDetailVo.getPageSize();
		PageHelper.startPage(pageNo, pageSize);
		List<ViewDoneDetailBean> tranList = tranlistInfoFromViewService.getTranlistInfoFromView(viewDoneDetailVo.getStartDate(), viewDoneDetailVo.getEndDate(), viewDoneDetailVo.getProd(), viewDoneDetailVo.getMultiple());
		PageInfo<ViewDoneDetailBean> page = new PageInfo<ViewDoneDetailBean>(tranList);
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), page);
	}
	//导出Excel
	@RequestMapping(value="/accex/TranlistInfoViewExcel.do")
	public void getTranlistInfoViewExcel(ViewDoneDetailVo viewDoneDetailVo,HttpServletRequest req, HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException{
		String fileName = viewDoneDetailVo.getTableName();//前台传的  表名
        List<Table> tableList = viewDoneDetailVo.getTableList();//前台传的  表头，及Key
		List<ViewDoneDetailBean> list = tranlistInfoFromViewService.getTranlistInfoFromView(viewDoneDetailVo.getStartDate(), viewDoneDetailVo.getEndDate(), viewDoneDetailVo.getProd(), viewDoneDetailVo.getMultiple());
        PoiExcelExport<ViewDoneDetailBean> pee = new PoiExcelExport<ViewDoneDetailBean>(fileName, tableList, list, resp);
        pee.exportExcel();
	}
	
	/**
	 * 贵金属流水查询
	 * @param startDate
	 * @param endDate
	 * @param prod
	 * @return 
	 */
	@RequestMapping(value="/accex/getGJSTranlist.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getGJSTranlist(@RequestBody ViewDoneDetailVo viewDoneDetailVo){
		// 分页
		Integer pageNo = viewDoneDetailVo.getPageNo() == null ? 1 : viewDoneDetailVo.getPageNo();
		Integer pageSize = viewDoneDetailVo.getPageSize() == null ? 10 : viewDoneDetailVo.getPageSize();
		PageHelper.startPage(pageNo, pageSize);
		List<ViewDoneDetailBean> tranList = tranlistInfoFromViewService.getGJSTranlist(viewDoneDetailVo.getStartDate(), viewDoneDetailVo.getEndDate(), viewDoneDetailVo.getProd());
		PageInfo<ViewDoneDetailBean> page = new PageInfo<ViewDoneDetailBean>(tranList);
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), page);
	}
	
	//导出Excel
	@RequestMapping(value="/accex/getGJSTranlistExcel.do",produces="application/json;charset=UTF-8")
	public void getGJSTranlistExcel(ViewDoneDetailVo viewDoneDetailVo,HttpServletRequest req, HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException{
		String fileName = viewDoneDetailVo.getTableName();//前台传的  表名
        List<Table> tableList = viewDoneDetailVo.getTableList();//前台传的  表头，及Key
        List<ViewDoneDetailBean> list = tranlistInfoFromViewService.getGJSTranlist(viewDoneDetailVo.getStartDate(), viewDoneDetailVo.getEndDate(), viewDoneDetailVo.getProd());
        PoiExcelExport<ViewDoneDetailBean> pee = new PoiExcelExport<ViewDoneDetailBean>(fileName, tableList, list, resp);
        pee.exportExcel();
	}
	
}
