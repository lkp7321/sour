package com.ss.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.model.User;
import com.ss.service.UserService;

/**
 *<p>Title: MainController.java</p>
 *<p>Description: TODO</p>
 *<p>CreateDate: 2017年6月12日</p>
 *@author shen
 *@version v1.0
 */
@RestController
public class MainController {

    private final static Logger LOG = Logger.getLogger(MainController.class);

    @Autowired
    UserService userService;

    @RequestMapping("/main")
    public String main(){
        User user = userService.findById("admin", "2ff4d63f4adb19d17cc0a99d2532b6a1");
        LOG.info(user);
        System.out.println(user.toString());
        return "Welcome to ssm-demo...";
    }

    @RequestMapping("/update")
    public String updateTest(){
        Integer num = userService.updateUser("test", "admin");
        LOG.info("return result: "+num);
        return "update operation test...";
    }

}
