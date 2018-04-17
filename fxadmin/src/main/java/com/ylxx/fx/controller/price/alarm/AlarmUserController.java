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
import com.ylxx.fx.service.po.Cmmalarmuser;
import com.ylxx.fx.service.price.alarm.AlarmUserService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 告警管理
 */
@Controller
//@RequestMapping("fx")
public class AlarmUserController {
	@Resource(name="alarmUserService")
	private AlarmUserService alarmUserService;
	private String codeMessage="";
	/**
	 * 告警用户--数据查询
	 * @return
	 */
	@RequestMapping(value="/price/alarmUserList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String alarmUserList(HttpServletRequest req){
		List<Cmmalarmuser> list = alarmUserService.getAlarmUser();
		if(list!=null&&list.size()>0){
			codeMessage = JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "当前数据为空";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	/**
	 * 添加
	 * @param req
	 * @param cmmalarmuser
	 * @return
	 */
	@RequestMapping(value="/price/addAlarmUser.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addAlarmUser(HttpServletRequest req, @RequestBody AlarmVo alarmVo){
		String userKey = alarmVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Cmmalarmuser cmmalarmuser = alarmVo.getAlarmUser();
		boolean flag = alarmUserService.insAlarmUser(curUser, cmmalarmuser, curUser.getCurIP());
		if(flag){
			codeMessage = "告警用户添加成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "告警用户添加失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	/**
	 * 修改
	 * @param req
	 * @param cmmalarmuser
	 * @return
	 */
	@RequestMapping(value="/price/modifyAlarmUser.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String modifyAlarmUser(HttpServletRequest req, @RequestBody AlarmVo alarmVo){
		String userKey = alarmVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Cmmalarmuser cmmalarmuser = alarmVo.getAlarmUser();
		boolean flag = alarmUserService.upAlarmUser(curUser, cmmalarmuser, curUser.getCurIP());
		if(flag){
			codeMessage = "告警用户修改成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "告警用户修改失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	/**
	 * 告警用户--删除数据
	 * @param req
	 * @param cmmalarmuser
	 * @return
	 */
	@RequestMapping(value="/price/removeAlarmUser.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String removeAlarmUser(HttpServletRequest req, @RequestBody AlarmVo alarmVo){
		String userKey = alarmVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Cmmalarmuser cmmalarmuser = alarmVo.getAlarmUser();
		boolean flag = alarmUserService.delAlarmUser(curUser, cmmalarmuser.getUsid(), cmmalarmuser.getUsnm(), curUser.getCurIP());
		if(flag){
			codeMessage = "告警用户删除成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "告警用户删除失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	/**
	 * 初始化id
	 * @return
	 */
	@RequestMapping(value="/price/alarmUserUsid.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String alarmUserUsid(HttpServletRequest req){
		codeMessage=alarmUserService.getAlarmUsid();
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
	}
}

