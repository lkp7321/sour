<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>My JSP 'customList.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<style type="text/css">
a {
	text-decoration: none;
	color: blue;
}
</style>
		<script type="text/javascript">
function checkAll(){
var all=document.getElementById("all");
var every=document.getElementsByName("del");
//全选
for(var i=0;i<every.length;i++){
every[i].checked=true;
}
//取消全选
if(all.checked==false){
for(var i=0;i<every.length;i++){
every[i].checked=false;
}
}
}

//如果有一个不选中则取消全选的选中状态
function change(){
var flag=false;
var all=document.getElementById("all");
var every=document.getElementsByName("del");
   for(var i=0;i<every.length;i++){
    if(every[i].checked==false){
    flag=true
    }
}
 if(flag){
 all.checked=false;
 }else{
 all.checked=true;
 }
}
</script>
	</head>
	<body>
		<p></p>

		<form action="list.do?currPage=1" method="post">&nbsp; 请输入用户id: 
			<input type="text" name="cusId" value=${custom.cusid}>
			<input type="submit" value="搜索" />
		</form>
		<form action="delete.do" method="post"
			onsubmit="return confirm('是否删除？')">
			&nbsp;&nbsp;<input type="submit" value="删除">
			&nbsp;&nbsp;
			
			<a href="addCustom.jsp"><input type="button" value="新增"></a>
			<p/>
				<table border="1" cellpadding="0" cellspacing="0" width="60%">
					<tr>
						<td>
							<input type="checkbox" id="all" onclick="checkAll();"/>全选
						</td>
						<td>
							客户id
						</td>
						<td>
							客户名称
						</td>
						<td>
							经理名称
						</td>
						<td>
							客户等级
						</td>
						<td>
							所属地域
						</td>
					</tr>
					<c:forEach var="custom" items="${pager.list}">
						<tr>
							<td>
								<input type="checkbox" name="del" value="${custom.id}"
									onchange="change();" />
							</td>
							<td>
								${custom.cusid }
							</td>
							<td>
								<a href="modify.do?id=${custom.id}">${custom.name }</a>
							</td>
							<td>
								${username}
							</td>
							<td>
								${custom.level }
							</td>
							<td>
								${custom.area}
							</td>
						</tr>
					</c:forEach>
				</table>
		</form>
		<p />
			共${allPage }页 &nbsp;
			<a href="list.do?currPage=${pager.curPage-1}"
				onclick="return ${pager.curPage>1}" id="a1">上一页</a>
			&nbsp;当前是第${pager.curPage}页&nbsp;
			<a href="list.do?currPage=${pager.curPage+1}"
				onclick="return ${pager.curPage<allPage}" id="a1">下一页</a>
	</body>
</html>
