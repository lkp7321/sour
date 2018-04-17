package com.ylxx.fx.core.mapper.accumu.businesspara;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
/**
 * 批量转账
 * @author lz130
 *
 */
public interface MGAPQueryBatchTransferMapper {
	/**
	 * 批量转账查询
	 * @param filename
	 * @param number
	 * @return
	 */
	List<Map<String,Object>> getSearch(@Param("filename")String filename,@Param("number")String number);
}
