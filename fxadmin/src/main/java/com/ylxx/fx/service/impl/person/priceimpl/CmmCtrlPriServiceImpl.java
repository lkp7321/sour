package com.ylxx.fx.service.impl.person.priceimpl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.price.CmmCtrlPriMapper;
import com.ylxx.fx.core.mapper.person.price.PdtRParaMapper;
import com.ylxx.fx.service.person.price.ICmmCtrlPriService;
import com.ylxx.fx.service.po.CmmCtrlpri;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodeCust;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Service("cmmCtrlPriService")
public class CmmCtrlPriServiceImpl implements ICmmCtrlPriService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CmmCtrlPriServiceImpl.class);
	@Resource
	private CmmCtrlPriMapper cmmCtrlPriMapper;
	@Resource
	private PdtRParaMapper pdtrParaMapper;

	//查询货币对
	public String allExnm(){
		String result = "";
		try {
			List<HashMap<String, String>> list = cmmCtrlPriMapper.selectPrices();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//查询页面数据
	public String allCmmCtrlPri(String exnm){
		String result = "";
		try {
			List<CmmCtrlpri> list = cmmCtrlPriMapper.selectAllCmmCtrlpri(exnm);
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setCxfg(list.get(i).getCxfg().equals("2")?"钞":"汇");
				list.get(i).setStfg(list.get(i).getStfg().equals("0")?"启用":"停用");
			}
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//添加
	public String addCmmCtrlpri(String userKey,CmmCtrlpri cmmCtrl){
		String result = "";
		//CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P999");
		curUser.setCurIP("127.0.0.1");
		
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Date bgtm = null;
		Date edtm = null;
		try {
			bgtm = sdf.parse(cmmCtrl.getBgtm());
			edtm = sdf.parse(cmmCtrl.getEdtm());
		} catch (ParseException e) {
			e.printStackTrace();
			LOGGER.error("日期类型转换异常");
			return ResultDomain.getRtnMsg(ErrorCodeCust.E_102.getCode(), null);
		}*/
		cmmCtrl.setCxfg(cmmCtrl.getCxfg()==null?"2":cmmCtrl.getCxfg());
		cmmCtrl.setNebp(cmmCtrl.getNebp()==null?"0":cmmCtrl.getNebp());
		cmmCtrl.setNesp(cmmCtrl.getNesp()==null?"0":cmmCtrl.getNesp());
		try {
			List<CmmCtrlpri> cmmctrls = cmmCtrlPriMapper.checkRepeat(cmmCtrl);
			if (cmmctrls.size()>0) {
				return ResultDomain.getRtnMsg(ErrorCodeCust.E_101.getCode(), null);
			}else {
				Boolean bo = cmmCtrlPriMapper.insertPrice(cmmCtrl);
				String nowtime = DataTimeClass.getCurDateTime();
				if (bo) {
					//TODO Psocket.SendSocket();
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd() + "添加分类干预,干预ID:"
							+ cmmCtrl.getCtid() + ",干预器名称:" + cmmCtrl.getCtnm()
							+ ",价格类型:" + cmmCtrl.getTpfg() + ",期限:" + cmmCtrl.getTerm()
							+ ",币别对:" + cmmCtrl.getExnm() + ",钞汇标志:"
							+ cmmCtrl.getCxfg() + ",买入干预点差:" + cmmCtrl.getNebp()
							+ ",卖出干预点差:" + cmmCtrl.getNesp() + ",起始时间:"
							+ cmmCtrl.getBgtm() + ",结束时间:" + cmmCtrl.getEdtm()
							+ ",干预标志:" + cmmCtrl.getStfg() + ",成功!时间:"
							+ nowtime);
					LogfileBean loginfo = new LogfileBean();
					loginfo.setRzdt(nowtime.substring(0,8));
					loginfo.setRzsj(nowtime.substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setProd(curUser.getProd());
					loginfo.setTymo("分类干预");
					loginfo.setRemk("添加");
					loginfo.setVold("登录产品:" + curUser.getProd() + "添加分类干预,干预ID:"
							+ cmmCtrl.getCtid() + ",干预器名称:" + cmmCtrl.getCtnm()
							+ ",价格类型:" + cmmCtrl.getTpfg() + ",期限:" + cmmCtrl.getTerm()
							+ ",币别对:" + cmmCtrl.getExnm() + ",钞汇标志:"
							+ cmmCtrl.getCxfg() + ",买入干预点差:" + cmmCtrl.getNebp()
							+ ",卖出干预点差:" + cmmCtrl.getNesp() + ",起始时间:"
							+ cmmCtrl.getBgtm() + ",结束时间:" + cmmCtrl.getEdtm()
							+ ",干预标志:" + cmmCtrl.getStfg());
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
							+ " 登录产品:" + curUser.getProd() + "添加分类干预,干预ID:"
							+ cmmCtrl.getCtid() + ",干预器名称:" + cmmCtrl.getCtnm()
							+ ",价格类型:" + cmmCtrl.getTpfg() + ",期限:" + cmmCtrl.getTerm()
							+ ",币别对:" + cmmCtrl.getExnm() + ",钞汇标志:"
							+ cmmCtrl.getCxfg() + ",买入干预点差:" + cmmCtrl.getNebp()
							+ ",卖出干预点差:" + cmmCtrl.getNesp() + ",起始时间:"
							+ cmmCtrl.getBgtm() + ",结束时间:" + cmmCtrl.getEdtm()
							+ ",干预标志:" + cmmCtrl.getStfg() + ",失败!时间:"
							+ nowtime);
					result = ResultDomain.getRtnMsg(ErrorCodeCust.E_102.getCode(), null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodeCust.E_102.getCode(), null);
		}
		return result;
	}
	//修改
	public String updateCmmCtrlpri(String userKey,CmmCtrlpri cmmCtrl){
		String result = "";
		//CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P999");
		curUser.setCurIP("127.0.0.1");
		
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Date bgtm = null;
		Date edtm = null;
		try {
			bgtm = sdf.parse(cmmCtrl.getBgtm());
			edtm = sdf.parse(cmmCtrl.getEdtm());
		} catch (ParseException e) {
			e.printStackTrace();
			LOGGER.error("日期类型转换异常");
			return ResultDomain.getRtnMsg(ErrorCodeCust.E_102.getCode(), null);
		}*/
		try {
			List<CmmCtrlpri> cmmctrls = cmmCtrlPriMapper.checkRepeat(cmmCtrl);
			if (cmmctrls.size()>0) {
				Boolean bo = cmmCtrlPriMapper.updateCtrl(cmmCtrl);
				String nowtime = DataTimeClass.getCurDateTime();
				if (bo) {
					//TODO Psocket.SendSocket();
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd() + "修改分类干预,干预ID:"
							+ cmmCtrl.getCtid() + ",干预器名称:" + cmmCtrl.getCtnm()
							+ ",价格类型:" + cmmCtrl.getTpfg() + ",期限:" + cmmCtrl.getTerm()
							+ ",币别对:" + cmmCtrl.getExnm() + ",钞汇标志:"
							+ cmmCtrl.getCxfg() + ",买入干预点差:" + cmmCtrl.getNebp()
							+ ",卖出干预点差:" + cmmCtrl.getNesp() + ",起始时间:"
							+ cmmCtrl.getBgtm() + ",结束时间:" + cmmCtrl.getEdtm()
							+ ",干预标志:" + cmmCtrl.getStfg() + ",成功!时间:"
							+ nowtime);
					LogfileBean loginfo = new LogfileBean();
					loginfo.setRzdt(nowtime.substring(0,8));
					loginfo.setRzsj(nowtime.substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setProd(curUser.getProd());
					loginfo.setTymo("分类干预");
					loginfo.setRemk("修改");
					loginfo.setVold("登录产品:" + curUser.getProd() + "修改分类干预,干预ID:"
							+ cmmCtrl.getCtid() + ",干预器名称:" + cmmCtrl.getCtnm()
							+ ",价格类型:" + cmmCtrl.getTpfg() + ",期限:" + cmmCtrl.getTerm()
							+ ",币别对:" + cmmCtrl.getExnm() + ",钞汇标志:"
							+ cmmCtrl.getCxfg() + ",买入干预点差:" + cmmCtrl.getNebp()
							+ ",卖出干预点差:" + cmmCtrl.getNesp() + ",起始时间:"
							+ cmmCtrl.getBgtm() + ",结束时间:" + cmmCtrl.getEdtm()
							+ ",干预标志:" + cmmCtrl.getStfg());
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
							+ " 登录产品:" + curUser.getProd() + "修改分类干预,干预ID:"
							+ cmmCtrl.getCtid() + ",干预器名称:" + cmmCtrl.getCtnm()
							+ ",价格类型:" + cmmCtrl.getTpfg() + ",期限:" + cmmCtrl.getTerm()
							+ ",币别对:" + cmmCtrl.getExnm() + ",钞汇标志:"
							+ cmmCtrl.getCxfg() + ",买入干预点差:" + cmmCtrl.getNebp()
							+ ",卖出干预点差:" + cmmCtrl.getNesp() + ",起始时间:"
							+ cmmCtrl.getBgtm() + ",结束时间:" + cmmCtrl.getEdtm()
							+ ",干预标志:" + cmmCtrl.getStfg() + ",失败!时间:"
							+ nowtime);
					result = ResultDomain.getRtnMsg(ErrorCodeCust.E_201.getCode(), null);
				}
			}else {
				result = ResultDomain.getRtnMsg(ErrorCodeCust.E_201.getCode(), "该记录可能已被删除!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodeCust.E_201.getCode(), null);
		}
		return result;
	}
	//启用/停用
	public String upCmmCtrlPriStfg(String userKey,List<CmmCtrlpri> ctrlpris,String usfg){
		String result = "";
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		/*CurrUser curUser = new CurrUser();
		curUser.setUsnm("xlj");
		curUser.setProd("P999");
		curUser.setCurIP("127.0.0.1");*/
		try {
			CmmCtrlpri ctrlpri;
			for (int i = 0; i < ctrlpris.size(); i++) {
				ctrlpri = new CmmCtrlpri();
				ctrlpri.setCtid(ctrlpris.get(i).getCtid().trim());
				ctrlpri.setTpfg(ctrlpris.get(i).getTpfg().trim());
				ctrlpri.setTerm(ctrlpris.get(i).getTerm().trim());
				ctrlpri.setExnm(ctrlpris.get(i).getExnm().trim());
				ctrlpri.setCxfg(ctrlpris.get(i).getCxfg().trim());
				ctrlpri.setCtnm(ctrlpris.get(i).getCtnm().trim());
				if (usfg.equals("启用")) {
					ctrlpri.setStfg("0");
					ctrlpri.setUsfg("0");
				}else {
					ctrlpri.setStfg("1");
					ctrlpri.setUsfg("1");
				}
				Boolean bo = cmmCtrlPriMapper.updateStfg(ctrlpri);
				String nowtime = DataTimeClass.getCurDateTime();
				if (bo) {
					//TODO Psocket.SendSocket();
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:"
							+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
							+ "修改分类干预启用标志,干预ID:" + ctrlpri.getCtid() + ",干预器名称:"
							+ ctrlpri.getCtnm() + ",价格类型:" + ctrlpri.getTpfg()
							+ ",期限:" + ctrlpri.getTerm() + ",币别对:"
							+ ctrlpri.getExnm() + ",状态:" + ctrlpri.getStfg()
							+ ",成功!时间:" + nowtime);
					LogfileBean loginfo = new LogfileBean();
					loginfo.setRzdt(nowtime.substring(0,8));
					loginfo.setRzsj(nowtime.substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setProd(curUser.getProd());
					loginfo.setTymo("分类干预启用标志");
					loginfo.setRemk("修改");
					loginfo.setVold("登录产品:" + curUser.getProd()
							+ "修改分类干预启用标志,干预ID:" + ctrlpri.getCtid() + ",干预器名称:"
							+ ctrlpri.getCtnm() + ",价格类型:" + ctrlpri.getTpfg()
							+ ",期限:" + ctrlpri.getTerm() + ",币别对:"
							+ ctrlpri.getExnm() + ",状态:" + ctrlpri.getStfg());
					loginfo.setVnew("成功");
					boolean boo = pdtrParaMapper.insertMarkLogger(loginfo);
					if (boo) {
						LOGGER.info("写审计日志成功!");
						if (i==ctrlpris.size()-1) {
							result = ResultDomain.getRtnMsg(ErrorCodeCust.E_200.getCode(), null);
						}
					}else {
						LOGGER.error("写审计日志失败!");
						return ResultDomain.getRtnMsg(ErrorCodeCust.E_201.getCode(), null);
					}
				}else {
					LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:"
							+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
							+ "修改分类干预启用标志,干预ID:" + ctrlpri.getCtid() + ",干预器名称:"
							+ ctrlpri.getCtnm() + ",价格类型:" + ctrlpri.getTpfg()
							+ ",期限:" + ctrlpri.getTerm() + ",币别对:"
							+ ctrlpri.getExnm() + ",状态:" + ctrlpri.getStfg()
							+ ",失败!时间:" + nowtime);
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
