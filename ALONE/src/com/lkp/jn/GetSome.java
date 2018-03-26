package com.lkp.jn;

import java.io.IOException;

/**
 * 模板模式
 */
public abstract class GetSome {
    public void getTime() throws IOException {
        Long sTime = System.currentTimeMillis();
        code();
        Long eTime = System.currentTimeMillis();
        System.out.println("方法运行时常："+(eTime-sTime)+"毫秒!");
    }
    public void getLog() throws IOException {
        System.out.println("ready start go！");
        code();
        System.out.println("do Log into table, and endding!");
    }
    public abstract void code() throws IOException;
}
