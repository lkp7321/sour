package com.ylxx.fx.service.person.fxipmonitor;

import java.util.List;

import com.ylxx.fx.service.po.Cmmalarm;

public interface CmmExceptionService {
	/*
	 * 查询当天异常数据 返回最新的15条数据
	 */
	public List<Cmmalarm> fxipExceptionData(String curDate);
}
