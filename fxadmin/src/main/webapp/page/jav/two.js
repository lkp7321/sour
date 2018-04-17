//	//测试哦
//	da3={
//			"pageNo":1,
//			"pageSize":10,
//			"startDate":'2018偶哟'
//	}
//	//查询定投价格
//	us = '/fx/jsh/getMyTest.do';
//	$.ajax({
//		'url':us,
//		'type':'post',
//		'data':da3,
//		'dataType':'html',
//		'async':true,
//		'success':function(data){
//			alert(data);
//		}
//	});
	var websocket = null;  
	var username = 'admin';  
	  
	//判断当前浏览器是否支持WebSocket  
	if ('WebSocket' in window) {  
	    websocket = new WebSocket("ws://" + document.location.host + "/fx/laal/"  + username);  
	} else {  
	    alert('当前浏览器 Not support websocket')  
	}  
	  
	//连接发生错误的回调方法  
	websocket.onerror = function() {  
	    alert("WebSocket连接发生错误");  
	};  
	  
	//连接成功建立的回调方法  
	websocket.onopen = function() {  
		// setMessageInnerHTML("WebSocket连接成功");  
		console.log("adgdfg");
		websocket.send(JSON.stringify({'To':'admin'}));
	}  
	  
	//接收到消息的回调方法  
	websocket.onmessage = function(event) {  
	    alert(event.data)  
	}  
	  
	//连接关闭的回调方法  
	websocket.onclose = function() {  
	    alert("WebSocket连接关闭");  
	}  
	  
	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。  
	window.onbeforeunload = function() {  
	    closeWebSocket();  
	}  
	  
	//关闭WebSocket连接  
	function closeWebSocket() {  
	    websocket.close();  
	} 
	
