package com.ylxx.qt.service;

import java.util.List;

import com.ylxx.qt.service.po.MenuBean;
import com.ylxx.qt.service.po.PositionDetailBean;
import com.ylxx.qt.service.po.ProductProfitBean;
import com.ylxx.qt.service.po.RoleBean;
import com.ylxx.qt.service.po.TradeFieldBean;
import com.ylxx.qt.service.po.TradingAccountFiledBean;
import com.ylxx.qt.service.po.UserInfoBean;

public interface IQTCountService {
	/**
	 * 获取盈亏信息
	 * @param investorid 
	 * @return List<TradeFieldBean>
	 */
	public List<TradeFieldBean> queryAll(String investorid);
	/**
	 *  获取交易周期
	 * @param investorid 
	 * @return 
	 */
	public int getDays(String investorid);
	/**
	 *  获取资金账户表信息
	 * @param accountid
	 * @return
	 */
	public List<TradingAccountFiledBean> getAll(List<String> item);
	/**
	 *  获取本金和每日权益
	 * @param accountid
	 * @return
	 */
	public List<TradingAccountFiledBean> getPrebalance(String accountid);
	/**
	 *  获取每月盈亏信息
	 * @param investorid
	 * @return
	 */
	public List<TradeFieldBean> getCloseProfit(String investorid);
	/**
	 * 获取每天持仓信息
	 * @param investorid
	 * @return
	 */
	public List<PositionDetailBean> getPosition(List<String> item);
	/**
	 *  获取品种盈亏信息
	 * @param investorid
	 * @return
	 */
	public List<ProductProfitBean> getPositionProfit(List<String> item);
	/**
	 *  获取产品持仓盈亏
	 * @param investorid
	 * @return
	 */
	public List<TradeFieldBean> getFTProfit(List<String> item);
	/**
	 *  登录
	 * @param username
	 * @param password
	 * @return
	 */
	public List<UserInfoBean> login(String username,String password);
	/**
	 *  获取菜单
	 * @return
	 */
	public List<MenuBean> getMenu();
	/**
	 *  添加用户
	 * @param user
	 */
	public void addUser(UserInfoBean user);
	/**
	 *  删除用户
	 * @param userid
	 */
	public void deleteUser(List<String> userid);
	/**
	 *  修改用户
	 * @param user
	 */
	public void updateUser(UserInfoBean user);
	/**
	 * 获取所有用户
	 * @return
	 */
	public List<UserInfoBean> getUserList();
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public List<UserInfoBean> SelectUser(String username);
	/**
	 *  获取用户角色
	 * @param userid
	 * @return
	 */
	public List<RoleBean> getRole(String userid);
	/**
	 *  获取用户账号
	 * @param userid
	 * @return
	 */
	public List<UserInfoBean> getAccount(String userid);
	/**
	 *  获取角色权限
	 * @param roleid
	 * @return
	 */
	public List<MenuBean> getPowers(String roleid);
	/**
	 *  添加角色
	 * @param role
	 */
	public void addRoles(List<RoleBean> role);
	/**
	 *  删除角色
	 * @param userid
	 */
	public void deleteRoles(String userid);
	/**
	 *  添加账号
	 * @param user
	 */
	public void addAccounts(List<UserInfoBean> user);
	/**
	 *  删除账号
	 * @param userid
	 */
	public void deleteAccounts(String userid);
	/**
	 *  添加权限
	 * @param menu
	 */
	public void addPowers(List<MenuBean> menu);
	/**
	 *  删除权限
	 * @param roleid
	 */
	public void deletePowers(String roleid);
	/**
	 *  获取持仓表所有信息
	 * @param day
	 * @param ctpid
	 * @param beginTime
	 * @param endTime
	 * @param index
	 * @param pagecounts
	 * @return
	 */
	public List<PositionDetailBean> getPositionMessage(String day,String ctpid, String beginTime, String endTime, int index,
			int pagecounts);
	/**
	 *  获取持仓表所有信息数量
	 * @param day
	 * @param ctpid
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Integer getPositionMessageCounts(String day,String ctpid, String beginTime, String endTime);

}
