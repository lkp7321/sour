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
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
		
	var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},userdata,
		userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	if(product=='P004'){
		$('.hands_submit').text('全部提交')
		//列参数;
	    var cols = [
            { title:'市场分类', name:'bcfg' ,width:100, align:'left' },         
	        { title:'价格类型', name:'tpnm' ,width:100, align:'center' },
	        { title:'期限', name:'term' ,width:80, align:'right'},
	        { title:'货币对', name:'exnm' ,width:150, align:'left'},
	        { title:'钞汇标准', name:'cxfg' ,width:80, align:'center'},
	        { title:'买入价', name:'neby' ,width:100, align:'right'},
	        { title:'卖出价', name:'nesl' ,width:80, align:'right'},
	        { title:'中间价', name:'nemd' ,width:80, align:'right'},
	        { title:'状态', name:'stfg' ,width:100, align:'center'}
	    ];
		//渲染数据
		function render(obj){
			$('.boxtop').html('');
	    	$('#ascrail2000').remove();
	    	$('.boxtop').append('<div class="box"></div>');
	    	 var mmg = $('.box').mmGrid({
				height:wh, 
				cols: cols
				,items:obj
		        , nowrap:true
		        ,fullWidthRows:true
		        ,showBackboard:true
		        ,checkCol:false
		        ,loadingText:'正在载入'
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
					 $(v).find('td').eq('1').attr('mkid',obj[i].mkid);
					 $(v).find('td').eq('1').attr('tpfg',obj[i].tpfg);
					 $(v).find('td').eq('1').attr('excd',obj[i].excd);
				 });
			 });
		}
	 //请求列表数据；
	   renfn();
	   function renfn(){
		   $.ajax({
				url:'/fx/selectProductVaList.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify({'userKey':userKey,'stfg':''}),
				async:true,
				success:function(data){
					if(data.code==00){
						userdata=JSON.parse( data.codeMessage );
						render( userdata);
					}else if(data.code==01){
						render('');
					}
				}
			});   
	   } 
	   //提交框弹出
		$('.hands_submit').click(function(){
			var arr=[],cxfg;
			$('.box tbody tr').each(function(i,v){
				if($(v).find('td').eq(8).find('span').text()!='未审核'){
					 if( $(v).find('td').eq(4).find('span').text()=='钞'){
						cxfg='2';
					}else{
						cxfg='1';
					}
					arr.push({
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
				}
			 });
		    if( arr.length>0){
				$.ajax({
					url:'/fx/updateHandQuoteState.do',
					type:'post',
					contentType:'application/json',
					data:JSON.stringify({'userKey':userKey,'labnm':'全部提交','hqVoList':arr}),
					async:true,
					success:function(data){
						if(data.code==00){
							dialog.choicedata(data.codeMessage,'quoteentry');
							renfn();
						}else{
							dialog.choicedata(data.codeMessage,'quoteentry');
						}
					}
				});
			}else{
				dialog.choicedata('请您先勾选一条数据再提交!','quoteentry');
			}
		}); 
	
	}else{
		$('.hands_submit').text('提交')
		//列参数;
	    var cols = [
	        { title:'价格类型', name:'tpnm' ,width:100, align:'center' },
	        { title:'期限', name:'term' ,width:80, align:'right'},
	        { title:'货币对', name:'exnm' ,width:150, align:'left'},
	        { title:'钞汇标准', name:'cxfg' ,width:80, align:'center'},
	        { title:'买入价', name:'neby' ,width:100, align:'right'},
	        { title:'卖出价', name:'nesl' ,width:80, align:'right'},
	        { title:'中间价', name:'nemd' ,width:80, align:'right'},
	        { title:'状态', name:'stfg' ,width:100, align:'center'}
	    ];
		//渲染数据
		function render(obj){
			$('.boxtop').html('');
	    	$('#ascrail2000').remove();
	    	$('.boxtop').append('<div class="box"></div>');
	    	//点击复选框；
	    	$('.box').on('click','tr input',function(){
	    		if( $(this).parents('tr').find('td').eq(8).find('span').text()=='未审核'){
	    			$(this).prop('checked','');
	    		}else{
	    			if( $(this).prop('checked') ){
	    				$(this).prop('checked','checked');
	    			}else{
	    				$(this).prop('checked','');
	    			}
	    		}
	    	});
	    	//点击全选框
	    	$('.boxtop').on('click','.checkAll',function(){
	    		 if($('.checkAll').is(":checked")){
			    	 $('.box').find('tbody tr').each(function(i,v){
						  if($(v).find('td').eq('8').find('span').text()=='未审核'){
						  $(v).find('input').prop('checked','')
					     }
				    });
			     }
	    	});
	    	 var mmg = $('.box').mmGrid({
				height:wh, 
				cols: cols
				,items:obj
		        , nowrap:true
		        ,fullWidthRows:true
		        ,showBackboard:true
		        ,checkCol:true
		        ,loadingText:'正在载入'
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
					 $(v).find('td').eq('1').attr('mkid',obj[i].mkid);
					 $(v).find('td').eq('1').attr('tpfg',obj[i].tpfg);
					 $(v).find('td').eq('1').attr('excd',obj[i].excd);
				 });
			 });
		}
	 //请求列表数据；
	   renfn();
	   function renfn(){
		   $.ajax({
				url:'/fx/selectProductVaList.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify({'userKey':userKey,'stfg':''}),
				async:true,
				success:function(data){
					if(data.code==00){
						userdata=JSON.parse( data.codeMessage );
						render( userdata);
					}else if(data.code==01){
						render('');
					}
				}
			});   
	   }
	   //提交框弹出
		$('.hands_submit').click(function(){
			var arr=[],cxfg;
			$('.box tr').each(function(i,v){
				if( $(v).find('input').prop('checked') ){
					if( $(v).find('td').eq(4).find('span').text()=='钞'){
						cxfg='2';
					}else{
						cxfg='1';
					}
					arr.push({
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
			if( arr.length>0){
				$.ajax({
					url:'/fx/updateHandQuoteState.do',
					type:'post',
					contentType:'application/json',
					data:JSON.stringify({'userKey':userKey,'labnm':'提交','hqVoList':arr}),
					async:true,
					success:function(data){
						if(data.code==00){
							dialog.choicedata(data.codeMessage,'quoteentry');
							renfn();
						}else{
							dialog.choicedata(data.codeMessage,'quoteentry');
						}
					}
				});
			}else{
				dialog.choicedata('请您先勾选一条数据再提交!','quoteentry');
			}
		}); 
	}
	
	
	
	
 
	
	//修改框弹出
	$('.modify').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			if( $('.box tr.selected td:eq(8) span').text()!='未审核'){
				//修改弹出框;+获取当前selected的数据;
	           dialog.quoteentrymodify("quoteentry");
	           $('.quoteentry .mkid input',parent.document).val( $('.box tr.selected td:eq(1)').attr('mkid'));
	           $('.quoteentry .tpnm input',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
	           
	           $('.quoteentry .term input',parent.document).val( $('.box tr.selected td:eq(2) span').text() );
	           $('.quoteentry .exnm input',parent.document).val( $('.box tr.selected td:eq(3) span').text() );
	           
	           $('.quoteentry .nemd input',parent.document).val( $('.box tr.selected td:eq(7) span').text() );
	           $('.quoteentry .neby input',parent.document).val( $('.box tr.selected td:eq(5) span').text() );
	           $('.quoteentry .nesl input',parent.document).val( $('.box tr.selected td:eq(6) span').text() );
	        
	           if( $('.box tr.selected td:eq(4) span').text()=='钞'){
	        	   $('.cxfg .priceradio0',parent.document).prop('checked','checked');
	           }else{
	        	   $('.cxfg .priceradio1',parent.document).prop('checked','checked');
	           }
	           //点击钞汇标志返回false；
	           $('.cxfg input[type="radio"]',parent.document).click(function(){
	        	   return false;
	           });
	           //改变买入和卖出，更改中间价格；
	           $('.neby input',parent.document).change(function(){
	        	   var bye=$(this).val()*1,
	        	   	   sale=$('.nesl input',parent.document).val()*1;
	 
	        	   if(bye>=sale&&bye!=''&&sale!=''){
	        		   dialog.choicedata('买入价不能大于等于卖出价!','quoteentry');
	        	   }else if( /^\d+$|^\d{1,}\.{1}\d+$/.test( $(this).val()) && /^\d+$|^\d{1,}\.\d+$/.test( $('.nesl input',parent.document).val() ) ){
	        		   if( $('.box tr.selected td:eq(3) span').text().indexOf('JPY')==-1){
	        			   $('.nemd input',parent.document).val( ((bye+sale)/2).toFixed(4));
	        		   }else{
	        			   $('.nemd input',parent.document).val( ((bye+sale)/2).toFixed(2));
	        		   }
	        	   }
	           });
	           $('.nesl input',parent.document).change(function(){
	        	   var sale=$(this).val()*1,
	    	   	   	   bye=$('.neby input',parent.document).val()*1;
	        	   if(bye>=sale&&bye!=''&&sale!=''){
	        		   dialog.choicedata('买入价不能大于等于卖出价!','quoteentry');
	        	   }else if( /^\d+$|^\d{1,}\.\d+$/.test( $(this).val()) && /^\d+$|^\d{1,}\.\d+$/.test( $('.neby input',parent.document).val() ) ){
	        		   if( $('.box tr.selected td:eq(3) span').text().indexOf('JPY')==-1){
	        			   $('.nemd input',parent.document).val( ((bye+sale)/2).toFixed(4));
	        		   }else{	 
	        			   $('.nemd input',parent.document).val( ((bye+sale)/2).toFixed(2));
	        		   }
	        	   }
	           });
	           $('.neby input',parent.document).blur(function(){
	        	   if( $('.quoteentry .neby input',parent.document).val()==''){
	   	   			$('.quoteentry .neby .formtip',parent.document).remove();
	   	   			$('.quoteentry .neby',parent.document).append('<i class="formtip">请输入买入价!</i>');
	   	   			
	   		   		}else if( !/^\d+$|^\d{1,}\.\d+$/.test($('.quoteentry .neby input',parent.document).val()) ){
	   		   			$('.quoteentry .neby .formtip',parent.document).remove();
	   		   			$('.quoteentry .neby',parent.document).append('<i class="formtip">买入价只能为数字!</i>');
	   		   		}else{
	   		   			$('.quoteentry .neby .formtip',parent.document).remove();
	   		   		}
	           });
	           $('.nesl input',parent.document).blur(function(){
	        	   if( $('.quoteentry .nesl input',parent.document).val()==''){
			   			$('.quoteentry .nesl .formtip',parent.document).remove();
			   			$('.quoteentry .nesl',parent.document).append('<i class="formtip">请输入卖出价!</i>');
			   			
			   		}else if(!/^\d+$|^\d{1,}\.\d+$/.test($('.quoteentry .nesl input',parent.document).val()) ){
			   			$('.quoteentry .nesl .formtip',parent.document).remove();
			   			$('.quoteentry .nesl',parent.document).append('<i class="formtip">卖出价只能为数字!</i>');
			   		}else{
			   			$('.quoteentry .nesl .formtip',parent.document).remove();
			   		}
	           });
			}else{
				dialog.choicedata('该信息尚未审核!','quoteentry');
			}
		}else{
			dialog.choicedata('请先选择一条数据!','quoteentry');
		}
	});
	//点击修改弹窗的关闭和取消;
	$('body',parent.document).on('click','.quoteen_close,.quoteen_cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
	//点击修改弹窗提交;
	$('body',parent.document).on('click','.quoteen_submit',function(){
		 //数据处理
		if( $('.quoteentry .neby input',parent.document).val()==''){
			$('.quoteentry .neby .formtip',parent.document).remove();
			$('.quoteentry .neby',parent.document).append('<i class="formtip">请输入买入价!</i>');
			
		}else if( !/^\d+$|\d{1,}\.\d+/.test($('.quoteentry .neby input',parent.document).val()) ){
			$('.quoteentry .neby .formtip',parent.document).remove();
			$('.quoteentry .neby',parent.document).append('<i class="formtip">买入价只能为数字!</i>');
		}else{
			$('.quoteentry .neby .formtip',parent.document).remove();
		}
		if( $('.quoteentry .nesl input',parent.document).val()==''){
			$('.quoteentry .nesl .formtip',parent.document).remove();
			$('.quoteentry .nesl',parent.document).append('<i class="formtip">请输入卖出价!</i>');
		}else if(!/^\d+$|\d{1,}\.\d+/.test($('.quoteentry .nesl input',parent.document).val()) ){
			$('.quoteentry .nesl .formtip',parent.document).remove();
			$('.quoteentry .nesl',parent.document).append('<i class="formtip">卖出价只能为数字!</i>');
		}else{
			$('.quoteentry .nesl .formtip',parent.document).remove();
		}
		if(product=="P004"){
			var vo={'userKey':userKey,'hqVo':{
				'bcfg':$('.box tr.selected td:eq(0) span').text(),
				'mkid':$('.box tr.selected td:eq(1)').attr('mkid'),
				'tpfg':$('.box tr.selected td:eq(1)').attr('tpfg'),
				'excd':$('.box tr.selected td:eq(1)').attr('excd'),
				'neby':Number( $('.quoteentry .neby input',parent.document).val()  ),
				'nesl':Number( $('.quoteentry .nesl input',parent.document).val() ),
				'nemd':Number( $('.quoteentry .nemd input',parent.document).val() ),
				'term':$('.quoteentry .term input',parent.document).val(),
				'exnm':$('.quoteentry .exnm input',parent.document).val(),
				'cxfg':$('.quoteentry .cxfg input[name="priceradio"]:checked',parent.document).data('tit')
              }}
		}else{
			var vo={'userKey':userKey,'hqVo':{
				'mkid':$('.box tr.selected td:eq(1)').attr('mkid'),
				'tpfg':$('.box tr.selected td:eq(1)').attr('tpfg'),
				'excd':$('.box tr.selected td:eq(1)').attr('excd'),
				'neby':Number( $('.quoteentry .neby input',parent.document).val()  ),
				'nesl':Number( $('.quoteentry .nesl input',parent.document).val() ),
				'nemd':Number( $('.quoteentry .nemd input',parent.document).val() ),
				'term':$('.quoteentry .term input',parent.document).val(),
				'exnm':$('.quoteentry .exnm input',parent.document).val(),
				'cxfg':$('.quoteentry .cxfg input[name="priceradio"]:checked',parent.document).data('tit')
              }}
		}
		if( $('.quoteentry .pripubdivs .formtip',parent.document).length==0){
			var bye=$('.quoteentry .neby input',parent.document).val(),
				sale=$('.quoteentry .nesl input',parent.document).val();
			if( bye<sale ){
				$.ajax({
					url:'/fx/updateProduct.do',
					type:'post',
					contentType:'application/json',
					data:JSON.stringify(vo),
					async:true,
					success:function(data){
						if(data.code==00){
							$('.quoteen_submit',parent.document).parents('.zhezhao').remove();
							dialog.choicedata(data.codeMessage,'quoteentry','two');
							renfn();
						}else if(data.code==01){
							dialog.choicedata(data.codeMessage,'quoteentry');
						}
					}
				});
			}else{
				dialog.choicedata('买入价不能大于等于卖出价!','quoteentry');
			}
		}
	});
	
	
	
	$('body',parent.document).on('click','.quoteentry .sure',function(){
		//关闭选择一条数据;
		var tit=$(this).parents('.quoteentry').data('tit');
		if( tit=='two'){
			$('.zhezhao',parent.document).remove();
		}else{
			$(this).closest('.zhezhao').remove();
		}
	   
	});
})