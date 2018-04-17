package com.ylxx.fx.core.mapper.price.pricesource;
import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.CmmPrice;
public interface ExnmMapper {
	public List<CmmPrice> selectCMMPRICE(@Param("mkinfo")String mkinfo); 
	public List<Map<String,String>> selectPdtinfo();
}
