package com.ylxx.fx.service;

import com.ylxx.fx.service.po.UserBean;

public interface IUserService {

	public UserBean Login(String username, String password);

}
