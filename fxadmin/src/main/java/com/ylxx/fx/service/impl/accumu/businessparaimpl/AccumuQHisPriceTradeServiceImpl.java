package com.ylxx.fx.service.impl.accumu.businessparaimpl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.accumu.businesspara.AccumuQHisPriceTradeMapper;
import com.ylxx.fx.service.accumu.businesspara.IAccumuQHisPriceTradeService;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;

@Service("iaccumuQHisPriceTradeService")
public class AccumuQHisPriceTradeServiceImpl implements IAccumuQHisPriceTradeService {

	@Resource
	private AccumuQHisPriceTradeMapper accumuQHisPriceTradeMapper;
	private static final Logger log = LoggerFactory.getLogger(AccumuQHisPriceTradeServiceImpl.class);
	// 定期历史价格 条件获得对应的数据
	public String selectAccumuHisPrice(String trdtbegin, String trdtend, Integer pageNo, Integer pageSize) {
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		List<Map<String, Object>> list = null;
		try {
			 list = accumuQHisPriceTradeMapper.selectAccumuHisPrice(trdtbegin, trdtend);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page=new PageInfo<Map<String, Object>>(list);
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), page);
	}
	//查所有，导出Excel
	public List<Map<String, Object>> selectAllAccumuHisPrice(String trdtbegin, String trdtend) {
		List<Map<String, Object>> list = null;
		try {
			 list = accumuQHisPriceTradeMapper.selectAccumuHisPrice(trdtbegin, trdtend);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

}
