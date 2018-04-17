package com.ylxx.fx.controller.price.pricesource;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.domain.price.PriceRecVo;
import com.ylxx.fx.core.domain.price.PriceReciveVo;
import com.ylxx.fx.service.person.businesspara.TradeProCalendarService;
import com.ylxx.fx.service.po.calendar.CalendarVO;
import com.ylxx.fx.service.po.calendar.OriginalVO;
import com.ylxx.fx.service.price.pricesource.PriceReceveService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Controller
//@RequestMapping("fx")
public class PriceReceiveController {
	@Resource(name="priceRecService")
	private PriceReceveService priceRecService;
	@Resource(name="tradeProCalendarService")
	private TradeProCalendarService tradeProCalendarService;
	private String codeMessage = "";
	
	@RequestMapping(value="price/getMarketinfoList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getMarketinfoList(){
		List<Map<String,String>> list = priceRecService.getMarketinfo();
		if(list!=null&&list.size()>0){
			codeMessage=JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "获取市场列表失败！";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
	
	@RequestMapping(value="price/getPriceReclist.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getPriceRec(@RequestBody PriceRecVo priceRecVo){
		String mkcode = priceRecVo.getMkid();
		String exnm = priceRecVo.getExnm();
		List<PriceRecVo> list = priceRecService.getPriceRecLists(mkcode, exnm);
		if(list!=null&&list.size()>0){
			codeMessage=JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "当前记录为空";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
	@RequestMapping(value="price/getBiBieDuiLists.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getBiBieDuiLists(@RequestBody String mkid){
		List<String> list = priceRecService.getBiBieDuiLists(mkid);
		codeMessage=JSON.toJSONString(list);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
	}
	/**
	 * 添加的保存
	 * @param priceReVo
	 * @return
	 */
	@RequestMapping(value="price/adSaveCheckPriceRec.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String adSaveCheckPriceRec(@RequestBody PriceReciveVo priceReVo){
		String userKey = priceReVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		List<OriginalVO> calList = priceReVo.getCalList();
		boolean f = tradeProCalendarService.checkoutCalRule(curUser, calList, curUser.getCurIP());
		if(f){
			PriceRecVo priceRecBean = priceReVo.getPricerecBean();
			boolean flag = priceRecService.insertPriceRec(curUser, priceRecBean);
			if(flag){
				codeMessage="添加成功！";
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
			}else{
				codeMessage="添加失败，请检查数据！";
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
			}
		}else{
			codeMessage = "请重新选择规则等级，所选规则等级有交叉或者同时有开闭盘！！";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	/**
	 * 修改保存
	 * @param priceReVo
	 * @return
	 */
	@RequestMapping(value="price/upchPriceRec.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upCheckPriceRec(@RequestBody PriceReciveVo priceReVo){
		String userKey = priceReVo.getUserKey();
		PriceRecVo priceRecBean = priceReVo.getPricerecBean();
		boolean f = priceRecService.upPriceRec(userKey, priceRecBean);
		if(f){
			codeMessage = "更新成功！";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "更新失败，请检查数据！";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	/**
	 * 删除
	 * @param priceReVo
	 * @return
	 */
	@RequestMapping(value="price/delchPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delchPrice(@RequestBody PriceReciveVo priceReVo){
		String userKey = priceReVo.getUserKey();
		PriceRecVo priceRecBean = priceReVo.getPricerecBean();
		boolean f = priceRecService.deletePriceRec(userKey, priceRecBean);
		if(f){
			codeMessage = "删除成功！";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "删除失败！";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	//批量修改
	@RequestMapping(value="price/upListPriceRec.do",produces="application/json;charset=UTF-8" )
	@ResponseBody
	public String upListPriceRec(@RequestBody PriceReciveVo priceReVo){
		String userKey = priceReVo.getUserKey();
		String calendarId = priceReVo.getCalendarId();
		List<PriceRecVo> priceRecList = priceReVo.getPriceRecList();
		boolean flag = priceRecService.upsPriceRecList(userKey, calendarId, priceRecList);
		if(flag){
			codeMessage = "修改成功！";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "修改失败！";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	//展示数据
	@RequestMapping(value="price/showPriceRec.do",produces="application/json;charset=UTF-8" )
	@ResponseBody
	public String ShowPriceRec(@RequestBody PriceReciveVo priceReVo){
		String userKey = priceReVo.getUserKey();
		String mkid = priceReVo.getMkid();
		String exnmCode = priceReVo.getExnm();
		String caltime = priceReVo.getTime();
		List<CalendarVO> list = priceRecService.getPriceReccalRuleList(userKey, mkid, exnmCode, caltime);
		if(list!=null&&list.size()>0){
			codeMessage = JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage = "当前暂无记录";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
}
