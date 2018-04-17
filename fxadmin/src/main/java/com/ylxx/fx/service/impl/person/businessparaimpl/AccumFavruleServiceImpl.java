package com.ylxx.fx.service.impl.person.businessparaimpl;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.person.businesspara.AccumFavruleMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.person.businesspara.AccumFavruleService;
import com.ylxx.fx.service.po.FavourRule;
import com.ylxx.fx.service.po.Favrule;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.price.Formula;
/**
 * 积存金
 * 分行优惠设置
 * @author lz130
 *
 */
@Service("accumFavruleService")
public class AccumFavruleServiceImpl implements AccumFavruleService {
	private static final Logger log = LoggerFactory.getLogger(AccumFavruleServiceImpl.class);
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	@Resource 
	private AccumFavruleMapper accumFavruleMap;
	
	/**
	 * 获得机构数据
	 */
	public List<Map<String,Object>> selectOrganlist(String userkey, String userorgleve) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userkey);
		List<Map<String,Object>> list = null;
		try {
			list = accumFavruleMap.queryOrganS(curUser.getOrgn(), userorgleve);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
		
	}

	/**
	 * 当前combobox的数据，渠道优惠
	 * */
	public List<Map<String,Object>> comboxData() {
		List<Map<String, Object>> list = null;
		try {
			list = accumFavruleMap.SelctChannel();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	/**
	 * 当前combobox的数据，客户优惠
	 */
	public List<Map<String,Object>> custboxData() {
		List<Map<String, Object>> list = null;
		try {
			list = accumFavruleMap.select();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	/**
	 * 依照机构获得对应的规则
	 */
	public PageInfo<Favrule> pageSelecFavrule(
			String userKey, String ogcd,
			Integer pageNo, Integer pageSize) {
		List<Favrule> list = null;
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		try {
			list = accumFavruleMap.SelectFavrule(ogcd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		PageInfo<Favrule> page = new PageInfo<Favrule>(list);
		return page;
	}


	// 启用优惠规则
	public boolean openStat(String userKey, List<Favrule> list) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		boolean flag = false;
		for (int i = 0; i < list.size(); i++) {
			try {
				a = accumFavruleMap.Begin(((Favrule) list.get(i)).getOgcd(), ((Favrule) list
						.get(i)).getFvid());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if (a>0) {
				log.info("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
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
				loginfo.setVnew("成功");
				loginfo.setProd(curUser.getProd());
				logfileCmdService.insertLog(loginfo);
				flag = true;
			} else {
				log.error("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ "启用优惠规则:机构:" + ((Favrule) list.get(i)).getOgcd()
						+ ",优惠编码:" + ((Favrule) list.get(i)).getFvid()
						+ ",优惠名称:" + ((Favrule) list.get(i)).getFvnm()
						+ ",状态：0,失败!时间:" + DataTimeClass.getCurDateTime());
				flag =false;
			}
		}
		return flag;
	}

	// 停用优惠规则
	public boolean stopStat(String userKey, List<Favrule> list) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		boolean flag = false;
		for (int i = 0; i < list.size(); i++) {
			try {
				a = accumFavruleMap.End(((Favrule) list.get(i)).getOgcd(), ((Favrule) list
						.get(i)).getFvid());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if (a>0) {
				log.info("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
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
						+ ((Favrule) list.get(i)).getFvnm() + ",状态：1");
				loginfo.setVnew("成功");
				loginfo.setProd(curUser.getProd());
				logfileCmdService.insertLog(loginfo);
				flag = true;
			} else {
				log.error("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ "停用优惠规则:机构:" + ((Favrule) list.get(i)).getOgcd()
						+ ",优惠编码:" + ((Favrule) list.get(i)).getFvid()
						+ ",优惠名称:" + ((Favrule) list.get(i)).getFvnm()
						+ ",状态：1,失败!时间:" + DataTimeClass.getCurDateTime());
				flag =false;
			}
		}
		return flag;
	}

	/****
	 * 得到当前规则的hashmap
	 * 
	 * @param rule
	 *            当前规则的字符串
	 * @return hashmap
	 * */
	public HashMap getHashMap(String rule) {
		HashMap hashmap = Formula.getFvdaMap(rule);
		return hashmap;
	}

	public List<FavourRule> onSelFavrule(String rule, String fvid) {
		List<FavourRule> list = new ArrayList<FavourRule>();
		FavourRule fRule = null;
		HashMap hashmap = getHashMap(rule);

		if (fvid.equals("F02")) {
			List<Map<String, Object>> rs = null;
			try {
				rs = accumFavruleMap.selectPrice();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if (rs != null && rs.size() > 0) {
				for (int i = 0; i < rs.size(); i++) {
					fRule = new FavourRule();
					fRule.setMyLabel(((String)rs.get(i).get("EXNM")).trim());
					fRule.setMyValue(((String)rs.get(i).get("EXNM")).trim());
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
				String ss = null;
				try {
					ss = accumFavruleMap.SelChann(para);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				if(ss==null){
					ss="其他";
				}
				strname = ss;
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
				// 获取中文标识
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
				// 获取中文标识
				String strname = "未知";
				fRule = new FavourRule();
				String xx = null;
				try {
					xx = accumFavruleMap.SelClient(para);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				if(xx==null){
					xx = "其他";
				}
				strname = xx;
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

	/***
	 *保存优惠规则的修改
	 * 
	 * @param userKey
	 *            list fvid
	 * @return true|false
	 **/
	public boolean saveFavruleRule(String userKey, List<FavourRule> list, String fvid,
			String ogcd) {
		boolean flag = false;
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			FavourRule fRule = ((FavourRule) list.get(i));

			hm.put(fRule.getMyValue().trim(), Integer.valueOf(fRule.getMyData()
					.trim()));
		}

		String rule = Formula.compRule(hm, fvid);
		int a=0;
		try {
			a = accumFavruleMap.onsave(ogcd.trim(), rule, fvid.trim());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (a>0) {
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "修改优惠规则:机构:" + ogcd
					+ ",优惠规则:" + rule + ",优惠编码:" + fvid + "成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("优惠规则");
			loginfo.setRemk("修改");
			loginfo.setVold("登录产品:" + curUser.getProd() + "修改优惠规则:机构:" + ogcd
					+ ",优惠编码:" + fvid);
			loginfo.setVnew("成功");
			loginfo.setProd(curUser.getProd());
			logfileCmdService.insertLog(loginfo);
			flag = true;
		} else {
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "修改优惠规则:机构:" + ogcd
					+ ",优惠规则:" + rule + ",优惠编码:" + fvid + "失败!时间:"
					+ DataTimeClass.getCurDateTime());
			flag = false;
		}
		return flag;
	}
	
	public boolean insertFavrule(String userKey, String ogcd) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		boolean flag = false;
		try {
			a = accumFavruleMap.insRule(ogcd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (a>0) {
			log
			.info("初始优惠规则成功"
					+ "insert into trd_favrule_p003 "
					+ " select distinct '"
					+ ogcd
					+ "','F01','渠道优惠','int getvar(String para){ int fvda=0;return fvda;}','0' from trd_ogcd "
					+ " UNION"
					+ " select distinct '"
					+ ogcd
					+ "','F02','币别对优惠','int getvar(String para){ int fvda=0;return fvda;}','0' from trd_ogcd "
					+ " UNION"
					+ " select distinct '"
					+ ogcd
					+ "','F04','客户优惠','int getvar(String para){ int fvda=0;return fvda;}','0' from trd_ogcd "
					+ " UNION"
					+ " select distinct '"
					+ ogcd
					+ "','F03','金额优惠','int getvar(double para){ int fvda=0;return fvda;}','0' from trd_ogcd "
					+ " UNION" + " select distinct '" + ogcd
					+ "','F05','机构优惠','0','0' from trd_ogcd");
			
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "添加初始优惠规则:机构:" + ogcd
					+ "成功!时间:" + DataTimeClass.getCurDateTime());
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
			flag = true;
		} else {
			log
			.error("初始优惠规则失败"
					+ "insert into trd_favrule_p003 "
					+ " select distinct '"
					+ ogcd
					+ "','F01','渠道优惠','int getvar(String para){ int fvda=0;return fvda;}','0' from trd_ogcd "
					+ " UNION"
					+ " select distinct '"
					+ ogcd
					+ "','F02','币别对优惠','int getvar(String para){ int fvda=0;return fvda;}','0' from trd_ogcd "
					+ " UNION"
					+ " select distinct '"
					+ ogcd
					+ "','F04','客户优惠','int getvar(String para){ int fvda=0;return fvda;}','0' from trd_ogcd "
					+ " UNION"
					+ " select distinct '"
					+ ogcd
					+ "','F03','金额优惠','int getvar(double para){ int fvda=0;return fvda;}','0' from trd_ogcd "
					+ " UNION" + " select distinct '" + ogcd
					+ "','F05','机构优惠','0','0' from trd_ogcd");
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "添加初始优惠规则:机构:" + ogcd
					+ "失败!时间:" + DataTimeClass.getCurDateTime());
			flag = false;
		}
		return flag;
	}
}
