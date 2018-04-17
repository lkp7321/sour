require.config({
	baseUrl:'/fx/js',
	paths:{
		jquery:'js_files/jquery-1.9.1.min',
		dialog:'dialog'
	}
});
 require(['jquery','dialog'],function($,dialog){
	 var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		userdata;
	 
	 var psdnum=/^.{6,20}$/,
	 	 engandnum=/^(?![^A-Za-z]+$)(?![^0-9]+$)[\x21-x7e]{6,20}$/;   
	 
//     $('.psdset_input p input').blur(function(){
//			 var $parent = $(this).parent(),
//			     oldpsd=$('#old').val(),
// 	             newpsd=$('#new').val(),
// 	             repeatpsd=$('#repeat').val(),
// 	             psdtrue=(oldpsd!='' && newpsd!='' && repeatpsd!=''),errorMsg;
//			 //判断旧密码不能为空
//			 if($(this).is('#old')){
//			    if(this.value==""){
//			 	 	errorMsg = '原密码不能为空';
//			 	 	$parent.append('<span class="formtips">'+errorMsg+'</span>'); 
//			 	 }else if( !psdnum.test(this.value) ){
//			 		errorMsg = '原密码的长度为6到20位';
//			 		$parent.append('<span class="formtips">'+errorMsg+'</span>'); 
//			 	 }else{
//			 		$parent.removed('.formtips');
//			 	 }
//			}
//			 //判断新密码不能为空
//			 if($(this).is('#new')){
//			 	 if(this.value==""){
//			 	 	errorMsg = '新密码不能为空';
//			 	 	$parent.append('<span class="formtips">'+errorMsg+'</span>'); 
//			 	 }else{
//			 	 	 var password=$('#old').val();
//                     if(this.value==password){
//                     	errorMsg = '新密码不能与原密码相同';
//			 	 	    $parent.append('<span class="formtips">'+errorMsg+'</span>'); 
//                     }else if( ! /^.{6,20}$/.test(this.value)){
//                     	errorMsg = '新密码的长度为6到20位';
//			 	 	     $parent.append('<span class="formtips">'+errorMsg+'</span>'); 
// 
//                     }else if( ! /^(?![^A-Za-z]+$)(?![^0-9]+$)[\x21-x7e]{6,20}$/.test(this.value) ){
// 
//				         errorMsg = '新密码不能为纯数字和纯字母组成';
//			 	 	     $parent.append('<span class="formtips">'+errorMsg+'</span>'); 
//				         
//			           }else if( !dialog.checknum( this.value) ){
//			        	   errorMsg = '新密码不能有连续三个字符';
//				 	 	   $parent.append('<span class="formtips">'+errorMsg+'</span>'); 
//			           }/*else if( /(.)\1\1/.test(this.value) ){
//				         var errorMsg = '新密码不能有连续三个字符';
//			 	 	     $parent.append('<span class="formtips">'+errorMsg+'</span>'); 
//			          } */
//			 	 }
//			}
//			 //判断重复密码不能为空
//			 if($(this).is('#repeat')){
//			 	 if(this.value==""){
//			 	 	var errorMsg = '重复密码不能为空';
//			 	 	$parent.append('<span class="formtips">'+errorMsg+'</span>'); 
//			 	 }else{
//			 	 	var password=$('#new').val();
//			 	 	if(this.value!=password){
//			 	 		var errorMsg = '重复密码与新密码不一致';
//				 	 	 $parent.append('<span class="formtips">'+errorMsg+'</span>');
//			 	 	}
//			 	 }
//			 }
//	 });
 
     $('.psdret_save').click(function(){
 		  	 var oldcuno=$('#old').val(),
 		  	     newcuno=$('#new').val(),
 		  	     caty=$('#repeat').val(),errmsg;
			   	 var vo={"oldcuno":oldcuno,"newcuno":newcuno,'caty':caty};
			   	 
//			     $('#old').parent('p').find('.formtips').remove();
//			   	 $('#new').parent('p').find('.formtips').remove();
//			   	 $('#repeat').parent('p').find('.formtips').remove();
			   	 
//				 if(oldpsd==''){
//					 errmsg='原密码不能为空';
//					 $('#old').parent('p').append('<span class="formtips">'+errmsg+'</span>');
//				 }else if(!psdnum.test(oldpsd) ){
//					 errmsg='原密码的长度为6到20位';
//					 $('#old').parent('p').append('<span class="formtips">'+errmsg+'</span>');
//				 }
//				 
//				 if(newpsd==''){
//					 errmsg='新密码不能为空';
//					 $('#new').parent('p').append('<span class="formtips">'+errmsg+'</span>');
//				 }else if(!psdnum.test(newpsd)){
//					 errmsg='新密码的长度为6到20位';
//					 $('#new').parent('p').append('<span class="formtips">'+errmsg+'</span>');
//				 }else if(!engandnum.test(newpsd)){
//					 errmsg='新密码不能为纯数字或纯字母';
//					 $('#new').parent('p').append('<span class="formtips">'+errmsg+'</span>');
//				 }else if(!dialog.checknum( this.value)){
//					 errmsg='新密码不能有连续三个字符';
//					 $('#new').parent('p').append('<span class="formtips">'+errmsg+'</span>');
//				 }else if(newpsd==oldpsd){
//					 errorMsg = '新密码不能与原密码相同';
//					 $('#new').parent('p').append('<span class="formtips">'+errmsg+'</span>');
//				 }
//				 if(reppsd==''){
//					 errmsg='重复密码不能为空';
//					 $('#repeat').parent('p').append('<span class="formtips">'+errmsg+'</span>');
//				 }else if(reppsd!=newpsd){
//					 errmsg='重复密码与新密码不一致';
//					 $('#repeat').parent('p').append('<span class="formtips">'+errmsg+'</span>');
//				 }
//				 if($('.formtips').length==0){
					 //请求数据
						$.ajax({
							url:'/fx/changeSend.do',
							type:'post',
							async:false,
							data:JSON.stringify( vo ),
							dataType:'json',
							contentType:'application/json;charset=utf-8',
							success:function(data){
								if(data.code==10){
									 dialog.choicedata(data.codeMessage,'changeusernum');
								}else if(data.code==11){
								     dialog.choicedata(data.codeMessage,'changeusernum');
								}
							}
						});
//				 }
			   });
	    
//	 // 用户名获取焦点触发的事件
//     $('.psdset_input p input').focus(function(){
//      var $parent = $(this).parent(); 
//      $parent.find(".formtips").remove();  
//    });
   
     //关闭弹出窗  
	 $('body',parent.document).on('click','.changeusernum .sure',function(){
		 $(this).closest('.psdset').remove(); 
	 });
    
});



