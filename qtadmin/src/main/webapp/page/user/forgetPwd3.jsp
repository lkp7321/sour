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
	<title>设置新密码</title>
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
				<div class="steplist  "></div>
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
					<div class="steptext">
						<em>4</em><br />
						<strong>完成</strong>
					</div>
				</div>
			</div>
			<!--for-stepcheng/-->
			<form id="form3"  
				class="forget-pwd">
				<dl>
					<!--<dt>新登录密码：</dt>
        <dd>
        	<input type="password" id="password" class="itxt" onblur="passwordBlur();" onfocus="passwordFocus();" tabindex="1" value=""/>
      	  <span class="clr"></span>
		     
             	<label class="" id="password_error"></label>
            
        </dd> 
        <div class="clears"></div>
       </dl>-->
					<dl class="newLoginPwd">
						<dt>新登录密码：</dt>
						<dd>
							<input type="password" class="newPwd" placeholder='请输入新密码'/> 
							<span id="remind" class="remind" > </span>
						</dd>

						<div class="clears"></div>
					</dl>
					<dl>
						<dt>安全程度：</dt>
						<dd class="safeDegree">
							<span class="degreeone"></span><span class="degreetwo"></span><span
								class="degreethree"></span>
						</dd>
						<div class="clears"></div>
					</dl>

					<dl class="conLoginPwd">
						<dt>确认新密码：</dt>
						<dd>
							<input class="confirmPwd" type="password" placeholder='请再次输入密码' />
							<span id="remind" class="remind"> </span>
						</dd>

						<div class="clears"></div>
					</dl>
					<div class="subtijiao">
					   <!-- <input id="nextStepThree" type="submit" value="提交" /> -->
					   <a u="${pageContext.request.contextPath}/user/toForgetPwd4.do" href="javascript:void(0);" id="nextStepThree">提交</a>
						 
					</div>
			</form>
			<!--forget-pwd/-->
		</div>
		<!--web-width/-->
	</div>
	<!--content/-->
	<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/forgetPwd3.js" type="text/javascript"
		charset="utf-8"></script>
</body>
</html>
