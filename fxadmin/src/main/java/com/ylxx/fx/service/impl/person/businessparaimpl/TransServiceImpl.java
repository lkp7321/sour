package com.ylxx.fx.service.impl.person.businessparaimpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.person.businesspara.TransMapper;
import com.ylxx.fx.service.person.businesspara.TransService;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.utils.TestFormatter;
/**
 * 交易流水查询
 * @author lz130
 *
 */
@Service("transService")
public class TransServiceImpl implements TransService {
	private static final Logger log = LoggerFactory.getLogger(TransServiceImpl.class);
	@Resource
	private TransMapper transMap;
	private List<Map<String, String>> hamap = null;
	
	/**
	 * 分页 P001，P002, P003
	 */
	public PageInfo<Tranlist> getPageAllTrans(String prod, String trancode, 
			String strcuac, String trdtbegin, String trdtend, 
			String byexnm, String comaogcd, String combogcd,
			Integer pageNo, Integer pageSize){
		log.info("prod:"+prod);
		log.info("trancode:"+trancode);
		log.info("strcuac:"+strcuac);
		log.info("comaogcd："+comaogcd);
		log.info("combogcd："+combogcd);
		log.info("trdtbegin："+trdtbegin);
		log.info("trdtend："+trdtend);
		log.info("byexnm："+byexnm);
		List<Tranlist> tranList = null;
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    try {
	    	if(prod.equals("P001")){
	    		tranList = transMap.selectFxipTranlistP001(trancode, strcuac, trdtbegin, 
						trdtend, byexnm, comaogcd, combogcd);
	    	}else if(prod.equals("P002")){
	    		tranList = transMap.selectFxipTranlistP002(trancode, strcuac, trdtbegin, 
						trdtend, byexnm, comaogcd, combogcd);
	    	}else if(prod.equals("P003")){
	    		if(trancode.equals("0")) {
	    			trancode = "all";
	    		}
	    		tranList = transMap.selectFxipTranlistP003(trancode, strcuac, trdtbegin, 
						trdtend, byexnm, comaogcd, combogcd);
	    	}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	    PageInfo<Tranlist> page = new PageInfo<Tranlist>(tranList);
	    if(page.getList()!=null && page.getList().size()>0){
			for (int i = 0; i < page.getList().size(); i++) {
				TestFormatter test = new TestFormatter();
				page.getList().get(i).setByam(test.getDecimalFormat(page.getList().get(i).getByam(), 2));
				page.getList().get(i).setSlam(test.getDecimalFormat(page.getList().get(i).getSlam(), 2));
				page.getList().get(i).setPram(test.getDecimalFormat(page.getList().get(i).getPram(), 2));
				page.getList().get(i).setAmut(test.getDecimalFormat(page.getList().get(i).getAmut(), 2));
				page.getList().get(i).setBrsy(test.getDecimalFormat(page.getList().get(i).getBrsy(), 2));
				page.getList().get(i).setUsam(test.getDecimalFormat(page.getList().get(i).getUsam(), 2));
			}
		}
		return page;
	}
	/**
	 * 导出P001,P002,P003
	 */
	public List<Tranlist> getAllTrans(String prod, String trancode, String strcuac, 
			String trdtbegin, String trdtend, String byexnm, String comaogcd, String combogcd){
		List<Tranlist> tranList = null;
		try {
			if(prod.equals("P001")){
				tranList = transMap.selectFxipTranlistP001(trancode, strcuac, trdtbegin, 
						trdtend, byexnm, comaogcd, combogcd);
			}else if(prod.equals("P002")){
				tranList = transMap.selectFxipTranlistP002(trancode, strcuac, trdtbegin, 
						trdtend, byexnm, comaogcd, combogcd);
			}else if(prod.equals("P003")){
	    		tranList = transMap.selectFxipTranlistP003(trancode, strcuac, trdtbegin, 
						trdtend, byexnm, comaogcd, combogcd);
	    	}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		if(tranList!=null&&tranList.size()>0){
			for (int i = 0; i < tranList.size(); i++) {
				TestFormatter test = new TestFormatter();
				tranList.get(i).setByam(test.getDecimalFormat(tranList.get(i).getByam(), 2));
				tranList.get(i).setSlam(test.getDecimalFormat(tranList.get(i).getSlam(), 2));
				tranList.get(i).setPram(test.getDecimalFormat(tranList.get(i).getPram(), 2));
				tranList.get(i).setAmut(test.getDecimalFormat(tranList.get(i).getAmut(), 2));
				tranList.get(i).setBrsy(test.getDecimalFormat(tranList.get(i).getBrsy(), 2));
				tranList.get(i).setUsam(test.getDecimalFormat(tranList.get(i).getUsam(), 2));
			}
		}
		return tranList;
	}
	
	/**
	 * 获取交易码，P001,P002,P003,P004,都需要
	 */
	public List<Map<String, String>> seltrancode(String prod){
		try {
			if(prod.equals("P001")){
				hamap = transMap.querytrancodeP001(prod);
			}else if(prod.equals("P002")){
				hamap = transMap.querytrancodeP002();
			}else if(prod.equals("P003")){
				hamap = transMap.querytrancodeP003();
			}else if(prod.equals("P004")){
				hamap = transMap.querytrancodeP004();
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return hamap;
	}
	/**
	 * 获取买 币P001,P002,P003--P004不需要
	 */
	public List<String> selbyexnm(String prod){
		List<String> strlist = null;
		try {
			if(prod.equals("P001")){
				strlist = transMap.querybuyexnmP001(prod);
			}else if(prod.equals("P002")){
				strlist = transMap.querybuyexnmP002();
			}else if(prod.equals("P003")){
				strlist = transMap.querybuyexnmP003();
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return strlist;
	}
	
	/**
	 * 导出P004
	 */
	public List<Tranlist> queryPerelistExcel(String strlcno, String strcuac,String strIdno,
			String bgdate, String enddate, String com1, String com2,
			String comaogcd, String combogcd,String comtrtp,String comchnl) {
		List<Tranlist> list = null;
	    try {
			list = transMap.selectFxipTranlistP004(
					strlcno, strcuac, strIdno, bgdate, enddate, com1, com2, comaogcd, combogcd, comtrtp, comchnl);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}
	/**
	 * 分页 P004
	 */
	public PageInfo<Tranlist> queryPerelist(
			String strlcno, String strcuac, String strIdno,
			String bgdate, String enddate, String com1, 
			String com2, String comaogcd, String combogcd,
			String comtrtp, String comchnl,
			Integer pageNo, Integer pageSize) {
		List<Tranlist> list = null;
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    try {
			list = transMap.selectFxipTranlistP004(
					strlcno, strcuac, strIdno, bgdate, enddate, com1, com2, comaogcd, combogcd, comtrtp, comchnl);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	    PageInfo<Tranlist> page = new PageInfo<Tranlist>(list);
	    return page;
	}
	
}
