package com.ylxx.fx.core.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.RoleBean;

public interface RoleMapper {
	
	/**
	 * 查询角色
	 * @param prod
	 * @return
	 */
	List<RoleBean> getRolelist(@Param("prod")String prod);
	/**
	 * 添加角色
	 * @param role
	 * @throws Exception
	 */
	void addRole(@Param("role")RoleBean role)throws Exception;
	/**
	 * 删除角色
	 * @param ptid
	 * @param prod
	 * @throws Exception
	 */
	void deleteRole(@Param("ptid")String ptid,@Param("prod")String prod)throws Exception;
	/**
	 * 删除权限
	 * @param ptid
	 * @throws Exception
	 */
	void deleteRoleNrgt(@Param("ptid")String ptid)throws Exception;
	/**
	 * 修改角色
	 * @param role
	 * @throws Exception
	 */
	void upRole(@Param("role")RoleBean role)throws Exception;
	
	int hasrole(@Param("ptid")String ptid,@Param("ptnm")String ptnm, @Param("prod")String prod);
	int hasroles(@Param("role")RoleBean role);
	
}
