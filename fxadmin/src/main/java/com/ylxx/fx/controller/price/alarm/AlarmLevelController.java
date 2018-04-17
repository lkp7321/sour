package com.ylxx.fx.controller.price.alarm;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.domain.price.AlarmVo;
import com.ylxx.fx.service.po.CmmAlarmLevel;
import com.ylxx.fx.service.price.alarm.AlarmLevelService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Controller
//@RequestMapping("fx")
public class AlarmLevelController {
	@Resource(name="alarmLevelService")
	private AlarmLevelService alarmLevelService;
	private String codeMessage="";
	@RequestMapping(value="price/alarmLevelList.do", produces="aplication/json;charset=UTF-8")
	@ResponseBody
	public String allAlarmLevelList(HttpServletRequest req){
		List<CmmAlarmLevel> list = alarmLevelService.getAllAlarmLevel();
		if(list!=null && list.size()>0){
			codeMessage = JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "当前数据为空";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
	
	@RequestMapping(value="price/addAlarmLevel.do", produces="aplication/json;charset=UTF-8")
	@ResponseBody
	public String addAlarmLevel(HttpServletRequest req,@RequestBody AlarmVo alarmVo){
		String userKey = alarmVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		CmmAlarmLevel cmmAlarmLevel = alarmVo.getAlarmLevel();
		boolean flag = alarmLevelService.insAlarmLevel(curUser, cmmAlarmLevel, curUser.getCurIP());
		if(flag){
			codeMessage = "告警等级添加成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "告警等级添加失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	@RequestMapping(value="price/modifyAlarmLevel.do", produces="aplication/json;charset=UTF-8")
	@ResponseBody
	public String modifyAlarmLevel(HttpServletRequest req, @RequestBody AlarmVo alarmVo){
		String userKey = alarmVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		CmmAlarmLevel cmmAlarmLevel = alarmVo.getAlarmLevel();
		boolean flag = alarmLevelService.upAlarmLevel(curUser, cmmAlarmLevel, curUser.getCurIP());
		if(flag){
			codeMessage = "告警等级修改成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "告警等级修改失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	@RequestMapping(value="price/removeAlarmLevel.do", produces="aplication/json;charset=UTF-8")
	@ResponseBody
	public String removeAlarmLevel(HttpServletRequest req,@RequestBody AlarmVo alarmVo){
		String userKey = alarmVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String alid = alarmVo.getAlid();
		boolean flag = alarmLevelService.delAlarmLevel(curUser, alid, curUser.getCurIP());
		if(flag){
			codeMessage = "告警等级删除成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "告警等级删除失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
}
