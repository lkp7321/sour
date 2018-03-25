package com.lkp.an;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射案例，为“Integer”类型的List集合存入“String”类型的值
 */
public class ArrayDemo {
    public static void main(String[] args) throws Exception{
        List<Integer> list1 = new ArrayList<Integer>();
        // 这里这样写会报错
        // list1.add("aa");
        // 通过反射改变
        Class cla = list1.getClass();
        Method m1 = cla.getDeclaredMethod("add",Object.class);
        m1.setAccessible(true);
        m1.invoke(list1,"aaa");
        m1.invoke(list1,"bbb");
        m1.invoke(list1,"ccc");
        System.out.println(list1);
    }
}
