package com.ylxx.fx.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylxx.fx.core.mapper.UserMapper;
import com.ylxx.fx.service.IUserService;
import com.ylxx.fx.service.po.UserBean;

@Service("loginService")
public class UserServiceImpl implements IUserService {

	@Resource
	private UserMapper um;

	@Override
	public UserBean Login(String username, String password) {
		System.out.println("login..........");
		return um.login(username, password);
	}

}
