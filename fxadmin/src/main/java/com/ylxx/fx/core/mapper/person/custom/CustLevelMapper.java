package com.ylxx.fx.core.mapper.person.custom;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.CustLevelBean;
import com.ylxx.fx.service.po.LogfileBean;

public interface CustLevelMapper{
	//获取所有客户级别信息
	public List<CustLevelBean> SelectCustLevelList(@Param("prod")String prod) throws Exception;
	//检查数据是否重复
	public List<CustLevelBean> checkRepeat(@Param("prod")String prod,@Param("id")String id,@Param("lvnm")String lvnm) throws Exception;
	//添加客户级别信息
	public int InsertCustLevel(@Param("prod")String prod,@Param("custLevelBean")CustLevelBean custLevelBean) throws Exception; 
	//写审计日志
	public boolean InsertCustLevelLogger(@Param("logfileBean")LogfileBean logfileBean) throws Exception;
	//修改客户级别信息
	public boolean updateCustLevel(@Param("prod")String prod,@Param("custLevelBean")CustLevelBean custLevelBean) throws Exception;
	//检查修改后的数据是否存在重复
	public List<CustLevelBean> cheUpRepeat(@Param("prod")String prod,@Param("id")String id,@Param("lvnm")String lvnm) throws Exception;
	//删除客户级别信息
	public int deleteCustLevel(@Param("prod")String prod,@Param("cuty")String cuty) throws Exception;
	
}

