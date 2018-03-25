package com.lkp.en;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理
 */
public class MyInvoke implements InvocationHandler{
    private Object target;
    public MyInvoke(Object target){
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("方法之前的校验");
        Object obj =  method.invoke(target,args);
        System.out.println("执行之后的日志");
        return obj;
    }
}
