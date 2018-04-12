package com.bootaop;

import com.bootaop.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleAopApplication implements CommandLineRunner {
    @Autowired
    HelloService helloService;
    @Override
    public void run(String... strings) throws Exception {
       helloService.seeMessage();
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleAopApplication.class,args);
    }
}
