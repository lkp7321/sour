package com.ylxx.fx.core.mapper.accumu.businesspara;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
/**
 * 批量转账明细查询
 * @author lz130
 *
 */
public interface MGAPQueryBatchTransferDetailMapper {
	List<HashMap<String,String>> getSearch(@Param("number")String number,@Param("status")String status);
}
