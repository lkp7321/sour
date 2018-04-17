package com.ylxx.fx.service.impl.person.businessparaimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.person.businesspara.TradeCalendarMapper;
import com.ylxx.fx.service.person.businesspara.TradeCalendarService;
import com.ylxx.fx.service.po.calendar.CalendarVO;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
@Service("tradeCalendarService")
public class TradeCalendarServiceImpl implements TradeCalendarService {
	private static final Logger log = LoggerFactory.getLogger(TradeCalendarServiceImpl.class);
	@Resource
	private TradeCalendarMapper tradeCalendarMap;
	
	/*
	 * 查询所有日历规则
	 */
	public PageInfo<CalendarVO> getAllCalendarVo(Integer pageNo,
			Integer pageSize) {
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    List<CalendarVO> list = null;
	    try {
	    	list = tradeCalendarMap.selectCalenderRuleList();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	    //用PageInfo对结果进行包装
	    PageInfo<CalendarVO> page = new PageInfo<CalendarVO>(list);
	    if(page.getList()!=null&&page.getList().size()>0){
	    	for (int i = 0; i < page.getList().size(); i++) {
				String beginWeek=page.getList().get(i).getBeginWeek();
				String endWeek = page.getList().get(i).getEndWeek();
				String levelType = page.getList().get(i).getLevelType();
				String levelName = "";
                if(beginWeek!=null||endWeek!=null){
					if(beginWeek.trim().equals("0")){
						beginWeek="星期日";
					}else if(beginWeek.trim().equals("1")){
						beginWeek="星期一";
					}else if(beginWeek.trim().equals("2")){
						beginWeek="星期二";
					}else if(beginWeek.trim().equals("3")){ 
						beginWeek="星期三";
					}else if(beginWeek.trim().equals("4")){
						beginWeek="星期四";
					}else if(beginWeek.trim().equals("5")){
						beginWeek="星期五";
					}else if(beginWeek.trim().equals("6")){
						beginWeek="星期六";
					}
					
					if(endWeek.trim().equals("0")){
						endWeek="星期日";
					}else if(endWeek.trim().equals("1")){
						endWeek="星期一";
					}else if(endWeek.trim().equals("2")){
						endWeek="星期二";
					}else if(endWeek.trim().equals("3")){
						endWeek="星期三";
					}else if(endWeek.trim().equals("4")){
						endWeek="星期四";
					}else if(endWeek.trim().equals("5")){
						endWeek="星期五";
					}else if(endWeek.trim().equals("6")){
						endWeek="星期六";
					}
					
					if(levelType.equals("1")){
						levelName=levelType+"、每周每日";
					}else if(levelType.equals("2")){
						levelName=levelType+"、每周特殊日";
					}else if(levelType.equals("3")){
						levelName=levelType+"、年度特殊日";
					}
 
				}
 
				
				if(levelType.equals("1")){
					levelName=levelType+"、每周每日";
				}else if(levelType.equals("2")){
					levelName=levelType+"、每周特殊日";
				}else if(levelType.equals("3")){
					levelName=levelType+"、年度特殊日";
				}
 
				page.getList().get(i).setBeginWeek(beginWeek);
				page.getList().get(i).setEndWeek(endWeek);
				page.getList().get(i).setLevelName(levelName);
				
				if(page.getList().get(i).getFlag().trim().equals("0")){
					page.getList().get(i).setFlag("开盘");
				}else if(page.getList().get(i).getFlag().trim().equals("1")){
					page.getList().get(i).setFlag("闭盘");
				}
				
				String quanName="";
				if(page.getList().get(i).getIsquantian().equals("0")){
					quanName="否";
				}else if(page.getList().get(i).getIsquantian().equals("1")){
					quanName="是";
				}
				page.getList().get(i).setQuanflag(quanName);
			}
	    }
		return page;
	}
	
	
	/**
	 * 删除交易日历规则
	 * @param userKey
	 * @param calendarId
	 * @return
	 */
	public boolean deleteCalendarRules(String calenderId){
		int a = 0;
		try {
			a=tradeCalendarMap.deleteCalendaRule(calenderId);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	public boolean deleteProCalendarRules(String calenderId){
		int a = 0;
		try {
			a = tradeCalendarMap.deleteProCalendarRule(calenderId);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	public boolean deletePriceRecCalendarRules(String calenderId){
		int a = 0;
		try {
			a = tradeCalendarMap.deletePriceRecCalendarRule(calenderId);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	public boolean deletePriceJiaCalendarRules(String calenderId){
		int a = 0;
		try {
			a = tradeCalendarMap.deletePriceJiaCalendarRule(calenderId);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
	
	public boolean deleteCalendarRule(String calendarId){
		
		boolean flag=false;
		if(!deleteCalendarRules(calendarId)){
			log.info("删除日历规则表失败!");
			return false;
		}
		if(!deleteProCalendarRules(calendarId)){
			log.info("删除产品日历规则表失败!");
			return false;
		}
		if(!deletePriceRecCalendarRules(calendarId)){
			log.info("删除价格接收日历规则表失败!");
			return false;
		}
		if(!deletePriceJiaCalendarRules(calendarId)){
			log.info("删除价格加工日历规则表失败!");
			return false;
		}
		return true;
	}
	/**
	 * 删除交易日历规则
	 * @param user
	 * @param calendarId
	 * @param ip
	 * @return
	 */
	public boolean delCalendarRules(CurrUser curUser, String calendarId, String ip){
		boolean flag = deleteCalendarRule(calendarId);
		if(flag){
			log.info("\n用户：" + curUser.getUsnm() + " \n登录IP:"
					+ ip + " \n登录产品:" + curUser.getProd()
					+ "\n删除" + curUser.getProd() + "交易日历规则  \n"
					+ "删除"+calendarId + "成功!\n时间:"
					+ DataTimeClass.getCurDateTime());
		}else{
			log.error("\n用户：" + curUser.getUsnm() + " \n登录IP:"
					+ ip + " \n登录产品:" + curUser.getProd()
					+ "\n删除" + curUser.getProd() + "交易日历规则  \n"
					+ "删除"+calendarId + "失败!\n时间:"
					+ DataTimeClass.getCurDateTime());
		}
		return flag;
	}
	/**
	 * 更新交易日历规则
	 * @param user
	 * @param calVo
	 * @param ip
	 * @return
	 */
	public boolean updateCalendarRule(CurrUser curUser, CalendarVO calVo, String ip){
		int a = 0;
		boolean flag = false;
		try {
			String isquantian = calVo.getIsquantian();
			if(isquantian.equals("0")){
				calVo.setBeginTime(calVo.getBeginTime());
				calVo.setEndTime(calVo.getEndTime());
			}else if(isquantian.equals("1")){
				calVo.setBeginTime("00:00:00");
				calVo.setEndTime("23:59:59");
			}
			a = tradeCalendarMap.updateCalendarRule(calVo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			flag = true;
		}else{
			flag = false;
		}
		if(flag){
			log.info("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ ip + " \n登录产品:" + curUser.getProd()
					+ "\n更新" + calVo.getProductId() + "交易日历规则  \n日历规则等级："+calVo.getLevelType()+" \n日历开始日期" + calVo.getBeginDate()
					+ "\n日历结束日期:" + calVo.getEndDate() + "\n日历开始星期:"
					+ calVo.getBeginWeek() + "\n日历结束星期:" + calVo.getEndWeek()
					+ " \n日历开始时间："+calVo.getBeginTime()+"\n 日历结束时间："+calVo.getEndTime()+"\n"
					+ "开闭盘标识："+calVo.getFlag() + "\n更新成功!\n时间:"
					+ DataTimeClass.getCurDateTime());
		}else{
			log.error("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ ip + " \n登录产品:" + curUser.getProd()
					+ "\n更新" + calVo.getProductId() + "交易日历规则  \n日历规则等级："+calVo.getLevelType()+" \n日历开始日期" + calVo.getBeginDate()
					+ "\n日历结束日期:" + calVo.getEndDate() + "\n日历开始星期:"
					+ calVo.getBeginWeek() + "\n日历结束星期:" + calVo.getEndWeek()
					+ " \n日历开始时间："+calVo.getBeginTime()+"\n 日历结束时间："+calVo.getEndTime()+"\n"
					+ "开闭盘标识："+calVo.getFlag() + "\n 更新失败!\n时间:"
					+ DataTimeClass.getCurDateTime());
		}
		return flag;
	}
	
	/**
	 * 添加交易日历规则
	 * @param userKey
	 * @param calVo
	 * @return
	 */
	public boolean  saveCalendarRules(CurrUser curUser, CalendarVO calVo, String ip){
		int a = 0;
		boolean flag = false;
		String calId = getUUid(calVo.getLevelType());
		calVo.setCalendarID(calId);
		String isquantian = calVo.getIsquantian();
		if(isquantian.equals("0")){
			calVo.setBeginTime(calVo.getBeginTime());
			calVo.setEndTime(calVo.getEndTime());
		}else if(isquantian.equals("1")){
			calVo.setBeginTime("00:00:00");
			calVo.setEndTime("23:59:59");
		}
		try {
			a = tradeCalendarMap.insertCalendarRule(calVo);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			flag = true;
		}else{
			flag = false;
		}
		if(flag){
			log.info("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ ip + " \n登录产品:" + curUser.getProd()
					+ "\n添加" + calVo.getProductId() + "交易日历规则  \n日历规则等级："+calVo.getLevelType()+" \n日历开始日期" + calVo.getBeginDate()
					+ "\n日历结束日期:" + calVo.getEndDate() + "\n日历开始星期:"
					+ calVo.getBeginWeek() + "\n日历结束星期:" + calVo.getEndWeek()
					+ " \n日历开始时间："+calVo.getBeginTime()+"\n 日历结束时间："+calVo.getEndTime()+"\n"
					+ "开闭盘标识："+calVo.getFlag() + "\n 添加成功!\n时间:"
					+ DataTimeClass.getCurDateTime());
		}else{
			log.error("用户：" + curUser.getUsnm() + " \n登录IP:"
					+ ip + " \n登录产品:" + curUser.getProd()
					+ "\n添加" + calVo.getProductId() + "交易日历规则  \n日历规则等级："+calVo.getLevelType()+" \n日历开始日期" + calVo.getBeginDate()
					+ "\n日历结束日期:" + calVo.getEndDate() + "\n日历开始星期:"
					+ calVo.getBeginWeek() + "\n日历结束星期:" + calVo.getEndWeek()
					+ " \n日历开始时间："+calVo.getBeginTime()+"\n 日历结束时间："+calVo.getEndTime()+"\n"
					+ "开闭盘标识："+calVo.getFlag() + "\n添加失败!\n时间:"
					+ DataTimeClass.getCurDateTime());
		}
		return flag;
	} 
	
	public String getUUid(String level){
		int maxid = 0;
		String maxId = "";
		try {
			maxId = tradeCalendarMap.selCalId(level);
			if(maxId!=null && !maxId.equals("")){
				maxid=Integer.valueOf(maxId);
				maxid=maxid+1;
			}else{
				log.info("maxId为空");
				if(level.equals("1")){
					maxid = 1001;
				}else if(level.equals("2")){
					maxid = 2001;
				}else if(level.equals("3")){
					maxid = 3001;
				}
			}
			log.info("最大数为："+maxid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return String.valueOf(maxid);
	}
}
