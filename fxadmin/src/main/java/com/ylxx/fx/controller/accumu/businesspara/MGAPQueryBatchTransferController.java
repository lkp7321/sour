package com.ylxx.fx.controller.accumu.businesspara;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.annotation.Resource;
import javax.xml.rpc.ServiceException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.AccumuBusinessParaVo;
import com.ylxx.fx.service.accumu.businesspara.MGAPQueryBatchTransferSerivce;
/**
 * 积存金：批量转账查询
 * @author lz130
 *
 */
@Controller
public class MGAPQueryBatchTransferController {
	@Resource(name="MGAPQueryBatchTransferSerivce")
	private MGAPQueryBatchTransferSerivce mGAPQueryBatchTransferSerivce;
	/**
	 * 批量转账查询
	 * @param accumuBusinessParaVo
	 * @return
	 */
	@RequestMapping(value="/querySearch.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String querySearch(@RequestBody AccumuBusinessParaVo accumuBusinessParaVo){
		return mGAPQueryBatchTransferSerivce.getSearch(accumuBusinessParaVo.getFilename(), accumuBusinessParaVo.getNumber(), accumuBusinessParaVo.getPageNo(), accumuBusinessParaVo.getPageSize());
	}
	/**
	 * 批量转账发送
	 * @throws ServiceException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 * trsn:"56801"+nowDate+nowTime+"0000000000000";
	 * trid:"A2107",
	 * bhid:"8500",
	 * chnl:"1001",
	 * rqdt:nowdate,
	 * rqtm:nowtime,
	 * trtl:"",
	 * ttyn:"",
	 * autl:"",
	 * number:"批次号",
	 * filename:"文件名",
	 * count:"总笔数",
	 * amut:"总金额（克）"
	 */
	@RequestMapping(value="/querySearchSend.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String querySearchSend(String trsn, String trid, String bhid, String chnl, String rqdt, String rqtm, String trtl, String ttyn, String autl, String number, String filename, String count, String amut) throws MalformedURLException, RemoteException, ServiceException{
		return mGAPQueryBatchTransferSerivce.getBatchTransfer(trsn, trid, bhid, chnl, rqdt, rqtm, trtl, ttyn, autl, number, filename, count, amut);
	}
}
