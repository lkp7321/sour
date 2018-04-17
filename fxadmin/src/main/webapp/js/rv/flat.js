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
	var listnumtotal;
	//列参数;
    var cols = [
            { title:'交易流水号', name:'usnm',width:120,align:'left' },
            { title:'请求日期', name:'ptnm',width:80, align:'right'},
            { title:'请求时间', name:'ognm',width:100, align:'right'},
            { title:'产品名称', name:'cmpt',width:120, align:'left'},
            { title:'交易码', name:'mbtl',width:120,align:'left'},
            { title:'交易类型', name:'telp',width:80, align:'center'},
            { title:'机构号', name:'ufax',width:80, align:'left'},
            { title:'机构名称', name:'mail',width:80, align:'left'},
            { title:'交易状态', name:'macip',width:80, align:'center'},
            { title:'交易日期', name:'macip',width:80, align:'right'},
            { title:'TYPEOFEVENT', name:'macip',width:120, align:'left'},
            { title:'交易注释', name:'usfg',width:80, align:'left'},
            { title:'左金额', name:'rmrk',width:80, align:'right'},
            { title:'右金额', name:'rmrk',width:80, align:'right'},           
            { title:'成交汇率', name:'rmrk',width:80, align:'right'},
            { title:'SPLITRATE', name:'rmrk',width:120, align:'left'},
            { title:'成交日期', name:'rmrk',width:80, align:'right'},
            { title:'DOWNLOADKEY', name:'rmrk',width:120, align:'left'},
            { title:'用户短名', name:'rmrk',width:80, align:'left'},
            { title:'货币对短名', name:'rmrk',width:80, align:'left'},
            { title:'交易短名', name:'rmrk',width:80, align:'left'},
            { title:'交易对手短名', name:'rmrk',width:80, align:'left'},
            { title:'处理状态', name:'rmrk',width:80, align:'center'}
    ];
   
    var startDate=$('#d1').val(),
    endDate=$('#d2').val(),
    rpcNo=$('.cardNu input').val(),
    pageNo= 1, 
    pageSize=10,
    KondorVo={"startDate":startDate,"endDate":endDate,"rpcNo":rpcNo,"pageNo":pageNo,"pageSize":pageSize }
    getSpotList(KondorVo);
    renpage();
	$('.sear').click(function(){
		startDate=$('#d1').val();
	    endDate=$('#d2').val();
	    rpcNo=$('.cardNu input').val();
	    pageNo= 1;
	    pageSize=10;
	    KondorVo={"startDate":startDate,"endDate":endDate,"rpcNo":rpcNo,"pageNo":pageNo,"pageSize":pageSize }
		getSpotList(KondorVo);
	    renpage();
	});
    
  //点击分页;
    /*$('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text();
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	KondorVo={"startDate":startDate,"endDate":endDate,"rpcNo":rpcNo,"pageNo":pageNo,"pageSize":pageSize }
 	    	getAllTradeCalendar(KondorVo); 
 	     }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text();
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	KondorVo={"startDate":startDate,"endDate":endDate,"rpcNo":rpcNo,"pageNo":pageNo,"pageSize":pageSize }
 	    	getAllTradeCalendar(KondorVo);  
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text(),
			Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	KondorVo={"startDate":startDate,"endDate":endDate,"rpcNo":rpcNo,"pageNo":pageNo,"pageSize":pageSize }
 	    	getAllTradeCalendar(KondorVo);  
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text(),
		Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	KondorVo={"startDate":startDate,"endDate":endDate,"rpcNo":rpcNo,"pageNo":pageNo,"pageSize":pageSize }
 	    	getAllTradeCalendar(KondorVo); 
	    }
	});*/
	//封装请求主列表数据
   function getSpotList(obj){
   	 $.ajax({
   		url:'/fx/selMidSpotList.do',
   		type:'post',
   		contentType:'application/json',
   		data:JSON.stringify(obj),
   		async:false,
   		success:function(data){
   		    if(data.code==00){
   				userdata=JSON.parse( data.codeMessage );
   			    dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
   			    listnumtotal=userdata.total;
   			   /* $('.page').remove();
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
	   		 		getSpotList({
   		 				"startDate":$('#d1').val(),
   		 				"endDate":$('#d2').val(),
   		 				"rpcNo":$('.cardNu input').val(),
   		 				"pageNo":obj.curr,
   		 				"pageSize":$('.layui-laypage-limits select option:selected').attr('value')*1 
	   		 		});
   		    	}	
   		    }
   		  });
   	});
   } 
    
});
