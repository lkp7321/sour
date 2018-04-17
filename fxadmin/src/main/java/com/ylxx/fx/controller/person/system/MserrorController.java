package com.ylxx.fx.controller.person.system;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.SystemVo;
import com.ylxx.fx.service.person.system.MserrorService;
/**
 * 错误码管理
 * @author lz130
 *
 */
@Controller
public class MserrorController {
	private static final Logger log = LoggerFactory.getLogger(MserrorController.class);
	
	@Resource(name="mserrorService")
	private MserrorService mserrorService;
	/**
	 * 查询错误码
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/getMserrors.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getMserrors(HttpServletRequest req, @RequestBody SystemVo sysVo){
		log.info("错误码管理查询数据");
		Integer pageNo = sysVo.getPageNo();
		Integer pageSize = sysVo.getPageSize();
		String strTxt = sysVo.getStrTxt();//错误号编号
		String strTxt1 = sysVo.getStrTxt1();//错误说明
		return mserrorService.getMsgErrorList(pageNo, pageSize, strTxt, strTxt1);
	}
	@RequestMapping(value="/addMserrors.do", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String addMserrors(HttpServletRequest req, String userKey, String ercd, String ertx, String erin){
		return mserrorService.addMsgError(userKey, ercd, ertx, erin);
	}
	@RequestMapping(value="/updateMserrors.do", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String upsMserrors(HttpServletRequest req, String userKey, String ercd, String ertx, String erin){
		return mserrorService.upsMsgError(userKey, ercd, ertx, erin);
	}
	@RequestMapping(value="/deleteMserrors.do", produces="text/html;charset=UTF-8")
	@ResponseBody
	public String delMserrors(HttpServletRequest req, String userKey, String ercd){
		return mserrorService.delMsgError(userKey, ercd);
	}
	
}
