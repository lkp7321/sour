/*
 * 外汇点差查询
 */
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
		WdatePicker:'./My97DatePicker/WdatePicker',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','WdatePicker','dialog'],function($,mmGrid,niceScroll,WdatePicker,dialog){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
		$('.boxtop').css('width',ww);
		$('.boxtop').css('height',wh);
	var str="",
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		numreg2=/^\d{1,5}$/;
	//列参数;
    var cols = [
            { title:'价格类型', name:'tpfg',width:60,align:'center' },
            { title:'期限', name:'term',width:60, align:'right'},
            { title:'货币对名称', name:'exnm',width:60, align:'left'},
            { title:'报价模式', name:'prtp',width:60,align:'center'},
            { title:'总行对客户买入点差', name:'cubd',width:60, align:'right'},
            { title:'总行对客户卖出点差', name:'cusd',width:60, align:'right'},
            { title:'价格生命周期(秒)', name:'qtcy',width:60,align:'right'},
            { title:'更新频率(毫秒)', name:'uphz',width:60, align:'right'}
    ];
    //请求下拉框；
    $.ajax({
		url:'/fx/price/getSelItems.do',
		type:'post',
		contentType:'application/json',
		data:$('.acudeal option:selected').attr('value'),
		async:false,
		success:function(data){
			var str='';
			if(data.code==01){
				userdata=JSON.parse( data.codeMessage );
				for(var i in userdata){
					str+='<option ptid='+userdata[i].PTID+'>'+userdata[i].PTNM+'</option>'
				}
				$('.P071').html(str);
			}else if(data.code==00){
				
			}
		}
	});
    $('.P071').change(function(){
    	 getlist({
	    	'userKey':userkey,
	    	'prod':$('.acudeal option:selected').attr('value'),
	    	'ptid1':$('.P071 option:selected').attr('ptid')
	    });
    });
    getlist({
    	'userKey':userkey,
    	'prod':$('.acudeal option:selected').attr('value'),
    	'ptid1':$('.P071 option:selected').attr('ptid')
    });
    function getlist( obj ){
		$.ajax({
			url:'/fx/price/getAllpdtprice.do',
			type:'post',
			contentType:'application/json',
			data:JSON.stringify( obj ),
			async:true,
			success:function(data){
				if(data.code==01){
					userdata=JSON.parse( data.codeMessage );
					ren({'cols':cols,'wh':wh,'userdata':userdata});
				}else if(data.code==00){
					ren({'cols':cols,'wh':wh,'userdata':''});
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
				 $(v).find('td').eq('0').attr('cxfg',obj.userdata[i].cxfg);
				 $(v).find('td').eq('0').attr('qtcy',obj.userdata[i].qtcy);
			  });
		  });
	  }
    }
    //点击生效、修改；
    $('.liveon').click(function(){
		$.ajax({
			url:'/fx/price/sendSoketB1.do',
			type:'post',
			async:true,
//			success:function(data){
//				if(data.code==01){
//					dialog.choicedata(data.codeMessage,'foreignexc');
//				}else if(data.code==00){
//					dialog.choicedata(data.codeMessage,'foreignexc');
//				}
//			}
		});
    });
    $('.changeon').click(function(){
    	if( $('.box tbody tr').hasClass('selected') ){
    		dialog.priceModify('foreignexc');
    		$('.foreignexc .six',parent.document).show();
    		
    		$('.foreignexc .hbdname span',parent.document).text( $('.box tr.selected td:eq(2) span').text() );
    		$('.foreignexc .productname span',parent.document).text( $('.acudeal option:selected').text() );    //chanpin;用来显示产品名称；
    		$('.foreignexc .privetype span',parent.document).text( $('.box tr.selected td:eq(0) span').text() ); //远期即期；
    		$('.dotsort',parent.document).text( $('.P071 option:selected').text() );
    		
    		if( $('.box tr.selected td:eq(2) span').text()=='钞'){       //钞汇标志；
    			$('.foreignexc .priceradi0',parent.document).prop('checked','checked');
    		}else{
    			$('.foreignexc .priceradi1',parent.document).prop('checked','checked');
    		}
    		
    		$('.foreignexc .pricelive',parent.document).val( $('.box tr.selected td:eq(6) span').text() );
			$('.foreignexc .sallcust',parent.document).val(  $('.box tr.selected td:eq(5) span').text());
			
			$('.foreignexc .bytoutdot',parent.document).val( $('.box tr.selected td:eq(4) span').text() );
		
			$('.foreignexc .refretime',parent.document).val( $('.box tr.selected td:eq(7) span').text() );
			
			if( $('.box tr.selected td:eq(3) span').text()=='双边价'){ //双边价；
				$('.foreignexc .priceradio4',parent.document).prop('checked','checked');
			}else{  			//中间价
				$('.foreignexc .priceradio5',parent.document).prop('checked','checked');
			}		
    	}else{
    		dialog.choicedata('请先选择一条数据!','foreignexc');
    	}
    });
    //保存；
