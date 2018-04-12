package com.ylxx.qt.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylxx.qt.service.IOverMonitorService;
import com.ylxx.qt.service.IQTCountService;
import com.ylxx.qt.service.ISegmentationSetService;
import com.ylxx.qt.service.YQUserService;
import com.ylxx.qt.service.po.MenuBean;
import com.ylxx.qt.service.po.ParameterdetailsBean;
import com.ylxx.qt.service.po.RoleBean;
import com.ylxx.qt.service.po.UserInfoBean;
import com.ylxx.qt.utils.AESUtil;

/**
 * 用户管理
 * 
 * @author mengpeitong
 * 
 */
@Controller
@RequestMapping("/user")
public class UserController {
	// 微信登录使用APPID
	private final static String AppID = "wx5519e2efae5a3f1a";
	// 微信登录使用AppSecret
	private final static String AppSecret = "7878a22c4cefedcf0cc4c57af1d4fb52";
	@Resource
	private IQTCountService qtcs;

	@Resource
	private YQUserService user;
	@Resource
	private IOverMonitorService ios;
	@Resource
	private ISegmentationSetService isss;

	// 获取用户
	@RequestMapping(value = "/getuser.do")
	public String getUserInfo(Model model) {
		List<UserInfoBean> list = qtcs.getUserList();
		model.addAttribute("list", list);
		return "main";
	}

	// 获取用户账号
	@RequestMapping(value = "/getuseraccount.do")
	public @ResponseBody String getUserAccount(HttpServletRequest request, HttpServletResponse response, Model model)
			throws Exception {
		// 这个集合获取到用户的所有信息
		List<UserInfoBean> list = new ArrayList<UserInfoBean>();
		// 这个集合目的是接受当前用户的账户
		List<String> accountlist = new ArrayList<String>();
		// 构造器，通过它将字符串转换为json 字符串
		ObjectMapper mapper = new ObjectMapper();
		// 获取到当前用户的uid
		String uid = (String) request.getSession().getAttribute("userid");
		list = user.getAccount(uid);
		for (int i = 0; i < list.size(); i++) {
			accountlist.add(list.get(i).getInvestor());
		}
		// 将集合转换为json串
		String json = mapper.writeValueAsString(accountlist);

		return json;
	}

	// 从首页跳转到账户统计页面
	@RequestMapping(value = "/toStatistics.do")
	public String toStatistics(HttpServletRequest request) {
		// 获取用户id
		String uid = (String) request.getSession().getAttribute("userid");
		String role = (String) request.getSession().getAttribute("role");
		// 获取账户统计左边列表栏
		// List<MenuBean> menu = qtcs.getMenu();
		//
		List<MenuBean> menu = user.findMenu(role, "A");
		// 对获取到的目录结构进行排序
		Collections.sort(menu, new Comparator<MenuBean>() {
			@Override
			public int compare(MenuBean o1, MenuBean o2) {
				int s = Integer.parseInt(o1.getMenuid().substring(1)) - (Integer.parseInt(o2.getMenuid().substring(1)));
				return s;
			}
		});
		request.getSession().setAttribute("menu", menu);
		// 如果从session中能获取到用户ID ,
		if (uid == null || uid == "") {
			return "newLogin";
		}
		return "statisticalanaly/statisticsmain";
	}

	// 从首页跳转到注册页面
	@RequestMapping(value = "/toregist.do")
	public String toRegist(HttpServletRequest request) {
		return "user/register";
	}

	// 从首页跳转到登录页面
	@RequestMapping(value = "/tologin.do")
	public String toLogIn(HttpServletRequest request) {
		return "newLogin";
	}

	// 从登录页面跳转到忘记密码页面
	@RequestMapping(value = "/toForgetPwd.do")
	public String toForgetPwd(HttpServletRequest request) {
		return "user/forgetPwd";
	}

