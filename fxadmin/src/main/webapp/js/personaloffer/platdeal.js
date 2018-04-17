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
	//列参数
    var cols = [
        { title:'用户名称', name:'username' ,width:100, align:'left' },
        { title:'角色', name:'role' ,width:100, align:'left'},
        { title:'机构名称', name:'from' ,width:150, align:'left'},
        { title:'真实姓名', name:'realname' ,width:80, align:'left'},
        { title:'移动电话', name:'phone' ,width:120, align:'left'},
        { title:'电话', name:'tel' ,width:100, align:'left'},
        { title:'传真', name:'cz' ,width:80, align:'left'},
        { title:'email', name:'email' ,width:100, align:'left'},
        { title:'mac/ip', name:'mac' ,width:80, align:'left'},
        { title:'备注', name:'beizhu',width:80, align:'left'},
        { title:'用户状态', name:'status' ,width:80, align:'left'}
    ];
	var mmg = $('.box').mmGrid({
			width:'1180px'
			,height:"580px"
			, cols: cols
            , url: '/fx/js/personaloffer/data.json'
            , method: 'get'
            , nowrap:true
            , fullWidthRows:true
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

/*----------------快速搜索功能的实现------------------------*/
	$('.review_serbtn').click(function(){
		 dialog.serchData('.review_seript');
    });


})

