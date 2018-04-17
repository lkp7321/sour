package com.ylxx.fx.service.impl.person.businessparaimpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.calendar.OriginalVO;
import com.ylxx.fx.core.mapper.person.businesspara.TradeProCalendarMapper;
import com.ylxx.fx.service.person.businesspara.TradeProCalendarService;
import com.ylxx.fx.service.po.calendar.CalendarVO;
import com.ylxx.fx.service.po.calendar.TradeCodeVO;
import com.ylxx.fx.service.po.calendar.TradeProCalVO;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.calendar.FirstComparator;
import com.ylxx.fx.utils.calendar.SecondComparator;
import com.ylxx.fx.utils.calendar.ThirdComparator;
@Service("tradeProCalendarService")
public class TradeProCalendarServiceImpl implements TradeProCalendarService {
	private static final Logger log = LoggerFactory.getLogger(TradeProCalendarServiceImpl.class);
	@Resource
	private TradeProCalendarMapper tradeProCalendarMap;
	
	//查询所有
	public PageInfo<TradeProCalVO> getAllProCalendarVo(String ip,
			String prod, String prod1, String prod2,
			String calendarID, String levelTy,
			Integer pageNo, Integer pageSize) {
		System.out.println(ip+" ip ");
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    List<TradeProCalVO> list = null;
	    try {
	    	list = tradeProCalendarMap.selectProCalender(
	    			prod, prod1, prod2, 
	    			calendarID, levelTy);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	    //用PageInfo对结果进行包装
	    PageInfo<TradeProCalVO> page = new PageInfo<TradeProCalVO>(list);
		return page;
	}
	
	/*
	 * 删除
	 * @see com.ylxx.fx.service.person.businesspara.TradeProCalendarService#delTradeProRule(com.ylxx.fx.service.po.User, com.ylxx.fx.service.po.calendar.TradeProCalVO, java.lang.String)
	 */
	public boolean delTradeProRule(CurrUser curUser, TradeProCalVO tradeProCalVo,String ip){
		tradeProCalVo.setPtid(curUser.getProd());
		int a = 0;
		boolean flag =false;
		try {
			a = tradeProCalendarMap.deleteTradeProRule(tradeProCalVo);
			if(a>0){
				flag = true;
			}else{
				flag = false;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag = false;
		}
		if(flag){
			log.info("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ ip + " \n登录产品:" + curUser.getProd()
					+ "\n删除" + curUser.getProd() + "产品日历规则  \n产品ID："+tradeProCalVo.getPtid()+ "\n "
					+ "交易码:"+tradeProCalVo.getTrcd()+"\n 日历ID:"+tradeProCalVo.getCalendarID()+"\n" 
					+ "删除成功!\n时间:"
					+ DataTimeClass.getCurDateTime());
		}else{
			log.error("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ ip + " \n登录产品:" + curUser.getProd()
					+ "\n删除" + curUser.getProd() + "产品日历规则  \n产品ID："+tradeProCalVo.getPtid()+ "\n "
					+ "交易码:"+tradeProCalVo.getTrcd()+"\n 日历ID:"+tradeProCalVo.getCalendarID()+"\n" 
					+ "删除失败!\n时间:"
					+ DataTimeClass.getCurDateTime());
		}
		return flag;
	}
	
	//校验1
	public boolean checkoutCalRule(CurrUser curUser,List<OriginalVO> calList,String ip){
		log.info("校验日历规则");
		boolean flag = false;
		try {
			flag = checkoutRule(calList);
		} catch (Exception e) {
			 log.error(e.getMessage(),e);
		}
		if(flag){
			log.info("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ ip + " \n登录产品:" + curUser.getProd()
					+ "\n校验" + curUser.getProd() + "产品日历规则  \n"
					+ "校验日历规则成功!\n时间:"
					+ DataTimeClass.getCurDateTime());
		}else{
			log.error("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ ip + " \n登录产品:" + curUser.getProd()
					+ "\n校验" + curUser.getProd() + "产品日历规则  \n"
					+ "校验日历规则失败!\n时间:"
					+ DataTimeClass.getCurDateTime());
		}
		return flag;
	}
	
	//校验2
	public boolean checkoutRule(List<OriginalVO> procalList) throws Exception{
		boolean flag=true;
		//String procals="";
		StringBuffer strbuff=new StringBuffer();
		for(int i=0;i<procalList.size();i++){
			if(i==0){
				//procals=procals+procalList.get(i).getCalendarID();
				strbuff.append(procalList.get(i).getCalendarID());
			}else{
				//procals=procals+","+procalList.get(i).getCalendarID();
				strbuff.append(",").append(procalList.get(i).getCalendarID());
			}
		}
		
		List<CalendarVO> calList=getCalenderRuleList(strbuff.toString());
		List<CalendarVO> firstList=new ArrayList<CalendarVO>();
		List<CalendarVO> secondList=new ArrayList<CalendarVO>();
		List<CalendarVO> thirdList=new ArrayList<CalendarVO>();
		for(CalendarVO calendarVo:calList){
			String levelType=calendarVo.getLevelType();
			if(levelType.equals("1")){
				firstList.add(calendarVo);
			}else if(levelType.equals("2")){
				secondList.add(calendarVo);
			}else if(levelType.equals("3")){
				thirdList.add(calendarVo);
			}
		}
		DateFormat df=new SimpleDateFormat("HH:mm:ss");
		//第一等级
		Collections.sort(firstList, new FirstComparator());
		log.info("第一等级的个数"+firstList.size());
		log.info("第二等级的个数"+secondList.size());
		log.info("第三等级的个数"+thirdList.size());
		for(int i=0;i<firstList.size();i++){
			log.info("按照起始时间排序："+firstList.get(i).getBeginTime());
		}
		for(int j=0;j<7;j++){
			List<CalendarVO> weekList=new ArrayList<CalendarVO>();
			for(CalendarVO calendarVo:firstList){
				String beginWeek=calendarVo.getBeginWeek();
				String endWeek=calendarVo.getEndWeek(); 
				int weekinterval=Integer.valueOf(endWeek)-Integer.valueOf(beginWeek);
				if(weekinterval>=0){
					for(int i=0;i<=weekinterval;i++){
						if((Integer.valueOf(beginWeek)+i)==j){
							weekList.add(calendarVo);
						}
					}
				}else if(weekinterval<0){
					return false;
				}
			}
			if(weekList.size()>1){
				Collections.sort(weekList, new FirstComparator());
				for(int i=0;i<weekList.size()-1;i++){
					//后一个开始时间在前一个结束时间之后则不交叉，其他都视为交叉情况
					Date backTime=df.parse(weekList.get(i+1).getBeginTime());
					Date frontTime=df.parse(weekList.get(i).getEndTime());
					if(!backTime.after(frontTime)){
						return false;
					}
					//如果开盘闭盘标识不一样
					if(!weekList.get(i).getFlag().equals(weekList.get(i+1).getFlag())){
						return false;
					}
					flag=true;
				}
			}
		}
		//第二等级
		if(secondList.size()>1){
			//首先将周日的0改成7
			for(int i=0;i<secondList.size();i++){
				CalendarVO calendarVo=secondList.get(i);
				if(calendarVo.getBeginWeek().equals("0")){
					calendarVo.setBeginWeek("7");
				}
				if(calendarVo.getEndWeek().equals("0")){
					calendarVo.setEndWeek("7");
				}
			}
			Collections.sort(secondList, new SecondComparator());
			log.info("第二等级的个数"+secondList.size());
			for(int i=0;i<secondList.size();i++){
				log.info("按照起始星期排序："+secondList.get(i).getBeginWeek()+"|||按照起始时间排序："+secondList.get(i).getBeginTime());
			}
			for(int i=0;i<secondList.size();i++){
				CalendarVO calendarVo=secondList.get(i);
				String beginWeek=calendarVo.getBeginWeek();
				String endWeek=calendarVo.getEndWeek();
				String endTime=calendarVo.getEndTime();
				Date endDate=df.parse(endTime);
				for(int j=i+1;j<secondList.size();j++){
					CalendarVO calendarVo1=secondList.get(j);
					String beginWeek1=calendarVo1.getBeginWeek();
					String endWeek1=calendarVo1.getEndWeek();
					String beginTime1=calendarVo1.getBeginTime();
					Date beginDate1=df.parse(beginTime1);
					//后一个开始星期在前一个开始星期和结束星期之间
					if(Integer.valueOf(beginWeek1).intValue()>Integer.valueOf(beginWeek).intValue()&&Integer.valueOf(beginWeek1).intValue()<Integer.valueOf(endWeek).intValue()){
						return  false;
					}else if(Integer.valueOf(beginWeek1).intValue()==Integer.valueOf(beginWeek).intValue()){
						//后一个开始星期等于前一个开始星期
						//后一个结束星期等于前一个结束星期
						if(Integer.valueOf(endWeek1).intValue()==Integer.valueOf(endWeek).intValue()){
							if(!beginDate1.after(endDate)){
								return false;
							}
						}else if(Integer.valueOf(endWeek1).intValue()>Integer.valueOf(endWeek).intValue()) {
							//后一个结束星期大于前一个结束星期
							//前一个星期周期为一天
							if(Integer.valueOf(beginWeek).intValue()==Integer.valueOf(endWeek).intValue()){
								if(!beginDate1.after(endDate)){
									return  false;
								}
							}else{
								//前一个星期周期不为一天
								return false ;
							}
						}else if(Integer.valueOf(endWeek1).intValue()<Integer.valueOf(endWeek).intValue()) {
							//后一个结束星期小于前一个结束星期
							return false;
						}
					}else if(Integer.valueOf(beginWeek1).intValue()==Integer.valueOf(endWeek).intValue()){
						//后一个开始星期等于前一个结束星期
						if(!beginDate1.after(endDate)){
							return false;
						}
					}
					//开盘闭盘标识不一致
					if(!calendarVo.getFlag().equals(calendarVo1.getFlag())){
						return false;
					}
				}
			}
		}
		
		//第三等级
		log.info("------------第三等级------------");
		DateFormat df1=new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		if(thirdList.size()>1){
			for(CalendarVO calendarVo:thirdList){
				try {
					Date begindatetime=df1.parse(calendarVo.getBeginDate()+" "+calendarVo.getBeginTime());
					Date enddatetime=df1.parse(calendarVo.getEndDate()+" "+calendarVo.getEndTime());
					calendarVo.setBegindatetime(begindatetime);
					calendarVo.setEnddatetime(enddatetime);
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			}
			Collections.sort(thirdList, new ThirdComparator());
			log.info("第三等级的个数"+thirdList.size());
			for(int i=0;i<thirdList.size();i++){
				log.info("按照起始日期排序："+thirdList.get(i).getBeginDate()+"|||按照起始时间排序："+thirdList.get(i).getBeginTime());
			}
			for(int i=0;i<thirdList.size()-1;i++){
				//后一个开始时间在前一个结束时间之后则为不交叉，其他情况视为交叉情况
				if(!thirdList.get(i+1).getBegindatetime().after(thirdList.get(i).getEnddatetime())){
					return false;
				}
				//开盘闭盘标识不一致
				if(!thirdList.get(i+1).getFlag().equals(thirdList.get(i).getFlag())){
					return false;
				}
				flag=true;
			}
		}
		
		return flag;
	}
	
	public List<CalendarVO> getCalenderRuleList(String calendarId){
		List<CalendarVO> calList = null;
		try {
			calList = tradeProCalendarMap.selCaleRuleList(calendarId);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return calList;
	}
	
	//添加
	public boolean saveTradeProRule(CurrUser curUser,TradeProCalVO tradeProCalVo, String ip){
		int a = 0;
		boolean flag = false;
		String tradeCode=tradeProCalVo.getTrcd();
		String ptid = tradeProCalVo.getPtid();
		String [] tradeCodes=tradeCode.split("&");
		for(String trcd:tradeCodes){
			String calId=tradeProCalVo.getCalendarID();
			String []calIds=calId.split("&");
			for(String caleId:calIds){
				try {
					a = tradeProCalendarMap.saveTradeProRule(ptid, trcd, caleId);
					if(a>0){
						flag = true;
					}else{
						flag = false;
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					flag = false;
				}
			}
		}
		return flag;
	}
	//修改
	public boolean updateTradeProRule(CurrUser curUser, TradeProCalVO tradeProCalVo, String ip){
		int a = 0;
		boolean flag = false;
		String pkey=tradeProCalVo.getPtid();
		String []keys=null;
		if(pkey!=null&&!pkey.equals("")){
			keys=pkey.trim().split("&");
			try {
				a = tradeProCalendarMap.updateTradeProRule(tradeProCalVo.getTrcd(),
						tradeProCalVo.getCalendarID(),keys[0],keys[1],keys[2]);
				if(a>0){
					flag = true;
				}else{
					flag = false;
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				flag = false;
			}
		}	
		if(flag){
			log.info("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ ip + " \n登录产品:" + curUser.getProd()
					+ "\n更新" + curUser.getProd() + "产品日历规则  \n"
					+ "交易码:"+tradeProCalVo.getTrcd()+"\n 日历ID:"+tradeProCalVo.getCalendarID()+"\n" 
					+ "更新成功!\n时间:"
					+ DataTimeClass.getCurDateTime());
		}else {
			log.error("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ ip + " \n登录产品:" + curUser.getProd()
					+ "\n更新" + curUser.getProd() + "产品日历规则  \n"
					+ "交易码："+tradeProCalVo.getTrcd()+"\n 日历ID:"+tradeProCalVo.getCalendarID()+"\n" 
					+ "更新失败!\n时间:"
					+ DataTimeClass.getCurDateTime());
		}
		return flag;
	}
	/*
	 * 查询日历等级
	 */
	public List<CalendarVO> selCalendarLeve(String levelty){
		List<CalendarVO> calvolist = null;
		try {
			calvolist = tradeProCalendarMap.selcallevel(levelty);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return calvolist;
	}
	
	/*
	 * 获取产品交易码
	 */
	public List<TradeCodeVO> seProTrade(String prod){
		List<TradeCodeVO> tradelist = null;
		try {
			tradelist = tradeProCalendarMap.selProTrade(prod);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return tradelist;
	}

	//查询等级
	public String selLevel(String calID) {
		// TODO Auto-generated method stub
		return tradeProCalendarMap.selLevel(calID);
	}
	
}
