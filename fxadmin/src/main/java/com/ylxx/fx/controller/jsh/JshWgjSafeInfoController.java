package com.ylxx.fx.controller.jsh;

import javax.annotation.Resource;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.jsh.JshWgjSafeInfoService;
import com.ylxx.fx.service.po.jsh.JshPages;
import com.ylxx.fx.service.po.jsh.Trd_SafeInfo;
import com.ylxx.fx.service.po.jsh.Trd_SafePrice;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 外管信息,利率
 * @author lz130
 *
 */
@Controller
public class JshWgjSafeInfoController {
	private static final Logger log = LoggerFactory.getLogger(JshWgjSafeInfoController.class);
	
	@Resource(name="jshWgjSafeInfoService")
	private JshWgjSafeInfoService jshWgjSafeInfoService;
	
	/**
	 * 查询利率表
	 */
	@RequestMapping(value="/jsh/getListSafePrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getJshSafePrices(@RequestBody String cyen) {
		log.info("开始查询利率表");
		List<Map<String, Object>> list = jshWgjSafeInfoService.selectJshSafePriceList(cyen);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	/**
	 * 利率添加
	 * @param trd_SafePrice
	 * @return
	 */
	@RequestMapping(value="/jsh/addSafePrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addJshSafePrices(@RequestBody JshPages<Trd_SafePrice> trd_SafePrice) {
		log.info("开始查询利率表");
		return jshWgjSafeInfoService.insertJshSafePrice(trd_SafePrice);
	}
	
	/**
	 * 外管局登陆信息查询
	 */
	@RequestMapping(value="/jsh/getListSafeInfo.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getJshSafeInfos(@RequestBody JshPages<Trd_SafeInfo> trd_SafeInfo) {
		PageInfo<Map<String, Object>> page = jshWgjSafeInfoService.selectJshSafeInfoList(trd_SafeInfo);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	
	/**
	 * 添加登陆外管
	 */
	@RequestMapping(value="/jsh/addSafeInfo.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addJshSafeInfos(@RequestBody JshPages<Trd_SafeInfo> trd_SafeInfo) {
		return jshWgjSafeInfoService.insertJshSafeInfo(trd_SafeInfo);
	}
	
	/**
	 * 修改登陆外管
	 */
	@RequestMapping(value="/jsh/updateListSafeInfo.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String modifyJshSafeInfos(@RequestBody JshPages<Trd_SafeInfo> trd_SafeInfo) {
		return jshWgjSafeInfoService.updateJshSafeInfo(trd_SafeInfo);
	}
	
	/**
	 * 删除登陆外管
	 */
	@RequestMapping(value="/jsh/removeSafeInfo.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String removeJshSafeInfos(@RequestBody JshPages<Trd_SafeInfo> trd_SafeInfo) {
		return jshWgjSafeInfoService.deleteJshSafeInfo(trd_SafeInfo);
	}
}
