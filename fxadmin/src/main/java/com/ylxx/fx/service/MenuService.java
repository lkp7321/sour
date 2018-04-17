package com.ylxx.fx.service;

import java.util.List;




import com.ylxx.fx.service.po.MenuBean;
import com.ylxx.fx.service.po.User;
import com.ylxx.fx.utils.Menu;

public interface MenuService {
	/**
	 * 查询一级菜单
	 * @param user
	 * @return
	 */
	List<MenuBean> findm1(User user);
	/**
	 * 查询二级菜单
	 * @param ptid
	 * @param sbmn
	 * @param sqno
	 * @return
	 */
	List<MenuBean> findm2(String ptid, String idno, String prod);
	/**
	 * 查询三级菜单
	 * @param ptid
	 * @param idno
	 * @return
	 */
	List<MenuBean> findm3(String ptid, String idno, String prod);
//	String getMenus(User user);
	/**
	 * 拼接菜单的json数据
	 * @param userKey
	 * @param user
	 * @return
	 */
	Menu getM(String userKey, User user);
}
