package com.ylxx.fx.utils.calendar;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

import com.ylxx.fx.service.po.calendar.CalendarVO;

public class FirstComparator implements Comparator<CalendarVO>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int compare(CalendarVO arg0, CalendarVO arg1) {
		int comparison = -1;  
		if(arg0 instanceof CalendarVO && arg1 instanceof CalendarVO ) { 
			CalendarVO calVo1=(CalendarVO)arg0;
			CalendarVO calVo2=(CalendarVO)arg1;
			String beginTime1=calVo1.getBeginTime();
			String beginTime2=calVo2.getBeginTime();
			if(!StringUtils.isEmpty(beginTime1)&&!StringUtils.isEmpty(beginTime2)){
				comparison=beginTime1.compareTo(beginTime2);
			}
		}   
		return comparison;
	}

}
