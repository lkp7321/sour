<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>运维管理</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/reset.css" />
<link rel="stylesheet" href="<%=basePath%>layui/css/layuichange.css"  media="all">
<link rel="stylesheet" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/statisticsmain.css" />
</head>
<body>
	<div class="navcontent">
		<div class="navHeader">
			<iframe src="<%=basePath%>page/head.jsp" border="0" width="100%"
				height="130" frameborder="0" scrolling="no" allowTransparency="true" id="frame"></iframe>
		</div> 
	</div>
	<div class="accountstatistics">
		<div class="header">
			<span>运维管理</span>
		</div>
		<div class="statcont">
			<div class="navlist">
				<ul>
						<!--	<dd id="/qtadmin${children.menuurl}">${children.menuname}</dd>  -->
						<li href="${pageContext.request.contextPath }/page/system/userManager.jsp"><a href="javascript:;">用户管理</a>
							<span>></span></li>
						<li href="${pageContext.request.contextPath }/page/system/roleManager.jsp"><a href="javascript:;">权限管理</a>
							<span>></span></li>
						<li href="${pageContext.request.contextPath }/page/marketguess/systhistoryprice.jsp"><a href="javascript:;">历史价维护</a>
							<span>></span></li>
					    <li href="${pageContext.request.contextPath }/page/system/dealday.jsp"><a href="javascript:;">交易日维护</a>
							<span>></span></li>
				</ul>
			</div>
			<div class="contright">

				<div class="acountbody">
					<iframe id='listiframe' name="listiframe" width="100%"
						class="listiframe" border="0" frameborder="0" scrolling="no"
						style="height:600px;" src="${pageContext.request.contextPath }/page/system/userManager.jsp"></iframe>
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
	<script src="<%=basePath%>layui/layui.all.js" charset="utf-8"></script>
	<script src="<%=basePath%>js/system.js"></script>
</body>
</html>
