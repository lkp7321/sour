package com.ylxx.qt.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.qt.service.po.MenuBean;
import com.ylxx.qt.service.po.RoleBean;
import com.ylxx.qt.service.po.UserInfoBean;

/**
 * 
 * @author 蒙佩童
 * 
 */
public interface YQUserMapper {

	public List<UserInfoBean> getUserAccount(String UserID) throws Exception;

	//获取用户名
	UserInfoBean findUserByName(String username);

	// 获取手机号
	UserInfoBean findUserByPhone(String phonenumber);

	// 获取邮箱
	UserInfoBean findUserByEmail(String email);

	// 添加用户
	public int insertUser(UserInfoBean userInfo);

	// 修改用户
	public int updateUser(UserInfoBean userInfo);
	
	//用户注册成功后添加默认角色
	public int addDefaultRoles(RoleBean roleBean);
	
	// 添加角色
	public void addRoles(List<RoleBean> role) throws Exception;
	
	/**
	 * 用户登录
	 * @param userName 用户名
	 * @param password 密码
	 * @return	list  用户集合
	 */
	public List<UserInfoBean> login();
	
	/**
	 * 根据用户id,查找用户权限
	 * @param userid
	 * @return
	 */
	public List<RoleBean> findRoleIDByUserId(String UserID);
	
	/**
	 * 根据权限和父标签查找目录
	 * @param role
	 * @param parent
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
	 * 根据角色查询目录
	 * @param role
	 * @return
	 */
	public List<MenuBean> showMenuByRole(String roleid);
	/**
	 * 根据用户权限和目录id更新目录是否显示
	 * @param roleId
	 * @param menuId
	 * @param isShow
	 */
	public 	int  updateMenu(@Param(value="roleId")String roleId,@Param(value="menuId") String menuId,@Param(value="isShow") String isShow);
	/**
	 * 根据用户id,用户名，修改用户权限
	 * @param userId
	 * @param username
	 * @param roleID
	 * @return
	 */
	public int updateUserRole(@Param("userId")String userId,@Param("username") String username,@Param("roleID") String roleID);
	/**
	 * 分页检索用户
	 * @param index
	 * @param limit
	 * @return
	 */
	public List<UserInfoBean> selectAllUser(@Param("index")int index, @Param("limit")int limit);
	/**
	 * 根据账号查找用户
	 * @param account
	 * @return
	 */
	public List<UserInfoBean> findUserByAccount(@Param("account") String account);
	/**
	 * 根据微信号查找用户
	 * @param WeiXinID
	 * @return
	 */
	public List<UserInfoBean> findUserByWeiXinID(@Param("WeiXinID") String WeiXinID);

}