	// 根据用户获取用户名、用户ID、手机号
	@RequestMapping(value = "/getUserName.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String findUserByName(HttpServletRequest request, HttpServletResponse response, Model model,
			String username) throws Exception {
		UserInfoBean userInfoBean = user.findUserByName(username);
		String json = "";
		if (null != userInfoBean) {
			json = "{\"code\":1,\"userid\":\"" + userInfoBean.getUserid() + "\",\"username\":\""
					+ userInfoBean.getUsername() + "\",\"investor\":\"" + userInfoBean.getInvestor()
					+ "\",\"roleid\":\"" + userInfoBean.getRoleid() + "\",\"phonenumber\":\""
					+ userInfoBean.getPhonenumber() + "\",\"email\":\"" + userInfoBean.getEamil() + "\",\"truename\":\""
					+ userInfoBean.getTruename() + "\",\"sex\":\"" + userInfoBean.getSex() + "\",\"residence\":\""
					+ userInfoBean.getResidence() + "\",\"qq\":\"" + userInfoBean.getQq() + "\",\"education\":\""
					+ userInfoBean.getEducation() + "\",\"position\":\"" + userInfoBean.getPosition()
					+ "\",\"profession\":\"" + userInfoBean.getProfession() + "\",\"certificatetype\":\""
					+ userInfoBean.getCertificatetype() + "\",\"certificateno\":\"" + userInfoBean.getCertificateno()
					+ "\",\"signature\":\"" + userInfoBean.getSignature() + "\",\"timezone\":\""
					+ userInfoBean.getTimezone() + "\",\"issecret\":\"" + userInfoBean.getIssecret() + "\"}";
		} else {
			json = "{\"code\":0}";
		}
		return json;
	}

	// 根据手机号获取用户名、用户ID、手机号
	@RequestMapping(value = "/getUserPhone.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String findUserByPhone(HttpServletRequest request, HttpServletResponse response, Model model,
			String phonenumber) throws Exception {
		UserInfoBean userInfoBean = user.findUserByPhone(phonenumber);
		String json = "";
		if (null != userInfoBean) {
			json = "{\"code\":1,\"userId\":\"" + userInfoBean.getUserid() + "\",\"username\":\""
					+ userInfoBean.getUsername() + "\",\"phonenumber\":\"" + userInfoBean.getPhonenumber() + "\"}";
		} else {
			json = "{\"code\":0}";
		}
		return json;
	}

	// 根据邮箱获取用户名、用户ID、手机号
	@RequestMapping(value = "/getUserEmail.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String findUserByEmail(HttpServletRequest request, HttpServletResponse response, Model model,
			String email) throws Exception {
		UserInfoBean userInfoBean = user.findUserByEmail(email);
		String json = "";
		if (null != userInfoBean) {
			json = "{\"code\":1,\"userId\":\"" + userInfoBean.getUserid() + "\",\"username\":\""
					+ userInfoBean.getUsername() + "\",\"phonenumber\":\"" + userInfoBean.getPhonenumber() + "\"}";
		} else {
			json = "{\"code\":0}";
		}
		return json;
	}

