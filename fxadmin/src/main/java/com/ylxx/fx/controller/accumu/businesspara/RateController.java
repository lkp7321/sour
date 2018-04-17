package com.ylxx.fx.controller.accumu.businesspara;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.RateVo;
import com.ylxx.fx.core.domain.UserVo;
import com.ylxx.fx.service.accumu.businesspara.IRateService;
import com.ylxx.fx.service.po.RateBean;
import com.ylxx.fx.utils.ErrorCodePrice;
import com.ylxx.fx.utils.ResultDomain;

/**
 * 利率配置
 * @author lz130
 *
 */
@Controller
public class RateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RateController.class);
	List<RateBean> rlist=null;
	
	
	@Resource(name="rateService")
	private IRateService rateService;
	
	/**
	 * 查询利率数据
	 * @param userKey
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/getRateList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getSearchList(@RequestBody UserVo userVo){
		String result = "";
		// 分页
		Integer pageNo = userVo.getPageNo() == null ? 1 : userVo.getPageNo();
		Integer pageSize = userVo.getPageSize() == null ? 10 : userVo.getPageSize();
	 	PageHelper.startPage(pageNo, pageSize);
		try {
			rlist = rateService.searchList();
			if (rlist != null && rlist.size()>0) {
				//用PageInfo对结果进行包装
				PageInfo<RateBean> page = new PageInfo<RateBean>(rlist);
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_00.getCode(), JSON.toJSONString(page));
				LOGGER.info("条数为："+rlist.size());
			}else if (rlist != null && rlist.size() == 0) {
				result = ResultDomain.getRtnMsg(ErrorCodePrice.E_01.getCode(), null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultDomain.getRtnMsg(ErrorCodePrice.E_02.getCode(), null);
		}
		return result;
	}
	
	/**
	 * 向利率表里添加记录	
	 * @param rateVo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doRateInsert.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String doInsert(@RequestBody RateVo rateVo) throws Exception{
//		RateVo rateVo = new RateVo();
//		rateVo.setPdtp("pdtp");
//		rateVo.setDesp("desp");
//		rateVo.setGain("1111.11");
//		rateVo.setJxtp("A");
//		rateVo.setMint("222.22");
//		rateVo.setPdbr("22222");
//		rateVo.setPdtm("33333");
//		rateVo.setRate("22.22");
		return rateService.insertWithInsertHis(rateVo);
	}
	
	/**
	 * 更新利率表里的记录	
	 * @param rateVo
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doRateUpdate.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String doUpdate(@RequestBody RateVo rateVo) throws Exception{
//		RateVo rateVo = new RateVo();
//		rateVo.setPdtp("pdtp");
//		rateVo.setDesp("despgg");
//		rateVo.setGain("1111.119");
//		rateVo.setJxtp("D");
//		rateVo.setMint("222.229");
//		rateVo.setPdbr("222229");
//		rateVo.setPdtm("333339");
//		rateVo.setRate("22.229");
		return rateService.updateWithInsertHis(rateVo);
	}
}
