package com.ylxx.fx.service.impl.person.priceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.domain.CmmCymsgBean;
import com.ylxx.fx.core.mapper.person.price.PdtStoperUpMapper;
import com.ylxx.fx.service.person.price.IPdtStoperUpService;
import com.ylxx.fx.service.po.LogfileBean;
import com.ylxx.fx.service.po.PdtStoperBean;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;



@Service("pdtStoperUpService")
public class PdtStoperUpServiceImpl implements IPdtStoperUpService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PdtStoperUpServiceImpl.class);
	@Resource
	private PdtStoperUpMapper pdtStoperUpMapper;
	
	//查询实盘币种
	public String selectEXNM(String prod){
		String result = "";
		List<CmmCymsgBean> cmmCymsgs = new ArrayList<CmmCymsgBean>();
		try {
			if (prod.equals("P002")) {
				cmmCymsgs = pdtStoperUpMapper.selectgoldEXNM();
			}else {
				cmmCymsgs = pdtStoperUpMapper.selectfxipEXNM();
			}
			if (cmmCymsgs!=null&&cmmCymsgs.size()>0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(cmmCymsgs));
			}else if (cmmCymsgs!=null&&cmmCymsgs.size()==0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
		}
		return result;
	}
	//获得停牌器的所有数据
	public String selectAllCmmStopers(String ptid){
		String result = "";
		List<PdtStoperBean> pdtStopers = new ArrayList<PdtStoperBean>();
		try {
			pdtStopers = pdtStoperUpMapper.selectAllCmmStopers(ptid);
			if (pdtStopers!=null&&pdtStopers.size()>0) {
				for (int i = 0; i < pdtStopers.size(); i++) {
					pdtStopers.get(i).setStfg("0".equals(pdtStopers.get(i).getStfg())?"正常":"停牌");
				}
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(pdtStopers));
			}else if (pdtStopers!=null&&pdtStopers.size()==0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
		}
		return result;
	}
	//条件获得停牌器的数据
	public String selectAllCmmStoper(String ptid,String excd){
		/*if (excd.trim().equals("all")) {
			excd = "";
		}*/
		String result = "";
		List<PdtStoperBean> pdtStopers = new ArrayList<PdtStoperBean>();
		try {
			pdtStopers = pdtStoperUpMapper.selectAllCmmStoper(ptid,excd);
			if (pdtStopers!=null&&pdtStopers.size()>0) {
				for (int i = 0; i < pdtStopers.size(); i++) {
					pdtStopers.get(i).setStfg("0".equals(pdtStopers.get(i).getStfg())?"正常":"停牌");
				}
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(pdtStopers));
			}else if (pdtStopers!=null&&pdtStopers.size()==0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
		}
		return result;
	}
	//启用状态
	public String openChannel(String prod,String userKey,List<PdtStoperBean> pdts){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String result = "";
		PdtStoperBean pdt = null;
		boolean bo = false;
		for (int i = 0; i < pdts.size(); i++) {
			pdt = new PdtStoperBean();
			pdt.setStid(pdts.get(i).getStid().toString());
			pdt.setExcd(pdts.get(i).getExcd().toString());
			pdt.setExnm(pdts.get(i).getExnm().toString());
			pdt.setStfg("0");//停牌标志(0正常1停牌）
			pdt.setUsfg("0");//启用标志（0 启用 1停用）
			try {
				bo = pdtStoperUpMapper.updateYesCtrlusfg(prod, pdt);
				String nowtime = DataTimeClass.getCurDateTime();
				if (bo) {
					//TODO Psocket.SendSocketB();
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP() 
							+ " 登录产品:" + curUser.getProd()
							+ "修改" + prod + "产品停牌标志:币别对:" + pdt.getExnm()
							+ "编号:" + pdt.getExcd() + "停牌标志:" + pdt.getStfg()
							+ "成功!时间:" + nowtime);
					LogfileBean loginfo = new LogfileBean();
					loginfo.setRzdt(nowtime.substring(0, 8));
					loginfo.setRzsj(nowtime.substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setTymo("产品停牌标志");
					loginfo.setRemk("启用");
					loginfo.setVold("登录产品:" + curUser.getProd() + "修改"
							+ prod + "产品停牌标志:币别对:" + pdt.getExnm() + "编号:"
							+ pdt.getExcd() + "停牌标志:" + pdt.getStfg());
					loginfo.setVnew("启用成功");
					loginfo.setProd(curUser.getProd());
					boolean boo = pdtStoperUpMapper.insertLog(loginfo);
					if (boo) {
						LOGGER.info("写审计日志成功!");
						if (i==pdts.size()-1) {
							result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
							//TODO Psocket.SendSocketB();
						}						
					}else {
						LOGGER.info("写审计日志失败!");
					}
				}else {
					LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP() 
							+ " 登录产品:" + curUser.getProd()
							+ "修改" + prod + "产品停牌标志:币别对:" + pdt.getExnm()
							+ "编号:" + pdt.getExcd() + "停牌标志:" + pdt.getStfg()
							+ "失败!时间:" + nowtime);
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
		}
		return result;		
	}
	//关闭状态
	public String closeChannel(String prod,String userKey,List<PdtStoperBean> pdts){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String result = "";
		PdtStoperBean pdt = null;
		boolean bo = false;
		for (int i = 0; i < pdts.size(); i++) {
			pdt = new PdtStoperBean();
			pdt.setStid(pdts.get(i).getStid().toString());
			pdt.setExcd(pdts.get(i).getExcd().toString());
			pdt.setExnm(pdts.get(i).getExnm().toString());
			pdt.setStfg("1");//停牌标志(0正常1停牌）
			pdt.setUsfg("0");//启用标志（0. 启用 1.停用）
			try {
				bo = pdtStoperUpMapper.updateYesCtrlusfg(prod, pdt);
				String nowtime = DataTimeClass.getCurDateTime();
				if (bo) {
					//TODO Psocket.SendSocketB();
					LOGGER.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP() 
							+ " 登录产品:" + curUser.getProd()
							+ "修改" + prod + "产品停牌标志:币别对:" + pdt.getExnm()
							+ "编号:" + pdt.getExcd() + "停牌标志:" + pdt.getStfg()
							+ "成功!时间:" + nowtime);
					LogfileBean loginfo = new LogfileBean();
					loginfo.setRzdt(nowtime.substring(0, 8));
					loginfo.setRzsj(nowtime.substring(9));
					loginfo.setUsem(curUser.getUsnm());
					loginfo.setTymo("产品停牌标志");
					loginfo.setRemk("停用");
					loginfo.setVold("登录产品:" + curUser.getProd() + "修改"
							+ prod + "产品停牌标志:币别对:" + pdt.getExnm() + "编号:"
							+ pdt.getExcd() + "停牌标志:" + pdt.getStfg());
					loginfo.setVnew("停用成功");
					loginfo.setProd(curUser.getProd());
					boolean boo = pdtStoperUpMapper.insertLog(loginfo);
					if (boo) {
						LOGGER.info("写审计日志成功!");
						if (i==pdts.size()-1) {
							result = ResultDomain.getRtnMsg(ErrorCodePrice.E_10.getCode(), null);
							//TODO Psocket.SendSocketB();
						}						
					}else {
						LOGGER.info("写审计日志失败!");
					}
				}else {
					LOGGER.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP() 
							+ " 登录产品:" + curUser.getProd()
							+ "修改" + prod + "产品停牌标志:币别对:" + pdt.getExnm()
							+ "编号:" + pdt.getExcd() + "停牌标志:" + pdt.getStfg()
							+ "失败!时间:" + nowtime);
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_11.getCode(), null);
			}
		}
		return result;		
	}
	//根据货币对名称查询货币对编号
	public String getExcd(String ptid,String exnm){
		String result = "";
		try {
			result = pdtStoperUpMapper.getExcd(ptid, exnm);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询货币对编号失败!");
		}
		return result;
	}
}
