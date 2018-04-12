package com.ylxx.qt.service;

import java.util.List;
import java.util.Map;

import com.ylxx.qt.service.po.MenuBean;
import com.ylxx.qt.service.po.RoleBean;
import com.ylxx.qt.service.po.UserInfoBean;

/**
 * 
 * @author 蒙佩童
 * 
 */
public interface YQUserService {
	/**
	 * 获取用户账户
	 * 
	 * @return
	 */
	public List<UserInfoBean> getAccount(String userid);

	// 获取用户名
	UserInfoBean findUserByName(String username);

	// 获取手机号
	UserInfoBean findUserByPhone(String phonenumber);

	// 获取邮箱
	UserInfoBean findUserByEmail(String email);

	// 添加用户
	public int insertUser(UserInfoBean userInfo);

	// 修改用户
	public int updateUser(UserInfoBean userInfo);

	// 用户注册成功后添加默认角色
	public int addDefaultRoles(RoleBean roleBean);

	// 添加角色
	public void addRoles(List<RoleBean> role) throws Exception;
	
	/**
	 * 
	 * @param userName 用户名
	 * @param password	密码
	 * @return UserInfoBean 用户实体
	 * @throws Exception 
	 */
	public UserInfoBean login(String userName,String password) throws Exception;
	/**
	 * 根据权限和url查找目录
	 * @param role
	 * @param parent： A 代表是账户统计下的菜单
	 * 		
	 * @return
	 */
	List<MenuBean> findMenu(String role, String parent);
	/**
	 * 展示所有的目录
	 * @return
	 */
	public List<MenuBean> showAllMenu();
	/**
	 * 查询所有的用户
	 * @return
	 */
	public List<UserInfoBean> findAllUser();
	/**
	 * 查询所有的角色信息
	 * @return
	 */
	public List<RoleBean> getAllRoles();
	/**
	 * 根据权限获取目录
	 * @param role
	 * @return
	 */
	public List<MenuBean> showMenuByRole(String role);

	/**
	 * 
	 * @param roleId
	 * @param menuId
	 * @param isShow
	 */
	public int updateMenu(String roleId, String menuId, String isShow);
	/**
	 * 更新用户权限
	 * @param userId
	 * @param username
	 * @param roleID
	 * @return
	 */
	public int updateUserRole(List<UserInfoBean> userList);
	/**
	 * 分页查询
	 * @param page
	 * @param limit
	 */
	public  List<UserInfoBean> selectAllUser(int page, int limit);
	/**
	 * 根据账号查找用户
	 * @param account
	 * @return
	 */
	public List<UserInfoBean> findUserByAccount(String account);
	
	
	/**
	 * 
	 * @param AppID
	 *            申请的微信AppID
	 * @param AppSecret
	 *            申请的微信 AppSecret
	 * @param CODE
	 *            微信公众平台返回的code
	 * @return
	 */
	public Map<String,String> getAccess_token(String AppID, String AppSecret, String CODE);

	/**
	 * 
	 * @param access_token
	 *            微信平台返回的access_token
	 * @param openid
	 *            微信平台返回的openid
	 * @return
	 */
	public UserInfoBean getUserInfo(String access_token, String openid);

	/**
	 * 
	 * @param WeiXinID 微信ID
	 * @return 
	 */
	public UserInfoBean IsHavingWeiXin(String WeiXinID);
	/**
	 * 手机号登录用户
	 * @param uname
	 * @param upwd
	 * @return
	 * @throws Exception 
	 */
	public UserInfoBean loginByPhone(String uname, String upwd) throws Exception;
	/**
	 * 邮箱登录
	 * @param uname
	 * @param upwd
	 * @return
	 */
	public UserInfoBean loginByEmail(String uname, String upwd)throws Exception;

}
