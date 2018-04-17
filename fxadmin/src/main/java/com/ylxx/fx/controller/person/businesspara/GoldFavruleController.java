package com.ylxx.fx.controller.person.businesspara;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.BusinessVo;
import com.ylxx.fx.service.person.businesspara.GoldFavruleService;
import com.ylxx.fx.service.po.FavourRule;
import com.ylxx.fx.service.po.Favrule;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 纸黄金
 * 分行优惠规则
 * @author lz130
 *
 */
@Controller
public class GoldFavruleController {
	@Resource(name="goldFavruleService")
	private GoldFavruleService goldFavruleService;
	
	/**
	 * 获取机构
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="gold/selectOrganlist.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectOrganlist(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		String userorgleve = bsVo.getLevelTy();
		List<Map<String,Object>> list = goldFavruleService.selectOrganlist(userKey, userorgleve);
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "获取机构失败");
		}
	}
	/**
	 * 获取渠道
	 * @param req
	 * @return
	 */
	@RequestMapping(value="gold/comboxData.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String comboxDatas(HttpServletRequest req){
		List<Map<String,Object>> list =goldFavruleService.comboxData();
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "获取渠道优惠失败");
		}
	}
	/**
	 * 获取客户
	 * @param req
	 * @return
	 */
	@RequestMapping(value="gold/custboxData.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String custboxData(HttpServletRequest req){
		List<Map<String,Object>> list =goldFavruleService.custboxData();
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "获取客户优惠失败");
		}
	}
	/**
	 * 优惠规则查询
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="gold/selecFavrule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selecFavrule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		Integer pageNo = bsVo.getPageNo();
		Integer pageSize = bsVo.getPageSize();
		String ogcd = bsVo.getOgcd();
		PageInfo<Favrule> page =goldFavruleService.selectAllFavrule(ogcd, pageNo, pageSize);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	/**
	 * 规则启用
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="gold/openFavrule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String openFavrule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		List<Favrule> list = bsVo.getFavruleList();
		boolean flag = goldFavruleService.openStat(userKey, list);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "优惠规则开启成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "优惠规则开启失败");
		}
	}
	/**
	 * 规则停用
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="gold/stopFavrule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String stopFavrule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		List<Favrule> list = bsVo.getFavruleList();
		boolean flag = goldFavruleService.stopStat(userKey, list);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "优惠规则停用成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "优惠规则停用失败");
		}
	}
	/**
	 * 初始化--修改优惠规则
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="gold/onSelFavrule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String onSelFavrule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String rule = bsVo.getRule();
		String fvid = bsVo.getFvid();
		List<FavourRule> list =goldFavruleService.onSelFavrule(rule, fvid);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	/**
	 * 修改操作
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="gold/saveFavruleRule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String saveFavruleRule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		String fvid = bsVo.getFvid();
		String ogcd = bsVo.getOgcd();
		List<FavourRule> list = bsVo.getFavourList();
		boolean flag = goldFavruleService.saveFavruleRule(userKey, list, fvid, ogcd);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "优惠规则修改成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "优惠规则修改失败");
		}
	}
	/**
	 * 添加固定的优惠规则
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="gold/insertFavrule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String insertFavrule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		String ogcd = bsVo.getOgcd();
		String ognm = bsVo.getOgnm();
		boolean flag = goldFavruleService.insertFavrule(userKey, ogcd);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), ognm+"优惠规则添加成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), ognm+"优惠规则添加失败");
		}
	}
}
