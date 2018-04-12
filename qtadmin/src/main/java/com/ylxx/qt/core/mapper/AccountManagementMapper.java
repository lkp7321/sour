package com.ylxx.qt.core.mapper;


import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ylxx.qt.service.po.AccountBean;
import com.ylxx.qt.service.po.UserAccountBean;

/**
 * 
 * @author zouhang
 *
 */
public interface  AccountManagementMapper {
	
	
	/**
	 * 添加账户
	 * @param account
	 * @return 
	 */
	public  int  addAccount(@Param("account")AccountBean account)  ;
	
	
	/**
	 * 根据账户I直接删除账户记录
	 * @param userid
	 */
	public void  deleteAccount(String userid) throws Exception;
	
	
	/**
	 * 更新账户任意一个字段
	 * @param account
	 */
	public int updateAccount(AccountBean  account) throws Exception;
		
	/**
	 * 根据用户id查找对应的账户信息
	 * @param userid
	 * @return
	 */
	public List<AccountBean> queryAccountByUserID(String userid);
	
	/**
	 * 根据账户ID查找对应的一条账户信息
	 * 
	 */
	public List<AccountBean> queryOneAccountByAccountID(String userid);
	
	
	/**
	 * 增加用户账户关系
	 * @param userAccount
	 * @return
	 */
	public int  addUserAccount(UserAccountBean userAccount);
	
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
	  */

	 public  void deleteUserAccount(String investor);

	 /**
	  * 根据投资者ID查询用户账户关系表中的记录
	  * @param investor
	  * @return
	  */
	public List<UserAccountBean> queryUserAccountByInvestor(String investor);
}
