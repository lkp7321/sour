package com.ylxx.fx.core.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.Trd_ogcdDoms;
import com.ylxx.fx.core.domain.UserDoms;
import com.ylxx.fx.service.po.RoleBean;
import com.ylxx.fx.service.po.User;

public interface UserParaMapper {
	/**
	 * 查询所有用户信息（Administrator）
	 * @param prod
	 * @return
	 */
	List<UserDoms> selectAllUser(@Param("prod")String prod);
	/**
	 * 查询非所有
	 * @param prod
	 * @param uspt
	 * @return
	 */
	List<UserDoms> selectUser(@Param("prod")String prod,@Param("uspt")String uspt);
	/**
	 * 添加用户信息
	 * @param user
	 * @return
	 */
	void insertUser(@Param("user")User user);
	/**
	 * 修改用户信息
	 * @param user
	 * @param usnm
	 * @param prod
	 */
	void updateUser(@Param("user")User user,@Param("usnm")String usnm,@Param("prod")String prod);
	/**
	 * 删除用户信息
	 * @param user
	 */
	void deleteUser(@Param("user")User user);

	int isExitUser(@Param("usnm")String usnm, @Param("prod")String prod);
	/**
	 * 获取角色下拉菜单
	 * @param prod
	 * @return
	 */
	List<RoleBean> initRole(@Param("prod")String prod);
	/**
	 * fx1
	 * 获取机构下拉菜单
	 * @return
	 */
	List<Trd_ogcdDoms> initOgnm();
	/**
	 * fx2
	 * 获取机构下拉菜单
	 * @return
	 */
	List<Trd_ogcdDoms> initOgnm2();
	
	/**
	 * 获取初始化的密码
	 * @return
	 */
	String selInitPswd();
	/**
	 * 
	 * @return
	 */
	String selInitPassword();
			
	/**
	 * 重置密码
	 * @param newPswd
	 * @param usnm
	 * @param prod
	 * @return
	 */
	void mitPswd(@Param("newPswd")String newPswd,@Param("usnm")String usnm,@Param("prod")String prod);
	/**
	 * 用户复核
	 * @param usfg
	 * @param usnm
	 * @param prod
	 */
	void userFg(@Param("usfg")String usfg,@Param("usnm")String usnm,@Param("prod")String prod);
	/**
	 * 修改密码
	 * @param newPswd
	 * @param utime
	 * @param usnm
	 * @param prod
	 */
	void updatePswds(@Param("newPswd")String newPswd,@Param("utime")String utime,@Param("usnm")String usnm,@Param("prod")String prod);
	/**
	 * 登陆异常用户
	 * @param prod
	 * @param ognm
	 * @return
	 */
	List<UserDoms> listErrorUser(@Param("prod")String prod,@Param("ognm")String ognm);
	/**
	 * 清空异常用户
	 * @param prod
	 * @return
	 */
	void deleteErrorUser(@Param("prod")String prod);
	
}
