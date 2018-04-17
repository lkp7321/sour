package com.ylxx.fx.core.mapper.jsh;
import java.util.*;
import org.apache.ibatis.annotations.Param;
/**
 * 系统参数管理
 * @author lz130
 *
 */
public interface JshSystemMapper {
	/**
	 * 查询所有业务参数
	 * @return
	 */
	List<Map<String, Object>> selJsh_CmmPtpara();
	/**
	 * 查询系统总控状态
	 * @return
	 */
	Map<String, Object> sel_Jsh_system();
	/**
	 * 查询业务参数记录数
	 * @param paid
	 * @return
	 */
	int selJsh_CmmPtpara_count(@Param("paid")String paid);
	/**
	 * 修改业务参数
	 * @param valu
	 * @param stat
	 * @param paid
	 * @return
	 */
	int upsJsh_CmmPtpara(@Param("valu")String valu, @Param("stat")String stat, @Param("paid")String paid);
	/**
	 * 添加业务参数
	 * @param valu
	 * @param stat
	 * @param paid
	 * @param pasubid
	 * @param remk
	 * @return
	 */
	int insJsh_CmmPtpara(@Param("valu")String valu, @Param("stat")String stat, @Param("paid")String paid, @Param("pasubid")String pasubid, @Param("remk")String remk);
	/**
	 * 删除业务参数
	 * @param paid
	 * @return
	 */
	int delJsh_CmmPtpara(@Param("paid")String paid);
	//==========================================================================
	/**
	 * 查询所有币种
	 * @return
	 */
	List<Map<String, Object>> selJshCyMsg();
	/**
	 * 查询币种记录
	 * @param cyen
	 * @param cytp
	 * @return
	 */
	int selJshCyMsg_count(@Param("cyen")String cyen, @Param("cytp")String cytp);
	/**
	 * 添加币种
	 * @param cyen
	 * @param cytp
	 * @param cycn
	 * @param usfg
	 * @param remk
	 * @return
	 */
	int insJshCyMsg(@Param("cyen")String cyen, @Param("cytp")String cytp, @Param("cycn")String cycn, @Param("usfg")String usfg, @Param("remk")String remk);
	/**
	 * 修改币种
	 * @param usfg
	 * @param cytp
	 * @param cyen
	 * @param remk
	 * @return
	 */
	int upsJshCyMsg(@Param("usfg")String usfg, @Param("cytp")String cytp, @Param("cyen")String cyen, @Param("remk")String remk);
	/**
	 * 删除币种
	 * @param cyen
	 * @param cytp
	 * @return
	 */
	int delJshCyMsg(@Param("cyen")String cyen, @Param("cytp")String cytp);
	//==========================================================================
	/**
	 * 查询所有货币对
	 * @return
	 */
	List<Map<String, Object>> selJshCurrMsg();
	/**
	 * 查询货币对记录
	 * @param exnm
	 * @param excd
	 * @return
	 */
	int selJshCurrMsg_count(@Param("exnm")String exnm, @Param("excd")String excd);
	/**
	 * 添加货币对
	 * @param exnm
	 * @param excd
	 * @param excn
	 * @param pion
	 * @param extp
	 * @return
	 */
	int insJshCurrMsg(@Param("exnm")String exnm, @Param("excd")String excd, @Param("excn")String excn, @Param("pion")String pion, @Param("extp")String extp);
	/**
	 * 修改货币对
	 * @param exnm
	 * @param excd
	 * @param excn
	 * @param pion
	 * @param extp
	 * @return
	 */
	int upsJshCurrMsg(@Param("exnm")String exnm, @Param("excd")String excd, @Param("excn")String excn, @Param("pion")String pion, @Param("extp")String extp);
	/**
	 * 删除货币对
	 * @param exnm
	 * @param excd
	 * @return
	 */
	int delJshCurrmsg(@Param("exnm")String exnm, @Param("excd")String excd);
}
