/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ylxx.fx.controller.user;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.Trd_ogcdDoms;
import com.ylxx.fx.core.domain.UserDoms;
import com.ylxx.fx.core.domain.UserVo;
import com.ylxx.fx.core.mapper.user.UserParaMapper;
import com.ylxx.fx.service.LoginService;
import com.ylxx.fx.service.po.RoleBean;
import com.ylxx.fx.service.po.User;
import com.ylxx.fx.service.user.UserService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.DesService;
import com.ylxx.fx.utils.ErrorCode;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.Table;
/**
 * 用户管理
 * @author lz130
 *
 */
@Controller
public class UserController {
	private DesService des = new DesService();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="loginServicer")
	private LoginService loginServicer;
	
	@Resource
	private UserParaMapper usermap;
	
	/**
	 * 用户信息页面
	 * @return
	 */
	@RequestMapping("touser.do")
	public String toUser() {
		LOGGER.info("到用户信息");
		return "personaloffer/user/userinfo";
	}
	/**
	 * 用户复核页面
	 * @return
	 */
	@RequestMapping("touserFg.do")
	public String toUserFg() {
		LOGGER.info("到用户复核");
		return "personaloffer/user/userreview";
	}
	/**
	 * 异常登录页面
	 * @return
	 */
	@RequestMapping("toerror.do")
	public String toerror() {
		LOGGER.info("到登录异常");
		return "personaloffer/user/unusualuser";
	}
	/**
	 * 修改密码页面
	 * @return
	 */
	@RequestMapping("toupPswd.do")
	public String toupPswd() {
		LOGGER.info("到修改密码");
		return "personaloffer/user/psdset";
	}
	
	/**
	 * 获取用户信息列表
	 * @param req
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value="/listUser.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllUser(HttpServletRequest req, @RequestBody UserVo userVo){
		LOGGER.info("获取用户信息列表：");
		String userKey = userVo.getUserKey();
		Integer pageNo = userVo.getPageNo();
		Integer pageSize = userVo.getPageSize();
		PageInfo<UserDoms> page = userService.getPageUserList(pageNo, pageSize, userKey);
		return ResultDomain.getRtnMsg(ErrorCode.E_0020.getCode(), page);
	}
	
	
	/**
	 * 初始化角色
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="toInitRole.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String toInitRole(@RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		LOGGER.info("【初始化角色下拉框】："+"登录用户："+curUser.getUsnm()+"产品"+curUser.getProd());
		User user = new User();
		user.setProd(curUser.getProd());
		user.setUsnm(curUser.getUsnm());
		List<RoleBean> list = userService.initSelRole(user.getProd());//获取角色列表
		if(list.size()>0&&list!=null){
			LOGGER.info("【初始化角色下拉框】："+"登录用户："+curUser.getUsnm()+"产品"+curUser.getProd()+"【成功】");
			String json = JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrorCode.E_0020.getCode(), json);
		}else{
			LOGGER.error("【初始化角色下拉框】："+"登录用户："+curUser.getUsnm()+"产品"+curUser.getProd()+"【失败】");
			return ResultDomain.getRtnMsg(ErrorCode.E_9993.getCode(), null);
		}
		
	}
	/**
	 * 初始化机构
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="toInitOG.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String toInitOG(@RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		LOGGER.info("【初始化机构下拉框】："+"登录用户："+curUser.getUsnm()+"产品"+curUser.getProd());
		User user = new User();
		user.setProd(curUser.getProd());
		user.setUsnm(curUser.getUsnm());
		String orgn = curUser.getCstn();
		if(orgn.equals("0001") || user.getUsnm().equals("Administrator")){
			List<Trd_ogcdDoms> list = userService.initSelOgnm(user.getProd());//获取机构列表
			if(list.size()>0&&list!=null){
				String json = JSON.toJSONString(list);
				LOGGER.info("【初始化机构下拉框】："+"登录用户："+curUser.getUsnm()+"产品"+curUser.getProd()+"【成功】");
				return ResultDomain.getRtnMsg(ErrorCode.E_0020.getCode(), json);
			}else{
				LOGGER.error("【初始化机构下拉框】："+"登录用户："+curUser.getUsnm()+"产品"+curUser.getProd()+"【失败】");
				return ResultDomain.getRtnMsg(ErrorCode.E_9994.getCode(), null);
			}
		}else{
			LOGGER.error("【初始化机构下拉框】："+"登录用户："+curUser.getUsnm()+"产品"+curUser.getProd()+"【失败】");
			return ResultDomain.getRtnMsg(ErrorCode.E_9994.getCode(), null);
		}
	}
	/**
	 * 添加用户
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value="/addUser.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addUser(@RequestBody UserVo userVo){
		User user = userVo.getUser();
		String userKey = userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		user.setProd(curUser.getProd());
		String pas = des.encrypt(user.getPswd());
		user.setPswd(pas);
		LOGGER.info("【添加新用户】："+"登录用户："+curUser.getUsnm()+"产品"+curUser.getProd()+"【添加用户】："+"用户名："+user.getUsnm()+"真实姓名:"+user.getCmpt()+"邮箱:"+user.getMail()+"固定电话:"+user.getTelp()+"移动电话:"+user.getMbtl()+"角色:"+user.getUspt()+"所属机构:"+user.getCstn()+"状态:"+user.getUsfg());
		//这些都是固定的，前台或后台固定
		user.setUsid("1011");
		user.setUsfg("0");
		user.setLtime("99999999");//最后一次登录时间,固定
		user.setUtime("99999999");//更新时间，固定
		//usnm,cmpt,pswd,ufax,macip,mail,telp,mbtl,rmrk
		//用户名,真实姓名,密码,传真,ip,mail,telp,mbtl,说明
		//uspt:角色，cstn:机构
		boolean i = userService.insertUsers(userKey, user);
		if(i){
			LOGGER.info("【添加新用户】："+"登录用户："+curUser.getUsnm()+"产品："+curUser.getProd()+"【添加用户】："+user.getUsnm()+"【成功】");
			return ResultDomain.getRtnMsg(ErrorCode.E_0020.getCode(), "success");
		}else{
			LOGGER.error("【添加新用户】："+"登录用户："+curUser.getUsnm()+"产品："+curUser.getProd()+"【添加用户】："+user.getUsnm()+"【失败】");
			return ResultDomain.getRtnMsg(ErrorCode.E_0022.getCode(), null);
		}
	}
	/**
	 * 再添加界面，输入用户名失去焦点
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value="/isUser.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String isUser(@RequestBody UserVo userVo){
		String userKey = userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		User user = userVo.getUser();
		user.setProd(curUser.getProd());
		LOGGER.info("【用户是否注册】："+"登录用户："+curUser.getUsnm()+"产品："+curUser.getProd()+"【添加用户】："+"用户名"+user.getUsnm());
		int f = userService.isUsers(user.getUsnm(),user.getProd());
		if(f>0){
			LOGGER.error("【用户是否注册】："+"登录用户："+curUser.getUsnm()+"产品："+curUser.getProd()+"【添加用户】："+"用户名"+user.getUsnm()+"【该用户已注册】");
			return ResultDomain.getRtnMsg(ErrorCode.E_0025.getCode(), null);
		}else{
			LOGGER.info("【用户是否注册】："+"登录用户："+curUser.getUsnm()+"产品："+curUser.getProd()+"【添加用户】："+"用户名"+user.getUsnm()+"【该用户未注册】");
			return ResultDomain.getRtnMsg(ErrorCode.E_0020.getCode(), "success");
		}
	}
	/**
	 * 删除用户
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value="/deleteUser.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deleteUser(@RequestBody UserVo userVo){
		String userKey = userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		User user =userVo.getUser();
		user.setProd(curUser.getProd()); 
		LOGGER.info("【删除用户】："+"登录用户："+curUser.getUsnm()+"产品："+curUser.getProd()+"【删除用户】："+"用户名"+user.getUsnm());
		if(user.getUsnm().equals("Administrator")||user.getUsnm().equals(curUser.getUsnm())){
			LOGGER.error("【删除用户】："+"登录用户："+curUser.getUsnm()+"产品："+curUser.getProd()+"【不能删除用户】："+"用户名"+user.getUsnm());
			return ResultDomain.getRtnMsg(ErrorCode.E_9990.getCode(), null);
		}else{
			boolean f = userService.deleteUsers(userKey, user);
			if(f){
				LOGGER.error("【删除用户】："+"登录用户："+curUser.getUsnm()+"产品："+curUser.getProd()+"【删除用户】："+"用户名"+user.getUsnm()+"【成功】");
				return ResultDomain.getRtnMsg(ErrorCode.E_0020.getCode(), "success");
			}else{
				LOGGER.error("【删除用户】："+"登录用户："+curUser.getUsnm()+"产品："+curUser.getProd()+"【删除用户】："+"用户名"+user.getUsnm()+"【失败】");
				return ResultDomain.getRtnMsg(ErrorCode.E_0024.getCode(), null);
			}
		}
	}
	/**
	 * 修改用户信息
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value="/editUser.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String editUser(@RequestBody UserVo userVo){
		String userKey = userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		User user = userVo.getUser();
		user.setProd(curUser.getProd());
		LOGGER.error("【修改用户】："+"登录用户："+curUser.getUsnm()+"产品："+curUser.getProd()+"【修改】："+"用户名"+user.getUsnm());
		//用户id,usnm,密码不会变，登录的两个时间不会变，当前产品不会变
		//cmpt,mbtl,telp,ufax,mail,macip,rmrk,uspt,cstn
		if(user.getUsnm().equals("Administrator")||user.getUsnm().equals(curUser.getUsnm())){
			return ResultDomain.getRtnMsg(ErrorCode.E_9995.getCode(), null);
		}else{
			boolean f = userService.updateUsers(userKey, user, user.getUsnm(), user.getProd());
			if(f){
				LOGGER.error("【修改用户】："+"登录用户："+curUser.getUsnm()+"产品："+curUser.getProd()+"【修改用户】："+"用户名"+user.getUsnm()+"【修改成功】");
				return ResultDomain.getRtnMsg(ErrorCode.E_0020.getCode(), "success");
			}else{
				LOGGER.error("【修改用户】："+"登录用户："+curUser.getUsnm()+"产品："+curUser.getProd()+"【修改用户】："+"用户名"+user.getUsnm()+"【修改成功】");
				return ResultDomain.getRtnMsg(ErrorCode.E_0023.getCode(), null);
			}
		}
	}
	/**
	 * 重置密码的操作
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value="/comitPswd.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String comitPswd(@RequestBody UserVo userVo){
		String userKey = userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		User user = userVo.getUser();//用户
		user.setProd(curUser.getProd());
		User user1 = new User();//登陆的用户
		user1.setUsnm(curUser.getUsnm());
		user1.setProd(user.getProd());
		String uspt=loginServicer.getUser(user1).getUspt();
		if(curUser.getUsnm().equals("Administrator")
				||uspt.equals("2007")
				||uspt.equals("1001")||uspt.equals("3001")
				||uspt.equals("4001")||uspt.equals("5001")
				||uspt.equals("6001")||uspt.equals("8001")){
			String newPswd = des.encrypt(userService.selInPswd(user.getProd()));//获取初始密码
			boolean f = userService.mitPswds(userKey, newPswd, user.getUsnm(), user.getProd());//修改密码
			if(f){
				return ResultDomain.getRtnMsg(ErrorCode.E_0020.getCode(), "success");
			}else{
				return ResultDomain.getRtnMsg(ErrorCode.E_0026.getCode(), null);
			}
		}else{
			return ResultDomain.getRtnMsg(ErrorCode.E_9992.getCode(), null);
		}
		
	}
	/**
	 * 用户复核
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value="/upUserFg.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upUserFg(@RequestBody UserVo userVo){
		String userKey = userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		User user = userVo.getUser();
		user.setProd(curUser.getProd());
		if(user.getUsnm().equals("Administrator")){
			return ResultDomain.getRtnMsg(ErrorCode.E_9991.getCode(), null);
		}else{
			//usfg为0,或1;
			boolean f = userService.userFsg(userKey, user.getUsfg(), user.getUsnm(), user.getProd());
			if(f){
				return ResultDomain.getRtnMsg(ErrorCode.E_0020.getCode(), "success");
			}else{
				return ResultDomain.getRtnMsg(ErrorCode.E_0027.getCode(), null);
			}
		}
		
	}
	/**
	 * 修改密码
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value="/editPassword.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String editPassword(@RequestBody UserVo userVo){
		String userKey = userVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String newpswd=userVo.getNewpswd();
		User user = userVo.getUser();//只有一个原密码
		
		String oldpswd = des.encrypt(user.getPswd());
		String newPswd = des.encrypt(newpswd);
		user.setUsnm(curUser.getUsnm());
		user.setPswd(oldpswd);
		user.setProd(curUser.getProd());
		String utime = DataTimeClass.getNowDay();//获取当前的时间
		User user1 = loginServicer.onLoger(user);
		if(user1!=null){
			boolean f = userService.updatePswd(userKey, newPswd,utime, user.getUsnm(), user.getProd());
			if(f){
				return ResultDomain.getRtnMsg(ErrorCode.E_0020.getCode(), "success");
			}else{
				return ResultDomain.getRtnMsg(ErrorCode.E_0029.getCode(), null);
			}
		}else{
			return ResultDomain.getRtnMsg(ErrorCode.E_0028.getCode(), null);
		}
	}
	/**
	 * 异常登陆账户
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/getErrorUser.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getErrorUser(@RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		User user = new User();
		user.setUsnm(curUser.getUsnm());
		user.setProd(curUser.getProd());
		String ognm = curUser.getCstn();
		List<UserDoms> list = userService.listError(user.getProd(), ognm);
		if(list.size()>0&&list!=null){
			String json = JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrorCode.E_0020.getCode(), json);
		}else{
			return ResultDomain.getRtnMsg(ErrorCode.E_0021.getCode(), null);
		}
		
	}
	/**
	 * 清空异常用户
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/deleteErrors.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deleteErrors(@RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prod = curUser.getProd();
		boolean f = userService.deleteError(userKey, prod);
		if(f){
			return ResultDomain.getRtnMsg(ErrorCode.E_0020.getCode(), "success");
		}
		else{
			return ResultDomain.getRtnMsg(ErrorCode.E_0030.getCode(), null);
		}
	}
	/**
	 * 用户信息导出Excel
	 * @param userVo
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@RequestMapping(value = "/outputexcel.do")
    public void showImgCode(UserVo userVo, HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		LOGGER.info("导出用户信息的Excel");
		String fileName = userVo.getTableName();//前台传的  表名
		String userKey = userVo.getUserKey();
        List<Table> tableList = userVo.getTableList();//前台传的  表头，及Key
		List<UserDoms> list = userService.getAllUserList(userKey);
        PoiExcelExport<UserDoms> pee = new PoiExcelExport<UserDoms>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
}
	
