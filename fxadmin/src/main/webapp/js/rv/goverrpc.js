require.config({
	baseUrl:'/fx/js',
	shim:{
		'jquery.niceScroll': {
			deps: ['jquery'],
			exports: 'jquery.niceScroll'
		},
		'page':{
			deps:['jquery'],
			exports:'page'
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
		page:'js_files/page',
		dialog:'dialog'
	}
});
require(['jquery','mmGrid','niceScroll','wdatepicker','page','dialog'],function($,mmGrid,niceScroll,wdatepicker,page,dialog){
	var wh=$(window).height()-190+"px",
		ww=$(window).width()-260+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('.d1,.d2').text( dialog.today);
	var listnumtotal;
	//列参数;
    var cols1 = [
            { title:'请求流水号', name:'frontserialdata',width:120,align:'left' },
            { title:'请求顺序号', name:'downloadkey',width:100, align:'left'},
            { title:'请求日期', name:'requestdate',width:120, align:'right'},
            { title:'请求时间', name:'requesttime',width:120,align:'right'},
            { title:'产品编号', name:'tradecode',width:80, align:'left'},
            { title:'交易类型', name:'tradetype',width:80, align:'center'},
            { title:'请求类型', name:'reqtype',width:80, align:'center'},
            { title:'重试次数', name:'retrytimes',width:80, align:'right'},
            { title:'处理状态', name:'state',width:80, align:'center'}
    ];
   getlist({
	   tradeCode:'',
	   clstate: '',
	   rpcNo : '',
	   pageNo : 1,
	   pageSize :10
   });
   renpage();
    //请求列表数据；
    function getlist( obj ){
    	var tit=$('title').text(),
    		url='selAllRpcList.do',
    		cols=cols1;
    	$.ajax({
    		url:url,
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			if(data.code==00){
    				userdata=JSON.parse( data.codeMessage);
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
  //点击分页;
    /*$('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text();
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	rendlist( {'url':'selAllRpcList.dos','obj':{'pageNo':Nopage,'pageSize':10} });
 	    }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=$('.Nopage').text();
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	rendlist( {'url':'selAllRpcList.do','obj':{'pageNo':Nopage,'pageSize':10} });
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=$('.Nopage').text(),
			Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	rendlist( {'url':'selAllRpcList.do','obj':{'pageNo':Nopage,'pageSize':10} });
	    }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=$('.Nopage').text(),
		Totalpage=$('.Totalpage').text();
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	rendlist( {'url':'selAllRpcList.do','obj':{'pageNo':Nopage,'pageSize':10} });
	    }
	});*/
    //点击查询和修改按钮；
    $('.sear').click(function(){
    	getFn();
    	renpage();
    });
    function getFn(){
    	var clstate,
    		Nopage=$('.Nopage').text();
    	if( $('.dat2 select option:selected').attr('value')=='all'){
    		clstate='';
    	}else{
    		clstate=$('.dat2 select option:selected').attr('value');
    	}
    	getlist({
		   tradeCode:$('.dat1 select option:selected').attr('value'),
		   clstate:clstate,
		   rpcNo : $('.cardNu input').val(),
		   pageNo :Nopage,
		   pageSize :10
	   });
    }
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
    		    		var clstate;
    		    		if( $('.dat2 select option:selected').attr('value')=='all'){
    		        		clstate='';
    		        	}else{
    		        		clstate=$('.dat2 select option:selected').attr('value');
    		        	}
    		    		getlist({
		    			   tradeCode:$('.dat1 select option:selected').attr('value'),
		    			   clstate:clstate,
		    			   rpcNo : $('.cardNu input').val(),
		    			   pageNo :obj.curr,
		    			   pageSize :$('.layui-laypage-limits select option:selected').attr('value')*1
		    		   });
    		    	}	
    		    }
    		  });
    	});
    }
    $('.change').click(function(){
    	if( !$('.box tbody tr').hasClass('selected') ){
    		var downloadkey=$('.box tr.selected td:eq(1) span').text(),
    			tradeCode=$('.dat1 select option:selected').attr('value'),
    			//state='成功';
    			state=$('.box tr.selected td:eq(8) span').text();
    		//dialog.changeda('goverrpc',state);
    		$.ajax({
    			url:'/fx/selInKondor.do',
    			type:'post',
        		contentType:'application/json',
        		data:JSON.stringify({
        			'tradeCode':tradeCode,
        			'downloadkey':downloadkey
        		}),
        		async:true,
        		success:function(data){
        			if(data.code==00){
        				if(state!='处理成功'){
        					dialog.changeda('goverrpc','state');
        					
        				}else {
        					dialog.choicedata('此交易已处理成功，无需修改!','goverrpc','aaa');
        				}        				
        			}else if(data.code==02){
        				dialog.choicedata(data.codeMessage,'goverrpc','aaa');
        			}
        		}
    		});
    	}else{
    		dialog.choicedata('请先选择一条数据!','goverrpc','aaa');
    	}
    });
    $('body',parent.document).on('click','.goverrpc .sure,.goverrpc .close,.goverrpc .faile',function(){
		//关闭选择一条数据;
		$(this).closest('.zhezhao').remove();
	}); 
    $('body',parent.document).on('blur','.rtime',function(){
    	if( !/^\d+$/.test(rtime) ){
    		$('.rtime re',parent.document).remove();
    		$('.rtime',parent.document).parent('div').append('<re>重试次数必须为整数</re>');
    	}else{
    		$('.rtime re',parent.document).remove();
    	}
    });
    $('body',parent.document).on('click','.goverrpc .succss',function(){
    	var retrytimes=$('.rtime',parent.document).val(),
    		rpcNo=$('.box tr.selected td:eq(0) span').text(),
    		clstate=$('.goverrpc input[name="aa"]:checked',parent.document).attr('tit');
    	if( /^\d+$/.test(retrytimes ) ){
    		$.ajax({
    			url:'/fx/rpcUpdate.do',
    			type:'post',
        		contentType:'application/json',
        		data:JSON.stringify({
        			retrytimes : retrytimes,
        			clstate : clstate,
        			rpcNo:rpcNo
        		}),
        		async:true,
        		success:function(data){
        			$('.goverrpc .succss',parent.document).closest('.zhezhao').remove();
        			if(data.code==00){
        				dialog.choicedata(data.codeMessage,'goverrpc','aaa');
        				getFn();
        			}else if(data.code==01){
        				dialog.choicedata(data.codeMessage,'goverrpc','aaa');
        			}
        		}
    		});
    		
    	}else{
    		$('.rtime re',parent.document).remove();
    		$('.rtime',parent.document).parent('div').append('<re>重试次数必须为整数</re>');
    	}
	}); 
});
