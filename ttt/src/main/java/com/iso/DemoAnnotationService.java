package com.iso;

import org.springframework.stereotype.Service;

@Service
public class DemoAnnotationService {
    @Action(name = "注解连接add方法操作")
    public void add(){
        System.out.println("DemoAnnotationService执行add方法");
    }
}
