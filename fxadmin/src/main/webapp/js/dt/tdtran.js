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
            { title:'流水号', name:'LCNO',width:280,align:'left' },
            { title:'证件号码', name:'IDNO',width:100, align:'left'},
            { title:'定投编号', name:'RSNO',width:280, align:'left'},
            { title:'客户号', name:'CUNO',width:100, align:'left'},
            { title:'结购汇标志', name:'JGFG',width:100,align:'center'},
            { title:'人民币币种', name:'CYNM',width:100, align:'left'},
            { title:'结购汇标志', name:'JGFG',width:100, align:'left'},
            { title:'外币币种', name:'FONM',width:100, align:'left'},
            { title:'外币金额', name:'FOAM',width:100, align:'right'},
            { title:'人民币金额', name:'CYAM',width:100, align:'right'},
            { title:'成交价格', name:'EXPC',width:100, align:'right'},
            { title:'交易日期', name:'TRDT',width:100, align:'right'},
            { title:'交易时间', name:'TRTM',width:100, align:'right'},
            { title:'状态标识码', name:'STATUS_CODE',width:100, align:'center'},
            { title:'卡号', name:'CUAC',width:100, align:'left'}
    ];
    //获取产品和用户名；
    var usnm=sessionStorage.getItem('usnm'),
		product=sessionStorage.getItem('product'),
		user={'usnm':usnm,'prod':product},
		listnumtotal,
		userdata;
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
    	tit=$('title').text();
    //请求列表数据；
    getlist({
      	pageNo:1,
      	pageSize:10,
      	entity:{
      		trdt:$('#d1').val(),
      		trtm:$('#d2').val(),
  			cuac:$('.dt_qian input').val(),
  			fonm:$('.cuu_pair select option:selected').attr('cyen')
      	}
    });
    renpage();
    //请求币种；
    $.ajax({
		url:'/fx/jsh/getDtCyMsg.do',
		type:'get',
		contentType:'application/json',
		async:true,
		success:function(data){
			var str='<option cyen="">请选择</option>';
			if(data.code==01){
				userdata= data.codeMessage;
				for(var i in userdata){
					str+='<option cyen='+userdata[i].CYEN+'>'+userdata[i].CYCN+'</option>'
				}
				$('.cuu_pair select').html( str );
			}else {
			}
		}
	});
    //点击查询按钮;
    $('.dt_bot button').click(function(){
    	var Nopage=1;
    	var obj={
    		trdt:$('#d1').val(),
    		trtm:$('#d2').val(),
			cuac:$('.dt_qian input').val(),
			fonm:$('.cuu_pair select option:selected').attr('cyen')
    	}
    	getlist({
        	pageNo:Nopage,
        	pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1,
        	entity:obj
        });
    	renpage();
    });
    function getlist( obj ){
    	$.ajax({
    		url:'/fx/jsh/getDtTranList.do',
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
    	                  	pageNo:obj.curr,
    	                  	pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1,
    	                  	entity:{
    	                  		trdt:$('#d1').val(),
    	                  		trtm:$('#d2').val(),
    	              			cuac:$('.dt_qian input').val(),
    	              			fonm:$('.cuu_pair select option:selected').attr('cyen')
    	                  	}
    	                });
    		    	}	
    		    }
    		  });
    	});
    }
    //导出Excel
	$('#logon').click(function(){
		$('#fornm input').remove();
		var str='<input type="text" name ="tableName" value='+tit+'>'+
			'<input type="text" name ="trdt" value='+$('#d1').val()+'>'+
			'<input type="text" name ="trtm" value='+$('#d2').val()+'>'+
			'<input type="text" name ="cuac" value='+$('.dt_qian input').val()+'>'+
			'<input type="text" name ="fonm" value='+$('.cuu_pair select option:selected').attr('cyen')+'>';
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
		 	       	pageNo:1,
		 	       	pageSize:10,
		 	       	entity:{
		 	   	    	trdt:$('#d1').val(),
		 	    		trtm:$('#d2').val(),
		 				cuac:$('.dt_qian input').val(),
		 				fonm:$('.cuu_pair select option:selected').attr('cyen')
		 	       	}
	 	       });
	 	    }
	    });
		$('.boxtop').on('click','.prev',function(){
		    var Nopage=$('.Nopage').text()*1;
		    if(Nopage>1){
		    	Nopage=Nopage-1;
		    	getlist({
		 	       	pageNo:1,
		 	       	pageSize:10,
		 	       	entity:{
			 	       	trdt:$('#d1').val(),
		 	    		trtm:$('#d2').val(),
		 				cuac:$('.dt_qian input').val(),
		 				fonm:$('.cuu_pair select option:selected').attr('cyen')
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
		 	       	pageNo:1,
		 	       	pageSize:10,
		 	       	entity:{
			 	       	trdt:$('#d1').val(),
		 	    		trtm:$('#d2').val(),
		 				cuac:$('.dt_qian input').val(),
		 				fonm:$('.cuu_pair select option:selected').attr('cyen')
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
		 	       	pageNo:1,
		 	       	pageSize:10,
		 	       	entity:{
			 	       	trdt:$('#d1').val(),
		 	    		trtm:$('#d2').val(),
		 				cuac:$('.dt_qian input').val(),
		 				fonm:$('.cuu_pair select option:selected').attr('cyen')
		 	       	}
	 	       });
		    }
		});*/
});
