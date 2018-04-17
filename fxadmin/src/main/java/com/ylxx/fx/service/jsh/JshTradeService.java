package com.ylxx.fx.service.jsh;

import java.util.List;


import com.afcat.jsh.po.ForfxsaInfoin;
import com.ylxx.fx.service.po.jsh.JshPages;
import com.ylxx.fx.service.po.jsh.Trd_errorstate;
import com.ylxx.fx.service.po.jsh.Trd_tranlist;
import com.ylxx.fx.service.po.jsh.WgCountry;
/**
 * 国别管理
 * @author lz130
 *
 */
public interface JshTradeService {
	/**
	 * 添加国别
	 * @param wgCountry
	 * @return
	 */
	String insetrcout(JshPages<WgCountry> wgCountry);
	/**
	 * 修改国别
	 * @param wgCountry
	 * @return
	 */
	String updatecout(JshPages<WgCountry> wgCountry);
	/**
	 * 查询国别
	 * @param pageNo
	 * @param pageSize
	 * @param name
	 * @return
	 */
	String selectcout(Integer pageNo, Integer pageSize, String name);
	/**
	 * 删除国别
	 * @param wgCountry
	 * @return
	 */
	String deletecout(JshPages<WgCountry> wgCountry);
	/*===========告警/流水===============*/
	/**
	 * 查询告警表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	String selecterror(Integer pageNo, Integer pageSize, String trdt, String lcno);
	/**
	 * 导出Excel
	 * @return
	 */
	List<Trd_errorstate> getAllWgErrorList(String trdt, String lcno);
	/**
	 * 删除告警信息
	 * @param wgCountry
	 * @return
	 */
	String deleteerror(JshPages<WgCountry> wgCountry);
	/**
	 * 查询流水
	 * @param jshTranList
	 * @return
	 */
	String selecttranlist(Integer pageNo, Integer pageSize,String ercd,String cuno,String trsn,String trdt);
	/**
	 * 导出Excel
	 * @param ercd
	 * @param cuno
	 * @param trsn
	 * @param trdt
	 * @return
	 */
	List<Trd_tranlist> getAllWgTranList(String ercd, String cuno, String trsn, String trdt);
	/**
	 * 购汇冲销外管局
	 * @param jshTranList
	 * @return
	 */
	String forfxsaInfoin(ForfxsaInfoin forfxsaInfoin);
	
	/**
	 * 更新流水表中的记录状态及错误码，告警表的处理标记
	 * @param trsn
	 * @return
	 */
	String upErrorTranlist(String trsn);
	/**
	 * 查询柜员号、密码
	 * @param bhid
	 * @param chnl
	 * @return
	 */
	String getLoginOgcd(String bhid,String chnl);

}
