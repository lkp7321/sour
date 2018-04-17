package com.ylxx.fx.controller.price.alarm;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.price.AlarmVo;
import com.ylxx.fx.service.po.Cmmalarm;
import com.ylxx.fx.service.price.alarm.AlarmEventService;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;

@Controller
//@RequestMapping("fx")
public class AlarmEventController {
	@Resource(name="alarmEventService")
	private AlarmEventService alarmEventService;
	/**
	 * 告警事件--查询
	 * @param alarmVo
	 * @return
	 */
	@RequestMapping(value="price/alarmEventList.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String alarmEventList(HttpServletRequest req, @RequestBody AlarmVo alarmVo){
		String betime = alarmVo.getBetime();
		String ercd = alarmVo.getErcd();
		Integer pageNo = alarmVo.getPageNo();
		Integer pageSize = alarmVo.getPageSize();
		PageInfo<Cmmalarm> page = alarmEventService.getAlarmEvent(betime, ercd, pageNo, pageSize);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	/**
	 * 告警事件--错误代码
	 * @param req
	 * @return
	 */
	@RequestMapping(value="price/alarmEventBox.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String alarmEventBox(HttpServletRequest req){
		List<Map<String, String>> list = alarmEventService.notifyAlarmEvent();
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
}
