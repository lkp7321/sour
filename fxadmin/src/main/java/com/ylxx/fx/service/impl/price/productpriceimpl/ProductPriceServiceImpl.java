package com.ylxx.fx.service.impl.price.productpriceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.mapper.price.product.PriceProductMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.PdtJGinfo;
import com.ylxx.fx.service.po.PdtRParaT;
import com.ylxx.fx.service.po.Pdtinfo;
import com.ylxx.fx.service.po.TradeOnOffBean;
import com.ylxx.fx.service.price.productprice.ProductPriceService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.UDPClient;
@Service("prodPriceService")
public class ProductPriceServiceImpl implements ProductPriceService{
	@Resource
	private PriceProductMapper priceProdMap;
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	private UDPClient UDPsocket = new UDPClient();
	private static final Logger log = LoggerFactory.getLogger(ProductPriceServiceImpl.class);
	
	/**
	 * 获取所有产品信息
	 */
	public List<Pdtinfo> selectAllPrice() {
		List<Pdtinfo> list=null;
		try {
			list = priceProdMap.selectAllPrice();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	/**
	 * 删除产品信息
	 */
	public boolean removePrice(String userKey, String ptid, String prnm) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tableName", ptid);
		params.put("sequName", prnm);
		Integer i =0;
		params.put("o_flag", i);
		params.put("o_msg", "");
		priceProdMap.dropPriceProd(params);
		String result = (String)params.get("o_msg");
		int rs = (Integer)params.get("o_flag");
		log.error("\n"+result+":\n"+rs);
		if(result.equals("0K")){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd()
					+ "删除产品信息,调用存储过程 DROPPDTTABLE 删除系列数据库表成功!时间:"
					+ DataTimeClass.getCurDateTime());
			int a = priceProdMap.deletePrice(ptid);
			if(a>0){
				UDPsocket.SendSocketPdtInfo();
				log.info("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ "删除产品信息,产品编号:" + ptid + ",产品简码:" + prnm + ",成功!时间:"
						+ DataTimeClass.getCurDateTime());
				Logfile loginfo = new Logfile();
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("产品信息");
				loginfo.setRemk("删除");
				loginfo.setVold("登录产品:" + curUser.getProd()
						+ "删除产品信息,产品编号:" + ptid + ",产品简码:" + prnm);
				loginfo.setVnew("成功");
				loginfo.setProd(curUser.getProd());
				logfileCmdService.insertLog(loginfo);
				return true;
			} else {
				log.error("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ "删除产品信息,产品编号:" + ptid + ",产品简码:" + prnm + ",失败!时间:"
						+ DataTimeClass.getCurDateTime());
				return false;
			}
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd()
					+ "删除产品信息,调用存储过程 DROPPDTTABLE 删除系列数据库表失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return false;
		}
	}

