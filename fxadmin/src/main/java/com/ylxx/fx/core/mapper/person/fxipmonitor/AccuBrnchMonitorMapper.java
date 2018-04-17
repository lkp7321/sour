package com.ylxx.fx.core.mapper.person.fxipmonitor;
import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.FormuleVo;
import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.service.po.AccinfoMonitorBean;
import com.ylxx.fx.service.po.ChangeInfoList;
import com.ylxx.fx.service.po.CkTotalBean;
import com.ylxx.fx.service.po.QYInfoList;
import com.ylxx.fx.service.po.Trd_ogcd;
/*
 * 积存金（P003） 
 * 价格监控
 */
public interface AccuBrnchMonitorMapper {
	/**
	 * 总分行价格
	 * @return
	 */
	List<FxipMonitorVo> selectAccumAllPdtBrnch();
	/**
	 * 客户价格
	 * @return
	 */
	List<FxipMonitorVo> selectAccumAllPdtCust();
	//总敞口
	/**
	 * 查询市场报价
	 * @return
	 */
	List<CkTotalBean> ckTotalData();
	/**
	 * 总敞口数据查询
	 * @return
	 */
	List<CkTotalBean> ckTotalYk();
	public int updateCKZCYK(@Param("ckTotalBean")CkTotalBean ckTotalBean);
	public int insertTQTranList(@Param("usnm")String usnm,@Param("ckTotalBean")CkTotalBean ckTotalBean);
	/**
	 * 查询美元人民币
	 * 中间价
	 * @return
	 */
	double USDCNYNEMD();
	/**
	 * 分类敞口（敞口列表）
	 * @return
	 */
	List<Map<String,Object>> accumCknoTree();
	/**
	 * 分类敞口（数据）
	 * @param prcd
	 * @param ckno
	 * @return
	 */
	List<FormuleVo> selectclassCk(@Param("prcd")String prcd,@Param("ckno")String ckno);
	
	/**
	 * 账户信息统计（查询等级）
	 * @param orgn
	 * @return
	 */
	String selLevel(@Param("orgn")String orgn);
	/**
	 * 账户信息统计（查询机构）
	 * @param orgn
	 * @return
	 */
	Trd_ogcd selOgcdNm(@Param("orgn")String orgn);
	/**
	 * 账户信息统计,机构等级为1，一级分行
	 * @param date
	 * @param orgn
	 * @return
	 */
	List<AccinfoMonitorBean> selAccumTrdLevel1(@Param("date")String date,@Param("orgn")String orgn);
	/**
	 * 账户信息统计,机构等级为0，总行
	 * @param date
	 * @return
	 */
	List<AccinfoMonitorBean> selAccumTrdLevel2(@Param("date")String date);
	/**
	 * 交易信息统计，机构等级为0
	 * @param dateBegin
	 * @param dateEnd
	 * @return
	 */
	List<ChangeInfoList> selChangeInfoList1(@Param("dateBegin")String dateBegin, @Param("dateEnd")String dateEnd);
	/**
	 * 交易信息统计，机构等级为1
	 * @param dateBegin
	 * @param dateEnd
	 * @param comaogcd
	 * @return
	 */
	List<ChangeInfoList> selChangeInfoList2(@Param("dateBegin")String dateBegin, 
			@Param("dateEnd")String dateEnd, @Param("comaogcd")String comaogcd);
	/**
	 * 签约信息统计,机构等级为0
	 * @param ogcd
	 * @param ognm
	 * @param trdtbegin
	 * @return
	 */
	List<QYInfoList> selqy1(@Param("ogcd")String ogcd,@Param("ognm")String ognm,@Param("trdtbegin")String trdtbegin);
	/**
	 * 签约信息统计，机构等级为1
	 * @param ogcd
	 * @param trdtbegin
	 * @return
	 */
	List<QYInfoList> selqy2(@Param("ogcd")String ogcd,@Param("trdtbegin")String trdtbegin);
}
