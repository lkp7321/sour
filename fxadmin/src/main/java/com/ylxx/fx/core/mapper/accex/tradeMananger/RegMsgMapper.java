package com.ylxx.fx.core.mapper.accex.tradeMananger;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.AccRegmsgBean;

public interface RegMsgMapper {
	
	/**
	 * 查询账户外汇账户信息
	 * @param startDate
	 * @param endDate
	 * @param prcd
	 * @return
	 */
	List<AccRegmsgBean> searchRegmsgList(@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("prcd")String prcd);
}
