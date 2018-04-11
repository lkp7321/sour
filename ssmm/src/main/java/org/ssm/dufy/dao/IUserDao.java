package org.ssm.dufy.dao;

import org.apache.ibatis.annotations.Param;
import org.ssm.dufy.entity.User;

public interface IUserDao {

    User selectByPrimaryKey(@Param("id")int id);

    int insertByPrimaryKey(@Param("id")int id, @Param("name")String name);
}