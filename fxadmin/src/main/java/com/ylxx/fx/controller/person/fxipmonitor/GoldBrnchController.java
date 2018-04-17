package com.ylxx.fx.controller.person.fxipmonitor;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.service.person.fxipmonitor.GoldBrnchMonitorService;
import com.ylxx.fx.service.po.FormuleBean;
import com.ylxx.fx.service.po.GckwebBean;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;

/**
 * 纸黄金分行价格监控
 * @author lz130
 *
 */
@Controller
public class GoldBrnchController {
	private static final Logger log = LoggerFactory.getLogger(GoldBrnchController.class);
	
	@Resource(name="goldBrnchService")
	private GoldBrnchMonitorService goldBrnchService;
	/**
	 * 获取总分行价格监控
	 */
	@RequestMapping(value="AllGoldBrnchList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String AllGoldBrnchList(){
		log.info("获取总分行价格监控：");
		List<FxipMonitorVo> list = goldBrnchService.getAllGoldBrnch();
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), JSON.toJSONString(list));
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "当前暂无数据");
		}
	}
	
	/**
	 * 获取客户价格监控
	 */
	@RequestMapping(value="goldCustMonitorList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String goldCustMonitorList(){
		List<FxipMonitorVo> list = goldBrnchService.getAllGoldCust();
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), JSON.toJSONString(list));
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "当前暂无数据");
		}
	}
	/**
	 * 敞口监控
	 */
	@RequestMapping(value="allGoldwebPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String allGoldwebPrice(){
		List<GckwebBean> gckwebBeans = goldBrnchService.getAllGoldwebPrice();
		if(gckwebBeans!=null && gckwebBeans.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), JSON.toJSONString(gckwebBeans));
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "当前暂无数据");
		}
	}
	/**
	 * 分类敞口监控
	 * @param ckno
	 * @return
	 */
	@RequestMapping(value="GoldCkclassPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String GoldCkclassPrice(@RequestBody String ckno){
		List<FormuleBean> list = goldBrnchService.getGoldclassCk(ckno);
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), JSON.toJSONString(list));
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "当前暂无数据");
		}
	}
	
	/**
	 * 分类敞口列表
	 * @return
	 */
	@RequestMapping(value="GoldCkclassTree.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String GoldCkclassTree(){
		List<Map<String,String>> list = goldBrnchService.getGoldCkno();
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), JSON.toJSONString(list));
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "当前暂无数据");
		}
	}
}
