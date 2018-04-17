package com.ylxx.fx.utils;

import java.util.Comparator;

import com.ylxx.fx.service.po.AccinfoMonitorBean;

public class ComparatorAccinfo implements Comparator {
	@Override
	public int compare(Object o1, Object o2) {
		AccinfoMonitorBean accinfo1=(AccinfoMonitorBean)o1;
		AccinfoMonitorBean accinfo2=(AccinfoMonitorBean)o2;
		
		return accinfo1.getOgcd().compareTo(accinfo2.getOgcd());
	}
}
