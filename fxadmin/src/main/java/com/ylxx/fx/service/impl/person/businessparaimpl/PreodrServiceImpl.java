package com.ylxx.fx.service.impl.person.businessparaimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.person.businesspara.PreodrMapper;
import com.ylxx.fx.service.person.businesspara.PreodrService;
import com.ylxx.fx.service.po.Preodrlist;
import com.ylxx.fx.service.po.Trd_ogcd;
import com.ylxx.fx.utils.TestFormatter;
/**
 * 挂单流水查询
 * @author lz130
 *
 */
@Service("preodrService")
public class PreodrServiceImpl implements PreodrService{
	private static final Logger log = LoggerFactory.getLogger(PreodrServiceImpl.class);
	@Resource
	private PreodrMapper preodrMap;
	private List<Preodrlist> preodList = null;
	private List<Trd_ogcd> trdogList = null;
	/**
	 * 查询数据
	 */
	public PageInfo<Preodrlist> getAllPreodrList(
			String prod, String strcuac, String trdtbegin, 
			String trdtend, String comaogcd, String combogcd,
			Integer pageNo, Integer pageSize){
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		try {
			preodList = preodrMap.selectPreodr(prod, strcuac, trdtbegin, trdtend, comaogcd, combogcd);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} 
		PageInfo<Preodrlist> page = new PageInfo<Preodrlist>(preodList);
		if(page.getList()!=null && page.getList().size()>0){
			for (int i = 0; i < page.getList().size(); i++) {
				TestFormatter test = new TestFormatter();
				page.getList().get(i).setByam(test.getDecimalFormat(preodList.get(i).getByam(), 2));
				page.getList().get(i).setSlam(test.getDecimalFormat(preodList.get(i).getSlam(), 2));
				page.getList().get(i).setPram(test.getDecimalFormat(preodList.get(i).getPram(), 2));
			}
		}
		return page;
	}
	
	/**
	 * 获取第一个下拉框
	 */
	public List<Trd_ogcd> getqueryOrgan1(){
		try {
			trdogList = preodrMap.queryOneOrgan();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return trdogList;
	}
	
	/**
	 * 根据第一个下拉框获取第二个
	 */
	public List<Trd_ogcd> getqueryOrgan2(String comaogcd){
		try {
			trdogList = preodrMap.queryTwoOrgan(comaogcd);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return trdogList;
	}

	/**
	 * 查询所有
	 * 挂单流水
	 */
	public List<Preodrlist> AllPreodrList(String prod, String strcuac,
			String trdtbegin, String trdtend, String comaogcd, String combogcd) {
		try {
			preodList = preodrMap.selectPreodr(prod, strcuac, trdtbegin, trdtend, comaogcd, combogcd);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		} 
		if(preodList!=null && preodList.size()>0){
			for (int i = 0; i < preodList.size(); i++) {
				TestFormatter test = new TestFormatter();
				preodList.get(i).setByam(test.getDecimalFormat(preodList.get(i).getByam(), 2));
				preodList.get(i).setSlam(test.getDecimalFormat(preodList.get(i).getSlam(), 2));
				preodList.get(i).setPram(test.getDecimalFormat(preodList.get(i).getPram(), 2));
			}
		}
		return preodList;
	}
}
