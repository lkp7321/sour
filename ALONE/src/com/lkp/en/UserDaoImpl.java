package com.lkp.en;

/**
 * dao 实现
 */
public class UserDaoImpl implements UserDao {

    @Override
    public void add() {
        System.out.println("添加操作");
    }

    @Override
    public void delete() {
        System.out.println("删除操作");
    }

    @Override
    public void update() {
        System.out.println("修改操作");
    }

    @Override
    public void search() {
        System.out.println("查询操作");
    }
}
