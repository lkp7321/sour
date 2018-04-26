package com.sl.www.dao;

import java.util.Collection;
import java.util.List;

import com.sl.www.base.BaseDao;
import com.sl.www.domain.Privilege;

public interface PrivilegeDao extends BaseDao<Privilege>{
	
	List<Privilege> findTopList();
	
	Collection<String> getAllPrivilegeUrls();

}
