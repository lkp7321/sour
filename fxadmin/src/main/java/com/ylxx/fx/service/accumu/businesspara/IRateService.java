package com.ylxx.fx.service.accumu.businesspara;

import java.util.List;

import com.ylxx.fx.core.domain.RateVo;
import com.ylxx.fx.service.po.RateBean;
/**
 * 利率配置
 * @author lz130
 *
 */
public interface IRateService {

	/**
	 * 查询利率配置数据
	 * @return
	 * @throws Exception
	 */
	 List<RateBean> searchList() throws Exception;
	
	/**
	 * 向利率历史表里添加记录
	 * @param rateVo
	 * @return
	 * @throws Exception
	 */
	 Boolean doInsertHis(RateVo rateVo) throws Exception;
		
	
	/**
	 * 向利率表里添加记录
	 * @param rateVo
	 * @return
	 * @throws Exception
	 */
	 Boolean doInsert(RateVo rateVo) throws Exception;
		
	 /**
	 * 删除利率历史表记录
	 * @param rateVo
	 * @return
	 * @throws Exception
	 */
	 void doDelete(String time, String pdtp) throws Exception;
	 
	 /**
	 * 更新利率表里的记录
	 * @param rateVo
	 * @return
	 * @throws Exception
	 */
	 Boolean doUpdate(RateVo rateVo) throws Exception;
	 
	 /**
	 * 向利率表里添加记录(插入之前写入历史表)	
	 * @param rateVo
	 * @return
	 */
	String insertWithInsertHis(RateVo rateVo) throws Exception;
	
	 /**
	 * 更新利率表里的记录(更新之前写入历史表)	
	 * @param rateVo
	 * @return
	 */
	String updateWithInsertHis(RateVo rateVo) throws Exception;
}

