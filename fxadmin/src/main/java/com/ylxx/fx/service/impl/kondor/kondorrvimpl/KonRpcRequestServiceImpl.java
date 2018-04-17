package com.ylxx.fx.service.impl.kondor.kondorrvimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.kondor.kondorrv.KonRpcRequestMapper;
import com.ylxx.fx.service.kondor.kondorrv.IKonRpcRequestService;
import com.ylxx.fx.service.po.Kon_RpcRequestBean;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;

@Service("konRpcRequestService")
public class KonRpcRequestServiceImpl implements IKonRpcRequestService {

	@Resource
	private KonRpcRequestMapper konRpcRequestMapper;

	//查询页面数据
	public String selAllRpcList(String tradeCode,String clstate,String rpcNo,
			Integer pageNo,Integer pageSize){
		String result = "";
		pageNo = pageNo==null?1:pageNo;
	 	pageSize = pageSize==null?10:pageSize;
	 	PageHelper.startPage(pageNo, pageSize);
		List<Kon_RpcRequestBean> beans = new ArrayList<Kon_RpcRequestBean>();
		try {
			if (tradeCode.equals("All")) {
				beans = konRpcRequestMapper.selAllRpcRequest(clstate.trim(), rpcNo.trim());
			}else {
				beans = konRpcRequestMapper.selRpcRequest(tradeCode.trim(), clstate.trim(), rpcNo.trim());
			}
			PageInfo<Kon_RpcRequestBean> page = new PageInfo<Kon_RpcRequestBean>(beans);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
	 	return result;
	}
	//判断此交易是否需要修改
	public String selInKondor(String downloadkey,String product){
		String result = "";
		boolean bo = false;
		String code = null;
		try {
			if ("BOND001".equals(product)) {
				code = konRpcRequestMapper.selBondInKondor(downloadkey);
			}else if (product.contains("SPOT")) {
				if ("SPOTP075".equals(product)) {
					code = konRpcRequestMapper.selAmountInKondor(downloadkey);
				}else {
					code = konRpcRequestMapper.selSpotInKondor(downloadkey);
				}
			}else if ("CASH001".equals(product)) {
				code = konRpcRequestMapper.selCashInKondor(downloadkey);
			}else {
				bo = false;
			}
			if (code.equals("AAAAAAA")) {
				bo = true;
			}
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(bo));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}
	//保存修改
	public String rpcUpdate(String times,String state,String rpcNo){
		String result = "";
		try {
			boolean bo = konRpcRequestMapper.rpcUpdate(times.trim(), state.trim(), rpcNo.trim());
			if (bo) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
			}else {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}
}
