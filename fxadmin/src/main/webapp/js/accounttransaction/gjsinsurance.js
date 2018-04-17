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
		table2excel:'js_files/excel',
		WdatePicker:'./My97DatePicker/WdatePicker'

	}
});
require(['jquery','mmGrid','niceScroll','dialog','table2excel','WdatePicker'],function($,mmGrid,niceScroll,dialog,table2excel,WdatePicker){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-345+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var userdata='',
		tit=$('title').text(),str='',
		product=sessionStorage.getItem('product');
	//列参数;
    var cols = [
        { title:'货币对', name:'exnm' ,width:80, align:'left' },
        { title:'左头寸', name:'lamt' ,width:110, align:'left'},
        { title:'右头寸', name:'ramt' ,width:110, align:'left'},
        { title:'浮动损益', name:'fdsy' ,width:100, align:'left'},
        { title:'轧差损益', name:'zcyk' ,width:100, align:'left'},
        { title:'自动平盘盈亏', name:'ppyk' ,width:100, align:'left'},
        { title:'手工平盘盈亏', name:'sgyk' ,width:100, align:'left'},
        { title:'买入价', name:'price' ,width:100, align:'right'},
        { title:'卖出价', name:'pricer' ,width:80, align:'right'},
        { title:'成本汇率', name:'cbhl' ,width:80, align:'right'}
    ]
 
	$('.toexcel').click(function(){
		if( $('.box tbody tr').length>1 ){
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
	})
    /*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		  dialog.serchData($('.info_seript').val(),'.mmg-headWrapper');
    });

        
})
