/* 实盘-分行价格监控*/
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
		WdatePicker:'./My97DatePicker/WdatePicker'
	}
});
require(['jquery','mmGrid','niceScroll','dialog','WdatePicker'],function($,mmGrid,niceScroll,dialog,WdatePicker){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
	var product=sessionStorage.getItem('product'),
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		tit=$('title').text();
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);

	if(tit=='产品管理-分行价格监控'){
		$.ajax({
			url:'/fx/getBrnchCom.do',
			type:'post',
			contentType:'application/json',
			async:true,
			success:function(data){
				if(data.code==01){
					var str='';
					userdata=data.codeMessage ;
					for( var i in userdata){
						str+='<option ptid='+userdata[i].PTID+'>'+userdata[i].PTNM+'</option>'
					}
					$('.prodsel').html( str );
					getlist1( $('.prodsel option:selected').attr('ptid') );
				}else if(data.code==00){

				}
			}
		});
	}else if( tit=='分行价格监控' ){
		getlist1( product );
		
	}else if( tit=='账户交易-价格监控' ){		//账户交易价格监控
		getlist1( 'P007' );
	}
	$('.prodsel').change(function(){
		getlist1( $('.prodsel option:selected').attr('ptid') );
	});
	//列参数
    var cols = [
        { title:'货币对名称', name:'exnm' ,width:100, align:'left' },
        { title:'买入价', name:'neby' ,width:100, align:'right'},
        { title:'中间价', name:'nemd' ,width:100, align:'right'},
        { title:'卖出价', name:'nesl' ,width:100, align:'right'},
        { title:'更新时间', name:'mdtm' ,width:100, align:'right'},
        { title:'报价状态', name:'stfg' ,width:100, align:'center'},
        { title:'报价来源', name:'mknm' ,width:80, align:'left'},
        { title:'错误码', name:'ercd' ,width:100, align:'center'},
        { title:'交易标志', name:'trfg' ,width:80, align:'center'}
    ];
    var cols1=[
         { title:'货币对名称', name:'exnm' ,width:100, align:'left' },
         { title:'买入价', name:'neby' ,width:100, align:'right'},
         { title:'中间价', name:'nemd' ,width:100, align:'right'},
         { title:'卖出价', name:'nesl' ,width:100, align:'right'},
         { title:'更新时间', name:'mdtm' ,width:100, align:'right'},
         { title:'报价状态', name:'stfg' ,width:100, align:'center'},
         { title:'报价来源', name:'mknm' ,width:80, align:'left'},
         { title:'错误码', name:'ercd' ,width:100, align:'center'},
         { title:'交易标志', name:'trfg' ,width:80, align:'center'},
         { title:'钞汇标志', name:'cxfg' ,width:80, align:'center'}
     ];
    //请求列表数据；
   function getlist1( obj ){
	   var colss;

	   $.ajax({
			url:'/fx/getBrnchprice.do',
			type:'post',
			contentType:'application/json',
			async:true,
			data:obj,
			success:function(data){
				if(data.code==01){
					userdata= data.codeMessage;
					ren({'cols':colss,'wh':wh,'userdata':userdata});
				}else{
					ren({'cols':colss,'wh':wh,'userdata':''});
				}
			}
		});
   }
    function ren(obj){
    	var aa;
 	   if( product=='P004'){
 		   aa=cols1;
 	   }else{
 		   aa=cols;
 	   }
    	$('.boxtop').html('');
    	$('#ascrail2000').remove();
    	$('.boxtop').append('<div class="box"></div>');
		var mmg = $('.box').mmGrid({
				height:obj.wh
				, cols:aa
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
    }
	
	/*----------------快速搜索功能的实现------------------------*/
	$('.pub_serbtn').click(function(){
		dialog.serchData($('.pub_seript').val(),'.mmg-headWrapper');
    });


})

