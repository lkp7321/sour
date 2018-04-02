package com.oso;

public class UseFunctionService {
    FunctionService functionService;
    public void setFunctionService(FunctionService functionService) {
        this.functionService = functionService;
    }
    public String sayHello(String world) {
        return functionService.sayHello(world);
    }
}
