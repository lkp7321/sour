<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="UTF-8">
		<title></title>
		<style type="text/css">
			.registerted{
				width: 1202px;
				height: 100%;
			    margin: auto;
			    
			}
			.reg_nav{
			   background: url(<%=basePath%>css/img/nav_bg.png);
			 }
			.navHeader{
			   width: 1202px;
			   margin: auto;
			}
			.reg_header{
				height: 70px;
				line-height: 70px;
				border-bottom: 1px solid #EBEBEB;
			}
			.cont_text{
				width: 40%;
				margin: auto;
				margin-top: 100px;
				
			}
			.cont_text div{
				float: left;
				margin-left: 10px;
			}
			
			.cont_icon{
				margin-top: 15px;
			}
			.cont_remind a{
				text-decoration: none;
				margin-right: 10px;
				color:#498EFC;
				font-size: 14px;
                font-weight: 700;
			}
		</style>
	</head>
	<body>
	    <div class="reg_nav">
			   <div class="navHeader">
			 	 <iframe src="${pageContext.request.contextPath}/page/head.jsp" border="0" width="100%" height="60" frameborder="0" scrolling="no" ></iframe>
			   </div>	
		     </div>
		<div class="registerted">
		     
			 <div class="reg_header">
			 	 注册成功！
			 </div>
			 <div class="reg_content">
			 	  <div class="cont_text">
			 	  	 <div class="cont_icon">
			 	  	 	<img src="<%=basePath%>img/register/registed.png" width="40px" height="40px" alt="" />
			 	  	 </div>
			 	  	 <div class="cont_remind">
			 	  	 	<p>感谢您注册易宽交易 现在讲以新手身份登录站点</p>
			 	  	 	<p><a href="${pageContext.request.contextPath}/user/toUserManage.do">马上去完善资料</a><a href="${pageContext.request.contextPath}">先去逛逛</a></p>
			 	  	 	<p><a href="${pageContext.request.contextPath}">如果您的浏览器没有跳转，请点击此链接</a></p>
			 	  	 </div>
			 	  </div>
			 </div>
		
		</div>
		<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"> </script>
		<script type="text/javascript">
			function goIndex(){
				window.location.href='${pageContext.request.contextPath}'
			}
			setTimeout('goIndex()',5000)
		</script>
	</body>
	
</html>
