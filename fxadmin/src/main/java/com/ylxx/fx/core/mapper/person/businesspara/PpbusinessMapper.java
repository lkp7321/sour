package com.ylxx.fx.core.mapper.person.businesspara;
import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.CK_handMxdetail;
import com.ylxx.fx.service.po.QutCmmPrice;
public interface PpbusinessMapper {
	//平盘交易查询
	public List<CK_handMxdetail> seldate(@Param("apdt")String apdt,@Param("jsdt")String jsdt);
	//原油手工平盘
	public List<QutCmmPrice> selectPrice();
}
