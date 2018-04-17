package com.ylxx.fx.service.accumu.clientPara;

import java.util.List;

public interface IAccumuCustinfoAccumuService {
		//获得全部数据
	public String queryRegmsgInfoList(String prod,String strcuno, String strcuac, String comaogcd, String combogcd, Integer pageNo, Integer pageSize) throws Exception;
	//获得对应数据
	public String selectTrac(String trac) throws Exception;

}

