package com.ylxx.fx.controller.person.system;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.price.PriceProdVo;
import com.ylxx.fx.service.person.system.PriceCheckService;
import com.ylxx.fx.service.person.system.SysclService;
import com.ylxx.fx.service.po.Sysctl;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ErrorCodeSystem;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 系统管理 
 * 价格校验器设置
 */
@Controller
public class PriceCheckController {
	private static final Logger log = LoggerFactory.getLogger(PriceCheckController.class);
	@Resource(name="priceCheckService")
	private PriceCheckService priceCheckService;
	@Resource(name="sysclService")
	private SysclService sysclService;
	/**
	 * 获取价格校验器设置数据
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/getAllPriceCheck.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllPricecheck(HttpServletRequest req, @RequestBody String userKey){
		log.info("获取价格校验器设置数据");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		return priceCheckService.allPriceCheck(curUser);
	}
	/**
	 * 获取系统总控状态
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/getSysStat.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getSysStat(HttpServletRequest req, @RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String s = sysclService.selecSyst(curUser.getProd());//开启或关闭
		return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), s);
	}
	/**
	 * 获取价格校验器状态
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/getPriceStat.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getPriceStat(HttpServletRequest req){
		String s = priceCheckService.getchkpara();//开启或关闭
		return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), s);
	}
	/**
	 * 关闭交易
	 * @param req
	 * @param userKey
	 */
	@RequestMapping(value="/closeJiaoYi.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public void closeJiaoYi(HttpServletRequest req, @RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Sysctl sysctl = new Sysctl();
		sysctl.setOpno("admin");//写死的数据
		sysctl.setUsfg("1");//写死的数据
		sysctl.setTrtm(DataTimeClass.getCurDateTime());
		sysclService.upcon(curUser, sysctl);//关闭系统总控
		priceCheckService.closePriceStat(curUser);//关闭价格源校验器
		priceCheckService.updatePriceStat(curUser);//开启产品校验器
	}
	/**
	 * 刷新价格源校验器
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/refshPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String refshPrice(HttpServletRequest req, @RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Sysctl sysctl = new Sysctl();
		sysctl.setOpno("admin");
		sysctl.setUsfg("1");
		sysctl.setTrtm(DataTimeClass.getCurDateTime());
		sysclService.upcon(curUser, sysctl);//关闭系统校验器
		priceCheckService.opPriceStat(curUser);//开启价格源
		priceCheckService.updatePriceStat(curUser);
		boolean flag = priceCheckService.refshPrice(curUser);
		if(flag){
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "刷新价格校验器成功");
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_22.getCode(), null);
		}
	}
	/**
	 * 打开交易
	 * @param req
	 * @param userKey
	 */
	@RequestMapping(value="/openJiaoYi.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public void openPrice(HttpServletRequest req, @RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Sysctl sysctl = new Sysctl();
		sysctl.setOpno("admin");
		sysctl.setUsfg("0");
		sysctl.setTrtm(DataTimeClass.getCurDateTime());
		sysclService.upcon(curUser, sysctl);//打开系统状态
	}
	/**
	 * 刷新外汇实盘校验器
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/refshPtPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String refshPtPrice(HttpServletRequest req, @RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Sysctl sysctl = new Sysctl();
		sysctl.setOpno("admin");
		sysctl.setUsfg("1");
		sysctl.setTrtm(DataTimeClass.getCurDateTime());
		sysclService.upcon(curUser, sysctl);//关闭系统状态
		priceCheckService.opPriceStat(curUser);
		priceCheckService.updatePriceStat(curUser);//打开产品校验器
		boolean flag = priceCheckService.refshSptPrice(curUser);
		if(flag){
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "刷新实盘校验器成功,请启动交易");
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_23.getCode(), null);
		}
	}
	/**
	 * 价格校验器，开启
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/openPricestat.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String openPricestat(HttpServletRequest req, @RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		boolean flag = priceCheckService.opPriceStat(curUser);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "开启价格校验器状态成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "开启价格校验器状态失败");
		}
	}
	/**
	 * 价格校验器，关闭
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/closePricestat.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String closePricestat(HttpServletRequest req, @RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		boolean flag = priceCheckService.closePriceStat(curUser);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "关闭价格校验器状态成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "关闭价格校验器状态失败");
		}
	}
	/**
	 * 刷新价格校验器P999价格监控-》市场利率 刷新价格校验器
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/refshPricestats.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String refshPricestats(HttpServletRequest req, @RequestBody String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		boolean flag = priceCheckService.refshPrices(curUser);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "刷新价格校验器成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "刷新价格校验器失败");
		}
	}
	/**
	 * 新增根据客户价格刷新校验器
	 * @param req
	 * @param priceProdVo
	 * @return
	 */
	@RequestMapping(value="/refshcustPricestats.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String refshcustPricestats(HttpServletRequest req, @RequestBody PriceProdVo priceProdVo ){
		String userKey = priceProdVo.getUserKey();
		String prod = priceProdVo.getPtid(); 
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		boolean flag = priceCheckService.refshCustPrices(curUser, prod);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "刷新价格校验器成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "刷新价格校验器失败");
		}
	}
}
