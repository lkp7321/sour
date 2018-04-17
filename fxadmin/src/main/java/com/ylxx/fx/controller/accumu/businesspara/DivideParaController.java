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

package com.ylxx.fx.controller.accumu.businesspara;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.DivideParaVo;
import com.ylxx.fx.core.domain.UserVo;
import com.ylxx.fx.service.accumu.businesspara.IDivideParaService;
import com.ylxx.fx.service.po.DivideParaBean;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;

/**
 * 分红日
 * @author lz130
 *
 */
@Controller
public class DivideParaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DivideParaController.class);
	
	@Resource(name="divideParaService")
	private IDivideParaService divideParaService;
	
	List<DivideParaBean> dlist = null;
	
	/**
	 * 查询分红
	 * @param userKey
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/getDivideParaList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getDivideParaList(@RequestBody UserVo userVo ){
		String result = "";
		// 分页
		Integer pageNo = userVo.getPageNo() == null ? 1 : userVo.getPageNo();
	 	Integer pageSize = userVo.getPageSize() == null ? 10 : userVo.getPageSize();
	 	PageHelper.startPage(pageNo, pageSize);
		try {
			dlist = divideParaService.searchDivideParaList();
			if (dlist != null && dlist.size()>0) {
				//用PageInfo对结果进行包装
				PageInfo<DivideParaBean> page = new PageInfo<DivideParaBean>(dlist);
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
				LOGGER.info("条数为："+ dlist.size());
			}else if (dlist != null && dlist.size() == 0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	
//	/**
//	 * 查删除后分红列表	
//	 * @param userKey
//	 * @param dddt
//	 * @return
//	 */
//	@RequestMapping(value="/doDeleteDivideParaSearch.do",produces="application/json;charset=UTF-8")
//	@ResponseBody
//	public String doDeleteDivideParaSearch(@RequestBody String userKey, String dddt){
//		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
//		Boolean deleteResult = false;
//		String result = "";
//		// 分页
//		Integer pageNo =  1;
//		Integer pageSize = 10;
//	 	PageHelper.startPage(pageNo, pageSize);
//		//1. 删除分红
//		try {
//			deleteResult = divideParaService.doDeleteDividePara(dddt);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//2. 查分红表
//		if(deleteResult){
//			try {
//				dlist = divideParaService.searchDivideParaList();
//				if (dlist != null && dlist.size()>0) {
//					//用PageInfo对结果进行包装
//					PageInfo<DivideParaBean> page = new PageInfo<DivideParaBean>(dlist);
//					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
//					LOGGER.info("条数为："+ dlist.size());
//				}else if (dlist != null && dlist.size() == 0) {
//					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
//			}
//		}
//		return result;
//	}
	
	/**
	 * 插入分红列表
	 * @param userKey	
	 * @param dddt
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doInsertDividePara.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String doInsertDividePara(@RequestBody DivideParaVo divideParaVo) throws Exception{
		return divideParaService.updateCmmParaAndinsertDividePara(divideParaVo.getDddt());
	}
}
