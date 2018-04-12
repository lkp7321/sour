package com.ylxx.qt.controller;
/**
 * @author LiangYongJian
 * @Date 2017 2017年12月27日 下午13:05:33
 */

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ylxx.qt.service.IOverMonitorService;
import com.ylxx.qt.service.IPriceSheetService;
import com.ylxx.qt.service.ISegmentationSetService;
import com.ylxx.qt.service.SocketNowPriceService;
import com.ylxx.qt.service.po.GlobaVlariables;
import com.ylxx.qt.service.po.InstrumentFieldBean;
import com.ylxx.qt.service.po.InstrumentPtrice;
import com.ylxx.qt.service.po.ParameterList;
import com.ylxx.qt.service.po.ParameterdetailsBean;
import com.ylxx.qt.service.po.PriceSheet;
import com.ylxx.qt.service.po.PriceSheetList;
import com.ylxx.qt.service.po.ProductBean;



@Controller
@RequestMapping("/market")
public class MarketAnalysisController {

	@Resource
	private IPriceSheetService ipss;
	@Resource
	private IOverMonitorService ios;
	@Resource
	private ISegmentationSetService isss;
	@Resource
	private SocketNowPriceService snps;
	//查询数据
	@RequestMapping(value = "/querypricelogin",produces="application/json;charset=UTF-8")
	public @ResponseBody String getProductPrice(int page,int limit,String proId,String proName,String priceType,Model model,HttpServletRequest request,HttpSession session)throws Exception{
		String proId_1 = null;
		String proName_1 = null;
		String priceType_str = null;
		Integer priceType_1 = -1;
		if (!proId.equals("")) {
			proId_1 = proId;
		}
		if(!proName.equals("")){
			proName_1 = proName;
		} 
		if(!priceType.equals("-1")){
			priceType_1 = Integer.parseInt(priceType);
		}
		Integer counts=ipss.selectCounts(proId_1,proName_1,priceType_1);//总数
		List<PriceSheet> proList = ipss.queryAll(proId_1,proName_1,priceType_1,page,limit);
		DecimalFormat df = new DecimalFormat("0.00");
		String s = "{\"code\":0,\"msg\":\"\",\"count\":"+counts+",\"data\":[";
		if(proList.size() == 0){
			s+="]}";
			return s;
		}
		for (int i=0;i<proList.size();i++) {
			if(proList.get(i).getPricetype() == 1){
				priceType_str = "主连";
			}else{
				priceType_str = "指数";
			}
			if(i<proList.size()-1){
				s += "{\"productid\":\""+proList.get(i).getProductid()+"\",\"productname\":\""+proList.get(i).getProductname()+"\",\"year\":\""+proList.get(i).getYear().toString()+"\",\"pricetype\":\""+priceType_str+"\",\"histortyprice\":"+df.format(proList.get(i).getHistortyprice())+",\"highestprice\":"+df.format(proList.get(i).getHighestprice())+",\"lowestprice\":"+df.format(proList.get(i).getLowestprice())+
						",\"pricetick\":"+df.format(proList.get(i).getPricetick())+"},";
			}else{
				s += "{\"productid\":\""+proList.get(i).getProductid()+"\",\"productname\":\""+proList.get(i).getProductname()+"\",\"year\":\""+proList.get(i).getYear().toString()+"\",\"pricetype\":\""+priceType_str+"\",\"histortyprice\":"+df.format(proList.get(i).getHistortyprice())+",\"highestprice\":"+df.format(proList.get(i).getHighestprice())+",\"lowestprice\":"+df.format(proList.get(i).getLowestprice())+
						",\"pricetick\":"+df.format(proList.get(i).getPricetick())+"}]}";
			}
		}
		model.addAttribute("list",proList);		
		return s;
	}
	
