package com.ylxx.fx.core.mapper.person.changk;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.PpsyBeanDomain;
import com.ylxx.fx.service.po.Ck_Dictionary;
import com.ylxx.fx.service.po.PpsyBean;
import com.ylxx.fx.service.po.TbCk_ppcontrol;
import com.ylxx.fx.service.po.TbCk_rulet;
import com.ylxx.fx.service.po.TrdSpcut;
import com.ylxx.fx.service.po.TrdTynm;

public interface ChangkMapper {
//--------------------------------查询敞口流水
	List<PpsyBean> selCondition(@Param("sartDate")String sartDate,@Param("endDate")String endDate,
			@Param("strExnm")String strExnm,@Param("lkno")String lkno);
	//查询敞口流水的直盘列表
	List<String> selUSDExnm(@Param("prcd")String prcd);
	
//--------------------------------平盘损益
	List<PpsyBeanDomain> selPpsy(@Param("sartDate")String sartDate,
			@Param("endDate")String endDate);
	PpsyBean selPptosy(@Param("sartDate")String sartDate,
			@Param("endDate")String endDate);
	
//--------------------------------敞口规则设置
	/**
	 * 查询敞口规则数据
	 * @param prcd
	 * @return
	 */
	List<TbCk_rulet> selck_rulet(@Param("prcd")String prcd);
	/**
	 * 查询添加左下拉框，初始化窗口，类型列表
	 * @param prcd
	 * @return
	 */
	List<Ck_Dictionary> selCkDictionary(@Param("prcd")String prcd);
	/**
	 * 查询账户列表
	 * @param prcd
	 * @return
	 */
	List<TrdTynm> sleTrdCustType(@Param("prcd")String prcd);
	/**
	 * 查询产品名称
	 * @param prcc
	 * @param prcd
	 * @return
	 */
	TbCk_rulet selRuleRecord(@Param("prcc")String prcc,
			@Param("prcd") String prcd);
	/**
	 * 添加,验证,判断是否重复
	 * @param ckrulet
	 * @return
	 */
	int inSelRulet(@Param("ckrulet")TbCk_rulet ckrulet);
	/**
	 * 添加分类敞口规则
	 * @param ckrulet
	 * @param data
	 * @param time
	 * @return
	 */
	int insClassTotal(@Param("ckrulet")TbCk_rulet ckrulet,
			@Param("data")String data,@Param("time")String time);
	/**
	 * 添加自动平盘规则
	 * @param ckrulet
	 * @return
	 */
	int insppControl(@Param("ckrulet")TbCk_rulet ckrulet);
	/**
	 * 添加敞口规则
	 * @param ckrulet
	 * @return
	 */
	int insCkRule(@Param("ckrulet")TbCk_rulet ckrulet);
	/**
	 * 修改前的验证，判断是否重复
	 * @param ckrulet
	 * @return
	 */
	int upSel(@Param("ckrulet")TbCk_rulet ckrulet);
	/**
	 * 修改敞口规则
	 * @param ckrulet
	 * @return
	 */
	int upCkRule(@Param("ckrulet")TbCk_rulet ckrulet);
	
	/**
	 * 规则级别设置查询
	 * @param prcd
	 * @return
	 */
	List<TbCk_rulet> selLeveCknm(@Param("prcd")String prcd);
	/**
	 * 规则级别设置
	 * @param list
	 * @throws Exception
	 */
	void upLevelCknm(@Param("list")List<TbCk_rulet> list)throws Exception;
	
//--------------------------------平盘规则设置
	/**
	 * 查询平盘规则数据
	 * @param prcd
	 * @param ckno
	 * @return
	 */
	List<TbCk_ppcontrol> selPpcontrolList(@Param("prcd")String prcd,
			@Param("ckno")String ckno);
	/**
	 * 查询页面的下拉框
	 * @param prcd
	 * @return
	 */
	List<TbCk_ppcontrol> selGroupCkno(@Param("prcd")String prcd);
	/**
	 * 修改平盘规则
	 * @param ppcon
	 * @return
	 */
	int upPpControl(@Param("ppcon")TbCk_ppcontrol ppcon);
	/**
	 * 修改平盘方式
	 * @param list
	 */
	void upPpconlist(@Param("list")List<TbCk_ppcontrol> list);
	
//--------------------------------特殊账户分类
	/**
	 * 查询特殊账户分类数据
	 * @param prcd
	 * @param apfg
	 * @return
	 */
	List<TrdTynm> selectTRD_TYNM(@Param("prcd")String prcd, @Param("apfg")String apfg);
	/**
	 * 删除特殊账户分类数据
	 * @param prcd
	 * @param trdtynm
	 * @return
	 */
	int deleteTRD_TYNM(@Param("prcd") String prcd,@Param("trdtynm")TrdTynm trdtynm);
	/**
	 * P001,添加前的校验
	 * @param prcd
	 * @param trdtynm
	 * @return
	 */
	int insel(@Param("prcd") String prcd, @Param("trdtynm")TrdTynm trdtynm);
	/**
	 * P002,P003,添加前的校验
	 * @param prcd
	 * @param trdtynm
	 * @return
	 */
	int insel1(@Param("prcd") String prcd, @Param("trdtynm")TrdTynm trdtynm);
	/**
	 * P001
	 * 特殊账号分类添加
	 * @param prcd
	 * @param trdtynm
	 * @return
	 */
	int insertTRD_TYNM(@Param("prcd") String prcd, @Param("trdtynm")TrdTynm trdtynm);
	/**
	 * P002,P003
	 * 特殊账号分类添加
	 * @param prcd
	 * @param trdtynm
	 * @return
	 */
	int insertTRD_TYNM2(@Param("prcd") String prcd, @Param("trdtynm")TrdTynm trdtynm);
	/**
	 * 特殊账户分类修改
	 * @param prcd
	 * @param trdtynm
	 * @return
	 */
	int updateTRD_TYNM(@Param("prcd") String prcd, @Param("trdtynm")TrdTynm trdtynm);
	
//--------------------------------特殊账户收集
	/**
	 * 特殊账号收集查询
	 * @param prcd
	 * @param cuno
	 * @return
	 */
	List<TrdSpcut> selectTRD_SPCUT(@Param("prcd")String prcd, @Param("cuno")String cuno);
	/**
	 * 特殊账号收集删除
	 * @param prcd
	 * @param cuno
	 * @return
	 */
	int deleteTRD_SPCUT(@Param("prcd")String prcd,@Param("cuno")String cuno);
	/**
	 * 特殊账号收集下拉框
	 * @param prcd
	 * @return
	 */
	List<TrdTynm> selectTYNM(@Param("prcd")String prcd);
	/**
	 * 特殊账号收集添加前的校验
	 */
	int selIns(@Param("prcd")String prcd, @Param("cuno")String cuno);
	/**
	 * 添加特殊账号收集
	 * @param prcd
	 * @param trdspcut
	 * @return
	 */
 	int insertTRD_SPCUT(@Param("prcd")String prcd, @Param("trdspcut")TrdSpcut trdspcut);
 	/**
 	 * 修改特殊账号收集
 	 * @param prcd
 	 * @param trdspcut
 	 * @return
 	 */
	int updateTRD_SPCUT(@Param("prcd")String prcd, @Param("trdspcut")TrdSpcut trdspcut);
}
