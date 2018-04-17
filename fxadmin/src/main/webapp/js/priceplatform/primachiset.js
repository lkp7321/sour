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
		tit=$('title').text(),
		olddata='';
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	
	/*s*/
	
	//列参数;
    var cols=[
		{ title:'产品编号', name:'ptid',width:60,align:'left' },
		{ title:'产品名称', name:'ptnm',width:60, align:'left'},
		{ title:'日历规则', name:'calendarName',width:60, align:'left'}   
    ];
   //点击添加按钮、修改、删除按钮；
	$('.add').click(function(){
		dialog.primachisetadd('primachiset','add');
		$.ajax({
			url:'/fx/price/getpriceProd.do',
			contentType:'application/json',
			async:true,
			success:function(data){
				var str="";
				if(data.code==01){
					userdata=JSON.parse( data.codeMessage );
					for(var i=0,num=userdata.length;i<num;i++){
						str+='<option ptid='+userdata[i].PTID+'>'+userdata[i].PTNM+'</option>'
					}
					$('.markna',parent.document).html(str);
				}else{
				
				}
			}
		});
		$('.ruleslev',parent.document).change(function(){
			str='';
			$('.carrule',parent.document).html('');
			var obj=$('.ruleslev option:selected',parent.document).attr('tit');
			 $.ajax({
					url:'/fx/getCalenLevel.do',
					type:'post',
					contentType:'application/json',
					data:obj,
					async:true,
					success:function(data){
						if(data.code==01){
							userdata=JSON.parse( data.codeMessage );
							for( var i=0,num=userdata.length;i<num;i++){
								str+='<option calendarid='+userdata[i].calendarID+'>'+userdata[i].calendarName+'</option>'
							}
							$('.carrule',parent.document).html(str);
						}else if(data.code==02){
							
						}
					}
				});
	    });
	});
	
	$('.modify').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			var calarid=$('.box tr.selected td:eq(0)').attr('calarid');
			dialog.primachisetadd('primachiset','modify');
			$.ajax({
				url:'/fx/price/getpriceProd.do',
				contentType:'application/json',
				async:true,
				success:function(data){
					var str="";
					if(data.code==01){
						userdata=JSON.parse( data.codeMessage );
						for(var i=0,num=userdata.length;i<num;i++){
							if(  userdata[i].PTNM==$('.box tr.selected td:eq(1) span').text() ){
								str+='<option ptid='+userdata[i].PTID+' selected="selected">'+userdata[i].PTNM+'</option>'
								olddata=userdata[i].PTID+"&";
							}else{
								str+='<option ptid='+userdata[i].PTID+'>'+userdata[i].PTNM+'</option>'
							}
						}
						$('.markna',parent.document).html(str);
					}else{
					
					}
				}
			});
			$.ajax({
				url:'/fx/selevel.do',
				contentType:'application/json',
				type:'post',
				async:true,
				data:calarid,
				success:function(data){
					$('.ruleslev option',parent.document).each(function(i,v){
						if( $(v).attr('tit')==data.codeMessage){
							 $(v).prop('selected','selected');
							 getcalar( $(v).attr('tit'));
						}
					});
				}
			});
			$('.ruleslev',parent.document).change(function(){
				str='';
				$('.carrule',parent.document).html('');
				var obj=$('.ruleslev option:selected',parent.document).attr('tit');
				 $.ajax({
						url:'/fx/getCalenLevel.do',
						type:'post',
						contentType:'application/json',
						data:obj,
						async:true,
						success:function(data){
							if(data.code==01){
								userdata=JSON.parse( data.codeMessage );
								for( var i=0,num=userdata.length;i<num;i++){
									str+='<option calendarid='+userdata[i].calendarID+'>'+userdata[i].calendarName+'</option>'
								}
								$('.carrule',parent.document).html(str);
							}else if(data.code==02){
								
							}
						}
					});
		    });
		}else{
			dialog.choicedata('请先选择一条数据!','primachiset');
		}
	});	
	$('body',parent.document).on('click','.primachiset .update',function(){
		var  ptid=$('.markna option:selected',parent.document).attr('ptid'),
			 calendarID=$('.carrule option:selected',parent.document).attr('calendarid'),
			 ptnm=$('.markna option:selected',parent.document).text(),
			 tit=$('.pubhead span',parent.document).text(),
			 data={};
		if(tit=='价格加工规则-添加'){
			url='price/savePriceSet.do';
			data={
				userKey:userkey,
				priceJiaBean:{
					ptid:ptid,
					calendarID:calendarID,
					ptnm:ptnm
				}
			}
		}else{
			url='price/modifyPriceSet.do';
			data={
				userKey:userkey,
				priceJiaBean:{
					ptid:ptid,
					ptnm:ptnm,
					calendarID:calendarID,
					priceJiaPK:olddata //旧的ptid+"&"+旧的calendarId 的字符串
				}
			}
		}
		if( $('.ruleslev option:selected',parent.document).text()=='所有'){
			dialog.choicedata('请先选择一条规则等级!','primachiset');
		}else{
			if( $('.carrule option',parent.document).length>0){
				$.ajax({
					url:url,
					contentType:'application/json',
					type:'post',
					async:true,
					data:JSON.stringify(data ),
					success:function(data){
						$('.primachiset .update',parent.document).closest('.zhezhao').remove();
						if(data.code==01){
							dialog.choicedata(data.codeMessage,'primachiset');
							getlist();
						}else if(data.code==02){
							dialog.choicedata(data.codeMessage,'primachiset');
						}
						
					}
				});
			}
		}
	});
	$('body',parent.document).on('click','.primachiset .confirm',function(){
		var carId=$('.box tr.selected td:eq(0)').attr('calarid'),
			ptid=$('.box tr.selected td:eq(0) span').text().trim(),
			ptnm=$('.box tr.selected td:eq(1) span').text().trim();
		$.ajax({
			url:'/fx/price/removePriceSet.do',
			contentType:'application/json',
			type:'post',
			async:true,
			data:JSON.stringify({
				userKey:userkey,
				priceJiaBean:{
					ptid:ptid,
					ptnm:ptnm,
					calendarID:carId
				}
			}),
			success:function(data){
				$('.primachiset .confirm',parent.document).closest('.zhezhao').remove();
				if(data.code==01){
					dialog.choicedata(data.codeMessage,'primachiset');
					$('.box tbody tr.selected').remove();
				}else if(data.code==02){
					dialog.choicedata(data.codeMessage,'primachiset');
				}
				
			}
		});
	});
	$('.del').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			dialog.cancelDate('您确定要删除当前记录吗？','primachiset');
		}else{
			dialog.choicedata('请先选择一条数据!','primachiset');
		}
	});	
    
    
	$('.markname select').change(function(){
		var mkid=$(this).find('option:selected').attr('mkid');
		if( mkid!='all'){
			getcuur( mkid );
		}else{
			$('.cuurpair select').html( '<option value="all">所有</option>' );
		}
	});
	function getcuur1(obj){
		var str="";
		$.ajax({
			url:'/fx/price/getBiBieDuiLists.do',
			contentType:'application/json',
			async:true,
			type:"post",
			data:obj,
			success:function(data){
				if(data.code==01){
					userdata=JSON.parse( data.codeMessage );
					for(var i in userdata){
						if(  userdata[i]==$('.box tr.selected td:eq(3) span').text()){
							str+='<option value='+userdata[i]+' selected="selected">'+userdata[i]+'</option>'
						}else{
							str+='<option value='+userdata[i]+'>'+userdata[i]+'</option>'
						}
					}
					$('.cuurpair',parent.document).html( str );
				}else if(data.code==02){
					
				}
			}
		});
	}
	//初始化查询页面数据；
	getlist();

	function getlist( ){
		$.ajax({
			url:'/fx/price/getPriceJiaList.do',
			contentType:'application/json',
			async:true,
			success:function(data){
				if(data.code==01){
					//userdata=JSON.parse( data.codeMessage );
					ren({'cols':cols,'wh':wh,'userdata':data.codeMessage});
				}else{
					ren({'cols':cols,'wh':wh,'userdata':''});
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
			horizrailenabled:obj.horizrailenabled
	  });
	  if( obj.userdata.length>0){
		  mmg.on('loadSuccess',function(){
			  $('.box tbody tr').each(function(i,v){
				 $(v).find('td').eq('0').attr('calarid',obj.userdata[i].calendarID);
			  });
		  });
	  }
	}
	function getcalar( obj ){
		var str="";
		$.ajax({
			url:'/fx/getCalenLevel.do',
			type:'post',
			contentType:'application/json',
			data:obj,
			async:true,
			success:function(data){
				if(data.code==01){
					userdata=JSON.parse( data.codeMessage );
					for( var i=0,num=userdata.length;i<num;i++){
						if( userdata[i].calendarName==$('.box tr.selected td:eq(2) span').text()){
							str+='<option calendarid='+userdata[i].calendarID+' selected="selected">'+userdata[i].calendarName+'</option>'
							olddata+=userdata[i].calendarID;
						}else{
							str+='<option calendarid='+userdata[i].calendarID+'>'+userdata[i].calendarName+'</option>'	
						}
					}
					$('.carrule',parent.document).html(' ').html(str);
					
				}else if(data.code==02){
					
				}
			}
		});
	}
	
	
	
	$('body',parent.document).on('click','.primachiset .sure,.primachiset .cancel,.primachiset .close,.dealcance',function(){
		//关闭选择一条数据;
	   $(this).closest('.zhezhao').remove();
	});
})
