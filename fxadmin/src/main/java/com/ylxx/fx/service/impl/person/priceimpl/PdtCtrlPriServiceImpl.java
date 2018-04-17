package com.ylxx.fx.service.impl.person.priceimpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.price.PdtCtrlPriMapper;
import com.ylxx.fx.service.person.price.IPdtCtrlPriService;
import com.ylxx.fx.service.po.PdtCtrlPriBean;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;

@Service("pdtCtrlPriService")
public class PdtCtrlPriServiceImpl implements IPdtCtrlPriService {

	@Resource
	private PdtCtrlPriMapper pdtCtrlPriMapper;

	//返回指定的产品干预值 通用规则
	public String selectAllPdtCtrlpri(String ptid) {
		String result = "";
		try {
			List<PdtCtrlPriBean> pdtCtrlPriBeans = pdtCtrlPriMapper.selectAllPdtCtrlpri(ptid.trim());
			if (pdtCtrlPriBeans!=null&&pdtCtrlPriBeans.size()>0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(pdtCtrlPriBeans));
			}else if (pdtCtrlPriBeans!=null&&pdtCtrlPriBeans.size()==0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}

}
