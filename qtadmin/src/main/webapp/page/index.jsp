<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  	<meta name="format-detection" content="telephone=no"/>
  	<meta name="format-detection" content="email=no"/>  
	<link href="http://www.afcat.com.cn/favicon.ico" rel="shortcut icon">
	<title>首页</title>
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
			<iframe src="<%=basePath%>page/head.jsp" border="0" id="frame" width="100%" height="130" frameborder="0" scrolling="no" allowTransparency="true"  ></iframe>
		</div>
		</div>
	</div>
	<!--banner-->
	<div class="ban" id="ban">
		<ul>
			<li class="show_li lione">
				<div class="zhezhao"></div>
				<img src='<%=basePath%>img/bann1.png'>
				<div class="font_one">
					<p>
						自由多窗口实时监控  / 多方式多策略一键下单   / 跨期货公司   /  资金同步刷新收益一览无余
					</p>
					<h5>多账户    交易    终端</h5>
					<p class="marg_top">
						<a href='<%=basePath%>page/swDownloads.jsp?lisid=4'>下载易宽</a>
						<a href='<%=basePath%>page/swDownloads.jsp?lisid=4'>了解更多</a>
					</p>
				</div>
			</li>
			<li class='litwo'>
				<div class="zhezhao"></div>
				<img src='<%=basePath%>img/bann2.png'>
				<div class="font_two">
					<p>
						在线教程
					</p>
					<h5>不会操作? 视频教程学一遍</h5>
					<p class="marg_top">
						<a href='<%=basePath%>page/onlinehelp/videoTutorials.jsp?lisid=6'>易宽视频</a>
						<a href='<%=basePath%>page/onlinehelp/helpdocument.jsp?lisid=6'>帮助文档</a>
					</p>
				</div>
			</li>
		<p class="bann_point">
			<i class="show_point">1</i>
			<i>2</i>
		</p>
	</div>
	<div style="height:400px;width:100%;"></div>
	 <div class="footer">
		     <div class="footerline"></div>
		     <div class="footer_cont"> 
					<iframe src="<%=basePath%>page/footer.jsp" border="0" width="100%" height="100%" frameborder="0" scrolling="no" ></iframe>
			 </div>
			 
	 </div>
</body>
<script src='<%=basePath%>/js/jquery-1.9.1.min.js'></script>
<script src="<%=basePath%>/js/index.js"></script>
</html>