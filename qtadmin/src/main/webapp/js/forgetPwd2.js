var username = sessionStorage.getItem("username"),
    phonenumber=sessionStorage.getItem("phonenumber");
 $('.userName').text(username)
 $('.phoneNun').text(phonenumber);  
  
//点击获取手机短信验证码
$('#mobileCode').click(function(){
	var times = 60, timer = null; 
    var that = this;
		this.disabled = true;
		var telphone=$('.phoneNun').text();
    	 $.ajax({
    			url : '/qtadmin/sms/sendSMS.do',
    			type : 'post',
    			async : false,// true 是默认的异步，false是同步
    			data : {"phone":telphone},
    			success : function(data) {
    				var result = JSON.parse(data);
    				sessionStorage.setItem('bizid',result.bizid)
					sessionStorage.setItem('code',result.code)
					sessionStorage.setItem('phone',result.phone)
					sessionStorage.setItem('sendDate',result.sendDate)
    				  timer = setInterval(function() {
        					times--;
        					that.value = times + "秒后重新获取";
        					if (times <= 0) {
        						that.disabled = false;
        						that.value = "获取短信校验码";
        						clearInterval(timer);
        						times = 60;
        					} 
        				}, 1000);
    				//}
    			 } 
    		});
    	 
	 
	
  })
$('#codeValue').focus(function(){
	$('.checkphonecode .remind').remove();
})
$('#nextStepTwo').click(function(){
	var telphone=$('.phoneNun').text(),
        v=$('#codeValue').val(),
	    bizid= sessionStorage.getItem('bizid'),
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
			  window.location.href=$('#nextStepTwo').attr('u');
		  }else{
			  $('.checkphonecode dd').append('<span id="remind" class="remind" style="color: #ff0000;">验证码不正确或已过期</span>') 
		  }
			 
		} 
	});
	
	
 });
