//package com.ylxx.fx.controller;
//
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingDeque;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//
//import com.ylxx.fx.service.po.jsh.WebSocketMessage;
//
//public class WebSocketMessageUtil implements Runnable{
//
//	 static Logger log = LoggerFactory.getLogger(WebSocketMessageUtil.class); 
//
//	    private static SimpMessagingTemplate messageingTemplate;
//	    private static BlockingQueue<WebSocketMessage> wsQueue = new LinkedBlockingDeque<>();
//
//	    public WebSocketMessageUtil() {
//	        new Thread(this).start();
//	    }
//
//	    @Autowired
//	    public void setTemplate(SimpMessagingTemplate t){
//	        WebSocketMessageUtil.messageingTemplate = t;
//	    }
//
//	    public static void addMessage(WebSocketMessage msg){
//	        try{
//	            wsQueue.put(msg);
//	        }catch(InterruptedException e){
//	            log.error("添加实时消息异常");
//	        }
//	    }
//
//	    public static void sendMessage(WebSocketMessage msg){
//	        if(StringUtils.isBlank(msg.getUserId())){
//	            messageingTemplate.convertAndSend(msg.getDistination(),msg);
//	        }else{
//	            messageingTemplate.convertAndSendToUser(msg.getUserId(), msg.getDistination(), msg);
//	        }
//	    }
//
//	    @Override
//	    public void run() {
//	        log.info(">>>>>>>推送消息线程启动,正在监听消息。");
//	        while (true) {
//	            try {
//	                WebSocketMessage msg = wsQueue.take();
//	                if(msg!=null){
//	                    WebSocketMessageUtil.sendMessage(msg);
//	                }
//	            } catch (Exception ex) {}
//	        }
//	    }
//}
