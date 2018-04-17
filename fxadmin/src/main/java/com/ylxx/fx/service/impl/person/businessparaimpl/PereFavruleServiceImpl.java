package com.ylxx.fx.service.impl.person.businessparaimpl;

import java.util.*;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.person.businesspara.PereFavruleMapper;
import com.ylxx.fx.core.mapper.person.system.OrganizationMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.person.businesspara.PereFavruleService;
import com.ylxx.fx.service.po.FavourRule;
import com.ylxx.fx.service.po.Favrule;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.service.po.Trd_Favrule;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.price.Formula;

@Service("perefavruleService")
public class PereFavruleServiceImpl implements PereFavruleService {
	
	private static final Logger log = LoggerFactory.getLogger(PereFavruleServiceImpl.class);
	@Resource
	private PereFavruleMapper pereFavrulemap;
	@Resource
	private OrganizationMapper organmap;
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	
	// 开启标志
	public boolean openChannel(String userKey, List<Trd_Favrule> chlist) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		boolean flag = false;
		int a = 0;
		for (int i = 0; i < chlist.size(); i++) {
			Trd_Favrule ppch = new Trd_Favrule();
			ppch.setFvid(((Trd_Favrule) chlist.get(i)).getFvid().toString());
			ppch.setFvnm(((Trd_Favrule) chlist.get(i)).getFvnm().toString());
			ppch.setStat("0");
			ppch.setOgcd(((Trd_Favrule) chlist.get(i)).getOgcd().toString());
			ppch.setOgnm(((Trd_Favrule) chlist.get(i)).getOgnm().toString());
			try {
				a = pereFavrulemap.Begin(ppch);
				if(a>0){
					flag = true;
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if (flag) {
				log.info("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ "启用优惠规则:机构:" + ppch.getOgcd() + ",机构名称:"
						+ ppch.getOgnm() + ",优惠编码:" + ppch.getFvid() + ",优惠名称:"
						+ ppch.getFvnm() + ",状态：0,成功!时间:"
						+ DataTimeClass.getCurDateTime());
				Logfile loginfo = new Logfile();
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("机构优惠规则");
				loginfo.setRemk("启用");
				loginfo.setProd(curUser.getProd());
				loginfo.setVold("登录产品:" + curUser.getProd() + "启用优惠规则:机构:"
						+ ppch.getOgcd() + ",机构名称:" + ppch.getOgnm() + ",优惠编码:"
						+ ppch.getFvid() + ",优惠名称:" + ppch.getFvnm() + ",状态：0");
				loginfo.setVnew("成功");
				logfileCmdService.insertLog(loginfo);
			} else {
				log.error("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ "启用优惠规则:机构:" + ppch.getOgcd() + ",机构名称:"
						+ ppch.getOgnm() + ",优惠编码:" + ppch.getFvid() + ",优惠名称:"
						+ ppch.getFvnm() + ",状态：0,失败!时间:"
						+ DataTimeClass.getCurDateTime());
			}
		}
		return flag;
	}

	// 关闭标志
	public boolean closeChannel(String userKey, List<Trd_Favrule> chlist) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		boolean flag = false;
		int a=0;
		for (int i = 0; i < chlist.size(); i++) {
			Trd_Favrule ppch = new Trd_Favrule();
			ppch.setFvid(((Trd_Favrule) chlist.get(i)).getFvid().toString());
			ppch.setFvnm(((Trd_Favrule) chlist.get(i)).getFvnm().toString());
			ppch.setStat("1");
			ppch.setOgcd(((Trd_Favrule) chlist.get(i)).getOgcd().toString());
			ppch.setOgnm(((Trd_Favrule) chlist.get(i)).getOgnm().toString());
			try {
				a = pereFavrulemap.End(ppch);
				if(a>0){
					flag = true;
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if (flag) {
				log.info("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ "停用优惠规则:机构:" + ppch.getOgcd() + ",机构名称:"
						+ ppch.getOgnm() + ",优惠编码:" + ppch.getFvid() + ",优惠名称:"
						+ ppch.getFvnm() + ",状态：1,成功!时间:"
						+ DataTimeClass.getCurDateTime());
				Logfile loginfo = new Logfile();
				loginfo.setUsem(curUser.getUsnm());
				loginfo.setTymo("机构优惠规则");
				loginfo.setRemk("停用");
				loginfo.setProd(curUser.getProd());
				loginfo.setVold("登录产品:" + curUser.getProd() + "停用优惠规则:机构:"
						+ ppch.getOgcd() + ",机构名称:" + ppch.getOgnm() + ",优惠编码:"
						+ ppch.getFvid() + ",优惠名称:" + ppch.getFvnm() + ",状态：1");
				loginfo.setVnew("成功");
				logfileCmdService.insertLog(loginfo);
			} else {
				log.error("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ "停用优惠规则:机构:" + ppch.getOgcd() + ",机构名称:"
						+ ppch.getOgnm() + ",优惠编码:" + ppch.getFvid() + ",优惠名称:"
						+ ppch.getFvnm() + ",状态：1,失败!时间:"
						+ DataTimeClass.getCurDateTime());
			}
		}
		return flag;
	}

	//分页查询 交易流水数据
	public PageInfo<Favrule> getPereFenfavrule(String ogcd,
			Integer pageNo, Integer pageSize) {
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    List<Favrule> list = null;
		try {
			if(ogcd.equals("0001")){
				ogcd = "all";
			}
			list = pereFavrulemap.selPereFenfavrule(ogcd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		 //用PageInfo对结果进行包装
	    PageInfo<Favrule> page = new PageInfo<Favrule>(list);
		return page;
	}
	//导出
	public List<Favrule> getAllPereFenfavrule(String ogcd
			) {
	    List<Favrule> list = null;
		try {
			if(ogcd.equals("0001")){
				ogcd = "all";
			}
			list = pereFavrulemap.selPereFenfavrule(ogcd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	
	//初始化修改页面
	public List<FavourRule> onSelFavrule(String rule, String fvid) {
		List<FavourRule> list = new ArrayList<FavourRule>();
		FavourRule fRule = null;
		HashMap hashmap = getHashMap(rule);

		if (fvid.equals("F02")) {
			List<Map<String, Object>> rs = pereFavrulemap.selectExnm();
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

			if (fvid.equals("F02")) {
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
				String s=null;
				try {
					s = pereFavrulemap.SelClient(para);
					if(s!=null){
						strname = s;
					}else{
						strname = "其他";
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				if (para.equals("DEFAULT")) {
					fRule.setMyLabel("默认值");
					fRule.setMyData("0");
				} else {
					fRule.setMyLabel(strname);
					fRule.setMyData(fvda + "");
				}
				fRule.setMyValue(para);

				list.add(fRule);
			}
		}
		return list;
	}
	
	//规则处理，
	public HashMap getHashMap(String rule) {
		HashMap hashmap = Formula.getFvdaMap(rule);
		return hashmap;
	}
	//客户优惠
	public List<Map<String, Object>> custboxData() {
		List<Map<String, Object>> list = null;
		try {
			list = pereFavrulemap.SelClient1();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	//保存添加
	public boolean saveFavruleRule(String userKey, List<FavourRule> list, String fvid,
			String ogcd) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		boolean flag = false;
		HashMap<String, Integer> hm = new HashMap<String, Integer>();

		for (int i = 0; i < list.size(); i++) {
			FavourRule fRule = ((FavourRule) list.get(i));

			hm.put(fRule.getMyValue().trim(), Integer.valueOf(fRule.getMyData()
					.trim()));
		}

		String rule = Formula.compRule(hm, fvid);
		try {
			a = pereFavrulemap.onsave(ogcd.trim(), rule, fvid.trim());
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
			loginfo.setTymo("机构优惠规则");
			loginfo.setRemk("修改");
			loginfo.setProd(curUser.getProd());
			loginfo.setVold("登录产品:" + curUser.getProd() + "修改优惠规则:机构:" + ogcd
					+ ",优惠编码:" + fvid);
			loginfo.setVnew("成功");
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
	
	//撤销与修改流水查询导出
	public List<Tranlist> selectTranlist(String userkey ,String start ,String end) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userkey);
		String ogcd = curUser.getOrgn();// 当前分行或总行机构
		List<Tranlist> list = null;
		try {
			list = pereFavrulemap.selectTranlist(start, end, ogcd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	//撤销与修改流水查询分页
	public PageInfo<Tranlist> pageTranlist(String userkey ,String start ,String end,
			Integer pageNo, Integer pageSize){
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userkey);
		String ogcd = curUser.getOrgn();// 当前分行或总行机构
		List<Tranlist> list = null;
		try {
			list = pereFavrulemap.selectTranlist(start, end, ogcd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		PageInfo<Tranlist> page = new PageInfo<Tranlist>(list);
		return page;
	}
	
}
