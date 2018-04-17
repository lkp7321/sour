package com.ylxx.fx.controller.accumu.businesspara;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ylxx.fx.core.domain.AccumuBusinessParaVo;
import com.ylxx.fx.service.accumu.businesspara.MGAPQueryBatchTransferDetailService;
/**
 * 积存金：
 * 批量转账明细
 * @author lz130
 *
 */
@Controller
public class MGAPQueryBatchTransferDetailController {
	@Resource(name="MGAPQueryBatchTransferDetailService")
	private MGAPQueryBatchTransferDetailService mGAPQueryBatchTransferDetailService;
	/**
	 * 批量转帐明细查询
	 * @param req
	 * @param accumuBusinessParaVo
	 * @return
	 */
	@RequestMapping(value="/detailSearch.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String detailSearch(HttpServletRequest req, @RequestBody AccumuBusinessParaVo accumuBusinessParaVo){
		return mGAPQueryBatchTransferDetailService.getSearch(accumuBusinessParaVo.getNumber(), accumuBusinessParaVo.getStatus(), accumuBusinessParaVo.getPageNo(), accumuBusinessParaVo.getPageSize());
	 } 
}
