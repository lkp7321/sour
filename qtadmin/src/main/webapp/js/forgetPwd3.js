var flag = false;
userid = sessionStorage.getItem("userid");
username = sessionStorage.getItem("username");

// 判断新密码不能为空 校验新密码是否合法
$('.newPwd').blur(function() {
	checkPwdValue()
})

function checkPwdValue() {
	flag = false;
	if ($('.newPwd').val() == '' || $('.newPwd').val() == undefined) {
		$('.newLoginPwd .remind').text('新密码不能为空，请输入新密码').css('color', 'red')
	} else {
		$('.newLoginPwd .remind').text('');
		flag = true;
	}
}

// 检测新密码的安全度 (后续判断条件可以根据需求修改！！！)
$('.newPwd').on('input propertychange', function() {
	var num = $(this).val().length
	if (num > 5 && num < 8) {
		$('.degreeone').css('background-color', '#7ED321')
	} else if (num >= 8 && num <= 11) {
		$('.degreetwo').css('background-color', '#7ABD54 ')
	} else if (num > 11) {
		$('.degreethree').css('background-color', '#0EB04B')
	}
});

// 确认不能为空 必须与新密码相同
$('.confirmPwd').blur(function() {
	confimPwdValue();
})
function confimPwdValue() {
	flag = false;
	if ($('.confirmPwd').val() == '' || $('.confirmPwd').val() == undefined) {
		$('.conLoginPwd .remind').text('密码不能为空，请确认密码').css('color', 'red')
	} else if ($('.confirmPwd').val() != $('.newPwd').val()) {
		$('.conLoginPwd .remind').text('密码确认错误，请重新输入').css('color', 'red')
	} else {
		$('.conLoginPwd .remind').text('');
		flag = true;
	}
}

$('#nextStepThree').click(function() {
	var password = $('.confirmPwd').val();
	checkPwdValue();
	confimPwdValue();
	if (flag == true) {
		$.ajax({
			url : '/qtadmin/user/updateUser.do',
			type : 'post',
			async : false,
			data : {
				"userid" : userid,
				"password" : password,
				"username" : username
			},
			success : function(data) {
				var data = JSON.parse(data);
			}
		});
	    window.location.href=$(this).attr('u');
	}
});