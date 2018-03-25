package com.lkp.TryCatch;

/**
 * 自定义异常，继承自RuntimeException
 * 方法不用throws抛出
 */
public class Test1 {
    public static void main(String[] args) {
        method(120);
    }
    private static void method(int math) {
        if(math>100 || math<0) {
            throw new MyException1();
        }else {
            System.out.println("数据正常了");
        }
    }

}
