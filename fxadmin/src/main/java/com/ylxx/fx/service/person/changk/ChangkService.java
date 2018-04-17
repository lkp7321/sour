package com.ylxx.fx.service.person.changk;

import java.util.List;

import com.ylxx.fx.core.domain.PpsyBeanDomain;
import com.ylxx.fx.service.po.PpsyBean;
import com.ylxx.fx.service.po.TbCk_ppcontrol;
import com.ylxx.fx.service.po.TbCk_rulet;
import com.ylxx.fx.service.po.TrdSpcut;
import com.ylxx.fx.service.po.TrdTynm;
import com.ylxx.fx.utils.CurrUser;

public interface ChangkService {
	
	//敞口流水
	public String getUSDExnm(String userKey);
	public List<PpsyBean> getCondition(String userKey,String sartDate,String endDate,
			String strExnm,String lkno);
	
	/**
	 * 查询敞口规则
	 * @param userKey
	 * @return
	 */
	String getSelck_rulet(String userKey);
	/**
	 * 查询列表，账户，币别，折美元
	 * @param userKey
	 * @return
	 */
	String getCkdictionary(String userKey);
	/**
	 * 查询账户列表
	 * @param userKey
	 * @return
	 */
	String getTrdCusType(String userKey);
	/**
	 * 查询产品名称
	 * @param userKey
	 * @return
	 */
	String getProdCkno(String userKey);
	/**
	 * 敞口规则添加
	 * @param curUser
	 * @param ckRulet
	 * @param ip
	 * @return
	 */
	String insertCkRuleComm(CurrUser curUser,TbCk_rulet ckRulet, String ip);
	/**
	 * 敞口规则修改
	 * @param curUser
	 * @param ckrulet
	 * @param ip
	 * @return
	 */
	String upCkRuleComm(CurrUser curUser,TbCk_rulet ckrulet, String ip);
	/**
	 * 敞口规则等级查询
	 * @param userKey
	 * @return
	 */
	String getRuletLevel(String userKey);
	/**
	 * 修改敞口规则
	 * @param curUser
	 * @param ruletList
	 * @param ip
	 * @return
	 */
	String setRuletLevel(CurrUser curUser,List<TbCk_rulet> ruletList, String ip);
	
	/**
	 * 平盘规则
	 * @param curUser
	 * @param ckno
	 * @return
	 */
	String getppCon(CurrUser curUser,String ckno,Integer pageNo, Integer pageSize);
	String getSelect(String userKey);
	boolean upPPconlist(CurrUser curUser, List<TbCk_ppcontrol> listppcon,String ip);
	String upPPcon(CurrUser curUser, TbCk_ppcontrol ppcon, String ip);
	/**
	 * 特殊账户分类，查询
	 * @param curUser
	 * @param apfg
	 * @return
	 */
	String getTRD_TYNM(CurrUser curUser,String apfg);
	/**
	 * 特殊账户分类，删除
	 * @param curUser
	 * @param trdtynm
	 * @return
	 */
	String deleteT_TYNM(CurrUser curUser,TrdTynm trdtynm);
	/**
	 * 特殊账户分类，添加
	 * @param curUser
	 * @param trdtynm
	 * @return
	 */
	String insertT_TYNM(CurrUser curUser,TrdTynm trdtynm);
	/**
	 * 特殊账户分类，修改
	 * @param curUser
	 * @param trdtynm
	 * @return
	 */
	String updateT_TYNM(CurrUser curUser,TrdTynm trdtynm);
	//特殊账户收集
	public String getTRD_SPCUT(CurrUser curUser,String cuno);
	public String deleteTRD_SPCUT(CurrUser curUser, String cuno);
	public String getTrdTynm(String userKey);
	public String insertTrdSpcut(CurrUser curUser, TrdSpcut trdspcut);
	public String updateTrdSpcut(CurrUser curUser, TrdSpcut trdspcut);
	//平盘损益
	public List<PpsyBeanDomain> getPpsy(String userKey,String start,String end);
	public String getPpTosy(CurrUser curUser,String start,String end);
}
