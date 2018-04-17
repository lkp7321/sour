package com.ylxx.fx.service.impl.person.fxipmonitorimpl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ylxx.fx.core.domain.FormuleVo;
import com.ylxx.fx.core.mapper.person.fxipmonitor.CKtotalMonitorMapper;
import com.ylxx.fx.service.person.fxipmonitor.CKtotalMonitorService;
import com.ylxx.fx.service.po.FormuleBean;
import com.ylxx.fx.service.po.FormuleBeanForAcc;
import com.ylxx.fx.service.po.price.ExposureBean;
import com.ylxx.fx.utils.TestFormatter;
import com.ylxx.fx.utils.price.Formula;

@Service("cktotalMonitorService")
public class CKtotalMonitorServiceImpl implements CKtotalMonitorService{
	
	private static final Logger log = LoggerFactory.getLogger(CKtotalMonitorServiceImpl.class);
	
	@Resource
	private CKtotalMonitorMapper cktotalMonitorMap;
	
	public List<FormuleBean> fxipExceptionData(String prcd) {
		Formula f = new Formula();
		List<FormuleVo> list = null;
		List<FormuleBean> list1 = new ArrayList<FormuleBean>();
		TestFormatter test = new TestFormatter();
		try {
			 list = cktotalMonitorMap.selectPrice(prcd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		double sumlamt = 0;
		double sumramt = 0;
		double sumfdsy = 0;
		double sumppyk = 0;
		double sumzcyk = 0;
		double sumTRYK = 0;
		double sumsgyk = 0;
		double sumtoyk = 0;
		if(list!=null&&list.size()>0){
			FormuleBean cmmbean = null;
			for (int i = 0; i < list.size(); i++) {
				cmmbean = new FormuleBean();
				cmmbean.setUdfg(list.get(i).getUdfg());
				cmmbean.setPrcd(list.get(i).getPrcd());
				cmmbean.setExnm(list.get(i).getExnm());
				cmmbean.setExcd(list.get(i).getExcd());
				cmmbean.setLamt(Double.parseDouble(list.get(i).getLamt()));
				cmmbean.setRamt(Double.parseDouble(list.get(i).getRamt()));
				cmmbean.setFdsy(Double.parseDouble(list.get(i).getFdsy()));
				cmmbean.setPrice(Double.valueOf(test.getDecimalFormat(list
						.get(i).getNeby()+"", 4)));
				cmmbean.setPricer(Double.valueOf(test.getDecimalFormat(list
						.get(i).getNesl()+"", 4)));
				cmmbean.setPpyk(Double.parseDouble(list.get(i).getPpyk()));
				cmmbean.setZcyk(Double.parseDouble(list.get(i).getZcyk()));
				cmmbean.setTryk(Double.parseDouble(list.get(i).getTryk()));
				cmmbean.setSgyk(Double.parseDouble(list.get(i).getSgyk()));
				cmmbean.setToyk(Double.parseDouble(list.get(i).getToyk()));

				list1.add(f.onFormule(cmmbean));
			}
			for (int j = 0; j < list1.size(); j++) {
				
				sumlamt = sumlamt + list1.get(j).getLamt();
				sumramt = sumramt + list1.get(j).getRamt();
				sumfdsy = sumfdsy + list1.get(j).getFdsy();
				sumppyk = sumppyk + list1.get(j).getPpyk();
				sumzcyk = sumzcyk + list1.get(j).getZcyk();
				sumTRYK = sumTRYK + list1.get(j).getTryk();
				sumsgyk = sumsgyk + list1.get(j).getSgyk();
				sumtoyk = sumtoyk + list1.get(j).getToyk();
			}
			cmmbean = new FormuleBean();
			cmmbean.setExnm("合计:");
			cmmbean.setFdsy(Double.parseDouble(test.getDecimalFormat(sumfdsy + "",2)));
			cmmbean.setPpyk(Double.parseDouble(test.getDecimalFormat(sumppyk + "",2)));
			cmmbean.setZcyk(Double.parseDouble(test.getDecimalFormat(sumzcyk + "",2)));
			cmmbean.setTryk(Double.parseDouble(test.getDecimalFormat(sumTRYK + "",2)));
			cmmbean.setSgyk(Double.parseDouble(test.getDecimalFormat(sumsgyk + "",2)));
			cmmbean.setToyk(Double.parseDouble(test.getDecimalFormat(sumtoyk + "",2)));
			list1.add(cmmbean);
		}
		System.out.println(list1.get(0).getLamt());
		return list1;
	}
		
	/**
	 * 敞口累加监控
	 * @param prcd
	 * @return
	 */
	public List<FormuleBean> fxipCKData(String prcd) {
		Formula f = new Formula();
		List<FormuleVo> list = null;
		List<FormuleBean> list1 = new ArrayList<FormuleBean>();
		TestFormatter test = new TestFormatter();
		list = cktotalMonitorMap.selectCKLJPrice(prcd);
		double sumlamt = 0;
		double sumramt = 0;
		double sumfdsy = 0;
		double sumppyk = 0;
		double sumzcyk = 0;
		double sumTRYK = 0;
		double sumsgyk = 0;
		double sumtoyk = 0;
		if (list != null && list.size() > 0) {
			FormuleBean cmmbean = null;
			for (int i = 0; i < list.size(); i++) {
				cmmbean = new FormuleBean();

				cmmbean.setUdfg(list.get(i).getUdfg());
				cmmbean.setPrcd(list.get(i).getPrcd());
				cmmbean.setExnm(list.get(i).getExnm());
				cmmbean.setExcd(list.get(i).getExcd());
				cmmbean.setLamt(Double.parseDouble(list.get(i).getTotal_lamt()));
				cmmbean.setRamt(Double.parseDouble(list.get(i).getTotal_ramt()));
				cmmbean.setFdsy(Double.parseDouble(list.get(i).getFdsy()));
				cmmbean.setPrice(Double.valueOf(test.getDecimalFormat(list.get(i).getNeby()+"",4)));
				cmmbean.setPricer(Double.valueOf(test.getDecimalFormat(list.get(i).getNeby()+"",4)));
				cmmbean.setPpyk(Double.parseDouble(list.get(i).getPpyk()));
				cmmbean.setZcyk(Double.parseDouble(list.get(i).getZcyk()));
				cmmbean.setTryk(Double.parseDouble(list.get(i).getTryk()));
				cmmbean.setSgyk(Double.parseDouble(list.get(i).getSgyk()));
				cmmbean.setToyk(Double.parseDouble(list.get(i).getToyk()));

				list1.add(f.onFormule(cmmbean));
			}
			for (int j = 0; j < list.size(); j++) {

				sumlamt = sumlamt + list1.get(j).getLamt();
				sumramt = sumramt + list1.get(j).getRamt();
				sumfdsy = sumfdsy + list1.get(j).getFdsy();
				sumppyk = sumppyk + list1.get(j).getPpyk();
				sumzcyk = sumzcyk + list1.get(j).getZcyk();
				sumTRYK = sumTRYK + list1.get(j).getTryk();
				sumsgyk = sumsgyk + list1.get(j).getSgyk();
				sumtoyk = sumtoyk + list1.get(j).getToyk();
			}
			// 合记
			cmmbean = new FormuleBean();
			cmmbean.setExnm("合计:");
			cmmbean.setFdsy(Double.parseDouble(test.getDecimalFormat(sumfdsy + "",
					2)));
			cmmbean.setPpyk(Double.parseDouble(test.getDecimalFormat(sumppyk + "",
					2)));
			cmmbean.setZcyk(Double.parseDouble(test.getDecimalFormat(sumzcyk + "",
					2)));
			cmmbean.setTryk(Double.parseDouble(test.getDecimalFormat(sumTRYK + "",
					2)));
			cmmbean.setSgyk(Double.parseDouble(test.getDecimalFormat(sumsgyk + "",
					2)));
			cmmbean.setToyk(Double.parseDouble(test.getDecimalFormat(sumtoyk + "",
					2)));

			list1.add(cmmbean);
		}
		return list1;
	}

	@Override
	public List<FormuleBeanForAcc> selectPriceData(String prcd1, String prcd2) {
		Formula f = new Formula();
		List<FormuleVo> list = null;
		List<FormuleBeanForAcc> list1 = new ArrayList<FormuleBeanForAcc>();
		TestFormatter test = new TestFormatter();
		try {
			 list = cktotalMonitorMap.selectPriceData(prcd1, prcd2);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		double sumlamt = 0;
		double sumramt = 0;
		double sumfdsy = 0;
		double sumppyk = 0;
		double sumzcyk = 0;
		double sumTRYK = 0;
		double sumsgyk = 0;
		double sumtoyk = 0;
		if(list!=null&&list.size()>0){
			FormuleBeanForAcc cmmbean = null;
			for (int i = 0; i < list.size(); i++) {
				cmmbean = new FormuleBeanForAcc();
				cmmbean.setUdfg(list.get(i).getUdfg());
				cmmbean.setPrcd(list.get(i).getPrcd());
				cmmbean.setExnm(list.get(i).getExnm());
				cmmbean.setExcd(list.get(i).getExcd());
				cmmbean.setLamt(Double.parseDouble(list.get(i).getLamt()));
				cmmbean.setRamt(Double.parseDouble(list.get(i).getRamt()));
				cmmbean.setFdsy(Double.parseDouble(list.get(i).getFdsy()));
				cmmbean.setPrice(Double.valueOf(test.getDecimalFormat(list
						.get(i).getNeby()+"", 4)));
				cmmbean.setPricer(Double.valueOf(test.getDecimalFormat(list
						.get(i).getNesl()+"", 4)));
				cmmbean.setPpyk(Double.parseDouble(list.get(i).getPpyk()));
				cmmbean.setZcyk(Double.parseDouble(list.get(i).getZcyk()));
				cmmbean.setTryk(Double.parseDouble(list.get(i).getTryk()));
				cmmbean.setSgyk(Double.parseDouble(list.get(i).getSgyk()));
				cmmbean.setToyk(Double.parseDouble(list.get(i).getToyk()));

				list1.add(onFormule(cmmbean));
			}
			for (int j = 0; j < list1.size(); j++) {
				
				sumlamt = sumlamt + list1.get(j).getLamt();
				sumramt = sumramt + list1.get(j).getRamt();
				sumfdsy = sumfdsy + list1.get(j).getFdsy();
				sumppyk = sumppyk + list1.get(j).getPpyk();
				sumzcyk = sumzcyk + list1.get(j).getZcyk();
				sumTRYK = sumTRYK + list1.get(j).getTryk();
				sumsgyk = sumsgyk + list1.get(j).getSgyk();
				sumtoyk = sumtoyk + list1.get(j).getToyk();
			}
			cmmbean = new FormuleBeanForAcc();
			cmmbean.setExnm("合计:");
			cmmbean.setFdsy(Double.parseDouble(test.getDecimalFormat(sumfdsy + "",2)));
			cmmbean.setPpyk(Double.parseDouble(test.getDecimalFormat(sumppyk + "",2)));
			cmmbean.setZcyk(Double.parseDouble(test.getDecimalFormat(sumzcyk + "",2)));
			cmmbean.setTryk(Double.parseDouble(test.getDecimalFormat(sumTRYK + "",2)));
			cmmbean.setSgyk(Double.parseDouble(test.getDecimalFormat(sumsgyk + "",2)));
			cmmbean.setToyk(Double.parseDouble(test.getDecimalFormat(sumtoyk + "",2)));
			list1.add(cmmbean);
		}
		System.out.println(list1.get(0).getLamt());
		return list1;
	}
	//计算成本汇率
	public FormuleBeanForAcc onFormule(FormuleBeanForAcc formule) {
		FormuleBeanForAcc frmbean = new FormuleBeanForAcc();
		double cbhl = 0;

		if (Math.abs(formule.getLamt()) <= 0.0) // 成本汇率
			cbhl = 0;
		else
			cbhl = Math.abs(formule.getRamt() / formule.getLamt());

		if ("USD".equals(formule.getExnm().substring(0, 3))) {
			if (formule.getRamt() > 0.0) {
				// 卖出价
				/*formule.setFDSY(formule.getRAMT() / formule.getPRICER()
						+ formule.getLAMT());*/
				//数据库初始化时产品源的价格都是0，这样就会报错，特殊处理一下，价格为0，浮动损益就为0
				if(formule.getPricer()!=0){
					formule.setFdsy(formule.getRamt() / formule.getPricer()
							+ formule.getLamt());
				}else{
					formule.setFdsy(0);
				}
				
			} 
			//右头寸为0时浮动损益照常按照计算公式计算
			/*else if(formule.getRAMT()==0){
				// 
				formule.setFDSY(0);
			}*/
			else {
				// 买入价
				/*formule.setFDSY(formule.getRAMT() / formule.getPRICE()
						+ formule.getLAMT());*/
				//数据库初始化时产品源的价格都是0，这样就会报错，特殊处理一下，价格为0，浮动损益就为0
				if(formule.getPrice()!=0){
					formule.setFdsy(formule.getRamt() / formule.getPrice()
							+ formule.getLamt());
				}else{
					formule.setFdsy(0);
				}
			}
		} else {
			if (formule.getLamt() > 0.0) {
				// 买入价
				formule.setFdsy(formule.getLamt() * formule.getPrice()
						+ formule.getRamt());
			} 
			//左头寸为0时浮动损益照常按照计算公式计算
			/*else if(formule.getLAMT()==0){
				// 
				formule.setFDSY(0);
			}*/
			else{
				// 卖出价
				formule.setFdsy(formule.getLamt() * formule.getPricer()
						+ formule.getRamt());
			}
		}

		try {
			TestFormatter test = new TestFormatter();
			// getDecimalFormat
			frmbean.setPrcd(formule.getPrcd());
			frmbean.setCknm(formule.getCknm());
			frmbean.setExnm(formule.getExnm());
			frmbean.setExcd(formule.getExcd());
			frmbean.setLamt(formule.getLamt());
			frmbean.setRamt(formule.getRamt());

			String cbhlstr = "0";
			cbhlstr = cbhl + "";
			if (formule.getExnm().substring(0, 3).equals("JPY")
					|| formule.getExnm().substring(3).equals("JPY")) {
				//cbhlstr = test.getDecimalFormat(cbhlstr, 4);
				//修改成保留2位小数
				cbhlstr = test.getDecimalFormat(cbhlstr, 2);
				formule.setFdsy(new Double(test.getDecimalFormat(formule
						.getFdsy()
						+ "", 0)));

			} else {
				cbhlstr = test.getDecimalFormat(cbhlstr, 4);
				formule.setFdsy(new Double(test.getDecimalFormat(formule
						.getFdsy()
						+ "", 2)));
			}

			frmbean.setCbhl(cbhlstr);
			if (Math.abs(formule.getFdsy()) == 0.00) {
				frmbean.setFdsy(Math.abs(formule.getFdsy()));
			} else
				frmbean.setFdsy(formule.getFdsy());
			frmbean.setUdfg(formule.getUdfg());
			frmbean.setPrice(formule.getPrice());
			frmbean.setPricer(formule.getPricer());
			frmbean.setPpyk(formule.getPpyk());
			frmbean.setZcyk(formule.getZcyk());
			frmbean.setTryk(formule.getTryk());
			frmbean.setToyk(formule.getToyk());
			frmbean.setSgyk(formule.getSgyk());
		} catch (Exception en) {
			 log.error(en.getMessage(),en);
		}

		return frmbean;
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public List<ExposureBean> pereFxipExceptionData(String prcd){
		List<ExposureBean> list1 = new ArrayList<>();
		Formula formula = new Formula();
		List<ExposureBean> list =null;
		ExposureBean expoTotal = null;
		try {
			if (prcd.equals("P001")) {
				prcd = "P006";
				list = cktotalMonitorMap.selExpoTotalList(prcd);
			}else {
				list = cktotalMonitorMap.selFxExpoTotalList(prcd);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				expoTotal = new ExposureBean();

				expoTotal.setUdfg(list.get(i).getUdfg());
				expoTotal.setPrcd(list.get(i).getPrcd());
				expoTotal.setExnm(list.get(i).getExnm());
				expoTotal.setExcd(list.get(i).getExcd());
				expoTotal
						.setLamt(list.get(i).getLamt() != null ? list.get(i).getLamt()
								: "0.00");
				expoTotal
						.setRamt(list.get(i).getRamt() != null ? list.get(i).getRamt()
								: "0.00");
				expoTotal
						.setFdsy(list.get(i).getFdsy() != null ? list.get(i).getFdsy()
								: "0.00");
				expoTotal
						.setNeby(list.get(i).getNeby() != null ? list.get(i).getNeby()
								: "0.00");
				expoTotal
						.setNesl(list.get(i).getNesl() != null ? list.get(i).getNesl()
								: "0.00");
				expoTotal
						.setPpyk(list.get(i).getPpyk() != null ? list.get(i).getPpyk()
								: "0.00");
				expoTotal.setZcyk(list.get(i).getZcyk()!=null ? list.get(i).getZcyk():"0.00");
				expoTotal.setSgyk(list.get(i).getSgyk()!=null ? list.get(i).getSgyk():"0.00");
				expoTotal.setTryk(list.get(i).getTryk()!=null ? list.get(i).getTryk():"0.00");
				expoTotal.setToyk(list.get(i).getToyk()!=null ? list.get(i).getToyk():"0.00");
				list1.add(formula.onFormule(expoTotal));
			}
		}
		return list1;
	}
}
