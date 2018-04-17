package com.ylxx.fx.service.person.ppmagager;

import com.ylxx.fx.service.po.CkHandMxDetail;

public interface IMoZhangService {

	//获取所有数据
	public String selectAllMxDetail(String prod) throws Exception;
	//条件查询
	public String selectMxDetail(String prod,String dateApdt,String dataEndInput) throws Exception;
	//抹账
	public String mozhang(String userKey,CkHandMxDetail ckno) throws Exception;
}

