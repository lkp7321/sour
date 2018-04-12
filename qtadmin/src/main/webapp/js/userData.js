var iflag=true,tflag=true,qflag=true,eflag=true; 
$('.information li').click(function() {
	var index = $(this).data("index");
	$("." + index + "").show().siblings('div').hide();
	$(this).addClass('licolor').siblings().removeClass('licolor')
})
// 根据当前用户请求用户信息
var username = $('.username').val(),userData;
 
$.ajax({
	url : '/qtadmin/user/getUserName.do',
	type : 'post',
	async : false,
	data : {
		"username" : username
	},
	success : function(data) {
		userData = JSON.parse(data);
		 
		$('.username').val(userData.username).attr('key', userData.userid);
		$('.truename').val(userData.truename!='null'?userData.truename:'');
        $('.qq').val(userData.qq!='null'?userData.qq:'');
		$('.phonenumber').val(userData.phonenumber!='null'?userData.phonenumber:'');
		$('.email').val(userData.email!='null'?userData.email:'');
        $('.education option').each(function(i,e){
		    if($(e).text()==userData.education){
		    	$(e).attr('selected',true)
		    } 
		}) 
		$('.position').val(userData.position!='null'?userData.position:'');
		$('.profession').val(userData.profession!='null'?userData.profession:'');
        $('.certificatetype option').each(function(i,e){
		    if($(e).text()==userData.certificatetype){
		    	$(e).attr('selected',true)
		    } 
		}) 
	    $('.residence option').each(function(i,e){
		    if($(e).attr('did')==userData.residence){
		    	$(e).attr('selected',true)
		    } 
		}) 
		$('.certificateno').val(userData.certificateno!='null'?userData.certificateno:'');
		$('.signature').val(userData.signature!='null'?userData.signature:'');
		$('.timezone option').each(function(i,e){
		    if($(e).val()==userData.timezone){
		    	$(e).attr('selected',true)
		    } 
		}) 
		if(userData.sex=='male'){
			$('input[value="male"]').prop('checked',true);
		}else if(userData.sex=='female'){
			$('input[value="female"]').prop('checked',true);
		}else{
			$('input[value="secrecy"]').prop('checked',true);
		}
	}
});
//身份证号的失焦判断
$('.certificateno').blur(function(){
	if($('.certificatetype option:selected').text()=='身份证'){
		checkIDcard();
	}
})
$('.certificatetype').change(function(){
	 $('.p_card .remind').text('');
})
//封装校验身份证
function checkIDcard(){
	 $('.p_card .remind').text('');
 	   iflag=true;
	var certificateno=$('.certificateno').val();
	if(certificateno){
		iflag=false;
		if(regExpManger.idCard.test(certificateno) ){
	   	   $('.p_card .remind').text('');
	   	   iflag=true;
	    }else{
	       $('.p_card .remind').text('您输入的证件号格式不正确，请重新输入！');
		   iflag=false;
	    } 
	} 
}
//QQ号的失焦判断
$('.qq').blur(function(){
	 checkQQ();
})
//封装校验qq
function checkQQ(){
	qflag=true;
	$('.p_qq .remind').text('');
	var qq=$('.qq').val();
	if(qq){
		qflag=false;
		if(regExpManger.qqPattern.test(qq) ){
	   	   $('.p_qq .remind').text('');
	   	   qflag=true;
	    }else{
	       $('.p_qq .remind').text('您输入的格式不正确，请重新输入！');
		   qflag=false;
	    } 
	} 
}
//手机号的失焦判断
$('.phonenumber').blur(function(){
	checkTel();
})
//封装校验手机
function checkTel(){
	 $('.p_phone .remind').text('');
 	 tflag=true;
	var phonenumber=$.trim($('.phonenumber').val());
	if(phonenumber){
		tflag=false;
		if(regExpManger.mobilReg.test(phonenumber) ){
	   	   $('.p_phone .remind').text('');
	   	   tflag=true;
	    }else{
	       $('.p_phone .remind').text('您输入的手机号不合法，请重新输入！');
		   tflag=false;
	    } 
	}else{
		$('.p_phone .remind').text('手机号不能为空！');
	    tflag=false;
	} 
}
//邮箱的失焦判断
$('.email').blur(function(){
	checkEmail();
})
//封装校验邮箱
function checkEmail(){
	 $('.p_email .remind').text('');
 	 eflag=true;
	var email=$.trim($('.email').val());
	if(email){
		eflag=false;
		if(regExpManger.emailReg.test(email) ){
	   	   $('.p_email .remind').text('');
	   	   eflag=true;
	    }else{
	       $('.p_email .remind').text('您输入的格式不正确，请重新输入！');
		   eflag=false;
	    } 
	}else{
		 $('.p_email .remind').text('邮箱不能为空！');
		  eflag=false;
	} 
}

$('.save').click(function() {
	var text = $('.licolor').text(), updateData;
	if (text == '基本资料') {
		iflag=true;  tflag=true;  qflag=true;  eflag=true;
		updateData = {
			'username' : $('.username').val(),
			'truename' : $('.truename').val(),
			'sex' : $('input:radio[name="gender"]:checked').val(),
			'residence':$('.residence option:selected').attr('did')
		};
		
	} else if (text == '联系方式') {
		updateData = {
			'username' : $('.username').val(),
			'qq' : $.trim($('.qq').val()),
			'phonenumber' : $('.phonenumber').val(),
			'email' :  $.trim($('.email').val())
		};
		checkEmail();
		checkTel();
		checkQQ();
		
	} else if (text == '教育情况') {
		iflag=true;  tflag=true;  qflag=true;  eflag=true;
		updateData = {
			'username' : $('.username').val(),
			'education' : $('.education option:selected').text()
		};
		
	} else if (text == '工作情况') {
		iflag=true;  tflag=true;  qflag=true;  eflag=true;
		updateData = {
			'username' : $('.username').val(),
			'position' : $('.position').val(),
			'profession' : $('.profession').val()
		};
	} else if (text == '个人信息') {
		updateData = {
			'username' : $('.username').val(),
			'certificatetype' : $('.certificatetype option:selected').text(),
			'certificateno' :  $('.certificateno').val(),
			'signature' : $('.personality textarea').val(),
			'timezone' : $('.timezone option:selected').val()
		};
		if($('.certificatetype option:selected').text()=='身份证'){
			checkIDcard();
		}
	}
 
    if(iflag==true && tflag==true && qflag==true && eflag==true){
    	$.ajax({
    		url : '/qtadmin/user/updateUser.do',
    		type : 'post',
    		async : false,
    		data : updateData,
    		success : function(data) {
    			var data=JSON.parse(data)
    			 
    			if(data.success==true){
    				$('.remindBox').show();
    			}

    		}
    	});
    }
	
})
$('.remindsure').click(function(){
	$('.remindBox').hide();
})


// 获取当前的时间
function p(s) {
	return s < 10 ? '0' + s : s;
}

var myDate = new Date();
// 获取当前年
var year = myDate.getFullYear();
// 获取当前月
var month = myDate.getMonth() + 1;
// 获取当前日
var date = myDate.getDate();
var h = myDate.getHours(); // 获取当前小时数(0-23)
var m = myDate.getMinutes(); // 获取当前分钟数(0-59)
var s = myDate.getSeconds();
var now = year + '' + p(month) + "" + p(date) + " " + p(h) + ':' + p(m) + ":"
		+ p(s);

$('.nowTime span').text(now);
