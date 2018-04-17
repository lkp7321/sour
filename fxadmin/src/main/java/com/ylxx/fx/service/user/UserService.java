package com.ylxx.fx.service.user;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.RoleBean;
import com.ylxx.fx.service.po.User;
import com.ylxx.fx.core.domain.Trd_ogcdDoms;
import com.ylxx.fx.core.domain.UserDoms;

public interface UserService {
	/**
	 * 分页，查寻用户信息
	 * @param pageNo
	 * @param pageSize
	 * @param userKey
	 * @return
	 */
	PageInfo<UserDoms>getPageUserList(Integer pageNo,Integer pageSize,
			String userKey);
	/**
	 * 查询所有用户信息
	 * @param userKey
	 * @return
	 */
	List<UserDoms> getAllUserList(String userKey);
	/**
	 * 添加用户
	 * @param userKey
	 * @param user
	 * @return
	 */
	boolean insertUsers(String userKey, User user);
	/**
	 * 修改用户
	 * @param userKey
	 * @param user
	 * @param usnm
	 * @param prod
	 * @return
	 */
	boolean updateUsers(String userKey, User user, String usnm, String prod );
	/**
	 * 删除用户
	 * @param userKey
	 * @param user
	 * @return
	 */
	boolean deleteUsers(String userKey, User user);
	/**
	 * 判断此用户是否存在
	 * @param usnm
	 * @param prod
	 * @return
	 */
	int isUsers(String usnm, String prod);
	/**
	 * 初始化角色下拉框
	 * @param prod
	 * @return
	 */
	List<RoleBean> initSelRole(String prod);
	/**
	 * 初始化机构下拉框
	 * @param prod
	 * @return
	 */
	List<Trd_ogcdDoms> initSelOgnm(String prod);
	/*
	 * 获取初始化的密码
	 */
	String selInPswd(String prod);
	/**
	 * 进行重置密码的操作
	 * @param userKey
	 * @param newPswd
	 * @param usnm
	 * @param prod
	 * @return
	 */
	boolean mitPswds(String userKey, String newPswd, String usnm, String prod);
	/**
	 * 用户复核
	 * @param userKey
	 * @param usfg
	 * @param usnm
	 * @param prod
	 * @return
	 */
	boolean userFsg(String userKey, String usfg, String usnm, String prod);
	/**
	 * 修改密码
	 * @param userKey
	 * @param newPswd
	 * @param utime
	 * @param usnm
	 * @param prod
	 * @return
	 */
	boolean updatePswd(String userKey, String newPswd, String utime, String usnm, String prod);
	/**
	 * 登陆异常用户
	 * @param prod
	 * @param ognm
	 * @return
	 */
	List<UserDoms> listError(String prod,String ognm);
	/**
	 * 清空异常登录用户
	 * @param userKey
	 * @param prod
	 * @return
	 */
	boolean deleteError(String userKey, String prod);
}
