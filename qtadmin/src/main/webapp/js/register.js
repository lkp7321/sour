var userName = document.getElementById("userName");// 获取用户名节点对象
var userPwd = document.getElementById("userPwd");// 获取密码节点对象
var confirmPwd = document.getElementById("confirmPwd");// 获取确认密码节点对象
var email = document.getElementById("email");// 获取邮箱节点对象
var mobile = document.getElementById("mobile");// 获取手机号节点对象
var code = document.getElementById("code");// 获取验证码节点对象
var phoneCode = document.getElementById('phoneCode');// 获取手机验证码文本节点对象
var mobileCode = document.getElementById('mobileCode');// 获取手机验证码按钮节点对象
var btn = document.getElementById("btn");// 获取注册按钮节点对象
var verifyCode = document.getElementById("verifyCode");// 获取生成的验证码节点对象
var userNameflag = 0;// 判断用户名是否占用标识，0：未占用；1：已占用
var emailFlag = 0;// 判断邮箱是否占用标识，0：未占用；1：已占用
var phoneFlag = 0;// 判断手机号是否占用标识，0：未占用；1：已占用
var identifyingcode;
var  phoneCodeFlag = 0;

/*----------------------------校验用户名----------------------------*/
function checkUserName(e) {
	var _e = window.event || e;
	var box = userName.parentNode;// 获取父节点
	var remind = box.nextElementSibling;// 下一个兄弟节点
	var span = remind.lastElementChild;// 获取remind中的span节点
	var v =$.trim(userName.value);
	/* 获取焦点 */
	if (_e) {
		if (_e.type == "focus") {
			if (v.length == 0) {
				box.className = "box";
				remind.className = "remind default";
				span.innerHTML = "支持字母、数字、-、_的组合，4-12个字符";
				return false;
			}
		}
		if (_e.type == "blur") {
			if (v.length == 0) {
				box.className = "box";
				remind.className = "remind error";
				span.innerHTML = "请输入用户名";
				return false;
			} else {
				judgeUserName();
				// 判断用户名是否存在
				if (userNameflag == 0) {
					box.className = "box right";
					remind.className = "remind hide";
					if (regExpManger.userNameReg.test(v)) {
						return true; 
					} else {
						box.className = "box error";
						remind.className = "remind error";
						span.innerHTML = "格式错误，仅支持字母、数字、-、_的组合，4-12个字符";
						return false;
					}
					
				} else {
					box.className = "box error";
					remind.className = "remind error";
					span.innerHTML = "该用户名已占用";
					return false;
				}
			}
		}
	}
	/* 其他事件 */
	if (v.length == 0) {
		box.className = "box error";
		remind.className = "remind error";
		span.innerHTML = "请输入用户名";
		return false;
	} else {
		// 判断用户名是否存在
		judgeUserName();
		if (userNameflag == 0) {
			box.className = "box right";
			remind.className = "remind hide";
			if (regExpManger.userNameReg.test(v)) {
				if (v.length >= 4 && v.length <= 12) {
					box.className = "box right";
					remind.className = "remind hide";
					return true;
				} else {
					box.className = "box error";
					remind.className = "remind error";
					span.innerHTML = "长度4-12个字符";
					return false;
				}
			} else {
				box.className = "box error";
				remind.className = "remind error";
				span.innerHTML = "格式错误，仅支持字母、数字、-、_的组合，4-12个字符";
				return false;
			}
		} else {
			box.className = "box error";
			remind.className = "remind error";
			span.innerHTML = "该用户名已占用";
			return false;
		}
		
	}
}
userName.onfocus = checkUserName;// 获得焦点
userName.onblur = checkUserName;// 失去焦点
//userName.onkeyup = checkUserName;// 按键弹起

