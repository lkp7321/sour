package com.ylxx.fx.controller.price.pricecontxp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hr.qutserver.base.objectbean.JSHPrice;
import com.hr.qutserver.base.objectbean.Price;
import com.ylxx.fx.core.domain.price.PriceProdVo;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.HisPriceExnm;
import com.ylxx.fx.utils.HisPriceProduct;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.price.ProductPriceHis;
/**
 * 历史价格查询
 */
@Controller
//@RequestMapping("fx")
public class PriceHistoryController {
	
	private ProductPriceHis productPriceHis = new ProductPriceHis();
	/**
	 * 产品
	 * @return
	 */
	@RequestMapping(value="/price/priceHisProd.do", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String allProduct(HttpServletRequest req){
		List<HisPriceProduct> list= productPriceHis.allProduct();
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	
	/**
	 * 货币对
	 * @param req
	 * @param prod
	 * @return
	 */
	@RequestMapping(value="/price/priceHisExnm.do", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String allProductExnm(HttpServletRequest req, @RequestBody String prod ){
		List<HisPriceExnm> list = productPriceHis.productExnm(prod);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	/**
	 * 查询
	 * @param req
	 * @param prod
	 * @return
	 */
	@RequestMapping(value="/price/priceHisList.do", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String priceHisList(HttpServletRequest req, @RequestBody PriceProdVo priceProdVo ){
		String prod = priceProdVo.getProd();
		String exnm = priceProdVo.getExnm();
		String begintime = priceProdVo.getBegintime();
		String endtime = priceProdVo.getEndtime();
		List<JSHPrice> list = productPriceHis.selPriceHis( 
				prod, exnm,
				begintime, endtime);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	
	/**
	 * 查询
	 * @param req
	 * @param prod
	 * @return
	 */
	@RequestMapping(value="price/priceHisList1.do", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String priceHisList1(HttpServletRequest req, @RequestBody PriceProdVo priceProdVo ){
		String prod = priceProdVo.getProd();
		String exnm = priceProdVo.getExnm();
		String begintime = priceProdVo.getBegintime();
		String endtime = priceProdVo.getEndtime();
		String type = priceProdVo.getType();
		List<Price> list = productPriceHis.selPriceHis(
				prod, exnm,
				begintime, endtime,
				type);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	
}
