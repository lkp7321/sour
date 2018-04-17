package com.ylxx.fx.service.impl.jsh;

import java.util.*;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.jsh.JshErrorCodeMapper;
import com.ylxx.fx.core.mapper.jsh.JshSystemMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.jsh.JshCmmSysService;
import com.ylxx.fx.service.po.jsh.Currmsg;
import com.ylxx.fx.service.po.jsh.Cytp;
import com.ylxx.fx.service.po.jsh.ErrorCode;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.jsh.Cmm_Ptpara;
import com.ylxx.fx.service.po.jsh.JshPages;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 系统管理
 * @author lz130
 *
 */
@Service("jshCmmSysService")
public class JshCmmSysServieImpl implements JshCmmSysService {
	private static final Logger log = LoggerFactory.getLogger(JshCmmSysServieImpl.class);
	
	@Resource
	private JshSystemMapper jshSystemMapper;
	@Resource
	private JshErrorCodeMapper jshErrorCodeMapper;
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	/*=============================业务参数管理=======================*/
	/**
	 * 分页：
	 * 查询业务参数
	 */
	public List<Map<String, Object>> selectCmmPtparaList() {
	    List<Map<String, Object>> list = null;
	    try {
	    	list = jshSystemMapper.selJsh_CmmPtpara();
		} catch (Exception e) {
			log.error("查询业务参数出错");
			log.error(e.getMessage(), e);
		}
	    if(list!=null && list.size()>0) {
	    	for (int i = 0; i < list.size(); i++) {
	    		String stat = list.get(i).get("STAT").equals("0")?"启用":"停用";
	    		list.get(i).put("STAT", stat);
	    	}
	    }
		return list;
	}
	/**
	 * 查询系统总控
	 */
	public Map<String, Object> selectCmmPtparaSystem(){
		Map<String, Object> entity = null;
		try {
			entity = jshSystemMapper.sel_Jsh_system();
		} catch (Exception e) {
			log.error("获取系统参数失败");
			log.error(e.getMessage(), e);
		}
		return entity;
	}
	/**
	 * 录入业务参数
	 */
	public String insertCmmPtparaList(JshPages<Cmm_Ptpara> cmm_Ptpara) {
		String userKey = cmm_Ptpara.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String valu = cmm_Ptpara.getEntity().getValu();
		String stat = cmm_Ptpara.getEntity().getStat();
		String pasubid = cmm_Ptpara.getEntity().getPasubid();
		String remk = cmm_Ptpara.getEntity().getRemk();
		String paid = cmm_Ptpara.getEntity().getPaid();
		try {
			int count = jshSystemMapper.selJsh_CmmPtpara_count(paid);
			if(count>0) {
				log.error("该业务参数已存在");
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "该业务参数已存在");
			}else {
				jshSystemMapper.insJsh_CmmPtpara(valu, stat, paid, pasubid, remk);
			}
		} catch (Exception e) {
			log.error("录入业务参数失败");
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "添加失败");
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("系统参数管理");
		logfile.setRemk("添加业务参数");
		logfile.setVold("登陆ip:"+curUser.getCurIP()+"参数编号"+paid+"说明:"+remk+"状态:"+stat+"值:"+valu);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "添加成功");
	}
	/**
	 * 修改业务参数
	 */
	public String updateCmmPtparaList(JshPages<Cmm_Ptpara> cmm_Ptpara) {
		String userKey = cmm_Ptpara.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String valu = cmm_Ptpara.getEntity().getValu();
		String stat = cmm_Ptpara.getEntity().getStat();
		String paid = cmm_Ptpara.getEntity().getPaid();
		try {
			jshSystemMapper.upsJsh_CmmPtpara(valu, stat, paid);
		} catch (Exception e) {
				log.error("修改业务参数失败");
				log.error(e.getMessage(), e);
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "修改失败");
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("系统参数管理");
		logfile.setRemk("修改业务参数");
		logfile.setVold("登陆ip:"+curUser.getCurIP()+"参数编号"+paid+"状态:"+stat+"值:"+valu);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "修改成功");
	}
	/**
	 * 删除业务参数
	 */
	public String deleteCmmPtparaList(JshPages<Cmm_Ptpara> cmm_Ptpara) {
		String userKey = cmm_Ptpara.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String paid = cmm_Ptpara.getEntity().getPaid();
		try {
			jshSystemMapper.delJsh_CmmPtpara(paid);
		} catch (Exception e) {
				log.error("删除业务参数失败");
				log.error(e.getMessage(), e);
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "删除失败");
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("系统参数管理");
		logfile.setRemk("删除业务参数");
		logfile.setVold("登陆ip:"+curUser.getCurIP()+"参数编号"+paid);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "删除成功");
	}
