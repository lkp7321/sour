package com.ylxx.fx.controller.pere.presysmanager;

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
import com.ylxx.fx.core.domain.ElecTellerManagerVo;
import com.ylxx.fx.core.domain.ElecTellerManagerSelectVo;
import com.ylxx.fx.service.pere.presysmanager.IElecTellerManagerService;
import com.ylxx.fx.service.po.ElecTellerManager;
import com.ylxx.fx.utils.ErrCodeBusiness;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.ResultDomain;
import com.ylxx.fx.utils.Table;

@Controller
//@RequestMapping("fx")
public class ElecTellerManagerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ElecTellerManagerController.class);
	
	@Resource(name="iElecTellerManagerService")
	private IElecTellerManagerService iElecTellerManagerService;
	
	//查询国别代码对照表
	@RequestMapping(value="/selcTellerList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selcTellerList(HttpServletRequest req,@RequestBody ElecTellerManagerSelectVo EtmsVo) throws Exception {
	   Integer pageNo = EtmsVo.getPageNo();
	   Integer pageSize = EtmsVo.getPageSize();
	   PageInfo<ElecTellerManager> page = iElecTellerManagerService.pageSelcAllTellerList(pageNo, pageSize);
	   return ResultDomain.getRtnMsg(ErrCodeBusiness.busines_00.getCode(), page);	
	}
	//导出
	@RequestMapping(value = "/selcTellerListExcel.do")
    public void showImgCode(ElecTellerManagerSelectVo EtmsVo,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = EtmsVo.getTableName();//前台传的  表名
        List<Table> tableList = EtmsVo.getTableList();//前台传的  表头，及Key
 	   	List<ElecTellerManager> list = iElecTellerManagerService.selcAllTellerList();
        PoiExcelExport<ElecTellerManager> pee = new PoiExcelExport<ElecTellerManager>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
	
	//条件查询电子柜员信息列表
	@RequestMapping(value="/selcPreMessage.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	//@RequestBody ElecTellerManagerSelectVo EtmsVo
	public String selcPreMessage(@RequestBody ElecTellerManagerSelectVo EtmsVo) throws Exception{
		/*ElecTellerManagerSelectVo EtmsVo=new ElecTellerManagerSelectVo();
		EtmsVo.setComdata1("");
		EtmsVo.setBhidp("");
		EtmsVo.setTrtltxt("");
		EtmsVo.setPageNo(null);
		EtmsVo.setPageSize(null);*/
		return iElecTellerManagerService.selcPreMessage(EtmsVo.getBhidp(),EtmsVo.getComdata1(),
				EtmsVo.getTrtltxt(),EtmsVo.getPageNo(),EtmsVo.getPageSize());
	}
	
	//删除柜员信息
	@RequestMapping(value="/deleteEleTeller.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	//@RequestBody ElecTellerManagerSelectVo EtmsVo
	public String deleteEleTeller(@RequestBody ElecTellerManagerSelectVo EtmsVo)throws Exception{
		/*ElecTellerManagerSelectVo EtmsVo = new ElecTellerManagerSelectVo();
		EtmsVo.setUserKey(null);
		EtmsVo.setTellerid("00000006");*/
		return iElecTellerManagerService.deleteEleTeller(EtmsVo.getUserKey(),EtmsVo.getTellerid());
		
	}
	
	@RequestMapping(value="/updateEleT.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	//@RequestBody ElecTellerManagerVo etmVo
	public String updateEleT(@RequestBody ElecTellerManagerVo etmVo) throws Exception{
		/*ElecTellerManagerVo etmVo=new ElecTellerManagerVo();
		etmVo.setUserKey(null);
		etmVo.setPass("888888");
		etmVo.setOgcd("310101001801");
		etmVo.setTltp("0");//
		etmVo.setBhid("0207");
		etmVo.setChnl("1105");
		etmVo.setTrtl("AUTO_002");
		etmVo.setTellerid("00000007");*/
		return iElecTellerManagerService.updateEleT(etmVo.getUserKey(),etmVo);
		
	}
	
	@RequestMapping(value="/isTrtlBhidExist.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	//@RequestBody ElecTellerManagerSelectVo EtmsVo
	public String isTrtlBhidExist(@RequestBody ElecTellerManagerSelectVo EtmsVo) throws Exception{
		/*ElecTellerManagerSelectVo EtmsVo=new ElecTellerManagerSelectVo();
		EtmsVo.setComdata1("");
		EtmsVo.setBhidp("");
		EtmsVo.setTrtltxt("");
		EtmsVo.setPageNo(null);
		EtmsVo.setPageSize(null);*/
		/*ElecTellerManagerSelectVo EtmsVo=new ElecTellerManagerSelectVo();
		EtmsVo.setUserKey(null);
		EtmsVo.setBhid("0207");
		EtmsVo.setTrtl("AUTO_002");*/
		return iElecTellerManagerService.isTrtlBhidExist(EtmsVo.getUserKey(),EtmsVo.getBhid(),EtmsVo.getTrtl());
	}
	

	//初始化外管局机构
	@RequestMapping(value="/selectOgcd.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectOgcd() throws Exception {
	   
		return iElecTellerManagerService.selectOgcd();
	}
	//用户添加机构框
	@RequestMapping(value="/selectTOgcd.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	//@RequestBody ElecTellerManagerSelectVo EtmsVo
	public String selectTOgcd(@RequestBody ElecTellerManagerSelectVo EtmsVo) throws Exception{
		/*ElecTellerManagerSelectVo EtmsVo=new ElecTellerManagerSelectVo();
		EtmsVo.setOrgn("0001");
		EtmsVo.setUsnm("Administrator");
		EtmsVo.setPageNo(null);
		EtmsVo.setPageSize(null);*/
		return iElecTellerManagerService.selectTOgcd(EtmsVo.getOrgn(),
				EtmsVo.getUsnm(),EtmsVo.getPageNo(),EtmsVo.getPageSize());
	}
	
	
	//用户添加机构框
		@RequestMapping(value="/selectChnlP.do",produces="application/json;charset=UTF-8")
		@ResponseBody
		public String selectChnlP() throws Exception{
			return iElecTellerManagerService.selectChnlP();
			
		}
		
		
		
		@RequestMapping(value="/insertEleT.do",produces="application/json;charset=UTF-8")
		@ResponseBody
		//@RequestBody ElecTellerManagerVo etmVo
		public String insertEleT(@RequestBody ElecTellerManagerVo etmVo) throws Exception{
			/*ElecTellerManagerVo etmVo=new ElecTellerManagerVo();
			etmVo.setUserKey(null);
			etmVo.setPass("222222");
			etmVo.setOgcd("310101001822");
			etmVo.setTltp("2");//
			etmVo.setBhid("2222");
			etmVo.setChnl("2222");
			etmVo.setTrtl("AUTO_222");*/
			return iElecTellerManagerService.insertEleT(etmVo.getUserKey(),etmVo);
			
		}
	
}
