<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'customDetail.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <p/>
 
  <form action="update.do" method="post">
  <input type="hidden" value="${custom.id}" name="id">
   <p>客户详情</p>
   <table border="1" cellpadding="0" cellspacing="0" width="80%">
   <tr><td>客户编号</td><td><input type="text" value="${custom.cusid}" name="cusid"/></td>
   <td>客户级别</td>
   <td><select name="level">
   <option selected>${custom.level}</option>
   <option>A</option>
   <option>B</option>
   <option>C</option>
   <option>D</option>
   </select></td></tr>
    <tr><td>客户性质</td><td><input type="text" name="type" value="${custom.custype}"/></td>
   <td>客户行业</td><td><input type="text" name="industry" value="${custom.cusindursty}"/></td></tr>
   <tr><td>客户名称</td>
   <td><input type="text" name="name" value="${custom.name}"/></td>
   <td>所属部门</td>
   <td><input type="text" name="dept" value="${custom. cusdept}"/></td></tr>
   <tr><td>客户经理</td>
   <td><input type="text" name="username" value="${username}"/></td>
   <td>客户地域</td>
   <td><input type="text" name="area" value="${custom.area }"/></td></tr>
   </table>
   <p>
   <input type="submit" value="修改"/>
   </form>
  </body>
</html>
