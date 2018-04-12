			package com.ylxx.qt.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.ylxx.qt.core.mapper.SupervisionCenterDataMapper;
import com.ylxx.qt.service.MarginCenterService;
import com.ylxx.qt.service.po.PositionBean;
import com.ylxx.qt.service.po.TradeBean;
import com.ylxx.qt.service.po.TradingaccountBean;
import com.ylxx.qt.utils.Test;
import com.ylxx.qt.utils.TestRecognize;
@Service("margincenterservice")
public class MarginCenterServiceImpl implements MarginCenterService {
	TradingaccountBean tb = new TradingaccountBean();
	List<TradeBean> tfbList = new ArrayList<TradeBean>();
	List<PositionBean> pbList = new ArrayList<PositionBean>();
	String table1 = null;//基本资料------>3
	String table2 = null;//期货期权账户资金状况----------->5
	String table3 = null;//期货期权账户出入金明细（单位：人民币）-------------->6
	String table4 = null;//期货期权账户出入金明细（单位：美元）
	String table5 = null;//其它资金明细
	String table6 = null;//期货成交汇总------------>9
	String table7 = null;//期货持仓汇总------------>11
	String table8 = null;//成交明细
	String CTPID = null;
	String tradeDate = null;//交易日期
	double floatProfit = 0;//逐日总浮动盈亏
	double openProfit = 0;//逐日总平仓盈亏
	@Resource
	private SupervisionCenterDataMapper scm;
	


