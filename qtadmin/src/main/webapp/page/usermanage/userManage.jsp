<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="<%=basePath%>/css/reset.css" />
		<link rel="stylesheet" href="<%=basePath%>/css/style.css" />
		<style>
			.userMana{
				width: 1202px;
				height: 100%;
			    margin: auto;
			    color: #909090;
			    margin-top: 79px;
			}
			.userman_header{
				height: 70px;
				line-height: 70px;
				border-bottom: 1px solid #EBEBEB;
			}
			.userman_main{
				width: 1202px;
				height: 100%;
				margin-top: 20px;
			}
			.userman_main div{
				float: left;
			}
			.user_main_nav{
				width: 200px;
			}
			 
			.user_main_nav ul{
				background-color: #F4F5F7;
			}
			.user_main_nav ul li{
				height: 60px;
				line-height: 60px;
				text-align: center;
				font-size: 13px;
				cursor: pointer;
				font-weight: 700;
			}
			.user_main_nav ul li:hover{
				color: #498EFC;
			}
			.user_main_nav ul li span{
				float: right;
				margin-right: 20px;
			}
			.user_main_right{
				width: 902px;
				height: 528px;
				margin-left: 40px;
			}
		</style>
	</head>
	<body>
	<!--导航部分-->
		<div class="navs">
			<div class="nav_wrap" id='mark_wrap'>
				<div class="w_1180"> 
					<iframe src="<%=basePath%>page/head.jsp" border="0" width="100%" height="130" frameborder="0" scrolling="no" ></iframe>
				</div>
			</div>
		</div>
		<div class="userMana">
			<div class="userman_header">
				用户管理
			</div>
			<div class="userman_main">
				<div class="user_main_nav">
					<ul>
						<li s='${pageContext.request.contextPath}/user/toUserData.do' style="color: #498EFC;">个人资料 <span>></span></li>
						<li s='${pageContext.request.contextPath}/user/toUserAccount.do'>账户管理 <span>></span></li>
					</ul>
				</div>
				<div class="user_main_right">
					<iframe  src="${pageContext.request.contextPath}/user/toUserData.do" width="100%" height="100%" border="0" frameborder="0" scrolling="no"></iframe>
				</div>
			</div>
		</div>
		<div class="footer">
		     <div class="footerline"></div>
		     <div class="footer_cont"> 
					<iframe src="<%=basePath%>page/footer.jsp" border="0" width="100%" height="100%" frameborder="0" scrolling="no" ></iframe>
			 </div>
			 
		</div> 
		<script src="<%=basePath%>/js/jquery-1.11.3.js" type="text/javascript"> </script>
		<script type="text/javascript">
			$('.user_main_nav li').click(function(){
				$(this).css('color','#498EFC').siblings().css('color','#909090')
				$('.user_main_right iframe').attr('src',$(this).attr('s'))
			});
			var account=window.location.href.split('=')[1];
			if(account==1){
				$('.user_main_right iframe').attr('src','${pageContext.request.contextPath}/user/toUserAccount.do')
			    $('.user_main_nav li:eq(1)').css('color','#498EFC').siblings().css('color','#909090');
			}
		</script>
	</body>
</html>
