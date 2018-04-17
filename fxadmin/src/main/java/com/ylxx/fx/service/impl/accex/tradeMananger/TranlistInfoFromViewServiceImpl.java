package com.ylxx.fx.service.impl.accex.tradeMananger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.mapper.accex.tradeMananger.TranlistInfoFromViewMapper;
import com.ylxx.fx.service.accex.tradeMananger.ITranlistInfoFromViewService;
import com.ylxx.fx.service.po.ViewDoneDetailBean;
@Service("tranlistInfoFromViewService")
public class TranlistInfoFromViewServiceImpl implements ITranlistInfoFromViewService{
	private static final Logger log = LoggerFactory.getLogger(TranlistInfoFromViewServiceImpl.class);
	@Resource
	private TranlistInfoFromViewMapper tranlistInfoFromViewMapper;
	
	/**
	 * 流水表视图 VIEW_DONEDETAIL_P007
	 * @param startDate
	 * @param endDate
	 * @param prod
	 * @param multiple
	 * @return
	 */
	public List<ViewDoneDetailBean> getTranlistInfoFromView(String startDate, String endDate, String prod, Integer multiple){
		List<ViewDoneDetailBean> detailList = tranlistInfoFromViewMapper.getTranlistInfoFromView(startDate, endDate, prod, multiple);
		BigDecimal totalAmat = BigDecimal.ZERO ;
		if(detailList != null && detailList.size() > 0){
			for(ViewDoneDetailBean viewDoneDetailBean :detailList){
				totalAmat = totalAmat.add(viewDoneDetailBean.getUsam());
			}
			ViewDoneDetailBean tb = new ViewDoneDetailBean();
			tb.setUsam(totalAmat);
			detailList.add(0,tb);
		}
		return detailList;
	}
	
	/**
	 * 贵金属流水查询
	 * @param startDate
	 * @param endDate
	 * @param prod
	 * @return
	 */
	public List<ViewDoneDetailBean> getGJSTranlist(String startDate, String endDate, String prod){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = null ;
		int mouth = 2;
		Calendar calendar = Calendar.getInstance();
		if(null!=startDate&&!"".equals(startDate)){
			if(null==endDate||"".equals(endDate)){
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
			if(null!=endDate&&!"".equals(endDate)){
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
		return tranlistInfoFromViewMapper.getGJSTranlist(startDate, endDate, prod);
	}
}
