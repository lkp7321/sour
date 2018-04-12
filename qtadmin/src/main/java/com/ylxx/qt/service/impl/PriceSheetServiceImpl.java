package com.ylxx.qt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylxx.qt.core.mapper.PriceSheetMapper;
import com.ylxx.qt.service.IPriceSheetService;
import com.ylxx.qt.service.po.PriceSheet;
import com.ylxx.qt.service.po.ProductBean;

@Service("pricesheetservice")
public class PriceSheetServiceImpl implements IPriceSheetService{
	@Resource
	private PriceSheetMapper priceSheetMapper;
	
	
	@Override
	public List<PriceSheet> queryAll(String proId,String proName,Integer priceType,int page,int pagecounts) throws Exception {
		// TODO Auto-generated method stub
		int index=(page-1)*pagecounts;
		return priceSheetMapper.getAll(proId, proName, priceType,index,pagecounts);
	}


	@Override
	public List<PriceSheet> queryByid(String proId,int page,int pagecounts) throws Exception {
		// TODO Auto-generated method stub
		int index=(page-1)*pagecounts;
		return priceSheetMapper.getByid(proId,index,pagecounts);
	}


	@Override
	public void deleteByid(String proId) throws Exception {
		// TODO Auto-generated method stub
		priceSheetMapper.deleteByid(proId);
	}


	@Override
	public void updateByid(List<PriceSheet> priceList) throws Exception {
		// TODO Auto-generated method stub
		priceSheetMapper.updateByid(priceList);
	}


	@Override
	public Integer selectCounts(String proId, String proName, Integer priceType)
			throws Exception {
		// TODO Auto-generated method stub
		return priceSheetMapper.selectCounts(proId, proName, priceType);
	}


	@Override
	public Integer selectCountsAll(String proId) throws Exception {
		// TODO Auto-generated method stub
		return priceSheetMapper.selectCountsAll(proId);
	}


	@Override
	public void insertPro(List<PriceSheet> list) throws Exception {
		// TODO Auto-generated method stub
		priceSheetMapper.insertPro(list);
	}


	@Override
	public void deleteByYear(String proId, Integer year) throws Exception {
		// TODO Auto-generated method stub
		priceSheetMapper.deleteByYear(proId, year);
	}


	@Override
	public List<PriceSheet> queryByid(String proId) throws Exception {
		// TODO Auto-generated method stub
		return priceSheetMapper.getByid1(proId);
	}


	@Override
	public List<ProductBean> selectProducts(String proId,String proName) throws Exception {
		// TODO Auto-generated method stub
		return priceSheetMapper.selectProducts(proId,proName);
	}


	@Override
	public List<ProductBean> productBypricesheet(String proId, String proName)
			throws Exception {
		// TODO Auto-generated method stub
		return priceSheetMapper.productBypricesheet(proId,proName);
	}


	
	
}
