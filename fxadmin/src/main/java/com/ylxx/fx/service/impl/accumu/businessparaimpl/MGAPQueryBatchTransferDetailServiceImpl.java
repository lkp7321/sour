package com.ylxx.fx.service.impl.accumu.businessparaimpl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.accumu.businesspara.MGAPQueryBatchTransferDetailMapper;
import com.ylxx.fx.service.accumu.businesspara.MGAPQueryBatchTransferDetailService;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.ResultDomain;
/**
 * 批量转账明细查询
 * @author lz130
 *
 */
@Service("MGAPQueryBatchTransferDetailService")
public class MGAPQueryBatchTransferDetailServiceImpl implements MGAPQueryBatchTransferDetailService {
	private static final Logger log = LoggerFactory.getLogger(MGAPQueryBatchTransferDetailServiceImpl.class);
	@Resource
	public MGAPQueryBatchTransferDetailMapper mGAPQueryBatchTransferDetailMapper;
	/**
	 * 批量转账明细查询
	 */
	@Override
	public String getSearch(String number, String status, Integer pageNo, Integer pageSize) {
		log.info("number:"+number);
		log.info("status:"+status);
		pageNo = pageNo==null?1:pageNo;
		pageSize = pageSize==null?10:pageSize;
		PageHelper.startPage(pageNo,pageSize);
		List<HashMap<String, String>> list = null;
		try {
			if(status!=null && !"".equals(status)) {
				status = status.trim().charAt(status.length()-1)+"";
			}
			if(status.equals("A")) {
				status="";
			}
			list = mGAPQueryBatchTransferDetailMapper.getSearch(number, status);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		PageInfo<HashMap<String,String>> page=new PageInfo<HashMap<String,String>>(list);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}

}
