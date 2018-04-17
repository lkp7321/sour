package com.ylxx.fx.service.impl.person.fxipmonitorimpl;

import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.mapper.person.fxipmonitor.GoldCKtotalMapper;
import com.ylxx.fx.service.person.fxipmonitor.GoldCKtotalService;
import com.ylxx.fx.service.po.CkTotalBean;
import com.ylxx.fx.service.po.User;
import com.ylxx.fx.utils.DataTimeClass;
import com.ylxx.fx.utils.TestFormatter;
@Service("goldCkToService")
public class GoldCKtotalServiceImpl implements GoldCKtotalService {
	private static final Logger log = LoggerFactory.getLogger(GoldCKtotalServiceImpl.class);
	@Resource
	private GoldCKtotalMapper goldCktotalMap;
	private TestFormatter test = new TestFormatter();
	//市场报价
	public List<CkTotalBean> ckTotalData() {
		//prnm,exnm,neby,nemd,nesl,mdtm,mknm,stfg,udfg
		return goldCktotalMap.ckTotalData();
	}

	//总敞口和折算敞口
	public List<CkTotalBean> ckTotal() {
		List<CkTotalBean> newlist = ckTotalChange();
		List<CkTotalBean> rtnlist = new ArrayList<CkTotalBean>();
		 CkTotalBean ck = null;
		 for(int i=0;i<newlist.size();i++){
			 ck=newlist.get(i);
			 rtnlist.add(ck);
		 }
		 rtnlist.add(newckTotalKAGRMB(newlist));
		 rtnlist.add(newckTotalKAURMB(newlist));
		 rtnlist.add(newckTotalUSDRMB(newlist));
		 return rtnlist;
	}
	//3
	public CkTotalBean ckToalSYYL() {
		List<CkTotalBean> list = ckTotalChange();
		CkTotalBean ckTotalBean = hjsy(list);
		return ckTotalBean;
	}
	//更新对外损益
	public boolean updateCKZCYK(User user,CkTotalBean ckTotalBean){
		boolean flag =false;
		try {
			int a = goldCktotalMap.updateCKZCYK(ckTotalBean);
			log.info("\n更新："+a);
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
			String currDate=DataTimeClass.getNowDay();
			String currTime=DataTimeClass.getCurTime();
			insertTQTranList(user.getUsnm(), currDate, currTime, ckTotalBean);
		}
		return flag;
	}
	public boolean insertTQTranList(String usnm, String currDate, String currTime,CkTotalBean ckTotalBean){
		try {
			int a = goldCktotalMap.insertTQTranList(usnm, currDate, currTime, ckTotalBean);
			if(a>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
	public CkTotalBean hjsy(List<CkTotalBean> list) {
		CkTotalBean ckTotalBean = new CkTotalBean();
		BigDecimal nemd = new BigDecimal(goldCktotalMap.USDCNYNEMD());
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
	
	public List<CkTotalBean> ckTotalChange() {
		
		List<CkTotalBean> newlist = new ArrayList<CkTotalBean>();
		List<CkTotalBean> list1 = goldCktotalMap.ckTotalYk();
		int i = 0;
		if (list1.size() > 0) {
			CkTotalBean ckBean = null;
			for (i = 0; i < list1.size(); i++) {
				ckBean = list1.get(i);
				ckBean = onFormule(ckBean);
				newlist.add(ckBean);
			}
		}
		return newlist;
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
	
	public CkTotalBean newckTotalKAGRMB(List<CkTotalBean> list) {
		CkTotalBean frmbean = new CkTotalBean();
		double lamt = 0;
		double ramt = 0;
		double fdsy = 0;
		double ckmd = 0;
		double zcsy = 0;
		String excd="";
		double nemd = goldCktotalMap.USDCNYNEMD();
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
	
	public CkTotalBean newckTotalKAURMB(List<CkTotalBean> list) {
		CkTotalBean frmbean = new CkTotalBean();
		double lamt = 0;
		double ramt = 0;
		double fdsy = 0;
		double ckmd = 0;
		double zcsy = 0;
		String excd="";
		double nemd = goldCktotalMap.USDCNYNEMD();
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
	public CkTotalBean newckTotalUSDRMB(List<CkTotalBean> list) {
		CkTotalBean frmbean = new CkTotalBean();
		double lamt = 0;
		double ramt = 0;
		double fdsy = 0;
		double ckmd = 0;
		double zcsy = 0;
		String excd ="";
		double nemd = goldCktotalMap.USDCNYNEMD();
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
}
