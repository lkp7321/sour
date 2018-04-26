package com.sl.www.service.serivcei;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sl.www.base.BaseDaoImpl;
import com.sl.www.dao.PrivilegeDao;
import com.sl.www.domain.Privilege;
import com.sl.www.service.PrivilegeService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class PrivilegeServiceImpl implements PrivilegeService{
    
	@Resource
	private PrivilegeDao privilegeDao;
	
	public List<Privilege> findTopList() {
		return privilegeDao.findTopList();
	}

	public Collection<String> getAllPrivilegeUrls() {
		return privilegeDao.getAllPrivilegeUrls();
	}

	public List<Privilege> findAll() {
		return privilegeDao.findAll();
	}

	public List<Privilege> getByIds(Integer[] privilegeIds) {
		return privilegeDao.getByIds(privilegeIds);
	}


}
