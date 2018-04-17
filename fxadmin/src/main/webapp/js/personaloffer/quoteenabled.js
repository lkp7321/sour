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
		
		var product=sessionStorage.getItem('product'),userdata,
        userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
     if(product=='P004'){
    	$('.ennable_open').text('全部启用');
    	$('.ennable_stop').text('全部停用'); 
    	//列参数;
	    var cols = [
	        { title:'市场分类', name:'bcfg' ,width:100, align:'left' },        
	        { title:'价格类型', name:'tpnm' ,width:100, align:'center' },
	        { title:'期限', name:'term' ,width:100, align:'right'},
	        { title:'货币对', name:'exnm' ,width:100, align:'left'},
	        { title:'钞汇标志', name:'cxfg' ,width:80, align:'center'},
	        { title:'买入价', name:'neby' ,width:120, align:'right'},
	        { title:'卖出价', name:'nesl' ,width:120, align:'right'},
	        { title:'中间价', name:'nemd' ,width:120, align:'right'},
	        { title:'使用标志', name:'ocfg' ,width:60, align:'center'}
	    ];
	    getPriceList();
	    function getPriceList(){
			   $.ajax({
					url:'/fx/selectHandPriceList.do',
					type:'post',
					contentType:'application/json',
					data:userKey,
					async:true,
					success:function(data){
						if(data.code==00){
							userdata=JSON.parse( data.codeMessage );
							ren({'cols':cols,'wh':wh,'userdata':userdata});
						 }else if(data.code==01||data.code==02){
							ren({'cols':cols,'wh':wh,'userdata':''});
						}
						
					} 
				});
		  }
	    $('.ennable_open,.ennable_stop').click(function(){
	    	var arr=[],usfg=$(this).text(),url
	    	 $('.box tbody tr').each(function(i,v){
				 arr.push({
					  'tpfg':$(v).find('td').eq('1').attr('tpfg'),
					  'mkid':$(v).find('td').eq('2').attr('mkid'),
					  'excd':$(v).find('td').eq('3').attr('excd'),
					  'term':$(v).find('td').eq(2).find('span').text(),
					  'exnm':$(v).find('td').eq(3).find('span').text(),
					  'cxfg':$(v).find('td').eq(4).find('span').text(),
					  'ocfg':$(v).find('td').eq(8).find('span').text(),
				 });
			 });
			 $.each(arr,function(i,e){
				 
				 if(e.cxfg=="钞"){
					 e.cxfg='2';
				 }else{
					 e.cxfg='1';
				 }
				  
			 })
		   
		   if(usfg=='全部启用'){
			   url='openHandQuote.do'
		   }else{
			   url='closeHandQuote.do'
		   }
		 
		    PriceVo={'userKey':userKey ,'hqVoList':arr};	 
		    $.ajax({
		    	url:url,
		        type:'post',
		    	contentType:'application/json',
		    	data:JSON.stringify(PriceVo),
		    	async:true,
		    	dataType:'json',
		    	success:function(data){
		    		if(data.code==00){
		    			 getPriceList();
		    			dialog.choicedata(data.codeMessage,'quoteenabled');
		    		}else if(data.code==01){
		    			//异常 
		    			dialog.choicedata(data.codeMessage,'quoteenabled');
		    		} 
		    	  }
		      }); 
	    })
     }else{
    	$('.ennable_open').text('启用'); 
    	$('.ennable_stop').text('停用');
    	//列参数;
    	    var cols = [
    	        { title:'价格类型', name:'tpnm' ,width:100, align:'center' },
    	        { title:'期限', name:'term' ,width:100, align:'right'},
    	        { title:'货币对', name:'exnm' ,width:100, align:'left'},
    	        { title:'钞汇标志', name:'cxfg' ,width:80, align:'center'},
    	        { title:'买入价', name:'neby' ,width:120, align:'right'},
    	        { title:'卖出价', name:'nesl' ,width:120, align:'right'},
    	        { title:'中间价', name:'nemd' ,width:120, align:'right'},
    	        { title:'使用标志', name:'ocfg' ,width:60, align:'center'}
    	    ];
    	    
    	    getPriceList();
    	   //启用框停用框弹出
    		$('.ennable_open,.ennable_stop').click(function(){
    			var a=0,arr=[];
    			$('.box tr').each(function(){
    				if( $(this).find('input[type="checkbox"]').prop('checked')==true){
    					a++;
    				}
    			})
    			if(a==0){
    				dialog.choicedata('请先勾选一条数据再进行此操作','quoteenabled');
    			}else{
    				 $('.box tr').each(function(i,v){
    					if( $(v).find('input[type="checkbox"]').prop('checked') ){
    					  arr.push({
    						  'tpfg':$(v).find('td').eq('1').attr('tpfg'),
    						  'mkid':$(v).find('td').eq('2').attr('mkid'),
    						  'excd':$(v).find('td').eq('3').attr('excd'),
    						  'term':$(v).find('td').eq(2).find('span').text(),
    						  'exnm':$(v).find('td').eq(3).find('span').text(),
    						  'cxfg':$(v).find('td').eq(4).find('span').text()
    				      });
    					}
					
				 });
				 $.each(arr,function(i,e){
					 
					 if(e.cxfg=="钞"){
						 e.cxfg=2;
					 }else{
						 e.cxfg=1;
					 }
					  
				 })
    			 var usfg=$(this).text(),url;
    			   if(usfg=='启用'){
    				   url='openHandQuote.do'
    			   }else{
    				   url='closeHandQuote.do'
    			   }
    			 
    			    PriceVo={'userKey':userKey ,'hqVoList':arr};	 
    			    $.ajax({
    			    	url:url,
    			        type:'post',
    			    	contentType:'application/json',
    			    	data:JSON.stringify(PriceVo),
    			    	async:true,
    			    	dataType:'json',
    			    	success:function(data){
    			    		if(data.code==00){
    			    			 getPriceList();
    			    			dialog.choicedata(data.codeMessage,'quoteenabled');
    			    		}else if(data.code==01){
    			    			//异常 
    			    		} 
    			    	  }
    			      });  	
    			}
    		});
    	 
    		
    		
    		  function getPriceList(){
    			   $.ajax({
    					url:'/fx/selectHandPriceList.do',
    					type:'post',
    					contentType:'application/json',
    					data:userKey,
    					async:true,
    					success:function(data){
    						if(data.code==00){
    							userdata=JSON.parse( data.codeMessage );
    							ren({'cols':cols,'wh':wh,'userdata':userdata,'checked':'true'});
    						 }else if(data.code==01||data.code==02){
    							ren({'cols':cols,'wh':wh,'userdata':''});
    						}
    						
    					} 
    				});
    		  }
    		
    		
    		
     }	
     $('body',parent.document).on('click','.quoteenabled .sure',function(){
			//关闭选择一条数据;
		   $(this).closest('.zhezhao').remove();
	 });
    //table的渲染
	function ren(obj){
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
				horizrailenabled:false
		  });
		   mmg.on('loadSuccess',function(e,data){
			   $('.box').find('tbody tr').each(function(i,v){
				 $(v).find('td').eq('1').attr('tpfg',obj.userdata[i].tpfg);
				 $(v).find('td').eq('2').attr('mkid',obj.userdata[i].mkid);
				 $(v).find('td').eq('3').attr('excd',obj.userdata[i].excd);
				 
			 });
		 }) 
	  }
 })