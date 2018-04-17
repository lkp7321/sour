package com.ylxx.fx.service.impl.person.systemimpl;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ylxx.fx.core.mapper.person.system.PriceCheckMapper;
import com.ylxx.fx.service.person.system.PriceCheckService;
import com.ylxx.fx.service.po.PdtCust;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCodeSystem;
import com.ylxx.fx.utils.ResultDomain;
@Service("priceCheckService")
public class PriceCheckServiceImpl implements PriceCheckService{
	@Resource
	private PriceCheckMapper priceCheckMap;
	private List<PdtCust> list = null;
	private List<String> list1 = null;
	private String priceStat;
	private static final Logger LOGGER = LoggerFactory.getLogger(PriceCheckServiceImpl.class);
	//获取所有
	public String allPriceCheck(CurrUser curUser){
		try {
			list = priceCheckMap.allCustPrice(curUser.getProd());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setStfg(list.get(i).getStfg().equals("0")?"开盘":"停盘");
			}
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_0.getCode(), null);
		}
	}
	
	//开启价格校验器
	public boolean opPriceStat(CurrUser curUser){
		boolean flag = false;
		try {
			int a = priceCheckMap.opPriceStat();
			if(a>0){
				flag = true;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return false;
		}
		return flag;
	}
	//关闭
	public boolean closePriceStat(CurrUser curUser){
		boolean flag = false;
		try {
			int a = priceCheckMap.closePriceStat();
			if(a>0){
				flag = true;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return false;
		}
		return flag;
	}
	
	// 开启产品校验器
	public boolean updatePriceStat(CurrUser curUser){
		boolean flag = false;
		try {
			int a = priceCheckMap.upStatPrice(curUser.getProd());
			if(a>0){
				flag = true;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return false;
		}
		return flag;
	}
	
	
	public List<String> allMkid(String prod){
		try {
			list1 = priceCheckMap.allMkid(prod);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		return list1;
	}
	// 刷新价格源校验器1
	public boolean refshPrice(CurrUser curUser,String prod){
		boolean flag = false;
		int a = 0;
		list1 = allMkid(prod);
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				try {
					a = priceCheckMap.refshUp(list1.get(i));
				} catch (Exception e) {
					LOGGER.error(e.getMessage(),e);
					return false;
				}
				if(a>0){
					flag = true;
				}
			}
		}
		return flag;
	}
	
	public List<String> allMkidNo(){
		try {
			list1 = priceCheckMap.allMkidNo();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		return list1;
	}	
	//刷新价格源校验器
	public boolean refshPrice(CurrUser curUser){
		boolean flag = false;
		int a = 0;
		list1 = allMkidNo();
		if(list1!=null&&list1.size()>0){
			for (int i = 0; i < list1.size(); i++) {
				try {
					a = priceCheckMap.refshUp1(list1.get(i));
					if(a>0){
						flag = true;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(),e);
					return false;
				}
			}
		}
		return flag;
	}
	
	// 刷新外汇实盘校验器
	public boolean refshSptPrice(CurrUser curUser){
		boolean flag = false;
		try {
			int a = priceCheckMap.refshSptPrice();
			if(a>0){
				flag = true;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return false;
		}
		return flag;
	}
	
	// 刷新价格源校验器2
	public boolean refshGoldPrice(CurrUser curUser, String prod) {
		boolean flag = false;
		int a = 0;
		list1 = allMkid(prod);
		if(list1!=null&&list1.size()>0){
			for (int i = 0; i < list1.size(); i++) {
				try {
					a = priceCheckMap.refshGoldPrice(list1.get(i));
					if(a>0){
						flag = true;
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(),e);
					return false;
				}
			}
		}
		return flag;
	}
	
	// 刷新纸黄金校验器
	public boolean refshGoldsPrice(CurrUser curUser){
		boolean f = false;
		int a = 0;
		try {
			a = priceCheckMap.refshGoldsPrice();
			if(a>0){
				f = true;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			return false;
		}
		return f;
	}
	
	//定时刷新价格源校验器
	public boolean refshPrices(CurrUser curUser) {
		boolean flag = false;
		try{
			flag = refshPrice(curUser);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			LOGGER.error("定时刷新价格源校验器出现异常！");
			return false;
		}
		if(flag){
			LOGGER.info("定时刷新价格源校验器成功！");
		}else {
			LOGGER.info("定时刷新价格源校验器失败！");
		}
		return flag;
	}
	
	//===================
	public List<String> selectPrice(){
		try {
			list1 = priceCheckMap.selectPrice();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return list1;
	}
	
	//
	public boolean refshCustPrice(String ptid){
		boolean flag = false;
		try {
			int a = priceCheckMap.refshCustPrice(ptid);
			if(a>0){
				flag = true;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
		}
		return flag;
	}
	
	//新增根据客户价刷新产品校验器
	public boolean refshCustPrices(CurrUser curUser,String prod){
		boolean flag = false;
		try {
			int a = priceCheckMap.refshCustPrice(prod);
			if(a>0){
				flag = true;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			flag = false;
		}
		if(flag){
			LOGGER.info("根据客户价刷新产品校验器成功");
		}else{
			LOGGER.info("根据客户价刷新产品校验器失败");
		}
		return flag;
	}
	
	//刷新账户外汇产品校验器
	public boolean refshCustPriceForAcc(String prod){
		boolean flag = false;
		try {
			int a = priceCheckMap.refshCustPriceForAcc(prod);
			if(a>0){
				flag = true;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			flag = false;
		}
		return flag;
	}
	//定时刷新产品校验器
	public boolean refshPdtPrice() {
		boolean flag =false;
		list1 = selectPrice();
		if (list1 != null&&list1.size() > 0) {
			for (int i = 0; i < list1.size(); i++) {
				String ptid = list1.get(i);
				flag = refshCustPrice(ptid);
			}
		}
		boolean flagForAcc = false;
		flagForAcc = refshCustPriceForAcc("P007");//刷新账户外汇产品校验器
		if(flagForAcc){
			LOGGER.info("刷新账户外汇产品校验器成功！");
		}else{
			LOGGER.info("刷新账户外汇产品校验器失败！");
		}
		if(flag){
			LOGGER.info("定时刷新产品校验器成功！");
		}else {
			LOGGER.info("定时刷新产品校验器失败！");
		}
		return flag;
	}
	
	//得到价格源校验器状态
	public String getchkpara(){
		int a = 0;
		try {
			a = priceCheckMap.getchkpara();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		if(a>0){
			priceStat = "开启";
		}else{
			priceStat = "关闭";
		}
		return priceStat;
	}
}
