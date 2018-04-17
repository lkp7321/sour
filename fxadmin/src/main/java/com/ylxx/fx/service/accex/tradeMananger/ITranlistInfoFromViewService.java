package com.ylxx.fx.service.accex.tradeMananger;

import java.util.List;

import com.ylxx.fx.service.po.ViewDoneDetailBean;


public interface ITranlistInfoFromViewService {

	/**
	 * 流水表视图 VIEW_DONEDETAIL_P007
	 * @param startDate
	 * @param endDate
	 * @param prod
	 * @param multiple
	 * @return
	 */
	List<ViewDoneDetailBean> getTranlistInfoFromView(String startDate, String endDate, String prod, Integer multiple);
	/**
	 * 贵金属流水查询
	 * @param startDate
	 * @param endDate
	 * @param prod
	 * @return
	 */
	List<ViewDoneDetailBean> getGJSTranlist(String startDate, String endDate, String prod);
}