	//根据id查询
	@RequestMapping(value = "/selectpricebyidlogin",produces="application/json;charset=UTF-8")
	public @ResponseBody String selectProPrice(int page,int limit,String proId,Model model) throws Exception{
		Integer counts=ipss.selectCountsAll(proId);//总数
		String priceType_str = null;
		List<PriceSheet> proList = ipss.queryByid(proId,page,limit);
		model.addAttribute("list",proList);
		DecimalFormat df = new DecimalFormat("0.00");
		String s = "{\"code\":0,\"msg\":\"\",\"count\":"+counts+",\"data\":[";
		if(proList.size() == 0){
			s+="]}";
			return s;
		}
		for (int i=0;i<proList.size();i++) {
			if(proList.get(i).getPricetype() == 1){
				priceType_str = "主连";
			}else{
				priceType_str = "指数";
			}
			if(i<proList.size()-1){
				s += "{\"productname\":\""+proList.get(i).getProductname()+"\",\"year\":\""+proList.get(i).getYear().toString()+"\",\"pricetype\":\""+priceType_str+"\",\"histortyprice\":"+df.format(proList.get(i).getHistortyprice())+",\"highestprice\":"+df.format(proList.get(i).getHighestprice())+",\"lowestprice\":"+df.format(proList.get(i).getLowestprice())+
						"},";
			}else{
				s += "{\"productname\":\""+proList.get(i).getProductname()+"\",\"year\":\""+proList.get(i).getYear().toString()+"\",\"pricetype\":\""+priceType_str+"\",\"histortyprice\":"+df.format(proList.get(i).getHistortyprice())+",\"highestprice\":"+df.format(proList.get(i).getHighestprice())+",\"lowestprice\":"+df.format(proList.get(i).getLowestprice())+
						"}]}";
			}
		}
		model.addAttribute("list",proList);	
		return s;
	}
	
	//删除该品种数据
		@RequestMapping("/deleteprice")
		public @ResponseBody String deleteProPrice(String proId) throws Exception{
			ipss.deleteByid(proId);
			return "";
		}
		
	//新增数据
		@RequestMapping("/insertprice")
		public String insertProPrice(@RequestBody PriceSheetList psl) throws Exception{
			ipss.insertPro(psl.getList());
			return "";
		}
		
