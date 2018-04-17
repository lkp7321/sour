package com.ylxx.fx.controller.person.fxipmonitor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ylxx.fx.service.person.fxipmonitor.CmmExceptionService;
import com.ylxx.fx.service.po.Cmmalarm;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 异常数据监控
 */
@Controller
//@RequestMapping("fx")
public class CmmExceptionController {
	private static final Logger log = LoggerFactory.getLogger(CmmExceptionController.class);
	@Resource(name="cmmExceptionService")
	private CmmExceptionService cmmExceptionService;
	/**
	 * 查询异常监控数据
	 * @param req
	 * @param curDate
	 * @return
	 */
	@RequestMapping(value="/getCmmException.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getCmmException(HttpServletRequest req, @RequestBody String curDate){
		log.info("\n异常数据监控");
		List<Cmmalarm> list = cmmExceptionService.fxipExceptionData(curDate);
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "当前无符合条件的数据");
		}
	}
}
