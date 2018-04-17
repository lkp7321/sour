package com.ylxx.fx.service.person.system;

import java.util.*;

import com.ylxx.fx.service.po.BailExceBean;
import com.ylxx.fx.service.po.BailMt4Bean;
import com.ylxx.fx.service.po.BailTranlist;
import com.ylxx.fx.service.po.Cmmptparac;

public interface BailSystemService {
	//系统参数设置
	public List<Cmmptparac> getAllBailSysPrice();
	public boolean updates(String userKey, Cmmptparac bean);
	/////////
	//交易码
	public List<Map<String,Object>> querytrancodes();
	//交易流水
	public List<BailTranlist> getsFxipTranlist(String acno, String strcuac,
			String trdtbegin,String trdtend, 
			String trcd);
	//交易异常监控
	public List<BailExceBean> bailExceptionData(String curDate,String toDate);
	//mt4用户组设置
	public List<BailMt4Bean> queryBailMt4Para();
	public boolean insertPtpara(String userKey, BailMt4Bean cmmbean);
	public boolean updatePtpara(String userKey, BailMt4Bean cmmbean);
	//异常交易处理
	public List<Map<String, Object>> seltrancodes();
	public List<BailExceBean> bailExceptionData(String curDate,String tranCode,String toDate); 
	public String  updateInitialize(String userKey,String tranCode,List<BailExceBean> bailExceList);
	public String updateSuccess(String userKey,String tranCode,List<BailExceBean> bailExceList);
}
