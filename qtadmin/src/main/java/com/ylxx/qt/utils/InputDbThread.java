package com.ylxx.qt.utils;

import java.util.logging.Logger;

import com.ylxx.qt.TomcatStart;
import com.ylxx.qt.service.impl.OverMonitorServiceImpl;
import com.ylxx.qt.service.po.GlobaVlariables;
import com.ylxx.qt.service.po.InstrumentPtrice;

public class InputDbThread implements Runnable{
	private static Logger logger = Logger.getLogger("InputDbThread");
	TomcatStart ts = new TomcatStart();
	@Override
	public void run() {	
		while(true){
			try {
				Thread.sleep(3000);
				InstrumentPtrice ippri = GlobaVlariables.queue.poll();
				//logger.info("我可是要写入数据的数据       "+ippri.getInstrumentId());
				if(null!=ippri){
					String instrumentid = ippri.getInstrumentId();
					String nowprice = ippri.getNowprice()+"";
					OverMonitorServiceImpl ios = (OverMonitorServiceImpl)SpringContextHelper.getBean(OverMonitorServiceImpl.class);
					InstrumentPtrice b = ios.selectinstrumentprice(ippri.getInstrumentId());
					if(null != b){
						ios.updateinstrumentprice(ippri);
					}else{
						ios.insertinstrumentprice(ippri);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
}
