package com.ylxx.fx.controller.person.ppmagager;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.MoZhangVo;
import com.ylxx.fx.service.person.ppmagager.ICheckppDetailService;
/**
 * 对账交易复核
 * @author lz130
 *
 */
@Controller
public class CheckppDetailController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckppDetailController.class);
	
	@Resource(name="checkppDetailService")
	private ICheckppDetailService checkppDetailService;
	
	/**
	 * 查询复核数据
	 * @param seqn
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ConditionList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String ConditionList(@RequestBody String seqn) throws Exception {
		LOGGER.info("查询对账复核数据：");
		//String seqn = "";//行内流水号
		return checkppDetailService.selAllList(seqn);	
	}
	/**
	 * 成功复核
	 * @param mzVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/success.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String success(@RequestBody MoZhangVo mzVo) throws Exception {
		/*String user = "xlj";
		String prod = "P001";
		String seqn = "2012072800002004";//行内流水号
		MoZhangVo mzVo = new MoZhangVo();
		mzVo.setUser(user);
		mzVo.setProd(prod);
		mzVo.setSeqn(seqn);*/
		return checkppDetailService.success(mzVo.getUserKey(), mzVo.getSeqn());	
	}
	/**
	 * 失败复核
	 * @param mzVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/unsuccess.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String unsuccess(@RequestBody MoZhangVo mzVo) throws Exception {
		/*String user = "xlj";
		String prod = "P001";
		String seqn = "2012072800005010";//行内流水号
		MoZhangVo mzVo = new MoZhangVo();
		mzVo.setUser(user);
		mzVo.setProd(prod);
		mzVo.setSeqn(seqn);*/
		return checkppDetailService.unsuccess(mzVo.getUserKey(), mzVo.getSeqn());	
	}
}
