package com.kso;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class JSR250Way {
    @PostConstruct
    public void init() {
        System.out.println("JSR250-init-method");
    }
    @PreDestroy
    public void destroy() {
        System.out.println("JSR250-destroy-method");
    }
    public JSR250Way() {
        super();
        System.out.println("初始化构造函数-JSR250Way");
    }

}
