package com.ylxx.fx.controller.price.alarm;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;
import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.domain.price.AlarmVo;
import com.ylxx.fx.service.po.CmmAlarmCode;
import com.ylxx.fx.service.price.alarm.AlarmCodeService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Controller
//@RequestMapping("fx")
public class AlarmCodeController {
	@Resource(name="alarmCodeService")
	private AlarmCodeService alarmCodeService;
	private String codeMessage = "";
	@RequestMapping(value="price/alarmCodeList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllAlarmCode(HttpServletRequest req){
		List<Map<String, String>> list = alarmCodeService.getAllalarmCode();
		if(list!=null&&list.size()>0){
			codeMessage = JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "当前数据为空";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
	@RequestMapping(value="price/modifyAlarmCode.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String modifyAlarmCode(HttpServletRequest req,@RequestBody AlarmVo alarmVo){
		String userKey = alarmVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		CmmAlarmCode cmmAlarmCode = alarmVo.getAlarmCode();
		boolean flag = alarmCodeService.upAlarmCode(curUser, cmmAlarmCode, curUser.getCurIP());
		if(flag){
			codeMessage="告警代码修改成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		} else{
			codeMessage="告警代码修改失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	@RequestMapping(value="price/addAlarmCode.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addAlarmCode(HttpServletRequest req,@RequestBody AlarmVo alarmVo){
		String userKey = alarmVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		CmmAlarmCode cmmAlarmCode = alarmVo.getAlarmCode();
		boolean flag = alarmCodeService.insAlarmCode(curUser, cmmAlarmCode, curUser.getCurIP());
		if(flag){
			codeMessage="告警代码添加成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		} else{
			codeMessage="告警代码添加失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	@RequestMapping(value="price/removeAlarmCode.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String removeAlarmCode(HttpServletRequest req,@RequestBody AlarmVo alarmVo){
		String userKey = alarmVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ercd = alarmVo.getErcd();
		boolean flag = alarmCodeService.delAlarmCode(curUser, ercd, curUser.getCurIP());
		if(flag){
			codeMessage="告警代码删除成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		} else{
			codeMessage="告警代码删除失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
}
