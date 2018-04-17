package com.ylxx.fx.controller.person.businesspara;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.BusinessVo;
import com.ylxx.fx.service.person.businesspara.FavruleService;
import com.ylxx.fx.service.po.FavourRule;
import com.ylxx.fx.service.po.Favrule;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
/**
 * P001
 * 分行优惠设置
 * @author lz130
 *
 */
@Controller
public class FavruleController {
	private static final Logger log = LoggerFactory.getLogger(FavruleController.class);
	@Resource(name="favruleService")
	private FavruleService favruleService;
	/**
	 * 分行优惠查询
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/getFavrulelist.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getFavrulelist(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		log.info("分行优惠查询");
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ogcd = bsVo.getOgcd();
		Integer pageNo = bsVo.getPageNo();
		Integer pageSize = bsVo.getPageSize();
		//ogcd 初始为all,为0001时也是all;
		String prod = curUser.getProd();//产品
		if(ogcd.equals("0001")){
			ogcd = "all";
		}
		PageInfo<Favrule> page = favruleService.getPageFavrulelist(pageNo, pageSize, prod, ogcd);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	/**
	 * 启用
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/openBegin.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String openBegin(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ip = curUser.getCurIP();
		List<Favrule> list = bsVo.getFavruleList();//new ArrayList<Favrule>();
		boolean flag = favruleService.openStat(curUser, list, ip);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "开启成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_05.getCode(), "开启失败");
		}
	}
	/**
	 * 停用
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/stopEnd.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String stopEnd(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ip = curUser.getCurIP();
		List<Favrule> list = bsVo.getFavruleList();//new ArrayList<Favrule>();
		boolean flag = favruleService.stopStat(curUser, list, ip);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "停用成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_06.getCode(), "停用失败");
		}
	}
	/**
	 * 修改--查询弹窗的数据
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/onSelFavrule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String onSelFavrule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String rule = bsVo.getRule();//"int getvar(String para){ int fvda=0;return fvda;}";
		String fvid = bsVo.getFvid();//"F01";
		List<FavourRule> list = favruleService.onSelFavrule(curUser, rule, fvid);
		List<Map<String, String>> list1 = favruleService.getfavbox(curUser.getProd(), fvid);
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "{\"box1\":"+JSON.toJSONString(list1)+",\"box2\":"+JSON.toJSONString(list)+"}");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), "当前无数据");
		}
	}
	/**
	 * 修改操作
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/onSaveFavrule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String onSaveSelFavrule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ip = curUser.getCurIP();
		List<FavourRule> list = bsVo.getFavourList();
		String fvid = bsVo.getFvid();
		String ogcd = bsVo.getComaogcd();//机构
		boolean flag = favruleService.saveFavruleRule(curUser, list, fvid, ogcd, ip);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "修改优惠规则成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_07.getCode(), "修改优惠规则失败");
		}
	}
	/**
	 * 初始化优惠规则
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/inisFav.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String inisFav(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String ogcd = bsVo.getOgcd();
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ip = curUser.getCurIP();
		boolean f = favruleService.insertFavrule(curUser, ogcd, ip);
		if(f){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "初始化所有优惠规则成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_22.getCode(), null);
		}
	}
	
}
