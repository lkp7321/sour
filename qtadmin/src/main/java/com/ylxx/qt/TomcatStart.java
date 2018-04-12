package com.ylxx.qt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ylxx.qt.service.po.GlobaVlariables;
import com.ylxx.qt.utils.InputDbThread;
import com.ylxx.qt.utils.InputMapThread;

public class TomcatStart implements ServletContextListener{
	
	
	@Override				
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			GlobaVlariables.socket.close();
			GlobaVlariables.bis.close();
	        GlobaVlariables.bos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {	
//			GlobaVlariables.socket = new Socket("221.122.75.230",18084);
			GlobaVlariables.socket = new Socket("39.104.72.121",8084);
			GlobaVlariables.bos = new BufferedOutputStream(GlobaVlariables.socket.getOutputStream());
			GlobaVlariables.bis = new BufferedInputStream(GlobaVlariables.socket.getInputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputMapThread mapThread = new InputMapThread();
		InputDbThread  dbThread = new InputDbThread();
		Thread map_thread = new Thread(mapThread);
		Thread db_thread = new Thread(dbThread);
		map_thread.start();
		db_thread.start();
		
		
		
		/*try {
			MarginCenterServiceImpl mcsi = (MarginCenterServiceImpl)SpringContextHelper.getBean(MarginCenterServiceImpl.class);
			List<String> dates = new ArrayList<String>();
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
			Date end=new Date();
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(end);
			rightNow.add(Calendar.MONTH,-6);//日期减3个月
			Date start = rightNow.getTime();
			Long spi = end.getTime() - start.getTime();
		 	Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数
		 	List<Date> dateList = new ArrayList<Date>();
		 	dateList.add(end);
		 	for (int i = 1; i <= step; i++) {
		 	      dateList.add(new Date(dateList.get(i - 1).getTime()
		 	              - (24 * 60 * 60 * 1000)));
		 	}
		 	for (Date date : dateList) {
				dates.add(dateFormat.format(date));
			}
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
				long startTime=System.currentTimeMillis();   //获取开始时间
			list = mcsi.RegistLogin(dates);
			if(list.size()>0){
				for (Map<String, String> map : list) {
					mcsi.UpdateTradingaccount(map);
				}
			}
				long endTime=System.currentTimeMillis(); //获取结束时间
				long gg = (endTime-startTime)/1000;
				System.out.println("颤抖吧！人类    =   "+gg);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*try {
		MarginCenterServiceImpl mcsi = (MarginCenterServiceImpl)SpringContextHelper.getBean(MarginCenterServiceImpl.class);
		Map<String,String> loginMap = null;
		while(true){
//			Date date = new Date();
//			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
//			String date_str = dateFormat.format(date);//时间
			loginMap = mcsi.LoginSuccess("2017-12-06");
			if(null!=loginMap){
				System.out.println("终于成功了！！！！");
				break;
			}
			
		}
		if(loginMap.get("gg")!=null){
			System.out.println("就是这么厉害     "+loginMap.get("gg"));
		}else{
			mcsi.UpdateTradingaccount(loginMap);
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	/*
	 *  final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23); // 控制时
		calendar.set(Calendar.MINUTE, 0); // 控制分
		calendar.set(Calendar.SECOND, 0); // 控制秒

		Date time = calendar.getTime();
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				try {
					Calendar c = Calendar.getInstance();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date time = c.getTime();
					String date_str = dateFormat.format(time);// 时间
					MarginCenterServiceImpl mcsi = (MarginCenterServiceImpl) SpringContextHelper
							.getBean(MarginCenterServiceImpl.class);
					Map<String, String> loginMap = null;
					while (true) {
						// Date date = new Date();
						// SimpleDateFormat dateFormat= new
						// SimpleDateFormat("yyyy-MM-dd");
						// String date_str = dateFormat.format(date);//时间
						loginMap = mcsi.LoginSuccess(date_str);
						if (null != loginMap) {
							System.out.println("终于成功了！！！！");
							break;
						}

					}
					if (loginMap.get("gg") != null) {
						System.out.println("就是这么厉害     " + loginMap.get("gg"));
					} else {
						mcsi.UpdateTradingaccount(loginMap);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
	*/
		
	}
}
