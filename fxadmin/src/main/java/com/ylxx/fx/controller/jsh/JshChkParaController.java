package com.ylxx.fx.controller.jsh;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.service.jsh.IJshChkParaService;
import com.ylxx.fx.service.po.jsh.JshChkParaVo;

@Controller
public class JshChkParaController { 
	@Resource(name="jshChkParaService")
	private IJshChkParaService jshChkParaService;
	 /**
	  * 查询货币对校验参数信息
	  * @param exnm pageNo pageSize 
	  * @return  查询成功00 查询无结果01 查询异常02
	  */ 
	@RequestMapping(value="/selJshChkPara.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String selJshChkPara(@RequestBody JshChkParaVo chkPara) throws Exception {
		return jshChkParaService.selJshChkPara(chkPara.getExnm(),chkPara.getPageNo(),chkPara.getPageSize());
	}
	
	 /**
	  * 新增货币对新校验规则
	  * @param JshChkParaVo ChkPara
	  * @return 添加失败01 添加成功00
	  */
	@RequestMapping(value="/insJshChkPara.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String insJshChkPara(@RequestBody JshChkParaVo chkPara) {
		return jshChkParaService.insJshChkPara(chkPara);
	}
	 /**
	  * 更新货币对
	  * @param String userKey exnm trfg pageNo pageSize
	  * @param JshChkParaVo ChkPara
	  * @return 添加失败01 添加成功00
	  */
	@RequestMapping(value="/updJshChkPara.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String updJshChkPara(@RequestBody JshChkParaVo chkPara) {
		return jshChkParaService.updJshChkPara(chkPara);
	}
	 /**
	  * 删除货币对的规则
	  * @param String userKey exnm pageNo pageSize
	  * @param JshChkParaVo ChkPara
	  * @return 添加失败01 添加成功00
	  */
	@RequestMapping(value="/delJshChkPara.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String delJshChkPara(@RequestBody JshChkParaVo chkPara) {
		return jshChkParaService.delJshChkPara(chkPara);
	}
	 /**
	  * 是否存在此货币对
	  * @param String exnm
	  * @return 该货币对已存在00  该货币对可以使用 01  查重失败02
	  */
	@RequestMapping(value="/selChkParaExnmExist.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String selChkParaExnmExist(@RequestBody JshChkParaVo chkPara) {
		return jshChkParaService.selChkParaExnmExist(chkPara);
	}
	 /**
	  * 查詢已有货币对
	  * @return HashMap
	  */
	@RequestMapping(value="/selChkParaExnm.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String selChkParaExnm() {
		return jshChkParaService.selChkParaExnm();
	}
}
