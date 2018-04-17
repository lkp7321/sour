package com.ylxx.fx.service.impl.person.businessparaimpl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.person.businesspara.FavruleMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.person.businesspara.FavruleService;
import com.ylxx.fx.service.po.FavourRule;
import com.ylxx.fx.service.po.Favrule;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.price.Formula;
@Service("favruleService")
public class FavruleServiceImpl implements FavruleService {
	
	private static final Logger log = LoggerFactory.getLogger(FavruleServiceImpl.class);
	@Resource
	private FavruleMapper favrulemap;
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	private HashMap hashmap = new HashMap();
	
	//查询所有数据
	public List<Favrule> getFavruletlist(String prod, String ogcd){
		List<Favrule> list = null;
		try {
			list = favrulemap.SelectFavrule(prod, ogcd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	public PageInfo<Favrule> getPageFavrulelist(
			Integer pageNo, Integer pageSize, 
			String prod, String ogcd){
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    List<Favrule> list = null;
		try {
			list = favrulemap.SelectFavrule(prod, ogcd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		PageInfo<Favrule> page = new PageInfo<Favrule>(list);
		return page;
	}
	
	//开启
	public boolean openStat(CurrUser curUser, List<Favrule> list, String ip) {
		int a = 0;
		boolean flag = false;
		for (int i = 0; i < list.size(); i++) {
			try {
				a = favrulemap.Begin(curUser.getProd(), ((Favrule) list.get(i)).getOgcd(), 
						((Favrule) list.get(i)).getFvid());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if(a>0){
				flag = true;
			}else{
				flag = false;
			}
			if (flag) {
				log.info("用户：" + curUser.getUsnm() + " 登录IP:"
						+ ip + " 登录产品:" + curUser.getProd()
						+ "启用优惠规则:机构:" + ((Favrule) list.get(i)).getOgcd()
						+ ",优惠编码:" + ((Favrule) list.get(i)).getFvid()
						+ ",优惠名称:" + ((Favrule) list.get(i)).getFvnm()
						+ ",状态：0,成功!时间:" + DataTimeClass.getCurDateTime());
		
				Logfile loginfo = new Logfile();
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("优惠规则");
				loginfo.setRemk("启用");
				loginfo.setVold("登录产品:" + curUser.getProd() + "启用优惠规则:机构:"
						+ ((Favrule) list.get(i)).getOgcd() + ",优惠编码:"
						+ ((Favrule) list.get(i)).getFvid() + ",优惠名称:"
						+ ((Favrule) list.get(i)).getFvnm() + ",状态：0");
				loginfo.setVnew("启成功");
				logfileCmdService.insertLog(loginfo);
			} else {
				log.error("用户：" + curUser.getUsnm() + " 登录IP:"
						+ ip + " 登录产品:" + curUser.getProd()
						+ "启用优惠规则:机构:" + ((Favrule) list.get(i)).getOgcd()
						+ ",优惠编码:" + ((Favrule) list.get(i)).getFvid()
						+ ",优惠名称:" + ((Favrule) list.get(i)).getFvnm()
						+ ",状态：0,失败!时间:" + DataTimeClass.getCurDateTime());
			}
		}
		return flag;
	}
	
	//停用
	public boolean stopStat(CurrUser curUser, List<Favrule> list, String ip) {
		int a = 0;
		boolean flag = false;
		for (int i = 0; i < list.size(); i++) {
			try {
				a = favrulemap.End(curUser.getProd(), ((Favrule) list.get(i)).getOgcd(), ((Favrule) list
						.get(i)).getFvid());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if(a>0){
				flag = true;
			}else{
				flag = false;
			}
			if (flag) {
				log.info("用户：" + curUser.getUsnm() + " 登录IP:"
						+ ip + " 登录产品:" + curUser.getProd()
						+ "停用优惠规则:机构:" + ((Favrule) list.get(i)).getOgcd()
						+ ",优惠编码:" + ((Favrule) list.get(i)).getFvid()
						+ ",优惠名称:" + ((Favrule) list.get(i)).getFvnm()
						+ ",状态：1,成功!时间:" + DataTimeClass.getCurDateTime());
				Logfile loginfo = new Logfile();
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("优惠规则");
				loginfo.setRemk("停用");
				loginfo.setVold("登录产品:" + curUser.getProd() + "停用优惠规则:机构:"
						+ ((Favrule) list.get(i)).getOgcd() + ",优惠编码:"
						+ ((Favrule) list.get(i)).getFvid() + ",优惠名称:"
						+ ((Favrule) list.get(i)).getStat() + ",状态：1");
				loginfo.setVnew("成功");
				logfileCmdService.insertLog(loginfo);
			} else {
				log.error("用户：" + curUser.getUsnm() + " 登录IP:"
						+ ip + " 登录产品:" + curUser.getProd()
						+ "停用优惠规则:机构:" + ((Favrule) list.get(i)).getOgcd()
						+ ",优惠编码:" + ((Favrule) list.get(i)).getFvid()
						+ ",优惠名称:" + ((Favrule) list.get(i)).getFvnm()
						+ ",状态：1,失败!时间:" + DataTimeClass.getCurDateTime());
			}
		}
		return flag;
	}
	
	//
	public HashMap getHashMap(String rule) {
		hashmap = Formula.getFvdaMap(rule);
		return hashmap;
	}
	//查询弹窗
	public List<FavourRule> onSelFavrule(CurrUser curUser, String rule, String fvid) {

		List<FavourRule> list = new ArrayList<FavourRule>();
		List<FavourRule> list1 = null;
		FavourRule fRule = null;
		hashmap = getHashMap(rule);
		if (fvid.equals("F02")) {
			list1 = favrulemap.selectPrice(curUser.getProd());
			if (list1 != null && list1.size() > 0) {
				for (int i = 0; i < list1.size(); i++) {
					fRule = new FavourRule();
					fRule.setMyLabel(list1.get(i).getMyLabel().trim());
					fRule.setMyValue(list1.get(i).getMyValue().trim());
					fRule.setMyData("0");
					list.add(fRule);
				}
			}
		}
		Set set = hashmap.keySet();
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String para = (String) it.next();
			int fvda = (Integer) hashmap.get(para);

			if (fvid.equals("F01")) {
				// 渠道
				String strname = "未知";
				strname = favrulemap.SelChann(para, curUser.getProd());

				fRule = new FavourRule();
				if (para.equals("DEFAULT")) {
					fRule.setMyLabel("默认值");
					fRule.setMyData("0");
				} else {
					fRule.setMyLabel(strname);
					fRule.setMyData(fvda + "");
				}
				fRule.setMyValue(para);

				list.add(fRule);
			} else if (fvid.equals("F02")) {
				// 币别对

				if (para.equals("DEFAULT")) {
					fRule = new FavourRule();
					fRule.setMyLabel("默认值");
					fRule.setMyValue("DEFAULT");
					fRule.setMyData("0");
					list.add(fRule);
				}
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getMyLabel().equals(para)) {
						list.get(i).setMyData(fvda + "");
						break;
					}
				}

			} else if (fvid.equals("F03")) {
				// 金额
				fRule = new FavourRule();
				if (para.equals("DEFAULT")) {
					fRule.setMyLabel("默认值");
					fRule.setMyData("0");
				} else {
					fRule.setMyLabel(para);
					fRule.setMyData(fvda + "");
				}
				fRule.setMyValue(para);

				list.add(fRule);
			} else if (fvid.equals("F04")) {
				// 客户
				String strname = "未知";

				fRule = new FavourRule();
				strname = favrulemap.SelClient(para, curUser.getProd());
				if (para.equals("DEFAULT")) {
					fRule.setMyLabel("默认值");
					fRule.setMyData("0");
				} else {
					fRule.setMyLabel(strname);
					fRule.setMyData(fvda + "");
				}
				fRule.setMyValue(para);

				list.add(fRule);
			} else {// 其他
				// 获取中文标识
				fRule = new FavourRule();
				fRule.setMyLabel("机构优惠默认值");
				fRule.setMyValue("DEFAULT");
				fRule.setMyData(fvda + "");

				list.add(fRule);
			}
		}
		return list;
	}
	
	public boolean onsaves(String prod, String ogcd, String rule, String fvid){
		int a = 0;
		try {
			a = favrulemap.onsave(prod, ogcd, rule, fvid);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(a>0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean saveFavruleRule(CurrUser curUser, List<FavourRule> list, 
			String fvid, String ogcd, String ip) {
		boolean flag = false;
		HashMap<String, Integer> hm = new HashMap<String, Integer>();

		for (int i = 0; i < list.size(); i++) {
			FavourRule fRule = ((FavourRule) list.get(i));
			hm.put(fRule.getMyValue().trim(), Integer.valueOf(fRule.getMyData()
					.trim()));
		}

		String rule = Formula.compRule(hm, fvid);

		flag = onsaves(curUser.getProd(), ogcd.trim(), rule, fvid.trim());
		if (flag) {
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "修改优惠规则:机构:" + ogcd
					+ ",优惠规则:" + rule + ",优惠编码:" + fvid + ",成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("优惠规则");
			loginfo.setRemk("修改");
			loginfo.setVold("登录产品:" + curUser.getProd() + "修改优惠规则:机构:" + ogcd
					+ ",优惠编码:" + fvid);
			loginfo.setVnew("成功");
			logfileCmdService.insertLog(loginfo);
		} else {
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "修改优惠规则:机构:" + ogcd
					+ ",优惠规则:" + rule + ",优惠编码:" + fvid + ",失败!时间:"
					+ DataTimeClass.getCurDateTime());
		}
		return flag;
	}
	
	//初始化优惠规则
	public boolean insertFavrule(CurrUser curUser, String ogcd, String ip) {
		boolean flag = false;
		int a = 0;
		String xl="int getvar(double para){ int fvda=0;if(para>=50000 && para<100000) fvda=1;else if(para>=300000 && para<500000) fvda=4;else if(para>=200000 && para<300000) fvda=3;else if(para>=500000) fvda=5;else if(para>=100000 && para<200000) fvda=2;return fvda;}";
		try {
			a = favrulemap.inssRule(ogcd,xl);
			flag = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag = false;
		}
		
		if (flag) {
			log.info("\n用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "\n添加初始优惠规则:机构:" + ogcd
					+ ",成功!时间:" + DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("优惠规则");
			loginfo.setRemk("初始添加");
			loginfo
					.setVold("登录产品:" + curUser.getProd() + "添加初始优惠规则:机构:"
							+ ogcd);
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
		} else {
			log.error("\n用户：" + curUser.getUsnm() + " 登录IP:" + ip
					+ " 登录产品:" + curUser.getProd() + "\n添加初始优惠规则:机构:" + ogcd
					+ ",失败!时间:" + DataTimeClass.getCurDateTime());
		}
		return flag;
	}
	
	public List<Map<String,String>> getfavbox(String prod, String fvid){
		List<Map<String, String>> list = null;
		try {
			if(fvid.equals("F01")){
				list = favrulemap.SelctChannel(prod);
			}
			else if(fvid.equals("F02")){
				list = new ArrayList<Map<String,String>>();
				Map<String,String> mp = new HashMap<String,String>();
				mp.put("F02", "");
				list.add(mp);
			}
			else if(fvid.equals("F03")){
				list = new ArrayList<Map<String,String>>();
				Map<String,String> mp1 = new HashMap<String,String>();
				mp1.put("F03", "<=金额<");
				list.add(mp1);
			}else if(fvid.equals("F04")){
				list = favrulemap.select(prod);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
}
