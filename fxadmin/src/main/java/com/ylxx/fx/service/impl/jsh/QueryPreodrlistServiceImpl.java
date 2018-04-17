package com.ylxx.fx.service.impl.jsh;

import java.util.List;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.mapper.jsh.QueryPreodrlistMapper;
import com.ylxx.fx.service.jsh.QueryPreodrlistService;
import com.ylxx.fx.service.po.jsh.QueryPreodrlistIn;
import com.ylxx.fx.service.po.jsh.Trd_Preodrlist;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;

@Service("queryPreodrlistService")
public class QueryPreodrlistServiceImpl implements QueryPreodrlistService {

	private static final Logger LOGGER = LoggerFactory.getLogger(QueryPreodrlistServiceImpl.class);
	@Resource
	private QueryPreodrlistMapper queryPreodrlistMapper;

	public String getPreodrlist(QueryPreodrlistIn in,Integer pageNo,Integer pageSize) {
		String result = "";
	 	pageNo = pageNo==null?1:pageNo;
	 	pageSize = pageSize==null?10:pageSize;
	 	PageHelper.startPage(pageNo, pageSize);
	 	String clms = "";
	 	try {
	 		//读取字段的名称:从接口输出表中得到
	 		List<String> columns = queryPreodrlistMapper.getColumns();
	 		for (int i = 0; i < columns.size(); i++) {
	 			if (i!=columns.size()-1) {
	 				clms = columns.get(i)+",";
				}else {
					clms += columns.get(i);
				}
			}
	 		LOGGER.info("需要查询的字段为："+clms);
	 		LOGGER.info("传入参数为: qutp="+in.getQutp()+",cuno="+in.getCuno()+",stdt="+in.getStdt()
	 				+",eddt="+in.getEddt()+",rqno="+in.getRqno());
	 		//查询挂单流水
	 		List<Trd_Preodrlist> list = queryPreodrlistMapper.getPreodrlist(clms,in.getProductCode(),in.getQutp(), in.getCuno(), in.getStdt(), 
	 				in.getEddt(), in.getRqno());
	 		if (list.size()==0||list==null) {
	 			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}else {
				PageInfo<Trd_Preodrlist> page = new PageInfo<Trd_Preodrlist>(list);
		 		result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
			}
		} catch (Exception e) {
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	
	/**
	 * 导出Excel
	 */
	@Override
	public List<Trd_Preodrlist> getPreodrList(String poductCode,int qutp, String cuno, String stdt, String eddt, String rqno) {
		List<Trd_Preodrlist> list = null;
		String clms = "";
		try {
	 		//读取字段的名称:从接口输出表中得到
	 		List<String> columns = queryPreodrlistMapper.getColumns();
	 		for (int i = 0; i < columns.size(); i++) {
	 			if (i!=columns.size()-1) {
	 				clms = columns.get(i)+",";
				}else {
					clms += columns.get(i);
				}
			}
	 		LOGGER.info("需要查询的字段为："+clms);
	 		//查询挂单流水
	 		list = queryPreodrlistMapper.getPreodrlist(clms, poductCode, qutp, cuno, stdt, eddt, rqno);
		}catch (Exception e) {
			LOGGER.error("查询挂单流水出错");
			LOGGER.error(e.getMessage(), e);
		}
		return list;
	}

}
