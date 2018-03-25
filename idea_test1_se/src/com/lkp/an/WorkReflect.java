package com.lkp.an;

import com.lkp.an.entity.Student;

import java.lang.reflect.Field;

/**
 * 这里主要是对反射的应用
 */
public class WorkReflect {
    public static void main(String[] args) throws IllegalAccessException {
        Student s = new Student();
        s.setProd("123");
        s.setName("li");
        s.setRole("emp");
        Object object = method1(s,"prod");
        System.out.println(object);
        Object object0 = method1(s,"name");
        System.out.println(object0);
        Object object1 = method1(s,"saf");
        System.out.println(object1);
        forFor(s);
        forFor1();
    }

    /**
     * 反射
     * @param obj
     * @param title
     * @return
     * @throws IllegalAccessException
     */
    private static Object method(Object obj, String title) throws IllegalAccessException{
        Class<?> clazz = obj.getClass();
        if(clazz != Object.class) {
            clazz = clazz.getSuperclass();
        }
        Field field = null;
        try {
            field = clazz.getDeclaredField(title);
        } catch (NoSuchFieldException e) {
        }
        if(field != null) {
            field.setAccessible(true);
            return field.get(obj);
        }else {
            return null;
        }
    }

    /**
     * for的使用
     * @param obj
     * @param title
     * @return
     * @throws IllegalAccessException
     */
    private static Object method1(Object obj, String title) throws IllegalAccessException{
        Class<?> clazz = obj.getClass();
        Field field = null;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(title);
            } catch (NoSuchFieldException e) {
            }
        }
        if(field != null) {
            field.setAccessible(true);
            return field.get(obj);
        }else {
            return null;
        }
    }

    private static void forFor(Object obj){
      System.out.println("xxx："+obj.getClass());
      System.out.println("yyy："+Object.class);
      System.out.println("zzz："+obj.getClass().getSuperclass());
    }
    private static void forFor1(){
        int x = 1;
        int y = 0;
        for (; x < 12; x += 2) {
            y+=1;
            System.out.println("我需要执行几次。"+y);
        }
        System.out.println("asdsgdsa："+y+x);
    }

}
