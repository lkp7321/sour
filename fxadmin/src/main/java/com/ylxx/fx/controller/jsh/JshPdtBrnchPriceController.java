package com.ylxx.fx.controller.jsh;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.service.jsh.IJshPdtBrnchPriceService;
import com.ylxx.fx.service.po.jsh.JshPdtBrnchPriceVo;

@Controller
public class JshPdtBrnchPriceController { 
	@Resource(name="jshPdtBrnchPriceService")
	private IJshPdtBrnchPriceService jshPdtBrnchPriceService;
	 /**
	  * 查询客户价公告板
	  * @param exnm trfg pageNo pageSize 
	  * @return  查询成功00 查询无结果01 查询异常02
	  */ 
	@RequestMapping(value="/selJshPdtBrnchPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String selJshPdtBrnchPrice(@RequestBody JshPdtBrnchPriceVo pdtBrnchPrice) throws Exception {
		return jshPdtBrnchPriceService.selJshPdtBrnchPrice(pdtBrnchPrice.getExnm() ,pdtBrnchPrice.getTrfg(),pdtBrnchPrice.getPageNo(),pdtBrnchPrice.getPageSize());
	}
	
	 /**
	  * 新增货币对
	  * @param String userKey exnm trfg pageNo pageSize
	  * @param JshPdtBrnchPriceVo pdtBrnchPrice
	  * @return 添加失败01 添加成功00
	  */
	@RequestMapping(value="/insJshPdtBrnchPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String insJshPdtBrnchPrice(@RequestBody JshPdtBrnchPriceVo pdtBrnchPrice) {
		return jshPdtBrnchPriceService.insJshPdtBrnchPrice(pdtBrnchPrice);
	}
	 /**
	  * 更新货币对
	  * @param String userKey exnm trfg pageNo pageSize
	  * @param JshPdtBrnchPriceVo pdtBrnchPrice
	  * @return 添加失败01 添加成功00
	  */
	@RequestMapping(value="/updJshPdtBrnchPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String updJshPdtBrnchPrice(@RequestBody JshPdtBrnchPriceVo pdtBrnchPrice) {
		return jshPdtBrnchPriceService.updJshPdtBrnchPrice(pdtBrnchPrice);
	}
	 /**
	  * 删除货币对
	  * @param String userKey exnm pageNo pageSize
	  * @param JshPdtBrnchPriceVo pdtBrnchPrice
	  * @return 添加失败01 添加成功00
	  */
	@RequestMapping(value="/delJshPdtBrnchPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String delJshPdtBrnchPrice(@RequestBody JshPdtBrnchPriceVo pdtBrnchPrice) {
		return jshPdtBrnchPriceService.delJshPdtBrnchPrice(pdtBrnchPrice);
	}
	 /**
	  * 是否存在此货币对
	  * @param String exnm
	  * @return 该货币对已存在00  该货币对可以使用 01  查重失败02
	  */
	@RequestMapping(value="/selBrnchExnmExist.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String selBrnchExnmExist(@RequestBody JshPdtBrnchPriceVo pdtBrnchPrice) {
		return jshPdtBrnchPriceService.selBrnchExnmExist(pdtBrnchPrice);
	}
	 /**
	  * 查詢已有货币对
	  * @return HashMap
	  */
	@RequestMapping(value="/selJshBrnchExnm.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String selJshExnm() {
		return jshPdtBrnchPriceService.selJshBrnchExnm();
	}
}
