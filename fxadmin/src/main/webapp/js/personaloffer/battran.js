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
	var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey;
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#page').css('width',ww);
	//列参数;
    var cols = [
            { title:'批次号', name:'BNUM',width:80,align:'left' },
            { title:'批次执行结果', name:'STAT',width:80, align:'left'},
            { title:'结果文件名', name:'TFILE',width:80, align:'left'},
            { title:'总笔数', name:'COUT',width:80, align:'right'},
            { title:'总金额(克)', name:'AMUT',width:80,align:'right'},
            { title:'成功笔数', name:'SNUM',width:80, align:'right'},
            { title:'成功金额(克)', name:'SSAM',width:80,align:'right'},
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    getlist({
    	'userkey':userkey,
		'number':$('.batnumber').val(),
		'filename':$('.asknum').val(),
		'pageNo':1,
		'pageSize':10
    });
    renpage();
    //点击查询；
    $('.sear').click(function(){
    	getlist({
    		'userkey':userkey,
    		'number':$('.batnumber').val(),
    		'filename':$('.asknum').val(),
    		'pageNo':1,
    		'pageSize':10
        });
    	renpage();
    });
    function getlist(obj){
    	$.ajax({
    		url:'/fx/querySearch.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			if(data.code==00){
    				userdata= data.codeMessage ;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
//    				$('.page').remove();
//    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    			}else if(data.code==01){
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    			
    		}
    	});
    	
    }
    $('.send').click(function(){
    	var number=$('.batnumber').val(),///批次号
    		filename=$('.asknum').val(),//请求文件名
    		count=$('.allnum').val(),///总笔数
    		amut=$('.allmon').val(),///总金额
    		str="批次号或请求文件名或总笔数或总金额不能为空!",
    		nowDate,nowTime,da=new Date();
    		nowDate=''+da.getFullYear()+ retuzero( da.getMonth()+1 )+ retuzero( da.getDate() ),
    		nowTime=''+retuzero( da.getHours() )+ retuzero( da.getMinutes() )+ retuzero( da.getSeconds() );
    		
    	if( number==""||filename==''||count==''||amut==''){
    		dialog.choicedata(str,'battran');
    	}else{
    		$.ajax({
        		url:'/fx/querySearchSend.do',
        		type:'post',
        		dataType:'html',
        		data:{
        			'trsn':"56801"+nowDate+nowTime+"0000000000000",
        		 	'trid':"A2107",
        		 	'bhid':"8500",
        		 	'chnl':"1001",
        		 	'rqdt':nowDate,
        		 	'rqtm':nowTime,
        		 	'trtl':"",
        		 	'ttyn':"",
        		 	'autl':"",
        		 	'number':number,
        		 	'filename':filename,
        		 	'count':count,
        		 	'amut':amut
        		},
        		async:false,
        		success:function(data){
        			dialog.choicedata(data,'battran1');
        		}
        	});
    	}
    });
    function retuzero( obj ){
    	var zeroobj;
    	if( obj<10){
    		zeroobj='0'+obj;
    	}else{
    		zeroobj=obj;
    	}
    	return zeroobj;
    }
    $('body',parent.document).on('click','.battran .sure,.battran1 .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
    //点击分页;
    $('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist({
	        	'pageNo':Nopage,
	        	'userkey':userkey,
	    		'number':$('.batnumber').val(),
	    		'filename':$('.asknum').val(),
	    		'pageSize':10
	        });
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text()*1;
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	getlist({
	        	'pageNo':Nopage,
	        	'userkey':userkey,
	    		'number':$('.batnumber').val(),
	    		'filename':$('.asknum').val(),
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
	        	'pageNo':Nopage,
	        	'userkey':userkey,
	    		'number':$('.batnumber').val(),
	    		'filename':$('.asknum').val(),
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
	        	'pageNo':Nopage,
	        	'userkey':userkey,
	    		'number':$('.batnumber').val(),
	    		'filename':$('.asknum').val(),
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
		    					'number':$('.batnumber').val(),
		    					'filename':$('.asknum').val(),
			    				'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
		    				    'pageNo':obj.curr
			    		    });
			    	}	
			    }
			  });
		});
	}
})