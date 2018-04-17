package com.ylxx.fx.service.impl.person.priceimpl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.price.CmmCtrlswhMapper;
import com.ylxx.fx.core.mapper.person.price.PdtRParaMapper;
import com.ylxx.fx.service.person.price.ICmmCtrlswhService;
import com.ylxx.fx.service.po.CmmCtrlswh;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodeCust;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Service("cmmCtrlswhService")
public class CmmCtrlswhServiceImpl implements ICmmCtrlswhService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmmCtrlswhServiceImpl.class);
	@Resource
	private CmmCtrlswhMapper cmmCtrlswhMapper;
	@Resource
	private PdtRParaMapper pdtrParaMapper;
	
	//加载页面数据
	public String selectCtrlSwh(){
		String result = "";
		try {
			List<CmmCtrlswh> list = cmmCtrlswhMapper.selectCmmPrice();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//查询直盘币别对
	public String curExnm(){
		String result = "";
		try {
			List<HashMap<String, String>> list = cmmCtrlswhMapper.selectPriceUSD();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//查询价格类型
	public String selectPrice(){
		String result = "";
		try {
			List<HashMap<String, String>> list = cmmCtrlswhMapper.selectPrice();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//添加点差干预上限
	public String addCmmCtrlSwh(String userKey, CmmCtrlswh cmmbean){
		String result = "";
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P999");
		curUser.setCurIP("127.0.0.1");*/
		
		try {
			List<CmmCtrlswh> list = cmmCtrlswhMapper.checkExsit(cmmbean);
			Boolean bo = false;
			if (list.size()>0) {
				return ResultDomain.getRtnMsg(ErrorCodeCust.E_101.getCode(), null);
			}else {
				bo = cmmCtrlswhMapper.insertPrice(cmmbean);
			}
			String nowtime = DataTimeClass.getCurDateTime();
			if (bo) {
				//TODO Psocket.SendSocket();
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "添加点差干预上限,价格类型:"
						+ cmmbean.getTpfg() + ",期限:" + cmmbean.getTerm() + ",钞汇标志:"
						+ cmmbean.getCxfg() + ",币别对:" + cmmbean.getExnm()
						+ ",总控标志:" + cmmbean.getUsfg() + ",买入干预上限:"
						+ cmmbean.getNbup() + ",卖出干预上限:" + cmmbean.getNsup()
						+ "成功!时间:" + nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setProd(curUser.getProd());
				loginfo.setTymo("点差干预上限");
				loginfo.setRemk("添加");
				loginfo.setVold("登录产品:" + curUser.getProd() + "添加点差干预上限,价格类型:"
						+ cmmbean.getTpfg() + ",期限:" + cmmbean.getTerm() + ",钞汇标志:"
						+ cmmbean.getCxfg() + ",币别对:" + cmmbean.getExnm()
						+ ",总控标志:" + cmmbean.getUsfg() + ",买入干预上限:"
						+ cmmbean.getNbup() + ",卖出干预上限:" + cmmbean.getNsup());
				loginfo.setVnew("成功");
				boolean boo = pdtrParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodeCust.E_100.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
					result = ResultDomain.getRtnMsg(ErrorCodeCust.E_102.getCode(), null);
				}
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "添加点差干预上限,价格类型:"
						+ cmmbean.getTpfg() + ",期限:" + cmmbean.getTerm() + ",钞汇标志:"
						+ cmmbean.getCxfg() + ",币别对:" + cmmbean.getExnm()
						+ ",总控标志:" + cmmbean.getUsfg() + ",买入干预上限:"
						+ cmmbean.getNbup() + ",卖出干预上限:" + cmmbean.getNsup()
						+ "失败!时间:" + nowtime);
				result = ResultDomain.getRtnMsg(ErrorCodeCust.E_102.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodeCust.E_102.getCode(), null);
		}
		return result;
	}
	//修改点差干预上限
	public String upCmmCtrlSwh(String userKey, CmmCtrlswh cmmbean){
		String result = "";
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P999");
		curUser.setCurIP("127.0.0.1");*/
		
		try {
			Boolean bo = cmmCtrlswhMapper.updatePrice(cmmbean);
			String nowtime = DataTimeClass.getCurDateTime();
			if (bo) {
				//TODO Psocket.SendSocket();
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "修改点差干预上限,价格类型:"
						+ cmmbean.getTpfg() + ",期限:" + cmmbean.getTerm() + ",钞汇标志:"
						+ cmmbean.getCxfg() + ",币别对:" + cmmbean.getExnm()
						+ ",总控标志:" + cmmbean.getUsfg() + ",买入干预上限:"
						+ cmmbean.getNbup() + ",卖出干预上限:" + cmmbean.getNsup()
						+ "成功!时间:" + nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setProd(curUser.getProd());
				loginfo.setTymo("点差干预上限");
				loginfo.setRemk("修改");
				loginfo.setVold("登录产品:" + curUser.getProd() + "修改点差干预上限,价格类型:"
						+ cmmbean.getTpfg() + ",期限:" + cmmbean.getTerm() + ",钞汇标志:"
						+ cmmbean.getCxfg() + ",币别对:" + cmmbean.getExnm()
						+ ",总控标志:" + cmmbean.getUsfg() + ",买入干预上限:"
						+ cmmbean.getNbup() + ",卖出干预上限:" + cmmbean.getNsup());
				loginfo.setVnew("成功");
				boolean boo = pdtrParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodeCust.E_200.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
					result = ResultDomain.getRtnMsg(ErrorCodeCust.E_201.getCode(), null);
				}
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "修改点差干预上限,价格类型:"
						+ cmmbean.getTpfg() + ",期限:" + cmmbean.getTerm() + ",钞汇标志:"
						+ cmmbean.getCxfg() + ",币别对:" + cmmbean.getExnm()
						+ ",总控标志:" + cmmbean.getUsfg() + ",买入干预上限:"
						+ cmmbean.getNbup() + ",卖出干预上限:" + cmmbean.getNsup()
						+ "失败!时间:" + nowtime);
				result = ResultDomain.getRtnMsg(ErrorCodeCust.E_201.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodeCust.E_201.getCode(), null);
		}
		return result;
	}
	//删除
	public String delCmmCtrl(String userKey, List<CmmCtrlswh> cmmbeans){
		String result = "";
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P999");
		curUser.setCurIP("127.0.0.1");*/
		try {
			CmmCtrlswh cmmbean;
			for (int i = 0; i < cmmbeans.size(); i++) {
				cmmbean = new CmmCtrlswh();
				cmmbean = cmmbeans.get(i);
				if (cmmbean.getCxfg().trim().equals("钞")) {
					cmmbean.setCxfg("2");
				}else {
					cmmbean.setCxfg("1");
				}
				Boolean bo = cmmCtrlswhMapper.deletePrice(cmmbean);
				String nowtime = DataTimeClass.getCurDateTime();
				if (bo) {
					//TODO Psocket.SendSocket();
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd() + "删除点差干预上限,价格类型:"
							+ cmmbean.getTpfg() + ",期限:" + cmmbean.getTerm() + ",钞汇标志:"
							+ cmmbean.getCxfg() + ",币别对:" + cmmbean.getExnm()
							+ "成功!时间:" + nowtime);
					LogfileBean loginfo = new LogfileBean();
					loginfo.setRzdt(nowtime.substring(0,8));
					loginfo.setRzsj(nowtime.substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setProd(curUser.getProd());
					loginfo.setTymo("点差干预上限");
					loginfo.setRemk("删除");
					loginfo.setVold("登录产品:" + curUser.getProd() + "删除点差干预上限,价格类型:"
							+ cmmbean.getTpfg() + ",期限:" + cmmbean.getTerm() + ",钞汇标志:"
							+ cmmbean.getCxfg() + ",币别对:" + cmmbean.getExnm());
					loginfo.setVnew("成功");
					boolean boo = pdtrParaMapper.insertMarkLogger(loginfo);
					if (boo) {
						LOGGER.info("写审计日志成功!");
						if (i==cmmbeans.size()-1) {
							result = ResultDomain.getRtnMsg(ErrorCodeCust.E_300.getCode(), null);
						}
					}else {
						LOGGER.error("写审计日志失败!");
						return ResultDomain.getRtnMsg(ErrorCodeCust.E_301.getCode(), null);
					}
				}else {
					LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd() + "删除点差干预上限,价格类型:"
							+ cmmbean.getTpfg() + ",期限:" + cmmbean.getTerm() + ",钞汇标志:"
							+ cmmbean.getCxfg() + ",币别对:" + cmmbean.getExnm()
							+ "失败!时间:" + nowtime);
					return ResultDomain.getRtnMsg(ErrorCodeCust.E_301.getCode(), null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodeCust.E_301.getCode(), null);
		}
		return result;
	}
	//启用/停用
	public String updateCmmCtrl(String userKey, String usfg,List<CmmCtrlswh> cmmbeans){
		String result = "";
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P999");
		curUser.setCurIP("127.0.0.1");*/
		try {
			CmmCtrlswh cmmbean;
			for (int i = 0; i < cmmbeans.size(); i++) {
				cmmbean = new CmmCtrlswh();
				cmmbean.setTpfg(cmmbeans.get(i).getTpfg().trim());
				cmmbean.setTerm(cmmbeans.get(i).getTerm().trim());
				cmmbean.setExnm(cmmbeans.get(i).getExnm().trim());
				cmmbean.setCxfg(cmmbeans.get(i).getCxfg().trim());
				if (usfg.equals("启用")) {
					cmmbean.setUsfg("0");
				}else {
					cmmbean.setUsfg("1");
				}
				Boolean bo = cmmCtrlswhMapper.updateCtrlusfg(cmmbean);
				String nowtime = DataTimeClass.getCurDateTime();
				if (bo) {
					//TODO Psocket.SendSocket();
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd() + "修改干预上限使用标志,价格类型:"
							+ cmmbean.getTpfg() + ",期限:" + cmmbean.getTerm() + ",钞汇标志:"
							+ cmmbean.getCxfg() + ",币别对:" + cmmbean.getExnm()
							+ ",使用标志:" + cmmbean.getUsfg() + "成功!时间:" + nowtime);
					LogfileBean loginfo = new LogfileBean();
					loginfo.setRzdt(nowtime.substring(0,8));
					loginfo.setRzsj(nowtime.substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setProd(curUser.getProd());
					loginfo.setTymo("干预上限标志");
					loginfo.setRemk("修改");
					loginfo.setVold("登录产品:" + curUser.getProd() + "修改干预上限使用标志,价格类型:"
							+ cmmbean.getTpfg() + ",期限:" + cmmbean.getTerm() + ",钞汇标志:"
							+ cmmbean.getCxfg() + ",币别对:" + cmmbean.getExnm()
							+ ",使用标志:" + cmmbean.getUsfg());
					loginfo.setVnew("成功");
					boolean boo = pdtrParaMapper.insertMarkLogger(loginfo);
					if (boo) {
						LOGGER.info("写审计日志成功!");
						if (i==cmmbeans.size()-1) {
							result = ResultDomain.getRtnMsg(ErrorCodeCust.E_200.getCode(), null);
						}
					}else {
						LOGGER.error("写审计日志失败!");
						return ResultDomain.getRtnMsg(ErrorCodeCust.E_201.getCode(), null);
					}
				}else {
					LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd() + "修改干预上限使用标志,价格类型:"
							+ cmmbean.getTpfg() + ",期限:" + cmmbean.getTerm() + ",钞汇标志:"
							+ cmmbean.getCxfg() + ",币别对:" + cmmbean.getExnm()
							+ ",使用标志:" + cmmbean.getUsfg() + "失败!时间:" + nowtime);
					return ResultDomain.getRtnMsg(ErrorCodeCust.E_201.getCode(), null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodeCust.E_201.getCode(), null);
		}
		return result;
	}

}
