package com.ylxx.qt.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ylxx.qt.service.YQUserService;
import com.ylxx.qt.service.po.MenuBean;
import com.ylxx.qt.service.po.UserInfoBean;
import com.ylxx.qt.utils.AESUtil;

@Controller
@RequestMapping("/client")
public class ClientController {

	@Resource
	private YQUserService userService;

	@RequestMapping(value = "/clientlogin.do")
	public String clietLogIn(HttpServletRequest request, HttpServletResponse repose) {
		String sKey="5BA63226092A1661E0534E0E130ACFF7";
		String accounts = request.getParameter("ac");
		String account="";
		try {
			account = AESUtil.decrypt(accounts, sKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<UserInfoBean> userList = userService.findUserByAccount(account);
		if (userList.size() > 0) {
			request.getSession().setAttribute("name", userList.get(0).getUsername());
			request.getSession().setAttribute("userid", userList.get(0).getUserid());
			request.getSession().setAttribute("role", userList.get(0).getRoleid());
			List<MenuBean> menu = userService.findMenu(userList.get(0).getRoleid(), "A");
			// 对获取到的目录结构进行排序
			Collections.sort(menu, new Comparator<MenuBean>() {
				@Override
				public int compare(MenuBean o1, MenuBean o2) {
					int s = Integer.parseInt(o1.getMenuid().substring(1))
							- (Integer.parseInt(o2.getMenuid().substring(1)));
					return s;
				}
			});
			request.getSession().setAttribute("menu", menu);
			return "statisticalanaly/statisticsmain";
		}
		return "newLogin";
	}

}
