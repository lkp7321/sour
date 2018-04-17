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
import com.ylxx.fx.service.person.businesspara.AccumFavruleService;
import com.ylxx.fx.service.po.FavourRule;
import com.ylxx.fx.service.po.Favrule;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 积存金(P003)
 * 分行优惠设置
 * @author lz130
 *
 */
@Controller
public class AccumFavruleController {

	@Resource(name="accumFavruleService")
	private AccumFavruleService accumFavruleService;
	
	/**
	 * 获取机构
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="accum/selectOrganlist.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectOrganlist(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		String userorgleve = bsVo.getLevelTy();
		List<Map<String,Object>> list = accumFavruleService.selectOrganlist(userKey, userorgleve);
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
	@RequestMapping(value="accum/comboxData.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String comboxDatas(HttpServletRequest req){
		List<Map<String,Object>> list =accumFavruleService.comboxData();
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "获取渠道失败");
		}
	}
	/**
	 * 获取客户
	 * @param req
	 * @return
	 */
	@RequestMapping(value="accum/custboxData.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String custboxData(HttpServletRequest req){
		List<Map<String,Object>> list =accumFavruleService.custboxData();
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "获取客户失败");
		}
	}
	/**
	 * 查询分行优惠数据
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="accum/selecFavrule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selecFavrule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String ogcd = bsVo.getOgcd();
		String userKey = bsVo.getUserKey();
		PageInfo<Favrule> page =accumFavruleService.pageSelecFavrule(userKey,ogcd,bsVo.getPageNo(),bsVo.getPageSize());
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	
	/**
	 * 优惠规则启用
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="accum/openFavrule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String openFavrule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		List<Favrule> list = bsVo.getFavruleList();
		boolean flag = accumFavruleService.openStat(userKey, list);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "开启优惠规则成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "开启优惠规则失败");
		}
	}
	
	/**
	 * 优惠规则停用
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="accum/stopFavrule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String stopFavrule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		List<Favrule> list = bsVo.getFavruleList();
		boolean flag = accumFavruleService.stopStat(userKey, list);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "停用优惠规则成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "停用优惠规则失败");
		}
	}
	/**
	 * 初始化修改页面
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="accum/onSelFavrule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String onSelFavrule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String rule = bsVo.getRule();
		String fvid = bsVo.getFvid();
		List<FavourRule> list =accumFavruleService.onSelFavrule(rule, fvid);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	/**
	 * 修改操作
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="accum/saveFavruleRule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String saveFavruleRule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		String fvid = bsVo.getFvid();
		String ogcd = bsVo.getOgcd();
		List<FavourRule> list = bsVo.getFavourList();
		boolean flag = accumFavruleService.saveFavruleRule(userKey, list, fvid, ogcd);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "优惠规则修改成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "优惠规则修改失败");
		}
	}
	
	/**
	 * 添加固定优惠规则
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="accum/insertFavrule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String insertFavrule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		String ogcd = bsVo.getOgcd();
		String ognm = bsVo.getOgnm();
		boolean flag = accumFavruleService.insertFavrule(userKey, ogcd);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), ognm+"优惠规则添加成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), ognm+"优惠规则添加失败");
		}
	}
	
}
