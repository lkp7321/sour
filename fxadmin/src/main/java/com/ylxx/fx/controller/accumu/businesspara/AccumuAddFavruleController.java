/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ylxx.fx.controller.accumu.businesspara;

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
import com.ylxx.fx.core.domain.GetValueListVo;
import com.ylxx.fx.service.accumu.businesspara.IAccumuAddFavruleService;
import com.ylxx.fx.utils.CurrUser;
import com.ylxx.fx.utils.LoginUsers;
import com.ylxx.fx.utils.PoiExcelExport;
import com.ylxx.fx.utils.Table;
/**
 * 电商积存金
 * （添加优惠规则）
 * @author lz130
 *
 */
@Controller
public class AccumuAddFavruleController {

	private static final Logger log = LoggerFactory.getLogger(AccumuAddFavruleController.class);
	
	@Resource(name="AccumuAddFavruleService")
	private IAccumuAddFavruleService accumuAddFavruleService;
	
	/**
	 * 获取下拉列表
	 * @param userKey
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addFavrule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addFavrule(@RequestBody String userKey) throws Exception {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(userKey);
		return accumuAddFavruleService.getBoxList();	
	}
	/**
	 * 查询
	 * @param getValueListVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getValueList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getValueList(@RequestBody GetValueListVo getValueListVo ) throws Exception{
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(getValueListVo.getUserKey());
		return accumuAddFavruleService.getValueList(getValueListVo.getOrnm(), getValueListVo.getPageNo(), getValueListVo.getPageSize());
	}
	/*//添加区间
	@RequestMapping(value="/getBeValueList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public AddFavruleBean getBeValueList(@RequestBody GetValueListVo getValueListVo ) {
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(getValueListVo.getUserKey());
		return accumuAddFavruleService.getBeValueList(getValueListVo.getLable(), getValueListVo.getSmall(), getValueListVo.getData(), getValueListVo.getBig());
	}*/
	//添加非区间的优惠（保存按钮）
	@RequestMapping(value="/insertFavrule.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String insertFavrule(@RequestBody GetValueListVo getValueListVo)throws Exception
	{
		/*List<AddFavruleBean> lists=new ArrayList<AddFavruleBean>();
		AddFavruleBean addFavruleBean=new AddFavruleBean();
		addFavruleBean.setMYDATA("111");
		addFavruleBean.setMYLABLE("between");
		addFavruleBean.setMYVALUE("333");
	    lists.add(addFavruleBean);*/
	
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(getValueListVo.getUserKey());
		return accumuAddFavruleService.insertFavrule(getValueListVo.getIdog(), getValueListVo.getYhbm(), 
				getValueListVo.getYhmc(), getValueListVo.getDefau(), getValueListVo.getBian(), 
				getValueListVo.getCon(), getValueListVo.getMaxyh());
	}
	
	/**
	 * 删除优惠规则
	 * @param getValueListVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getDeleteList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getDeleteList(@RequestBody GetValueListVo getValueListVo)throws Exception
	{
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(getValueListVo.getUserKey());
		return accumuAddFavruleService.getDeleteList(getValueListVo.getIdog(), getValueListVo.getFvid());
	}
	//修改规则里的保存
	@RequestMapping(value="/doUpdateFav.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String doUpdateFav(@RequestBody GetValueListVo getValueListVo)throws Exception{
		CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(getValueListVo.getUserKey());
		return accumuAddFavruleService.doUpdateFav(getValueListVo.getIdog(), getValueListVo.getYhbm(), getValueListVo.getTiao(),
				getValueListVo.getYhmc(), getValueListVo.getBian(), getValueListVo.getDefau(), getValueListVo.getMaxyh(), getValueListVo.getCon());
	
	}
	//修改规则获取条件列表
		@RequestMapping(value="/getLableList.do",produces="application/json;charset=UTF-8")
		@ResponseBody
		public String  getLableList(@RequestBody GetValueListVo getValueListVo )throws Exception{
			CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(getValueListVo.getUserKey());
			return accumuAddFavruleService.LableList(getValueListVo.getIdog(), getValueListVo.getFvid());
		}
		//修改规则初始化数据
				@RequestMapping(value="/getFavruleList.do",produces="application/json;charset=UTF-8")
				@ResponseBody
				public String  getFavruleList(@RequestBody GetValueListVo getValueListVo )throws Exception{
					CurrUser curUser = LoginUsers.getLoginUser().getCurrUser(getValueListVo.getUserKey());
					return accumuAddFavruleService.FavruleList(getValueListVo.getIdog(), getValueListVo.getFvid());
				}
	/**
	 * 查询电商商户号 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/queryShnoInfo.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String queryShnoInfo()throws Exception {
		return accumuAddFavruleService.queryShnoInfo();
	}
	//电商积存金查询 
	@RequestMapping(value="/getTransferTotal.do",produces="application/json;charset=UTF-8")
	@ResponseBody
			public String getTransferTotal(@RequestBody GetValueListVo getValueListVo) {
				return accumuAddFavruleService.getTransferTotal(getValueListVo.getTrdtbegin(), getValueListVo.getTrdtend(),getValueListVo.getShno(),getValueListVo.getDirc());
			}
	/**
	 * 金生金查询
	 * @param getValueListVo
	 * @return
	 */
	@RequestMapping(value="/selectGold.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectGold(HttpServletRequest req, @RequestBody GetValueListVo getValueListVo) {
		log.info("开始金生金查询");
		return accumuAddFavruleService.selectGold(
				getValueListVo.getPageNo(),getValueListVo.getPageSize(),getValueListVo.getData());
	}
	
	/**
	 * 金生金导出Excel
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	@RequestMapping(value="/selectGoldExcel.do")
	public void selectGoldExcel(HttpServletRequest req, HttpServletResponse resp, GetValueListVo getValueListVo) throws IllegalArgumentException, IllegalAccessException, IOException {
		String fileName = getValueListVo.getTableName();//前台传的  表名
        List<Table> tableList = getValueListVo.getTableList();//前台传的  表头，及Key
        List<Map<String, Object>> list = accumuAddFavruleService.selectAllGold(getValueListVo.getData());
        PoiExcelExport<Map<String, Object>> pee = new PoiExcelExport<Map<String, Object>>(fileName, tableList, list, resp);
        pee.exportExcel();
	}
	/**
	 * 金生金 数量查询
	 */
	@RequestMapping(value="/selectSumcblv.do",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String selectSumcblv(HttpServletRequest req, String data) {
		return accumuAddFavruleService.selectSumcblv(data);
	}
	/**
	 * 电商积存金查询
	 * @param getValueListVo
	 * @return
	 */
	@RequestMapping(value="/selectTranfer.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String selectTranfer(@RequestBody GetValueListVo getValueListVo) {
		//@RequestBody GetValueListVo getValueListVo
		/*GetValueListVo getValueListVo=new GetValueListVo();
		getValueListVo.setPageNo(1);
		getValueListVo.setPageSize(10);*/
		return accumuAddFavruleService.selectTranfer(getValueListVo.getTrdtbegin(),getValueListVo.getTrdtend(),getValueListVo.getShno(),getValueListVo.getDirc(),getValueListVo.getPageNo(),getValueListVo.getPageSize());
		//return accumuAddFavruleService.selectTranfer("20160202","20160303","all","2",1,10);
	}

	//电商积存金手动中止	
	@RequestMapping(value="/stopOrStrat.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String stopOrStrat(@RequestBody GetValueListVo getValueListVo) {
		//@RequestBody GetValueListVo getValueListVo
		
			return 	accumuAddFavruleService.stopOrStrat(getValueListVo.getShno(), getValueListVo.getFldt(), getValueListVo.getMafl());
		//return 	accumuAddFavruleService.stopOrStrat("B001", "20160225", "0-未手工中止");
	}
	//初始化页面后获取该机构的目前的优惠
	@RequestMapping(value="/getSearchInitList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getSearchList(@RequestBody String ogcd) {
		//@RequestBody GetValueListVo getValueListVo
		
			//return 	accumuAddFavruleService.stopOrStrat(getValueListVo.getShno(), getValueListVo.getFldt(), getValueListVo.getMafl());
		return 	accumuAddFavruleService.getSearchList(ogcd);
	}
	
	//初始化获得条件列表
	@RequestMapping(value="/getinitList.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getinitList(@RequestBody String ogcd) {
		//@RequestBody String ogcd
				
			//return 	accumuAddFavruleService.stopOrStrat(getValueListVo.getShno(), getValueListVo.getFldt(), getValueListVo.getMafl());
		return 	accumuAddFavruleService.getinitList(ogcd);
	}
}