	@Override
	public boolean savePrice(String userKey, Pdtinfo pdtinfo) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		boolean flag = inupprice(pdtinfo);
		if(flag){
			UDPsocket.SendSocketPdtInfo();
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "添加(修改)产品信息,产品编号:"
					+ pdtinfo.getPtid() + ",产品简码:" + pdtinfo.getPrnm() + ",产品名称:"
					+ pdtinfo.getPtnm() + ",价格频率(秒):" + pdtinfo.getFrqy() + ",开通状态:"
					+ pdtinfo.getUsfg() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("产品信息");
			loginfo.setRemk("添加(修改)");
			loginfo.setVold("登录产品:" + curUser.getProd() + "添加(修改)产品信息,产品编号:"
					+ pdtinfo.getPtid() + ",产品简码:" + pdtinfo.getPrnm() + ",产品名称:"
					+ pdtinfo.getPtnm() + ",价格频率(秒):" + pdtinfo.getFrqy() + ",开通状态:"
					+ pdtinfo.getUsfg());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
		} else {
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "添加(修改)产品信息,产品编号:"
					+ pdtinfo.getPtid() + ",产品简码:" + pdtinfo.getPrnm() + ",产品名称:"
					+ pdtinfo.getPtnm() + ",价格频率(秒):" + pdtinfo.getFrqy() + ",开通状态:"
					+ pdtinfo.getUsfg() + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
		}
		return flag;
	}
	public boolean inupprice(Pdtinfo pdtinfo){
		int a = 0;
		boolean f = false;
		try {
			a=priceProdMap.selectPrice(pdtinfo.getPtid());
			if(a>0){
				int b = priceProdMap.updatePrice(pdtinfo);
				if(b>0){
					f = true;
				}
			}else{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("tableName", pdtinfo.getPtid());
				params.put("sequName", pdtinfo.getPrnm());
				Integer i =0;
				params.put("o_flag", i);
				params.put("o_msg", "");
				priceProdMap.createPriceProd(params);
				String res = (String)params.get("o_msg");
				if(res.equals("0K")){
					int x = priceProdMap.increated(pdtinfo);
					if(x>0){
						f = true;
					}else{
						f = false;
					}
				}else{
					f = false;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return f;
	}
	//货币对配置
	//下拉框
	public List<Map<String, Object>> selectexnmprice() {
		List<Map<String, Object>> list = null;
		try {
			list = priceProdMap.selectexnmprice();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	
	//所有数据
	public List<PdtRParaT> selectallExnmPrice(String prod) {
		List<PdtRParaT> list =null;
		try {
			list = priceProdMap.selectallExnmPrice(prod);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	
	public boolean deletePmodPdtinfo(String userKey, String ptid) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		try {
			a = priceProdMap.deletePmodPrice(ptid.trim());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (a>0) {
			UDPsocket.SendSocketPdtInfo();
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "删除产品加工信息,产品编号:" + ptid
					+ ",成功!时间:" + DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("产品加工信息");
			loginfo.setRemk("删除");
			loginfo.setVold("登录产品:" + curUser.getProd() + "删除产品加工信息,产品编号:"
					+ ptid);
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return true;
		} else {
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "删除产品加工信息,产品编号:" + ptid
					+ ",失败!时间:" + DataTimeClass.getCurDateTime());
			return false;
		}
	}

	/**
	 * 产品加工信息
	 */
	public List<PdtJGinfo> selectAllPmodPrice() {
		List<PdtJGinfo> list = null;
		try {
			list = priceProdMap.selectAllPmodPrice();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	//添加产品配置信息
	public boolean addPmodPdtinfo(String userKey, PdtJGinfo bean) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		boolean flag = inUpPmoddate(bean);
		if (flag) {
			UDPsocket.SendSocketPdtInfo();
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "添加(修改)产品加工信息,产品编号:"
					+ bean.getPtid() + ",产品类型:" + bean.getTransactflag()
					+ ",总分行价格池:" + bean.getBanckflag() + ",客户价格池:"
					+ bean.getCustflag() + ",历史价格表:" + bean.getHisflag()
					+ ",开通状态:" + bean.getUsfg() + ",备注:" + bean.getPrcd()
					+ ",成功!时间:" + DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("产品加工信息");
			loginfo.setRemk("添加(修改)");
			loginfo.setVold("登录产品:" + curUser.getProd() + "添加(修改)产品加工信息,产品编号:"
					+ bean.getPtid() + ",产品类型:" + bean.getTransactflag()
					+ ",总分行价格池:" + bean.getBanckflag() + ",客户价格池:"
					+ bean.getCustflag() + ",历史价格表:" + bean.getHisflag()
					+ ",开通状态:" + bean.getUsfg() + ",备注:" + bean.getPrcd());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
		} else {
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "添加(修改)产品加工信息,产品编号:"
					+ bean.getPtid() + ",产品类型:" + bean.getTransactflag()
					+ ",总分行价格池:" + bean.getBanckflag() + ",客户价格池:"
					+ bean.getCustflag() + ",历史价格表:" + bean.getHisflag()
					+ ",开通状态:" + bean.getUsfg() + ",备注:" + bean.getPrcd()
					+ ",失败!时间:" + DataTimeClass.getCurDateTime());
		}
		return flag;
	}
	public boolean inUpPmoddate(PdtJGinfo bean){
		try {
			int a = priceProdMap.selPmodPriceOn(bean.getPtid().trim());
			int b = 0;
			if(a>0){
				b = priceProdMap.updatePmodPrice(bean);
			}else{
				b = priceProdMap.insPmodPrice(bean);
			}
			if(b>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	
	/*
	 * 交易开闭市管理
	 */
	public boolean upUsfg(String userKey, String ptidcomb, String usfg) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		String time = DataTimeClass.getCurDateTime();
		try {
			a = priceProdMap.updateUsfg(curUser.getUsnm(), ptidcomb, usfg, time);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (a>0) {
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "修改开闭市,修改产品:" + ptidcomb
					+ ",开闭式状态:" + usfg + "成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("开闭市");
			loginfo.setRemk("修改");
			loginfo.setVold("登录产品:" + curUser.getProd() + "修改开闭市,修改产品:"
					+ ptidcomb + ",开闭式状态:" + usfg);
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return true;
		} else {
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "修改开闭市,修改产品:" + ptidcomb
					+ ",开闭式状态:" + usfg + "失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return false;
		}
	}

	/**
	 * 开闭式，通过产品查询数据
	 */
	public List<TradeOnOffBean> selectSysctls(String ptid) {
		List<TradeOnOffBean> list = null;
		try {
			list = priceProdMap.selectSysctls(ptid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
}