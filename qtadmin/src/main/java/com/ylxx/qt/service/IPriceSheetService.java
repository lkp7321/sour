package com.ylxx.qt.service;

import java.util.List;

import com.ylxx.qt.service.po.PriceSheet;
import com.ylxx.qt.service.po.ProductBean;

public interface IPriceSheetService {
	/**
	 * 获取所有品种价格
	 * @return
	 * @throws Exception
	 */
	public List<PriceSheet> queryAll(String proId,String proName,Integer priceType,int page,int pagecounts)throws Exception;
	//获取指定品种价格
	public List<PriceSheet> queryByid(String proId,int page,int pagecounts)throws Exception;
	//获取指定品种价格无分页
	public List<PriceSheet> queryByid(String proId)throws Exception; 
	//删除指定品种价格
	public void deleteByid(String proId)throws Exception;
	//更新指定品种价格
	public void updateByid(List<PriceSheet> priceList)throws Exception;
	//数据的条数
	public Integer selectCounts(String proId,String proName,Integer priceType)throws Exception;
	//所有数据条数
	public Integer selectCountsAll(String proId)throws Exception;
	//新增数据
	public void insertPro(List<PriceSheet> list)throws Exception;
	//根据品种id和年份删除
	public void deleteByYear(String proId,Integer year)throws Exception;
	//查询所有品种
	public List<ProductBean> selectProducts(String proId,String proName)throws Exception;
	
	public List<ProductBean> productBypricesheet(String proId,String proName)throws Exception;

}
