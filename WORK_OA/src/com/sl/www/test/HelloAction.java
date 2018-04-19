package com.sl.www.test;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class HelloAction extends ActionSupport{
	
	@Resource
	private TestService service;
	
	public String test(){
		System.out.println("HelloAction.test()");
		service.saveTwoUser();
		return "success";
	}

}
