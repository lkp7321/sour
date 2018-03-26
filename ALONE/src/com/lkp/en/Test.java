package com.lkp.en;

import java.lang.reflect.Proxy;

/**
 * 动态代理，测试
 */
public class Test {
    public static void main(String[] args){
        UserDao ud = new UserDaoImpl();
        System.out.println("普通的方法调用");
        ud.add();
        ud.delete();
        ud.update();
        ud.search();
        System.out.println("代理的方法调用");
        UserDao ud2 = new UserDaoImpl();
        MyInvoke myInvoke = new MyInvoke(ud2);
        UserDao usd = (UserDao) Proxy.newProxyInstance(ud2.getClass().getClassLoader(),ud2.getClass().getInterfaces(),myInvoke);
        usd.add();
        usd.delete();
        usd.update();
        usd.search();

    }
}
