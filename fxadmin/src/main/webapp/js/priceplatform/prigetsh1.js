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
		ww=$(window).width()-260+"px",
		tit=$('title').text();
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var str='<option mkid="all" class="al">所有</option>';
	
	//列参数;
    var cols=[
		{ title:'星期', name:'ptid',width:60,align:'left' },
		{ title:'开始时间', name:'ptnm',width:60, align:'left'},
		{ title:'结束时间', name:'calendarName',width:60, align:'left'},
		{ title:'标志', name:'ptnm',width:60, align:'left'}
    ];
    getlist();
    function getlist( ){
		$.ajax({
			url:'/fx/price/getPriceJiaList.do',
			contentType:'application/json',
			async:true,
			success:function(data){
				if(data.code==01){
					//userdata=JSON.parse( data.codeMessage );
					ren({'cols':cols,'wh':wh,'userdata':data.codeMessage,'checked':true});
				}else{
					ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
				}
			}
		});
	}
    
})