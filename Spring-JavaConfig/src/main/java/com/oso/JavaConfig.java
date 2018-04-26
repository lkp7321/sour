package com.oso;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //声明当前类是一个配置类（定义Bean）
public class JavaConfig {
    /*
     此处@Bean 声明当前方法返回时一个Bean，Bean的名称是方法名，相当于在xml中配置
    <beans>
    <bean id="transferService" class="com.acme.TransferServiceImpl"/>
    </beans>
    在spring容器中只要容器中存在某个Bean，就可以在另外一个Bean的声明方法的参数中写入
    */
    @Bean
    public FunctionService functionService() {
        return new FunctionService();
    }

//    @Bean
//    public UseFunctionService useFunctionService() {
//        UseFunctionService useFunctionService = new UseFunctionService();
//        useFunctionService.setFunctionService(functionService());
//        return useFunctionService;
//    }
    @Bean
    public UseFunctionService useFunctionService(FunctionService functionService) {
        UseFunctionService useFunctionService = new UseFunctionService();
        useFunctionService.setFunctionService(functionService);
        return useFunctionService;
    }
}
