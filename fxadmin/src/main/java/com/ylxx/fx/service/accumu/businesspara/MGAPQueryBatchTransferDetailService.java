package com.ylxx.fx.service.accumu.businesspara;
/**
 * 查询批量转账明细
 * @author lz130
 *
 */
public interface MGAPQueryBatchTransferDetailService {
	/**
	 * 查询批量转账明细
	 * @param number
	 * @param status
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	String getSearch(String number,String status,Integer pageNo,Integer pageSize);
}
