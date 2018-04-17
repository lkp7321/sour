package com.ylxx.fx.service.impl.person.priceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.price.PdtPointMapper;
import com.ylxx.fx.service.person.price.IPdtPointService;
import com.ylxx.fx.service.po.PdtPointBean;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;

@Service("pdtPointService")
public class PdtPointServiceImpl implements IPdtPointService {

	@Resource
	private PdtPointMapper pdtPointMapper;

	//查询产品点差列表
	public String selectAllPdtPoint(String prod){
		String result = "";
		try {
			List<PdtPointBean> pdtPoints = pdtPointMapper.selectAllPdtPoint(prod.trim());
			if (pdtPoints!=null&&pdtPoints.size()>0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(pdtPoints));				
			}else if (pdtPoints!=null&&pdtPoints.size()==0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}

}
