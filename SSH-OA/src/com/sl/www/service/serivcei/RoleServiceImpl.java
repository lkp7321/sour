package com.sl.www.service.serivcei;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sl.www.dao.RoleDao;
import com.sl.www.domain.Role;
import com.sl.www.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
    
	@Resource
	private RoleDao roleDao;
	
	public List<Role> findAll() {
		return roleDao.findAll();
	}

	public void delete(int id) {
		roleDao.delete(id);
		
	}

	public Role getById(int id) {
		return roleDao.getById(id);
	}

	public void save(Role model) {
		roleDao.save(model);
		
	}

	public void update(Role role) {
		roleDao.update(role);
	}

	public List<Role> getByIds(Integer[] ids) {
		return roleDao.getByIds(ids);
	}

}
