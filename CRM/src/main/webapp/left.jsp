<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'left.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery.min.js"></script>
<style type="text/css">
ul{
list-style:none;
}
.c1{
display:none;
}
.c2{
display:show;
}
a{
text-decoration:none;
}
</style>
<script type="text/javascript">
$(function(){
$("#l").click(function(){
$("#u").removeClass("c1").addClass("c2");
});
});

</script>
  </head>
  
  <body>
    <ul>
    <li>市场商机管理</li>
    <li id="l">客户信息管理
    <ul id="u" class="c1">
    <li><a href="list.do?currPage=1" target="right">我负责的客户</a></li>
    <li>我有权的客户</li>
    <li>所有客户</li>
    </ul>
    
    </li>
    <li>销售管理</li>
    </ul>
  </body>
</html>
