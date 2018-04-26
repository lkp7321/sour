package com.iso;

import org.springframework.stereotype.Service;

@Service
public class DemoMethodService {
    public void add(){
        System.out.println("DemoMethodService执行add方法");
    }
}