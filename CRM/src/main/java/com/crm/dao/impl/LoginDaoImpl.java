package com.crm.dao.impl;

import java.util.List;

import com.crm.dao.LoginDao;
import com.crm.entity.User;
import com.crm.util.DBUtil;

public class LoginDaoImpl implements LoginDao{
	public User findFirst(String account, String password) {
		Object[] params={account,password};
		String sql="SELECT * FROM user where account=? AND password=?";
		List<User> list=DBUtil.getObjectList(sql, params,User.class);
		User user=null;
		if(list.size()==1){
		user=list.get(0);
		 
		} 
	   return user;
	}
public static void main(String[] args) {
	LoginDaoImpl dao=new LoginDaoImpl();
	dao.findFirst("admi","123");
}
}
