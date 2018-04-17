package com.ylxx.fx.core.mapper.person.system;
import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.BailExceBean;
import com.ylxx.fx.service.po.BailMt4Bean;
import com.ylxx.fx.service.po.BailTranlist;
import com.ylxx.fx.service.po.Cmmptparac;
public interface BailSystemMapper {
	//系统参数
	public List<Cmmptparac> selAllBailSysPrice();
	public int update(@Param("bean")Cmmptparac bean);
	
	//交易流水查询
	//查询交易码
	public List<Map<String,Object>> querytrancode();
	//查询数据
	public List<BailTranlist> selectFxipTranlist(@Param("acno")String acno, @Param("strcuac")String strcuac,
			@Param("trdtbegin")String trdtbegin, @Param("trdtend")String trdtend, 
			@Param("trcd")String trcd);
	//异常交易监控
	public List<BailExceBean> selectExceptionList(@Param("curDate")String curDate,@Param("toDate")String toDate);
	//Mt4
	public List<BailMt4Bean> selectMt4User();
	public int add(@Param("cmmbean")BailMt4Bean cmmbean);
	public int updateMt4(@Param("cmmbean")BailMt4Bean cmmbean);
	//异常交易处理
	public List<Map<String, Object>> querytrancodeExcep();
	public List<BailExceBean> setExceptionList(@Param("curDate")String curDate,
			@Param("toDate")String toDate,@Param("tranCode")String tranCode);
	public void updateInitialize2(@Param("list")List<BailExceBean> list);
	public void updateInitialize1(@Param("list")List<BailExceBean> list);
	public int updateInitialize3(@Param("lcno")String lcno,@Param("trdt")String trdt,@Param("trcd")String trcd);
	//成功处理
	public void updateSuccess(@Param("list")List<BailExceBean> list);
}
