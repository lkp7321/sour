package com.ylxx.fx.service.impl.price.pricesourceimpl;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.price.pricesource.MktinfoMapper;
import com.ylxx.fx.service.LogfileCmdService;
import com.ylxx.fx.service.po.CmmChkpara;
import com.ylxx.fx.service.po.Logfile;
import com.ylxx.fx.service.po.Mktinfo;
import com.ylxx.fx.service.price.pricesource.MktinfoService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.UDPClient;
@Service("mktinfoService")
public class MktinfoServiceImpl implements MktinfoService {
	
	@Resource
	private MktinfoMapper mktinfoMap;
	@Resource(name="logfileCmdService")
	private LogfileCmdService logfileCmdService;
	UDPClient Psocket = new UDPClient();
	private static final Logger log = LoggerFactory.getLogger(MktinfoServiceImpl.class);
	
	//价格源注册
	public List<Mktinfo> getMkPrice() {
		return mktinfoMap.selectMkPrice();
	}
	
	//启用
	public boolean openChannel(String userKey, List<CmmChkpara> chlist) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		boolean flag = false;
		for (int i = 0; i < chlist.size(); i++) {
			CmmChkpara ppch = new CmmChkpara();

			ppch.setMkid(((CmmChkpara) chlist.get(i)).getMkid().toString());

			ppch.setExnm(((CmmChkpara) chlist.get(i)).getExnm().toString());
			ppch.setUsfg("0");
			a = mktinfoMap.updateUsfg(ppch);
			if (a>0) {
				log.info("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ "启用校验器有效标志,市场编号:" + ppch.getMkid() + ",币别对:"
						+ ppch.getExnm() + ",有效标志:" + ppch.getUsfg() + "成功!时间:"
						+ DataTimeClass.getCurDateTime());
				flag = true;
			} else {
				log.error("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ "启用校验器有效标志,市场编号:" + ppch.getMkid() + ",币别对:"
						+ ppch.getExnm() + ",有效标志:" + ppch.getUsfg() + "失败!时间:"
						+ DataTimeClass.getCurDateTime());
				flag = false;
				break;
			}
		}
		if(flag){
			Psocket.SendSocket();
		}
		return flag;
	}
	
	//停用
	public boolean closeChannel(String userKey, List<CmmChkpara> chlist) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int a = 0;
		boolean flag = false;
		for (int i = 0; i < chlist.size(); i++) {
			CmmChkpara ppch = new CmmChkpara();

			ppch.setMkid(((CmmChkpara) chlist.get(i)).getMkid().toString());

			ppch.setExnm(((CmmChkpara) chlist.get(i)).getExnm().toString());

			ppch.setUsfg("1");
			a = mktinfoMap.updateUsfg(ppch);
			if (a>0) {
				log.info("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ "停用校验器有效标志,市场编号:" + ppch.getMkid() + ",币别对:"
						+ ppch.getExnm() + ",有效标志:" + ppch.getUsfg() + "成功!时间:"
						+ DataTimeClass.getCurDateTime());
				flag = true;
			} else {
				log.error("用户：" + curUser.getUsnm() + " 登录IP:"
						+ curUser.getCurIP() + " 登录产品:" + curUser.getProd()
						+ "停用校验器有效标志,市场编号:" + ppch.getMkid() + ",币别对:"
						+ ppch.getExnm() + ",有效标志:" + ppch.getUsfg() + "失败!时间:"
						+ DataTimeClass.getCurDateTime());
				flag = false;
			}
		}
		if(flag){
			Psocket.SendSocket();
		}
		return flag;
	}

	//查询页面数据
