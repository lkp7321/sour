package com.ylxx.fx.service.impl.kondor.kondorrvimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.kondor.kondorrv.KonBondTradeMapper;
import com.ylxx.fx.service.kondor.kondorrv.IKonBondTradeService;
import com.ylxx.fx.service.po.Kon_BondTradeBean;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;



@Service("konBondTradeService")
public class KonBondTradeServiceImpl implements IKonBondTradeService {

	@Resource
	private KonBondTradeMapper konBondTradeMapper;

	//查询页面数据
	public String selAllBondList(String startDate,String endDate, String rpcNo,
			Integer pageNo,Integer pageSize){
		String result = "";
		pageNo = pageNo==null?1:pageNo;
	 	pageSize = pageSize==null?10:pageSize;
	 	PageHelper.startPage(pageNo, pageSize);
		try {
			List<Kon_BondTradeBean> beans = konBondTradeMapper.selBondTrade(startDate.trim(), 
					endDate.trim(), rpcNo.trim());
			PageInfo<Kon_BondTradeBean> page = new PageInfo<Kon_BondTradeBean>(beans);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		} 
		return result;
	}

}
