package com.ylxx.fx.service.impl.person.fxipmonitorimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ylxx.fx.core.domain.FormuleVo;
import com.ylxx.fx.core.mapper.person.fxipmonitor.CKclassMonitorMapper;
import com.ylxx.fx.service.person.fxipmonitor.CKclassMonitorService;
import com.ylxx.fx.service.po.FormuleBean;
import com.ylxx.fx.utils.TestFormatter;
import com.ylxx.fx.utils.price.Formula;

@Service("ckclassMonitorService")
public class CKclassMonitorServiceImpl implements CKclassMonitorService {
	private static final Logger log = LoggerFactory.getLogger(CKclassMonitorServiceImpl.class);
	@Resource
	private CKclassMonitorMapper ckclassMonitorMap;
	/*
	 * 获取敞口列表
	 */
	public List<Map<String, String>> gettree(String prcd) {
		List<Map<String, String>> list = null;
		try {
			list = ckclassMonitorMap.selectCkno(prcd);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	/*
	 * 查询轧差敞口分类
	 */
	public List<FormuleBean> getSelectPrice(String prcd, String ckno) {
		List<FormuleVo> list = null;
		List<FormuleBean> list1 = new ArrayList<FormuleBean>();
		Formula f = new Formula();
		TestFormatter test = new TestFormatter();
		try {
			list = ckclassMonitorMap.selectPrice(prcd, ckno);
			if(list!=null&&list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					FormuleBean cmmbean = new FormuleBean();
					cmmbean.setUdfg(list.get(i).getUdfg());
					cmmbean.setPrcd(list.get(i).getPrcd());
					cmmbean.setExnm(list.get(i).getExnm());
					cmmbean.setExcd(list.get(i).getExcd());
					cmmbean.setLamt(Double.parseDouble(test.getDecimalFormat(list.get(i).getLamt()+"",2)));
					cmmbean.setRamt(Double.parseDouble(test.getDecimalFormat(list.get(i).getRamt()+"",2)));
					cmmbean.setFdsy(Double.parseDouble(list.get(i).getFdsy()));
					cmmbean.setPrice(Double.parseDouble(test.getDecimalFormat(list.get(i).getNeby()+"",4)));
					cmmbean.setPricer(Double.parseDouble(test.getDecimalFormat(list.get(i).getNesl()+"",4)));
					cmmbean.setZcyk(Double.parseDouble(test.getDecimalFormat(list.get(i).getZcyk()+"",2)));
					cmmbean.setPpyk(Double.parseDouble(test.getDecimalFormat(list.get(i).getPpyk()+"",2)));
					cmmbean.setTryk(Double.parseDouble(test.getDecimalFormat(list.get(i).getTryk()+"",2)));
					cmmbean.setSgyk(Double.parseDouble(test.getDecimalFormat(list.get(i).getSgyk()+"",2)));
					cmmbean.setToyk(Double.parseDouble(test.getDecimalFormat(list.get(i).getToyk()+"",2)));
					list1.add(f.onFormule(cmmbean));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			log.error("\n分类敞口监控数据失败");
		}
		return list1;
	}

	/*
	 * 查询累加敞口分类
	 */
	public List<FormuleBean> getLJSelectPrice(String prcd, String ckno) {
		List<FormuleVo> list = null;
		List<FormuleBean> list1 = new ArrayList<FormuleBean>();
		Formula f = new Formula();
		TestFormatter test = new TestFormatter();
		try {
			list = ckclassMonitorMap.selectLJCKPrice(prcd, ckno);
			if(list!=null&&list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					FormuleBean cmmbean = new FormuleBean();
					cmmbean.setUdfg(list.get(i).getUdfg());
					cmmbean.setPrcd(list.get(i).getPrcd());
					cmmbean.setExnm(list.get(i).getExnm());
					cmmbean.setExcd(list.get(i).getExcd());
					cmmbean.setLamt(Double.parseDouble(test.getDecimalFormat(list.get(i).getLamt()+"",2)));
					cmmbean.setRamt(Double.parseDouble(test.getDecimalFormat(list.get(i).getRamt()+"",2)));
					cmmbean.setFdsy(Double.parseDouble(list.get(i).getFdsy()));
					cmmbean.setPrice(Double.parseDouble(test.getDecimalFormat(list.get(i).getNeby()+"",4)));
					cmmbean.setPricer(Double.parseDouble(test.getDecimalFormat(list.get(i).getNesl()+"",4)));
					cmmbean.setZcyk(Double.parseDouble(test.getDecimalFormat(list.get(i).getZcyk()+"",2)));
					cmmbean.setPpyk(Double.parseDouble(test.getDecimalFormat(list.get(i).getPpyk()+"",2)));
					cmmbean.setTryk(Double.parseDouble(test.getDecimalFormat(list.get(i).getTryk()+"",2)));
					cmmbean.setSgyk(Double.parseDouble(test.getDecimalFormat(list.get(i).getSgyk()+"",2)));
					cmmbean.setToyk(Double.parseDouble(test.getDecimalFormat(list.get(i).getToyk()+"",2)));
					list1.add(f.onFormule(cmmbean));
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list1;
	}
	
	
}
