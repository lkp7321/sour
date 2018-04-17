require.config({
	baseUrl:'/fx/js',
	shim:{
		'jquery.niceScroll': {
			deps: ['jquery'],
			exports: 'jquery.niceScroll'
		},
		'mmGrid':{
			deps:['jquery'],
			exports:'mmGrid'
		}
	},
	paths:{
		jquery:'js_files/jquery-1.9.1.min',
		mmGrid:'js_files/mmGrid',
		niceScroll:'js_files/jquery.nicescroll.min',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','dialog'],function($,mmGrid,niceScroll,dialog){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px",
		addurl,modifeurl,deleurl,listurl;
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var tit=$('title').text();
	var reg1=/^[a-zA-Z0-9]{4}$/,
		numreg=/^\d+$/,
		phonereg=/(^(\d{3,4}-)?\d{7,8})$|(1[3|5|7|8]\d{9})/,
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		IPreg=/((?:(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d))))/;
	//列参数;
    var cols = [
        { title:'告警ID', name:'alid',width:50,align:'left' },
        { title:'告警信息描述', name:'alds',width:100, align:'left'},
        { title:'闪屏', name:'flfg',width:60, align:'center'},
        { title:'声音', name:'vofg',width:60, align:'center'},
        { title:'短信', name:'ntfg',width:60,align:'center'},
        { title:'电话', name:'tlfg',width:30, align:'center'},
        { title:'长时间闪屏', name:'lffg',width:30, align:'center'},
        { title:'时间间隔(秒)', name:'intv',width:30, align:'right'}
    ],
    cols1 = [  	 			//声音和使用状态；
      { title:'用户ID', name:'usid',width:60,align:'left' },
      { title:'用户名称', name:'usnm',width:60, align:'left'},
      { title:'用户IP', name:'usip',width:80, align:'right'},
      { title:'声音', name:'stcd',width:60, align:'center'},
      { title:'电话(外拨使用)', name:'tlno',width:60,align:'right'},
      { title:'手机(短信使用)', name:'mbno',width:30, align:'right'},
      { title:'使用状态', name:'musc',width:30, align:'center'}
     ], 
      cols2 = [
          { title:'告警代码', name:'ercd',width:60,align:'left' },
          { title:'错误描述', name:'ertx',width:60, align:'left'},
          { title:'告警级别', name:'alds',width:80, align:'right'},
          { title:'用户ID', name:'usid',width:60, align:'left'},
          { title:'用户姓名', name:'usnm',width:60,align:'left'}
      ],
      cols3= [
       { title:'告警代码', name:'ERCD',width:60,align:'left' },
       { title:'告警描述', name:'ERMS',width:60, align:'left'}
      ];
    
	getlist();
	//请求页面数据；
	function getlist(){
		var url,coll;
		if(tit=='告警管理'){
	    	url='price/alarmLevelList.do';
	    	coll=cols;
	    }else if(tit=='告警代码管理'){
	    	url='price/alarmCodeList.do';
	        coll=cols3;
	    }else if(tit=='告警方式配置'){
	    	url='price/allAlarmFsList.do';
	    	coll=cols2;
	    }else if( tit=='告警用户设置' ){
	    	url='price/alarmUserList.do';
	    	coll=cols1;
	    }
		$.ajax({
			url:url,
			type:'post',
			contentType:'application/json',
			async:true,
			success:function(data){
				if(data.code==01){
					userdata=JSON.parse( data.codeMessage );
					ren({'cols':coll,'wh':wh,'userdata':userdata});
				}else if(data.code==00){
					ren({'cols':coll,'wh':wh,'userdata':''});
				}
			}
		});
	}
	function ren( obj ){
		$('.boxtop').html('');
    	$('#ascrail2000').remove();
    	$('.boxtop').append('<div class="box"></div>');
		var mmg = $('.box').mmGrid({
				height:obj.wh
				, cols: obj.cols
				,items:obj.userdata
	            , nowrap:true
	            , fullWidthRows:true
	            , checkCol:obj.checked
	            , multiSelect:obj.select
	            ,showBackboard:true
	  	});
		 $(".mmg-bodyWrapper").niceScroll({
				touchbehavior:false,
				cursorcolor:"#666",
				cursoropacitymax:0.7,
				cursorwidth:6,
				background:"#ccc",
				autohidemode:false,
				horizrailenabled:true
		  });
		 if(tit=='告警方式配置'){
			 if( obj.userdata.length>0 ){
				 mmg.on('loadSuccess',function(){
					$('.box tbody tr').each(function(i,v){
						$(v).find('td:eq(0)').attr('usid',obj.userdata[i].usid);
					}) ;
				 });
			 }
		 }
		
	}
	//点击添加、修改、删除;
	$('.add').click(function(){
		var url,str='',str1='',str2='';
		if(tit=='告警管理'){
	    	url='price/addAlarmLevel.do';
	    	dialog.addalarmFn1('alarm','add');
	    	$('.fitname',parent.document).on('blur',function(){
	    		if( $(this).val()==''){
	    			$('.fitname',parent.document).closest('div').find('re').remove();
	    			$('.fitname',parent.document).closest('div').append('<re>告警ID不能为空!</re>');
	    		}else if( $(this).val()!=''&&!reg1.test( $(this).val() )){
	    			$('.fitname',parent.document).closest('div').find('re').remove();
	    			$('.fitname',parent.document).closest('div').append('<re>告警ID必须为4位的字母和数字组合!</re>');
	    		}else{
	    			$('.fitname',parent.document).closest('div').find('re').remove();
	    		}
	    	});
	    	$('.asknum',parent.document).on('blur',function(){
	    		if( $(this).val()==''){
	    			$('.asknum',parent.document).closest('div').find('re').remove();
	    			$('.asknum',parent.document).closest('div').append('<re>告警描述不能为空!</re>');
	    		}else if( $(this).val()!=''&&$(this).val().length<2){
	    			$('.asknum',parent.document).closest('div').find('re').remove();
	    			$('.asknum',parent.document).closest('div').append('<re>告警描述最少是两位!</re>');
	    		}else{
	    			$('.asknum',parent.document).closest('div').find('re').remove();
	    		}
	    	});
	    	$('.dealrate',parent.document).on('blur',function(){
	    		if( $(this).val()==''){
	    			$('.dealrate',parent.document).closest('div').find('re').remove();
	    			$('.dealrate',parent.document).closest('div').append('<re>时间间隔不能为空!</re>');
	    		}else if( $(this).val()!=''&&!numreg.test( $(this).val() )){
	    			$('.dealrate',parent.document).closest('div').find('re').remove();
	    			$('.dealrate',parent.document).closest('div').append('<re>时间间隔必须为数字!</re>');
	    		}else{
	    			$('.dealrate',parent.document).closest('div').find('re').remove();
	    		}
	    	});
	    }else if(tit=='告警代码管理'){
	    	dialog.addalarmFn3('alarm','add');
	    	$('.fitname',parent.document).on('blur',function(){
	    		if( $(this).val()==''){
	    			$('.fitname',parent.document).closest('div').find('re').remove();
	    			$('.fitname',parent.document).closest('div').append('<re>告警代码不能为空!</re>');
	    		}else{
	    			$('.fitname',parent.document).closest('div').find('re').remove();
	    		}
	    	});
	    	$('.asknum',parent.document).on('blur',function(){
	    		if( $(this).val()==''){
	    			$('.asknum',parent.document).closest('div').find('re').remove();
	    			$('.asknum',parent.document).closest('div').append('<re>告警描述不能为空!</re>');
	    		}else{
	    			$('.asknum',parent.document).closest('div').find('re').remove();
	    		}
	    	});
	    }else if(tit=='告警方式配置'){
	    	dialog.addalarmFn4('alarm','add');
    		//请求获取后台;
	    	$.ajax({
				url:'/fx/price/alarmFsbox.do',
				contentType:'application/json',
    			async:true,
				type:'get',
				success:function(data){
					if(data.code==01){
						userdata=JSON.parse( data.codeMessage);
				
						for(var i=0,num=userdata.us.length;i<num;i++){
							str+='<option usid='+userdata.us[i].USID+'>'+userdata.us[i].USNM+'</option>'
						}
						for(var i=0,num=userdata.er.length;i<num;i++){
							str1+='<option erms='+userdata.er[i].ERMS+'>'+userdata.er[i].ERCD+'</option>'
						}
						for(var i=0,num=userdata.le.length;i<num;i++){
							str2+='<option alid='+userdata.le[i].ALID+'>'+userdata.le[i].ALDS+'</option>'
						}
						$('.alarm .errcode select',parent.document).html( str1 );
						$('.alarm .usernm select',parent.document).html( str );
						$('.alarm .polilevel',parent.document).html( str2 );
						$('.errrmrk',parent.document).val( $('.alarm .errcode select option:selected',parent.document).attr('erms') );
					}else{
						dialog.choicedata(data.codeMessage,'alarm');
					}
				}
			});
	    	//切换告警错误码；
	    	$('.alarm .errcode select',parent.document).on('change',function(){
	    		var usid=$('.alarm .errcode select option:selected',parent.document).attr('erms');
	    		$('.errrmrk',parent.document).val( usid );
	    	});
	    	
	    }else if( tit=='告警用户设置' ){
	    	dialog.addalarmFn2('alarm','add');
	    	//请求获取后台的ID;
	    	$.ajax({
				url:'/fx/price/alarmUserUsid.do',
				contentType:'application/json',
    			async:true,
				type:'get',
				success:function(data){
					if(data.code==01){
						$('.fitname',parent.document).val( data.codeMessage )
					}else{
						
					}
				}
			});
	    	$('.asknum',parent.document).on('blur',function(){
	    		if( $(this).val()==''){
	    			$('.asknum',parent.document).closest('div').find('re').remove();
	    			$('.asknum',parent.document).closest('div').append('<re>用户名称不能为空!</re>');
	    		}else{
	    			$('.asknum',parent.document).closest('div').find('re').remove();
	    		}
	    	});
	    	$('.IP',parent.document).on('blur',function(){
	    		if( $(this).val()==''){
	    			$('.IP',parent.document).closest('div').find('re').remove();
	    			$('.IP',parent.document).closest('div').append('<re>告警IP不能为空!</re>');
	    		}else if( $(this).val()!=''&&!IPreg.test( $(this).val() )){
	    			$('.IP',parent.document).closest('div').find('re').remove();
	    			$('.IP',parent.document).closest('div').append('<re>请输入正确的IP号!</re>');
	    		}else{
	    			$('.IP',parent.document).closest('div').find('re').remove();
	    		}
	    	});
	    	$('.call',parent.document).on('blur',function(){
	    		if( $(this).val()==''){
	    			$('.call',parent.document).closest('div').find('re').remove();
	    			$('.call',parent.document).closest('div').append('<re>电话号码不能为空!</re>');
	    		}else if( $(this).val()!=''&&!phonereg.test( $(this).val() )){
	    			$('.call',parent.document).closest('div').find('re').remove();
	    			$('.call',parent.document).closest('div').append('<re>时请输入正确的电话号码!</re>');
	    		}else{
	    			$('.call',parent.document).closest('div').find('re').remove();
	    		}
	    	});
	    	$('.phone',parent.document).on('blur',function(){
	    		if( $(this).val()==''){
	    			$('.phone',parent.document).closest('div').find('re').remove();
	    			$('.phone',parent.document).closest('div').append('<re>手机号不能为空!</re>');
	    		}else if( $(this).val()!=''&&!phonereg.test( $(this).val() )){
	    			$('.phone',parent.document).closest('div').find('re').remove();
	    			$('.phone',parent.document).closest('div').append('<re>请输入正确的手机号!</re>');
	    		}else{
	    			$('.phone',parent.document).closest('div').find('re').remove();
	    		}
	    	});
	    }
	});
	
	$('.modify').click(function(){
		var url,str2;
		if( $('.box tbody tr').hasClass('selected') ){
			if(tit=='告警管理'){
		    	dialog.addalarmFn1('alarm','modify');
		    	$('.fitname',parent.document).on('blur',function(){
		    		if( $(this).val()==''){
		    			$('.fitname',parent.document).closest('div').find('re').remove();
		    			$('.fitname',parent.document).closest('div').append('<re>告警ID不能为空!</re>');
		    		}else if( $(this).val()!=''&&!reg1.test( $(this).val() )){
		    			$('.fitname',parent.document).closest('div').find('re').remove();
		    			$('.fitname',parent.document).closest('div').append('<re>告警ID必须为4位的字母和数字组合!</re>');
		    		}else{
		    			$('.fitname',parent.document).closest('div').find('re').remove();
		    		}
		    	});
		    	$('.asknum',parent.document).on('blur',function(){
		    		if( $(this).val()==''){
		    			$('.asknum',parent.document).closest('div').find('re').remove();
		    			$('.asknum',parent.document).closest('div').append('<re>告警描述不能为空!</re>');
		    		}else if( $(this).val()!=''&&$(this).val().length<2){
		    			$('.asknum',parent.document).closest('div').find('re').remove();
		    			$('.asknum',parent.document).closest('div').append('<re>告警描述最少是两位!</re>');
		    		}else{
		    			$('.asknum',parent.document).closest('div').find('re').remove();
		    		}
		    	});
		    	$('.dealrate',parent.document).on('blur',function(){
		    		if( $(this).val()==''){
		    			$('.dealrate',parent.document).closest('div').find('re').remove();
		    			$('.dealrate',parent.document).closest('div').append('<re>时间间隔不能为空!</re>');
		    		}else if( $(this).val()!=''&&!numreg.test( $(this).val() )){
		    			$('.dealrate',parent.document).closest('div').find('re').remove();
		    			$('.dealrate',parent.document).closest('div').append('<re>时间间隔必须为数字!</re>');
		    		}else{
		    			$('.dealrate',parent.document).closest('div').find('re').remove();
		    		}
		    	});
		    	$('.fitname',parent.document).val( $('.box tr.selected td:eq(0) span').text() );
		    	$('.asknum',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
		    	$('.dealrate',parent.document).val( $('.box tr.selected td:eq(7) span').text() );
		    	if(  $('.box tr.selected td:eq(2) span').text()=='启用'){
		    		$('input.a1',parent.document).prop('checked','checked');
		    	}else{
		    		$('input.a2',parent.document).prop('checked','checked');
		    	}
		    	
		    	if(  $('.box tr.selected td:eq(3) span').text()=='启用'){
		    		$('input.a3',parent.document).prop('checked','checked');
		    	}else{
		    		$('input.a4',parent.document).prop('checked','checked');
		    	}
		    	
		    	if(  $('.box tr.selected td:eq(4) span').text()=='启用'){
		    		$('input.a5',parent.document).prop('checked','checked');
		    	}else{
		    		$('input.a6',parent.document).prop('checked','checked');
		    	}
		    	
		    	if(  $('.box tr.selected td:eq(5) span').text()=='启用'){
		    		$('input.a7',parent.document).prop('checked','checked');
		    	}else{
		    		$('input.a8',parent.document).prop('checked','checked');
		    	}
		    	
		    	if(  $('.box tr.selected td:eq(6) span').text()=='启用'){
		    		$('input.a9',parent.document).prop('checked','checked');
		    	}else{
		    		$('input.a10',parent.document).prop('checked','checked');
		    	}
		    	
		    }else if(tit=='告警代码管理'){
		    	dialog.addalarmFn3('alarm','modify');
		    	$('.fitname',parent.document).val( $('.box tr.selected td:eq(0) span').text());
				$('.asknum',parent.document).val($('.box tr.selected td:eq(1) span').text() );
		    }else if(tit=='告警方式配置'){
		    	dialog.addalarmFn4('alarm','modify');
		    	$('.errcode input',parent.document).val( $('.box tr.selected td:eq(0) span').text() );
		    	$('.errrmrk',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
		    	$('.usernm input',parent.document).val( $('.box tr.selected td:eq(4) span').text() );
		    	//请求获取后台;
		    	$.ajax({
					url:'/fx/price/alarmFsbox.do',
					contentType:'application/json',
	    			async:true,
					type:'get',
					success:function(data){
						if(data.code==01){
							userdata=JSON.parse( data.codeMessage);
				
							for(var i=0,num=userdata.le.length;i<num;i++){
								if( $('.box tr.selected td:eq(2) span').text()==userdata.le[i].ALDS ){
									str2+='<option alid='+userdata.le[i].ALID+' selected="selected">'+userdata.le[i].ALDS+'</option>'
								}else{
									str2+='<option alid='+userdata.le[i].ALID+'>'+userdata.le[i].ALDS+'</option>'
								}
							}
							$('.alarm .polilevel',parent.document).html( str2 );
						}else{
							dialog.choicedata(data.codeMessage,'alarm');
						}
					}
				});
		    }else if( tit=='告警用户设置' ){
		    	dialog.addalarmFn2('alarm','modify');
		    	
		    	$('.fitname',parent.document).val( $('.box tr.selected td:eq(0) span').text() );
		    	$('.asknum',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
		    	$('.IP',parent.document).val( $('.box tr.selected td:eq(2) span').text() );
		    	$('.call',parent.document).val( $('.box tr.selected td:eq(4) span').text() );
		    	$('.phone',parent.document).val( $('.box tr.selected td:eq(5) span').text() );
		    	
		    	if(  $('.box tr.selected td:eq(3) span').text()=='0'){
		    		$('input.a3',parent.document).prop('checked','checked');
		    	}else{
		    		$('input.a4',parent.document).prop('checked','checked');
		    	}
		    	
		    	if(  $('.box tr.selected td:eq(6) span').text()=='0'){
		    		$('input.a9',parent.document).prop('checked','checked');
		    	}else{
		    		$('input.a10',parent.document).prop('checked','checked');
		    	}
		    	$('.asknum',parent.document).on('blur',function(){
		    		if( $(this).val()==''){
		    			$('.asknum',parent.document).closest('div').find('re').remove();
		    			$('.asknum',parent.document).closest('div').append('<re>用户名称不能为空!</re>');
		    		}else{
		    			$('.asknum',parent.document).closest('div').find('re').remove();
		    		}
		    	});
		    	$('.IP',parent.document).on('blur',function(){
		    		if( $(this).val()==''){
		    			$('.IP',parent.document).closest('div').find('re').remove();
		    			$('.IP',parent.document).closest('div').append('<re>告警IP不能为空!</re>');
		    		}else if( $(this).val()!=''&&!IPreg.test( $(this).val() )){
		    			$('.IP',parent.document).closest('div').find('re').remove();
		    			$('.IP',parent.document).closest('div').append('<re>请输入正确的IP号!</re>');
		    		}else{
		    			$('.IP',parent.document).closest('div').find('re').remove();
		    		}
		    	});
		    	$('.call',parent.document).on('blur',function(){
		    		if( $(this).val()==''){
		    			$('.call',parent.document).closest('div').find('re').remove();
		    			$('.call',parent.document).closest('div').append('<re>电话号码不能为空!</re>');
		    		}else if( $(this).val()!=''&&!phonereg.test( $(this).val() )){
		    			$('.call',parent.document).closest('div').find('re').remove();
		    			$('.call',parent.document).closest('div').append('<re>时请输入正确的电话号码!</re>');
		    		}else{
		    			$('.call',parent.document).closest('div').find('re').remove();
		    		}
		    	});
		    	$('.phone',parent.document).on('blur',function(){
		    		if( $(this).val()==''){
		    			$('.phone',parent.document).closest('div').find('re').remove();
		    			$('.phone',parent.document).closest('div').append('<re>手机号不能为空!</re>');
		    		}else if( $(this).val()!=''&&!phonereg.test( $(this).val() )){
		    			$('.phone',parent.document).closest('div').find('re').remove();
		    			$('.phone',parent.document).closest('div').append('<re>请输入正确的手机号!</re>');
		    		}else{
		    			$('.phone',parent.document).closest('div').find('re').remove();
		    		}
		    	});
		    }
		}else{
			dialog.choicedata('请先选择一条数据!','alarm');
		}
	});
	//告警错误通知用户的保存按钮；
	$('body',parent.document).on('click','.alarm4 .succss',function(){
		var tit,url,data;
		tit=$('.pubhead span',parent.document).text();
		if( tit=='告警错误通知用户-添加'){
	   		url='price/addAlarmFs.do';
   	   		data=JSON.stringify({
	   	   		notify:{
					ercd:$('.errcode select option:selected',parent.document).text(),
					ertx:$('.errrmrk',parent.document).val(),
					alid:$('.polilevel option:selected',parent.document).attr('alid'),
					usid:$('.usernm select option:selected',parent.document).attr('usid')
		   		},
		   		userKey:userkey
   	   		});
	   	   	if($('.alarm .errcode select',parent.document).length>0&&$('.alarm .usernm select',parent.document).length>0&& $('.alarm .polilevel',parent.document).length>0){
	   	   		getlis(url,data);
	   	   	}
	   	}else if(tit=='告警错误通知用户-修改'){
   			url='price/modifyAlarmFs.do';
   			data=JSON.stringify({
	   	   		notify:{
					ercd:$('.errcode input',parent.document).val(),
					ertx:$('.errrmrk',parent.document).val(),
					alid:$('.polilevel option:selected',parent.document).attr('alid'),
					usid:$('.box tr.selected td:eq(0)').attr('usid')
		   		},
		   		userKey:userkey
   	   		});
   			if( $('.alarm .polilevel',parent.document).length>0){
   				getlis(url,data);
   			}
	   	}   	   	   	
	});
	function getlis(url,data){
		$.ajax({
  				url:url,
  				type:'post',
  				contentType:'application/json',
  				async:true,
  				data:data,
  				success:function(data){
  					$('.alarm4 .succss',parent.document).closest('.zhezhao').remove();
  					if(data.code==01){
  						dialog.choicedata(data.codeMessage,'alarm','aaa');
  						getlist();
  					}else if(data.code==02){
  						dialog.choicedata(data.codeMessage,'alarm','aaa');
  					}
  				}
		});
	}
	//告警代码管理中的保存按钮;
	$('body',parent.document).on('click','.alarm3 .succss',function(){
		var tit,url,data;
		tit=$('.pubhead span',parent.document).text();
		if( $('.fitname',parent.document).val()==''){
			$('.fitname',parent.document).closest('div').find('re').remove();
			$('.fitname',parent.document).closest('div').append('<re>告警代码不能为空!</re>');
		}else{
			$('.fitname',parent.document).closest('div').find('re').remove();
		}
   		if( $('.asknum',parent.document).val()==''){
			$('.asknum',parent.document).closest('div').find('re').remove();
			$('.asknum',parent.document).closest('div').append('<re>告警描述不能为空!</re>');
		}else{
			$('.asknum',parent.document).closest('div').find('re').remove();
		}
   		if( tit=='告警代码-添加'){
   			url='price/addAlarmCode.do';
   		}else if(tit=='告警代码-修改'){
   			url='price/modifyAlarmCode.do';
   		}
   		if( $('.alarm3 re',parent.document).length==0){
   			data={
				erms:$('.asknum',parent.document).val(),
				ercd:$('.fitname',parent.document).val()
	   		}
   	   		$.ajax({
   				url:url,
   				type:'post',
   				contentType:'application/json',
   				async:true,
   				data:JSON.stringify({
   						userKey:userkey,
   						alarmCode:data
   				}),
   				success:function(data){
   					$('.alarm3 .succss',parent.document).closest('.zhezhao').remove();
   					if(data.code==01){
   						dialog.choicedata(data.codeMessage,'alarm','aaa');
   						getlist();
   					}else if(data.code==02){
   						dialog.choicedata(data.codeMessage,'alarm','aaa');
   					}
   				}
   			});
   		}
   		
	});
	//告警级别设置----弹出框中的添加按钮；
	$('body',parent.document).on('click','.alarm1 .succss',function(){
		var tit,url,data;
		if( $('.fitname',parent.document).val()==''){
			$('.fitname',parent.document).closest('div').find('re').remove();
			$('.fitname',parent.document).closest('div').append('<re>告警ID不能为空!</re>');
		}else if( $('.fitname',parent.document).val()!=''&&!reg1.test( $('.fitname',parent.document).val() )){
			$('.fitname',parent.document).closest('div').find('re').remove();
			$('.fitname',parent.document).closest('div').append('<re>告警ID必须为4位的字母和数字组合!</re>');
		}else{
			$('.fitname',parent.document).closest('div').find('re').remove();
		}
   		if( $('.asknum',parent.document).val()==''){
			$('.asknum',parent.document).closest('div').find('re').remove();
			$('.asknum',parent.document).closest('div').append('<re>告警描述不能为空!</re>');
		}else if( $('.asknum',parent.document).val()!=''&&$('.asknum',parent.document).val().length<2){
			$('.asknum',parent.document).closest('div').find('re').remove();
			$('.asknum',parent.document).closest('div').append('<re>告警描述最少是两位!</re>');
		}else{
			$('.asknum',parent.document).closest('div').find('re').remove();
		}
   		if( $('.dealrate',parent.document).val()==''){
			$('.dealrate',parent.document).closest('div').find('re').remove();
			$('.dealrate',parent.document).closest('div').append('<re>时间间隔不能为空!</re>');
		}else if( $('.dealrate',parent.document).val()!=''&&!numreg.test( $('.dealrate',parent.document).val() )){
			$('.dealrate',parent.document).closest('div').find('re').remove();
			$('.dealrate',parent.document).closest('div').append('<re>时间间隔必须为数字!</re>');
		}else{
			$('.dealrate',parent.document).closest('div').find('re').remove();
		}
   		tit=$('.pubhead span',parent.document).text();
   		if( tit=='告警级别-添加'){
   			url='price/addAlarmLevel.do';
   		}else if(tit=='告警级别-修改'){
   			url='price/modifyAlarmLevel.do';
   		}
    	if( $('.alarm re',parent.document).length==0){
    		data={
				alid:$('.fitname',parent.document).val(),
				alds:$('.asknum',parent.document).val(),
				flfg:$('input[name="a1"]:checked',parent.document).attr('tit'),
				vofg:$('input[name="a2"]:checked',parent.document).attr('tit'),
				ntfg:$('input[name="a3"]:checked',parent.document).attr('tit'),
				tlfg:$('input[name="a4"]:checked',parent.document).attr('tit'),
				lffg:$('input[name="a5"]:checked',parent.document).attr('tit'),
				intv:$('.dealrate',parent.document).val()
    		}
    		$.ajax({
    			url:url,
    			type:'post',
    			contentType:'application/json',
    			async:true,
    			data:JSON.stringify({
    				userKey:userkey,
    				alarmLevel:data
    			}),
    			success:function(data){
    				$('.alarm1 .succss',parent.document).closest('.zhezhao').remove();
    				if(data.code==01){
    					dialog.choicedata(data.codeMessage,'alarm','aaa');
    					getlist();
    				}else if(data.code==02){
    					dialog.choicedata(data.codeMessage,'alarm','aaa');
    				}
    			}
    		});
    	}
	});
	//告警用户--添加；
	$('body',parent.document).on('click','.alarm2 .succss',function(){
		var tit,url,data;
		tit=$('.pubhead span',parent.document).text();
   		if( tit=='告警用户-添加'){
   			url='price/addAlarmUser.do';
   		}else if(tit=='告警用户-修改'){
   			url='price/modifyAlarmUser.do';
   		}
   		data={
			usid:$('.fitname',parent.document).val(),
			usnm:$('.asknum',parent.document).val(),
			usip:$('.IP',parent.document).val(),
			musc:$('input[name="a2"]:checked',parent.document).attr('tit'),
			tlno:$('.call',parent.document).val(),
			mbno:$('.phone',parent.document).val(),
			stcd:$('input[name="a5"]:checked',parent.document).attr('tit')
   		}
		if( $('.asknum',parent.document).val()==''){
			$('.asknum',parent.document).closest('div').find('re').remove();
			$('.asknum',parent.document).closest('div').append('<re>用户名称不能为空!</re>');
		}else{
			$('.asknum',parent.document).closest('div').find('re').remove();
    		}
 
    	if($('.IP',parent.document).val()==''){
			$('.IP',parent.document).closest('div').find('re').remove();
			$('.IP',parent.document).closest('div').append('<re>告警IP不能为空!</re>');
		}else if( $('.IP',parent.document).val()!=''&&!IPreg.test( $('.IP',parent.document).val() )){
			$('.IP',parent.document).closest('div').find('re').remove();
			$('.IP',parent.document).closest('div').append('<re>请输入正确的IP号!</re>');
		}else{
			$('.IP',parent.document).closest('div').find('re').remove();
    		}
   
    		if( $('.call',parent.document).val()==''){
			$('.call',parent.document).closest('div').find('re').remove();
			$('.call',parent.document).closest('div').append('<re>电话号码不能为空!</re>');
		}else if( $('.call',parent.document).val()!=''&&!phonereg.test( $('.call',parent.document).val() )){
			$('.call',parent.document).closest('div').find('re').remove();
			$('.call',parent.document).closest('div').append('<re>请输入正确的电话号码!</re>');
		}else{
			$('.call',parent.document).closest('div').find('re').remove();
		}
		if( $('.phone',parent.document).val()==''){
			$('.phone',parent.document).closest('div').find('re').remove();
			$('.phone',parent.document).closest('div').append('<re>手机号不能为空!</re>');
		}else if( $('.phone',parent.document).val()!=''&&!phonereg.test( $('.phone',parent.document).val() )){
			$('.phone',parent.document).closest('div').find('re').remove();
			$('.phone',parent.document).closest('div').append('<re>请输入正确的手机号!</re>');
		}else{
			$('.phone',parent.document).closest('div').find('re').remove();
		}
		if( $('.alarm2 re',parent.document).length==0){
			$.ajax({
				url:url,
				type:'post',
				contentType:'application/json',
				async:true,
				data:JSON.stringify({
					userKey:userkey,
					alarmUser:data
				}),
				success:function(data){
					$('.alarm2 .succss',parent.document).closest('.zhezhao').remove();
					if(data.code==01){
						dialog.choicedata(data.codeMessage,'alarm','aaa');
						getlist();
					}else if(data.code==02){
						dialog.choicedata(data.codeMessage,'alarm','aaa');
					}
				}
			});
		}
	});
	$('.del').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			//删除弹出框;
			dialog.cancelDate('您确定要删除当前记录吗？','alarm');
		 }else{
			dialog.choicedata('请先选择一条数据!','alarm');
		}
	});
	//点击删除数据 确认+ajax;
	$('body',parent.document).on('click','.alarm .confirm',function(){
		var url,dataa={};
		if(tit=='告警管理'){
	    	url='price/removeAlarmLevel.do';
	    	dataa=JSON.stringify({
	    		alid:$('.box tr.selected td:eq(0) span').text(),
	    		userKey:userkey
	    	});
	    }else if(tit=='告警代码管理'){
	    	url='price/removeAlarmCode.do';
	    	dataa=JSON.stringify({
	    		userKey:userkey,
	    		ercd:$('.box tr.selected td:eq(0) span').text()
	    	});
	    }else if(tit=='告警方式配置'){
	    	url='price/delAlarmFs.do';
	    	dataa=JSON.stringify({
	    		'notify':{
	    			ercd:$('.box tr.selected td:eq(0) span').text(),
	    			usid:$('.box tr.selected td:eq(3) span').text()
	    		},userKey:userkey
	    	});
	    }else if( tit=='告警用户设置' ){
	    	url='price/removeAlarmUser.do';
	    	dataa=JSON.stringify({
	    		alarmUser:{    
	    		  'usid':$('.box tr.selected td:eq(0) span').text(),
	    		 'usnm':$('.box tr.selected td:eq(1) span').text()
		    	},userKey:userkey});
	    }	
		$.ajax({
			url:url,
			type:'post',
			async:true,
			contentType:'application/json',
    		data:dataa,
			success:function(data){
				$('.alarm .confirm',parent.document).closest('.alarm').remove();
				if(data.code==01){
					//this指向；
					$('.box tbody tr.selected').remove();
					dialog.choicedata(data.codeMessage,'alarm');
				}else{
					dialog.choicedata(data.codeMessage,'alarm');
				}
			}
		});
	});
	$('body',parent.document).on('click','.alarm .close,.alarm .sure,.alarm .cancel,.alarm .faile',function(){
		$(this).closest('.zhezhao').remove();
	});
})