/*========================币种信息管理===================*/
	/**
	 * 查询所有币种信息
	 */
	public List<Map<String, Object>> selectCmmCymsgList(){
		List<Map<String, Object>> list = null;
		try {
			list = jshSystemMapper.selJshCyMsg();
		} catch (Exception e) {
			log.error("查询币种信息出错");
			log.error(e.getMessage(), e);
		}
		if(list!=null && list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				String usfg = list.get(i).get("USFG").equals("0")?"启用":"停用";
				list.get(i).put("USFG", usfg);
			}
		}
		return list;
	}
	/**
	 * 修改币种信息
	 */
	public String updateCmmCymsgList(JshPages<Cytp> cytp) {
		String userKey = cytp.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String cyen = cytp.getEntity().getCyen();
	    String cytps = cytp.getEntity().getCytp();
	    String usfg = cytp.getEntity().getUsfg();
	    String remk = cytp.getEntity().getRemk();
	    try {
			jshSystemMapper.upsJshCyMsg(usfg, cytps, cyen, remk);
		} catch (Exception e) {
			log.error("修改币种信息出错");
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "修改失败");
		}
	    Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("币种管理");
		logfile.setRemk("修改币种");
		logfile.setVold("登陆ip:"+curUser.getCurIP()+"币种编号"+cytps+"币种英文:"+cyen+"币种描述:"+remk+"币种状态:"+usfg);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
	    return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "修改成功");
	}
	/**
	 * 添加币种信息
	 */
	public String insertCmmCymsgList(JshPages<Cytp> cytp) {
		String userKey = cytp.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String cyen = cytp.getEntity().getCyen();
	    String cytps = cytp.getEntity().getCytp();
	    String remk = cytp.getEntity().getRemk();
	    String cycn = cytp.getEntity().getCycn();
	    String usfg = cytp.getEntity().getUsfg();
	    try {
	    	int count = jshSystemMapper.selJshCyMsg_count(cyen, cytps);
	    	if(count>0) {
	    		log.info("该币种已存在");
	    		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "币种已存在");
	    	}else {
	    		jshSystemMapper.insJshCyMsg(cyen, cytps, cycn, usfg, remk);
	    	}
		} catch (Exception e) {
			log.error("添加币种信息出错");
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "添加失败");
		}
	    Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("币种管理");
		logfile.setRemk("添加币种");
		logfile.setVold("登陆ip:"+curUser.getCurIP()+"币种编号"+cytps+"币种英文:"+cyen+"币种中文名:"+cycn+"币种描述:"+remk+"币种状态:"+usfg);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
	    return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "添加成功");
	}
	/**
	 * 删除币种信息
	 */
	public String deleteCmmCymsgList(JshPages<Cytp> cytp) {
		String userKey = cytp.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String cyen = cytp.getEntity().getCyen();
	    String cytps = cytp.getEntity().getCytp();
	    try {
			jshSystemMapper.delJshCyMsg(cyen, cytps);
		} catch (Exception e) {
			log.error("删除币种信息出错");
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "删除失败");
		}
	    Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("币种管理");
		logfile.setRemk("删除币种");
		logfile.setVold("登陆ip:"+curUser.getCurIP()+"币种编号"+cytps+"币种英文:"+cyen);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
	    return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "删除成功");
	}
