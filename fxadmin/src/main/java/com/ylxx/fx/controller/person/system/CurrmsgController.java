package com.ylxx.fx.controller.person.system;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.SystemVo;
import com.ylxx.fx.core.domain.price.CurrmsgForAcc;
import com.ylxx.fx.core.domain.price.CurrmsgVo;
import com.ylxx.fx.service.person.system.CurmsgService;
import com.ylxx.fx.service.po.Currmsg;
import com.ylxx.fx.service.po.Cytp;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ErrorCodeSystem;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 货币对管理
 * @author lz130
 *
 */
@Controller
public class CurrmsgController {
	
	private static final Logger log = LoggerFactory.getLogger(CurrmsgController.class);
	@Resource(name="curmsgService")
	private CurmsgService curmsgService;
	/**
	 * P007
	 * 货币对管理  - 查询
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/getAllCurrmsgP7.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllCurrmsgP007(HttpServletRequest req){
		log.error("有关P007的货币对查询");
		return curmsgService.getAllCurrmsgP7();
	}
	
	/**
	 * P007
	 * 货币对2--修改前的查询
	 * @param req
	 * @return
	 */
	@RequestMapping(value="price/getexnminit.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getexnminit(HttpServletRequest req){
		List<Map<String,Object>> list = curmsgService.selExnminit();
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	/**
	 * P007
	 * 货币对2--修改
	 * @param currmsgVo
	 * @return
	 */
	@RequestMapping(value="price/upCurrmsg.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upCurrmsgPriceP7(@RequestBody CurrmsgVo currmsgVo){
		String userKey = currmsgVo.getUserKey();
		CurrmsgForAcc currmsgacc = currmsgVo.getCurrmsgacc();
		boolean flag = curmsgService.upCurrmsgPrice7(currmsgacc, userKey);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "修改成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "修改失败");
		}
	}
	/**
	 * P001,P009
	 * 货币对管理   - 查询
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/getAllCurrmsg.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllCurrmsg(HttpServletRequest req, @RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		return curmsgService.getAllCurrmsg(curUser.getProd());
	}
	/**
	 * 获取货币对下拉框
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/ExnmCurrmsglist.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ExnmCurrmsglist(HttpServletRequest req,@RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		List<Cytp> list = curmsgService.selCytpExnm(curUser.getProd());
		if(list!=null&&list.size()>0){
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_01.getCode(), "获取货币对失败");
		}
	}
	/**
	 * 删除币种
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/delCurrmsg.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delCurrmsg(HttpServletRequest req,@RequestBody SystemVo sysVo){
		String userKey = sysVo.getUserKey();
		String exnm = sysVo.getExnm();
		boolean flag = curmsgService.delCurrPrice(userKey, exnm);
		if(flag){
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "删除成功");
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_01.getCode(), "删除失败");
		}
	}
	/**
	 * 添加币种
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/addCurrmsg.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addCurrmsg(HttpServletRequest req,@RequestBody SystemVo sysVo){
		Currmsg currmsg = sysVo.getCurrmsg();
		String userKey = sysVo.getUserKey();
		return curmsgService.insCurrPrice(userKey, currmsg);
	}
	/**
	 * 修改币种
	 * @param req
	 * @param sysVo
	 * @return
	 */
	@RequestMapping(value="/modifyCurrmsg.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String modifyCurrmsg(HttpServletRequest req,@RequestBody SystemVo sysVo){
		Currmsg currmsg = sysVo.getCurrmsg();
		String userKey = sysVo.getUserKey();
		return curmsgService.upsCurrPrice(userKey, currmsg);
	}
}
