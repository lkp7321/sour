package com.iso;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect // 注解声明一个切面
@Component //让此切面成为spring管理的Bean
public class LogAspect {
    /**
     * 声明一个以（@Action）注解为切点
     */
    @Pointcut("@annotation(com.iso.Action)")
    public void annotationPointCut(){
        System.out.println("开始拦截 -- 但是不会打印的");
    }
    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint){
        MethodSignature signature=(MethodSignature)joinPoint.getSignature();
        Method method=signature.getMethod();
        Action action=method.getAnnotation(Action.class);
        System.out.println("注解拦截方式:"+action.name());//5
    }

    /**
     * 将 匹配DemoMethodService类中任意方法作为切点
     * @param joinPoint
     */
    @Before("execution(* com.iso.DemoMethodService.*(..))")//6
    public void before(JoinPoint joinPoint){
        MethodSignature signature=(MethodSignature)joinPoint.getSignature();
        Method method=signature.getMethod();
        System.out.println("方法规则拦截方式:"+method.getName());
    }
}