	/**
	 * 跳转到概况页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/tosurvy.do")
	public String toSurvy() {
		return "statisticalanaly/survy";
	}

	// 从忘记密码第一页面填写账户名通过后跳转到验证身份页面
	@RequestMapping(value = "/toForgetPwd2.do")
	public String toForgetPwd2(HttpServletRequest request) {
		return "user/forgetPwd2";
	}

	// 从忘记密码第二页面验证身份通过后跳转到修改密码页面
	@RequestMapping(value = "/toForgetPwd3.do")
	public String toForgetPwd3(HttpServletRequest request) {
		return "user/forgetPwd3";
	}

	// 从忘记密码第三页面修改密码通过后跳转到注册成功页面
	@RequestMapping(value = "/toForgetPwd4.do")
	public String toForgetPwd4(HttpServletRequest request) {
		return "user/forgetPwd4";
	}

	// 从注册页面跳转到注册成功页面
	@RequestMapping(value = "/toRegisterSuccess.do")
	public String toRegisterSuccess(HttpServletRequest request) {
		return "user/registerSuccess";
	}

	// 从注册成功页面跳转到个人管理页面
	@RequestMapping(value = "/toUserManage.do")
	public String toUserManage(HttpServletRequest request) {
		return "usermanage/userManage";
	}

	// 从用户管理菜单跳转到个人管理页面
	@RequestMapping(value = "/toUserData.do")
	public String toUserData(HttpServletRequest request) {
		return "usermanage/userData";
	}

	// 从用户管理菜单跳转到账户管理页面
	@RequestMapping(value = "/toUserAccount.do")
	public String toUserAccount(HttpServletRequest request) {
		return "useraccount/accountManage";
	}

	// 增加用户
	@RequestMapping(value = "/addUser.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String insertUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 封装操作结果，包括success是否成功和msg消息提示
		JSONObject result = new JSONObject();

		UserInfoBean newUser = new UserInfoBean();
		String uid = AESUtil.getGUID();
		newUser.setUserid(uid);
		// 判断用户名是否存在
		String username = request.getParameter("username");
		UserInfoBean userByName = user.findUserByName(username);
		if (null != userByName) {
			result.put("success", false);
			result.put("msg", "用户名已存在");
		} else {
			newUser.setUsername(username);
			newUser.setPhonenumber(request.getParameter("phonenumber"));
			String pwd = AESUtil.encrypt(request.getParameter("password"), uid);
			newUser.setPassword(pwd);
			newUser.setEmail(request.getParameter("email"));
			newUser.setRoleid("2");// 默认用户权限

			// 用户注册成功后添加默认角色
			RoleBean roleBean = new RoleBean();
			roleBean.setRoleid("2");// 默认用户权限
			roleBean.setUserid(uid);
			roleBean.setUsername(request.getParameter("username"));

			try {
				int resultInfo = user.insertUser(newUser);
				if (resultInfo == 1) {
					int rolesResult = user.addDefaultRoles(roleBean);// 用户注册成功后添加默认角色
					if (rolesResult == 1) {
						result.put("success", true);
						result.put("msg", "注册成功！");
					}
				} else {
					result.put("success", false);
					result.put("msg", "注册失败！");
				}
			} catch (Exception e) {
				result.put("success", false);
				result.put("msg", "注册失败！");
			}
		}
		return result.toString();
	}

	// 修改用户
	@RequestMapping(value = "/updateUser.do", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public @ResponseBody String updateUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		UserInfoBean newUser = new UserInfoBean();
		String suid = request.getParameter("userid");
		newUser.setUserid(suid);
		newUser.setUsername(request.getParameter("username"));
		String upwd = request.getParameter("password");
		if (null != upwd) {
			String pwd = AESUtil.encrypt(upwd, suid);
			newUser.setPassword(pwd);
		}

		newUser.setPhonenumber(request.getParameter("phonenumber"));
		newUser.setEmail(request.getParameter("email"));
		newUser.setTruename(request.getParameter("truename"));
		newUser.setSex(request.getParameter("sex"));
		newUser.setResidence(request.getParameter("residence"));
		newUser.setQq(request.getParameter("qq"));
		newUser.setEducation(request.getParameter("education"));
		newUser.setPosition(request.getParameter("position"));
		newUser.setProfession(request.getParameter("profession"));
		newUser.setCertificatetype(request.getParameter("certificatetype"));
		newUser.setCertificateno(request.getParameter("certificateno"));
		newUser.setSignature(request.getParameter("signature"));
		newUser.setTimezone(request.getParameter("timezone"));
		newUser.setIssecret(request.getParameter("issecret"));

		// 封装操作结果，包括success是否成功和msg消息提示
		JSONObject result = new JSONObject();
		try {
			int resultInfo = user.updateUser(newUser);
			if (resultInfo == 1) {
				result.put("success", true);
				result.put("msg", "修改成功！");
			} else {
				result.put("success", false);
				result.put("msg", "修改失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "修改失败！");
		}
		return result.toString();
	}

	/**
	 * 用户登录
	 * 
	 * @param uname
	 * @param upwd
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login.do", produces = "plain/text; charset=UTF-8")
	public @ResponseBody String Login(String uname, String upwd, HttpServletRequest request, Model model)
			throws Exception {
		// 邮箱的正则表达
		String em = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		// 手机号的正则表达
		String phone = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		UserInfoBean userInfo = null;
		UserInfoBean userHaving = null;
		// 封装操作结果，包括success是否成功和msg消息提示
		JSONObject result = new JSONObject(); // 新增
		// 手机号登录
		if (Pattern.matches(phone, uname)) {
			userInfo = user.loginByPhone(uname, upwd);
			userHaving = user.findUserByPhone(uname);
			// 邮箱登录
		} else if (Pattern.matches(em, uname)) {
			userInfo = user.loginByEmail(uname, upwd);
			userHaving = user.findUserByEmail(uname);// 查询用户键入的用户名，判断该用户是否已经注册
			// 用户名登录
		} else {
			userInfo = user.login(uname, upwd);
			userHaving = user.findUserByName(uname);// 查询用户键入的用户名，判断该用户是否已经注册
		}

		if (userHaving == null) {
			result.put("success", false);
			result.put("msg", "该账号尚未注册");
			return result.toJSONString();
		} else {
			if (userInfo != null) {
				request.getSession().setAttribute("userid", userInfo.getUserid());
				request.getSession().setAttribute("name", userInfo.getUsername());
				request.getSession().setAttribute("role", userInfo.getRoleid());
				List<ParameterdetailsBean> list = ios.queryParamet(userInfo.getUsername());
				Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
				if (list.size() == 0) {// 如果该用户没有参数设置，默认显示管理员的
					list = ios.queryParamet("admin");
				}
				for (int i = 0; i < list.size(); i++) {
					String[] constr = list.get(i).getContractset().split(",");
					for (int j = 0; j < constr.length; j++) {
						map.put(constr[j], isss.CreateSegmentSet(list.get(i)));
					}
				}
				request.getSession().setAttribute("subinfo", map);
				result.put("success", true);
				result.put("msg", "登陆成功");
				return result.toJSONString();
			} else {
				result.put("success", false);
				result.put("msg", "您输入的账号或密码有误，请重新输入");
				return result.toJSONString();
			}
		}

	}

	/**
	 * 登出
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout.do", produces = "plain/text; charset=UTF-8")
	public @ResponseBody String logout(HttpSession session) {
		session.invalidate();
		return "您已成功退出";
	}

	@RequestMapping(value = "getRole.do", produces = "plain/text; charset=UTF-8")
	public @ResponseBody String sytemManager(HttpServletRequest request, HttpServletResponse repose, Model model)
			throws Exception {
		List<MenuBean> menuList = user.showAllMenu();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(menuList);
		return "{\"code\":0,\"msg\":\"\",\"count\":" + menuList.size() + ",\"data\":" + json + "}";
	}

	@RequestMapping(value = "/alluser.do", produces = "plain/text; charset=UTF-8")
	public @ResponseBody String userManager(HttpServletRequest request, HttpServletResponse response, int page,
			int limit) throws Exception {
		List<UserInfoBean> userList = user.findAllUser();
		List<UserInfoBean> list = user.selectAllUser(page, limit);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		return "{\"code\":0,\"msg\":\"\",\"count\":" + userList.size() + ",\"data\":" + json + "}";
	}

	@RequestMapping(value = "/getallroles.do", produces = "plain/text; charset=UTF-8")
	public @ResponseBody String getAllRoles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<RoleBean> rolelist = user.getAllRoles();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(rolelist);
		return json;
	}

	@RequestMapping(value = "/getroleMenu.do", produces = "plain/text; charset=UTF-8")
	public @ResponseBody String getAccountMenuByRole(HttpServletRequest request, String roleid) {
		roleid = request.getParameter("roleid");
		List<MenuBean> menuList = user.showMenuByRole(roleid);
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(menuList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "{\"code\":0,\"msg\":\"\",\"count\":" + menuList.size() + ",\"data\":" + json + "}";
	}

	@RequestMapping(value = "/updatemenu.do", produces = "plain/text; charset=UTF-8")
	public @ResponseBody String updateRoleMenu(HttpServletRequest request, HttpServletResponse repose) {
		JSONObject json = new JSONObject();
		try {
			String roleId = request.getParameter("roleid");
			String menuId = request.getParameter("menuid");
			String isShow = request.getParameter("isshow");
			int s = user.updateMenu(roleId, menuId, isShow);
			if (s != 0) {
				json.put("code", "1");
				json.put("msg", "数据更新成功");
			}
		} catch (Exception e) {
			json.put("code", "0");
			json.put("msg", "数据更新失败");
		}
		return json.toString();
	}

	@RequestMapping(value = "/updateUserRole.do", produces = "plain/text; charset=UTF-8")
	public @ResponseBody String updateUserRole(HttpServletRequest request, HttpServletResponse response) {
		String userId = null;
		String username = null;
		String roleID = null;
		String roleList = request.getParameter("roleList");
		List<UserInfoBean> userInfoList = new ArrayList<UserInfoBean>();
		JSONObject json = new JSONObject();
		JSONArray jsonArray = JSONObject.parseArray(roleList);
		for (int i = 0; i < jsonArray.size(); i++) {
			UserInfoBean userinfo = new UserInfoBean();
			userId = (String) jsonArray.getJSONObject(i).get("userid");
			username = (String) jsonArray.getJSONObject(i).get("username");
			roleID = (String) jsonArray.getJSONObject(i).get("roleid");
			userinfo.setUserid(userId);
			userinfo.setUsername(username);
			userinfo.setRoleid(roleID);
			userInfoList.add(userinfo);
		}
		int result = 0;
		if (userInfoList.size() != 0) {
			result = user.updateUserRole(userInfoList);
			if (result >= userInfoList.size()) {
				json.put("code", result);
				json.put("msg", "修改成功");
			} else {
				json.put("code", "0");
				json.put("msg", "修改失败");
			}
		} else {
			json.put("code", "0");
			json.put("msg", "修改失败");
		}
		return json.toString();
	}

	@RequestMapping(value = "/stasticMenu.do", produces = "plain/text; charset=UTF-8")
	public @ResponseBody String getStaticsMenu(HttpServletRequest request, HttpServletResponse response) {
		String role = (String) request.getSession().getAttribute("role");
		List<MenuBean> menu = user.findMenu(role, "A");
		// 对获取到的目录结构进行排序
		Collections.sort(menu, new Comparator<MenuBean>() {
			@Override
			public int compare(MenuBean o1, MenuBean o2) {
				int s = Integer.parseInt(o1.getMenuid().substring(1)) - (Integer.parseInt(o2.getMenuid().substring(1)));
				return s;
			}
		});
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(menu);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	@RequestMapping(value = "/getcode.do", produces = "plain/text; charset=UTF-8")
	public String getCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 微信登录 获取code值，根据code值获取 access_token和openid;
		// 然后根据access_token和openID获取用户信息
		String code = request.getParameter("code");
		if (code == null || code.equals("")) {
			return "index";
		}
		Map<String, String> hashmap = user.getAccess_token(AppID, AppSecret, code);
		if (hashmap == null) {
			return "index";
		}
		if (hashmap.isEmpty()) {
			return "index";
		}

		UserInfoBean userInfo = user.getUserInfo(hashmap.get("access_token"), hashmap.get("openid"));
		/*
		 * 判断该微信是否登录过此应用；如果有，将信息返回，保存在session中； 如果没有，将用户信息保存在数据库中。
		 */
		UserInfoBean userBean = user.IsHavingWeiXin(userInfo.getWeixinID());
		if (userBean != null) {
			request.getSession().setAttribute("userid", userBean.getUserid());
			request.getSession().setAttribute("name", userBean.getUsername());
			request.getSession().setAttribute("role", userBean.getRoleid());
			List<ParameterdetailsBean> list = ios.queryParamet(userBean.getUsername());
			Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
			if (list.size() == 0) {// 如果该用户没有参数设置，默认显示管理员的
				list = ios.queryParamet("admin");
			}
			for (int i = 0; i < list.size(); i++) {
				String[] constr = list.get(i).getContractset().split(",");
				for (int j = 0; j < constr.length; j++) {
					map.put(constr[j], isss.CreateSegmentSet(list.get(i)));
				}
			}
			request.getSession().setAttribute("subinfo", map);
		} else {
			// 生成userid;
			String uid = AESUtil.getGUID();
			RoleBean roleBean = new RoleBean();
			userInfo.setUserid(uid);
			int resultInfo = user.insertUser(userInfo);
			// 用户注册成功后添加默认角色
			if (resultInfo == 1) {
				roleBean.setUserid(uid);
				roleBean.setRoleid("2");
				roleBean.setUsername(userInfo.getUsername());
				int rolesResult = user.addDefaultRoles(roleBean);
			}

			request.getSession().setAttribute("userid", uid);
			request.getSession().setAttribute("name", userInfo.getUsername());
			request.getSession().setAttribute("role", "2");

		}

		return "index";
	}

}
