package com.ylxx.fx.core.mapper.accex.tradeMananger;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.ViewDoneDetailBean;

public interface TranlistInfoFromViewMapper {
	
	/**
	 * 流水表视图 VIEW_DONEDETAIL_P007
	 * @param startDate
	 * @param endDate
	 * @param prod
	 * @param multiple
	 * @return
	 */
	List<ViewDoneDetailBean> getTranlistInfoFromView(@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("prod")String prod, @Param("multiple")Integer multiple);
	
	/**
	 * 贵金属流水查询
	 * @param startDate
	 * @param endDate
	 * @param prod
	 * @return
	 */
	List<ViewDoneDetailBean> getGJSTranlist(@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("prod")String prod);
	
}
