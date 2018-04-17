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
		wdatepicker:'My97DatePicker/WdatePicker',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','wdatepicker','dialog'],function($,mmGrid,niceScroll,wdatepicker,dialog){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('.d1,.d2').text( dialog.today);
	var tit=$('title').text(),listnumtotal,
		cols;
	if( tit=='国债流水查询'){
		url='selAllBondList.do';
		cols=cols1;
	}else if( tit=='现金流流水查询'  ){
		url='selAllCashList.do';
		cols=cols2;
	}
	//列参数;
    var cols1 = [
            { title:'请求流水号', name:'frontserialdata',width:200,align:'left' },
            { title:'请求日期', name:'requestdate',width:100, align:'right'},
            { title:'请求时间', name:'requesttime',width:100, align:'right'},
            { title:'DOWNLOADKEY', name:'downloadkey',width:200, align:'left'},
            { title:'交易注释', name:'comments',width:150,align:'left'},
            { title:'债券短名', name:'bonds_shortname',width:150, align:'left'},
            { title:'交易方向', name:'dealtype',width:100, align:'left'},
            { title:'交易日期', name:'tradedate',width:150, align:'right'},
            { title:'日期', name:'valuedate',width:150, align:'right'},
            { title:'日期', name:'settlementdate',width:150, align:'right'},
            { title:'交易价格', name:'price',width:100, align:'right'},
            { title:'交易面额', name:'faceamount',width:100, align:'right'},
            { title:'交易数量', name:'quantity',width:100, align:'right'},
            { title:'交易金额', name:'grossamount',width:100, align:'right'},
            { title:'TYPEOFEVENT', name:'typeofevent',width:120, align:'left'},
            { title:'债券交易全价', name:'dirtyprice',width:150, align:'right'},
            { title:'用户短名', name:'users_shortname',width:100, align:'left'},
            { title:'交易短名', name:'folders_shortname',width:100, align:'left'},
            { title:'交易对手短名', name:'cpty_shortname',width:100, align:'left'},
            { title:'清算模式短名', name:'clearingmodes_shortname',width:100, align:'left'},
            { title:'错误代码', name:'errorcode',width:100, align:'left'},
            { title:'错误信息', name:'errormessage',width:130, align:'left'},
    ],
    cols2=[
           { title:'请求流水号', name:'frontserialdata',width:200,align:'left' },
           { title:'请求日期', name:'requestdate',width:100, align:'right'},
           { title:'请求时间', name:'requesttime',width:100, align:'right'},
           { title:'DOWNLOADKEY', name:'downloadkey',width:200, align:'left'},
           { title:'交易状态', name:'dealstatus',width:150,align:'center'},
           { title:'交易日期', name:'tradedate',width:100, align:'right'},
           { title:'TYPEOFEVENT', name:'typeofevent',width:120, align:'left'},
           { title:'交易注释', name:'comments',width:100, align:'left'},
           { title:'支付开始日期', name:'paymentdate',width:100, align:'right'},
           { title:'支付结束日期', name:'enddate',width:100, align:'right'},
           { title:'支付的金额', name:'cashflow',width:100, align:'right'},
           { title:'现金流类型', name:'cashflowtype',width:100, align:'center'},
           { title:'期限方式', name:'periodtype',width:100, align:'center'},
           { title:'使用方式', name:'used',width:100, align:'center'},
           { title:'用户短名', name:'usersshortname',width:100, align:'left'},
           { title:'交易对手短名', name:'cptyshortname',width:100, align:'left'},
           { title:'交易Folder短名', name:'foldersshortname',width:150, align:'left'},
           { title:'货币短名', name:'currenciesshortname',width:100, align:'left'},
           { title:'处理日期', name:'responsedate',width:100, align:'right'},
           { title:'处理时间', name:'responsetime',width:100, align:'right'},
           { title:'错误代码', name:'errorcode',width:100, align:'left'},
           { title:'错误信息', name:'errormessage',width:130, align:'left'}
    ];
    
    //请求列表数据； 
    getlist({
    	startDate :dialog.today(),
    	endDate : dialog.today(),
    	rpcNo : '',
    	pageNo : 1,
    	pageSize :10
	});
    renpage();
    function getlist(obj){
    	var tit=$('title').text(),
    		cols;
    	if( tit=='国债流水查询'){
	    	url='selAllBondList.do';
	    	cols=cols1;
	    }else if( tit=='现金流流水查询'  ){
	    	url='selAllCashList.do';
	    	cols=cols2;
	    }
    	$.ajax({
    		url:url,
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			if(data.code==00){
    				userdata=JSON.parse( data.codeMessage );
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
    				/*$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    				*/
    			}else if(data.code==02){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	}); 
    }
  /*//点击分页;
    $('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text();
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	rendlist( {'url':url,'obj':{'pageNo':Nopage,'pageSize':10} });
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text();
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	rendlist( {'url':url,'obj':{'pageNo':Nopage,'pageSize':10} });
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text(),
			Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	rendlist( {'url':url,'obj':{'pageNo':Nopage,'pageSize':10} });
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text(),
			Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	rendlist( {'url':url,'obj':{'pageNo':Nopage,'pageSize':10} });
	    }
	});*/
	//点击查询；  									点击查询的时候，传输分页；
    $('.sear').click(function(){
    	//判断起始日期和结束日期；
    	var st,et,rpcNo,Nopage;
    	if( $('#d1').val()==''){
    		st=$('.d1').text();
    	}else{
    		st=$('#d1').val();
    	}
    	
    	if( $('#d2').val()==''){
    		et=$('.d2').text();
    	}else{
    		et=$('#d2').val();
    	}
    	rpcNo=$('.cardNu input').val();
    	Nopage=$('.Nopage').text();
    	getlist({
    		startDate :st,
        	endDate : et,
        	rpcNo : rpcNo,
        	pageNo :1,
        	pageSize :10
    	});
    	renpage();
    });
    function renpage(){
    	layui.use(['laypage', 'layer'], function(){
    		  var laypage = layui.laypage,layer = layui.layer;
    		  //完整功能
    		  laypage.render({
    		    elem: 'page'
    		    ,count:listnumtotal
    		    ,theme: '#5a8bff'
    		    ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
    		    ,jump: function(obj,first){
    		    	if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
    		    		var st,et,rpcNo,Nopage;
    		        	if( $('#d1').val()==''){
    		        		st=$('.d1').text();
    		        	}else{
    		        		st=$('#d1').val();
    		        	}
    		        	if( $('#d2').val()==''){
    		        		et=$('.d2').text();
    		        	}else{
    		        		et=$('#d2').val();
    		        	}
    		        	rpcNo=$('.cardNu input').val();
    		    		getlist({
    		    	    	startDate :st,
    		            	endDate : et,
    		            	rpcNo : rpcNo,
    		            	pageNo :obj.curr,
    		            	pageSize :$('.layui-laypage-limits select option:selected').attr('value')*1
    		    	    });
    		    	}	
    		    }
    		  });
    	});
    }
});
