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
		ww=$(window).width()-250+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	//列参数;
    var cols = [
            { title:'市场编号', name:'mkid',width:60,align:'left' },
            { title:'市场名称', name:'mknm',width:60, align:'left'},
            { title:'额度上限', name:'amup',width:70, align:'right'},
            { title:'额度下限', name:'amlw',width:70, align:'right'},
            { title:'已完成额度', name:'fham',width:60,align:'right'},//
            { title:'限定笔数', name:'ctnu',width:60, align:'right'},//
            { title:'已完成笔数', name:'fhnu',width:70, align:'right'},//
            { title:'使用状态', name:'usfg',width:50, align:'center'},
            { title:'IP地址', name:'mkip',width:70, align:'left'},   
            { title:'交易属性', name:'trtp',width:50, align:'left'},
            { title:'发布名称', name:'putnm',width:80, align:'left'},
            { title:'价格源-手报', name:'hcid',width:80, align:'left'},
            { title:'备注', name:'remk',width:80, align:'left'}
    ];
  //请求列表数据；
	$.ajax({
		url:'/fx/price/getMkPrice.do',
		type:'post',
		contentType:'application/json',
		async:true,
		success:function(data){
			if(data.code==01){
				userdata=JSON.parse( data.codeMessage );
				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
			}else if(data.code==00){
				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
			}
			
		}
	});
})