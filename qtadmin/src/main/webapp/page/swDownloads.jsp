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
		<title>软件下载</title>
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
			.contright{ width: 945px; 	height: 528px; margin-left: 40px; }
			.yd{ display:none; }
			.pub_downbox:hover{box-shadow: 0 0 15px #ccc;}
			.pub_downbox{  width:272px; height:224px; border:2px solid #EBEBEB;  margin-right: 40px; }
			.pub_downbox .pub_up{ width:220px;  margin-left: 22px;  border-bottom: 2px solid #EBEBEB; }
			.pub_downbox .pub_up p{  text-align:center;  height: 60px;  line-height: 60px; }
			.pub_downbox .pub_down{  height: 100px;  margin-left: 13px;  line-height: 100px; }
			.pub_downbox .pub_down  a{ padding: 10px 20px; cursor: pointer;  font-size: 13px;}
			.pub_downbox .pub_down .down{  margin-left: 12px;  background: #5A8BFF;  color: #fff; }
			.pub_downbox .pub_down .know{  background: #fff;color: #5A8BFF; margin-left: 20px; }
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
			  	<span>软件下载
			  	</span>
			  </div> 
			  <div class="statcont">
			  	  <div class="down_main_nav">
			  	  	 <ul>
                         <li data-type="pc" style="color: #498EFC;">PC客户端 <span>></span></li>
                         <li data-type="yd">移动客户端 <span>></span></li>
			  	  	 </ul>
			  	  </div>
			  	  <div class="contright">
			  	  	   <div class="pc">
			  	  	      <div class="pub_downbox">
			  	  	           <div class="pub_up">
			  	  	              <p style="color:#323232">软件测试环境</p>
			  	  	              <p>请先下载安装软件环境</p>
			  	  	           </div>
			  	  	           <div class="pub_down">
			  	  	              <a href="${pageContext.request.contextPath}/file/downsoft.do" class="envir_down down">点击下载</a>
			  	  	              <a class="know">了解更多</a>
			  	  	           </div>
			  	  	      </div>
			  	  	      <div class="pub_downbox">
			  	  	           <div class="pub_up">
			  	  	              <p style="color:#323232">Window客户端</p>
			  	  	              <p>需先安装环境才能正常使用</p>
			  	  	           </div>
			  	  	           <div class="pub_down">
			  	  	              <a href="${pageContext.request.contextPath}/file/downpc.do" class="window_down down">点击下载</a>
			  	  	              <a class="know">了解更多</a>
			  	  	           </div>
			  	  	      </div>
			  	  	      
			  	  	   </div>
			  	  	   <div class="yd">
			  	  	         <div class="pub_downbox">
			  	  	           <div class="pub_up">
			  	  	              <p style="color:#323232">Android版</p>
			  	  	              <p>适用于Android4.0及以上</p>
			  	  	           </div>
			  	  	           <div class="pub_down">
			  	  	              <a href="${pageContext.request.contextPath}/file/downapk.do" class="android_down down">点击下载</a>
			  	  	              <a class="know">了解更多</a>
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
