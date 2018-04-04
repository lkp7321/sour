package com.ss.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ss.model.User;

public interface UserMapper extends BaseMapper<User>{

    User findById(@Param("userCode") String code,@Param("userPwd") String pwd);

    Integer updateUser(@Param("userName")String userName, @Param("userCode")String userCode);
}
