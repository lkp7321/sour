package com.sl.www.service.serivcei;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sl.www.dao.UserDao;
import com.sl.www.domain.User;
import com.sl.www.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserDao userDao;

	public void delete(int id) {
		userDao.delete(id);
		
	}

	public List<User> findAll() {
		return userDao.findAll();
	}

	public User findByLoginNameAndPassword(String loginName, String password) {
		return userDao.findByLoginNameAndPassword(loginName,password);
	}

	public User getById(int id) {
		return userDao.getById(id);
	}

	public void save(User model) {
		userDao.save(model);
		
	}

	public void update(User department) {
		userDao.update(department);
	}
	

}
