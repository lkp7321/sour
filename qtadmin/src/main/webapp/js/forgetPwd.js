var flag=false,vflag=false;
//用户名获取焦点
$('#userName').focus(function(){
	 $('.testName .remind').remove();
	 $('.testName dd').append('<span id="remind" class="remind" style="color: #CCCCCC;">请填写您的用户名</span>')
	 
})
//用户名失去焦点
$('#userName').blur(function(){
	checkUserName()
})
 
function checkUserName(){
	flag=false;
	$('.testName .remind').remove();
	var userName=$('#userName').val().trim();
	if(userName==""||userName==undefined){
		$('.testName dd').append('<span id="remind" class="remind" style="color: #ff0000;">用户名不能为空</span>')
	}else{
	   $('.testName .remind').remove();
	   $.ajax({
		url:'/qtadmin/user/getUserName.do',
		type:'post',
		async:false, 
		data:{"username":userName},
		success:function(data){
			var data=JSON.parse(data);
			if(data.code==1){
				flag=true;
				 sessionStorage.setItem("userid",data.userid);
				 sessionStorage.setItem("phonenumber",data.phonenumber);
				 sessionStorage.setItem("username",data.username);
			}else{
				flag=false;
				$('.testName dd').append('<span id="remind" class="remind" style="color: #ff0000;">用户名不存在</span>') 
			     
            }
		}
	  });
    }
}
 //验证码的实现
var verifyCode = new GVerify({
        id:"verifyCode",
        //type:"number"
});
//看不清更换验证码
$('.coderepeats').click(function(){
	verifyCode.refresh();
})
//判断验证码填写是否正确
$('#authCode').blur(function(){
	checkCode()
})
function checkCode(){
	vflag=false;
	if($('#authCode').val()==''||$('#authCode').val()==undefined){
		$('.testCode .remind').text('验证码不能为空，请重新输入').css('color','red')
		vflag=false;
	}else if(!verifyCode.validate($('#authCode').val())){
   	    $('.testCode .remind').text('验证码输入有误，请重新输入').css('color','red')
   	    vflag=false;
     }else{
   	     $('.testCode .remind').text('');
   	    vflag=true;
     }
}
$('#nextStepOne').click(function(){
	checkUserName();
	checkCode();
	if(flag==true && vflag==true){
		window.location.href=$(this).attr('u');
	}
	 
	
 })