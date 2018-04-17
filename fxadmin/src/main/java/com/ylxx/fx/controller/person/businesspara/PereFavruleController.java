package com.ylxx.fx.controller.person.businesspara;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.ylxx.fx.core.domain.BusinessVo;
import com.ylxx.fx.service.person.businesspara.PereFavruleService;
import com.ylxx.fx.service.po.FavourRule;
import com.ylxx.fx.service.po.Favrule;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.service.po.Trd_Favrule;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.Table;
/**
 * 个人结售汇-P004
 * 分行优惠规则
 * @author lz130
 *
 */
@Controller
public class PereFavruleController {
	@Resource(name="perefavruleService")
	private PereFavruleService perefavruleService;
	/**
	 * 规则启用
	 * userKey,chlist
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="pere/openChannel.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String pereOpenChannel(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		List<Trd_Favrule> chlist = bsVo.getChlist();
		boolean flag = perefavruleService.openChannel(userKey, chlist);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "开启成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "开启失败");
		}
	}
	/**
	 * 规则停用
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="pere/closeChannel.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String perecloseChannel(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		List<Trd_Favrule> chlist = bsVo.getChlist();
		boolean flag = perefavruleService.closeChannel(userKey, chlist);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "停用成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "停用失败");
		}
	}
	/**
	 * 分行优惠规则查询
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="pere/selfavrule.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String pereSelfavrule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String ogcd = bsVo.getOgcd();
		Integer pageNo = bsVo.getPageNo();
		Integer pageSize = bsVo.getPageSize();
		PageInfo<Favrule> page = perefavruleService.getPereFenfavrule(ogcd,
				pageNo, pageSize);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	/**
	 * 分行优惠查询 导出Excel
	 * @param req
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	@RequestMapping(value="pere/selfavruleExcel.do")
	public void selfavruleExcel(HttpServletRequest req, BusinessVo bsVo, HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException{
		String fileName = bsVo.getTableName();//前台传的  表名
        List<Table> tableList = bsVo.getTableList();//前台传的  表头，及Key
        String ogcd = bsVo.getOgcd();
		List<Favrule> list = perefavruleService.getAllPereFenfavrule(ogcd);
        PoiExcelExport<Favrule> pee = new PoiExcelExport<Favrule>(fileName, tableList, list, resp);
        pee.exportExcel();
	}
	
	/**
	 * 初始化修改页面
	 * @param req
	 * @return
	 */
	@RequestMapping(value="pere/initfavrule.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String pereInitfavrule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String rule = bsVo.getRule();
		String fvid = bsVo.getFvid();
		List<FavourRule> list = perefavruleService.onSelFavrule(rule, fvid);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	/**
	 * 初始化客户优惠
	 * @param req
	 * @return
	 */
	@RequestMapping(value="pere/initCustfavrule.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String pereInitCustfavrule(HttpServletRequest req){
		List<Map<String, Object>> list = perefavruleService.custboxData();
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
	}
	/**
	 * 修改操作
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="pere/savefavrule.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	//favourList,userkey,fvid,ogcd
	public String pereSavefavrule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		List<FavourRule> favourList = bsVo.getFavourList();
		String fvid = bsVo.getFvid();
		String ogcd = bsVo.getOgcd();
		boolean flag = perefavruleService.saveFavruleRule(userKey, favourList, fvid, ogcd);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "保存优惠规则成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "保存优惠规则失败");
		}
	}
	/**
	 * 撤销与修改流水（功能）
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="pere/selRemoveRule.do", produces="application/json;charset=UTF-8")
	@ResponseBody
	//userkey,trdtbegin,trdtend,pageNo,pageSize,
	public String selRemoveRule(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		String start = bsVo.getTrdtbegin();
		String end = bsVo.getTrdtend();
		Integer pageNo = bsVo.getPageNo();
		Integer pageSize = bsVo.getPageSize();
		PageInfo<Tranlist> page = perefavruleService.pageTranlist(userKey, start, end, pageNo, pageSize);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}
	/**
	 * 撤销与修改流水导出Excel
	 * @param bsVo
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@RequestMapping(value = "/pere/selRemoveOutputexcel.do")
    public void showImgCodePreodr(BusinessVo bsVo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = bsVo.getTableName();//前台传的  表名
        List<Table> tableList = bsVo.getTableList();//前台传的  表头，及Key
		String userKey = bsVo.getUserKey();
		String start = bsVo.getTrdtbegin();
		String end = bsVo.getTrdtend();
		//这里是我自己查询数据的过程
        List<Tranlist> list = perefavruleService.selectTranlist(userKey, start, end);
        PoiExcelExport<Tranlist> pee = new PoiExcelExport<Tranlist>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
}
