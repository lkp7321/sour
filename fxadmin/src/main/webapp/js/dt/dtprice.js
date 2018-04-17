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
            { title:'币别对名称', name:'EXNM',width:100,align:'left' },
            { title:'日期', name:'TRDT',width:100, align:'right'},
            { title:'时间', name:'TRTM',width:100, align:'right'},
            { title:'客户买入价', name:'TCBY',width:100, align:'right'},
            { title:'客户卖出价', name:'TCSL',width:100,align:'right'},
            { title:'客户中间价', name:'TCMD',width:100, align:'right'},
            { title:'价格序列号', name:'SEQU',width:100, align:'left'}	
    ];
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,listnumtotal,
    	tit=$('title').text();
    //请求列表数据；
    getlist({
		pageNo:1,
		pageSize:$('.layui-laypage-limits select option:selected').attr('value')*1,
    	entity:{
    		trdt:$('#d1').val(),
    		trtm:$('#d2').val(),
    		exnm:$('.cuu_pair select option:selected').attr('exnm')
    	}
	});
    renpage();
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
    	var Nopage=1;
    	var obj={
    		trdt:$('#d1').val(),
    		trtm:$('#d2').val(),
    		exnm:$('.cuu_pair select option:selected').attr('exnm')
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
    		url:'/fx/jsh/getDtPrice.do',
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
	  		              		exnm:$('.cuu_pair select option:selected').attr('exnm')
	  	                  	}
	  	              	});
	  		    	}
	  		    }
	  		  });
	     });
	   
    }
    
  //导出excel;
  $('#logon').click(function(){
	  $('#fornm input').remove();
	  var str='<input type="text" name = "tableName" value='+tit+'>'+
	  		  '<input type="text" name = "trdt" value='+$('#d1').val()+'>'+
	  		  '<input type="text" name = "trtm" value='+$('#d2').val()+'>'+
	  		  '<input type="text" name = "exnm" value='+$('.cuu_pair select option:selected').attr('exnm')+'>';
	  for(var i in cols){
		  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
	  }
	  $('#fornm').append( str );
	  $('#fornm').submit();
   });
	  
    //点击分页;
   /* $('.boxtop').on('click','.first',function(){
    	var Nopage=$('.Nopage').text()*1;
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	getlist({
	        	pageNo:Nopage,
	        	pageSize:10,
	        	entity:{
	        		trdt:$('#d1').val(),
	        		trtm:$('#d2').val(),
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
	        		trdt:$('#d1').val(),
	        		trtm:$('#d2').val(),
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
	        		trdt:$('#d1').val(),
	        		trtm:$('#d2').val(),
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
	        		trdt:$('#d1').val(),
	        		trtm:$('#d2').val(),
	        		exnm:$('.cuu_pair select option:selected').attr('exnm')
	        	}
	        });
	    }
	});*/
});
