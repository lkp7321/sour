/*实盘-成交流水监控*/
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
		ww=$(window).width()-345+"px",
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	var text=$('.head span:eq(1)').text().substr(5);
	 
	if(text=="成交流水监控"){
		//列参数
	    var cols = [
	        { title:'交易流水号', name:'lcno' ,width:100, align:'left' },
	        { title:'交易日期', name:'trdt' ,width:80, align:'left'},
	        { title:'交易时间', name:'trtm' ,width:100, align:'left'},
	        { title:'买入币种', name:'bynm' ,width:80, align:'left'},
	        { title:'卖出币种', name:'slnm' ,width:100, align:'left'},
	        { title:'卖金额', name:'slam' ,width:80, align:'left'},
	        { title:'买金额', name:'byam' ,width:80, align:'left'},
	        { title:'折美元金额', name:'usam' ,width:80, align:'left'},
	        { title:'成交价格', name:'expc' ,width:80, align:'left'},
	        { title:'优惠点数', name:'fvda' ,width:80, align:'left'},
	        { title:'卡号', name:'cuac' ,width:80, align:'left'},
	        { title:'交易账号', name:'trac' ,width:80, align:'left'}
	     ];
		var totalUrl='todayTranCount.do',
		    todayUrl='todayTranUsam.do',
		    listUrl='queryTranList.do';
		    
   }else if(text="平盘交易监控"){
		 //列参数
	    var cols = [
	        { title:'平盘流水号', name:'ppno' ,width:100, align:'left' },
	        { title:'敞口编码', name:'ckno' ,width:80, align:'left'},
	        { title:'交易日期', name:'trdt' ,width:80, align:'left'},
	        { title:'卖币种', name:'slcy' ,width:80, align:'left'},
	        { title:'买币种', name:'bycy:' ,width:100, align:'left'},
	        { title:'买金额', name:'bamt' ,width:80, align:'left'},
	        { title:'卖金额', name:'samt' ,width:80, align:'left'},
	        { title:'盈亏金额', name:'ykam' ,width:80, align:'left'},
	        { title:'交易状态', name:'stat' ,width:80, align:'left'}
	          
	     ];
	    var totalUrl='todayTranDetils.do',
	    todayUrl='todayTranUsams.do',
	    listUrl='queryPpTranLists.do';
	}
    $('#d12').val(dialog.today());
    $('#d13').val(dialog.today());
    //请求当日成交总数
    $.ajax({
		url:totalUrl,
		type:'get',
		contentType:'application/json',
		async:true,
		success:function(data){
			 if(data.code==01){
				 $('.head p span:eq(0) i').text(data.codeMessage)
			 }else if(data.code==00){
			 }
		}
	});
    //请求当日成交量
    $.ajax({
		url:todayUrl,
		type:'get',
		contentType:'application/json',
		async:true,
		success:function(data){
			 if(data.code==01){
				 $('.head p span:eq(1) i').text(data.codeMessage)
			 }else if(data.code==00){
			 }
		}
	});
    gettodayTranCount({"btime":"","etime":""})
    $('.chaun').click(function(){
    	var btime=$('#d12').val(),
    	    etime=$('#d13').val();
    	gettodayTranCount({"btime":btime,"etime":etime})
     })
 //封装请求列表
    function gettodayTranCount(obj){
    	$.ajax({
    		url:listUrl,
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
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
        
    };
    
    
    
    
    
    
    
    
/*----------------快速搜索功能的实现------------------------*/
	$('.review_serbtn').click(function(){
		var txt=$('.review_seript').val();
		  dialog.serchData(txt);
    });


})

