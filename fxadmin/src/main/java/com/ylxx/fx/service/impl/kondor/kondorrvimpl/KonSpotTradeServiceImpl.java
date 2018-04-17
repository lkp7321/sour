package com.ylxx.fx.service.impl.kondor.kondorrvimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.kondor.kondorrv.KonSpotTradeMapper;
import com.ylxx.fx.service.kondor.kondorrv.IKonSpotTradeService;
import com.ylxx.fx.service.po.Kon_SpotTradeBean;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;

@Service("konSpotTradeService")
public class KonSpotTradeServiceImpl implements IKonSpotTradeService {

	@Resource
	private KonSpotTradeMapper konSpotTradeMapper;

	//查询页面数据
	public String selAllSpotList(String startDate,String endDate, 
			String tradeCode, String rpcNo,Integer pageNo,Integer pageSize){
		String result = "";
		pageNo = pageNo==null?1:pageNo;
	 	pageSize = pageSize==null?10:pageSize;
	 	PageHelper.startPage(pageNo, pageSize);
	 	try {
			List<Kon_SpotTradeBean> beans = konSpotTradeMapper.selSpotTrade(startDate.trim(), 
					endDate.trim(),tradeCode.trim(), rpcNo.trim());
			PageInfo<Kon_SpotTradeBean> page = new PageInfo<Kon_SpotTradeBean>(beans);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}

}
