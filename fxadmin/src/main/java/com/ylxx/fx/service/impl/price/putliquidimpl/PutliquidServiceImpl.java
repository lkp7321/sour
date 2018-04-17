package com.ylxx.fx.service.impl.price.putliquidimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.*;
import javax.annotation.Resource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.price.putliquid.PutliQuidMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.PdtChkpara;
import com.ylxx.fx.service.po.Pdtinfo;
import com.ylxx.fx.service.po.Put_Config;
import com.ylxx.fx.service.po.Put_Liquid;
import com.ylxx.fx.service.price.putliquid.PutliQuidService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.price.GetMac;
/**
 * 价格发布配置
 */
@Service("putliQuidService")
public class PutliquidServiceImpl implements PutliQuidService {
	
	@Resource
	private PutliQuidMapper putliquidMap;
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	private static final Logger log = LoggerFactory.getLogger(PutliquidServiceImpl.class);
	
	/*
	 * 价格使用页面数据
	 */
	public PageInfo<Put_Liquid> getAllPutliQuid(String stat, 
			Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    List<Put_Liquid> list = null;
	    try {
			list = putliquidMap.selectPutList(stat);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	    PageInfo<Put_Liquid> page = new PageInfo<Put_Liquid>(list);
	    if(page.getList()!=null && page.getList().size()>0){
	    	for (int i = 0; i < page.getList().size(); i++) {
	    		String strstat = page.getList().get(i).getStat();
				if (strstat.equals("0")) {
					strstat = "未审批";
				} else if (strstat.equals("1")) {
					strstat = "审批";
				} else if (strstat.equals("2")) {
					strstat = "未通过";
				}
				page.getList().get(i).setStat(strstat);
	    	}
	    }
		return page;
	}
	/*
	 * 价格使用申请
	 */
	public boolean addPutliquid(String userKey, Put_Liquid putLiquid) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		try {
			a = putliquidMap.insertPrice(putLiquid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "添加价格使用申请:申请人:"
					+ putLiquid.getName() + ",单位:" + putLiquid.getUnit() + ",联系电话:"
					+ putLiquid.getTelp() + ",Email;" + putLiquid.getEmail()
					+ ",Ip地址;" + putLiquid.getIp() + ",接口名称;" + putLiquid.getJkmc()
					+ ",接口说明;" + putLiquid.getJksm() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("价格使用申请");
			loginfo.setRemk("添加");
			loginfo.setVold("登录产品:" + curUser.getProd() + "添加价格使用申请:申请人:"
					+ putLiquid.getName() + ",单位:" + putLiquid.getUnit() + ",联系电话:"
					+ putLiquid.getTelp() + ",Email;" + putLiquid.getEmail()
					+ ",Ip地址;" + putLiquid.getIp() + ",接口名称;" + putLiquid.getJkmc()
					+ ",接口说明;" + putLiquid.getJksm());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return true;
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "添加价格使用申请:申请人:"
					+ putLiquid.getName() + ",单位:" + putLiquid.getUnit() + ",联系电话:"
					+ putLiquid.getTelp() + ",Email;" + putLiquid.getEmail()
					+ ",Ip地址;" + putLiquid.getIp() + ",接口名称;" + putLiquid.getJkmc()
					+ ",接口说明;" + putLiquid.getJksm() + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return false;
		}
	}

	/*
	 * 价格使用审批
	 */
	public boolean updatePutliquid(String userKey, Put_Liquid putLiquid) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		try {
			a = putliquidMap.updatePrice(putLiquid.getStat(), putLiquid.getSqid());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "更新审批信息:申请人:"
					+ putLiquid.getName() + ",单位:" + putLiquid.getUnit()
					+ ",联系电话:" + putLiquid.getTelp() + ",Email;"
					+ putLiquid.getEmail() + ",Ip地址;" + putLiquid.getIp()
					+ ",接口名称;" + putLiquid.getJkmc() + ",状态:"
					+ putLiquid.getStat() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("价格审批管理");
			loginfo.setRemk("更新");
			loginfo.setVold("登录产品:" + curUser.getProd() + "更新审批信息:申请人:"
					+ putLiquid.getName() + ",单位:" + putLiquid.getUnit()
					+ ",联系电话:" + putLiquid.getTelp() + ",Email;"
					+ putLiquid.getEmail() + ",Ip地址;" + putLiquid.getIp()
					+ ",接口名称;" + putLiquid.getJkmc() + ",状态:"
					+ putLiquid.getStat());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			return true;
		} else {
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "更新审批信息:申请人:"
					+ putLiquid.getName() + ",单位:" + putLiquid.getUnit()
					+ ",联系电话:" + putLiquid.getTelp() + ",Email;"
					+ putLiquid.getEmail() + ",Ip地址;" + putLiquid.getIp()
					+ ",接口名称;" + putLiquid.getJkmc() + ",状态:"
					+ putLiquid.getStat() + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
			return false;
		}
	}

