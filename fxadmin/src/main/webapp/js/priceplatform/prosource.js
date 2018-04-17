/*产品源监控*/
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
	 var cols = [
             { title:'货币对名称', name:'exnm',width:60,align:'left' },
             { title:'买入价', name:'neby',width:60, align:'right'},
             { title:'中间价', name:'nemd',width:60, align:'right'},
             { title:'卖出价', name:'nesl',width:60, align:'right'},
             { title:'更新时间', name:'mdtm',width:60,align:'right'},
             { title:'报价状态', name:'stfg',width:60, align:'center'},
             { title:'报价来源', name:'mknm',width:30, align:'left'},
             { title:'错误码', name:'ercn',width:40, align:'left'},
             { title:'交易标志', name:'trfg',width:30, align:'left'}
     ];
	$.ajax({
		url:'/fx/price/prodExnmPrice.do',
		type:'post',
		contentType:'application/json',
		async:false,
		success:function(data){
			var str="";
			if(data.code==01){
				userdata=JSON.parse( data.codeMessage );
				for(var i=0,num=userdata.length;i<num;i++){
					str+='<option ptid='+userdata[i].PTID+'>'+userdata[i].PTNM+'</option>'
				}
				$('.markname1 select').html(str );
			}else{
				
			}
		} 
	});	
	getlist( $('.markname1 select option:selected').attr('ptid') );
	$('.markname1 select').change(function(){
		var tit=$(this).find('option:selected').attr('ptid');
		getlist(tit);
	});
	function getlist( obj ){
		$.ajax({
			url:'/fx/price/getPriceProdse.do',
			type:'post',
			contentType:'application/json',
			data:obj,
			async:true,
			success:function(data){
				if(data.code==01){
					userdata= data.codeMessage;
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
		mmg.on('loadSuccess',function(){
			if( obj.userdata.length>0){
				$('.box tbody tr').each(function(i,v){
					$(v).find('td:eq(0)').attr('udfg',obj.userdata[i].udfg);
					if( obj.userdata[i].udfg==2){
						$(v).find('td span').css('color','red');
					}
					if( obj.userdata[i].udfg==1 ){
						$(v).find('td span').css('color','rgba(48, 218, 63, 0.53)');		//change color;
					}
				});
			}
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
})