package com.lkp.TryCatch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 编译期异常必须处理的
 * 运行时期异常可以不处理
 */
public class Demo1 {
    public static void main(String[] args) {
        try {
            method0();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("哦 哟");
        method1();
        System.out.println("呀 出错了");
    }
    private static void method0() throws ParseException {
        String da = "2018-01-01";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(da);
        System.out.println(date);
    }
    private static void method1()throws ArithmeticException {
        int a = 100;
        int b = 0;
        System.out.println(a/b);
    }
}
