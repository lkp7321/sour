package com.ylxx.fx.controller.pere.PereYh;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.CustLevelFavRuleVo;
import com.ylxx.fx.service.pere.PereYh.IPereCustLevelFavruleService;
import com.ylxx.fx.service.po.Trd_FavruleOgcd;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Controller
//@RequestMapping("fx")
public class PereCustLevelFavruleController {

	@Resource(name = "pereCustLevelFavruleService")
	private IPereCustLevelFavruleService pereCustLevelFavruleService;

	/**
	 * 添加客户等级优惠
	 * 
	 * @param userKey
	 * @param favruleOgcd
	 * @return
	 */
	@RequestMapping(value="/addCustLevelFavRule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String  addCustLevelFavRule(@RequestBody CustLevelFavRuleVo addCustLevelFavRuleVo){
		//@RequestBody CustLevelFavRuleVo addCustLevelFavRuleVo
		String result ="";
		
		
		//@RequestBody AddCustLevelFavRuleVo addCustLevelFavRuleVo
		//String userKey,String ogcd,String currency,String level,String byds,String slds,String beginDate,String endDate
		//0001	AUDRMB	R1	1	1	20171115	20171115
//	测试数据	
		String exit="false";
	String content=	pereCustLevelFavruleService.exist(addCustLevelFavRuleVo.getCurrency(),addCustLevelFavRuleVo.getLevel(),addCustLevelFavRuleVo.getOgcd());
	if(null!=content){
		try {
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "当前币种已存在");
			exit="true";
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),"无记录");
		} 
	}
		CurrUser curUser=LoginUsers.getLoginUser().getCurrUser(addCustLevelFavRuleVo.getUserKey());