	@Override
	public String UpdateTradingaccount(Map<String, String> map)
			throws Exception {
		Document docAll = Jsoup.parse(map.get("tradeHtml"));
		Elements tables = docAll.select("table");
		table1 = tables.get(3).toString();
		table1 = table1.replace("&nbsp;", "");
		Document doc = Jsoup.parse(table1);
		Elements trs = doc.select("tr");
		for(int i=0;i<trs.size();i++){
			Elements tds = trs.get(i).select("td");
			if(i==1){
				CTPID = tds.get(1).text().trim();//ctpid
			}
			if(i==1){
				tradeDate = tds.get(3).text().trim();
			}
		}
		
		table2 = tables.get(5).toString();
		table2 = table2.replace("&nbsp;", "");
		doc = Jsoup.parse(table2);
		trs = doc.select("tr");
		tb.setTradeDay(tradeDate.replace("-", ""));
		System.out.println("-------------------------------------------------------"+tradeDate.replace("-", "")+"====================");
		tb.setcTPID(CTPID);
		for(int i=0;i<trs.size();i++){
			Elements tds = trs.get(i).select("td");
			
			if(i==1){
				tb.setPreBalanceByTrade(Double.parseDouble(tds.get(1).text().trim().replace(",", "")));//逐笔上日结存
			}
			if(i==7){
				tb.setPositionProfitByTrade(Double.parseDouble(tds.get(1).text().trim().replace(",", "")));//逐笔浮动盈亏
			}
			if(i==3){
				tb.setCloseProfitByTrade(Double.parseDouble(tds.get(1).text().trim().replace(",", "")));//逐笔平仓盈亏
			}
			if(i==5){
				tb.setCommission(Double.parseDouble(tds.get(1).text().trim().replace(",", "")));//手续费
			}
			if(i==6){
				if(! tds.get(3).text().trim().equals("--")){
					tb.setCurrMargin(Double.parseDouble(tds.get(3).text().trim().replace(",", "")));//保证金
				}
			}
			if(i==5){
				tb.setFrozenCash(Double.parseDouble(tds.get(3).text().trim().replace(",", "")));//冻结资金
			}
			if(i==7){
				tb.setAvailable(Double.parseDouble(tds.get(3).text().trim().replace(",", "")));//可用资金
			}
			if(i==1){
				tb.setFund(Double.parseDouble(tds.get(3).text().trim().replace(",", "")));//客户权益
			}
			if(i==8){
				tb.setRisk(Double.parseDouble(tds.get(3).text().trim().substring(0, 5)));//风险度
			}
		}
		
		table3 = tables.get(6).toString();
		table3 = table3.replace("&nbsp;", "");
		doc = Jsoup.parse(table3);
		trs = doc.select("tr");
		for(int i=2;i<trs.size();i++){
			Elements tds = trs.get(i).select("td");
			if(tds.get(0).text().trim().equals("合计")){
				tb.setDeposit(Double.parseDouble(tds.get(1).text().trim().replace(",", "")));//入金
				tb.setWithdraw(Double.parseDouble(tds.get(2).text().trim().replace(",", "")));//出金
				break;
			}else{continue;}
		}
		
		table7 = tables.get(11).toString();
		table7 = table7.replace("&nbsp;", "");
		doc = Jsoup.parse(table7);
		trs = doc.select("tr");
		for(int i=3;i<trs.size()-1;i++){
			Boolean b = true;//true:买   FALSE:卖
			PositionBean pb = new PositionBean();
			pb.setTradeDay(tradeDate.replace("-", "").trim());
			pb.setcTPID(CTPID);
			Elements tds = trs.get(i).select("td");
			
			pb.setInstrumentID(tds.get(0).text().trim());
			
			if(tds.get(1).text().trim().equals("")){
				pb.setDirection("1");//卖
				b = false;
			}else{
				pb.setDirection("0");//买
				pb.setPosition(Integer.parseInt(tds.get(1).text().trim().replace(",", "")));
			}
			
			if(b){
				pb.setOpenCost(Double.parseDouble(tds.get(2).text().trim().replace(",", "")));
				pb.setPrice(Double.parseDouble(tds.get(2).text().trim().replace(",", "")));
			}
			
			if(! tds.get(3).text().trim().equals("")){
				pb.setPosition(Integer.parseInt(tds.get(3).text().trim().replace(",", "")));
			}
			
			if(!b){
				pb.setOpenCost(Double.parseDouble(tds.get(4).text().trim().replace(",", "")));
				pb.setPrice(Double.parseDouble(tds.get(4).text().trim().replace(",", "")));
			}
			
			pb.setLastSettlementPrice(Double.parseDouble(tds.get(5).text().trim().replace(",", "")));//昨结算价
			
			pb.setSettlementPrice(Double.parseDouble(tds.get(6).text().trim().replace(",", "")));//今结算价
			
			pb.setPositionProfitByTrade(Double.parseDouble(tds.get(7).text().trim().replace(",", "")));//逐笔浮动盈亏
			
			pb.setMargin(Double.parseDouble(tds.get(8).text().trim().replace(",", "")));//交易保证金
			
			if(tds.get(9).text().trim().equals("投机")){
				pb.setHedge("0");
			}else{
				pb.setHedge("1");
			}
			pbList.add(pb);
		}
		
		
		/*逐日*/
		docAll = Jsoup.parse(map.get("dateHtml"));
		tables = docAll.select("table");
		table2 = tables.get(5).toString();
		table2 = table2.replace("&nbsp;", "");
		doc = Jsoup.parse(table2);
		trs = doc.select("tr");
		for(int i=0;i<trs.size();i++){
			Elements tds = trs.get(i).select("td");
				if(i==1){
					tb.setPreBalance(Double.parseDouble(tds.get(1).text().trim().replace(",", "")));;//逐日上日结存
				}
		}
		
		table7 = tables.get(11).toString();
		table7 = table7.replace("&nbsp;", "");
		doc = Jsoup.parse(table7);
		trs = doc.select("tr");
		for(int i=3;i<trs.size();i++){
			Boolean b = true;//true:买   FALSE:卖
			String instrId = null;//合约
			String direction = null;//买卖方向
			double ppBydate = 0;;//逐日浮动盈亏
			Elements tds = trs.get(i).select("td");
			if(i!=trs.size()-1){
				instrId = tds.get(0).text().trim();
				
				if(! tds.get(1).text().trim().equals("")){
					direction = "0";
				}else{
					direction = "1";
				}
				ppBydate = Double.parseDouble(tds.get(7).text().trim().replace(",", ""));//逐日浮动盈亏
			}
			if(i==trs.size()-1){
				floatProfit = Double.parseDouble(tds.get(7).text().trim().replace(",", ""));//逐日浮动总盈亏
			}
			for (PositionBean obj : pbList) {
				if(obj.getInstrumentID().equals(instrId) && obj.getDirection().equals(direction)){
					obj.setPositionProfit(ppBydate);//逐日浮动盈亏
				}
			}
		}
		
		table6 = tables.get(9).toString();
		table6 = table6.replace("&nbsp;", "");
		doc = Jsoup.parse(table6);
		trs = doc.select("tr");
		for(int i=0;i<trs.size();i++){
			Elements tds = trs.get(i).select("td");
				if(i==trs.size()-1){
					openProfit = Double.parseDouble(tds.get(8).text().trim().replace(",", ""));//逐日平仓盈亏
				}
		}
		tb.setPositionProfit(floatProfit);//逐日总浮动盈亏
		tb.setCloseProfit(openProfit);//逐日总平仓盈亏
		
		/*成交明细*/
		docAll = Jsoup.parse(map.get("trade_tran"));
		tables = docAll.select("table");
		table8 = tables.get(3).toString();
		table8 = table8.replace("&nbsp;", "");
		doc = Jsoup.parse(table8);
		trs = doc.select("tr");
		for(int i=3;i<trs.size()-1;i++){
			TradeBean tfb = new TradeBean();
			tfb.setcTPID(CTPID);
			Elements tds = trs.get(i).select("td");
			
			tfb.setInstrumentID(tds.get(0).text().trim());
			
			String num_str = Integer.parseInt(tds.get(1).text().trim())+"";
			tfb.setTradeID(num_str.trim());
			
			tfb.setTradeTime(tds.get(2).text().trim());
			
			if(tds.get(3).text().trim().equals("买")){
				tfb.setDirection("0");
			}else{
				tfb.setDirection("1");
			}
			
			if(tds.get(4).text().trim().equals("投机")){
				tfb.setHedge("0");
			}else{
				tfb.setHedge("1");
			}
			
			tfb.setPrice(Double.parseDouble(tds.get(5).text().trim().replace(",", "")));//成交价
			
			tfb.setVolume(Integer.parseInt(tds.get(6).text().trim()));
			
			if(tds.get(8).text().trim().equals("开")){
				tfb.setOffset("0");
			}else{
				tfb.setOffset("1");
			}
			
			if(! tds.get(10).text().trim().equals("--")){
				tfb.setCloseProfit(Double.parseDouble(tds.get(10).text().trim().replace(",", "")));
			}
			
			tfb.setTradingDay(tds.get(11).text().trim().replace("-", ""));
			
			tfbList.add(tfb);
		}
		
		docAll = Jsoup.parse(map.get("date_tran"));
		tables = docAll.select("table");
		table8 = tables.get(3).toString();
		table8 = table8.replace("&nbsp;", "");
		doc = Jsoup.parse(table8);
		trs = doc.select("tr");
		for(int i=3;i<trs.size()-1;i++){
			String tran_num = null;//成交编号
			double closeProByDate = 0;
			Elements tds = trs.get(i).select("td");
				
			String num_str = Integer.parseInt(tds.get(1).text().trim())+"";
			tran_num = num_str.trim();
				
			if(! tds.get(10).text().trim().equals("--")){
				closeProByDate = Double.parseDouble(tds.get(10).text().trim().replace(",", ""));//逐日平仓盈亏
			}
				
			for (TradeBean obj : tfbList) {
				if(obj.getTradeID().equals(tran_num)){
					obj.setCloseProfitByDate(closeProByDate);//逐日平仓盈亏
				}
			}
		}
		
		Integer tradAccCounts = scm.selectTradAccount(tb);
		if(tradAccCounts > 0 && tradAccCounts == 1){
			scm.updateTradAccount(tb);
		}else{
			scm.insertTradAccount(tb);
		}
		
		for (TradeBean obj : tfbList) {
			Integer tradeFiledCounts = scm.selectTradeFild(obj);
			if(tradeFiledCounts > 0 && tradeFiledCounts == 1){
				scm.updateTradeFild(obj);
			}else{
				scm.insertTradeFild(obj);
			}
		}
		
		for (PositionBean obj : pbList) {
			Integer positionCounts = scm.selectPositionFild(obj);
			if(positionCounts > 0 && positionCounts == 1){
				scm.updatePositionFild(obj);
			}else{
				scm.insertPositionFild(obj);
			}
		}
		
		return null;
	}


	
	
	
	
