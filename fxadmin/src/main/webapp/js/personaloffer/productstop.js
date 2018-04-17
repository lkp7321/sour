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
		wdatePicker:'./My97DatePicker/WdatePicker',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','wdatePicker','dialog'],function($,mmGrid,niceScroll,wdatePicker,dialog){
	var wh=$(window).height()-190+"px",
	   ww=$(window).width()-260+"px";
	   $('.boxtop').css('width',ww);
	   $('.boxtop').css('height',wh);

    var numreg=/^\d{1,4}$/,
    	numreg1=/^\d+$|^\d+\.{1}\d{1,10}$/,
    	numreg2=/^\d{1,5}$/;
	//列参数;
    var cols = [
        { title:'停牌器ID', name:'stid' ,width:150, align:'left' },
        { title:'停牌器名称', name:'stnm' ,width:150, align:'left'},
        { title:'货币对名称', name:'exnm' ,width:150, align:'left'},
        { title:'停牌标志', name:'stfg' ,width:100, align:'center'},
        { title:'有效状态', name:'usfg' ,width:80, align:'center'},
        { title:'价格类型', name:'tpfg' ,width:80, align:'center'},
        { title:'期限', name:'term' ,width:80, align:'right'}
    ];
    var usnm=sessionStorage.getItem('usnm'),
        product=sessionStorage.getItem('product'),str,userdata,
        loginuser={'usnm':usnm,'prod':product},
        userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
    
    switch(product){
        case 'P001':chanpin='外汇实盘交易平台';break;
	    case 'P002':chanpin='纸黄金交易管理平台';break;
	    case 'P003':chanpin='积存金管理平台';break;
	    case 'P004':chanpin='个人结售汇';break;
	    case 'P007':chanpin='账户交易';break;
	    case 'P009':chanpin='保证金前置';break;
	    case 'P998':chanpin='RV前置';break;
	    case 'P999':chanpin='报价平台';break;
    }
  //请求列表
    getStopList({'userKey':userKey});
	function getStopList(obj){
		$.ajax({
	    	url:'/fx/getStopList.do',
	        type:'post',
	        data:JSON.stringify(obj ),
	    	contentType:'application/json',
	    	async:true,
	    	dataType:'json',
	    	success:function(data){
	    		if(data.code==00){
	    			 ren({'cols':cols,'wh':wh,'userdata':JSON.parse(data.codeMessage)});
	    		}else if(data.code==01){
	    			ren({'cols':cols,'wh':wh,'userdata':''});
	    		}else if(data.code==02){
	    			//查询异常
	    		}
	    	  }
	      });
	};
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
				 $(v).find('td').eq(0).attr('tit',obj.userdata[i].cxfg);
			 });
		 });
    }
	//修改框弹出
	$('.modify').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){
			var exnm=$('.box tr.selected td:eq(2) span').text(),
                tpfg=$('.box tr.selected td:eq(5) span').text();
			$.ajax({
		    	url:'/fx/selectExtp2.do',
		        type:'post',
		        data:exnm,
		    	contentType:'application/json',
		    	async:false,
		    	dataType:'json',
		    	success:function(data){
		    		if(data.code==00){
						userdata=JSON.parse(data.codeMessage);
						if( userdata==1){                                            //判断展示对应的弹出框；
							 dialog.priceModify("productstop",1);
				             $('.productstop .pricecontmain .four',parent.document).show(); 
				             $('.productstop .pricecontmain ul',parent.document).css({"width":"134px"});               //初始化校验参数；
				             
				            checkfn({'url':'ChkParaInit.do','data':{userKey:userKey, exnm:exnm },'obj':'productstop'});
						}else if( userdata==0){
							dialog.priceModify("productstop",0);
				             $('.productstop .pricecontmain .one',parent.document).show();
				             $('.productstop .pricecontmain ul',parent.document).css({"width":"344px"});
				            checkfn({'url':'MarkInit2.do','data':{userKey:userKey,exnm:exnm },'obj':'productstop'});  //初始化产品市场源头选择；
						}
						//点击钞汇标志；
						$('.chnote input',parent.document).on('click',function(){
							return false;
						});
					}else if(data.code==01){
						
					}
		    	  }
		      });
		}else{
			dialog.choicedata('请先选择一条数据!','productstop');
		}
	});
	//保存校验；
	$('body',parent.document).on('click','.productstop .four .price_sav',function(){   
		if( $('.productstop .byeprice',parent.document).val()==''){
			$('.productstop .byeprice',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .byeprice',parent.document).parent('div').append('<i class="formtip">买入价不能为空!</i>');
		}else if( !numreg1.test($('.productstop .byeprice',parent.document).val()) ){
			$('.productstop .byeprice',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .byeprice',parent.document).parent('div').append('<i class="formtip">买入价必须是整数或小数!</i>');
		}else{
			$('.productstop .byeprice',parent.document).parent('div').find('.formtip').remove();
		}
		
		if( $('.productstop .cenpricedot',parent.document).val()==''){
			$('.productstop .cenpricedot',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .cenpricedot',parent.document).parent('div').append('<i class="formtip">中间价两次浮动点差不能为空!</i>');
		}else if( !numreg.test($('.productstop .cenpricedot',parent.document).val()) ){
			$('.productstop .cenpricedot',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .cenpricedot',parent.document).parent('div').append('<i class="formtip">中间价两次浮动点差必须正整数!</i>');
		}else{
			$('.productstop .cenpricedot',parent.document).parent('div').find('.formtip').remove();
		}
		if($('.productstop .pricenouse',parent.document).val()==''){
			$('.productstop .pricenouse',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .pricenouse',parent.document).parent('div').append('<i class="formtip">未波动次数不能为空!</i>');
		}else if( !numreg.test($('.productstop .pricenouse',parent.document).val()) ){
			$('.productstop .pricenouse',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .pricenouse',parent.document).parent('div').append('<i class="formtip">未波动次数必须是正整数!</i>');		
		}else{
			$('.productstop .pricenouse',parent.document).parent('div').find('.formtip').remove();
		}
		if($('.productstop .bigshavedot',parent.document).val()==''){
			$('.productstop .bigshavedot',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .bigshavedot',parent.document).parent('div').append('<i class="formtip">最大波动点差不能为空!</i>');
		}else if( !numreg.test($('.productstop .bigshavedot',parent.document).val()) ){
			$('.productstop .bigshavedot',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .bigshavedot',parent.document).parent('div').append('<i class="formtip">最大波动点差必须是正整数!</i>');
		}else{
			$('.productstop .bigshavedot',parent.document).parent('div').find('.formtip').remove();
		}
		
		if($('.productstop .goodnum',parent.document).val()==''){
			$('.productstop .goodnum',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .goodnum',parent.document).parent('div').append('<i class="formtip">合法波动次数不能为空!</i>');
		}else if( !numreg.test($('.productstop .goodnum',parent.document).val()) ){
			$('.productstop .goodnum',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .goodnum',parent.document).parent('div').append('<i class="formtip">合法波动次数必须是正整数!</i>');
		}else{
			$('.productstop .goodnum',parent.document).parent('div').find('.formtip').remove();
		}
		if( $('.productstop .four .formtip',parent.document).length<=0){
		    var PriceVo={ userKey:userKey,pdtChk:{
				'mdmd':$('.productstop .byeprice',parent.document).val()*1,
				'mxbp':$('.productstop .cenpricedot',parent.document).val(),
				'mxud':$('.productstop .pricenouse',parent.document).val(),
				'mxdp':$('.productstop .bigshavedot',parent.document).val(),
				'mxct':$('.productstop .goodnum',parent.document).val(),
				'tpfg':$('.box tr.selected td:eq(5) span').text(),
				'cxfg':$('.productstop .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
				'usfg':$('.productstop .pripubdivs input[name="priceradio"]:checked',parent.document).attr('tit'),
				'term':$('.box tr.selected td:eq(6) span').text(),
				'exnm':$('.box tr.selected td:eq(2) span').text()
				},
				exnm:$('.box tr.selected td:eq(2) span').text()
			};
			savefn({url:'/fx/SaveChkPara.do',obj:PriceVo});
		 }
	});
	//保存停牌
	$('body',parent.document).on('click','.productstop .five .price_sav',function(){
		var PriceVo={ userKey:userKey,pdtStoper:{
			'stid':$('.productstop .suspenid',parent.document).val(),
			'stfg':$('.productstop input[name="priceradio2"]:checked',parent.document).attr('tit'),    //停牌标志；
			'tpfg':$('.box tr.selected td:eq(5) span').text(),
			'term':$('.box tr.selected td:eq(6) span').text(),
			'exnm':$('.box tr.selected td:eq(2) span').text(),
			}
		};
		savefn({url:'/fx/SavePdtStop.do',obj:PriceVo});
	});
	//点击点差保存；
	$('body',parent.document).on('click','.productstop .six .price_save',function(){
		if($('.productstop .pricelive',parent.document).val()==''){
			$('.productstop .pricelive',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期不能为空!</i>');
		}else if( !numreg2.test( $('.productstop .pricelive',parent.document).val() ) ){
			$('.productstop .pricelive',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期必须是正整数!</i>');
		}else{
			$('.productstop .pricelive',parent.document).parent('div').find('.formtip').remove();
		}
		
		if( $('.productstop .buyindot',parent.document).val()=='' ){
			$('.productstop .buyindot',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .buyindot',parent.document).parent('div').append('<i class="formtip">总行对分行买入点差不能为空!</i>');
		}else if( !numreg2.test($('.productstop .buyindot',parent.document).val()) ){
			$('.productstop .buyindot',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .buyindot',parent.document).parent('div').append('<i class="formtip">总行对分行买入点差必须是正整数!</i>');
		}else{
			$('.productstop .buyindot',parent.document).parent('div').find('.formtip').remove();
		}
		
		if($('.productstop .bytoutdot',parent.document).val()==''){
			$('.productstop .bytoutdot',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差不能为空!</i>');	
		}else if( !numreg2.test($('.productstop .bytoutdot',parent.document).val()) ){
			$('.productstop .bytoutdot',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差必须是正整数!</i>');		
		}else{
			$('.productstop .bytoutdot',parent.document).parent('div').find('.formtip').remove();
		}
		
		if( $('.productstop .sallhand',parent.document).val()==''){
			$('.productstop .sallhand',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .sallhand',parent.document).parent('div').append('<i class="formtip">总行对分行卖出点差不能为空!</i>');
		}else if( !numreg2.test($('.productstop .sallhand',parent.document).val()) ){
			$('.productstop .sallhand',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .sallhand',parent.document).parent('div').append('<i class="formtip">总行对分行卖出点差必须是正整数!</i>');
		}else{
			$('.productstop .sallhand',parent.document).parent('div').find('.formtip').remove();
		}
		if($('.productstop .sallcust',parent.document).val()==''){
			$('.productstop .sallcust',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差不能为空!</i>');
		}else if( !numreg2.test($('.productstop .sallcust',parent.document).val()) ){
			$('.productstop .sallcust',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差必须是正整数!</i>');
		}else{
			$('.productstop .sallcust',parent.document).parent('div').find('.formtip').remove();
		}
		if( $('.productstop .six .formtip',parent.document).length<=0){
			var PriceVo={ userKey:userKey,pdtPoint:{
				'tpfg':$('.box tr.selected td:eq(5) span').text(),
				'exnm':$('.box tr.selected td:eq(2) span').text(),
				'bhbd':$('.productstop .buyindot',parent.document).val(),
				'bhsd':$('.productstop .sallhand',parent.document).val(),
				'cubd':$('.productstop .bytoutdot',parent.document).val(),
				'cusd':$('.productstop .sallcust',parent.document).val(),
				'prtp':$('.productstop input[name="priceradio4"]:checked',parent.document).attr('tit'),
				'term':$('.box tr.selected td:eq(6) span').text(),
				'qtcy':$('.productstop .pricelive',parent.document).val(),
				'cxfg':$('.productstop .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit')
				}
			};
			savefn({url:'/fx/SavePoint.do',obj:PriceVo});
		}
	});
	
	//保存产品市场源头选择；
	$('body',parent.document).on('click','.productstop .one .price_sav',function(){
		if($('.productstop .prisoright .marketcontr p',parent.document).length>0){
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
			var PriceVo={ userKey:userKey,pdtrPara:{
				'mkst':mkst,
				'mklv':mklv,
				'mksl':mksl,
				'cxfg':$('.productstop .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
				'exnm':$('.box tr.selected td:eq(2) span').text(),
				'tpfg':$('.box tr.selected td:eq(5) span').text(),
				'term':$('.box tr.selected td:eq(6) span').text()
				}
			};
			 if( $('.productstop .one .marketcontr p',parent.document).length==1){  //只有一条市场权重设置；
	    		 if( $('.productstop .one .marketcontr p',parent.document).find('span').eq(1).text()==1){
	    			savefn({url:'/fx/SaveMark.do',obj:PriceVo});
	    		 }else{
	    			 dialog.choicedata('市场权重必须是0到1之间的数字，小于1的数字以‘.’开始!','productsto');
	    		 }
	    	 }else{  																	//多条市场权重；
	    		 $('.productstop .one .marketcontr p',parent.document).each(function(i,v){
	    			 if( $(v).find('span').eq(1).text()==''||!/^\.{1}\d+$/.test($(v).find('span').eq(1).text())){
	    				 wrong++;
	    			 }else{
	    				 spantxt=spantxt+$(v).find('span').eq(1).text()*1;
	    			 }
	    		 });
	    		 if( spantxt==1&&wrong==0){
	    			 savefn({url:'/fx/SaveMark.do',obj:PriceVo});
	    		 }else if(spantxt!=1&&wrong==0){
	    			 dialog.choicedata('所有市场权重之和必须是1!','productstop');
	    		 }else{
	    			 dialog.choicedata('市场权重必须是0到1之间的数字，小于1的数字以‘.’开始!','productsto');
	    		 }
	    	 }
		}else{
			dialog.choicedata('没有添加产品市场，不能保存!','productstop');
		}
	});
	//保存策略；
	$('body',parent.document).on('click','.productstop .two .price_sav',function(){
		if( $('.productstop .two .marketcontr .tabpbgc',parent.document).length>0){
			var PriceVo={ userKey:userKey,pdtrPara:{
				'gmnm':$('.two .pristrright .marketcontr p',parent.document).text(),
				'cxfg':$('.productstop .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
				'exnm':$('.box tr.selected td:eq(2) span').text(),
				'tpfg':$('.box tr.selected td:eq(5) span').text(),
				'term':$('.box tr.selected td:eq(6) span').text()
				},
				'exnm':$('.box tr.selected td:eq(2) span').text()
			};
			savefn({url:'/fx/SaveGmnm.do',obj:PriceVo});
		}else{
			dialog.choicedata('策略没有修改,不必保存!','productstop');
		}
	});
	//保存干预；
	$('body',parent.document).on('click','.productstop .three .price_save',function(){
		if($('.productstop .three .nebp',parent.document).val()=='' ){
			$('.productstop .nebp',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .nebp',parent.document).parent('div').append('<i class="formtip">买入价干预点数不能为空!</i>');
		}else if(!numreg.test($('.productstop .three .nebp',parent.document).val())){
			$('.productstop .nebp',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .nebp',parent.document).parent('div').append('<i class="formtip">买入价干预点数必须是不超过4位的正整数!</i>');
		}else{
			$('.productstop .nebp',parent.document).parent('div').find('.formtip').remove();
		}
		if( $('.productstop .three .nesp',parent.document).val()==''){
			$('.productstop .nesp',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .nesp',parent.document).parent('div').append('<i class="formtip">卖出价干预点数不能为空!</i>');
		}else if(!numreg.test($('.productstop .three .nesp',parent.document).val())){
			$('.productstop .nesp',parent.document).parent('div').find('.formtip').remove();
			$('.productstop .nesp',parent.document).parent('div').append('<i class="formtip">卖出价干预点数必须是不超过4位的正整数!</i>');
		}else{
			$('.productstop .nesp',parent.document).parent('div').find('.formtip').remove();
		}
		if( $('.productstop .three .formtip',parent.document).length<=0&& comptime( $('body #sT',parent.document).val(),$('body #eT',parent.document).val() )==true){			
			var PriceVo={ userKey:userKey,pdtCtrl:{
				'nebp':$('.productstop .three .nebp',parent.document).val(),
				'nesp':$('.productstop .three .nesp',parent.document).val(),
				'bgtm':$('.productstop .three #sT',parent.document).val(),
				'edtm':$('.productstop .three #eT',parent.document).val(),
				'stfg':$('.productstop .three .usestfg input[name="stfg"]:checked',parent.document).attr('tit'),
				'ctid':$('.productstop .three .ctid',parent.document).val(),
				'term':$('.box tr.selected td:eq(6) span').text(),
				'exnm':$('.box tr.selected td:eq(2) span').text(),
				'cxfg':$('.productstop .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit')
				}
			};
			savefn({url:'/fx/SaveCtrl.do',obj:PriceVo});
		}else if($('.productstop .three .formtip',parent.document).length<=0&&comptime( $('body #sT',parent.document).val(),$('body #eT',parent.document).val() )==false){
			dialog.choicedata('截止时间应大于起始时间!','productstop');
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
					dialog.choicedata(data.codeMessage,'productstop');
					if(obj.url=='SaveGmnm.do'){
						$('.productstop .two .currentstra div',parent.document).text( $('.productstop .two .pristrright .tabpbgc',parent.document).text() );
					}
					//checkfn({'url':'GetGmnm.do','data':{prod:product,exnm:exnm},'obj':'productstop','exnm':$('.box tr.selected td:eq(0) span').text(),'chanpin':chanpin});
					 
				}else{
					dialog.choicedata(data.codeMessage,'productstop');
				}
			}
		});
		
	}
	//调用弹出框数据的函数；
	function checkfn(obj){                                      //校验函数
		var str='',str1='',chanpin,cxfg;
		switch(product){
			case 'P001':chanpin='个人实盘';break;
			case 'P002':chanpin='纸黄金交易管理平台';break;
			case 'P003':chanpin='积存金管理平台';break;
			case 'P004':chanpin='个人结售汇';break;
			case 'P007':chanpin='账户交易';break;
			case 'P009':chanpin='保证金前置';break;
			case 'P998':chanpin='RV前置';break;
			case 'P999':chanpin='报价平台';break;
		}
		//公共部分  更改弹出框头部的内容；
		$('.'+obj.obj+' .hbdname span',parent.document).text( $('.box tr.selected td:eq(2) span').text() );
		$('.'+obj.obj+' .productname span',parent.document).text( chanpin );    //chanpin;用来显示产品名称；
		$('.'+obj.obj+' .privetype span',parent.document).text( $('.box tr.selected td:eq(5) span').text() ); //远期即期；
		if( $('.box tr.selected td:eq(0)').attr('tit')=='汇'){       //钞汇标志；
			$('.'+obj.obj+' .priceradi1',parent.document).prop('checked','checked');
		}else{
			$('.'+obj.obj+' .priceradi0',parent.document).prop('checked','checked');
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
						//wenti:  1.查询暂无记录；2.停牌标志；
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
									str1+='<p mkid='+product[i].mkid+' mklv='+product[i].mklv+'>'+'<span>'+product[i].mknm+'</span>'+'<span contenteditable="true">'+product[i].mksl+'</span>'+'</p>'
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
			exnm=$('.box tr.selected td:eq(2) span').text(),
			PriceVo;
		if(txt=='校验'){
			checkfn({'url':'ChkParaInit.do','data':{userKey:userKey, exnm:exnm},'obj':'productstop'});
		}else if(txt=='停牌'){
			PriceVo={userKey:userKey,exnm:exnm,stid:'SC99'};
			checkfn({'url':'CurStopInit.do','data':PriceVo,'obj':'productstop'} );
			
		}else if(txt=='点差'){
			checkfn({'url':'CurPointInit.do','data':{userKey:userKey,exnm:exnm},'obj':'productstop'});
		
		}else if(txt=='产品市场源头选择'){
			
			checkfn({'url':'CurPointInit.do','data':{userKey:userKey,exnm:exnm},'obj':'productstop'});
		}else if(txt=='策略'){
			
			checkfn({'url':'GetGmnm.do','data':{userKey:userKey,exnm:exnm},'obj':'productstop'});
		}else if(txt=='干预'){
			
			checkfn({'url':'GetPdtCtrl.do','data':{userKey:userKey,ctid:'CP99',exnm:exnm},'obj':'productstop'});
		}
      $(this).css({"background-color":"#fff"}).siblings().css({"background-color":"#9CBAFF"});
      var index=$(this).data("index");
      $("."+index+"",parent.document).siblings('.tabdiv').hide();
      $("."+index+"",parent.document).show();
         
      //失去焦点判断    校验；
      $('body',parent.document).find('.productstop .four .byeprice').on('blur',function(){ // 买入价；
	    	 if( $('.productstop .byeprice',parent.document).val()==''){
	    		 $(this).parent('div').find('.formtip').remove();
	 			 $('.productstop .byeprice',parent.document).parent('div').append('<i class="formtip">买入价不能为空!</i>');
	    	 }else if( !numreg1.test($('.productstop .byeprice',parent.document).val()) ){
				$(this).parent('div').find('.formtip').remove();
				$('.productstop .byeprice',parent.document).parent('div').append('<i class="formtip">买入价必须是正整数或小数!</i>');
			}else{
				$(this).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productstop .four .cenpricedot').on('blur',function(){ 
			if( $('.productstop .cenpricedot',parent.document).val()=='' ){
				$(this).parent('div').find('.formtip').remove();
				$('.productstop .cenpricedot',parent.document).parent('div').append('<i class="formtip">中间价两次浮动点差不能为空!</i>');
			}else if( !numreg.test($('.productstop .cenpricedot',parent.document).val()) ){
				$(this).parent('div').find('.formtip').remove();
				$('.productstop .cenpricedot',parent.document).parent('div').append('<i class="formtip">中间价两次浮动点差必须是正整数!</i>');
			}else{
				$(this).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productstop .four .pricenouse').on('blur',function(){
			if($('.productstop .pricenouse',parent.document).val()==''){
				$(this).parent('div').find('.formtip').remove();
				$('.productstop .pricenouse',parent.document).parent('div').append('<i class="formtip">未波动次数不能为空!</i>');	
			}else if( !numreg.test($('.productstop .pricenouse',parent.document).val()) ){
				$(this).parent('div').find('.formtip').remove();
				$('.productstop .pricenouse',parent.document).parent('div').append('<i class="formtip">未波动次数必须是正整数!</i>');		
			}else{
				$(this).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productstop .four .bigshavedot').on('blur',function(){
			if($('.productstop .bigshavedot',parent.document).val()==''){
				$(this).parent('div').find('.formtip').remove();
				$('.productstop .bigshavedot',parent.document).parent('div').append('<i class="formtip">最大波动点差不能为空!</i>');
			}else if( !numreg.test($('.productstop .bigshavedot',parent.document).val()) ){
				$(this).parent('div').find('.formtip').remove();
				$('.productstop .bigshavedot',parent.document).parent('div').append('<i class="formtip">最大波动点差必须是正整数!</i>');
			}else{
				$(this).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productstop .four .goodnum').on('blur',function(){
			if( $('.productstop .goodnum',parent.document).val()==''){
				$(this).parent('div').find('.formtip').remove();
				$('.productstop .goodnum',parent.document).parent('div').append('<i class="formtip">合法波动次数不能为空!</i>');
			}else if( !numreg.test($('.productstop .goodnum',parent.document).val()) ){
				$(this).parent('div').find('.formtip').remove();
				$('.productstop .goodnum',parent.document).parent('div').append('<i class="formtip">合法波动次数必须是正整数!</i>');
			}else{
				$(this).parent('div').find('.formtip').remove();
			}
		});
		//点差 失焦判断；
		$('body',parent.document).find('.productstop .six .pricelive').on('blur',function(){
			if( $('.productstop .pricelive',parent.document).val()==''){
				$('.productstop .pricelive',parent.document).parent('div').find('.formtip').remove();
				$('.productstop .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期不能为空!</i>');
			}else if( !numreg2.test( $('.productstop .pricelive',parent.document).val() ) ){
				$('.productstop .pricelive',parent.document).parent('div').find('.formtip').remove();
				$('.productstop .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期必须是正整数!</i>');
			}else{
				$('.productstop .pricelive',parent.document).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productstop .six .buyindot').on('blur',function(){ 
			if( $('.productstop .buyindot',parent.document).val()==''){
				$('.productstop .buyindot',parent.document).parent('div').find('.formtip').remove();
				$('.productstop .buyindot',parent.document).parent('div').append('<i class="formtip">总行对分行买入点差不能为空!</i>');
			}else if( !numreg2.test($('.productstop .buyindot',parent.document).val()) ){
				$('.productstop .buyindot',parent.document).parent('div').find('.formtip').remove();
				$('.productstop .buyindot',parent.document).parent('div').append('<i class="formtip">总行对分行买入点差必须是正整数!</i>');
			}else{
				$('.productstop .buyindot',parent.document).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productstop .six .bytoutdot').on('blur',function(){ 
			if( $('.productstop .bytoutdot',parent.document).val()=='' ){
				$('.productstop .bytoutdot',parent.document).parent('div').find('.formtip').remove();
				$('.productstop .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差不能为空!</i>');		
			}else if( !numreg2.test($('.productstop .bytoutdot',parent.document).val()) ){
				$('.productstop .bytoutdot',parent.document).parent('div').find('.formtip').remove();
				$('.productstop .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差必须是正整数!</i>');		
			}else{
				$('.productstop .bytoutdot',parent.document).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productstop .six .sallhand').on('blur',function(){ 
			if( $('.productstop .sallhand',parent.document).val()=='' ){
				$('.productstop .sallhand',parent.document).parent('div').find('.formtip').remove();
				$('.productstop .sallhand',parent.document).parent('div').append('<i class="formtip">总行对分行卖出点差不能为空!</i>');
				
			}else if( !numreg2.test($('.productstop .sallhand',parent.document).val()) ){
				$('.productstop .sallhand',parent.document).parent('div').find('.formtip').remove();
				$('.productstop .sallhand',parent.document).parent('div').append('<i class="formtip">总行对分行卖出点差必须是正整数!</i>');
			}else{
				$('.productstop .sallhand',parent.document).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productstop .six .sallcust').on('blur',function(){ 
			if($('.productstop .sallcust',parent.document).val()==''){
				$('.productstop .sallcust',parent.document).parent('div').find('.formtip').remove();
				$('.productstop .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差不能为空!</i>');
			}else if( !numreg2.test($('.productstop .sallcust',parent.document).val()) ){
				$('.productstop .sallcust',parent.document).parent('div').find('.formtip').remove();
				$('.productstop .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差必须是正整数!</i>');
			}else{
				$('.productstop .sallcust',parent.document).parent('div').find('.formtip').remove();
			}
		});
		//干预  失焦判断；
		$('body',parent.document).find('.productstop .three .nebp').on('blur',function(){ 
			if($('.productstop .three .nebp',parent.document).val()==''){
				$('.productstop .nebp',parent.document).parent('div').find('.formtip').remove();
				$('.productstop .nebp',parent.document).parent('div').append('<i class="formtip">买入价干预点数不能为空!</i>');
			}else if(!numreg.test($('.productstop .three .nebp',parent.document).val())){
				$('.productstop .nebp',parent.document).parent('div').find('.formtip').remove();
				$('.productstop .nebp',parent.document).parent('div').append('<i class="formtip">买入价干预点数必须是不超过4位的正整数!</i>');
			}else{
				$('.productstop .nebp',parent.document).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productstop .three .nesp').on('blur',function(){ 
			if($('.productstop .three .nesp',parent.document).val()==''){
				$('.productstop .nesp',parent.document).parent('div').find('.formtip').remove();
				$('.productstop .nesp',parent.document).parent('div').append('<i class="formtip">卖出价干预点数不能为空!</i>');
			}else if(!numreg.test($('.productstop .three .nesp',parent.document).val())){
				$('.productstop .nesp',parent.document).parent('div').find('.formtip').remove();
				$('.productstop .nesp',parent.document).parent('div').append('<i class="formtip">卖出价干预点数必须是不超过4位的正整数!</i>');
			}else{
				$('.productstop .nesp',parent.document).parent('div').find('.formtip').remove();
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
			position:{left:-302,top:-60},
			onpicked:function(){
					comptime( $('body #sT',parent.document).val(),$('body #eT',parent.document).val());
			}
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
			position:{left:-302,top:-60},
			onpicked:function(){
				comptime( $('body #sT',parent.document).val(),$('body #eT',parent.document).val());
			}
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
     $('body',parent.document).on('click','.productstop .marketsource p ',function(){
		 $(this).addClass('tabpbgc').siblings().removeClass('tabpbgc');
         
     });
     //点击 添加  产品市场源头；
     $('body',parent.document).on('click','.productstop .prisoleft .priceadd ',function(){
		 var txt=$('.productstop .sourcechoice .tabpbgc',parent.document).text(),
		 	 valu=$('.productstop .sourcechoice .tabpbgc',parent.document).attr('value'),
		 	 brr=[];
		$('.productstop .marktset p',parent.document).each(function(i,v){
			brr.push( $(v).attr('mkid') );
		});
		if( brr.indexOf(valu)==-1){
				$('.productstop .marktset .marketcont ',parent.document).append('<p mkid='+valu+' mklv='+(brr.length+1)+'><span>'+txt+'</span><span contenteditable="true">0</input></p>');
		}else{
			 dialog.choicedata('该市场已经存在!','productstop');
		}
     });
	$('body',parent.document).on('click','.productstop .prisoleft .pricedelete ',function(){
		  $('.productstop .marktset .tabpbgc',parent.document).remove();
     });
      //上移下移的实现
     $('body',parent.document).on('click','.productstop .prisoright .priceadd ',function(){
     	  var current=$('.productstop .marktset .marketcont .tabpbgc',parent.document) 
		 if(current.prev().html() != undefined){ 
            var obj = current.clone(true); 
            current.prev().before(obj); 
            current.remove(); 
        }
	 });
	 $('body',parent.document).on('click','.productstop .prisoright .pricedelete ',function(){
     	  var current=$('.productstop .marktset .marketcont .tabpbgc',parent.document) 
		 if(current.next().html() != undefined){ 
            var obj = current.clone(true); 
            current.next().after(obj); 
            current.remove(); 
        }
	 });
	 $('body',parent.document).on('click','#sT',function(){
			WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',position:{left:-302,top:-60}}); 
		});
		$('body',parent.document).on('click','#simg',function(){
			WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',position:{left:-302,top:-60},el:'sT' }); 
		});
		$('body',parent.document).on('click','#eT,#eimg',function(){
			WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',position:{left:-302,top:-60} }); 
		});
		$('body',parent.document).on('click','#eimg',function(){
			WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',position:{left:-302,top:-60},el:'eT' }); 
		});
	//修改当前策略；
	 $('body',parent.document).on('click','.productstop .two .pristrleft .marketcontr p',function(){
		 $('.productstop .two .pristrright .marketcontr',parent.document).html( $(this).clone() );
	 });
	$('body',parent.document).on('click','.productstop .sure,.productsto .sure',function(){
		//关闭选择一条数据;
	    $(this).closest('.zhezhao').remove();
	}); 
	//修改框的取消  关闭修改框 
	$('body',parent.document).on('click','.productstop .price_cancel,.productstop .price_close',function(){
		$(this).closest('.zhezhao').remove();
		getStopList({'userKey':userKey});
	}); 
})