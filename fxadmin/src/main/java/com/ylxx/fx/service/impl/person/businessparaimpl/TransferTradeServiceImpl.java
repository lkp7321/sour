package com.ylxx.fx.service.impl.person.businessparaimpl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.person.businesspara.TransferTradeMapper;
import com.ylxx.fx.service.person.businesspara.TransferTradeService;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.utils.TestFormatter;
@Service("transferTradeService")
public class TransferTradeServiceImpl implements TransferTradeService {
	private static final Logger log = LoggerFactory.getLogger(TransferTradeServiceImpl.class);
	@Resource
	private TransferTradeMapper transTradeMap;
	
	//获取所有数据
	public List<Tranlist> selectTranlist(String prod, String lcno, String strcuac,
			String trdtbegin, String trdtend, String comaogcd, String combogcd) {
		List<Tranlist> list = null;
		TestFormatter test = new TestFormatter();
		try {
			if(prod.equals("P001")){
				list = transTradeMap.selectTranlist(
						lcno, strcuac, trdtbegin, trdtend, comaogcd, combogcd);
			}else if(prod.equals("P002")){
				list = transTradeMap.selectTranlist2(lcno, strcuac, trdtbegin, trdtend, comaogcd, combogcd);
			}else if(prod.equals("P003")){
				list = transTradeMap.selectTranlist3(lcno, strcuac, trdtbegin, trdtend, comaogcd, combogcd);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (list!=null && list.size()>0) {
			for (int j = 0; j < list.size(); j++) {
				list.get(j).setAmut(test.getDecimalFormat(list.get(j).getAmut()+"",2));
			}
		}
		return list;
	}

	//出入金分页
	public PageInfo<Tranlist> selectPageTranlist(Integer pageNo,
			Integer pageSize, String prod, String lcno, String strcuac,
			String trdtbegin, String trdtend, String comaogcd, String combogcd) {
		List<Tranlist> list = null;
		TestFormatter test = new TestFormatter();
		pageNo = pageNo == null?1:pageNo;
	    pageSize = pageSize == null?10:pageSize;
	    PageHelper.startPage(pageNo, pageSize);
	    try {
	    	if(prod.equals("P001")){
				list = transTradeMap.selectTranlist(
						lcno, strcuac, trdtbegin, trdtend, comaogcd, combogcd);
			}else if(prod.equals("P002")){
				list = transTradeMap.selectTranlist2(
						lcno, strcuac, trdtbegin, trdtend, comaogcd, combogcd);
			}else if(prod.equals("P003")){
				list = transTradeMap.selectTranlist3(
						lcno, strcuac, trdtbegin, trdtend, comaogcd, combogcd);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	    PageInfo<Tranlist> page = new PageInfo<Tranlist>(list);
	    if (page.getList()!=null && page.getList().size()>0) {
			for (int j = 0; j < list.size(); j++) {
				page.getList().get(j).setAmut(test.getDecimalFormat(page.getList().get(j).getAmut()+"",2));
			}
		}
		return page;
	}

}