//	public List<CmmChkpara> jiaoyansel(String mkid, String exnm) {
//		List<CmmChkpara> list = null;
//	    try {
//	    	if(mkid.equals("all")&&exnm.equals("所有")){
//				list = mktinfoMap.jiaoyansel(mkid, exnm);
//			}else{
//				if(mkid.equals("all")){
//					mkid = "";
//				}
//				if(exnm.equals("所有")){
//					exnm = "";
//				}
//				list = mktinfoMap.jiaoyansel1(mkid, exnm);
//			}
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//		}
//	    
//		if(list!=null && list.size()>0){
//			for (int i = 0; i < list.size(); i++) {
//				list.get(i).setUsfg(list.get(i).getUsfg().equals("0")?"启用":"停用");
//			}
//		}
//		return list;
//	}
	//校验器分页
	public PageInfo<CmmChkpara> pagejiaoyansel(Integer pageNo, Integer pageSize, String mkid, String exnm) {
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    List<CmmChkpara> list = null;
	    try {
	    	if(mkid.equals("all")&&exnm.equals("所有")){
				list = mktinfoMap.jiaoyansel(mkid, exnm);
			}else{
				if(mkid.equals("all")){
					mkid = "";
				}
				if(exnm.equals("所有")){
					exnm = "";
				}
				list = mktinfoMap.jiaoyansel1(mkid, exnm);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	    PageInfo<CmmChkpara> page = new PageInfo<CmmChkpara>(list);
		if(page.getList()!=null && page.getList().size()>0){
			for (int i = 0; i < page.getList().size(); i++) {
				page.getList().get(i).setUsfg(page.getList().get(i).getUsfg().equals("0")?"启用":"停用");
			}
		}
		return page;
	}
	
	public boolean inUpdate(CmmChkpara cmmbean){
		int a = mktinfoMap.selectPrice(cmmbean);
		int bool = 0;
		if(a>0){
			bool = mktinfoMap.updatePrice(cmmbean);
		}else{
			bool = mktinfoMap.insertPrice(cmmbean);
		}
		if(bool>0){
			return true;
		}else{
			return false;
		}
	}
	public double round(double v, int scale) {
		if (0 > scale) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal b = BigDecimal.valueOf(v);
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	public boolean updateCmmprice(CmmChkpara cmmbean, String userKey) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		int va = mktinfoMap.selectPION(cmmbean.getExnm());
		
		// DecimalFormat df = new DecimalFormat("0.0000");
		int vadg = (int) (Math.pow(10, va));
		BigDecimal mimd = (cmmbean.getMdmd().subtract(cmmbean.getMxdp())).divide(new BigDecimal(vadg));
		BigDecimal mxmd = (cmmbean.getMdmd().add(cmmbean.getMxdp())).divide(new BigDecimal(vadg));
		cmmbean.setXimd(mimd.divide(new BigDecimal(1), va, BigDecimal.ROUND_HALF_UP).doubleValue());
		cmmbean.setXxmd(mxmd.divide(new BigDecimal(1), va, BigDecimal.ROUND_HALF_UP).doubleValue());
	
		// cmmbean.setMIMD(df.format(mimd));
		// cmmbean.setMXMD(df.format(mxmd));

		boolean flag = inUpdate(cmmbean);
		if (flag) {
			Psocket.SendSocket();
			log.info("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "保存校验参数,市场:"
					+ cmmbean.getMknm() + ",市场编号:" + cmmbean.getMkid()
					+ ",币别对:" + cmmbean.getExnm() + ",价格类型:"
					+ cmmbean.getTpfg() + ",期限:" + cmmbean.getTerm() + ",钞汇标志:"
					+ cmmbean.getCxfg() + ",买入价:" + cmmbean.getMdmd()
					+ ",最大波动点数:" + cmmbean.getMxdp() + ",中间价两次波动点差:"
					+ cmmbean.getMxbp() + ",合法波动次数:" + cmmbean.getMxct()
					+ ",变为无效波动次数:" + cmmbean.getMxud() + ",使用标志:"
					+ cmmbean.getUsfg() + "成功!时间:"
					+ DataTimeClass.getCurDateTime());
			Logfile loginfo = new Logfile();
			loginfo.setUsem(curUser.getUsnm());
			loginfo.setTymo("价格源公告板模板表信息");
			loginfo.setRemk("更新");
			loginfo.setVold("登录产品:" + curUser.getProd() + "保存校验参数,市场:"
					+ cmmbean.getMknm() + ",市场编号:" + cmmbean.getMkid()
					+ ",币别对:" + cmmbean.getExnm() + ",价格类型:"
					+ cmmbean.getTpfg() + ",期限:" + cmmbean.getTerm() + ",钞汇标志:"
					+ cmmbean.getCxfg() + ",买入价:" + cmmbean.getMdmd()
					+ ",最大波动点数:" + cmmbean.getMxdp() + ",中间价两次波动点差:"
					+ cmmbean.getMxbp() + ",合法波动次数:" + cmmbean.getMxct()
					+ ",变为无效波动次数:" + cmmbean.getMxud() + ",使用标志:"
					+ cmmbean.getUsfg());
			loginfo.setVnew("成功");
			logfileCmdService.insertLog(loginfo);
		} else {
			log.error("用户：" + curUser.getUsnm() + " 登录IP:" + curUser.getCurIP()
					+ " 登录产品:" + curUser.getProd() + "保存校验参数,市场:"
					+ cmmbean.getMknm() + ",市场编号:" + cmmbean.getMkid()
					+ ",币别对:" + cmmbean.getExnm() + ",价格类型:"
					+ cmmbean.getTpfg() + ",期限:" + cmmbean.getTerm() + ",钞汇标志:"
					+ cmmbean.getCxfg() + ",买入价:" + cmmbean.getMdmd()
					+ ",最大波动点数:" + cmmbean.getMxdp() + ",中间价两次波动点差:"
					+ cmmbean.getMxbp() + ",合法波动次数:" + cmmbean.getMxct()
					+ ",变为无效波动次数:" + cmmbean.getMxud() + ",使用标志:"
					+ cmmbean.getUsfg() + "失败!时间:"
					+ DataTimeClass.getCurDateTime());
		}
		return flag;
	}
	//查询价格类型
	public List<Map<String, Object>> selUpType() {
		// TODO Auto-generated method stub
		return mktinfoMap.selUpType();
	}
}
