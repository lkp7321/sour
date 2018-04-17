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
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('.page').css('width',ww);
	$('#d1,#d2').val(dialog.today());
	 
	//列参数;
    var cols = [
        { title:'挂单类型', name:'ODTP' ,width:120, align:'center' },
        { title:'交易日期', name:'PRDT' ,width:120, align:'right'},
        { title:'成交时间', name:'PRTM' ,width:120, align:'right'},
        { title:'客户号', name:'CUNO' ,width:120, align:'left' },
        { title:'钞汇标志', name:'CXFG' ,width:120, align:'center' },
        { title:'止盈汇率', name:'AKPC' ,width:120, align:'right' },
        { title:'止损汇率', name:'ZSPC' ,width:120, align:'right' },
        { title:'成交汇率', name:'EXPC' ,width:120, align:'right' },
        { title:'挂单日期', name:'TRDT' ,width:120, align:'right' },
        { title:'挂单时间', name:'TRTM' ,width:120, align:'right' },
        { title:'渠道类型', name:'QDTP' ,width:120, align:'center' },
        { title:'优惠点数', name:'FVDA' ,width:120, align:'right' },
        { title:'客户购/结汇外币名称', name:'FONM' ,width:120, align:'left' },
        { title:'客户购/结汇人民币名称', name:'CYNM' ,width:120, align:'left' },
        { title:'委托状态', name:'STCD' ,width:120, align:'center' },
       /* { title:'卖出币种金额', name:'' ,width:120, align:'right'},
        { title:'买入币种金额', name:'stdt' ,width:120, align:'right'}*/
    ]
    var userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,prevpage,
    	tit=$('title').text();
        
    //根据用户登录信息 获取机构号和机构名称
    /*$.ajax({
		url:'/fx/getOrgn.do',
		type:'post',
		contentType:'application/json',
		data:userkey,
		async:true,
		success:function(data){
			 if(data.code==00){
			 	 userdata=JSON.parse( data.codeMessage );
			     $('.agentname').val(userdata.exnm);
			     $('.agentnum').val(userdata.ogcd);
			     sessionStorage.obj =userdata.ogcd;
			}else if(data.code==01){
				//异常
			}
		}
	}); */
    //请求列表
    var product="P004",
        ogcdVo={
	    	pageNo :1,
	    	pageSize : 10,
	    	entity:{
	    		productCode :product,
 		    	qutp :$('.gdState select option:selected').attr('value'),
 		    	cuno :$('.userNum').val(),
 		    	rqno :$('.guadanNum').val(),
 		    	stdt :$('#d1').val(),
 		    	eddt :$('#d2').val()
	    	}
    	} 
    	getOrgnlist(ogcdVo);
    //点击查询请求列表
    $('.pub_button').click(function(){
    	 ogcdVo={
 	    	pageNo :1,
 	    	pageSize : 10,
 	    	entity:{
 		    	productCode :product,
 		    	qutp :$('.gdState select option:selected').attr('value'),
 		    	cuno :$('.userNum').val(),
 		    	rqno :$('.guadanNum').val(),
 		    	stdt :$('#d1').val(),
 		    	eddt :$('#d2').val()
 	    	}
     	} 
    	 getOrgnlist(ogcdVo);
    })
	//关闭弹窗
	$('body',parent.document).on('click','.discountpub .close,.discountpub .cancel,.agentchoose .close,.agentchoose .cancel',function(){
		  $(this).closest('.zhezhao').remove();
	});
    //封装请求列表
	function getOrgnlist(obj){
		$.ajax({
    		url:'getPreodrlist.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:true,
    		success:function(data){
    			if(data.code==00){
       				userdata=JSON.parse( data.codeMessage );
       				dialog.ren({'cols':cols,'wh':wh,'userdata':userdata.list,'checked':true});
       				$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
       			}else{
       				dialog.ren({'cols':cols,'wh':wh,'userdata':'','checked':true});
       			}
    		}
    	}); 
	}
	//点击分页;
    $('.boxtop').on('click','.page .first',function(){
    	var Nopage=parseInt($('.Nopage').text());
 	    if(Nopage>1){
 	    	Nopage=1;
 	    	ogcdVo={
    	    	pageNo :Nopage,
    	    	pageSize : 10,
    	    	entity:{
    		    	productCode :product,
    		    	qutp :$('.gdState select option:selected').attr('value'),
    		    	cuno :$('.userNum').val(),
    		    	rqno :$('.guadanNum').val(),
    		    	stdt :$('#d1').val(),
    		    	eddt :$('#d2').val()
    	    	}
          } 
       	  getOrgnlist(ogcdVo);
 	   }
    });
	$('.boxtop').on('click','.prev',function(){
	    var Nopage=parseInt($('.Nopage').text());
	    if(Nopage>1){
	    	Nopage=Nopage-1;
 	    	ogcdVo={
    	    	pageNo :Nopage,
    	    	pageSize : 10,
    	    	entity:{
    		    	productCode :product,
    		    	qutp :$('.gdState select option:selected').attr('value'),
    		    	cuno :$('.userNum').val(),
    		    	rqno :$('.guadanNum').val(),
    		    	stdt :$('#d1').val(),
    		    	eddt :$('#d2').val()
    	    	}
        	} 
       	  getOrgnlist(ogcdVo);
	    }
	});
	$('.boxtop').on('click','.next',function(){
		var Nopage=parseInt($('.Nopage').text()),
			Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Nopage+1;
	    	ogcdVo={
    	    	pageNo :Nopage,
    	    	pageSize : 10,
    	    	entity:{
    		    	productCode :product,
    		    	qutp :$('.gdState select option:selected').attr('value'),
    		    	cuno :$('.userNum').val(),
    		    	rqno :$('.guadanNum').val(),
    		    	stdt :$('#d1').val(),
    		    	eddt :$('#d2').val()
    	    	}
        	} 
       	  getOrgnlist(ogcdVo);
	     }
	});
	$('.boxtop').on('click','.last',function(){
		var Nopage=parseInt($('.Nopage').text()),
		Totalpage=parseInt($('.Totalpage').text());
	    if(Nopage<Totalpage){
	    	Nopage=Totalpage;
	    	ogcdVo={
    	    	pageNo :Nopage,
    	    	pageSize : 10,
    	    	entity:{
    		    	productCode :product,
    		    	qutp :$('.gdState select option:selected').attr('value'),
    		    	cuno :$('.userNum').val(),
    		    	rqno :$('.guadanNum').val(),
    		    	stdt :$('#d1').val(),
    		    	eddt :$('#d2').val()
    	    	}
        	} 
       	   getOrgnlist(ogcdVo);
	    }
	});
	//导出excel;
	  $('#logon').click(function(){
		  $('#fornm input').remove();
		  var str='<input type="text" name = "tableName" value='+tit+'>'+
		  		  '<input type="text" name = "productCode" value='+product+'>'+
		  		  '<input type="text" name = "qutp" value='+$('.gdState select option:selected').attr('value')+'>'+
		  		  '<input type="text" name = "cuno" value='+$('.userNum').val()+'>'+
		  		  '<input type="text" name = "rqno" value='+$('.guadanNum').val()+'>'+
		  		  '<input type="text" name = "stdt" value='+$('#d1').val()+'>'+
		  		  '<input type="text" name = "eddt" value='+$('#d2').val()+'>';
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
   });
})
