package com.ylxx.qt.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.qt.service.po.InstrumentFieldBean;
import com.ylxx.qt.service.po.InstrumentPtrice;
import com.ylxx.qt.service.po.ParameterdetailsBean;

public interface OverMonitorMapper {
	public List<ParameterdetailsBean> queryParamet(@Param("userId")String userId,@Param("proId")String proId,@Param("index")int index,@Param("pagecounts") int pagecounts) throws Exception;
	
	public List<ParameterdetailsBean> queryParamet1(@Param("userId")String userId) throws Exception;
	public void insertParamet(@Param("list") List<ParameterdetailsBean> list) throws Exception;
	public void deleteParamet(@Param("userId")String userId) throws Exception;
	
	public List<InstrumentFieldBean> queryInstrument(String proId) throws Exception; 
	public List<InstrumentFieldBean> queryAllInstrument() throws Exception;
	public void insertinstrumentprice(InstrumentPtrice instrumentptrice) throws Exception;
	public InstrumentPtrice selectinstrumentprice(String instrumentId)throws Exception;
	public void updateinstrumentprice(InstrumentPtrice instrumentptrice)throws Exception;
}
