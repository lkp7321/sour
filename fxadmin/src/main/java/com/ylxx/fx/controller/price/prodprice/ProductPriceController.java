package com.ylxx.fx.controller.price.prodprice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.domain.price.PriceProdVo;
import com.ylxx.fx.service.po.PdtJGinfo;
import com.ylxx.fx.service.po.PdtRParaT;
import com.ylxx.fx.service.po.Pdtinfo;
import com.ylxx.fx.service.po.TradeOnOffBean;
import com.ylxx.fx.service.price.productprice.ProductPriceService;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 报价平台———产品管理/产品管理，货币对
 */
@Controller
//@RequestMapping("fx")
public class ProductPriceController {
	@Resource(name = "prodPriceService")
	private ProductPriceService prodPriceService;
	private String codeMessage = "";
	/**
	 * 添加新产品数据页面
	 * @return
	 */
	@RequestMapping(value="price/getAllprodPrice.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllprodPrice(HttpServletRequest req){
		List<Pdtinfo> list = prodPriceService.selectAllPrice();
		if(list!=null&&list.size()>0){
			codeMessage=JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="当前数据为空";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
	/**
	 * 产品删除
	 * @param priceProdVo
	 * @return
	 */
	@RequestMapping(value="price/delprodPrice.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delprodPrice(HttpServletRequest req, @RequestBody PriceProdVo priceProdVo){
		String ptid = priceProdVo.getPtid();//"P678";
		String prnm = priceProdVo.getPrnm();//"PPRR1";
		String userKey = priceProdVo.getUserKey();
		boolean flag = prodPriceService.removePrice(userKey, ptid, prnm);
		if(flag){
			codeMessage="删除成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="删除失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	
	/**
	 * 产品添加/修改
	 * @param priceProdVo
	 * @return
	 */
	@RequestMapping(value="price/savePrice.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String saveprodPrice(HttpServletRequest req, @RequestBody PriceProdVo priceProdVo){
		String userKey = priceProdVo.getUserKey();
		Pdtinfo pdtinfo = priceProdVo.getPdtinfo();
		boolean flag = prodPriceService.savePrice(userKey, pdtinfo);
		if(flag){
			codeMessage="添加（修改）产品成功！";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="添加（修改）产品失败！";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	/**
	 * 货币对配置，产品下拉框
	 * @return
	 */
	@RequestMapping(value="price/prodExnmPrice.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String prodExnmPrice(HttpServletRequest req){
		List<Map<String,Object>> list = prodPriceService.selectexnmprice();
		if(list!=null&&list.size()>0){
			codeMessage=JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="获取产品失败!";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	/**
	 * 获取产货币对数据
	 * @param ptid
	 * @return
	 */
	@RequestMapping(value="price/prodExnmAllPrice.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String prodExnmAllPrice(HttpServletRequest req, @RequestBody String ptid){
		List<PdtRParaT> list = prodPriceService.selectallExnmPrice(ptid);
		if(list!=null&&list.size()>0){
			codeMessage=JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="当前记录为空";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}
	}
	/**
	 * 产品信息配置
	 * @param ptid
	 * @return
	 */
	@RequestMapping(value="price/allPmodPrice.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String allPmodPrice(HttpServletRequest req){
		List<PdtJGinfo> list = prodPriceService.selectAllPmodPrice();
		if(list!=null&&list.size()>0){
			codeMessage=JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="当前记录为空";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_0.getCode(), codeMessage);
		}
	}
	/**
	 * 删除产品配置信息
	 * @param req
	 * @param priceProdVo
	 * @return
	 */
	@RequestMapping(value="price/delPmodPrice.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String delPmodPrice(HttpServletRequest req, @RequestBody PriceProdVo priceProdVo){
		String userKey = priceProdVo.getUserKey();
		String ptid = priceProdVo.getPtid();
		boolean flag = prodPriceService.deletePmodPdtinfo(userKey, ptid);
		if(flag){
			codeMessage="删除成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="删除失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	/**
	 * 保存修改产品配置信息
	 * @param priceProdVo
	 * @return
	 */
	@RequestMapping(value="price/savePmodPrice.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String savePmodPrice(HttpServletRequest req, @RequestBody PriceProdVo priceProdVo){
		String userKey = priceProdVo.getUserKey();
		PdtJGinfo bean = priceProdVo.getBean();
		boolean flag = prodPriceService.addPmodPdtinfo(userKey, bean);
		if(flag){
			codeMessage="添加（修改）成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="删除（修改）失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	
	/**
	 * 修改保存开闭市
	 * @param req
	 * @param priceProdVo
	 * @return
	 */
	@RequestMapping(value="price/saveTradeOnofPrice.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String saveTradeOnofPrice(HttpServletRequest req, @RequestBody PriceProdVo priceProdVo){
		String userKey = priceProdVo.getUserKey();
		String ptidcomb = priceProdVo.getPtid();
		String usfg = priceProdVo.getUsfg();
		boolean flag = prodPriceService.upUsfg(userKey, ptidcomb, usfg);
		if(flag){
			codeMessage="修改成功";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="修改失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
	
	/**
	 * 交易开闭式管理，下拉框数据
	 * @param req
	 * @param ptid
	 * @return
	 */
	@RequestMapping(value="price/selTradeOnofPrice.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selTradeOnofPrice(HttpServletRequest req, @RequestBody String ptid){
		List<TradeOnOffBean> list = prodPriceService.selectSysctls(ptid);
		if(list!=null && list.size()>0){
			codeMessage=JSON.toJSONString(list);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), codeMessage);
		}else{
			codeMessage="获取失败";
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), codeMessage);
		}
	}
}
