package com.ylxx.fx.service.jsh;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.po.jsh.Currmsg;
import com.ylxx.fx.service.po.jsh.Cytp;
import com.ylxx.fx.service.po.jsh.ErrorCode;
import com.ylxx.fx.service.po.jsh.Cmm_Ptpara;
import com.ylxx.fx.service.po.jsh.JshPages;

public interface JshCmmSysService {
	
	/**
	 * 分页：
	 * 获取所有业务参数
	 * @param cmm_Ptpara
	 * @return
	 */
	List<Map<String, Object>> selectCmmPtparaList();
	/**
	 * 获取系统总控状态
	 * @return
	 */
	Map<String, Object> selectCmmPtparaSystem();
	/**
	 * 添加业务参数
	 * @param cmm_Ptpara
	 * @return
	 */
	String insertCmmPtparaList(JshPages<Cmm_Ptpara> cmm_Ptpara);
	/**
	 * 修改业务参数
	 * @param cmm_Ptpara
	 * @return
	 */
	String updateCmmPtparaList(JshPages<Cmm_Ptpara> cmm_Ptpara);
	/**
	 * 删除业务参数
	 * @param cmm_Ptpara
	 * @return
	 */
	String deleteCmmPtparaList(JshPages<Cmm_Ptpara> cmm_Ptpara);
	/**
	 * 查询所有币种
	 * @return
	 */
	List<Map<String, Object>> selectCmmCymsgList();
	/**
	 * 修改币种
	 * @param cytp
	 * @return
	 */
	String updateCmmCymsgList(JshPages<Cytp> cytp);
	/**
	 * 添加币种
	 * @param cytp
	 * @return
	 */
	String insertCmmCymsgList(JshPages<Cytp> cytp);
	/**
	 * 删除币种
	 * @param cytp
	 * @return
	 */
	String deleteCmmCymsgList(JshPages<Cytp> cytp);
	/**
	 * 获取所有货币对
	 * @return
	 */
	List<Map<String, Object>> selectJshCurrMsgList();
	/**
	 * 添加货币对
	 * @param currmsg
	 * @return
	 */
	String insertJshCurrMsgList(JshPages<Currmsg> currmsg);
	/**
	 * 修改货币对
	 * @param currmsg
	 * @return
	 */
	String updateJshCurrMsgList(JshPages<Currmsg> currmsg);
	/**
	 * 删除货币对
	 * @param currmsg
	 * @return
	 */
	String deleteJshCurrMsgList(JshPages<Currmsg> currmsg);
	/**
	 * 分页查询错误码
	 * @param errorMsg
	 * @return
	 */
	PageInfo<Map<String, Object>> selectJshErrorCodeList(JshPages<ErrorCode> errorMsg);
	/**
	 * 添加错误码
	 * @param errorMsg
	 * @return
	 */
	String insertJshErrorCode(JshPages<ErrorCode> errorMsg);
	/**
	 * 添加错误码
	 * @param errorMsg
	 * @return
	 */
	String updateJshErrorCode(JshPages<ErrorCode> errorMsg);
	/**
	 * 添加错误码
	 * @param errorMsg
	 * @return
	 */
	String deleteJshErrorCode(JshPages<ErrorCode> errorMsg);

}
