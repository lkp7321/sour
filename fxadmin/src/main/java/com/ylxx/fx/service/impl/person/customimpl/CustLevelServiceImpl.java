package com.ylxx.fx.service.impl.person.customimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.custom.CustLevelMapper;
import com.ylxx.fx.service.person.custom.ICustLevelService;
import com.ylxx.fx.service.po.CustLevelBean;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodeCust;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Service("custLevelService")
public class CustLevelServiceImpl implements ICustLevelService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustLevelServiceImpl.class);
	@Resource
	private CustLevelMapper custLevelMapper;
	
	//页面加载完成后，获取所有客户级别信息*
	public String getCustLevel(String prod){
		String result = "";
		List<CustLevelBean> custLevelBeans = null;
		try {
			custLevelBeans = custLevelMapper.SelectCustLevelList(prod);
			if (custLevelBeans!=null&&custLevelBeans.size()>0) {
				result = ResultDomain.getRtnMsg(ErrorCodeCust.E_00.getCode(),JSON.toJSONString(custLevelBeans));
			}else if (custLevelBeans!=null&&custLevelBeans.size() == 0) {
				result = ResultDomain.getRtnMsg(ErrorCodeCust.E_01.getCode(),null);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			result = ResultDomain.getRtnMsg(ErrorCodeCust.E_02.getCode(),null);
		}
		return result;
	}
	//添加客户级别信息
	public String CustLevelAdd(CustLevelBean custLevelBean,String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String id = custLevelBean.getCuty();
		String lvnm = custLevelBean.getTynm();
		String result = "";
		try {
			//检查是否存在重复数据
			List<CustLevelBean> cBeans = custLevelMapper.checkRepeat(curUser.getProd(),id,lvnm);
			if (cBeans.size()>0) {
				return ResultDomain.getRtnMsg(ErrorCodeCust.E_101.getCode(), null);	
			}
			//执行数据添加
			int effectCount = custLevelMapper.InsertCustLevel(curUser.getProd(),custLevelBean);
			//获取当前时间
			String nowtime = DataTimeClass.getCurDateTime();
			//根据影响数据库记录数判断是否添加成功，写入日志，或返回错误信息.
			if (effectCount>0) {
				//写添加客户级别信息日志					
				LOGGER.info("用户:"+ curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "添加客户级别信息:级别"
						+ custLevelBean.getCuty() + "级别名称:" + custLevelBean.getTynm() + "成功!时间:"
						+ nowtime);
				LogfileBean logfileBean = new LogfileBean();
				logfileBean.setRzdt(nowtime.substring(0, 8));
				logfileBean.setRzsj(nowtime.substring(9));
				logfileBean.setUsem(curUser.getUsnm());
				logfileBean.setTymo("客户级别管理");
				logfileBean.setRemk("添加");
				logfileBean.setVold("登录产品:"+curUser.getProd()+",添加客户级别信息:级别"
				+custLevelBean.getCuty()+",级别名称:"+custLevelBean.getTynm());
				logfileBean.setVnew("添加成功");
				logfileBean.setProd(curUser.getProd());
				boolean bo = custLevelMapper.InsertCustLevelLogger(logfileBean);
				if (bo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodeCust.E_100.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodeCust.E_102.getCode(), null);
				}					
			}else {//若添加失败，则返回错误信息
				LOGGER.error("用户:"+ curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "添加客户级别信息:级别"
						+ custLevelBean.getCuty() + "级别名称:" + custLevelBean.getTynm() + "失败!时间:"
						+ nowtime);
				return ResultDomain.getRtnMsg(ErrorCodeCust.E_102.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodeCust.E_102.getCode(), null);
		}
		return result;
	}
	//修改客户级别信息
	public String CustLevelUpate(CustLevelBean custLevelBean,String userKey) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String result = ""; 
		boolean bo = false;
		try {
			List<CustLevelBean> custBeans = custLevelMapper.cheUpRepeat(curUser.getProd(),
					custLevelBean.getCuty(),custLevelBean.getTynm());
			if (custBeans!=null&&custBeans.size()>0) {
				return ResultDomain.getRtnMsg(ErrorCodeCust.E_202.getCode(), null);
			}
			if (custLevelBean!=null) {
				//执行数据更新
				bo = custLevelMapper.updateCustLevel(curUser.getProd(),custLevelBean);
				//获取当前时间
				String nowtime = DataTimeClass.getCurDateTime();
				//写日志
				if (bo) {
					LOGGER.info("更新数据执行成功!");
					LOGGER.info("用户:"+ curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd() + "更新客户级别信息:级别"
							+ custLevelBean.getCuty() + "级别名称:" + custLevelBean.getTynm() + "成功!时间:"
							+ nowtime);
					LogfileBean logfileBean = new LogfileBean();
					logfileBean.setRzdt(nowtime.substring(0, 8));
					logfileBean.setRzsj(nowtime.substring(9));
					logfileBean.setUsem(curUser.getUsnm());
					logfileBean.setTymo("客户级别管理");
					logfileBean.setRemk("更新");
					logfileBean.setVold("登录产品:"+ curUser.getProd() +",更新客户级别信息:级别"
					+custLevelBean.getCuty()+",级别名称:"+custLevelBean.getTynm());
					logfileBean.setVnew("更新成功");
					logfileBean.setProd(curUser.getProd());
					boolean boo = custLevelMapper.InsertCustLevelLogger(logfileBean);
					if (boo) {
						LOGGER.info("写审计日志成功!");
						result = ResultDomain.getRtnMsg(ErrorCodeCust.E_200.getCode(),null);
					}else {
						LOGGER.error("写审计日志失败!");
						return ResultDomain.getRtnMsg(ErrorCodeCust.E_201.getCode(),null);
					}					
				}else {
					LOGGER.error("用户:"+ curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
							+ " 登录产品:" + curUser.getProd() + "更新客户级别信息:级别"
							+ custLevelBean.getCuty() + "级别名称:" + custLevelBean.getTynm() + "失败!时间:"
							+ nowtime);
					return ResultDomain.getRtnMsg(ErrorCodeCust.E_201.getCode(),null);
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodeCust.E_201.getCode(),null);
		}
		return result;
	}
	//删除客户级别信息
	public String CustLevelDelete(CustLevelBean clBean,String userKey){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String cuty = clBean.getCuty();
		int effectCount = 0;
		String result = "";
		try {
			//执行数据删除
			effectCount = custLevelMapper.deleteCustLevel(curUser.getProd(),cuty);
			//获取当前时间
			String nowtime = DataTimeClass.getCurDateTime();
			if (effectCount>0) {
				LOGGER.info("数据删除执行成功!");
				//写日志
				LOGGER.info("用户:"+ curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "删除客户级别信息:级别"
						+ cuty +  "成功!时间:"	+ nowtime);
				LogfileBean logfileBean = new LogfileBean();
				logfileBean.setRzdt(nowtime.substring(0, 8));
				logfileBean.setRzsj(nowtime.substring(9));
				logfileBean.setUsem(curUser.getUsnm());
				logfileBean.setTymo("客户级别管理");
				logfileBean.setRemk("删除");
				logfileBean.setVold("登录产品:"+curUser.getProd()+",删除客户级别信息:级别"+cuty);
				logfileBean.setVnew("删除成功");
				logfileBean.setProd(curUser.getProd());
				boolean bo = custLevelMapper.InsertCustLevelLogger(logfileBean);
				if (bo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodeCust.E_300.getCode(), null);
				}else {
					LOGGER.error("写审计日志失败!");
					return ResultDomain.getRtnMsg(ErrorCodeCust.E_301.getCode(), null);
				}
			}else {
				LOGGER.error("用户:"+ curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "删除客户级别信息:级别"
						+ cuty +  "失败!时间:"	+ nowtime);
				return ResultDomain.getRtnMsg(ErrorCodeCust.E_301.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodeCust.E_301.getCode(), null);
		}
		return result;
	}

}