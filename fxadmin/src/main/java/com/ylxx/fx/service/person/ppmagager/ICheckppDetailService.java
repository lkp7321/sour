package com.ylxx.fx.service.person.ppmagager;

public interface ICheckppDetailService {

	//条件查询
	public String selAllList(String seqn) throws Exception;
	//成功复核
	public String success(String userKey,String seqn) throws Exception;
	//失败复核
	public String unsuccess(String userKey,String seqn) throws Exception;
}

