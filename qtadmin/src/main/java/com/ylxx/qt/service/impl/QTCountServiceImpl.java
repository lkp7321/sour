package com.ylxx.qt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylxx.qt.core.mapper.QTCountMapper;
import com.ylxx.qt.service.IQTCountService;
import com.ylxx.qt.service.po.MenuBean;
import com.ylxx.qt.service.po.PositionDetailBean;
import com.ylxx.qt.service.po.ProductProfitBean;
import com.ylxx.qt.service.po.RoleBean;
import com.ylxx.qt.service.po.TradeFieldBean;
import com.ylxx.qt.service.po.TradingAccountFiledBean;
import com.ylxx.qt.service.po.UserInfoBean;

@Service("qtCountService")
public class QTCountServiceImpl implements IQTCountService {
	
	@Resource
	private QTCountMapper qtcm;
	
	// 获取盈亏信息
	@Override
	public List<TradeFieldBean> queryAll(String investorid) {
		List<TradeFieldBean> list = null;
		try {
			list = qtcm.queryAll(investorid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取交易周期
	@Override
	public int getDays(String investorid) {
		int days = 0;
		try {
			days = qtcm.getDays(investorid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return days;
	}
	
	// 获取资金账户表信息
	@Override
	public List<TradingAccountFiledBean> getAll(List<String> item) {
		List<TradingAccountFiledBean> list = null;
		try {
			list = qtcm.getAll(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取本金和每日权益
	@Override
	public List<TradingAccountFiledBean> getPrebalance(String accountid) {
		List<TradingAccountFiledBean> list = null;
		try {
			list = qtcm.getPrebalance(accountid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取每月盈亏信息
	@Override
	public List<TradeFieldBean> getCloseProfit(String investorid) {
		List<TradeFieldBean> list = null;
		try {
			list = qtcm.getCloseProfit(investorid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取每天持仓信息
	@Override
	public List<PositionDetailBean> getPosition(List<String> item) {
		List<PositionDetailBean> list = null;
		try {
			list = qtcm.getPosition(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取品种盈亏信息
	@Override
	public List<TradeFieldBean> getFTProfit(List<String> item) {
		List<TradeFieldBean> list = null;
		try {
			list = qtcm.getFTProfit(item);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//获取品种持仓盈亏
	public List<ProductProfitBean> getPositionProfit(List<String> item) {
		List<ProductProfitBean> list = null;
		try {
			list = qtcm.getPositionProfit(item);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 登录
	@Override
	public List<UserInfoBean> login(String username, String password) {
		
		List<UserInfoBean> list = null;
		try {
			list = qtcm.login();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取所有用户
	@Override
	public List<UserInfoBean> getUserList() {
		List<UserInfoBean> list = null;
		try {
			list = qtcm.getUserList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 根据用户名获取用户
	@Override
	public List<UserInfoBean> SelectUser(String username) {
		List<UserInfoBean> list = null;
		try {
			list = qtcm.SelectUser(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 获取菜单
	@Override
	public List<MenuBean> getMenu() {
		List<MenuBean> list = null;
		try {
			list = qtcm.getMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 添加用户
	@Override
	public void addUser(UserInfoBean user) {
		try {
			qtcm.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 删除用户
	@Override
	public void deleteUser(List<String> userid) {
		try {
			qtcm.deleteUser(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 修改用户
	@Override
	public void updateUser(UserInfoBean user) {
		try {
			qtcm.updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 获取用户角色
	@Override
	public List<RoleBean> getRole(String userid) {
		List<RoleBean> list = null;
		try {
			list = qtcm.getRole(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 添加角色
	@Override
	public void addRoles(List<RoleBean> role) {
		try {
			qtcm.addRoles(role);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 删除角色
	@Override
	public void deleteRoles(String userid) {
		try {
			qtcm.deleteRoles(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 获取用户账号
	@Override
	public List<UserInfoBean> getAccount(String userid) {
		List<UserInfoBean> list = null;
		try {
			list = qtcm.getAccount(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 添加账号
	@Override
	public void addAccounts(List<UserInfoBean> user) {
		 try {
			qtcm.addAccounts(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 删除账号
	@Override
	public void deleteAccounts(String userid) {
		try {
			qtcm.deleteAccounts(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 获取角色权限
	@Override
	public List<MenuBean> getPowers(String roleid) {
		List<MenuBean> list = null;
		try {
			list = qtcm.getPowers(roleid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 添加角色
	@Override
	public void addPowers(List<MenuBean> menu) {
		try {
			qtcm.addPowers(menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 删除角色
	@Override
	public void deletePowers(String roleid) {
		try {
			qtcm.deletePowers(roleid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PositionDetailBean> getPositionMessage(String day,String ctpid, String beginTime, String endTime, int page,
			int limit) {
		List<PositionDetailBean> list = null;
		try {
			int index=(page-1)*limit;
			list = qtcm.getPositionMessage(day,ctpid, beginTime, endTime, index, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Integer getPositionMessageCounts(String day,String ctpid, String beginTime, String endTime) {	
		return qtcm.getPositionMessageCounts(day,ctpid, beginTime, endTime);
	}
}
