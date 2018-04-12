package com.bootaop.service.impl;

import com.bootaop.service.HelloService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Value("${name:World}")
    private String name;
    @Override
    public void seeMessage() {
        System.out.println("Hello : "+this.name);
    }
}
