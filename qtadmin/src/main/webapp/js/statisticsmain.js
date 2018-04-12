$(function(){
	 var selectData=[],str='';
	// h=$('.acountbody').height();
	  

	 //左侧nav的点击事件
	  $('.navlist li:eq(0)').addClass('licolor')
	  $('.navlist li').click(function(){
	  	  var s=$(this).attr('href')
	  	  $(this).addClass('licolor').siblings().removeClass('licolor')
	  	  $('#listiframe').attr('src', s );
	  	  $('.statcont').height($('.acountbody').height()+50);
	  });
	 $.ajax({
	  		url:'${pageContext.request.contextPath}/user/getuseraccount.do',
	  		type:'post',
	  		dataType:'json',
	  		contentType:'application/json;charset=utf-8',
	  		async: false,
	  		before:function(){
	  		},
	  		success:function(data){
	  			  if(data.length==0){
	  				   str='<li style="width:100%"><a href="${pageContext.request.contextPath}/user/toUserManage.do?account=1">您还没有账户，马上去账户管理添加账户！</a></li>'
	  			  }else{
	  				for(k in data){
		  				 str+="<li><input style='margin:0,0,0,20%' type='checkbox' name='account' value='"+data[k]+"' /><span>"+data[k]+"</span></li>";
		  			  } 
	  			  }
	  			  $('.accountmany ul').html(str);
	  			  $('.accountmany li input:eq(0)').prop('checked',true)
	  		}
	  	});
	 
	 /*setIframeHeight('#listiframe')
	  function setIframeHeight(iframe) {
		 if (iframe) {
			 var iframeWin = iframe.contentWindow || iframe.contentWindow.parentWindow;
			 if (iframeWin.document.body) {
			  iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
			 }
			 console.log(iframe.height)
		 }
	 };  */
	 
	  
})