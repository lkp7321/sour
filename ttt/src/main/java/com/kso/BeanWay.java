package com.kso;

public class BeanWay {
    public void init() {
        System.out.println("@Bean-init-method");
    }
    public void destroy() {
        System.out.println("@Bean-destroy-destroy");
    }
    public BeanWay() {
        super();
        System.out.println("初始化构造函数-BeanWay");
    }
}
