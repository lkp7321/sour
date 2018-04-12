<%@ page language="java" import="java.util.*"  pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="text/html; utf-8">
<title>收益排名</title>
<link rel="stylesheet" href='<%=basePath%>css/reset.css'>
<link rel="stylesheet" href="<%=basePath%>css/style.css" />
<link rel="stylesheet" href="<%=basePath%>layui/css/layuichange.css"  media="all">
<style>
  .mark_data{margin-top: -10px;}
</style>
</head>
<body>
<!--导航部分-->
<div class="navs">
	<div class="nav_wrap" id='mark_wrap'>
		<div class="w_1180"> 
			<iframe src="<%=basePath%>page/head.jsp" id="frame" border="0" width="100%" height="130" frameborder="0" scrolling="no" allowTransparency="true"></iframe>
		</div>
	</div>
</div>
<!-- 页面主体部分-->
<div class="main_body">
	<p class="mark_guss"><span>收益排名</span></p>
	<div class="mark_main">
		<ul>
			<li class="sel_li" data-src="${pageContext.request.contextPath }/page/revenuerank/revenueRank.jsp"><a>综合排名</a><span>></span></li>
		</ul>
		<div class="mark_data">
			<iframe id="change_src" src="${pageContext.request.contextPath }/page/revenuerank/revenueRank.jsp" frameborder="no" width='100%' height='500'></iframe>
		</div>
	</div>
</div>
<!-- 尾部-->
<div class="footer">
		     <div class="footerline"></div>
		     <div class="footer_cont"> 
					<iframe src="<%=basePath%>page/footer.jsp" border="0" width="100%" height="100%" frameborder="0" scrolling="no" ></iframe>
			 </div>
			 
		</div>
</body>
<script src='<%=basePath%>/js/jquery-1.9.1.min.js'></script>
<script src="<%=basePath%>layui/layui.all.js" charset="utf-8"></script>
<script>
//点击左侧list;
$('.mark_main ul li').click(function(){
	 $(this).siblings().removeClass('sel_li');
	 $(this).addClass('sel_li');
	 $('#change_src').attr( 'src',$(this).data('src') );
});
</script>
</html>