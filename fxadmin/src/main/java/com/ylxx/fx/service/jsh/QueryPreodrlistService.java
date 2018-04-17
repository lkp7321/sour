package com.ylxx.fx.service.jsh;

import java.util.List;

import com.ylxx.fx.service.po.jsh.QueryPreodrlistIn;
import com.ylxx.fx.service.po.jsh.Trd_Preodrlist;

public interface QueryPreodrlistService {
	/**
	 * 分页查询挂单流水
	 * @param in
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	String getPreodrlist(QueryPreodrlistIn in,Integer pageNo,Integer pageSize) throws Exception;
	/**
	 * 导出Excel
	 * @param poductCode
	 * @param qutp
	 * @param cuno
	 * @param stdt
	 * @param eddt
	 * @param rqno
	 * @return
	 */
	List<Trd_Preodrlist> getPreodrList(String poductCode,int qutp, String cuno, String stdt, String eddt, String rqno);
}

