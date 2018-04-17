package com.ylxx.fx.controller.person.businesspara;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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
import com.ylxx.fx.service.person.businesspara.TransService;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.Table;
/**
 * 交易流水查询
 * @author lz130
 *
 */
@Controller
public class TransController {
	private static final Logger log = LoggerFactory.getLogger(TransController.class);
	@Resource(name="transService")
	private TransService transService;
	
	/**
	 * 交易流水查询P001,P002,P003  分页 
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/getAllTransList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllTransList(HttpServletRequest req,@RequestBody BusinessVo bsVo){
		log.info("查询交易流水:");
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prod = curUser.getProd();
		Integer pageNo = bsVo.getPageNo();
		Integer pageSize = bsVo.getPageSize();
		String trancode = bsVo.getTrancode();//"all";//交易码
		String strcuac = bsVo.getStrcuac();//"";//卡号
		String trdtbegin = bsVo.getTrdtbegin();//"20171010"; 
		String trdtend = bsVo.getTrdtend();//"20171010";
		String byexnm = bsVo.getByexnm();//"all";//币别
		String comaogcd = bsVo.getComaogcd();//"A000";//下拉框
		String combogcd = bsVo.getCombogcd();//"all";
		PageInfo<Tranlist> page = transService.getPageAllTrans(prod, trancode, strcuac, trdtbegin, trdtend, byexnm, comaogcd, combogcd, pageNo, pageSize);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	/**
	 * 交易流水查询 分页 P004
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/getAllTransListP04.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllTransListP04(HttpServletRequest req,@RequestBody BusinessVo bsVo){
		log.info("个人结售汇查询交易流水");
		Integer pageNo = bsVo.getPageNo();
		Integer pageSize = bsVo.getPageSize();
		String strlcno = bsVo.getStrlcno();
		String strcuac = bsVo.getStrcuac();//"";//卡号
		String strIdno = bsVo.getStrIdno();
		String bgdate = bsVo.getTrdtbegin();//"20171010"; 
		String enddate = bsVo.getTrdtend();//"20171010";
		String comaogcd = bsVo.getComaogcd();//"A000";//下拉框
		String combogcd = bsVo.getCombogcd();//"all";
		String com1 = bsVo.getCom1();
		String com2 = bsVo.getCom2();
		String comtrtp = bsVo.getComtrtp();
		String comchnl = bsVo.getComchnl();
		PageInfo<Tranlist> page = transService.queryPerelist(
				strlcno, strcuac, strIdno, bgdate, enddate, com1, com2, 
				comaogcd, combogcd, comtrtp, comchnl, pageNo, pageSize);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	
	/**
	 * 获取交易类型P001,P002,P003,P004 
	 * @param req
	 * @param userKey
	 * @return
	 */
	@RequestMapping(value="/getTranCode.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getTranCode(HttpServletRequest req, @RequestBody String userKey){
		log.info("交易类型");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prod = curUser.getProd();
		List<Map<String, String>> hamap = transService.seltrancode(prod);
		if(hamap!=null && hamap.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), hamap);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_03.getCode(), "获取交易类型失败");
		}
		
	}
	
	 /**
	  * 获取交易币别P001,P002,P003
	  * @param req
	  * @param userKey
	  * @return
	  */
	@RequestMapping(value="/getByExnm.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getByExnm(HttpServletRequest req, @RequestBody String userKey){
		log.info("查询交易币别");
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prod = curUser.getProd();
		List<String> list = transService.selbyexnm(prod);
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_04.getCode(),"获取币别失败");
		}
	}
	/**
	 * 交易流水导出
	 * 导出P001,P002,P003
	 * @param bsVo
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@RequestMapping(value = "/TransOutputexcel.do")
    public void showImgCodeTrans(BusinessVo bsVo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = bsVo.getTableName();//前台传的  表名
        List<Table> tableList = bsVo.getTableList();//前台传的  表头，及Key
        String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prod = curUser.getProd();
		String trancode = bsVo.getTrancode();//"all";//交易码
		String strcuac = bsVo.getStrcuac();//"";//卡号
		String trdtbegin = bsVo.getTrdtbegin();//"20171010"; 
		String trdtend = bsVo.getTrdtend();//"20171010";
		String byexnm = bsVo.getByexnm();//"all";//币别
		String comaogcd = bsVo.getComaogcd();//"A000";//下拉框
		String combogcd = bsVo.getCombogcd();//"all";
		List<Tranlist> list = transService.getAllTrans(prod, trancode,
				strcuac, trdtbegin, trdtend, 
				byexnm, comaogcd, combogcd);
        PoiExcelExport<Tranlist> pee = new PoiExcelExport<Tranlist>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
	/**
	 * 交易流水导出
	 * 导出P004
	 * @param bsVo
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@RequestMapping(value = "/TransOutputexcelP04.do")
    public void showImgCodeTransP04(BusinessVo bsVo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = bsVo.getTableName();//前台传的  表名
        List<Table> tableList = bsVo.getTableList();//前台传的  表头，及Key
        String strlcno = bsVo.getStrlcno();
		String strcuac = bsVo.getStrcuac();//"";//卡号
		String strIdno = bsVo.getStrIdno();
		String bgdate = bsVo.getTrdtbegin();//"20171010"; 
		String enddate = bsVo.getTrdtend();//"20171010";
		String comaogcd = bsVo.getComaogcd();//"A000";//下拉框
		String combogcd = bsVo.getCombogcd();//"all";
		String com1 = bsVo.getCom1();
		String com2 = bsVo.getCom2();
		String comtrtp = bsVo.getComtrtp();
		String comchnl = bsVo.getComchnl();
		List<Tranlist> list = transService.queryPerelistExcel(
				strlcno, strcuac, strIdno, bgdate, 
				enddate, com1, com2, comaogcd, 
				combogcd, comtrtp, comchnl);
        PoiExcelExport<Tranlist> pee = new PoiExcelExport<Tranlist>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
}
