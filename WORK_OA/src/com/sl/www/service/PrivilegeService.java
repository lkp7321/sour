package com.sl.www.service;

import java.util.Collection;
import java.util.List;

import com.sl.www.domain.Privilege;

public interface PrivilegeService{
	
	/**
	 * 查询所有顶级的权限
	 * 
	 * @return
	 */
	List<Privilege> findTopList();

	/**
	 * 查询所有权限对应的URL集合（不重复）
	 * @return
	 */
	Collection<String> getAllPrivilegeUrls();
	
	/**
	 * 查询所有
	 * @return
	 */
	List<Privilege> findAll();
	
	/**
	 * 查询一组
	 * @param privilegeIds
	 * @return
	 */
	List<Privilege> getByIds(Integer[] privilegeIds);


}
