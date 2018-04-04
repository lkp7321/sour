package com.a;

import com.DemoObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);
    @RequestMapping("/index")
    public String hello() {
        return "index";
    }
    @RequestMapping(value = "/anno",produces = "text/plain; charset=UTF-8")
    public @ResponseBody String index(HttpServletRequest req) {
        return "url：" + req.getRequestURI() +" can access";
    }

    /**
     * 在此映射路径后面加数据
     * @param str
     * @param req
     * @return
     */
    @RequestMapping(value = "/pathvar/{str}", produces = "text/plain; charset=UTF-8")
    public @ResponseBody String demoPathvar(@PathVariable String str, HttpServletRequest req) {
        return "url：" + req.getRequestURI() +" can access, str：" + str;
    }

    /**
     * 采用？param=""的方式获取
     * @param name
     * @param req
     * @return
     */
    @RequestMapping(value = "/requestParam", produces = "text/plain; charset=UTF-8")
    public @ResponseBody String passParam(String name, HttpServletRequest req) {
        return "url：" + req.getRequestURI() +" can access, name：" + name;
    }
    /**
     * ?id= & name=
     * @param demoObj
     * @param req
     * @return
     */
    @RequestMapping(value = "/objParam", produces = "application/json; charset=UTF-8")
    public @ResponseBody String objParam(DemoObj demoObj, HttpServletRequest req) {
        return "url：" + req.getRequestURI() +" can access, id：" + demoObj.getId() + " name：" + demoObj.getName();
    }

    @RequestMapping(value = {"/name1", "/name2"}, produces = "text/plain; charset=UTF-8")
    public @ResponseBody String move(HttpServletRequest req) {
        log.info("匹配多个路径！");
        return "url：" + req.getRequestURI() +" can access ";
    }

    @RequestMapping(value ="/xmlObj", produces = "application/xml; charset=UTF-8")
    public @ResponseBody DemoObj xmlReturn(DemoObj demoObj) {
        log.info("返回xml：");
        return new DemoObj(demoObj.getId(),demoObj.getName()+"12");
    }
}
