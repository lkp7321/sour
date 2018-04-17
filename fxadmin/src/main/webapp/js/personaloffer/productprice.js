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
  
   //列参数;
    var cols = [
        { title:'价格类型', name:'tpnm' ,width:100, align:'center' },
        { title:'即期', name:'term' ,width:100, align:'right'},
        { title:'货币对', name:'exnm' ,width:150, align:'left'},
        { title:'钞汇标志', name:'cxfg' ,width:80, align:'center'},
        { title:'浮动点差', name:'mxdt' ,width:120, align:'right'},
        { title:'使用标志', name:'usfg' ,width:100, align:'center'}
       
    ];
    var usnm=sessionStorage.getItem('usnm'),
        product=sessionStorage.getItem('product'),
        loginuser={'usnm':usnm,'prod':product},
        userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
        
    if(product=='P999'){
		//请求产品列表
	    $.ajax({
			url:'/fx/pdtCom.do',
			type:'get',
			async:false,
			dataType:'json',
		    contentType:'application/json;charset=utf-8',
			success:function(data){
				var handdata=JSON.parse( data.codeMessage ),str='';
				if(data.code==00){
					for(var i in handdata){
						str+='<option value='+handdata[i].PTID +'>'+handdata[i].PTNM+'</option>'
					}
					$('.product').html('<select onmousedown="if(this.options.length>6){this.size=7}" onblur="this.size=0" onchange="this.size=0">'+str+'</select>');
				}else if(data.code==01){
					 
				}
			}
		});
	 }
     var prod=$('.product option:selected').val();
    if(product=="P999"){
		//请求列表
	    getPprice({'userKey':userKey,'prod':prod});
	  //根据列表的变化重新请求列表
        $('.product select').change(function(){
        	prod=$('.product option:selected').val();
        	 getPprice({'userKey':userKey,'prod':prod});
        })
	}else{
		//请求列表
	    getPprice({'userKey':userKey});
	}
    
     
    //添加框弹出
	$('.add').click(function(){
		  dialog.propricemodify("add");
		  //请求价格类型列表
		  $.ajax({
				url:'/fx/getTpfgList.do',
				type:'get',
				async:false,
				dataType:'json',
	 			contentType:'application/json',
	 			beforeSend:function(){
	 			     str='';
	 			},
				success:function(data){
					var datalist=JSON.parse(data.codeMessage);
					if(data.code==00){
						for(var i in datalist){
							str+='<option value='+datalist[i].tpfg+'>'+datalist[i].tpnm+'</option>'
						}
						$('.pricetype select',parent.document).html(str);
					}else if(data.code==01){
						 
					}
				}
			});
		  changecurrlist();
		 //浮动点差失焦；
		  $('.priceterm input',parent.document).on('blur',function(){
			  if( $('.priceterm input',parent.document).val()==''){
				  $('.priceterm .formtip',parent.document).remove();
	      		  $('.priceterm',parent.document).append('<i class="formtip">期限不能为空!</i>');
			  }else{
				  $('.priceterm .formtip',parent.document).remove();
			  }
		  });
          $('.pricepoint input',parent.document).on('blur',function(){
       	   if( $('.pricepoint input',parent.document).val()==''){
      			$('.pricepoint .formtip',parent.document).remove();
      			$('.pricepoint',parent.document).append('<i class="formtip">浮动点差不能为空!</i>');
	       	   }else if(!/^\d{0,5}$/.test( $('.pricepoint input',parent.document).val() ) ){
	       			$('.pricepoint .formtip',parent.document).remove();
	       			$('.pricepoint',parent.document).append('<i class="formtip">浮动点差必须为5位以内的整数!</i>');
	       	   }else{
	       			$('.pricepoint .formtip',parent.document).remove();
	       		}
          });
		  //请求货币对列表
		  function changecurrlist(){
			  var term;
			 if(  $('.priceterm input',parent.document).val() ){
				 term=parseInt( $('.priceterm input',parent.document).val() );
			 }else{
				 term='';
			 }
			 
			 if(product=="P999"){
				 var pVo={
						  'userKey':userKey ,
						  'prod':prod,
						  'tpfg':$('.pricetype select option:selected',parent.document).attr('value'),
						  'term':term,
						  'cxfg':$('.pricesign input[name="priceradio0"]:checked',parent.document).data('tit')
					} 
			 }else{
				var pVo={
						  'userKey':userKey ,
						  'tpfg':$('.pricetype select option:selected',parent.document).attr('value'),
						  'term':term,
						  'cxfg':$('.pricesign input[name="priceradio0"]:checked',parent.document).data('tit')
					} 
			 }
			  $.ajax({
					url:'/fx/getExnmList.do',
					type:'post',
					async:false,
					dataType:'json',
					data:JSON.stringify(pVo),
		 			contentType:'application/json',
		 			beforeSend:function(){
		 			     str='';
		 			},
					success:function(data){
						if(data.code==00){
							var datalist=JSON.parse(data.codeMessage);
							for(var i in datalist){
								str+='<option value='+datalist[i].excd+'>'+datalist[i].exnm+'</option>'
							}
							$('.pcurrency select',parent.document).html(str);
						}else if(data.code==01){
							 
							 dialog.choicedata('符合条件的币种已全部添加','productprice');
							 $('.pcurrency select',parent.document).html('');
						}else if(data.code==02){
							$('.pcurrency select',parent.document).html('');
						}
					}
			});
		 }
		//改变价格类型，更改货币对；
		$('.pricetype select',parent.document).change(function(){
			changecurrlist();
		});
		//改变炒汇标志，改变货币对；
		$('.pricesign input',parent.document).change(function(){
				changecurrlist();
		});
		//改变期限，改变货币对；
		$('.priceterm input',parent.document).change(function(){
			changecurrlist();
		});
		//点击添加的保存；
		$('.prop_add',parent.document).click(function(){
			var term,usfg;
			if( $('.priceterm input',parent.document).val()==''){
				$('.priceterm .formtip',parent.document).remove();
				$('.priceterm',parent.document).append('<i class="formtip">期限不能为空!</i>');
			}else{
				$('.priceterm .formtip',parent.document).remove();
			}
			
			if( $('.pricepoint input',parent.document).val()==''){
				$('.pricepoint .formtip',parent.document).remove();
				$('.pricepoint',parent.document).append('<i class="formtip">浮动点差不能为空!</i>');
			}else if(!/^\d{0,5}$/.test( $('.pricepoint input',parent.document).val() ) ){
				$('.pricepoint .formtip',parent.document).remove();
				$('.pricepoint',parent.document).append('<i class="formtip">浮动点差必须为5位以内的整数!</i>');
			}else{
				$('.pricepoint .formtip',parent.document).remove();
			}
			
			 if(  $('.priceterm input',parent.document).val() ){
				 term=$('.priceterm input',parent.document).val();
			 }else{
				 term='';
			 }
			 usfg=String( $('.usesifn input[name="priceradio"]:checked',parent.document).data('tit') )
			
//			 var userdat = {
//					"userKey":userKey,
//					"pdtVa":{
//						"tpfg": $('.pricetype select option:selected',parent.document).attr('value'),
//						"term": term,
//						"exnm": $('.pcurrency select option:selected',parent.document).text(), 
//						"excd": $('.pcurrency select option:selected',parent.document).attr('value'),
//						"cxfg": String( $('.pricesign input[name="priceradio0"]:checked',parent.document).data('tit') ),
//						"mxdt": parseInt( $('.pricepoint input',parent.document).val() ),
//						"usfg": usfg
//					}
//			}
			 if(product=="P999"){
				 var userdat = {
					"userKey":userKey,
					"prod":prod,
					"pdtVa":{
						"tpfg": $('.pricetype select option:selected',parent.document).attr('value'),
						"term": term,
						"exnm": $('.pcurrency select option:selected',parent.document).text(), 
						"excd": $('.pcurrency select option:selected',parent.document).attr('value'),
						"cxfg": String( $('.pricesign input[name="priceradio0"]:checked',parent.document).data('tit') ),
						"mxdt": parseInt( $('.pricepoint input',parent.document).val() ),
						"usfg": usfg
					}
			    }
			 }else{
				 var userdat = {
					"userKey":userKey,
					"pdtVa":{
						"tpfg": $('.pricetype select option:selected',parent.document).attr('value'),
						"term": term,
						"exnm": $('.pcurrency select option:selected',parent.document).text(), 
						"excd": $('.pcurrency select option:selected',parent.document).attr('value'),
						"cxfg": String( $('.pricesign input[name="priceradio0"]:checked',parent.document).data('tit') ),
						"mxdt": parseInt( $('.pricepoint input',parent.document).val() ),
						"usfg": usfg
					}
		     	}
			 }
			if( $('.dia_proprice .formtip',parent.document).length==0&&$('.pcurrency select option',parent.document).length>0){
				 $.ajax({
						url:'/fx/addProductVa.do',
						type:'post',
						async:false,
						dataType:'json',
						data:JSON.stringify( userdat ),
			 			contentType:'application/json',
						success:function(data){
							if(data.code==00){
								dialog.choicedata('添加成功!','productprice','two');
								if(product=="P999"){
									getPprice({'userKey':userKey,'prod':prod});
								}else{
									getPprice({'userKey':userKey});
								}
							}else if(data.code==01){
								dialog.choicedata('添加失败!','productprice');  //添加失败的原因；后台返回添加失败的原因；
							}
						}
					}); 
			 }
		});
	}); 
	//修改框弹出
	$('.modify').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			//修改弹出框;+获取当前selected的数据;
           dialog.propricemodify("modify");
           $('.pricetype input',parent.document).val( $('.box tr.selected td:eq(1) span').text() );
           $('.priceterm input',parent.document).val( $('.box tr.selected td:eq(2) span').text() );
           $('.pcurrency input',parent.document).val( $('.box tr.selected td:eq(3) span').text() );
          
           if( $('.box tr.selected td:eq(4) span').text()=='钞'){
        	   $('.pricesign .priceradi0',parent.document).prop('checked','checked');
           }else{
        	   $('.pricesign .priceradi1',parent.document).prop('checked','checked');
           }
           //点击钞汇标志返回false；
	       $('.pricesign input[type="radio"]',parent.document).click(function(){
	    	   return false;
	       });
           $('.pricepoint input',parent.document).val( $('.box tr.selected td:eq(5) span').text() );
          
           if( $('.box tr.selected td:eq(6) span').text()=='启用' ){
        	   $('.usesifn .priceradio0',parent.document).prop('checked','checked');
           }else{
        	   $('.usesifn .priceradio1s',parent.document).prop('checked','checked');
           }
           //浮动点差失焦；
           $('.pricepoint input',parent.document).on('blur',function(){
        	   if( $('.pricepoint input',parent.document).val()==''){
       			$('.pricepoint .formtip',parent.document).remove();
       			$('.pricepoint',parent.document).append('<i class="formtip">浮动点差不能为空!</i>');
	       	   }else if(!/^\d{0,5}$/.test( $('.pricepoint input',parent.document).val() ) ){
	       			$('.pricepoint .formtip',parent.document).remove();
	       			$('.pricepoint',parent.document).append('<i class="formtip">浮动点差必须为5位以内的整数!</i>');
	       	   }else{
	       			$('.pricepoint .formtip',parent.document).remove();
	       		}
           });
		}else{ 
			dialog.choicedata('请先选择一条数据!','productprice');
		}
	});
	//点击修改弹窗的关闭和取消;
	$('body',parent.document).on('click','.prop_close,.prop_cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
	//点击修改弹窗保存;
	$('body',parent.document).on('click','.freq_save',function(){
		
		if( $('.pricepoint input',parent.document).val()==''){
			$('.pricepoint .formtip',parent.document).remove();
			$('.pricepoint',parent.document).append('<i class="formtip">浮动点差不能为空!</i>');
		}else if(!/^\d{0,5}$/.test( $('.pricepoint input',parent.document).val() ) ){
			$('.pricepoint .formtip',parent.document).remove();
			$('.pricepoint',parent.document).append('<i class="formtip">浮动点差必须为5位以内的整数!</i>');
		}else{
			$('.pricepoint .formtip',parent.document).remove();
		}
		if( $('.dia_proprice .formtip',parent.document).length==0 ){
			if(product=="P999"){
				var ppVo={'userKey':userKey,'prod':prod,'pdtVa':{
					'tpnm':$('.pricetype input',parent.document).val(),
					'term':$('.priceterm input',parent.document).val(),
					'exnm':$('.pcurrency input',parent.document).val(),
					'cxfg':$('.pricesign input:checked',parent.document).data('tit'),
					'mxdt':$('.pricepoint input',parent.document).val(),
					'usfg':$('.usesifn input:checked',parent.document).data('tit')
                }} 
			}else{
				var ppVo={'userKey':userKey,'pdtVa':{
					'tpnm':$('.pricetype input',parent.document).val(),
					'term':$('.priceterm input',parent.document).val(),
					'exnm':$('.pcurrency input',parent.document).val(),
					'cxfg':$('.pricesign input:checked',parent.document).data('tit'),
					'mxdt':$('.pricepoint input',parent.document).val(),
					'usfg':$('.usesifn input:checked',parent.document).data('tit')
              }} 
			}
			
			//数据处理
			 $.ajax({
				 url:'/fx/updateProductVa.do',
				 type:'post',
				async:false,
				dataType:'json',
				data:JSON.stringify(ppVo),
	 			contentType:'application/json',
				success:function(data){
					if(data.code==00){
						dialog.choicedata('更新成功!','productprice','two');
						if(product=="P999"){
							getPprice({'userKey':userKey,'prod':prod});
						}else{
							getPprice({'userKey':userKey});
						}
					}else if(data.code==01){
						dialog.choicedata('更新失败!','productprice');  //添加失败的原因；后台返回添加失败的原因；
					}
				}
			 });
		}
	});
	
	//删除框弹出
	$('.del').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			 dialog.cancelDate("您确定要删除当前记录吗?","productprice");
		}else{
			dialog.choicedata('请先选择一条数据!','productprice');
		}
	});
	$('body',parent.document).on('click','.productprice .cancel',function(){
		//关闭删除弹窗;
		$(this).closest('.zhezhao').remove();
	});
	$('body',parent.document).on('click','.productprice .confirm',function(){
		//删除弹框的确定;  ajax掉删除接口
		$(this).closest('.zhezhao').remove();
		var cxfg;
		if( $('.box tr.selected td:eq(4) span').text() =='钞' ){
			cxfg='2';
		}else{
			cxfg='1';
		}
		if(product=="P999"){
			var delVo={'userKey':userKey,'prod':prod,
					  'pdtVa':{'tpnm':$('.box tr.selected td:eq(1) span').text(),
					  	   'term':$('.box tr.selected td:eq(2) span').text(),
					  	   'exnm':$('.box tr.selected td:eq(3) span').text(),
					  	   'cxfg':cxfg
				   } 
            } 
		}else{
			var delVo={'userKey':userKey,
					  'pdtVa':{'tpnm':$('.box tr.selected td:eq(1) span').text(),
					  	   'term':$('.box tr.selected td:eq(2) span').text(),
					  	   'exnm':$('.box tr.selected td:eq(3) span').text(),
					  	   'cxfg':cxfg
				   } 
             } 
		}
		
		 $.ajax({
			 url:'/fx/deleteProductVa.do',
			 type:'post',
				async:false,
				dataType:'json',
				data:JSON.stringify(delVo),
	 			contentType:'application/json',
				success:function(data){
					if(data.code==00){
						dialog.choicedata('删除成功!','productprice');
						if(product=="P999"){
							getPprice({'userKey':userKey,'prod':prod});
						}else{
							getPprice({'userKey':userKey});
						}
					}else if(data.code==01){
						dialog.choicedata('删除失败!','productprice');  //添加失败的原因；后台返回添加失败的原因；
					}
				}
		 });
	});
	//启用框弹出
	$('.hands_open').click(function(){
		var arr=[],cxfg;
		$('.box tr').each(function(i,v){
			if( $(v).find('input').prop('checked') ){
				if( $(v).find('td').eq(4).text()=='2'){  ///gaibian 
					cxfg='2';
				}else{
					cxfg='1';
				}
				arr.push({
						'tpnm':$(v).find('td').eq(1).text(),
						'term':$(v).find('td').eq(2).text(),
						'exnm':$(v).find('td').eq(3).text(), 
						'cxfg':cxfg,
						'mxdt':$(v).find('td').eq(5).text()
				});
			}
		});
		if(product=='P999'){
			var openVo={'userKey':userKey,'prod':prod,'pdtVas':arr}
		}else{
			var openVo={'userKey':userKey,'pdtVas':arr}
		}
		
		if(arr.length>0){
			 $.ajax({
				url:'/fx/openUsfg.do',
				type:'post',
				async:false,
				dataType:'json',
				data:JSON.stringify(openVo ),
	 			contentType:'application/json',
				success:function(data){
					if(data.code==00){
						dialog.choicedata('更新成功!','productprice');
						if(product=="P999"){
							getPprice({'userKey':userKey,'prod':prod});
						}else{
							getPprice({'userKey':userKey});
						}
					}else if(data.code==01){
						dialog.choicedata('更新失败!','productprice');  //添加失败的原因；后台返回添加失败的原因；
					}
				}
			});
		}else{
			dialog.choicedata('请先勾选一条数据!','productprice');
		}
	});
	 //停用框弹出
	$('.hands_stop').click(function(){
		var arr=[],cxfg;
		$('.box tr').each(function(i,v){
			if( $(v).find('input').prop('checked') ){
				if( $(v).find('td').eq(4).text()=='2'){  ///gaibian 
					cxfg='2';
				}else{
					cxfg='1';
				}
				arr.push({
						'tpnm':$(v).find('td').eq(1).text(),
						'term':$(v).find('td').eq(2).text(),
						'exnm':$(v).find('td').eq(3).text(), 
						'cxfg':cxfg,
						'mxdt':$(v).find('td').eq(5).text()
				});
			}
		});
		if(product=='P999'){
			var closeVo={'userKey':userKey,'prod':prod,'pdtVas':arr}
		}else{
			var closeVo={'userKey':userKey,'pdtVas':arr}
		}
		if(arr.length>0){
			$.ajax({
				url:'/fx/closeUSFG.do',
				type:'post',
				async:false,
				dataType:'json',
				data:JSON.stringify(closeVo),
	 			contentType:'application/json',
				success:function(data){
					if(data.code==00){
						dialog.choicedata('更新成功!','productprice');
						if(product=="P999"){
							getPprice({'userKey':userKey,'prod':prod});
						}else{
							getPprice({'userKey':userKey});
						}
						
					}else if(data.code==01){
						dialog.choicedata('更新失败!','productprice');  //添加失败的原因；后台返回添加失败的原因；
					}
				}
			});
		}else{
			dialog.choicedata('请先勾选一条数据 !','productprice');
		}
	});
	$('body',parent.document).on('click','.productprice .sure',function(){
		//var tit=$('.productprice',parent.document).data('tit');
		var tit=$(this).parents('.productprice').data('tit');
		if( tit=='two'){
			$('.zhezhao',parent.document).remove();
		}else{
			//关闭选择一条数据;
			$(this).closest('.zhezhao').remove();
		}
	});

	//封装请求列表
	function getPprice(obj){
		$.ajax({
	    	url:'/fx/getProductVaList.do',
	        type:'post',
	    	contentType:'application/json',
	    	data:JSON.stringify(obj),
	    	async:false,
	    	dataType:'json',
	    	success:function(data){
	    		if(data.code==00){
	    			 dialog.ren({'cols':cols,'wh':wh,'userdata':JSON.parse(data.codeMessage),'checked':true});
	    		}else if(data.code==01){
	    			dialog.ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
	    		}else if(data.code==02){
	    			//查询异常
	    			dialog.ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
	    		}
	    	  }
	      });
	};
});