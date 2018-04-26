package com.crm.service.impl;

import com.crm.dao.LoginDao;
import com.crm.dao.impl.LoginDaoImpl;
import com.crm.entity.User;
import com.crm.service.LoginService;

public class LoginServiceImpl implements LoginService {
   
	public User Login(String account, String password) {
		 LoginDao dao=new LoginDaoImpl();
		User user=dao.findFirst(account, password);
		return user;
	}

}
