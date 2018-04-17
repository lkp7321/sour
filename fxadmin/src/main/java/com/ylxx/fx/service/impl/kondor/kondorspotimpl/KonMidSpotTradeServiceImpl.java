package com.ylxx.fx.service.impl.kondor.kondorspotimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.kondor.kondorspot.KonMidSpotTradeMapper;
import com.ylxx.fx.service.kondor.kondorspot.IKonMidSpotTradeService;
import com.ylxx.fx.service.po.Kon_MidSpotTradeBean;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;


@Service("konMidSpotTradeService")
public class KonMidSpotTradeServiceImpl implements IKonMidSpotTradeService {

	@Resource
	private KonMidSpotTradeMapper konMidSpotTradeMapper;

	//查询页面数据
	public String selMidSpotList(String startDate,String endDate, String rpcNo,
			Integer pageNo,Integer pageSize){
		String result = "";
		pageNo = pageNo==null?1:pageNo;
	 	pageSize = pageSize==null?10:pageSize;
	 	PageHelper.startPage(pageNo, pageSize);
		try {
			List<Kon_MidSpotTradeBean> beans = konMidSpotTradeMapper.selMidSpotList(startDate.trim(), 
					endDate.trim(), rpcNo.trim());
			PageInfo<Kon_MidSpotTradeBean> page = new PageInfo<Kon_MidSpotTradeBean>(beans);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		} 
		return result;
	}

}
