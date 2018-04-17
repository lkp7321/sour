/*
 * 外币敞口监控、贵金属敞口监控、结售汇敞口监控
 * */
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
		},
		'table2excel':{
			deps:['jquery'],
			exports:'table2excel'
		}
	},
	paths:{
		jquery:'js_files/jquery-1.9.1.min',
		mmGrid:'js_files/mmGrid',
		niceScroll:'js_files/jquery.nicescroll.min',
		dialog:'dialog',
		table2excel:'js_files/excel'
	}
});
require(['jquery','mmGrid','niceScroll','dialog','table2excel'],function($,mmGrid,niceScroll,dialog,table2excel){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var userdata='',
		tit=$('title').text(),prcd;
	//列参数;
    var cols = [
        { title:'货币对', name:'exnm' ,width:80, align:'left' },
        { title:'左头寸', name:'lamt' ,width:80, align:'right'},
        { title:'右头寸', name:'ramt' ,width:80, align:'right'},
        { title:'浮动损益', name:'fdsy' ,width:100, align:'right'},
        { title:'轧差损益', name:'zcyk' ,width:100, align:'right'},
        { title:'自动平盘盈亏', name:'ppyk' ,width:100, align:'right'},
        { title:'手工平盘盈亏', name:'sgyk' ,width:100, align:'right'},
        { title:'买入价', name:'price' ,width:100, align:'right'},
        { title:'卖出价', name:'pricer' ,width:80, align:'right'},
        { title:'成本汇率', name:'cbhl' ,width:80, align:'right'}
    ]
    if(tit=='外币敞口监控'){
    	prcd='P072';
    	prcd1='P075';
    }else if( tit=='贵金属敞口监控'){
    	prcd='P073';
    	prcd1='';
    }else if(tit=='结售汇敞口监控'){
    	prcd='P071';
    	prcd1='';
    }
    getlist();
    function getlist(){
    	$.ajax({
    		url:'/fx/price/accexPriceMonitor.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify({
    			'prcd':prcd,
    			'prcd1':prcd1
    		}),
    		async:true,
    		success:function(data){
    			if(data.code==01){
    				//userdata=JSON.Parse( data.codeMessage );
    				userdata= data.codeMessage ;
    				ren({'cols':cols,'wh':wh,'userdata':userdata});
    			}else{
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
				horizrailenabled:true
		  });
		 mmg.on('loadSuccess',function(){
			 if( obj.userdata.length>0){
				 $('.box tbody tr').each(function(i,v){
					// console.log( obj.userdata[i].udfg )
					if( obj.userdata[i].udfg==1 ){
						 $(v).addClass('colgre');
					}else if( obj.userdata[i].udfg==2 ){
						$(v).addClass('colred');
					}
				 });
			 }
		 });
    }
    $('.excel').click(function(){
		if( $('.box tbody tr').length>=1 ){
			$(".mmg-body").table2excel({
				exclude: ".noExl",
				name: "Excel Document Name",
				filename: tit + new Date().toISOString().replace(/[\-\:\.]/g, ""),
				fileext: ".xls",
				exclude_img: true,
				exclude_links: true,
				exclude_inputs: true
			});
		}
	});
    /*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		  dialog.serchData($('.info_seript').val(),'.mmg-headWrapper');
    });
})
