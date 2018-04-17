package com.ylxx.fx.controller.jsh;

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

import com.afcat.jsh.po.ForfxsaInfoin;
import com.ylxx.fx.service.jsh.JshTradeService;
import com.ylxx.fx.service.po.jsh.JhfxsaInfoin;
import com.ylxx.fx.service.po.jsh.JshPages;
import com.ylxx.fx.service.po.jsh.TbTrd_safeAccinfo;
import com.ylxx.fx.service.po.jsh.Trd_errorstate;
import com.ylxx.fx.service.po.jsh.Trd_tranlist;
import com.ylxx.fx.service.po.jsh.WgCountry;
import com.ylxx.fx.utils.PoiExcelExport;
/**
 * 外管国别管理
 * @author lz130
 *
 */
@Controller
public class JshTradeController {
	private static final Logger log = LoggerFactory.getLogger(JshTradeController.class);
	
	@Resource(name="jshTradeService")
	private JshTradeService jshTradeService;
	/**
	 * 查询国别
	 * @param jshpage
	 * @return
	 */
	@RequestMapping(value="/selectcout.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String selectcout(@RequestBody JshPages<WgCountry> jshpage) {
		log.info("当前页："+jshpage.getPageNo());
		log.info("每页条数："+jshpage.getPageSize());
		return jshTradeService.selectcout(jshpage.getPageNo(), jshpage.getPageSize(),jshpage.getEntity().getName());
	}
	/**
	 * 修改国别
	 * @param wgCountry
	 * @return
	 */
	@RequestMapping(value="/updatecout.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String updatecout(@RequestBody JshPages<WgCountry> wgCountry) {
		return jshTradeService.updatecout(wgCountry);
	}
	/**
	 * 添加国别
	 * @param wgCountry
	 * @return
	 */
	@RequestMapping(value="/insetrcout.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String insetrcout(@RequestBody JshPages<WgCountry> wgCountry) {
		return jshTradeService.insetrcout(wgCountry);
	}
	/**
	 * 删除国别
	 * @param wgCountry
	 * @return
	 */
	@RequestMapping(value="/deletecout.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String deletecout(@RequestBody JshPages<WgCountry> wgCountry) {
		return jshTradeService.deletecout(wgCountry);
	}
	/*=============================告警管理==================================*/
	/**
	 * 分页
	 * 查询告警表
	 * @param pageNo
	 * @param pageSize
	 * @param trdt
	 * @param status
	 * @return
	 */
	@RequestMapping(value="/selecterror.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String selecterror(int pageNo, int pageSize, String trdt, String lcno) {
		log.info("第 "+pageNo+" 页，每页共："+ pageSize+"条");
		Integer pageNos = pageNo;
		Integer pageSizes = pageSize;
		return jshTradeService.selecterror(pageNos, pageSizes, trdt, lcno);
	}
	/**
	 * 导出Excel
	 * @param Trd_errorstate 
	 * @param trdt
	 * @param lcno
	 * @param tableName
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@RequestMapping(value = "/wgErrorListExcel.do")
    public void wgErrorListExcel(JshPages<Trd_errorstate> Trd_errorstate, String trdt, String lcno, String tableName, HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		log.info("导出Excel");
		List<Trd_errorstate> list = jshTradeService.getAllWgErrorList(trdt, lcno);
        PoiExcelExport<Trd_errorstate> pee = new PoiExcelExport<Trd_errorstate>(tableName, Trd_errorstate.getTableList(), list, resp);
        pee.exportExcel();
    }
	/**
	 * 删除告警表数据
	 * @param originatechannelserialno
	 * @return
	 */
	@RequestMapping(value="/deleteerror.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String deleteerror(@RequestBody JshPages<WgCountry> wgCountry) {
		return jshTradeService.deleteerror(wgCountry);
	}
	/**
	 * 查询流水表
	 * @return
	 */
	@RequestMapping(value="/selecttranlist.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	 public String selecttranlist(HttpServletRequest req, int pageNo, int pageSize,String ercd,String cuno,String trsn,String trdt) {
		//@RequestBody JshPages<JshTrdTranList> jshTranList
		/*Integer pageNo=100;
		Integer pageSize=200;*/
		/*log.info("当前页："+jshTranList.getPageNo());
		log.info("每页条数："+jshTranList.getPageSize());*/
		//时间，日期，客户号，错误码
		log.info("第 "+pageNo+" 页，每页共："+ pageSize+"条");
		Integer pageNos = pageNo;
		Integer pageSizes = pageSize;
		return jshTradeService.selecttranlist( pageNos,  pageSizes, ercd, cuno, trsn,trdt);
	}
	/**
	 * 流水表
	 * 导出Excel
	 */
	@RequestMapping(value = "/wgTranListExcel.do")
    public void wgTranListExcel(String tableName,String ercd,String cuno,String trsn,String trdt, JshPages<Trd_tranlist> jshTranList, HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		log.info("导出Excel");
		List<Trd_tranlist> list = jshTradeService.getAllWgTranList(ercd, cuno, trsn, trdt);
        PoiExcelExport<Trd_tranlist> pee = new PoiExcelExport<Trd_tranlist>(tableName, jshTranList.getTableList(), list, resp);
        pee.exportExcel();
    }
	/**
	 * 购汇调用外管冲销
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value="/forfxsaInfoin.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	 public String ForfxsaInfoin(@RequestBody JhfxsaInfoin JhfxsaInfoin) {
	/*	log.info("当前页："+jshTranList.getPageNo());
		log.info("每页条数："+jshTranList.getPageSize());*/
		//时间，日期，客户号，错误码  @RequestBody ForfxsaInfoin forfxsaInfoin
		ForfxsaInfoin forfxsaInfoin=new ForfxsaInfoin();
		//forfxsaInfoin.setBANK_SELF_NUM("51700201705115000001512975721122");
		/*forfxsaInfoin.setCOMMON_ORG_CODE("110108001801");
		forfxsaInfoin.setCOMMON_USER_CODE("NET_003");
		forfxsaInfoin.setPASSWORD("777777");*/
		forfxsaInfoin.setBANK_SELF_NUM(JhfxsaInfoin.getBank_self_num());	
		forfxsaInfoin.setCANCEL_REASON(JhfxsaInfoin.getCancel_reason());
		forfxsaInfoin.setCANCEL_REMARK(JhfxsaInfoin.getCancel_remark());
		forfxsaInfoin.setCOMMON_ORG_CODE(JhfxsaInfoin.getCommon_org_code());
		forfxsaInfoin.setCOMMON_USER_CODE(JhfxsaInfoin.getCommon_user_code());
		forfxsaInfoin.setPASSWORD(JhfxsaInfoin.getPassword());
		//forfxsaInfoin.setREFNO("4");
		String msg="";
//		try {
			msg = jshTradeService.forfxsaInfoin(forfxsaInfoin);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//			System.out.println("msg=" + msg);
//		}finally{
			return msg;
//		}
	}
	
	/**
	 * 更新流水表中的记录状态及错误码，告警表的处理标记
	 * @param trsn
	 * @return  
	 */
	@RequestMapping(value="/upErrorTranlist.do",produces="application/json;charset=UTF-8")
	@ResponseBody  
	public String upErrorTranlist(@RequestBody String trsn){
		//String trsn="31301201512290002163777313000006";
		return jshTradeService.upErrorTranlist(trsn);
	}
	
	/**
	 * 查询柜员号、密码
	 * @param accInfo
	 * @return  
	 */
	@RequestMapping(value="/getLoginOgcd.do",produces="application/json;charset=UTF-8")
	@ResponseBody   
	public String getLoginOgcd(@RequestBody TbTrd_safeAccinfo accInfo){
		/*String bhid = "0214";
		String chnl = "1103";*/
		return jshTradeService.getLoginOgcd(accInfo.getBhid(), accInfo.getChnl());
	}
}
