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
<title>找回密码</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/reset.css"/>
<link type="text/css" href="<%=basePath%>css/forgetPwd.css"
	rel="stylesheet" />

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
				<div class="steplist"></div>
				<div class="steplist"></div>

				<div class="steptextbox">
					<div style="margin-left: 0;" class="steptext for-cur">
						<em>1</em><br /> <strong>填写账户名</strong>
					</div>
					<div class="steptext">
						<em>2</em><br /> <strong>验证身份</strong>
					</div>
					<div class="steptext">
						<em>3</em><br /> <strong>设置新密码</strong>
					</div>
					<div class="steptext">
						<em>4</em><br /> <strong>完成</strong>
					</div>
				</div>
			</div>
			<form id="form1"  
				class="forget-pwd">
				<dl class=testName>
					<dt>账户名：</dt>
					<dd>
						<input type="text" tabindex="1" id="userName" name="userName"
							class="itxt" placeholder="用户名" />
						<!--提示信息-->
						 
					</dd>
					<div class="clears"></div>
				</dl>
				<dl class="testCode">
					<dt>验证码：</dt>
					<dd>
						<input type="text" name="authCode" id="authCode" tabindex="2"
							autocomplete="off" maxlength="5" class="itxt"
							placeholder="请输入验证码" />
						<div class="verifyCode" id="verifyCode"></div>
						<div class="coderepeats" id="coderepeats">
							<span>看不清？</span><span style="color:#498EFC;">下一张</span>
						</div>

						<!--提示信息-->
						<span id="remind" class="remind"> </span>
					</dd>
					<div class="clears"></div>
				</dl>
				<div class="subtijiao">
				    <!--<input id="nextStepOne" type="submit" value="提交" />  -->
				    <!--<a href="${pageContext.request.contextPath}/user/toForgetPwd2.do" id="nextStepOne">提交</a>-->
					 <a u="${pageContext.request.contextPath}/user/toForgetPwd2.do" href="javascript:void(0);" id="nextStepOne">提交</a> 
					
				</div>
			</form>
			<!--forget-pwd/-->
		</div>
		<!--web-width/-->
	</div>
	<!--content/-->
	<script src="<%=basePath%>js/jquery-1.9.1.min.js"
		type="text/javascript"></script>
	<script src="<%=basePath%>js/gVerify.js" type="text/javascript"
		charset="utf-8"></script>
	<script src="<%=basePath%>js/forgetPwd.js" type="text/javascript"
		charset="utf-8"></script>
</body>
</html>
