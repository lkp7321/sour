package com.ylxx.fx.service.impl.person.priceimpl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.price.HandQuickStopMapper;
import com.ylxx.fx.core.mapper.person.price.PdtRParaMapper;
import com.ylxx.fx.service.person.price.IHandQuickStopService;
import com.ylxx.fx.service.po.CmmStoper;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Service("handQuickStopService")
public class HandQuickStopServiceImpl implements IHandQuickStopService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HandQuickStopServiceImpl.class);
	@Resource
	private HandQuickStopMapper handQuickStopMapper;
	@Resource
	private PdtRParaMapper pdtrParaMapper;

	//加载市场
	public String queryMktinfo(){
		String result = "";
		try {
			List<HashMap<String, String>> list = handQuickStopMapper.selectPdtinfo();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//加载币别对
	public String queryhqsExnms(){
		String result = "";
		try {
			List<HashMap<String, String>> list = handQuickStopMapper.selectEXNM();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//加载停牌数据
	public String selecthqsCmmStopers(){
		String result = "";
		try {
			List<CmmStoper> list = handQuickStopMapper.selecthqsCmmStopers();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//条件加载停牌数据
	public String selecthqsCmmStopers(String mkid, String excd){
		String result = "";
		if (mkid.trim().equals("all")) {
			mkid = "";
		}
		if (excd.trim().equals("all")) {
			excd = "";
		}
		try {
			List<CmmStoper> list = handQuickStopMapper.selectAllCmmStoper(mkid,excd);
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//开牌
	public String openChannel(String userKey,List<CmmStoper> cmms){
		String result = "";
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P999");
		curUser.setCurIP("127.0.0.1");*/
		CmmStoper ppch = null;
		try {
			for (int i = 0; i < cmms.size(); i++) {
				ppch = new CmmStoper();
				ppch = cmms.get(i);
				ppch.setStfg("0");
				ppch.setUsfg("0");
				Boolean bo = handQuickStopMapper.updatehqsYesCtrlusfg(ppch);
				String nowtime = DataTimeClass.getCurDateTime();
 				if (bo) {
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:"
							+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
							+ "手工快速停牌,市场:" + ppch.getMknm() + ",市场编号:"
							+ ppch.getMkid() + ",币别对:" + ppch.getExnm()
							+ ",价格类型:" + ppch.getTpfg() + ",期限:"
							+ ppch.getTerm() + ",停牌期名称:" + ppch.getStnm()
							+ ",停牌标志:" + ppch.getUsfg() + "启用,成功!时间:"
							+ nowtime);
					LogfileBean loginfo = new LogfileBean();
					loginfo.setRzdt(nowtime.substring(0,8));
					loginfo.setRzsj(nowtime.substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setTymo("手工快速停牌");
					loginfo.setRemk("启用");
					loginfo.setVold("登录产品:" + curUser.getProd() + "手工快速停牌,市场:"
							+ ppch.getMknm() + ",市场编号:" + ppch.getMkid()
							+ ",币别对:" + ppch.getExnm() + ",价格类型:"
							+ ppch.getTpfg() + ",期限:" + ppch.getTerm()
							+ ",停牌期名称:" + ppch.getStnm() + ",停牌标志:"
							+ ppch.getUsfg());
					loginfo.setVnew("成功");
					loginfo.setProd(curUser.getProd());
					boolean boo = pdtrParaMapper.insertMarkLogger(loginfo);
					if (boo) {
						LOGGER.info("写审计日志成功!");
						if (i==cmms.size()-1) {
							result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
						}
						//TODO Psocket.SendSocket();
					}else {
						LOGGER.error("写审计日志失败!");
						return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
					}
				}else {
					LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:"
							+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
							+ "手工快速停牌,市场:" + ppch.getMknm() + ",市场编号:"
							+ ppch.getMkid() + ",币别对:" + ppch.getExnm()
							+ ",价格类型:" + ppch.getTpfg() + ",期限:"
							+ ppch.getTerm() + ",停牌期名称:" + ppch.getStnm()
							+ ",停牌标志:" + ppch.getUsfg() + "启用,失败!时间:"
							+ nowtime);
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}
	//停牌
	public String closeChannel(String userKey,List<CmmStoper> cmms){
		String result = "";
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P999");
		curUser.setCurIP("127.0.0.1");*/
		CmmStoper ppch = null;
		try {
			for (int i = 0; i < cmms.size(); i++) {
				ppch = new CmmStoper();
				ppch = cmms.get(i);
				ppch.setStfg("1");
				ppch.setUsfg("0");
				Boolean bo = handQuickStopMapper.updatehqsYesCtrlusfg(ppch);
				String nowtime = DataTimeClass.getCurDateTime();
 				if (bo) {
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:"
							+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
							+ "手工快速停牌,市场:" + ppch.getMknm() + ",市场编号:"
							+ ppch.getMkid() + ",币别对:" + ppch.getExnm()
							+ ",价格类型:" + ppch.getTpfg() + ",期限:"
							+ ppch.getTerm() + ",停牌期名称:" + ppch.getStnm()
							+ ",停牌标志:" + ppch.getUsfg() + "停牌,成功!时间:"
							+ nowtime);
					LogfileBean loginfo = new LogfileBean();
					loginfo.setRzdt(nowtime.substring(0,8));
					loginfo.setRzsj(nowtime.substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setTymo("手工快速停牌");
					loginfo.setRemk("停牌");
					loginfo.setVold("登录产品:" + curUser.getProd() + "手工快速停牌,市场:"
							+ ppch.getMknm() + ",市场编号:" + ppch.getMkid()
							+ ",币别对:" + ppch.getExnm() + ",价格类型:"
							+ ppch.getTpfg() + ",期限:" + ppch.getTerm()
							+ ",停牌期名称:" + ppch.getStnm() + ",停牌标志:"
							+ ppch.getUsfg());
					loginfo.setVnew("成功");
					loginfo.setProd(curUser.getProd());
					boolean boo = pdtrParaMapper.insertMarkLogger(loginfo);
					if (boo) {
						LOGGER.info("写审计日志成功!");
						if (i==cmms.size()-1) {
							result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
						}
						//TODO Psocket.SendSocket();
					}else {
						LOGGER.error("写审计日志失败!");
						return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
					}
				}else {
					LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:"
							+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
							+ "手工快速停牌,市场:" + ppch.getMknm() + ",市场编号:"
							+ ppch.getMkid() + ",币别对:" + ppch.getExnm()
							+ ",价格类型:" + ppch.getTpfg() + ",期限:"
							+ ppch.getTerm() + ",停牌期名称:" + ppch.getStnm()
							+ ",停牌标志:" + ppch.getUsfg() + "停牌,失败!时间:"
							+ nowtime);
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}
}
