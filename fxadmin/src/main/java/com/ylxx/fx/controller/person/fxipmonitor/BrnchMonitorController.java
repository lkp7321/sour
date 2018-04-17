package com.ylxx.fx.controller.person.fxipmonitor;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.service.person.fxipmonitor.BrnchMonitorService;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 分行价格监控   && 账户交易价格监控
 */
@Controller
public class BrnchMonitorController {
	private static final Logger log = LoggerFactory.getLogger(BrnchMonitorController.class);
	@Resource(name="brnchMonitorService")
	private BrnchMonitorService brnchMonitorService;
	/**
	 * p001,p999分行价格监控，
	 * p007账户交易价格监控
	 * @param req
	 * @param ptid
	 * @return
	 */
	@RequestMapping(value="getBrnchprice.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getBrnchprice(HttpServletRequest req, @RequestBody String ptid){
		log.info("分行价格监控");
		List<FxipMonitorVo> list = brnchMonitorService.selAllBrnchPrice(ptid);
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "当前无记录");
		}
	}
	/**
	 * P999产品管理分行价格监控下拉框
	 * @param req
	 * @return
	 */
	@RequestMapping(value="getBrnchCom.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getBrnchCom(HttpServletRequest req){
		List<Map<String,Object>> list = brnchMonitorService.selbrnchcom();
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "获取产品错误");
		}
	}
}
