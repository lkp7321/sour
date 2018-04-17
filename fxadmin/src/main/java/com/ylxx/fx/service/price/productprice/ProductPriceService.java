package com.ylxx.fx.service.price.productprice;

import java.util.List;
import java.util.Map;

import com.ylxx.fx.service.po.PdtJGinfo;
import com.ylxx.fx.service.po.PdtRParaT;
import com.ylxx.fx.service.po.Pdtinfo;
import com.ylxx.fx.service.po.TradeOnOffBean;

public interface ProductPriceService {
	/**
	 * 查询产品信息
	 * @return
	 */
	public List<Pdtinfo> selectAllPrice();
	/**
	 * 删除产品信息
	 * @param userKey
	 * @param ptid
	 * @param prnm
	 * @return
	 */
	public boolean removePrice(String userKey, String ptid, String prnm);
	/**
	 * 修改保存产品信息
	 * @param userKey
	 * @param pdtinfo
	 * @return
	 */
	public boolean savePrice(String userKey, Pdtinfo pdtinfo);
	/**
	 * 货币对配置下拉框，及开闭式下拉框数据
	 * @return
	 */
	public List<Map<String, Object>> selectexnmprice();
	/**
	 * 货币对配置数据
	 * @param prod
	 * @return
	 */
	public List<PdtRParaT> selectallExnmPrice(String prod);
	/**
	 * 产品加工信息
	 * @return
	 */
	public List<PdtJGinfo> selectAllPmodPrice();
	public boolean deletePmodPdtinfo(String userKey, String ptid);
	public boolean addPmodPdtinfo(String userKey, PdtJGinfo bean);
	/**
	 * 交易开闭式-修改保存数据
	 * @param userKey
	 * @param ptidcomb
	 * @param usfg
	 * @return
	 */
	public boolean upUsfg(String userKey, String ptidcomb, String usfg);
	/**
	 * 根据产品下拉框 获取对应数据
	 * @param ptid
	 * @return
	 */
	public List<TradeOnOffBean> selectSysctls(String ptid);
}
