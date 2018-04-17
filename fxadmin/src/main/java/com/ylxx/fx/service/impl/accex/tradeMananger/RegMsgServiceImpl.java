package com.ylxx.fx.service.impl.accex.tradeMananger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.mapper.accex.tradeMananger.RegMsgMapper;
import com.ylxx.fx.service.accex.tradeMananger.IRegMsgService;
import com.ylxx.fx.service.po.AccRegmsgBean;
@Service("regMsgService")
public class RegMsgServiceImpl implements IRegMsgService{
	private static final Logger log = LoggerFactory.getLogger(RegMsgServiceImpl.class);
	@Resource
	private RegMsgMapper regMsgMapper;
	
	/**
	 * 查询账户外汇账户信息
	 * @param startDate
	 * @param endDate
	 * @param prcd
	 * @return
	 */
	public List<AccRegmsgBean> searchRegmsgList(String startDate, String endDate, String prcd){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null ;
		int mouth = 2;
		Calendar calendar = Calendar.getInstance();
		if(null != startDate && !"".equals(startDate)){
			if(null == endDate || "".equals(endDate)){
				try {
					date = sdf.parse(startDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				calendar.setTime(date);
				calendar.add(Calendar.MONTH, mouth);
				endDate = sdf.format(calendar.getTime());
			}
		}else {
			if(null != endDate && !"".equals(endDate)){
				try {
					date = sdf.parse(endDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar calRule = Calendar.getInstance();
				calRule.setTime(date);
				calRule.add(Calendar.MONTH, 0-mouth);
				startDate = sdf.format(calRule.getTime());
			}else{
				Calendar calToday = Calendar.getInstance();
				calToday.add(Calendar.MONTH, 0-mouth);
				startDate = sdf.format(calToday.getTime());
				endDate = sdf.format(new Date());
			}
		}
		List<AccRegmsgBean> list = null;
		try {
			list = regMsgMapper.searchRegmsgList(startDate, endDate, prcd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
		
	}
}
