package org.ssm.dufy.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.ssm.dufy.entity.User;
import org.ssm.dufy.service.IUserService;

@Controller
public class UserController {

	@Autowired
	private IUserService userService;

    /**
     * 测试查询
     * @param uid
     * @param request
     * @param model
     * @return
     */
	@RequestMapping(value = "/showname", method = RequestMethod.GET)
	public String showUserName(@RequestParam("uid") int uid,HttpServletRequest request,Model model) {
		User user = userService.getUserById(uid);
		if(user != null){
			request.setAttribute("name", user.getName());
			return "showName";
		}
		request.setAttribute("error", "没有找到该用户！");
		return "error";
	}

    /**
     * 测试添加
     * @param uid
     * @param request
     * @return
     */
	@RequestMapping(value = "/addname", method = RequestMethod.GET)
	public ModelAndView addUserName(@RequestParam("uid") int uid,HttpServletRequest request) {
		int i = userService.addUserById(uid);
		if(i > 0){
		    ModelAndView mav = new ModelAndView("showName");
		    mav.addObject("name", "添加成功");
			return mav;
		}else {
            ModelAndView mav = new ModelAndView("error");
            mav.addObject("error", "添加失败");
            return mav;
        }
	}

    /**
     * 测试事务
     * @param request
     * @param model
     * @returnq
     */
	@RequestMapping(value = "/testTrans", method = RequestMethod.GET)
	public String testUser(HttpServletRequest request,Model model) {
	    try {
            userService.updateTransition();
            request.setAttribute("error", "测试事务失败！");
            return "error";
        }catch (Exception e) {
            request.setAttribute("name", "测试事务成功！");
            return "showName";
        }
	}
}
