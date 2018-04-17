package com.ylxx.fx.service.impl.person.ppmagagerimpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ylxx.fx.core.mapper.person.ppmagager.QingSuanMapper;
import com.ylxx.fx.service.person.ppmagager.IQingSuanService;
import com.ylxx.fx.service.po.Totalccyam;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;



@Service("qingSuanService")
public class QingSuanServiceImpl implements IQingSuanService {

	@Resource
	private QingSuanMapper qingSuanMapper;

	//条件查询
	public String QingSuanGC(String trdt, int data){
		String result = null;
		try {
			List<Totalccyam> totalccyams = new ArrayList<Totalccyam>();
			if (data==1) {
				totalccyams = qingSuanMapper.selQQGCHZ(trdt.trim());
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(totalccyams));
			}else {
				//TODO sql语句的表或视图不存在!!!!!
				totalccyams = qingSuanMapper.selqs(trdt.trim());
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(totalccyams));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}

}
