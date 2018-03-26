package com.lkp.fn;
import static java.lang.Math.abs;

/**
 * 静态导入
 */
public class StaticImport {
    public static void main(String[] args){
        // 方式1
        System.out.println(Math.abs(-10));
        // 方式2
        // 必须时静态方法
        // import static java.lang.Math.abs;
        System.out.println(abs(-10));
        //方式3 在该方法前带上包名
    }
}