/*----------------------------校验密码----------------------------*/
function checkPwd(e) {
	var _e = window.event || e;
	var box = userPwd.parentNode;// 获取父节点
	var remind = box.nextElementSibling;// 下一个兄弟节点
	var span = remind.lastElementChild;// 获取remind中的span节点
	var v = userPwd.value;
	/* 获取焦点 */
	if (_e) {
		if (_e.type == "focus") {
			if (v.length == 0) {
				box.className = "box";
				remind.className = "remind default";
				span.innerHTML = "建议使用数字、字母和符号两种以上的组合，6-20个字符";
				return false;
			}
		}
		if (_e.type == "blur") {
			if (v.length == 0) {
				/*box.className = "box";
				remind.className = "remind hide";*/
				box.className = "box error";
				remind.className = "remind error";
				span.innerHTML = "请输入密码";
				return false;
			}
		}
	}
	/* 其他事件 */
	if (v.length == 0) {
		box.className = "box error";
		remind.className = "remind error";
		span.innerHTML = "请输入密码";
		return false;
	} else {
		/* 验证密码长度和安全等级 */
		if (v.length >= 6 && v.length <= 20) {
			box.className = "box right";
			var level = 0;
			if (regExpManger.regNum.test(v)) {
				level++;
			}
			if (regExpManger.regWord.test(v)) {
				level++;
			}
			if (regExpManger.regOther.test(v)) {
				level++;
			}
			switch (level) {
			case 1:
				remind.className = "remind ruo";
				span.innerHTML = "有被盗风险,建议使用字母、数字和符号两种及以上组合";
				break;
			case 2:
				remind.className = "remind zhong";
				span.innerHTML = "密码强度中，可以使用三种以上的组合来提高安全强度";
				break;
			case 3:
				remind.className = "remind qiang";
				span.innerHTML = "你的密码很安全";
				break;
			default:
				remind.className = "remind ruo";
				span.innerHTML = "有被盗风险,建议使用字母、数字和符号两种及以上组合";
			}
			return true;
		} else {
			box.className = "box error";
			remind.className = "remind error";
			span.innerHTML = "密码长度不符合";
			return false;
		}
	}
}
userPwd.onfocus = checkPwd;// 获得焦点
userPwd.onblur = checkPwd;// 失去焦点
userPwd.onkeyup = checkPwd;// 按键弹起

/*----------------------------校验确认密码----------------------------*/
function checkConfirmPwd(e) {
	var _e = window.event || e;
	var box = confirmPwd.parentNode;// 获取父节点
	var remind = box.nextElementSibling;// 下一个兄弟节点
	var span = remind.lastElementChild;// 获取remind中的span节点
	var v = confirmPwd.value;
	/* 获取焦点 */
	if (_e) {
		if (_e.type == "focus") {
			if (v.length == 0) {
				box.className = "box";
				remind.className = "remind default";
				span.innerHTML = "请确保密码一致";
				return false;
			}
		}
		if (_e.type == "blur") {
			if (v.length == 0) {
				/*box.className = "box";
				remind.className = "remind hide";*/
				box.className = "box error";
				remind.className = "remind error";
				span.innerHTML = "请再次输入密码";
				return false;
			}
		}
	}
	/* 其他事件 */
	if (v.length == 0) {
		box.className = "box error";
		remind.className = "remind error";
		span.innerHTML = "请再次输入密码";
		return false;
	} else {
		if (v.length >= 6 && v.length <= 20) {
			if (v == userPwd.value) {
				box.className = "box right";
				remind.className = "remind hide";
				return true;
			} else {
				box.className = "box error";
				remind.className = "remind error";
				span.innerHTML = "两次输入密码不同，请确保密码一致";
				return false;
			}
		} else {
			box.className = "box error";
			remind.className = "remind error";
			span.innerHTML = "您输入的密码位数有误";
			return false;
		}
	}
}
confirmPwd.onfocus = checkConfirmPwd;// 获得焦点
confirmPwd.onblur = checkConfirmPwd;// 失去焦点
confirmPwd.onkeyup = checkConfirmPwd;// 按键弹起

/*----------------------------校验邮箱----------------------------*/
function checkEmail(e) {
	var _e = window.event || e;
	var box = email.parentNode;// 获取父节点
	var remind = box.nextElementSibling;// 下一个兄弟节点
	var span = remind.lastElementChild;// 获取remind中的span节点
	var v = $.trim(email.value);
	/* 获取焦点 */
	if (_e) {
		if (_e.type == "focus") {
			if (v.length == 0) {
				box.className = "box";
				remind.className = "remind default";
				span.innerHTML = "完成验证后，你可以用该邮箱登录和找回密码";
				return false;
			}
		}
		if (_e.type == "blur") {
			if (v.length == 0) {
				box.className = "box";
				remind.className = "remind hide";
				return false;
			} else {
				// 判断邮箱是否存在
				if (judgeUserEmail() == 0) {
					box.className = "box right";
					remind.className = "remind hide";
					return true;
				} else {
					box.className = "box error";
					remind.className = "remind error";
					span.innerHTML = "该邮箱已占用";
					return false;
				}
			}
		}
	}
	/* 其他事件 */
	if (v.length == 0) {
		box.className = "box error";
		remind.className = "remind error";
		span.innerHTML = "完成验证后，你可以用该邮箱登录和找回密码";
		return false;
	} else {
		if (regExpManger.emailReg.test(v)) {
			if (v.length >= 6 && v.length <= 20) {
				box.className = "box right";
				remind.className = "remind hide";
				return true;

			} else {
				box.className = "box error";
				remind.className = "remind error";
				span.innerHTML = "长度6-20个字符";
				return false;
			}
		} else {
			box.className = "box error";
			remind.className = "remind error";
			span.innerHTML = "您输入的邮箱有误";
			return false;
		}
	}
}
email.onfocus = checkEmail;// 获得焦点
email.onblur = checkEmail;// 失去焦点
email.onkeyup = checkEmail;// 按键弹起