/*===========================货币对管理=====================*/
	/**
	 * 查询所有货币对
	 */
	public List<Map<String, Object>> selectJshCurrMsgList() {
		List<Map<String, Object>> list = null;
		try {
			list = jshSystemMapper.selJshCurrMsg();
		} catch (Exception e) {
			log.error("查询货币对出错");
			log.error(e.getMessage(), e);
		}
		return  list;
	}
	/**
	 * 货币对添加
	 */
	public String insertJshCurrMsgList(JshPages<Currmsg> currmsg) {
		String userKey = currmsg.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String exnm = currmsg.getEntity().getExnm();
	    String excd = currmsg.getEntity().getExcd();
	    String pion = currmsg.getEntity().getPion();
	    String extp = currmsg.getEntity().getExtp();
	    String excn = currmsg.getEntity().getExcn();
	    try {
	    	int count = jshSystemMapper.selJshCurrMsg_count(exnm, excd);
	    	if(count>0) {
	    		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "该货币对已存在");
	    	}else {
	    		jshSystemMapper.insJshCurrMsg(exnm, excd, excn, pion, extp);
	    	}
		} catch (Exception e) {
			log.error("添加货币对出错");
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "添加失败");
		}
	    Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("货币对管理");
		logfile.setRemk("添加货币对");
		logfile.setVold("登陆ip:"+curUser.getCurIP()+"货币编号:"+excd+"货币英文:"+exnm+"货币中文名:"+excn+"价格点数:"+pion+"价格类型:"+extp);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
	    return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "删除成功");
	}
	/**
	 * 货币对修改
	 */
	public String updateJshCurrMsgList(JshPages<Currmsg> currmsg) {
		String userKey = currmsg.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String exnm = currmsg.getEntity().getExnm();
	    String excd = currmsg.getEntity().getExcd();
	    String pion = currmsg.getEntity().getPion();
	    String extp = currmsg.getEntity().getExtp();
	    String excn = currmsg.getEntity().getExcn();
	    try {
			jshSystemMapper.upsJshCurrMsg(exnm, excd, excn, pion, extp);
		} catch (Exception e) {
			log.error("修改货币对出错");
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "修改失败");
		}
	    Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("货币对管理");
		logfile.setRemk("修改货币对");
		logfile.setVold("登陆ip:"+curUser.getCurIP()+"货币编号:"+excd+"货币英文:"+exnm+"货币中文名:"+excn+"价格点数:"+pion+"价格类型:"+extp);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
	    return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "修改成功");
	}
	/**
	 * 货币对删除
	 */
	public String deleteJshCurrMsgList(JshPages<Currmsg> currmsg) {
		String userKey = currmsg.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String exnm = currmsg.getEntity().getExnm();
	    String excd = currmsg.getEntity().getExcd();
	    try {
			jshSystemMapper.delJshCurrmsg(exnm, excd);
		} catch (Exception e) {
			log.error("删除货币对出错");
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "删除失败");
		}
	    Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("货币对管理");
		logfile.setRemk("删除货币对");
		logfile.setVold("登陆ip:"+curUser.getCurIP()+"货币编号:"+excd+"货币英文:"+exnm);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
	    return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "删除成功");
	}
/*======================错误码查询============================*/
	/**
	 * 分页查询错误码
	 */
	public PageInfo<Map<String, Object>> selectJshErrorCodeList(JshPages<ErrorCode> errorMsg){
		Integer pageNo = errorMsg.getPageNo();
		Integer pageSize = errorMsg.getPageSize();
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    List<Map<String, Object>> list = null;
	    try {
			list = jshErrorCodeMapper.selErrorCodeMsg(errorMsg.getEntity().getErcd());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	    PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
	    return page;
	}
	/**
	 * 添加错误码
	 */
	@Override
	public String insertJshErrorCode(JshPages<ErrorCode> errorMsg) {
		String userKey = errorMsg.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ercd = errorMsg.getEntity().getErcd();
		String ertx = errorMsg.getEntity().getErtx();
		String erin = errorMsg.getEntity().getErin();
		try {
			int errcodeCount = jshErrorCodeMapper.selErrorCodeCount(ercd);
			if(errcodeCount>0) {
				log.error("该错误码编号已存在");
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "该错误码已存在");
			}else {
				jshErrorCodeMapper.insertErrorCode(ercd, ertx, erin);
			}
		} catch (Exception e) {
			log.error("添加错误码出现错误");
			log.error(e.getMessage());
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "添加失败");
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("错误码管理");
		logfile.setRemk("添加错误码");
		logfile.setVold("登陆ip:"+curUser.getCurIP()+"错误编号:"+ercd+"外部错误说明:"+ertx+"内部错误说明:"+erin);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
	    return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "添加成功");
	}
	/**
	 * 修改错误码
	 */
	@Override
	public String updateJshErrorCode(JshPages<ErrorCode> errorMsg) {
		String userKey = errorMsg.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ercd = errorMsg.getEntity().getErcd();
		String ertx = errorMsg.getEntity().getErtx();
		String erin = errorMsg.getEntity().getErin();
		try {
			jshErrorCodeMapper.updateErrorCode(ercd, ertx, erin);
		} catch (Exception e) {
			log.error("修改错误码出现错误");
			log.error(e.getMessage());
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "修改失败");
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("错误码管理");
		logfile.setRemk("修改错误码");
		logfile.setVold("登陆ip:"+curUser.getCurIP()+"错误编号:"+ercd+"外部错误说明:"+ertx+"内部错误说明:"+erin);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
	    return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "修改成功");
	}
	/**
	 * 删除错误码
	 */
	@Override
	public String deleteJshErrorCode(JshPages<ErrorCode> errorMsg) {
		String userKey = errorMsg.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String ercd = errorMsg.getEntity().getErcd();
		try {
			jshErrorCodeMapper.deleteErrorCode(ercd);
		} catch (Exception e) {
			log.error("删除错误码出现错误");
			log.error(e.getMessage());
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "删除失败");
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("错误码管理");
		logfile.setRemk("删除错误码");
		logfile.setVold("登陆ip:"+curUser.getCurIP()+"错误编号:"+ercd);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
	    return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "删除成功");
	}
}
