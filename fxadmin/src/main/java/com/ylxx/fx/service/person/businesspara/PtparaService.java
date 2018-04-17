package com.ylxx.fx.service.person.businesspara;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.Ptpara;
import com.ylxx.fx.utils.CurrUser;
/**
 * P001,P002,P003,P004
 * 业务参数设置
 * @author lz130
 *
 */
public interface PtparaService {
	/**
	 * 查询交易参数设置
	 * p001,p002,p003,P004
	 * @param prod
	 * @return
	 */
	PageInfo<Ptpara> getptparalist(String prod, Integer pageNo, Integer pageSize);
	/**
	 * 修改交易参数设置
	 * p001,p002,p003,P004
	 * @param curUser
	 * @param ptpara
	 * @return
	 */
	boolean updateptpara(CurrUser curUser, Ptpara ptpara);
}
