/*
 *账户交易   交叉盘计算
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
            { title:'货币对名称', name:'exnm',width:80,align:'left' },
            { title:'货币对A', name:'exna',width:80, align:'left'},
            { title:'货币对B', name:'exnb',width:80, align:'left'},
            { title:'乘除', name:'mudi',width:80, align:'left'},
            { title:'计算公式', name:'expr',width:80,align:'left'}
    ];
    
    getlist();
    function getlist(){
    	$.ajax({
    		url:'/fx/price/getPriceContxp1.do',
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
    }
})