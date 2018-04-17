package com.ylxx.fx.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.ProductDoms;

import com.ylxx.fx.service.po.User;

public interface LoginMapper {
	
	/**
	 * 查询用户产品
	 * @param usnm
	 * @return
	 */
	List<ProductDoms> findProList(@Param("usnm")String usnm);
	/**
	 * 查询分类
	 * @param usnm
	 * @return
	 */
	List<ProductDoms> findProList1(@Param("usnm")String usnm);
	/**
	 * 查询用户信息
	 * @param user
	 * @return
	 */
	User findUser(@Param("user")User user);
	/**
	 * 更新登陆时间
	 * @param nowtime
	 * @param user
	 * @return
	 */
	int upLoginTime(@Param("nowtime")String nowtime,@Param("user")User user);
	/**
	 * 判断用户角色是否存在
	 * @param user
	 * @return
	 */
	int findUserRole(@Param("user")User user);
	/**
	 * 登陆验证
	 * @param user
	 * @return
	 */
	User onLogin(@Param("user")User user);
	/**
	 * 当天的异常登陆处理，登陆次数+1
	 * @param user
	 * @return
	 */
	int upErrCount(@Param("user")User user);
	/**
	 * 登陆成功时更新utime
	 * @param nowtime
	 * @param user
	 * @return
	 */
	int upOverTime(@Param("nowtime")String nowtime,@Param("user")User user);
	/**
	 * 查询密码是否正确
	 * @param usnm
	 * @param pass
	 * @param prod
	 * @return
	 */
	int selectPassword(@Param("usnm")String usnm, @Param("pass")String pass, @Param("prod")String prod);
	/**
	 * 修改密码
	 * @param usnm
	 * @param pass
	 * @param prod
	 * @return
	 */
	int updatePasswordBegin(@Param("usnm")String usnm, @Param("pass")String pass, @Param("prod")String prod);
}
