<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	  	<meta name="format-detection" content="telephone=no"/>
	  	<meta name="format-detection" content="email=no"/>  
		<title>在线教程</title>
		<link rel="stylesheet" href='<%=basePath%>css/reset.css'>
		<link rel="stylesheet" href="<%=basePath%>css/style.css" />
		<style>
			.navs{position:fixed;}
		</style>
	</head>
	<body> 
	<!--导航部分-->
	<div class="navs">
		<div class="nav_wrap">
			<div class="w_1180">
				<iframe src="<%=basePath%>page/head.jsp" id="frame" border="0" width="100%" height="60" frameborder="0" scrolling="no" allowTransparency="true"></iframe>
			</div>
		</div>
	</div>
	<div class="ban" id="zai_ban">
		<ul>
			<li>
				<div class="zhezhao"></div>
				<img src='../img/bann2.png'>
				<div class="font_two">
					<p>
						在线教程
					</p>
					<h5>不会操作? 视频教程学一遍</h5>
					<p class="marg_top">
						<a href='<%=basePath%>page/onlinehelp/videoTutorials.jsp?lisid=6'>易宽视频</a>
						<a href='<%=basePath%>page/onlinehelp/helpdocument.jsp?lisid=6'>易宽帮助文档</a>
					</p>
				</div>
			</li>
		</ul>
		</div>
		<div style="height:400px;width:100%;"></div>
	 <div class="footer">
		     <div class="footerline"></div>
		     <div class="footer_cont"> 
					<iframe src="<%=basePath%>page/footer.jsp" border="0" width="100%" height="100%" frameborder="0" scrolling="no" ></iframe>
			 </div>
			 
	 </div>
	</body>
<script src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
<script>
	 /* var link = document.querySelector('link[rel="import"]');
     var content = link.import;

     // 从 warning.html 的文档中获取 DOM。
     var el = content.querySelector('.head');
     document.getElementsByClassName('w_1180')[0].appendChild(el.cloneNode(true)); */
</script>
</html>
