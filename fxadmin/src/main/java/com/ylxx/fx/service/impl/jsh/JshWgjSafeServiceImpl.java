package com.ylxx.fx.service.impl.jsh;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.jsh.JshWgjSafeMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.jsh.JshWgjSafeInfoService;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.jsh.JshPages;
import com.ylxx.fx.service.po.jsh.Trd_SafeInfo;
import com.ylxx.fx.service.po.jsh.Trd_SafePrice;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 外管利率，外管登录柜员
 * @author lz130
 *
 */
@Service("jshWgjSafeInfoService")
public class JshWgjSafeServiceImpl implements JshWgjSafeInfoService{
	private static final Logger log = LoggerFactory.getLogger(JshWgjSafeServiceImpl.class);
	@Resource
	private JshWgjSafeMapper jshWgjSafeMapper;
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	/**
	 * 查询利率
	 */
	public List<Map<String, Object>> selectJshSafePriceList(String cyen) {
	    log.info("币别："+cyen);
	    List<Map<String, Object>> list = null;
	    try {
	    	if("all".equals(cyen)) {
	    		list = jshWgjSafeMapper.selJshWgjSafePriceList("");
	    	}else {
	    		list = jshWgjSafeMapper.selJshWgjSafePriceList(cyen);
	    	}
		} catch (Exception e) {
			log.error("查询利率信息出错");
			log.error(e.getMessage(), e);
		}
	    return list;
	}
	/**
	 * 添加利率
	 */
	public String insertJshSafePrice(JshPages<Trd_SafePrice> trd_SafePrice) {
		String userKey = trd_SafePrice.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String cyen = trd_SafePrice.getEntity().getCyen();
		String cout = trd_SafePrice.getEntity().getCout();
		String trdt = DataTimeClass.getNowDay().substring(0, 6);
		try {
			int count = jshWgjSafeMapper.selJshWgjSafePriceCount(cyen);
			if(count>0) {
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "该币种已存在");
			}else {
				jshWgjSafeMapper.insJshWghSafePrice(cyen,cout,trdt);
			}
		} catch (Exception e) {
			log.error("添加利率信息出错");
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "添加失败");
		} 
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("利率管理");
		logfile.setRemk("利率添加");
		logfile.setVold("登录ip:"+curUser.getCurIP()+"币种:"+cyen+"名称:"+cout);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "添加成功");
	}
	/**
	 * 分页
	 * 查询外管局登陆信息
	 * @param trd_SafeInfo
	 * @return
	 */
	public PageInfo<Map<String, Object>> selectJshSafeInfoList(JshPages<Trd_SafeInfo> trd_SafeInfo) {
		Integer pageNo = trd_SafeInfo.getPageNo();
		Integer pageSize = trd_SafeInfo.getPageSize();
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?20:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    List<Map<String, Object>> list = null;
	    String tellerId = trd_SafeInfo.getEntity().getTellerId();
	    try {
			list = jshWgjSafeMapper.selJshWgjSafeInfoList(tellerId);
		} catch (Exception e) {
			log.error("查询外管局登陆信息出错");
			log.error(e.getMessage(), e);
		}
	    PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
		return page;
	}
	/**
	 * 外管局登陆信息添加
	 * @param trd_SafeInfo
	 * @return
	 */
	public String insertJshSafeInfo(JshPages<Trd_SafeInfo> trd_SafeInfo) {
		String userKey = trd_SafeInfo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String bhid = trd_SafeInfo.getEntity().getBhid();
	    String chnl = trd_SafeInfo.getEntity().getChnl();
	    String ogcd = trd_SafeInfo.getEntity().getOgcd();
	    String pass = trd_SafeInfo.getEntity().getPass();
	    String tltp = trd_SafeInfo.getEntity().getTltp();
	    String trtl = trd_SafeInfo.getEntity().getTrtl();
	    try {
			jshWgjSafeMapper.insJshWgjSafeInfo(bhid, chnl, ogcd, tltp, trtl, pass);
		} catch (Exception e) {
			log.error("外管局登陆信息录入出错");
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "添加失败");
		}
	    Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("柜员管理");
		logfile.setRemk("柜员添加");
		logfile.setVold("登录ip:"+curUser.getCurIP()+"请求方机构号:"+bhid+"渠道:"+chnl+"机构:"+ogcd+"柜员:"+trtl+"柜员类型:"+tltp+"柜员密码:"+pass);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "添加成功");
	}
	/**
	 * 外管局信息修改
	 * @param trd_SafeInfo
	 * @return
	 */
	public String updateJshSafeInfo(JshPages<Trd_SafeInfo> trd_SafeInfo) {
		String userKey = trd_SafeInfo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String bhid = trd_SafeInfo.getEntity().getBhid();
	    String chnl = trd_SafeInfo.getEntity().getChnl();
	    String ogcd = trd_SafeInfo.getEntity().getOgcd();
	    String pass = trd_SafeInfo.getEntity().getPass();
	    String tellerId = trd_SafeInfo.getEntity().getTellerId();
	    String tltp = trd_SafeInfo.getEntity().getTltp();
	    String trtl = trd_SafeInfo.getEntity().getTrtl();
	    try {
			jshWgjSafeMapper.upsJshWgjSafeInfo(bhid, chnl, ogcd, tltp, trtl, pass, tellerId);
		} catch (Exception e) {
			log.error("外管局登陆信息修改出错");
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "修改失败");
		}
	    Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("柜员管理");
		logfile.setRemk("柜员修改");
		logfile.setVold("登录ip:"+curUser.getCurIP()+"柜员编号:"+tellerId+"请求方机构号:"+bhid+"渠道:"+chnl+"机构:"+ogcd+"柜员:"+trtl+"柜员类型:"+tltp+"柜员密码:"+pass);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "修改成功");
	}
	/**
	 * 外管局登陆信息删除
	 * @param trd_SafeInfo
	 * @return
	 */
	public String deleteJshSafeInfo(JshPages<Trd_SafeInfo> trd_SafeInfo) {
		String userKey = trd_SafeInfo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String tellerId = trd_SafeInfo.getEntity().getTellerId();
		try {
			jshWgjSafeMapper.moveJshWgjSafeInfo(tellerId);
			jshWgjSafeMapper.delJsnWgjSafeInfo(tellerId);
		} catch (Exception e) {
			log.error("外管局登陆信息删除出错");
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "删除失败");
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setUsem(curUser.getUsnm());
		logfile.setTymo("柜员管理");
		logfile.setRemk("柜员删除");
		logfile.setVold("登录ip:"+curUser.getCurIP()+"柜员编号:"+tellerId);
		logfile.setVnew("成功");
		logfileCmdService.insertLog(logfile);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "删除成功");
	}
}
