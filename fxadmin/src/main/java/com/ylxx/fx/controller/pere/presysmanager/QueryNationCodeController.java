package com.ylxx.fx.controller.pere.presysmanager;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.QueryNationCodeVo;
import com.ylxx.fx.service.pere.presysmanager.IQueryNationCodeService;

@Controller
//@RequestMapping("fx")

public class QueryNationCodeController {
private static final Logger LOGGER = LoggerFactory.getLogger(QueryNationCodeController.class);
	
	@Resource(name="iQueryNationCodeService")
	private IQueryNationCodeService iQueryNationCodeService;
	
	//查询国别代码对照表
	@RequestMapping(value="/selcCount.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selcCount() throws Exception {
	   
		return iQueryNationCodeService.selcCount();	
	}
	
	//根据输入条件查询国别代码对照表
		@RequestMapping(value="/queryCountByCondition.do",produces="application/json;charset=UTF-8")
		@ResponseBody
	//@RequestBody QueryNationCodeVo QncVo
	public String queryCountByCondition(@RequestBody QueryNationCodeVo QncVo) throws Exception {
		/*QueryNationCodeVo QncVo=new QueryNationCodeVo();
		QncVo.setFieldName("");
		QncVo.setFieldValue("");
		QncVo.setPageNo(null);
		QncVo.setPageSize(null);*/
		
		return iQueryNationCodeService.queryCountByCondition(QncVo.getFieldName(),QncVo.getFieldValue(),
					QncVo.getPageNo(),QncVo.getPageSize());
	}
	
	
	

}
