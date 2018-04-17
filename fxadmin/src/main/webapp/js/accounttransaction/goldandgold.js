require.config({
	baseUrl:'/fx/js/',
	shim:{
		'jquery.niceScroll': {
			deps: ['jquery'],
			exports: 'jquery.niceScroll'
				},
		'mmGrid':{
			deps:['jquery'],
			exports:'mmGrid'
		},
		'table2excel':{
			deps:['jquery'],
			exports:'table2excel'
		}
	},
	paths:{
		jquery:'js_files/jquery-1.9.1.min',
		mmGrid:'js_files/mmGrid',
		niceScroll:'js_files/jquery.nicescroll.min',
		dialog:'dialog',
		wdatePicker:'./My97DatePicker/WdatePicker'
	}
});
require(['jquery','mmGrid','niceScroll','wdatePicker','dialog'],function($,mmGrid,niceScroll,wdatePicker,dialog){
	var wh=$(window).height()-200+"px",
		ww=$(window).width()-260+"px";
   var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
		tit=$('title').text();
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('#page').css('width',ww);
	//列参数;
    var cols = [
        { title:'客户号', name:'CUNO' ,width:80, align:'left' },         
        { title:'卡号', name:'CUAC' ,width:120, align:'left' },        
        { title:'开卡机构号', name:'OGCD' ,width:100, align:'left' },
        { title:'开卡机构名称', name:'OGNM' ,width:180, align:'left'},
        { title:'余额', name:'CBLV' ,width:80, align:'right' },        
        { title:'签约日期', name:'QYDT' ,width:80, align:'right' },
        { title:'签约时间', name:'QYTM' ,width:80, align:'right'}  
    ];
    var juesedata=false,jigoudata=false,jueseda,jigouda,listnumtotal;
    //请求列表
     var vo={'pageNo':1,'pageSize':10,'data':$('#goldborngold').val()};
     getList(vo);
     //请求总额
     $.ajax({
 		url:'/fx/selectSumcblv.do',
 		type:'post',
 		data:{'data':$('#goldborngold').val()},
 		dataType:'html',
 	    async:true,
 		success:function(data){
 			data = JSON.parse(data);
 			if(data.code==00){
    			$('.totaldeal').val(data.codeMessage);   
    	    }else if(data.code==01){
    				 
            } 
 		}
 	 }); 
     $("#goldborngold").change(function () {  
    	 $.ajax({
    	 		url:'/fx/selectSumcblv.do',
    	 		type:'post',
    	 		data:{'data':$('#goldborngold').val()},
    	 		dataType:'html',
    	 	    async:true,
    	 		success:function(data){
    	 			data = JSON.parse(data);
    	 			if(data.code==00){
    	    			$('.totaldeal').val(data.codeMessage);   
    	    	    }else if(data.code==01){
    	    				 
    	            } 
    	 		}
    	 	 }); 
    	 var vo={'pageNo':1,'pageSize':10,'data':$('#goldborngold').val()};
         getList(vo);
         renpage();
     });  
    //点击请选择一条数据的确定按钮；
//	$('body',parent.document).on('click','.adduserleval .sure',function(){
//		  $(this).closest('.zhezhao').remove();
//	});
//	//关闭弹窗
//	$('body',parent.document).on('click','.discountpub .close,.discountpub .cancel',function(){
//		  $(this).closest('.zhezhao').remove();
//		   
//	});
    //封装请求列表		
	function getList(obj){
		$.ajax({
    		url:'/fx/selectGold.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			if(data.code==01){
       				userdata=data.codeMessage ;
       				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list});
       				listnumtotal=userdata.total;
//       				$('.page').remove();
//    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
       			}else{
       				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
       			}
    		}
    	}); 
	}
	renpage();
	//导出；
	$('#logon').click(function(){
		  $('#fornm input').remove();
		  var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tit+'>';
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
	});
	//点击分页;
    $('.boxtop').on('click','.page .first',function(){
    	var Nopage=parseInt($('.Nopage').text());
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	vo={'pageNo':Nopage,'pageSize':10};
	        getList(vo);
 	     }
 	    
    });
	$('.boxtop').on('click','.prev',function(){
		var Nopage=parseInt($('.Nopage').text());
	    if(Nopage>1){
	    	Nopage=Nopage-1;
	    	vo={'pageNo':Nopage,'pageSize':10};
	        getList(vo);
	     }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=parseInt($('.Nopage').text());
		   Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Nopage*1+1;
	    	vo={'pageNo':Nopage,'pageSize':10};
	        getList(vo);
	     }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=parseInt($('.Nopage').text());
		   Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	vo={'pageNo':Nopage,'pageSize':10};
	        getList(vo);
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
			    		getList({
			    			'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1,
	    				    'pageNo':obj.curr
			    			});
			    	}	
			    }
			  });
		});
	} 
	
	 
	  
})
