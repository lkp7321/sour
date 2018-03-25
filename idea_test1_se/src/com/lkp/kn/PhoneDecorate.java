package com.lkp.kn;

/**
 * 装饰抽象类
 */
public abstract class PhoneDecorate implements Phone {
    private Phone phone;
    public PhoneDecorate(Phone phone){
        this.phone = phone;
    }
    @Override
    public void call(){
        this.phone.call();
    }
}
