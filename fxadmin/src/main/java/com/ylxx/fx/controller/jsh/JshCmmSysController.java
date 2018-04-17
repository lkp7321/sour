package com.ylxx.fx.controller.jsh;

import javax.annotation.Resource;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.jsh.JshCmmSysService;
import com.ylxx.fx.service.po.jsh.Currmsg;
import com.ylxx.fx.service.po.jsh.Cytp;
import com.ylxx.fx.service.po.jsh.ErrorCode;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.jsh.Cmm_Ptpara;
import com.ylxx.fx.service.po.jsh.JshPages;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 系统管理
 * @author lz130
 *
 */
@Controller
public class JshCmmSysController {
	private static final Logger log = LoggerFactory.getLogger(JshCmmSysController.class);
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	@Resource(name="jshCmmSysService")
	private JshCmmSysService jshCmmSysService;
	
	/**
	 * 查询业务参数
	 * @param cmm_Ptpara
	 * @return
	 */
	@RequestMapping(value="/jsh/getCmmPtpara.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getJshCmmPtparaList() {
		log.info("开始查询业务参数");
		List<Map<String, Object>> list = jshCmmSysService.selectCmmPtparaList();
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	/**
	 * 查询系统总控
	 * @return
	 */
	@RequestMapping(value="/jsh/getCmmSystem.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getCmmSystem() {
		log.info("开始查询系统总控");
		Map<String, Object>  entity = jshCmmSysService.selectCmmPtparaSystem();
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), entity);
	}
	/**
	 * 添加业务参数
	 * @param cmm_Ptpara
	 * @return
	 */
	@RequestMapping(value="/jsh/addCmmPtpara.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addJshCmmPtparaList(@RequestBody JshPages<Cmm_Ptpara> cmm_Ptpara) {
		return jshCmmSysService.insertCmmPtparaList(cmm_Ptpara);
	}
	
	/**
	 * 修改业务参数
	 * @param cmm_Ptpara
	 * @return
	 */
	@RequestMapping(value="/jsh/modifyCmmPtpara.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String modifyJshCmmPtparaList(@RequestBody JshPages<Cmm_Ptpara> cmm_Ptpara) {
		return jshCmmSysService.updateCmmPtparaList(cmm_Ptpara);
	}
	/**
	 * 删除业务参数
	 * @param cmm_Ptpara
	 * @return
	 */
	@RequestMapping(value="/jsh/removeCmmPtpara.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String removeJshCmmPtparaList(@RequestBody JshPages<Cmm_Ptpara> cmm_Ptpara) {
		return jshCmmSysService.deleteCmmPtparaList(cmm_Ptpara);
	}
//====================================币种信息==========================================
	/**
	 * 查询所有
	 * @return
	 */
	@RequestMapping(value="/jsh/getDtCyMsg.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllJshCmmCymsgList() {
		List<Map<String, Object>> list = jshCmmSysService.selectCmmCymsgList();
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	/**
	 * 币种修改
	 * @param cytp
	 * @return
	 */
	@RequestMapping(value="/jsh/modifyDtCyMsg.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateJshCmmCymsgList(@RequestBody JshPages<Cytp> cytp) {
		return jshCmmSysService.updateCmmCymsgList(cytp);
		
	}
	/**
	 * 添加
	 * @param cytp
	 * @return
	 */
	@RequestMapping(value="/jsh/addDtCyMsg.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addJshCmmCymsgList(@RequestBody JshPages<Cytp> cytp) {
		return jshCmmSysService.insertCmmCymsgList(cytp);
		
	}
	/**
	 * 币种删除
	 * @param cytp
	 * @return
	 */
	@RequestMapping(value="/jsh/removeDtCyMsg.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String removeJshCmmCymsgList(@RequestBody JshPages<Cytp> cytp) {
		return jshCmmSysService.deleteCmmCymsgList(cytp);
	}
	
//==================================货币对管理======================================
	/**
	 * 查询所有货币对
	 * @param currmsg
	 * @return
	 */
	@RequestMapping(value="/jsh/getDtCurrMsg.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllJshCurrMsgs() {
		List<Map<String, Object>> list = jshCmmSysService.selectJshCurrMsgList();
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	
	/**
	 * 添加货币对
	 * @param currmsg
	 * @return
	 */
	@RequestMapping(value="/jsh/addCurrMsg.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addJshCurrMsgList(@RequestBody JshPages<Currmsg> currmsg) {
		return jshCmmSysService.insertJshCurrMsgList(currmsg);
	}
	
	/**
	 * 修改货币对
	 * @param currmsg
	 * @return
	 */
	@RequestMapping(value="/jsh/modifyCurrMsg.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String modifyJshCurrMsgList(@RequestBody JshPages<Currmsg> currmsg) {
		return jshCmmSysService.updateJshCurrMsgList(currmsg);
	}
	
	/**
	 * 删除货币对
	 * @param currmsg
	 * @return
	 */
	@RequestMapping(value="/jsh/removeCurrMsg.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String removeJshCurrMsgList(@RequestBody JshPages<Currmsg> currmsg) {
		return jshCmmSysService.deleteJshCurrMsgList(currmsg);
	}
	
	/**
	 * 错误码管理
	 */
	@RequestMapping(value="/jsh/getErrorCodeList.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getJshErrorCodes(@RequestBody JshPages<ErrorCode> errorMsg) {
		PageInfo<Map<String, Object>> page = jshCmmSysService.selectJshErrorCodeList(errorMsg);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	/**
	 * 错误码添加
	 * @param errorMsg
	 * @return
	 */
	@RequestMapping(value="/jsh/addErrorCodes.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addJshErrorCodes(@RequestBody JshPages<ErrorCode> errorMsg) {
		return jshCmmSysService.insertJshErrorCode(errorMsg);
	}
	/**
	 * 错误码修改
	 * @param errorMsg
	 * @return
	 */
	@RequestMapping(value="/jsh/modifyErrorCodes.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String modifyErrorCodes(@RequestBody JshPages<ErrorCode> errorMsg) {
		return jshCmmSysService.updateJshErrorCode(errorMsg);
	}
	/**
	 * 错误码删除
	 * @param errorMsg
	 * @return
	 */
	@RequestMapping(value="/jsh/removeErrorCodes.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String removeJshErrorCodes(@RequestBody JshPages<ErrorCode> errorMsg) {
		return jshCmmSysService.deleteJshErrorCode(errorMsg);
	}
	
	/**
	 * 查询日志信息
	 */
	@RequestMapping(value="/jsh/getJshErrorCodeList.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getJshErrorCodeList(@RequestBody JshPages<Logfile> logfile) {
		PageInfo<Logfile> page = logfileCmdService.selectLikeMngLog(logfile);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);	
	}
}