package com.lkp.kn;

/**
 * 装饰类
 */
public class MusicDecorPhone extends PhoneDecorate{

    public MusicDecorPhone(Phone phone) {
        super(phone);
    }
    @Override
    public void call(){
        System.out.println("新加的我可以听音乐");
        super.call();
    }
}
