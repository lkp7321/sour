package com.lkp.kn;

/**
 * 测试类
 */
public class Test {
    public static void main(String[] args){
        PhoneDecorate phoneDecorate = new MusicDecorPhone(new Iphone());
        phoneDecorate.call();
    }
}
