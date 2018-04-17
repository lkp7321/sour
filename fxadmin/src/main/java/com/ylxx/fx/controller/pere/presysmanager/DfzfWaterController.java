package com.ylxx.fx.controller.pere.presysmanager;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.CustFavVo;
import com.ylxx.fx.service.pere.presysmanager.IDfzfWaterService;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.Table;
@Controller
//@RequestMapping("fx")
@Scope("prototype")
public class DfzfWaterController {
	@Resource(name="dfzfWaterService")
	private IDfzfWaterService dfzfWaterService;
	
	 /**
	  * 根据条件查询东方支付流水
	  * @param dgfieldbegin pageNo 
	  * @param dgfieldend pageSize
	  * @param comStcd
	  * @return List<Tranlist> regList
	  */
	@RequestMapping(value="/selcDfzfWater.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryDfzfWater(HttpServletRequest req, @RequestBody CustFavVo custFav){
		return dfzfWaterService.selcDfzfWater(custFav.getDgfieldbegin(),custFav.getDgfieldend(),custFav.getComStcd(),custFav.getPageNo(),custFav.getPageSize());
	}
	
	/**
	 * 导出Excel
	 * @throws IOException 
	 */
	@RequestMapping(value = "/selcDfzfWaterEexcel.do")
    public void selcDfzfWaterEexcel(CustFavVo custFav,HttpServletRequest req, 
             HttpServletResponse resp) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = custFav.getTableName();//前台传的  表名
        List<Table> tableList = custFav.getTableList();//前台传的  表头，及Key
        List<CustFavVo> list = dfzfWaterService.selcAllDfzfWater(custFav.getDgfieldbegin(),custFav.getDgfieldend(),custFav.getComStcd());
        PoiExcelExport<CustFavVo> pee = new PoiExcelExport<CustFavVo>(fileName, tableList, list, resp);
        pee.exportExcel();
    }
}
