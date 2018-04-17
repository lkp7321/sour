package com.ylxx.fx.service.impl.accumu.businessparaimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.accumu.businesspara.AccumuQTranTradeMapper;
import com.ylxx.fx.service.accumu.businesspara.IAccumuQTranTradeService;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 定期申购签约解约
 * @author lz130
 *
 */
@Service("accumuQTranTradeService")
public class IAccumuQTranTradeServiceImpl implements IAccumuQTranTradeService {
	@Resource
	private AccumuQTranTradeMapper accumuQTranTradeMapper;
	private static final Logger log = LoggerFactory.getLogger(IAccumuQTranTradeServiceImpl.class);
	/**
	 * 分页查询
	 */
	@Override
	public String selectAccumuTranTrade(String comdata1, String comdata3, String strcuac, String trdtbegin,
			String trdtend,Integer pageNo,Integer pageSize) {
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		List<Tranlist> list = null;
		try {
			list = accumuQTranTradeMapper.selectAccumuTranTrade(comdata1, comdata3, strcuac, trdtbegin, trdtend);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		PageInfo<Tranlist> page=new PageInfo<Tranlist>(list);
		return ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), page);
	}
	/**
	 * 查询所有
	 */
	public List<Tranlist> selAccumuTranTrade(
			String comdata1, String comdata3,
			String strcuac, String trdtbegin,
			String trdtend){
		List<Tranlist> list = null;
		try {
			list = accumuQTranTradeMapper.selectAccumuTranTrade(comdata1, comdata3, strcuac, trdtbegin, trdtend);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
}
