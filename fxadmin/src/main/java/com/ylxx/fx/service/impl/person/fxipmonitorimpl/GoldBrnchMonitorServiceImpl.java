package com.ylxx.fx.service.impl.person.fxipmonitorimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylxx.fx.core.domain.FormuleVo;
import com.ylxx.fx.core.domain.FxipMonitorVo;
import com.ylxx.fx.core.mapper.person.fxipmonitor.GoldBrnchMonitorMapper;
import com.ylxx.fx.service.person.fxipmonitor.GoldBrnchMonitorService;
import com.ylxx.fx.service.po.FormuleBean;
import com.ylxx.fx.service.po.GckwebBean;
import com.ylxx.fx.utils.TestFormatter;
import com.ylxx.fx.utils.price.Formula;

@Service("goldBrnchService")
public class GoldBrnchMonitorServiceImpl implements GoldBrnchMonitorService {
	
	@Resource 
	private GoldBrnchMonitorMapper goldBrnchMap;
	/*
	 * 获取总分行价格监控
	 */
	public List<FxipMonitorVo> getAllGoldBrnch() {
		return goldBrnchMap.selectAllPdtBrnch();
	}

	/*
	 * 获取客户价格监控
	 */
	public List<FxipMonitorVo> getAllGoldCust() {
		return goldBrnchMap.selectAllPdtCust();
	}

	/*
	 * 获取敞口监控
	 */
	public List<GckwebBean> getAllGoldwebPrice() {
		GckwebBean gck = null;
		List<GckwebBean> list1= new ArrayList<GckwebBean>();
		List<GckwebBean> list = null;
		TestFormatter test = new TestFormatter();
		list = goldBrnchMap.selectwebPrice();
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				String CBHL = "";
				if (list.get(i).getLamt() == 0) {
					CBHL = "0.0000";

				} else {
					double s = list.get(i).getRamt()
							/ list.get(i).getLamt();

					double s1 = Math.abs(s);

					CBHL = test.getDecimalFormat(("" + s1), 4);
				}
				String FDSY = "";
				double CB = new Double(CBHL);
				if (list.get(i).getLamt() >= 0) {
					/*double ss = rds.getFieldValueAsDouble("RAMT")
							* (rds.getFieldValueAsDouble("NEBY") - CB);*/
					double ss = list.get(i).getLamt()
					* (list.get(i).getNeby() - CB);
					FDSY = test.getDecimalFormat(("" + ss), 4);

				} else {
					/*double ss1 = rds.getFieldValueAsDouble("RAMT")
							* (rds.getFieldValueAsDouble("NESL") - CB);*/
					double ss1 = list.get(i).getLamt()
					* (list.get(i).getNesl() - CB);
					FDSY = test.getDecimalFormat(("" + ss1), 4);

				}
				gck = new GckwebBean();
				gck.setExnm(list.get(i).getExnm());
				gck.setExcn(list.get(i).getExcn());
				gck.setLamt(list.get(i).getLamt());
				gck.setRamt(list.get(i).getRamt());
				gck.setCbhl(CBHL);
				gck.setFdsy(FDSY);
				gck.setToyk(list.get(i).getToyk());
				gck.setNeby(list.get(i).getNeby());
				gck.setNesl(list.get(i).getNesl());
				gck.setPpyk(list.get(i).getPpyk());
				gck.setZcyk(list.get(i).getTryk());
				gck.setTryk(list.get(i).getTryk());
				gck.setSgyk(list.get(i).getSgyk());
				list1.add(gck);
			}
		}
		return list1;
	}

	/*
	 * 获取敞口列表
	 */
	public List<Map<String, String>> getGoldCkno() {
		return goldBrnchMap.selectGoldCkno();
	}

	/*
	 * 获取分类敞口的价格数据
	 */
	public List<FormuleBean> getGoldclassCk(String ckno) {
		List<FormuleVo> list = null;
		List<FormuleBean> list1 = new ArrayList<FormuleBean>();
		Formula f = new Formula();
		TestFormatter test = new TestFormatter();
			list = goldBrnchMap.selectGoldclassPrice(ckno);
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
}
