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
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#page').css('width',ww);
	
	//列参数;
    var cols = [
           /* { title:'序号', name:'SEQNO',width:80,align:'center' },*/
            { title:'渠道流水号', name:'TRSN',width:200, align:'left'},
            { title:'渠道', name:'CHNL',width:80, align:'left'},
            { title:'机构号', name:'BHID',width:80, align:'left'},
            { title:'柜员号', name:'TRTL',width:80,align:'left'},
            { title:'付款账号', name:'SOTRAC',width:80, align:'left'},
            { title:'付款账户名称', name:'SONAME',width:80, align:'left'},
            { title:'付款客户号', name:'SOCUNO',width:80, align:'left'},
            { title:'付款客户类型', name:'SOCATY',width:120, align:'center'},
            { title:'收款账号', name:'TOTRAC',width:80, align:'left'},
            { title:'收款账户名称', name:'TONAME',width:80, align:'left'},
            { title:'收款客户号', name:'TOCUNO',width:80,align:'left'},
            { title:'收款客户类型', name:'TOCATY',width:120, align:'center'},
            { title:'转账克数', name:'TRAM',width:80, align:'right'},
            { title:'币种', name:'CYNM',width:80, align:'left'},
            { title:'收款客户手机号', name:'TELO',width:80, align:'left'},
            { title:'是否给收款客户发送短信', name:'SENDFG',width:30, align:'center'},
            { title:'交易日期', name:'TODT',width:80, align:'right'},
            { title:'交易时间', name:'TRTM',width:80, align:'right'},
            { title:'摘要', name:'POSTS',width:80,align:'left'},
            { title:'处理状态', name:'STCD',width:80, align:'center'},
            { title:'错误码', name:'ERCD',width:80, align:'left'},
            { title:'错误描述', name:'ERMG',width:80,align:'left'}
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    getlist({
    	'userkey':userkey,
		'number':$('.batnum').val(),
		'status':$('.handlestat option:selected').text(),
		'pageNo':1,
		'pageSize':10
    });
    renpage();
    //点击查询；
    $('.sear').click(function(){
    	getlist({
        	'userkey':userkey,
    		'number':$('.batnum').val(),
    		'status':$('.handlestat option:selected').text(),
    		'pageNo':1,
    		'pageSize':10
    		
        });
    	renpage();
    });
    function getlist(obj){
    	$.ajax({
    		url:'/fx/detailSearch.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			if(data.code==01){
    				userdata=data.codeMessage ;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
//    				$('.page').remove();
//    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    		
    			}else if(data.code==00){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    			
    		}
    	});
    }
    //点击分页;
    $('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist({
	        	'userKey':userkey,
	        	'Number':$('.batnum').val(),
 	    		'Status':$('.handlestat option:selected').text(),
	        	'pageNo':Nopage,
	        	'pageSize':10
	        });
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getlist({
	        	'userKey':userkey,
	        	'Number':$('.batnum').val(),
 	    		'Status':$('.handlestat option:selected').text(),
	        	'pageNo':Nopage,
	        	'pageSize':10
	        });
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	getlist({
	        	'userKey':userkey,
	        	'Number':$('.batnum').val(),
 	    		'Status':$('.handlestat option:selected').text(),
	        	'pageNo':Nopage,
	        	'pageSize':10
	        });
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text()*1,
		Totalpage=$('.Totalpage').text()*1;
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	getlist({
	        	'userKey':userkey,
	        	'Number':$('.batnum').val(),
 	    		'Status':$('.handlestat option:selected').text(),
	        	'pageNo':Nopage,
	        	'pageSize':10
	        });
	    }
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
			    		 getlist({
			    		    	'userkey':userkey,
			    				'number':$('.batnum').val(),
			    				'status':$('.handlestat option:selected').text(),
			    				'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
		    				    'pageNo':obj.curr
			    		    });
			    	}	
			    }
			  });
		});
	} 	
})