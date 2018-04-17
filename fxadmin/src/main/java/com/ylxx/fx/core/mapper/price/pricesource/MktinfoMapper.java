package com.ylxx.fx.core.mapper.price.pricesource;
import java.util.*;
import org.apache.ibatis.annotations.Param;
import com.ylxx.fx.service.po.CmmChkpara;
import com.ylxx.fx.service.po.Mktinfo;
/**
 * 报价平台--价格源管理
 */
public interface MktinfoMapper {
	public List<Mktinfo>selectMkPrice();
	//校验器数据————市场和货币对都为所有
	public List<CmmChkpara> jiaoyansel(@Param("mkid")String mkid,@Param("exnm")String exnm);
	//校验器数据———市场和货币对不为所有
	public List<CmmChkpara> jiaoyansel1(@Param("mkid")String mkid,@Param("exnm")String exnm);
	
	public int updateUsfg(@Param("ppch")CmmChkpara ppch);
	
	public int insertPrice(@Param("cmmbean")CmmChkpara cmmbean);
	public int updatePrice(@Param("cmmbean")CmmChkpara cmmbean);
	public int selectPrice(@Param("cmmbean")CmmChkpara cmmbean);
	
	public List<Map<String,Object>> selUpType();
	public int selectPION(@Param("exnm")String exnm);
}
