package com.ylxx.fx.controller.person.fxipmonitor;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.service.person.fxipmonitor.PpTranMonitorService;
import com.ylxx.fx.service.po.Ck_ppresultdetail;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 平盘交易监控
 */
@Controller
public class PpTranMonitorController {
	private static final Logger log = LoggerFactory.getLogger(PpTranMonitorController.class);
	@Resource(name="ppTranMonitorService")
	private PpTranMonitorService ppTranMonitorService;
	
	/**
	 * 当日成交总数
	 */
	@RequestMapping(value="/todayTranDetils.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String todayTranDetils(HttpServletRequest req){
		log.info("获取当日成交总数");
		String curTime = DataTimeClass.getNowDay();
		String count = ppTranMonitorService.todayPpDetailCount(curTime);
		if(count==null||count.equals("")){
			count = "0";
		}
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), count);
	}
	/**
	 * 当日成交量
	 */
	@RequestMapping(value="/todayTranUsams.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String todayTranUsams(HttpServletRequest req){
		String curTime = DataTimeClass.getNowDay();
		String tosam = ppTranMonitorService.todayPpDetailAm(curTime);
		if(tosam==null||tosam.equals("")){
			tosam = "0";
		}
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), tosam);
	}
	
	/**
	 * 查询平盘交易记录
	 */
	@RequestMapping(value="/queryPpTranLists.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryPpTranLists(HttpServletRequest req, @RequestBody FxipMonitorVo fxipMonVo){
		String bTime = fxipMonVo.getBtime();
		String eTime = fxipMonVo.getEtime();
		List<Ck_ppresultdetail> list = ppTranMonitorService.timePpDetailList(bTime, eTime);
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "暂无符合记录的条件");
		}
	}
}
