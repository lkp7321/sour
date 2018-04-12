<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang='en'>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" href='<%=basePath%>css/reset.css'>
<link rel="stylesheet" href="<%=basePath%>css/style.css" />
</head>
<body class="head">
	<div class='w_logo'>
		<a href="<%=basePath%>" target='_parent' title='易宽交易'><img src="<%=basePath%>img/logo.png"></a>
	</div>
	<ul class="li_list">
		<li class="check_li"><a href="<%=basePath%>?lisid=0" target='_parent'>首页</a></li>
		<li><a href='<%=basePath%>page/marketguss.jsp?lisid=1' target='_parent'>市场分析</a></li>
		<li><a href='${pageContext.request.contextPath }/user/toStatistics.do?lisid=2' target='_parent'>账户统计</a></li>
		<li><a href='${pageContext.request.contextPath }/revenue/toRevenueRank.do?lisid=3' target='_parent'>收益排名</a></li>
		<li><a href="<%=basePath%>page/swDownloads.jsp?lisid=4" target='_parent'>软件下载</a></li>
		<li><a href="javascript:;" target='_parent'>策略超市</a></li>
		<li><a href="<%=basePath%>page/zaixian.jsp?lisid=6" target='_parent'>在线教程</a></li>
		<c:if test="${sessionScope.role == 1}" >
		 <li><a href="<%=basePath%>page/system/systemmenu.jsp?lisid=7" target='_parent'>运维管理</a></li>
		</c:if>
	</ul>
	<div class="sear_logo">
		<div class="sear_compon">
			<img src='<%=basePath%>img/search.png'  class="search_btn"/>
			<input type="text" class="seartxt_btn" placeholder='请输入搜索内容'/>
		</div>
		<div class="logon_compon">
			<div class="logon_comm">
				 <c:choose>
					<c:when test="${sessionScope.name != null}">
					   <a style="color: inherit" href="<%=basePath%>user/toUserManage.do" target='_parent' class="logon">
							<img src='<%=basePath%>img/acountnum.png' />
							<span class="login_name">${sessionScope.name}</span>
						</a> 
					</c:when>
					<c:otherwise>
					     <a style="color: inherit" href="${pageContext.request.contextPath }/user/tologin.do" target='_parent' class="logon">
							<img src='<%=basePath%>img/acountnum.png' />
							<span class="login_name">登录</span>
						</a> 
					</c:otherwise>
			    </c:choose>
			</div>
			<c:choose>
				<c:when test="${sessionScope.name != null}">
					| <a style="color: inherit" href="javascript:;" class="exitout" onclick="backfa();">退出</a>
				</c:when>
				<c:otherwise>
					| <a style="color: inherit" href="<%=basePath%>user/toregist.do" target="_parent">注册</a>
				</c:otherwise>
		    </c:choose>
			 
		</div>
	</div>
</body>
<script src='<%=basePath%>js/jquery-1.9.1.min.js'></script>
<script>
	var list_id=window.parent.location.href.split('=')[1],
		userName='${sessionScope.name}' ;
	$('.li_list li').siblings().removeClass('check_li');
	if( list_id ){
		$('.li_list li:eq('+list_id+')').addClass('check_li');
	}else{
		$('.li_list li:eq(0)').addClass('check_li');
	}
	function backfa(){
		 $.ajax({
			url:"<%=basePath%>/user/logout.do",
			async:false,
			success:function(data){
				window.parent.open("<%=basePath%>?lisid=0",'_self')
			},
			error:function(){
				
			}
		});
	}
	//搜索框输入查询；
	$('.seartxt_btn').keyup(function(e){
		var txt=encodeURI( $(this).val() );
		var e=e.witch||e.keyCode;
		if( e==13){
			if( txt==''){
			}else{
				window.open("<%=basePath%>common/toSearch.do?sear="+txt,'_target');
			}
		}
	});
	//点击搜索按钮；
	$('.search_btn').click(function(){
		$('.sear_compon .seartxt_btn').css({'width':'150px','border-width':'1px'});
	});
	
 </script>
</html>
