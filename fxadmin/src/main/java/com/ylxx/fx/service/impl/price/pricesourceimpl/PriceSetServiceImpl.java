package com.ylxx.fx.service.impl.price.pricesourceimpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.domain.price.PriceJiaVo;
import com.ylxx.fx.core.mapper.price.pricesource.PriceJiaMapper;
import com.ylxx.fx.service.po.calendar.CalendarVO;
import com.ylxx.fx.service.price.pricesource.PriceSetService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.LoginUsers;
@Service("priceSetService")
public class PriceSetServiceImpl implements PriceSetService{
	@Resource
	private PriceJiaMapper priceJiaMap;
	
	private static final Logger log = LoggerFactory.getLogger(PriceSetServiceImpl.class);
	//获取页面数据
	public List<PriceJiaVo> getPriceJiaList() {
		
		return priceJiaMap.selPriceJiaList();
	}

	//获取产品列表
	public List<Map<String, String>> getProList() {
		return priceJiaMap.getProList();
	}

	//保存
	public boolean savePriceJia(String userKey, PriceJiaVo priceJiaBean) {
		CurrUser curUser= LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		try {
			a=priceJiaMap.savePriceJia(priceJiaBean.getPtid(),priceJiaBean.getCalendarID());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			log.info("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
					+ "\n添加" + curUser.getProd() + "产品价格加工日历规则  \n"
					+ "产品ID:"+priceJiaBean.getPtid()+"\n 日历ID:"+priceJiaBean.getCalendarID()+"\n" 
					+ "添加成功!\n时间:"
					+ DataTimeClass.getCurDateTime());
			return true;
		}else{
			log.error("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
					+ "\n添加" + curUser.getProd() + "产品价格加工日历规则  \n"
					+ "产品ID："+priceJiaBean.getPtid()+"\n 日历ID:"+priceJiaBean.getCalendarID()+"\n" 
					+ "添加失败!\n时间:"
					+ DataTimeClass.getCurDateTime());
			return false;
		}
	}

	//删除
	public boolean delPriceJia(String userKey, PriceJiaVo priceJiaBean) {
		CurrUser curUser= LoginUsers.getLoginUser().getCurrUser(userKey);
		int a=0;
		try {
			a = priceJiaMap.delPriceJia(priceJiaBean.getPtid(), priceJiaBean.getCalendarID());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			log.info("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
					+ "\n删除" + curUser.getProd() + "产品价格加工日历规则  \n"
					+ "产品ID:"+priceJiaBean.getPtid()+"\n 日历ID:"+priceJiaBean.getCalendarID()+"\n" 
					+ "删除成功!\n时间:"
					+ DataTimeClass.getCurDateTime());
			return true;
		}else{
			log.error("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
					+ "\n删除" + curUser.getProd() + "产品价格加工日历规则  \n"
					+ "产品ID："+priceJiaBean.getPtid()+"\n 日历ID:"+priceJiaBean.getCalendarID()+"\n" 
					+ "删除失败!\n时间:"
					+ DataTimeClass.getCurDateTime());
			return false;
		}
	}

