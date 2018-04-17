package com.ylxx.fx.controller.person.system;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.SystemVo;
import com.ylxx.fx.service.person.system.OrganizationService;
import com.ylxx.fx.service.po.Trd_ogcd;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 系统管理  机构管理
 * @author lz130
 *
 */
@Controller
public class OrganizationController {
	
	private static final Logger log = LoggerFactory.getLogger(OrganizationController.class);
	@Resource(name="organService")
	private OrganizationService organService;
	/**
	 * 查询当前登录的用户机构信息
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/getuserOgcd.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getUserOgcd(HttpServletRequest req, @RequestBody String userKey){
		Trd_ogcd trdOgcd = organService.getUserog(userKey);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), trdOgcd);
	}
	/**
	 * 查询所有机构
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/getAllOgcd.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getPageAllOgcd(HttpServletRequest req, @RequestBody SystemVo sysVo){
		log.info("获取所有机构列表");
		String userKey = sysVo.getUserKey();
		Integer pageNo = sysVo.getPageNo();
		Integer pageSize = sysVo.getPageSize();
		PageInfo<Trd_ogcd> page = organService.getPageAllOgcd(userKey,pageNo,pageSize);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	/**
	 * 添加机构
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/addOgancd.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addOgancd(HttpServletRequest req, @RequestBody SystemVo sysVo){
		String userKey = sysVo.getUserKey();
		Trd_ogcd trdOgcd = sysVo.getTrdOgcd();//ogcd,OGNM,USFG,LEVE,OGUP:"上级机构编号",ORGN:"自定义一级编号"
		return organService.insert(trdOgcd, userKey);
	}
	/**
	 * 修改机构
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/modifyOgancd.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String modifyOgancd(HttpServletRequest req, @RequestBody SystemVo sysVo){
		String userKey = sysVo.getUserKey();
		Trd_ogcd trdOgcd = sysVo.getTrdOgcd();//ogcd,OGNM,USFG,LEVE,OGUP:"上级机构编号",ORGN:"自定义一级编号"
		return organService.updateOg(trdOgcd, userKey);
	}
	/**
	 * 获取添加的机构下拉框
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/getAdOrgan.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAdOrgan(HttpServletRequest req,@RequestBody SystemVo sysVo){
		String leve = sysVo.getLeve();
		String userKey = sysVo.getUserKey();
		return organService.curLeveList(userKey, leve);
	}
	/**
	 * 获取修改的机构下拉框
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/getUpOrgan.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getUpOrgan(HttpServletRequest req, @RequestBody SystemVo sysVo){
		String userKey = sysVo.getUserKey();
		String leve = sysVo.getLeve();
		return organService.organUpList(userKey, leve);
	}
	
	/**
	 * 删除
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/delOrgan.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delOrgan(HttpServletRequest req, @RequestBody SystemVo sysVo){
		String userKey = sysVo.getUserKey();
		String ogcd = sysVo.getOgcd();
		return organService.delog(userKey, ogcd);
	}
	/**
	 * 获取机构合并下拉框
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/getHeOrgan.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getHeOrgan(HttpServletRequest req, @RequestBody SystemVo sysVo){
		String userKey = sysVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Trd_ogcd trdOgcd = sysVo.getTrdOgcd();//机构编号和上级机构编号
		return organService.getHeBing(curUser.getProd(), 
				trdOgcd.getOgup(), trdOgcd.getOgcd());
	}
	/**
	 * 机构合并
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/heOrgan.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String heOrgan(HttpServletRequest req, @RequestBody SystemVo sysVo){
		String userKey = sysVo.getUserKey();
		String newogcd = sysVo.getNewogcd();
		String oldogcd = sysVo.getOldogcd();
		return organService.heBing(userKey, newogcd, oldogcd);
	}
}
