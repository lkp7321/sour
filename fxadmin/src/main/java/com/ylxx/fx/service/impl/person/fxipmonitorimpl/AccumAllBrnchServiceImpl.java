package com.ylxx.fx.service.impl.person.fxipmonitorimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.FormuleVo;
import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.core.mapper.person.fxipmonitor.AccuBrnchMonitorMapper;
import com.ylxx.fx.service.person.fxipmonitor.AccumBrnchService;
import com.ylxx.fx.service.po.AccinfoMonitorBean;
import com.ylxx.fx.service.po.ChangeInfoList;
import com.ylxx.fx.service.po.CkTotalBean;
import com.ylxx.fx.service.po.FormuleBean;
import com.ylxx.fx.service.po.QYInfoList;
import com.ylxx.fx.service.po.Trd_ogcd;
import com.ylxx.fx.utils.ComparatorAccinfo;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.TestFormatter;
import com.ylxx.fx.utils.price.Formula;
/**
 * 价格监控
 * @author lz130
 *
 */
@Service("accumAllBrnchService")
public class AccumAllBrnchServiceImpl implements AccumBrnchService{
	
	@Resource
	private AccuBrnchMonitorMapper accumbrnchMap;
	private static final Logger log = LoggerFactory.getLogger(AccumAllBrnchServiceImpl.class);
	private TestFormatter test = new TestFormatter();
	private List<FxipMonitorVo> oldlist = null;
	/**
	 * 民生金
	 * 总分行价格
	 */
	public List<FxipMonitorVo> getAccumAllPdtBrnch() {
		List<FxipMonitorVo> list = null;
		try {
			list = accumbrnchMap.selectAccumAllPdtBrnch();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}		
		if(list!=null&&list.size()>0){
			if(oldlist!=null && oldlist.size()>0) {
				for (int i = 0; i < list.size(); i++) {
					list.get(i).setStfg(list.get(i).getStfg().equals("0")?"开盘":"停盘");
					list.get(i).setTrfg(list.get(i).getTrfg().equals("0")?"可交易":"不可交易");
					if(list.get(i).getNemd().compareTo(oldlist.get(i).getNemd())==1) {list.get(i).setColor("red");}
					else if(list.get(i).getNemd().compareTo(oldlist.get(i).getNemd())==-1) {list.get(i).setColor("green");}
					else {list.get(i).setColor("black");}
				}
			}else {
				for (int i = 0; i < list.size(); i++) {
					list.get(i).setStfg(list.get(i).getStfg().equals("0")?"开盘":"停盘");
					list.get(i).setTrfg(list.get(i).getTrfg().equals("0")?"可交易":"不可交易");
					list.get(i).setColor("black");
				}
			}
			oldlist = list;
		}
		return list;
	}

