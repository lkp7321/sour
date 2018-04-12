package com.ylxx.qt.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.qt.service.po.MenuBean;
import com.ylxx.qt.service.po.PositionDetailBean;
import com.ylxx.qt.service.po.ProductProfitBean;
import com.ylxx.qt.service.po.RoleBean;
import com.ylxx.qt.service.po.TradeFieldBean;
import com.ylxx.qt.service.po.TradingAccountFiledBean;
import com.ylxx.qt.service.po.UserInfoBean;

public interface QTCountMapper {
	// 获取盈亏信息
	public List<TradeFieldBean> queryAll(String investorid) throws Exception;

	// 获取交易周期
	public int getDays(String investorid) throws Exception;

	// 获取资金账户表信息
	public List<TradingAccountFiledBean> getAll(List<String> Alist) throws Exception;

	// 获取本金和每日权益
	public List<TradingAccountFiledBean> getPrebalance(String accountid) throws Exception;

	// 获取每月盈亏信息
	public List<TradeFieldBean> getCloseProfit(String investorid) throws Exception;

	// 获取每天持仓信息
	public List<PositionDetailBean> getPosition(List<String> Alist) throws Exception;

	// 获取品种盈亏信息
	public List<TradeFieldBean> getFTProfit(List<String> AList) throws Exception;
	
	// 获取品种持仓盈亏
	public List<ProductProfitBean> getPositionProfit(List<String> AList) throws Exception;

	// 登录
	public List<UserInfoBean> login() throws Exception;

	// 获取菜单
	public List<MenuBean> getMenu() throws Exception;

	// 添加用户
	public void addUser(UserInfoBean user) throws Exception;

	// 删除用户
	public void deleteUser(List<String> userid) throws Exception;

	// 修改用户
	public void updateUser(UserInfoBean user) throws Exception;

	// 获取所有用户
	public List<UserInfoBean> getUserList() throws Exception;

	// 根据用户名获取用户
	public List<UserInfoBean> SelectUser(String username) throws Exception;

	// 获取用户角色
	public List<RoleBean> getRole(String userid) throws Exception;

	// 获取用户账号
	public List<UserInfoBean> getAccount(String userid) throws Exception;

	// 获取角色权限
	public List<MenuBean> getPowers(String roleid) throws Exception;

	// 添加角色
	public void addRoles(List<RoleBean> role) throws Exception;

	// 删除角色
	public void deleteRoles(String userid) throws Exception;

	// 添加账号
	public void addAccounts(List<UserInfoBean> user) throws Exception;

	// 删除账号
	public void deleteAccounts(String userid) throws Exception;

	// 添加权限
	public void addPowers(List<MenuBean> menu) throws Exception;

	// 删除权限
	public void deletePowers(String roleid) throws Exception;

	// 获取持仓表所有信息
	public List<PositionDetailBean> getPositionMessage(@Param("day")String day,@Param("investor_id") String ctpid,
			@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("index") int index,
			@Param("pagecounts") int pagecounts) throws Exception;
	// 获取持仓表所有信息数量
	public Integer getPositionMessageCounts(@Param("day")String day,@Param("investor_id") String ctpid,
			@Param("beginTime") String beginTime, @Param("endTime") String endTime);


}
