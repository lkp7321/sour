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
import com.ylxx.fx.core.domain.AccRegMsgVo;
import com.ylxx.fx.service.accex.tradeMananger.IRegMsgService;
import com.ylxx.fx.service.po.AccRegmsgBean;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.Table;

/**
 * 账户交易 签约信息查询
 */
@Controller
//@RequestMapping("fx")
public class RegMsgController {

	private static final Logger log = LoggerFactory.getLogger(RegMsgController.class);
	
	@Resource(name="regMsgService")
	private IRegMsgService regMsgService;
	
	/**
	 * 账户交易签约信息查询
	 * @param startDate
	 * @param endDate
	 * @param prcd
	 * @return 
	 */
	@RequestMapping(value="accex/searchRegMsgList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String searchRegMsgList(HttpServletRequest req, @RequestBody AccRegMsgVo accRegMsgVo){
		log.info("签约信息查询：");
		Integer pageNo = accRegMsgVo.getPageNo() == null ? 1 : accRegMsgVo.getPageNo();
		Integer pageSize = accRegMsgVo.getPageSize() == null ? 10 : accRegMsgVo.getPageSize();
		PageHelper.startPage(pageNo, pageSize);
		List<AccRegmsgBean> regMsgList = regMsgService.searchRegmsgList(accRegMsgVo.getStartDate(), accRegMsgVo.getEndDate(), accRegMsgVo.getProd());
		PageInfo<AccRegmsgBean> page = new PageInfo<AccRegmsgBean>(regMsgList);
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), page);
	}
	
	/**
	 * 导出Excel
	 * @param accRegMsgVo
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException 
	 */
	@RequestMapping(value = "accex/searchRegMsgListExcel.do")
    public void searchRegMsgListExcel(AccRegMsgVo accRegMsgVo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = accRegMsgVo.getTableName();//前台传的  表名
        List<Table> tableList = accRegMsgVo.getTableList();//前台传的  表头，及Key
        List<AccRegmsgBean> list = regMsgService.searchRegmsgList(accRegMsgVo.getStartDate(), accRegMsgVo.getEndDate(), accRegMsgVo.getProd());
        PoiExcelExport<AccRegmsgBean> pee = new PoiExcelExport<AccRegmsgBean>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
	
}
