package com.ylxx.fx.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.MenuBean;
import com.ylxx.fx.service.po.User;

public interface MenuMapper {
	/**
	 * 获取一级菜单
	 * @param user
	 * @return
	 */
	List<MenuBean> findMenuOne(@Param("user")User user);
	/**
	 * 获取二级菜单
	 * @param ptid:user.uspt,用户角色
	 * @param sbmn:权限名称
	 * @param sqno:产品编号，P001等
	 * @return
	 */
	List<MenuBean> findMenuTwo(@Param("ptid")String ptid,@Param("idno")String idno, @Param("prod")String prod);
	
	/**
	 * 获取三级菜单
	 * @param ptid:user.uspt,用户角色
	 * @param idno:上级权限编号,idno
	 * @return
	 */
	List<MenuBean> findMenuOther(@Param("ptid")String ptid, @Param("idno")String idno, @Param("prod")String prod);
	
}
