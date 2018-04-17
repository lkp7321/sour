package com.ylxx.fx.controller.person.system;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.SystemVo;
import com.ylxx.fx.service.person.system.CytpService;
import com.ylxx.fx.service.po.Cytp;
import com.ylxx.fx.utils.ErrorCodeSystem;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 币种管理
 * @author lz130
 *
 */
@Controller
public class CytpController {
	
	private static final Logger log = LoggerFactory.getLogger(CytpController.class);
	
	@Resource(name="cytpService")
	private CytpService cytpService;
	/**
	 * 币种管理 获取所有数据
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/getAllCytp.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllCytp(@RequestBody String userKey){
		log.info("获取币种信息列表");
		return cytpService.getAllCytp(userKey);
	}
	/**
	 * 添加币种
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/addCytp.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addCytp(HttpServletRequest req, @RequestBody SystemVo sysVo){
		String userKey = sysVo.getUserKey();
		Cytp cytp = sysVo.getCytp();
		boolean flag = cytpService.insCytp(userKey,cytp);
		if(flag){
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "添加币种成功");
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_02.getCode(), "添加币种失败");
		}
	}
	/**
	 * 修改币种
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/modifyCytp.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String modifyCytp(HttpServletRequest req, @RequestBody SystemVo sysVo){
		String userKey = sysVo.getUserKey();
		Cytp cytp = sysVo.getCytp();
		boolean flag = cytpService.upsCytp(userKey,cytp);
		if(flag){
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "修改币种成功");
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_01.getCode(), "修改币种失败");
		}
	}
	/**
	 * 删除币种
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/removeCytp.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String removeCytp(HttpServletRequest req, @RequestBody SystemVo sysVo){
		String userKey = sysVo.getUserKey();
		String cytpName = sysVo.getCytpName();
		boolean flag = cytpService.delCytp(userKey,cytpName);
		if(flag){
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "删除币种成功");
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_02.getCode(), "删除比重失败");
		}
	}
	
}
