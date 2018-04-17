package com.ylxx.fx.service.bail.eodprocess;

import java.util.List;

import com.ylxx.fx.service.po.BailTranlist;

public interface IBailOutAccountService {

	//查询交易码
	public String seltrancode() throws Exception;
	//查询批量出金数据
	public String seleOutAccountList(String hddate,String status,String enddate,
			Integer pageNo,Integer pageSize) throws Exception;
	//核对提交
	public String hedui(String userKey,List<BailTranlist> list) throws Exception;
	//核对取消
	public String cancelHedui(String userKey,List<BailTranlist> list) throws Exception;
	//复核
	public String fuhe(String userKey,List<BailTranlist> list) throws Exception;
}

