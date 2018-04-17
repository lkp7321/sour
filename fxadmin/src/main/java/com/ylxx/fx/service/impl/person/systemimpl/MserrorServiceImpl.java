package com.ylxx.fx.service.impl.person.systemimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.person.system.MserrorMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.person.system.MserrorService;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.Mserror;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrorCodeSystem;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
@Service("mserrorService")
public class MserrorServiceImpl implements MserrorService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MserrorServiceImpl.class);
	@Resource
	private MserrorMapper mserrormap;
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	/**
	 * 查询
	 */
	@Override
	public String getMsgErrorList(Integer pageNo, Integer pageSize, String StrTxt, String StrTxt1){
		List<Mserror> list = null;
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		try {
			list = mserrormap.selMsgError(StrTxt, StrTxt1);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		PageInfo<Mserror> page = new PageInfo<Mserror>(list);
		return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), page);
	}
	/**
	 * 添加
	 */
	@Override
	public String addMsgError(String userKey, String ercd, String ertx, String erin) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			mserrormap.addErrorCode(ercd, ertx, erin);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_01.getCode(), "添加失败,数据可能重复");
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setRemk("添加");
		logfile.setTymo("错误码管理");
		logfile.setUsem(curUser.getUsnm());
		logfile.setVnew("成功");
		logfile.setVold("登录ip："+curUser.getCurIP()+"添加错误码：编号"+ercd+"外部描述："+ertx+"内部描述："+erin);
		logfileCmdService.insertLog(logfile);
		return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "添加成功");
	}
	/**
	 * 修改
	 */
	@Override
	public String upsMsgError(String userKey, String ercd, String ertx, String erin) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			mserrormap.upsErrorCode(ercd, ertx, erin);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_01.getCode(), "修改失败");
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setRemk("修改");
		logfile.setTymo("错误码管理");
		logfile.setUsem(curUser.getUsnm());
		logfile.setVnew("成功");
		logfile.setVold("登录ip："+curUser.getCurIP()+"添加错误码：编号"+ercd+"外部描述："+ertx+"内部描述："+erin);
		logfileCmdService.insertLog(logfile);
		return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "修改成功");
	}
	/**
	 * 删除
	 */
	@Override
	public String delMsgError(String userKey, String ercd) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			mserrormap.delErrorCode(ercd);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrorCodeSystem.E_01.getCode(), "删除失败");
		}
		Logfile logfile = new Logfile();
		logfile.setProd(curUser.getProd());
		logfile.setRemk("删除");
		logfile.setTymo("错误码管理");
		logfile.setUsem(curUser.getUsnm());
		logfile.setVnew("成功");
		logfile.setVold("登录ip："+curUser.getCurIP()+"添加错误码：编号"+ercd);
		logfileCmdService.insertLog(logfile);
		return ResultDomain.getRtnMsg(ErrorCodeSystem.E_00.getCode(), "删除成功");
	}
}
