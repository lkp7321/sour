package com.ylxx.fx.core.mapper.jsh;


import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.jsh.TbTrd_safeAccinfo;
import com.ylxx.fx.service.po.jsh.Trd_errorstate;
import com.ylxx.fx.service.po.jsh.Trd_tranlist;
/**
 * 国别管理
 * @author lz130
 *
 */
public interface JshTradeMapper {
	/**
	 * 添加国别
	 * @param name
	 * @param cmbccout
	 * @param cout
	 * @param copycout
	 * @return
	 */
	boolean insetrcout(@Param("name")String name, @Param("cmbccout")String cmbccout, @Param("cout")String cout, @Param("copycout")String copycout);
	/**
	 * 修改国别
	 * @param name
	 * @param cmbccout
	 * @param cout
	 * @param copycout
	 * @return
	 */
	boolean updatecout(@Param("name")String name, @Param("cmbccout")String cmbccout, @Param("cout")String cout, @Param("copycout")String copycout);
	/**
	 * 查询国别信息
	 * @param name
	 * @return
	 */
	List<HashMap<String, String>> selectcout(@Param("name")String name);
	/**
	 * 删除国别
	 * @param cmbccout
	 */
	void deletecout(@Param("cmbccout")String cmbccout);
	
	/**
	 * 查询告警信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<Trd_errorstate> selecterror(@Param("trdt")String trdt, @Param("lcno")String lcno);
	/**
	 * 查询告警信息头
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	String selecterrorcode(@Param("ERCD")String ERCD);
	/**
	 * 删除告警信息
	 * @param originatechannelserialno
	 * @return
	 */
	void deleteerror(@Param("originatechannelserialno")String originatechannelserialno);
	/**
	 * 外管局交易流水号 
	 * @param originatechannelserialno
	 */
	String getmsgno();
	/**
	 * 查询流水记录
	 * @param pageNo
	 * @param pageSize
	 * @param trdt
	 * @param trsn
	 * @param cuno
	 * @param ercd
	 * @return
	 */
	List<Trd_tranlist> selecttranlist(@Param("trdt")String trdt, @Param("trsn")String trsn,@Param("cuno")String cuno,@Param("ercd")String ercd);
	
	/**
	 * 更新流水表中的记录状态及错误码
	 */
	boolean upTranlist(@Param("trsn")String trsn) throws Exception;
	
	/**
	 * 更新告警表的处理标记
	 */
	boolean upError(@Param("trsn")String trsn) throws Exception;
	
	/**
	 * 查询柜员号、密码
	 * @param bhid
	 * @param chnl
	 * @return
	 * @throws Exception
	 */
	TbTrd_safeAccinfo getLoginOgcd(@Param("bhid")String bhid,@Param("chnl")String chnl) throws Exception;
	
	/**
	 * 获取流水号
	 * @return
	 * @throws Exception
	 */
	String getMsgno() throws Exception;
	/**
	 * 根据流水号查询流水表
	 * @return
	 * @throws Exception
	 */
	String getlist(@Param("trsn")String trsn) throws Exception;
	/**
	 * 根据流水号查询业务参号
	 * @return
	 * @throws Exception
	 */
	String getfxid(@Param("trsn")String trsn) throws Exception;
}
