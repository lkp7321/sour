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
		wdatepicker:'./My97DatePicker/WdatePicker',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','wdatepicker','dialog'],function($,mmGrid,niceScroll,wdatepicker,dialog){
	var wh=$(window).height()-200+"px",
		ww=$(window).width()-260+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#d1,#d2').val( dialog.today);
	var text=$('.head span:eq(1)').text().substr(7),
		listnumtotal;
	if(text=="实盘流水查询"){
		//列参数
	    var cols = [
	        { title:'请求流水号', name:'frontserialdata' ,width:200, align:'left' },
	        { title:'请求日期', name:'requestdate' ,width:100, align:'right'},
	        { title:'请求时间', name:'requesttime' ,width:80, align:'right'},
	        { title:'产品编号', name:'productcode' ,width:80, align:'left'},
	        { title:'机构号', name:'tradeorg' ,width:80, align:'left'},
	        { title:'机构名称', name:'tradename' ,width:80, align:'left'},
	        { title:'交易状态', name:'dealstatus' ,width:80, align:'center'},
	        { title:'交易时间', name:'tradedate' ,width:100, align:'right'},
	        { title:'TYPEOFEVENT', name:'typeofevent' ,width:100, align:'left' },
	        { title:'交易注释', name:'comments' ,width:200, align:'left'},
	        { title:'左金额', name:'amount1' ,width:100, align:'right'},
	        { title:'右金额', name:'amount2' ,width:100, align:'right'},
	        { title:'SPLITRATE', name:'splitrate' ,width:120, align:'left'},
	        { title:'VALUEDATE', name:'downloadkey' ,width:120, align:'left'},
	        { title:'DOWNLOADKEY', name:'downloadkey' ,width:120, align:'left'},
	        { title:'用户短名', name:'users_shortname' ,width:100, align:'left'},
	        { title:'货币短名', name:'pairs_shortname' ,width:100, align:'left' },
	        { title:'Folder短名', name:'folders_shortname' ,width:100, align:'left'},
	        { title:'交易对手短名', name:'cpty_shortname' ,width:100, align:'left'},
	        { title:'交易Folder短名1', name:'folders_spot1shortname' ,width:120, align:'left'},
	        { title:'交易状态', name:'tradestatus' ,width:100, align:'center'},
	        { title:'错误代码', name:'errorcode' ,width:100, align:'left'},
	        { title:'错误信息', name:'errormessage' ,width:200, align:'left'}
	       ];
	    var url='selAllSpotList.do' 
   }else if(text=="客户交易流水查询"){
		//列参数
	    var cols = [
			{ title:'产品名称', name:'productcode' ,width:100, align:'left' },
			{ title:'交易流水号', name:'kondornumber' ,width:200, align:'left'},
			{ title:'币别对编号', name:'currencypairs' ,width:100, align:'left'},
			{ title:'交易时间', name:'tradedate' ,width:80, align:'right'},
			{ title:'左金额', name:'amount1' ,width:100, align:'right'},
			{ title:'右金额', name:'amount2' ,width:80, align:'right'},
			{ title:'交易汇率', name:'spotrate' ,width:80, align:'right'},
			{ title:'交易状态', name:'states' ,width:200, align:'center'}
	       ];
		var url='selTotalSpotList.do'
   }else if(text="客户交易明细查询"){
		 //列参数
	    var cols = [
	        { title:'请求流水号', name:'frontserialdata' ,width:100, align:'left' },
	        { title:'请求日期', name:'requestdate' ,width:80, align:'right'},
	        { title:'请求时间', name:'requesttime' ,width:80, align:'right'},
	        { title:'产品名称', name:'productcode' ,width:80, align:'left'},
	        { title:'交易状态', name:'dealstatus' ,width:100, align:'center'},
	        { title:'交易日期', name:'tradedate' ,width:80, align:'right'},
	        { title:'交易注释', name:'comments' ,width:80, align:'left'},
	        { title:'币别对编号', name:'currencypairs' ,width:80, align:'left'},
	        { title:'左金额', name:'amount1' ,width:80, align:'right'},
	        { title:'右金额', name:'amount2' ,width:80, align:'right'},
	        { title:'成交汇率', name:'spotrate' ,width:80, align:'right'},
	        { title:'成交日期', name:'valuedate' ,width:80, align:'right'},
	        { title:'成交状态', name:'state' ,width:80, align:'center'}
	     ];
	    var url='selMxSpotList.do';
	}
	
	  var startDate=$('#d1').val(),
	      endDate=$('#d2').val(),
	      tradeCode=$('.productType option:selected').val(),
	      rpcNo=$('.cardNu input').val(),
	      pageNo= 1, 
	      pageSize=10,
	      KondorVo={"startDate":startDate,"endDate":endDate,"tradeCode":tradeCode,"rpcNo":rpcNo,"pageNo":pageNo,"pageSize":pageSize }
	      getSpotList(url,KondorVo);
	  	  renpage();
	  $('.sear').click(function(){
		  startDate=$('#d1').val();
	      endDate=$('#d2').val();
	      tradeCode=$('.productType option:selected').val();
	      rpcNo=$('.cardNu input').val();
	      pageNo= 1;
	      pageSize=10;
	      KondorVo={"startDate":startDate,"endDate":endDate,"tradeCode":tradeCode,"rpcNo":rpcNo,"pageNo":pageNo,"pageSize":pageSize }
		  getSpotList(url,KondorVo);
	      renpage();
	  });
	  
	   /*//点击分页;
	    $('.boxtop').on('click','.first',function(){
	    	var Nopage=$('.Nopage').text();
	 	    if(Nopage>1){
	 	    	Nopage=1;
	 	    	KondorVo={"startDate":startDate,"endDate":endDate,"tradeCode":tradeCode,"rpcNo":rpcNo,"pageNo":pageNo,"pageSize":pageSize }
	 	    	getAllTradeCalendar(url,KondorVo); 
	 	     }
	    });
		$('.boxtop').on('click','.prev',function(){
		    var Nopage=$('.Nopage').text();
		    if(Nopage>1){
		    	Nopage=Nopage-1;
		    	KondorVo={"startDate":startDate,"endDate":endDate,"tradeCode":tradeCode,"rpcNo":rpcNo,"pageNo":pageNo,"pageSize":pageSize }
	 	    	getAllTradeCalendar(url,KondorVo);  
		    }
		});
		$('.boxtop').on('click','.next',function(){
			var Nopage=$('.Nopage').text(),
				Totalpage=$('.Totalpage').text();
		    if(Nopage<Totalpage){
		    	Nopage=Nopage*1+1;
		    	KondorVo={"startDate":startDate,"endDate":endDate,"tradeCode":tradeCode,"rpcNo":rpcNo,"pageNo":pageNo,"pageSize":pageSize }
	 	    	getAllTradeCalendar(url,KondorVo);  
		    }
		});
		$('.boxtop').on('click','.last',function(){
			var Nopage=$('.Nopage').text(),
			Totalpage=$('.Totalpage').text();
		    if(Nopage<Totalpage){
		    	Nopage=Totalpage;
		    	KondorVo={"startDate":startDate,"endDate":endDate,"tradeCode":tradeCode,"rpcNo":rpcNo,"pageNo":pageNo,"pageSize":pageSize }
	 	    	getAllTradeCalendar(url,KondorVo); 
		    }
		});*/
		//封装请求主列表数据
	   function getSpotList(url,obj){
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
	   };
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
	    		  	      KondorVo={
    		  	    		  "startDate":$('#d1').val(),
    		  	    		  "endDate":$('#d2').val(),
    		  	    		  "tradeCode":$('.productType option:selected').val(),
    		  	    		  "rpcNo":$('.cardNu input').val(),
    		  	    		  "pageNo":$('.layui-laypage-limits select option:selected').attr('value')*1,
    		  	    		  "pageSize":pageSize 
	    		  	      }
	    		  		  getSpotList(url,KondorVo);
	    		    	}	
	    		    }
	    		  });
	    	});
	    }
});
