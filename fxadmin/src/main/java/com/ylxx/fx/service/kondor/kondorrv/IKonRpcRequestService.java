package com.ylxx.fx.service.kondor.kondorrv;

public interface IKonRpcRequestService {

	//查询页面数据
	public String selAllRpcList(String tradeCode,String clstate,String rpcNo,
			Integer pageNo,Integer pageSize) throws Exception;
	//判断此交易是否需要修改
	public String selInKondor(String downloadkey,String product) throws Exception;
	//保存修改
	public String rpcUpdate(String times,String state,String rpcNo) throws Exception;
}