$('body',parent.document).on('click','.foreignexc .six .price_save',function(){
		
		if($('.foreignexc .pricelive',parent.document).val()==''){
			$('.foreignexc .pricelive',parent.document).parent('div').find('.formtip').remove();
			$('.foreignexc .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期不能为空!</i>');
		}else if( !numreg2.test( $('.foreignexc .pricelive',parent.document).val() ) ){
			$('.foreignexc .pricelive',parent.document).parent('div').find('.formtip').remove();
			$('.foreignexc .pricelive',parent.document).parent('div').append('<i class="formtip">价格生命周期必须是正整数!</i>');
		}else{
			$('.foreignexc .pricelive',parent.document).parent('div').find('.formtip').remove();
		}
	
		if($('.foreignexc .bytoutdot',parent.document).val()==''){
			$('.foreignexc .bytoutdot',parent.document).parent('div').find('.formtip').remove();
			$('.foreignexc .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差不能为空!</i>');	
		}else if( !numreg2.test($('.foreignexc .bytoutdot',parent.document).val()) ){
			$('.foreignexc .bytoutdot',parent.document).parent('div').find('.formtip').remove();
			$('.foreignexc .bytoutdot',parent.document).parent('div').append('<i class="formtip">总行对客户买入点差必须是正整数!</i>');		
		}else{
			$('.foreignexc .bytoutdot',parent.document).parent('div').find('.formtip').remove();
		}
		
		if($('.foreignexc .sallcust',parent.document).val()==''){
			$('.foreignexc .sallcust',parent.document).parent('div').find('.formtip').remove();
			$('.foreignexc .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差不能为空!</i>');
		}else if( !numreg2.test($('.foreignexc .sallcust',parent.document).val()) ){
			$('.foreignexc .sallcust',parent.document).parent('div').find('.formtip').remove();
			$('.foreignexc .sallcust',parent.document).parent('div').append('<i class="formtip">总行对客户卖出点差必须是正整数!</i>');
		}else{
			$('.foreignexc .sallcust',parent.document).parent('div').find('.formtip').remove();
		}
		
		if($('.foreignexc .refretime',parent.document).val()==''){
			$('.foreignexc .refretime',parent.document).parent('div').find('.formtip').remove();
			$('.foreignexc .refretime',parent.document).parent('div').append('<i class="formtip">更新频率不能为空!</i>');
		}else if( !numreg2.test($('.foreignexc .refretime',parent.document).val()) ){
			$('.foreignexc .refretime',parent.document).parent('div').find('.formtip').remove();
			$('.foreignexc .refretime',parent.document).parent('div').append('<i class="formtip">更新频率必须是正整数!</i>');
		}else{
			$('.foreignexc .refretime',parent.document).parent('div').find('.formtip').remove();
		}
		
		if( $('.foreignexc .six .formtip',parent.document).length<=0){
			var priceParaVo={
				userKey:userkey,
				prod:$('.acudeal option:selected').attr('value'),//下拉框1
				pdtPoint:{						
					'bhbd':"0",
					'bhsd':"0",
					'cubd':$('.foreignexc .bytoutdot',parent.document).val(),
					'cusd':$('.foreignexc .sallcust',parent.document).val(),
					'prtp':$('.foreignexc input[name="priceradio4"]:checked',parent.document).attr('tit'),
					'qtcy':$('.foreignexc .pricelive',parent.document).val(),
					'uphz':$('.foreignexc .refretime',parent.document).val(),
					'ptid':$('.dotsort',parent.document).text(),
					'exnm':$('.box tr.selected td:eq(2) span').text()
				}
			}
			savefn({url:'/fx/price/upPricepara.do',obj:priceParaVo});
		 };
	});
	function savefn(obj){
		$.ajax({
			url:obj.url,
			type:'post',
			contentType:'application/json',
			data:JSON.stringify(obj.obj),
			async:true,
			success:function(data){
				$('.foreignexc .price_save',parent.document).closest('.zhezhao').remove();
				if(data.code==01){
					dialog.choicedata(data.codeMessage,'foreignexc');
					getlist({
				    	'userKey':userkey,
				    	'prod':$('.acudeal option:selected').attr('value'),
				    	'ptid1':$('.P071 option:selected').attr('ptid')
				    });
				}else{
					dialog.choicedata(data.codeMessage,'foreignexc');
				}
			}
		});
		
	}
    $('body',parent.document).on('click','.foreignexc .sure,.foreignexc .close,.foreignexc .price_cancel',function(){
		$(this).closest('.zhezhao').remove();
	});
})