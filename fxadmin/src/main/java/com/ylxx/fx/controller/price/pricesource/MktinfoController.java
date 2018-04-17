package com.ylxx.fx.controller.price.pricesource;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.price.PriceRecVo;
import com.ylxx.fx.core.domain.price.PriceReciveVo;
import com.ylxx.fx.service.po.CmmChkpara;
import com.ylxx.fx.service.po.Mktinfo;
import com.ylxx.fx.service.price.pricesource.MktinfoService;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.UDPClient;

@Controller
//@RequestMapping("fx")
public class MktinfoController {
	
	@Resource(name="mktinfoService")
	private MktinfoService mktinfoService;
	private String codeMessage = "";
	private UDPClient udpclient = new UDPClient(); 
	//价格源注册，查询市场
	@RequestMapping(value="price/getMkPrice.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getMkPriceList(){
		List<Mktinfo> list = mktinfoService.getMkPrice();
		if(list!=null && list.size()>0){
			codeMessage=JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="当前数据为空";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
	/**
	 * 校验器数据：
	 * @param priceRecVo
	 * @return
	 */
	@RequestMapping(value="price/getJiaoYanQiList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getJiaoYanQiList(@RequestBody PriceRecVo priceRecVo){
		String mkid = priceRecVo.getMkid();
		String exnm = priceRecVo.getExnm();
		Integer pageNo = priceRecVo.getPageNo();
		Integer pageSize = priceRecVo.getPageSize();
		PageInfo<CmmChkpara> page = mktinfoService.pagejiaoyansel(pageNo, pageSize, mkid, exnm);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	
	//校验器启用停用
	@RequestMapping(value="price/opencloseJiaoyanqi.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String opencloseJiaoyanqi(HttpServletRequest req, @RequestBody PriceReciveVo priceReVo){
		boolean f = false;
		String userKey = priceReVo.getUserKey();
		List<CmmChkpara> chlist = priceReVo.getChlist();
		if(chlist.get(0).getUsfg().equals("1")){
			f = mktinfoService.openChannel(userKey, chlist);
		}else{
			f = mktinfoService.closeChannel(userKey, chlist);
		}
		if(f){
			codeMessage="启用（停用）成功!";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="启用（停用）失败!";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
	
	@RequestMapping(value="price/upJiaoyanqi.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upJiaoyanqi(@RequestBody PriceReciveVo priceReVo){
		String userKey = priceReVo.getUserKey();
		CmmChkpara cmmbean = priceReVo.getCmmbean();
		boolean flag = mktinfoService.updateCmmprice(cmmbean, userKey);
		if(flag){
			codeMessage="修改成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="修改失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	
	@RequestMapping(value="price/sendSocketB1.do",produces="application/json;charset=UTF-8")
	public void sendSocketB1(){
		udpclient.SendSocketB1();
	}
	
	@RequestMapping(value="price/selUptype.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selUptype(){
		List<Map<String,Object>> list = mktinfoService.selUpType();
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
}
