/*账户交易-产品校验参数&&报价平台-账户交易-产品校验参数*/
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
	//获取产品和用户名；
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		userdata,
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey
    //判断数字的正则；
    var numreg=/^\d{1,4}$/,
    	numreg1=/^\d+$|^\d+\.{1}\d{1,10}$/,
    	numreg2=/^\d{1,5}$/;
    if( product=='P999'){
   	 $('.productcalibrate .prod').show();
   	 $('.productcalibrate .head .txt').text('账户交易>产品校验参数');
   }else{
   	$('.productcalibrate .head .txt').text('价格管理>产品校验参数');
   }
	//列参数;
    var cols = [
        { title:'使用标志', name:'usfg' ,width:80, align:'center' },
        { title:'价格类型', name:'tpnm' ,width:80, align:'center'},
        { title:'期限', name:'term' ,width:80, align:'right'},
        { title:'货币对名称', name:'exnm' ,width:100, align:'left'},
        { title:'买入价上限', name:'mxmd' ,width:90, align:'right'},
        { title:'买入价', name:'mdmd' ,width:100, align:'right'},
        { title:'买入价下限', name:'mimd' ,width:100, align:'right'},
        { title:'最大波动', name:'mxdp' ,width:100, align:'right'}
    ];
   
   //请求列表数据；
    getList({
    	userKey:userkey,
    	prod:$('.prod option:selected').attr('value')//下拉框
    });
    function getList( obj ){
    	$.ajax({
    		url:'/fx/price/getAllPdtChkpara.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify( obj ),
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				userdata=JSON.parse( data.codeMessage );
    				ren({'cols':cols,'wh':wh,'userdata':userdata});
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
			 if( obj.userdata.length>0 ){
				 $('.box').find('tbody tr').each(function(i,v){
					 $(v).find('td').eq(0).attr('tit',obj.userdata[i].cxfg);
					 $(v).find('td').eq(0).attr('mxct',obj.userdata[i].mxct);
					 $(v).find('td').eq(0).attr('mxbp',obj.userdata[i].mxbp);
					 $(v).find('td').eq(0).attr('mxud',obj.userdata[i].mxud);
					 
				 });
			 }
		 });
    }
	//修改框弹出
  /* $('.modify').click(function(){
		if( $('.box tbody tr').hasClass('selected') ){          
          //修改弹出框;+获取当前selected的数据;            
            dialog.priceModify("productcalibrate1");
            $('.productcalibrate1 .pricecontmain .four',parent.document).show(); 
            
            $('.productcalibrate1 .hbdname span',parent.document).text( $('.box tr.selected td:eq(3) span').text() );
    		$('.productcalibrate1 .productname span',parent.document).text(  $('.prod option:selected').text() );   
    		$('.productcalibrate1 .privetype span',parent.document).text( $('.box tr.selected td:eq(1) span').text() ); //远期即期；
    		
    		if( $('.box tr.selected td:eq(0)').attr('tit')=='1'){       //钞汇标志；
    			$('.productcalibrate1 .priceradi1',parent.document).prop('checked','checked');
    		}else{
    			$('.productcalibrate1 .priceradi0',parent.document).prop('checked','checked');
    		}
    		
    		$('.productcalibrate1 .byeprice',parent.document).val(  $('.box tr.selected td:eq(4) span').text() );
    		
			$('.productcalibrate1 .cenpricedot',parent.document).val(  $('.box tr.selected td:eq(0)').attr('mxbp') );
			$('.productcalibrate1 .pricenouse',parent.document).val(  $('.box tr.selected td:eq(0)').attr('mxud') );
			$('.productcalibrate1 .goodnum',parent.document).val(  $('.box tr.selected td:eq(0)').attr('mxct') );
			
			$('.productcalibrate1 .bigshavedot',parent.document).val(  $('.box tr.selected td:eq(7) span').text() );
			
		
			if ( $('.box tr.selected td:eq(0) span').text()=='启用' ){     //使用标志；
				$('.productcalibrate1 .priceradio0',parent.document).prop('checked','checked');
			}else{
				$('.productcalibrate1 .priceradio1',parent.document).prop('checked','checked');
			}
			
		}else{
			dialog.choicedata('请先选择一条数据!','productcalibrate1');
		}
	});*/
   
   //保存校验；
	/*$('body',parent.document).on('click','.productcalibrate1 .four .price_sav',function(){   
		if( $('.productcalibrate1 .byeprice',parent.document).val()==''){
			$('.productcalibrate1 .byeprice',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate1 .byeprice',parent.document).parent('div').append('<i class="formtip">买入价不能为空!</i>');
		}else if( !numreg1.test($('.productcalibrate1 .byeprice',parent.document).val()) ){
			$('.productcalibrate1 .byeprice',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate1 .byeprice',parent.document).parent('div').append('<i class="formtip">买入价必须是整数或小数!</i>');
		}else{
			$('.productcalibrate1 .byeprice',parent.document).parent('div').find('.formtip').remove();
		}
		
		if( $('.productcalibrate1 .cenpricedot',parent.document).val()==''){
			$('.productcalibrate1 .cenpricedot',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate1 .cenpricedot',parent.document).parent('div').append('<i class="formtip">中间价两次浮动点差不能为空!</i>');
		}else if( !numreg.test($('.productcalibrate1 .cenpricedot',parent.document).val()) ){
			$('.productcalibrate1 .cenpricedot',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate1 .cenpricedot',parent.document).parent('div').append('<i class="formtip">中间价两次浮动点差必须正整数!</i>');
		}else{
			$('.productcalibrate1 .cenpricedot',parent.document).parent('div').find('.formtip').remove();
		}
		if($('.productcalibrate1 .pricenouse',parent.document).val()==''){
			$('.productcalibrate1 .pricenouse',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate1 .pricenouse',parent.document).parent('div').append('<i class="formtip">未波动次数不能为空!</i>');
		}else if( !numreg.test($('.productcalibrate1 .pricenouse',parent.document).val()) ){
			$('.productcalibrate1 .pricenouse',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate1 .pricenouse',parent.document).parent('div').append('<i class="formtip">未波动次数必须是正整数!</i>');		
		}else{
			$('.productcalibrate1 .pricenouse',parent.document).parent('div').find('.formtip').remove();
		}
		if($('.productcalibrate1 .bigshavedot',parent.document).val()==''){
			$('.productcalibrate1 .bigshavedot',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate1 .bigshavedot',parent.document).parent('div').append('<i class="formtip">最大波动点差不能为空!</i>');
		}else if( !numreg.test($('.productcalibrate1 .bigshavedot',parent.document).val()) ){
			$('.productcalibrate1 .bigshavedot',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate1 .bigshavedot',parent.document).parent('div').append('<i class="formtip">最大波动点差必须是正整数!</i>');
		}else{
			$('.productcalibrate1 .bigshavedot',parent.document).parent('div').find('.formtip').remove();
		}
		
		if($('.productcalibrate1 .goodnum',parent.document).val()==''){
			$('.productcalibrate1 .goodnum',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate1 .goodnum',parent.document).parent('div').append('<i class="formtip">合法波动次数不能为空!</i>');
		}else if( !numreg.test($('.productcalibrate1 .goodnum',parent.document).val()) ){
			$('.productcalibrate1 .goodnum',parent.document).parent('div').find('.formtip').remove();
			$('.productcalibrate1 .goodnum',parent.document).parent('div').append('<i class="formtip">合法波动次数必须是正整数!</i>');
		}else{
			$('.productcalibrate1 .goodnum',parent.document).parent('div').find('.formtip').remove();
		}
		if( $('.productcalibrate1 .four .formtip',parent.document).length<=0){
			var PriceVo={ logUser:user,pdtChk:{
				'mdmd':$('.productcalibrate1 .byeprice',parent.document).val()*1,
				'mxbp':$('.productcalibrate1 .cenpricedot',parent.document).val(),
				'mxud':$('.productcalibrate1 .pricenouse',parent.document).val(),
				'mxdp':$('.productcalibrate1 .bigshavedot',parent.document).val(),
				'mxct':$('.productcalibrate1 .goodnum',parent.document).val(),
				'tpfg':$('.box tr.selected td:eq(1) span').text(),
				'cxfg':$('.productcalibrate1 .priceconttop input[name="priceradi"]:checked',parent.document).attr('tit'),
				'usfg':$('.productcalibrate1 .pripubdivs input[name="priceradio"]:checked',parent.document).attr('tit'),
				'term':$('.box tr.selected td:eq(2) span').text(),
				'exnm':$('.box tr.selected td:eq(3) span').text()
				},
				exnm:$('.box tr.selected td:eq(3) span').text()
			};
			savefn({url:'/fx/SaveChkPara.do',obj:PriceVo});
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
					dialog.choicedata(data.codeMessage,'productcalibrate1');
					if(obj.url=='SaveGmnm.do'){
						$('.productcalibrate1 .two .currentstra div',parent.document).text( $('.productcalibrate1 .two .pristrright .tabpbgc',parent.document).text() );
					}
				}else{
					dialog.choicedata(data.codeMessage,'productcalibrate1');
				}
			}
		});
		
	}*/
	
	//修改框的tab切换
	/*$('body',parent.document).on('click','.pricecontmain li',function(){         
        var txt=$(this).text(),
			exnm=$('.box tr.selected td:eq(3) span').text(),
			PriceVo;
		if(txt=='校验'){
			checkfn({'url':'ChkParaInit.do','data':{prod:product, exnm:exnm},'obj':'productcalibrate1'});
		}else if(txt=='停牌'){
			PriceVo={prod:product,exnm:exnm,stid:'SC99'};
			checkfn({'url':'CurStopInit.do','data':PriceVo,'obj':'productcalibrate1'} );
			
		}else if(txt=='点差'){
			checkfn({'url':'CurPointInit.do','data':{prod:product,exnm:exnm},'obj':'productcalibrate1'});
		
		}else if(txt=='产品市场源头选择'){
			
			checkfn({'url':'CurPointInit.do','data':{logUser:{usnm:usnm,prod:product},exnm:exnm},'obj':'productcalibrate1'});
		}else if(txt=='策略'){
			
			checkfn({'url':'GetGmnm.do','data':{prod:product,exnm:exnm},'obj':'productcalibrate1'});
		}else if(txt=='干预'){
			
			checkfn({'url':'GetPdtCtrl.do','data':{prod:product,ctid:'CP99',exnm:exnm},'obj':'productcalibrate1'});
		}
     $(this).css({"background-color":"#fff"}).siblings().css({"background-color":"#9CBAFF"});
     var index=$(this).data("index");
     $("."+index+"",parent.document).siblings('.tabdiv').hide();
     $("."+index+"",parent.document).show();
*/       
     //失去焦点判断    校验；
     /*$('body',parent.document).find('.productcalibrate1 .four .byeprice').on('blur',function(){ // 买入价；
    	 if( $('.productcalibrate1 .byeprice',parent.document).val()==''){
    		 $(this).parent('div').find('.formtip').remove();
 			 $('.productcalibrate1 .byeprice',parent.document).parent('div').append('<i class="formtip">买入价不能为空!</i>');
    	 }else if( !numreg1.test($('.productcalibrate1 .byeprice',parent.document).val()) ){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate1 .byeprice',parent.document).parent('div').append('<i class="formtip">买入价必须是正整数或小数!</i>');
			}else{
				$(this).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productcalibrate1 .four .cenpricedot').on('blur',function(){ 
			if( $('.productcalibrate1 .cenpricedot',parent.document).val()=='' ){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate1 .cenpricedot',parent.document).parent('div').append('<i class="formtip">中间价两次浮动点差不能为空!</i>');
			}else if( !numreg.test($('.productcalibrate1 .cenpricedot',parent.document).val()) ){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate1 .cenpricedot',parent.document).parent('div').append('<i class="formtip">中间价两次浮动点差必须是正整数!</i>');
			}else{
				$(this).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productcalibrate1 .four .pricenouse').on('blur',function(){
			if($('.productcalibrate1 .pricenouse',parent.document).val()==''){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate1 .pricenouse',parent.document).parent('div').append('<i class="formtip">未波动次数不能为空!</i>');	
			}else if( !numreg.test($('.productcalibrate1 .pricenouse',parent.document).val()) ){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate1 .pricenouse',parent.document).parent('div').append('<i class="formtip">未波动次数必须是正整数!</i>');		
			}else{
				$(this).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productcalibrate1 .four .bigshavedot').on('blur',function(){
			if($('.productcalibrate1 .bigshavedot',parent.document).val()==''){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate1 .bigshavedot',parent.document).parent('div').append('<i class="formtip">最大波动点差不能为空!</i>');
			}else if( !numreg.test($('.productcalibrate1 .bigshavedot',parent.document).val()) ){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate1 .bigshavedot',parent.document).parent('div').append('<i class="formtip">最大波动点差必须是正整数!</i>');
			}else{
				$(this).parent('div').find('.formtip').remove();
			}
		});
		$('body',parent.document).find('.productcalibrate1 .four .goodnum').on('blur',function(){
			if( $('.productcalibrate1 .goodnum',parent.document).val()==''){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate1 .goodnum',parent.document).parent('div').append('<i class="formtip">合法波动次数不能为空!</i>');
			}else if( !numreg.test($('.productcalibrate1 .goodnum',parent.document).val()) ){
				$(this).parent('div').find('.formtip').remove();
				$('.productcalibrate1 .goodnum',parent.document).parent('div').append('<i class="formtip">合法波动次数必须是正整数!</i>');
			}else{
				$(this).parent('div').find('.formtip').remove();
			}
		});
		
    });
	*/
	
    //点击全部启用、全部停用；
    $('.calibrate_open').click(function(){
    	//if( product=='P999'){
    		useornouse( $(this).text() );
      // }
    });
    $('.calibrate_stop').click(function(){
    	//if( product=='P999'){
    		useornouse( $(this).text() );
       //}
    });
    //点击启用或停用函数；
    function useornouse(obj){
    	var url;
    	if(obj=='全部启用'){
    		url='price/startAll.do';
    	}else{
    		url='price/closeAll.do';
    	}
    	$.ajax({
    		url:url,
    		type:'post',
    		contentType:'application/json',
    		async:true,
    		data:JSON.stringify({
    			userKey:userkey,
    			prod:$('.prod option:selected').attr('value')
    		}),
    		success:function(data){
    			if(data.code==01){
    				dialog.choicedata(data.codeMessage,'productcalibrate1');
    				getList({
    			    	userKey:userkey,
    			    	prod:$('.prod option:selected').attr('value')//下拉框
    			    });
    			}else{
    				dialog.choicedata(data.codeMessage,'productcalibrate1');
    			}
    		}
    	});
    }
    //生效；
    $('.liveon').click(function(){
    	//if( product=='P999'){
    		$.ajax({
        		url:'/fx/price/sendSoketB.do',
        		type:'post',
        		async:true,
        		success:function(data){
        			if(data.code==00){
        				dialog.choicedata(data.codeMessage,'productcalibrate1');
        			}else{
        				dialog.choicedata(data.codeMessage,'productcalibrate1');
        			}
        		}
        	});
       //}
    });
	//修改框的取消  关闭修改框 
	$('body',parent.document).on('click','.productcalibrate1 .price_cancel,.productcalibrate1 .price_close,.productcalibrate1 .sure',function(){
		$(this).closest('.zhezhao').remove();
	}); 
});