package com.sl.www.service;

import java.util.List;

import com.sl.www.domain.Role;

public interface RoleService {

	List<Role> findAll();

	void save(Role model);

	Role getById(int id);

	void update(Role role);

	void delete(int id);
	
	List<Role> getByIds(Integer[] ids);

}
