package com.sl.www.dao;

import java.util.List;

import com.sl.www.base.BaseDao;
import com.sl.www.domain.Department;

public interface DepartmentDao extends BaseDao<Department>{

	List<Department> findChildrenList(Integer parentId);

	List<Department> findTopList();

}
