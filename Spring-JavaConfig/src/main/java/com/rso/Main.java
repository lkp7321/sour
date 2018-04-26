package com.rso;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 在使用这个的时候出现了一些访问不到资源文件的问题
 * 解决：把静态文件存入resource下，设置resource,java的编译输出路径都在classes下面
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ElConfig.class);
        ElConfig resourceService = context.getBean(ElConfig.class);
        resourceService.outputResource();
        context.close();
    }
}
