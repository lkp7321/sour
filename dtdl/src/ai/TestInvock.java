package ai;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TestInvock implements InvocationHandler {
    private Object target;
    public TestInvock(Object target) {
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("要执行的方法："+method.getName());
        System.out.println("在方法执行之前：的日志");
        // 把执行对象给代理掉
        Object object = method.invoke(target,args);
        System.out.println("在方法执行之后：的日志");
        return object;
    }
}
