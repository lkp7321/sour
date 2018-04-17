package com.ylxx.fx.core.mapper.person.ppmagager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Ck_ppresultdetail;

public interface PpAccountMapper{
	
	//查询不明交易
	public List<Ck_ppresultdetail> selectallaccount(@Param("lcno")String lcno) throws Exception;
	//查询UBS联系方式
	public String queryPtpara(@Param("paid")String paid) throws Exception;
	//更新
	public boolean update(@Param("ppno")String ppno, @Param("prod")String prod) throws Exception;
}