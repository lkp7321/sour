//package com.ylxx.fx.controller;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.Headers;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//
//@Controller  
//public class WebSocketController {  
//	@Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
//
//    @MessageMapping("/webSocket/testWithServer")
//    //与页面的stompClient.send("/ws/webSocket/testWithServer", 
//    //{'name': 'xiao','syn':'wang'}, JSON.stringify({'message': name }));方法对应
//    public String send(String message,@Header("name")String name,
//            @Headers Map<String, Object> headers){
//        System.out.println(message);
//        System.out.println(name);
//        System.out.println(headers);
//        simpMessagingTemplate.convertAndSend("/user/1/testUser","服务端返回消息");
//        return "";
//    }
//}  
