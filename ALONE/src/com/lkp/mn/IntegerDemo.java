package com.lkp.mn;

/**
 * 包装类
 */
public class IntegerDemo {
    public static void main(String[] args){
        //把100转换为2进制，8进制，16进制数,计算int的最大值最小值
        first();
        //Integer的知识点
        second();
        //String和int类型之间的转换
        //String.valueOf();
        //Integer.parseInt();
        third();
        //10进制到其他进制转换
        //进制转换只有从2--36，因为0-9，a-z就只有36个
        System.out.println(Integer.toString(100,10));
        System.out.println(Integer.toString(100,5));
        //其他进制到10进制转换
        System.out.println(Integer.parseInt("100",2));
        System.out.println(Integer.parseInt("100",8));
    }

    /**
     * String || int
     */
    private static void third() {
        int i = 100;
        String s = "101";
        //int to String
        String str = ""+i;
        System.out.println("int to String1："+str);
        //int to String
        String str1 = String.valueOf(i);
        System.out.println("int to String2："+str1);
        //int to String
        String str2 = Integer.toString(i);
        System.out.println("int to String3："+str2);
        //int to String
        Integer iI = new Integer(i);
        System.out.println("int to String4："+iI.toString());
        System.out.println("------------------------------");
        //String to int
        //float等同样适用
        Integer integer = new Integer(s);
        int i1 = integer.intValue();
        System.out.println("String to int1："+i1);
        //String to int
        int i2 = Integer.parseInt(s);
        System.out.println("String to int2："+i2);

    }

    /**
     * Integer的构造方法
     */
    private static void second() {
        Integer i = new Integer(100);
        System.out.println("构造方法1："+i);
        //这里可以是字符串
        Integer is = new Integer("100");
        System.out.println("构造方法2："+is);
        //注意
        Integer is1 = new Integer("010");
        System.out.println("构造方法21："+is1);
        //注意：当构造参数是字符串时只能时由数字类型组成的字符串
//        Integer is2 = new Integer("abc");
//        System.out.println("构造方法2："+is2);
    }
    /**
     * 把100转换为2进制，8进制，16进制数,计算int的最大值最小值
     */
    private static void first(){
        System.out.println("二进制："+Integer.toBinaryString(100));
        System.out.println("八进制："+Integer.toOctalString(100));
        System.out.println("十六进制："+Integer.toHexString(100));
        System.out.println("int的最大值："+Integer.MAX_VALUE+"int的最小值："+Integer.MIN_VALUE);
    }
}
