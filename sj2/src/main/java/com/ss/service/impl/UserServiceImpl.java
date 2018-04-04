package com.ss.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ss.dao.UserMapper;
import com.ss.model.User;
import com.ss.service.UserService;


@Service("userService")
public class UserServiceImpl implements UserService{

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection") //  在这里出现了一个问题，不使用注解就会报error,idea检查的错误
    private UserMapper userMapper;

    @Override
    public User findById(String code,String pwd) {
        //User user = userDao.findById(code,pwd);//普通方式

        List<User> list = userMapper.selectList(
                new EntityWrapper<User>().eq("user_code", code).eq("user_pwd", pwd)//Mybatis-plus封装方法
        );
	    /*测试readonly事务
	    logger.info(list.toString());

	    User user = new User();
        user.setUserId(1);
        user = userDao.selectById(user.getUserId());

        Integer num = userDao.updateUser("---", "admin");
        logger.info(num.toString());*/
        if (list.size()>0) {
            return list.get(0);
        } else {
            //return user;
            return null;
        }
    }

    @Override
    public Integer updateUser(String userName, String userCode) {
        Integer num = userMapper.updateUser(userName, userCode);
        return num;
    }

}