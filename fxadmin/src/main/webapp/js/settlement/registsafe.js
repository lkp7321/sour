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
		WdatePicker:'./My97DatePicker/WdatePicker',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','WdatePicker','dialog'],function($,mmGrid,niceScroll,WdatePicker,dialog){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	
	$('#d1,#d2').val( dialog.today() );
	//列参数;
    var cols = [
            { title:'本地流水号', name:'lcon',width:60,align:'left' },
            { title:'交易日期', name:'trdt',width:60, align:'right'},
            { title:'交易主体', name:'pety',width:80, align:'left'},
            { title:'国别', name:'cont',width:60, align:'left'},
            { title:'交易类型', name:'trtp',width:60,align:'center'},
            { title:'交易状态', name:'stcd',width:60, align:'center'},
            { title:'处理标识', name:'stat',width:30, align:'center'},
            { title:'错误码', name:'ercd',width:40, align:'center'}
    ];
    
    getlist({
    	dgfieldbegin:dialog.today(),
    	dgfieldend:dialog.today(),
    	comStcd:'w',
    	pageNo:1,    
    	pageSize:10
    });
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/queryRegWater.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj ),
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
    }
})