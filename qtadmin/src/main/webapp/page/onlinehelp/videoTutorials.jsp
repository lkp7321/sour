<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>视频教程</title>
		<link rel="icon" href="" type="image/x-icon"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/reset.css"/>
		<link rel="stylesheet" href="<%=basePath%>/css/style.css" /> 
		<style>
			.swDownloadss{ width: 1202px; height: 100%; margin: auto;  color: #909090;margin-top: 79px;}
			.swDownloadss .header{ height: 70px; line-height: 70px; border-bottom: 1px solid #EBEBEB; }
			.statcont{ width: 1202px; height: 100%; margin-top: 20px; }
			.statcont div{ float: left; }
			.down_main_nav{ width: 200px; }
			.down_main_nav ul{ background-color: #F4F5F7; }
			.down_main_nav ul li{ height: 60px; line-height: 60px; text-align: center; font-size: 13px; cursor: pointer;  font-weight: 700; }
			.down_main_nav ul li:hover{ color: #498EFC; }
			.down_main_nav ul li span{ float: right; margin-right: 20px; }
			.contright{ width: 945px; 	min-height: 528px; margin-left: 40px;}
			.yd,.layout{ display:none; }
			.pub_downbox{  width:272px; height:224px; border:2px solid #EBEBEB;  margin-right: 40px;margin-bottom: 10px; }
			.pub_downbox:hover{box-shadow: 0 0 15px #ccc;}
			.pub_downbox .pub_up{ width:220px;  margin-left: 22px;  border-bottom: 2px solid #EBEBEB;font-size:13px;float:none; }
			.pub_downbox .pub_up p{  text-align:center;  height: 40px;  line-height: 40px; }
			.pub_downbox .pub_down{  height: 100px;  margin-left: 13px;  line-height: 100px; }
			.pub_downbox .pub_down button{  border: navajowhite;  width: 95px;  height: 31px;  cursor: pointer; }
			.pub_downbox .pub_down .down{  margin-left: 12px;  background: #5A8BFF;  color: #fff; }
			.pub_downbox .pub_down .know{  background: #fff;color: #5A8BFF; margin-left: 20px; }
			.pub_downbox .pub_down img{width: 200px;  height: 125px;  margin: 10px 0 0 20px;}
			 
		</style>
	</head>
	
	<body>
	    <!--导航部分-->
		 <div class="navs"> 
			<div class="nav_wrap" id='mark_wrap'>
				<div class="w_1180"> 
					<iframe src="<%=basePath%>page/head.jsp" border="0" width="100%" height="130" frameborder="0" scrolling="no" ></iframe>
				</div>
			</div>
		</div>
		<div class="swDownloadss">
		      <div class="header">
			  	<span>视频教程</span>
			  </div> 
			  <div class="statcont">
			  	  <div class="down_main_nav">
			  	  	 <ul>
                         <li data-type="basic" style="color: #498EFC;">基本常识<span>></span></li>
                         <li data-type="operation">操作部分 <span>></span></li>
                         <li data-type="layout">布局管理 <span>></span></li>
			  	  	 </ul>
			  	  </div>
			  	  <div class="contright">
			  	  	   <div class="pc basic">
			  	  	      <div class="pub_downbox">
			  	  	           <div class="pub_up">
			  	  	              <p style="color:#323232"><a href="<%=basePath%>page/onlinehelp/videoPage.jsp" target="_blank"
			  	  	              onclick="sessionStorage.setItem('src','<%=basePath%>video/Yikuanintro.mp4')">易宽聊期货：易宽期货介绍</a></p>
			  	  	              <p>易宽期货量化交易软件视频教程</p>
			  	  	           </div>
			  	  	           <div class="pub_down">
			  	  	              <a href="<%=basePath%>page/onlinehelp/videoPage.jsp" target="_blank"
			  	  	              onclick="sessionStorage.setItem('src','<%=basePath%>video/Yikuanintro.mp4')">
			  	  	                <img src="<%=basePath%>img/Yikuanintro.png"/>
			  	  	              </a>
			  	  	              
			  	  	           </div>
			  	  	      </div>
			  	  	      <div class="pub_downbox">
			  	  	           <div class="pub_up">
			  	  	              <p style="color:#323232"><a href="<%=basePath%>page/onlinehelp/videoPage.jsp" target="_blank"
			  	  	              onclick="sessionStorage.setItem('src','<%=basePath%>video/PosiandAutodeal.mp4')">易宽聊期货：持仓与自成交防范</a></p>
			  	  	              <p>易宽期货量化交易软件视频教程</p>
			  	  	           </div>
			  	  	           <div class="pub_down">
			  	  	              <a href="<%=basePath%>page/onlinehelp/videoPage.jsp" target="_blank"
			  	  	              onclick="sessionStorage.setItem('src','<%=basePath%>video/PosiandAutodeal.mp4')">
			  	  	                <img src="<%=basePath%>img/Yikuanintro.png"/>
			  	  	              </a>
			  	  	           </div>
			  	  	      </div>
			  	  	      
			  	  	   </div>
			  	  	   <div class="yd operation">
			  	  	      <div class="pub_downbox">
			  	  	           <div class="pub_up">
			  	  	              <p style="color:#323232"><a href="<%=basePath%>page/onlinehelp/videoPage.jsp" target="_blank"
			  	  	              onclick="sessionStorage.setItem('src','<%=basePath%>video/Autodeal.mp4')">操作部分：自动交易</a></p>
			  	  	              <p>易宽期货量化交易软件视频教程</p>
			  	  	           </div>
			  	  	           <div class="pub_down">
			  	  	                <a href="<%=basePath%>page/onlinehelp/videoPage.jsp" target="_blank"
			  	  	              onclick="sessionStorage.setItem('src','<%=basePath%>video/Autodeal.mp4')">
			  	  	                <img src="<%=basePath%>img/Yikuanintro.png"/>
			  	  	              </a>
			  	  	           </div>
			  	  	      </div>
			  	  	      <div class="pub_downbox">
			  	  	           <div class="pub_up">
			  	  	              <p style="color:#323232"><a href="<%=basePath%>page/onlinehelp/videoPage.jsp" target="_blank"
			  	  	              onclick="sessionStorage.setItem('src','<%=basePath%>video/instructionfun.mp4')">操作部分：指令功能</a></p>
			  	  	              <p>易宽期货量化交易软件视频教程</p>
			  	  	           </div>
			  	  	           <div class="pub_down">
			  	  	                <a href="<%=basePath%>page/onlinehelp/videoPage.jsp" target="_blank"
			  	  	              onclick="sessionStorage.setItem('src','<%=basePath%>video/instructionfun.mp4')">
			  	  	                <img src="<%=basePath%>img/Yikuanintro.png"/>
			  	  	              </a>
			  	  	           </div>
			  	  	      </div>
			  	  	       <div class="pub_downbox">
			  	  	           <div class="pub_up">
			  	  	              <p style="color:#323232"><a href="<%=basePath%>page/onlinehelp/videoPage.jsp" target="_blank"
			  	  	              onclick="sessionStorage.setItem('src','<%=basePath%>video/shiftposfun.mp4')">操作部分：移仓功能</a></p>
			  	  	              <p>易宽期货量化交易软件视频教程</p>
			  	  	           </div>
			  	  	           <div class="pub_down">
			  	  	                <a href="<%=basePath%>page/onlinehelp/videoPage.jsp" target="_blank"
			  	  	              onclick="sessionStorage.setItem('src','<%=basePath%>video/shiftposfun.mp4')">
			  	  	                <img src="<%=basePath%>img/Yikuanintro.png"/>
			  	  	              </a>
			  	  	           </div>
			  	  	      </div>
			  	  	      <div class="pub_downbox">
			  	  	           <div class="pub_up">
			  	  	              <p style="color:#323232"><a href="<%=basePath%>page/onlinehelp/videoPage.jsp" target="_blank"
			  	  	              onclick="sessionStorage.setItem('src','<%=basePath%>video/Statisticalchart.mp4')">操作部分：统计图表</a></p>
			  	  	              <p>易宽期货量化交易软件视频教程</p>
			  	  	           </div>
			  	  	           <div class="pub_down">
			  	  	               <a href="<%=basePath%>page/onlinehelp/videoPage.jsp" target="_blank"
			  	  	              onclick="sessionStorage.setItem('src','<%=basePath%>video/Statisticalchart.mp4')">
			  	  	                <img src="<%=basePath%>img/Yikuanintro.png"/>
			  	  	              </a>
			  	  	           </div>
			  	  	      </div>
			  	  	   
			  	  	      
			  	  	   </div>
			  	  	   <div class="layout">
			  	  	       <div class="pub_downbox">
			  	  	           <div class="pub_up">
			  	  	              <p style="color:#323232"><a href="<%=basePath%>page/onlinehelp/videoPage.jsp" target="_blank"
			  	  	              onclick="sessionStorage.setItem('src','<%=basePath%>video/windowindiva.mp4')">布局管理：窗口布局与个性化</a> </p>
			  	  	              <p>易宽期货量化交易软件视频教程</p>
			  	  	           </div>
			  	  	           <div class="pub_down">
			  	  	                <a href="<%=basePath%>page/onlinehelp/videoPage.jsp" target="_blank"
			  	  	              onclick="sessionStorage.setItem('src','<%=basePath%>video/windowindiva.mp4')">
			  	  	                <img src="<%=basePath%>img/Yikuanintro.png"/>
			  	  	              </a>
			  	  	           </div>
			  	  	      </div>
			  	  	   
			  	  	   </div> 
			  	  </div>
			  </div>
		</div>
		
		<div class="footer">
		     <div class="footerline"></div>
		     <div class="footer_cont"> 
					<iframe src="<%=basePath%>page/footer.jsp" border="0" width="100%" height="100%" frameborder="0" scrolling="no" ></iframe>
			 </div>
			 
		</div>
		<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		    $('.down_main_nav li').click(function(){
		    	var type = $(this).data("type");
				$(this).css('color','#498EFC').siblings().css('color','#909090')
				$("." + type + "").show().siblings('div').hide();
			});
		</script>
	</body>
</html>
