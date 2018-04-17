/*价格接收设置*/
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
		olddata;
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var str='<option mkid="all" class="al">所有</option>';
	
	//列参数;
    var cols = [
            { title:'市场编号', name:'mkid',width:60,align:'left' },
            { title:'市场名称', name:'mknm',width:60, align:'left'},
            { title:'货币对编号', name:'exnm',width:60, align:'left'},
            { title:'日历规则', name:'calendarName',width:60,align:'left'}
    ],
    cols1=[
		{ title:'产品编号', name:'usnm',width:60,align:'left' },
		{ title:'产品名称', name:'ptnm',width:60, align:'left'},
		{ title:'日历规则', name:'mbtl',width:60, align:'left'}   
    ];
   //点击添加按钮、修改、删除按钮；
	$('.add').click(function(){
		dialog.pricegetadd('priceget');
		$('.markna',parent.document).html( $('.markname select').html() );
		$('.al',parent.document).remove();
		var str="";
		$.ajax({
			url:'/fx/price/getBiBieDuiLists.do',
			contentType:'application/json',
			async:true,
			type:"post",
			data:$('.markna option:selected',parent.document).attr('mkid'),
			success:function(data){
				if(data.code==01){
					userdata=JSON.parse( data.codeMessage );
					for(var i in userdata){
					 	str+='<li value='+userdata[i]+'>'+userdata[i]+'</li>'
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
							$('.calanderule',parent.document).html(str);
						}else if(data.code==02){
							
						}
						
					}
				});
	    });
	});
	
	$('.modify').click(function(){
		var calarid=$('.box tr.selected td:eq(0)').attr('calarid');
		if( $('.box tbody tr').hasClass('selected') ){
			dialog.pricegmodi('priceget');
	
			$('.markna',parent.document).html( $('.markname').html() );
			$('.markna .al',parent.document).remove();
			
			getcuur1(  $('.markna option:selected',parent.document).attr('mkid') );
			
			olddata=$('.markna option:selected',parent.document).attr('mkid')+'&'
			
			$('.markna',parent.document).change(function(){
				var mkid=$(this).find('option:selected').attr('mkid');
				getcuur1( mkid );
				
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
	
				if( obj==''||obj==2){
					$('.carrule',parent.document).html(' ');
				}else{
					obj=obj;
					getcalar( obj );
				}
		    });
			
		}else{
			dialog.choicedata('请先选择一条数据!','priceget');
		}
	});	
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
						if( userdata[i].calendarName==$('.box tr.selected td:eq(4) span').text()){
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
	$('body',parent.document).on('click','.priceget .update',function(){
		
		var mknm=$('.markna option:selected',parent.document).text().trim(),
			mkid=$('.markna option:selected',parent.document).attr('mkid'),
			calendarId=$('.carrule option:selected',parent.document).attr('calendarid'),
			exnm=$('.cuurpair option:selected',parent.document).text();
		if(  $('.ruleslev option:selected',parent.document).text()=='所有'||$('.ruleslev option:selected',parent.document).text()=='每周特殊日'){
			dialog.choicedata('请先选择规则等级!','priceget');
		}else{
			$.ajax({
				url:'/fx/price/upchPriceRec.do',
				contentType:'application/json',
				type:'post',
				async:true,
				data:JSON.stringify({					
						userKey:userkey,
						pricerecBean:{
							mkid:mkid,
							mknm:mknm,
							calendarID:calendarId,
							exnm:exnm,
							mkpk:olddata
						}
				}),
				success:function(data){
					$('.priceget .update',parent.document).closest('.zhezhao').remove();
					if(data.code==01){
						dialog.choicedata(data.codeMessage,'priceget');
						var mkid=$('.markname select option:selected').attr('mkid'),
							exnm=$('.cuurpair select option:selected').attr('value');
					
						getlist({'mkid':mkid,'exnm':exnm});
					}else if(data.code==02){
						dialog.choicedata(data.codeMessage,'priceget');
					}
			 }
			});
		}
	});
	$('body',parent.document).on('click','.priceget .confirm',function(){
		var carId=$('.box tr.selected td:eq(0)').attr('calarid'),
			mkid=$('.box tr.selected td:eq(1) span').text().trim(),
			exnm=$('.box tr.selected td:eq(3) span').text().trim();
		$.ajax({
			url:'/fx/price/delchPrice.do',
			contentType:'application/json',
			type:'post',
			async:true,
			data:JSON.stringify({
				userKey:userkey,
				pricerecBean:{
					mkid:mkid,
					exnm:exnm,
					calendarID:carId
				}
			}),
			success:function(data){
				$('.priceget .confirm',parent.document).closest('.zhezhao').remove();
				if(data.code==01){
					dialog.choicedata(data.codeMessage,'priceget');
					$('.box tbody tr.selected').remove();
				}else if(data.code==02){
					dialog.choicedata(data.codeMessage,'priceget');
				}
				
			}
		});
	});
	$('.del').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			dialog.cancelDate('您确定要删除当前记录吗？','priceget');
		}else{
			dialog.choicedata('请先选择一条数据!','priceget');
		}
	});	
    
    //获取市场名称；
	$.ajax({
		url:'/fx/price/getMarketinfoList.do',
		contentType:'application/json',
		async:false,
		success:function(data){
			if(data.code==01){
				userdata=JSON.parse( data.codeMessage );
				for(var i in userdata){
				 	str+='<option mkid='+userdata[i].MKID+'>'+userdata[i].MKNM+'</option>'
				}
				$('.markname select').html( str );
			}else if(data.code==02){
			}
			
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
	//获取货币对；
	function getcuur(obj){
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
					 	str+='<option value='+userdata[i]+'>'+userdata[i]+'</option>'
					}
					$('.cuurpair select').html( str );
				}else if(data.code==02){
					
				}
			}
		});
	}
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
							olddata+=userdata[i]+'&'
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
	getlist({
		mkid:"all",//市场编号
		exnm:"all"//货币对
	});
	$('.query').click(function(){			
		var mkid=$('.markname select option:selected').attr('mkid'),
			exnm=$('.cuurpair select option:selected').attr('value');
		
		getlist({'mkid':mkid,'exnm':exnm});
	});
	function getlist( obj ){
		var mkid,exnm;
		if(obj.mkid!='all'){
			mkid=mkid;
		}else{
			mkid='all';
		}
		exnm=obj.exnm;
		$.ajax({
			url:'/fx/price/getPriceReclist.do',
			contentType:'application/json',
			async:true,
			type:"post",
			data:JSON.stringify( { 
				mkid:mkid,
				exnm:exnm
			}),
			success:function(data){
				if(data.code==01){
					userdata=JSON.parse( data.codeMessage );
					ren({'cols':cols,'wh':wh,'userdata':userdata,'checked':true});
				}else{
					ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
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
	//和添加有关；
	
	//添加页面中的添加按钮；
	$('body',parent.document).on('click','.dtadd',function(){
		var txt=$('.priceget .dtleft .tabpbgc',parent.document).text(),
			trcd=$('.priceget .dtleft .tabpbgc',parent.document).attr('value'),arr=[];
		$('.dtright ul li',parent.document).each(function(i,v){
			arr.push( $(v).text() );
		});
		if( $('.priceget .dtleft .tabpbgc',parent.document).length>0){
			if( arr.indexOf(txt)==-1){
				$('.dtright ul',parent.document).append( "<li trcd="+trcd+">"+txt+"</li>");
			}else{
				dialog.choicedata('该货币对已存在!','priceget');	
			}
		}else{
			dialog.choicedata('请先选择一条数据!','priceget');
		}
	});
	//添加页面中的全选全删按钮；
	$('body',parent.document).on('click','.allche',function(){
		$('.dtright ul',parent.document).html( $('.priceget .dtleft ul',parent.document).html() );
	});
	$('body',parent.document).on('click','.allrem',function(){
		$('.dtright ul',parent.document).html(' ');
	});
	//删除按钮；
	$('body',parent.document).on('click','.dtbot',function(){
		if( $('.dtright ul li',parent.document).hasClass('tabpbgc') ){
			$('.dtright ul li.tabpbgc',parent.document).remove();
		}else{
			dialog.choicedata('请先选择一条数据!','priceget');
		}
	});
	$('body',parent.document).on('click','.dbrem',function(){
		if( $('.dbright ul li',parent.document).hasClass('tabpbgc') ){
			$('.dbright ul li.tabpbgc',parent.document).remove();
		}else{
			dialog.choicedata('请先选择一条数据!','priceget');
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
			dialog.choicedata('该日历规则已存在!','priceget');	
		}
	});
	//添加保存；
	$('body',parent.document).on('click','.dealsave',function(){
		var arr='',brr='',crr=[],a=$('.small option:selected').text(),
		b=$('.ttsmall option:selected').attr('calendarid'),
		wrongnum=0;;
		$('.dtright ul li',parent.document).each(function(i,v){
			if(i==0){
				arr+=$(v).attr('trcd');
			}else{
				arr+='&'+$(v).attr('trcd');
			}
		});
		$('.dbright ul li',parent.document).each(function(i,v){
			if(i==0){
				brr+=$(v).attr('calendarid');
			}else{
				brr+='&'+$(v).attr('calendarid');
			}
			crr.push( {'calendarid':$(v).attr('calendarid')});
		});
		if( arr.length<=0){
			wrongnum++;
			dialog.choicedata('请选择货币对!','priceget');
		}else{
			if( brr.length<=0){
				wrongnum++;
				dialog.choicedata('请选择日历规则!','priceget');
			}
		}
		
		if( wrongnum==0){
			$.ajax({
		   		url:'/fx/price/adSaveCheckPriceRec.do',
		   		type:'post',
		   		contentType:'application/json',
		   		async:true,
		   		data:JSON.stringify({
		   			userKey:userkey,
		   			calList:crr,
			   		pricerecBean:{
			   			calendarID:brr,
			   			exnm:arr,
			   			mkid:$('.markna option:selected',parent.document).attr('mkid'),
			   			mknm:$('.markna option:selected',parent.document).text()
		   			}
		   		}),
		   		success:function(data){
		   			$('.dealsave',parent.document).closest('.zhezhao').remove();
		   		    if(data.code==01){
		   		    	dialog.choicedata(data.codeMessage,'priceget');
		   		    	getlist({
		   		    		mkid:$('.markname select option:selected').attr('mkid'),
		   					exnm:$('.cuurpair select option:selected').attr('value')
			   		 	});
		   			}else if(data.code==02){
		   				dialog.choicedata(data.codeMessage,'priceget');
		   			}
		   		}
		   	});
		}
	});
	$('body',parent.document).on('click','.priceget .sure,.priceget .cancel,.priceget .close,.dealcance',function(){
		//关闭选择一条数据;
	   $(this).closest('.zhezhao').remove();
	});
	$('.rulelev').change(function(){
		var str='';
		$('.calalev',parent.document).html('');
		var obj=$('.rulelev option:selected').attr('tit');
		if(obj==''||obj=='2'){
			
		}else{
			$.ajax({
				url:'/fx/getCalenLevel.do',
				type:'post',
				contentType:'application/json',
				data:obj,
				async:true,
				success:function(data){
					if(data.code==01){
						userdata=data.codeMessage;
						for( var i=0,num=userdata.length;i<num;i++){
							str+='<option calendarid='+userdata[i].calendarID+'>'+userdata[i].calendarName+'</option>'
						}
						$('.calalev').html(str);
					}else if(data.code==02){
						
					}
				}
			});
		}
    });
	//点击批量修改；
	$('.batchmodify').click(function(){
		var checknum=0,arr=[];
		if( $('.rulelev option:selected').text()=='所有'){
			dialog.choicedata('请选择规则等级!','priceget');
		}else{
			$('.box tbody tr').each(function(i,v){
			//	console.log(	$(v).find('td:eq(0) input[type="checkbox"]').prop('checked') );
				if( $(v).find('td:eq(0) input[type="checkbox"]').prop('checked') ){
					checknum++;
					arr.push({
						'mkid':$(v).find('td:eq(1) span').text(),
						'exnm':$(v).find('td:eq(3) span').text(),
						'calendarID':$(v).find('td:eq(0)').attr('calarid'),
					});
				}
			});
			if(checknum==0){
				dialog.choicedata('请先勾选一条数据!','priceget');
			}else{
				$.ajax({
					url:'/fx/price/upListPriceRec.do',
					type:'post',
					contentType:'application/json',
					data:JSON.stringify({
						userKey:userkey,
						calendarId:$('.calalev option:selected').attr('calendarid'),
						priceRecList:arr
					}),
					async:true,
					success:function(data){
						if(data.code==01){
							dialog.choicedata(data.codeMessage,'priceget');
							var mkid=$('.markname select option:selected').attr('mkid'),
								exnm=$('.cuurpair select option:selected').attr('value');
						
							getlist({'mkid':mkid,'exnm':exnm});
						}else if(data.code==02){
							dialog.choicedata(data.codeMessage,'priceget');
						}
					}
				});
			}
		}
	});	
})
