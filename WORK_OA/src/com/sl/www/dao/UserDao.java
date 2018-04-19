package com.sl.www.dao;

import com.sl.www.base.BaseDao;
import com.sl.www.domain.User;

public interface UserDao extends BaseDao<User>{

	User findByLoginNameAndPassword(String loginName, String password);

}
