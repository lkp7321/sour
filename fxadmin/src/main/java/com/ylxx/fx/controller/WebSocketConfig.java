//package com.ylxx.fx.controller;
//
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//@EnableWebSocketMessageBroker
//@Controller
//public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
//	@Override  
//    public void registerStompEndpoints(StompEndpointRegistry registry) {  
//		//注册websocket，客户端用ws://host:port/项目名/webSocket 访问
//        registry.addEndpoint("/webSocket")
//                .setHandshakeHandler(new StompMessageHandshakeHandler())
//                .addInterceptors(new WebSocketHandshakeInterceptor())
//                .withSockJS();//表示支持以SockJS方式连接服务器  
//    } 
//	
//	@Override  
//    public void configureMessageBroker(MessageBrokerRegistry registry) {  
//		registry.enableSimpleBroker("/topic","/user");//这句话表示在topic和user这两个域上服务端可以向客户端发消息
//        registry.setApplicationDestinationPrefixes("/ws");//这句话表示客户端向服务器端发送时的主题上面需要加"/ws"作为前缀
//        registry.setUserDestinationPrefix("/user");//这句话表示服务端给客户端指定用户发送一对一的主题，前缀是"/user"  
//    }
//}
