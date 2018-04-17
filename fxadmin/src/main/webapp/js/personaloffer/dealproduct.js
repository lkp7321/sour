/*
 * 交易产品日历*/
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
		ww=$(window).width()-260+"px";
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		tit=$('title').text();
	
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	//列参数
    var cols = [
        { title:'产品编号', name:'ptid' ,width:100, align:'left' },
        { title:'产品名称', name:'ptnm' ,width:100, align:'left'},
        { title:'交易码', name:'trcd' ,width:150, align:'left'},
        { title:'交易码描述', name:'trds' ,width:80, align:'left'},
        { title:'日历规则', name:'calendarName' ,width:120, align:'left'}
    ];
    var product=sessionStorage.getItem('product');
    renderlist({'obj':'','obj1':''});
    function renderlist(a){
    	$.ajax({
    		url:'/fx/getAllProTradeCalendar.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify({
    			userKey:userkey,
    			calendarID:a.obj,
    			levelTy:a.obj1,
    			pageNo:1,
    			pageSize:50
    		}),
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage;
    				ren({'cols':cols,'wh':wh,'userdata':userdata.list,'checked':false});
    			}else if(data.code==00){
    				ren({'cols':cols,'wh':wh,'userdata':'','checked':false});
    			}
    		}
    	});
    }
	
    //更改规则等级；
    $('.small').change(function(){
    	var tit=$('.small option:selected').text();
    	if( tit=='请选择' ){
    		getcalan('');
    	}else if(tit=='每周每日'){
    		getcalan('1' );
    	}else if(tit=='每周特殊日'){
    		getcalan('2' );
    	}else if(tit=='年度特殊日'){
    		getcalan('3' );
    	}
    });
	function getcalan(obj){
		$('.ttsmall').html('');
		if( obj==''){
			str='';
		}else if( obj=='2'){
			str='';
		}else if( obj=='3'||obj=='1'){
			str='<option calendarid="all">所有</option>';
		}
		if( obj!=''){
			$.ajax({
				url:'/fx/getCalenLevel.do',
				type:'post',
				contentType:'application/json',
				data:obj,
				async:false,
				success:function(data){
					if(data.code==01){
						userdata= data.codeMessage ;
						for( var i=0,num=userdata.length;i<num;i++){
							str+='<option calendarid='+userdata[i].calendarID+'>'+userdata[i].calendarName+'</option>'
						}
						$('.ttsmall').html(str);
					}else if(data.code==02){
						//dialog.choicedata(data.codeMessage,'dealproduct','aaa');
					}
					
				}
			});
		}
	}
	//点击查询；
	$('.search').click(function(){
		var a=$('.small option:selected').text(),
			b=$('.ttsmall option:selected').attr('calendarid');
		if( a=='请选择' ){
    		a="";
    	}else if(a=='每周每日'){
    		a="1";
    	}else if(a=='每周特殊日'){
    		a="2";
    	}else if(a=='年度特殊日'){
    		a="3";
    	}
		if( !b ){
			b=''
		}else{
			b=b;
		}
		renderlist({'obj':b,'obj1':a});
	});
	$('.add').click(function(){
		str="";
		dialog.dealproadd('dealproduct',product);
		$.ajax({
	   		url:'/fx/allTradeCodeList.do',
	   		type:'post',
	   		contentType:'application/json',
	   		async:true,
	   		data:userkey,
	   		success:function(data){
	   			str='';
	   		    if(data.code==01){
	   		    	userdata= data.codeMessage ;
	   		    	for(var i=0,num=userdata.length;i<num;i++){
	   		    		if(userdata[i].trds==$('.box tr.selected td:eq(3) span').text() ){
	   		    			str+='<li trcd='+userdata[i].trcd+' selected="selected">'+userdata[i].trds+'</li>'
	   		    		}else{
	   		    			str+='<li trcd='+userdata[i].trcd+'>'+userdata[i].trds+'</li>'
	   		    		}
	   		    	}
	   		    	$('.dtleft ul',parent.document).html( str );
	   			}else if(data.code==02){
	   				
	   			}
	   		}
	   	});
		$('.rulelevel',parent.document).change(function(){
			str='';
			$('.calanderule',parent.document).html('');
			var obj=$('.rulelevel option:selected',parent.document).attr('tit');
			console.log( obj )
			 $.ajax({
					url:'/fx/getCalenLevel.do',
					type:'post',
					contentType:'application/json',
					data:obj,
					async:true,
					success:function(data){
						if(data.code==01){
							userdata= data.codeMessage;
							for( var i=0,num=userdata.length;i<num;i++){
								str+='<option calendarid='+userdata[i].calendarID+'>'+userdata[i].calendarName+'</option>'
							}
							$('.calanderule',parent.document).html(str);
						}else if(data.code==02){
							
						}
						
					}
				});
	    });
	});
	//添加页面中的添加按钮；
	$('body',parent.document).on('click','.dtadd',function(){
		var txt=$('.dealproduct .dtleft .tabpbgc',parent.document).text(),
			trcd=$('.dealproduct .dtleft .tabpbgc',parent.document).attr('trcd'),arr=[];
		$('.dtright ul li',parent.document).each(function(i,v){
			arr.push( $(v).text() );
		});
		if( $('.dealproduct .dtleft .tabpbgc',parent.document).length>0){
			if( arr.indexOf(txt)==-1){
				$('.dtright ul',parent.document).append( "<li trcd="+trcd+">"+txt+"</li>");
			}else{
				dialog.choicedata('该交易码已存在!','dealproduct');	
			}
		}else{
			dialog.choicedata('请先选择一条数据!','dealproduct');
		}
	});
	//添加页面中的全选全删按钮；
	$('body',parent.document).on('click','.allche',function(){
		$('.dtright ul',parent.document).html( $('.dealproduct .dtleft ul',parent.document).html() );
	});
	$('body',parent.document).on('click','.allrem',function(){
		$('.dtright ul',parent.document).html(' ');
	});
	//删除按钮；
	$('body',parent.document).on('click','.dtbot',function(){
		if( $('.dtright ul li',parent.document).hasClass('tabpbgc') ){
			$('.dtright ul li.tabpbgc',parent.document).remove();
		}else{
			dialog.choicedata('请先选择一条数据!','dealproduct');
		}
	});
	$('body',parent.document).on('click','.dbrem',function(){
		if( $('.dbright ul li',parent.document).hasClass('tabpbgc') ){
			$('.dbright ul li.tabpbgc',parent.document).remove();
		}else{
			dialog.choicedata('请先选择一条数据!','dealproduct');
		}
	});
	$('body',parent.document).on('click','.dtleft ul li,.dtright ul li,.dbright ul li',function(){
		$(this).siblings().removeClass('tabpbgc');
		$(this).addClass('tabpbgc');
	});
	$('body',parent.document).on('click','.dbadd',function(){
		var brr=[],
			txt=$('.calanderule option:selected',parent.document).text(),
			calendarid=$('.calanderule option:selected',parent.document).attr('calendarid');
		$('.dbright ul li',parent.document).each(function(i,v){
			brr.push( $(v).text() );
		});
		if( brr.indexOf( txt)==-1){
			$('.dbright ul',parent.document).append('<li calendarid='+calendarid+'>'+txt+'</li>')
		}else{
			dialog.choicedata('该日历规则已存在!','dealproduct');	
		}
	});
	//添加保存；
	$('body',parent.document).on('click','.dealsave',function(){
		var arr=[],brr=[],a=$('.small option:selected').text(),
		b=$('.ttsmall option:selected').attr('calendarid'),
		wrongnum=0;;
		$('.dtright ul li',parent.document).each(function(i,v){
			arr.push({'trcd':$(v).attr('trcd')});
		});
		$('.dbright ul li',parent.document).each(function(i,v){
			brr.push( {'calendarID':$(v).attr('calendarid') });
		});
		if( arr.length<=0){
			wrongnum++;
			dialog.choicedata('请选择交易码!','dealproduct');
		}else{
			if( brr.length<=0){
				wrongnum++;
				dialog.choicedata('请选择日历规则!','dealproduct');
			}
		}
		if( wrongnum==0){
			$.ajax({
		   		url:'/fx/checkCalRule.do',
		   		type:'post',
		   		contentType:'application/json',
		   		async:true,
		   		data:JSON.stringify({
		   			'userKey':userkey,
		   			calList:brr,
		   			codeList:arr
		   		}),
		   		success:function(data){
		   			str='';
		   			$('.dealsave',parent.document).closest('.zhezhao').remove();
		   		    if(data.code==01){
		   		    	dialog.choicedata(data.codeMessage,'dealproduct');
		   		    	renderlist({'obj':b,'obj1':a});
		   			}else if(data.code==02){
		   				dialog.choicedata(data.codeMessage,'quoteparamete');
		   			}
		   		}
		   	});
		}
	});
	$('.modify').click(function(){
		if( $('.box tr').hasClass('selected') ){
			dialog.dealpromodi('dealproduct', product);
		var dealnum=$('.box tr.selected td:eq(0)').attr('levetype');
			//请求修改框中的交易码；
			getdealumFn();
    		if( dealnum==1 ){
    			$('#product_sel2 option:eq(0)').prop('checked','checked');
    		}else if( dealnum==2 ){
    			$('#product_sel2 option:eq(1)').prop('checked','checked');
    		}else if(dealnum==3){
    			$('#product_sel2 option:eq(2)').prop('checked','checked');
    		}
    		getpro(dealnum);
    		$('#product_sel2',parent.document).change(function(){
    			getpro( $(this).find('option:selected').attr('levetype'));
    		});
		}else{
			dialog.choicedata('请先选择一条数据!','dealproduct');
		}
	});
	function getpro(obj){
		$('#product_sel3',parent.document).html('' );
		$.ajax({
	   		url:'/fx/getCalenLevel.do',
	   		type:'post',
	   		contentType:'application/json',
	   		async:true,
	   		data:obj,
	   		success:function(data){
	   			str='';
	   		    if(data.code==01){
	   		    	userdata= data.codeMessage;
	   		    	for(var i=0,num=userdata.length;i<num;i++){
	   		    		if(userdata[i].calendarName==$('.box tr.selected td:eq(4) span').text() ){
	   		    			str+='<option calendarID='+userdata[i].calendarID+' selected="selected">'+userdata[i].calendarName+'</option>'
	   		    		}else{
	   		    			str+='<option calendarID='+userdata[i].calendarID+'>'+userdata[i].calendarName+'</option>'
	   		    		}
	   		    	}
	   		    	$('#product_sel3',parent.document).html( str );
	   		    	
	   			}else if(data.code==02){
	   				
	   			}
	   		}
	   	});
	}
	function getdealumFn(){
		$.ajax({
	   		url:'/fx/allTradeCodeList.do',
	   		type:'post',
	   		contentType:'application/json',
	   		data:userkey,
	   		async:true,
	   		success:function(data){
	   			str='';
	   		    if(data.code==01){
	   		    	userdata= data.codeMessage;
	   		    	for(var i=0,num=userdata.length;i<num;i++){
	   		    		if(userdata[i].trds==$('.box tr.selected td:eq(3) span').text() ){
	   		    			str+='<option odcd='+userdata[i].odcd+' trcd='+userdata[i].trcd+' trds='+userdata[i].trcd+' selected="selected">'+userdata[i].trds+'</option>'
	   		    		}else{
	   		    			str+='<option odcd='+userdata[i].odcd+' trcd='+userdata[i].trcd+' trds='+userdata[i].trcd+'>'+userdata[i].trds+'</option>'
	   		    		}
	   		    	}
	   		    	$('#product_sel1',parent.document).html( str );
	   			}else if(data.code==02){
	   				
	   			}
	   		}
	   	});
	}
	//点击更新；
	$('body',parent.document).on('click','.updata',function(){
		var trcd=$('#product_sel1 option:selected',parent.document).attr('trcd'),
			calendarId=$('#product_sel3 option:selected',parent.document).attr('calendarId'),
			ptid=$('.box tr.selected td:eq(0) span').text(),
			otrcd=$('.box tr.selected td:eq(2) span').text(),
			ocalendarId=$('.box tr.selected td:eq(0)').attr('calendarid'),
			a=$('.small option:selected').text(),
			b=$('.ttsmall option:selected').attr('calendarid');
		$.ajax({
			url:'/fx/upTradeProRules.do',
	   		type:'post',
	   		contentType:'application/json',
	   		async:true,
	   		data:JSON.stringify({
	   				'userKey':userkey,
	   				'tradeProCalVo':{
		   			ptid:ptid+'&'+otrcd+'&'+ocalendarId,   //calendarId：产品名称  trcd  交易码；
		   			calendarID:calendarId,    			//更新失败；
		   			trcd:trcd //交易码
		   		}}),
	   		success:function(data){
	   			$('.dealproduct .updata',parent.document).closest('.zhezhao').remove();
	   		    if(data.code==01){
	   		    	dialog.choicedata(data.codeMessage,'dealproduct','success');
	   		    	renderlist({'obj':b,'obj1':a});
	   			}else if(data.code==02){
	   				dialog.choicedata(data.codeMessage,'dealproduct','aaa');
	   			}
	   		}
		});
	});
	$('.delete').click(function(){
		if( $('.box tr').hasClass('selected') ){
			dialog.cancelDate('您确定要删除当前记录吗？','dealproduct');
		}else{
			dialog.choicedata('请先选择一条数据!','dealproduct');
		}
	});
	//确认删除按钮；
	$('body',parent.document).on('click','.dealproduct .confirm',function(){
		var id=$('.box tr.selected td:eq(0)').attr('calendarid'),
			trcd=$('.box tr.selected td:eq(2) span').text();	    
	    $.ajax({
			url:'/fx/delTradeProRules.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify({'userKey':userkey,'tradeProCalVo':{'calendarID':id,'trcd':trcd}}),
			async:true,
			success:function(data){
				$('.dealproduct .confirm',parent.document).closest('.zhezhao').remove();
				if(data.code==01){
					$('.box tr.selected').remove();
					dialog.choicedata(data.codeMessage,'dealproduct','success');
				}else if(data.code==02){
					dialog.choicedata(data.codeMessage,'dealproduct','aaa');
				}
			}
		});
	});
	function ren(obj ){
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
				horizrailenabled:obj.horizrailenabled
		  });
		 if( obj.userdata.length>0){
			 mmg.on('loadSuccess',function(){
				 $('.box tbody tr').each(function(i,v){
					 $(v).find('td').eq('0').attr('calendarid',obj.userdata[i].calendarID );
					 $(v).find('td').eq('0').attr('leveType',obj.userdata[i].leveType );
				 });
			 });
		 }
	}
	$('body',parent.document).on('click','.dealproduct .sure',function(){
		var tit=$('.dealproduct',parent.document).data('tit');
		if(tit=='success'){
			$('.zhezhao',parent.document).remove();
		}else{
			//关闭选择一条数据;
			$(this).closest('.zhezhao').remove();
		}
		
	});
	$('body',parent.document).on('click','.dealproduct .sure,.dealproduct .cancel,.dealproduct .close,.dealproduct .dealcance,.quoteparamete .sure',function(){
		//关闭选择一条数据;
	   $(this).closest('.zhezhao').remove();
	});
/*----------------快速搜索功能的实现------------------------*/
/*	$('.review_serbtn').click(function(){
		 dialog.serchData('.review_seript');
    });*/


})

