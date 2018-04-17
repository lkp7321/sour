package com.ylxx.fx.service.impl.accex.tradeMananger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.accex.tradeMananger.AccexPromotionListMapper;
import com.ylxx.fx.service.accex.tradeMananger.IAccexPromotionListService;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;

@Service("iaccexPromotionListService")
public class AccexPromotionListServiceImpl implements IAccexPromotionListService {

	@Resource
	private AccexPromotionListMapper accexPromotionListMapper;
	private static final Logger log = LoggerFactory.getLogger(AccexPromotionListServiceImpl.class);

	//分页查询
	public String selectPromotion(Integer pageNo, Integer pageSize, String endDate, String prod, String strateDate) {
		int mouth = 2;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		Date date = null ;
		if(null!=strateDate&&!"".equals(strateDate)){
			if(null==endDate||"".equals(endDate)){
				try {
					date = sdf.parse(strateDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				calendar.setTime(date);
				calendar.add(Calendar.MONTH, mouth);
				endDate = sdf.format(calendar.getTime());
			}
		}else {
			if(null!=endDate&&!"".equals(endDate)){
				try {
					date = sdf.parse(endDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar calRule = Calendar.getInstance();
				calRule.setTime(date);
				calRule.add(Calendar.MONTH, 0-mouth);
				strateDate = sdf.format(calRule.getTime());
			}else{
				Calendar calToday = Calendar.getInstance();
				calToday.add(Calendar.MONTH, 0-mouth);
				strateDate = sdf.format(calToday.getTime());
				endDate = sdf.format(new Date());
			}
		}
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		List<Map<String, Object>> list = null;
		try {
			list = accexPromotionListMapper.selectPromotion(strateDate,endDate,prod);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);	 
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), page);
	}		
	
	public List<Map<String, Object>> selectAllPromotion(String endDate, String prod, String strateDate) {
		int mouth = 2;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		Date date = null ;
		if(null!=strateDate&&!"".equals(strateDate)){
			if(null==endDate||"".equals(endDate)){
				try {
					date = sdf.parse(strateDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				calendar.setTime(date);
				calendar.add(Calendar.MONTH, mouth);
				endDate = sdf.format(calendar.getTime());
			}
		}else {
			if(null!=endDate&&!"".equals(endDate)){
				try {
					date = sdf.parse(endDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar calRule = Calendar.getInstance();
				calRule.setTime(date);
				calRule.add(Calendar.MONTH, 0-mouth);
				strateDate = sdf.format(calRule.getTime());
			}else{
				Calendar calToday = Calendar.getInstance();
				calToday.add(Calendar.MONTH, 0-mouth);
				strateDate = sdf.format(calToday.getTime());
				endDate = sdf.format(new Date());
			}
		}
		List<Map<String, Object>> list = null;
		try {
			list = accexPromotionListMapper.selectPromotion(strateDate,endDate,prod);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		return list;
	}		
}

