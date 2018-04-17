package com.ylxx.fx.service.impl.person.fxipmonitorimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.mapper.person.fxipmonitor.FxipTranListMapper;
import com.ylxx.fx.service.person.fxipmonitor.FxipTransListService;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;

@Service("fxipTransListService")
public class FxipTransListServiceImpl implements FxipTransListService{
	
	private static final Logger log = LoggerFactory.getLogger(FxipTransListServiceImpl.class);
	
	@Resource
	private FxipTranListMapper fxipTranListmap;
	
//	/**
//	 * 取得成交流水数据
//	 * @param User
//	 * @param String
//	 * @return list
//	 * */
//	public List<Tranlist> allTranList(CurrUser curUser, String time) {
//		List<Tranlist> list = null;
//		// lcno , trtm ,bycy,slcy,byam,slam,usam,cspc,fvda,CUAC,trdt
//		try {
//			list = fxipTranListmap.selectTran(curUser.getProd(), time);
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//		return list;
//	}

	/***
	 * 成交流水查询
	 * 
	 * @param userKey
	 *            取得当前产品编号
	 * @param bTime
	 *            开始时间
	 * @param eTime
	 *            结束时间
	 * @return list 当前时间段的所有交易
	 * **/
	public List<Tranlist> timeTranList(CurrUser curUser, String bTime, String eTime) {
		String curTime = DataTimeClass.getNowDay();
		List<Tranlist> list = null;
		try {
			if(bTime.equals(eTime) && (bTime.equals(curTime)||eTime.equals(curTime))){
				list = fxipTranListmap.selectTran(curUser.getProd(), curTime);
			}else{
				list =  fxipTranListmap.selectTimeTran(curUser.getProd(), bTime, eTime);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	/**
	 * 当天交易总数
	 * */
	public String todayTranCount(CurrUser curUser, String curTime) {
		int a = 0;
		try {
			a = fxipTranListmap.selectTimeCount(curUser.getProd(), curTime);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return String.valueOf(a);
	}

	/**
	 * 当天交易量
	 * */
	public String todayTranUsam(CurrUser curUser, String curTime) {
		String curCount = null;
		try {
			curCount = fxipTranListmap.selectUsamCount(curUser.getProd(), curTime);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return curCount;
	}
}
