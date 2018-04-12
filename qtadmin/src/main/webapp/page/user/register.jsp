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
<meta charset="utf-8" />
<title>用户注册</title>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/register.css" />
</head>
<body>
	<div id="form-header" class="header">
		<div>
			<a href="<%=basePath%>" class="logo"> </a>
			<div class="logo-title">欢迎注册</div>
			<div class="have-account">
				已有账号？ <a href="${pageContext.request.contextPath}/user/tologin.do">请登录</a>
			</div>
		</div>
	</div>
	<div class="container">
		<!--注册模块-->
		<div class="register">
			<!--用户名注册-->
			<div class="register-box">
				<!--表单项-->
				<div class="box">
					<label for="userName">用&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;名</label>
					<input type="text" name="userName" maxlength="12" id="userName"
						placeholder="您的账户名和登录名" /> <i></i>
				</div>
				<!--提示信息-->
				<div class="remind">
					<i></i> <span></span>
				</div>
			</div>
			<!--设置密码-->
			<div class="register-box">
				<!--表单项-->
				<div class="box">
					<label for="userPwd">设 置 密 码</label> <input type="password"
						name="userPwd" maxlength="20" id="userPwd"
						placeholder="建议至少使用两种字符组合" /> <i></i>
				</div>
				<!--提示信息-->
				<div class="remind">
					<i></i> <span></span>
				</div>
			</div>
			<!--确认密码-->
			<div class="register-box">
				<!--表单项-->
				<div class="box">
					<label for="confirmPwd">确 认 密 码</label> <input type="password"
						name="confirmPwd" maxlength="20" id="confirmPwd"
						placeholder="请再次输入密码" /> <i></i>
				</div>
				<!--提示信息-->
				<div class="remind">
					<i></i> <span></span>
				</div>
			</div>
			<!--邮箱-->
			<div class="register-box">
				<!--表单项-->
				<div class="box">
					<label for="email">邮 箱 验 证</label> <input type="email" name="email"
						id="email" maxlength="30" placeholder="建议使用常用邮箱" /> <i></i>
				</div>
				<!--提示信息-->
				<div class="remind">
					<i></i> <span></span>
				</div>
			</div>
			<!--手机号-->
			<div class="register-box">
				<!--表单项-->
				<div class="box">
					<label for="mobile">手&nbsp;&nbsp;&nbsp;机&nbsp;&nbsp;&nbsp;号</label>
					<input type="text" name="mobile" id="mobile" maxlength="11"
						placeholder="建议使用常用手机" /> <i></i>
				</div>
				<!--提示信息-->
				<div class="remind">
					<i></i> <span></span>
				</div>
			</div>
			<!--验证码-->
			<div class="register-box">
				<!--表单项-->
				<div class="box verify">
					<label for="code">验&nbsp;&nbsp;&nbsp;证&nbsp;&nbsp;&nbsp;码</label> <input
						type="text" name="code" id="code" autocomplete="off" maxlength="5"
						placeholder="请输入验证码" />
					<div class="verifyCode" id="verifyCode"></div>
				</div>
				<!--提示信息-->
				<div class="remind">
					<i></i> <span></span>
				</div>
			</div>
			<!--手机验证码-->
			<div class="register-box">
				<!--表单项-->
				<div class="box verify">
					<label for="phoneCode">手机验证码</label> <input type="text"
						name="phoneCode" id="phoneCode" maxlength="6" autocomplete="off"
						class="phoneCode" placeholder="请输入手机验证码" />
					<!--<button id="mobileCode" class="btn-phoneCode" type="button" value="">获取验证码</button>-->
					<input id="mobileCode" class="btn-phoneCode" type="button"
						value="获取验证码" />
				</div>
				<!--提示信息-->
				<div class="remind">
					<i></i> <span></span>
				</div>
			</div>
			<!--注册-->
			<button id="btn" type="submit">立即注册</button>
		</div>
		<div></div>
	</div>
	<script src="<%=basePath%>js/regExpManger.js" type="text/javascript"
		charset="utf-8"></script>
		<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"
		charset="utf-8"></script>
	<script src="<%=basePath%>js/gVerify.js" type="text/javascript"
		charset="utf-8"></script>
	<script src="<%=basePath%>js/register.js" type="text/javascript"
		charset="utf-8"></script>
</body>
</html>
