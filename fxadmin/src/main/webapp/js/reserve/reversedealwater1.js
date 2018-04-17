/*个人-交易流水查询*/
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
	var wh=$(window).height()-230+"px",
		ww=$(window).width()-260+"px";
	var product=sessionStorage.getItem('product'),
		userkey=JSON.parse( sessionStorage.getItem('menu') ).userKey,listnumtotal,
		tile=$('title').text();
	$('.boxtop').css('width',ww);
	$('.boxtop').css('height',wh);
	
	//列参数
    var cols = [
        { title:'交易流水号', name:'lcno' ,width:100, align:'left' },
        { title:'机构', name:'ognm' ,width:150, align:'left'},
        { title:'卡号', name:'cuac' ,width:150, align:'left'},
        { title:'交易日期', name:'trdt' ,width:80, align:'right'},
        { title:'交易时间', name:'trtm' ,width:120, align:'right'},
        { title:'交易类型', name:'trds' ,width:100, align:'center'},
        { title:'买入币种', name:'bynm' ,width:80, align:'left'},
        { title:'卖出币种', name:'slnm' ,width:100, align:'left'},
        { title:'容忍点差', name:'rrdt' ,width:80, align:'right'},
        { title:'买入金额', name:'byam',width:80, align:'right'},
        { title:'成交汇率', name:'slam' ,width:80, align:'right'},
        { title:'卖出金额', name:'expc' ,width:80, align:'right'},
        { title:'发生额（转账）', name:'amut' ,width:80, align:'right'},
        { title:'优惠点数', name:'fvda' ,width:80, align:'right'},
        { title:'分行损益', name:'brsy' ,width:80, align:'right'},
        { title:'折美元金额', name:'usam' ,width:80, align:'right'},
        { title:'交易状态', name:'stcd' ,width:80, align:'center'}
    ];
    $('.dealdate #d1').val(dialog.today());
    $('.dealdate #d2').val(dialog.today());
    
    var num=0;
    //请求机构名称一
    getList('comboxA.do','#comboxA');  
    $('#comboxA').change(function(){
    	comaogcd=$('#comboxA option:selected').attr('value');
    	getDeallists('comboxB.do',comaogcd,'#comboxB');
    });
    $('#tranCode').click(function(){
    	var valt=$(this).find('option:selected').attr('value');
    	if(valt==2){
    		$('#tranCode1').show();
    	}else{
    		$('#tranCode1').hide();
    	}
    });
    $.ajax({
		url:'/fx/getTranCode.do',
		type:'post',
		async:false,
		contentType:'application/json',
		dataType:'json',
		data:userkey,
		success:function(data){
		    var str='<option trcd="all">所有</option>';
		    if(data.code==01){
		    	var data= data.codeMessage;
		    	 $.each(data,function(i,v){
				    str+='<option trcd='+v.TRCD+'>'+v.TRDS+'</option>'
				})
			    $('#tranCode').html(str);
		    }
		}
    });
    //请求买币种
    $.ajax({
		url:'/fx/getByExnm.do',
		type:'post',
		async:true,
		contentType:'application/json',
		dataType:'json',
		data:userkey,
		success:function(data){
		    var str='<option value="all">所有</option>';
		    if(data.code==01){
		    	var data= data.codeMessage;
		    	 $.each(data,function(i,v){
				    str+='<option>'+v+'</option>'
				})
			    $('#byExnm').html(str);
		    }
		}
    });
   var 	comdata3='',
   		comdata1=$('#tranCode option:selected').attr('value'),
        byexnm=$('#byExnm option:selected').val(),
        comaogcd=$('#comboxA option:selected').val(),
        combogcd=$('#comboxB option:selected').val(),
        strcuac=$('.cardnum').val(),
        trdtbegin=$('.dealdate #d1').val(),
        trdtend=$('.dealdate #d2').val(),
        strcuno=$('.cusnum').val();
   		
   		getDeallists('comboxB.do',comaogcd,'#comboxB');
   		getAllTrans({
    		'userKey':userkey,
    		'trancode':$('#tranCode option:selected').attr('trcd'),
    		'strcuac':$('.cardnum').val(),
    		'trdtbegin':$('.dealdate #d1').val(),
    		'trdtend':$('.dealdate #d2').val(),
    		'byexnm':$('#byExnm option:selected').val(),
    		'comaogcd':$('#comboxA option:selected').val(),
    		'combogcd':$('#comboxB option:selected').val(),
    		'pageNo':1,
    		'pageSize':10
		});	
   		renpage();
   /*else{
	        bsVo={
	        	'comdata1':$('#tranCode option:selected').attr('value'),
	        	'comdata3':comdata3,
	        	'strcuac':$('.cardnum').val(),
	        	'trdtbegin':trdtbegin,
	        	'trdtend':trdtend,
	        	'byexnm':byexnm,
	        	'comaogcd':comaogcd,
	        	'combogcd':combogcd,
	        	'pageNo':1,
	        	'pageSize':10
	        	}
		}*/
   		//getAllTrans( bsVo);
   		//点击分页;
   	    /*$('.boxtop').on('click','.first',function(){
   	    	var Nopage=$('.Nopage').text()*1;
   	 	    if(Nopage>1){
   	 	    	Nopage=1;
   	 	    	getAllTrans({
	        		'userKey':userkey,
	        		'trancode':$('#tranCode option:selected').attr('trcd'),
	        		'strcuac':$('.cardnum').val(),
	        		'trdtbegin':$('.dealdate #d1').val(),
	        		'trdtend':$('.dealdate #d2').val(),
	        		'byexnm':$('#byExnm option:selected').val(),
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
   		    	getAllTrans({
	        		'userKey':userkey,
	        		'trancode':$('#tranCode option:selected').attr('trcd'),
	        		'strcuac':$('.cardnum').val(),
	        		'trdtbegin':$('.dealdate #d1').val(),
	        		'trdtend':$('.dealdate #d2').val(),
	        		'byexnm':$('#byExnm option:selected').val(),
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
   		    	getAllTrans({
	        		'userKey':userkey,
	        		'trancode':$('#tranCode option:selected').attr('trcd'),
	        		'strcuac':$('.cardnum').val(),
	        		'trdtbegin':$('.dealdate #d1').val(),
	        		'trdtend':$('.dealdate #d2').val(),
	        		'byexnm':$('#byExnm option:selected').val(),
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
   		    	getAllTrans({
	        		'userKey':userkey,
	        		'trancode':$('#tranCode option:selected').attr('trcd'),
	        		'strcuac':$('.cardnum').val(),
	        		'trdtbegin':$('.dealdate #d1').val(),
	        		'trdtend':$('.dealdate #d2').val(),
	        		'byexnm':$('#byExnm option:selected').val(),
	        		'comaogcd':$('#comboxA option:selected').val(),
	        		'combogcd':$('#comboxB option:selected').val(),
	        		'pageNo':Nopage,
	        		'pageSize':10
   				});
   		    }
   		});*/
    $('.search').click(function(){    	
    	comdata1=$('#tranCode option:selected').attr('value'),
    	comdata3=$('#tranCode1 option:selected').attr('value'),
        byexnm=$('#byExnm option:selected').val(),  //
        comaogcd=$('#comboxA option:selected').val(),
        combogcd=$('#comboxB option:selected').val(),
        strcuac=$('.cardnum').val(),
        trdtbegin=$('.dealdate #d1').val(),
        trdtend=$('.dealdate #d2').val(),
        strcuno=$('.cusnum').val();
        bsVo={'userKey':userkey,'trancode':$('#tranCode option:selected').attr('trcd'),'strcuac':strcuac,'trdtbegin':trdtbegin,'trdtend':trdtend,'byexnm':byexnm,'comaogcd':comaogcd,'combogcd':combogcd,'pageSize':10,'pageNo':1};

    	/*if(tile=='签约流水查询'){
 	       bsVo={'comdata1':comdata1,'comdata3':comdata3,'strcuno':strcuno,'strcuac':strcuac,'trdtbegin':trdtbegin,'trdtend':trdtend,'comaogcd':comaogcd,'combogcd':combogcd,'pageNo':1,'pageSize':10}
    	}else if(tile=='交易流水查询'){
    	}else{
            bsVo={'comdata1':comdata1,'comdata3':comdata3,'strcuac':strcuac,'trdtbegin':trdtbegin,'trdtend':trdtend,'byexnm':byexnm,'comaogcd':comaogcd,'combogcd':combogcd,'pageNo':1,'pageSize':10}
    	}*/
    	getAllTrans( bsVo );
    	renpage();
    })
    //封装请求列表
    function getAllTrans( obj ){
    	url='getAllTransList.do';
		cols=cols;
    	/*if(tile=='签约流水查询'){
    		url='selectAccumuRegTrade.do';
    		cols=cols1;
    	}else if(tile=='交易流水查询'){
    		url='getAllTransList.do';
    		cols=cols;
    	}else{
    		url='selectAccumuTranlist.do';
    		cols=cols;
    	}*/
    	 $.ajax({
    		url:url,
    		type:'post',
    		contentType:'application/json',
    		data:JSON.stringify(obj ),
    		async:false,
    		success:function(data){
    			num++;
    			if(data.code==01){
    				userdata= data.codeMessage;
    			    dialog.ren({'cols':cols,'wh':wh,'userdata':userdata});
    			    listnumtotal=userdata.total;
					/*$('.page').remove();
    				$('.boxtop').append('<div class="page"><span class="first">首页</span><span class="prev">上一页</span><span class="next">下一页</span><span class="last">末页</span><span class="nopage">第<b class="Nopage">'+userdata.pageNum+'</b>页/共<b class="Totalpage">'+userdata.pages+'</b>页</span><span class="totalpage">共<b class="total">'+userdata.total+'</b>条记录</span></div>')
    				*/
    			}else{
    				dialog.ren({'cols':cols,'wh':wh,'userdata':''});
    				if(num>1){
    					dialog.choicedata(data.codeMessage,'dealwater');
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
    		    		getAllTrans({
    		        		'userKey':userkey,
    		        		'trancode':$('#tranCode option:selected').attr('trcd'),
    		        		'strcuac':$('.cardnum').val(),
    		        		'trdtbegin':$('.dealdate #d1').val(),
    		        		'trdtend':$('.dealdate #d2').val(),
    		        		'byexnm':$('#byExnm option:selected').val(),
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
    //封装get请求到的option
    function getList(url,dom){
    	 $.ajax({
    			url:url,
    			type:'get',
    			async:false,
    			data:userkey,
    			dataType:'json',
    			success:function(data){
    				 if(data.code==01){
    					var listdata=data.codeMessage,str;
    					var str='';
    					for(var i in listdata){
    						 str+='<option value='+listdata[i].ogcd+'>'+listdata[i].ognm+'</option>'
    					}
    					$(''+dom+'').html(str);
    					 var comaogcd=$('#comboxA option:selected').attr('value');
    					 getDeallists('comboxB.do',comaogcd,'#comboxB');
    				  }else{
    					 //获取机构失败
    				}
    			}
    		});
    }
    //请求列表option
    function getDeallists(url,obj,dom){
    	$.ajax({
			url:url,
			type:'post',
			async:true,
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
					$(''+dom+'').html(str);
				   }else{
					 //获取失败
				}
			}
		});
    }
    $('body',parent.document).on('click','.dealwater .sure',function(){
		$(this).closest('.zhezhao').remove();
	});
/*----------------快速搜索功能的实现------------------------*/
	$('.review_serbtn').click(function(){
		var txt=$('.review_seript').val();
		  dialog.serchData(txt);
    });	
	//导出excel;
	 $('#logon').click(function(){
		 $('#fornm input').remove();
		  var str='<input type="text" name = "userKey" value='+userkey+'><input type="text" name = "tableName" value='+tile+'>'+
		  		'<input type="text" name = "trancode" value='+$('#tranCode option:selected').attr('trcd')+'><input type="text" name = "trdtbegin" value='+$('.showtime1').text()+'>'+
		  		'<input type="text" name = "trdtend" value='+$('.showtime2').text()+'><input type="text" name = "comaogcd" value='+$('#comboxA option:selected').val()+'>'+
		  		'<input type="text" name = "combogcd" value='+$('#comboxB option:selected').val()+'><input type="text" name = "byexnm" value='+$('#byExnm option:selected').val()+'>'+
		  		'<input type="text" name = "strcuac" value='+$('.cardnum').val()+'>';
		  for(var i in cols){
			  str+='<input type="text" name ="tableList['+i+'].name" value='+cols[i].title+'><input type="text" name ="tableList['+i+'].value" value='+cols[i].name+'>'
		  }
		  $('#fornm').append( str );
		  $('#fornm').submit();
    });
})

