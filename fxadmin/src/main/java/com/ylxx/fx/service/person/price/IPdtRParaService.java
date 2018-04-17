package com.ylxx.fx.service.person.price;

import java.util.List;

import com.ylxx.fx.service.po.MktinfoBean;
import com.ylxx.fx.service.po.PdtChkParaBean;
import com.ylxx.fx.service.po.PdtCtrlPriTBean;
import com.ylxx.fx.service.po.PdtPointBean;
import com.ylxx.fx.service.po.PdtRParaTBean;
import com.ylxx.fx.service.po.PdtStoperBean;
/**
 * 报价参数设置
 * @author lz130
 *
 */
public interface IPdtRParaService {
	/**
	 * 分页显示查询报价参数
	 * @param prod
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	String addpage(String prod,Integer pageNo,Integer pageSize) throws Exception;
	/**
	 * P999查询产品列表
	 * @return
	 * @throws Exception
	 */
	String pdtCom() throws Exception;
	/**
	 * 报价参数设置中读取币种交叉盘标识从产品币种表中读取:根据币别对名称（主键）查询记录（以获得币别对类型）*
	 * @param prod
	 * @param exnm
	 * @return
	 * @throws Exception
	 */
	String selectObjPdtr(String prod,String exnm) throws Exception;
	/**
	 * 查询货币对类型
	 * @param exnm
	 * @return
	 * @throws Exception
	 */
	String selectObjPdtr2(String exnm) throws Exception;
	/**
	 * 选中数据后点击修改，【校验】页面初始化
	 * @param exnm
	 * @param prod
	 * @return
	 * @throws Exception
	 */
	String getCurChk(String exnm,String prod) throws Exception;
	/**
	 * 保存 【校验】 修改
	 * @param userKey
	 * @param prod
	 * @param pdtChk
	 * @param exnm
	 * @return
	 * @throws Exception
	 */
	String saveChkPara(String userKey,String prod,PdtChkParaBean pdtChk,String exnm)throws Exception;
	/**
	 * 【停牌】页面初始化
	 * @param prod
	 * @param exnm
	 * @param stid
	 * @return
	 * @throws Exception
	 */
	String getCurStop(String prod,String exnm,String stid) throws Exception;
	/**
	 * 保存【停牌】修改
	 * @param userKey
	 * @param prod
	 * @param pdtStoper
	 * @return
	 * @throws Exception
	 */
	String saveStop(String userKey,String prod,PdtStoperBean pdtStoper) throws Exception;
	/**
	 * 【点差】页面初始化
	 * @param prod
	 * @param exnm
	 * @return
	 * @throws Exception
	 */
	String getCurPoint(String prod,String exnm) throws Exception;
	/**
	 * 保存【点差】修改
	 * @param userKey
	 * @param prod
	 * @param pdtPointBean
	 * @return
	 * @throws Exception
	 */
	String savePoint(String userKey,String prod,PdtPointBean pdtPointBean) throws Exception;
	/**
	 * 产品市场源头选择，返回当前市场信息
	 * @param prod
	 * @param exnm
	 * @return
	 * @throws Exception
	 */
	String curMkList(String prod, String exnm)throws Exception;
	/**
	 * 产品价格源
	 * @param prod
	 * @param exnm
	 * @return
	 * @throws Exception
	 */
	PdtRParaTBean pdtPara(String prod,String exnm) throws Exception;
	/**
	 * 返回所有市场信息
	 * @param prod
	 * @param exnm
	 * @return
	 * @throws Exception
	 */
	String allMkList(String prod,String exnm) throws Exception;
	/**
	 * 获取产品市场源头MKNM（、市场名称MKID）
	 * @param exnm
	 * @param tpfg
	 * @param cxfg
	 * @return
	 * @throws Exception
	 */
	List<MktinfoBean> selMark(String exnm, String tpfg, String cxfg) throws Exception;
	/**
	 * 保存【产品市场源头选择】数据
	 * @param userKey
	 * @param prod
	 * @param pdtRParaTBean
	 * @return
	 * @throws Exception
	 */
	String saveMarket(String userKey,String prod,PdtRParaTBean pdtRParaTBean) throws Exception;
	/**
	 * 查询当前策略
	 * @param ptid
	 * @param exnm
	 * @return
	 * @throws Exception
	 */
	String selectGMNM(String ptid,String exnm)throws Exception;
	/**
	 * 查询所有策略信息
	 * @return
	 * @throws Exception
	 */
	String selectPriceUs() throws Exception;
	/**
	 * 保存【策略】的修改
	 * @param userKey
	 * @param prod
	 * @param pdt
	 * @param exnm
	 * @return
	 * @throws Exception
	 */
	String saveGmnm(String userKey,String prod,PdtRParaTBean pdt,String exnm) throws Exception;
	/**
	 * 【干预】页面初始化:返回当前干预器
	 * @param prod
	 * @param ctid
	 * @param exnm
	 * @return
	 * @throws Exception
	 */
	String curPdtCtrl(String prod,String ctid,String exnm) throws Exception;
	/**
	 * 保存【干预】的修改
	 * @param userKey
	 * @param prod
	 * @param ctrl
	 * @return
	 * @throws Exception
	 */
	String saveCtrl(String userKey,String prod,PdtCtrlPriTBean ctrl) throws Exception;
	/**
	 * 根据价格类型名称获取价格类型
	 * @param tpnm
	 * @return
	 * @throws Exception
	 */
	String getTpfgByTpnm(String tpnm) throws Exception;
	/**
	 * 根据策略名称查询策略编号
	 * @param gmnm
	 * @return
	 * @throws Exception
	 */
	String getGmidByGmnm(String gmnm) throws Exception;
	/**
	 * 账户交易->报价管理->报价参数设置:生效
	 * @return
	 * @throws Exception
	 */
	String SendAccExPdtRparaSocket() throws Exception;
}

