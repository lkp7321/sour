package com.kso;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PrePostConfig.class);
        // BeanWay beanWay = context.getBean(BeanWay.class);
        JSR250Way jsr250Way = context.getBean(JSR250Way.class);
        // 在这里出现了一个问题：生成两个bean和生成一个Bean没有什么区别，都是生成了所有bean
        context.close();
    }
}
