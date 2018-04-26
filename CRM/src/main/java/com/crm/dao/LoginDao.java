package com.crm.dao;

import com.crm.entity.User;

public interface LoginDao {
public User findFirst(String account,String password);
}
