/*报价平台-产品管理-产品校验参数*/
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
		ww=$(window).width()-250+"px";
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
		$('#page').css('width',ww);
	//获取产品和用户名；
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},userdata,
		userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
    //判断数字的正则；
    var numreg=/^\d{1,4}$/,
    	numreg1=/^\d+$|^\d+\.{1}\d{1,10}$/,
    	numreg2=/^\d{1,5}$/;
	//列参数;
    var cols = [
        { title:'使用标志', name:'usfg' ,width:80, align:'center' },
        { title:'价格类型', name:'tpnm' ,width:80, align:'center'},
        { title:'期限', name:'term' ,width:80, align:'right'},
        { title:'货币对名称', name:'exnm' ,width:100, align:'left'},
        { title:'买入价上限', name:'mxmd' ,width:120, align:'right'},
        { title:'买入价', name:'mdmd' ,width:120, align:'right'},
        { title:'买入价下限', name:'mimd' ,width:120, align:'right'},
        { title:'最大波动', name:'mxdp' ,width:100, align:'right'},
        { title:'价格名', name:'tpfg' ,width:80, align:'left'}
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    //下拉列表；
   	 $('.productcalibrate .prod').show();
     $.ajax({
		url:'/fx/pdtCom.do',
		type:'get',
		contentType:'application/json',
		async:false,
		success:function(data){
			var str='';
			if(data.code==00){
				userdata=JSON.parse( data.codeMessage );
				for(var i in userdata){
					str+='<option ptid='+userdata[i].PTID+'>'+userdata[i].PTNM+'</option>'
				}
				$('.prod').html( str );
			}else if(data.code==01){
			}
		}
	});
     $('.prod').change(function(){
    	 getList({
    		 'userKey':userKey,
        	 'prod':$('.prod option:selected').attr('ptid'),
        	 'pageNo':1,
        	 'pageSize':10
	    });
    	 renpage();
     });
   //请求列表数据；
    getList({
    	'userKey':userKey,
    	 'prod':$('.prod option:selected').attr('ptid'),
    	 'pageNo':1,
    	 'pageSize':10
    });
    renpage();
    function getList(obj){
    	$.ajax({
    		url:'/fx/getChkList.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj ),
    		async:false,
    		success:function(data){
    			if(data.code==00){
    				userdata=data.codeMessage;
    				ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
    			}else{
    				ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
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
				horizrailenabled:true
		  });
		 mmg.on('loadSuccess',function(e,data){
			 if( obj.userdata.length>0){
				 $('.box').find('tbody tr').each(function(i,v){
					 $(v).find('td').eq(0).attr('tit',obj.userdata[i].cxfg);
				 });
			 }
		 });
    }
 //修改框弹出
   $('.modify').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){          
          //修改弹出框;+获取当前selected的数据;            
            var exnm=$('.box tr.selected td:eq(3) span').text();
			$.ajax({      															//查询货币对类型，以判断点击“修改”后的弹出框的部分；
				url:'/fx/selectExtp2.do',
				type:'post',
				contentType:'application/json',
				data:exnm,
				async:true,
				success:function(data){
					if(data.code==00){
						userdata=JSON.parse(data.codeMessage);
						if( userdata==1){                                            //判断展示对应的弹出框；
							 dialog.priceModify("productcalibrate",1);
				             $('.productcalibrate .pricecontmain .four',parent.document).show(); 
				             $('.productcalibrate .pricecontmain ul',parent.document).css({"width":"134px"});               //初始化校验参数；
				             
				            checkfn({'url':'ChkParaInit.do','data':{userKey:userKey, exnm:exnm,'prod':$('.prod option:selected').attr('ptid')},'obj':'productcalibrate','product':product});
						}else if( userdata==0){
							dialog.priceModify("productcalibrate",0);
				             $('.productcalibrate .pricecontmain .one',parent.document).show();
				             $('.productcalibrate .pricecontmain ul',parent.document).css({"width":"344px"});
				             checkfn({'url':'MarkInit2.do','data':{userKey:userKey,exnm:exnm,'prod':$('.prod option:selected').attr('ptid')},'obj':'productcalibrate','product':product});  //相当于初始化产品市场源头选择；
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
			dialog.choicedata('请先选择一条数据!','productcalibrate');
		}
	});
 //保存校验；
	$('body',parent.document).on('click','.productcalibrate .four .price_sav',function(){   
		if( $('.productcalibrate .byeprice',parent.document).val()==''){
			$('.productcalibrate .byeprice',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .byeprice',parent.document).parent('div').append('<i class="formtip">买入价不能为空!</i>');
		}else if( !numreg1.test($('.productcalibrate .byeprice',parent.document).val()) ){
			$('.productcalibrate .byeprice',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .byeprice',parent.document).parent('div').append('<i class="formtip">买入价必须是整数或小数!</i>');
		}else{
			$('.productcalibrate .byeprice',parent.document).parent('div').find('.formtip').remove();
		}
		
		if( $('.productcalibrate .cenpricedot',parent.document).val()==''){
			$('.productcalibrate .cenpricedot',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .cenpricedot',parent.document).parent('div').append('<i class="formtip">中间价两次浮动点差不能为空!</i>');
		}else if( !numreg.test($('.productcalibrate .cenpricedot',parent.document).val()) ){
			$('.productcalibrate .cenpricedot',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .cenpricedot',parent.document).parent('div').append('<i class="formtip">中间价两次浮动点差必须正整数!</i>');
		}else{
			$('.productcalibrate .cenpricedot',parent.document).parent('div').find('.formtip').remove();
		}
		if($('.productcalibrate .pricenouse',parent.document).val()==''){
			$('.productcalibrate .pricenouse',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .pricenouse',parent.document).parent('div').append('<i class="formtip">未波动次数不能为空!</i>');
		}else if( !numreg.test($('.productcalibrate .pricenouse',parent.document).val()) ){
			$('.productcalibrate .pricenouse',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .pricenouse',parent.document).parent('div').append('<i class="formtip">未波动次数必须是正整数!</i>');		
		}else{
			$('.productcalibrate .pricenouse',parent.document).parent('div').find('.formtip').remove();
		}
		if($('.productcalibrate .bigshavedot',parent.document).val()==''){
			$('.productcalibrate .bigshavedot',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .bigshavedot',parent.document).parent('div').append('<i class="formtip">最大波动点差不能为空!</i>');
		}else if( !numreg.test($('.productcalibrate .bigshavedot',parent.document).val()) ){
			$('.productcalibrate .bigshavedot',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .bigshavedot',parent.document).parent('div').append('<i class="formtip">最大波动点差必须是正整数!</i>');
		}else{
			$('.productcalibrate .bigshavedot',parent.document).parent('div').find('.formtip').remove();
		}
		
		if($('.productcalibrate .goodnum',parent.document).val()==''){
			$('.productcalibrate .goodnum',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .goodnum',parent.document).parent('div').append('<i class="formtip">合法波动次数不能为空!</i>');
		}else if( !numreg.test($('.productcalibrate .goodnum',parent.document).val()) ){
			$('.productcalibrate .goodnum',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .goodnum',parent.document).parent('div').append('<i class="formtip">合法波动次数必须是正整数!</i>');
		}else{
			$('.productcalibrate .goodnum',parent.document).parent('div').find('.formtip').remove();
		}
		if( $('.productcalibrate .four .formtip',parent.document).length<=0){
			var PriceVo={ userKey:userKey,pdtChk:{
				'mdmd':$('.productcalibrate .byeprice',parent.document).val()*1,
				'mxbp':$('.productcalibrate .cenpricedot',parent.document).val(),
				'mxud':$('.productcalibrate .pricenouse',parent.document).val(),
				'mxdp':$('.productcalibrate .bigshavedot',parent.document).val(),
				'mxct':$('.productcalibrate .goodnum',parent.document).val(),
				'tpfg':$('.box tr.selected td:eq(1) span').text(),
				'cxfg':$('.productcalibrate .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
				'usfg':$('.productcalibrate .pripubdivs input[name="priceradio"]:checked',parent.document).attr('tit'),
				'term':$('.box tr.selected td:eq(2) span').text(),
				'exnm':$('.box tr.selected td:eq(3) span').text()
				},
				'prod':$('.prod option:selected').attr('ptid'),
				exnm:$('.box tr.selected td:eq(3) span').text()
			};
			savefn({url:'/fx/SaveChkPara.do',obj:PriceVo});
		}
	});
	//保存停牌
	$('body',parent.document).on('click','.productcalibrate .five .price_sav',function(){
		var PriceVo={ userKey:userKey,
				'prod':$('.prod option:selected').attr('ptid'),
				pdtStoper:{
			'stid':$('.productcalibrate .suspenid',parent.document).val(),
			'stfg':$('.productcalibrate input[name="priceradio2"]:checked',parent.document).attr('tit'),    //停牌标志；
			'tpfg':$('.box tr.selected td:eq(1) span').text(),
			'term':$('.box tr.selected td:eq(2) span').text(),
			'exnm':$('.box tr.selected td:eq(3) span').text()
			}
		};
		savefn({url:'/fx/SavePdtStop.do',obj:PriceVo});
	});
	//点击点差保存；
	$('body',parent.document).on('click','.productcalibrate .six .price_save',function(){
		if($('.productcalibrate .pricelive',parent.document).val()==''){
			$('.productcalibrate .pricelive',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期不能为空!</i>');
		}else if( !numreg2.test( $('.productcalibrate .pricelive',parent.document).val() ) ){
			$('.productcalibrate .pricelive',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期必须是正整数!</i>');
		}else{
			$('.productcalibrate .pricelive',parent.document).parent('div').find('.formtip').remove();
		}
		
		if( $('.productcalibrate .buyindot',parent.document).val()=='' ){
			$('.productcalibrate .buyindot',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .buyindot',parent.document).parent('div').append('<i class="formtip">总行对分行买入点差不能为空!</i>');
		}else if( !numreg2.test($('.productcalibrate .buyindot',parent.document).val()) ){
			$('.productcalibrate .buyindot',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .buyindot',parent.document).parent('div').append('<i class="formtip">总行对分行买入点差必须是正整数!</i>');
		}else{
			$('.productcalibrate .buyindot',parent.document).parent('div').find('.formtip').remove();
		}
		
		if($('.productcalibrate .bytoutdot',parent.document).val()==''){
			$('.productcalibrate .bytoutdot',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差不能为空!</i>');	
		}else if( !numreg2.test($('.productcalibrate .bytoutdot',parent.document).val()) ){
			$('.productcalibrate .bytoutdot',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差必须是正整数!</i>');		
		}else{
			$('.productcalibrate .bytoutdot',parent.document).parent('div').find('.formtip').remove();
		}
		
		if( $('.productcalibrate .sallhand',parent.document).val()==''){
			$('.productcalibrate .sallhand',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .sallhand',parent.document).parent('div').append('<i class="formtip">总行对分行卖出点差不能为空!</i>');
		}else if( !numreg2.test($('.productcalibrate .sallhand',parent.document).val()) ){
			$('.productcalibrate .sallhand',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .sallhand',parent.document).parent('div').append('<i class="formtip">总行对分行卖出点差必须是正整数!</i>');
		}else{
			$('.productcalibrate .sallhand',parent.document).parent('div').find('.formtip').remove();
		}
		if($('.productcalibrate .sallcust',parent.document).val()==''){
			$('.productcalibrate .sallcust',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差不能为空!</i>');
		}else if( !numreg2.test($('.productcalibrate .sallcust',parent.document).val()) ){
			$('.productcalibrate .sallcust',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差必须是正整数!</i>');
		}else{
			$('.productcalibrate .sallcust',parent.document).parent('div').find('.formtip').remove();
		}
		if( $('.productcalibrate .six .formtip',parent.document).length<=0){
			var PriceVo={ userKey:userKey,pdtPoint:{
				'tpfg':$('.box tr.selected td:eq(1) span').text(),
				'exnm':$('.box tr.selected td:eq(3) span').text(),
				'bhbd':$('.productcalibrate .buyindot',parent.document).val(),
				'bhsd':$('.productcalibrate .sallhand',parent.document).val(),
				'cubd':$('.productcalibrate .bytoutdot',parent.document).val(),
				'cusd':$('.productcalibrate .sallcust',parent.document).val(),
				'prtp':$('.productcalibrate input[name="priceradio4"]:checked',parent.document).attr('tit'),
				'term':$('.box tr.selected td:eq(2) span').text(),
				'qtcy':$('.productcalibrate .pricelive',parent.document).val(),
				'cxfg':$('.productcalibrate .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit')
			 },
			 'prod':$('.prod option:selected').attr('ptid')
			};
			savefn({url:'/fx/SavePoint.do',obj:PriceVo});
		}
	});
	
	//保存产品市场源头选择；
	$('body',parent.document).on('click','.productcalibrate .one .price_sav',function(){
		if($('.productcalibrate .prisoright .marketcontr p',parent.document).length>0){
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
				'cxfg':$('.productcalibrate .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
				'exnm':$('.box tr.selected td:eq(0) span').text(),
				'tpfg':$('.box tr.selected td:eq(1) span').text(),
				'term':$('.box tr.selected td:eq(4) span').text()
				}
			};
			 if( $('.productcalibrate .one .marketcontr p',parent.document).length==1){  //只有一条市场权重设置；
	    		 if( $('.productcalibrate .one .marketcontr p',parent.document).find('span').eq(1).text()==1){
	    			savefn({url:'/fx/SaveMark.do',obj:PriceVo});
	    		 }else{
	    			 dialog.choicedata('市场权重必须是0到1之间的数字，小于1的数字以‘.’开始!','productcalibrat');
	    		 }
	    	 }else{  																	//多条市场权重；
	    		 $('.productcalibrate .one .marketcontr p',parent.document).each(function(i,v){
	    			 if( $(v).find('span').eq(1).text()==''||!/^\.{1}\d+$/.test($(v).find('span').eq(1).text())){
	    				 wrong++;
	    			 }else{
	    				 spantxt=spantxt+$(v).find('span').eq(1).text()*1;
	    			 }
	    		 });
	    		 if( spantxt==1&&wrong==0){
	    			 savefn({url:'/fx/SaveMark.do',obj:PriceVo});
	    		 }else if(spantxt!=1&&wrong==0){
	    			 dialog.choicedata('所有市场权重之和必须是1!','productcalibrate');
	    		 }else{
	    			 dialog.choicedata('市场权重必须是0到1之间的数字，小于1的数字以‘.’开始!','productcalibrat');
	    		 }
	    	 }
		}else{
			dialog.choicedata('没有添加产品市场，不能保存!','productcalibrate');
		}
	});
	
	//保存策略；
	$('body',parent.document).on('click','.productcalibrate .two .price_sav',function(){
		if( $('.productcalibrate .two .marketcontr .tabpbgc',parent.document).length>0){
			var PriceVo={ userKey:userKey,pdtrPara:{
				'gmnm':$('.two .pristrright .marketcontr p',parent.document).text(),
				'cxfg':$('.productcalibrate .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
				'exnm':$('.box tr.selected td:eq(3) span').text(),
				'tpfg':$('.box tr.selected td:eq(1) span').text(),
				'term':$('.box tr.selected td:eq(2) span').text()
				},
				'exnm':$('.box tr.selected td:eq(3) span').text()
			};
			savefn({url:'/fx/SaveGmnm.do',obj:PriceVo});
		}else{
			dialog.choicedata('策略没有修改,不必保存!','productcalibrate');
		} 
	});
	//保存干预；
	$('body',parent.document).on('click','.productcalibrate .three .price_save',function(){
		if($('.productcalibrate .three .nebp',parent.document).val()=='' ){
			$('.productcalibrate .nebp',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .nebp',parent.document).parent('div').append('<i class="formtip">买入价干预点数不能为空!</i>');
		}else if(!numreg.test($('.productcalibrate .three .nebp',parent.document).val())){
			$('.productcalibrate .nebp',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .nebp',parent.document).parent('div').append('<i class="formtip">买入价干预点数必须是不超过4位的正整数!</i>');
		}else{
			$('.productcalibrate .nebp',parent.document).parent('div').find('.formtip').remove();
		}
		if( $('.productcalibrate .three .nesp',parent.document).val()==''){
			$('.productcalibrate .nesp',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .nesp',parent.document).parent('div').append('<i class="formtip">卖出价干预点数不能为空!</i>');
		}else if(!numreg.test($('.productcalibrate .three .nesp',parent.document).val())){
			$('.productcalibrate .nesp',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate .nesp',parent.document).parent('div').append('<i class="formtip">卖出价干预点数必须是不超过4位的正整数!</i>');
		}else{
			$('.productcalibrate .nesp',parent.document).parent('div').find('.formtip').remove();
		}
		if( $('.productcalibrate .three .formtip',parent.document).length<=0&& comptime( $('body #sT',parent.document).val(),$('body #eT',parent.document).val() )==true){			
			var PriceVo={ userKey:userKey,pdtCtrl:{
				'nebp':$('.productcalibrate .three .nebp',parent.document).val(),
				'nesp':$('.productcalibrate .three .nesp',parent.document).val(),
				'bgtm':$('.productcalibrate .three #sT',parent.document).val(),
				'edtm':$('.productcalibrate .three #eT',parent.document).val(),
				'stfg':$('.productcalibrate .three .usestfg input[name="stfg"]:checked',parent.document).attr('tit'),
				'ctid':$('.productcalibrate .three .ctid',parent.document).val(),
				'term':$('.box tr.selected td:eq(2) span').text(),
				'exnm':$('.box tr.selected td:eq(3) span').text(),
				'cxfg':$('.productcalibrate .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit')
				}
			};
			savefn({url:'/fx/SaveCtrl.do',obj:PriceVo});
		}else if($('.productcalibrate .three .formtip',parent.document).length<=0&&comptime( $('body #sT',parent.document).val(),$('body #eT',parent.document).val() )==false){
			dialog.choicedata('截止时间应大于起始时间!','productcalibrate');
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
					dialog.choicedata(data.codeMessage,'productcalibrate');
					if(obj.url=='SaveGmnm.do'){
						$('.productcalibrate .two .currentstra div',parent.document).text( $('.productcalibrate .two .pristrright .tabpbgc',parent.document).text() );
					}
				}else{
					dialog.choicedata(data.codeMessage,'productcalibrate');
				}
			}
		});
		
	}
	//调用弹出框数据的函数；
	function checkfn(obj){                                      //校验函数
		var str='',str1='',chanpin,cxfg;
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
		//公共部分  更改弹出框头部的内容；
		$('.'+obj.obj+' .hbdname span',parent.document).text( $('.box tr.selected td:eq(3) span').text() );
		$('.'+obj.obj+' .productname span',parent.document).text( chanpin );    //chanpin;用来显示产品名称；
		$('.'+obj.obj+' .privetype span',parent.document).text( $('.box tr.selected td:eq(1) span').text() ); //远期即期；
		if( $('.box tr.selected td:eq(0)').attr('tit')=='1'){       //钞汇标志；
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
			exnm=$('.box tr.selected td:eq(3) span').text(),
			PriceVo;
		if(txt=='校验'){
			checkfn({'url':'ChkParaInit.do','data':{userKey:userKey, exnm:exnm,'prod':$('.prod option:selected').attr('ptid')},'obj':'productcalibrate'});
		}else if(txt=='停牌'){
			PriceVo={userKey:userKey,exnm:exnm,stid:'SC99','prod':$('.prod option:selected').attr('ptid')};
			checkfn({'url':'CurStopInit.do','data':PriceVo,'obj':'productcalibrate'} );
			
		}else if(txt=='点差'){
			checkfn({'url':'CurPointInit.do','data':{userKey:userKey,exnm:exnm,'prod':$('.prod option:selected').attr('ptid')},'obj':'productcalibrate'});
		
		}else if(txt=='产品市场源头选择'){
			
			checkfn({'url':'CurPointInit.do','data':{userKey:userKey,exnm:exnm},'obj':'productcalibrate'});
		}else if(txt=='策略'){
			
			checkfn({'url':'GetGmnm.do','data':{userKey:userKey,exnm:exnm},'obj':'productcalibrate'});
		}else if(txt=='干预'){
			
			checkfn({'url':'GetPdtCtrl.do','data':{userKey:userKey,ctid:'CP99',exnm:exnm},'obj':'productcalibrate'});
		}
     $(this).css({"background-color":"#fff"}).siblings().css({"background-color":"#9CBAFF"});
     var index=$(this).data("index");
     $("."+index+"",parent.document).siblings('.tabdiv').hide();
     $("."+index+"",parent.document).show();
       
     //失去焦点判断    校验；
     $('body',parent.document).find('.productcalibrate .four .byeprice').on('blur',function(){ // 买入价；
    	 if( $('.productcalibrate .byeprice',parent.document).val()==''){
    		 $(this).parent('div').find('.formtip').remove();
 			 $('.productcalibrate .byeprice',parent.document).parent('div').append('<i class="formtip">买入价不能为空!</i>');
    	 }else if( !numreg1.test($('.productcalibrate .byeprice',parent.document).val()) ){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate .byeprice',parent.document).parent('div').append('<i class="formtip">买入价必须是正整数或小数!</i>');
			}else{
				$(this).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productcalibrate .four .cenpricedot').on('blur',function(){ 
			if( $('.productcalibrate .cenpricedot',parent.document).val()=='' ){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate .cenpricedot',parent.document).parent('div').append('<i class="formtip">中间价两次浮动点差不能为空!</i>');
			}else if( !numreg.test($('.productcalibrate .cenpricedot',parent.document).val()) ){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate .cenpricedot',parent.document).parent('div').append('<i class="formtip">中间价两次浮动点差必须是正整数!</i>');
			}else{
				$(this).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productcalibrate .four .pricenouse').on('blur',function(){
			if($('.productcalibrate .pricenouse',parent.document).val()==''){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate .pricenouse',parent.document).parent('div').append('<i class="formtip">未波动次数不能为空!</i>');	
			}else if( !numreg.test($('.productcalibrate .pricenouse',parent.document).val()) ){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate .pricenouse',parent.document).parent('div').append('<i class="formtip">未波动次数必须是正整数!</i>');		
			}else{
				$(this).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productcalibrate .four .bigshavedot').on('blur',function(){
			if($('.productcalibrate .bigshavedot',parent.document).val()==''){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate .bigshavedot',parent.document).parent('div').append('<i class="formtip">最大波动点差不能为空!</i>');
			}else if( !numreg.test($('.productcalibrate .bigshavedot',parent.document).val()) ){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate .bigshavedot',parent.document).parent('div').append('<i class="formtip">最大波动点差必须是正整数!</i>');
			}else{
				$(this).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productcalibrate .four .goodnum').on('blur',function(){
			if( $('.productcalibrate .goodnum',parent.document).val()==''){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate .goodnum',parent.document).parent('div').append('<i class="formtip">合法波动次数不能为空!</i>');
			}else if( !numreg.test($('.productcalibrate .goodnum',parent.document).val()) ){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate .goodnum',parent.document).parent('div').append('<i class="formtip">合法波动次数必须是正整数!</i>');
			}else{
				$(this).parent('div').find('.formtip').remove();
			}
		});
		//点差 失焦判断；
		$('body',parent.document).find('.productcalibrate .six .pricelive').on('blur',function(){
			if( $('.productcalibrate .pricelive',parent.document).val()==''){
				$('.productcalibrate .pricelive',parent.document).parent('div').find('.formtip').remove();
				$('.productcalibrate .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期不能为空!</i>');
			}else if( !numreg2.test( $('.productcalibrate .pricelive',parent.document).val() ) ){
				$('.productcalibrate .pricelive',parent.document).parent('div').find('.formtip').remove();
				$('.productcalibrate .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期必须是正整数!</i>');
			}else{
				$('.productcalibrate .pricelive',parent.document).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productcalibrate .six .buyindot').on('blur',function(){ 
			if( $('.productcalibrate .buyindot',parent.document).val()==''){
				$('.productcalibrate .buyindot',parent.document).parent('div').find('.formtip').remove();
				$('.productcalibrate .buyindot',parent.document).parent('div').append('<i class="formtip">总行对分行买入点差不能为空!</i>');
			}else if( !numreg2.test($('.productcalibrate .buyindot',parent.document).val()) ){
				$('.productcalibrate .buyindot',parent.document).parent('div').find('.formtip').remove();
				$('.productcalibrate .buyindot',parent.document).parent('div').append('<i class="formtip">总行对分行买入点差必须是正整数!</i>');
			}else{
				$('.productcalibrate .buyindot',parent.document).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productcalibrate .six .bytoutdot').on('blur',function(){ 
			if( $('.productcalibrate .bytoutdot',parent.document).val()=='' ){
				$('.productcalibrate .bytoutdot',parent.document).parent('div').find('.formtip').remove();
				$('.productcalibrate .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差不能为空!</i>');		
			}else if( !numreg2.test($('.productcalibrate .bytoutdot',parent.document).val()) ){
				$('.productcalibrate .bytoutdot',parent.document).parent('div').find('.formtip').remove();
				$('.productcalibrate .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差必须是正整数!</i>');		
			}else{
				$('.productcalibrate .bytoutdot',parent.document).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productcalibrate .six .sallhand').on('blur',function(){ 
			if( $('.productcalibrate .sallhand',parent.document).val()=='' ){
				$('.productcalibrate .sallhand',parent.document).parent('div').find('.formtip').remove();
				$('.productcalibrate .sallhand',parent.document).parent('div').append('<i class="formtip">总行对分行卖出点差不能为空!</i>');
				
			}else if( !numreg2.test($('.productcalibrate .sallhand',parent.document).val()) ){
				$('.productcalibrate .sallhand',parent.document).parent('div').find('.formtip').remove();
				$('.productcalibrate .sallhand',parent.document).parent('div').append('<i class="formtip">总行对分行卖出点差必须是正整数!</i>');
			}else{
				$('.productcalibrate .sallhand',parent.document).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productcalibrate .six .sallcust').on('blur',function(){ 
			if($('.productcalibrate .sallcust',parent.document).val()==''){
				$('.productcalibrate .sallcust',parent.document).parent('div').find('.formtip').remove();
				$('.productcalibrate .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差不能为空!</i>');
			}else if( !numreg2.test($('.productcalibrate .sallcust',parent.document).val()) ){
				$('.productcalibrate .sallcust',parent.document).parent('div').find('.formtip').remove();
				$('.productcalibrate .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差必须是正整数!</i>');
			}else{
				$('.productcalibrate .sallcust',parent.document).parent('div').find('.formtip').remove();
			}
		});
		//干预  失焦判断；
		$('body',parent.document).find('.productcalibrate .three .nebp').on('blur',function(){ 
			if($('.productcalibrate .three .nebp',parent.document).val()==''){
				$('.productcalibrate .nebp',parent.document).parent('div').find('.formtip').remove();
				$('.productcalibrate .nebp',parent.document).parent('div').append('<i class="formtip">买入价干预点数不能为空!</i>');
			}else if(!numreg.test($('.productcalibrate .three .nebp',parent.document).val())){
				$('.productcalibrate .nebp',parent.document).parent('div').find('.formtip').remove();
				$('.productcalibrate .nebp',parent.document).parent('div').append('<i class="formtip">买入价干预点数必须是不超过4位的正整数!</i>');
			}else{
				$('.productcalibrate .nebp',parent.document).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productcalibrate .three .nesp').on('blur',function(){ 
			if($('.productcalibrate .three .nesp',parent.document).val()==''){
				$('.productcalibrate .nesp',parent.document).parent('div').find('.formtip').remove();
				$('.productcalibrate .nesp',parent.document).parent('div').append('<i class="formtip">卖出价干预点数不能为空!</i>');
			}else if(!numreg.test($('.productcalibrate .three .nesp',parent.document).val())){
				$('.productcalibrate .nesp',parent.document).parent('div').find('.formtip').remove();
				$('.productcalibrate .nesp',parent.document).parent('div').append('<i class="formtip">卖出价干预点数必须是不超过4位的正整数!</i>');
			}else{
				$('.productcalibrate .nesp',parent.document).parent('div').find('.formtip').remove();
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
    $('body',parent.document).on('click','.productcalibrate .marketsource p ',function(){
		 $(this).addClass('tabpbgc').siblings().removeClass('tabpbgc');
        
    });
    //点击 添加  产品市场源头；
    $('body',parent.document).on('click','.productcalibrate .prisoleft .priceadd ',function(){
		 var txt=$('.productcalibrate .sourcechoice .tabpbgc',parent.document).text(),
		 	 valu=$('.productcalibrate .sourcechoice .tabpbgc',parent.document).attr('value'),
		 	 brr=[];
		$('.productcalibrate .marktset p',parent.document).each(function(i,v){
			brr.push( $(v).attr('mkid') );
		});
		if( brr.indexOf(valu)==-1){
				$('.productcalibrate .marktset .marketcont ',parent.document).append('<p mkid='+valu+' mklv='+(brr.length+1)+'><span>'+txt+'</span><span contenteditable="true">0</input></p>');
		}else{
			 dialog.choicedata('该市场已经存在!','productcalibrate');
		}
    });
   /* //点击全部启用、全部停用；
    $('.calibrate_open').click(function(){
    	useornouse( $(this).text() );
    });
    $('.calibrate_stop').click(function(){
    	useornouse( $(this).text() );
    });
    //点击启用或停用函数；
    function useornouse(obj){
    	$.ajax({
    		url:'/fx/updateChk.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify({ userKey:userKey,usfg:obj}),
    		async:true,
    		success:function(data){
    			if(data.code==00){
    				dialog.choicedata(data.codeMessage,'productcalibrate');
    				 getList({
				    	'userKey':userKey,
				    	 'prod':$('.prod option:selected').attr('ptid')
				    });
    			}else{
    				dialog.choicedata(data.codeMessage,'productcalibrate');
    			}
    		}
    	});
    }*/
	$('body',parent.document).on('click','.productcalibrate .prisoleft .pricedelete ',function(){
		  $('.productcalibrate .marktset .tabpbgc',parent.document).remove();
    });
     //上移下移的实现
    $('body',parent.document).on('click','.productcalibrate .prisoright .priceadd ',function(){
    	  var current=$('.productcalibrate .marktset .marketcont .tabpbgc',parent.document) 
		 if(current.prev().html() != undefined){ 
           var obj = current.clone(true); 
           current.prev().before(obj); 
           current.remove(); 
       }
	 });
	 $('body',parent.document).on('click','.productcalibrate .prisoright .pricedelete ',function(){
    	  var current=$('.productcalibrate .marktset .marketcont .tabpbgc',parent.document) 
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
	 $('body',parent.document).on('click','.productcalibrate .two .pristrleft .marketcontr p',function(){
		 $('.productcalibrate .two .pristrright .marketcontr',parent.document).html( $(this).clone() );
	 });
    
	$('body',parent.document).on('click','.productcalibrate .sure,.productcalibrat .sure',function(){
		//关闭选择一条数据;
	    $(this).closest('.zhezhao').remove();
	}); 
	//修改框的取消  关闭修改框 
	$('body',parent.document).on('click','.productcalibrate .price_cancel,.productcalibrate .price_close',function(){
		$(this).closest('.zhezhao').remove();
		$('.box tr.selected').removeClass('selected');
	});
	//全部启用，全部停用；；
    $('.calibrate_open').click(function(){
    		useornouse( $(this).text() );
    });
    $('.calibrate_stop').click(function(){
    	useornouse( $(this).text() );
    });
    //点击启用或停用函数；
    function useornouse(obj){
    	$.ajax({
    		url:'/fx/updateChk.do',
    		type:'post',
    		contentType:'application/json',
    		async:true,
    		data:JSON.stringify({
    			userKey:userKey,
    			prod:$('.prod option:selected').attr('ptid'),
    			usfg:obj
    		}),
    		success:function(data){
    			if(data.code==00){
    				dialog.choicedata(data.codeMessage,'productcalibrate');
    				 getList({
    					 'userKey':userKey,
    			    	 'prod':$('.prod option:selected').attr('ptid'),
    			    	 'pageNo':1,
    			    	 'pageSize':10
				    });
    				 renpage();
    			}else{
    				dialog.choicedata(data.codeMessage,'productcalibrate');
    			}
    		}
    	});
    }
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
    		    		getList({
    		    	    	'userKey':userKey,
		    	    	    'prod':$('.prod option:selected').attr('ptid'),
		    	    	    'pageNo':obj.curr,
 		    	    	    'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1
    		    	    });
    		    	}	
    		    }
    		  });
    	});
    }
});