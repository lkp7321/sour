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
import com.ylxx.fx.service.person.businesspara.CurrencyChangeService;
import com.ylxx.fx.service.po.TRD_EXCHTRANLIST;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.Table;
/**
 * 外币兑换流水
 * @author lz130
 *
 */
@Controller
public class CurrencyChangeController {
	private static final Logger log = LoggerFactory.getLogger(CurrencyChangeController.class);
	@Resource(name="currencychanService")
	private CurrencyChangeService currencychanService;
	
	/**
	 * 外币兑换流水   - 查询
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/getAllCurrencychan.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllCurrencychan(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		log.info("外币交换流水查询");
		Integer pageNo = bsVo.getPageNo();
		Integer pageSize = bsVo.getPageSize();
		String curLcno=bsVo.getCurLcno();//"";//交易流水号
		String strcuno=bsVo.getStrcuno();//客户号
		String strsoac=bsVo.getStrsoac(); //来源账号
		String vurData=bsVo.getVurData(); //日期
		String comaogcd=bsVo.getComaogcd();//A000 
		String combogcd=bsVo.getCombogcd();//all
		PageInfo<TRD_EXCHTRANLIST> page = currencychanService.selPageTranlist(
				pageNo, pageSize, curLcno, strcuno, strsoac, vurData, comaogcd, combogcd);
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
	@RequestMapping(value = "/CurrencyOutputexcel.do")
    public void showImgCodeCurrency(BusinessVo bsVo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = bsVo.getTableName();//前台传的  表名
        List<Table> tableList = bsVo.getTableList();//前台传的  表头，及Key
        String curLcno=bsVo.getCurLcno();//"";//交易流水号
		String strcuno=bsVo.getStrcuno();//客户号
		String strsoac=bsVo.getStrsoac(); //来源账号
		String vurData=bsVo.getVurData(); //日期
		String comaogcd=bsVo.getComaogcd();//A000 
		String combogcd=bsVo.getCombogcd();//all
		//这里是我自己查询数据的过程
		List<TRD_EXCHTRANLIST> list = currencychanService.selTranlist(
				curLcno, strcuno, strsoac, vurData, comaogcd, combogcd);
        PoiExcelExport<TRD_EXCHTRANLIST> pee = new PoiExcelExport<TRD_EXCHTRANLIST>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
}
