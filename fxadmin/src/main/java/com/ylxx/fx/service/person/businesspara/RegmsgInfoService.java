package com.ylxx.fx.service.person.businesspara;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.RegmsgBean;
/**
 * 客户签约信息查询
 * @author lz130
 *
 */
public interface RegmsgInfoService {
	PageInfo<RegmsgBean> getAllRegmsgInfo(String userProd,
			String strcuno, String strcuac, 
			String comaogcd, String combogcd,
			Integer pageNo, Integer pageSize);
	
}
