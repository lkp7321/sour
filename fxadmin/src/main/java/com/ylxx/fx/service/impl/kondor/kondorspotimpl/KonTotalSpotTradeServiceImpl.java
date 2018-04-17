package com.ylxx.fx.service.impl.kondor.kondorspotimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.kondor.kondorspot.KonTotalSpotTradeMapper;
import com.ylxx.fx.service.kondor.kondorspot.IKonTotalSpotTradeService;
import com.ylxx.fx.service.po.Kon_TotalSpotTradeBean;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;


@Service("konTotalSpotTradeService")
public class KonTotalSpotTradeServiceImpl implements IKonTotalSpotTradeService {

	@Resource
	private KonTotalSpotTradeMapper konTotalSpotTradeMapper;

	//查询页面数据
	public String selTotalSpotList(String startDate,String endDate, 
			String tradeCode, String rpcNo,Integer pageNo,Integer pageSize){
		String result = "";
		pageNo = pageNo==null?1:pageNo;
	 	pageSize = pageSize==null?10:pageSize;
	 	PageHelper.startPage(pageNo, pageSize);
		try {
			List<Kon_TotalSpotTradeBean> beans = konTotalSpotTradeMapper.selTotalSpotList(startDate.trim(), 
					endDate.trim(), tradeCode.trim(), rpcNo.trim());
			PageInfo<Kon_TotalSpotTradeBean> page = new PageInfo<Kon_TotalSpotTradeBean>(beans);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}

}
