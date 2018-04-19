package com.sl.www.service;

import java.util.List;

import com.sl.www.domain.Department;

public interface DepartmentService {

	List<Department> findAll();

	void save(Department model);

	Department getById(int id);

	void update(Department department);

	void delete(int id);

	List<Department> findTopList();

	List<Department> findChildrenList(Integer parentId);

}
