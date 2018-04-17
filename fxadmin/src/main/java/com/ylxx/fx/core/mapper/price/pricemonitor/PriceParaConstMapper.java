package com.ylxx.fx.core.mapper.price.pricemonitor;
import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.PdtChkparaForAcc;
import com.ylxx.fx.service.po.PdtPointForAcc;
import com.ylxx.fx.service.po.PdtPointTForAcc;
public interface PriceParaConstMapper {
	//外汇点差查询
	public List<Map<String,String>> selectItems(@Param("ptid")String ptid);
	public List<PdtPointForAcc> selectAllPdtPoint(@Param("ptid")String ptid,@Param("ptid1")String ptid1);
	public int updatePoint(@Param("ptid")String ptid,@Param("pdtPoint")PdtPointTForAcc pdtPoint);
	/**
	 * 产品校验参数
	 * @param pdtinfoid
	 * @return
	 */
	List<PdtChkparaForAcc> selectAllPdtChkpara(@Param("pdtinfoid")String pdtinfoid );
	//开启全部
	public int openAll(@Param("pdtinfoid")String pdtinfoid);
	public int stopAll(@Param("pdtinfoid")String pdtinfoid); 
	
	
	
}