/*----------------------------校验手机号----------------------------*/
function checkMobile(e) {
	var _e = window.event || e;
	var box = mobile.parentNode;// 获取父节点
	var remind = box.nextElementSibling;// 下一个兄弟节点
	var span = remind.lastElementChild;// 获取remind中的span节点
	var v = $.trim(mobile.value);
	/* 获取焦点 */
	if (_e) {
		if (_e.type == "focus") {
			if (v.length == 0) {
				box.className = "box";
				remind.className = "remind default";
				span.innerHTML = "完成验证后，你可以用该手机找回密码";
				return false;
			}
		}
		if (_e.type == "blur") {
			if (v.length == 0) {
				box.className = "box error";
				remind.className = "remind error";
				span.innerHTML = "请输入手机号";
				return false;
			} else {
				// 判断手机号是否存在
				if (judgeUserPhone() == 0) {
					box.className = "box right";
					remind.className = "remind hide";
					return true;
				} else {
					box.className = "box error";
					remind.className = "remind error";
					span.innerHTML = "该手机号码已占用";
					return false;
				}
			}
		}
	}
	/* 其他事件 */
	if (v.length == 0) {
		box.className = "box error";
		remind.className = "remind error";
		span.innerHTML = "请输入手机号";
		return false;
	} else {
		if (regExpManger.mobilReg.test(v)) {
			if (v.length == 11) {
				box.className = "box right";
				remind.className = "remind hide";
				return true;

			} else {
				box.className = "box error";
				remind.className = "remind error";
				span.innerHTML = "请检查输入的手机号";
				return false;
			}
		} else {
			box.className = "box error";
			remind.className = "remind error";
			span.innerHTML = "非正常手机号，请重新输入";
			return false;
		}
	}
}
mobile.onfocus = checkMobile;// 获得焦点
mobile.onblur = checkMobile;// 失去焦点
mobile.onkeyup = checkMobile;// 按键弹起

/*----------------------------校验验证码----------------------------*/
// 验证码的实现
var veriFyCode = new GVerify('verifyCode');
// 点击更换验证码
$('#verifyCode').click(function() {
	veriFyCode.refresh();

})
function checkCode(e) {
	var _e = window.event || e;
	var box = code.parentNode;// 获取父节点
	var remind = box.nextElementSibling;// 下一个兄弟节点
	var span = remind.lastElementChild;// 获取remind中的span节点
	var v = code.value;
	/* 获取焦点 */
	if (_e) {
		if (_e.type == "focus") {
			if (v.length == 0) {
				box.className = "box verify";
				remind.className = "remind default";
				span.innerHTML = "请正确输入验证码";
				return false;
			}
		}
		if (_e.type == "blur") {
			if (v.length == 0) {
				box.className = "box verify";
				remind.className = "remind hide";
				return false;
			} else {
				if (veriFyCode.validate(v)) {
					box.className = "box verify right";
					remind.className = "remind hide";
					return true;
				} else {
					box.className = "box verify error";
					remind.className = "remind error";
					span.innerHTML = "您输入的验证码有误";
					return false;
				}
			}
		}
	}
	/* 其他事件 */
	if (v.length == 0) {
		box.className = "box verify error";
		remind.className = "remind error";
		span.innerHTML = "请输入验证码";
		return false;
	} else {
			return true;
	}
}
code.onfocus = checkCode;// 获得焦点
code.onblur = checkCode;// 失去焦点
// code.onkeyup = checkCode;// 按键弹起

