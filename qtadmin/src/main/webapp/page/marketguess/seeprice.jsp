<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>维护历史价格</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="<%=basePath%>layui/css/layuichange.css"  media="all">
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.3.js"></script>
  </head>
  
  <body>
  <table id="demo" lay-filter="test"></table>
  </body>
  <script src="<%=basePath%>layui/layui.all.js" charset="utf-8"></script>
  <script type="text/javascript">
  layui.use(['table','layer'], function(){
	  var table = layui.table,
	      layer=layui.layer,
	      id=$('html').context.URL.split('=')[1];
	  table.render({
		    elem: '#demo'
		    ,height: 315
		    ,url: '/qtadmin/market/selectpricebyidlogin?proId='+id//数据接口
		    ,cols: [[ //表头
		      {field: 'productname', title: '品种名称', width:120,align:'center'}
		      ,{field: 'year', title: '年份', width:120,align:'center'} 
		      ,{field: 'pricetype', title: '价格类型', width: 120,align:'center'}
		      //,{field: 'histortyprice', title: '历史价', width: 125, sort: true,align:'center'}
		      ,{field: 'highestprice', title: '最高价', width: 125, sort: true,align:'center'}
		      ,{field: 'lowestprice', title: '最低价', width: 125, sort: true,align:'center'}
		    ]]
		 	    ,skin: 'row' //表格风格		    
			    ,even: true
			    ,id: 'testReload'
		  });
  });
			  
  </script>
</html>
