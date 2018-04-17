//package com.ylxx.fx.controller;
//
//import java.util.Map;
//
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
//
//public class WebSocketHandshakeInterceptor extends HttpSessionHandshakeInterceptor{
//	@Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
//            Exception ex) {
//        super.afterHandshake(request, response, wsHandler, ex);
//    }
//
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
//            Map<String, Object> attributes) throws Exception {
//        return super.beforeHandshake(request, response, wsHandler, attributes);
//    }
//}
