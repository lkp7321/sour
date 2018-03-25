package com.lkp.mn;

/**
 * jdk5的新特性，自动装箱和拆箱
 * 自动装箱：把基本类型转换为包装类型
 * 自动拆箱：把包装类型转换为基本类型
 * 针对byte内的数据，有一个常量池(-128<-->127)有一个数据缓冲池，如果在这个范围内，就直接去一个值，如果不在就新创建一个。
 *
 */
public class IntegetInt {
    public static void main(String[] args){
        Integer i = 100;
        i += 200;
        System.out.println("自装自拆："+i);
        //实现原理
        Integer ii = Integer.valueOf(100);
        ii = Integer.valueOf(ii.intValue()+200);
        System.out.println("实现原理："+ii);
        //面试题
        firstView();
        secondView();
        thirdView();
        fourthView();
    }

    /**
     * 面试1
     */
    private static void firstView(){
        Integer ii1 = new Integer(127);
        Integer ii2 = new Integer(127);
        System.out.println("面试1："+(ii1==ii2));
        System.out.println("面试1："+(ii1.equals(ii2)));
    }
    /**
     * 面试2
     */
    private static void secondView(){
        Integer ii1 = new Integer(128);
        Integer ii2 = new Integer(128);
        System.out.println("面试2："+(ii1==ii2));
        System.out.println("面试2："+(ii1.equals(ii2)));
    }
    /**
     * 面试3
     */
    private static void thirdView(){
        Integer ii1 = 127;
        Integer ii2 = 127;
        System.out.println("面试3："+(ii1==ii2));
        System.out.println("面试3："+(ii1.equals(ii2)));
    }
    private static void fourthView(){
        Integer ii1 = 128;
        Integer ii2 = 128;
        System.out.println("面试4："+(ii1==ii2));
        System.out.println("面试4："+(ii1.equals(ii2)));
    }



}
