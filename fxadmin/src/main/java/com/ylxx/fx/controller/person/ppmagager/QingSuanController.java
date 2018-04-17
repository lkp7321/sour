package com.ylxx.fx.controller.person.ppmagager;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.MoZhangVo;
import com.ylxx.fx.service.person.ppmagager.IQingSuanService;

/**
 * 清算汇总查询
 * @author lz130
 *
 */
@Controller
//@RequestMapping("fx")
public class QingSuanController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QingSuanController.class);
	
	@Resource(name="qingSuanService")
	private IQingSuanService qingSuanService;
	
	/**
	 * 条件查询
	 * @param mzVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/selectTrdt.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectTrdt(@RequestBody MoZhangVo mzVo) throws Exception {
		LOGGER.info("条件查询清算汇总数据");
		return qingSuanService.QingSuanGC(mzVo.getTrdt(), mzVo.getCombox());	
	}
}
