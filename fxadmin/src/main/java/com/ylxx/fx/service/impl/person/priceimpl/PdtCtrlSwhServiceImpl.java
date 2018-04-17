package com.ylxx.fx.service.impl.person.priceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.price.PdtCtrlSwhMapper;
import com.ylxx.fx.service.person.price.IPdtCtrlSwhService;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.service.po.PdtCtrlSwhBean;
import com.ylxx.fx.service.po.PdtRParaTBean;
import com.ylxx.fx.service.po.PdtinfoBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;

@Service("pdtCtrlSwhService")
public class PdtCtrlSwhServiceImpl implements IPdtCtrlSwhService{
	private static final Logger LOGGER = LoggerFactory.getLogger(PdtCtrlSwhServiceImpl.class);
	@Resource
	private PdtCtrlSwhMapper pdtCtrlSwhMapper;
	PdtinfoBean pdtinfoBean = null;
	
	//得到当前所有干预器列表及状态
	public String selectPrice(String ptid){
		String result = "";
		List<PdtCtrlSwhBean> pdtCtrlSwhBeans = new ArrayList<PdtCtrlSwhBean>();
		try {
			pdtCtrlSwhBeans = pdtCtrlSwhMapper.selectPrice(ptid.trim());
			if (pdtCtrlSwhBeans!=null&&pdtCtrlSwhBeans.size()>0) {
				for (int i = 0; i < pdtCtrlSwhBeans.size(); i++) {
					if (pdtCtrlSwhBeans.get(i).getUsfg().equals("0")) {
						pdtCtrlSwhBeans.get(i).setUsfg("启用");
					}
					if (pdtCtrlSwhBeans.get(i).getUsfg().equals("1")) {
						pdtCtrlSwhBeans.get(i).setUsfg("停用");
					}
				}
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(pdtCtrlSwhBeans));
			}else if (pdtCtrlSwhBeans!=null&&pdtCtrlSwhBeans.size()==0){
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(pdtCtrlSwhBeans));
		}
		return result;		
	}
	//得到所有币种信息:修改时显示选中数据
	public String selectPriceUSD(String ptid) throws Exception {
		String result = "";
		List<PdtRParaTBean> pdtRParaTBeans;
		try {
			pdtRParaTBeans = pdtCtrlSwhMapper.selectPriceUSD(ptid);
			if (pdtRParaTBeans!=null&&pdtRParaTBeans.size()>0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(pdtRParaTBeans));
			}else if (pdtRParaTBeans!=null&&pdtRParaTBeans.size()==0){
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;	
	}
	//添加或更新产品价格干预器总控
	public String pdtAddCtrlList(String userKey,String prod,PdtCtrlSwhBean pdtCtrlSwhBean){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String result = "";
		boolean bo = false;
		try {
			//查询产品信息
			pdtinfoBean = pdtCtrlSwhMapper.selectObjPrice(prod.trim());
			LOGGER.info("查询产品信息成功!");
			pdtCtrlSwhBean.setTpfg(pdtinfoBean.getQtty().trim());
			pdtCtrlSwhBean.setTerm(pdtinfoBean.getTerm().trim());
			pdtCtrlSwhBean.setCxfg(pdtinfoBean.getCxfg().trim());
			//查询记录是否存在，以更新或添加记录
			List<PdtCtrlSwhBean> pdtCtrlSwhBeans = pdtCtrlSwhMapper.selectPri(pdtinfoBean.getPtid().trim(), pdtCtrlSwhBean);
			if (pdtCtrlSwhBeans!=null && pdtCtrlSwhBeans.size()>0) {
				bo = pdtCtrlSwhMapper.updatePri(pdtinfoBean.getPtid().trim(), pdtCtrlSwhBean);
			}else {
				bo = pdtCtrlSwhMapper.insertPri(pdtinfoBean.getPtid().trim(), pdtCtrlSwhBean);
			}
			//获取当前时间
			String nowtime = DataTimeClass.getCurDateTime();
			if (bo) {
				//TODO Psocket.SendSocketB();
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "添加(修改)" + prod
						+ "点差干预上限:币别对:" + pdtCtrlSwhBean.getExnm() + "总控开关:"
						+ pdtCtrlSwhBean.getUsfg() + "买入干预上限:" + pdtCtrlSwhBean.getNbup()
						+ ",卖出干预上限" + pdtCtrlSwhBean.getNsup() + "成功!时间:"
						+nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("点差干预上限");
				loginfo.setRemk("编辑");
				loginfo.setVold("登录产品:" + curUser.getProd() + "添加(修改)" + prod
						+ "点差干预上限:币别对:" + pdtCtrlSwhBean.getExnm() + "总控开关:"
						+ pdtCtrlSwhBean.getUsfg() + "买入干预上限:" + pdtCtrlSwhBean.getNbup()
						+ ",卖出干预上限" + pdtCtrlSwhBean.getNsup());
				loginfo.setVnew("成功");
				loginfo.setProd(prod);
				boolean boo = pdtCtrlSwhMapper.insertLog(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
					//TODO ut.SendSocketB1();
				}else {
					LOGGER.error("写审计日志失败!");
				}
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "添加(修改)" + prod
						+ "点差干预上限:币别对:" + pdtCtrlSwhBean.getExnm() + "总控开关:"
						+ pdtCtrlSwhBean.getUsfg() + "买入干预上限:" + pdtCtrlSwhBean.getNbup()
						+ ",卖出干预上限" + pdtCtrlSwhBean.getNsup() + "失败!时间:"
						+nowtime);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}
	//删除点差干预上限
	public String delCtrl(String userKey,String prod,String exnm) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String result = "";
		boolean bo = false;
		try {
			//查询产品信息
			pdtinfoBean = pdtCtrlSwhMapper.selectObjPrice(prod.trim());
			//执行删除
			bo = pdtCtrlSwhMapper.deletePrice(pdtinfoBean.getPtid(),pdtinfoBean.getQtty(),pdtinfoBean.getTerm(),
					exnm.trim(),pdtinfoBean.getCxfg());
			//获取当前时间
			String nowtime = DataTimeClass.getCurDateTime();
			if (bo) {
				LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "删除" + prod + "币别对" + exnm
						+ "点差干预上限成功!时间:" + nowtime);
				LogfileBean loginfo = new LogfileBean();
				loginfo.setRzdt(nowtime.substring(0,8));
				loginfo.setRzsj(nowtime.substring(9));
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("点差干预上限");
				loginfo.setRemk("删除");
				loginfo.setVold("登录产品:" + curUser.getProd() + "删除" + prod + "币别对" + exnm);
				loginfo.setVnew("成功");
				loginfo.setProd(curUser.getProd());
				boolean boo = pdtCtrlSwhMapper.insertLog(loginfo);
				if (boo) {
					LOGGER.info("写审计日志成功!");
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_30.getCode(), null);
					//TODO ut.SendSocketB1();
				}else {
					LOGGER.error("写审计日志失败!");
				}
			}else {
				LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "删除" + prod + "币别对" + exnm
						+ "点差干预上限失败!时间:" + nowtime);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_31.getCode(), null);
		}
		return result;		
	}
	//启用/停用
	public String updateCtrl(String userKey,String prod,String usfg,List<PdtCtrlSwhBean> pdtCtrls) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		PdtCtrlSwhBean pdtctrl = null;
		boolean bo = false;
		String result = "";
		try {
			//查询产品信息
			pdtinfoBean = pdtCtrlSwhMapper.selectObjPrice(prod.trim());
			for (int i = 0; i < pdtCtrls.size(); i++) {
				pdtctrl = new PdtCtrlSwhBean();
				pdtctrl.setExnm(pdtCtrls.get(i).getExnm().trim());
				pdtctrl.setTpfg(pdtinfoBean.getQtty().trim());
				pdtctrl.setTerm(pdtinfoBean.getTerm().trim());
				pdtctrl.setCxfg(pdtinfoBean.getCxfg().trim());
				if (usfg.equals("启用")) {
					pdtctrl.setUsfg("0");
				}else {
					pdtctrl.setUsfg("1");
				}
				bo = pdtCtrlSwhMapper.updateUsfg(prod,pdtctrl);
				//获取当前时间
				String nowtime = DataTimeClass.getCurDateTime();
				if (bo) {
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:"
							+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
							+ "更新(批量)" + prod + "点差上限设置:币别对:" + pdtctrl.getExnm()
							+ "总控为:" + pdtctrl.getUsfg() + "成功!时间:"
							+ nowtime);
					result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
					//TODO Psocket.SendSocketB();
				}else {
					LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:"
							+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
							+ "更新(批量)" + prod + "点差上限设置:币别对:" + pdtctrl.getExnm()
							+ "总控为:" + pdtctrl.getUsfg() + "失败!时间:"
							+ nowtime);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
		}
		return result;
	}

}