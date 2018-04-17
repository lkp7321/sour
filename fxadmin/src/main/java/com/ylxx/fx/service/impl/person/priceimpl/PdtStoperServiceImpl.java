package com.ylxx.fx.service.impl.person.priceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.price.PdtStoperMapper;
import com.ylxx.fx.service.person.price.IPdtStoperService;
import com.ylxx.fx.service.po.PdtStoperBean;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;



@Service("pdtStoperService")
public class PdtStoperServiceImpl implements IPdtStoperService {

	@Resource
	private PdtStoperMapper PdtStoperMapper;
	
	//获取所有的产品价格停牌数据
	public String selectAllPdtStoper(String ptid){
		String result = "";
		List<PdtStoperBean> pdtStoperBeans = new ArrayList<PdtStoperBean>();
		try {
			pdtStoperBeans = PdtStoperMapper.selectAllPdtStoper(ptid.trim());
			if (pdtStoperBeans!=null&&pdtStoperBeans.size()>0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(pdtStoperBeans));
			}else if (pdtStoperBeans!=null&&pdtStoperBeans.size()==0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}

}