	//更新
	public boolean upPriceJia(String userKey, PriceJiaVo priceJiaBean) {
		CurrUser curUser= LoginUsers.getLoginUser().getCurrUser(userKey);
		String priceRecPK=priceJiaBean.getPriceJiaPK();
		log.info("priceRecPK=="+priceRecPK);
		String []markpks=null;
		int a = 0;
		boolean flag = false;
		if(priceRecPK!=null&&!priceRecPK.equals("")){
			markpks=priceRecPK.split("&");
			log.info("交易产品："+markpks[0]+"日历规则："+markpks[1]);
			log.info("交易产品a："+priceJiaBean.getPtid()+"日历规则a："+priceJiaBean.getCalendarID());
			try {
				a = priceJiaMap.upPriceJia(priceJiaBean.getPtid(), priceJiaBean.getCalendarID(), markpks[0], markpks[1]);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if(a>0){
				log.info("用户：" + curUser.getUsnm() + " \n登录IP:"
						+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
						+ "\n更新" + curUser.getProd() + "产品价格加工日历规则  \n"
						+ "产品ID:"+priceJiaBean.getPtid()+"\n 日历ID:"+priceJiaBean.getCalendarID()+"\n" 
						+ "更新成功!\n时间:"
						+ DataTimeClass.getCurDateTime());
				flag = true;
			}else{
				log.error("用户：" + curUser.getUsnm() + " \n登录IP:"
						+ curUser.getCurIP() + " \n登录产品:" + curUser.getProd()
						+ "\n更新" + curUser.getProd() + "产品价格加工日历规则  \n"
						+ "产品ID："+priceJiaBean.getPtid()+"\n 日历ID:"+priceJiaBean.getCalendarID()+"\n" 
						+ "更新失败!\n时间:"
						+ DataTimeClass.getCurDateTime());
				flag = false;
			}
		}
		return flag;
	}
	
	public List<CalendarVO> getPriceJiacalRuleList(String userKey,String proid,String caltime){
		List<CalendarVO> calList=new ArrayList<CalendarVO>();
		CurrUser currUser=LoginUsers.getLoginUser().getCurrUser(userKey);
		DateFormat df=new SimpleDateFormat("yyyyMMdd");
		Date calDate = null;
		try {
			calDate=df.parse(caltime);
		} catch (ParseException e) {
			 log.error(e.getMessage(),e);
		
			return null;
		}
		Calendar beginCal=new GregorianCalendar();
		beginCal.setFirstDayOfWeek(Calendar.MONDAY);
		beginCal.setTime(calDate);
		int week_of_year=beginCal.get(Calendar.WEEK_OF_YEAR);
		Map<Integer ,CalendarVO> ruleMap=getRuleChart(proid,"",week_of_year);
		if(ruleMap.containsKey(1)){
			CalendarVO calendarVo=ruleMap.get(1);
			ruleMap.remove(1);
			ruleMap.put(8, calendarVo);
		}
		Set<Entry<Integer,CalendarVO>> entrySet=ruleMap.entrySet();
		Iterator<Entry<Integer,CalendarVO>> it=entrySet.iterator();
		while(it.hasNext()){
			Entry<Integer,CalendarVO> entry=it.next();
			int key=entry.getKey()-1;
			log.info("星期"+key);
			log.info("开始结束时间为："+entry.getValue().getBeginendtime());
			log.info("-----------------");
			String []timeArrays=entry.getValue().getBeginendtime().split("&");
			String []flags=entry.getValue().getFlags().split("&");
			for(int i=0;i<timeArrays.length;i++){
				String []beginendArrays=timeArrays[i].split("-");
				CalendarVO calendarVo=new CalendarVO();
				calendarVo.setWeek(String.valueOf(key));
				calendarVo.setBeginTime(beginendArrays[0]);
				calendarVo.setEndTime(beginendArrays[1]);
				String flag=flags[i].equals("0")?"开盘":"闭盘";
				calendarVo.setFlag(flag);
				calList.add(calendarVo);
			}
		}
		log.info("用户：" + currUser.getUsnm() + " \n登录IP:"
				+ currUser.getCurIP() + " \n登录产品:" + currUser.getProd()
				+ "交易日历规则个数为"+calList.size()+"!\n时间:"
				+ DataTimeClass.getCurDateTime());
		return calList;
	}
	
	public  Map<Integer ,CalendarVO> getRuleChart(String ptid,String levelType,int week_of_year){
		Map<Integer ,CalendarVO> ruleMap=new HashMap<Integer ,CalendarVO>();
		ruleMap=getThirdRule( ptid,week_of_year);
		ruleMap=getSecondRule( ptid);
		ruleMap=getFirstRule( ptid);
		return ruleMap;
	}
	//开始日期和结束日期都不在同一周内
	public Map<Integer ,CalendarVO> getThirdRule4(Date beginDate,Date endDate,Calendar beginCal,Calendar endCal,CalendarVO calendarVo){
		Map<Integer ,CalendarVO> ruleMap=new HashMap<Integer ,CalendarVO>();
		int  firstDayOfWeek =beginCal.getFirstDayOfWeek();
		int  endDayOfWeek=0;
		if(firstDayOfWeek==Calendar.MONDAY){
			endDayOfWeek=8;
		}else if(firstDayOfWeek==Calendar.SUNDAY){
			endDayOfWeek=7;
		}
		int interval=endDayOfWeek-firstDayOfWeek;
		for(int j=0;j<=interval;j++){
			CalendarVO calendarVo1=new  CalendarVO();
			calendarVo1.setBeginTime("00:00:00");
			calendarVo1.setEndTime("23:59:59");
			calendarVo.setBeginendtime(calendarVo1.getBeginTime()+"-"+calendarVo1.getEndTime());
			calendarVo.setFlags(calendarVo.getFlag());
			if(!ruleMap.containsKey(firstDayOfWeek+j)){
				calendarVo1.setBeginendtime(calendarVo.getBeginendtime());
				calendarVo1.setFlags(calendarVo.getFlags());
				ruleMap.put(firstDayOfWeek+j, calendarVo1);
			}else{
				CalendarVO cal=ruleMap.get(firstDayOfWeek+j);
				String oldtime=cal.getBeginendtime();
				String oldflag=cal.getFlags();
				calendarVo1.setBeginendtime(oldtime+"&"+calendarVo.getBeginendtime());
				calendarVo1.setFlags(oldflag+"&"+calendarVo.getFlags());
				ruleMap.put(firstDayOfWeek+j, calendarVo1);
			}
		}
		return ruleMap;
	}
	//开始日期和结束日期都在同一周内
	public Map<Integer ,CalendarVO> getThirdRule1(Date beginDate,Date endDate,Calendar beginCal,Calendar endCal,CalendarVO calendarVo){
		Map<Integer ,CalendarVO> ruleMap=new HashMap<Integer ,CalendarVO>();
		String quanflag=calendarVo.getIsquantian();
		int  firstDayOfWeek =beginCal.getFirstDayOfWeek();
		int beginDayOfWeek=beginCal.get(Calendar.DAY_OF_WEEK);
		int endDayOfWeek=endCal.get(Calendar.DAY_OF_WEEK);
		if(firstDayOfWeek==Calendar.MONDAY){
			if(beginDayOfWeek==Calendar.SUNDAY){
				beginDayOfWeek=8;
			}
			if(endDayOfWeek==Calendar.SUNDAY){
				endDayOfWeek=8;
			}
		}
		int interval=endDayOfWeek-beginDayOfWeek;
		if(interval>0){
			for(int j=0;j<=interval;j++){
				CalendarVO calendarVo1=new  CalendarVO();
				//如果是全天不用处理
				if(quanflag.equals("0")){
					//如果不是全天
					if(j==0){
						calendarVo1.setBeginTime(calendarVo.getBeginTime());
						calendarVo1.setEndTime("23:59:59");
					}else if(j==interval){
						calendarVo1.setBeginTime("00:00:00");
						calendarVo1.setEndTime(calendarVo.getEndTime());
					}else {
						calendarVo1.setBeginTime("00:00:00");
						calendarVo1.setEndTime("23:59:59");
					}
				}
				calendarVo.setBeginendtime(calendarVo1.getBeginTime()+"-"+calendarVo1.getEndTime());
				calendarVo.setFlags(calendarVo.getFlag());
				if(!ruleMap.containsKey(beginCal.get(Calendar.DAY_OF_WEEK)+j)){
					calendarVo1.setBeginendtime(calendarVo.getBeginendtime());
					calendarVo1.setFlags(calendarVo.getFlags());
					ruleMap.put(beginCal.get(Calendar.DAY_OF_WEEK)+j, calendarVo1);
				}else{
					CalendarVO cal=ruleMap.get(beginCal.get(Calendar.DAY_OF_WEEK)+j);
					String oldtime=cal.getBeginendtime();
					String oldflag=cal.getFlags();
					calendarVo1.setBeginendtime(oldtime+"&"+calendarVo.getBeginendtime());
					calendarVo1.setFlags(oldflag+"&"+calendarVo.getFlags());
					ruleMap.put(beginCal.get(Calendar.DAY_OF_WEEK)+j, calendarVo1);
				}
			}
		}else if(interval==0){
			CalendarVO calendarVo1=new  CalendarVO();
			calendarVo.setBeginendtime(calendarVo.getBeginTime()+"-"+calendarVo.getEndTime());
			calendarVo.setFlags(calendarVo.getFlag());
			if(!ruleMap.containsKey(beginCal.get(Calendar.DAY_OF_WEEK))){
				calendarVo1.setBeginendtime(calendarVo.getBeginendtime());
				calendarVo1.setFlags(calendarVo.getFlags());
				ruleMap.put(beginCal.get(Calendar.DAY_OF_WEEK), calendarVo1);
			}else{
				CalendarVO cal=ruleMap.get(beginCal.get(Calendar.DAY_OF_WEEK));
				String oldtime=cal.getBeginendtime();
				String oldflag=cal.getFlags();
				calendarVo1.setBeginendtime(oldtime+"&"+calendarVo.getBeginendtime());
				calendarVo1.setFlags(oldflag+"&"+calendarVo.getFlags());
				ruleMap.put(beginCal.get(Calendar.DAY_OF_WEEK), calendarVo1);
			}
		}
		/*if(ruleMap.containsKey(8)){
			CalendarVO tempCalVo=ruleMap.get(8);
			ruleMap.remove(8);
			ruleMap.put(1, tempCalVo);
		}*/
		return ruleMap;
	}
	//开始日期和结束日期不在同一周内,开始日期等于用户要求查询的日期周
	public Map<Integer ,CalendarVO> getThirdRule2(Date beginDate,Date endDate,Calendar beginCal,Calendar endCal,CalendarVO calendarVo){
		Map<Integer ,CalendarVO> ruleMap=new HashMap<Integer ,CalendarVO>();
		String quanflag=calendarVo.getIsquantian();
		int  firstDayOfWeek =beginCal.getFirstDayOfWeek();
		int beginDayOfWeek=beginCal.get(Calendar.DAY_OF_WEEK);
		int enddayOfWeek=0;
		if(firstDayOfWeek==Calendar.MONDAY){
			if(beginDayOfWeek==Calendar.SUNDAY){
				beginDayOfWeek=8;
			}
			enddayOfWeek=8;
		}else{
			enddayOfWeek=7;
		}
		int interval=enddayOfWeek-beginDayOfWeek;
		if(interval>0){
			for(int j=0;j<=interval;j++){
				CalendarVO calendarVo1=new  CalendarVO();
				//如果是全天不用处理
				if(quanflag.equals("0")){
					//如果不是全天
					if(j==0){
						calendarVo1.setBeginTime(calendarVo.getBeginTime());
						calendarVo1.setEndTime("23:59:59");
					}else {
						calendarVo1.setBeginTime("00:00:00");
						calendarVo1.setEndTime("23:59:59");
					}
				}
				calendarVo.setBeginendtime(calendarVo1.getBeginTime()+"-"+calendarVo1.getEndTime());
				calendarVo.setFlags(calendarVo.getFlag());
				if(!ruleMap.containsKey(beginDayOfWeek+j)){
					calendarVo1.setBeginendtime(calendarVo.getBeginendtime());
					calendarVo1.setFlags(calendarVo.getFlags());
					ruleMap.put(beginDayOfWeek+j, calendarVo1);
				}else{
					CalendarVO cal=ruleMap.get(beginDayOfWeek+j);
					String oldtime=cal.getBeginendtime();
					String oldflag=cal.getFlags();
					calendarVo1.setBeginendtime(oldtime+"&"+calendarVo.getBeginendtime());
					calendarVo1.setFlags(oldflag+"&"+calendarVo.getFlags());
					ruleMap.put(beginDayOfWeek+j, calendarVo1);
				}
			}
		}else if(interval==0){
			CalendarVO calendarVo1=new  CalendarVO();
			calendarVo1.setBeginTime(calendarVo.getBeginTime());
			calendarVo1.setEndTime("23:59:59");
			calendarVo.setBeginendtime(calendarVo1.getBeginTime()+"-"+calendarVo1.getEndTime());
			calendarVo.setFlags(calendarVo.getFlag());
			if(!ruleMap.containsKey(beginDayOfWeek)){
				calendarVo1.setBeginendtime(calendarVo.getBeginendtime());
				calendarVo1.setFlags(calendarVo.getFlags());
				ruleMap.put(beginDayOfWeek, calendarVo1);
			}else{
				CalendarVO cal=ruleMap.get(beginDayOfWeek);
				String oldtime=cal.getBeginendtime();
				String oldflag=cal.getFlags();
				calendarVo1.setBeginendtime(oldtime+"&"+calendarVo.getBeginendtime());
				calendarVo1.setFlags(oldflag+"&"+calendarVo.getFlags());
				ruleMap.put(beginDayOfWeek, calendarVo1);
			}
		}
		/*if(ruleMap.containsKey(8)){
			CalendarVO tempCalVo=ruleMap.get(8);
			ruleMap.remove(8);
			ruleMap.put(1, tempCalVo);
		}*/
		return ruleMap;
	}
	//开始日期和结束日期不在同一周内,结束日期等于用户要求查询的日期周
	public Map<Integer ,CalendarVO> getThirdRule3(Date beginDate,Date endDate,Calendar beginCal,Calendar endCal,CalendarVO calendarVo){
		Map<Integer ,CalendarVO> ruleMap=new HashMap<Integer ,CalendarVO>();
		String quanflag=calendarVo.getIsquantian();
		int  firstDayOfWeek =endCal.getFirstDayOfWeek();
		int  lastDayOfWeek=endCal.get(Calendar.DAY_OF_WEEK);
		if(firstDayOfWeek==Calendar.MONDAY){
			if(lastDayOfWeek==Calendar.SUNDAY){
				lastDayOfWeek=8;
			}
		}
		if(firstDayOfWeek<lastDayOfWeek){
			int interval=lastDayOfWeek-firstDayOfWeek;
			for(int j=0;j<=interval;j++){
				CalendarVO calendarVo1=new  CalendarVO();
				//如果是全天不用处理
				if(quanflag.equals("0")){
					//如果不是全天
					if(j==interval){
						calendarVo1.setBeginTime("00:00:00");
						calendarVo1.setEndTime(calendarVo.getEndTime());
					}else {
						calendarVo1.setBeginTime("00:00:00");
						calendarVo1.setEndTime("23:59:59");
					}
				}
				calendarVo.setBeginendtime(calendarVo1.getBeginTime()+"-"+calendarVo1.getEndTime());
				calendarVo.setFlags(calendarVo.getFlag());
				if(!ruleMap.containsKey(firstDayOfWeek+j)){
					calendarVo1.setBeginendtime(calendarVo.getBeginendtime());
					calendarVo1.setFlags(calendarVo.getFlags());
					ruleMap.put(firstDayOfWeek+j, calendarVo1);
				}else{
					CalendarVO cal=ruleMap.get(firstDayOfWeek+j);
					String oldtime=cal.getBeginendtime();
					String oldflag=cal.getFlags();
					calendarVo1.setBeginendtime(oldtime+"&"+calendarVo.getBeginendtime());
					calendarVo1.setFlags(oldflag+"&"+calendarVo.getFlags());
					ruleMap.put(firstDayOfWeek+j, calendarVo1);
				}
			}
		}else if(firstDayOfWeek==lastDayOfWeek){
			CalendarVO calendarVo1=new  CalendarVO();
			calendarVo1.setBeginTime("00:00:00");
			calendarVo1.setEndTime(calendarVo.getEndTime());
			calendarVo.setBeginendtime(calendarVo1.getBeginTime()+"-"+calendarVo1.getEndTime());
			calendarVo.setFlags(calendarVo.getFlag());
			if(!ruleMap.containsKey(firstDayOfWeek)){
				calendarVo1.setBeginendtime(calendarVo.getBeginendtime());
				calendarVo1.setFlags(calendarVo.getFlags());
				ruleMap.put(firstDayOfWeek, calendarVo1);
			}else{
				CalendarVO cal=ruleMap.get(firstDayOfWeek);
				String oldtime=cal.getBeginendtime();
				String oldflag=cal.getFlags();
				calendarVo1.setBeginendtime(oldtime+"&"+calendarVo.getBeginendtime());
				calendarVo1.setFlags(oldflag+"&"+calendarVo.getFlags());
				ruleMap.put(firstDayOfWeek, calendarVo1);
			}
		}
		/*if(ruleMap.containsKey(8)){
			CalendarVO tempCalVo=ruleMap.get(8);
			ruleMap.remove(8);
			ruleMap.put(1, tempCalVo);
		}*/
		return ruleMap;
	}
	
	//得到第三级规则等级
	public Map<Integer ,CalendarVO>  getThirdRule(String ptid,int week_of_year){
		Map<Integer ,CalendarVO> ruleMap=new HashMap<Integer ,CalendarVO>();
		DateFormat df=new SimpleDateFormat("yyyyMMdd");
		Date beginDate=null;
		Date endDate=null;
		List<CalendarVO> thirdCalRuleList=getRule( ptid, "3");
		log.info("第三级规则等级个数为："+thirdCalRuleList.size());
		if(thirdCalRuleList!=null&&thirdCalRuleList.size()>0){
			for(int i=0;i<thirdCalRuleList.size();i++){
				CalendarVO calendarVo=thirdCalRuleList.get(i);
				try {
					beginDate=df.parse(calendarVo.getBeginDate());
					endDate=df.parse(calendarVo.getEndDate());
				} catch (ParseException e) {
					log.error(e.getMessage(),e);
				}
				Calendar beginCal=new GregorianCalendar();
				Calendar endCal=new GregorianCalendar();
				beginCal.setFirstDayOfWeek(Calendar.MONDAY);
				beginCal.setTime(beginDate);
				endCal.setFirstDayOfWeek(Calendar.MONDAY);
				endCal.setTime(endDate);
				
				if(beginCal.get(Calendar.WEEK_OF_YEAR)==week_of_year&&endCal.get(Calendar.WEEK_OF_YEAR)==week_of_year){
					//开始日期和结束日期都在同一周内
					ruleMap=getThirdRule1( beginDate, endDate, beginCal, endCal, calendarVo);
				}else if(beginCal.get(Calendar.WEEK_OF_YEAR)==week_of_year){
					//开始日期和结束日期不在同一周内,开始日期等于用户要求查询的日期周
					ruleMap=getThirdRule2( beginDate, endDate, beginCal, endCal, calendarVo);	
				}else if(endCal.get(Calendar.WEEK_OF_YEAR)==week_of_year){
					//开始日期和结束日期不在同一周内,结束日期等于用户要求查询的日期周
					ruleMap=getThirdRule3( beginDate, endDate, beginCal, endCal, calendarVo);
				}else if(beginCal.get(Calendar.WEEK_OF_YEAR)<week_of_year&&week_of_year<endCal.get(Calendar.WEEK_OF_YEAR)){
					//开始日期和结束日期都不在所要选择的那一周内
					ruleMap=getThirdRule4( beginDate, endDate, beginCal, endCal, calendarVo);
				}
			}
			if(ruleMap.containsKey(8)){
				CalendarVO tempCalVo=ruleMap.get(8);
				ruleMap.remove(8);
				ruleMap.put(1, tempCalVo);
			}
		}
		
		return ruleMap;
	}
	//得到第二级规则等级
	public Map<Integer ,CalendarVO>  getSecondRule(String ptid){
		Map<Integer ,CalendarVO> ruleMap=new HashMap<Integer ,CalendarVO>();
		List<CalendarVO> secondCalRuleList=getRule( ptid, "2");
		log.info("第二级规则等级个数为："+secondCalRuleList.size());
		if(secondCalRuleList!=null&&secondCalRuleList.size()>0){
			List<Integer> addList=new ArrayList<Integer>();
			for(int i=0;i<secondCalRuleList.size();i++){
				CalendarVO calendarVo=secondCalRuleList.get(i);
				String beginWeek=calendarVo.getBeginWeek();
				String endWeek=calendarVo.getEndWeek();
				String quanflag=calendarVo.getIsquantian();
				if(endWeek.equals("0")){
					endWeek="7";
				}
				if(beginWeek.equals("0")){
					beginWeek="7";
				}
				int interval=Integer.valueOf(endWeek).intValue()-Integer.valueOf(beginWeek).intValue();
				
				if(interval>0){
					for(int z=0;z<=interval;z++){
						CalendarVO calendarVo1=new  CalendarVO();
						int week=Integer.valueOf(beginWeek).intValue()+1+z;
						//如果是全天不用处理
						if(quanflag.equals("0")){
							//如果不是全天
							if(z==0){
								calendarVo1.setBeginTime(calendarVo.getBeginTime());
								calendarVo1.setEndTime("23:59:59");
							}else if(z==interval){
								calendarVo1.setBeginTime("00:00:00");
								calendarVo1.setEndTime(calendarVo.getEndTime());
							}else {
								calendarVo1.setBeginTime("00:00:00");
								calendarVo1.setEndTime("23:59:59");
							}
						}
						calendarVo.setBeginendtime(calendarVo1.getBeginTime()+"-"+calendarVo1.getEndTime());
						calendarVo.setFlags(calendarVo.getFlag());
						if(addList!=null&&addList.size()>0){
							for(int m=0;m<addList.size();m++){
								if(week==addList.get(m)){
									CalendarVO cal=ruleMap.get(addList.get(m));
									String oldtime=cal.getBeginendtime();
									String oldflag=cal.getFlags();
									calendarVo1.setBeginendtime(oldtime+"&"+calendarVo.getBeginendtime());
									calendarVo1.setFlags(oldflag+"&"+calendarVo.getFlags());
									ruleMap.put(addList.get(m), calendarVo1);
								}
							}
						}
						if(!ruleMap.containsKey(week)){
							calendarVo1.setBeginendtime(calendarVo.getBeginendtime());
							calendarVo1.setFlags(calendarVo.getFlags());
							ruleMap.put(week, calendarVo1);
							addList.add(week);
						}
					}
				}else if(interval==0){
					CalendarVO calendarVo1=new  CalendarVO();
					int week=Integer.valueOf(beginWeek).intValue()+1;
					calendarVo.setBeginendtime(calendarVo.getBeginTime()+"-"+calendarVo.getEndTime());
					calendarVo.setFlags(calendarVo.getFlag());
					if(addList!=null&&addList.size()>0){
						for(int m=0;m<addList.size();m++){
							if(week==addList.get(m)){
								CalendarVO cal=ruleMap.get(addList.get(m));
								String oldtime=cal.getBeginendtime();
								String oldflag=cal.getFlags();
								calendarVo1.setBeginendtime(oldtime+"&"+calendarVo.getBeginendtime());
								calendarVo1.setFlags(oldflag+"&"+calendarVo.getFlags());
								ruleMap.put(addList.get(m), calendarVo1);
							}
						}
					}
					if(!ruleMap.containsKey(week)){
						calendarVo1.setBeginendtime(calendarVo.getBeginendtime());
						calendarVo1.setFlags(calendarVo.getFlags());
						ruleMap.put(week, calendarVo1);
						addList.add(week);
					}
				}
			}
			if(ruleMap.containsKey(8)){
				CalendarVO tempCalVo=ruleMap.get(8);
				ruleMap.remove(8);
				ruleMap.put(1, tempCalVo);
			}
			log.info("二级addList长度为："+addList.size());
			for(int h=0;h<addList.size();h++){
				log.info("二级"+addList.get(h));
			}
		}
		return ruleMap;
	}
	//得到第一级规则等级
	public Map<Integer ,CalendarVO>  getFirstRule(String ptid){
		Map<Integer ,CalendarVO> ruleMap=new HashMap<Integer ,CalendarVO>();
		List<CalendarVO> firstCalRuleList=getRule( ptid, "1");
		log.info("第一级规则等级个数为："+firstCalRuleList.size());
		if(firstCalRuleList!=null&&firstCalRuleList.size()>0){
			List<Integer> addList=new ArrayList<Integer>();
			for(int i=0;i<firstCalRuleList.size();i++){
				CalendarVO calendarVo=firstCalRuleList.get(i);
				String beginWeek=calendarVo.getBeginWeek();
				String endWeek=calendarVo.getEndWeek();
				if(endWeek.equals("0")){
					endWeek="7";
				}
				if(beginWeek.equals("0")){
					beginWeek="7";
				}
				int interval=Integer.valueOf(endWeek).intValue()-Integer.valueOf(beginWeek).intValue();
				if(interval>0){
					for(int z=0;z<=interval;z++){
						CalendarVO calendarVo1=new  CalendarVO();
						int week=Integer.valueOf(beginWeek).intValue()+1+z;
						calendarVo.setBeginendtime(calendarVo.getBeginTime()+"-"+calendarVo.getEndTime());
						calendarVo.setFlags(calendarVo.getFlag());
						if(addList!=null&&addList.size()>0){
							for(int m=0;m<addList.size();m++){
								if(week==addList.get(m)){
									log.info("周"+week+"是否与周"+addList.get(m)+(week==addList.get(m)));
									CalendarVO cal=ruleMap.get(addList.get(m));
									String oldtime=cal.getBeginendtime();
									String oldflag=cal.getFlags();
									log.info("周"+week+"现在时间："+calendarVo.getBeginendtime());
									calendarVo1.setBeginendtime(oldtime+"&"+calendarVo.getBeginendtime());
									calendarVo1.setFlags(oldflag+"&"+calendarVo.getFlags());
									ruleMap.put(addList.get(m), calendarVo1);
								}
							}
						}
						if(!ruleMap.containsKey(week)){
							calendarVo1.setBeginendtime(calendarVo.getBeginendtime());
							calendarVo1.setFlags(calendarVo.getFlags());
							ruleMap.put(week, calendarVo1);
							addList.add(week);
						}
					}
				}else if(interval==0) {
					CalendarVO calendarVo1=new  CalendarVO();
					int week=Integer.valueOf(beginWeek).intValue()+1;
					calendarVo.setBeginendtime(calendarVo.getBeginTime()+"-"+calendarVo.getEndTime());
					calendarVo.setFlags(calendarVo.getFlag());
					if(addList!=null&&addList.size()>0){
						for(int m=0;m<addList.size();m++){
							if(week==addList.get(m)){
								log.info("ruleMap包含："+addList.get(m));
								CalendarVO cal=ruleMap.get(addList.get(m));
								String oldtime=cal.getBeginendtime();
								String oldflag=cal.getFlags();
								log.info("新的星期时间为："+calendarVo.getBeginendtime());
								calendarVo1.setBeginendtime(oldtime+"&"+calendarVo.getBeginendtime());
								calendarVo1.setFlags(oldflag+"&"+calendarVo.getFlags());
								ruleMap.put(addList.get(m), calendarVo1);
							}
						}
					}
					if(!ruleMap.containsKey(week)){
						calendarVo1.setBeginendtime(calendarVo.getBeginendtime());
						calendarVo1.setFlags(calendarVo.getFlags());
						ruleMap.put(week, calendarVo1);
						addList.add(week);
					}
				}
			}
			if(ruleMap.containsKey(8)){
				CalendarVO tempCalVo=ruleMap.get(8);
				ruleMap.remove(8);
				ruleMap.put(1, tempCalVo);
			}
			log.info("一级addList长度为："+addList.size());
			for(int h=0;h<addList.size();h++){
				log.info("一级"+addList.get(h));
			}
		}
		Iterator<Integer> it=ruleMap.keySet().iterator();
		log.info("------------------------");
		while(it.hasNext()){
			int ggh=it.next();
			log.info("key==="+ggh+"=====>>>>value=="+ruleMap.get(ggh).getBeginendtime());
		}
		log.info("------------------------");
		return ruleMap;
	}
	
	public  List<CalendarVO> getRule(String ptid,String levelType){
		List<CalendarVO> calendarRuleList=new ArrayList<CalendarVO>();
		CalendarVO calendarVo=null;
		List<CalendarVO> list = null;
		try {
			list = priceJiaMap.selcalPricesetRule(ptid, levelType);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					calendarVo=new CalendarVO();
					calendarVo.setCalendarID(list.get(i).getCalendarID());
					calendarVo.setCalendarName(list.get(i).getCalendarName());
					if(levelType.equals("1")){
						String weekStart=list.get(i).getBeginWeek();
						String weekEnd=list.get(i).getEndWeek();
						calendarVo.setBeginWeek(weekStart);
						calendarVo.setEndWeek(weekEnd);
					}else if(levelType.equals("2")){
						String weekStart=list.get(i).getBeginWeek();
						String weekEnd=list.get(i).getEndWeek();
						calendarVo.setBeginWeek(weekStart);
						calendarVo.setEndWeek(weekEnd);
					}else if(levelType.equals("3")){
						calendarVo.setBeginDate(list.get(i).getBeginDate());
						calendarVo.setEndDate(list.get(i).getEndDate());
					}
					calendarVo.setBeginTime(list.get(i).getBeginTime());
					calendarVo.setEndTime(list.get(i).getEndTime());
					String level=list.get(i).getLevelType();
					calendarVo.setLevelType(level);
					calendarVo.setFlag(list.get(i).getFlag().trim());
					String quantianflag=list.get(i).getIsquantian();
					calendarVo.setIsquantian(quantianflag);
					calendarRuleList.add(calendarVo);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return calendarRuleList;
	} 
}
