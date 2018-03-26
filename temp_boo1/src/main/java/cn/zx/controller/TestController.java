package cn.zx.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.zx.service.UserService;

@Controller
@RequestMapping("zx")
public class TestController {
	
	@Resource(name="userService")
	private UserService userService;
	
	@RequestMapping("/xx.do")
	@ResponseBody
	public String xx() {
		return "xxx";
	}
	
	@RequestMapping("/home.do")
	public String welcome(Map<String, Object> model) {
		model.put("message", "hello,likunpeng");
		return "welcome";
	}
	
	@RequestMapping("/selUser.do")
	public String selcome(Map<String,Object> model) {
		model.put("usnm", userService.getUsers().get(0).getName());
		model.put("message", "hello,likunpeng");
		return "welcome";
	}
	
	@RequestMapping("/")
	public String home() {
		return "welcome";
	}
	@RequestMapping("/testh.do")
	public String sghs() {
		return "test/test1";
	}
}
