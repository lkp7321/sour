package com.ylxx.fx.service.impl.price.pricemonitorimpl;

import java.util.*;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.core.domain.price.AllMakPrice;
import com.ylxx.fx.core.mapper.price.pricemonitor.FpriceMonitorMapper;
import com.ylxx.fx.service.price.pricemonitor.FpriceMonitorService;
import com.ylxx.fx.utils.TestFormatter;

@Service("fpriceMonitorService")
public class FpriceMonitorServiceImpl implements FpriceMonitorService{
	@Resource
	private FpriceMonitorMapper fpriceMonitorMap;
	private static final Logger log = LoggerFactory.getLogger(FpriceMonitorServiceImpl.class);
	//
	public List<AllMakPrice> getAllMaketPrice(String type, String name) {
		List<AllMakPrice> list1 = new ArrayList<AllMakPrice>();
		List<AllMakPrice> list = null;
		try {
			if(type.equals("1")){
				list = fpriceMonitorMap.selectAllMaket(name);
			}
			if(type.equals("2")){
				list = selectAllExnm(name);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		TestFormatter format = new TestFormatter();
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getNeby() == null || list.get(i).getNesl() == null || list.get(i).getNemd() == null){
					continue;
				}
				AllMakPrice allMakPrice = new AllMakPrice();
				allMakPrice.setMknm(list.get(i).getMknm());
				allMakPrice.setExnm(list.get(i).getExnm());
				allMakPrice.setTpnm(list.get(i).getTpnm());
				allMakPrice.setUdfg(list.get(i).getUdfg());
				allMakPrice.setCxfg(list.get(i).getCxfg().equals("1")?"汇":"钞");
				allMakPrice.setCxem(list.get(i).getCxem());
				allMakPrice.setTerm(list.get(i).getTerm());
				allMakPrice.setTrfg(list.get(i).getTrfg().equals("0")?"可交易":"不可交易");
				allMakPrice.setErcn(list.get(i).getErcn());
				allMakPrice.setNebp(list.get(i).getNebp());
				allMakPrice.setNesp(list.get(i).getNesp());
				
				//如果是利率市场报价，报价精度直接定义为6位
				if(type.equals("1")&&name.equals("M0301")){
					allMakPrice.setNeby(format.getDecimalFormat(list.get(i).getNeby(),6));
					allMakPrice.setNesl(format.getDecimalFormat(list.get(i).getNesl(),6));
					allMakPrice.setNemd(format.getDecimalFormat(list.get(i).getNemd(),6));
				}else{
					if(list.get(i).getExnm().contains("JPY")&&list.get(i).getExnm().substring(0,3).equals("HKD")){
						allMakPrice.setNeby(format.getDecimalFormat(list.get(i).getNeby(),3));
						allMakPrice.setNesl(format.getDecimalFormat(list.get(i).getNesl(),3));
						allMakPrice.setNemd(format.getDecimalFormat(list.get(i).getNemd(),3));
						
					}else if(list.get(i).getExnm().contains("JPY")){
						allMakPrice.setNeby(format.getDecimalFormat(list.get(i).getNeby(),2));
						allMakPrice.setNesl(format.getDecimalFormat(list.get(i).getNesl(),2));
						allMakPrice.setNemd(format.getDecimalFormat(list.get(i).getNemd(),2));
					}else{
						allMakPrice.setNeby(format.getDecimalFormat(list.get(i).getNeby(),4));
						allMakPrice.setNesl(format.getDecimalFormat(list.get(i).getNesl(),4));
						allMakPrice.setNemd(format.getDecimalFormat(list.get(i).getNemd(),4));
					}
				}
				if(list.get(i).getMdtm()!=null){
					allMakPrice.setMdtm(list.get(i).getMdtm());
				}
				list1.add(allMakPrice);
			}
		}
		return list1;
	}
	
	private List<AllMakPrice> selectAllExnm(String name) {
		List<AllMakPrice> list = null;
		List<String> listString = fpriceMonitorMap.selMike(); 
		if(listString!=null && listString.size()>0){
			String sql = "";
			for (int i = 0; i < listString.size(); i++) {
				if (i == 0)
					sql = "select '"
							+listString.get(i).substring(13)+
							"', a.*  from "+
							listString.get(i)+
							" a where  exnm = '"+name+"'";
				else {
					sql += " union select '"
							+ listString.get(i).substring(13)
							+ "',a.* from "
							+ listString.get(i)
							+ " a where exnm = '"+name+"'";
				}			
			}
			try {
				list = fpriceMonitorMap.selMike2(sql);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return list;
	}

	@Override
	public List<Map<String, String>> getAllMk() {
		// TODO Auto-generated method stub
		return fpriceMonitorMap.selAllMk();
	}

	@Override
	public List<Map<String, String>> getAllexnm() {
		// TODO Auto-generated method stub
		return fpriceMonitorMap.selAllexnm();
	}

	/**
	 * 产品源监控
	 */
	@Override
	public List<FxipMonitorVo> selectAllPdtprod(String prcd) {
		List<FxipMonitorVo> list = null;
		try {
			list = fpriceMonitorMap.selectAllPdtprod(prcd);
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
}