	@Override
	public Map<String,String> LoginSuccess(String date) throws Exception {
		try {
			Map<String,String> nvps=new HashMap<String, String>(); //登录
			Map<String,String> htmlAll = new HashMap<String, String>();
			Map<String,String> tran_details=new HashMap<String, String>();//成交明细
			Map<String,String> date_tran=new HashMap<String, String>();//逐日
			Map<String,String> trade_tran=new HashMap<String, String>();//逐日
			String rootUrl = "https://investorservice.cfmmc.com";//根
			String htmlgg = getResult("https://investorservice.cfmmc.com/");
			String verstr = returnVerification();
			if(verstr != null && verstr.length() == 6){
				Document doc = Jsoup.parse(htmlgg);
				Element form = doc.select("form").get(0); 
				String token = form.select("input[name=org.apache.struts.taglib.html.TOKEN]").get(0).val();
				nvps.put("userID", "0078901202193");  
			    nvps.put("password", "Lee19860325");  
			    nvps.put("vericode", verstr);  
			    nvps.put("org.apache.struts.taglib.html.TOKEN", token); 
			    String actionPath=form.attr("action");
			    String connectPath = rootUrl+actionPath;//拼接action
			    Document trade=Jsoup.connect(connectPath).data(nvps).post();
			    if(trade.toString().contains("退出系统")){
			    	System.out.println("进入系统了-----------");
			    	
			    		
			    		/*逐笔*/
				    	String tradeOld_url = trade.select("form[name=customerForm]").get(0).attr("action");
				    	token = trade.select("input[name=org.apache.struts.taglib.html.TOKEN]").get(0).val();//提交
				    	String trade_url = rootUrl+tradeOld_url;
				    	trade_tran.put("org.apache.struts.taglib.html.TOKEN", token);
				    	trade_tran.put("tradeDate", date);
				    	trade_tran.put("byType", "trade");
				    	trade=Jsoup.connect(trade_url).data(trade_tran).post();//逐笔首页
				        if(trade.toString().contains("其它资金明细") && trade.toString().contains("期货成交汇总") && trade.toString().contains("期货持仓汇总") && trade.toString().contains("期货期权账户出入金明细（单位：美元）" ) && trade.toString().contains("期货期权账户出入金明细（单位：人民币）") && trade.toString().contains("期货期权账户资金状况")){
				        	System.out.println("有数据，真开心！！！！！！！！！");
				        	token = trade.select("input[name=org.apache.struts.taglib.html.TOKEN]").get(2).val();//逐笔成交明细
					    	tran_details.put("org.apache.struts.taglib.html.TOKEN", token);
					    	//逐笔成交明细
					    	String transactionBytrade_url = trade.select("form[name=userInfoForm]").get(0).attr("action");
					    	String connectPathBytrade_tran = rootUrl+transactionBytrade_url;
					    	Document tradeDoc_tran=Jsoup.connect(connectPathBytrade_tran).data(tran_details).post();
					    	/*逐日*/
					    	String dateOld_url = trade.select("form[name=customerForm]").get(0).attr("action");
					    	token = trade.select("input[name=org.apache.struts.taglib.html.TOKEN]").get(0).val();//提交
					    	String date_url = rootUrl+dateOld_url;
					    	date_tran.put("org.apache.struts.taglib.html.TOKEN", token);
					    	date_tran.put("tradeDate", date);
					    	date_tran.put("byType", "date");
					    	Document dateAll=Jsoup.connect(date_url).data(date_tran).post();//逐日首页
					    	//逐日成交明细
					    	token = dateAll.select("input[name=org.apache.struts.taglib.html.TOKEN]").get(2).val();//逐日成交明细
					    	tran_details.put("org.apache.struts.taglib.html.TOKEN", token);
					    	String transactionBydate_url = dateAll.select("form[name=userInfoForm]").get(0).attr("action");
					    	String connectPathBydate_tran = rootUrl+transactionBydate_url;
					    	Document dateDoc_tran=Jsoup.connect(connectPathBydate_tran).data(tran_details).post();
					    	
					    	htmlAll.put("tradeHtml", trade.toString());
					    	htmlAll.put("dateHtml", dateAll.toString());
					    	htmlAll.put("trade_tran", tradeDoc_tran.toString());
					    	htmlAll.put("date_tran", dateDoc_tran.toString());
					    	return htmlAll;
				    	}else{
				    		System.out.println("没有数据！！！！！！！！！!!!!!!!!!!!!!!!!!");
				    		htmlAll.put("gg", "今天数据异常");
				    		return htmlAll;
				    	}
			    }else{
			    }
			}else{
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static String returnVerification() throws Exception{
		//解析验证码
//	        File file = new File("E:/testyzm/gg/test.png");
	        File file = new File("E:/Myeclipse/workspace/qtadmin/ocrimg/test.png");
	        InputStream is = new FileInputStream(file);
	        BufferedImage bi=ImageIO.read(is);
	        BufferedImage tt = TestRecognize.removeLine(bi,3);
//	        ImageIO.write(tt, "png", new File("E:/testyzm/gg/test.png"));
//	        String ttt = Test.recognizeText(new File("E:/testyzm/gg/test.png"), "png");
	        ImageIO.write(tt, "png", new File("E:/Myeclipse/workspace/qtadmin/ocrimg/test.png"));
	        String ttt = Test.recognizeText(new File("E:/Myeclipse/workspace/qtadmin/ocrimg/test.png"), "png");
	        char[] strs = ttt.toCharArray();
	        String verificat = "";
	        for (char c : strs) {
				if(c>='a'&&c<='z' || c>='A'&&c<='Z' || Character.isDigit(c)){
					verificat += c;
				}
			}
	        System.out.println("准确率不咋地    "+verificat);
		 return verificat;
	 }
	
	public static String getResult(String url) throws Exception {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                CloseableHttpResponse response = httpClient.execute(new HttpGetConfig(url))) {

        	HttpGet httpGet=new HttpGet("https://investorservice.cfmmc.com/veriCode.do?t=(new%20Date()).getTime()"); //2、创建请求
 	        CloseableHttpResponse closeableHttpResponse=httpClient.execute(httpGet); //3、执行
 	        HttpEntity httpEntity=closeableHttpResponse.getEntity(); //4、获取实体
 	        if(httpEntity!=null){
 	            //System.out.println("ContentType:"+httpEntity.getContentType().getValue());
 	            InputStream inputStream=(InputStream)httpEntity.getContent();
 	            //FileUtils.copyToFile(inputStream, new File("D://xxx.png")); //将图片保存在本次磁盘D盘，命名为xxx.png
 	            int index;  
 	            byte[] bytes = new byte[1024];  
// 	            FileOutputStream downloadFile = new FileOutputStream("E:/testyzm/gg/test.png");  
 	            FileOutputStream downloadFile = new FileOutputStream("E:/Myeclipse/workspace/qtadmin/ocrimg/test.png");  
 	            while ((index = inputStream.read(bytes)) != -1) {  
 	                downloadFile.write(bytes, 0, index);  
 	                downloadFile.flush();  
 	            }  
 	            downloadFile.close();  
 	            inputStream.close();  
 	        }
        	
        	
            String result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            System.out.println("获取失败");
            return "";
        }
    }


	public Document login() throws Exception{
		Map<String,String> nvps=new HashMap<String, String>(); //登录
		String rootUrl = "https://investorservice.cfmmc.com";//根
		String htmlgg = getResult("https://investorservice.cfmmc.com/");
		String verstr = returnVerification();
		if(verstr != null && verstr.length() == 6){
			Document doc = Jsoup.parse(htmlgg);
			Element form = doc.select("form").get(0); 
			String token = form.select("input[name=org.apache.struts.taglib.html.TOKEN]").get(0).val();
			nvps.put("userID", "0078901201911");  //0078901202193
		    nvps.put("password", "Mark1234");  //Lee19860325
		    nvps.put("vericode", verstr);  
		    nvps.put("org.apache.struts.taglib.html.TOKEN", token); 
		    String actionPath=form.attr("action");
		    String connectPath = rootUrl+actionPath;//拼接action
		    Document trade=Jsoup.connect(connectPath).data(nvps).post();
		    return trade;
	    }else{
	    	return null;
	    }
	}
	
	//首次注册登录获取当前注册时间半年前数据
	@Override
	public List<Map<String, String>> RegistLogin(List<String> dates) throws Exception {
		try {
				List<Map<String, String>> listhtml = new ArrayList<Map<String, String>>();
			    String token = "";		
				String rootUrl = "https://investorservice.cfmmc.com";//根
				int i =0;
				boolean flag = false;
				while(true){
					Document trade = login();
					if(trade!=null && trade.toString().contains("退出系统")){
						System.out.println("---------------这不是进来了么！-----------------");
					}else{
						continue;
					}
					
					for(;i<dates.size();i++){
						if(i==dates.size()-1){
				    		flag=true;
				    	}
						String date = dates.get(i);
						System.out.println("-------------"+date+"----------------------");
						Map<String,String> htmlAll = new HashMap<String, String>();
						Map<String,String> tran_details=new HashMap<String, String>();//成交明细
						Map<String,String> date_tran=new HashMap<String, String>();//逐日
						Map<String,String> trade_tran=new HashMap<String, String>();//逐笔
					    	/*逐笔*/
					    	String tradeOld_url = trade.select("form[name=customerForm]").get(0).attr("action");
					    	token = trade.select("input[name=org.apache.struts.taglib.html.TOKEN]").get(0).val();//提交
					    	String trade_url = rootUrl+tradeOld_url;
					    	trade_tran.put("org.apache.struts.taglib.html.TOKEN", token);
					    	trade_tran.put("tradeDate", date);
					    	trade_tran.put("byType", "trade");
					    	trade=Jsoup.connect(trade_url).data(trade_tran).post();//逐笔首页
					    	if(trade.toString().contains("登录超时")){
					    		System.out.println("--------登录超时，去重新登录-------------");
								break;
							}
					    	if(trade.toString().contains("其它资金明细") && trade.toString().contains("期货成交汇总") && trade.toString().contains("期货持仓汇总") && trade.toString().contains("期货期权账户出入金明细（单位：美元）" ) && trade.toString().contains("期货期权账户出入金明细（单位：人民币）") && trade.toString().contains("期货期权账户资金状况")){
					    		
							}else{
								System.out.println("---------没有数据啊这一天天的---------------");
								continue;
							}
					    	token = trade.select("input[name=org.apache.struts.taglib.html.TOKEN]").get(2).val();//逐笔成交明细
					    	tran_details.put("org.apache.struts.taglib.html.TOKEN", token);
					    	//逐笔成交明细
					    	String transactionBytrade_url = trade.select("form[name=userInfoForm]").get(0).attr("action");
					    	String connectPathBytrade_tran = rootUrl+transactionBytrade_url;
					    	Document tradeDoc_tran=Jsoup.connect(connectPathBytrade_tran).data(tran_details).post();
					    	if(tradeDoc_tran.toString().contains("登录超时")){
								break;
							}
					    	/*逐日*/
					    	String dateOld_url = trade.select("form[name=customerForm]").get(0).attr("action");
					    	token = trade.select("input[name=org.apache.struts.taglib.html.TOKEN]").get(0).val();//提交
					    	String date_url = rootUrl+dateOld_url;
					    	date_tran.put("org.apache.struts.taglib.html.TOKEN", token);
					    	date_tran.put("tradeDate", date);
					    	date_tran.put("byType", "date");
					    	Document dateAll=Jsoup.connect(date_url).data(date_tran).post();//逐日首页
					    	if(dateAll.toString().contains("登录超时")){
								break;
							}
					    	//逐日成交明细
					    	token = dateAll.select("input[name=org.apache.struts.taglib.html.TOKEN]").get(2).val();//逐日成交明细
					    	tran_details.put("org.apache.struts.taglib.html.TOKEN", token);
					    	String transactionBydate_url = dateAll.select("form[name=userInfoForm]").get(0).attr("action");
					    	String connectPathBydate_tran = rootUrl+transactionBydate_url;
					    	Document dateDoc_tran=Jsoup.connect(connectPathBydate_tran).data(tran_details).post();
					    	if(dateDoc_tran.toString().contains("登录超时")){
								break;
							}	
					    	
					    	htmlAll.put("tradeHtml", trade.toString());
					    	htmlAll.put("dateHtml", dateAll.toString());
					    	htmlAll.put("trade_tran", tradeDoc_tran.toString());
					    	htmlAll.put("date_tran", dateDoc_tran.toString());
					    	listhtml.add(htmlAll);
				}
					if(flag){
						break;
					}
			}
				return listhtml;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}






	@Override
	public String InsertTradingaccount(Map<String, String> map)
			throws Exception {
		return null;
	}






	@Override
	public Map<String, String> TestGG(String date) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}




class HttpGetConfig extends HttpGet {
    public HttpGetConfig(String url) {
        super(url);
        setDefaulConfig();
    }

    private void setDefaulConfig() {
        this.setConfig(RequestConfig.custom()
                .setConnectionRequestTimeout(10000)
                .setConnectTimeout(10000)
                .setSocketTimeout(10000).build());
        this.setHeader("User-Agent", "spider");
    }
}