	//删除年份数据
		@RequestMapping("deletebyyear")
		public String deleteByYear(String proId,Integer year) throws Exception{
			ipss.deleteByYear(proId, year);
			return "";
		}
	//更新数据
		@RequestMapping(value="updatepri",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
		public String updatePrice(@RequestBody PriceSheetList psl) throws Exception{
			List<PriceSheet> list = psl.getList();
			for (PriceSheet priceSheet : list) {
				priceSheet.setProductid(psl.getProId());
				priceSheet.setPricetick(Double.parseDouble(psl.getPricetick()));
			}
			ipss.deleteByid(psl.getProId());
			ipss.insertPro(list);
			return "";
			
		}
		
		/*-----------------超卖超买区监控------------------------*/
		//按每个合约显示
		@RequestMapping(value="queryparamer",produces="application/json;charset=UTF-8")
		public @ResponseBody String queryParamer(int page,int limit,String userId,String proId, HttpServletRequest request) throws Exception{
			Map<String,Map<String,String>> map_old = (Map<String,Map<String,String>>)request.getSession().getAttribute("subinfo");
			Integer counts = 30;
			double nowPrice;
			nowPrice = 4780;//为了测试
			String proId_1 = null;
			String priceType = null;
			String segmenttype = null;
			if(!proId.equals("")){
				proId_1 = proId;
			}
			List<ParameterdetailsBean> list = ios.queryParamet(userId, proId_1,page,limit);
			if(list.size() == 0){//如果该用户没有参数设置，默认显示管理员的
				list = ios.queryParamet("admin", proId_1,page,limit);
			}
			DecimalFormat df = new DecimalFormat("0.00");
			String s = "{\"code\":0,\"msg\":\"\",\"count\":"+counts+",\"data\":[";
			if(list.size() == 0){
				s+="]}";
				return s;
			}
			for (int i=0;i<list.size();i++) {
				List<PriceSheet> list_pricesheet = ipss.queryByid(list.get(i).getProductid());
				Integer priceType_old = 0;
				if(list_pricesheet.size()>0){
					priceType_old = list_pricesheet.get(0).getPricetype();
				}
				
				if(priceType_old == 1){
					priceType = "主连";
				}else{
					priceType = "指数";
				}
				
				String[] constr = list.get(i).getContractset().split(",");
				for(int j=0;j<constr.length;j++){
					if(GlobaVlariables.instruNowPrice.containsKey(constr[j])){
						nowPrice = GlobaVlariables.instruNowPrice.get(constr[j]);
					}else{
						InstrumentPtrice instrumentptrice = ios.selectinstrumentprice(constr[j]);
						if(instrumentptrice == null){//数据库有无这个合约
							nowPrice = 4000;
						}else{
							nowPrice = instrumentptrice.getNowprice();
						}
						
					}
					String belongStall = isss.BelongStalls(nowPrice, map_old.get(constr[j]));//返回所属档位
					String sellAndbuy = list.get(i).getBullandbearratio()/10+":"+list.get(i).getBullandbearratio()%10;//多空比
					if(list.get(i).getSegmenttype()==1){
						segmenttype = "值均";
					}else{
						segmenttype = "均值";
					}
					if(i == list.size()-1 && j == constr.length-1){
						s += "{\"productid\":\""+list.get(i).getProductid()+"\",\"pricetype\":\""+priceType+"\",\"bullandbearratio\":\""+sellAndbuy+"\",\"longsegment\":\""+list.get(i).getLongsegment().toString()+"\",\"shortsegment\":\""+list.get(i).getShortsegment().toString()+"\",\"segmenttype\":\""+segmenttype+"\",\"contractset\":\""+constr[j]+
								"\",\"nowprice\":"+df.format(nowPrice)+",\"belongstalls\":\""+belongStall+"\"}]}";
					}else{
						s += "{\"productid\":\""+list.get(i).getProductid()+"\",\"pricetype\":\""+priceType+"\",\"bullandbearratio\":\""+sellAndbuy+"\",\"longsegment\":\""+list.get(i).getLongsegment().toString()+"\",\"shortsegment\":\""+list.get(i).getShortsegment().toString()+"\",\"segmenttype\":\""+segmenttype+"\",\"contractset\":\""+constr[j]+
								"\",\"nowprice\":"+df.format(nowPrice)+",\"belongstalls\":\""+belongStall+"\"},";
					}
				}
			}	
			return s;
		}
		//参数设置
				@RequestMapping(value = "paramsetquery",produces="application/json;charset=UTF-8")
				public @ResponseBody String queryparamset(int page,int limit,String userId) throws Exception{
					Integer counts = 3;
					String proId_1 = null;
					String priceType = null;
					List<ParameterdetailsBean> list = ios.queryParamet(userId, proId_1,page,limit);
					DecimalFormat df = new DecimalFormat("0.00");
					String s = "{\"code\":0,\"msg\":\"\",\"count\":"+counts+",\"data\":[";
					if(list.size() == 0){
						s+="]}";
						return s;
					}
					for (int i=0;i<list.size();i++) {
						List<PriceSheet> list_pricesheet = ipss.queryByid(list.get(i).getProductid());
						Integer priceType_old = 0;
						if(list_pricesheet.size()>0){
							priceType_old = list_pricesheet.get(0).getPricetype();
						}
						if(priceType_old == 1){
							priceType = "主连";
						}else{
							priceType = "指数";
						}
						String sellAndbuy = list.get(i).getBullandbearratio()/10+":"+list.get(i).getBullandbearratio()%10;//多空比
						if(i < list.size()-1){
							s += "{\"productid\":\""+list.get(i).getProductid()+"\",\"proname\":\""+list.get(i).getProductname()+"\",\"pricetype\":\""+priceType+"\",\"bullandbearratio\":\""+sellAndbuy+"\",\"longsegment\":\""+list.get(i).getLongsegment().toString()+"\",\"shortsegment\":\""+list.get(i).getShortsegment().toString()+"\",\"segmenttype\":\""+list.get(i).getSegmenttype().toString()+"\",\"contractset\":\""+list.get(i).getContractset()+
									"\",\"weisets\":\""+list.get(i).getWeightset()+"\"},";
						}else{
							s += "{\"productid\":\""+list.get(i).getProductid()+"\",\"proname\":\""+list.get(i).getProductname()+"\",\"pricetype\":\""+priceType+"\",\"bullandbearratio\":\""+sellAndbuy+"\",\"longsegment\":\""+list.get(i).getLongsegment().toString()+"\",\"shortsegment\":\""+list.get(i).getShortsegment().toString()+"\",\"segmenttype\":\""+list.get(i).getSegmenttype().toString()+"\",\"contractset\":\""+list.get(i).getContractset()+
									"\",\"weisets\":\""+list.get(i).getWeightset()+"\"}]}";
						}
					}	
					return s;
				}
				
		//查询该品种下的所有合约
		@RequestMapping(value="queryallcontract",produces="text/html;charset=UTF-8")
		public @ResponseBody String querycontract(String proId) throws Exception{
			Integer counts = 10;
			List<InstrumentFieldBean> list = ios.queryInstrument(proId);
			DecimalFormat df = new DecimalFormat("0.00");
			String s = "{\"code\":0,\"msg\":\"\",\"count\":"+counts+",\"data\":[";
			if(list.size() == 0){
				s+="]}";
				return s;
			}
			for (int i=0;i<list.size();i++) {
				if(i < list.size()-1){
					s += "{\"instrumentid\":\""+list.get(i).getInstrument_id()+"\",\"instrumentname\":\""+list.get(i).getInstrumentname()+"\",\"exchangeid\":\""+list.get(i).getExchange_id()+"\",\"proid\":\""+list.get(i).getProductbean().getProductId()+"\",\"protype\":\"期权\"},";
				}else{
					s += "{\"instrumentid\":\""+list.get(i).getInstrument_id()+"\",\"instrumentname\":\""+list.get(i).getInstrumentname()+"\",\"exchangeid\":\""+list.get(i).getExchange_id()+"\",\"proid\":\""+list.get(i).getProductbean().getProductId()+"\",\"protype\":\"期权\"}]}";
				}
			}	
			//System.out.println(s);
			return s;
		}
		//查询该品种权重
				@RequestMapping(value="queryweights",produces="application/json;charset=UTF-8")
				public @ResponseBody String queryWeights(int page,int limit,String userId,String proId) throws Exception{
					DecimalFormat df = new DecimalFormat("0.00");
					String wight_tmp;
					List<ParameterdetailsBean> list = ios.queryParamet(userId, proId,page,limit);
					if(list.size()>0){
						String weistr = list.get(0).getWeightset();
						String[] weistrs = weistr.split(",");
						Integer counts=weistrs.length;//总数
						String s = "{\"code\":0,\"msg\":\"\",\"count\":"+counts+",\"data\":[";
						if(list.size() == 0){
							s+="]}";
							return s;
						}
						for(int i=0;i<weistrs.length;i++){
							if(i<weistrs.length-1){
								s += "{\"productid\":\""+proId+"\",\"year\":\""+weistrs[i].split(";")[3]+"\",\"highprice\":\""+weistrs[i].split(";")[0]+"\",\"lowprice\":\""+weistrs[i].split(";")[1]+"\",\"weight\":\""+weistrs[i].split(";")[2]+"\"},";
							}else{
								s += "{\"productid\":\""+proId+"\",\"year\":\""+weistrs[i].split(";")[3]+"\",\"highprice\":\""+weistrs[i].split(";")[0]+"\",\"lowprice\":\""+weistrs[i].split(";")[1]+"\",\"weight\":\""+weistrs[i].split(";")[2]+"\"}]}";
							}
						}
						return s;
					}else{
						List<PriceSheet> proList = ipss.queryByid(proId,page,limit);
						Integer counts=ipss.selectCountsAll(proId);//总数
						String s = "{\"code\":0,\"msg\":\"\",\"count\":"+counts+",\"data\":[";
						if(proList.size()==0){
							s+="]}";
							return s;
						}
						wight_tmp = df.format(1/proList.size());
						for(int i=0;i<proList.size();i++){
							if(i<proList.size()-1){
								s += "{\"productid\":\""+proList.get(i).getProductid()+"\",\"year\":\""+proList.get(i).getYear().toString()+"\",\"highprice\":\""+proList.get(i).getHighestprice().toString()+"\",\"lowprice\":\""+proList.get(i).getLowestprice().toString()+"\",\"weight\":\""+wight_tmp+"\"},";
							}else{
								s += "{\"productid\":\""+proList.get(i).getProductid()+"\",\"year\":\""+proList.get(i).getYear().toString()+"\",\"highprice\":\""+proList.get(i).getHighestprice().toString()+"\",\"lowprice\":\""+proList.get(i).getLowestprice().toString()+"\",\"weight\":\""+wight_tmp+"\"}]}";
							}
						}
						return s;
					}
					
				}
		//返回权重
		@RequestMapping("getweights")
		public @ResponseBody String getWeights() throws Exception{
			
			return "";
		}
		//修改该用户参数设置
		@RequestMapping(value="setparameter",method=RequestMethod.POST,produces="application/json;charset=UTF-8")
		public  String setParameter(@RequestBody ParameterList pl, HttpServletRequest request) throws Exception{
			Map<String,Map<String,String>> map_old = (Map<String,Map<String,String>>)request.getSession().getAttribute("subinfo");
			List<ParameterdetailsBean> list = pl.getList();
			if(list.size()==0){
				ios.deleteParamet(pl.getUserId());
				return "";
			}
			for (ParameterdetailsBean parametbean : list) {
				parametbean.setUserid(pl.getUserId());
				Map<String,String> map = isss.CreateSegmentSet(parametbean);
				parametbean.setSegmentprice(Double.parseDouble(map.get("segmentprice").trim()));;
			}
			ios.deleteParamet(pl.getUserId());
			ios.insertParamet(list);
			for (int i=0;i<list.size();i++) {
				Map<String,String> map1 = isss.CreateSegmentSet(list.get(i));//生成分档信息
				/*System.out.println("sell      "+map1.get("sell"));
				System.out.println("buy      "+map1.get("buy"));*/
				String[] constr = list.get(i).getContractset().split(",");
				for(int j=0;j<constr.length;j++){
					map_old.put(constr[j], map1);
				}
			}
			request.getSession().setAttribute("subinfo",map_old);
			return "";
		}
		
		
		/*--------------------------------------------------------------------------------*/
		
		
		@RequestMapping(value = "queryProducts",produces="application/json;charset=UTF-8")
		public @ResponseBody String testParameter(String proId,String proName) throws Exception{
			String proId_1 = null;
			String proName_1 = null;
			if (!proId.equals("")) {
				proId_1 = proId;
			} 
			if(!proName.equals("")){
				proName_1 = proName;
			}
			List<ProductBean> list = ipss.selectProducts(proId_1,proName_1);
			Integer counts = 10;
			String s = "{\"code\":0,\"msg\":\"\",\"count\":"+counts+",\"data\":[";
			if(list.size() == 0){
				s+="]}";
				return s;
			}
			for (int i=0;i<list.size();i++) {
				if(i<list.size()-1){
					s += "{\"productId\":\""+list.get(i).getProductId()+"\",\"productName\":\""+list.get(i).getProductName()+"\",\"exchangeNo\":\""+list.get(i).getExchangeNo()+"\"},";
				}else{
					s += "{\"productId\":\""+list.get(i).getProductId()+"\",\"productName\":\""+list.get(i).getProductName()+"\",\"exchangeNo\":\""+list.get(i).getExchangeNo()+"\"}]}";
				}
			}
			return s;
		}
		
		
		@RequestMapping(value = "productBypricesheet",produces="application/json;charset=UTF-8")
		public @ResponseBody String productBypricesheet(String proId,String proName) throws Exception{
			String proId_1 = null;
			String proName_1 = null;
			if (!proId.equals("")) {
				proId_1 = proId;
			} 
			if(!proName.equals("")){
				proName_1 = proName;
			}
			List<ProductBean> list = ipss.productBypricesheet(proId_1,proName_1);
			Integer counts = 10;
			String s = "{\"code\":0,\"msg\":\"\",\"count\":"+counts+",\"data\":[";
			if(list.size() == 0){
				s+="]}";
				return s;
			}
			for (int i=0;i<list.size();i++) {
				if(i<list.size()-1){
					s += "{\"productId\":\""+list.get(i).getProductId()+"\",\"productName\":\""+list.get(i).getProductName()+"\",\"exchangeNo\":\""+list.get(i).getExchangeNo()+"\"},";
				}else{
					s += "{\"productId\":\""+list.get(i).getProductId()+"\",\"productName\":\""+list.get(i).getProductName()+"\",\"exchangeNo\":\""+list.get(i).getExchangeNo()+"\"}]}";
				}
			}
			return s;
		}
		
		@RequestMapping("testsocket")
		public @ResponseBody String testParameter(String Instrument) throws Exception{
			Instrument = "{\"ExchangeID\": \"DCE\",\"InstrumentID\": \"a1805\"}";
			return "";
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
}
