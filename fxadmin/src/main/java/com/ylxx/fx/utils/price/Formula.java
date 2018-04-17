package com.ylxx.fx.utils.price;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ylxx.fx.service.po.FormuleBean;
import com.ylxx.fx.service.po.price.ExposureBean;
import com.ylxx.fx.utils.TestFormatter;

/**
 * 计算公式
 * 
 * @author Administrator
 * 
 */
public class Formula {
	static final Logger log = LoggerFactory.getLogger(Formula.class);
	public FormuleBean onFormule(FormuleBean formule) {
		FormuleBean frmbean = new FormuleBean();
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
				if(formule.getPrice()!=0){
					formule.setFdsy(formule.getRamt() / formule.getPrice()
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

	/**
	 * 分解优惠规则函数串，获取优惠规则的HASHMAP
	 * 
	 * @param rule
	 *            --优惠规则函数串
	 */
	@SuppressWarnings("unchecked")
	public static HashMap getFvdaMap(String rule) {
		HashMap<String, Integer> hm = new HashMap<String, Integer>();

		int flag = 0;// 判断是否为金额优惠
		if (rule == null || "".equals(rule.trim()))
			return hm;
		if (rule.length() <= 2) {
			hm.put("OGCD", Integer.valueOf(rule.trim()));
			return hm;
		}
		String[] s = rule.split(";");
		for (int i = 0; i < s.length - 2; i++) {// 最后2个为 return和 }

			String strTmp = s[i].trim();
			int fvda = Integer.valueOf(strTmp.substring(
					strTmp.lastIndexOf("=") + 1).trim());// 取得优惠点差

			if (i == 0) {
				hm.put("DEFAULT", fvda);

				if (strTmp.indexOf("double") >= 0)// 金额优惠
					flag = 1;
				continue;
			}

			String para = "";

			if (flag == 0) {
				para = strTmp.substring(strTmp.indexOf("\"") + 1, strTmp
						.lastIndexOf("\""));// 取得参数值
				hm.put(para, fvda);
			} else {
				para = strTmp.substring(strTmp.indexOf("(") + 1, strTmp
						.lastIndexOf(")"));// 取得参数说明 para放前eg:para<10000&&para
				// >= 5000
				String[] strArr = para.split("&&");

				String usamSmall = "0";// 小金额
				String usamBig = "∞";// 大金额

				for (String str : strArr) {
					int posSmall = str.indexOf(">=");
					int posBig = str.indexOf("<");

					if (posSmall > 0)
						usamSmall = str.substring(posSmall + 2).trim();
					if (posBig > 0)
						usamBig = str.substring(posBig + 1).trim();
				}

				String usam = usamSmall + "," + usamBig;// eg:5000,10000
				hm.put(usam, fvda);
			}
		}
		return hm;
	}

	/**
	 * 组合优惠规则函数串
	 * 
	 * @param HashMap
	 *            --优惠规则函数串映射, <br>
	 *            fvid--优惠编码( <br>
	 *            F01:渠道优惠, <br>
	 *            F02:币别对优惠, <br>
	 *            F03:金额优惠, <br>
	 *            F04:客户优惠, <br>
	 *            F05:机构优惠)
	 */
	@SuppressWarnings("unchecked")
	public static String compRule(HashMap<String, Integer> hm, String fvid) {
		String funhead = "int getvar(String para){ ";
		if (fvid.equals("F03"))
			funhead = "int getvar(double para){ ";
		String funbody = "int fvda=";
		String funend = "return fvda;}";
		String funbodyif = "";
		String funbodyifhead = "if(para.equals(\"";
		String funbodyifend = "\")) fvda=";
		// String funfvda="fvda=";

		//Set set = hm.keySet();
		//Iterator it = set.iterator();
		Set<Entry<String, Integer>> set=hm.entrySet();
		Iterator<Entry<String, Integer>> it=set.iterator();
		if (fvid.equals("F05")) {
			//Map.Entry entry = (Map.Entry)it.next();
	        //String para = (String) entry.getKey();

			//String para = (String) it.next();
			//return hm.get(para).toString();
			Entry<String, Integer> entry=it.next();
			return entry.getValue().toString();
			
		}

		int j = 0;// 第一次用if
		while (it.hasNext()) {
			//String para = (String) it.next();
			//int fvda = hm.get(para);
			Entry<String, Integer> entry=it.next();
			String para =entry.getKey();
			int fvda =entry.getValue();
			if (para.equals("DEFAULT")) {
				funbody += fvda + ";";
			} else {
				if (j == 0) {
					if (fvid.equals("F01") || fvid.equals("F02")
							|| fvid.equals("F04"))
						funbodyif = funbodyifhead + para + funbodyifend + fvda
								+ ";";
					else if (fvid.equals("F03")) {
						String[] strArr = para.split(",");// eg:1000,5000
						String usamSmall = strArr[0].trim();
						String usamBig = strArr[1].trim();

						if (usamSmall.equals("0")) {
							funbodyif = "if(para<" + usamBig + ") fvda=" + fvda
									+ ";";
						}

						else if (usamBig.equals("∞")) {
							funbodyif = "if(para>=" + usamSmall + ") fvda="
									+ fvda + ";";
						}

						else
							funbodyif = "if(para>=" + usamSmall + " && para<"
									+ usamBig + ") fvda=" + fvda + ";";
					}

				} else {
					if (fvid.equals("F01") || fvid.equals("F02")
							|| fvid.equals("F04"))
						funbodyif += "else " + funbodyifhead + para
								+ funbodyifend + fvda + ";";
					else if (fvid.equals("F03")) {
						String[] strArr = para.split(",");// eg:1000,5000
						String usamSmall = strArr[0].trim();
						String usamBig = strArr[1].trim();

						if (usamSmall.equals("0")) {
							funbodyif += "else if(para<" + usamBig + ") fvda="
									+ fvda + ";";
						}

						else if (usamBig.equals("∞")) {
							funbodyif += "else if(para>=" + usamSmall
									+ ") fvda=" + fvda + ";";
						}

						else
							funbodyif += "else if(para>=" + usamSmall
									+ " && para<" + usamBig + ") fvda=" + fvda
									+ ";";
					}
				}
				j++;
			}
		}

		return funhead + funbody + funbodyif + funend;
	}

	/**
	 * 计算公式
	 * 
	 * @author Administrator
	 * 
	 */
	public ExposureBean onFormule(ExposureBean formule) {
		ExposureBean expoTotal = new ExposureBean();
		BigDecimal cbhl = BigDecimal.ZERO;
		
		BigDecimal lamt = stringToBigDecimal(formule.getLamt());
		BigDecimal ramt = stringToBigDecimal(formule.getRamt());
		BigDecimal nesl = stringToBigDecimal(formule.getNesl());
		BigDecimal neby = stringToBigDecimal(formule.getNeby());
		 
		String exnm = formule.getExnm();
		
		if (lamt.abs().compareTo(BigDecimal.ZERO)<=0) // 成本汇率
			cbhl = BigDecimal.ZERO;
		else
			cbhl = ramt.divide(lamt,4,BigDecimal.ROUND_HALF_UP).abs();
	
		if("USDRMB".equals(exnm)){
			if (lamt.compareTo(BigDecimal.ZERO)==1) {
				// 买入价
				//formule.setFdsy(lamt.multiply(neby.divide(BigDecimal.valueOf(100))).add(ramt).toString());
				//由于纸黄金中也存在USDRMB这个币种，它是标准价，除以100就存在问题；
				//结售汇中币种在分行价和客户价中都是经过加工后的价格，它需要特殊处理，
				//但是路透价格是标准价，使用它就不需要特殊处理啦
				formule.setFdsy(lamt.multiply(neby).add(ramt).toString());
			} else {
				// 卖出价
				//formule.setFdsy(lamt.multiply(nesl.divide(BigDecimal.valueOf(100))).add(ramt).toString());
				formule.setFdsy(lamt.multiply(nesl).add(ramt).toString());
			}
		}
		else{
			if ("USD".equals(exnm.substring(0, 3))) {
				if (ramt.compareTo(BigDecimal.ZERO)==1) {
					// 卖出价
					//formule.setFdsy(ramt.divide(nesl,4,BigDecimal.ROUND_HALF_UP).add(lamt).toString());
					//数据库初始化时价格源公告板价格都为0，这样就会出错，做特殊处理，价格为0，浮动损益为0
					if(nesl.compareTo(BigDecimal.ZERO)==1){
						formule.setFdsy(ramt.divide(nesl,4,BigDecimal.ROUND_HALF_UP).add(lamt).toString());
					}else{
						formule.setFdsy("0");
					}
				} else {
					// 买入价
					//formule.setFdsy(ramt.divide(neby,4,BigDecimal.ROUND_HALF_UP).add(lamt).toString());
					//数据库初始化时价格源公告板价格都为0，这样就会出错，做特殊处理，价格为0，浮动损益为0
					if(neby.compareTo(BigDecimal.ZERO)==1){
						formule.setFdsy(ramt.divide(neby,4,BigDecimal.ROUND_HALF_UP).add(lamt).toString());
					}else{
						formule.setFdsy("0");
					}
				}
			} else {
				if (lamt.compareTo(BigDecimal.ZERO)==1) {
					// 买入价
					formule.setFdsy(lamt.multiply(neby).add(ramt).toString());
				} else {
					// 卖出价
					formule.setFdsy(lamt.multiply(nesl).add(ramt).toString());
				}
			}
		}
		try {
			TestFormatter priceFormatter = new TestFormatter();
			// getDecimalFormat
			expoTotal.setPrcd(formule.getPrcd());
			expoTotal.setExnm(exnm);
			expoTotal.setExcd(formule.getExcd());
			expoTotal.setLamt(lamt + "");
			expoTotal.setRamt(ramt + "");

			String cbhlstr = "0";
			cbhlstr = cbhl + "";
			cbhlstr = priceFormatter.getDecimalFormat(cbhlstr, 4);

			formule.setFdsy(priceFormatter.getDecimalFormat(formule.getFdsy()
					+ "", 2));
			expoTotal.setCbhl(cbhlstr);
			expoTotal.setFdsy(formule.getFdsy());

			expoTotal.setUdfg(formule.getUdfg());
			expoTotal.setZcyk(formule.getZcyk());
			expoTotal.setNeby(neby + "");
			expoTotal.setNesl(nesl + "");
			//由于纸黄金中也存在USDRMB这个币种，它是标准价，除以100就存在问题；
			//结售汇中币种在分行价和客户价中都是经过加工后的价格，它需要特殊处理，
			//但是路透价格是标准价，使用它就不需要特殊处理啦
			/*if("USDRMB".equals(exnm)){
				expoTotal.setNeby(neby.divide(BigDecimal.valueOf(100)).toString());
				expoTotal.setNesl(nesl.divide(BigDecimal.valueOf(100)).toString());
			}*/
			expoTotal.setPpyk(formule.getPpyk());
			expoTotal.setSgyk(formule.getSgyk());
			expoTotal.setTryk(formule.getTryk());
			expoTotal.setToyk(formule.getToyk());
		} catch (Exception e) {
			 log.error(e.getMessage(),e);
		}
		return expoTotal;
	}

	// 3.将字符串转为bigDecmal类型
	public BigDecimal stringToBigDecimal(String str) {
		BigDecimal bigStr = null;
		String strArr[] = str.split("\\.");

		if (2 <= strArr.length) {

			int length = strArr[1].length();
			StringBuffer sb = new StringBuffer();
			for (int temp = 0; temp < length; temp++) {
				sb.append("0");
			}
			if (sb.toString().equals(strArr[1])) {
				bigStr = BigDecimal.valueOf(Long.parseLong(strArr[0]));
			} else {
				bigStr = BigDecimal.valueOf(Double.parseDouble(str));
			}

		} else if (1 == strArr.length) {
			bigStr = BigDecimal.valueOf(Long.parseLong(str));
		}
		return bigStr;
	}
}
