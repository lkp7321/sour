package com.ylxx.fx.controller.accumu.businesspara;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.fx.core.domain.AccumuBusinessParaVo;
import com.ylxx.fx.service.accumu.businesspara.IAccumuTransService;
/**
 * 积存金
 * -交易流水查询-
 * @author lz130
 *
 */
@Controller
//@RequestMapping("fx")
public class AccumuTransController {
	
//	private static final Logger log = LoggerFactory.getLogger(AccumuTransController.class);
//	
//	@Resource(name="accumuTransService")
//	private IAccumuTransService accumutransService;
//	
//	//查询买币种
//	@RequestMapping(value="/selbyexnm.do",produces="application/json;charset=UTF-8")
//	@ResponseBody
//	public String selbyexnm(@RequestBody String prod) throws Exception {
//		log.info("交易流水查询:");
//	//	String prod="P003";
//		return accumutransService.selbyexnm(prod);	
//	}
//	
//	//查询交易码
//		@RequestMapping(value="/querytrancode.do",produces="application/json;charset=UTF-8")
//		@ResponseBody
//		public String querytrancode() throws Exception {
//		   //String prod="P003";
//			return accumutransService.querytrancode();
//		}
//		
//	//为第一个机构下拉框赋值
//			@RequestMapping(value="/queryOneOrgan.do",produces="application/json;charset=UTF-8")
//			@ResponseBody
//			public String queryOneOrgan() throws Exception {
//			  // String prod="P003";
//				return accumutransService.queryOneOrgan();
//			}
//		//为第二个机构下拉框赋值
//		@RequestMapping(value="/queryTwoOrgan.do",produces="application/json;charset=UTF-8")
//		@ResponseBody
//		public String queryTwoOrgan(@RequestBody String comaogcd) throws Exception {
//		  // String prod="P003";
//			//String comaogcd="6900";
//			return accumutransService.queryTwoOrgan(comaogcd);
//		}
//		
//		//条件获得对应的数据
//			@RequestMapping(value="/selectAccumuTranlist.do",produces="application/json;charset=UTF-8")
//			@ResponseBody
//			public String selectAccumuTranlist(@RequestBody AccumuBusinessParaVo AccumuBusinessParaVo) throws Exception {
//			  
//			/*	AccumuBusinessParaVo AccumuBusinessParaVo=new AccumuBusinessParaVo();
//				AccumuBusinessParaVo.setComdata1("1");
//				AccumuBusinessParaVo.setComdata3("");
//				AccumuBusinessParaVo.setStrcuac("");
//				AccumuBusinessParaVo.setTrdtbegin("");
//				AccumuBusinessParaVo.setTrdtend("");
//				AccumuBusinessParaVo.setByexnm("");
//				AccumuBusinessParaVo.setComaogcd("");
//				AccumuBusinessParaVo.setCombogcd("");
//				AccumuBusinessParaVo.setPageNo(10);
//				AccumuBusinessParaVo.setPageSize(20);*/
//				
//		
//				return accumutransService.selectAccumuTranlist(AccumuBusinessParaVo.getComdata1(), AccumuBusinessParaVo.getComdata3(),AccumuBusinessParaVo.getStrcuac(), AccumuBusinessParaVo.getTrdtbegin(), AccumuBusinessParaVo.getTrdtend(), AccumuBusinessParaVo.getByexnm(),AccumuBusinessParaVo.getComaogcd(), AccumuBusinessParaVo.getCombogcd(),AccumuBusinessParaVo.getPageNo(),AccumuBusinessParaVo.getPageSize());
//			}
	
			
}
