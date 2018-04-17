package com.ylxx.fx.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ylxx.fx.core.domain.ProductDoms;
import com.ylxx.fx.service.po.User;

public interface LoginService {
	/**
	 * 查询所有产品
	 * @param usnm
	 * @return
	 */
	List<ProductDoms> findProduct(String usnm);
	/**
	 * 分类查询产品
	 * @param usnm
	 * @return
	 */
	List<ProductDoms> findProduct1(String usnm);
	/**
	 * 获取角色
	 * @param user
	 * @return
	 */
	int findRole(User user);
	/**
	 * 获取用户
	 * @param user
	 * @return
	 */
	User getUser(User user);
	/**
	 * 修改登录错误次数
	 * @param user
	 * @return
	 */
	int upErr(User user);
	/**
	 * 修改登录时间
	 * @param nowtime
	 * @param user
	 * @return
	 */
	int upTime(String nowtime, User user);
	/**
	 * 登录
	 * @param user
	 * @param req
	 * @return
	 */
	String login(User user, HttpServletRequest req);
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	User onLoger(User user);
	/**
	 * 最后修改时间
	 * @param nowtime
	 * @param user
	 * @return
	 */
	int upOveTime(String nowtime,User user);
	/**
	 * 查询旧密码是否正确
	 * @param user
	 * @return
	 */
	String findPswd(String usnm, String prod, String oldPswd, String newPswd, HttpServletRequest req);
}