//			CurrUser curUser= new CurrUser();
//			curUser.setUsnm("weiren");
//			curUser.setProd("dfddff");
//			curUser.setCurIP("11566111555");
//			curUser.setProd("FSD");
			
		if(exit.equals("false")){
			String flag=pereCustLevelFavruleService.addCustLevelFavRule(addCustLevelFavRuleVo, curUser);
			if(flag.equals("true")){
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "添加成功");
				
			}else{
				
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),"添加失败");
				
			}
		}
		return result;
	}

	/**
	 * @AUTHOR 韦任 删除客户等级优惠
	 * @PARAM DeleteCustLevelFavRulesVo deleteCustLevelFavRulesVo
	 * @RETURN
	 */
	@RequestMapping(value = "/deleteCustLevelFavRules.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteCustLevelFavRules(
			@RequestBody CustLevelFavRuleVo addCustLevelFavRuleVo) {
		// @RequestBody DeleteCustLevelFavRulesVo deleteCustLevelFavRulesVo
		// ArrayList <Trd_FavruleOgcd> chlist = new ArrayList<>();
		// Trd_FavruleOgcd favruleOgcd1=new Trd_FavruleOgcd();
		// Trd_FavruleOgcd favruleOgcd2=new Trd_FavruleOgcd();
		// favruleOgcd1.setByds("33");
		// favruleOgcd1.setSlds("22");
		// favruleOgcd1.setStdt("20161102");
		// /favruleOgcd1.setEddt("20171102");
		// favruleOgcd1.setExnm("JPYRMB");
		// favruleOgcd2.setByds("33");
		// favruleOgcd2.setSlds("11");
		// favruleOgcd2.setStdt("20161102");
		// favruleOgcd2.setEddt("20171102");
		// favruleOgcd2.setExnm("GBPRMB");
		// chlist.add(favruleOgcd2);
		// chlist.add(favruleOgcd1);
		// DeleteCustLevelFavRulesVo deleteCustLevelFavRulesVo=new
		// DeleteCustLevelFavRulesVo();
		// deleteCustLevelFavRulesVo.setChlist(chlist);
		// deleteCustLevelFavRulesVo.setOgcd("3300");
		// deleteCustLevelFavRulesVo.setLeve("R1");
		String result = "";
		String flag=pereCustLevelFavruleService
				.deleteCustLevelFavRules(addCustLevelFavRuleVo);
		
		if(flag.equals("true")){
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), "删除成功");
		
		}
		else{
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), "删除失败");
			
		}
		return result;
	}

	/**
	 * @AUTHOR 韦任 修改客户等级优惠
	 * @PARAM UpdateFavRuleByOgcdsVo uspdateFavRuleByOgcdsVo
	 * @RETURN
	 */
	// String userKey, ArrayList chlist,String ogcd,String custLevel,String
	// byds,String slds,String beginDate,String endDate
	@RequestMapping(value = "/updateRuleByCustLevels.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String updateRuleByCustLevels(
			@RequestBody CustLevelFavRuleVo custLevelFavRuleVo) {
		// @RequestBody CustLevelFavRuleVo custLevelFavRuleVo
		// ArrayList <Trd_FavruleOgcd> chlist = new ArrayList<>();
		// Trd_FavruleOgcd favruleOgcd1=new Trd_FavruleOgcd();
		// Trd_FavruleOgcd favruleOgcd2=new Trd_FavruleOgcd();
		// favruleOgcd1.setByds("33");
		// favruleOgcd1.setSlds("22");
		// favruleOgcd1.setStdt("20161102");
		// favruleOgcd1.setEddt("20171102");
		// favruleOgcd1.setExnm("EURRMB");
		// favruleOgcd2.setByds("33");
		// favruleOgcd2.setSlds("11");
		// favruleOgcd2.setStdt("20161102");
		// favruleOgcd2.setEddt("20171102");
		// favruleOgcd2.setExnm("USDRMB");
		// chlist.add(favruleOgcd2
		// chlist.add(favruleOgcd1);
		//
		// CustLevelFavRuleVo custLevelFavRuleVo= new CustLevelFavRuleVo();
		// custLevelFavRuleVo.setChlist(chlist);
		// custLevelFavRuleVo.setBeginDate("20170508");
		// custLevelFavRuleVo.setEndDate("20170608");
		// custLevelFavRuleVo.setByds("80");
		// custLevelFavRuleVo.setSlds("80");
		// custLevelFavRuleVo.setOgcd("3300");
		// custLevelFavRuleVo.setLevel("R1");
		//
		// CurrUser curUser= new CurrUser();
		// curUser.setUsnm("weiren");
		// curUser.setProd("dfddff");
		// curUser.setCurIP("11566111555");
		// curUser.setProd("FSD");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(
				custLevelFavRuleVo.getUserKey());
		String result = "";
		String flag=pereCustLevelFavruleService
				.updateRuleByCustLevels(custLevelFavRuleVo,
						curUser);
		
		if(flag.equals("true")){
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(),"修改成功");
			
			
		}
		else{result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),"修改失败");
			
		}
		return result;
	}

	/**
	 * @AUTHOR 韦任 查询等级优惠
	 * @PARAM CustLevelFavRuleVo custLevelFavRuleVo
	 * @RETURN
	 */
	// String ogcd,String custLevel PageNo PageSize
	@RequestMapping(value = "/getCustFavRuleByOgcd.do", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getCustFavRuleByOgcd(
			@RequestBody CustLevelFavRuleVo custLevelFavRuleVo) {
		// @RequestBody CustLevelFavRuleVo custLevelFavRuleVo
		// CustLevelFavRuleVo custLevelFavRuleVo=new CustLevelFavRuleVo();
		// custLevelFavRuleVo.setPageNo(10);
		// custLevelFavRuleVo.setPageSize(8);
		// custLevelFavRuleVo.setOgcd("3300");
		// custLevelFavRuleVo.setLevel("R1");

		String result = "";
		String ogcd = custLevelFavRuleVo.getOgcd();
		Integer pageNo = custLevelFavRuleVo.getPageNo();
		Integer pageSize = custLevelFavRuleVo.getPageSize();
		pageNo = pageNo == null ? 1 : pageNo;
		pageSize = pageSize == null ? 10 : pageSize;
		PageHelper.startPage(pageNo, pageSize);
		try {
			List<Trd_FavruleOgcd> linkedList = pereCustLevelFavruleService
					.getCustFavRuleByOgcd(custLevelFavRuleVo.getOgcd(),
							custLevelFavRuleVo.getLevel());
			PageInfo<Trd_FavruleOgcd> page = new PageInfo<Trd_FavruleOgcd>(
					linkedList);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(),
					JSON.toJSONString(page));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain
					.getRtnMsg(ErrorCodePrice.E_02.getCode(), "无记录");
		}
		return result;
	}

}
