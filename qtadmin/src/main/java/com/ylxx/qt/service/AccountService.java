package com.ylxx.qt.service;

import java.util.List;

import com.ylxx.qt.service.po.AccountBean;
import com.ylxx.qt.service.po.UserAccountBean;
public interface AccountService {
	/**
	 * 新增账户信息
	 * @param account
	 * @return 
	 */
	public int addAccount(AccountBean account); 
	
	/**
	 * 更新账户信息
	 * @param account
	 * @return 
	 */
	public int updateAccount(AccountBean account) throws Exception;
	
	/**
	 * 根据用户id查找对应的所有账户信息
	 * @param userid
	 * @return
	 */
	public List<AccountBean> queryAccountByUserID(String userid);
	


	/**
	 * 根据账户ID查找对应的账户记录
	 * @param userid
	 * @return
	 */
	 public List<AccountBean> queryOneAccountByAccountID(String userid);
	
	 
	 /**
	  * 跟据用户ID删除相应记录
	  * @param userid
	 * @throws Exception 
	  */
	 public  void  deleteAccount(String userid) throws Exception;
	 
	 
	 /**
	  * 新增账户用户关系
	  * @param userAccount
	  * @return
	  */
	 public int addUserAccount (UserAccountBean userAccount);
	 
	 /**
	  * 更新账户用户关系
	  * @param userAccount
	  * @return
	  */
	 public int updateUserAccount(UserAccountBean userAccount);
	 
	 /**
	  * 根据用户的UUID查询账户用户关系
	  * @param userid
	  * @return
	  */
	 public List<UserAccountBean> queryUserAccountByUserName(String username);
	 
	 /**
	  * 根据投资者ID删除用户账户关系表中记录
	  * @param investor
	 * @return 
	  */

	 public  void deleteUserAccount(String investor);
	 
	 
	 /**
	  * 根据投资者ID查询用户账户关系表中的记录
	  * @param investor
	  * @return
	  */
	public List<UserAccountBean> queryUserAccountByInvestor(String investor);

	
}
