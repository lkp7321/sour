package com.ylxx.fx.service.impl.jsh;

import javax.annotation.Resource;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.jsh.DT_TranRemsgMapper;
import com.ylxx.fx.service.jsh.DT_TranRemsgService;
import com.ylxx.fx.service.po.jsh.DT_Price;
import com.ylxx.fx.service.po.jsh.JshPages;
import com.ylxx.fx.service.po.jsh.TbTrd_RegMsgList;
import com.ylxx.fx.service.po.jsh.TbTrd_TranList;
/**
 * 定投管理
 * @author lz130
 *
 */
@Service("dt_TranRemsgService")
public class DT_TranRemsgServiceImpl implements DT_TranRemsgService {
	private static final Logger log = LoggerFactory.getLogger(DT_TranRemsgServiceImpl.class);
	@Resource
	private DT_TranRemsgMapper dt_TranRemsgMapper;
	/**
	 * 定投签约信息查询
	 * 分页
	 */
	public PageInfo<Map<String, Object>> selectDtTranRemsgList(JshPages<TbTrd_RegMsgList> tbTrd_RegMsgList) {
		log.info("开始查询签约解约信息：");
		List<Map<String, Object>> list = null;
		Integer pageNo = tbTrd_RegMsgList.getPageNo();
		Integer pageSize = tbTrd_RegMsgList.getPageSize();
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
		try {
			if(tbTrd_RegMsgList.getEntity().getRgtp().equals("0")) {//签约
				list =  dt_TranRemsgMapper.selDT_TranRemsgListQ(tbTrd_RegMsgList.getEntity().getRgdt(), tbTrd_RegMsgList.getEntity().getCrdt(), tbTrd_RegMsgList.getEntity().getExnm(), tbTrd_RegMsgList.getEntity().getCuac());
			}else if(tbTrd_RegMsgList.getEntity().getRgtp().equals("1")) {//解约
				list =  dt_TranRemsgMapper.selDT_TranRemsgListJ(tbTrd_RegMsgList.getEntity().getRgdt(), tbTrd_RegMsgList.getEntity().getCrdt(), tbTrd_RegMsgList.getEntity().getExnm(), tbTrd_RegMsgList.getEntity().getCuac());
			}
		} catch (Exception e) {
			log.error("查询签约信息出错");
			log.error(e.getMessage(), e);
		}
		PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
		if(page.getList()!=null && page.getList().size()>0) {
	    	for (int i = 0; i < page.getList().size(); i++) {
	    		String jgfg = page.getList().get(i).get("JGFG").equals("01")?"结汇":"购汇";
	    		String rgtp = page.getList().get(i).get("RGTP").equals("0")?"签约":"解约";
	    		String cxfg = page.getList().get(i).get("CXFG").equals("1")?"汇":"钞";
	    		page.getList().get(i).put("JGFG", jgfg);
	    		page.getList().get(i).put("RGTP", rgtp);
	    		page.getList().get(i).put("CXFG", cxfg);
			}
	    }
		return page;
	}
	/**
	 * 定投流水查询
	 */
	public PageInfo<Map<String, Object>> selectDtTransList(JshPages<TbTrd_TranList> tbTrd_TranList) {
		log.info("开始查询定投流水信息：");
		List<Map<String, Object>> list = null;
		Integer pageNo = tbTrd_TranList.getPageNo();
		Integer pageSize = tbTrd_TranList.getPageSize();
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    try {
			list =  dt_TranRemsgMapper.selDT_TranList(tbTrd_TranList.getEntity().getTrdt(), tbTrd_TranList.getEntity().getTrtm(), tbTrd_TranList.getEntity().getFonm(), tbTrd_TranList.getEntity().getCuac());
		} catch (Exception e) {
			log.error("查询定投流水信息出错");
			log.error(e.getMessage(), e);
		}
	    PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
	    if(page.getList()!=null && page.getList().size()>0) {
	    	for (int i = 0; i < page.getList().size(); i++) {
	    		String jgfg = page.getList().get(i).get("JGFG").equals("01")?"结汇":"购汇";
	    		String type = page.getList().get(i).get("TYPE").equals("01")?"签约流水":page.getList().get(i).get("TYPE").equals("02")?"解约流水":"定投交易流水";
	    		String status_code = "W".equals(page.getList().get(i).get("STATUS_CODE"))?"正在处理":"S".equals(page.getList().get(i).get("STATUS_CODE"))?"处理成功":"处理失败";
	    		page.getList().get(i).put("JGFG", jgfg);
	    		page.getList().get(i).put("TYPE", type);
				page.getList().get(i).put("STATUS_CODE", status_code);
			}
	    }
		return page;
	}
	/**
	 * 定投价格查询
	 */
	public PageInfo<Map<String, Object>> selectDtPriceList(JshPages<DT_Price> dT_Price) {
		log.info("开始查询定投价格：");
		List<Map<String, Object>> list = null;
		Integer pageNo = dT_Price.getPageNo();
		Integer pageSize = dT_Price.getPageSize();
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    try {
			list =  dt_TranRemsgMapper.selDT_Price(
					dT_Price.getEntity().getTrdt(), dT_Price.getEntity().getTrtm(), dT_Price.getEntity().getExnm());
		} catch (Exception e) {
			log.error("查询定投价格出错");
			log.error(e.getMessage(), e);
		}
	    PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
		return page;
	}
	/**
	 * 导出
	 */
	@Override
	public List<Map<String, Object>> selectAllDtTranRemsgList(String rgtp, String rgdt, String crdt, String exnm, String cuac) {
		List<Map<String, Object>> list = null;
		try {
			if("0".equals(rgtp)) {//签约
				list =  dt_TranRemsgMapper.selDT_TranRemsgListQ(rgdt, crdt, exnm, cuac);
			}else if("1".equals(rgtp)) {//解约
				list =  dt_TranRemsgMapper.selDT_TranRemsgListJ(rgdt, crdt, exnm, cuac);
			}
		} catch (Exception e) {
			log.error("查询签约信息出错");
			log.error(e.getMessage(), e);
		}
		if(list!=null && list.size()>0) {
	    	for (int i = 0; i < list.size(); i++) {
	    		String jgfg = list.get(i).get("JGFG").equals("01")?"结汇":"购汇";
	    		String rgtps = list.get(i).get("RGTP").equals("0")?"签约":"解约";
	    		String cxfg = list.get(i).get("CXFG").equals("1")?"汇":"钞";
	    		list.get(i).put("JGFG", jgfg);
	    		list.get(i).put("RGTP", rgtps);
	    		list.get(i).put("CXFG", cxfg);
			}
	    }
		return list;
	}
	/**
	 * 导出
	 */
	@Override
	public List<Map<String, Object>> selectAllDtTransList(String trdt, String trtm, String cuac, String fonm) {
		List<Map<String, Object>> list = null;
	    try {
			list =  dt_TranRemsgMapper.selDT_TranList(trdt, trtm, fonm, cuac);
		} catch (Exception e) {
			log.error("查询定投流水信息出错");
			log.error(e.getMessage(), e);
		}
	    if(list!=null && list.size()>0) {
	    	for (int i = 0; i < list.size(); i++) {
	    		String jgfg = list.get(i).get("JGFG").equals("01")?"结汇":"购汇";
	    		String type = list.get(i).get("TYPE").equals("01")?"签约流水":list.get(i).get("TYPE").equals("02")?"解约流水":"定投交易流水";
	    		String status_code = "W".equals(list.get(i).get("STATUS_CODE"))?"正在处理":"S".equals(list.get(i).get("STATUS_CODE"))?"处理成功":"处理失败";
	    		list.get(i).put("JGFG", jgfg);
	    		list.get(i).put("TYPE", type);
				list.get(i).put("STATUS_CODE", status_code);
			}
	    }
		return list;
	}
	/**
	 * 导出
	 */
	@Override
	public List<Map<String, Object>> selectAllDtPriceList(String trdt, String trtm, String exnm) {
		List<Map<String, Object>> list = null;
	    try {
			list =  dt_TranRemsgMapper.selDT_Price(trdt, trtm, exnm);
		} catch (Exception e) {
			log.error("查询定投价格出错");
			log.error(e.getMessage(), e);
		}
		return list;
	}
}
