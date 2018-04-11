package org.ssm.dufy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssm.dufy.dao.IUserDao;
import org.ssm.dufy.entity.User;
import org.ssm.dufy.service.IUserService;

@Service("userService")
public class IUserServiceImpl  implements IUserService{
	private Logger log = LoggerFactory.getLogger(IUserServiceImpl.class);
	@Autowired
	public IUserDao udao;
	
	@Override
	public User getUserById(int id) {
		User user = null;
		try {
			user = udao.selectByPrimaryKey(id);
		}catch (Exception e) {
			log.error("操作数据库出错");
		}
		return user;
	}

	@Override
	public int addUserById(int id) {
		int i = 0;
		try {
			i = udao.insertByPrimaryKey(id,"wow-0-wow");
		}catch (Exception e) {
			log.error("操作数据库出错");
			log.error(e.getMessage());
		}
		return i;
	}

	/**
	 * 用来测试事务
	 * 在这里我使用了两种事务的处理方式，一种是注解，一种是基于tx和aop命名空间的事务配置
	 */
	@Override
	//@Transactional
	public void updateTransition() {
		udao.insertByPrimaryKey(4,"测试事务，1");	// 表user中id为主键，数据库不存在这条数据，会插入成功
		udao.insertByPrimaryKey(3,"测试事务，2");	// 数据库存在这条数据，会报错
		// 这里是不能使用try...catch捕获这个异常，否则事务就不会起作用了
		// 添加注解@Transactional，不捕获该异常，两条数据都成功时才会commit
	}
}
