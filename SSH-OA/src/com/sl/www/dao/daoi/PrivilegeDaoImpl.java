package com.sl.www.dao.daoi;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.sl.www.base.BaseDaoImpl;
import com.sl.www.dao.PrivilegeDao;
import com.sl.www.domain.Privilege;

@Repository
@SuppressWarnings("unchecked")
public class PrivilegeDaoImpl extends BaseDaoImpl<Privilege> implements PrivilegeDao{

	public List<Privilege> findTopList() {
		return getSession().createQuery(//
		"FROM Privilege p WHERE p.parent IS NULL")//
		.list();
	}

	public Collection<String> getAllPrivilegeUrls() {
		return getSession().createQuery(//
		"SELECT DISTINCT p.url FROM Privilege p WHERE p.url IS NOT NULL")//
		.list();
	}

}
