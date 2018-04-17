package com.ylxx.fx.service.impl.person.priceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.domain.TpfgtBean;
import com.ylxx.fx.core.mapper.person.price.PdtRParaMapper;
import com.ylxx.fx.core.mapper.person.price.PdtValidateMapper;
import com.ylxx.fx.service.person.price.IPdtValidateService;
import com.ylxx.fx.service.po.CurrmsgBean;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.service.po.PdtValidateBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Service("pdtValidateService")
public class PdtValidateServiceImpl implements IPdtValidateService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PdtValidateServiceImpl.class);
	
	@Resource
	private PdtValidateMapper pdtValidateMapper;
	@Resource
	private PdtRParaMapper pdtRParaMapper;
	
	//查询产品均价校验器列表
	public String selectProductVaList(String prod) {
		String result = "";
		try {
			List<PdtValidateBean> pdtVals = pdtValidateMapper.selectProductVaList(prod);
			if (pdtVals!=null&&pdtVals.size()>0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(),JSON.toJSONString(pdtVals));
			}else if (pdtVals!=null&&pdtVals.size()==0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	//获取价格类型列表
	public String getTpfg(){
		String result = "";
		List<TpfgtBean> tpfgtBeans;
		try {
			tpfgtBeans = pdtValidateMapper.getTpfg();
			if (tpfgtBeans!=null&&tpfgtBeans.size()>0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(tpfgtBeans));
			}else if (tpfgtBeans!=null&&tpfgtBeans.size()==0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}		
		return result;
	}
	//根据价格类型名称获取价格类型
	public String getTpfgByTpnm(String tpnm){
		String tpfg = null;
		try {
			tpfg = pdtValidateMapper.getTpfgByTpnm(tpnm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tpfg;
	}
	//查询货币对列表
	public String selectExnm(String ptid,String tpfg,String term,String cxfg){
		String result = "";
		try {
			List<CurrmsgBean> currmsgBeans = pdtValidateMapper.selectExnm(ptid, tpfg, term, cxfg);
			if (currmsgBeans!=null&&currmsgBeans.size()>0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(currmsgBeans));
			}else if (currmsgBeans!=null&&currmsgBeans.size()==0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
		}
		return result;
	}
	//添加校验器
	public String addProductVa(String userKey,String prod,PdtValidateBean pdtVa){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String result = "";
		boolean bo = false;
		try {
			bo = pdtValidateMapper.addProductVa(prod, pdtVa);
			String nowtime = DataTimeClass.getCurDateTime();
			if (bo) {
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + ",为" + prod
						+ "添加均价校验器:价格类型" + pdtVa.getTpfg() + "期限:"
						+ pdtVa.getTerm() + "钞汇标志:"+ pdtVa.getCxfg() + "币别对:"
						+ pdtVa.getExnm() + "浮动点差"+ pdtVa.getMxdt() + "启用状态"
						+ pdtVa.getUsfg() + "成功!时间:"+ nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setProd(curUser.getProd());
				loginfo.setTymo("均价校验器");
				loginfo.setRemk("添加");
				loginfo.setVold("登录产品:" + curUser.getProd() + ",为" + prod
						+ "添加均价校验器:价格类型" + pdtVa.getTpfg() + "期限:"
						+ pdtVa.getTerm() + "钞汇标志:"
						+ pdtVa.getCxfg() + "币别对:"
						+ pdtVa.getExnm() + "浮动点差"
						+ pdtVa.getMxdt() + "启用状态"
						+ pdtVa.getUsfg());
				loginfo.setVnew("成功");
				boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_20.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
				}
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + ",为" + prod
						+ "添加均价校验器:价格类型" + pdtVa.getTpfg() + "期限:"
						+ pdtVa.getTerm() + "钞汇标志:"+ pdtVa.getCxfg() + "币别对:"
						+ pdtVa.getExnm() + "浮动点差"+ pdtVa.getMxdt() + "启用状态"
						+ pdtVa.getUsfg() + "成功!时间:"+ nowtime);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_21.getCode(), null);
		}
		return result;
	}
	//修改校验器
	public String updateProductVa(String userKey,String prod,PdtValidateBean pdtVa){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
 		String result = "";
		boolean bo = false;
		try {
			bo = pdtValidateMapper.updateProductVa(prod, pdtVa);
			String nowtime = DataTimeClass.getCurDateTime();
			if (bo) {
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "修改" + prod
						+ "添加均价校验器:价格类型" + pdtVa.getTpfg() + "期限:"
						+ pdtVa.getTerm() + "钞汇标志:"+ pdtVa.getCxfg() + "币别对:"
						+ pdtVa.getExnm() + "浮动点差"+ pdtVa.getMxdt() + "启用状态"
						+ pdtVa.getUsfg() + "成功!时间:"+ nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setProd(curUser.getProd());
				loginfo.setTymo("均价校验器");
				loginfo.setRemk("修改");
				loginfo.setVold("登录产品:" + curUser.getProd() + "修改" + prod
						+ "添加均价校验器:价格类型" + pdtVa.getTpfg() + "期限:"
						+ pdtVa.getTerm() + "钞汇标志:"
						+ pdtVa.getCxfg() + "币别对:"
						+ pdtVa.getExnm() + "浮动点差"
						+ pdtVa.getMxdt() + "启用状态"
						+ pdtVa.getUsfg());
				loginfo.setVnew("成功");
				boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
				}
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "修改" + prod
						+ "添加均价校验器:价格类型" + pdtVa.getTpfg() + "期限:"
						+ pdtVa.getTerm() + "钞汇标志:"+ pdtVa.getCxfg() + "币别对:"
						+ pdtVa.getExnm() + "浮动点差"+ pdtVa.getMxdt() + "启用状态"
						+ pdtVa.getUsfg() + "失败!时间:"+ nowtime);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}
	//删除校验器
	public String deleteProductVa(String userKey,String prod,PdtValidateBean pdtVa){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String result = "";
		boolean bo = false;
		try {
			bo = pdtValidateMapper.deleteProductVa(prod, pdtVa);
			String nowtime = DataTimeClass.getCurDateTime();
			if (bo) {
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "删除" + prod
						+ "添加均价校验器:价格类型" + pdtVa.getTpfg() + "期限:"
						+ pdtVa.getTerm() + "钞汇标志:"+ pdtVa.getCxfg() + "币别对:"
						+ pdtVa.getExnm() + "成功!时间:"+ nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setProd(curUser.getProd());
				loginfo.setTymo("均价校验器");
				loginfo.setRemk("删除");
				loginfo.setVold("登录产品:" + curUser.getProd() + "删除" + prod
						+ "添加均价校验器:价格类型" + pdtVa.getTpfg() + "期限:"
						+ pdtVa.getTerm() + "钞汇标志:"
						+ pdtVa.getCxfg() + "币别对:"
						+ pdtVa.getExnm());
				loginfo.setVnew("成功");
				boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_30.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodePrice.E_31.getCode(), null);
				}
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "删除" + prod
						+ "添加均价校验器:价格类型" + pdtVa.getTpfg() + "期限:"
						+ pdtVa.getTerm() + "钞汇标志:"+ pdtVa.getCxfg() + "币别对:"
						+ pdtVa.getExnm() + "失败!时间:"+ nowtime);
				return ResultDomain.getRtnMsg(ErrorCodePrice.E_31.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_31.getCode(), null);
		}
		return result;
	}
	//启用
	public String openUsfg(String userKey,String prod,List<PdtValidateBean> pdtVas){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String result = "";
		boolean bo = false;
		try {			
			for (int i = 0; i < pdtVas.size(); i++) {
				PdtValidateBean pdtVa = new PdtValidateBean();
				pdtVa.setTpfg(pdtVas.get(i).getTpfg().toString().trim());
				pdtVa.setTerm(pdtVas.get(i).getTerm().toString().trim());
				pdtVa.setExnm(pdtVas.get(i).getExnm().toString().trim());
				pdtVa.setCxfg(pdtVas.get(i).getCxfg().toString().trim());
				pdtVa.setMxdt(pdtVas.get(i).getMxdt());
				pdtVa.setUsfg("0");// 启用标志（0. 启用 1.停用）
				bo = pdtValidateMapper.updateUsfg(prod, pdtVa);
				String nowtime = DataTimeClass.getCurDateTime();
				if (bo) {
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd() + "修改" + prod
							+ "均价校验器:价格类型" + pdtVa.getTpfg() + "期限:"
							+ pdtVa.getTerm() + "钞汇标志:"+ pdtVa.getCxfg() + "币别对:"
							+ pdtVa.getExnm() + "浮动点差" + pdtVa.getMxdt() + "启用状态"
							+ pdtVa.getUsfg()+ "成功!时间:"+ nowtime);
					LogfileBean loginfo = new LogfileBean();
					loginfo.setRzdt(nowtime.substring(0,8));
					loginfo.setRzsj(nowtime.substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setProd(curUser.getProd());
					loginfo.setTymo("均价校验器");
					loginfo.setRemk("启用");
					loginfo.setVold("登录产品:" + curUser.getProd() + "修改" + prod
							+ "均价校验器:价格类型" + pdtVa.getTpfg() + "期限:"
							+ pdtVa.getTerm() + "钞汇标志:" + pdtVa.getCxfg()
							+ "币别对:" + pdtVa.getExnm()+ "浮动点差"
							+ pdtVa.getMxdt() + "启用状态" + pdtVa.getUsfg());
					loginfo.setVnew("成功");
					boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
					if (boo) {
						LOGGER.info("写审计日志成功!");
					}else {
						LOGGER.error("写审计日志失败!");
						return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
					}
					if (boo&&i==pdtVas.size()-1) {
						result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
					}
				}else {
					LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd() + "修改" + prod
							+ "均价校验器:价格类型" + pdtVa.getTpfg() + "期限:"
							+ pdtVa.getTerm() + "钞汇标志:"+ pdtVa.getCxfg() + "币别对:"
							+ pdtVa.getExnm() + "浮动点差" + pdtVa.getMxdt() + "启用状态"
							+ pdtVa.getUsfg()+ "失败!时间:"+ nowtime);
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}
	//停用
	public String closeUSFG(String userKey,String prod,List<PdtValidateBean> pdtVas){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String result = "";
		boolean bo = false;
		try {			
			for (int i = 0; i < pdtVas.size(); i++) {
				PdtValidateBean pdtVa = new PdtValidateBean();
				pdtVa.setTpfg(pdtVas.get(i).getTpfg().toString().trim());
				pdtVa.setTerm(pdtVas.get(i).getTerm().toString().trim());
				pdtVa.setExnm(pdtVas.get(i).getExnm().toString().trim());
				pdtVa.setCxfg(pdtVas.get(i).getCxfg().toString().trim());
				pdtVa.setMxdt(pdtVas.get(i).getMxdt());
				pdtVa.setUsfg("1");// 启用标志（0. 启用 1.停用）
				bo = pdtValidateMapper.updateUsfg(prod, pdtVa);
				String nowtime = DataTimeClass.getCurDateTime();
				if (bo) {
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd() + "修改" + prod
							+ "均价校验器:价格类型" + pdtVa.getTpfg() + "期限:"
							+ pdtVa.getTerm() + "钞汇标志:"+ pdtVa.getCxfg() + "币别对:"
							+ pdtVa.getExnm() + "浮动点差" + pdtVa.getMxdt() + "停用状态"
							+ pdtVa.getUsfg()+ "成功!时间:"+ nowtime);
					LogfileBean loginfo = new LogfileBean();
					loginfo.setRzdt(nowtime.substring(0,8));
					loginfo.setRzsj(nowtime.substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setProd(curUser.getProd());
					loginfo.setTymo("均价校验器");
					loginfo.setRemk("停用");
					loginfo.setVold("登录产品:" + curUser.getProd() + "修改" + prod
							+ "均价校验器:价格类型" + pdtVa.getTpfg() + "期限:"
							+ pdtVa.getTerm() + "钞汇标志:" + pdtVa.getCxfg()
							+ "币别对:" + pdtVa.getExnm()+ "浮动点差"
							+ pdtVa.getMxdt() + "停用状态" + pdtVa.getUsfg());
					loginfo.setVnew("成功");
					boolean boo = pdtRParaMapper.insertMarkLogger(loginfo);
					if (boo) {
						LOGGER.info("写审计日志成功!");
					}else {
						LOGGER.error("写审计日志失败!");
						return ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
					}
					if (boo&&i==pdtVas.size()-1) {
						result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
					}
				}else {
					LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd() + "修改" + prod
							+ "均价校验器:价格类型" + pdtVa.getTpfg() + "期限:"
							+ pdtVa.getTerm() + "钞汇标志:"+ pdtVa.getCxfg() + "币别对:"
							+ pdtVa.getExnm() + "浮动点差" + pdtVa.getMxdt() + "停用状态"
							+ pdtVa.getUsfg()+ "失败!时间:"+ nowtime);
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}
}