	//==========================
	/*
	 * 价格使用接口配置
	 */
	public String upbuildput(
			String userKey,String inid, 
			List<Put_Config> put_configList, 
			List<Pdtinfo> pdtinList, Put_Liquid put_Liquid) {
		log.info("配置价格使用接口：");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		try {
			putliquidMap.delePtnm(inid);
			for (int i = 0; i < pdtinList.size(); i++) {
				save(curUser, put_configList.get(i), pdtinList.get(i));
			}
			int b = 0;
			try {
				b = putliquidMap.updateIDPass(put_Liquid);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if(b>0){
				log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "修改价格发布配置:,接口ID:"
						+ put_Liquid.getInid() + "接口名称:" + put_Liquid.getJkmc()
						+ ",用户IP:" + put_Liquid.getIp() + "接口密码:"
						+ put_Liquid.getPass() + ",成功!时间:"
						+ DataTimeClass.getCurDateTime());
				Logfile loginfo = new Logfile();
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("价格发布配置");
				loginfo.setRemk("修改");
				loginfo.setVold("登录产品:" + curUser.getProd() + "修改价格发布配置:,接口ID:"
						+ put_Liquid.getInid() + "接口名称:" + put_Liquid.getJkmc()
						+ ",用户IP:" + put_Liquid.getIp() + "接口密码:"
						+ put_Liquid.getPass());
				loginfo.setVnew("成功");
				loginfo.setProd(curUser.getProd());
				logfileCmdService.insertLog(loginfo);
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "价格使用接口配置成功");
			}else{
				log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
						+ " 登录产品:" + curUser.getProd() + "保存价格发布配置:,接口ID:"
						+ put_Liquid.getInid() + "接口名称:" + put_Liquid.getJkmc()
						+ ",用户IP:" + put_Liquid.getIp() + "接口密码:"
						+ put_Liquid.getPass() + ",失败!时间:"
						+ DataTimeClass.getCurDateTime());
				return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "价格使用接口配置失败，请检验数据是否正确！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "价格使用接口配置失败，请检验数据是否正确！");
		}
	}
	
	public boolean save(CurrUser curUser, Put_Config put_config, Pdtinfo pdtin){
		boolean flag = false;
		int b = 0;
		try {
			Pdtinfo pdtinfo = putliquidMap.returnPdtinfo(pdtin.getPtnm());
			if(equalsObj(put_config,pdtin)){
				return false;
			}
			 b = putliquidMap.insertPutConfig(put_config, pdtinfo);
			if(b>0){
				flag = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag = false;
		}
		if(flag){
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "保存价格发布配置:接口ID:"
					+ put_config.getInid() + ",币种名称:" + put_config.getExnm()
					+ ",产品名称:" + pdtin.getPtnm() + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("价格发布配置");
			loginfo.setRemk("添加");
			loginfo.setVold("登录产品:" + curUser.getProd() + "保存价格发布配置:接口ID:"
					+ put_config.getInid() + ",币种名称:" + put_config.getExnm()
					+ ",产品名称:" + pdtin.getPtnm());
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
		}else{
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "保存价格发布配置:接口ID:"
					+ put_config.getInid() + ",币种名称:" + put_config.getExnm()
					+ ",产品名称:" + pdtin.getPtnm() + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
		}
		return flag;
	}
	
	public boolean equalsObj(Put_Config put_config, Pdtinfo pdtin) {
		boolean flag = false;
		Pdtinfo pdtinfo = null;
		List<Put_Config> list = null;
		try {
			pdtinfo = putliquidMap.returnPdtinfo(pdtin.getPtnm());
			list = putliquidMap.returnConfig(put_config.getInid());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (list != null && list.size() > 0 && pdtinfo!=null) {
			for (int i = 0; i < list.size(); i++) {
				String inid = list.get(i).getInid();
				String prty = list.get(i).getPrty();
				String exnm = list.get(i).getExnm();
				String ptid = pdtinfo.getPtid();
				String pexnm = put_config.getExnm();
				if (inid.equals(put_config.getInid()) && prty.equals(ptid)
						&& exnm.equals(pexnm)) {
					flag = true;
					return flag;
				}
			}
		}
		return flag;
	}
	//=====
	//配置页面产品列表查询
	public List<Map<String,String>> getAllptno(){
		log.info("\n查询价格接口配置的所有产品列表：");
		List<Map<String,String>> list = null;
		try {
			list = putliquidMap.selectPdtinfo();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("\n长度:"+list.size());
		return list;
	}
	
	//配置页面产品接口查询
	public List<PdtChkpara> getPtidJk(String prod){
		log.info("\n查询当前产品的货币对信息");
		if (prod.trim().equals("P006")){
			prod = "P001";
		}	
		if (prod.trim().equals("P008")){
			prod = "P002";
		}
		List<PdtChkpara> list = null;
		try {
			list = putliquidMap.selectMkid(prod.trim());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		List<PdtChkpara> exnmList = new ArrayList<PdtChkpara>();
		if (list.size()>0) {
			for (int i = 0; i < list.size(); i++) {
				PdtChkpara currmsg = new PdtChkpara();
				currmsg.setExcd(list.get(i).getExcd());
				currmsg.setExnm(list.get(i).getExnm().trim()
						+ "." + list.get(i).getTpfg().trim()
						+ "." + list.get(i).getTerm().trim()
						+ "." + list.get(i).getCxfg().trim());
				exnmList.add(currmsg);
			}
		}
		return exnmList;
	}	
	
	//配置的所有货币对接口信息
	public List<PdtChkpara> getAllPtidJK(String inid){
		log.info("\n查询当前接口的所有货币对信息：");
		List<PdtChkpara> list = null;
		try {
			list = putliquidMap.reConfigCmm(inid);
		} catch (Exception e) {
			list = new ArrayList<PdtChkpara>();
		}
		log.info("\n长度为："+list.size());
		return list;
	}
	//查询当前选中数据
	public Put_Liquid getonlyquid(String sqid, String name, String unit) {
		GetMac getmac = new GetMac();
		log.info("\n获取当前选中数据信息");
		try {
			Put_Liquid put_Liquid = putliquidMap.selectOnLiquid(sqid, name, unit);
			if ("0".equals(put_Liquid.getInid())) {
				put_Liquid.setInid(getmac.buildRandomID());
				put_Liquid.setPass(getmac.buildRandomPass());
			} else {
				put_Liquid.setInid(put_Liquid.getInid());
				put_Liquid.setPass(put_Liquid.getPass());
			}
			return put_Liquid;
		} catch (Exception e) {
			return new Put_Liquid();
		}
	}
}
