package com.ylxx.fx.service.impl.person.businessparaimpl;

import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.person.businesspara.CurrencyChangeMapper;
import com.ylxx.fx.service.person.businesspara.CurrencyChangeService;
import com.ylxx.fx.service.po.TRD_EXCHTRANLIST;

@Service("currencychanService")
public class CurrencyChangeServiceImpl implements CurrencyChangeService {
	private static final Logger log = LoggerFactory.getLogger(CurrencyChangeServiceImpl.class);
	@Resource
	private CurrencyChangeMapper currencychanMap;
	
	//查询所有
	public List<TRD_EXCHTRANLIST> selTranlist(String curLcno, String strcuno,
			String strsoac, String vurData, String comaogcd, String combogcd) {
		List<TRD_EXCHTRANLIST> list = null;
		try {
			list = currencychanMap.selectTranlist(
					curLcno, strcuno, strsoac, vurData, comaogcd, combogcd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	//分页
	public PageInfo<TRD_EXCHTRANLIST> selPageTranlist(Integer pageNo,
			Integer pageSize, String curLcno, String strcuno, String strsoac,
			String vurData, String comaogcd, String combogcd) {
		List<TRD_EXCHTRANLIST> list = null;
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    try {
			list = currencychanMap.selectTranlist(
					curLcno, strcuno, strsoac, vurData, comaogcd, combogcd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	    PageInfo<TRD_EXCHTRANLIST> page = new PageInfo<TRD_EXCHTRANLIST>(list);
		return page;
	}
	
	
	
}
