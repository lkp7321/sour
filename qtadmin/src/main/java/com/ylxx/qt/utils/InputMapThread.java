package com.ylxx.qt.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ylxx.qt.service.AnalysisPriceInfoService;
import com.ylxx.qt.service.SocketNowPriceService;
import com.ylxx.qt.service.impl.OverMonitorServiceImpl;
import com.ylxx.qt.service.po.GlobaVlariables;
import com.ylxx.qt.service.po.InstrumentFieldBean;
import com.ylxx.qt.service.po.InstrumentPtrice;
@Component
public class InputMapThread implements Runnable{
	private static Logger logger = Logger.getLogger("InputMapThread");
	@Resource
	private AnalysisPriceInfoService apis;
	@Resource
	private SocketNowPriceService snps;
	@Override
	public void run() {
		try {
			
			
            //读取数据
            int len;
            byte[] inByte = new byte[1024];
            if (GlobaVlariables.bis != null) {
                len = GlobaVlariables.bis.read(inByte);
                if (len <= 0) {
                    //return null;
                }
                String result = new String(inByte, 0, len, "utf-8");
                while (!result.substring(result.length() - 3, result.length()).contains("###")) {
                    len = GlobaVlariables.bis.read(inByte);
                    result = result + new String(inByte, 0, len, "utf-8");
                }
                //System.out.println("99999999999999999999999999"+ "---" + result.getBytes()[0] + "--" + result.getBytes()[1] + result);
            }
            
            //登录
            String longin_str = "{\"Account\": \"admin\", \"PassWord\": \"123456\",\"CanSendTradeField\":\"0\"}###";
            byte[] new_login = longin_str.getBytes();
            byte[] newByte_login = new byte[new_login.length + 2];
            newByte_login[0] = 1;
            newByte_login[1] = 0;
            for (int i = 0; i < new_login.length; i++) {
            	newByte_login[i + 2] = new_login[i];
            }
            if (GlobaVlariables.bos != null) {
            	GlobaVlariables.bos.write(newByte_login);
            	GlobaVlariables.bos.flush();
            }   
            //读取数据
            int len2;
            byte[] inByte2 = new byte[1024];
            if (GlobaVlariables.bis != null) {
                len2 = GlobaVlariables.bis.read(inByte2);
                if (len2 <= 0) {
                    //return null;
                }
                String result = new String(inByte2, 0, len2, "utf-8");
                while (!result.substring(result.length() - 3, result.length()).contains("###")) {
                    len = GlobaVlariables.bis.read(inByte2);
                    result = result + new String(inByte2, 0, len, "utf-8");
                }
                //System.out.println("888888888888888888888888888"+ "---" + result.getBytes()[0] + "--" + result.getBytes()[1] + result);
                String[] split = result.split("###");
                for (int i = 0; i < split.length; i++) {
                    byte[] bytes = split[i].getBytes();
                    byte b1 = bytes[0];
                    final byte b2 = bytes[1];
                    String newStr = new String(bytes, 2, bytes.length - 2, "utf-8");
                }
            }
          //请求合约最新价
            String nowprice_str = "{\"Account\": \"A01\", \"dataList\":[";
            OverMonitorServiceImpl ios = (OverMonitorServiceImpl)SpringContextHelper.getBean(OverMonitorServiceImpl.class);
            List<InstrumentFieldBean> instrlist = new ArrayList<InstrumentFieldBean>();
            instrlist = ios.queryAllInstrument();
            for(int i=0;i<instrlist.size();i++){
            	if(i<instrlist.size()-1){
            		nowprice_str += "{\"ExchangeID\":\""+instrlist.get(i).getExchange_id()+"\",\"InstrumentID\":\""+instrlist.get(i).getInstrument_id()+"\"},";
            	}else{
            		nowprice_str += "{\"ExchangeID\":\""+instrlist.get(i).getExchange_id()+"\",\"InstrumentID\":\""+instrlist.get(i).getInstrument_id()+"\"}]}###";
            	}
            }
            //logger.info("nowprice_str     "+nowprice_str);
	        byte[] outprice = nowprice_str.getBytes();
	        byte[] newnowprice = new byte[outprice.length + 2];
	        newnowprice[0] = 27;
	        newnowprice[1] = 0;
	        for (int i = 0; i < outprice.length; i++) {
	        	newnowprice[i + 2] = outprice[i];
	        }
	        if (GlobaVlariables.bos != null) {
	        	GlobaVlariables.bos.write(newnowprice);
	        	GlobaVlariables.bos.flush();
	        } 
          
            
	        int len3;
	        byte[] inByte3 = new byte[1024];
	        if (GlobaVlariables.bis != null) {
	            len3 = GlobaVlariables.bis.read(inByte3);
	            String result = new String(inByte3, 0, len3, "utf-8");
	            while (!result.substring(result.length() - 3, result.length()).contains("###")) {
	                len3 = GlobaVlariables.bis.read(inByte3);
	                result = result + new String(inByte3, 0, len3, "utf-8");
	            }
	            //System.out.println("行情订阅"+ "---" + result.getBytes()[0] + "--" + result.getBytes()[1] + result);
	            
	            byte[] inByte4 = new byte[1024];
	           
	            while (true) {
	            	int len4 = GlobaVlariables.bis.read(inByte4);
	 	            String result4 = new String(inByte4, 0, len4, "utf-8");
	            	while(!result4.substring(result4.length() - 3, result4.length()).contains("###")){
	            		 len4 = GlobaVlariables.bis.read(inByte4);
	                     result4 = result4 + new String(inByte4, 0, len4, "utf-8");
	            	}
	            	//System.out.println("999感冒灵qian    "+result4+"    999感冒灵");
	            	String[] finally_oldstr = result4.split("###");
	            	for (String strinstrumnet : finally_oldstr) {
	            		strinstrumnet = strinstrumnet.trim().substring(0, strinstrumnet.length()-3).trim();
		            	String[] finally_str = strinstrumnet.split(",");
		        		InstrumentPtrice instrpri = new InstrumentPtrice();
		        		//logger.info(finally_str.length+"   "+finally_str[0]+finally_str[1]);
		        		if(finally_str.length<13){
		        			continue;
		        		}
		        		instrpri.setInstrumentId(finally_str[0]);
		        		instrpri.setNowprice(Double.parseDouble(finally_str[13]));
		        		GlobaVlariables.instruNowPrice.put(finally_str[0], Double.parseDouble(finally_str[13]));
		        		GlobaVlariables.queue.put(instrpri);
					}
				} 
	        }	 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
