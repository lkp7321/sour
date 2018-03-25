package com.lkp.en;

/**
 * dao接口
 * 阿里开发规范：所有接口的方发不加访问修饰符
 */
public interface UserDao {
    void add();
    void delete();
    void update();
    void search();
}
