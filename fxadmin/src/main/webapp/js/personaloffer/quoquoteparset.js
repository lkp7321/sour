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
		dialog:'dialog',
		'wdatepicker':'My97DatePicker/WdatePicker'
	}
});
require(['jquery','mmGrid','niceScroll','dialog','wdatepicker'],function($,mmGrid,niceScroll,dialog,wdatepicker){
	var wh=$(window).height()-200+"px",
		ww=$(window).width()-260+"px";
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
		$('#page').css('width',ww);
	 //获取产品和用户名；
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		userdata,chanpin,jiaoyandata,diandata,stoppai,productorigin,celue,ganyu,quanjuTime=0;
    //判断数字的正则；
    var numreg=/^\d{1,4}$/,
    	numreg1=/^\d+$|^\d+\.{1}\d{1,10}$/,
    	numreg2=/^\d{1,5}$/;
    
    //列参数;
    var cols = [
        { title:'货币对名称', name:'exnm' ,width:100, align:'left' },
        { title:'价格类型', name:'tpnm' ,width:100, align:'center'},
        { title:'钞汇标志', name:'cxfg' ,width:150, align:'center'},
        { title:'报价策略', name:'gmnm' ,width:150, align:'left'},
        { title:'期限', name:'term' ,width:100, align:'right'}
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    var prod=$('.product option:selected').val();
    if(product=="P999"){
    	 //请求列表
    	 rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'prod':prod,'pageNo':1,'pageSize':10} });
    	 renpage(product);
    	 //根据列表的变化重新请求列表
        $('.product select').change(function(){
        	prod=$('.product option:selected').val();
        	 rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'prod':prod,'pageNo':1,'pageSize':10} });
        	 renpage(product);
        })
    }else{
    	//请求列表数据；
        rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'pageNo':1,'pageSize':10} });
        renpage(product);
    }
    function rendlist(obj){
    	$.ajax({
    		url:obj.url,
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj.obj ),
    		async:false,
    		success:function(data){
    			if(data.code==00){
    				userdata=data.codeMessage;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
    			}else{
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
	//修改框弹出
	$('.modify').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			 
			var exnm=$('.box tr.selected td:eq(0) span').text(),modifyVo;
			if(product=="P999"){
				modifyVo={'userKey':userKey,'prod':prod,'exnm':exnm}
			}else{
				modifyVo={'userKey':userKey,'exnm':exnm};
			}
			$.ajax({      															//查询货币对类型，以判断点击“修改”后的弹出框的部分；
				url:'/fx/selectExtp.do',
				type:'post',
				contentType:'application/json',
				data:JSON.stringify(modifyVo),
				async:true,
				success:function(data){
					if(data.code==00){
						userdata=JSON.parse(data.codeMessage);
						if( userdata==1){                                            //判断展示对应的弹出框；
							 dialog.priceModify("quoteparameter",1);
				             $('.quoteparameter .pricecontmain .four',parent.document).show(); 
				             $('.quoteparameter .pricecontmain ul',parent.document).css({"width":"134px"});               //初始化校验参数；
				            if(product=='P999'){
				            	checkfn({'url':'ChkParaInit.do','data':{'userKey':userKey,'prod':prod,'exnm':exnm },'obj':'quoteparameter','product':product});
				            }else{
				            	checkfn({'url':'ChkParaInit.do','data':{'userKey':userKey,'exnm':exnm },'obj':'quoteparameter','product':product});	
				            }
				           // checkfn({'url':'ChkParaInit.do','data':{'userKey':userKey,'exnm':exnm },'obj':'quoteparameter','product':product});
				            
						}else if( userdata==0){
							  
								 dialog.priceModify("quoteparameter",2); 
							  
							 $('.quoteparameter .pricecontmain .one',parent.document).show();
				             $('.quoteparameter .pricecontmain ul',parent.document).css({"width":"344px"});
				             //checkfn({'url':'MarkInit2.do','data':{userKey:userKey,exnm:exnm },'obj':'quoteparameter','product':product});  //相当于初始化产品市场源头
						     if(product=="P999"){
						    	 checkfn({'url':'MarkInit2.do','data':{userKey:userKey,'prod':prod,exnm:exnm },'obj':'quoteparameter','product':product});  //相当于初始化产品市场源头
						     }else{
						    	 checkfn({'url':'MarkInit2.do','data':{userKey:userKey,exnm:exnm },'obj':'quoteparameter','product':product});  //相当于初始化产品市场源头
						     }
						}
						//点击钞汇标志；
						$('.chnote input',parent.document).on('click',function(){
							return false;
						});
						
					}else if(data.code==01||data.code==02){     //提示不同的操作原因；
						
					}
				}
			});
		}else{
			dialog.choicedata('请先选择一条数据!','quoteparameter');
		}
	});
	
	//保存校验；
	$('body',parent.document).on('click','.quoteparameter .four .price_sav',function(){   
		
		if( $('.quoteparameter .byeprice',parent.document).val()==''){
			$('.quoteparameter .byeprice',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .byeprice',parent.document).parent('div').append('<i class="formtip">买入价不能为空!</i>');
		}else if( !numreg1.test($('.quoteparameter .byeprice',parent.document).val()) ){
			$('.quoteparameter .byeprice',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .byeprice',parent.document).parent('div').append('<i class="formtip">买入价必须是整数或小数!</i>');
		}else{
			$('.quoteparameter .byeprice',parent.document).parent('div').find('.formtip').remove();
		}
		
		if( $('.quoteparameter .cenpricedot',parent.document).val()==''){
			$('.quoteparameter .cenpricedot',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .cenpricedot',parent.document).parent('div').append('<i class="formtip">中间价两次浮动点差不能为空!</i>');
		}else if( !numreg.test($('.quoteparameter .cenpricedot',parent.document).val()) ){
			$('.quoteparameter .cenpricedot',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .cenpricedot',parent.document).parent('div').append('<i class="formtip">中间价两次浮动点差必须正整数!</i>');
		}else{
			$('.quoteparameter .cenpricedot',parent.document).parent('div').find('.formtip').remove();
		}
		if($('.quoteparameter .pricenouse',parent.document).val()==''){
			$('.quoteparameter .pricenouse',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .pricenouse',parent.document).parent('div').append('<i class="formtip">未波动次数不能为空!</i>');
		}else if( !numreg.test($('.quoteparameter .pricenouse',parent.document).val()) ){
			$('.quoteparameter .pricenouse',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .pricenouse',parent.document).parent('div').append('<i class="formtip">未波动次数必须是正整数!</i>');		
		}else{
			$('.quoteparameter .pricenouse',parent.document).parent('div').find('.formtip').remove();
		}
		if($('.quoteparameter .bigshavedot',parent.document).val()==''){
			$('.quoteparameter .bigshavedot',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .bigshavedot',parent.document).parent('div').append('<i class="formtip">最大波动点差不能为空!</i>');
		}else if( !numreg.test($('.quoteparameter .bigshavedot',parent.document).val()) ){
			$('.quoteparameter .bigshavedot',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .bigshavedot',parent.document).parent('div').append('<i class="formtip">最大波动点差必须是正整数!</i>');
		}else{
			$('.quoteparameter .bigshavedot',parent.document).parent('div').find('.formtip').remove();
		}
		
		if($('.quoteparameter .goodnum',parent.document).val()==''){
			$('.quoteparameter .goodnum',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .goodnum',parent.document).parent('div').append('<i class="formtip">合法波动次数不能为空!</i>');
		}else if( !numreg.test($('.quoteparameter .goodnum',parent.document).val()) ){
			$('.quoteparameter .goodnum',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .goodnum',parent.document).parent('div').append('<i class="formtip">合法波动次数必须是正整数!</i>');
		}else{
			$('.quoteparameter .goodnum',parent.document).parent('div').find('.formtip').remove();
		}
		if( $('.quoteparameter .four .formtip',parent.document).length<=0){
//			var PriceVo={ userKey:userKey,pdtChk:{
//				'mdmd':$('.quoteparameter .byeprice',parent.document).val()*1,
//				'mxbp':$('.quoteparameter .cenpricedot',parent.document).val(),
//				'mxud':$('.quoteparameter .pricenouse',parent.document).val(),
//				'mxdp':$('.quoteparameter .bigshavedot',parent.document).val(),
//				'mxct':$('.quoteparameter .goodnum',parent.document).val(),
//				'tpfg':$('.box tr.selected td:eq(1) span').text(),
//				'cxfg':$('.quoteparameter .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
//				'usfg':$('.quoteparameter .pripubdivs input[name="priceradio"]:checked',parent.document).attr('tit'),
//				'term':$('.box tr.selected td:eq(4) span').text(),
//				'exnm':$('.box tr.selected td:eq(0) span').text()
//				},
//				exnm:$('.box tr.selected td:eq(0) span').text()
//			};
			if(product=="P999"){
				var PriceVo={ userKey:userKey,'prod':prod,pdtChk:{
					'mdmd':$('.quoteparameter .byeprice',parent.document).val()*1,
					'mxbp':$('.quoteparameter .cenpricedot',parent.document).val(),
					'mxud':$('.quoteparameter .pricenouse',parent.document).val(),
					'mxdp':$('.quoteparameter .bigshavedot',parent.document).val(),
					'mxct':$('.quoteparameter .goodnum',parent.document).val(),
					'tpfg':$('.box tr.selected td:eq(1) span').text(),
					'cxfg':$('.quoteparameter .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
					'usfg':$('.quoteparameter .pripubdivs input[name="priceradio"]:checked',parent.document).attr('tit'),
					'term':$('.box tr.selected td:eq(4) span').text(),
					'exnm':$('.box tr.selected td:eq(0) span').text()
					},
					exnm:$('.box tr.selected td:eq(0) span').text()
				};
			}else{
				var PriceVo={ userKey:userKey,pdtChk:{
					'mdmd':$('.quoteparameter .byeprice',parent.document).val()*1,
					'mxbp':$('.quoteparameter .cenpricedot',parent.document).val(),
					'mxud':$('.quoteparameter .pricenouse',parent.document).val(),
					'mxdp':$('.quoteparameter .bigshavedot',parent.document).val(),
					'mxct':$('.quoteparameter .goodnum',parent.document).val(),
					'tpfg':$('.box tr.selected td:eq(1) span').text(),
					'cxfg':$('.quoteparameter .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
					'usfg':$('.quoteparameter .pripubdivs input[name="priceradio"]:checked',parent.document).attr('tit'),
					'term':$('.box tr.selected td:eq(4) span').text(),
					'exnm':$('.box tr.selected td:eq(0) span').text()
					},
					exnm:$('.box tr.selected td:eq(0) span').text()
				};
			}
			savefn({url:'/fx/SaveChkPara.do',obj:PriceVo});
		}
	});
	
	//保存停牌
	$('body',parent.document).on('click','.quoteparameter .five .price_sav',function(){
//		var PriceVo={ userKey:userKey,pdtStoper:{
//			'stid':$('.quoteparameter .suspenid',parent.document).val(),
//			'stfg':$('.quoteparameter input[name="priceradio2"]:checked',parent.document).attr('tit'),    //停牌标志；
//			'tpfg':$('.box tr.selected td:eq(1) span').text(),
//			'term':$('.box tr.selected td:eq(4) span').text(),
//			'exnm':$('.box tr.selected td:eq(0) span').text(),
//			}
//		};
		if(product=='P999'){
			var PriceVo={ userKey:userKey,'prod':prod,pdtStoper:{
				'stid':$('.quoteparameter .suspenid',parent.document).val(),
				'stfg':$('.quoteparameter input[name="priceradio2"]:checked',parent.document).attr('tit'),    //停牌标志；
				'tpfg':$('.box tr.selected td:eq(1) span').text(),
				'term':$('.box tr.selected td:eq(4) span').text(),
				'exnm':$('.box tr.selected td:eq(0) span').text(),
				}
			};
		}else{
			var PriceVo={ userKey:userKey,pdtStoper:{
				'stid':$('.quoteparameter .suspenid',parent.document).val(),
				'stfg':$('.quoteparameter input[name="priceradio2"]:checked',parent.document).attr('tit'),    //停牌标志；
				'tpfg':$('.box tr.selected td:eq(1) span').text(),
				'term':$('.box tr.selected td:eq(4) span').text(),
				'exnm':$('.box tr.selected td:eq(0) span').text(),
				}
			};
		}
		savefn({url:'/fx/SavePdtStop.do',obj:PriceVo});
	});
	//点击点差保存；
	$('body',parent.document).on('click','.quoteparameter .six .price_save',function(){
		
		if($('.quoteparameter .pricelive',parent.document).val()==''){
			$('.quoteparameter .pricelive',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期不能为空!</i>');
		}else if( !numreg2.test( $('.quoteparameter .pricelive',parent.document).val() ) ){
			$('.quoteparameter .pricelive',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期必须是正整数!</i>');
		}else{
			$('.quoteparameter .pricelive',parent.document).parent('div').find('.formtip').remove();
		}
		
		if( $('.quoteparameter .buyindot',parent.document).val()=='' ){
			$('.quoteparameter .buyindot',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .buyindot',parent.document).parent('div').append('<i class="formtip">总行对分行买入点差不能为空!</i>');
		}else if( !numreg2.test($('.quoteparameter .buyindot',parent.document).val()) ){
			$('.quoteparameter .buyindot',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .buyindot',parent.document).parent('div').append('<i class="formtip">总行对分行买入点差必须是正整数!</i>');
		}else{
			$('.quoteparameter .buyindot',parent.document).parent('div').find('.formtip').remove();
		}
		
		if($('.quoteparameter .bytoutdot',parent.document).val()==''){
			$('.quoteparameter .bytoutdot',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差不能为空!</i>');	
		}else if( !numreg2.test($('.quoteparameter .bytoutdot',parent.document).val()) ){
			$('.quoteparameter .bytoutdot',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差必须是正整数!</i>');		
		}else{
			$('.quoteparameter .bytoutdot',parent.document).parent('div').find('.formtip').remove();
		}
		
		if( $('.quoteparameter .sallhand',parent.document).val()==''){
			$('.quoteparameter .sallhand',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .sallhand',parent.document).parent('div').append('<i class="formtip">总行对分行卖出点差不能为空!</i>');
		}else if( !numreg2.test($('.quoteparameter .sallhand',parent.document).val()) ){
			$('.quoteparameter .sallhand',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .sallhand',parent.document).parent('div').append('<i class="formtip">总行对分行卖出点差必须是正整数!</i>');
		}else{
			$('.quoteparameter .sallhand',parent.document).parent('div').find('.formtip').remove();
		}
		if($('.quoteparameter .sallcust',parent.document).val()==''){
			$('.quoteparameter .sallcust',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差不能为空!</i>');
		}else if( !numreg2.test($('.quoteparameter .sallcust',parent.document).val()) ){
			$('.quoteparameter .sallcust',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差必须是正整数!</i>');
		}else{
			$('.quoteparameter .sallcust',parent.document).parent('div').find('.formtip').remove();
		}
		if( $('.quoteparameter .six .formtip',parent.document).length<=0){
//			var PriceVo={ userKey:userKey,pdtPoint:{
//				'tpfg':$('.box tr.selected td:eq(1) span').text(),
//				'exnm':$('.box tr.selected td:eq(0) span').text(),
//				'bhbd':$('.quoteparameter .buyindot',parent.document).val(),
//				'bhsd':$('.quoteparameter .sallhand',parent.document).val(),
//				'cubd':$('.quoteparameter .bytoutdot',parent.document).val(),
//				'cusd':$('.quoteparameter .sallcust',parent.document).val(),
//				'prtp':$('.quoteparameter input[name="priceradio4"]:checked',parent.document).attr('tit'),
//				'term':$('.box tr.selected td:eq(4) span').text(),
//				'qtcy':$('.quoteparameter .pricelive',parent.document).val(),
//				'cxfg':$('.quoteparameter .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit')
//				}
//			};
			if(product=="P999"){
				var PriceVo={ userKey:userKey,'prod':prod,pdtPoint:{
					'tpfg':$('.box tr.selected td:eq(1) span').text(),
					'exnm':$('.box tr.selected td:eq(0) span').text(),
					'bhbd':$('.quoteparameter .buyindot',parent.document).val(),
					'bhsd':$('.quoteparameter .sallhand',parent.document).val(),
					'cubd':$('.quoteparameter .bytoutdot',parent.document).val(),
					'cusd':$('.quoteparameter .sallcust',parent.document).val(),
					'prtp':$('.quoteparameter input[name="priceradio4"]:checked',parent.document).attr('tit'),
					'term':$('.box tr.selected td:eq(4) span').text(),
					'qtcy':$('.quoteparameter .pricelive',parent.document).val(),
					'cxfg':$('.quoteparameter .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit')
					}
				};
			}else{
				var PriceVo={ userKey:userKey,pdtPoint:{
					'tpfg':$('.box tr.selected td:eq(1) span').text(),
					'exnm':$('.box tr.selected td:eq(0) span').text(),
					'bhbd':$('.quoteparameter .buyindot',parent.document).val(),
					'bhsd':$('.quoteparameter .sallhand',parent.document).val(),
					'cubd':$('.quoteparameter .bytoutdot',parent.document).val(),
					'cusd':$('.quoteparameter .sallcust',parent.document).val(),
					'prtp':$('.quoteparameter input[name="priceradio4"]:checked',parent.document).attr('tit'),
					'term':$('.box tr.selected td:eq(4) span').text(),
					'qtcy':$('.quoteparameter .pricelive',parent.document).val(),
					'cxfg':$('.quoteparameter .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit')
					}
				};
			}
			savefn({url:'/fx/SavePoint.do',obj:PriceVo});
		}
	});
	//保存产品市场源头选择；
	$('body',parent.document).on('click','.quoteparameter .one .price_sav',function(){		 
		if($('.quoteparameter .prisoright .marketcontr p',parent.document).length>0){
			var mkst='',mksl='',mklv='',spantxt=0,wrong=0;
			$('.prisoright .marketcontr p',parent.document).each(function(i,v){
				if(i==0){
					mkst+=$(v).attr('mkid')
					mksl+=$(v).find('span').eq(1).text()
		            mklv+=$(v).attr('mklv')
				}else{
					mkst+='|'+$(v).attr('mkid')
					mksl+='|'+$(v).find('span').eq(1).text()
		            mklv+='|'+$(v).attr('mklv')
				}
			});
//			var PriceVo={ userKey:userKey,pdtrPara:{
//				'mkst':mkst,
//				'mklv':mklv,
//				'mksl':mksl,
//				'cxfg':$('.quoteparameter .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
//				'exnm':$('.box tr.selected td:eq(0) span').text(),
//				'tpfg':$('.box tr.selected td:eq(1) span').text(),
//				'term':$('.box tr.selected td:eq(4) span').text()
//				}
//			};
			if(product=='P999'){
				var PriceVo={ userKey:userKey,'prod':prod,pdtrPara:{
					'mkst':mkst,
					'mklv':mklv,
					'mksl':mksl,
					'cxfg':$('.quoteparameter .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
					'exnm':$('.box tr.selected td:eq(0) span').text(),
					'tpfg':$('.box tr.selected td:eq(1) span').text(),
					'term':$('.box tr.selected td:eq(4) span').text()
					}
				};
			}else{
				var PriceVo={ userKey:userKey,pdtrPara:{
					'mkst':mkst,
					'mklv':mklv,
					'mksl':mksl,
					'cxfg':$('.quoteparameter .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
					'exnm':$('.box tr.selected td:eq(0) span').text(),
					'tpfg':$('.box tr.selected td:eq(1) span').text(),
					'term':$('.box tr.selected td:eq(4) span').text()
					}
				};
			}
			 if( $('.quoteparameter .one .marketcontr p',parent.document).length==1){  //只有一条市场权重设置；
	    		 if( $('.quoteparameter .one .marketcontr p',parent.document).find('span').eq(1).text()==1){
	    			savefn({url:'/fx/SaveMark.do',obj:PriceVo});
	    		 }else{
	    			 dialog.choicedata('市场权重必须是0到1之间的数字，小于1的数字以‘.’开始!','quoteparamete');
	    		 }
	    	 }else{  																	//多条市场权重；
	    		 $('.quoteparameter .one .marketcontr p',parent.document).each(function(i,v){
	    			 if( $(v).find('span').eq(1).text()==''||!/^\.{1}\d+$/.test($(v).find('span').eq(1).text())){
	    				 wrong++;
	    			 }else{
	    				 spantxt=spantxt+$(v).find('span').eq(1).text()*1;
	    			 }
	    		 });
	    		 if( spantxt==1&&wrong==0){
	    			 savefn({url:'/fx/SaveMark.do',obj:PriceVo});
	    		 }else if(spantxt!=1&&wrong==0){
	    			 dialog.choicedata('所有市场权重之和必须是1!','quoteparameter');
	    		 }else{
	    			 dialog.choicedata('市场权重必须是0到1之间的数字，小于1的数字以‘.’开始!','quoteparamete');
	    		 }
	    	 }
		}else{
			dialog.choicedata('没有添加产品市场，不能保存!','quoteparameter');
		}
	});
	//保存策略；
	$('body',parent.document).on('click','.quoteparameter .two .price_sav',function(){
		if( $('.quoteparameter .two .marketcontr .tabpbgc',parent.document).length>0){
//			var PriceVo={ userKey:userKey,pdtrPara:{
//				'gmnm':$('.two .pristrright .marketcontr p',parent.document).text(),
//				'cxfg':$('.quoteparameter .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
//				'exnm':$('.box tr.selected td:eq(0) span').text(),
//				'tpfg':$('.box tr.selected td:eq(1) span').text(),
//				'term':$('.box tr.selected td:eq(4) span').text()
//				},
//				'exnm':$('.box tr.selected td:eq(0) span').text()
//			};
			if(product=="P999"){
				var PriceVo={ userKey:userKey,'prod':prod,pdtrPara:{
					'gmnm':$('.two .pristrright .marketcontr p',parent.document).text(),
					'cxfg':$('.quoteparameter .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
					'exnm':$('.box tr.selected td:eq(0) span').text(),
					'tpfg':$('.box tr.selected td:eq(1) span').text(),
					'term':$('.box tr.selected td:eq(4) span').text()
					},
					'exnm':$('.box tr.selected td:eq(0) span').text()
				};
			}else{
				var PriceVo={ userKey:userKey,pdtrPara:{
					'gmnm':$('.two .pristrright .marketcontr p',parent.document).text(),
					'cxfg':$('.quoteparameter .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
					'exnm':$('.box tr.selected td:eq(0) span').text(),
					'tpfg':$('.box tr.selected td:eq(1) span').text(),
					'term':$('.box tr.selected td:eq(4) span').text()
					},
					'exnm':$('.box tr.selected td:eq(0) span').text()
				};
			}
			savefn({url:'/fx/SaveGmnm.do',obj:PriceVo});
		}else{
			dialog.choicedata('策略没有修改,不必保存!','quoteparameter');
		}
	});
	//保存干预；
	$('body',parent.document).on('click','.quoteparameter .three .price_save',function(){
		
		if($('.quoteparameter .three .nebp',parent.document).val()=='' ){
			$('.quoteparameter .nebp',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .nebp',parent.document).parent('div').append('<i class="formtip">买入价干预点数不能为空!</i>');
		}else if(!numreg.test($('.quoteparameter .three .nebp',parent.document).val())){
			$('.quoteparameter .nebp',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .nebp',parent.document).parent('div').append('<i class="formtip">买入价干预点数必须是不超过4位的正整数!</i>');
		}else{
			$('.quoteparameter .nebp',parent.document).parent('div').find('.formtip').remove();
		}
		if( $('.quoteparameter .three .nesp',parent.document).val()==''){
			$('.quoteparameter .nesp',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .nesp',parent.document).parent('div').append('<i class="formtip">卖出价干预点数不能为空!</i>');
		}else if(!numreg.test($('.quoteparameter .three .nesp',parent.document).val())){
			$('.quoteparameter .nesp',parent.document).parent('div').find('.formtip').remove();
			$('.quoteparameter .nesp',parent.document).parent('div').append('<i class="formtip">卖出价干预点数必须是不超过4位的正整数!</i>');
		}else{
			$('.quoteparameter .nesp',parent.document).parent('div').find('.formtip').remove();
		}
		
		if( $('.quoteparameter .three .formtip',parent.document).length<=0&& comptime( $('body #sT',parent.document).val(),$('body #eT',parent.document).val() )==true){
//			var PriceVo={ userKey:userKey,pdtCtrl:{
//				'nebp':$('.quoteparameter .three .nebp',parent.document).val(),
//				'nesp':$('.quoteparameter .three .nesp',parent.document).val(),
//				'bgtm':$('.quoteparameter .three #sT',parent.document).val(),
//				'edtm':$('.quoteparameter .three #eT',parent.document).val(),
//				'stfg':$('.quoteparameter .three .usestfg input[name="stfg"]:checked',parent.document).attr('tit'),
//				'ctid':$('.quoteparameter .three .ctid',parent.document).val(),
//				'term':$('.box tr.selected td:eq(4) span').text(),
//				'exnm':$('.box tr.selected td:eq(0) span').text(),
//				'cxfg':$('.quoteparameter .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit')
//				}
//			};
			if(product=="P999"){
				var PriceVo={ userKey:userKey,'prod':prod,pdtCtrl:{
				'nebp':$('.quoteparameter .three .nebp',parent.document).val(),
				'nesp':$('.quoteparameter .three .nesp',parent.document).val(),
				'bgtm':$('.quoteparameter .three #sT',parent.document).val(),
				'edtm':$('.quoteparameter .three #eT',parent.document).val(),
				'stfg':$('.quoteparameter .three .usestfg input[name="stfg"]:checked',parent.document).attr('tit'),
				'ctid':$('.quoteparameter .three .ctid',parent.document).val(),
				'term':$('.box tr.selected td:eq(4) span').text(),
				'exnm':$('.box tr.selected td:eq(0) span').text(),
				'cxfg':$('.quoteparameter .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit')
				}
			  };
			}else{
				var PriceVo={ userKey:userKey,pdtCtrl:{
					'nebp':$('.quoteparameter .three .nebp',parent.document).val(),
					'nesp':$('.quoteparameter .three .nesp',parent.document).val(),
					'bgtm':$('.quoteparameter .three #sT',parent.document).val(),
					'edtm':$('.quoteparameter .three #eT',parent.document).val(),
					'stfg':$('.quoteparameter .three .usestfg input[name="stfg"]:checked',parent.document).attr('tit'),
					'ctid':$('.quoteparameter .three .ctid',parent.document).val(),
					'term':$('.box tr.selected td:eq(4) span').text(),
					'exnm':$('.box tr.selected td:eq(0) span').text(),
					'cxfg':$('.quoteparameter .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit')
					}
				  };
			}
			savefn({url:'/fx/SaveCtrl.do',obj:PriceVo});
		}else if($('.quoteparameter .three .formtip',parent.document).length<=0&&comptime( $('body #sT',parent.document).val(),$('body #eT',parent.document).val() )==false){
			dialog.choicedata('截止时间应大于起始时间!','quoteparameter');
		}
	});
	function savefn(obj){
		$.ajax({
			url:obj.url,
			type:'post',
			contentType:'application/json',
			data:JSON.stringify(obj.obj),
			async:true,
			success:function(data){
				if(data.code==00){
					dialog.choicedata(data.codeMessage,'quoteparameter');
					if(obj.url=='SaveGmnm.do'){
						$('.quoteparameter .two .currentstra div',parent.document).text( $('.quoteparameter .two .pristrright .tabpbgc',parent.document).text() );
					}
					//rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'pageNo':1,'pageSize':10} });
				}else{
					dialog.choicedata(data.codeMessage,'quoteparameter');
				}
			}
		});
		
	}
	function checkfn(obj){                                      //校验函数
		var str='',str1='',chanpin;
		switch(obj.product){
			case 'P001':chanpin='个人实盘';break;
			case 'P002':chanpin='纸黄金交易管理平台';break;
			case 'P003':chanpin='积存金管理平台';break;
			case 'P004':chanpin='个人结售汇';break;
			case 'P007':chanpin='账户交易';break;
			case 'P009':chanpin='保证金前置';break;
			case 'P998':chanpin='RV前置';break;
			case 'P999':chanpin='报价平台';break;
		}
		$('.'+obj.obj+' .hbdname span',parent.document).text( $('.box tr.selected td:eq(0) span').text() );
		$('.'+obj.obj+' .productname span',parent.document).text( chanpin );    //chanpin;用来显示产品名称；
		$('.'+obj.obj+' .privetype span',parent.document).text( $('.box tr.selected td:eq(1) span').text() ); //远期即期；
		if( $('.box tr.selected td:eq(2) span').text()=='钞'){       //钞汇标志；
			$('.'+obj.obj+' .priceradi0',parent.document).prop('checked','checked');
		}else{
			$('.'+obj.obj+' .priceradi1',parent.document).prop('checked','checked');
		}
		
		$.ajax({      														
			url:obj.url,
			type:'post',
			contentType:'application/json',
			data:JSON.stringify(obj.data),
			async:true,
			success:function(data){
				if(data.code==00){
					userdata=JSON.parse(data.codeMessage);
					if(obj.url=='ChkParaInit.do'){           //校验
						jiaoyandata=userdata;
						$('.'+obj.obj+' .byeprice',parent.document).val(userdata.mdmd);
						$('.'+obj.obj+' .cenpricedot',parent.document).val(userdata.mxbp);
						$('.'+obj.obj+' .pricenouse',parent.document).val(userdata.mxud);
						$('.'+obj.obj+' .bigshavedot',parent.document).val(userdata.mxdp);
						$('.'+obj.obj+' .goodnum',parent.document).val(userdata.mxct);
					
						if(userdata.usfg==0){     //使用标志；
							$('.'+obj.obj+' .priceradio0',parent.document).prop('checked','checked');
						}else{
							$('.'+obj.obj+' .priceradio1',parent.document).prop('checked','checked');
						}
					}else if(obj.url=='CurStopInit.do'){         	//停牌
						stoppai=userdata;
						$('.'+obj.obj+' .suspenname',parent.document).val(userdata.stnm); //停牌器名称
						$('.'+obj.obj+' .suspenid',parent.document).val( userdata.stid );   
						if(userdata.stfg==0){ //启用 停用    正常停牌；
							$('.'+obj.obj+' .priceradio2',parent.document).prop('checked','checked');
						}else{
							$('.'+obj.obj+' .priceradio3',parent.document).prop('checked','checked');
						}
						
					}else if(obj.url=='CurPointInit.do'){			//点差
						diandata=userdata;
						
						$('.'+obj.obj+' .pricelive',parent.document).val( userdata.qtcy );
						$('.'+obj.obj+' .buyindot',parent.document).val( userdata.bhbd);
						$('.'+obj.obj+' .bytoutdot',parent.document).val( userdata.cubd );
						$('.'+obj.obj+' .sallhand',parent.document).val( userdata.bhsd );
						$('.'+obj.obj+' .sallcust',parent.document).val( userdata.cusd );
						
						if(userdata.prtp==0){ //双边价；
							$('.'+obj.obj+' .priceradio4',parent.document).prop('checked','checked');
						}else{  			//中间价
							$('.'+obj.obj+' .priceradio5',parent.document).prop('checked','checked');
						}		
						
					}else if(obj.url=='MarkInit2.do'){               //产品市场源头选择；
						productorigin=userdata;
						for(var i in userdata){
							str+='<p value='+userdata[i].mkid+'>'+userdata[i].mknm+'</p>'
						}
						$('.'+obj.obj+' .prisoleft .marketcont',parent.document).html(str);
						
						$.ajax({
							url:'/fx/MarkInit1.do',
							type:'post',
							contentType:'application/json',
							data:JSON.stringify(obj.data),
							async:true,
							success:function(data){
								var product=JSON.parse(data.codeMessage);
								for(var i in product){
									str1+='<p mkid='+product[i].mkid+' mklv='+product[i].mklv+'>'+'<span>'+product[i].mknm+'</span>'+'<span contenteditable="true" class="conte">'+product[i].mksl+'</span>'+'</p>'
								}
								$('.'+obj.obj+' .prisoright .marketcont',parent.document).html(str1);
							}
						});
						
					}else if(obj.url=='GetGmnm.do'){				//策略
						celue=userdata;
					
						//显示当前策略；
						$('.'+obj.obj+' .two .pristrleft .currentstra div',parent.document).text(userdata.gmnm);
						$.ajax({
							url:'/fx/GetAllGmnm.do',
							type:'post',
							contentType:'application/json',
							async:true,
							success:function(data){
								var str='',date=JSON.parse(data.codeMessage);
								for(var i=0,num=date.length;i<num;i++){
									str+='<p value='+date[i].gmid+'>'+date[i].gmnm+'</p>'
								}
								$('.'+obj.obj+' .two .pristrleft .marketcontr',parent.document).html(str);
							}
						});								
					}else if(obj.url=='GetPdtCtrl.do'){				//干预
						ganyu=userdata;
						$('.'+obj.obj+' .three .ctid',parent.document).val( userdata.ctid );
						$('.'+obj.obj+' .three .ctnm',parent.document).val( userdata.ctnm );
						
						$('.'+obj.obj+' .three .nebp',parent.document).val( userdata.nebp );
						$('.'+obj.obj+' .three .nesp',parent.document).val( userdata.nesp );
						$('.'+obj.obj+' .three #sT',parent.document).val( userdata.bgtm );
						$('.'+obj.obj+' .three #eT',parent.document).val( userdata.edtm );
						
						if( userdata.stfg==0){
							$('.'+obj.obj+' .three .stfg0',parent.document).prop('checked','checked');
						}else{
							$('.'+obj.obj+' .three .stfg1',parent.document).prop('checked','checked');
						}	
					}	
				}else if(data.code==01){
					
				}else if(data.code=02){
					
				}
			}
		});
	}
	//修改框的tab切换
	$('body',parent.document).on('click','.pricecontmain li',function(){
		var txt=$(this).text(),
			exnm=$('.box tr.selected td:eq(0) span').text(),
			PriceVo;
		if(txt=='校验'){
			//checkfn({'url':'ChkParaInit.do','data':{userKey:userKey, exnm:exnm},'obj':'quoteparameter'});
		   if(product=='P999'){
			   checkfn({'url':'ChkParaInit.do','data':{userKey:userKey,'prod':prod, exnm:exnm},'obj':'quoteparameter'});
		   }else{
			   checkfn({'url':'ChkParaInit.do','data':{userKey:userKey, exnm:exnm},'obj':'quoteparameter'}); 
		   }
		}else if(txt=='停牌'){
			if(product=="P999"){
				PriceVo={userKey:userKey,'prod':prod,exnm:exnm,stid:'SC99'};
			}else{
				PriceVo={userKey:userKey,exnm:exnm,stid:'SC99'};
			}
			//PriceVo={userKey:userKey,exnm:exnm,stid:'SC99'};
			checkfn({'url':'CurStopInit.do','data':PriceVo,'obj':'quoteparameter'} );
			
		}else if(txt=='点差'){
			//checkfn({'url':'CurPointInit.do','data':{userKey:userKey,exnm:exnm},'obj':'quoteparameter'});
		    if(product=='P999'){
		    	checkfn({'url':'CurPointInit.do','data':{userKey:userKey,'prod':prod,exnm:exnm},'obj':'quoteparameter'});
		    }else{
		    	checkfn({'url':'CurPointInit.do','data':{userKey:userKey,exnm:exnm},'obj':'quoteparameter'});
		    }
		}else if(txt=='产品市场源头选择'){
			
			//checkfn({'url':'MarkInit2.do','data':{userKey:userKey,exnm:exnm},'obj':'quoteparameter'});
            if(product=="P999"){
            	checkfn({'url':'MarkInit2.do','data':{userKey:userKey,'prod':prod,exnm:exnm},'obj':'quoteparameter'});
			}else{
				checkfn({'url':'MarkInit2.do','data':{userKey:userKey,exnm:exnm},'obj':'quoteparameter'});
			}
		}else if(txt=='策略'){
			//checkfn({'url':'GetGmnm.do','data':{userKey:userKey,exnm:exnm},'obj':'quoteparameter'});
			if(product=='P999'){
				checkfn({'url':'GetGmnm.do','data':{userKey:userKey,'prod':prod,exnm:exnm},'obj':'quoteparameter'});
			}else{
				checkfn({'url':'GetGmnm.do','data':{userKey:userKey,exnm:exnm},'obj':'quoteparameter'});
			}
		}else if(txt=='干预'){
			
			//checkfn({'url':'GetPdtCtrl.do','data':{userKey:userKey,ctid:'CP99',exnm:exnm},'obj':'quoteparameter'});
		    if(product=='P999'){
		    	checkfn({'url':'GetPdtCtrl.do','data':{userKey:userKey,'prod':prod,ctid:'CP99',exnm:exnm},'obj':'quoteparameter'});
		    }else{
		    	checkfn({'url':'GetPdtCtrl.do','data':{userKey:userKey,ctid:'CP99',exnm:exnm},'obj':'quoteparameter'});
		    }
		}
         $(this).css({"background-color":"#fff"}).siblings().css({"background-color":"#9CBAFF"});
         var index=$(this).data("index");
         $("."+index+"",parent.document).siblings('.tabdiv').hide();
         $("."+index+"",parent.document).show();
         
       
        //失去焦点判断    校验；
         $('body',parent.document).find('.quoteparameter .four .byeprice').on('blur',function(){ // 买入价；
	    	 if( $('.quoteparameter .byeprice',parent.document).val()==''){
	    		 $(this).parent('div').find('.formtip').remove();
	 			 $('.quoteparameter .byeprice',parent.document).parent('div').append('<i class="formtip">买入价不能为空!</i>');
	    	 }else if( !numreg1.test($('.quoteparameter .byeprice',parent.document).val()) ){
 				$(this).parent('div').find('.formtip').remove();
 				$('.quoteparameter .byeprice',parent.document).parent('div').append('<i class="formtip">买入价必须是正整数或小数!</i>');
 			}else{
 				$(this).parent('div').find('.formtip').remove();
 			}
 		});
 		$('body',parent.document).find('.quoteparameter .four .cenpricedot').on('blur',function(){ 
 			if( $('.quoteparameter .cenpricedot',parent.document).val()=='' ){
 				$(this).parent('div').find('.formtip').remove();
 				$('.quoteparameter .cenpricedot',parent.document).parent('div').append('<i class="formtip">中间价两次浮动点差不能为空!</i>');
 			}else if( !numreg.test($('.quoteparameter .cenpricedot',parent.document).val()) ){
 				$(this).parent('div').find('.formtip').remove();
 				$('.quoteparameter .cenpricedot',parent.document).parent('div').append('<i class="formtip">中间价两次浮动点差必须是正整数!</i>');
 			}else{
 				$(this).parent('div').find('.formtip').remove();
 			}
 		});
 		$('body',parent.document).find('.quoteparameter .four .pricenouse').on('blur',function(){
 			if($('.quoteparameter .pricenouse',parent.document).val()==''){
 				$(this).parent('div').find('.formtip').remove();
 				$('.quoteparameter .pricenouse',parent.document).parent('div').append('<i class="formtip">未波动次数不能为空!</i>');	
 			}else if( !numreg.test($('.quoteparameter .pricenouse',parent.document).val()) ){
 				$(this).parent('div').find('.formtip').remove();
 				$('.quoteparameter .pricenouse',parent.document).parent('div').append('<i class="formtip">未波动次数必须是正整数!</i>');		
 			}else{
 				$(this).parent('div').find('.formtip').remove();
 			}
 		});
 		$('body',parent.document).find('.quoteparameter .four .bigshavedot').on('blur',function(){
 			if($('.quoteparameter .bigshavedot',parent.document).val()==''){
 				$(this).parent('div').find('.formtip').remove();
 				$('.quoteparameter .bigshavedot',parent.document).parent('div').append('<i class="formtip">最大波动点差不能为空!</i>');
 			}else if( !numreg.test($('.quoteparameter .bigshavedot',parent.document).val()) ){
 				$(this).parent('div').find('.formtip').remove();
 				$('.quoteparameter .bigshavedot',parent.document).parent('div').append('<i class="formtip">最大波动点差必须是正整数!</i>');
 			}else{
 				$(this).parent('div').find('.formtip').remove();
 			}
 		});
 		$('body',parent.document).find('.quoteparameter .four .goodnum').on('blur',function(){
 			if( $('.quoteparameter .goodnum',parent.document).val()==''){
 				$(this).parent('div').find('.formtip').remove();
 				$('.quoteparameter .goodnum',parent.document).parent('div').append('<i class="formtip">合法波动次数不能为空!</i>');
 			}else if( !numreg.test($('.quoteparameter .goodnum',parent.document).val()) ){
 				$(this).parent('div').find('.formtip').remove();
 				$('.quoteparameter .goodnum',parent.document).parent('div').append('<i class="formtip">合法波动次数必须是正整数!</i>');
 			}else{
 				$(this).parent('div').find('.formtip').remove();
 			}
 		});
 		//点差 失焦判断；
 		$('body',parent.document).find('.quoteparameter .six .pricelive').on('blur',function(){
 			if( $('.quoteparameter .pricelive',parent.document).val()==''){
 				$('.quoteparameter .pricelive',parent.document).parent('div').find('.formtip').remove();
 				$('.quoteparameter .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期不能为空!</i>');
 			}else if( !numreg2.test( $('.quoteparameter .pricelive',parent.document).val() ) ){
 				$('.quoteparameter .pricelive',parent.document).parent('div').find('.formtip').remove();
 				$('.quoteparameter .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期必须是正整数!</i>');
 			}else{
 				$('.quoteparameter .pricelive',parent.document).parent('div').find('.formtip').remove();
 			}
 		});
 		$('body',parent.document).find('.quoteparameter .six .buyindot').on('blur',function(){ 
 			if( $('.quoteparameter .buyindot',parent.document).val()==''){
 				$('.quoteparameter .buyindot',parent.document).parent('div').find('.formtip').remove();
 				$('.quoteparameter .buyindot',parent.document).parent('div').append('<i class="formtip">总行对分行买入点差不能为空!</i>');
 			}else if( !numreg2.test($('.quoteparameter .buyindot',parent.document).val()) ){
 				$('.quoteparameter .buyindot',parent.document).parent('div').find('.formtip').remove();
 				$('.quoteparameter .buyindot',parent.document).parent('div').append('<i class="formtip">总行对分行买入点差必须是正整数!</i>');
 			}else{
 				$('.quoteparameter .buyindot',parent.document).parent('div').find('.formtip').remove();
 			}
 		});
 		$('body',parent.document).find('.quoteparameter .six .bytoutdot').on('blur',function(){ 
 			if( $('.quoteparameter .bytoutdot',parent.document).val()=='' ){
 				$('.quoteparameter .bytoutdot',parent.document).parent('div').find('.formtip').remove();
 				$('.quoteparameter .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差不能为空!</i>');		
 			}else if( !numreg2.test($('.quoteparameter .bytoutdot',parent.document).val()) ){
 				$('.quoteparameter .bytoutdot',parent.document).parent('div').find('.formtip').remove();
 				$('.quoteparameter .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差必须是正整数!</i>');		
 			}else{
 				$('.quoteparameter .bytoutdot',parent.document).parent('div').find('.formtip').remove();
 			}
 		});
 		$('body',parent.document).find('.quoteparameter .six .sallhand').on('blur',function(){ 
 			if( $('.quoteparameter .sallhand',parent.document).val()=='' ){
 				$('.quoteparameter .sallhand',parent.document).parent('div').find('.formtip').remove();
 				$('.quoteparameter .sallhand',parent.document).parent('div').append('<i class="formtip">总行对分行卖出点差不能为空!</i>');
 				
 			}else if( !numreg2.test($('.quoteparameter .sallhand',parent.document).val()) ){
 				$('.quoteparameter .sallhand',parent.document).parent('div').find('.formtip').remove();
 				$('.quoteparameter .sallhand',parent.document).parent('div').append('<i class="formtip">总行对分行卖出点差必须是正整数!</i>');
 			}else{
 				$('.quoteparameter .sallhand',parent.document).parent('div').find('.formtip').remove();
 			}
 		});
 		$('body',parent.document).find('.quoteparameter .six .sallcust').on('blur',function(){ 
 			if($('.quoteparameter .sallcust',parent.document).val()==''){
 				$('.quoteparameter .sallcust',parent.document).parent('div').find('.formtip').remove();
 				$('.quoteparameter .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差不能为空!</i>');
 			}else if( !numreg2.test($('.quoteparameter .sallcust',parent.document).val()) ){
 				$('.quoteparameter .sallcust',parent.document).parent('div').find('.formtip').remove();
 				$('.quoteparameter .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差必须是正整数!</i>');
 			}else{
 				$('.quoteparameter .sallcust',parent.document).parent('div').find('.formtip').remove();
 			}
 		});
 		//干预  失焦判断；
 		$('body',parent.document).find('.quoteparameter .three .nebp').on('blur',function(){ 
 			if($('.quoteparameter .three .nebp',parent.document).val()==''){
 				$('.quoteparameter .nebp',parent.document).parent('div').find('.formtip').remove();
 				$('.quoteparameter .nebp',parent.document).parent('div').append('<i class="formtip">买入价干预点数不能为空!</i>');
 			}else if(!numreg.test($('.quoteparameter .three .nebp',parent.document).val())){
 				$('.quoteparameter .nebp',parent.document).parent('div').find('.formtip').remove();
 				$('.quoteparameter .nebp',parent.document).parent('div').append('<i class="formtip">买入价干预点数必须是不超过4位的正整数!</i>');
 			}else{
 				$('.quoteparameter .nebp',parent.document).parent('div').find('.formtip').remove();
 			}
 		});
 		$('body',parent.document).find('.quoteparameter .three .nesp').on('blur',function(){ 
 			if($('.quoteparameter .three .nesp',parent.document).val()==''){
 				$('.quoteparameter .nesp',parent.document).parent('div').find('.formtip').remove();
 				$('.quoteparameter .nesp',parent.document).parent('div').append('<i class="formtip">卖出价干预点数不能为空!</i>');
 			}else if(!numreg.test($('.quoteparameter .three .nesp',parent.document).val())){
 				$('.quoteparameter .nesp',parent.document).parent('div').find('.formtip').remove();
 				$('.quoteparameter .nesp',parent.document).parent('div').append('<i class="formtip">卖出价干预点数必须是不超过4位的正整数!</i>');
 			}else{
 				$('.quoteparameter .nesp',parent.document).parent('div').find('.formtip').remove();
 			}
 		});
 		
     });
	 //产品市场源头选择  ；失焦判断；
	 var Numreg=/\.{1}\d*$/;  
	$('body',parent.document).on('click','#sT',function(){
		WdatePicker({
			dateFmt:'yyyyMMdd HH:mm:ss',
			isShowClear:false,
			isShowToday:false,
			isShowOthers:false,
			isShowOk:false,
			qsEnabled:false,
			position:{left:-302,top:-60}
			/*onpicked:function(){
				$('body #sT',parent.document).attr('t','clicked');
				if( $('body #eT',parent.document).attr('t') ){
					comptime( $('body #sT',parent.document).val(),$('body #eT',parent.document).val());
				}
			}*/
		}); 
	});
	$('body',parent.document).on('click','#eT',function(){
		WdatePicker({
			dateFmt:'yyyyMMdd HH:mm:ss',
			isShowClear:false,
			isShowToday:false,
			isShowOthers:false,
			isShowOk:false,
			qsEnabled:false,
			position:{left:-302,top:-60}
			/*onpicked:function(){
				$('body #eT',parent.document).attr('t','clicked');
				if( $('body #sT',parent.document).attr('t')  ){
					comptime( $('body #sT',parent.document).val(),$('body #eT',parent.document).val());
				}
			}*/
		}); 	
	});
	function comptime(obj,obj1) {
	    var beginTime = obj;
	    var endTime = obj1;
	    beginTime = beginTime.substring(0,4) + '-' + beginTime.substring(4,6) + '-' + beginTime.substring(6,8)+' '+beginTime.substring(9,16);
	    endTime = endTime.substring(0,4) + '-' + endTime.substring(4,6) + '-' + endTime.substring(6,8)+' '+endTime.substring(9, 16);
	    var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
	    
	  	if (a < 0||a==0) {
	  		return false;
	  		//dialog.choicedasta('截止时间应大于起始时间!','quoteparameter');
	    } else if (a > 0) {
	        return true;
	    }
	}
     //tab切换的div左右选择
     $('body',parent.document).on('click','.quoteparameter .marketsource p ',function(){
		 $(this).addClass('tabpbgc').siblings().removeClass('tabpbgc');
     });
     //点击 添加  产品市场源头；
     $('body',parent.document).on('click','.quoteparameter .prisoleft .priceadd ',function(){
    	 if($('.quoteparameter .sourcechoice .tabpbgc',parent.document).length>0 ){
			 var txt=$('.quoteparameter .one .sourcechoice .tabpbgc',parent.document).text(),
			 	 valu=$('.quoteparameter .sourcechoice .tabpbgc',parent.document).attr('value'),
			 	 brr=[];
			$('.quoteparameter .marktset p',parent.document).each(function(i,v){
				brr.push( $(v).attr('mkid') );
			});
			if( brr.indexOf(valu)==-1){
					$('.quoteparameter .marktset .marketcont ',parent.document).append('<p mkid='+valu+' mklv='+(brr.length+1)+'><span>'+txt+'</span><span contenteditable="true" class="conte">0</input></p>');
			}else{
				 dialog.choicedata('该市场已经存在!','quoteparameter');
			}
    	 }else{
    		 dialog.choicedata('请先选择一条数据!','quoteparameter');
    	 }
     });
     
      $('body',parent.document).on('click','.quoteparameter .prisoleft .pricedelete ',function(){
    	  if( $('.quoteparameter .one .prisoright .tabpbgc',parent.document).length>0){
    		  $('.quoteparameter .marktset .tabpbgc',parent.document).remove();
    	  }else{
    		  dialog.choicedata('请先选择一条数据!','quoteparameter');
    	  }
     });
     //上移下移的实现
     $('body',parent.document).on('click','.quoteparameter .prisoright .priceadd ',function(){
     	  var current=$('.quoteparameter .marktset .marketcont .tabpbgc',parent.document) 
		 if(current.prev().html() != undefined){ 
            var obj = current.clone(true); 
            current.prev().before(obj); 
            current.remove(); 
        }
	 });
	 $('body',parent.document).on('click','.quoteparameter .prisoright .pricedelete ',function(){
     	  var current=$('.quoteparameter .marktset .marketcont .tabpbgc',parent.document) 
		 if(current.next().html() != undefined){ 
            var obj = current.clone(true); 
            current.next().after(obj); 
            current.remove(); 
        }
	 });
	//点击分页;
    $('.boxtop').on('click','.first',function(){
    	var Nopage=parseInt($('.Nopage').text());
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	if(product=="P999"){
 	    		rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'prod':prod,'pageNo':Nopage,'pageSize':10} });
 	    	}else{
 	    		rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'pageNo':Nopage,'pageSize':10} });
 	    	}
 	    	
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=parseInt($('.Nopage').text());
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	if(product=="P999"){
 	    		rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'prod':prod,'pageNo':Nopage,'pageSize':10} });
 	    	}else{
 	    		rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'pageNo':Nopage,'pageSize':10} });
 	    	}
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=parseInt($('.Nopage').text());
			Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	if(product=="P999"){
 	    		rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'prod':prod,'pageNo':Nopage,'pageSize':10} });
 	    	}else{
 	    		rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'pageNo':Nopage,'pageSize':10} });
 	    	}
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=parseInt($('.Nopage').text());
		Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	if(product=="P999"){
 	    		rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'prod':prod,'pageNo':Nopage,'pageSize':10} });
 	    	}else{
 	    		rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'pageNo':Nopage,'pageSize':10} });
 	    	}
	    }
	});
	//点击生效按钮
    $('.takeeffect').click(function(){
   	     $.ajax({
			url:'/fx/SendAccExPdtRparaSocket.do',
			type:'get',
			async:false,
			dataType:'json',
		    contentType:'application/json;charset=utf-8',
			success:function(data){
				 if(data.code==00){
					dialog.choicedata('发送消息','quoteparameter');
				}else{
					dialog.choicedata('发送消息','quoteparameter'); 
				}
			}
		});
    })
	 //修改当前策略；
	 $('body',parent.document).on('click','.quoteparameter .two .pristrleft .marketcontr p',function(){
		 $('.quoteparameter .two .pristrright .marketcontr',parent.document).html( $(this).clone() );
	 });
	$('body',parent.document).on('click','.quoteparameter .sure,.quoteparamete .sure',function(){
		//关闭选择一条数据;
		$(this).closest('.zhezhao').remove();
	}); 
	//修改框的取消  关闭修改框
	$('body',parent.document).on('click','.quoteparameter .price_close',function(){
		$(this).closest('.zhezhao').remove();
		if(product=="P999"){
			rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'prod':prod,'pageNo':1,'pageSize':10} });
			renpage(product)
		}else{
			rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'pageNo':1,'pageSize':10} });
			renpage(product);
		}
		
	}); 
	$('body',parent.document).on('click','.price_cancel',function(){
		$(this).closest('.zhezhao').remove();
		
		if(product=="P999"){
			rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'prod':prod,'pageNo':1,'pageSize':10} });
			renpage(product);
		}else{
			rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'pageNo':1,'pageSize':10} });
			renpage(product);
		}
	});
	function renpage(product){
    	layui.use(['laypage', 'layer'], function(){
    		  var laypage = layui.laypage,layer = layui.layer;
    		  //完整功能
    		  laypage.render({
    		    elem: 'page'
    		    ,count:listnumtotal
    		    ,theme: '#5a8bff'
    		    ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
    		    ,jump: function(obj,first){
    		    	if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
    		    		if(product=='P999'){
    		    			rendlist( {
    		    				'url':'selectPdtRList.do',
    		    				'obj':{
    		    					'userKey':userKey,
    		    					'prod':prod,
    		    					'pageNo':obj.curr,
    		    					'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1
    		    					} 
    		    			});
    		    		}else{
    		    			rendlist( {
    		    				'url':'selectPdtRList.do',
    		    				'obj':{
    		    					'userKey':userKey,
    		    					'pageNo':obj.curr,
    	    		    	    	'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1
    	    		    	    	}
    		    			});
    		    		}
    		    	}	
    		    }
    		  });
    	});
    }

});