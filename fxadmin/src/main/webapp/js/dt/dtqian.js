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
		ww=$(window).width()-250+"px";
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('.page').css('width',ww);
	$('#d1,#d2').val( dialog.today() );
	//列参数;
    var cols = [
            { title:'额度标志', name:'VLFG',width:100,align:'center' },
            { title:'客户号', name:'CUNO',width:100, align:'left'},
            { title:'证件号码', name:'IDNO',width:100, align:'left'},
            { title:'国别', name:'CONT',width:100, align:'left'},
            { title:'证件类型代码', name:'IDTP',width:100,align:'left'},
            { title:'结购汇标志', name:'JGFG',width:100, align:'center'},
            { title:'外币币种', name:'FONM',width:100, align:'left'},
            { title:'人民币币种', name:'CYNM',width:100, align:'left'},
            { title:'外币金额', name:'FOAM',width:100, align:'right'},
            { title:'人民币金额', name:'CYAM',width:100, align:'right'},
            { title:'标准币别对', name:'EXNM',width:100, align:'left'},
            { title:'定投频率', name:'MSFY',width:100, align:'right'},
            { title:'签约日期', name:'RGDT',width:100, align:'right'},
            { title:'签约时间', name:'RGTM',width:100, align:'right'},
            { title:'签约状态', name:'RGTP',width:100, align:'center'},
            { title:'账号', name:'CUAC',width:100, align:'left'}
    ];
    //获取产品和用户名；
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},listnumtotal,
		userdata;
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
    	tit=$('title').text();
    //请求列表数据；
    getlist({
    	pageNo:1,
    	pageSize:10,
    	entity:{
	    	rgtp:$('.signout input[type="radio"]:checked').attr('rgtp'),//0，签约；1，解约
	    	rgdt:$('#d1').val(),//起始日期
	    	crdt:$('#d2').val(),//结束日期
	    	cuac:$('.dt_qian input').val(),//交易账号
	    	exnm:$('.parentfit option.selected').attr('exnm')//标准货币对
    	}
    });
    //请求币别对；
    $.ajax({
		url:'/fx/jsh/getDtCurrMsg.do',
		type:'get',
		contentType:'application/json',
		async:true,
		success:function(data){
			var str='<option exnm="">请选择</option>';
			if(data.code==01){
				userdata= data.codeMessage;
				for(var i in userdata){
					str+='<option exnm='+userdata[i].EXNM+'>'+userdata[i].EXNM+'</option>'
				}
				$('.cuu_pair select').html( str );
			}else {
			}
		}
	});
    //点击查询按钮;
    $('.dt_bot button').click(function(){
    	var obj={
			rgdt:$('#d1').val(),
    		crdt:$('#d2').val(),
    		rgtp:$('.signout input[type="radio"]:checked').attr('rgtp'),//0，签约；1，解约
			cuac:$('.dt_qian input').val(),
	    	exnm:$('.cuu_pair select option:selected').attr('exnm')
    	}
    	getlist({
	    	pageNo:1,
	    	pageSize:10,
	    	entity:obj
	    });
    });
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/jsh/getDtTranRemsgs.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify( obj ),
    		async:false,
    		success:function(data){
    			if(data.code==01){
    				userdata= data.codeMessage;
    				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
    				listnumtotal=userdata.total;
    				/*$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    				*/
    			}else {
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    			}
    		}
    	});
    }
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
		      	    	pageNo:obj.curr,
		      	    	pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1,
		      	    	entity:{
		      	    		rgdt:$('#d1').val(),
		              		crdt:$('#d2').val(),
		              		rgtp:$('.signout input[type="radio"]:checked').attr('rgtp'),//0，签约；1，解约
		          			cuac:$('.dt_qian input').val(),
		          	    	exnm:$('.cuu_pair select option:selected').attr('exnm')
		      	    	}
		      	    });
		    	}
		     }
		  });
	});
	/*----------------快速搜索功能的实现------------------------*/
	$('.info_serbtn').click(function(){
		  var txt=$('.info_seript').val();
		  dialog.serchData(txt);
    });
	/*-----------------封装检查方法------------------------*/
	 //导出excel;
	  $('#logon').click(function(){
		  $('#fornm input').remove();
		  var str='<input type="text" name = "tableName" value='+tit+'>'+
		  		  '<input type="text" name = "rgdt" value='+$('#d1').val()+'>'+
		  		  '<input type="text" name = "crdt" value='+$('#d2').val()+'>'+
		  		  '<input type="text" name = "rgtp" value='+$('.signout input[type="radio"]:checked').attr('rgtp')+'>'+
		  		  '<input type="text" name = "cuac" value='+$('.dt_qian input').val()+'>'+
		  		  '<input type="text" name = "exnm" value='+$('.cuu_pair select option:selected').attr('exnm')+'>';
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
     });
	    //点击分页;
	    /*$('.boxtop').on('click','.first',function(){
	    	var Nopage=$('.Nopage').text()*1;
	 	    if(Nopage>1){
	 	    	Nopage=1;
	 	    	getlist({
		 	       	pageNo:Nopage,
		 	       	pageSize:10,
		 	       	entity:{
			 	       	rgdt:$('#d1').val(),
		 	    		crdt:$('#d2').val(),
		 	    		rgtp:$('.signout input[type="radio"]:checked').attr('rgtp'),//0，签约；1，解约
		 				cuac:$('.dt_qian input').val(),
		 		    	exnm:$('.cuu_pair select option:selected').attr('exnm')
		 	       	}
	 	       });
	 	    }
	    });
		$('.boxtop').on('click','.prev',function(){
		    var Nopage=$('.Nopage').text()*1;
		    if(Nopage>1){
		    	Nopage=Nopage-1;
		    	getlist({
		 	       	pageNo:Nopage,
		 	       	pageSize:10,
		 	       	entity:{
			 	       	rgdt:$('#d1').val(),
		 	    		crdt:$('#d2').val(),
		 	    		rgtp:$('.signout input[type="radio"]:checked').attr('rgtp'),//0，签约；1，解约
		 				cuac:$('.dt_qian input').val(),
		 		    	exnm:$('.cuu_pair select option:selected').attr('exnm')
		 	       	}
	 	       });
		    }
		});
		$('.boxtop').on('click','.next',function(){
			var Nopage=$('.Nopage').text()*1,
				Totalpage=$('.Totalpage').text()*1;
		    if(Nopage<Totalpage){
		    	Nopage=Nopage*1+1;
		    	getlist({
		 	       	pageNo:Nopage,
		 	       	pageSize:10,
		 	       	entity:{
			 	       	rgdt:$('#d1').val(),
		 	    		crdt:$('#d2').val(),
		 	    		rgtp:$('.signout input[type="radio"]:checked').attr('rgtp'),//0，签约；1，解约
		 				cuac:$('.dt_qian input').val(),
		 		    	exnm:$('.cuu_pair select option:selected').attr('exnm')
		 	       	}
	 	       });
		    }
		});
		$('.boxtop').on('click','.last',function(){
			var Nopage=$('.Nopage').text()*1,
			Totalpage=$('.Totalpage').text()*1;
		    if(Nopage<Totalpage){
		    	Nopage=Totalpage;
		    	getlist({
		 	       	pageNo:Nopage,
		 	       	pageSize:10,
		 	       	entity:{
		 	   	    	rgdt:$('#d1').val(),
		 	    		crdt:$('#d2').val(),
		 	    		rgtp:$('.signout input[type="radio"]:checked').attr('rgtp'),//0，签约；1，解约
		 				cuac:$('.dt_qian input').val(),
		 		    	exnm:$('.cuu_pair select option:selected').attr('exnm')
		 	       	}
	 	       });
		    }
		});*/
});
