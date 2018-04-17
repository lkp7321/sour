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
		ww=$(window).width()-345+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	
	//列参数;
    var cols = [
            { title:'申请人', name:'name',width:60,align:'left' },
            { title:'单位', name:'unit',width:60, align:'left'},
            { title:'联系电话', name:'telp',width:60, align:'left'},
            { title:'Email', name:'email',width:60,align:'left'},
            { title:'使用IP', name:'ip',width:60,align:'left'},
            { title:'接口名称', name:'jksm',width:60,align:'left'},
            { title:'状态', name:'stat',width:60,align:'left'},
    ];
})