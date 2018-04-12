package com.ylxx.qt.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.qt.service.po.PriceSheet;
import com.ylxx.qt.service.po.ProductBean;

public interface PriceSheetMapper {
	
	//新增数据
	public void insertPro(List<PriceSheet> list) throws Exception;
	
	//查询所有品种价格
	public List<PriceSheet> getAll(@Param("proId")String proId,@Param("proName")String proName,@Param("priceType")Integer priceType,@Param("index")int index,@Param("pagecounts") int pagecounts) throws Exception;
	
	//查询某品种价格
	public List<PriceSheet> getByid(@Param("proId")String proId,@Param("index")int index,@Param("pagecounts") int pagecounts) throws Exception;
	
	//查询品种价格无分页
	public List<PriceSheet> getByid1(String proId)throws Exception;
	
	//删除某品种价格
	public void deleteByid(String proId) throws Exception;
	
	//更新某品种价格
	public void updateByid(List<PriceSheet> priceList) throws Exception;
	
	//查询数量
	public Integer selectCounts(@Param("proId")String proId,@Param("proName")String proName,@Param("priceType")Integer priceType) throws Exception;
	
	//查询所有数量
	public Integer selectCountsAll(String proId) throws Exception;
	
	//删除品种年份
	public void deleteByYear(String proId,Integer year) throws Exception;
	
	//查询品种
	public List<ProductBean> selectProducts(@Param("proId")String proId,@Param("proName")String proName) throws Exception;
	
	public List<ProductBean> productBypricesheet(@Param("proId")String proId,@Param("proName")String proName) throws Exception;

}