/*----------------------------校验手机验证码----------------------------*/
function checkMobileCode(e) {
	var _e = window.event || e;
	var box = phoneCode.parentNode;// 获取父节点
	var remind = box.nextElementSibling;// 下一个兄弟节点
	var span = remind.lastElementChild;// 获取remind中的span节点
	var v = phoneCode.value;
	var telphone=$('#mobile').val();
	var bizid= sessionStorage.getItem('bizid'),
	    code= sessionStorage.getItem('code'),
	    phone= sessionStorage.getItem('phone'),
	    sendDate= sessionStorage.getItem('sendDate');
	
	
	/* 获取焦点 */
	if (_e) {
		if (_e.type == "focus") {
			if (v.length == 0) {
				box.className = "box verify";
				remind.className = "remind default";
				span.innerHTML = "请输入短信验证码";
				return false;
			}
		}
		if (_e.type == "blur") {
			 if (v.length == 0) {
				box.className = "box error";
				remind.className = "remind error";
				span.innerHTML = "验证码不能为空";
				return false;
			}else if(telphone==phone && v==code){
				/*通过后台校验当前手机与验证码是否一致*/
				$.ajax({
	    			url : '/qtadmin/sms/checkSMS.do',
	    			type : 'post',
	    			async : false,// true 是默认的异步，false是同步
	    			data : {
	    					"phoneNum":phone,
	    					"phoneCode":code,
	    				    "bizid":bizid,
	    				    "sendDate":sendDate,
	    				    "phone":telphone,
	    				    "code":v
				     },
	    			success : function(data) {
	    				var result = JSON.parse(data); 
	    			  if(result.success){//判断条件根据后台传回的修改！！！
	    				  box.className = "box verify";
    					  remind.className = "remind hide";
    					  return true; 
	    			  }else{
	    				  box.className = "box error";
	    				  remind.className = "remind error";
	    				  span.innerHTML = "验证码输入有误！";
	    				  return false;
	    			  }
	    				 
	    			} 
	    		});
				
			}else{
				box.className = "box error";
				remind.className = "remind error";
				span.innerHTML = "验证码不正确或已过期，请重新获取";
				return false;
			}
			
			 
		}
	}
	/* 其他事件 */
	if (v.length == 0) {
		box.className = "box verify error";
		remind.className = "remind error";
		span.innerHTML = "请输入短信验证码";
		return false;
	}else if(telphone==phone && v==code){
		judgePhoneCode();
		if( phoneCodeFlag == 0){
			box.className = "box verify";
			  remind.className = "remind hide";
			  return true;
		}else{
			 box.className = "box error";
			  remind.className = "remind error";
			  span.innerHTML = "验证码输入有误！";
			  return false;
		}
		/*点击注册的时候     通过后台校验当前手机与验证码是否一致*/
		/*$.ajax({
			url : '/qtadmin/sms/checkSMS.do',
			type : 'post',
			async : false,// true 是默认的异步，false是同步
			data : {"phone":telphone,
				    "code":v,
				    "bizid":bizid,
				    "phoneCode":code,
				    "phoneNum":phone,
				    "sendDate":sendDate
				   },
			success : function(data) {
			  var result = JSON.parse(data); 
			  if(result.success){//判断条件根据后台传回的修改！！！
				  box.className = "box verify";
				  remind.className = "remind hide";
				  return true;
			  }else{
				  box.className = "box error";
				  remind.className = "remind error";
				  span.innerHTML = "验证码输入有误！";
				  return false;
			  }
				 
			} 
		});*/
	}else{
		box.className = "box error";
		remind.className = "remind error";
		span.innerHTML = "验证码不正确或已过期，请重新获取";
		return false;
	}
	
 
}
phoneCode.onfocus = checkMobileCode;// 获得焦点
phoneCode.onblur = checkMobileCode;// 失去焦点
//phoneCode.onkeyup = checkMobileCode;// 按键弹起

/*----------------------------获取短信验证码----------------------------*/
function getModileCode(e) {
	 if ( checkMobile() ) {
		times = 60;
		timer = null;
		mobileCode.onclick = function() {
			// 计时开始
			var that = this;
			this.disabled = true;
			timer = setInterval(function() {
				times--;
				that.value = times + "秒后重新获取";
				if (times <= 0) {
					that.disabled = false;
					that.value = "获取验证码";
					clearInterval(timer);
					times = 60;
				} 
			}, 1000);
		}
	}
}

//mobileCode.onfocus = getModileCode;// 获得焦点
//mobileCode.onblur = getModileCode;// 失去焦点
//mobileCode.onkeyup = getModileCode;// 按键弹起

function judgeUserName() {
	var userName = $('#userName').val();
	$.ajax({
		url : '/qtadmin/user/getUserName.do',
		type : 'post',
		async : false,// true 是默认的异步，false是同步
		data : {
			"username" : userName
		},
		success : function(data) {
			var jsonObj = JSON.parse(data);
			if (jsonObj.code === 1) {
				userNameflag = 1;// 用户名已占用
			} else {
				userNameflag = 0;// 用户名未存在
			}
		}
	});
	return userNameflag;
}

