require.config({
	baseUrl:"/fx/js",
    paths: { 
        jquery: './js_files/jquery-1.9.1.min',
        dialog:'dialog'
    }
});
//问题1：  在点击x的时候，失焦不触发； 问题2 光标下移的问题； 和一一致，在点击和按键盘事件的时候，都会触发失焦事件；
require(['jquery','dialog'], function($,dialog) {
	var userdata,unametrue=false,clickednum=0;  //unametrue判断用户名是否正确(已请求后台)
	   
	$('.icon-del').on('click',function(){  // 点击事件和失焦事件是同时触发的    
		clickednum=1;
		$(this).prev('input').val('');
	});
   // input失去焦点触发的事件
   $('.form-group input').bind('blur',function(){ 
	   checkbl($(this));
   });
   //光标下移；
 $('body').on('keyup',function(event) {
  	var key = (event.keyCode ? event.keyCode : event.which);  
  	  if( key == 13 ){
  		      
  		  	   $(".formtips").remove(); 
	  		  if( $('#userId').val()==""){     // 判断用户名不能为空
	              var errorMsg = '用户名不能为空！'; 
	              $('#userId').parent().append('<span class="formtips">'+errorMsg+'</span>'); 
	              $('.u-line .icon-del').addClass('icon-close');
	              $('.u-line').css('border-bottom','1px solid red');
	          }
//	  		  else{
//	          	//根据用户名，请求产品；
//	          	var user={"usnm":$('#userId').val()};       	
//	         	focus( user,$('#userId').parent() );
//	          }
		  		if( $('#pwd').val()==""){ 
	                var errorMsg = '密码不能为空！'; 
	                $('#pwd').parent().append('<span class="formtips">'+errorMsg+'</span>'); 
	                $('.p-line .icon-del').addClass('icon-close');
	                $('.p-line').css('border-bottom','1px solid red');
	           } 
	  		if( $('.login-form .formtips').length==0 && $('.zhezhao').length<1){
	  			loginfn( {'usnm':$('#userId').val(),'prod':$('#product option:selected').attr('name'),'pswd':$('#pwd').val() });
	  		} 
	         
  	 }
  });
  function checkbl(obj){
	  if(clickednum==1){ 
		  clickednum=0;
		  return false;
	  }else{
	         var $parent = obj.parent(); 
	         $parent.find(".formtips").remove(); 
	         if( obj.is('#userId') ){  
	             if( obj.val()==""){     // 判断用户名不能为空
	                 var errorMsg = '用户名不能为空！'; 
	                 $parent.append('<span class="formtips">'+errorMsg+'</span>'); 
	                 $('.u-line .icon-del').addClass('icon-close');
	                 $('.u-line').css('border-bottom','1px solid red');
	             }else{
	             	//根据用户名，请求产品；
	             	var user={"usnm":obj.val()};       	
	            	focus( user,$parent );
	              }
	          }
	         // 判断密码不能为空
	         if( obj.is('#pwd') ){  
	             if( obj.val()==""){ 
	                 var errorMsg = '密码不能为空！'; 
	                 $parent.append('<span class="formtips">'+errorMsg+'</span>'); 
	                 $('.p-line .icon-del').addClass('icon-close');
	                 $('.p-line').css('border-bottom','1px solid red');
	            } 
	         }
	  }
	 
  }
   function focus( obj,obj1 ){  //请求判断当前用户
	   var str='';
	   $.ajax({
    		url:'/fx/getPro.do',
    		type:'post',
    		data:JSON.stringify(obj),
    		dataType:'json',
    		contentType:'application/json;charset=utf-8',
    		async: false,
    		success:function(data){
    			if(data.code==00){
        			userdata=JSON.parse( data.codeMessage );
        			for(var i in userdata){
    					str+='<option name='+userdata[i].prod+'>'+userdata[i].ptnm+'</option>';
        			}
        			unametrue=true;
        			$('#product').html(str);
        			$('#product').removeProp('disabled');
        			if( obj1==1){
        				$('#pwd').focus();
        			}
    			}else if(data.code==01){
    				unametrue=false;
    				$('#product').html('');
    				//改变提示  用户未复核  用户名不存在  变弹出框为提示;
    				$('#userId').parent('.form-group').append('<span class="formtips">'+data.codeMessage+'</span>'); 
    				$('#product').prop('disabled','disabled');
    			}
    		}
    	});      
   }
 //点击登录按钮；
    $('.default').click(function(){
    	var wrongnum=0,
    		product=$('#product option:checked').attr('name'),
    		usnm=$('#userId').val(),
    		psd=$('#pwd').val(),
    		user={"usnm":usnm,"prod":product,"pswd":psd};
    	//再添加一个判断，判断用户名是否正确;
    	if(usnm==''){
    		wrongnum++;
    		var errorMsg='用户名不能为空！';
    		$('#userId').parent('.form-group').find('.formtips').remove();
    		$('#userId').parent('.form-group').append('<span class="formtips">'+errorMsg+'</span>'); 
    	}
    	if(psd==''){
    		wrongnum++;
    		var errorMsg='密码不能为空！';
    		$('#pwd').parent('.form-group').find('.formtips').remove();
    		$('#pwd').parent('.form-group').append('<span class="formtips">'+errorMsg+'</span>'); 
    	}
    	if(product==''){
    		wrongnum++;
    	}
    	if( wrongnum==0&& unametrue==true ){
    		loginfn(user);
    	}
    });

  //登陸函數;
    function loginfn(obj){
    	$.ajax({
     		url:'/fx/doLogin.do',
     		type:'post',
     		data:JSON.stringify(obj),
     		dataType:'json',
     		contentType:'application/json;charset=utf-8',
     		async:true,
     		success:function(data){
     			if(data.code==00){
         			userdata=data.codeMessage ;
         			sessionStorage.setItem('product',obj.prod);
         			sessionStorage.setItem('usnm',obj.usnm);
         			sessionStorage.setItem('menu',data.codeMessage );
         			
         			location.href='/fx/toindex.do';
         			
         			$('#myfro input').remove();
         			$('#myfro').append('<input type="text" name = "userKey" value='+JSON.parse( data.codeMessage).userKey+'>');
         			$('#myfro').submit();
         			
     			}else if(data.code==04){
     				//无操作,没有操作；
     				dialog.choicedata(data.codeMessage,'login','aaa');
     			}else if(data.code==03){
     				//30天未修改密码和新用户首次登陆；
     				dialog.cancelDate(data.codeMessage,'resetpsd',null);
     			}else if(data.code==02){
     				//密码错误、角色未复核，该用户被锁、更新时间错误，更新系数错误；
     				dialog.choicedata(data.codeMessage,'login','aaa');
     			}
     		}
    	});
    }
   /* function intfn( obj ){
    	$.ajax({
     		url:'/fx/toindex.do',
     		type:'post',
     		data:obj ,
     		dataType:'json',
     		contentType:'application/json;charset=utf-8',
     		async: false,
     		success:function(data){
     			if(data.code==01){
     			}	
     		}
    	});
    }*/
    $('body').on('click','.psdset .sure',function(){
    	var tit=$(this).closest('.zhezhao').data('tit');
    	if(tit=='editpasd'){
    		$('#pwd').val('');
    		$('.zhezhao').remove();
    	}else{
    		 $(this).closest('.zhezhao').remove();
    	}
    });
    //点击是否修改密码和新用户首次登陆确认键；
  
    $('body').on('click','.resetpsd .confirm ',function(){
         $(this).closest('.zhezhao').remove();
    	 dialog.resetpsd('psdset');
    });
   //修改密码弹窗的逻辑判断
    $('body',parent.document).on('blur','.psdset_input p input',function(){
    	 var $parent = $(this).parent(); 
		 //判断旧密码不能为空
		 if($(this).is('#old')){
		    if(this.value==""){
		 	 	var errorMsg = '原密码不能为空';
		 	 	$parent.append('<span class="formtips">'+errorMsg+'</span>'); 
		 	 }else{
		 	 	
		 	 }
		}
		 //判断新密码不能为空
		 if($(this).is('#new')){
		 	 if(this.value==""){
		 	 	var errorMsg = '新密码不能为空';
		 	 	$parent.append('<span class="formtips">'+errorMsg+'</span>'); 
		 	 }else{
		 	 	 var password=$('#old').val();
                 if(this.value==password){
                 	var errorMsg = '新密码不能与原密码相同';
		 	 	    $parent.append('<span class="formtips">'+errorMsg+'</span>'); 
                 }else if( ! /^.{6,20}$/.test(this.value)){
                 	var errorMsg = '新密码的长度为6到20位';
		 	 	     $parent.append('<span class="formtips">'+errorMsg+'</span>'); 

                 }
                 
                  else if( ! /^(?![^A-Za-z]+$)(?![^0-9]+$)[\x21-x7e]{6,20}$/.test(this.value) ){

			         var errorMsg = '新密码不能为纯数字或纯字母';
		 	 	     $parent.append('<span class="formtips">'+errorMsg+'</span>'); 
			         
		          }
		          /*
		          else if(!dialog.checknum( this.value)){
		        	  var errorMsg = '新密码不能有连续三个字符';
			 	 	   $parent.append('<span class="formtips">'+errorMsg+'</span>'); 
		          }
		          */
		          /*else if( /(.)\1\1/.test(this.value) ){
			         var errorMsg = '新密码不能有连续三个字符';
		 	 	     $parent.append('<span class="formtips">'+errorMsg+'</span>'); 
		          } */
		 	 }
		}
		
		 //判断重复密码不能为空
		 if($(this).is('#repeat')){
		 	 if(this.value==""){
		 	 	var errorMsg = '重复密码不能为空';
		 	 	$parent.append('<span class="formtips">'+errorMsg+'</span>'); 
		 	 }else{
		 	 	var password=$('#new').val();
		 	 	if(this.value==password){
		 	 		
		 	 	}else{
		 	 	  var errorMsg = '重复密码与新密码不一致';
		 	 	  $parent.append('<span class="formtips">'+errorMsg+'</span>');
		 	 	}
		 	 }
		}
    });
    //点击修改密码确定按钮
   $('body',parent.document).on('click','.psdset .addsav',function(){
    	 var usnm=$('#userId').val(),
    	     product=$('#product option:checked').attr('name'),
    	     oldpsd=$('#old').val(),
    	     newpsd=$('#new').val(),
    	     repeat=$('#repeat').val();
     	 var vo={ 'usnm':usnm,'prod':product,'oldPass':oldpsd,'newPass':newpsd};

     	 if(oldpsd==''){
     		 $('#old').parent('p').find('.formtips').remove();
     		 $('#old').parent('p').append('<span class="formtips">原密码不能为空</span>');
     	 }else if(!/^.{6,20}$/.test(oldpsd) ){
     		 $('#old').parent('p').find('.formtips').remove();
     		 $('#old').parent('p').append('<span class="formtips">旧密码的长度为6到20位</span>');
     	 }
     	 /*
     	 else if( ! /^(?![^A-Za-z]+$)(?![^0-9]+$)[\x21-x7e]{6,20}$/.test(oldpsd)){
     		 $('#old').parent('p').find('.formtips').remove();
     		 $('#old').parent('p').append('<span class="formtips">旧密码不能为纯数字和纯字母组成</span>');
     	 }
     	 */
     	 if(newpsd==''){
     		 $('#new').parent('p').find('.formtips').remove();
     		 $('#new').parent('p').append('<span class="formtips">新密码不能为空</span>');
     	 }else if( newpsd==oldpsd){
     		 $('#new').parent('p').find('.formtips').remove();
     		 $('#new').parent('p').append('<span class="formtips">新密码不能与原密码相同</span>');
     	 }
     	 
     	 if(repeat==''){
     		 $('#repeat').parent('p').find('.formtips').remove();
     		 $('#repeat').parent('p').append('<span class="formtips">重复密码不能为空</span>');
     	 }else if(repeat!=newpsd){
     		 $('#repeat').parent('p').find('.formtips').remove();
     		 $('#repeat').parent('p').append('<span class="formtips">重复密码与新密码不一致</span>');
     	 }
     	 if($('span.formtips').length==0){  /*修改接口*/
     		$.ajax({
     			//url:'/fx/editPassword.do',
     			url:'/fx/updateLoginPswd.do',
     			type:'post',
     			async:false,
     			data:vo,
     			dataType:'html',
     			success:function(data){
     				var data1=JSON.parse(data);
     				/*console.log(data1.code )
     				console.log( data1.code==01 )*/
     				if(data1.code==01){
     					 dialog.choicedata(data1.codeMessage,'psdset','editpasd');
     				}else{
     				     dialog.choicedata(data1.codeMessage,'psderror','aaa');
     				}
     			  }
     	      }); 
     	 }
	   });
	   //关闭修改弹出窗  点击密码错误的提示窗的确认键；
	  $('body',parent.document).on('click','.psdset .addcance,.psdset .close,.login .twosure,.resetpsd .cancel',function(){
		   $(this).closest('.zhezhao').remove(); 
	   });
	  //关闭成功提示弹出窗  
	   $('body').on('click','.psdset .sure',function(){
            $('.zhezhao').remove();
	   });
	   //原密码不正确提示窗关闭
	   $('body').on('click','.psderror .sure',function(){
		   $(this).closest('.zhezhao').remove(); 
	   });
 
    // 用户名获取焦点触发的事件
	   $('#userId').focus(function(){
	      var $parent = $(this).parent(); 
	      $parent.find(".formtips").remove();  
	      $('.u-line .icon-del').removeClass('icon-close');
	      $('.u-line').css('border-bottom','1px solid #ccc');
	   });
    // 密码获取焦点触发的事件
    $('#pwd').focus(function(){
      var $parent = $(this).parent(); 
      $parent.find(".formtips").remove();  
      $('.p-line .icon-del').removeClass('icon-close');
      $('.p-line').css('border-bottom','1px solid #ccc');
   	});
    
    // 用户名获取焦点触发的事件
    $('body',parent.document).on('focus','.psdset_input p input',function(){
	    var $parent = $(this).parent(); 
	      $parent.find(".formtips").remove();
   });
   //点击删除按钮清空input内容
   $('.icon-del').on('click',function(){
	   $(this).prev().val('');
  });
  
   //点击重置清空用户输入的内容
   $('.reset').click(function(){
        $('.input').val('');
   });
});

 
 
