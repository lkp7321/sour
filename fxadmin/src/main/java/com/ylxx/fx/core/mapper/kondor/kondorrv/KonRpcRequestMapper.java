package com.ylxx.fx.core.mapper.kondor.kondorrv;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Kon_RpcRequestBean;

public interface KonRpcRequestMapper{
	
	//当产品类型选择“所有”时数据的查询
	public List<Kon_RpcRequestBean> selAllRpcRequest(@Param("clstate")String clstate,
			@Param("rpcNo")String rpcNo) throws Exception;
	//当产品类型未选择“所有”时数据的查询
	public List<Kon_RpcRequestBean> selRpcRequest(@Param("tradeCode")String tradeCode,
			@Param("clstate")String clstate,@Param("rpcNo")String rpcNo) throws Exception;
	//判断此交易是否需要修改:取错误码1
	public String selBondInKondor(@Param("downloadkey")String downloadkey) throws Exception;
	//判断此交易是否需要修改:取错误码2
	public String selCashInKondor(@Param("downloadkey")String downloadkey) throws Exception;
	//判断此交易是否需要修改:取错误码3
	public String selAmountInKondor(@Param("downloadkey")String downloadkey) throws Exception;
	//判断此交易是否需要修改:取错误码4
	public String selSpotInKondor(@Param("downloadkey")String downloadkey) throws Exception;
	//保存修改
	public boolean rpcUpdate(@Param("times")String times,@Param("state")String state,
			@Param("rpcNo")String rpcNo) throws Exception;
}