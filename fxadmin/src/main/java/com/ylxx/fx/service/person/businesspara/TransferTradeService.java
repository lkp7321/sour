package com.ylxx.fx.service.person.businesspara;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.Tranlist;

public interface TransferTradeService {
	
	public List<Tranlist> selectTranlist(String prod,
		String lcno, String strcuac, String trdtbegin, 
		String trdtend, String comaogcd, String combogcd
			);
	public PageInfo<Tranlist> selectPageTranlist( 
			Integer pageNo, Integer pageSize, 
			String prod,String lcno, String strcuac, 
			String trdtbegin, String trdtend, 
			String comaogcd, String combogcd
				);
}
