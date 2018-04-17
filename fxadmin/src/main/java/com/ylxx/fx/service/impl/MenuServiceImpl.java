package com.ylxx.fx.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.mapper.MenuMapper;
import com.ylxx.fx.service.MenuService;
import com.ylxx.fx.service.po.MenuBean;
import com.ylxx.fx.service.po.User;
import com.ylxx.fx.utils.Men1;
import com.ylxx.fx.utils.Men2;
import com.ylxx.fx.utils.Menu;
import com.ylxx.fx.utils.Menus;

@Service("menuService")
public class MenuServiceImpl implements MenuService{
	private static final Logger log = LoggerFactory.getLogger(MenuServiceImpl.class);
	@Resource
	private MenuMapper menumap;
	/**
	 * 获取一级菜单
	 */
	@Override
	public List<MenuBean> findm1(User user) {
		List<MenuBean> list = null;
		try {
			list = menumap.findMenuOne(user);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	/**
	 * 获取二级菜单
	 */
	@Override
	public List<MenuBean> findm2(String ptid, String idno, String prod) {
		List<MenuBean> list = null;
		try {
			list = menumap.findMenuTwo(ptid, idno, prod);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	
	
//	public String getMenus(User user) {
//		StringBuffer a1=new StringBuffer("");
//		a1.append("\""+"Administrator"+"\":[");
//		
//		//获取一级菜单
//		List<MenuBean> list1 = findm1(user); 
//		for(int j = 0;j<list1.size();j++){
//			String sbmn1 = list1.get(j).getSbmn();
//			
//			a1.append("{\"one\""+":"+"\""+sbmn1+"\",\"two\":[");
//			//获取二级菜单
//			List<MenuBean> list2 = findm2(user.getUspt(), sbmn1, user.getProd());
//			for(int k = 0;k<list2.size();k++){
//				String sbmn2 = list2.get(k).getSbmn();
//				if(k<list2.size()-1){
//					a1.append("{\"key\":\""+sbmn2+"\"");
//					//获取三级菜单
//					List<MenuBean> list3 = findm3(user.getUspt(), list2.get(k).getIdno());
//					if(list3.size()>0){
//						a1.append(",\"key1\":[");
//						for(int y=0;y<list3.size();y++){
//							if(y<list3.size()-1){
//								a1.append("\""+list3.get(y).getSbmn()+"\",");
//							}
//							else{
//								a1.append("\""+list3.get(y).getSbmn()+"\"]");
//							}
//						}
//					}
//					a1.append("},");
//				}else{
//					a1.append("{\"key\":\""+sbmn2+"\"");
//					//获取三级菜单
//					List<MenuBean> list3 = findm3(user.getUspt(), list2.get(k).getIdno());
//					if(list3.size()>0){
//						a1.append(",\"key1\":[");
//						for(int y=0;y<list3.size();y++){
//							if(y<list3.size()-1){
//								a1.append("\""+list3.get(y).getSbmn()+"\",");
//							}
//							else{
//								a1.append("\""+list3.get(y).getSbmn()+"\"]");
//							}
//						}
//					}
//					a1.append("}");
//				}
//			}
//			if(j!=list1.size()-1){
//				a1.append("]},");
//			}else{
//				a1.append("]}");
//			}
//		}
//		a1.append("]}");
//		return a1.toString();
//	}
	/**
	 * 获取三级菜单
	 */
	@Override
	public List<MenuBean> findm3(String ptid, String idno, String prod) {
		List<MenuBean> list =null;
		try {
			list = menumap.findMenuOther(ptid, idno, prod);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	/**
	 * 获取菜单树对象
	 */
	public Menu getM(String userKey, User user){
		Menu menu = new Menu();
		menu.setUserKey(userKey);
		List<Menus> list = new ArrayList<Menus>();
		//获取一级菜单
		List<MenuBean> list1 = findm1(user); 
		Menus menus = null;
		if(list1!=null && list1.size()>0){
			for(int i = 0; i < list1.size(); i++){
				menus = new Menus();
				Men1 men1 = new Men1();
				men1.setId(list1.get(i).getIdno());
				men1.setNm(list1.get(i).getSbmn());
				men1.setUr(list1.get(i).getUrls());
				menus.setMenu1(men1);
				List<Men2> lists = new ArrayList<Men2>();
				List<MenuBean> list2 = findm2(user.getUspt(), list1.get(i).getIdno(), user.getProd());
				Men2 men2 = null;
				Men1 menKey1 = null;
				if(list2!=null && list2.size()>0){
					for(int j = 0; j < list2.size();j++){
						men2 = new Men2();
						menKey1 = new Men1();
						menKey1.setId(list2.get(j).getIdno());
						menKey1.setNm(list2.get(j).getSbmn());
						menKey1.setUr(list2.get(j).getUrls());
						men2.setMenKey1(menKey1);
						List<Men1> menKey2 = new ArrayList<Men1>();
						Men1 menx = null;
						List<MenuBean> list3 = findm3(user.getUspt(), list2.get(j).getIdno(), user.getProd());
						if(list3!=null && list3.size()>0){
							for(int k = 0; k < list3.size();k++){
								menx = new Men1();
								menx.setId(list3.get(k).getIdno());
								menx.setNm(list3.get(k).getSbmn());
								menx.setUr(list3.get(k).getUrls());
								menKey2.add(menx);
							}
						}
						men2.setMenKey2(menKey2);
						lists.add(men2);
					}
					menus.setMenu2(lists);
				}
				list.add(menus);
			}
		}
		menu.setMenuList(list);
		return menu;
	}
	
}
