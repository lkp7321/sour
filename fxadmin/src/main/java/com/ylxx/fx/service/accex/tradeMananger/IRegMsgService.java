package com.ylxx.fx.service.accex.tradeMananger;

import java.util.List;

import com.ylxx.fx.service.po.AccRegmsgBean;


public interface IRegMsgService {

	/**
	 * 查询账户外汇账户信息
	 * @param startDate
	 * @param endDate
	 * @param prcd
	 * @return
	 */
	List<AccRegmsgBean> searchRegmsgList(String startDate, String endDate, String prcd);
	
}

