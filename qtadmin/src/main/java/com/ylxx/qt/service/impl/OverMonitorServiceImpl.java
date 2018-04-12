package com.ylxx.qt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylxx.qt.core.mapper.OverMonitorMapper;
import com.ylxx.qt.service.IOverMonitorService;
import com.ylxx.qt.service.po.InstrumentFieldBean;
import com.ylxx.qt.service.po.InstrumentPtrice;
import com.ylxx.qt.service.po.ParameterdetailsBean;


@Service("overmonitorservice")
public class OverMonitorServiceImpl implements IOverMonitorService {

	@Resource
	private OverMonitorMapper omm;
	@Override
	public List<ParameterdetailsBean> queryParamet(String userId, String proId,int page,int pagecounts)
			throws Exception {
		int index=(page-1)*pagecounts;
		return omm.queryParamet(userId, proId,index,pagecounts);
	}
	@Override
	public void insertParamet(List<ParameterdetailsBean> list)
			throws Exception {
		// TODO Auto-generated method stub
		omm.insertParamet(list);
	}
	@Override
	public void deleteParamet(String userId) throws Exception {
		// TODO Auto-generated method stub
		omm.deleteParamet(userId);
	}
	//查询合约
	@Override
	public List<InstrumentFieldBean> queryInstrument(String proId) throws Exception {
		// TODO Auto-generated method stub
		
		return omm.queryInstrument(proId);
	}
	@Override
	public List<InstrumentFieldBean> queryAllInstrument() throws Exception {
		// TODO Auto-generated method stub
		return omm.queryAllInstrument();
	}
	@Override
	public void insertinstrumentprice(InstrumentPtrice instrumentptrice)
			throws Exception {
		// TODO Auto-generated method stub
		omm.insertinstrumentprice(instrumentptrice);
	}
	@Override
	public InstrumentPtrice selectinstrumentprice(String instrumentId) throws Exception {
		// TODO Auto-generated method stub
		InstrumentPtrice instr = omm.selectinstrumentprice(instrumentId);
		return instr;
		
	}
	@Override
	public void updateinstrumentprice(InstrumentPtrice instrumentptrice)
			throws Exception {
		// TODO Auto-generated method stub
		omm.updateinstrumentprice(instrumentptrice);
		
	}
	@Override
	public List<ParameterdetailsBean> queryParamet(String userId)
			throws Exception {
		// TODO Auto-generated method stub
		return omm.queryParamet1(userId);
	}
	

}
