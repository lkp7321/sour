package ai;

import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {
        DemoService demoService = new DemoServiceImpl();
        // 生成创建代理对象的对象
        TestInvock testInvock = new TestInvock(demoService);
        // 生成具体业务的代理对象
        DemoService demoServices = (DemoService) Proxy.newProxyInstance(demoService.getClass().getClassLoader(), demoService.getClass().getInterfaces(), testInvock);
        // 使用生成的代理对象完成功能
        demoServices.add();
        demoServices.del();
    }
}
