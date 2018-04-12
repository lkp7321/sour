package com.bootaop.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopClient {
    @AfterReturning("execution(* com.bootaop.service.*.*(..))")
    public void after() {
        System.out.println("aop 后置方法");
    }
    @Before("execution(* com.bootaop.service.*.*(..))")
    public void befor() {
        System.out.println("aop 前置方法");
    }
}
