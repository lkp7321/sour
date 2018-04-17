require.config({
	baseUrl:'/fx/js/',
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
	    { title:'货币对名称', name:'exnm' ,width:150, align:'left' },
	    { title:'价格类型', name:'tpnm' ,width:150, align:'center'},
	    { title:'钞汇标志', name:'cxfg' ,width:150, align:'center'},
	    { title:'报价策略', name:'gmnm' ,width:150, align:'left'},
	    { title:'期限', name:'term' ,width:120, align:'right'}
	];
	var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    //请求列表数据；
    rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'pageNo':1,'pageSize':10} });
    function rendlist(obj){
    	$.ajax({
    		url:obj.url,
    		type:'post',
    		async:false,
    		contentType:'application/json',
    		data:JSON.stringify(obj.obj ),
    		success:function(data){
    			if(data.code==00){
    				userdata=data.codeMessage;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
//    				$('.page').remove();
//    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    			}else if(data.code==01||data.code==02){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    } 
    
    
	$('.offerparase .modify').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
		    var exnm=$('.box tr.selected td:eq(0) span').text();
			$.ajax({      															//查询货币对类型，以判断点击“修改”后的弹出框的部分；
				url:'/fx/selectExtp2.do',
				type:'post',
				contentType:'application/json',
				//data:JSON.stringify({'exnm':exnm}),
				data:exnm,
				async:true,
				success:function(data){
					if(data.code==00){
						userdata=JSON.parse(data.codeMessage);
						if( userdata==0){                                            //判断展示对应的弹出框；
							 dialog.priceModify("offerparaset");
							 $('.offerparaset .pricecontmain .two',parent.document).show();
					         $('.offerparaset .pricecontmain ul',parent.document).css({"width":"344px"});
					         checkfn({'url':'GetGmnm.do','data':{userKey:userKey,exnm:exnm},'obj':'offerparaset'});
					 	 }else if( userdata==1){
							 dialog.priceModify("offerparaset","0");
							 $('.offerparaset .pricecontmain .six',parent.document).show();
					         $('.offerparaset .pricecontmain ul',parent.document).css({"width":"344px"});
					         checkfn({'url':'CurPointInit.do','data':{userKey:userKey,exnm:exnm},'obj':'offerparaset'});
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
			dialog.choicedata('请先选择一条数据!','offerparaset');
		}
	});
	renpage();
    function renpage(){
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
			    		rendlist({
			    				'url':'/fx/selectPdtRList.do',
			    				'obj':{
			    					'userKey':userKey,
			    					'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
			    					'pageNo':obj.curr
			    					} 
			    		}
			    		);
			    	}	
			    }
			  });
		});
	}
	//点击点差保存；
	$('body',parent.document).on('click','.offerparaset .six .price_save',function(){
		
		if($('.offerparaset .pricelive',parent.document).val()==''){
			$('.offerparaset .pricelive',parent.document).parent('div').find('.formtip').remove();
			$('.offerparaset .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期不能为空!</i>');
		}else if( !numreg2.test( $('.offerparaset .pricelive',parent.document).val() ) ){
			$('.offerparaset .pricelive',parent.document).parent('div').find('.formtip').remove();
			$('.offerparaset .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期必须是正整数!</i>');
		}else{
			$('.offerparaset .pricelive',parent.document).parent('div').find('.formtip').remove();
		}
		
		if( $('.offerparaset .buyindot',parent.document).val()=='' ){
			$('.offerparaset .buyindot',parent.document).parent('div').find('.formtip').remove();
			$('.offerparaset .buyindot',parent.document).parent('div').append('<i class="formtip">总行对分行买入点差不能为空!</i>');
		}else if( !numreg2.test($('.offerparaset .buyindot',parent.document).val()) ){
			$('.offerparaset .buyindot',parent.document).parent('div').find('.formtip').remove();
			$('.offerparaset .buyindot',parent.document).parent('div').append('<i class="formtip">总行对分行买入点差必须是正整数!</i>');
		}else{
			$('.offerparaset .buyindot',parent.document).parent('div').find('.formtip').remove();
		}
		
		if($('.offerparaset .bytoutdot',parent.document).val()==''){
			$('.offerparaset .bytoutdot',parent.document).parent('div').find('.formtip').remove();
			$('.offerparaset .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差不能为空!</i>');	
		}else if( !numreg2.test($('.offerparaset .bytoutdot',parent.document).val()) ){
			$('.offerparaset .bytoutdot',parent.document).parent('div').find('.formtip').remove();
			$('.offerparaset .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差必须是正整数!</i>');		
		}else{
			$('.offerparaset .bytoutdot',parent.document).parent('div').find('.formtip').remove();
		}
		
		if( $('.offerparaset .sallhand',parent.document).val()==''){
			$('.offerparaset .sallhand',parent.document).parent('div').find('.formtip').remove();
			$('.offerparaset .sallhand',parent.document).parent('div').append('<i class="formtip">总行对分行卖出点差不能为空!</i>');
		}else if( !numreg2.test($('.offerparaset .sallhand',parent.document).val()) ){
			$('.offerparaset .sallhand',parent.document).parent('div').find('.formtip').remove();
			$('.offerparaset .sallhand',parent.document).parent('div').append('<i class="formtip">总行对分行卖出点差必须是正整数!</i>');
		}else{
			$('.offerparaset .sallhand',parent.document).parent('div').find('.formtip').remove();
		}
		if($('.offerparaset .sallcust',parent.document).val()==''){
			$('.offerparaset .sallcust',parent.document).parent('div').find('.formtip').remove();
			$('.offerparaset .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差不能为空!</i>');
		}else if( !numreg2.test($('.offerparaset .sallcust',parent.document).val()) ){
			$('.offerparaset .sallcust',parent.document).parent('div').find('.formtip').remove();
			$('.offerparaset .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差必须是正整数!</i>');
		}else{
			$('.offerparaset .sallcust',parent.document).parent('div').find('.formtip').remove();
		}
		if( $('.offerparaset .six .formtip',parent.document).length<=0){
			var PriceVo={ userKey:userKey,pdtPoint:{
				'tpfg':$('.box tr.selected td:eq(1) span').text(),
				'exnm':$('.box tr.selected td:eq(0) span').text(),
				'bhbd':$('.offerparaset .buyindot',parent.document).val(),
				'bhsd':$('.offerparaset .sallhand',parent.document).val(),
				'cubd':$('.offerparaset .bytoutdot',parent.document).val(),
				'cusd':$('.offerparaset .sallcust',parent.document).val(),
				'prtp':$('.offerparaset input[name="priceradio4"]:checked',parent.document).attr('tit'),
				'term':$('.box tr.selected td:eq(4) span').text(),
				'qtcy':$('.offerparaset .pricelive',parent.document).val(),
				'cxfg':$('.offerparaset .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit')
				}
			};
			savefn({url:'/fx/SavePoint.do',obj:PriceVo});
		}
	});
	//保存策略；
	$('body',parent.document).on('click','.offerparaset .two .price_sav',function(){
		if( $('.offerparaset .two .marketcontr .tabpbgc',parent.document).length>0){
			var PriceVo={ userKey:userKey,pdtrPara:{
				'gmnm':$('.two .pristrright .marketcontr p',parent.document).text(),
				'cxfg':$('.offerparaset .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
				'exnm':$('.box tr.selected td:eq(0) span').text(),
				'tpfg':$('.box tr.selected td:eq(1) span').text(),
				'term':$('.box tr.selected td:eq(4) span').text()
				},
				'exnm':$('.box tr.selected td:eq(0) span').text()
			};
			savefn({url:'/fx/SaveGmnm.do',obj:PriceVo});
		}else{
			dialog.choicedata('策略没有修改,不必保存!','offerparaset');
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
					dialog.choicedata(data.codeMessage,'offerparaset');
					if(obj.url=='SaveGmnm.do'){
						$('.offerparaset .two .currentstra div',parent.document).text( $('.offerparaset .two .pristrright .tabpbgc',parent.document).text() );
					}
					//rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'pageNo':1,'pageSize':10} });
				}else{
					dialog.choicedata(data.codeMessage,'offerparaset');
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
					if(obj.url=='CurPointInit.do'){			//点差
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
		if(txt=='点差'){
			checkfn({'url':'CurPointInit.do','data':{userKey:userKey,exnm:exnm},'obj':'offerparaset'});
		
		}else if(txt=='策略'){
			
			checkfn({'url':'GetGmnm.do','data':{userKey:userKey,exnm:exnm},'obj':'offerparaset'});
		} 
	})
	//点差 失焦判断；
 		$('body',parent.document).find('.offerparaset .six .pricelive').on('blur',function(){
 			if( $('.offerparaset .pricelive',parent.document).val()==''){
 				$('.offerparaset .pricelive',parent.document).parent('div').find('.formtip').remove();
 				$('.offerparaset .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期不能为空!</i>');
 			}else if( !numreg2.test( $('.offerparaset .pricelive',parent.document).val() ) ){
 				$('.offerparaset .pricelive',parent.document).parent('div').find('.formtip').remove();
 				$('.offerparaset .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期必须是正整数!</i>');
 			}else{
 				$('.offerparaset .pricelive',parent.document).parent('div').find('.formtip').remove();
 			}
 		});
 		$('body',parent.document).find('.offerparaset .six .buyindot').on('blur',function(){ 
 			if( $('.offerparaset .buyindot',parent.document).val()==''){
 				$('.offerparaset .buyindot',parent.document).parent('div').find('.formtip').remove();
 				$('.offerparaset .buyindot',parent.document).parent('div').append('<i class="formtip">总行对分行买入点差不能为空!</i>');
 			}else if( !numreg2.test($('.offerparaset .buyindot',parent.document).val()) ){
 				$('.offerparaset .buyindot',parent.document).parent('div').find('.formtip').remove();
 				$('.offerparaset .buyindot',parent.document).parent('div').append('<i class="formtip">总行对分行买入点差必须是正整数!</i>');
 			}else{
 				$('.offerparaset .buyindot',parent.document).parent('div').find('.formtip').remove();
 			}
 		});
 		$('body',parent.document).find('.offerparaset .six .bytoutdot').on('blur',function(){ 
 			if( $('.offerparaset .bytoutdot',parent.document).val()=='' ){
 				$('.offerparaset .bytoutdot',parent.document).parent('div').find('.formtip').remove();
 				$('.offerparaset .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差不能为空!</i>');		
 			}else if( !numreg2.test($('.offerparaset .bytoutdot',parent.document).val()) ){
 				$('.offerparaset .bytoutdot',parent.document).parent('div').find('.formtip').remove();
 				$('.offerparaset .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差必须是正整数!</i>');		
 			}else{
 				$('.offerparaset .bytoutdot',parent.document).parent('div').find('.formtip').remove();
 			}
 		});
 		$('body',parent.document).find('.offerparaset .six .sallhand').on('blur',function(){ 
 			if( $('.offerparaset .sallhand',parent.document).val()=='' ){
 				$('.offerparaset .sallhand',parent.document).parent('div').find('.formtip').remove();
 				$('.offerparaset .sallhand',parent.document).parent('div').append('<i class="formtip">总行对分行卖出点差不能为空!</i>');
 				
 			}else if( !numreg2.test($('.offerparaset .sallhand',parent.document).val()) ){
 				$('.offerparaset .sallhand',parent.document).parent('div').find('.formtip').remove();
 				$('.offerparaset .sallhand',parent.document).parent('div').append('<i class="formtip">总行对分行卖出点差必须是正整数!</i>');
 			}else{
 				$('.offerparaset .sallhand',parent.document).parent('div').find('.formtip').remove();
 			}
 		});
 		$('body',parent.document).find('.offerparaset .six .sallcust').on('blur',function(){ 
 			if($('.offerparaset .sallcust',parent.document).val()==''){
 				$('.offerparaset .sallcust',parent.document).parent('div').find('.formtip').remove();
 				$('.offerparaset .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差不能为空!</i>');
 			}else if( !numreg2.test($('.offerparaset .sallcust',parent.document).val()) ){
 				$('.offerparaset .sallcust',parent.document).parent('div').find('.formtip').remove();
 				$('.offerparaset .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差必须是正整数!</i>');
 			}else{
 				$('.offerparaset .sallcust',parent.document).parent('div').find('.formtip').remove();
 			}
 		});
	//修改框的tab切换
	$('body',parent.document).on('click','.offerparaset .pricecontmain li',function(){
		 
         $(this).css({"background-color":"#fff"}).siblings().css({"background-color":"#9CBAFF"});
         var index=$(this).data("index");
         $("."+index+"",parent.document).siblings('.tabdiv').hide();
         $("."+index+"",parent.document).show();
     });
     //tab切换的div左右选择
     $('body',parent.document).on('click','.offerparaset .marketsource p ',function(){
		 $(this).addClass('tabpbgc').siblings().removeClass('tabpbgc');
     });
     //修改当前策略；
	 $('body',parent.document).on('click','.offerparaset .two .pristrleft .marketcontr p',function(){
		 $('.offerparaset .two .pristrright .marketcontr',parent.document).html( $(this).clone() );
	 });
	 
	$('body',parent.document).on('click','.offerparaset .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
	//关闭修改弹出框；
	$('body',parent.document).on('click','.offerparaset .price_close,.offerparaset .price_cancel',function(){
		$(this).closest('.zhezhao').remove();
		 rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'pageNo':1,'pageSize':10} });
	}); 
	//点击分页;
    $('.boxtop').on('click','.first',function(){
    	var Nopage=parseInt($('.Nopage').text());
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'pageNo':Nopage,'pageSize':10} });
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=parseInt($('.Nopage').text());
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'pageNo':Nopage,'pageSize':10} });
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=parseInt($('.Nopage').text());
			Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'pageNo':Nopage,'pageSize':10} });
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=parseInt($('.Nopage').text());
		Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	rendlist( {'url':'selectPdtRList.do','obj':{'userKey':userKey,'pageNo':Nopage,'pageSize':10} });
	    }
	});
	
	/*----------------快速搜索功能的实现------------------------*/
	$('.outfit_serbtn').click(function(){
		 
		  dialog.serchData('.outfit_seript');
    });
	//根据用户界面判断弹出层中的是否选中；  更改这块的内容;
	/*function checkradio(obj){
		if( obj=='正常'){
			$('.startoutfit',parent.document).prop('checked','checked');
		}else{
			$('.nostartout',parent.document).prop('checked','checked');
		}
	}*/

})
