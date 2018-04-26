package com.sl.www.dao.daoi;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Repository;

import com.sl.www.base.BaseDaoImpl;
import com.sl.www.dao.UserDao;
import com.sl.www.domain.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	public User findByLoginNameAndPassword(String loginName, String password) {
		// 使用密码的MD5摘要进行对比
		String md5Digest = DigestUtils.md5Hex(password);
		return (User) getSession().createQuery(//
				"FROM User u WHERE u.loginName=? AND u.password=?")//
				.setParameter(0, loginName)//
				.setParameter(1, md5Digest)//
				.uniqueResult();
	}


}
