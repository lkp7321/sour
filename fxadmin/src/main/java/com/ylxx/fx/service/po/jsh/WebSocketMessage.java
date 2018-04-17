package com.ylxx.fx.service.po.jsh;

public class WebSocketMessage {
	/**
     * 发送广播消息，发送地址：/topic/* ，*为任意名字，如取名monitor,则客户端对应订阅地址为：/topic/monitor
     * 发送私人消息，发送地址：/*，*为任意名字，这里取名为message,客户端对应订阅地址：/user/{自定义客户端标识ID}/message
     */
    //可以自定义其他的属性
    private String distination;
    private Object data;//实际发送的数据对象
    private String userId;//如果不为空，则表示发送给个人而不是广播

    public String getDistination() {
        return distination;
    }
    public void setDistination(String distination) {
        this.distination = distination;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
