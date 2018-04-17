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
	
	//列参数;
    var cols = [
            { title:'市场', name:'mknm',width:40,align:'left' },
            { title:'货币对', name:'exnm',width:40, align:'left'},
            { title:'价格类型', name:'tpnm',width:40, align:'center'},
            { title:'钞汇标志', name:'cxfg',width:50, align:'center'},
            { title:'期限', name:'term',width:40,align:'right'},
            { title:'买入价', name:'neby',width:50, align:'right'},
            { title:'中间价', name:'nemd',width:50, align:'right'},
            { title:'卖出价', name:'nesl',width:50, align:'right'},
            { title:'交易标志', name:'trfg',width:40, align:'center'},
            { title:'错误码', name:'ercn',width:40, align:'center'},
            { title:'更新时间', name:'mdtm',width:100, align:'right'}
    ];
    $.ajax({
		url:'/fx/price/getFpriceMonitor.do',
		type:'post',
		contentType:'application/json',
		async:true,
		success:function(data){
			if(data.code==01){
				userdata=JSON.parse( data.codeMessage );
				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
			}else{
				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
			}
		}
	});
})