	/**
	 * 民生金，客户价格
	 */
	public List<FxipMonitorVo> getAccumAllPdtCust() {
		List<FxipMonitorVo> list = null;
		try {
			list = accumbrnchMap.selectAccumAllPdtCust();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setStfg(list.get(i).getStfg().equals("0")?"开盘":"停盘");
				list.get(i).setTrfg(list.get(i).getTrfg().equals("0")?"可交易":"不可交易");
			}
		}
		return list;
	}
	
	
	/**
	 * 积存金 总敞口
	 */
	/**
	 * 市场报价
	 * 数据1
	 */
	public List<CkTotalBean> ckTotalData() {
		List<CkTotalBean> list = new ArrayList<CkTotalBean>();
		try {
			list = accumbrnchMap.ckTotalData();
		} catch (Exception e) {
			log.error("查询市场报价出错");
			log.error(e.getMessage(), e);
		}
		return list;
	}

	public List<CkTotalBean> ckTotalChange() {
		List<CkTotalBean> list = new ArrayList<CkTotalBean>();
		List<CkTotalBean> newlist = new ArrayList<CkTotalBean>();
		List<CkTotalBean> rtnlist = new ArrayList<CkTotalBean>();
		try {
			list = accumbrnchMap.ckTotalYk();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		int i = 0;
		if (list != null && list.size() > 0) {
			CkTotalBean ckBean = null;
			CkTotalBean ckTotal = null;
			for (i = 0; i < list.size(); i++) {
				ckBean = list.get(i);
				ckBean = onFormule(ckBean);
				newlist.add(ckBean);
				//ckTotal = ckTotalChange(ckBean);
				//newlist.add(ckTotal);
			}
		}
//		CkTotalBean ck = null;
//		for (i = 0; i < 4; i++) {
//			ck = new CkTotalBean();
//			ck = newlist.get(i);
//			rtnlist.add(ck);
//		}
//		rtnlist.add(ckTotalKAGRMB(newlist));
//		rtnlist.add(ckTotalKAGUSD(newlist));
//		for (i = 4; i < 8; i++) {
//			ck = new CkTotalBean();
//			ck = newlist.get(i);
//			rtnlist.add(ck);
//		}
//		rtnlist.add(ckTotalKAURMB(newlist));
//		rtnlist.add(ckTotalKAUUSD(newlist));
		return newlist;
	}
	/**
	 * 总敞口   AND 折算敞口
	 * 数据2
	 * @return
	 */
	public List<CkTotalBean> ckTotal(){
		List<CkTotalBean> newlist= ckTotalChange();
		List<CkTotalBean> rtnlist= new ArrayList<CkTotalBean>();
		CkTotalBean ck = null;
		for(int i=0;i<newlist.size();i++){
			ck=newlist.get(i);
			rtnlist.add(ck);
		}
//		rtnlist.add(newckTotalKAGRMB(newlist));
		rtnlist.add(newckTotalKAURMB(newlist));
		rtnlist.add(newckTotalUSDRMB(newlist));
		return rtnlist;
	}
	
	/**
	 * 计算USDRMB
	 * 折算敞口
	 * @param list
	 * @return
	 */
	public CkTotalBean newckTotalUSDRMB(List<CkTotalBean> list) {
		CkTotalBean frmbean = new CkTotalBean();
		double lamt = 0;
		double ramt = 0;
		double fdsy = 0;
		double ckmd = 0;
		double zcsy = 0;
		String excd ="";
		double nemd = accumbrnchMap.USDCNYNEMD();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNmex().contains("美元/人民币")) {
				lamt += list.get(i).getLamt();
				ramt += list.get(i).getRamt();
				fdsy += list.get(i).getFdsy();
				ckmd=(list.get(i).getNeby()+list.get(i).getNesl())/2;
				zcsy = list.get(i).getZcyk();
				excd = list.get(i).getExcd();
			}
			if(list.get(i).getNmex().contains("盎司白银/美元")||list.get(i).getNmex().contains("盎司黄金/美元")){
				lamt += list.get(i).getRamt();
				ramt += Math.abs(list.get(i).getRamt())*nemd;
				fdsy += list.get(i).getFdsy()*nemd;
			}
		}
		frmbean.setExnm("USDRMB");
		frmbean.setExcd(excd);
		frmbean.setNmex("美元/人民币");
		frmbean.setLamt(new Double(test.getDecimalFormat(lamt+"",2)));
		frmbean.setRamt(new Double(test.getDecimalFormat((ramt-zcsy)+"",2)));
		frmbean.setFdsy(new Double(test.getDecimalFormat(fdsy+"",2)));
		frmbean.setNemd(Double.valueOf(test.getDecimalFormat(ckmd+"",4)));
		frmbean.setZcyk(zcsy);
		if(frmbean.getLamt()!=0){
			frmbean.setCbhl(test.getDecimalFormat(String.valueOf(Math.abs(frmbean.getRamt()/frmbean.getLamt())),4));
		}else{
			frmbean.setCbhl("0");
		}
		
		return frmbean;
	}
	public CkTotalBean newckTotalKAGRMB(List<CkTotalBean> list) {
		CkTotalBean frmbean = new CkTotalBean();
		double lamt = 0;
		double ramt = 0;
		double fdsy = 0;
		double ckmd = 0;
		double zcsy = 0;
		String excd="";
		double nemd = accumbrnchMap.USDCNYNEMD();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNmex().contains("克白银/人民币")) {
				lamt += list.get(i).getLamt();
				ramt += list.get(i).getRamt();
				fdsy += list.get(i).getFdsy();
				if(list.get(i).getExnm().equals("KAGRMB")){
					ckmd=(list.get(i).getNeby()+list.get(i).getNesl())/2;
					zcsy=list.get(i).getZcyk();
					excd=list.get(i).getExcd();
				}
			}
			if (list.get(i).getNmex().contains("盎司白银/美元")) {
				lamt += list.get(i).getLamt()*31.1035;
				ramt += list.get(i).getRamt()*nemd;
				fdsy += list.get(i).getFdsy()*nemd;
			}
		}
		frmbean.setExnm("KAGRMB");
		frmbean.setExcd(excd);
		frmbean.setNmex("克白银/人民币");
		frmbean.setLamt(new Double(test.getDecimalFormat(lamt+"",4)));
		frmbean.setRamt(new Double(test.getDecimalFormat((ramt-zcsy)+"",2)));
		frmbean.setFdsy(new Double(test.getDecimalFormat(fdsy+"",2)));
		frmbean.setNemd(Double.valueOf(test.getDecimalFormat(ckmd+"",4)));
		frmbean.setZcyk(zcsy);
		if(frmbean.getLamt()!=0){
			frmbean.setCbhl(test.getDecimalFormat(String.valueOf(Math.abs(frmbean.getRamt()/frmbean.getLamt())),4));
		}else{
			frmbean.setCbhl("0");
		}
		return frmbean;
	}
	/**
	 * 计算KAURMB的折算敞口
	 * @param list
	 * @return
	 */
	public CkTotalBean newckTotalKAURMB(List<CkTotalBean> list) {
		CkTotalBean frmbean = new CkTotalBean();
		double lamt = 0;
		double ramt = 0;
		double fdsy = 0;
		double ckmd = 0;
		double zcsy = 0;
		String excd="";
		double nemd = accumbrnchMap.USDCNYNEMD();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNmex().contains("克黄金/人民币")) {
				lamt += list.get(i).getLamt();
				ramt += list.get(i).getRamt();
				fdsy += list.get(i).getFdsy();
				if(list.get(i).getExnm().equals("KAURMB")){
					ckmd=(list.get(i).getNeby()+list.get(i).getNesl())/2;
					zcsy=list.get(i).getZcyk();
					excd=list.get(i).getExcd();
				}
			}
			if (list.get(i).getNmex().contains("盎司黄金/美元")) {
				lamt += list.get(i).getLamt()*31.1035;
				ramt += list.get(i).getRamt()*nemd;
				fdsy += list.get(i).getFdsy()*nemd;
			}
		}
		frmbean.setExnm("KAURMB");
		frmbean.setExcd(excd);
		frmbean.setNmex("克黄金/人民币");
		frmbean.setLamt(new Double(test.getDecimalFormat(lamt+"",4)));
		frmbean.setRamt(new Double(test.getDecimalFormat((ramt-zcsy)+"",2)));
		frmbean.setFdsy(new Double(test.getDecimalFormat(fdsy+"",2)));
		frmbean.setNemd(Double.valueOf(test.getDecimalFormat(ckmd+"",4)));
		frmbean.setZcyk(zcsy);
		if(frmbean.getLamt()!=0){
			frmbean.setCbhl(test.getDecimalFormat(String.valueOf(Math.abs(frmbean.getRamt()/frmbean.getLamt())),4));
		}else{
			frmbean.setCbhl("0");
		}
		return frmbean;
	}
	/**
	 * 更新敞口中对外损益
	 * @param ckTotalBean
	 * @return
	 */
	public boolean updateCKZCYK(String userKey,CkTotalBean ckTotalBean){
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		boolean flag = false;
		try {
			int a=accumbrnchMap.updateCKZCYK(ckTotalBean);
			flag = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag = false;
		}
		log.info("print--flag-"+flag);
		if(flag){
			if(ckTotalBean.getLamt()!=0){
				ckTotalBean.setCbhl(test.getDecimalFormat(String.valueOf(Math.abs(ckTotalBean.getRamt()/ckTotalBean.getLamt())),4));
			}else{
				ckTotalBean.setCbhl("0");
			}
			accumbrnchMap.insertTQTranList(curUser.getUsnm(), ckTotalBean);
		}
		return flag;
	}
	public CkTotalBean ckToalSYYL() {
		List<CkTotalBean> list = ckTotalChange();
		CkTotalBean ckTotalBean = hjsy(list);
		return ckTotalBean;
	}

	public CkTotalBean hjsy(List<CkTotalBean> list) {
		CkTotalBean ckTotalBean = new CkTotalBean();
		BigDecimal nemd = new BigDecimal(accumbrnchMap.USDCNYNEMD());
		BigDecimal zfdsy = BigDecimal.valueOf(0);
		BigDecimal rmbzyl = BigDecimal.valueOf(0);
		BigDecimal usdzyl = BigDecimal.valueOf(0);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getNmex().contains("人民币")) {
					zfdsy = zfdsy.add(new BigDecimal(list.get(i).getFdsy()));
					rmbzyl = rmbzyl.add(new BigDecimal(list.get(i).getHjsy()));
				}
				if (list.get(i).getNmex().contains("美元")) {
					usdzyl = usdzyl.add(new BigDecimal(list.get(i).getHjsy()));
				}
			}
		}
		ckTotalBean.setZfdsy(zfdsy.setScale(2, 4) + "");
		ckTotalBean.setRmbzyl(rmbzyl.setScale(2, 4) + "");
		ckTotalBean.setUsdzyl(usdzyl.setScale(2, 4) + "");

		ckTotalBean.setRmbhzsy((zfdsy.add(rmbzyl).add(usdzyl.multiply(nemd)
				.setScale(2, 4))).setScale(2, 4)
				+ "");
		return ckTotalBean;
	}

	public CkTotalBean ckTotalKAGRMB(List<CkTotalBean> list) {
		CkTotalBean frmbean = new CkTotalBean();
		double lamt = 0;
		double ramt = 0;
		double fdsy = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNmex().equals("克白银/人民币")) {
				lamt += list.get(i).getLamt();
				ramt += list.get(i).getRamt();
				fdsy += list.get(i).getFdsy();
			}
		}
		frmbean.setExnm("合计");
		frmbean.setNmex("克白银/人民币");
		frmbean.setLamt(lamt);
		frmbean.setRamt(ramt);
		frmbean.setFdsy(fdsy);
		return frmbean;
	}

	public CkTotalBean ckTotalKAGUSD(List<CkTotalBean> list) {
		CkTotalBean frmbean = new CkTotalBean();
		double lamt = 0;
		double ramt = 0;
		double fdsy = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNmex().equals("盎司白银/美元")) {
				lamt += list.get(i).getLamt();
				ramt += list.get(i).getRamt();
				fdsy += list.get(i).getFdsy();
			}
		}
		frmbean.setExnm("合计");
		frmbean.setNmex("盎司白银/美元");
		frmbean.setLamt(lamt);
		frmbean.setRamt(ramt);
		frmbean.setFdsy(fdsy);
		return frmbean;
	}

	public CkTotalBean ckTotalKAURMB(List<CkTotalBean> list) {
		CkTotalBean frmbean = new CkTotalBean();
		double lamt = 0;
		double ramt = 0;
		double fdsy = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNmex().equals("克黄金/人民币")) {
				lamt += list.get(i).getLamt();
				ramt += list.get(i).getRamt();
				fdsy += list.get(i).getFdsy();
			}
		}
		frmbean.setExnm("合计");
		frmbean.setNmex("克黄金/人民币");
		frmbean.setLamt(lamt);
		frmbean.setRamt(ramt);
		frmbean.setFdsy(fdsy);
		return frmbean;
	}

	public CkTotalBean ckTotalKAUUSD(List<CkTotalBean> list) {
		CkTotalBean frmbean = new CkTotalBean();
		double lamt = 0;
		double ramt = 0;
		double fdsy = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNmex().equals("盎司黄金/美元")) {
				lamt += list.get(i).getLamt();
				ramt += list.get(i).getRamt();
				fdsy += list.get(i).getFdsy();
			}
		}
		frmbean.setExnm("合计");
		frmbean.setNmex("盎司黄金/美元");
		frmbean.setLamt(lamt);
		frmbean.setRamt(ramt);
		frmbean.setFdsy(fdsy);
		return frmbean;
	}

	public CkTotalBean ckTotalChange(CkTotalBean formule) {
		CkTotalBean frmbean = new CkTotalBean();
		double nemd = accumbrnchMap.USDCNYNEMD();
		if (formule.getExnm().contains("RMB")) {
			if (formule.getExnm().contains("KAG"))
				frmbean.setNmex("盎司白银/美元");
			else
				frmbean.setNmex("盎司黄金/美元");
			frmbean.setExnm("换算");
			frmbean.setLamt(new Double(test.getDecimalFormat(formule.getLamt()
					/ 31.1035 + "", 4)));
			frmbean.setRamt(new Double(test.getDecimalFormat(formule.getRamt()
					/ nemd + "", 2)));
			frmbean.setFdsy(new Double(test.getDecimalFormat(formule.getFdsy()
					/ nemd + "", 2)));
		} else {
			if (formule.getExnm().contains("KAG"))
				frmbean.setNmex("克白银/人民币");
			else
				frmbean.setNmex("克黄金/人民币");
			frmbean.setExnm("换算");
			frmbean.setLamt(new Double(test.getDecimalFormat(formule.getLamt()
					* 31.1035 + "", 4)));
			frmbean.setRamt(new Double(test.getDecimalFormat(formule.getRamt()
					* nemd + "", 2)));
			frmbean.setFdsy(new Double(test.getDecimalFormat(formule.getFdsy()
					* nemd + "", 2)));
		}

		// frmbean.setCBHL("");
		// frmbean.setZCYK(0);
		// frmbean.setSGYK(0);
		// frmbean.setPPYK(0);
		// frmbean.setHJSY(0);
		// frmbean.setNEBY(0);
		// frmbean.setNESL(0);
		return frmbean;
	}

	private CkTotalBean onFormule(CkTotalBean formule) {
		CkTotalBean frmbean = new CkTotalBean();
		double cbhl = 0;

		if (Math.abs(formule.getLamt()) <= 0.0) // 成本汇率
			cbhl = 0;
		else
			cbhl = Math.abs(formule.getRamt() / formule.getLamt());

		if (formule.getLamt() > 0.0) {
			// 买入价
			formule.setFdsy(formule.getLamt() * formule.getNeby()
					+ formule.getRamt());
		} else {
			// 卖出价
			formule.setFdsy(formule.getLamt() * formule.getNesl()
					+ formule.getRamt());
		}
		if ((formule.getNeby() == 0) && (formule.getNesl() == 0)) {
			formule.setFdsy(0.0);
		}
		try {
			// getDecimalFormat
			frmbean.setExnm(formule.getExnm());
			frmbean.setExcd(formule.getExcd());
			frmbean.setNmex(formule.getNmex());
			frmbean.setLamt(formule.getLamt());
			frmbean.setRamt(formule.getRamt());

			String cbhlstr = "0";
			cbhlstr = cbhl + "";

			cbhlstr = test.getDecimalFormat(cbhlstr, 4);
			formule.setFdsy(new Double(test.getDecimalFormat(formule.getFdsy()
					+ "", 2)));

			frmbean.setCbhl(cbhlstr);
			if (Math.abs(formule.getFdsy()) == 0.00) {
				frmbean.setFdsy(Math.abs(formule.getFdsy()));
			} else
				frmbean.setFdsy(formule.getFdsy());
				// frmbean.setUDFG(formule.getUDFG());
				frmbean.setZcyk(formule.getZcyk());
				frmbean.setSgyk(formule.getSgyk());
				frmbean.setPpyk(formule.getPpyk());
				frmbean.setHjsy(formule.getZcyk() + formule.getSgyk()
						+ formule.getPpyk());
				frmbean.setNeby(formule.getNeby());
				frmbean.setNesl(formule.getNesl());
		} catch (Exception en) {
			 log.error(en.getMessage(),en);
		}
		
		return frmbean;
	}
	
	/**
	 * 分类敞口
	 */
	@Override
	public List<Map<String, Object>> getaccumCknoTree() {
		// TODO Auto-generated method stub
		return accumbrnchMap.accumCknoTree();
	}

	@Override
	public List<FormuleBean> getselectclassCk(String prcd, String ckno) {
		List<FormuleVo> list = null;
		List<FormuleBean> list1 = new ArrayList<FormuleBean>();
		Formula f = new Formula();
		TestFormatter test = new TestFormatter();
			list = accumbrnchMap.selectclassCk(prcd, ckno);
			if(list!=null&&list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					FormuleBean cmmbean = new FormuleBean();
					cmmbean.setUdfg(list.get(i).getUdfg());
					cmmbean.setPrcd(list.get(i).getPrcd());
					cmmbean.setExnm(list.get(i).getExnm());
					cmmbean.setExcd(list.get(i).getExcd());
					cmmbean.setLamt(Double.parseDouble(list.get(i).getLamt()));
					cmmbean.setRamt(Double.parseDouble(list.get(i).getRamt()));
					cmmbean.setFdsy(Double.parseDouble(test.getDecimalFormat(list.get(i).getFdsy()+"", 2)));
					cmmbean.setPrice(Double.parseDouble(test.getDecimalFormat(list.get(i).getNeby()+"",4)));
					cmmbean.setPricer(Double.parseDouble(test.getDecimalFormat(list.get(i).getNesl()+"",4)));
					cmmbean.setZcyk(Double.parseDouble(list.get(i).getZcyk()));
					cmmbean.setPpyk(Double.parseDouble(list.get(i).getPpyk()));
					cmmbean.setTryk(Double.parseDouble(list.get(i).getTryk()));
					cmmbean.setSgyk(Double.parseDouble(list.get(i).getSgyk()));
					cmmbean.setToyk(Double.parseDouble(list.get(i).getToyk()));
					list1.add(f.onFormule(cmmbean));
				}
			}
		return list1;
	}

	/**
	 * 账户信息统计
	 */
	public List<AccinfoMonitorBean> getAccinfoList(String orgn, String date) {
		AccinfoMonitorBean accinfoBean=null;
		List<AccinfoMonitorBean> accinfoList=new ArrayList<AccinfoMonitorBean>();
		BigDecimal talHqam=BigDecimal.valueOf(0.0);
		BigDecimal talDqam=BigDecimal.valueOf(0.0);
		BigDecimal talAmnt=BigDecimal.valueOf(0.0);
		String talAccdate = "" ;
		String talNowtime = "" ;
		String talOgnm = "" ;
		List<AccinfoMonitorBean> list = null;
		//查询当前用户机构等级
		String level =null;
		try {
			level = accumbrnchMap.selLevel(orgn);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(level.equals("1")){	// 如果当前用户是一级机构 
			try {
				list = accumbrnchMap.selAccumTrdLevel1(date, orgn);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if(list!=null&&list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					if(orgn.equals(list.get(i).getOgcd())){
						talAccdate = list.get(i).getAccDate();
						talNowtime = list.get(i).getNowTime();
						talOgnm = list.get(i).getOgnm();
						talHqam=talHqam.add(list.get(i).getHqam());
						talDqam=talDqam.add(list.get(i).getDqam());
						talAmnt=talAmnt.add(list.get(i).getAmnt());
						continue;
					}
					accinfoBean=new AccinfoMonitorBean();
					accinfoBean.setAccDate(list.get(i).getAccDate());
					accinfoBean.setNowTime(list.get(i).getNowTime());
					accinfoBean.setOgnm(list.get(i).getOgnm());
					accinfoBean.setOgcd(list.get(i).getOgcd());
					accinfoBean.setHqam(list.get(i).getHqam());
					accinfoBean.setDqam(list.get(i).getDqam());
					accinfoBean.setAmnt(list.get(i).getAmnt());
					//将所以当前的三个累计量相加为后面的总计预备
					talHqam=talHqam.add(list.get(i).getHqam());
					talDqam=talDqam.add(list.get(i).getDqam());
					talAmnt=talAmnt.add(list.get(i).getAmnt());
					accinfoList.add(accinfoBean);
					
				}
			}
			ComparatorAccinfo comparator=new ComparatorAccinfo();
			Collections.sort(accinfoList,comparator);
			
			accinfoBean=new AccinfoMonitorBean();
			if(accinfoList.size()>0){
				accinfoBean.setAccDate(accinfoList.get(1).getAccDate());
				accinfoBean.setNowTime(accinfoList.get(1).getNowTime());
				Trd_ogcd trdogcd = null;
				try {
					trdogcd = accumbrnchMap.selOgcdNm(orgn);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				accinfoBean.setOgcd(trdogcd.getOgcd());
				accinfoBean.setOgnm(trdogcd.getOgnm());
				accinfoBean.setHqam(talHqam);
				accinfoBean.setDqam(talDqam);
				accinfoBean.setAmnt(talAmnt);
				accinfoList.add(accinfoBean);
				//添加总和数据到第一个位置
				accinfoList.add(0, accinfoList.get(accinfoList.size()-1));
				//移出重复的总和数据
				accinfoList.remove(accinfoList.size()-1);
			}		
		}
		if(level.equals("0")){	// 如果当前用户机构是总行
			try {
				list = accumbrnchMap.selAccumTrdLevel2(date);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if (list != null && list.size() > 0) {
				for (int k = 0; k <list.size(); k++) {
					if(orgn.equals(list.get(k).getOgcd())){
						talAccdate = list.get(k).getAccDate();
						talNowtime = list.get(k).getNowTime();
						talOgnm = list.get(k).getOgnm();
						talHqam=talHqam.add(list.get(k).getHqam());
						talDqam=talDqam.add(list.get(k).getDqam());
						talAmnt=talAmnt.add(list.get(k).getAmnt());
						continue;
					}
					accinfoBean=new AccinfoMonitorBean();
					accinfoBean.setAccDate(list.get(k).getAccDate());
					accinfoBean.setNowTime(list.get(k).getNowTime());
					accinfoBean.setOgnm(list.get(k).getOgnm());
					accinfoBean.setOgcd(list.get(k).getOgcd());
					accinfoBean.setHqam(list.get(k).getHqam());
					accinfoBean.setDqam(list.get(k).getDqam());
					accinfoBean.setAmnt(list.get(k).getAmnt());
					//将所以当前的三个累计量相加为后面的总计预备
					talHqam=talHqam.add(accinfoBean.getHqam());
					talDqam=talDqam.add(accinfoBean.getDqam());
					talAmnt=talAmnt.add(accinfoBean.getAmnt());
					accinfoList.add(accinfoBean);
				}
			}
		}
			accinfoBean=new AccinfoMonitorBean();
			if(accinfoList.size()>0){
				accinfoBean.setAccDate(accinfoList.get(1).getAccDate());
				accinfoBean.setNowTime(accinfoList.get(1).getNowTime());
				//查询当前用户的机构情况，做总计并添加到list集合中
				Trd_ogcd trdogcd = null;
				try {
					trdogcd = accumbrnchMap.selOgcdNm(orgn);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
				accinfoBean.setOgcd(trdogcd.getOgcd());
				accinfoBean.setOgnm(trdogcd.getOgnm());
				
				accinfoBean.setHqam(talHqam);
				accinfoBean.setDqam(talDqam);
				accinfoBean.setAmnt(talAmnt);
				accinfoList.add(accinfoBean);
				//将集合进行按照ogcd排序
				ComparatorAccinfo comparator=new ComparatorAccinfo();
				Collections.sort(accinfoList,comparator);	
		}
		if(accinfoList.size()<=0 && !"".equals(talOgnm)){
			accinfoBean.setOgcd(orgn);
			accinfoBean.setAccDate(talAccdate);
			accinfoBean.setNowTime(talNowtime);
			accinfoBean.setOgnm(talOgnm);
			accinfoBean.setHqam(talHqam);
			accinfoBean.setDqam(talDqam);
			accinfoBean.setAmnt(talAmnt);
			accinfoList.add(accinfoBean);
		}
		return accinfoList;
	}
	/**
	 * 查询交易信息统计
	 */
	public List<ChangeInfoList> getChangeInfo(String dateBegin, String dateEnd,
			String comaogcd,String comaleve,String comalabel) {
		BigDecimal sum1 = BigDecimal.valueOf(0);
		BigDecimal sum2 = BigDecimal.valueOf(0);
		BigDecimal sum3 = BigDecimal.valueOf(0);
		BigDecimal sum4 = BigDecimal.valueOf(0);
		BigDecimal sum5 = BigDecimal.valueOf(0);
		List<ChangeInfoList> list = null;
		ChangeInfoList changInfo = null;
		List<ChangeInfoList> changeInfoList = new ArrayList<ChangeInfoList>() ;
		if(comaleve!=null&&comaleve.equals("0")){
			try{
			list = accumbrnchMap.selChangeInfoList1(dateBegin, dateEnd);
				if(list!=null&&list.size()>0){
					for (int i = 0; i < list.size(); i++) {
						changInfo = new ChangeInfoList();
						changInfo.setOgcd(list.get(i).getOgcd());//机构号
						changInfo.setOgnm(list.get(i).getOgnm());//机构名称
						changInfo.setSumbyku(list.get(i).getSumbyku());//买入黄金
						changInfo.setSumslcy(list.get(i).getSumslcy());//卖出金额
						changInfo.setSumslku(list.get(i).getSumslku());//卖出黄金
						changInfo.setSumswku(list.get(i).getSumswku());//提取黄金数
						changInfo.setSumbycy(list.get(i).getSumbycy());//买入金额
						sum1=sum1.add(new BigDecimal(list.get(i).getSumbyku()));
						sum2=sum2.add(new BigDecimal(list.get(i).getSumslcy()));
						sum3=sum3.add(new BigDecimal(list.get(i).getSumslku()));
						sum4=sum4.add(new BigDecimal(list.get(i).getSumswku()));
						sum5=sum5.add(new BigDecimal(list.get(i).getSumbycy()));
						if(!list.get(i).getOgcd().equals(comaogcd)){
							changeInfoList.add(changInfo);
						}
					}
				}
			} catch (Exception db) {
				log.error(db.getMessage(),db);
			}
		}else if(comaleve!=null&&comaleve.equals("1")){
			try {
				list = accumbrnchMap.selChangeInfoList2(dateBegin, dateEnd, comaogcd);
				if(list!=null&&list.size()>0){
					for (int i = 0; i < list.size(); i++) {
						changInfo = new ChangeInfoList();
						changInfo.setOgcd(list.get(i).getOgcd());
						changInfo.setOgnm(list.get(i).getOgnm());
						changInfo.setSumbyku(list.get(i).getSumbyku());//买入黄金
						changInfo.setSumslcy(list.get(i).getSumslcy());//卖出金额
						changInfo.setSumslku(list.get(i).getSumslku());//卖出黄金
						changInfo.setSumswku(list.get(i).getSumswku());//提取黄金数
						changInfo.setSumbycy(list.get(i).getSumbycy());//买入金额
						sum1=sum1.add(new BigDecimal(list.get(i).getSumbyku()));
						sum2=sum2.add(new BigDecimal(list.get(i).getSumslcy()));
						sum3=sum3.add(new BigDecimal(list.get(i).getSumslku()));
						sum4=sum4.add(new BigDecimal(list.get(i).getSumswku()));
						sum5=sum5.add(new BigDecimal(list.get(i).getSumbycy()));
						if(!list.get(i).getOgcd().equals(comaogcd)){
							changeInfoList.add(changInfo);
						}
					}
				}
			} catch (Exception db) {
				log.error(db.getMessage(),db);
			}
		}
		if(list!=null&&list.size()>0){
			ChangeInfoList changInfoA = new ChangeInfoList();
			changInfoA.setOgcd(comaogcd);
			changInfoA.setOgnm(comalabel);
			changInfoA.setSumbycy(sum5.toString());
			changInfoA.setSumbyku(sum1.toString());
			changInfoA.setSumslcy(sum2.toString());
			changInfoA.setSumslku(sum3.toString());
			changInfoA.setSumswku(sum4.toString());
			changeInfoList.add(0,changInfoA);
		}
		return changeInfoList;
	}
	
	/**
	 * 签约信息统计
	 */
	public PageInfo<QYInfoList> getQYAccumuRegTrade(Integer pageNo, Integer pageSize, String trdtbegin, String userKey){
		List<QYInfoList> list = null;
		pageNo = pageNo == null?1:pageNo;
		pageSize = pageSize == null?10:pageSize;
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String cstn = curUser.getCstn();
		Trd_ogcd trd_ogcd = accumbrnchMap.selOgcdNm(cstn);
		String ogcd = trd_ogcd.getOgcd();
		String ognm = trd_ogcd.getOgnm();
		String leve = trd_ogcd.getLeve();
		 PageHelper.startPage(pageNo, pageSize);
		try {
			if ("0".equals(leve)){
				list = accumbrnchMap.selqy1(ogcd, ognm, trdtbegin);
			} else if ("1".equals(leve)) {
				list = accumbrnchMap.selqy2(ogcd, trdtbegin);
			} else {
				log.error("机构等级错误！");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return new PageInfo<QYInfoList>(list);
	}
}
