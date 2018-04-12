<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<title>亚联（天津）信息技术有限责任公司</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/loginstyle.css" />
<style type="text/css">
.btn_tab_login {
	float: right;
	margin-top: 48px;
}

.btn_tab_login li {
	display: inline-block;
	margin-left: 30px;
	font-size: 14px;
}

.btn_tab_login li.cur a {
	color: #d00;
}
</style>

<div id="header"></div>
<style type="text/css">
.wellogo {
	width: 100%;
	background: #fff;
	box-shadow: 0 9px 12px 0 #F3F2F3;
	height: 60px;
}

.link-box {
	width: 1202px;
	margin: auto;
}

.link-box span {
	display: inline-block;
	line-height: 60px;
}

.logo {
	display: inline-block;
	width: 100px;
	position: relative;
	top: 10px;
	margin-right: 15px;
}

.link-box p {
	float: right;
	line-height: 60px;
}

.forgetpassword {
	/* position: absolute; */
	/* left: 600px; */
	/* top: 200px; */
	display: inline-block;
	width: 100%;
	margin-top: 11px;
	text-align: right;
}

.forgetpassword a {
	font-size: 12px;
}

#weixin_login_container iframe {
	width: 158px;
	height: 158px;
}
</style>
</head>
<body>


	<div class="login-wrap">
		<div class="wellogo">
			<div class="link-box ">
				<a href="<%=basePath%>" target='_parent'> <img
					src='../img/register/logo.png' class='logo' />
				</a> <span>欢迎登录</span>
				<p>
					<span>还没账号？</span> <a
						href="${pageContext.request.contextPath}/user/toregist.do"
						class="register">请注册</a>
				</p>
			</div>
			<div class="form-box fr loginV2" style="display:block;">
				<ul class="form-tab clearfix">
					<li class="tab-li tab-li-one"><a href="javascript:;"
						tjjj="passport.login_type.wixin_qrcode">扫码登录</a></li>
					<li class="tab-li tab-li-two cur"><a href="javascript:;"
						tjjj="passport.login_type.login_name">账号登录</a></li>
				</ul>
				<div class="form-con">
					<div class="weixin-login" style="display:none;">
						<div class="wx-box clearfix">
							<a href="javascript:;" class="wx-img-box"> 
							<span class="wx-qrCode" id="qrCodeImg"></span>
							<div id="weixin_login_container" style="display:none;"></div></a>
							<img src="${pageContext.request.contextPath}/img/login/images/wx-image.png" class="wx-image">
						</div>
						<!-- <p class="wx-help">
							<a href="javascript:;" class="help-a">如何使用？</a>
						</p> -->
					</div>
					<div class="login-normal" style="display:block;">
						<form action="${pageContext.request.contextPath}/user/login.do" id="nameLoginForm"
							method="post" autocomplete="off"
							onsubmit="return false">
							<div class="form-error" style="">
								<i></i><label class="text"></label>
							</div>
							<dl class="clearfix">
								<dt>账户名：</dt>
								<dd>
									<input type="text" name="uname" placeholder="邮箱/用户名/已验证手机号"
										id="normalUser" class="input-text" autocomplete="off" />
								</dd>
							</dl>
							<dl class="top1 clearfix">
								<dt>
									密<em></em>码：
								</dt>
								<dd>
									<input type="password" name="upwd" placeholder="请输入密码 "
										id="normalPassword" class="input-text">
								</dd>
							</dl>
							<div class="forgetpassword">
								<a href="${pageContext.request.contextPath}/user/toForgetPwd.do"
									class="forget-pass" tjjj="passport.forget.password"
									target='_parent'>忘记密码？</a>
							</div>
							<div class="btn-box clearfix">

								<input id="normalSubmit" class="btn-settlement" type=button
									value="登    录" tjjj="passport.button.login"> <!-- <span class="bor_sp">span</span>
								<div style="margin:0 auto;width:300px;text-align:center;">
									<input id="qqSubmit" class="btn-qq" type="image"
										src="../img/login/images/QQLogin.png"> <input
										id="wechartSubmit" class="btn-qq" type="image"
										src="../img/login/images/WeixinLogin.png"> -->
							</div>
						</form>