function judgeUserPhone() {
	var mobile = $('#mobile').val();
	$.ajax({
		url : '/qtadmin/user/getUserPhone.do',
		type : 'post',
		async : false,// true 是默认的异步，false是同步
		data : {
			"phonenumber" : mobile
		},
		success : function(data) {
			var jsonObj = JSON.parse(data);
			if (jsonObj.code === 1) {
				phoneFlag = 1;// 手机号已占用
			} else {
				phoneFlag = 0;// 手机号不存在
			}
		}
	});
	return phoneFlag;
}
function judgePhoneCode(){
	var telphone=$('#mobile').val(),
	    v=$('#phoneCode').val();
	var bizid= sessionStorage.getItem('bizid'),
	    code= sessionStorage.getItem('code'),
	    phone= sessionStorage.getItem('phone'),
	    sendDate= sessionStorage.getItem('sendDate');
	$.ajax({
		url : '/qtadmin/sms/checkSMS.do',
		type : 'post',
		async : false,// true 是默认的异步，false是同步
		data : {"phone":telphone,
			    "code":v,
			    "bizid":bizid,
			    "phoneCode":code,
			    "phoneNum":phone,
			    "sendDate":sendDate
			   },
		success : function(data) {
		  var result = JSON.parse(data); 
		  if(result.success){//判断条件根据后台传回的修改！！！
			  phoneCodeFlag = 0;
		  }else{
			  phoneCodeFlag = 1;
		  }
			 
		} 
	});
	return  phoneCodeFlag;
}

function judgeUserEmail() {
	var email = $('#email').val();
	$.ajax({
		url : '/qtadmin/user/getUserEmail.do',
		type : 'post',
		async : false,// true 是默认的异步，false是同步
		data : {
			"email" : email
		},
		success : function(data) {
			var jsonObj = JSON.parse(data);
			if (jsonObj.code === 1) {
				emailFlag = 1;// 邮箱已占用
			} else {
				emailFlag = 0;// 邮箱不存在
			}
		}
	});
	return emailFlag;
}
//点击获取手机短信验证码
$('#mobileCode').click(function(){
	var times = 60, timer = null; 
    if( checkMobile() ){
    	var that = this;
		this.disabled = true;
		var telphone=$('#mobile').val();
    	if(telphone){
    		$.ajax({
    			url : '/qtadmin/sms/sendSMS.do',
    			type : 'post',
    			async : false,// true 是默认的异步，false是同步
    			data : {"phone":telphone},
    			success : function(data) {
    				var result = JSON.parse(data);
    				//if(result.success){
    					sessionStorage.setItem('bizid',result.bizid)
    					sessionStorage.setItem('code',result.code)
    					sessionStorage.setItem('phone',result.phone)
    					sessionStorage.setItem('sendDate',result.sendDate)
    					timer = setInterval(function() {
        					times--;
        					that.value = times + "秒后重新获取";
        					if (times <= 0) {
        						that.disabled = false;
        						that.value = "获取验证码";
        						clearInterval(timer);
        						times = 60;
        					} 
        				}, 1000);
    				//}
    			 } 
    		});
    	}
	}
	
  })

/*----------------------------点击注册按钮----------------------------*/
btn.onclick = function() {
	  
	 if (checkUserName() && checkPwd() && checkConfirmPwd() && checkEmail()
			&& checkMobile() && checkCode()  && checkMobileCode()) {
		var userName = $('#userName').val();
		var userPwd = $('#userPwd').val();
		var mobile = $('#mobile').val();
		var email = $('#email').val();
		
		var result;
		$.ajax({
			url : '/qtadmin/user/addUser.do',
			type : 'post',
			async : false,// true 是默认的异步，false是同步
			data : {
				"username":userName,
				"password":userPwd,
				"phonenumber":mobile,
				"email":email
			},
			success : function(data) {
				if (data == "") {
					window.location.href = "/qtadmin/page/empty.jsp";
				}
				result = JSON.parse(data);
				//注册成功
				if (result.success) {
					submitName(userName,userPwd)
					window.location.href = "/qtadmin/user/toRegisterSuccess.do";
				} else {
					window.location.href = "/qtadmin/page/empty.jsp";
				}
			},
			error : function(errorMsg) {
				alert("[发呆]加载数据失败！");
				window.location.href = "/qtadmin/page/empty.jsp";
			}
		});
	}
}

//处理登录settion的问题
function submitName(name,pwd){
	$.ajax({
		url : '/qtadmin/user/login.do',
		type : 'post',
		async : true,
		data : {
			"uname":name,
			"upwd":pwd
		},
		success : function(data) {
		
		}
   });
}
