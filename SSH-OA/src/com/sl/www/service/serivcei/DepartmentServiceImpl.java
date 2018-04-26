package com.sl.www.service.serivcei;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sl.www.dao.DepartmentDao;
import com.sl.www.domain.Department;
import com.sl.www.service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{
    
	@Resource
	private DepartmentDao departmentDao;
	
	public List<Department> findAll() {
		return departmentDao.findAll();
	}

	public void delete(int id) {
		departmentDao.delete(id);
		
	}

	public Department getById(int id) {
		return departmentDao.getById(id);
	}

	public void save(Department model) {
		departmentDao.save(model);
		
	}

	public void update(Department department) {
		departmentDao.update(department);
	}

	public List<Department> findChildrenList(Integer parentId) {
		// TODO Auto-generated method stub
		return departmentDao.findChildrenList(parentId);
	}

	public List<Department> findTopList() {
		// TODO Auto-generated method stub
		return departmentDao.findTopList();
	}

}
