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
import com.ylxx.fx.service.person.businesspara.MaxpavpointService;
import com.ylxx.fx.service.person.system.OrganizationService;
import com.ylxx.fx.service.po.Maxpavpoint;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.Table;
/**
 * P001,P002,P003,P004
 * 最大优惠规则设置
 * @author lz130
 *
 */
@Controller
public class MaxpavpointController {
	private static final Logger log = LoggerFactory.getLogger(MaxpavpointController.class);
	@Resource(name="maxpavpointService")
	private MaxpavpointService maxpavpointService;
	@Resource(name="organService")
	private OrganizationService organService;
	/**
	 * 最大优惠规则数据查询
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/getMaxpavpoint.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getMaxpavpoint(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		log.info("获取最大优惠规则数据：");
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String combogcd = bsVo.getCombogcd();//机构编号
		Integer pageNo = bsVo.getPageNo();
		Integer pageSize = bsVo.getPageSize();
		if(combogcd.equals("0001")){
			combogcd = "all";
		}
		PageInfo<Maxpavpoint> page= maxpavpointService.selPageMaxpavpoint(curUser, combogcd, pageNo, pageSize);
		return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);
	}	
	/**
	 * 获取最大优惠，获取分行优惠设置
	 * 机构下拉框
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/queryMaxpavpoint.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	//userKey, levelTy（等级）
	public String queryMaxpavpoint(HttpServletRequest req, @RequestBody BusinessVo bsVo){
		String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String prod = curUser.getProd();
		String userorgleve = bsVo.getLevelTy();
		List<Map<String, String>> list = maxpavpointService.queryMaxpoint(prod,
				curUser.getOrgn(), userorgleve);
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_02.getCode(), "机构获取失败");
		}
	}
	/**
	 * 获取添加时 的币别对
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/selMaxExnm.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selMaxExnm(HttpServletRequest req,@RequestBody BusinessVo bsVo){
		String ogcd = bsVo.getOgcd();
		String userKey = bsVo.getUserKey();
		List<Map<String,String>> list = maxpavpointService.getMaxExnm(userKey, ogcd);
		if(list!=null && list.size()>0){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), list);
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "获取添加币别失败");
		}
	}
	/**
	 * 添加操作
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/addMaxPoint.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addMaxExnm(HttpServletRequest req,@RequestBody BusinessVo bsVo){
		Maxpavpoint maxpoint = bsVo.getMaxpoint();
		String userKey = bsVo.getUserKey();
		boolean flag = maxpavpointService.adMaxpoint(userKey, maxpoint);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "添加最大优惠设置成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "添加最大优惠设置失败");
		}
		
	}
	/**
	 * 修改操作
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/upMaxPoint.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String upMaxExnm(HttpServletRequest req,@RequestBody BusinessVo bsVo){
		Maxpavpoint maxpoint = bsVo.getMaxpoint();
		String userKey = bsVo.getUserKey();
		boolean flag = maxpavpointService.upMaxpoint(userKey, maxpoint);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "最大优惠规则修改成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "最大优惠规则修改失败");
		}
		
	}
	/**
	 * 删除操作
	 * @param req
	 * @param bsVo
	 * @return
	 */
	@RequestMapping(value="/deMaxPoint.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String deMaxExnm(HttpServletRequest req,@RequestBody BusinessVo bsVo){
		Maxpavpoint maxpoint = bsVo.getMaxpoint();
		String userKey = bsVo.getUserKey();
		boolean flag = maxpavpointService.deMaxpoint(userKey, maxpoint);
		if(flag){
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), "删除最大优惠规则成功");
		}else{
			return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_01.getCode(), "删除最大优惠规则失败");
		}
	}
	/**
	 * 优惠规则导出
	 * @param bsVo
	 * @param req
	 * @param resp
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 */
	@RequestMapping(value = "/FenPointOutputexcel.do")
    public void showImgCodeFenpoint(BusinessVo bsVo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = bsVo.getTableName();//前台传的  表名
        List<Table> tableList = bsVo.getTableList();//前台传的  表头，及Key
        String userKey = bsVo.getUserKey();
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		String combogcd = bsVo.getCombogcd();//机构编号
		if(combogcd.equals("0001")){
			combogcd = "all";
		}
		List<Maxpavpoint> list= maxpavpointService.selMaxpavpoint(curUser, combogcd);
        PoiExcelExport<Maxpavpoint> pee = new PoiExcelExport<Maxpavpoint>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
}
