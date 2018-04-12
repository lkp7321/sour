package com.ylxx.qt.service;

import java.util.List;

import com.ylxx.qt.service.po.InstrumentFieldBean;
import com.ylxx.qt.service.po.InstrumentPtrice;
import com.ylxx.qt.service.po.ParameterdetailsBean;

public interface IOverMonitorService {
	//查询参数
	public List<ParameterdetailsBean> queryParamet(String userId,String proId,int page,int pagecounts)throws Exception;
	
	public List<ParameterdetailsBean> queryParamet(String userId)throws Exception;
	
	//新增参数
	public void insertParamet(List<ParameterdetailsBean> list)throws Exception;
	//删除参数
	public void deleteParamet(String userId)throws Exception;
	
	
	public List<InstrumentFieldBean> queryInstrument(String proId)throws Exception;
	
	public List<InstrumentFieldBean> queryAllInstrument()throws Exception;
	public void insertinstrumentprice(InstrumentPtrice instrumentptrice)throws Exception;
	public InstrumentPtrice selectinstrumentprice(String instrumentId)throws Exception;
	public void updateinstrumentprice(InstrumentPtrice instrumentptrice)throws Exception;
}
