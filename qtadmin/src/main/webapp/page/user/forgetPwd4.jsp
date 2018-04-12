<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Author" contect="http://www.webqin.net">
	<title>忘记密码</title>
	<link type="text/css" href="<%=basePath%>css/forgetPwd.css" rel="stylesheet" />
</head>

<body>
	<div class="content">
	<div class="forget_nav">
	        <div class='fnav_main'>
	             <div class="nav_logo">
	                 <a href="<%=basePath%>" target='_parent' title='易宽交易'><img src="<%=basePath%>img/logo.png"></a>
	             </div>
	             <div class='nav_text'>
	                 <a href="<%=basePath%>">返回首页去登录</a>
	             </div>
	        </div>
	    </div>
		<div class="web-width">
			<div class="stepheader">
				<span>找回密码</span>
			</div>
			<div class="stepflex">
				<div class="steplist for-cur"></div>
				<div class="steplist for-cur"></div>
				<div class="steplist for-cur"></div>

				<div class="steptextbox">
					<div style="margin-left: 0;" class="steptext for-cur">
						<em>1</em><br />
						<strong>填写账户名</strong>
					</div>
					<div class="steptext for-cur">
						<em>2</em><br />
						<strong>验证身份</strong>
					</div>
					<div class="steptext for-cur">
						<em>3</em><br />
						<strong>设置新密码</strong>
					</div>
					<div class="steptext for-cur">
						<em>4</em><br />
						<strong>完成</strong>
					</div>
				</div>
			</div>
			<!--for-stepcheng/-->
			<div class="successs">
	
				<h4>密码重置成功，请您牢记密码！</h4>
				<!--
      	 <h4>请牢记您新设置的密码，返回<a href="/qtadmin/root/login.do">首页</a>！</h3>
      -->

			</div>
			<div class="reLogin">
				<a u="" href="${pageContext.request.contextPath}/user/tologin.do">重新登录</a>
			</div>
			<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"></script>
		</div>
		<!--web-width/-->
	</div>
	<!--content/-->

</body>
</html>
