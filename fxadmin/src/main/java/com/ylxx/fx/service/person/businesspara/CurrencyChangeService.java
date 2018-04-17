package com.ylxx.fx.service.person.businesspara;

import java.util.List;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.TRD_EXCHTRANLIST;

public interface CurrencyChangeService {
	public List<TRD_EXCHTRANLIST> selTranlist(
			String curLcno, String strcuno, String strsoac, 
			String vurData, String comaogcd, String combogcd
			);
	public PageInfo<TRD_EXCHTRANLIST> selPageTranlist(
			Integer pageNo, Integer pageSize, 
			String curLcno, String strcuno, String strsoac, 
			String vurData, String comaogcd, String combogcd
			);
}
