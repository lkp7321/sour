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
import com.ylxx.fx.service.po.CmmAlarmNotify;
import com.ylxx.fx.service.price.alarm.AlarmFsService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Controller
//@RequestMapping("fx")
public class AlarmFsController {
	@Resource(name="alarmFsService")
	private AlarmFsService alarmFsService;
	private String codeMessage="";
	
	@RequestMapping(value="price/allAlarmFsList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String allAlarmFsList(HttpServletRequest req){
		List<CmmAlarmNotify> list = alarmFsService.getAllAlarmNotify();
		if(list!=null && list.size()>0){
			codeMessage=JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="当前数据为空";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
	@RequestMapping(value="price/addAlarmFs.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addAlarmFs(HttpServletRequest req,@RequestBody AlarmVo alarmVo){
		String userKey = alarmVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		CmmAlarmNotify notify = alarmVo.getNotify();
		boolean flag = alarmFsService.addAlarmfs(curUser, notify, curUser.getCurIP());
		if(flag){
			codeMessage="添加告警方式成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="添加告警方式失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	@RequestMapping(value="price/modifyAlarmFs.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String modifyAlarmFs(HttpServletRequest req,@RequestBody AlarmVo alarmVo){
		String userKey = alarmVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		CmmAlarmNotify notify = alarmVo.getNotify();
		boolean flag = alarmFsService.modifyAlarmfs(curUser, notify, curUser.getCurIP());
		if(flag){
			codeMessage="修改告警方式成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="修改告警方式失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	@RequestMapping(value="price/delAlarmFs.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delAlarmFs(HttpServletRequest req, @RequestBody AlarmVo alarmVo){
		String userKey = alarmVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		CmmAlarmNotify notify = alarmVo.getNotify();
		boolean flag = alarmFsService.delAlarmfs(curUser, notify, curUser.getCurIP());
		if(flag){
			codeMessage="删除告警方式成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="删除告警方式失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	//
	@RequestMapping(value="price/alarmFsbox.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String alarmFsbox(HttpServletRequest req){
		List<Map<String,String>> list = alarmFsService.getAlarmStcd();//用户
		List<Map<String,String>> list1 = alarmFsService.selectAlarmLeveAll();//等级
		List<Map<String,String>> list2 = alarmFsService.selectAlarmERR();//错误
		if((list!=null&&list.size()>0)&&(list1!=null&&list1.size()>0)&&(list2!=null&&list2.size()>0)){
			codeMessage = "{\"us\":"+JSON.toJSONString(list)+","+"\"le\":"+JSON.toJSONString(list1)+","+"\"er\":"+JSON.toJSONString(list2)+"}";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(),codeMessage);
		}else{
			if(list==null || list.size()<=0){
				codeMessage = "用户下拉框获取失败，不能添加";
			}
			if(list1==null || list1.size()<=0){
				codeMessage = "等级下拉框获取失败，不能添加";
			}
			if(list2==null || list2.size()<=0){
				codeMessage = "等级下拉框获取失败，不能添加";
			}
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
}
