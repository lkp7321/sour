package com.sl.www.dao.daoi;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sl.www.base.BaseDaoImpl;
import com.sl.www.dao.DepartmentDao;
import com.sl.www.domain.Department;

@Repository
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements DepartmentDao{

	public List<Department> findChildrenList(Integer parentId) {
		return getSession().createQuery("from Department d where d.parent.id = "+parentId).list();
	}

	public List<Department> findTopList() {
		return getSession().createQuery("from Department d where d.parent = null").list();
	}

}
