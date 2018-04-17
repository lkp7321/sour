package com.ylxx.fx.service.impl.person.ppmagagerimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.ppmagager.CheckppDetailMapper;
import com.ylxx.fx.service.person.ppmagager.ICheckppDetailService;
import com.ylxx.fx.service.po.CheckPpDetail;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;


@Service("checkppDetailService")
public class CheckppDetailServiceImpl implements ICheckppDetailService {

	@Resource
	private CheckppDetailMapper checkppDetailMapper;
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckppDetailServiceImpl.class);
	
	//条件查询
	public String selAllList(String seqn){
		String result = "";
		try {
			List<CheckPpDetail> details = checkppDetailMapper.selCondition(seqn.trim());
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(details));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(),null);
		}
		return result;
	}
	//成功复核
	public String success(String userKey,String seqn){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String result = "";
		try {
			boolean bo = checkppDetailMapper.upSuccess(seqn.trim());
			String nowtime = DataTimeClass.getCurDateTime();
			if (bo) {
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "对账交易复核:行内流水号" + seqn
						+ "成功复核,成功!时间:" + nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("对账交易复核");
				loginfo.setRemk("成功复核");
				loginfo.setVold("登录产品:" + curUser.getProd() + "对账交易复核:行内流水号" + seqn
						+ "成功复核");
				loginfo.setVnew("成功");
				loginfo.setProd(curUser.getProd());
				boolean boo = checkppDetailMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}else {
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "对账交易复核:行内流水号" + seqn
						+ "成功复核,失败!时间:" + nowtime);
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
		}
		return result;
	}
	//失败复核
	public String unsuccess(String userKey,String seqn){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String result = "";
		try {
			boolean bo = checkppDetailMapper.upUnsuccess(seqn.trim());
			String nowtime = DataTimeClass.getCurDateTime();
			if (bo) {
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "对账交易复核:行内流水号" + seqn
						+ "失败复核,成功!时间:" + nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("对账交易复核");
				loginfo.setRemk("失败复核");
				loginfo.setVold("登录产品:" + curUser.getProd() + "对账交易复核:行内流水号" + seqn
						+ "失败复核");
				loginfo.setVnew("成功");
				loginfo.setProd(curUser.getProd());
				boolean boo = checkppDetailMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}else {
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "对账交易复核:行内流水号" + seqn
						+ "失败复核,失败!时间:" + nowtime);
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
		}
		return result;
	}
}
