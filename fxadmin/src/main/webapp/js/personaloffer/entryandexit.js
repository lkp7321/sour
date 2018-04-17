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
	    ww=$(window).width()-260+"px",
	    num=0,
	    userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,
	    listnumtotal,
		tit=$('title').text();
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	$('.dealdate #d1').val(dialog.today());
	$('.dealdate #d2').val(dialog.today());
	//列参数
    var cols = [
			{ title:'机构', name:'OGNM' ,width:100, align:'left'},
			{ title:'卡号', name:'CUAC' ,width:100, align:'left'},
			{ title:'交易流水号', name:'LCNO' ,width:120, align:'left'},
			{ title:'交易日期', name:'TRDT' ,width:120, align:'right'},
			{ title:'交易时间', name:'TRTM' ,width:120, align:'right'},
			{ title:'交易类型', name:'TRDS' ,width:100, align:'center'},
			{ title:'交易币种', name:'CYEN' ,width:80, align:'left'},
			{ title:'钞汇标志', name:'CXFG' ,width:100, align:'center'},
			{ title:'发生额', name:'AMUT' ,width:80, align:'right'},
			{ title:'记录状态', name:'STCD' ,width:120, align:'center'}
    ];
  //请求机构1
    $.ajax({
		url:'/fx/comboxA.do',
		type:'get',
		async:false,
		dataType:'json',
		success:function(data){
			 if(data.code==01){
				var listdata=data.codeMessage,str;
				for(var i in listdata){
					 str+='<option value='+listdata[i].ogcd+'>'+listdata[i].ognm+'</option>'
				}
				$('.longone').html(str);
				 var comaogcd=$('.longone option:selected').val();
				     getComboxB( comaogcd );
			  }else if(data.code==02){
				 //获取机构失败
			}
		}
	});
    
    //请求机构2
    $('.longone').change(function(){
    	comaogcd=$('.longone option:selected').val();
    	getComboxB( comaogcd );
    });   
    function getComboxB( obj ){
    	$.ajax({
    		url:'/fx/comboxB.do',
    		type:'post',
    		async:false,
    		dataType:'json',
    		data:obj,
    			contentType:'application/json;charset=utf-8',
    			beforeSend:function(){
    				 str='<option value="all">所有</option>';
    			},
    			success:function(data){
    			 if(data.code==01){
    				var listdata=data.codeMessage;
    				for(var i in listdata){
    					str+='<option value='+listdata[i].ogcd+'>'+listdata[i].ognm+'</option>'
    				}
    				$('.longtwo').html(str);
    			   }else if(data.code==02){
    				 //获取失败
    			}
    		}
    	});
    } 
    var curLcno=$('.entryandexit .curLcno').val(),
        strcuac=$('.entryandexit .strcuac').val(),
        trdtbegin=$('.dealdate #d1').val(),
        trdtend=$('.dealdate #d2').val(),
        comaogcd=$('.longone option:selected').val(),
        combogcd=$('.longtwo option:selected').val(),
        bsVo;
    	if( curLcno==''){
		   curLcno='';
	   }else{
		   curLcno=curLcno;
	   }
	   if(strcuac==''){
		   strcuac='';
	   }else{
		   strcuac=strcuac;
	   }
       bsVo={'userKey':userkey,'curLcno':curLcno,'strcuac':strcuac,'trdtbegin':trdtbegin,'trdtend':trdtend,'comaogcd':comaogcd,'combogcd':combogcd,'pageNo':1,'pageSize':10};
       getAllTransferTrade( bsVo );
       renpage();
   $('.serch').click(function(){
	   curLcno=$('.entryandexit .curLcno').val(),
       strcuac=$('.entryandexit .strcuac').val(),
       trdtbegin=$('.dealdate #d1').val(),
       trdtend=$('.dealdate #d2').val(),
       comaogcd=$('.entryandexit .longone option:selected').val(),
       combogcd=$('.entryandexit .longtwo option:selected').val(),
       bsVo;
	   if( curLcno==''){
		   curLcno='';
	   }else{
		   curLcno=curLcno;
	   }
	   if(strcuac==''){
		   strcuac='';
	   }else{
		   strcuac=strcuac;
	   }
       bsVo={'userKey':userkey,'curLcno':curLcno,'strcuac':strcuac,'trdtbegin':trdtbegin,'trdtend':trdtend,'comaogcd':comaogcd,'combogcd':combogcd,'pageNo':1,'pageSize':10};
       getAllTransferTrade(bsVo );
       renpage();
   })
    
    
    //封装请求列表
    function getAllTransferTrade( obj ){
    	 $.ajax({
    		url:'/fx/getAllTransferTrade.do',
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj),
    		async:false,
    		success:function(data){
    			num++;
    			if(data.code==01){
    				userdata= data.codeMessage ;
    			    ren({'cols':cols,'wh':wh,'userdata':userdata});
    			    listnumtotal=userdata.total;
    			    /*$('.page').remove();
    			    $('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    			*/
    			}else if(data.code==00){
    				ren({'cols':cols,'wh':wh,'userdata':''});
    				if(num>1){
    					dialog.choicedata(data.codeMessage,'entryandexit');
    				}
    				
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
    		    		getAllTransferTrade({
    		 	    		'userKey':userkey,
    		        		'strcuac':$('.cardnum').val(),
    		        		'trdtbegin':$('.dealdate #d1').val(),
    		        		'trdtend':$('.dealdate #d2').val(),
    		        		'curLcno':$('.entryandexit .curLcno').val(),
    		        		'comaogcd':$('#comboxA option:selected').val(),
    		        		'combogcd':$('#comboxB option:selected').val(),
    		        		'pageNo':obj.curr,
    		        		'pageSize':$('.layui-laypage-limits select option:selected').attr('value')*1
    		 	    	});
    		    	}	
    		    }
    		  });
    	});
    }
    	//点击分页;
	   /* $('.boxtop').on('click','.first',function(){
	    	var Nopage=$('.Nopage').text()*1;
	 	    if(Nopage>1){
	 	    	Nopage=1;
	 	    	getAllTransferTrade({
	 	    		'userKey':userkey,
	        		'strcuac':$('.cardnum').val(),
	        		'trdtbegin':$('.dealdate #d1').val(),
	        		'trdtend':$('.dealdate #d2').val(),
	        		'curLcno':$('.entryandexit .curLcno').val(),
	        		'comaogcd':$('#comboxA option:selected').val(),
	        		'combogcd':$('#comboxB option:selected').val(),
	        		'pageNo':Nopage,
	        		'pageSize':10
	 	    	});
	 	    }
	    });
		$('.boxtop').on('click','.prev',function(){
		    var Nopage=$('.Nopage').text()*1;
		    if(Nopage>1){
		    	Nopage=Nopage-1;
		    	getAllTransferTrade({
	 	    		'userKey':userkey,
	        		'strcuac':$('.cardnum').val(),
	        		'trdtbegin':$('.dealdate #d1').val(),
	        		'trdtend':$('.dealdate #d2').val(),
	        		'curLcno':$('.entryandexit .curLcno').val(),
	        		'comaogcd':$('#comboxA option:selected').val(),
	        		'combogcd':$('#comboxB option:selected').val(),
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
		    	getAllTransferTrade({
	 	    		'userKey':userkey,
	        		'strcuac':$('.cardnum').val(),
	        		'trdtbegin':$('.dealdate #d1').val(),
	        		'trdtend':$('.dealdate #d2').val(),
	        		'curLcno':$('.entryandexit .curLcno').val(),
	        		'comaogcd':$('#comboxA option:selected').val(),
	        		'combogcd':$('#comboxB option:selected').val(),
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
		    	getAllTransferTrade({
	 	    		'userKey':userkey,
	        		'strcuac':$('.cardnum').val(),
	        		'trdtbegin':$('.dealdate #d1').val(),
	        		'trdtend':$('.dealdate #d2').val(),
	        		'curLcno':$('.entryandexit .curLcno').val(),
	        		'comaogcd':$('#comboxA option:selected').val(),
	        		'combogcd':$('#comboxB option:selected').val(),
	        		'pageNo':Nopage,
	        		'pageSize':10
	 	    	});
		    }
		});*/
  //渲染列表
    function ren(obj){
    	$('.boxtop').html('');
    	$('#ascrail2000').remove();
    	$('.boxtop').append('<div class="box"></div>');
		var mmg = $('.box').mmGrid({
				height:obj.wh
				, cols: obj.cols
				,items:obj.userdata
	            , nowrap:true
	            , fullWidthRows:true
	            , checkCol:obj.checked
	            , multiSelect:obj.select
	            ,showBackboard:true
	  	});
		 $(".mmg-bodyWrapper").niceScroll({
				touchbehavior:false,
				cursorcolor:"#666",
				cursoropacitymax:0.7,
				cursorwidth:6,
				background:"#ccc",
				autohidemode:false,
				horizrailenabled:true
		  });
    }
    $('body',parent.document).on('click','.entryandexit .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
/*----------------快速搜索功能的实现------------------------*/
	/*$('.review_serbtn').click(function(){
		 dialog.serchData('.review_seript');
    });*/
    $('#logon').click(function(){
		 $('#fornm input').remove();
		  var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tit+'>'+
		  		  '<input type="text" name = "trdtbegin" value='+$('.dealdate #d1').val()+'><input type="text" name = "curLcno" value='+$('.entryandexit .curLcno').val()+'>'+
		  		  '<input type="text" name = "trdtend" value='+$('.dealdate #d2').val()+'><input type="text" name = "comaogcd" value='+$('#comboxA option:selected').val()+'>'+
		  		  '<input type="text" name = "strcuac" value='+$('.cardnum').val()+'><input type="text" name = "combogcd" value='+$('#comboxB option:selected').val()+'>';
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
    });

})

