package com.ylxx.qt.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylxx.qt.service.CommonService;
import com.ylxx.qt.service.po.InstruProductBean;
import com.ylxx.qt.service.po.ParameterdetailsBean;

/**
 * 公共管理
 * 
 * @author suimanman
 * 
 */
@Controller
@RequestMapping("/common")
public class CommonController {

	@Resource
	private CommonService comser;

	// 全局搜索：搜索框回车跳转到搜索页面
	@RequestMapping(value = "/toSearch.do")
	public String toSearch(HttpServletRequest request) {
		return "common/search";
	}

	// 全局搜索：从搜索页面跳转到超买/卖监控页面
	@RequestMapping(value = "/toObosmonit.do")
	public String toObosmonit(HttpServletRequest request) {
		return "obosmonit";
	}

	// 全局搜索：从搜索页面跳转到超买/卖监控--新增品种页面
	@RequestMapping(value = "/toNewquerypar.do")
	public String toNewquerypar(HttpServletRequest request) {
		return "marketguess/newquerypar";
	}

	// 全局搜索：从搜索页面跳转到超买/卖监控--新增合约页面
	@RequestMapping(value = "/toQueryallcontract.do")
	public String toQueryallcontract(HttpServletRequest request) {
		return "marketguess/queryallcontract";
	}

	// 全局搜索：根据输入条件查询合约代码、品种名称列表
	@RequestMapping(value = "/getInstruProduct.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody
	String findInsProdList(HttpServletRequest request,
			HttpServletResponse response, Model model, String sear)
			throws Exception {
		String json = "";
		if (null != sear) {
			ObjectMapper mapper = new ObjectMapper();

			List<InstruProductBean> list = new ArrayList<InstruProductBean>();
			list = comser.findInsProdList(sear);
			// 这个集合目的是接受当前查询出的结果
			List<String> resultList = new ArrayList<String>();

			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					resultList.add(list.get(i).getInstrumentid());
					resultList.add(list.get(i).getProductname());
				}
				// 将集合转换为json串
				json = mapper.writeValueAsString(resultList);
			}
		}
		return json;
	}

	// 全局搜索：根据用户id、品种代码、合约名称，获取详细信息
	@RequestMapping(value = "/getParamDetail.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody
	String findParamDetailList(HttpServletRequest request,
			HttpServletResponse response, String instrumentid,
			String productname, String userid, String result) throws Exception {
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		// 接收到的品种名称字符串转换成数组
		String[] prodName = productname.split(",");
		// 接收到的合约代码字符串转换成数组
		String[] instruId = instrumentid.split(",");
		// 根据用户id、搜索内容，获取详细信息
		List<ParameterdetailsBean> list = new ArrayList<ParameterdetailsBean>();
		list = comser.findParamByUseid(userid, result);
		
		// 这个集合目的是接受当前查询出的品种名称结果,用set集合，往set集合里放数据不会出现重复数据
		Set<String> prodList = new HashSet<String>();
		Set<String> prodIdList = new HashSet<String>();
		StringBuffer insBuf = new StringBuffer();
		
		if (null != list && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String cont = list.get(i).getContractset();
				insBuf.append(cont).append(",");// 逗号拼接 合约代码

				prodList.add(list.get(i).getProductname()); // 品种名称
				prodIdList.add(list.get(i).getProductid()); // 品种编号
			}
			// 去掉最后一个 逗号
			String insBufStr = insBuf.toString();
			String[] insBufStrTmp = {};
			if (insBufStr.length() > 0) {
				insBufStr = insBufStr.substring(0, insBufStr.length() - 1);
				insBufStrTmp = insBufStr.split(","); // 转换成数组
			}

			// 先比较品种名称长度和合约代码，全局比用户多的就要跳转到新增的品种页面
			if (prodName.length > prodList.size()
					|| instruId.length > insBufStrTmp.length) {
				if (prodName.length > prodList.size()) {
					// 将跳转到新增品种名称页面关键词转换为json串
					json = mapper.writeValueAsString("newquerypar");

				}
				if (instruId.length > insBufStrTmp.length) {
					// 将跳转到新增合约代码页面关键词与品种转换为json串
					json = mapper.writeValueAsString("queryallcontract"+prodIdList);
				}
			} else {
				// 全局等于用户就要跳转到超买卖页面
				json = mapper.writeValueAsString("obosmonit");
			}
		}else {
			// 全局等于用户就要跳转到超买卖页面
			json = mapper.writeValueAsString("newquerypar");
		}
		return json;
	}

}
