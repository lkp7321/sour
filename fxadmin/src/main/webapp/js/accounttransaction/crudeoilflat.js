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
		ww=$(window).width()-260+"px";
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		product=sessionStorage.getItem('product'),userdata='',
		tit=$('title').text();
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	//列参数;
    var cols = [
        { title:'交易帐号', name:'trac' ,width:120, align:'left' },
        { title:'保证金类型', name:'cyen' ,width:100, align:'center'},
        { title:'持仓数量', name:'amut' ,width:100, align:'right'},
        { title:'持仓保证金', name:'marg' ,width:100, align:'right'},
        { title:'浮动盈亏', name:'profit' ,width:100, align:'right'},
        { title:'风险度', name:'risk' ,width:100, align:'right'},
        { title:'客户号', name:'cuno' ,width:100, align:'left'},
        { title:'卡号', name:'cuac' ,width:140, align:'left'},
        { title:'移动电话', name:'tlno' ,width:80, align:'left'}
    ]
   //请求列表数据；
	$.ajax({
		url:'/fx/addMargin.do',
		type:'post',
		contentType:'application/json',
		data:JSON.stringify({
			'userkey':userkey,
			'prod':product,
			'pageNo':1,
			'pageSize':10
		}),
		async:true,
		success:function(data){
			if(data.code==00){
				userdata=JSON.parse( data.codeMessage );
				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
			}else if(data.code==01){
				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
			}
		}
	});
	$('.toexcel').click(function(){
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
	})
    /*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		  dialog.serchData($('.info_seript').val(),'.mmg-headWrapper');
    });

        
})
