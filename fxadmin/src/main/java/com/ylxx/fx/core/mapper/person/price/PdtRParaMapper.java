package com.ylxx.fx.core.mapper.person.price;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.CurrmsgBean;
import com.ylxx.fx.service.po.FiltrateBean;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.service.po.MktinfoBean;
import com.ylxx.fx.service.po.PdtChkParaBean;
import com.ylxx.fx.service.po.PdtCtrlPriTBean;
import com.ylxx.fx.service.po.PdtPointBean;
import com.ylxx.fx.service.po.PdtRParaTBean;
import com.ylxx.fx.service.po.PdtStoperBean;
import com.ylxx.fx.service.po.PdtinfoBean;
import com.ylxx.fx.service.po.MaxFavPointBean;

public interface PdtRParaMapper{
	
	/**
	 * 非P999，非P007
	 * 查询报价参数
	 * @param prod
	 * @return
	 * @throws Exception
	 */
	List<PdtRParaTBean> selectPriceNew(@Param("prod")String prod)throws Exception;
	/**
	 * P007
	 * 查询报价参数:账户交易 >>报价管理
	 * @param prod
	 * @return
	 * @throws Exception
	 */
	List<PdtRParaTBean> selAccExPrice(@Param("prod")String prod)throws Exception;
	//P999查询产品列表
	public List<HashMap<String, String>> pdtCom() throws Exception;
	//根据货币对对名称（主键）查询记录（以获得币别对类型）
	public CurrmsgBean selectObjPrice(@Param("prod")String prod,@Param("exnm")String exnm)throws Exception;
	//查询货币对
	public CurrmsgBean selectObjPri(@Param("exnm")String exnm) throws Exception;
	//查询产品信息
	public PdtinfoBean selectObjP(@Param("prod")String prod) throws Exception;
	//【校验】页面数据的查询
	public PdtChkParaBean selectPrice(@Param("pdtChkParaBean")PdtChkParaBean pdtChkParaBean,@Param("ptid")String ptid) throws Exception;
	//更新 【校验】 数据
	public boolean updatePrice(@Param("pdtChkParaBean")PdtChkParaBean pdtChkParaBean,@Param("ptid")String ptid) throws Exception;
	//插入 【校验】 数据
	public boolean insertPrice(@Param("pdtChkParaBean")PdtChkParaBean pdtChkParaBean,@Param("ptid")String ptid) throws Exception;
	//【停牌】页面数据的查询
	public PdtStoperBean selectStopPrice(@Param("pdtStoperBean")PdtStoperBean pdtStoperBean,@Param("ptid")String ptid) throws Exception;
	//更新【停牌】页面
	public boolean updateStopPrice(@Param("pdtStoperBean")PdtStoperBean pdtStoperBean,@Param("ptid")String ptid) throws Exception;
	//插入【停牌】页面
	public boolean insertStopPrice(@Param("pdtStoperBean")PdtStoperBean pdtStoperBean,@Param("ptid")String ptid) throws Exception;
	//【点差】页面初始化
	public PdtPointBean selectPointPrice(@Param("pdtPointBean")PdtPointBean pdtPointBean,@Param("ptid")String ptid) throws Exception;
	/**
	 * 点差
	 * 更新【点差】数据
	 * @param pdtPointBean
	 * @param ptid
	 * @return
	 * @throws Exception
	 */
	boolean updatePointPrice(@Param("pdtPointBean")PdtPointBean pdtPointBean,@Param("ptid")String ptid) throws Exception;
	//添加【点差】数据
	public boolean insertPointPrice(@Param("pdtPointBean")PdtPointBean pdtPointBean,@Param("ptid")String ptid) throws Exception;
	//最大优惠点差表查询
	public List<MaxFavPointBean> selectMaxpavpoint(@Param("prod")String prod) throws Exception;
	/**
	 * 点差
	 * 更新总行客户点差、总行分行点差时更新分行最大优惠点差 
	 * @param prod
	 * @param mxfv
	 * @param exnm
	 * @return
	 * @throws Exception
	 */
	boolean upMaxpavpointByExnm(@Param("prod")String prod,@Param("mxfv")String mxfv,@Param("exnm")String exnm) throws Exception;
	//条件查询币别对的数据
	public PdtRParaTBean selectPrice1(@Param("pdtrPara")PdtRParaTBean pdtrPara,@Param("ptid")String ptid) throws Exception;
	//条件查询币别对的数据:账户交易>>报价管理
	public PdtRParaTBean selectAccExPrice(@Param("pdtrPara")PdtRParaTBean pdtrPara,@Param("ptid")String ptid) throws Exception;	
	//根据市场编号(报价源)查询市场信息表
	public MktinfoBean selectObjPrice1(@Param("mkid")String mkid) throws Exception;
	//查询所有价格源公告板表表名
	public List<String> getMarkTab() throws Exception;
	//查询产品市场源头MKNM（、市场名称MKID）
	public MktinfoBean selMark(@Param("mktab")String mktab,@Param("tpfg")String tpfg, @Param("cxfg")String cxfg,@Param("exnm")String exnm) throws Exception;
	//查询币别对序号
	public String selectExse(@Param("prod")String prod,@Param("exnm")String exnm) throws Exception;
	/**
	 * 保存的（策略）
	 * 修改币别对的数据
	 * @param pdtrPara
	 * @param ptid
	 * @return
	 * @throws Exception
	 */
	boolean updateMarkPrice(@Param("pdtrPara")PdtRParaTBean pdtrPara,@Param("ptid")String ptid) throws Exception;
	//修改币别对的数据 : 账户交易>>报价管理
	public boolean updateAccExPrice(@Param("pdtrPara")PdtRParaTBean pdtrPara,@Param("ptid")String ptid) throws Exception;
	//添加产品币别对的数据
	public boolean insertMarkPrice(@Param("pdtrPara")PdtRParaTBean pdtrPara,@Param("ptid")String ptid) throws Exception;
	//写审计日志
	public boolean insertMarkLogger(@Param("logfileBean")LogfileBean logfileBean) throws Exception;
	//查询当前策略
	public PdtRParaTBean selectGMNM(@Param("ptid")String ptid,@Param("exnm")String exnm) throws Exception;
	//查询所有策略信息
	public List<FiltrateBean> selectPriceUs() throws Exception;
	//查询实盘产品价格干预器
	public PdtCtrlPriTBean selectCtrlPrice(@Param("pcpri")PdtCtrlPriTBean pcpri,@Param("ptid")String ptid) throws Exception;
	//更新【干预】数据
	public boolean updateCtrlPrice(@Param("pcpri")PdtCtrlPriTBean pcpri,@Param("ptid")String ptid) throws Exception;
	//添加【干预】数据
	public boolean insertCtrlPrice(@Param("pcpri")PdtCtrlPriTBean pcpri,@Param("ptid")String ptid) throws Exception;
	//根据价格类型名称获取价格类型
	public String getTpfgByTpnm(@Param("tpnm")String tpnm) throws Exception;
	//根据策略名称查询策略编号
	public String getGmidByGmnm(@Param("gmnm")String gmnm) throws Exception;
}