</body>
</html>
<script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"  src="${pageContext.request.contextPath}/js/jquery-1.11.3.js"></script>
<script type="text/javascript">
	var _wx_server_qr_code_count = 0;
	var _wx_server_qr_code_loaded = false;
	var _qr_code_limited = '';
	var _qr_code_wait_time = 20;
	var flashQrCodeWaitingTimer = null;
	var getQrCodeStatusTimer = null;
	var getQrCodeTimer = null;
	function nameLoginCheck() {
		var loginName = $("#nameLoginForm").find("#normalUser").eq(0).val();
		var password = $("#nameLoginForm").find("#normalPassword").eq(0).val();
		if ($(".tips ").is(":visible")) {
			return false;
		}
		if (loginName == null || loginName == "") {
			showError("请输入用户名");
			return false;
		}
		if (password == null || password == "") {
			showError("请输入密码");
			return false;
		}
		if ($("#normalYzm")
				&& $("#nameLoginForm").find("#normalYzm").length > 0) {
			if ($("#normalYzm").val() == "" || $("#normalYzm").val() == null) {
				showError("请输入验证码");
				return false;
			}
		}
		sessionStorage.setItem('name', loginName);
		return true; 
	}
	//手机登陆验证
	function mobileLoginCheck() {
		var mobile = $("#mobileLoginForm").find("#partnerPhone").eq(0).val();
		var captch = $("#mobileLoginForm").find("#partnerYzm").eq(0).val();
		var code = $("#mobileLoginForm").find("#partnerJym").eq(0).val();
		if (mobile == null || mobile == '' || !(_mobile_reg).test(mobile)) {
			showError("请填写正确的手机号");
			return false;
		}
		if (captch == null || captch == "" || captch == undefined) {
			showError("请填写验证码");
			return false;
		}
		if (code == null || code == "") {
			showError("请填写校验码");
			return false;
		}
		return true;
	}

	function mobileCheck(obj) {
		if (!(_mobile_reg).test($("#partnerPhone").val())) {
			showError("请填写正确的手机号");
			return;
		} else {
			closeError();
		}
	}
	//发送短信
	function sendSms(obj) {
		alert("信息已发送  ");
	}
	function captchCheck(obj) {
		if (!(_mobile_reg).test($("#partnerPhone").val())) {
			showError("请填写正确的手机号");
			return;
		}
		var captch = $(obj).val();
		if (captch == '' || captch == null) {
			showError("请填写验证码");
		} else {
			checkCaptch(captch, function() {
				if (!$("#smsSendButton").hasClass("sending")) {
					$("#smsSendButton").removeClass("disabled");
				}
				closeError();
			}, function() {
				showError("验证码错误");
				$("#smsSendButton").addClass("disabled");
			});
		}
	}
	$(function() {
		$(".form-tab li").on("click", function() {
			var index = $(this).index();
			$(this).addClass("cur").siblings().removeClass("cur");
			$(".form-con>div").hide().eq(index).show();
			if (index == 0) {
				$(".form-foot").hide();
			} else {
				$(".form-foot").show();

			}
			$(".form-error").hide();
		});
		///划过如何使用
		$(".weixin-login .help-a").hover(function() {
			$(".wx-img-box,.wx-image").stop();
			$(this).parents(".weixin-login").find(".wx-img-box").animate({
				"marginLeft" : "15px"
			}, 300, function() {
				$(this).parents(".weixin-login").find(".wx-image").animate({
					"opacity" : 1
				}, 300);
			});
		}, function() {
			$(".wx-img-box,.wx-image").stop();
			$(this).parents(".weixin-login").find(".wx-image").stop().animate({
				"opacity" : 0
			}, 300, function() {
				$(this).parents(".weixin-login").find(".wx-img-box").animate({
					"marginLeft" : "110px"
				}, 300);
			});
		});

	});
	$('.jia_foot_open').click(function() {
		$('.footnone').slideToggle();
		$(this).find('i').toggleClass('footnow');
	});
	$('.phoneLogin').click(function() {
		$('.loginV2').hide();
		$('.shortLogin').show();
		$('.form-error').hide();
	});
	$('.backLogin').click(function() {
		$('.login-normal').show();
		$('.loginV2').show();
		$('.shortLogin').hide();
		$('.form-error').hide();
	});
	$('#normalUser,#normalPassword').focus(function(){
		$('.form-error').hide();
	});
	//开启错误提示
	function showError(error) {
		$(".form-error").find("label").html(error);
		$(".form-error").show();
	}
	//扫码登录与账号登录之间的切换;
	$('.form-tab li').click(function() {
		var txt=$(this).find('a').text();
		
		$(this).siblings().removeClass('seleed');
		$(this).addClass('seleed');
		if( txt=="扫码登录"){
			//微信登录对象;
			 var obj = new WxLogin({
				 self_redirect:false,
				 id: "qrCodeImg", 
				 appid: 'wx5519e2efae5a3f1a', 
				 scope: 'snsapi_login', 
				 redirect_uri: "http://eqtrader.afcat.com.cn/user/getcode.do",
				 state: "",
				 style: "",
				 href:"data:text/css;base64,DQpodG1sLGJvZHl7bWFyZ2luOjA7cGFkZGluZzowO30NCi5pbXBvd2VyQm94IC5xcmNvZGUge3dpZHRoOiAxNTVweDt9DQouaW1wb3dlckJveCAudGl0bGUge2Rpc3BsYXk6IG5vbmU7fQ0KLmltcG93ZXJCb3ggLmluZm8ge3dpZHRoOiAxNjBweDt9DQouc3RhdHVzX2ljb24ge2Rpc3BsYXk6IG5vbmV9DQouaW1wb3dlckJveCAuc3RhdHVzIHt0ZXh0LWFsaWduOiBjZW50ZXI7fQ0KLmltcG93ZXJCb3ggLndycF9jb2RlIC5xcmNvZGV7bWFyZ2luLXRvcDowcHg7fQ=="
			 });
		}
	});
	
	
	
	
	//点击登录
	$('#normalSubmit').click(function(){
		 nameLoginCheck(); 
		 if(nameLoginCheck()){
			 $.ajax({
					url : '${pageContext.request.contextPath}/user/login.do',
					type : 'post',
					async : true,// true 是默认的异步，false是同步
					data : {
						"uname":$('#normalUser').val(),
						"upwd":$('#normalPassword').val()
					 },
					success : function(data) {
						result = JSON.parse(data);
						//注册成功
						if (result.success) {
							window.location.href = "/qtadmin/page/index.jsp";
						} else {
							showError("您输入的账号或密码有误!"); 
						}
					},
					error : function(errorMsg) {
						 
					}
				}); 
		 }
	})
</script>

