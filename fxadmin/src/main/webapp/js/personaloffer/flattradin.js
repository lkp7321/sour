/*
 * 平盘交易查询 -贵金属平盘
 **/
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
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-270+"px";
    
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#d1').val(dialog.today());
    var cols = [
    	             { title:'手工登记流水', name:'ppno' ,width:150, align:'left' },
    	             { title:'产品代码', name:'prcd' ,width:90, align:'left'},
    	             { title:'登记日期', name:'ckno' ,width:90, align:'right'},
    	             { title:'登记类型', name:'bsfx' ,width:90, align:'center'},
    	             { title:'敞口流水号', name:'trdt' ,width:100, align:'left'},
    	             { title:'登记时间', name:'trtm' ,width:100, align:'right'},
    	             { title:'交易员名称', name:'jgdt' ,width:100, align:'left'},
    	             { title:'币别对名称', name:'bycy' ,width:100, align:'left'},
    	             { title:'敞口编号', name:'bysl' ,width:90, align:'left'},
    	             { title:'买入币种', name:'cbsl',width:90, align:'left'},
    	             { title:'卖出币种', name:'cbby' ,width:90, align:'left'},
    	             { title:'买入金额', name:'samt' ,width:100, align:'right' },
    	             { title:'卖出金额', name:'bamt' ,width:100, align:'right'},
    	             { title:'成交汇率', name:'ykam' ,width:90, align:'right'},
    	             { title:'成本汇率', name:'jycl' ,width:90, align:'right'},
    	             { title:'成本金额', name:'mkno' ,width:90, align:'right'},
    	             { title:'盈利金额', name:'trty' ,width:100, align:'right'},
    	             { title:'钞汇标志', name:'dsno' ,width:150, align:'center'},
    	             { title:'结算日期', name:'ydtm' ,width:100, align:'right'},
    	             { title:'平盘对手', name:'cbhl' ,width:90, align:'left'},
    	             { title:'交易对手流水号', name:'expc',width:90, align:'left'},
    	             { title:'记录状态', name:'refe' ,width:280, align:'center'}
    	         ];
    var userKey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
    //
    getselectList( {'apdt':dialog.today(),'jsdt':''} );
    //点击查询 请求数据
    $('.openquery').click(function(){
    	var dt=$('#product option:selected').text();
    	    date=$('#d1').val();
    	    
    	if(dt=="登记日期"){
    		MoZhangVo ={'apdt':date,'jsdt':""}
    	}else if(dt=="结算日期"){
    		MoZhangVo ={'apdt':"",'jsdt':date}
    	}
    	getselectList( MoZhangVo);
     });
    //封装请求列表
    function getselectList(obj){
    	 $.ajax({
    		url:'/fx/pphandsel.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify( obj ),
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
        
    };
   
 })