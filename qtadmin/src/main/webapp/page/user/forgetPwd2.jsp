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
<title>验证身份</title>
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
				<div class="steplist"></div>

				<div class="steptextbox">
					<div style="margin-left: 0;" class="steptext for-cur">
						<em>1</em><br />
						<strong>填写账户名</strong>
					</div>
					<div class="steptext for-cur">
						<em>2</em><br />
						<strong>验证身份</strong>
					</div>
					<div class="steptext">
						<em>3</em><br />
						<strong>设置新密码</strong>
					</div>
					<div class="steptext">
						<em>4</em><br />
						<strong>完成</strong>
					</div>
				</div>
			</div>
			<!--for-stepcheng/-->
			<form id="form2"  
				class="forget-pwd">
				<dl>
					<dt>验证方式：</dt>
					<dd>
						<select class="selyz">
							<option value="0">已验证手机</option>
							<option value="1">已验证邮箱</option>
						</select>
					</dd>
					<div class="clears"></div>
				</dl>
				<dl>
					<dt>用户名：</dt>
					<dd>
						<span class='userName'></span>
					</dd>
					<div class="clears"></div>
				</dl>
				<dl>
					<dt>已验证手机：</dt>
					<dd>
						<span class="phoneNun"></span>
						<span style="color:#498EFC;"> 如该手机已无法使用，请联系客服</span>
					</dd>

					<div class="clears"></div>
				</dl>
				</dl>
				<dl class='checkphonecode'>
					<dt>手机校验码：</dt>
					<dd>
						<input id='codeValue' type="text" placeholder='请输入校验码'/> <input id="mobileCode"
							class="btn-phoneCode" type="button" value="获取短信校验码" />
					</dd>
					<div class="clears"></div>
				</dl>
				<div class="subtijiao">
				    <!--<input type="submit" id="nextStepTwo" value="提交" />  -->
				    <a u="${pageContext.request.contextPath}/user/toForgetPwd3.do" href="javascript:void(0);" id="nextStepTwo">提交</a>
					
				</div>
			</form>
			<!--forget-pwd/-->
		</div>
		<!--web-width/-->
	</div>
	<!--content/-->
	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/forgetPwd2.js" type="text/javascript"
		charset="utf-8"></script>
</body>
</html>
