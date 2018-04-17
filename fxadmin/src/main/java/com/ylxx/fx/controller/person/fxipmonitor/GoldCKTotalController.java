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

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.service.person.fxipmonitor.GoldCKtotalService;
import com.ylxx.fx.service.po.CkTotalBean;
import com.ylxx.fx.service.po.User;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 纸黄金总敞口监控
 * @author lz130
 *
 */
@Controller
public class GoldCKTotalController {
	private static final Logger log = LoggerFactory.getLogger(GoldCKTotalController.class);
	@Resource(name="goldCkToService")
	private GoldCKtotalService goldCkToService;
	
	/**
	 * 获取市场报价
	 */
	@RequestMapping(value="getCkTotalData.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getCkTotalData(){
		log.info("获取市场报价：");
		List<CkTotalBean> list = goldCkToService.ckTotalData();
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), JSON.toJSONString(list));
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "当前数据为空");
		}
	}
	
	/**
	 * 获取总敞口 && 折算敞口
	 */
	@RequestMapping(value="goldCkTotalData1.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String goldCkTotalData1(){
		List<CkTotalBean> list = goldCkToService.ckTotal();
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), JSON.toJSONString(list));
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "当前数据为空");
		}
	}
	/**
	 * 提取损益
	 * @param req
	 * @param ckTotalBean
	 * @return
	 */
	@RequestMapping(value="upgoldCkto.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upgoldCkto(HttpServletRequest req,@RequestBody CkTotalBean ckTotalBean){
		User user = (User)req.getSession().getAttribute("loginuser");
		boolean flag = goldCkToService.updateCKZCYK(user, ckTotalBean);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "提取外部损益成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "提取外部损益失败");
		}
	}
	
	/**
	 * 获取总浮动损益   && 人名币盈利
	 */
	@RequestMapping(value="goldCkTotalData2.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getCkTotalData2(){
		CkTotalBean ckTotalBean = goldCkToService.ckToalSYYL();
		if(ckTotalBean!=null){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), JSON.toJSONString(ckTotalBean));
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "获取浮动损益失败");
		}
	}
}
