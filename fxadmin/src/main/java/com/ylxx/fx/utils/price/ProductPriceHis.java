package com.ylxx.fx.utils.price;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hr.qutserver.base.objectbean.JSHPrice;
import com.hr.qutserver.base.objectbean.Price;
import com.hr.qutserver.base.objectbean.SelHisPricePoint;
import com.hr.qutserver.productbusiness.ProcessHisPrice;
import com.ylxx.fx.utils.HisPriceExnm;
import com.ylxx.fx.utils.HisPriceProduct;
import com.ylxx.fx.utils.ReadHisPrice;

public class ProductPriceHis {
private static final Logger log = LoggerFactory.getLogger(ProductPriceHis.class);
	
	/**
	 * 查询产品
	 * 
	 * @return List -- 产品集合
	 * */
	public List<HisPriceProduct> allProduct() {
		log.info("获取产品:");
		return ReadHisPrice.getReadHisPriceInstance().getProductList();
	}

	/**
	 * 查询产品下币别对
	 * 
	 * @param prod
	 *            -- 产品编号
	 * @return List -- 产品下币别对集合
	 * */
	public List<HisPriceExnm> productExnm(String prod) {
		log.info("获取币别对:");
		return ReadHisPrice.getReadHisPriceInstance().getProductExnmMap().get(prod);
	}

	/**
	 * 查询产品结售汇币别对的历史价格
	 * 
	 * @param productid
	 *            -- 产品编号
	 * @param currencyPair
	 *            -- 币别对
	 * @param begintime
	 *            -- 开始时间
	 * @param endtime
	 *            -- 结束时间
	 * @param priceType
	 *            -- 价格类型
	 * @return List<Price> -- 产品下币别对历史价格集合
	 * */
	public List<JSHPrice> selPriceHis(
			String productid, String currencyPair,
			String begintime, String endtime) {
		log.info("产品为P004，查询历史价格：");
		ProcessHisPrice priceHist = new ProcessHisPrice();
		SelHisPricePoint shpp = new SelHisPricePoint();
		shpp.setProductid(productid);
		shpp.setCurrencyPair(currencyPair);
		shpp.setBegintime(begintime);
		shpp.setEndtime(endtime);
		shpp.setPriceType("1");
		
		List priceList = priceHist.getPriceByTime(shpp);

		List<JSHPrice> l = priceList;
		
		return l;
	}
	
	/**
	 * 查询产品结售汇币别对的历史价格
	 * 
	 * @param productid
	 *            -- 产品编号
	 * @param currencyPair
	 *            -- 币别对
	 * @param begintime
	 *            -- 开始时间
	 * @param endtime
	 *            -- 结束时间
	 * @param priceType
	 *            -- 价格类型
	 * @return List<Price> -- 产品下币别对历史价格集合
	 * */
	public List<Price> selPriceHis(
			String productid, String currencyPair,
			String begintime, String endtime,String priceType) {
		log.info("产品不为P004，查询历史价格:");
		ProcessHisPrice priceHist = new ProcessHisPrice();
		SelHisPricePoint shpp = new SelHisPricePoint();
		shpp.setProductid(productid);
		shpp.setCurrencyPair(currencyPair);
		shpp.setBegintime(begintime);
		shpp.setEndtime(endtime);
		shpp.setPriceType(priceType);

		List<Price> priceList = priceHist.getPriceByTime(shpp);
		return priceList;
	}

	public static void main(String[] args) {
		List<Price> list = new ProductPriceHis().selPriceHis("P001", "EURUSD",
				"20110614 00:00:00", "20110614 01:00:00", "1");
		for(int i=0;i<list.size();i++){
			log.info(list.get(i).getBuyPrice()+" " +list.get(i).getSellPrice()+" " + list.get(i).getUpdateTime());
		}
	}
}
