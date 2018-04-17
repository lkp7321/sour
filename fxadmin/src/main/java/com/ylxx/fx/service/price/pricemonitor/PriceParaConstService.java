package com.ylxx.fx.service.price.pricemonitor;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.PdtChkparaForAcc;
import com.ylxx.fx.service.po.PdtPointForAcc;
import com.ylxx.fx.service.po.PdtPointTForAcc;

public interface PriceParaConstService {
	/**
	 * 外汇点差查询,下拉框
	 * @param ptid
	 * @return
	 */
	public List<Map<String,String>> getItems(String ptid);
	/**
	 * 外汇点差查询数据
	 * @param userKey
	 * @param ptid
	 * @param ptid1
	 * @return
	 */
	public List<PdtPointForAcc> getAllPdtPoint(String userKey,String ptid,String ptid1);
	public boolean upPoint(String userKey, String prod,PdtPointTForAcc pdtPoint);
	/**
	 * 点差生效
	 */
	public void SendSocketB1();
	/**
	 * 校验生效
	 */
	public void SendSocketB();
	/**
	 * 产品校验参数
	 * @param userKey
	 * @param prod
	 * @return
	 */
	List<PdtChkparaForAcc> getAllPdtChkpara(String userKey, String prod);
	//开启全部
	public boolean startAll(String userKey, String pdtinfoid);
	public boolean endAll(String userKey, String pdtinfoid); 
}
