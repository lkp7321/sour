<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addCustom.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style type="text/css">
	
	</style>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
function add(){
var name=$("#name").val();
var cusid=$("#cusid").val();
var managerid=$("#managerid").val();
if(name==null||name==""){
alert("客户名称不能为空");
return false;
}else if(cusid==null||cusid==""){
alert("客户编号不能为空");
return false;
}else if(managerid==null||managerid==""){
alert("经理编号不能为空");
return false;
}
return true;
}

</script>
  </head>
  
  <body>
  新增客户
  <p/>
  <font color="red">${msg}</font>
  <p/>
  <form action="add.do"  onsubmit="return add();" method="post"> 
   <table border="1" cellpadding="0" cellspacing="0" width="80%">
   <tr><td>客户编号</td><td><input type="text"   name="cusid" id="cusid"/></td>
   <td>客户级别</td>
   <td><select name="level">
   <option selected>==请选择客户级别==</option>
   <option>A</option>
   <option>B</option>
   <option>C</option>
   <option>D</option>
   </select></td></tr>
    <tr><td>客户性质</td><td><input type="text" name="type" /></td>
   <td>客户行业</td><td><input type="text" name="industry" /></td></tr>
   <tr><td>客户名称</td>
   <td><input type="text" name="name" id="name"/></td>
   <td>所属部门</td>
   <td><input type="text" name="dept" /></td></tr>
   <tr><td>经理编号</td>
   <td><input type="text" name="managerid" id="managerid"/></td>
   <td>客户地域</td>
   <td><input type="text" name="area" /></td></tr>
   </table>
  <p/>
  <input type="submit" value="保存">&nbsp;&nbsp;<input type="reset" value="重置"/>
   
  </form>
  </body>
</html>
