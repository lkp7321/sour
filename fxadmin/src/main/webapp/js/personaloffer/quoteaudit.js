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
		ww=$(window).width()-260+"px";;
	    $('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
		$('.audit_open').text('')
	
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},userdata,stfg,
		userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
   if(product=='P004'){
	   $('.audit_open').text('全部复核')
	   $('.audit_nopass').text('全部未通过')
	   //列参数;
	    var cols = [
            { title:'市场分类', name:'bcfg' ,width:100, align:'left' }, 
	        { title:'价格类型', name:'tpnm' ,width:100, align:'center' },
	        { title:'期限', name:'term' ,width:80, align:'right'},
	        { title:'货币对', name:'exnm' ,width:150, align:'left'},
	        { title:'钞汇标准', name:'cxfg' ,width:80, align:'center'},
	        { title:'买入价', name:'neby' ,width:80, align:'right'},
	        { title:'卖出价', name:'nesl' ,width:80, align:'right'},
	        { title:'中间价', name:'nemd' ,width:80, align:'right'},
	        { title:'状态', name:'stfg' ,width:100, align:'center'}
	    ];
	   renfn( {'url':'selectProductVaList.do','data':{'userKey':userKey,'stfg':''}});
	    function renfn(obj){
	    	$.ajax({
	    		url:obj.url,
	    		type:'post',
	    		contentType:'application/json',
	    		data:JSON.stringify(obj.data),
	    		async:false,
	    		success:function(data){
	    			if(data.code==00){
	    				userdata=JSON.parse( data.codeMessage );
	    				//render( userdata );
	    				render({'cols':cols,'wh':wh,'userdata':userdata});
	    			}else if(data.code==01||data.code==02){
	    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
	    			}
	    		}
	    	});
	    }
	    //更改select;
	    $('.selopti').change(function(){
	    		var txt=$('option:selected').text();
	    		$(this).next().find('.pub_button').css({'background':'rgba(0,0,0,0.5)'});
				$(this).next().find('.pub_button').attr('disabled','disabled');
				$(this).next().next().find('.pub_button').css({'background':'rgba(0,0,0,0.5)'});
				$(this).next().next().find('.pub_button').attr('disabled','disabled');
	    		if(txt=='所有'){
	    			stfg='';
	    		}else if(txt=='未审批'){
	    			stfg=0;
	    			$(this).next().find('.pub_button').css({'background':'#5a8bff'});
	    			$(this).next().find('.pub_button').removeAttr('disabled');
	    			$(this).next().next().find('.pub_button').css({'background':'#5a8bff'});
	    			$(this).next().next().find('.pub_button').removeAttr('disabled');
	    		}else if(txt=='审批'){
	    			stfg=1;
	    		}else if(txt=='未通过'){
	    			stfg=2;
	    		}
	    		 renfn( {'url':'selectProductVaList.do','data':{'userKey':userKey,'stfg':stfg}});
	    });
	    //点击全部复核和全部未通过；
	    $('.audit_open,.audit_nopass').click(function(){
	    	var hqVoList=[];
	    	$('.box tbody tr').each(function(i,v){
	    		 
	    			if( $(v).find('td').eq(4).find('span').text()=='钞'){
						cxfg='2';
					}else{
						cxfg='1';
					}
	    			hqVoList.push({
	    				'bcfg':$(v).find('td').eq(0).find('span').text(),
	    				'mkid':$(v).find('td').eq(1).attr('mkid'),
						'tpfg':$(v).find('td').eq(1).attr('tpfg'),
						'excd':$(v).find('td').eq(1).attr('excd'),
						'neby':$(v).find('td').eq(5).find('span').text(),
						'nesl':$(v).find('td').eq(6).find('span').text(),
						'nemd':$(v).find('td').eq(7).find('span').text(),
						'term':$(v).find('td').eq(2).find('span').text(),
						'exnm':$(v).find('td').eq(3).find('span').text(),
						'cxfg':cxfg
	    			});
	    		 
	    	});
	    	 
	    	auditnopass( {'userKey':userKey,labnm:$(this).text(),'hqVoList':hqVoList} );
	    	 
	    });
	    function auditnopass(obj){
	    	$.ajax({
	    		url:'/fx/updateHandQuoteState.do',
	    		type:'post',
	    		contentType:'application/json',
	    		data:JSON.stringify(obj),
	    		async:false,
	    		success:function(data){
	    			if(data.code==00){
	    				dialog.choicedata(data.codeMessage,'quoteaudit');
	    				renfn( {'url':'selectProductVaList.do','data':{'userKey':userKey,'stfg':stfg}});
	    			}else if(data.code==01){
	    				dialog.choicedata(data.codeMessage,'quoteaudit');
	    			}
	    			
	    		}
	    	});
	    }
   }else{
	   $('.audit_open').text('复核')
	   $('.audit_nopass').text('未通过')
	 //列参数;
	    var cols = [
	        { title:'价格类型', name:'tpnm' ,width:100, align:'center' },
	        { title:'期限', name:'term' ,width:80, align:'right'},
	        { title:'货币对', name:'exnm' ,width:150, align:'left'},
	        { title:'钞汇标准', name:'cxfg' ,width:80, align:'center'},
	        { title:'买入价', name:'neby' ,width:80, align:'right'},
	        { title:'卖出价', name:'nesl' ,width:80, align:'right'},
	        { title:'中间价', name:'nemd' ,width:80, align:'right'},
	        { title:'状态', name:'stfg' ,width:100, align:'center'}
	    ];
	    renfn( {'url':'selectProductVaList.do','data':{'userKey':userKey,'stfg':''}});
	    function renfn(obj){
	    	$.ajax({
	    		url:obj.url,
	    		type:'post',
	    		contentType:'application/json',
	    		data:JSON.stringify(obj.data),
	    		async:false,
	    		success:function(data){
	    			if(data.code==00){
	    				userdata=JSON.parse( data.codeMessage );
	    				//render( userdata );
	    				render({'cols':cols,'wh':wh,'userdata':userdata,'checked':'true'});
	    			}else if(data.code==01||data.code==02){
	    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
	    			}
	    		}
	    	});
	    }
	   
	    //更改select;
	    $('.selopti').change(function(){
	    		var txt=$('option:selected').text();
	    		$(this).next().find('.pub_button').css({'background':'rgba(0,0,0,0.5)'});
				$(this).next().find('.pub_button').attr('disabled','disabled');
				$(this).next().next().find('.pub_button').css({'background':'rgba(0,0,0,0.5)'});
				$(this).next().next().find('.pub_button').attr('disabled','disabled');
	    		if(txt=='所有'){
	    			stfg='';
	    		}else if(txt=='未审批'){
	    			stfg=0;
	    			$(this).next().find('.pub_button').css({'background':'#5a8bff'});
	    			$(this).next().find('.pub_button').removeAttr('disabled');
	    			$(this).next().next().find('.pub_button').css({'background':'#5a8bff'});
	    			$(this).next().next().find('.pub_button').removeAttr('disabled');
	    		}else if(txt=='审批'){
	    			stfg=1;
	    		}else if(txt=='未通过'){
	    			stfg=2;
	    		}
	    		 renfn( {'url':'selectProductVaList.do','data':{'userKey':userKey,'stfg':stfg}});
	    });
	    
	    //点击复核和未通过；
	    $('.audit_open,.audit_nopass').click(function(){
	    	var hqVoList=[];
	    	$('.box tr').each(function(i,v){
	    		if( $(v).find('input').prop('checked') ){
	    			if( $(v).find('td').eq(4).find('span').text()=='钞'){
						cxfg='2';
					}else{
						cxfg='1';
					}
	    			hqVoList.push({
	    				'mkid':$(v).find('td').eq(1).attr('mkid'),
						'tpfg':$(v).find('td').eq(1).attr('tpfg'),
						'excd':$(v).find('td').eq(1).attr('excd'),
						'neby':$(v).find('td').eq(5).find('span').text(),
						'nesl':$(v).find('td').eq(6).find('span').text(),
						'nemd':$(v).find('td').eq(7).find('span').text(),
						'term':$(v).find('td').eq(2).find('span').text(),
						'exnm':$(v).find('td').eq(3).find('span').text(),
						'cxfg':cxfg
	    			});
	    		}
	    	});
	    	if( hqVoList.length>0){
	    		auditnopass( {'userKey':userKey,labnm:$(this).text(),'hqVoList':hqVoList} );
	    	}else{
	    		dialog.choicedata('请您先勾选一条数据!','quoteaudit');
	    	}
	    	
	    });
	    function auditnopass(obj){
	    	$.ajax({
	    		url:'/fx/updateHandQuoteState.do',
	    		type:'post',
	    		contentType:'application/json',
	    		data:JSON.stringify(obj),
	    		async:false,
	    		success:function(data){
	    			if(data.code==00){
	    				dialog.choicedata(data.codeMessage,'quoteaudit');
	    				renfn( {'url':'selectProductVaList.do','data':{'userKey':userKey,'stfg':stfg}});
	    			}else if(data.code==01){
	    				dialog.choicedata(data.codeMessage,'quoteaudit');
	    			}
	    			
	    		}
	    	});
	    }
   
   }
   $('body',parent.document).on('click','.quoteaudit .sure',function(){
		//关闭选择一条数据;
	   $(this).closest('.zhezhao').remove();
	});
   function render(obj){
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
		 mmg.on('loadSuccess',function(e,data){
			 $('.box').find('tbody tr').each(function(i,v){
				 $(v).find('td').eq('1').attr('mkid',obj.userdata[i].mkid);
				 $(v).find('td').eq('1').attr('tpfg',obj.userdata[i].tpfg);
				 $(v).find('td').eq('1').attr('excd',obj.userdata[i].excd);
			 });
		 });
	}
})