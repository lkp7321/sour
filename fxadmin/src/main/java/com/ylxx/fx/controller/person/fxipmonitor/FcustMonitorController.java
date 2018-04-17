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
import com.ylxx.fx.service.person.fxipmonitor.FcustMonitorService;
import com.ylxx.fx.service.po.PereCustMonitorB;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;
/**
 * P001,P004
 * 客户价格监控
 */
@Controller
public class FcustMonitorController {
	private static final Logger log = LoggerFactory.getLogger(FcustMonitorController.class);
	@Resource(name="fcustMonitorService")
	private FcustMonitorService fcustMonitorService;
	
	@RequestMapping(value="getAllpdtlist.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllgetAllpdtlist(HttpServletRequest req, @RequestBody String ptid){
		log.info("查询客户价格监控");
		if(ptid.equals("p004")){
			List<PereCustMonitorB> list = fcustMonitorService.selAllPdtP004();
			if(list.size()>0){
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
			}else{
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "当前无记录");
			}
		}else{
			List<FxipMonitorVo> list = fcustMonitorService.selAllPdt(ptid);
			if(list.size()>0){
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
			}else{
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "当前无记录");
			}
		}
	}
}
