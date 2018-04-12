package com.ylxx.qt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylxx.qt.core.mapper.AccountManagementMapper;
import com.ylxx.qt.service.AccountService;
import com.ylxx.qt.service.po.AccountBean;
import com.ylxx.qt.service.po.UserAccountBean;


@Service("accountService")
public class AccountServiceImpl implements AccountService{
	
	
	@Resource
	private AccountManagementMapper accountMapper;

	@Override
	public int addAccount(AccountBean account) {
		//  增加账户
		int resutl =	accountMapper.addAccount(account);
		return resutl;
		
	}
	


	@Override
	public int updateAccount(AccountBean account) throws Exception{
		//  更新账户信息
		return accountMapper.updateAccount(account);
		
	}

	@Override
	public List<AccountBean> queryAccountByUserID(String userid) {
		return accountMapper.queryAccountByUserID(userid) ;
		//TODO 根据USERID查询用户名下的所有账户
	}

	@Override
	public List<AccountBean> queryOneAccountByAccountID(String userid) {
		return accountMapper.queryOneAccountByAccountID(userid);
		//TODO 根据账户ID查询账户信息
	}

	/**
	 * 根据账户ID删除记录
	 * @param userid
	 * @throws Exception 
	 */
	@Override
	public void deleteAccount(String userid) throws Exception{
		accountMapper.deleteAccount(userid);
		
	}

	
	@Override
	public int addUserAccount(UserAccountBean userAccount) {
		//  增加用户账户关系
		return accountMapper.addUserAccount(userAccount);
		
		
	}
	
	@Override
	public int updateUserAccount(UserAccountBean userAccount) {
		// 更新用户账户关系信息
		return accountMapper.updateUserAccount(userAccount);
	}
	
	@Override
	public List<UserAccountBean> queryUserAccountByUserName(String username) {
		return accountMapper.queryUserAccountByUserName(username);
		// 根据username查询所有用户账户关系
	}
	
	@Override
	public List<UserAccountBean> queryUserAccountByInvestor(String investor){
		return accountMapper.queryUserAccountByInvestor(investor);
	}
	
	@Override
	public void deleteUserAccount (String investor){
		accountMapper.deleteUserAccount(investor);
	}
	
	
}
