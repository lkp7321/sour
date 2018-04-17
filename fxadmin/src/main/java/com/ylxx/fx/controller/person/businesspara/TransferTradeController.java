package com.ylxx.fx.controller.person.businesspara;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.BusinessVo;
import com.ylxx.fx.service.person.businesspara.TransferTradeService;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.Table;
/**
 * 出入金流水查询
 * @author lz130
 *
 */
@Controller
public class TransferTradeController {
	private static final Logger log = LoggerFactory.getLogger(TransferTradeController.class);
	@Resource(name="transferTradeService")
	private TransferTradeService transferTradeService;
	
	/**
	 * 数据查询
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/getAllTransferTrade.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllTransferTrade(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		log.info("出入金流水查询");
		Integer pageNo = bsVo.getPageNo();
		Integer pageSize = bsVo.getPageSize();
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String lcno=bsVo.getCurLcno();//交易流水号
		String strcuac=bsVo.getStrcuac();//卡号
		String trdtbegin=bsVo.getTrdtbegin(); 
		String trdtend=bsVo.getTrdtbegin(); 
		String comaogcd=bsVo.getComaogcd();//A000
		String combogcd=bsVo.getCombogcd();//all
		PageInfo<Tranlist> page = transferTradeService.selectPageTranlist(
				pageNo, pageSize, curUser.getProd(),
				lcno, strcuac, trdtbegin, trdtend, comaogcd, combogcd);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	/**
	 * 导出Excel
	 * @param bsVo
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@RequestMapping(value = "/TransferOutputexcel.do")
    public void showImgCodeTransfer(BusinessVo bsVo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = bsVo.getTableName();//前台传的  表名
        List<Table> tableList = bsVo.getTableList();//前台传的  表头，及Key
        String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String lcno=bsVo.getCurLcno();//交易流水号
		String strcuac=bsVo.getStrcuac();//卡号
		String trdtbegin=bsVo.getTrdtbegin(); 
		String trdtend=bsVo.getTrdtbegin(); 
		String comaogcd=bsVo.getComaogcd();//A000
		String combogcd=bsVo.getCombogcd();//all
		//这里是我自己查询数据的过程
		List<Tranlist> list = transferTradeService.selectTranlist(curUser.getProd(),
				lcno, strcuac, trdtbegin, trdtend, comaogcd, combogcd);
        //数据封装
        PoiExcelExport<Tranlist> pee = new PoiExcelExport<Tranlist>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
}
